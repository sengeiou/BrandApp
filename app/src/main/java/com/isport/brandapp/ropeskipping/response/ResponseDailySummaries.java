package com.isport.brandapp.ropeskipping.response;

import com.isport.blelibrary.db.table.s002.DailyBrief;

import java.util.ArrayList;

public class ResponseDailySummaries {
    /**
     * "day": "2020-09-17",
     * "totalSkippingNum": 3278,
     * "totalDuration": 3012,
     * "totalCalories": 247
     */
    String day;
    int totalSkippingNum;
    String totalDuration;
    String totalCalories;
    String userId;

    boolean isOpen;

    ArrayList<DailyBrief> list;
    ArrayList<DailyBrief> currentShowList;

    public ResponseDailySummaries() {
        list = new ArrayList<>();
    }

    public boolean isOpen() {
        return isOpen;
    }

    public void setOpen(boolean open) {
        isOpen = open;
    }

    public ArrayList<DailyBrief> getCurrentShowList() {
        if (currentShowList == null) {
            currentShowList = new ArrayList<>();
        }
        return currentShowList;
    }

    public void setCurrentShowList(ArrayList<DailyBrief> currentShowList) {
        this.currentShowList = currentShowList;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public int getTotalSkippingNum() {
        return totalSkippingNum;
    }

    public void setTotalSkippingNum(int totalSkippingNum) {
        this.totalSkippingNum = totalSkippingNum;
    }

    public String getTotalDuration() {
        return totalDuration;
    }

    public void setTotalDuration(String totalDuration) {
        this.totalDuration = totalDuration;
    }

    public String getTotalCalories() {
        return totalCalories;
    }

    public void setTotalCalories(String totalCalories) {
        this.totalCalories = totalCalories;
    }

    public ArrayList<DailyBrief> getList() {
        if (list == null) {
            list = new ArrayList<>();
        }
        return list;
    }

    public void setList(ArrayList<DailyBrief> list) {
        this.list = list;
    }

    @Override
    public String toString() {
        return "DailySummaries{" +
                "day='" + day + '\'' +
                ", totalSkippingNum=" + totalSkippingNum +
                ", totalDuration='" + totalDuration + '\'' +
                ", totalCalories='" + totalCalories + '\'' +
                ", isOpen=" + isOpen +
                ", list=" + list +
                '}';
    }
}
