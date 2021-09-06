package com.isport.brandapp.login.view;

import brandapp.isport.com.basicres.entry.bean.UpdatePhotoBean;

import brandapp.isport.com.basicres.mvp.BaseView;

public interface ActivityImageShowView extends BaseView {
    void postPhotosSuccess(UpdatePhotoBean bean);
}
