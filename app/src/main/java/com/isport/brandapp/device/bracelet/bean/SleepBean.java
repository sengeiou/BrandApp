package com.isport.brandapp.device.bracelet.bean;

    public class SleepBean {

    public int starIndex;
    public int endIndex;
    public String startTime;
    public String endTime;
    public int state;
    public int sleepTime;

    @Override
    public String toString() {
        return "SleepBean{" +
                "starIndex=" + starIndex +
                ", endIndex=" + endIndex +
                ", startTime='" + startTime + '\'' +
                ", endTime='" + endTime + '\'' +
                ", state=" + state +
                ", sleepTime=" + sleepTime +
                '}';
    }
}
