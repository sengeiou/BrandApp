package com.isport.brandapp.device.bracelet.braceletPresenter;

import com.isport.blelibrary.utils.Logger;
import com.isport.brandapp.device.bracelet.braceletModel.DeviceGoalModelImp;
import com.isport.brandapp.device.bracelet.braceletModel.IDeviceGoalModel;
import com.isport.brandapp.device.bracelet.view.DeviceGoalStepView;

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

public class DeviceGoalStepPresenter extends BasePresenter<DeviceGoalStepView> {
    DeviceGoalStepView view;
    IDeviceGoalModel modelSetting;

    public DeviceGoalStepPresenter(DeviceGoalStepView view) {
        this.view = view;
        modelSetting = new DeviceGoalModelImp();
    }


    public void saveDeviceGoalStep(String userId, String deviceId, int step) {
        Observable.create(new ObservableOnSubscribe<Boolean>() {
            @Override
            public void subscribe(ObservableEmitter<Boolean> emitter) throws Exception {
                modelSetting.saveDeviceGoalValue(userId, deviceId, step, 0);
                // ArrayList<String> strDates = iw311DataModel.getMonthData(strDate, deviceId);
                emitter.onNext(true);
                emitter.onComplete();
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).as(view.bindAutoDispose()).subscribe(new BaseObserver<Boolean>(BaseApp.getApp(), false) {
            @Override
            public void onNext(Boolean faceWatchMode) {
                if (view != null) {

                    view.successSaveGoalStep(step);
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
     * 保存设备目标
     * @param userId userId
     * @param deviceId  deviceId
     * @param step  目标步数
     * @param type  类型
     */
    public void saveDeviceGoalStep(String userId, String deviceId, int step,int type) {
        Observable.create(new ObservableOnSubscribe<Boolean>() {
            @Override
            public void subscribe(ObservableEmitter<Boolean> emitter) throws Exception {
                modelSetting.saveDeviceGoalValue(userId, deviceId, step, type);
                // ArrayList<String> strDates = iw311DataModel.getMonthData(strDate, deviceId);
                emitter.onNext(true);
                emitter.onComplete();
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).as(view.bindAutoDispose()).subscribe(new BaseObserver<Boolean>(BaseApp.getApp(), false) {
            @Override
            public void onNext(Boolean faceWatchMode) {
                if (view != null) {

                    view.successSaveGoalStep(step);
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



    public void getDeviceGoalStep(String userId, String deviceId) {
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
                    faceWatchMode.setTargetStep(6000);
                }
                emitter.onNext(faceWatchMode);
                emitter.onComplete();
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).as(view.bindAutoDispose()).subscribe(new BaseObserver<WatchTargetBean>(BaseApp.getApp(), false) {
            @Override
            public void onNext(WatchTargetBean watchTargetBean) {
                if (view != null) {
                    view.successGetGoalStep(watchTargetBean.getTargetStep());
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
