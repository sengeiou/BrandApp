package com.isport.brandapp.device.watch.bean;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Collections;

public class HrHitoryDetail {

    int startIndex;
    int endIndex;
    String startTime;
    String endTime;
    int maxHr;
    int minHr;
    int avgHr;
    boolean isSelect;
    ArrayList<Integer> lists;
    int invert;

    public int getInvert() {
        if (invert == 0) {
            invert = 1;
        }
        return invert;
    }

    public void setInvert(int invert) {
        this.invert = invert;
    }

    public int getStartIndex() {
        return startIndex;
    }

    public void setStartIndex(int startIndex) {
        this.startIndex = startIndex;
    }

    public int getEndIndex() {
        return endIndex;
    }

    public void setEndIndex(int endIndex) {
        this.endIndex = endIndex;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public int getMaxHr() {
        return maxHr;
    }

    public void setMaxHr(int maxHr) {
        this.maxHr = maxHr;
    }

    public int getMinHr() {
        return minHr;
    }

    public void setMinHr(int minHr) {
        this.minHr = minHr;
    }

    public boolean isSelect() {
        return isSelect;
    }

    public void setSelect(boolean select) {
        isSelect = select;
    }

    public ArrayList<Integer> getLists() {
        return lists;
    }

    public int getAvgHr() {
        return avgHr;
    }

    public void setAvgHr(int avgHr) {
        this.avgHr = avgHr;
    }

    public void setLists(ArrayList<Integer> lists) {
        this.lists = new ArrayList<>();
        this.lists.addAll(lists);
        this.setMaxHr(Collections.max(lists));
        this.setMinHr(Collections.min(lists));

        int sum = 0;
        for (int i = 0; i < lists.size(); i++) {
            sum += lists.get(i);
        }
        if (sum > 0) {
            this.setAvgHr(sum / lists.size());
        }
    }

    @Override
    public String toString() {
        return "HrHitoryDetail{" +
                "startIndex=" + startIndex +
                ", endIndex=" + endIndex +
                ", startTime='" + startTime + '\'' +
                ", endTime='" + endTime + '\'' +
                ", maxHr=" + maxHr +
                ", minHr=" + minHr +
                ", avgHr=" + avgHr +
                ", isSelect=" + isSelect +
                ", lists=" + new Gson().toJson(lists) +
                ", invert=" + invert +
                '}';
    }
}
