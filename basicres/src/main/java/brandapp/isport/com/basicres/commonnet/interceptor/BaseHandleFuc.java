package brandapp.isport.com.basicres.commonnet.interceptor;

import brandapp.isport.com.basicres.BaseApp;
import brandapp.isport.com.basicres.commonbean.BaseBean;
import brandapp.isport.com.basicres.commonbean.BaseResponse;
import brandapp.isport.com.basicres.commonutil.AppUtil;
import io.reactivex.functions.Function;

/**
 * 将一个对象转换成了String
 *
 * @param <T>
 */
public class BaseHandleFuc<T> implements Function<BaseResponse<T>, String> {
    @Override
    public String apply(BaseResponse<T> response) throws Exception {
        if (!response.isOk()) {
            if (AppUtil.isZh(BaseApp.getApp())) {
                throw new ServerExceptions(response.getCode(), response.getMsg() != null ? response.getMsg() : "");
            }else {
                throw new ServerExceptions(response.getCode(), response.getMessage_en() != null ? response.getMessage_en() : "");
            }
        }
        if (AppUtil.isZh(BaseApp.getApp())) {
            return response.getMsg();
        }else{
            return response.getMessage_en();
        }

    }
}
