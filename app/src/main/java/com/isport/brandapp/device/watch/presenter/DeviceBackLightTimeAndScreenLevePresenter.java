package com.isport.brandapp.device.watch.presenter;

import com.isport.blelibrary.db.table.w526.Device_BacklightTimeAndScreenLuminanceModel;
import com.isport.blelibrary.utils.Logger;
import com.isport.brandapp.device.bracelet.braceletModel.DeviceGoalModelImp;
import com.isport.brandapp.device.bracelet.braceletModel.IDeviceGoalModel;
import com.isport.brandapp.device.bracelet.view.DeviceGoalStepView;
import com.isport.brandapp.device.watch.W526.IW526DeviceSetting;
import com.isport.brandapp.device.watch.W526.W526DeviceSettingImp;
import com.isport.brandapp.device.watch.view.DeviceBackLightTimeAndScrenLeveView;

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

public class DeviceBackLightTimeAndScreenLevePresenter extends BasePresenter<DeviceBackLightTimeAndScrenLeveView> {
    DeviceBackLightTimeAndScrenLeveView view;
    IW526DeviceSetting modelSetting;

    public DeviceBackLightTimeAndScreenLevePresenter(DeviceBackLightTimeAndScrenLeveView view) {
        this.view = view;
        modelSetting = new W526DeviceSettingImp();
    }


    /**
     * @param userId
     * @param deviceId
     * @param value
     * @param type     0:为屏幕亮度等级
     *                 1:屏幕时长
     */

    public void saveDeviceBackLightTimeOrScreenLeve(String userId, String deviceId, int value, int type) {
        Observable.create(new ObservableOnSubscribe<Boolean>() {
            @Override
            public void subscribe(ObservableEmitter<Boolean> emitter) throws Exception {
                switch (type) {
                    case 0:
                        modelSetting.saveScrenLeve(deviceId, userId, value);
                        break;
                    case 1:
                        modelSetting.saveBackLightTime(deviceId, userId, value);
                        break;
                }

                // ArrayList<String> strDates = iw311DataModel.getMonthData(strDate, deviceId);
                emitter.onNext(true);
                emitter.onComplete();
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).as(view.bindAutoDispose()).subscribe(new BaseObserver<Boolean>(BaseApp.getApp(), false) {
            @Override
            public void onNext(Boolean faceWatchMode) {
                if (view != null) {
                    view.successSaveValue();
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

    public void getDeviceBackLightAndScreenLeve(String userId, String deviceId) {
        Observable.create(new ObservableOnSubscribe<Device_BacklightTimeAndScreenLuminanceModel>() {
            @Override
            public void subscribe(ObservableEmitter<Device_BacklightTimeAndScreenLuminanceModel> emitter) throws Exception {
                Device_BacklightTimeAndScreenLuminanceModel faceWatchMode = modelSetting.getMode(deviceId, userId);
                Logger.myLog("getBackLight:" + faceWatchMode + "userId:" + userId + "deviceId:" + deviceId);
                // ArrayList<String> strDates = iw311DataModel.getMonthData(strDate, deviceId);
                if (faceWatchMode == null) {
                    faceWatchMode = new Device_BacklightTimeAndScreenLuminanceModel();
                    faceWatchMode.setDeviceId(deviceId);
                    faceWatchMode.setUserId(userId);
                    faceWatchMode.setValuseScreenLeve(1);
                    faceWatchMode.setValuseBacklightTime(3);
                }
                emitter.onNext(faceWatchMode);
                emitter.onComplete();
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).as(view.bindAutoDispose()).subscribe(new BaseObserver<Device_BacklightTimeAndScreenLuminanceModel>(BaseApp.getApp(), false) {
            @Override
            public void onNext(Device_BacklightTimeAndScreenLuminanceModel watchTargetBean) {
                Logger.myLog("getBackLight:" + view);

                if (view != null) {
                    Logger.myLog("getBackLight:" + watchTargetBean.getValuseScreenLeve() + watchTargetBean.getValuseBacklightTime());
                    view.successGetBackLightScreen(watchTargetBean.getValuseScreenLeve(), watchTargetBean.getValuseBacklightTime());
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
                Logger.myLog("getBackLight: ResponeThrowable" + e);
            }

        });
    }
}
