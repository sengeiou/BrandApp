package com.isport.brandapp.device.scale.presenter;

import com.isport.brandapp.device.scale.view.ScaleMainView;

import brandapp.isport.com.basicres.mvp.BasePresenter;

public class ScaleMainPresenter extends BasePresenter<ScaleMainView> {

    private ScaleMainView mMessageView;

    public ScaleMainPresenter(ScaleMainView messageView) {
        this.mMessageView = messageView;
    }


}
