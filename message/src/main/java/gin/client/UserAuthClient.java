package gin.client;

import model.UserAuthVO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import reponse.ApiResponse;

@FeignClient(name = "child-user", path = "/auth")
public interface UserAuthClient {

    @PostMapping("/validate")
    ApiResponse<UserAuthVO> validate(@RequestBody String token);
}