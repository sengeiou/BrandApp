package com.isport.brandapp.device.watch.view;

import com.isport.blelibrary.db.table.watch_w516.Watch_W516_NotifyModel;

import brandapp.isport.com.basicres.mvp.BaseView;

public interface CallAndMessageNotiView extends BaseView {

    public void successGetCallAndMessageNoti(Watch_W516_NotifyModel model);

    public void successSaveCallAndMessageNoti();
}
