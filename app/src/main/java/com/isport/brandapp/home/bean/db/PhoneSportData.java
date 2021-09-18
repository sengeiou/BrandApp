package com.isport.brandapp.home.bean.db;

import com.isport.brandapp.home.bean.BaseMainData;

/**
 * @Author
 * @Date 2019/1/10
 * @Fuction
 */

public class PhoneSportData extends BaseMainData {

    private int time;//运动的时间 分钟
    private long lastSyncTime;//上次同步时间
    private int compareTime;//和上次运动比较,分钟
}
