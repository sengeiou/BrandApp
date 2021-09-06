package com.isport.brandapp.device.watch.presenter;

import com.isport.blelibrary.ISportAgent;
import com.isport.blelibrary.db.action.watch_w516.Watch_W516_NotifyModelAction;
import com.isport.blelibrary.db.action.watch_w516.Watch_W516_SettingModelAction;
import com.isport.blelibrary.db.table.DeviceTempUnitlTable;
import com.isport.blelibrary.db.table.bracelet_w311.Bracelet_W311_24H_hr_SettingModel;
import com.isport.blelibrary.db.table.w526.Device_BacklightTimeAndScreenLuminanceModel;
import com.isport.blelibrary.db.table.watch_w516.Watch_W516_NotifyModel;
import com.isport.blelibrary.db.table.watch_w516.Watch_W516_SettingModel;
import com.isport.blelibrary.managers.BaseManager;
import com.isport.blelibrary.utils.Logger;
import com.isport.brandapp.device.bracelet.bean.StateBean;
import com.isport.brandapp.device.bracelet.braceletModel.IW311SettingModel;
import com.isport.brandapp.device.bracelet.braceletModel.W311ModelSettingImpl;
import com.isport.brandapp.device.bracelet.view.DeviceGoalStepView;
import com.isport.brandapp.device.watch.W526.IW526DeviceSetting;
import com.isport.brandapp.device.watch.W526.W526DeviceSettingImp;
import com.isport.brandapp.device.watch.view.Device24HrView;
import com.isport.brandapp.device.watch.view.DeviceBackLightTimeAndScrenLeveView;

import brandapp.isport.com.basicres.BaseApp;
import brandapp.isport.com.basicres.commonnet.interceptor.BaseObserver;
import brandapp.isport.com.basicres.commonnet.interceptor.ExceptionHandle;
import brandapp.isport.com.basicres.commonutil.TokenUtil;
import brandapp.isport.com.basicres.mvp.BasePresenter;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class Device24HrPresenter extends BasePresenter<Device24HrView> {
    Device24HrView view;

    IW311SettingModel w311ModelSetting;

    public Device24HrPresenter(Device24HrView view) {
        this.view = view;
        w311ModelSetting = new W311ModelSettingImpl();
    }


    public void saveTempUtil(String deviceName, String userId, String unitl) {
        Observable.create(new ObservableOnSubscribe<Boolean>() {
            @Override
            public void subscribe(ObservableEmitter<Boolean> emitter) throws Exception {

                boolean isSuccess = w311ModelSetting.saveTempUtil(userId, deviceName, unitl);

                Logger.myLog("DeviceTempUnitlTable:" + isSuccess);



                emitter.onNext(isSuccess);
                emitter.onComplete();
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).as(view.bindAutoDispose()).subscribe(new BaseObserver<Boolean>(BaseApp.getApp()) {
            @Override
            public void onNext(Boolean stateBean) {
                if (view != null) {
                    //  view.successState(stateBean);
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
                StateBean stateBean = new StateBean();
                if (view != null) {
                    stateBean.isHrState = false;
                    stateBean.isCall = false;
                    stateBean.isMessage = false;
                    view.successState(stateBean);
                }
            }

        });
    }

    public void getMessageCallState(String deviceName, String userId) {


        Observable.create(new ObservableOnSubscribe<StateBean>() {
            @Override
            public void subscribe(ObservableEmitter<StateBean> emitter) throws Exception {
                Watch_W516_NotifyModel watch_w516_notifyModelByDeviceId = Watch_W516_NotifyModelAction.findWatch_W516_NotifyModelByDeviceId(deviceName, userId);
                Watch_W516_SettingModel w516SettingModelByDeviceId = Watch_W516_SettingModelAction.findW516SettingModelByDeviceId(deviceName, userId);

                DeviceTempUnitlTable table = w311ModelSetting.getTempUtil(userId, deviceName);

                Logger.myLog("DeviceTempUnitlTable:" + table);

                StateBean stateBean = new StateBean();

                if (table == null) {
                    stateBean.tempUnitl = "0";
                    BaseManager.isTmepUnitl = "0";
                } else {
                    BaseManager.isTmepUnitl = table.getTempUnitl();
                    stateBean.tempUnitl = table.getTempUnitl();
                }

                if (watch_w516_notifyModelByDeviceId != null) {
                    stateBean.isCall = watch_w516_notifyModelByDeviceId.getCallSwitch();
                    stateBean.isMessage = watch_w516_notifyModelByDeviceId.getMsgSwitch();
                } else {
                    stateBean.isCall = false;
                    stateBean.isMessage = false;
                }

                if (w516SettingModelByDeviceId != null) {
                    stateBean.isHrState = w516SettingModelByDeviceId.getHeartRateSwitch();
                } else {
                    stateBean.isHrState = false;
                }
                //这里需要查短信

                emitter.onNext(stateBean);
                emitter.onComplete();
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).as(view.bindAutoDispose()).subscribe(new BaseObserver<StateBean>(BaseApp.getApp()) {
            @Override
            public void onNext(StateBean stateBean) {
                if (view != null) {
                    view.successState(stateBean);
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
                StateBean stateBean = new StateBean();
                if (view != null) {
                    stateBean.isHrState = false;
                    stateBean.isCall = false;
                    stateBean.isMessage = false;
                    view.successState(stateBean);
                }
            }

        });


    }


    public void getDevice24HrSwitchState() {
        Observable.create(new ObservableOnSubscribe<Boolean>() {
            @Override
            public void subscribe(ObservableEmitter<Boolean> emitter) throws Exception {
                Watch_W516_SettingModel w516SettingModelByDeviceId = Watch_W516_SettingModelAction.findW516SettingModelByDeviceId(ISportAgent.getInstance().getCurrnetDevice().getDeviceName(), TokenUtil.getInstance().getPeopleIdInt(BaseApp.getApp()));
                if (w516SettingModelByDeviceId != null) {
                    emitter.onNext(w516SettingModelByDeviceId.getHeartRateSwitch());
                } else {
                    emitter.onNext(false);
                }
                emitter.onComplete();
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).as(view.bindAutoDispose()).subscribe(new BaseObserver<Boolean>(BaseApp.getApp(), false) {
            @Override
            public void onNext(Boolean isOpen) {

                if (view != null) {
                    view.success24HrSwitch(isOpen);
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
