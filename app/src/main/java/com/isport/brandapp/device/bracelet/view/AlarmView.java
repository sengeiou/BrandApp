package com.isport.brandapp.device.bracelet.view;


import com.isport.blelibrary.db.table.bracelet_w311.Bracelet_W311_AlarmModel;
import com.isport.blelibrary.db.table.watch_w516.Watch_W560_AlarmModel;

import java.util.ArrayList;

import brandapp.isport.com.basicres.mvp.BaseView;

public interface AlarmView extends BaseView {

    public void successAllAlarmItem(ArrayList<Bracelet_W311_AlarmModel> bracelet_w311_displayModel);

    public void successW560AllAlarmItem(ArrayList<Watch_W560_AlarmModel> watch_w560_alarmModels);

    public void successSaveAlarmItem();
    public void successDelectAlarmItem();


}
