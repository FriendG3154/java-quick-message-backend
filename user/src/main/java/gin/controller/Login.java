package gin.controller;

import gin.model.QmUser.QmUserAuthVO;
import gin.model.QmUser.QmUserInput;
import gin.model.SmsModel.SmsInput;
import gin.model.common.ApiResponse;
import gin.service.QmUserService;
import gin.service.util.SmsService;
import org.springframework.http.converter.json.AbstractJackson2HttpMessageConverter;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
public class Login {

    private final SmsService smsService;
    private final QmUserService qmUserService;
    private final AbstractJackson2HttpMessageConverter abstractJackson2HttpMessageConverter;

    public Login(SmsService smsService, AbstractJackson2HttpMessageConverter abstractJackson2HttpMessageConverter, QmUserService qmUserService) {
        this.smsService = smsService;
        this.qmUserService = qmUserService;
        this.abstractJackson2HttpMessageConverter = abstractJackson2HttpMessageConverter;
    }

    @PostMapping("/sendSms")
    public ApiResponse<Boolean> sendSms(@RequestBody QmUserInput qmUserInput) {
        try {
            return ApiResponse.success(smsService.MsgTool(qmUserInput.getPhone()));
        } catch (Exception e) {
            return ApiResponse.error(500, e);
        }
    }

    @PostMapping("/login")
    public ApiResponse<String> login(@RequestBody QmUserInput qmUserInput) {
        try{
            return ApiResponse.success(qmUserService.login(qmUserInput));
        } catch (Exception e) {
            return ApiResponse.error(500, e);
        }
    }
}
