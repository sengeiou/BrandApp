package com.isport.brandapp.net;


import androidx.annotation.NonNull;
import android.text.TextUtils;

import com.google.gson.Gson;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import phone.gym.jkcq.com.commonres.common.JkConfiguration;
import brandapp.isport.com.basicres.commonbean.BaseUrl;
import brandapp.isport.com.basicres.commonnet.interceptor.BaseErrorTransformer;
import brandapp.isport.com.basicres.commonnet.interceptor.ErrorTransformer;
import brandapp.isport.com.basicres.commonnet.interceptor.RxScheduler;
import brandapp.isport.com.basicres.commonnet.interceptor.Transformer;
import brandapp.isport.com.basicres.commonnet.net.HttpLoggingInterceptor;
import brandapp.isport.com.basicres.commonnet.net.PostBody;
import io.reactivex.Observable;
import okhttp3.Headers;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * @author azheng
 * @date 2018/4/17.
 * GitHub：https://github.com/RookieExaminer
 * email：wei.azheng@foxmail.com
 * description：
 */
public class AuthouRetrofitClient {

    private static volatile AuthouRetrofitClient instance;
    private APIService apiService;
    // private BaseApiService apiService;
    //private String baseUrl = "http://www.wanandroid.com/";
    private static String baseUrl = "https://api.weixin.qq.com/";
//    private static String baseUrl = "https://api.weixin.qq.com/sns/";
    public static ErrorTransformer transformer = new ErrorTransformer();//返回子对象
    public static BaseErrorTransformer baseTransformer = new BaseErrorTransformer();//返回String
    public static Transformer desTransformer = new Transformer();//返回顶层的对象

    private AuthouRetrofitClient() {
    }

    public static AuthouRetrofitClient getInstance() {
        if (instance == null) {
            synchronized (AuthouRetrofitClient.class) {
                if (instance == null) {
                    instance = new AuthouRetrofitClient();
                }
            }
        }
        return instance;
    }

    /**
     * 设置Header
     *
     * @return
     */
    private static Interceptor getHeaderInterceptor() {
        return new Interceptor() {
            @Override
            public Response intercept(@NonNull Chain chain) throws IOException {
                Request original = chain.request();
                Request.Builder requestBuilder = original.newBuilder()
                        //添加Token
                        .header("token", "");
                Request request = requestBuilder.build();

                //获取到response
                Response response = chain.proceed(request);


                Headers headers = response.headers();

                if (!TextUtils.isEmpty(headers.get("token"))) {
                    String token = headers.get("token");
                }
                if (!TextUtils.isEmpty(headers.get("Date"))) {
                    String Date = headers.get("Date");
                }

                return response;
            }
        };

    }

    /**
     * 设置拦截器
     *
     * @return
     */
    private static Interceptor getInterceptor() {

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        //显示日志
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);


        return interceptor;
    }


    private volatile static Retrofit retrofit;

    @NonNull
    public static Retrofit getRetrofit() {
        if (retrofit == null) {
            synchronized (AuthouRetrofitClient.class) {
                if (retrofit == null) {
                    // 指定缓存路径,缓存大小 50Mb

                    // Cookie 持久化
                    /*ClearableCookieJar cookieJar =
                            new PersistentCookieJar(new SetCookieCache(), new SharedPrefsCookiePersistor(InitApp.AppContext));*/

                    OkHttpClient.Builder builder = new OkHttpClient.Builder()
                            // .cookieJar(cookieJar)
                            .connectTimeout(10, TimeUnit.SECONDS)
                            .readTimeout(15, TimeUnit.SECONDS)
                            .writeTimeout(15, TimeUnit.SECONDS)
                            .retryOnConnectionFailure(true) //设置Header
                            .addInterceptor(getHeaderInterceptor())
                            //设置拦截器
                            .addInterceptor(getInterceptor());


                    retrofit = new Retrofit.Builder()
                            .client(builder.build())
                            //设置网络请求的Url地址
                            .baseUrl(baseUrl)
                            //设置数据解析器
                            .addConverterFactory(GsonConverterFactory.create())
                            //设置网络请求适配器，使其支持RxJava与RxAndroid
                            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                            .build();

                }
            }
        }
        return retrofit;
    }

    public Observable<?> post(String url, PostBody body, boolean isString) {
        Gson gson = new Gson();
        final RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), gson.toJson(body.data));
        if (isString) {
            return getRetrofit().create(APIService.class).getUserInfo(url, requestBody).compose(RxScheduler.Obs_io_main()).compose(baseTransformer);
        } else {
            return getRetrofit().create(APIService.class).getUserInfo(url, requestBody).compose(RxScheduler.Obs_io_main()).compose(transformer);
        }
    }

    public Observable<?> updatepost(PostBody body) {
        Gson gson = new Gson();
        BaseUrl baseUrl = (BaseUrl) body.requseturl;
        final RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), gson.toJson(body.data));
        return getRetrofit().create(APIService.class).updateData(baseUrl.url1, baseUrl.url2, requestBody).compose(RxScheduler.Obs_io_main()).compose(baseTransformer);
    }



    public Observable<?> post(PostBody body) {
        RequestBody requestBody = null;
        if (body.data != null) {
            Gson gson = new Gson();
            requestBody = RequestBody.create(MediaType.parse("application/json"), gson.toJson(body.data));
        }
        BaseUrl url = (BaseUrl) body.requseturl;
        switch (body.type) {
            case JkConfiguration.RequstType.MYPERSONAL_DEVICELIST:
                return getRetrofit().create(APIService.class).getMyPersonalDeviceList(url.url1, url.url2, requestBody).compose(RxScheduler.Obs_io_main()).compose(transformer);

            case JkConfiguration.RequstType.FIND_DEVICE_DATA_HOME:
                return getRetrofit().create(APIService.class).getDeviceDataHome(url.url1, url.url2, url.userid).compose(RxScheduler.Obs_io_main()).compose(transformer);

            case JkConfiguration.RequstType.SLEEP_HISTORY:
                /**
                 *
                 */
                return getRetrofit().create(APIService.class).getSleepHistory(url.url1, url.url2, requestBody).compose(RxScheduler.Obs_io_main()).compose(desTransformer);
            case JkConfiguration.RequstType.SCALE_HISTORY:
                return getRetrofit().create(APIService.class).getScaleHistory(url.url1, url.url2, requestBody).compose(RxScheduler.Obs_io_main()).compose(desTransformer);

            case JkConfiguration.RequstType.WRISTBAND_HISTORY:
                return getRetrofit().create(APIService.class).getwristbandHistory(url.url1, url.url2, requestBody).compose(RxScheduler.Obs_io_main()).compose(transformer);

            case JkConfiguration.RequstType.BIND_DEVICE:
                return getRetrofit().create(APIService.class).bindDevice(url.url1, url.url2,url.url3, requestBody).compose(RxScheduler.Obs_io_main()).compose(transformer);

            case JkConfiguration.RequstType.SLEEP_GET_CLOCK_TIME:
                return getRetrofit().create(APIService.class).getSleepClockTime(url.url1, url.url2, url.userid).compose(RxScheduler.Obs_io_main()).compose(transformer);

            case JkConfiguration.RequstType.FATSTEELYARD_REPORT:
                return getRetrofit().create(APIService.class).getScaleReport(url.url1, url.url2, url.userid).compose(RxScheduler.Obs_io_main()).compose(transformer);

            case JkConfiguration.RequstType.FATSTEELYARD_UPDATE:
                return getRetrofit().create(APIService.class).synchronizeScaleData(url.url1, url.url2,url.url3,  requestBody).compose(RxScheduler.Obs_io_main()).compose(transformer);

            case JkConfiguration.RequstType.SLEEP_UPDATE:
                return getRetrofit().create(APIService.class).synchronizeSleepData(url.url1, url.url2,url.url3, requestBody).compose(RxScheduler.Obs_io_main()).compose(transformer);

            case JkConfiguration.RequstType.WRISTBAND_UPDATE:
                return getRetrofit().create(APIService.class).synchronizeBandData(url.url1, url.url2, requestBody).compose(RxScheduler.Obs_io_main()).compose(transformer);

        }
        return getRetrofit().create(APIService.class).updateData(url.url1, url.url2, requestBody).compose(RxScheduler.Obs_io_main()).compose(transformer);
    }


    /**
     * 创建默认url的api类
     *
     * @return ApiManager
     */
 /*   public RetrofitClient createBaseApi() {
        apiService = create(BaseApiService.class);
        return this;
    }*/

    /**
     * create you ApiService
     * Create an implementation of the API endpoints defined by the {@code service} interface.
     */
    public static <T> T create(final Class<T> service) {
        if (service == null) {
            throw new RuntimeException("Api service is null!");
        }
        return retrofit.create(service);
    }


}
