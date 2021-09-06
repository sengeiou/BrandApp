package com.isport.brandapp.ropeskipping.history.bean;

import java.util.Objects;

public class BarInfo {


    String date;
    String mdDate;
    boolean select;
    int maxVlaue;
    int currentValue;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public boolean isSelect() {
        return select;
    }

    public void setSelect(boolean select) {
        this.select = select;
    }

    public int getMaxVlaue() {
        return maxVlaue;
    }

    public void setMaxVlaue(int maxVlaue) {
        this.maxVlaue = maxVlaue;
    }

    public int getCurrentValue() {
        return currentValue;
    }

    public void setCurrentValue(int currentValue) {
        this.currentValue = currentValue;
    }

    public String getMdDate() {
        return mdDate;
    }

    public void setMdDate(String mdDate) {
        this.mdDate = mdDate;
    }


    public BarInfo() {

    }

    public BarInfo(String date,String mdDate) {
        this.date = date;
        this.mdDate=mdDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BarInfo barInfo = (BarInfo) o;
        return Objects.equals(date, barInfo.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(date);
    }
}
