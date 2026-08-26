package gin.tool;

import gin.config.JwtConfig;
import gin.model.QmUser.QmUserAuthVO;
import gin.service.util.RedisService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Component
public class JwtTool {
    private final JwtConfig jwtConfig; // Changed from private to public
    private final SecretKey secretKey;
    private final RedisService redisService;
    public JwtTool(JwtConfig jwtConfig, RedisService redisService) {
        this.jwtConfig = jwtConfig;
        this.secretKey = Keys.hmacShaKeyFor(jwtConfig.getSecret().getBytes(StandardCharsets.UTF_8));
        this.redisService = redisService;
    }

    public String generateToken(QmUserAuthVO qmUserAuthVO) {
        long now = System.currentTimeMillis();
        var builder = Jwts.builder()
                .subject(qmUserAuthVO.getUserId())
                .claim("phone", qmUserAuthVO.getPhone())
                .claim("wx_openid", qmUserAuthVO.getWx_openid())
                .claim("wx_name", qmUserAuthVO.getWx_name())
                .claim("voice_message", qmUserAuthVO.getVoice_message())
                .claim("name", qmUserAuthVO.getName())
                .issuedAt(new Date(now))
                .expiration(new java.util.Date(now + jwtConfig.getExpire()))
                .signWith(secretKey);
        return builder.compact();
    }

    /// 验证token,验证token是否过期
    public QmUserAuthVO validateToken(String token) {
        QmUserAuthVO user = new QmUserAuthVO();
        Claims claims = Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(token)
                .getPayload();
        user.setUserId(claims.getSubject());
        user.setPhone(claims.get("phone", String.class));
        user.setWx_openid(claims.get("wx_openid", String.class));
        user.setWx_name(claims.get("wx_name", String.class));
        user.setVoice_message(claims.get("voice_message", Boolean.class));
        user.setName(claims.get("name", String.class));
        return user;
    }
}
