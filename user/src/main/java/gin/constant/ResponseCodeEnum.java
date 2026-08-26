package gin.constant;

public enum ResponseCodeEnum {
    SUCCESS(0,"操作成功"),
    FAIL(1,"操作失败");

    private Integer code;
    private String name;
    ResponseCodeEnum(Integer code, String name){
        this.code = code;
        this.name = name;
    }

    public Integer getCode() {
        return code;
    }
    public String getName() {
        return name;
    }
    public void setCode(Integer code) {
        this.code = code;
    }
    public void setName(String name) {
        this.name = name;
    }
}
