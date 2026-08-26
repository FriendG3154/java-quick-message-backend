package gin.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import gin.model.QmUser.QmUserAuthVO;
import gin.model.common.ApiResponse;
import gin.tool.JwtTool;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.time.LocalDateTime;

@Component
public class Interceptor implements HandlerInterceptor {
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
    private final JwtTool jwtTool;

    public Interceptor(JwtTool jwtTool) {
        this.jwtTool = jwtTool;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        String token = request.getHeader("token");
        if (token == null){
            return writeUnauthorized(response, "密钥丢失");
        }
        try{
            QmUserAuthVO claim = jwtTool.validateToken(token);
            request.setAttribute("userId", claim.userId);
            request.setAttribute("voiceMessage",claim.voice_message);// 是否具有音频功能
        }catch(Exception e){
            return writeUnauthorized(response, "密钥验证失败");
        }
        return true;
    }

    public boolean writeUnauthorized(HttpServletResponse response, String message) {
        ApiResponse<String> apiResponse = ApiResponse.error(401, message);
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json;charset=UTF-8");
        try {
            String json = OBJECT_MAPPER.writeValueAsString(apiResponse);
            response.getWriter().write(json);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        System.out.println("[Interceptor] " + LocalDateTime.now() + " URI: " + request.getRequestURI() + ", exception: " + ex);
    }
}
