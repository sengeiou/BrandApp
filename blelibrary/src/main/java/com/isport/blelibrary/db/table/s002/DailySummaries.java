package com.isport.blelibrary.db.table.s002;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class DailySummaries {
    /**
     * "day": "2020-09-17",
     * "totalSkippingNum": 3278,
     * "totalDuration": 3012,
     * "totalCalories": 247
     */
    @Id
    private Long id;
    String day;
    int totalSkippingNum;
    String totalDuration;
    String totalCalories;
    String userId;
    boolean isLocation;

    public boolean isLocation() {
        return isLocation;
    }

    public void setLocation(boolean location) {
        isLocation = location;
    }

    public String getUserId() {
        return this.userId;
    }
    public void setUserId(String userId) {
        this.userId = userId;
    }
    public String getTotalCalories() {
        return this.totalCalories;
    }
    public void setTotalCalories(String totalCalories) {
        this.totalCalories = totalCalories;
    }
    public String getTotalDuration() {
        return this.totalDuration;
    }
    public void setTotalDuration(String totalDuration) {
        this.totalDuration = totalDuration;
    }
    public int getTotalSkippingNum() {
        return this.totalSkippingNum;
    }
    public void setTotalSkippingNum(int totalSkippingNum) {
        this.totalSkippingNum = totalSkippingNum;
    }
    public String getDay() {
        return this.day;
    }
    public void setDay(String day) {
        this.day = day;
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public boolean getIsLocation() {
        return this.isLocation;
    }
    public void setIsLocation(boolean isLocation) {
        this.isLocation = isLocation;
    }
    @Generated(hash = 1908454197)
    public DailySummaries(Long id, String day, int totalSkippingNum,
            String totalDuration, String totalCalories, String userId,
            boolean isLocation) {
        this.id = id;
        this.day = day;
        this.totalSkippingNum = totalSkippingNum;
        this.totalDuration = totalDuration;
        this.totalCalories = totalCalories;
        this.userId = userId;
        this.isLocation = isLocation;
    }
    @Generated(hash = 1913683130)
    public DailySummaries() {
    }

    @Override
    public String toString() {
        return "DailySummaries{" +
                "id=" + id +
                ", day='" + day + '\'' +
                ", totalSkippingNum=" + totalSkippingNum +
                ", totalDuration='" + totalDuration + '\'' +
                ", totalCalories='" + totalCalories + '\'' +
                ", userId='" + userId + '\'' +
                ", isLocation=" + isLocation +
                '}';
    }
}
