package com.isport.brandapp.device.watch.watchModel;

import com.isport.brandapp.home.bean.db.HeartRateMainData;
import com.isport.brandapp.home.bean.db.StandardMainData;
import com.isport.brandapp.home.bean.db.WatchSportMainData;
import com.isport.brandapp.home.bean.http.WatchSleepDayData;
import com.isport.brandapp.home.bean.http.WristbandHrHeart;
import com.isport.brandapp.home.bean.http.Wristbandstep;

import java.util.ArrayList;

import brandapp.isport.com.basicres.entry.WatchRealTimeData;
import brandapp.isport.com.basicres.entry.WatchTargetBean;

public interface IW516Model {


    public Wristbandstep getLastSprotData();

    public WatchSportMainData getWatchStepLastTwoData(boolean isConnect);

    public HeartRateMainData getWatchHeartRteLastTwoData();

    public Wristbandstep getWatchDayData(String strDate);

    public void getSumData();

    public void getDetailData();

    public ArrayList<String> getMonthData(String strData);

    public ArrayList<String> getSleepMonthData(String deviceId, String strDate);

    public Wristbandstep getWatchLastMonthData(String userId, String deviceId);

    public WatchTargetBean getWatchTargetStep(String deviceId, String userId);

    public WatchSleepDayData getWatchSleepDayData(String strDate, String deviceId);

    public Wristbandstep calStepToKmAndCal(long currentstep);

    public WatchSleepDayData getWatchSleepDayLastData(String deviceId);

    public WristbandHrHeart getDayHrData(String strData);

    public WristbandHrHeart getLastDayHrData();

    public ArrayList<String> getMonthHrDataToStrDate(String strDate);


    public StandardMainData getWatchWeekData(String userId, int targetStep);

    public WatchRealTimeData getRealWatchData(String deviceName);


    public void saveDeviceSedentaryReminder(String devcieId, String userId, int times, String starTime, String endTime, boolean enable);
}
