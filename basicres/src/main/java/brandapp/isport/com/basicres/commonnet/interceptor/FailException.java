package brandapp.isport.com.basicres.commonnet.interceptor;

public class FailException extends Exception {
    //异常信息
    private String message;

    //构造函数
    public FailException(String message) {
        super(message);
        this.message = message;
    }

}
