package com.isport.brandapp.device.bracelet.braceletModel;

import com.isport.blelibrary.db.table.bracelet_w311.Bracelet_W311_RealTimeData;
import com.isport.brandapp.home.bean.db.HeartRateMainData;
import com.isport.brandapp.home.bean.db.SleepMainData;
import com.isport.brandapp.home.bean.db.StandardMainData;
import com.isport.brandapp.home.bean.db.WatchSportMainData;
import com.isport.brandapp.home.bean.http.WatchSleepDayData;
import com.isport.brandapp.home.bean.http.WristbandHrHeart;
import com.isport.brandapp.home.bean.http.Wristbandstep;

import java.util.ArrayList;

public interface IW311DataModel {

    public Wristbandstep getW311SportData(String userid, String strDate, String deviceId);


    public void saveCurrentW311SportData(String userid, String strDate, String deviceId, long step, long cal, float dis);

    public ArrayList<String> getMonthData(String strDate, String deviceId);

    public Bracelet_W311_RealTimeData getRealTimeData(String userId, String deviceId);

    public boolean saveRealTimeData(String userid, String deviceId, int step, float dis, int cal, String date, String mac);

    public WatchSportMainData getW311hStepLastTwoData(String userid, String deviceId, boolean isToday);

    public HeartRateMainData getWatchHeartRteLastTwoData();


    public WatchSleepDayData getWatchSleepDayLastData(String deviceId);

    public SleepMainData getWatchSleepDayLastFourData(String deviceId);

    public WatchSleepDayData getWatchSleepDayData(String strDate, String deviceId);


    public void updateTodayTotalStep(long step, String strDate, String deviceId);

    public ArrayList<String> getSleepMonthData(String strDate, String deviceId);

    /**
     * @param userId
     * @param targetStep
     * @return
     */
    public StandardMainData getBraceletWeekData(String userId, int targetStep);


    /**
     * 获取最后一次的心率数据
     *
     * @return
     */
    public WristbandHrHeart getLastDayHrData();

    /**
     * 获取有心率数据的天数
     *
     * @param strDate
     * @return
     */
    public ArrayList<String> getMonthHrDataToStrDate(String strDate, String deviceId);

    /**
     * 获取当前的心率值
     *
     * @param strDate
     * @return
     */
    WristbandHrHeart getDayHrData(String strDate, String deviveId);
}
