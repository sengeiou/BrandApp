package com.isport.brandapp.device.bracelet.view;


import com.isport.blelibrary.db.table.bracelet_w311.Bracelet_W311_RealTimeData;
import com.isport.blelibrary.db.table.bracelet_w311.Bracelet_w311_hrModel;

import brandapp.isport.com.basicres.entry.WatchRealTimeData;
import brandapp.isport.com.basicres.mvp.BaseView;

public interface W311RealTimeDataView extends BaseView {

    public void getW311RealTimeData(Bracelet_W311_RealTimeData bracelet_w311_wearModel);
    public void getW516OrW556(WatchRealTimeData bracelet_w311_wearModel);

    public void successSaveRealTimeData(boolean isSave);

}
