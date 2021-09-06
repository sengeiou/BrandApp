package brandapp.isport.com.basicres.commonnet.net;

public class PostBody<T,T1,T2> {
    /**
     * "userId": 0,
     * "interfaceId": 0,
     * "mobile": "",
     * "verify": "",
     * "type": 0
     */



    public T data;
    public T1 requseturl;
    public T2 dbParm;
    public int type;
    public boolean isStandAlone;


    @Override
    public String toString() {
        return "PostBody{" +
                "data=" + data +
                ", requseturl=" + requseturl +
                ", dbParm=" + dbParm +
                ", type=" + type +
                ", isStandAlone=" + isStandAlone +
                '}';
    }
}
