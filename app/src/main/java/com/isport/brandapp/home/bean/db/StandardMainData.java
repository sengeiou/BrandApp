package com.isport.brandapp.home.bean.db;

import com.isport.brandapp.home.bean.BaseMainData;
import com.isport.brandapp.device.bracelet.bean.StepBean;

import java.util.ArrayList;

/**
 * @Author
 * @Date 2019/1/10
 * @Fuction
 */

public class StandardMainData extends BaseMainData {

    private int days;//达标天数 天
    private long lastSyncTime;//上次同步时间
    private int compareDays;//和上周达标天数比较,天
    private boolean hasReach;//当天是否已经达标
    private ArrayList<StepBean> listDays;//步数数据list

    @Override
    public String toString() {
        return "StandardMainData{" +
                "days=" + days +
                ", lastSyncTime=" + lastSyncTime +
                ", compareDays=" + compareDays +
                ", hasReach=" + hasReach +
                ", listDays=" + listDays +
                '}';
    }

    public boolean isHasReach() {
        return hasReach;
    }

    public void setHasReach(boolean hasReach) {
        this.hasReach = hasReach;
    }

    public int getDays() {
        return days;
    }

    public void setDays(int days) {
        this.days = days;
    }

    public long getLastSyncTime() {
        return lastSyncTime;
    }

    public void setLastSyncTime(long lastSyncTime) {
        this.lastSyncTime = lastSyncTime;
    }

    public int getCompareDays() {
        return compareDays;
    }

    public void setCompareDays(int compareDays) {
        this.compareDays = compareDays;
    }

    public ArrayList<StepBean> getListDays() {
        return listDays;
    }

    public void setListDays(ArrayList<StepBean> listDays) {
        this.listDays = listDays;
    }

}
