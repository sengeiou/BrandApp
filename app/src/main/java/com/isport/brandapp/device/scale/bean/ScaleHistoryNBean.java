package com.isport.brandapp.device.scale.bean;

import java.io.Serializable;

/**
 * @Author
 * @Date 2019/1/25
 * @Fuction
 */

public class ScaleHistoryNBean implements Serializable {
    @Override
    public String toString() {
        return "ScaleHistoryNBean{" +
                "fatsteelyardTargetId=" + fatsteelyardTargetId +
                ", deviceId='" + deviceId + '\'' +
                ", userId=" + userId +
                ", pk=" + pk +
                ", dateStr='" + dateStr + '\'' +
                ", timestamp='" + timestamp + '\'' +
                ", bodyWeight=" + bodyWeight +
                ", bfpBodyFatPercent=" + bfpBodyFatPercent +
                ", slmMuscleWeight=" + slmMuscleWeight +
                ", bmcBoneMineralContent=" + bmcBoneMineralContent +
                ", bwpBodyWeightPercent=" + bwpBodyWeightPercent +
                ", ppProteinPercent=" + ppProteinPercent +
                ", smmSkeletalMuscleMass=" + smmSkeletalMuscleMass +
                ", vfrVisceralFatRating=" + vfrVisceralFatRating +
                ", bmi=" + bmi +
                ", sbwStandardBodyWeight=" + sbwStandardBodyWeight +
                ", mcMuscleControl=" + mcMuscleControl +
                ", wcWeightControl=" + wcWeightControl +
                ", fcFatControl=" + fcFatControl +
                ", bmrBasalMetabolicRate=" + bmrBasalMetabolicRate +
                ", maBodyAge=" + maBodyAge +
                ", sbcIndividualScore=" + sbcIndividualScore +
                ", createTime='" + createTime + '\'' +
                '}';
    }

    public String getFatsteelyardTargetId() {
        return fatsteelyardTargetId;
    }

    public void setFatsteelyardTargetId(String fatsteelyardTargetId) {
        this.fatsteelyardTargetId = fatsteelyardTargetId;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getPk() {
        return pk;
    }

    public void setPk(int pk) {
        this.pk = pk;
    }

    public String getDateStr() {
        return dateStr;
    }

    public void setDateStr(String dateStr) {
        this.dateStr = dateStr;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public double getBodyWeight() {
        return bodyWeight;
    }

    public void setBodyWeight(double bodyWeight) {
        this.bodyWeight = bodyWeight;
    }

    public double getBfpBodyFatPercent() {
        return bfpBodyFatPercent;
    }

    public void setBfpBodyFatPercent(double bfpBodyFatPercent) {
        this.bfpBodyFatPercent = bfpBodyFatPercent;
    }

    public double getSlmMuscleWeight() {
        return slmMuscleWeight;
    }

    public void setSlmMuscleWeight(double slmMuscleWeight) {
        this.slmMuscleWeight = slmMuscleWeight;
    }

    public double getBmcBoneMineralContent() {
        return bmcBoneMineralContent;
    }

    public void setBmcBoneMineralContent(double bmcBoneMineralContent) {
        this.bmcBoneMineralContent = bmcBoneMineralContent;
    }

    public double getBwpBodyWeightPercent() {
        return bwpBodyWeightPercent;
    }

    public void setBwpBodyWeightPercent(double bwpBodyWeightPercent) {
        this.bwpBodyWeightPercent = bwpBodyWeightPercent;
    }

    public double getPpProteinPercent() {
        return ppProteinPercent;
    }

    public void setPpProteinPercent(double ppProteinPercent) {
        this.ppProteinPercent = ppProteinPercent;
    }

    public double getSmmSkeletalMuscleMass() {
        return smmSkeletalMuscleMass;
    }

    public void setSmmSkeletalMuscleMass(double smmSkeletalMuscleMass) {
        this.smmSkeletalMuscleMass = smmSkeletalMuscleMass;
    }

    public double getVfrVisceralFatRating() {
        return vfrVisceralFatRating;
    }

    public void setVfrVisceralFatRating(double vfrVisceralFatRating) {
        this.vfrVisceralFatRating = vfrVisceralFatRating;
    }

    public double getBmi() {
        return bmi;
    }

    public void setBmi(double bmi) {
        this.bmi = bmi;
    }

    public double getSbwStandardBodyWeight() {
        return sbwStandardBodyWeight;
    }

    public void setSbwStandardBodyWeight(double sbwStandardBodyWeight) {
        this.sbwStandardBodyWeight = sbwStandardBodyWeight;
    }

    public double getMcMuscleControl() {
        return mcMuscleControl;
    }

    public void setMcMuscleControl(double mcMuscleControl) {
        this.mcMuscleControl = mcMuscleControl;
    }

    public double getWcWeightControl() {
        return wcWeightControl;
    }

    public void setWcWeightControl(double wcWeightControl) {
        this.wcWeightControl = wcWeightControl;
    }

    public double getFcFatControl() {
        return fcFatControl;
    }

    public void setFcFatControl(double fcFatControl) {
        this.fcFatControl = fcFatControl;
    }

    public double getBmrBasalMetabolicRate() {
        return bmrBasalMetabolicRate;
    }

    public void setBmrBasalMetabolicRate(double bmrBasalMetabolicRate) {
        this.bmrBasalMetabolicRate = bmrBasalMetabolicRate;
    }

    public double getMaBodyAge() {
        return maBodyAge;
    }

    public void setMaBodyAge(double maBodyAge) {
        this.maBodyAge = maBodyAge;
    }

    public double getSbcIndividualScore() {
        return sbcIndividualScore;
    }

    public void setSbcIndividualScore(double sbcIndividualScore) {
        this.sbcIndividualScore = sbcIndividualScore;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }


    private String fatsteelyardTargetId;
    private String deviceId;

            private int userId;

            private int pk;

            private String dateStr;

            private String timestamp;

            private double bodyWeight;

            private double bfpBodyFatPercent;

            private double slmMuscleWeight;

            private double bmcBoneMineralContent;

            private double bwpBodyWeightPercent;

            private double ppProteinPercent;

            private double smmSkeletalMuscleMass;

            private double vfrVisceralFatRating;

            private double bmi;

            private double sbwStandardBodyWeight;

            private double mcMuscleControl;

            private double wcWeightControl;

            private double fcFatControl;

            private double bmrBasalMetabolicRate;

            private double maBodyAge;

            private double sbcIndividualScore;

            private String createTime;

    }
