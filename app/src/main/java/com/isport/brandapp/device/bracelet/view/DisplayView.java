package com.isport.brandapp.device.bracelet.view;


import com.isport.blelibrary.db.table.bracelet_w311.Bracelet_W311_DisplayModel;

import brandapp.isport.com.basicres.mvp.BaseView;

public interface DisplayView extends BaseView {

    public void successDisplayItem(Bracelet_W311_DisplayModel bracelet_w311_displayModel,boolean isMessage,boolean isCall);

    public void successSaveDisplayItem();

}
