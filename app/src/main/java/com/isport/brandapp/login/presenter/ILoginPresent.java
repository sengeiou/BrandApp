package com.isport.brandapp.login.presenter;

import brandapp.isport.com.basicres.mvp.IBasePresenter;

public interface ILoginPresent extends IBasePresenter {

    //用户信息的所有接口


    void login(String phoneNum, String code, String type);

    void thirdPartyLogin(int platformType, String authId, String nickName, String headUrl);


    /**
     * 获取验证码
     */
    void getVerify(String phone, String type);


    void getEmailVerify(String email, String type, String language);

    //登录成功


    //获取用户信息

}
