package com.isport.brandapp.ropeskipping.realsport;

import com.isport.brandapp.repository.S002DeviceDataRepository;

import brandapp.isport.com.basicres.BaseApp;
import brandapp.isport.com.basicres.commonnet.interceptor.BaseObserver;
import brandapp.isport.com.basicres.commonnet.interceptor.ExceptionHandle;
import brandapp.isport.com.basicres.mvp.BasePresenter;

public class RealRopeSkippingPresenter extends BasePresenter<RealRopeSkippingView> {

    private RealRopeSkippingView mMessageView;

    public RealRopeSkippingPresenter(RealRopeSkippingView messageView) {
        this.mMessageView = messageView;
    }
    public void upgradeChalleg(String challengeItemId, String userId) {
        S002DeviceDataRepository.requstUserChallengReCords(challengeItemId, userId).as(mMessageView.bindAutoDispose()).subscribe(new BaseObserver<String>(BaseApp.getApp()) {
            @Override
            protected void hideDialog() {

            }

            @Override
            protected void showDialog() {

            }

            @Override
            public void onError(ExceptionHandle.ResponeThrowable e) {

                if (e.code == 2000) {
                    if (mMessageView != null) {
                        mMessageView.successChallegUpdate();
                    }
                }
            }

            @Override
            public void onNext(String s) {

            }
        });
    }

}
