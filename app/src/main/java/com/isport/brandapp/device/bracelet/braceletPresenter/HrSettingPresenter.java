package com.isport.brandapp.device.bracelet.braceletPresenter;

import com.isport.blelibrary.db.action.bracelet_w311.Bracelet_W311_SettingModelAction;
import com.isport.blelibrary.db.table.bracelet_w311.Bracelet_W311_24H_hr_SettingModel;
import com.isport.blelibrary.db.table.bracelet_w311.Bracelet_w311_hrModel;
import com.isport.blelibrary.utils.Logger;
import com.isport.brandapp.device.bracelet.braceletModel.IW311SettingModel;
import com.isport.brandapp.device.bracelet.braceletModel.W311ModelSettingImpl;
import com.isport.brandapp.device.bracelet.view.HrSettingView;

import brandapp.isport.com.basicres.BaseApp;
import brandapp.isport.com.basicres.commonnet.interceptor.BaseObserver;
import brandapp.isport.com.basicres.commonnet.interceptor.ExceptionHandle;
import brandapp.isport.com.basicres.mvp.BasePresenter;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class HrSettingPresenter extends BasePresenter<HrSettingView> {
    private HrSettingView view;
    IW311SettingModel model;

    public HrSettingPresenter(HrSettingView view) {
        this.view = view;
        model = new W311ModelSettingImpl();
    }


    public void get24hHeartSwitchState(String userId, String devcieId) {


        Observable.create(new ObservableOnSubscribe<Bracelet_W311_24H_hr_SettingModel>() {
            @Override
            public void subscribe(ObservableEmitter<Bracelet_W311_24H_hr_SettingModel> emitter) throws Exception {
                Bracelet_W311_24H_hr_SettingModel settingModel = model.get24HSwitchState(userId, devcieId);
                //这里需要查短信
                if (settingModel == null) {
                    settingModel = new Bracelet_W311_24H_hr_SettingModel(1l, userId, devcieId, false);
                }
                emitter.onNext(settingModel);
                emitter.onComplete();
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).as(view.bindAutoDispose()).subscribe(new BaseObserver<Bracelet_W311_24H_hr_SettingModel>(BaseApp.getApp()) {
            @Override
            public void onNext(Bracelet_W311_24H_hr_SettingModel bracelet_w311_displayModel) {
                if (view != null) {
                    view.success24HrSettingState(bracelet_w311_displayModel);
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
                    view.success24HrSettingState(new Bracelet_W311_24H_hr_SettingModel(1l, userId, devcieId, false));
                }
            }

        });
    }

    /**
     * @param deviceId
     */
    public void getHrItem(String userid, String deviceId) {
        Observable.create(new ObservableOnSubscribe<Bracelet_w311_hrModel>() {
            @Override
            public void subscribe(ObservableEmitter<Bracelet_w311_hrModel> emitter) throws Exception {
                Bracelet_w311_hrModel dispalyItem = model.getHrSetting(userid, deviceId);
                if (dispalyItem == null) {
                    dispalyItem = new Bracelet_w311_hrModel(1l, userid, deviceId, false, 5);
                }
                Logger.myLog("Bracelet_w311_hrModel" + dispalyItem.toString());
                emitter.onNext(dispalyItem);
                emitter.onComplete();

            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).as(view.bindAutoDispose()).subscribe(new BaseObserver<Bracelet_w311_hrModel>(BaseApp.getApp()) {
            @Override
            public void onNext(Bracelet_w311_hrModel bracelet_w311_displayModel) {
                if (view != null) {
                    view.successHrSettingItem(bracelet_w311_displayModel);
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
                    view.successHrSettingItem(new Bracelet_w311_hrModel(1l, userid, deviceId, false, 30));
                }
            }

        });

    }

    public void saveHrSetting(Bracelet_w311_hrModel models) {

        Observable.create(new ObservableOnSubscribe<Boolean>() {
            @Override
            public void subscribe(ObservableEmitter<Boolean> emitter) throws Exception {

                Logger.myLog("Bracelet_w311_hrModel" + models.toString());

                boolean dispalyItem = model.saveHrSetting(models);
                Bracelet_W311_24H_hr_SettingModel model = new Bracelet_W311_24H_hr_SettingModel();
                model.setHeartRateSwitch(models.getIsOpen());
                model.setDeviceId(models.getDeviceId());
                model.setUserId(models.getUserId());
                Bracelet_W311_SettingModelAction.saveOrUpateBracelet24HHrSetting(model);
                emitter.onNext(dispalyItem);
                emitter.onComplete();
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).as(view.bindAutoDispose()).subscribe(new BaseObserver<Boolean>(BaseApp.getApp(), false) {
            @Override
            public void onNext(Boolean isSave) {
                if (view != null) {
                    view.successSave(isSave);
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

    public void saveHrSetting(String userId, String deviceId, boolean enbale) {

        Observable.create(new ObservableOnSubscribe<Boolean>() {
            @Override
            public void subscribe(ObservableEmitter<Boolean> emitter) throws Exception {
                Bracelet_W311_24H_hr_SettingModel model = new Bracelet_W311_24H_hr_SettingModel();
                model.setHeartRateSwitch(enbale);
                model.setDeviceId(deviceId);
                model.setUserId(userId);
                Bracelet_W311_SettingModelAction.saveOrUpateBracelet24HHrSetting(model);
                emitter.onNext(true);
                emitter.onComplete();
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).as(view.bindAutoDispose()).subscribe(new BaseObserver<Boolean>(BaseApp.getApp()) {
            @Override
            public void onNext(Boolean isSave) {
                if (view != null) {
                    view.successSave(isSave);
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
