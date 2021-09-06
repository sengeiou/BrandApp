package com.isport.brandapp.login.view;


import brandapp.isport.com.basicres.commonbean.UserInfoBean;
import brandapp.isport.com.basicres.mvp.BaseView;

/**
 * Created by huashao on 2017/10/30.
 */

public interface ActivitySettingUserInfoView extends BaseView {

    void getUserInfo(UserInfoBean details);

    void saveUserBaseInfoSuccess();
}
