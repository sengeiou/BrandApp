package com.isport.brandapp.Home.presenter;

import com.isport.blelibrary.deviceEntry.impl.BaseDevice;
import com.isport.brandapp.Home.bean.http.Wristbandstep;

public interface IFragmentDataPresenter {

    void getDeviceList(boolean isFirstDisplayDB, boolean show, boolean reConnect);



    void connectDevice(BaseDevice device, boolean show, boolean isConnectByUser);

    void connectDevice(String deviceName, String mac, int deviceType, boolean show, boolean isConnectByUser);

    void cancelScan();
    void scan(int type, boolean isScale);

    void updateSportData(Wristbandstep mWristbandstep);

    void updateSleepHistoryData(boolean show);

    void getSportHomeData();

    void getWeekDate(String userid, String deviceId, boolean show, int currentType);

    void updateWatchHistoryData(boolean show);

    void syncUdateTime();

}
