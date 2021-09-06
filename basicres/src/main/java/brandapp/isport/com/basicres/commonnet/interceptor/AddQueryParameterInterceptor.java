package brandapp.isport.com.basicres.commonnet.interceptor;

import java.io.IOException;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class AddQueryParameterInterceptor implements Interceptor{
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request originalRequest=chain.request();
        Request request;
        HttpUrl modifiedUrl = originalRequest.url().newBuilder()
                .addQueryParameter("token","")
              /*  .addQueryParameter("version", GlobalVariable.VERSION)
                .addQueryParameter("deviceOs", GlobalVariable.DEVICE_OS)
                .addQueryParameter("lang", GlobalVariable.LANG)
                .addQueryParameter("deviceCode", GlobalVariable.DEVICE_CODE)*/.build();
        request=originalRequest.newBuilder().url(modifiedUrl).build();
        return chain.proceed(request);
    }
}
