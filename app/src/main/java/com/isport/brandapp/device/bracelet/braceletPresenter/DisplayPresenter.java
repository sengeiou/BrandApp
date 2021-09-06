package com.isport.brandapp.device.bracelet.braceletPresenter;

import com.isport.blelibrary.db.action.watch_w516.Watch_W516_NotifyModelAction;
import com.isport.blelibrary.db.table.bracelet_w311.Bracelet_W311_DisplayModel;
import com.isport.blelibrary.db.table.watch_w516.Watch_W516_NotifyModel;
import com.isport.brandapp.device.bracelet.braceletModel.IW311SettingModel;
import com.isport.brandapp.device.bracelet.braceletModel.W311ModelSettingImpl;
import com.isport.brandapp.device.bracelet.view.DisplayView;

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

public class DisplayPresenter extends BasePresenter<DisplayView> {
    private DisplayView view;
    IW311SettingModel model;

    public DisplayPresenter(DisplayView view) {
        this.view = view;
        model = new W311ModelSettingImpl();
    }


    public void getAllDisplayState(String userid, String deviceId) {
        Observable.create(new ObservableOnSubscribe<Bracelet_W311_DisplayModel>() {
            @Override
            public void subscribe(ObservableEmitter<Bracelet_W311_DisplayModel> emitter) throws Exception {
                Bracelet_W311_DisplayModel dispalyItem = model.getDispalyItem(userid, deviceId);
                if (dispalyItem == null) {
                    dispalyItem = new Bracelet_W311_DisplayModel(1l, userid, deviceId, true, false, false, false, false, false);
                }
                emitter.onNext(dispalyItem);
                emitter.onComplete();
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).as(view.bindAutoDispose()).subscribe(new BaseObserver<Bracelet_W311_DisplayModel>(BaseApp.getApp()) {
            @Override
            public void onNext(Bracelet_W311_DisplayModel bracelet_w311_displayModel) {
                if (view != null) {
                    Watch_W516_NotifyModel watch_w516_notifyModelByDeviceId = Watch_W516_NotifyModelAction.findWatch_W516_NotifyModelByDeviceId(deviceId, TokenUtil.getInstance().getPeopleIdInt(BaseApp.getApp()));

                    boolean isMessage = false;
                    boolean isCall = false;
                    if (watch_w516_notifyModelByDeviceId != null) {
                        isMessage = watch_w516_notifyModelByDeviceId.getMsgSwitch();
                        isCall = watch_w516_notifyModelByDeviceId.getCallSwitch();
                    } else {

                    }
                    view.successDisplayItem(bracelet_w311_displayModel, isMessage, isCall);
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
                    Watch_W516_NotifyModel watch_w516_notifyModelByDeviceId = Watch_W516_NotifyModelAction.findWatch_W516_NotifyModelByDeviceId(deviceId, TokenUtil.getInstance().getPeopleIdInt(BaseApp.getApp()));
                    boolean isMessage = false;
                    boolean isCall = false;
                    if (watch_w516_notifyModelByDeviceId != null) {
                        isMessage = watch_w516_notifyModelByDeviceId.getMsgSwitch();
                        isCall = watch_w516_notifyModelByDeviceId.getCallSwitch();
                    } else {

                    }
                    view.successDisplayItem(new Bracelet_W311_DisplayModel(1l, userid, deviceId, true, false, false, false, false, false), isMessage, isCall);
                }
            }

        });

    }

    public void saveDisplayItem(Bracelet_W311_DisplayModel models) {

        Observable.create(new ObservableOnSubscribe<Boolean>() {
            @Override
            public void subscribe(ObservableEmitter<Boolean> emitter) throws Exception {
                boolean dispalyItem = model.saveDisplayItem(models);

                emitter.onNext(dispalyItem);
                emitter.onComplete();
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).as(view.bindAutoDispose()).subscribe(new BaseObserver<Boolean>(BaseApp.getApp()) {
            @Override
            public void onNext(Boolean isSave) {
                if (view != null) {
                    view.successSaveDisplayItem();
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
