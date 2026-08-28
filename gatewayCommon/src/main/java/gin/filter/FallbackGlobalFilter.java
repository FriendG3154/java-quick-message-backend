package gin.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;

@Component
/// 全局筛选
public class FallbackGlobalFilter  implements GlobalFilter, Ordered {

    private static final ObjectMapper MAPPER = new ObjectMapper();
    private static final Logger log = LoggerFactory.getLogger(FallbackGlobalFilter.class);
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        log.info(">>> 进入 FallbackGlobalFilter, path={}", exchange.getRequest().getURI().getPath());
        return chain.filter(exchange)
                .doOnError(e -> log.warn(">>> 转发失败: {}", e.toString()))
                .onErrorResume(throwable -> writeFallback(exchange, throwable));
    }

    private Mono<Void> writeFallback(ServerWebExchange exchange, Throwable e) {
        ServerHttpResponse response =exchange.getResponse();
        if (response.isCommitted()) {
            return Mono.error(e);
        }
        int status = e instanceof ResponseStatusException rse
                ? rse.getStatusCode().value() : 503;
        response.setStatusCode(HttpStatus.SERVICE_UNAVAILABLE);
        response.getHeaders().setContentType(MediaType.APPLICATION_JSON);
        String json;
        try {
            // 与下游 ApiResponse 结构保持一致:{code, message, data}
            json = MAPPER.writeValueAsString(
                    java.util.Map.of("code", status, "message", "服务暂不可用", "data", ""));
        } catch (Exception ex) {
            json = "{\"code\":503,\"message\":\"服务暂不可用\",\"data\":\"\"}";
        }
        DataBuffer buffer = response.bufferFactory().wrap(json.getBytes(StandardCharsets.UTF_8));
        return response.writeWith(Mono.just(buffer));
    }
    @Override
    public int getOrder() {
        return Ordered.LOWEST_PRECEDENCE;
    }
}
