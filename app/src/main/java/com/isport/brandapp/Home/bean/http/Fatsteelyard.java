package com.isport.brandapp.Home.bean.http;

import com.isport.brandapp.Home.bean.BaseMainData;

import java.io.Serializable;

/**
 * @Author
 * @Date 2018/10/24
 * @Fuction  体脂称主页数据
 */

public class Fatsteelyard extends BaseMainData implements Serializable {

    private int reportId;

    @Override
    public String toString() {
        return "Fatsteelyard{" +
                "reportId=" + reportId +
                ", nearestTime='" + nearestTime + '\'' +
                ", weight='" + weight + '\'' +
                ", standardweight='" + standardweight + '\'' +
                ", standar='" + standar + '\'' +
                ", percentageFat='" + percentageFat + '\'' +
                ", bmi='" + bmi + '\'' +
                '}';
    }

    private long nearestTime;

    private String weight;

    private String standardweight;

    private String standar;

    private String percentageFat;

    private String bmi;

    public void setReportId(int reportId){
        this.reportId = reportId;
    }
    public int getReportId(){
        return this.reportId;
    }
    public void setNearestTime(long nearestTime){
        this.nearestTime = nearestTime;
    }
    public long getNearestTime(){
        return this.nearestTime;
    }
    public void setWeight(String weight){
        this.weight = weight;
    }
    public String getWeight(){
        return this.weight;
    }
    public void setStandardweight(String standardweight){
        this.standardweight = standardweight;
    }
    public String getStandardweight(){
        return this.standardweight;
    }
    public void setStandar(String standar){
        this.standar = standar;
    }
    public String getStandar(){
        return this.standar;
    }
    public void setPercentageFat(String percentageFat){
        this.percentageFat = percentageFat;
    }
    public String getPercentageFat(){
        return this.percentageFat;
    }
    public void setBmi(String bmi){
        this.bmi = bmi;
    }
    public String getBmi(){
        return this.bmi;
    }
}
