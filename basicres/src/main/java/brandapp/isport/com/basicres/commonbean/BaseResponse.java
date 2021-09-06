package brandapp.isport.com.basicres.commonbean;


public class BaseResponse<T> {

    private int    code;
    private String message;
    private String message_en;

    public String getMessage_en() {
        return message_en;
    }

    public void setMessage_en(String message_en) {
        this.message_en = message_en;
    }

    private T       obj;
    private boolean islastdata;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return message;
    }

    public void setMsg(String msg) {
        this.message = msg;
    }

    public T getData() {
        return obj;
    }

    public boolean isIslastdata() {
        return islastdata;
    }

    public void setIslastdata(boolean islastdata) {
        this.islastdata = islastdata;
    }

    public void setData(T data) {
        this.obj = data;
    }

    public boolean isOk() {
        return code == 2000 || code == 2002 || code == 1004;
    }

    public boolean isTokenFailure() {
        return code == 1003;
    }

}
