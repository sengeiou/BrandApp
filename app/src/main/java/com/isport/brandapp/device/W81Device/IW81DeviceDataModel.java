package com.isport.brandapp.device.W81Device;

import com.isport.blelibrary.db.table.w811w814.W81DeviceDetailData;
import com.isport.brandapp.Home.bean.db.HeartRateMainData;
import com.isport.brandapp.Home.bean.db.SleepMainData;
import com.isport.brandapp.Home.bean.db.WatchSportMainData;
import com.isport.brandapp.Home.bean.http.WatchSleepDayData;
import com.isport.brandapp.Home.bean.http.WristbandHrHeart;
import com.isport.brandapp.Home.bean.http.Wristbandstep;
import com.isport.brandapp.device.watch.bean.WatchInsertBean;
import com.isport.brandapp.wu.bean.BPInfo;
import com.isport.brandapp.wu.bean.ExerciseInfo;
import com.isport.brandapp.wu.bean.OnceHrInfo;
import com.isport.brandapp.wu.bean.OxyInfo;
import com.isport.brandapp.wu.bean.TempInfo;

import java.util.ArrayList;
import java.util.List;

public interface IW81DeviceDataModel {


    Wristbandstep getStepData(String userId, String deviceId, String strDate);


    WatchSportMainData getDateStrStepData(String userId, String deviceId, String strDate);

    /**
     * 获取最后一天的数据
     */
    WatchSportMainData getLastStepData(String userId, String deviceId, boolean isUbind);

    /**
     * 获取最新的一条睡眠数据
     */
    WatchSleepDayData getLastSleepData(String userId, String deviceId, boolean main);

    /**
     * 获取最新的心率数据
     */
    HeartRateMainData getLastHrData(String userId, String deviceId);

    /**
     * 获取指定日期的睡眠数据
     */
    WatchSleepDayData getStrDateSleepData(String userId, String deviceId, String strDate);

    /**
     * 获取指定日期的心率数据
     */
    WristbandHrHeart getStrDateHrData(String userId, String deviceId, String strDate);


    ArrayList<String> getMonthData(String strDate, String deviceId);

    ArrayList<String> getHrMonthData(String strDate, String userId, String deviceId);

    ArrayList<String> getSleepMonthData(String strDate, String userId, String deviceId);

    /**
     * 获取所有未上传的数据
     */
    List<WatchInsertBean> getAllNoUpgradeW81DeviceDetailData(String deviceId, String userId, String defWriId, boolean isUpgradeToday);


    void saveHrData(String deviceId, String userId, String wristbandSportDetailId, String dateStr, long timestamp, String hrList, int timeInterval);

    void saveStepData(String deviceId, String userId, String wristbandSportDetailId, String dateStr, long timestamp, int step, int dis, int cal, boolean isNet);

    void saveSleepData(String deviceId, String userId, String wristbandSportDetailId, String dateStr, long timestamp, int totalTime,
                       int restfulTime,
                       int lightTime,
                       int soberTime, String sleepDetail);


    void updateWriId(String deviceId, String userId, String strDate, String updateWriId);


    void saveOxygenData(OxyInfo oxyInfo);

    void saveBloodPresureData(BPInfo bpInfo);

    void saveTempData(TempInfo bpInfo);

    void saveExeciseData(ExerciseInfo exerciseInfo);

    void saveOnceHrData(OnceHrInfo exerciseInfo);


    OxyInfo getOxygenLastData(String deviceId, String userId);

    BPInfo getBloodPressureLastData(String deviceId, String userId);

    TempInfo getTempInfoeLastData(String deviceId, String userId);

    OnceHrInfo getOneceHrLastData(String deviceId, String userId);

    OnceHrInfo getOnceHrInfo(String deviceId, String userId);

    void updateWriId(String deviceId, String userId, int type);

    List<TempInfo> getLastNumberTempData(String deviceId, String userId, int number) ;


}
