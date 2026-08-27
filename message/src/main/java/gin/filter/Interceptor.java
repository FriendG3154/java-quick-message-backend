package gin.filter;

// ... existing code ...
import gin.client.UserAuthClient;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.UserAuthVO;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import reponse.AbstractAuthInterceptor;
import reponse.ApiResponse;

@Component
public class Interceptor extends AbstractAuthInterceptor {

    private final UserAuthClient userAuthClient;

    public Interceptor(@Lazy UserAuthClient userAuthClient) {
        this.userAuthClient = userAuthClient;
    }

    @Override
    protected boolean doAuthenticate(String token, HttpServletRequest request, HttpServletResponse response) {
        try {
            ApiResponse<UserAuthVO> result = userAuthClient.validate(token);
            if (result == null || result.getCode() != 200 || result.getData() == null) {
                return writeUnauthorized(response, "密钥验证失败");
            }
            request.setAttribute("userId", result.getData().getUserId());
            request.setAttribute("voiceMessage", result.getData().getVoice_message());
            return true;
        } catch (Exception e) {
            return writeUnauthorized(response, "鉴权服务不可用");
        }
    }
}