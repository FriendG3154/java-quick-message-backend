package gin.tool;

import com.aliyun.dysmsapi20170525.Client;
import com.aliyun.dysmsapi20170525.models.SendSmsRequest;
import com.aliyun.dysmsapi20170525.models.SendSmsResponse;
import com.aliyun.teaopenapi.models.Config;
import com.aliyun.teautil.models.RuntimeOptions;
import gin.config.AliYunConfig;
import org.springframework.stereotype.Component;

/**
 * 短信工具类：生成验证码 + 调用阿里云短信接口发送短信
 */
@Component
public class SmsTool {

    private final AliYunConfig aliYunConfig;

    public SmsTool(AliYunConfig aliYunConfig) {
        this.aliYunConfig = aliYunConfig;
    }

    /// 生成四位随机码
    public static String createmsCode() {
        return String.valueOf((int) ((Math.random()*9+1) * 10000));
    }

    /// 创建阿里云短信客户端，使用配置中的 AccessKey 显式鉴权
    public Client createClient() throws Exception {
        Config config = new Config()
                .setAccessKeyId(aliYunConfig.getAccessKeyId())
                .setAccessKeySecret(aliYunConfig.getAccessKeySecret())
                .setRegionId(aliYunConfig.getRegionId());
        config.endpoint = "dysmsapi.aliyuncs.com";
        return new Client(config);
    }

    /// 发送短信验证码，使用配置的签名与模板
    public String sendSms(String phone, String code) throws Exception {
        Client client = createClient();
        SendSmsRequest sendSmsRequest = new SendSmsRequest()
                .setPhoneNumbers(phone)
                .setSignName(aliYunConfig.getSignName())
                .setTemplateCode(aliYunConfig.getTemplateCode())
                .setTemplateParam("{\"code\":\"" + code + "\"}");
        try {
            SendSmsResponse resp = client.sendSmsWithOptions(sendSmsRequest, new RuntimeOptions());
            return new com.google.gson.Gson().toJson(resp);
        } catch (Exception e) {
            throw new Exception("短信发送失败: " + e.getMessage());
        }
    }
}