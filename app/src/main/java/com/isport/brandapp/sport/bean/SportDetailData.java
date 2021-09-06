package com.isport.brandapp.sport.bean;

public class SportDetailData {

    /**
     * {
     * "iphoneSportId" : 136,
     * "paceArr" : "33",
     * "heartRateArr" : "333"
     * "coorArr":""
     * }
     */

    Long id;//数据库的id
    String iphoneSportId;
    String paceArr;
    String heartRateArr;
    String coorArr;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIphoneSportId() {
        return iphoneSportId;
    }

    public void setIphoneSportId(String iphoneSportId) {
        this.iphoneSportId = iphoneSportId;
    }

    public String getPaceArr() {
        return paceArr;
    }

    public void setPaceArr(String paceArr) {
        this.paceArr = paceArr;
    }

    public String getHeartRateArr() {
        return heartRateArr;
    }

    public void setHeartRateArr(String heartRateArr) {
        this.heartRateArr = heartRateArr;
    }

    public String getCoorArr() {
        return coorArr;
    }

    public void setCoorArr(String coorArr) {
        this.coorArr = coorArr;
    }
}
