package com.isport.brandapp.wu.bean;

import android.os.Parcel;
import android.os.Parcelable;

public class ExerciseInfo implements Parcelable {
    /**
     * wristbandTrainingId : 550
     * deviceId : W814-C7AFF1103604
     * userId : 122
     * exerciseType : 2
     * dateStr : 2019-11-20
     * createTime : 2019-11-20T08:08:09.612+0000
     * extend1 : null
     * extend2 : null
     * extend3 : null
     * aveRate :
     * startTimestamp : 1574233416000
     * endTimestamp : 1574237286000
     * vaildTimeLength : 65
     * totalSteps : 402
     * totalDistance : 333
     * totalCalories : 36
     * heartRateDetailArray :
     */

    private String wristbandTrainingId;
    private String deviceId;
    private String userId;
    private String exerciseType;
    private String dateStr;
    private String createTime;
    private String extend1;
    private String extend2;
    private String extend3;
    private String aveRate;
    private long startTimestamp;
    private long endTimestamp;
    private String vaildTimeLength;
    private String totalSteps;
    private String totalDistance;
    private String totalCalories;
    private String heartRateDetailArray;

    //W560详细心率
    private String heartDetailArray;
    private String stepDetailArray;
    private String distanceDetailArray;
    private String caloriesDetailArray;


    public String getHeartDetailArray() {
        return heartDetailArray;
    }

    public void setHeartDetailArray(String heartDetailArray) {
        this.heartDetailArray = heartDetailArray;
    }

    public String getStepDetailArray() {
        return stepDetailArray;
    }

    public void setStepDetailArray(String stepDetailArray) {
        this.stepDetailArray = stepDetailArray;
    }

    public String getDistanceDetailArray() {
        return distanceDetailArray;
    }

    public void setDistanceDetailArray(String distanceDetailArray) {
        this.distanceDetailArray = distanceDetailArray;
    }

    public String getCaloriesDetailArray() {
        return caloriesDetailArray;
    }

    public void setCaloriesDetailArray(String caloriesDetailArray) {
        this.caloriesDetailArray = caloriesDetailArray;
    }

    public String getWristbandTrainingId() {
        return wristbandTrainingId;
    }

    public void setWristbandTrainingId(String wristbandTrainingId) {
        this.wristbandTrainingId = wristbandTrainingId;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getExerciseType() {
        return exerciseType;
    }

    public void setExerciseType(String exerciseType) {
        this.exerciseType = exerciseType;
    }

    public String getDateStr() {
        return dateStr;
    }

    public void setDateStr(String dateStr) {
        this.dateStr = dateStr;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public Object getExtend1() {
        return extend1;
    }

    public void setExtend1(String extend1) {
        this.extend1 = extend1;
    }

    public Object getExtend2() {
        return extend2;
    }

    public void setExtend2(String extend2) {
        this.extend2 = extend2;
    }

    public Object getExtend3() {
        return extend3;
    }

    public void setExtend3(String extend3) {
        this.extend3 = extend3;
    }

    public String getAveRate() {
        return aveRate;
    }

    public void setAveRate(String aveRate) {
        this.aveRate = aveRate;
    }

    public long getStartTimestamp() {
        return startTimestamp;
    }

    public void setStartTimestamp(long startTimestamp) {
        this.startTimestamp = startTimestamp;
    }

    public long getEndTimestamp() {
        return endTimestamp;
    }

    public void setEndTimestamp(long endTimestamp) {
        this.endTimestamp = endTimestamp;
    }

    public String getVaildTimeLength() {
        return vaildTimeLength;
    }

    public void setVaildTimeLength(String vaildTimeLength) {
        this.vaildTimeLength = vaildTimeLength;
    }

    public String getTotalSteps() {
        return totalSteps;
    }

    public void setTotalSteps(String totalSteps) {
        this.totalSteps = totalSteps;
    }

    public String getTotalDistance() {
        return totalDistance;
    }

    public void setTotalDistance(String totalDistance) {
        this.totalDistance = totalDistance;
    }

    public String getTotalCalories() {
        return totalCalories;
    }

    public void setTotalCalories(String totalCalories) {
        this.totalCalories = totalCalories;
    }

    public String getHeartRateDetailArray() {
        return heartRateDetailArray;
    }

    public void setHeartRateDetailArray(String heartRateDetailArray) {
        this.heartRateDetailArray = heartRateDetailArray;
    }

    @Override
    public String toString() {
        return "ExerciseInfo{" +
                "wristbandTrainingId='" + wristbandTrainingId + '\'' +
                ", deviceId='" + deviceId + '\'' +
                ", userId='" + userId + '\'' +
                ", exerciseType='" + exerciseType + '\'' +
                ", dateStr='" + dateStr + '\'' +
                ", createTime='" + createTime + '\'' +
                ", extend1='" + extend1 + '\'' +
                ", extend2='" + extend2 + '\'' +
                ", extend3='" + extend3 + '\'' +
                ", aveRate='" + aveRate + '\'' +
                ", startTimestamp=" + startTimestamp +
                ", endTimestamp=" + endTimestamp +
                ", vaildTimeLength='" + vaildTimeLength + '\'' +
                ", totalSteps='" + totalSteps + '\'' +
                ", totalDistance='" + totalDistance + '\'' +
                ", totalCalories='" + totalCalories + '\'' +
                ", heartRateDetailArray='" + heartRateDetailArray + '\'' +
                ", heartDetailArray='" + heartDetailArray + '\'' +
                ", stepDetailArray='" + stepDetailArray + '\'' +
                ", distanceDetailArray='" + distanceDetailArray + '\'' +
                ", caloriesDetailArray='" + caloriesDetailArray + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.wristbandTrainingId);
        dest.writeString(this.deviceId);
        dest.writeString(this.userId);
        dest.writeString(this.exerciseType);
        dest.writeString(this.dateStr);
        dest.writeString(this.createTime);
        dest.writeString(this.extend1);
        dest.writeString(this.extend2);
        dest.writeString(this.extend3);
        dest.writeString(this.aveRate);
        dest.writeLong(this.startTimestamp);
        dest.writeLong(this.endTimestamp);
        dest.writeString(this.vaildTimeLength);
        dest.writeString(this.totalSteps);
        dest.writeString(this.totalDistance);
        dest.writeString(this.totalCalories);
        dest.writeString(this.heartRateDetailArray);

        dest.writeString(this.heartDetailArray);
        dest.writeString(this.distanceDetailArray);
        dest.writeString(this.caloriesDetailArray);
        dest.writeString(this.stepDetailArray);
    }

    public ExerciseInfo() {
    }

    protected ExerciseInfo(Parcel in) {
        this.wristbandTrainingId = in.readString();
        this.deviceId = in.readString();
        this.userId = in.readString();
        this.exerciseType = in.readString();
        this.dateStr = in.readString();
        this.createTime = in.readString();
        this.extend1 = in.readString();
        this.extend2 = in.readString();
        this.extend3 = in.readString();
        this.aveRate = in.readString();
        this.startTimestamp = in.readLong();
        this.endTimestamp = in.readLong();
        this.vaildTimeLength = in.readString();
        this.totalSteps = in.readString();
        this.totalDistance = in.readString();
        this.totalCalories = in.readString();
        this.heartRateDetailArray = in.readString();

        this.heartDetailArray = in.readString();
        this.stepDetailArray = in.readString();
        this.distanceDetailArray = in.readString();
        this.caloriesDetailArray = in.readString();
    }

    public static final Parcelable.Creator<ExerciseInfo> CREATOR = new Parcelable.Creator<ExerciseInfo>() {
        @Override
        public ExerciseInfo createFromParcel(Parcel source) {
            return new ExerciseInfo(source);
        }

        @Override
        public ExerciseInfo[] newArray(int size) {
            return new ExerciseInfo[size];
        }
    };
}
