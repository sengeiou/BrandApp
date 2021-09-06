package com.isport.brandapp.device.bracelet.braceletPresenter;

import com.isport.blelibrary.db.action.watch_w516.Watch_W516_NotifyModelAction;
import com.isport.blelibrary.db.table.bracelet_w311.Bracelet_W311_ThridMessageModel;
import com.isport.blelibrary.db.table.watch_w516.Watch_W516_NotifyModel;
import com.isport.blelibrary.managers.Constants;
import com.isport.brandapp.AppConfiguration;
import com.isport.brandapp.device.bracelet.braceletModel.IW311SettingModel;
import com.isport.brandapp.device.bracelet.braceletModel.W311ModelSettingImpl;
import com.isport.brandapp.device.bracelet.view.DisplayView;
import com.isport.brandapp.device.bracelet.view.ThridMeaageView;

import java.util.concurrent.ConcurrentHashMap;

import brandapp.isport.com.basicres.BaseApp;
import brandapp.isport.com.basicres.commonnet.interceptor.BaseObserver;
import brandapp.isport.com.basicres.commonnet.interceptor.ExceptionHandle;
import brandapp.isport.com.basicres.mvp.BasePresenter;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class ThridMessagePresenter extends BasePresenter<ThridMeaageView> {
    private ThridMeaageView view;
    IW311SettingModel model;

    public ThridMessagePresenter(ThridMeaageView view) {
        this.view = view;
        model = new W311ModelSettingImpl();
    }


    public void getAllThridMessageItem(String userid, String deviceId) {
        Observable.create(new ObservableOnSubscribe<Bracelet_W311_ThridMessageModel>() {
            @Override
            public void subscribe(ObservableEmitter<Bracelet_W311_ThridMessageModel> emitter) throws Exception {
                Bracelet_W311_ThridMessageModel dispalyItem = model.getW311ThridMessageItem(userid, deviceId);
                if (dispalyItem == null) {
                    dispalyItem = new Bracelet_W311_ThridMessageModel(1l, userid, deviceId, false, false, false, false, false, false, false, false, false, false, false, false);
                }
                emitter.onNext(dispalyItem);
                emitter.onComplete();
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).as(view.bindAutoDispose()).subscribe(new BaseObserver<Bracelet_W311_ThridMessageModel>(BaseApp.getApp(), false) {
            @Override
            public void onNext(Bracelet_W311_ThridMessageModel bracelet_w311_displayModel) {
                if (view != null) {
                    if (AppConfiguration.thridMessageList == null) {
                        AppConfiguration.thridMessageList = new ConcurrentHashMap<>();
                    }
                    if (bracelet_w311_displayModel.getIsQQ()) {
                        AppConfiguration.thridMessageList.put(Constants.KEY_13_PACKAGE, true);
                    } else {
                        AppConfiguration.thridMessageList.put(Constants.KEY_13_PACKAGE, false);
                    }

                    if (bracelet_w311_displayModel.getIsWechat()) {
                        AppConfiguration.thridMessageList.put(Constants.KEY_14_PACKAGE, true);
                    } else {
                        AppConfiguration.thridMessageList.put(Constants.KEY_14_PACKAGE, false);
                    }
                    if (bracelet_w311_displayModel.getIsSkype()) {
                        AppConfiguration.thridMessageList.put(Constants.KEY_15_PACKAGE, true);
                        AppConfiguration.thridMessageList.put(Constants.KEY_15_PACKAGE_1, true);
                        AppConfiguration.thridMessageList.put(Constants.KEY_15_PACKAGE_2, true);
                    } else {
                        AppConfiguration.thridMessageList.put(Constants.KEY_15_PACKAGE, false);
                        AppConfiguration.thridMessageList.put(Constants.KEY_15_PACKAGE_1, false);
                        AppConfiguration.thridMessageList.put(Constants.KEY_15_PACKAGE_2, false);
                    }

                    if (bracelet_w311_displayModel.getIsFaceBook()) {
                        AppConfiguration.thridMessageList.put(Constants.KEY_16_PACKAGE, true);
                    } else {
                        AppConfiguration.thridMessageList.put(Constants.KEY_16_PACKAGE, false);
                    }

                    if (bracelet_w311_displayModel.getIsTwitter()) {
                        AppConfiguration.thridMessageList.put(Constants.KEY_17_PACKAGE, true);

                    } else {
                        AppConfiguration.thridMessageList.put(Constants.KEY_17_PACKAGE, false);
                    }

                    if (bracelet_w311_displayModel.getIsWhatApp()) {
                        AppConfiguration.thridMessageList.put(Constants.KEY_1B_PACKAGE, true);
                    } else {
                        AppConfiguration.thridMessageList.put(Constants.KEY_1B_PACKAGE, false);
                    }
                    if (bracelet_w311_displayModel.getIsLinkedin()) {
                        AppConfiguration.thridMessageList.put(Constants.KEY_18_PACKAGE, true);
                    } else {
                        AppConfiguration.thridMessageList.put(Constants.KEY_18_PACKAGE, false);
                    }
                    if (bracelet_w311_displayModel.getIsInstagram()) {
                        AppConfiguration.thridMessageList.put(Constants.KEY_19_PACKAGE, true);
                    } else {
                        AppConfiguration.thridMessageList.put(Constants.KEY_19_PACKAGE, false);
                    }
                    if (bracelet_w311_displayModel.getIskakaotalk()) {
                        AppConfiguration.thridMessageList.put(Constants.KEY_KAOKAO_TALK, true);
                    } else {
                        AppConfiguration.thridMessageList.put(Constants.KEY_KAOKAO_TALK, false);
                    }
                    if (bracelet_w311_displayModel.getIsOthers()) {
                        AppConfiguration.thridMessageList.put(Constants.MESSGE_OHTERS, true);
                    } else {
                        AppConfiguration.thridMessageList.put(Constants.MESSGE_OHTERS, false);
                    }
                    if (bracelet_w311_displayModel.getIsLine()) {
                        AppConfiguration.thridMessageList.put(Constants.MESSGE_LINE, true);
                    } else {
                        AppConfiguration.thridMessageList.put(Constants.MESSGE_LINE, false);
                    }
                }

                //存到内存中
                view.successThridMessageItem(bracelet_w311_displayModel);
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
                    view.successThridMessageItem(new Bracelet_W311_ThridMessageModel(1l, userid, deviceId, true, false, false, false, false, false, false, false, false, false, false, false));
                }
            }

        });

    }

    public void saveThridMessageItem(Bracelet_W311_ThridMessageModel models) {

        Observable.create(new ObservableOnSubscribe<Boolean>() {
            @Override
            public void subscribe(ObservableEmitter<Boolean> emitter) throws Exception {
                boolean dispalyItem = model.saveW311ThridItem(models);

                emitter.onNext(dispalyItem);
                emitter.onComplete();
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).as(view.bindAutoDispose()).subscribe(new BaseObserver<Boolean>(BaseApp.getApp(), false) {
            @Override
            public void onNext(Boolean isSave) {
                if (view != null) {
                    view.successThridMessageItem();
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
