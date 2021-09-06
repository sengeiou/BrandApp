package com.isport.brandapp.device.bracelet.braceletPresenter;

import com.isport.blelibrary.db.table.bracelet_w311.Bracelet_W311_RealTimeData;
import com.isport.blelibrary.result.impl.watch.WatchRealTimeResult;
import com.isport.blelibrary.utils.Logger;
import com.isport.blelibrary.utils.StepArithmeticUtil;
import com.isport.blelibrary.utils.TimeUtils;
import com.isport.brandapp.AppConfiguration;
import com.isport.brandapp.Home.bean.db.WatchSportMainData;
import com.isport.brandapp.device.W81Device.IW81DeviceDataModel;
import com.isport.brandapp.device.W81Device.W81DeviceDataModelImp;
import com.isport.brandapp.device.bracelet.braceletModel.IW311DataModel;
import com.isport.brandapp.device.bracelet.braceletModel.W311DataModelImpl;
import com.isport.brandapp.device.bracelet.view.W311RealTimeDataView;
import com.isport.brandapp.util.DeviceTypeUtil;

import bike.gymproject.viewlibray.pickerview.utils.DateUtils;
import brandapp.isport.com.basicres.BaseApp;
import brandapp.isport.com.basicres.action.BaseAction;
import brandapp.isport.com.basicres.action.WatchRealTimeDataAction;
import brandapp.isport.com.basicres.commonnet.interceptor.BaseObserver;
import brandapp.isport.com.basicres.commonnet.interceptor.ExceptionHandle;
import brandapp.isport.com.basicres.commonutil.TokenUtil;
import brandapp.isport.com.basicres.entry.WatchRealTimeData;
import brandapp.isport.com.basicres.gen.WatchRealTimeDataDao;
import brandapp.isport.com.basicres.mvp.BasePresenter;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class W311RealTimeDataPresenter extends BasePresenter<W311RealTimeDataView> {
    private W311RealTimeDataView view;
    IW311DataModel model;
    IW81DeviceDataModel w81DeviceDataModel;

    public W311RealTimeDataPresenter(W311RealTimeDataView view) {
        this.view = view;
        model = new W311DataModelImpl();
        w81DeviceDataModel = new W81DeviceDataModelImp();
    }


    public void saveW526RealTimeData(int step, int cal, float dis, String deviceName) {

        Observable.create(new ObservableOnSubscribe<Boolean>() {
            @Override
            public void subscribe(ObservableEmitter<Boolean> emitter) throws Exception {
                WatchRealTimeDataDao watchRealTimeDataDao = BaseAction.getWatchRealTimeDataDao();
                WatchRealTimeData watchRealTimeData = new WatchRealTimeData();
                watchRealTimeData.setId((long) 2);
                watchRealTimeData.setCal(cal);
                watchRealTimeData.setStepKm(dis);
                watchRealTimeData.setStepNum(step);
                watchRealTimeData.setDate(DateUtils.getYMD(System.currentTimeMillis()));
                watchRealTimeData.setMac(deviceName);
                watchRealTimeDataDao.insertOrReplace(watchRealTimeData);
                emitter.onNext(true);
                emitter.onComplete();
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).as(view.bindAutoDispose()).subscribe(new BaseObserver<Boolean>(BaseApp.getApp(), false) {
            @Override
            public void onNext(Boolean isSave) {
                if (view != null) {
                    if (isSave) {
                        view.successSaveRealTimeData(isSave);
                    }
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

    public void saveRealTimeData(String userid, String deviceId, int step, float dis, int cal, String date, String mac) {
        Observable.create(new ObservableOnSubscribe<Boolean>() {
            @Override
            public void subscribe(ObservableEmitter<Boolean> emitter) throws Exception {
                boolean dispalyItem = model.saveRealTimeData(userid, deviceId, step, dis, cal, date, mac);
                if (dispalyItem) {
                    model.saveCurrentW311SportData(userid, date, deviceId, step, cal, dis);
                }
                emitter.onNext(dispalyItem);
                emitter.onComplete();
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).as(view.bindAutoDispose()).subscribe(new BaseObserver<Boolean>(BaseApp.getApp(), false) {
            @Override
            public void onNext(Boolean isSave) {
                if (view != null) {
                    if (isSave) {
                        view.successSaveRealTimeData(isSave);
                    }
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


    public void getDeviceStepLastTwoData(int currentType) {
        Observable.create(new ObservableOnSubscribe<Bracelet_W311_RealTimeData>() {
            @Override
            public void subscribe(ObservableEmitter<Bracelet_W311_RealTimeData> emitter) throws Exception {
                WatchSportMainData mWatchSportMainData;
                mWatchSportMainData = w81DeviceDataModel.getLastStepData(TokenUtil.getInstance().getPeopleIdStr(BaseApp.getApp()), AppConfiguration.braceletID, true);
                Bracelet_W311_RealTimeData w311_realTimeData = new Bracelet_W311_RealTimeData();
                Logger.myLog("getDeviceStepLastTwoData" + mWatchSportMainData);
                if (mWatchSportMainData == null) {
                    w311_realTimeData = new Bracelet_W311_RealTimeData(1l, TokenUtil.getInstance().getPeopleIdInt(BaseApp.getApp()), AppConfiguration.braceletID,
                            0, 0, 0, "0", "0");
                } else {
                    int step = Integer.parseInt(mWatchSportMainData.getStep());
                    //int dis = Integer.parseInt(mWatchSportMainData.getDistance());//单位是米
                    int cal = Integer.parseInt(mWatchSportMainData.getCal());
                    w311_realTimeData.setStepNum(step);
                    w311_realTimeData.setStepKm(Float.parseFloat(mWatchSportMainData.getDistance()));
                    w311_realTimeData.setCal(cal);
                }
                Logger.myLog("getDeviceStepLastTwoData" + w311_realTimeData);
                emitter.onNext(w311_realTimeData);
                emitter.onComplete();

            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).as(view.bindAutoDispose()).subscribe(new BaseObserver<Bracelet_W311_RealTimeData>(BaseApp.getApp(), false) {
            @Override
            public void onNext(Bracelet_W311_RealTimeData watchSportMainData) {
                if (view != null) {
                    view.getW311RealTimeData(watchSportMainData);
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


    public void getRealTimeDataW516OrW556() {


        Observable.create(new ObservableOnSubscribe<WatchRealTimeData>() {
            @Override
            public void subscribe(ObservableEmitter<WatchRealTimeData> emitter) throws Exception {
                WatchRealTimeData watchRealTimeData1 = WatchRealTimeDataAction.getWatchRealTimeData
                        (DateUtils
                                .getYMD(System.currentTimeMillis()), AppConfiguration.braceletID);
                if (watchRealTimeData1 == null) {
                    watchRealTimeData1 = new WatchRealTimeData();
                    watchRealTimeData1.setStepNum(0);
                    watchRealTimeData1.setStepKm(0);
                    watchRealTimeData1.setCal(0);
                    watchRealTimeData1.setDate(TimeUtils.getTodayddMMyyyy());
                }
                emitter.onNext(watchRealTimeData1);
                emitter.onComplete();
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).as(view.bindAutoDispose()).subscribe(new BaseObserver<WatchRealTimeData>(BaseApp.getApp()) {
            @Override
            public void onNext(WatchRealTimeData bracelet_w311_displayModel) {
                if (view != null) {
                    view.getW516OrW556(bracelet_w311_displayModel);
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
     * @param deviceId
     */
    public void getRealTimeData(String userid, String deviceId) {
        Observable.create(new ObservableOnSubscribe<Bracelet_W311_RealTimeData>() {
            @Override
            public void subscribe(ObservableEmitter<Bracelet_W311_RealTimeData> emitter) throws Exception {
                Bracelet_W311_RealTimeData dispalyItem = model.getRealTimeData(userid, deviceId);
                if (dispalyItem == null) {
                    dispalyItem = new Bracelet_W311_RealTimeData(1l, userid, deviceId,
                            0, 0, 0, "0", "0");
                }
                emitter.onNext(dispalyItem);
                emitter.onComplete();
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).as(view.bindAutoDispose()).subscribe(new BaseObserver<Bracelet_W311_RealTimeData>(BaseApp.getApp()) {
            @Override
            public void onNext(Bracelet_W311_RealTimeData bracelet_w311_displayModel) {
                if (view != null) {
                    view.getW311RealTimeData(bracelet_w311_displayModel);
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
                    view.getW311RealTimeData(new Bracelet_W311_RealTimeData(1l, userid, deviceId,
                            0, 0, 0, "0", "0"));
                }
            }

        });

    }


}
