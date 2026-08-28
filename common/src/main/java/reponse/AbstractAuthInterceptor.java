package reponse;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.time.LocalDateTime;

@Component
public abstract class AbstractAuthInterceptor implements HandlerInterceptor {
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        String token = request.getHeader("authorization");
        if (token == null) {
            return writeUnauthorized(response, "密钥丢失");
        }
        try {
            return doAuthenticate(token, request, response);
        } catch (Exception e) {
            return writeUnauthorized(response, "鉴权失败");
        }
    }

    /**
     * 子类实现具体校验逻辑：
     * 成功时自行 setAttribute 并返回 true；
     * 失败时调用 writeUnauthorized 并返回 false。
     */
    protected abstract boolean doAuthenticate(String token, HttpServletRequest request, HttpServletResponse response) throws Exception;

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
