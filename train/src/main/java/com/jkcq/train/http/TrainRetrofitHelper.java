package com.jkcq.train.http;

import com.google.gson.GsonBuilder;

import java.util.concurrent.TimeUnit;

import brandapp.isport.com.basicres.net.CommonRetrofitClient;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * created by wq on 2019/4/12
 */
public class TrainRetrofitHelper {

    private volatile static Retrofit retrofit;

    private static final boolean IS_DEBUG = true;

    public static TrainApiService getService() {
        return getRetrofit().create(TrainApiService.class);
    }

    public static <T> T getService(Class<T> tClass) {
        return getRetrofit().create(tClass);
    }

    private static Retrofit getRetrofit() {
        if (retrofit == null) {
            synchronized (TrainRetrofitHelper.class) {
                if (retrofit == null) {
                    retrofit = new Retrofit.Builder()
                            .baseUrl(CommonRetrofitClient.baseUrl)
                            .client(getOkHttpClient())
                            .addConverterFactory(GsonConverterFactory.create(new GsonBuilder().create()))
                            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                            .build();
                }
            }
        }
        return retrofit;
    }


    private static OkHttpClient getOkHttpClient() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();

        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        if (TrainRetrofitHelper.IS_DEBUG) {
            httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        } else {
            httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.NONE);
        }
        // .cookieJar(cookieJar)
        builder.connectTimeout(10, TimeUnit.SECONDS)
                .readTimeout(15, TimeUnit.SECONDS)
                .writeTimeout(15, TimeUnit.SECONDS)
                .retryOnConnectionFailure(true) //设置Header
                .addInterceptor(httpLoggingInterceptor);
        return builder.build();
    }
}
