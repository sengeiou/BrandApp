package com.isport.brandapp.net;


import brandapp.isport.com.basicres.commonbean.BaseBean;
import brandapp.isport.com.basicres.commonbean.BaseResponse;
import brandapp.isport.com.basicres.commonnet.interceptor.BaseErrorTransformer;
import brandapp.isport.com.basicres.commonnet.interceptor.ErrorTransformer;
import brandapp.isport.com.basicres.mvp.RxScheduler;
import io.reactivex.Observable;

public class ObserbleUtil<T> {


    public Observable<T> getObserBase(Observable<BaseResponse<T>> observable) {
        BaseErrorTransformer<BaseResponse> baseResponseBaseErrorTransformer = new BaseErrorTransformer<>();
        return observable
                .compose(RxScheduler.Obs_io_main())
                .compose(baseResponseBaseErrorTransformer);
    }

    public static Observable<?> getObser(Observable<?> observable) {
        ErrorTransformer<BaseResponse> responseBaseErrorTransformer = new ErrorTransformer<>();
        return observable
                .compose(RxScheduler.Obs_io_main())
                .compose(responseBaseErrorTransformer);
    }

}
