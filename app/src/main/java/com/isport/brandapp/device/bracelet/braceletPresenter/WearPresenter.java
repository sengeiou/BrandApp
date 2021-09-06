package com.isport.brandapp.device.bracelet.braceletPresenter;

import android.text.TextUtils;

import com.isport.blelibrary.db.action.DeviceInformationTableAction;
import com.isport.blelibrary.db.table.DeviceInformationTable;
import com.isport.blelibrary.db.table.bracelet_w311.Bracelet_W311_DeviceInfoModel;
import com.isport.blelibrary.db.table.bracelet_w311.Bracelet_W311_WearModel;
import com.isport.blelibrary.utils.Logger;
import com.isport.brandapp.R;
import com.isport.brandapp.device.bracelet.braceletModel.IW311SettingModel;
import com.isport.brandapp.device.bracelet.braceletModel.W311ModelSettingImpl;
import com.isport.brandapp.device.bracelet.view.WearView;
import com.isport.brandapp.util.DeviceTypeUtil;

import brandapp.isport.com.basicres.BaseApp;
import brandapp.isport.com.basicres.commonnet.interceptor.BaseObserver;
import brandapp.isport.com.basicres.commonnet.interceptor.ExceptionHandle;
import brandapp.isport.com.basicres.mvp.BasePresenter;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class WearPresenter extends BasePresenter<WearView> {
    private WearView view;
    IW311SettingModel model;

    public WearPresenter(WearView view) {
        this.view = view;
        model = new W311ModelSettingImpl();
    }

    /**
     * @param deviceId
     */
    public void getWearItem(String userid, String deviceId) {
        Observable.create(new ObservableOnSubscribe<Bracelet_W311_WearModel>() {
            @Override
            public void subscribe(ObservableEmitter<Bracelet_W311_WearModel> emitter) throws Exception {
                Bracelet_W311_WearModel dispalyItem = model.getWearModel(userid, deviceId);
                if (dispalyItem == null) {
                    dispalyItem = new Bracelet_W311_WearModel(1l, userid, deviceId, true);
                }
                emitter.onNext(dispalyItem);
                emitter.onComplete();
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).as(view.bindAutoDispose()).subscribe(new BaseObserver<Bracelet_W311_WearModel>(BaseApp.getApp()) {
            @Override
            public void onNext(Bracelet_W311_WearModel bracelet_w311_displayModel) {
                if (view != null) {
                    view.successWearItem(bracelet_w311_displayModel);
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
                    view.successWearItem(new Bracelet_W311_WearModel(1l, userid, deviceId, true));
                }
            }

        });

    }

    public void saveWearItem(Bracelet_W311_WearModel models) {

        Observable.create(new ObservableOnSubscribe<Boolean>() {
            @Override
            public void subscribe(ObservableEmitter<Boolean> emitter) throws Exception {
                boolean dispalyItem = model.saveWearModel(models);

                emitter.onNext(dispalyItem);
                emitter.onComplete();
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).as(view.bindAutoDispose()).subscribe(new BaseObserver<Boolean>(BaseApp.getApp()) {
            @Override
            public void onNext(Boolean isSave) {
                if (view != null) {
                    view.successWearItem();
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

    public void saveDeviceInfo(Bracelet_W311_DeviceInfoModel infoModel) {
        Observable.create(new ObservableOnSubscribe<Boolean>() {
            @Override
            public void subscribe(ObservableEmitter<Boolean> emitter) throws Exception {
                boolean dispalyItem = model.saveDevieVersion(infoModel);

            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).as(view.bindAutoDispose()).subscribe(new BaseObserver<Boolean>(BaseApp.getApp(), false) {
            @Override
            public void onNext(Boolean model) {
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


    public void getDeviceInfo(String userId, String deviceId, int currentType) {
        if (DeviceTypeUtil.isContaintW81(currentType)) {
            getDeviceW81DeviceInfo(userId, deviceId);
        } else {
            getDeviceInfo(userId, deviceId);
        }
    }


    public void getDeviceW81DeviceInfo(String userId, String devcieId) {


        Observable.create(new ObservableOnSubscribe<DeviceInformationTable>() {
            @Override
            public void subscribe(ObservableEmitter<DeviceInformationTable> emitter) throws Exception {
                DeviceInformationTable deviceInfoByDeviceId = DeviceInformationTableAction.findDeviceInfoByDeviceId
                        (devcieId);
                if (deviceInfoByDeviceId == null) {
                    deviceInfoByDeviceId = new DeviceInformationTable();
                }
                emitter.onNext(deviceInfoByDeviceId);
                emitter.onComplete();
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).as(view.bindAutoDispose()).subscribe(new BaseObserver<DeviceInformationTable>(BaseApp.getApp(), false) {
            @Override
            public void onNext(DeviceInformationTable model) {
                if (view != null) {
                    view.getDeviceInfo(model);
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

    public void getDeviceInfo(String userId, String deviceId) {
        Observable.create(new ObservableOnSubscribe<Bracelet_W311_DeviceInfoModel>() {
            @Override
            public void subscribe(ObservableEmitter<Bracelet_W311_DeviceInfoModel> emitter) throws Exception {
                Bracelet_W311_DeviceInfoModel dispalyItem = model.getDevieVersion(userId, deviceId);
                emitter.onNext(dispalyItem);
                emitter.onComplete();
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).as(view.bindAutoDispose()).subscribe(new BaseObserver<Bracelet_W311_DeviceInfoModel>(BaseApp.getApp(), false) {
            @Override
            public void onNext(Bracelet_W311_DeviceInfoModel model) {
                if (view != null) {
                    view.getDeviceInfo(model);
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
