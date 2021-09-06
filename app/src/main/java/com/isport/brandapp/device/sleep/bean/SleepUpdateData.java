package com.isport.brandapp.device.sleep.bean;

import java.io.Serializable;

/**
 * @Author
 * @Date 2018/10/29
 * @Fuction
 */

public class SleepUpdateData implements Serializable{

    private String buildTime;

    private String fallAlseepAllTime;

    private String duration;

    private String deepSleepAllTime;

    private String deepSleepPerc;

    private String trunOverTimes;

    private String averageHeartBeatRate;

    private String averageBreathRate;

    public void setBuildTime(String buildTime){
        this.buildTime = buildTime;
    }
    public String getBuildTime(){
        return this.buildTime;
    }
    public void setFallAlseepAllTime(String fallAlseepAllTime){
        this.fallAlseepAllTime = fallAlseepAllTime;
    }
    public String getFallAlseepAllTime(){
        return this.fallAlseepAllTime;
    }
    public void setDuration(String duration){
        this.duration = duration;
    }
    public String getDuration(){
        return this.duration;
    }
    public void setDeepSleepAllTime(String deepSleepAllTime){
        this.deepSleepAllTime = deepSleepAllTime;
    }
    public String getDeepSleepAllTime(){
        return this.deepSleepAllTime;
    }
    public void setDeepSleepPerc(String deepSleepPerc){
        this.deepSleepPerc = deepSleepPerc;
    }
    public String getDeepSleepPerc(){
        return this.deepSleepPerc;
    }
    public void setTrunOverTimes(String trunOverTimes){
        this.trunOverTimes = trunOverTimes;
    }
    public String getTrunOverTimes(){
        return this.trunOverTimes;
    }
    public void setAverageHeartBeatRate(String averageHeartBeatRate){
        this.averageHeartBeatRate = averageHeartBeatRate;
    }
    public String getAverageHeartBeatRate(){
        return this.averageHeartBeatRate;
    }
    public void setAverageBreathRate(String averageBreathRate){
        this.averageBreathRate = averageBreathRate;
    }
    public String getAverageBreathRate(){
        return this.averageBreathRate;
    }
}
