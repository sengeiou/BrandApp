package brandapp.isport.com.basicres.commonnet.interceptor;

public class ServerExceptions extends RuntimeException {

    public ServerExceptions(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int code;
    public String message;
}
