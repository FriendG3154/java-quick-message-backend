package gin.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import gin.config.GatewayJwtConfig;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.data.redis.core.ReactiveStringRedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.List;
import java.util.Optional;

@Component
public class AuthGlobalFilter implements GlobalFilter, Ordered {

    private static final Logger log = LoggerFactory.getLogger(AuthGlobalFilter.class);
    private static final ObjectMapper MAPPER = new ObjectMapper();
    private static final AntPathMatcher PATH_MATCHER = new AntPathMatcher();
    private static final String LOGIN_TOKEN_KEY = "login:token:";
    private final SecretKey secretKey;
    private final ReactiveStringRedisTemplate redisTemplate;
    private final List<String> whiteList;

    public AuthGlobalFilter(GatewayJwtConfig jwtConfig, ReactiveStringRedisTemplate redisTemplate) {
        this.secretKey = Keys.hmacShaKeyFor(jwtConfig.getSecret().getBytes(StandardCharsets.UTF_8));
        this.redisTemplate = redisTemplate;
        this.whiteList = jwtConfig.getWhiteList();
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        String path = exchange.getRequest().getURI().getPath();
        if(whiteList.stream().anyMatch(p->PATH_MATCHER.match(p, path))) {
            return chain.filter(exchange);
        }
        String token = exchange.getRequest().getHeaders().getFirst("Authorization");
        if (token == null || token.isBlank()) {
            return writeUnauthorized(exchange,"密钥丢失");
        }
        Claims claims;
        try{
            claims = Jwts.parser()
                    .verifyWith(secretKey)
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();
        } catch (Exception e) {
            log.warn("[Auth] 验签失败, path={}, err={}", path, e.toString());
            return writeUnauthorized(exchange, "密钥验证失败");
        }
        String userId = claims.getSubject();
        return redisTemplate.opsForValue().get(LOGIN_TOKEN_KEY + userId)
                .map(Optional::of)
                .defaultIfEmpty(Optional.empty())
                .timeout(Duration.ofMillis(200))
                .onErrorResume(e -> {
                    // 故障开放:Redis 挂了降级为仅验签,避免拖垮整个网关
                    log.warn("[Auth] Redis 查询异常,降级为仅验签: {}", e.toString());
                    return Mono.just(Optional.empty());
                })
                .flatMap(state -> {
                    if (state.isPresent() && !state.get().equals(token)) {
                        // 已刷新出新 token 或已登出,旧 token 立即失效
                        return writeUnauthorized(exchange, "登录已失效,请重新登录");
                    }
                    // 校验通过,透传用户身份,下游无需再解析 JWT
                    ServerHttpRequest request = exchange.getRequest().mutate()
                            .header("X-User-Id", userId)
                            .build();
                    return chain.filter(exchange.mutate().request(request).build());
                });
    }

    /// 输出格式与下游 ApiResponse {code, message, data} 保持一致
    private Mono<Void> writeUnauthorized(ServerWebExchange exchange, String message) {
        ServerHttpResponse response = exchange.getResponse();
        response.setStatusCode(HttpStatus.UNAUTHORIZED);
        response.getHeaders().setContentType(MediaType.APPLICATION_JSON);
        String json;
        try {
            json = MAPPER.writeValueAsString(java.util.Map.of("code", 401, "message", message, "data", ""));
        } catch (Exception e) {
            json = "{\"code\":401,\"message\":\"鉴权失败\",\"data\":\"\"}";
        }
        DataBuffer buffer = response.bufferFactory().wrap(json.getBytes(StandardCharsets.UTF_8));
        return response.writeWith(Mono.just(buffer));
    }


    @Override
    public int getOrder() {
        return -100;
    }
}
