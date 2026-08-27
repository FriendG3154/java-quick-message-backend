package gin.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import gin.model.QmUser.QmUserAuthVO;
import gin.tool.JwtTool;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import reponse.AbstractAuthInterceptor;
import reponse.ApiResponse;

import java.time.LocalDateTime;


@Component
public class Interceptor extends AbstractAuthInterceptor {

    private final JwtTool jwtTool;

    public Interceptor(JwtTool jwtTool) {
        this.jwtTool = jwtTool;
    }

    @Override
    protected boolean doAuthenticate(String token, HttpServletRequest request, HttpServletResponse response) {
        try {
            QmUserAuthVO claim = jwtTool.validateToken(token);
            request.setAttribute("userId", claim.userId);
            request.setAttribute("voice_message", claim.voice_message);
            return true;
        } catch (Exception e) {
            return writeUnauthorized(response, "密钥验证失败");
        }
    }

}
