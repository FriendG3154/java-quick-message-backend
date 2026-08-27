package gin.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "wx.appinfo")
public class WxConfig {
    private String appId;
    private String appSecret;
}
