package com.isport.brandapp.Home.bean.http;

/**
 * @创建者 bear
 * @创建时间 2019/3/11 11:21
 * @描述
 */
public class SporadicNapData {

    private int state;//睡眠状态 0 清醒  1 睡眠

    private int time;//时间长度

    private String sleepTimeStr;//睡眠时间段,只有睡眠时间才会标开始和结束


    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getSleepTimeStr() {
        return sleepTimeStr;
    }

    public void setSleepTimeStr(String sleepTimeStr) {
        this.sleepTimeStr = sleepTimeStr;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return "SporadicNapData{" +
                "state=" + state +
                ", time=" + time +
                ", sleepTimeStr='" + sleepTimeStr + '\'' +
                '}';
    }
}
