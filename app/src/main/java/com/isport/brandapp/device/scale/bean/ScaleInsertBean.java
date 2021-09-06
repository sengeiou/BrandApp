package com.isport.brandapp.device.scale.bean;

/**
 * @Author
 * @Date 2019/1/18
 * @Fuction
 */

public class ScaleInsertBean {

    private long timestamp;
    private String dateStr;
    private String deviceId;//本地设备ID
    private String userId;
    private Integer fatsteelyardTargetId;//报告id

    private float bfp_bodyFatPercent;//体脂率
    private float bmc_boneMineralContent;//骨盐量
    private float bmi;//BMI
    private float bmr_basalMetabolicRate;//基础代谢
    private float bodyWeight;//体重
    private float bwp_bodyWeightPercent;//水含量%
    private float fc_fatControl;//脂肪控制
    private float ma_bodyAge;//身体年龄
    private float mc_muscleControl;//肌肉控制
    private float pp_proteinPercent;//蛋白质%
    private float sbc_individualScore;//评分
    private float sbw_standardBodyWeight;//标准体重kg
    private float slm_muscleWeight;//肌肉重kg
    private float smm_skeletalMuscleMass;//骨骼肌kg
    private float vfr_visceralFatRating;//内脏脂肪等级
    private float wc_weightControl;//体重控制

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        return "ScaleInsertBean{" +
                "timestamp=" + timestamp +
                ", dateStr='" + dateStr + '\'' +
                ", deviceId='" + deviceId + '\'' +
                ", userId=" + userId +
                ", fatsteelyardTargetId=" + fatsteelyardTargetId +
                ", bfp_bodyFatPercent=" + bfp_bodyFatPercent +
                ", bmc_boneMineralContent=" + bmc_boneMineralContent +
                ", bmi=" + bmi +
                ", bmr_basalMetabolicRate=" + bmr_basalMetabolicRate +
                ", bodyWeight=" + bodyWeight +
                ", bwp_bodyWeightPercent=" + bwp_bodyWeightPercent +
                ", fc_fatControl=" + fc_fatControl +
                ", ma_bodyAge=" + ma_bodyAge +
                ", mc_muscleControl=" + mc_muscleControl +
                ", pp_proteinPercent=" + pp_proteinPercent +
                ", sbc_individualScore=" + sbc_individualScore +
                ", sbw_standardBodyWeight=" + sbw_standardBodyWeight +
                ", slm_muscleWeight=" + slm_muscleWeight +
                ", smm_skeletalMuscleMass=" + smm_skeletalMuscleMass +
                ", vfr_visceralFatRating=" + vfr_visceralFatRating +
                ", wc_weightControl=" + wc_weightControl +
                '}';
    }

    public String getDateStr() {
        return dateStr;
    }

    public void setDateStr(String dateStr) {
        this.dateStr = dateStr;
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

    public Integer getFatsteelyardTargetId() {
        return fatsteelyardTargetId;
    }

    public void setFatsteelyardTargetId(Integer fatsteelyardTargetId) {
        this.fatsteelyardTargetId = fatsteelyardTargetId;
    }

    public float getBfp_bodyFatPercent() {
        return bfp_bodyFatPercent;
    }

    public void setBfp_bodyFatPercent(float bfp_bodyFatPercent) {
        this.bfp_bodyFatPercent = bfp_bodyFatPercent;
    }

    public float getBmc_boneMineralContent() {
        return bmc_boneMineralContent;
    }

    public void setBmc_boneMineralContent(float bmc_boneMineralContent) {
        this.bmc_boneMineralContent = bmc_boneMineralContent;
    }

    public float getBmi() {
        return bmi;
    }

    public void setBmi(float bmi) {
        this.bmi = bmi;
    }

    public float getBmr_basalMetabolicRate() {
        return bmr_basalMetabolicRate;
    }

    public void setBmr_basalMetabolicRate(float bmr_basalMetabolicRate) {
        this.bmr_basalMetabolicRate = bmr_basalMetabolicRate;
    }

    public float getBodyWeight() {
        return bodyWeight;
    }

    public void setBodyWeight(float bodyWeight) {
        this.bodyWeight = bodyWeight;
    }

    public float getBwp_bodyWeightPercent() {
        return bwp_bodyWeightPercent;
    }

    public void setBwp_bodyWeightPercent(float bwp_bodyWeightPercent) {
        this.bwp_bodyWeightPercent = bwp_bodyWeightPercent;
    }

    public float getFc_fatControl() {
        return fc_fatControl;
    }

    public void setFc_fatControl(float fc_fatControl) {
        this.fc_fatControl = fc_fatControl;
    }

    public float getMa_bodyAge() {
        return ma_bodyAge;
    }

    public void setMa_bodyAge(float ma_bodyAge) {
        this.ma_bodyAge = ma_bodyAge;
    }

    public float getMc_muscleControl() {
        return mc_muscleControl;
    }

    public void setMc_muscleControl(float mc_muscleControl) {
        this.mc_muscleControl = mc_muscleControl;
    }

    public float getPp_proteinPercent() {
        return pp_proteinPercent;
    }

    public void setPp_proteinPercent(float pp_proteinPercent) {
        this.pp_proteinPercent = pp_proteinPercent;
    }

    public float getSbc_individualScore() {
        return sbc_individualScore;
    }

    public void setSbc_individualScore(float sbc_individualScore) {
        this.sbc_individualScore = sbc_individualScore;
    }

    public float getSbw_standardBodyWeight() {
        return sbw_standardBodyWeight;
    }

    public void setSbw_standardBodyWeight(float sbw_standardBodyWeight) {
        this.sbw_standardBodyWeight = sbw_standardBodyWeight;
    }

    public float getSlm_muscleWeight() {
        return slm_muscleWeight;
    }

    public void setSlm_muscleWeight(float slm_muscleWeight) {
        this.slm_muscleWeight = slm_muscleWeight;
    }

    public float getSmm_skeletalMuscleMass() {
        return smm_skeletalMuscleMass;
    }

    public void setSmm_skeletalMuscleMass(float smm_skeletalMuscleMass) {
        this.smm_skeletalMuscleMass = smm_skeletalMuscleMass;
    }

    public float getVfr_visceralFatRating() {
        return vfr_visceralFatRating;
    }

    public void setVfr_visceralFatRating(float vfr_visceralFatRating) {
        this.vfr_visceralFatRating = vfr_visceralFatRating;
    }

    public float getWc_weightControl() {
        return wc_weightControl;
    }

    public void setWc_weightControl(float wc_weightControl) {
        this.wc_weightControl = wc_weightControl;
    }
}
