package brandapp.isport.com.basicres.commonnet.interceptor;

import com.isport.blelibrary.utils.Logger;

import brandapp.isport.com.basicres.BaseApp;
import brandapp.isport.com.basicres.commonbean.BaseResponse;
import brandapp.isport.com.basicres.commonutil.AppUtil;
import brandapp.isport.com.basicres.service.observe.LoginOutObservable;
import io.reactivex.functions.Function;

/**
 * 将一个对象转换成了String
 *
 * @param <T>
 */
public class Handle<T> implements Function<BaseResponse<T>, BaseResponse<T>> {

    boolean isFirst = true;

    @Override
    public BaseResponse<T> apply(BaseResponse<T> response) throws Exception {
//        Log.e("MyLog", "response错误信息=1111=" + response.toString());
        if (response.isTokenFailure()) {
            isFirst = false;

            LoginOutObservable.getInstance().show();
            response.setCode(2000);

            Logger.myLog("AppUtil.isZh(BaseApp.getApp()):" + AppUtil.isZh(BaseApp.getApp()));

            // EventBus.getDefault().post(new MessageEvent(MessageEvent.NEED_LOGIN));
           /* if (AppUtil.isZh(BaseApp.getApp())) {
                throw new FailException(response.getMsg() != null ? response.getMsg() : "");
            } else {
                throw new FailException(response.getMessage_en() != null ? response.getMessage_en() : "");
            }*/
        }
        if (!response.isOk()) {
            Logger.myLog("AppUtil.isZh(BaseApp.getApp()):" + AppUtil.isZh(BaseApp.getApp()));
            if (AppUtil.isZh(BaseApp.getApp())) {
                throw new FailException(response.getMsg() != null ? response.getMsg() : "");
            } else {
                throw new FailException(response.getMessage_en() != null ? response.getMessage_en() : "");
            }
        }
        return response;
    }
}
