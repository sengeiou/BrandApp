package com.isport.brandapp.device.bracelet.braceletPresenter;

import com.isport.blelibrary.utils.Logger;
import com.isport.brandapp.device.bracelet.braceletModel.DeviceGoalModelImp;
import com.isport.brandapp.device.bracelet.braceletModel.IDeviceGoalModel;
import com.isport.brandapp.device.bracelet.view.DeviceGoalDistanceView;

import brandapp.isport.com.basicres.BaseApp;
import brandapp.isport.com.basicres.commonnet.interceptor.BaseObserver;
import brandapp.isport.com.basicres.commonnet.interceptor.ExceptionHandle;
import brandapp.isport.com.basicres.entry.WatchTargetBean;
import brandapp.isport.com.basicres.mvp.BasePresenter;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class DeviceGoalDistancePresenter extends BasePresenter<DeviceGoalDistanceView> {
    DeviceGoalDistanceView view;
    IDeviceGoalModel modelSetting;

    public DeviceGoalDistancePresenter(DeviceGoalDistanceView view) {
        this.view = view;
        modelSetting = new DeviceGoalModelImp();
    }


    public void saveDeviceGoalDistance(String userId, String deviceId, int distance) {
        Observable.create(new ObservableOnSubscribe<Boolean>() {
            @Override
            public void subscribe(ObservableEmitter<Boolean> emitter) throws Exception {
                modelSetting.saveDeviceGoalValue(userId, deviceId, distance, 1);
                // ArrayList<String> strDates = iw311DataModel.getMonthData(strDate, deviceId);
                emitter.onNext(true);
                emitter.onComplete();
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).as(view.bindAutoDispose()).subscribe(new BaseObserver<Boolean>(BaseApp.getApp(), false) {
            @Override
            public void onNext(Boolean faceWatchMode) {
                if (view != null) {

                    view.successSaveGoalDistance(distance);
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

    public void getDeviceGoalDistance(String userId, String deviceId) {
        Observable.create(new ObservableOnSubscribe<WatchTargetBean>() {
            @Override
            public void subscribe(ObservableEmitter<WatchTargetBean> emitter) throws Exception {
                WatchTargetBean faceWatchMode = modelSetting.getDeviceGoalValue(userId, deviceId);
                Logger.myLog("getWatchFace:" + faceWatchMode + "userId:" + userId + "deviceId:" + deviceId);
                // ArrayList<String> strDates = iw311DataModel.getMonthData(strDate, deviceId);
                if (faceWatchMode == null) {
                    faceWatchMode = new WatchTargetBean();
                    faceWatchMode.setDeviceId(deviceId);
                    faceWatchMode.setUserId(userId);
                    faceWatchMode.setTargetDistance(1000);
                }
                emitter.onNext(faceWatchMode);
                emitter.onComplete();
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).as(view.bindAutoDispose()).subscribe(new BaseObserver<WatchTargetBean>(BaseApp.getApp(), false) {
            @Override
            public void onNext(WatchTargetBean watchTargetBean) {
                if (view != null) {
                    view.successGetGoalDistance(watchTargetBean.getTargetDistance());
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
