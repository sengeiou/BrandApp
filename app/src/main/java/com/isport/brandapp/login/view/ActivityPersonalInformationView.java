package com.isport.brandapp.login.view;



import brandapp.isport.com.basicres.commonbean.UserInfoBean;
import brandapp.isport.com.basicres.mvp.BaseView;

/**
 * Created by huashao on 2017/10/31.
 */

public interface ActivityPersonalInformationView extends BaseView {

    void genderPSuccess(String gender);

    void getUserInfoSuccess(UserInfoBean bean);

    void postPhotosSuccess(UserInfoBean userDetails);

    void postDeletePhotosSuccess(UserInfoBean userDetails);
}
