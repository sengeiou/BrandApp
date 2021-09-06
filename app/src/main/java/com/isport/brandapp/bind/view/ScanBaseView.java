package com.isport.brandapp.bind.view;


import com.isport.blelibrary.deviceEntry.impl.BaseDevice;

import java.util.List;
import java.util.Map;

import brandapp.isport.com.basicres.mvp.BaseView;

/**
 * @Author
 * @Date 2018/10/15
 * @Fuction
 */

public interface ScanBaseView extends BaseView {

    void onScan(List<BaseDevice> baseViewList);

    void onScan(Map<String, BaseDevice> listDevicesMap);

    void bindSuccess(int deviceId);


    void canBind(int state);//-1 已经被绑定 0 已经用过的，更新 1 没有绑定过，新设备，插入

    int STATE_HASBIND_CONTBIND = -1;
    int STATE_HASBIND_CANBIND = 0;
    int STATE_NOBIND = 1;
    int STATE_BINDED = 2;//本地版本，绑定成功
}
