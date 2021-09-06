package com.isport.blelibrary.db.table.s002;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class DailyBrief {
    /**
     * "ropeSportDetailId": 140,
     * "startTime": "2020-09-18 11:34:12",
     * "skippingNum": 44,
     * "skippingDuration": 165,
     * "exerciseType": 2,totalSkippingNum
     * "challengeType": 0,
     * "averageFrequency": 8,
     * "totalCalories": 2
     */
    @Id
    private Long id;
    String ropeSportDetailId;
    String startTime;
    String skippingNum;
    int exerciseType;
    int challengeType;
    long skippingDuration;
    String averageFrequency;
    String totalCalories;
    String hhandMin;
    String userId;
    String strDate;
    boolean isLocation;

    public String getStrDate() {
        return this.strDate;
    }

    public void setStrDate(String strDate) {
        this.strDate = strDate;
    }

    public String getUserId() {
        return this.userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getHhandMin() {
        return this.hhandMin;
    }

    public void setHhandMin(String hhandMin) {
        this.hhandMin = hhandMin;
    }

    public String getTotalCalories() {
        return this.totalCalories;
    }

    public void setTotalCalories(String totalCalories) {
        this.totalCalories = totalCalories;
    }

    public String getAverageFrequency() {
        return this.averageFrequency;
    }

    public void setAverageFrequency(String averageFrequency) {
        this.averageFrequency = averageFrequency;
    }

    public long getSkippingDuration() {
        return this.skippingDuration;
    }

    public void setSkippingDuration(long skippingDuration) {
        this.skippingDuration = skippingDuration;
    }

    public int getChallengeType() {
        return this.challengeType;
    }

    public void setChallengeType(int challengeType) {
        this.challengeType = challengeType;
    }

    public int getExerciseType() {
        return this.exerciseType;
    }

    public void setExerciseType(int exerciseType) {
        this.exerciseType = exerciseType;
    }

    public String getSkippingNum() {
        return this.skippingNum;
    }

    public void setSkippingNum(String skippingNum) {
        this.skippingNum = skippingNum;
    }

    public String getStartTime() {
        return this.startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getRopeSportDetailId() {
        return this.ropeSportDetailId;
    }

    public void setRopeSportDetailId(String ropeSportDetailId) {
        this.ropeSportDetailId = ropeSportDetailId;
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

    @Generated(hash = 1888691601)
    public DailyBrief(Long id, String ropeSportDetailId, String startTime,
                      String skippingNum, int exerciseType, int challengeType,
                      long skippingDuration, String averageFrequency, String totalCalories,
                      String hhandMin, String userId, String strDate, boolean isLocation) {
        this.id = id;
        this.ropeSportDetailId = ropeSportDetailId;
        this.startTime = startTime;
        this.skippingNum = skippingNum;
        this.exerciseType = exerciseType;
        this.challengeType = challengeType;
        this.skippingDuration = skippingDuration;
        this.averageFrequency = averageFrequency;
        this.totalCalories = totalCalories;
        this.hhandMin = hhandMin;
        this.userId = userId;
        this.strDate = strDate;
        this.isLocation = isLocation;
    }

    @Generated(hash = 2079952672)
    public DailyBrief() {
    }

    @Override
    public String toString() {
        return "DailyBrief{" +
                "id=" + id +
                ", ropeSportDetailId='" + ropeSportDetailId + '\'' +
                ", startTime='" + startTime + '\'' +
                ", skippingNum='" + skippingNum + '\'' +
                ", exerciseType=" + exerciseType +
                ", challengeType=" + challengeType +
                ", skippingDuration=" + skippingDuration +
                ", averageFrequency='" + averageFrequency + '\'' +
                ", totalCalories='" + totalCalories + '\'' +
                ", hhandMin='" + hhandMin + '\'' +
                ", userId='" + userId + '\'' +
                ", strDate='" + strDate + '\'' +
                ", isLocation=" + isLocation +
                '}';
    }

    public boolean isLocation() {
        return isLocation;
    }

    public void setLocation(boolean location) {
        isLocation = location;
    }
}
