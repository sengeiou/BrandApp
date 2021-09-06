package com.isport.brandapp.device.bracelet.view;


import com.isport.blelibrary.db.table.bracelet_w311.Bracelet_W311_DisplayModel;
import com.isport.blelibrary.db.table.bracelet_w311.Bracelet_W311_ThridMessageModel;

import brandapp.isport.com.basicres.mvp.BaseView;

public interface ThridMeaageView extends BaseView {

    public void successThridMessageItem(Bracelet_W311_ThridMessageModel bracelet_w311_displayModel);

    public void successThridMessageItem();

}
