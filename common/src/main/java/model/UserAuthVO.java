package model;

public class UserAuthVO {
    private String userId;
    private String phone;
    private String name;
    private Boolean voice_message;

    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; }
    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public Boolean getVoice_message() { return voice_message; }
    public void setVoice_message(Boolean voice_message) { this.voice_message = voice_message; }

}
