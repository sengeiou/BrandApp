package com.isport.brandapp.device.watch.presenter;

import com.isport.blelibrary.db.action.watch_w516.Watch_W516_NotifyModelAction;
import com.isport.blelibrary.db.table.watch_w516.Watch_W516_NotifyModel;
import com.isport.blelibrary.utils.Logger;
import com.isport.brandapp.device.watch.view.CallAndMessageNotiView;

import brandapp.isport.com.basicres.BaseApp;
import brandapp.isport.com.basicres.commonnet.interceptor.BaseObserver;
import brandapp.isport.com.basicres.commonnet.interceptor.ExceptionHandle;
import brandapp.isport.com.basicres.commonutil.TokenUtil;
import brandapp.isport.com.basicres.entry.WatchTargetBean;
import brandapp.isport.com.basicres.mvp.BasePresenter;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class CallAndMessageNotiPresenter extends BasePresenter<CallAndMessageNotiView> {
    CallAndMessageNotiView view;

    public CallAndMessageNotiPresenter(CallAndMessageNotiView view) {
        this.view = view;
    }


    public void saveCallAndMessageNotiState(String deviceId, int userId, boolean isSwitchCall, boolean isSwitchMessage) {
    }

    public void getCallAndMessageNotiState(String deviceId, String  userId) {
        Observable.create(new ObservableOnSubscribe<Watch_W516_NotifyModel>() {
            @Override
            public void subscribe(ObservableEmitter<Watch_W516_NotifyModel> emitter) throws Exception {
                Watch_W516_NotifyModel watch_w516_notifyModelByDeviceId = Watch_W516_NotifyModelAction.findWatch_W516_NotifyModelByDeviceId(deviceId, userId);
                // ArrayList<String> strDates = iw311DataModel.getMonthData(strDate, deviceId);
                if (watch_w516_notifyModelByDeviceId == null) {
                    watch_w516_notifyModelByDeviceId = new Watch_W516_NotifyModel();
                    watch_w516_notifyModelByDeviceId.setDeviceId(deviceId);
                    watch_w516_notifyModelByDeviceId.setUserId(userId);
                    watch_w516_notifyModelByDeviceId.setCallSwitch(false);
                    watch_w516_notifyModelByDeviceId.setMsgSwitch(false);
                }
                emitter.onNext(watch_w516_notifyModelByDeviceId);
                emitter.onComplete();
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).as(view.bindAutoDispose()).subscribe(new BaseObserver<Watch_W516_NotifyModel>(BaseApp.getApp(), false) {
            @Override
            public void onNext(Watch_W516_NotifyModel watchTargetBean) {
                if (view != null) {
                    view.successGetCallAndMessageNoti(watchTargetBean);
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
}
