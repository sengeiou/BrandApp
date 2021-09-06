package com.isport.brandapp.device.bracelet.braceletPresenter;

import com.isport.blelibrary.db.action.watch_w516.Watch_W516_SleepAndNoDisturbModelAction;
import com.isport.blelibrary.db.table.watch_w516.Watch_W516_SleepAndNoDisturbModel;
import com.isport.blelibrary.utils.Constants;
import com.isport.brandapp.device.bracelet.view.NoDisturbView;

import brandapp.isport.com.basicres.BaseApp;
import brandapp.isport.com.basicres.commonnet.interceptor.BaseObserver;
import brandapp.isport.com.basicres.commonnet.interceptor.ExceptionHandle;
import brandapp.isport.com.basicres.commonutil.UIUtils;
import brandapp.isport.com.basicres.mvp.BasePresenter;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class NoDisturbPresenter extends BasePresenter<NoDisturbView> {

    NoDisturbView view;


    public NoDisturbPresenter() {

    }

    public NoDisturbPresenter(NoDisturbView view) {
        this.view = view;
    }


    public void getNodisturbBean(String userId, String deviceId) {
        Observable.create(new ObservableOnSubscribe<Watch_W516_SleepAndNoDisturbModel>() {
            @Override
            public void subscribe(ObservableEmitter<Watch_W516_SleepAndNoDisturbModel> emitter) throws Exception {
                Watch_W516_SleepAndNoDisturbModel watch_w516_sleepAndNoDisturbModelyDeviceId = Watch_W516_SleepAndNoDisturbModelAction.findWatch_W516_SleepAndNoDisturbModelyDeviceId(userId, deviceId);
                // ArrayList<String> strDates = iw311DataModel.getMonthData(strDate, deviceId);

                if (watch_w516_sleepAndNoDisturbModelyDeviceId == null) {
                    watch_w516_sleepAndNoDisturbModelyDeviceId = new Watch_W516_SleepAndNoDisturbModel();
                    watch_w516_sleepAndNoDisturbModelyDeviceId.setOpenNoDisturb(false);
                    watch_w516_sleepAndNoDisturbModelyDeviceId.setNoDisturbEndTime(Constants.defStartTime);
                    watch_w516_sleepAndNoDisturbModelyDeviceId.setNoDisturbStartTime(Constants.defEndTime);
                }

                emitter.onNext(watch_w516_sleepAndNoDisturbModelyDeviceId);
                emitter.onComplete();
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).as(view.bindAutoDispose()).subscribe(new BaseObserver<Watch_W516_SleepAndNoDisturbModel>(BaseApp.getApp(), false) {
            @Override
            public void onNext(Watch_W516_SleepAndNoDisturbModel watch_w516_sleepAndNoDisturbModel) {
                if (view != null) {
                    view.successDisturb(watch_w516_sleepAndNoDisturbModel);
                }
            }

            @Override
            protected void hideDialog() {

            }

            @Override
            protected void showDialog() {

            }

            @Override
            public void onError(ExceptionHandle.ResponeThrowable e) {

            }

        });

    }

    /**
     * 获取勿扰的开关状态
     *
     * @param
     */
    public void getNodisturb(String userId, String deviceId) {
        Observable.create(new ObservableOnSubscribe<Watch_W516_SleepAndNoDisturbModel>() {
            @Override
            public void subscribe(ObservableEmitter<Watch_W516_SleepAndNoDisturbModel> emitter) throws Exception {
                Watch_W516_SleepAndNoDisturbModel watch_w516_sleepAndNoDisturbModelyDeviceId = Watch_W516_SleepAndNoDisturbModelAction.findWatch_W516_SleepAndNoDisturbModelyDeviceId(userId, deviceId);
                // ArrayList<String> strDates = iw311DataModel.getMonthData(strDate, deviceId);

                boolean isOpen = false;
                if (watch_w516_sleepAndNoDisturbModelyDeviceId == null) {
                    watch_w516_sleepAndNoDisturbModelyDeviceId = new Watch_W516_SleepAndNoDisturbModel();
                }

                emitter.onNext(watch_w516_sleepAndNoDisturbModelyDeviceId);
                emitter.onComplete();
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).as(view.bindAutoDispose()).subscribe(new BaseObserver<Watch_W516_SleepAndNoDisturbModel>(BaseApp.getApp(), false) {
            @Override
            public void onNext(Watch_W516_SleepAndNoDisturbModel model) {
                if (view != null) {
                    view.successDisturb(model);
                }
            }

            @Override
            protected void hideDialog() {

            }

            @Override
            protected void showDialog() {

            }

            @Override
            public void onError(ExceptionHandle.ResponeThrowable e) {
                if (view != null) {
                    view.successDisturb(false);
                }
            }

        });

    }

}
