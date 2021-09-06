package com.isport.brandapp.login.view;


import brandapp.isport.com.basicres.commonbean.BaseBean;
import brandapp.isport.com.basicres.commonbean.UserInfoBean;
import brandapp.isport.com.basicres.mvp.BaseView;

/**
 * Created by Administrator on 2017/10/16.
 */
public interface LoginBaseView extends BaseView {

    void loginSuccess(UserInfoBean loginBean);

    void getVerCodeSuccess(BaseBean baseBean);

    void getVerCodeSuccess(String baseBean);

    void getVerCodeNotPass(BaseBean baseBean);

    void thirdPartyLoginSuccess(UserInfoBean thirdLoginBean, int platformType);

    void loginBackCode(int code);
}