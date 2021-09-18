package com.isport.brandapp.home.view;

import com.isport.blelibrary.db.table.scale.Scale_FourElectrode_DataModel;
import com.isport.blelibrary.deviceEntry.impl.BaseDevice;
import com.isport.brandapp.home.bean.AdviceBean;
import com.isport.brandapp.home.bean.MainDeviceBean;
import com.isport.brandapp.home.bean.ScacleBean;
import com.isport.brandapp.bean.DeviceBean;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import brandapp.isport.com.basicres.mvp.BaseView;

/**
 * @Author
 * @Date 2018/10/12
 * @Fuction
 */

public interface DeviceListView extends BaseView {


    //******************************************已整理**********************************************//
    void successGetDeviceListFormDB(HashMap<Integer, DeviceBean> deviceBeanHashMap, ArrayList<MainDeviceBean> list, boolean show, boolean reConnect,boolean isConnect);

    void successGetDeviceListFormHttp(HashMap<Integer, DeviceBean> deviceBeanHashMap, ArrayList<MainDeviceBean> list, boolean show, boolean reConnect,boolean isConnect);


    void successGetMainScaleDataFromDB(ArrayList<ScacleBean> scacleBeans,
                                       Scale_FourElectrode_DataModel scale_fourElectrode_dataModel, boolean show);


    void onScan(Map<String, BaseDevice> listDevicesMap);

    void onScan(String key, BaseDevice baseDevice);

    void onScanFinish();

    void getAdviceList(List<AdviceBean> adviceBeans);


}
