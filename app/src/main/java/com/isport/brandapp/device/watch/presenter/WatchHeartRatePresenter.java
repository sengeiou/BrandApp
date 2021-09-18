package com.isport.brandapp.device.watch.presenter;

import com.isport.blelibrary.utils.DateUtil;
import com.isport.brandapp.AppConfiguration;
import com.isport.brandapp.home.bean.http.WristbandHrHeart;
import com.isport.brandapp.device.W81Device.IW81DeviceDataModel;
import com.isport.brandapp.device.W81Device.W81DeviceDataModelImp;
import com.isport.brandapp.device.bracelet.braceletModel.IW311DataModel;
import com.isport.brandapp.device.bracelet.braceletModel.W311DataModelImpl;
import com.isport.brandapp.device.watch.view.WatchHeartRateView;
import com.isport.brandapp.device.watch.watchModel.IW516Model;
import com.isport.brandapp.device.watch.watchModel.W516Model;
import com.isport.brandapp.util.DeviceTypeUtil;

import java.util.ArrayList;
import java.util.Date;

import brandapp.isport.com.basicres.BaseApp;
import brandapp.isport.com.basicres.commonnet.interceptor.BaseObserver;
import brandapp.isport.com.basicres.commonnet.interceptor.ExceptionHandle;
import brandapp.isport.com.basicres.mvp.BasePresenter;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class WatchHeartRatePresenter extends BasePresenter<WatchHeartRateView> {

    IW516Model watch516;
    IW311DataModel iw311DataModel;
    IW81DeviceDataModel w81DeviceDataModel;

    private WatchHeartRateView view;

    public WatchHeartRatePresenter(WatchHeartRateView view) {
        this.view = view;
        watch516 = new W516Model();
        iw311DataModel = new W311DataModelImpl();
        w81DeviceDataModel = new W81DeviceDataModelImp();
    }


    public void getMonthStrDate(String strMonth, int currentType, String devcieId, String userId) {
        Observable.create(new ObservableOnSubscribe<ArrayList<String>>() {
            @Override
            public void subscribe(ObservableEmitter<ArrayList<String>> emitter) throws Exception {
                ArrayList<String> strings;
                if (DeviceTypeUtil.isContainWrishBrand(currentType)) {
                    strings = iw311DataModel.getMonthHrDataToStrDate(strMonth, devcieId);
                } else if (DeviceTypeUtil.isContainWatch(currentType)) {
                    strings = watch516.getMonthHrDataToStrDate(strMonth);
                } else if (DeviceTypeUtil.isContainW81(currentType)) {
                    strings = w81DeviceDataModel.getHrMonthData(strMonth, userId, devcieId);
                } else {
                    strings = new ArrayList<>();
                }
                emitter.onNext(strings);
                emitter.onComplete();
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).as(view.bindAutoDispose()).subscribe(new BaseObserver<ArrayList<String>>(BaseApp.getApp()) {
            @Override
            public void onNext(ArrayList<String> list) {
                if (view != null) {
                    view.successMonthStrDate(list);
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

    public void getLastHrDetailData(int currentType, String userId, String devcieId) {

        Observable.create(new ObservableOnSubscribe<WristbandHrHeart>() {
            @Override
            public void subscribe(ObservableEmitter<WristbandHrHeart> emitter) throws Exception {
                WristbandHrHeart wristbandstep = null;
                if (DeviceTypeUtil.isContainWrishBrand(currentType)) {
                    wristbandstep = iw311DataModel.getLastDayHrData();
                    wristbandstep.setTimeInterval(5);
                } else if (DeviceTypeUtil.isContainWatch(currentType)) {
                    wristbandstep = watch516.getLastDayHrData();
                    wristbandstep.setTimeInterval(1);
                } else if (DeviceTypeUtil.isContainW81(currentType)) {
                    wristbandstep = w81DeviceDataModel.getStrDateHrData(String.valueOf(userId), devcieId, "");
                }
                if (wristbandstep == null) {
                    wristbandstep = new WristbandHrHeart();
                    if (DeviceTypeUtil.isContainWrishBrand(currentType)) {
                        wristbandstep.setTimeInterval(5);
                    } else if (DeviceTypeUtil.isContainWatch(currentType)) {
                        wristbandstep.setTimeInterval(1);
                    } else if (DeviceTypeUtil.isContainW81(currentType)) {
                        wristbandstep.setTimeInterval(5);
                    }
                    wristbandstep.setStrDate(DateUtil.dataToString(new Date(), "yyyy-MM-dd"));
                    wristbandstep.setAvgHr(0);
                    wristbandstep.setHrArry(new ArrayList<>());
                    wristbandstep.setMaxHr(0);
                    wristbandstep.setMinHr(0);

                }
                emitter.onNext(wristbandstep);
                emitter.onComplete();
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).as(view.bindAutoDispose()).subscribe(new BaseObserver<WristbandHrHeart>(BaseApp.getApp()) {
            @Override
            public void onNext(WristbandHrHeart wristbandHrHeart) {
                if (view != null) {
                    view.successDayHrDate(wristbandHrHeart);
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
                WristbandHrHeart wristbandstep = null;
                if (wristbandstep == null) {
                    wristbandstep = new WristbandHrHeart();
                    if (DeviceTypeUtil.isContainWrishBrand(currentType)) {
                        wristbandstep.setTimeInterval(5);
                    } else if (DeviceTypeUtil.isContainWatch(currentType)) {
                        wristbandstep.setTimeInterval(1);
                    } else if (DeviceTypeUtil.isContainW81(currentType)) {
                        wristbandstep.setTimeInterval(5);
                    }
                    wristbandstep.setStrDate(DateUtil.dataToString(new Date(), "yyyy-MM-dd"));
                    wristbandstep.setAvgHr(0);
                    wristbandstep.setHrArry(new ArrayList<>());
                    wristbandstep.setMaxHr(0);
                    wristbandstep.setMinHr(0);
                    wristbandstep = new WristbandHrHeart();
                }
                if (view != null) {
                    view.successDayHrDate(wristbandstep);
                }
            }

        });

    }

    public void getDayHrDetailData(String strDate, int currentType, String userId, String deviceId) {
        Observable.create(new ObservableOnSubscribe<WristbandHrHeart>() {
            @Override
            public void subscribe(ObservableEmitter<WristbandHrHeart> emitter) throws Exception {
                WristbandHrHeart wristbandstep = null;
                if (DeviceTypeUtil.isContainWrishBrand(currentType)) {
                    wristbandstep = iw311DataModel.getDayHrData(strDate, AppConfiguration.braceletID);
                    wristbandstep.setTimeInterval(5);
                } else if (DeviceTypeUtil.isContainWatch(currentType)) {
                    wristbandstep = watch516.getDayHrData(strDate);
                    wristbandstep.setTimeInterval(1);
                } else if (DeviceTypeUtil.isContainW81(currentType)) {
                    wristbandstep = w81DeviceDataModel.getStrDateHrData(userId, deviceId, strDate);
                }
                if (wristbandstep == null) {
                    wristbandstep = new WristbandHrHeart();
                    if (DeviceTypeUtil.isContainWrishBrand(currentType)) {
                        wristbandstep.setTimeInterval(5);
                    } else if (DeviceTypeUtil.isContainWatch(currentType)) {
                        wristbandstep.setTimeInterval(1);
                    } else if (DeviceTypeUtil.isContainW81(currentType)) {
                        wristbandstep.setTimeInterval(5);
                    }
                    wristbandstep.setStrDate(strDate);
                    wristbandstep.setAvgHr(0);
                    wristbandstep.setHrArry(new ArrayList<>());
                    wristbandstep.setMaxHr(0);
                    wristbandstep.setMinHr(0);

                }
                emitter.onNext(wristbandstep);
                emitter.onComplete();


            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).as(view.bindAutoDispose()).subscribe(new BaseObserver<WristbandHrHeart>(BaseApp.getApp()) {
            @Override
            public void onNext(WristbandHrHeart wristbandHrHeart) {
                if (view != null) {
                    view.successDayHrDate(wristbandHrHeart);
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
                WristbandHrHeart wristbandHrHeart = new WristbandHrHeart();
                if (DeviceTypeUtil.isContainWrishBrand(currentType)) {
                    wristbandHrHeart.setTimeInterval(5);
                } else if (DeviceTypeUtil.isContainWatch(currentType)) {
                    wristbandHrHeart.setTimeInterval(1);
                } else if (DeviceTypeUtil.isContainW81(currentType)) {
                    wristbandHrHeart.setTimeInterval(5);
                }
                wristbandHrHeart.setStrDate(strDate);
                wristbandHrHeart.setAvgHr(0);
                wristbandHrHeart.setHrArry(new ArrayList<>());
                wristbandHrHeart.setMaxHr(0);
                wristbandHrHeart.setMinHr(0);
                if (view != null) {
                    view.successDayHrDate(wristbandHrHeart);
                }
            }


        });
    }
}
