package reponse;

import java.io.Serializable;

public class ApiResponse<T> implements Serializable {

    private static final long serialVersionUID = 1L;
    private T data;
    private String message;
    private int code;

    public int getCode() {return this.code;}
    public void setCode(int code){
        this.code = code;
    }

    public String getMessage() {return this.message;}
    public void setMessage(String message){
        this.message = message;
    }
    public T getData() {return this.data;}
    public void setData(T data){
        this.data = data;
    }

    /// 成功的时候
    public static <T> ApiResponse<T> success(T data) {
        ApiResponse<T> apiResponse = new ApiResponse<>();
        apiResponse.setCode(200);
        apiResponse.setData(data);
        return apiResponse;
    }
    /// 失败的时候
    ///
    public static <T> ApiResponse<T> error(int code, Exception message) {
        ApiResponse<T> apiResponse = new ApiResponse<>();
        apiResponse.setCode(code);
        apiResponse.setMessage(message.getMessage());
        return apiResponse;
    }

    public static <T> ApiResponse<T> error(int code, String message) {
        ApiResponse<T> apiResponse = new ApiResponse<>();
        apiResponse.setCode(code);
        apiResponse.setMessage(message);
        return apiResponse;
    }
    @Override
    public String toString() {
        return "ApiResponse{" +
                "data=" + data +
                ", message='" + message + '\'' +
                ", code=" + code +
                '}';
    }
}
