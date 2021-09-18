package com.isport.brandapp.home.view;

import brandapp.isport.com.basicres.commonbean.UserInfoBean;

import brandapp.isport.com.basicres.mvp.BaseView;

/**
 * Created by huashao on 2017/10/27.
 */

public interface FragmentMineView extends BaseView {


    void successGetUserInfo(UserInfoBean userInfo);

    void postDeviceList();

}
