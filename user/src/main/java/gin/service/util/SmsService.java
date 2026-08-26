package gin.service.util;

import gin.entity.QmUser;
import gin.model.SmsModel.SmsInput;
import gin.service.QmUserService;
import gin.tool.SmsTool;
import org.springframework.stereotype.Service;

///短信服务，负责验证码生成、缓存与发送
@Service
public class SmsService {
    private final QmUserService qmUserService;
    private final RedisService redisService;
    private final SmsTool smsTool;
    public SmsService(QmUserService qmUserService, RedisService redisService, SmsTool smsTool) {
        this.qmUserService = qmUserService;
        this.redisService = redisService;
        this.smsTool = smsTool;
    }
    /// 短信工具
    public Boolean MsgTool(String phone) throws Exception {

        Boolean isExists = qmUserService.lambdaQuery().eq(QmUser::getPhone,phone).exists(); // Fixed: Added missing parameter
        /// 如果手机存在则发送验证码
        if(isExists.equals(true)){
            String Code =  SmsTool.createmsCode();
            redisService.setString(phone, Code, 5 * 60);// 五分钟
            SmsInput smsInput = new SmsInput();
            smsInput.setPhone(phone);
            smsInput.setCode(Code);
//            SendMsg(smsInput); // Fixed: Added missing parameter
        }else{
            throw new Exception("手机号未注册，禁止发送验证码");
        }
        return true;
    }

    /// 发送验证码，调用 SmsTool 适配的阿里云短信接口
    public String SendMsg(SmsInput Input) throws  Exception {
        return smsTool.sendSms(Input.getPhone(), Input.getCode());
    }
}