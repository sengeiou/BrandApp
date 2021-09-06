package com.isport.brandapp.sport.bean;

import java.util.ArrayList;

public class ResultSportHistoryListBean {

    String dateStr;
    ArrayList<SportSumData> list;

    public String getDateStr() {
        return dateStr;
    }

    public void setDateStr(String dateStr) {
        this.dateStr = dateStr;
    }

    public ArrayList<SportSumData> getList() {
        return list;
    }

    public void setList(ArrayList<SportSumData> list) {
        this.list = list;
    }
}
