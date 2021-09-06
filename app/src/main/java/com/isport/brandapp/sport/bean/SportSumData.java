package com.isport.brandapp.sport.bean;

import java.io.Serializable;

public class SportSumData implements Serializable{


    String userId;
    int type;
    String avgPace;
    String avgSpeed;
    String calories;
    String distance;
    String avgHeartRate;
    long endTimestamp;
    long startTimestamp;
    String maxHeartRate;
    String maxPace;
    String minHeartRate;
    String minPace;
    String step;
    int timeLong;
    String iphoneSportId;
    int drawableRes;
    String sportTypeName;

    public String getStrTime() {
        return strTime;
    }

    public void setStrTime(String strTime) {
        this.strTime = strTime;
    }

    String strTime;
    String strEndTime;
    String strSpeed;

    public String getStrSpeed() {
        return strSpeed;
    }

    public void setStrSpeed(String strSpeed) {
        this.strSpeed = strSpeed;
    }

    public String getStrEndTime() {
        return strEndTime;
    }

    public void setStrEndTime(String strEndTime) {
        this.strEndTime = strEndTime;
    }

    public String getSportTypeName() {
        return sportTypeName;
    }

    public void setSportTypeName(String sportTypeName) {
        this.sportTypeName = sportTypeName;
    }

    /**
     *
     * @return    "dataUrl": "http://192.168.10.203:8130/isport/concumer-wristband/swagger-ui.html",
    "shareUrl": "http://192.168.10.203:8130/isport/concumer-wristband/swagger-ui.html"
     */
    String dataUrl;
    String shareUrl;

    public String getIphoneSportId() {
        return iphoneSportId;
    }

    public void setIphoneSportId(String iphoneSportId) {
        this.iphoneSportId = iphoneSportId;
    }

    public String getUserId() {
        return userId;
    }

    public String getDataUrl() {
        return dataUrl;
    }

    public void setDataUrl(String dataUrl) {
        this.dataUrl = dataUrl;
    }

    public String getShareUrl() {
        return shareUrl;
    }

    public void setShareUrl(String shareUrl) {
        this.shareUrl = shareUrl;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getAvgPace() {
        return avgPace;
    }

    public void setAvgPace(String avgPace) {
        this.avgPace = avgPace;
    }

    public String getAvgSpeed() {
        return avgSpeed;
    }

    public void setAvgSpeed(String avgSpeed) {
        this.avgSpeed = avgSpeed;
    }

    public String getCalories() {
        return calories;
    }

    public void setCalories(String calories) {
        this.calories = calories;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public long getEndTimestamp() {
        return endTimestamp;
    }

    public void setEndTimestamp(long endTimestamp) {
        this.endTimestamp = endTimestamp;
    }

    public long getStartTimestamp() {
        return startTimestamp;
    }

    public void setStartTimestamp(long startTimestamp) {
        this.startTimestamp = startTimestamp;
    }

    public String getMaxHeartRate() {
        return maxHeartRate;
    }

    public void setMaxHeartRate(String maxHeartRate) {
        this.maxHeartRate = maxHeartRate;
    }

    public String getMaxPace() {
        return maxPace;
    }

    public void setMaxPace(String maxPace) {
        this.maxPace = maxPace;
    }

    public String getMinHeartRate() {
        return minHeartRate;
    }

    public void setMinHeartRate(String minHeartRate) {
        this.minHeartRate = minHeartRate;
    }

    public String getMinPace() {
        return minPace;
    }

    public void setMinPace(String minPace) {
        this.minPace = minPace;
    }

    public String getStep() {
        return step;
    }

    public void setStep(String step) {
        this.step = step;
    }

    public int getTimeLong() {
        return timeLong;
    }

    public void setTimeLong(int timeLong) {
        this.timeLong = timeLong;
    }

    public int getDrawableRes() {
        return drawableRes;
    }

    public void setDrawableRes(int drawableRes) {
        this.drawableRes = drawableRes;
    }

    public String getAvgHeartRate() {
        return avgHeartRate;
    }

    public void setAvgHeartRate(String avgHeartRate) {
        this.avgHeartRate = avgHeartRate;
    }

    /**
     * {
     "avgPace": "string",
     "avgSpeed": 0,
     "calories": 0,
     "distance": 0,
     "endTimestamp": 0,
     "maxHeartRate": 0,
     "maxPace": "string",
     "minHeartRate": 0,
     "minPace": "string",
     "startTimestamp": 0,
     "step": "string",
     "timeLong": 0,
     "type": 0,
     "userId": 0
     }
     */
}
