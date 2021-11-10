package com.isport.brandapp.ropeskipping.realsport;

import com.isport.blelibrary.utils.Logger;
import com.isport.brandapp.repository.S002DeviceDataRepository;

import brandapp.isport.com.basicres.BaseApp;
import brandapp.isport.com.basicres.commonnet.interceptor.BaseObserver;
import brandapp.isport.com.basicres.commonnet.interceptor.ExceptionHandle;
import brandapp.isport.com.basicres.mvp.BasePresenter;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;

public class RealRopeSkippingPresenter extends BasePresenter<RealRopeSkippingView> {

    private static final String TAG = "RealRopeSkippingPresent";

    private RealRopeSkippingView mMessageView;

    public RealRopeSkippingPresenter(RealRopeSkippingView messageView) {
        this.mMessageView = messageView;
    }
    public void upgradeChalleg(String challengeItemId, String userId) {
        Logger.myLog(TAG,"----upgradeChalleg参数="+challengeItemId+"\n"+userId);
        S002DeviceDataRepository.requstUserChallengReCords(challengeItemId, userId).as(mMessageView.bindAutoDispose()).subscribe(new BaseObserver<String>(BaseApp.getApp(),false) {
            @Override
            protected void hideDialog() {

            }

            @Override
            protected void showDialog() {

            }

            @Override
            public void onSubscribe(Disposable d) {
                super.onSubscribe(d);
                Logger.myLog(TAG,"----onSubscribe=");
            }

            @Override
            public void onError(ExceptionHandle.ResponeThrowable e) {
                Logger.myLog(TAG,"----ResponeThrowable="+e.getMessage()+"\n"+e.code);
//                if (e.code == 2000) {
//                    if (mMessageView != null) {
//                        mMessageView.successChallegUpdate();
//                    }
//                }
            }

            @Override
            public void onComplete() {
                super.onComplete();
                Logger.myLog(TAG,"---onComplete=");
            }

            @Override
            public void onNext(String s) {
                Logger.myLog(TAG,"---onNext="+s);
                if (mMessageView != null) {
                    mMessageView.successChallegUpdate(s);
                }
            }
        });
    }



    public void getAllRopeChallengeRank(String id){
        S002DeviceDataRepository.getRopeChallengeRank(id).as(mMessageView.bindAutoDispose()).subscribe(new BaseObserver<String>(BaseApp.getApp()) {
            @Override
            protected void hideDialog() {

            }

            @Override
            protected void showDialog() {

            }

            @Override
            public void onError(ExceptionHandle.ResponeThrowable e) {
                Logger.myLog(TAG,"-----eeee="+e.toString());
            }

            @Override
            public void onNext(@NonNull String s) {
                Logger.myLog(TAG,"-----onNext="+s);
                if(mMessageView != null)
                    mMessageView.getAllRopeChallengeRank(s);
            }
        });
    }

}
