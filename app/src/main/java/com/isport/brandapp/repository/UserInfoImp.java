package com.isport.brandapp.repository;

import brandapp.isport.com.basicres.commonbean.UserInfoBean;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;

public class UserInfoImp {

    public static Observable<UserInfoBean> getUserInfo() {
        return Observable.create(new ObservableOnSubscribe<UserInfoBean>() {
            @Override
            public void subscribe(ObservableEmitter<UserInfoBean> emitter) throws Exception {
                UserInfoBean userInfoBean = new UserInfoBean();
                emitter.onNext(userInfoBean);
            }
        });
    }
}
