package com.isport.blelibrary;

import android.content.Context;

import com.isport.blelibrary.managers.BraceletW311BleManager;
import com.isport.blelibrary.managers.BraceletW520BleManager;
import com.isport.blelibrary.managers.BraceletW811W814Manager;
import com.isport.blelibrary.managers.ScaleBleManager;
import com.isport.blelibrary.managers.SleepBleManager;
import com.isport.blelibrary.managers.Watch516BleManager;
import com.isport.blelibrary.managers.WatchW557BleManager;
import com.isport.blelibrary.utils.Logger;

public class InitDeviceManager {

    public static void initManager(Context mContext) {
        initSleep(mContext);
        initScale(mContext);
        inintW520(mContext);
        initW311(mContext);
        initWatch516(mContext);
        initW81x(mContext);
        initW577(mContext);


    }


    /**
     * 睡眠带
     */
    public static void initSleep(Context mContext) {
        SleepBleManager.getInstance().init(mContext);
    }

    /**
     * 体脂称
     */
    public static void initScale(Context mContext) {
        if (ScaleBleManager.instance == null)
            ScaleBleManager.getInstance().init(mContext);
    }


    /**
     * 初始化w311
     */
    public static void initW311(Context mContext) {
        Logger.myLog("initW311:" + BraceletW311BleManager.instance);
        if (BraceletW311BleManager.instance == null)
            BraceletW311BleManager.getInstance().init(mContext);
    }

    /**
     * w516
     */
    public static void initWatch516(Context mContext) {
        if (Watch516BleManager.instance == null)
            Watch516BleManager.getInstance().init(mContext);
    }


    public static void initW81x(Context mContext) {
        if (BraceletW811W814Manager.instance == null) {
            BraceletW811W814Manager.getInstance().init(mContext);
        }
    }

    public static void initW577(Context mContext) {
        if (WatchW557BleManager.instance == null) {
            WatchW557BleManager.getInstance().init(mContext);
        }
    }


    public static void inintW520(Context mContext) {
        BraceletW520BleManager.getInstance().init(mContext);
    }


}
