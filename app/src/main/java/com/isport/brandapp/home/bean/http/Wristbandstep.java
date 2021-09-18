package com.isport.brandapp.home.bean.http;

import com.isport.brandapp.home.bean.BaseMainData;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * @Author
 * @Date 2018/11/5
 * @Fuction
 */

public class Wristbandstep extends BaseMainData implements Serializable {

    private int days;
    public String mothAndDay;
    private int wristbandStepId;

    private long buildTime;

    private long lastServerTime;

    private String stepNum;

    private String stepKm;

    private String calorie;

    private String strDate;

    private ArrayList<Integer> stepArry;


    public String avgStep;

    public String avgDis;

    public String avgCal;

    public String getAvgCal() {
        return avgCal;
    }

    public void setAvgCal(String avgCal) {
        this.avgCal = avgCal;
    }

    public String sumGasoline;

    public String sumFat;

    public void setWristbandStepId(int wristbandStepId) {
        this.wristbandStepId = wristbandStepId;
    }

    public int getWristbandStepId() {
        return this.wristbandStepId;
    }

    public void setBuildTime(long buildTime) {
        this.buildTime = buildTime;
    }

    public long getBuildTime() {
        return this.buildTime;
    }

    public void setLastServerTime(long lastServerTime) {
        this.lastServerTime = lastServerTime;
    }

    public long getLastServerTime() {
        return this.lastServerTime;
    }

    public void setStepNum(String stepNum) {
        this.stepNum = stepNum;
    }

    public String getStepNum() {
        return this.stepNum;
    }

    public void setStepKm(String stepKm) {
        this.stepKm = stepKm;
    }

    public String getStepKm() {
        return this.stepKm;
    }

    public void setCalorie(String calorie) {
        this.calorie = calorie;
    }

    public String getCalorie() {
        return this.calorie;
    }

    public ArrayList<Integer> getStepArry() {
        return stepArry;
    }

    public void setStepArry(ArrayList<Integer> stepArry) {
        this.stepArry = stepArry;
    }

    public String getStrDate() {
        return strDate;
    }

    public void setStrDate(String strDate) {
        this.strDate = strDate;
    }

    public void setAvgStep(String avgStep) {
        this.avgStep = avgStep;
    }

    public void setAvgDis(String avgDis) {
        this.avgDis = avgDis;
    }

    public void setSumGasoline(String sumGasoline) {
        this.sumGasoline = sumGasoline;
    }

    public void setSumFat(String sumFat) {
        this.sumFat = sumFat;
    }


    public String getAvgStep() {
        return avgStep;
    }

    public String getAvgDis() {
        return avgDis;
    }

    public String getSumGasoline() {
        return sumGasoline;
    }

    public String getSumFat() {
        return sumFat;
    }

    public String getMothAndDay() {
        return mothAndDay;
    }

    public void setMothAndDay(String mothAndDay) {
        this.mothAndDay = mothAndDay;
    }

    public Wristbandstep() {
    }

    public Wristbandstep(String mothAndDay, int wristbandStepId, long buildTime, long lastServerTime, String stepNum, String stepKm, String calorie, String strDate, ArrayList<Integer> stepArry, String avgStep, String avgDis, String avgCal, String sumGasoline, String sumFat) {
        this.mothAndDay = mothAndDay;
        this.wristbandStepId = wristbandStepId;
        this.buildTime = buildTime;
        this.lastServerTime = lastServerTime;
        this.stepNum = stepNum;
        this.stepKm = stepKm;
        this.calorie = calorie;
        this.strDate = strDate;
        this.stepArry = stepArry;
        this.avgStep = avgStep;
        this.avgDis = avgDis;
        this.avgCal = avgCal;
        this.sumGasoline = sumGasoline;
        this.sumFat = sumFat;
    }

    @Override
    public String toString() {
        return "Wristbandstep{" +
                "days=" + days +
                ", mothAndDay='" + mothAndDay + '\'' +
                ", wristbandStepId=" + wristbandStepId +
                ", buildTime=" + buildTime +
                ", lastServerTime=" + lastServerTime +
                ", stepNum='" + stepNum + '\'' +
                ", stepKm='" + stepKm + '\'' +
                ", calorie='" + calorie + '\'' +
                ", strDate='" + strDate + '\'' +
                ", stepArry=" + stepArry +
                ", avgStep='" + avgStep + '\'' +
                ", avgDis='" + avgDis + '\'' +
                ", avgCal='" + avgCal + '\'' +
                ", sumGasoline='" + sumGasoline + '\'' +
                ", sumFat='" + sumFat + '\'' +
                '}';
    }

    public int getDays() {
        return days;
    }

    public void setDays(int days) {
        this.days = days;
    }
}
