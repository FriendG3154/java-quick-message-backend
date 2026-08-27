package gin.model.QmUser;

public class UserLoginInfo {
    private String token;
    private String refreshToken;
    private Long expire;
    private Long refreshExpire;

    public String getToken(){
        return this.token;
    }
    public String getRefreshToken(){
        return this.refreshToken;
    }
    public Long getExpirer(){
        return this.expire;
    }
    public Long getRefreshExpire(){
        return this.refreshExpire;
    }
    public void setToken(String token){
        this.token = token;
    }
    public void setRefreshToken(String refreshToken){
        this.refreshToken = refreshToken;
    }
    public void setExpire(Long expire){
        this.expire = expire;
    }
    public void setRefreshExpire(Long refreshExpire){
        this.refreshExpire = refreshExpire;
    }
}
