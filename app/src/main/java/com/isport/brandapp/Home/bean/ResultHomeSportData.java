package com.isport.brandapp.Home.bean;

public class ResultHomeSportData {
    /**
     * "timestamp": 1551075626340,
     "timeLong": 0,
     "compareTime": 0,
     "sportName": "运动"
     */
    long timestamp;
    long timeLong;
    int compareTime;
    String sportName;

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public long getTimeLong() {
        return timeLong;
    }

    public void setTimeLong(long timeLong) {
        this.timeLong = timeLong;
    }

    public int getCompareTime() {
        return compareTime;
    }

    public void setCompareTime(int compareTime) {
        this.compareTime = compareTime;
    }

    public String getSportName() {
        return sportName;
    }

    public void setSportName(String sportName) {
        this.sportName = sportName;
    }
}
