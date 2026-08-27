package gin.controller;

import gin.model.QmUser.QmUserAuthVO;
import gin.tool.JwtTool;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reponse.ApiResponse;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final JwtTool jwtTool;

    public AuthController(JwtTool jwtTool) {
        this.jwtTool = jwtTool;
    }

    @PostMapping("/validate")
    public ApiResponse<QmUserAuthVO> validate(@RequestBody String token) {
        try {
            return ApiResponse.success(jwtTool.validateToken(token));
        } catch (Exception e) {
            return ApiResponse.error(401, "密钥验证失败");
        }
    }

}
