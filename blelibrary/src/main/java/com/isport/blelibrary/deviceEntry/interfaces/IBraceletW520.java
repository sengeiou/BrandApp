package com.isport.blelibrary.deviceEntry.interfaces;

import com.isport.blelibrary.entry.AutoSleep;
import com.isport.blelibrary.entry.WristbandData;
import com.isport.blelibrary.entry.WristbandForecast;

import java.util.List;

public interface IBraceletW520 {
    public void w520SetDial(int enbale);

    public void setWeather(WristbandData wristbandData,List<WristbandForecast> list);

    public void setSleepData(AutoSleep sleepData);

    public void getSleepData();
    public void setRaise307J(int state);


}
