package com.isport.blelibrary.deviceEntry.interfaces;

import com.isport.blelibrary.entry.WristbandData;
import com.isport.blelibrary.entry.WristbandForecast;

import java.util.List;

public interface ITarget {
    void setDeviceGoalStep(int targetStep);

    void setTimeFormat(int timeFormat);

    void sendMessage(String message, int messageType);

    void showSwitchCameraView();

    void sendOtherMessageSwitch(boolean isSwitch);

    void measureBloodPressure(boolean isState);

    void measureOxygenBlood(boolean isState);

    void measureOnceHrData(boolean isState);

    void w81QeryAlarmList();

    void setTodayWeather(WristbandData weather, String city);

    void set15DayWeather(List<WristbandForecast> weather);


    void getVersion();



}
