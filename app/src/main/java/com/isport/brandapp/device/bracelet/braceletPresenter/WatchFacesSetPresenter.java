package com.isport.brandapp.device.bracelet.braceletPresenter;

import com.isport.blelibrary.db.table.w811w814.FaceWatchMode;
import com.isport.blelibrary.utils.Logger;
import com.isport.brandapp.device.bracelet.braceletModel.W311DataModelImpl;
import com.isport.brandapp.device.bracelet.braceletModel.W311ModelSettingImpl;
import com.isport.brandapp.device.bracelet.view.W311RealTimeDataView;
import com.isport.brandapp.device.bracelet.view.WatchFacesSetView;
import com.isport.brandapp.util.DeviceTypeUtil;

import java.util.ArrayList;

import brandapp.isport.com.basicres.BaseApp;
import brandapp.isport.com.basicres.commonnet.interceptor.BaseObserver;
import brandapp.isport.com.basicres.commonnet.interceptor.ExceptionHandle;
import brandapp.isport.com.basicres.mvp.BasePresenter;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class WatchFacesSetPresenter extends BasePresenter<WatchFacesSetView> {
    WatchFacesSetView view;
    W311ModelSettingImpl modelSetting;

    public WatchFacesSetPresenter(WatchFacesSetView view) {
        this.view = view;
        modelSetting = new W311ModelSettingImpl();
    }


    public void saveWatchFace(String devcieId, String userId, int faceMode) {
        Observable.create(new ObservableOnSubscribe<Boolean>() {
            @Override
            public void subscribe(ObservableEmitter<Boolean> emitter) throws Exception {
                boolean isSave = modelSetting.saveWatchFacesSetting(devcieId,userId,faceMode);
                // ArrayList<String> strDates = iw311DataModel.getMonthData(strDate, deviceId);
                emitter.onNext(isSave);
                emitter.onComplete();
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).as(view.bindAutoDispose()).subscribe(new BaseObserver<Boolean>(BaseApp.getApp(), false) {
            @Override
            public void onNext(Boolean faceWatchMode) {
                if (view != null) {
                    view.successSaveWatchFaceMode();
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

    public void getWatchFace(String userId, String deviceId) {
        Observable.create(new ObservableOnSubscribe<FaceWatchMode>() {
            @Override
            public void subscribe(ObservableEmitter<FaceWatchMode> emitter) throws Exception {
                FaceWatchMode faceWatchMode = modelSetting.getWatchModeSetting(userId, deviceId);
                Logger.myLog("getWatchFace:" + faceWatchMode + "userId:" + userId + "deviceId:" + deviceId);
                // ArrayList<String> strDates = iw311DataModel.getMonthData(strDate, deviceId);
                if (faceWatchMode == null) {
                    faceWatchMode = new FaceWatchMode();
                    faceWatchMode.setDeviceId(deviceId);
                    faceWatchMode.setUserId(userId);
                    faceWatchMode.setFaceWatchMode(1);
                }
                emitter.onNext(faceWatchMode);
                emitter.onComplete();
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).as(view.bindAutoDispose()).subscribe(new BaseObserver<FaceWatchMode>(BaseApp.getApp(), false) {
            @Override
            public void onNext(FaceWatchMode faceWatchMode) {
                if (view != null) {
                    view.successGetWatchFacesMode(faceWatchMode);
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
