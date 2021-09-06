package com.isport.brandapp.device.bracelet.braceletPresenter;

import com.crrepa.ble.conn.type.CRPTimeSystemType;
import com.isport.blelibrary.db.table.w811w814.DeviceTimeFormat;
import com.isport.blelibrary.db.table.w811w814.FaceWatchMode;
import com.isport.blelibrary.utils.Logger;
import com.isport.brandapp.device.bracelet.braceletModel.W311ModelSettingImpl;
import com.isport.brandapp.device.bracelet.view.TimeFormatView;
import com.isport.brandapp.device.bracelet.view.WatchFacesSetView;

import brandapp.isport.com.basicres.BaseApp;
import brandapp.isport.com.basicres.commonnet.interceptor.BaseObserver;
import brandapp.isport.com.basicres.commonnet.interceptor.ExceptionHandle;
import brandapp.isport.com.basicres.mvp.BasePresenter;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class TimeFormatPresenter extends BasePresenter<TimeFormatView> {
    TimeFormatView view;
    W311ModelSettingImpl modelSetting;


    public TimeFormatPresenter(TimeFormatView view) {
        this.view = view;
        modelSetting = new W311ModelSettingImpl();
    }


    public void saveTimeFormat(String devcieId, String userId, int formate) {
        Observable.create(new ObservableOnSubscribe<Boolean>() {
            @Override
            public void subscribe(ObservableEmitter<Boolean> emitter) throws Exception {
                boolean isSave = modelSetting.saveTimeFormat(devcieId, userId, formate);
                // ArrayList<String> strDates = iw311DataModel.getMonthData(strDate, deviceId);
                emitter.onNext(isSave);
                emitter.onComplete();
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).as(view.bindAutoDispose()).subscribe(new BaseObserver<Boolean>(BaseApp.getApp(), false) {
            @Override
            public void onNext(Boolean isSave) {
                if (view != null) {
                    view.successTimeFormat(isSave);
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

    public void getTimeFomate(String userId, String deviceId) {
        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
                DeviceTimeFormat deviceTimeFormat = modelSetting.getTimeFormate(deviceId, userId);
                // ArrayList<String> strDates = iw311DataModel.getMonthData(strDate, deviceId);
                ////CRPTimeSystemType.TIME_SYSTEM_12 ==0  CRPTimeSystemType.TIME_SYSTEM_24==1
                int timeFormat = 0;
                if (deviceTimeFormat == null) {
                    timeFormat = CRPTimeSystemType.TIME_SYSTEM_24;
                } else {
                    timeFormat = deviceTimeFormat.getTimeformate();
                }
                emitter.onNext(timeFormat);
                emitter.onComplete();
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).as(view.bindAutoDispose()).subscribe(new BaseObserver<Integer>(BaseApp.getApp(), false) {
            @Override
            public void onNext(Integer faceWatchMode) {
                if (view != null) {
                    view.successGetTimeFormat(faceWatchMode);
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
