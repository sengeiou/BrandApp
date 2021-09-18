package com.isport.brandapp.home.view;

import com.isport.blelibrary.db.table.scale.Scale_FourElectrode_DataModel;
import com.isport.blelibrary.db.table.sleep.Sleep_Sleepace_DataModel;
import com.isport.blelibrary.deviceEntry.impl.BaseDevice;
import com.isport.brandapp.home.bean.ScacleBean;
import com.isport.brandapp.home.bean.db.HeartRateMainData;
import com.isport.brandapp.home.bean.db.SleepMainData;
import com.isport.brandapp.home.bean.db.WatchSportMainData;
import com.isport.brandapp.home.bean.http.WatchSleepDayData;
import com.isport.brandapp.home.bean.http.WristbandHrHeart;
import com.isport.brandapp.home.bean.http.Wristbandstep;
import com.isport.brandapp.bean.DeviceBean;
import com.isport.brandapp.device.UpdateSuccessBean;
import com.isport.brandapp.wu.bean.BPInfo;
import com.isport.brandapp.wu.bean.OnceHrInfo;
import com.isport.brandapp.wu.bean.OxyInfo;
import com.isport.brandapp.wu.bean.TempInfo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import brandapp.isport.com.basicres.mvp.BaseView;

/**
 * @Author
 * @Date 2018/10/12
 * @Fuction
 */

public interface FragmentDataView extends BaseView {


    //******************************************已整理**********************************************//
    void successGetDeviceListFormDB(HashMap<Integer, DeviceBean> deviceBeanHashMap, boolean show, boolean reConnect);

    void successGetDeviceListFormHttp(HashMap<Integer, DeviceBean> deviceBeanHashMap, boolean show, boolean reConnect);

    void successGetMainScaleDataFromDB(ArrayList<ScacleBean> scacleBeans,
                                       Scale_FourElectrode_DataModel scale_fourElectrode_dataModel, boolean show);

    void successGetMainLastStepDataForDB(WatchSportMainData watchSportMainData);

    void successGetMainLastHrDataForDb(HeartRateMainData oxyInfo);

    void successGetMainLastOxgenData(OxyInfo info);

    void successGetMainLastOnceHrData(OnceHrInfo info);

    void successGetMainLastBloodPresuure(BPInfo info);
    void successGetMainLastTempValue(TempInfo info);

    void successGetMainTotalAllTime(Integer time);


    void successGetMainSleepaceDataFromDB(Sleep_Sleepace_DataModel sleep_Sleepace_DataModel,
                                          SleepMainData sleepMainData, boolean show);

    void successGetMainBraceletSleepFromDB(
            SleepMainData sleepMainData, boolean show);


    void successWatchHistoryDataFormHttp(boolean show,int type);


    void successSleepHistoryDataFormHttp(SleepMainData sleepMainData, Sleep_Sleepace_DataModel
            sleep_Sleepace_DataModel);

    //******************************************待整理**********************************************//


    void onScan(Map<String, BaseDevice> listDevicesMap);

    void onScan(String key, BaseDevice baseDevice);

    void onScanFinish();

    void updateSleepHistoryDataSuccess(UpdateSuccessBean updateSleepReportBean);


    void successSportMainData(String resultHomeSportData);


    void updateWatchHistoryDataSuccess(UpdateSuccessBean updateSleepReportBean);

    void updateScaleHistoryDataSuccess(UpdateSuccessBean updateSleepReportBean);


    void successDayHrData(WristbandHrHeart wristbandHrHeart, int currentType);

    void successDaySleepData(WatchSleepDayData watchSleepDayData, int currentType);

    void successDaySportData(Wristbandstep wristbandstep);


}
