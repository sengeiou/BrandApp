package com.isport.brandapp.home.bean.http;

/**
 * @Author
 * @Date 2018/11/6
 * @Fuction
 */

public class WatchDayData {

    private String buildTime;

    private String stepNum;

    private String stepKm;

    private String calorie;

    public void setBuildTime(String buildTime){
        this.buildTime = buildTime;
    }
    public String getBuildTime(){
        return this.buildTime;
    }
    public void setStepNum(String stepNum){
        this.stepNum = stepNum;
    }
    public String getStepNum(){
        return this.stepNum;
    }
    public void setStepKm(String stepKm){
        this.stepKm = stepKm;
    }
    public String getStepKm(){
        return this.stepKm;
    }
    public void setCalorie(String calorie){
        this.calorie = calorie;
    }
    public String getCalorie(){
        return this.calorie;
    }
}
