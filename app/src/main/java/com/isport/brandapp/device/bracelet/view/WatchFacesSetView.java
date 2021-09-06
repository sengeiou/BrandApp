package com.isport.brandapp.device.bracelet.view;

import com.isport.blelibrary.db.table.w811w814.FaceWatchMode;

import brandapp.isport.com.basicres.mvp.BaseView;

public interface WatchFacesSetView extends BaseView {

    void successGetWatchFacesMode(FaceWatchMode faceWatchMode);

    void successSaveWatchFaceMode();

}
