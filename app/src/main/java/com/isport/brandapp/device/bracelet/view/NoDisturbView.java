package com.isport.brandapp.device.bracelet.view;


import com.isport.blelibrary.db.table.bracelet_w311.Bracelet_W311_AlarmModel;
import com.isport.blelibrary.db.table.watch_w516.Watch_W516_SleepAndNoDisturbModel;

import java.util.ArrayList;

import brandapp.isport.com.basicres.mvp.BaseView;

public interface NoDisturbView extends BaseView {

    public void successDisturb(boolean isOpen);
    public void successDisturb(Watch_W516_SleepAndNoDisturbModel isOpen);



}
