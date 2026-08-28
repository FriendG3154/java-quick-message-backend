package gin.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@ConfigurationProperties(prefix = "jwt")
public class GatewayJwtConfig {
    private String secret;
    private long expire;
    private long refreshExpire;

    /// 白名单:无需 token 的接口(匹配网关入口原始路径,在 StripPrefix 之前)
    private List<String> whiteList = new ArrayList<>();

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public long getExpire() {
        return expire;
    }
    public List<String> getWhiteList() {
        return whiteList;
    }

    public void setExpire(long expire) {
        this.expire = expire;
    }
    public void setWhiteList(List<String> whiteList) {
        this.whiteList = whiteList;
    }
    public long getRefreshExpire() {
        return refreshExpire;
    }
    public void setRefreshExpire(long refreshExpire) {
        this.refreshExpire = refreshExpire;
    }
}