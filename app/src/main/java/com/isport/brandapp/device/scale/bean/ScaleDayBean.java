package com.isport.brandapp.device.scale.bean;

public class ScaleDayBean extends BaseDayBean {

    /**
     * "fatSteelyardId": 116,
     * "creatTime": "2018-10-24 15:58:29",
     * "weight": "4121.6",
     * "leftweight": "-0.8",
     * "fatpersent": "48.5",
     * "leftfatpersent": "-2.5"
     */

    public String fatSteelyardId;
    public long creatTime;
    public String weight;
    public String fatpersent;
    public String leftweight;
    public String leftfatpersent;
    public boolean isWeightUp;
    public boolean isBodyRateUp;
    public String strWeight;
    public String strWeightRate;

    public String strBodyPresent;
    public String strBodyRate;

    @Override
    public String toString() {
        return "ScaleDayBean{" +
                "date='" + date + '\'' +
                ", time='" + time + '\'' +
                ", fatSteelyardId='" + fatSteelyardId + '\'' +
                ", creatTime=" + creatTime +
                ", weight='" + weight + '\'' +
                ", fatpersent='" + fatpersent + '\'' +
                ", leftweight='" + leftweight + '\'' +
                ", leftfatpersent='" + leftfatpersent + '\'' +
                ", isWeightUp=" + isWeightUp +
                ", isBodyRateUp=" + isBodyRateUp +
                ", strWeight='" + strWeight + '\'' +
                ", strWeightRate='" + strWeightRate + '\'' +
                ", strBodyPresent='" + strBodyPresent + '\'' +
                ", strBodyRate='" + strBodyRate + '\'' +
                '}';
    }
}
