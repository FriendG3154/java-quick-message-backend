package gin.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 阿里云短信服务配置，读取 application.yaml 中 aliyun.sms 前缀的配置项
 * 密钥通过环境变量 ALIYUN_SMS_ACCESS_KEY_ID / ALIYUN_SMS_ACCESS_KEY_SECRET 注入
 */
@Component
@ConfigurationProperties(prefix = "aliyun.sms")
public class AliYunConfig {
    /// 地域 ID
    private String regionId;
    /// 访问密钥 ID
    private String accessKeyId;
    /// 访问密钥
    private String accessKeySecret;
    /// 短信签名
    private String signName;
    /// 短信模板 CODE
    private String templateCode;

    public String getRegionId() {
        return regionId;
    }

    public void setRegionId(String regionId) {
        this.regionId = regionId;
    }

    public String getAccessKeyId() {
        return accessKeyId;
    }

    public void setAccessKeyId(String accessKeyId) {
        this.accessKeyId = accessKeyId;
    }

    public String getAccessKeySecret() {
        return accessKeySecret;
    }

    public void setAccessKeySecret(String accessKeySecret) {
        this.accessKeySecret = accessKeySecret;
    }

    public String getSignName() {
        return signName;
    }

    public void setSignName(String signName) {
        this.signName = signName;
    }

    public String getTemplateCode() {
        return templateCode;
    }

    public void setTemplateCode(String templateCode) {
        this.templateCode = templateCode;
    }
}