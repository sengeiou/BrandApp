package com.isport.brandapp.device.bracelet.braceletPresenter;

import com.isport.blelibrary.db.table.bracelet_w311.Bracelet_W311_LiftWristToViewInfoModel;
import com.isport.brandapp.device.bracelet.braceletModel.IW311SettingModel;
import com.isport.brandapp.device.bracelet.braceletModel.W311ModelSettingImpl;
import com.isport.brandapp.device.bracelet.view.LiftWristView;

import brandapp.isport.com.basicres.BaseApp;
import brandapp.isport.com.basicres.commonnet.interceptor.BaseObserver;
import brandapp.isport.com.basicres.commonnet.interceptor.ExceptionHandle;
import brandapp.isport.com.basicres.mvp.BasePresenter;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class LiftWristPresenter extends BasePresenter<LiftWristView> {
    private LiftWristView view;
    IW311SettingModel model;

    public LiftWristPresenter(LiftWristView view) {
        this.view = view;
        model = new W311ModelSettingImpl();
    }


    public void saveLiftWristBean(Bracelet_W311_LiftWristToViewInfoModel models) {
        Observable.create(new ObservableOnSubscribe<Boolean>() {
            @Override
            public void subscribe(ObservableEmitter<Boolean> emitter) throws Exception {
                boolean isSave = model.saveLifWristToViewInfo(models);
                emitter.onNext(isSave);
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
                if (view != null) {
                    view.successSave(false);
                }
            }

        });
    }

    /**
     * @param deviceId
     */
    public void getLiftWristBean(String userid, String deviceId) {
        Observable.create(new ObservableOnSubscribe<Bracelet_W311_LiftWristToViewInfoModel>() {
            @Override
            public void subscribe(ObservableEmitter<Bracelet_W311_LiftWristToViewInfoModel> emitter) throws Exception {
                Bracelet_W311_LiftWristToViewInfoModel dispalyItem = model.getLifWristToViewInfo(userid, deviceId);
                if (dispalyItem == null) {
                    dispalyItem = new Bracelet_W311_LiftWristToViewInfoModel(1l, userid, deviceId, 0, 22, 0, 7, 0, true);
                }
                emitter.onNext(dispalyItem);
                emitter.onComplete();
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).as(view.bindAutoDispose()).subscribe(new BaseObserver<Bracelet_W311_LiftWristToViewInfoModel>(BaseApp.getApp()) {
            @Override
            public void onNext(Bracelet_W311_LiftWristToViewInfoModel bracelet_w311_displayModel) {
                if (view != null) {
                    view.successLifWristBean(bracelet_w311_displayModel);
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
                    view.successLifWristBean(new Bracelet_W311_LiftWristToViewInfoModel(1l, userid, deviceId, 0, 22, 0, 7, 0, true));
                }
            }

        });

    }
}
