package gin.model.QmUser;

public class QmUserAuthVO {
    public String userId;
    public String phone;
    public String wx_openid;
    public String wx_name;
    public Boolean voice_message;
    public String name;

    public String getUserId() {
        return userId;
    }

    public String getPhone() {
        return phone;
    }

    public String getWx_openid() {
        return wx_openid;
    }

    public String getWx_name() {
        return wx_name;
    }

    public Boolean getVoice_message() {
        return voice_message;
    }

    public String getName() {
        return name;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setWx_openid(String wx_openid) {
        this.wx_openid = wx_openid;
    }
    public void setWx_name(String wx_name) {
        this.wx_name = wx_name;
    }
    public void setVoice_message(Boolean voice_message) {
        this.voice_message = voice_message;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setUserId(String userId) {
        this.userId = userId;
    }
}
