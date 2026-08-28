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
        return true;
    }
}