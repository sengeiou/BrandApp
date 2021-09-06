package com.isport.brandapp.device.bracelet.view;

import com.isport.blelibrary.db.table.bracelet_w311.Bracelet_W311_LiftWristToViewInfoModel;

import brandapp.isport.com.basicres.mvp.BaseView;

public interface LiftWristView extends BaseView {

    void successLifWristBean(Bracelet_W311_LiftWristToViewInfoModel model);
    void successSave(boolean isSave);
}
