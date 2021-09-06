package com.isport.blelibrary.db.table.s002;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class Summary {
    @Id
    private Long id;
    String totalSkippingNum;
    int totalDuration;
    String totalTimes;
    String totalCalories;
    String hour;
    String min;
    String strTime;
    String userId;
    String summaryType;
    String day;
    boolean isLocation;


    @Generated(hash = 1228385208)
    public Summary(Long id, String totalSkippingNum, int totalDuration,
                   String totalTimes, String totalCalories, String hour, String min,
                   String strTime, String userId, String summaryType, String day,
                   boolean isLocation) {
        this.id = id;
        this.totalSkippingNum = totalSkippingNum;
        this.totalDuration = totalDuration;
        this.totalTimes = totalTimes;
        this.totalCalories = totalCalories;
        this.hour = hour;
        this.min = min;
        this.strTime = strTime;
        this.userId = userId;
        this.summaryType = summaryType;
        this.day = day;
        this.isLocation = isLocation;
    }

    @Generated(hash = 1461598545)
    public Summary() {
    }


    public String getStrTime() {
        return strTime;
    }

    public void setStrTime(String strTime) {
        this.strTime = strTime;
    }

    public String getTotalSkippingNum() {
        return totalSkippingNum;
    }

    public void setTotalSkippingNum(String totalSkippingNum) {
        this.totalSkippingNum = totalSkippingNum;
    }

    public int getTotalDuration() {
        return totalDuration;
    }

    public void setTotalDuration(int totalDuration) {
        this.totalDuration = totalDuration;
    }

    public String getHour() {
        return hour;
    }

    public void setHour(String hour) {
        this.hour = hour;
    }

    public String getMin() {
        return min;
    }

    public void setMin(String min) {
        this.min = min;
    }

    public String getTotalTimes() {
        return totalTimes;
    }

    public void setTotalTimes(String totalTimes) {
        this.totalTimes = totalTimes;
    }

    public String getTotalCalories() {
        return totalCalories;
    }

    public void setTotalCalories(String totalCalories) {
        this.totalCalories = totalCalories;
    }

    public String getSummaryType() {
        return this.summaryType;
    }

    public void setSummaryType(String summaryType) {
        this.summaryType = summaryType;
    }

    public String getUserId() {
        return this.userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDay() {
        return this.day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public boolean getIsLocation() {
        return this.isLocation;
    }

    public void setIsLocation(boolean isLocation) {
        this.isLocation = isLocation;
    }

    @Override
    public String toString() {
        return "Summary{" +
                "id=" + id +
                ", totalSkippingNum='" + totalSkippingNum + '\'' +
                ", totalDuration=" + totalDuration +
                ", totalTimes='" + totalTimes + '\'' +
                ", totalCalories='" + totalCalories + '\'' +
                ", hour='" + hour + '\'' +
                ", min='" + min + '\'' +
                ", strTime='" + strTime + '\'' +
                ", userId='" + userId + '\'' +
                ", summaryType='" + summaryType + '\'' +
                ", day='" + day + '\'' +
                ", isLocation=" + isLocation +
                '}';
    }
}
