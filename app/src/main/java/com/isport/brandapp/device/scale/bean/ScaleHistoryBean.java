package com.isport.brandapp.device.scale.bean;

import java.util.ArrayList;

public class ScaleHistoryBean{
    @Override
    public String toString() {
        return "ScaleHistoryBean{" +
                "month='" + month + '\'' +
                ", leftWeight='" + leftWeight + '\'' +
                ", leftFatPersent='" + leftFatPersent + '\'' +
                ", strWeight='" + strWeight + '\'' +
                ", strFat='" + strFat + '\'' +
                ", isUpWeight=" + isUpWeight +
                ", isUpFatPresent=" + isUpFatPresent +
                ", isMonthTitle=" + isMonthTitle +
                ", datalist=" + datalist +
                '}';
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getLeftWeight() {
        return leftWeight;
    }

    public void setLeftWeight(String leftWeight) {
        this.leftWeight = leftWeight;
    }

    public String getLeftFatPersent() {
        return leftFatPersent;
    }

    public void setLeftFatPersent(String leftFatPersent) {
        this.leftFatPersent = leftFatPersent;
    }

    public String getStrWeight() {
        return strWeight;
    }

    public void setStrWeight(String strWeight) {
        this.strWeight = strWeight;
    }

    public String getStrFat() {
        return strFat;
    }

    public void setStrFat(String strFat) {
        this.strFat = strFat;
    }

    public boolean isUpWeight() {
        return isUpWeight;
    }

    public void setUpWeight(boolean upWeight) {
        isUpWeight = upWeight;
    }

    public boolean isUpFatPresent() {
        return isUpFatPresent;
    }

    public void setUpFatPresent(boolean upFatPresent) {
        isUpFatPresent = upFatPresent;
    }

    public boolean isMonthTitle() {
        return isMonthTitle;
    }

    public void setMonthTitle(boolean monthTitle) {
        isMonthTitle = monthTitle;
    }

    public ArrayList<ScaleDayBean> getDatalist() {
        return datalist;
    }

    public void setDatalist(ArrayList<ScaleDayBean> datalist) {
        this.datalist = datalist;
    }

    /**
     * 最外部是月header数据
     */
    public String month;
    public String leftWeight;
    public String leftFatPersent;
    public String strWeight;
    public String strFat;
    public boolean isUpWeight;
    public boolean isUpFatPresent;

    public boolean isMonthTitle;

    /**
     * 每月详细数据
     */
    public ArrayList<ScaleDayBean> datalist;
}
