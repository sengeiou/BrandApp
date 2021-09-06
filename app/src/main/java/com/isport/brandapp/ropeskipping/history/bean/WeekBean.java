package com.isport.brandapp.ropeskipping.history.bean;

import com.isport.blelibrary.db.table.s002.DailyBrief;

import java.util.ArrayList;

public class WeekBean {

    int sumCount;
    String cal;
    String date;
    ArrayList<DailyBrief> list;
    boolean isOpen;


    public boolean isOpen() {
        return isOpen;
    }

    public void setOpen(boolean open) {
        isOpen = open;
    }


    public int getSumCount() {
        return sumCount;
    }

    public void setSumCount(int sumCount) {
        this.sumCount = sumCount;
    }

    public String getCal() {
        return cal;
    }

    public void setCal(String cal) {
        this.cal = cal;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public ArrayList<DailyBrief> getList() {
        return list;
    }

    public void setList(ArrayList<DailyBrief> list) {
        this.list = list;
    }
}
