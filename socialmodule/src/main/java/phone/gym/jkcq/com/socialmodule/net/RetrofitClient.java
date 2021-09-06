package phone.gym.jkcq.com.socialmodule.net;


import brandapp.isport.com.basicres.commonbean.BaseUrl;
import brandapp.isport.com.basicres.commonnet.net.PostBody;
import brandapp.isport.com.basicres.commonnet.net.RxScheduler;
import brandapp.isport.com.basicres.net.CommonRetrofitClient;
import io.reactivex.Observable;

/**
 * @author azheng
 * @date 2018/4/17.
 * GitHub：https://github.com/RookieExaminer
 * email：wei.azheng@foxmail.com
 * description：
 */
public class RetrofitClient extends CommonRetrofitClient {

    private static volatile RetrofitClient instance;

    public RetrofitClient() {
    }

    public static RetrofitClient getInstance() {
        if (instance == null) {
            synchronized (CommonRetrofitClient.class) {
                if (instance == null) {
                    instance = new RetrofitClient();
                }
            }
        }
        return instance;
    }

    public Observable<?> postEdit(PostBody body) {
        // return super.post(body);
        BaseUrl url = (BaseUrl) body.requseturl;
        return getRetrofit().create(APIService.class).editBackGround(url.extend1, url.userid).compose
                (RxScheduler.Obs_io_main()).compose(transformer);
    }
}
