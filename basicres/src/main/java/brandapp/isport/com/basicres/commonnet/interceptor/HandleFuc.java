package brandapp.isport.com.basicres.commonnet.interceptor;

import com.isport.blelibrary.utils.Logger;

import brandapp.isport.com.basicres.BaseApp;
import brandapp.isport.com.basicres.commonbean.BaseResponse;
import brandapp.isport.com.basicres.commonutil.AppUtil;
import brandapp.isport.com.basicres.service.observe.LoginOutObservable;
import io.reactivex.functions.Function;

public class HandleFuc<T> implements Function<BaseResponse<T>, T> {

    boolean isFirst = true;

    @Override
    public T apply(BaseResponse<T> response) throws Exception {
//        Log.e("MyLog", "response错误信息=000=" + response.toString());
        if (response.isTokenFailure() && isFirst) {
            isFirst = false;
            LoginOutObservable.getInstance().show();
            //EventBus.getDefault().post(new MessageEvent(MessageEvent.NEED_LOGIN));
            response.setCode(2000);
//            throw new RuntimeException(response.getCode() + "" + response.getMsg() != null ? response.getMsg() : "");
        }
        if (!response.isOk()) {
            Logger.myLog("HandleFuc AppUtil.isZh(BaseApp.getApp()):" + AppUtil.isZh(BaseApp.getApp()) + response.getMsg());
            if (AppUtil.isZh(BaseApp.getApp())) {
                throw new FailException(response.getMsg() != null ? response.getMsg() : "");
            } else {
                throw new FailException(response.getMessage_en() != null ? response.getMessage_en() : "");
            }

        }
        if (response.isOk()) {
            if (response.getData() == null) {
                throw new SuccessException(response.getMsg() != null ? response.getMsg() : "");
            } else {
                return response.getData();
            }
        } else {
            if (response.getData() == null) {
                Logger.myLog("AppUtil.isZh(BaseApp.getApp()):" + AppUtil.isZh(BaseApp.getApp()));
                if (AppUtil.isZh(BaseApp.getApp())) {
                    throw new FailException(response.getMsg() != null ? response.getMsg() : "");
                } else {
                    throw new FailException(response.getMessage_en() != null ? response.getMessage_en() : "");
                }

            }
        }
        return response.getData();
    }
}
