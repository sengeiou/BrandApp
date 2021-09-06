package com.isport.brandapp.login.view;

import brandapp.isport.com.basicres.commonbean.UserInfoBean;
import brandapp.isport.com.basicres.mvp.BaseView;

/**
 * Created by huashao on 2017/10/27.
 */

public interface ActivityBindMobilePhoneView extends BaseView {
    void getVerCodeSuccess(String baseBean);

    void getVerCodeNotPass(String baseBean);

    void bindPhoneSuccess(UserInfoBean baseBean);
}
