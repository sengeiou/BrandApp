package com.isport.brandapp.Home.bean.http;

import java.util.List;

/**
 * @Author
 * @Date 2019/1/18
 * @Fuction
 */

public class ScaleHistoryData {

    private int pageNum;

    private int pageSize;

    private int total;

    private int pages;

    private List<ScaleList> list ;

    private boolean isFirstPage;

    private boolean isLastPage;

    public void setPageNum(int pageNum){
        this.pageNum = pageNum;
    }
    public int getPageNum(){
        return this.pageNum;
    }
    public void setPageSize(int pageSize){
        this.pageSize = pageSize;
    }
    public int getPageSize(){
        return this.pageSize;
    }
    public void setTotal(int total){
        this.total = total;
    }
    public int getTotal(){
        return this.total;
    }
    public void setPages(int pages){
        this.pages = pages;
    }
    public int getPages(){
        return this.pages;
    }
    public void setList(List<ScaleList> list){
        this.list = list;
    }
    public List<ScaleList> getList(){
        return this.list;
    }
    public void setIsFirstPage(boolean isFirstPage){
        this.isFirstPage = isFirstPage;
    }
    public boolean getIsFirstPage(){
        return this.isFirstPage;
    }
    public void setIsLastPage(boolean isLastPage){
        this.isLastPage = isLastPage;
    }
    public boolean getIsLastPage(){
        return this.isLastPage;
    }

    @Override
    public String toString() {
        return "ScaleHistoryData{" +
                "pageNum=" + pageNum +
                ", pageSize=" + pageSize +
                ", total=" + total +
                ", pages=" + pages +
                ", list=" + list +
                ", isFirstPage=" + isFirstPage +
                ", isLastPage=" + isLastPage +
                '}';
    }

    public class ScaleList {
        @Override
        public String toString() {
            return "ScaleList{" +
                    "fatsteelyardTargetId=" + fatsteelyardTargetId +
                    ", deviceId='" + deviceId + '\'' +
                    ", userId=" + userId +
                    ", pk='" + pk + '\'' +
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

        private String fatsteelyardTargetId;

        private String deviceId;

        private String userId;

        private String pk;

        private String dateStr;

        private String timestamp;

        private float bodyWeight;

        private float bfpBodyFatPercent;

        private float slmMuscleWeight;

        private float bmcBoneMineralContent;

        private float bwpBodyWeightPercent;

        private float ppProteinPercent;

        private float smmSkeletalMuscleMass;

        private float vfrVisceralFatRating;

        private float bmi;

        private float sbwStandardBodyWeight;

        private float mcMuscleControl;

        private float wcWeightControl;

        private float fcFatControl;

        private float bmrBasalMetabolicRate;

        private float maBodyAge;

        private float sbcIndividualScore;

        private String createTime;

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

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public String getPk() {
            return pk;
        }

        public void setPk(String pk) {
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

        public float getBodyWeight() {
            return bodyWeight;
        }

        public void setBodyWeight(float bodyWeight) {
            this.bodyWeight = bodyWeight;
        }

        public float getBfpBodyFatPercent() {
            return bfpBodyFatPercent;
        }

        public void setBfpBodyFatPercent(float bfpBodyFatPercent) {
            this.bfpBodyFatPercent = bfpBodyFatPercent;
        }

        public float getSlmMuscleWeight() {
            return slmMuscleWeight;
        }

        public void setSlmMuscleWeight(float slmMuscleWeight) {
            this.slmMuscleWeight = slmMuscleWeight;
        }

        public float getBmcBoneMineralContent() {
            return bmcBoneMineralContent;
        }

        public void setBmcBoneMineralContent(float bmcBoneMineralContent) {
            this.bmcBoneMineralContent = bmcBoneMineralContent;
        }

        public float getBwpBodyWeightPercent() {
            return bwpBodyWeightPercent;
        }

        public void setBwpBodyWeightPercent(float bwpBodyWeightPercent) {
            this.bwpBodyWeightPercent = bwpBodyWeightPercent;
        }

        public float getPpProteinPercent() {
            return ppProteinPercent;
        }

        public void setPpProteinPercent(float ppProteinPercent) {
            this.ppProteinPercent = ppProteinPercent;
        }

        public float getSmmSkeletalMuscleMass() {
            return smmSkeletalMuscleMass;
        }

        public void setSmmSkeletalMuscleMass(float smmSkeletalMuscleMass) {
            this.smmSkeletalMuscleMass = smmSkeletalMuscleMass;
        }

        public float getVfrVisceralFatRating() {
            return vfrVisceralFatRating;
        }

        public void setVfrVisceralFatRating(float vfrVisceralFatRating) {
            this.vfrVisceralFatRating = vfrVisceralFatRating;
        }

        public float getBmi() {
            return bmi;
        }

        public void setBmi(float bmi) {
            this.bmi = bmi;
        }

        public float getSbwStandardBodyWeight() {
            return sbwStandardBodyWeight;
        }

        public void setSbwStandardBodyWeight(float sbwStandardBodyWeight) {
            this.sbwStandardBodyWeight = sbwStandardBodyWeight;
        }

        public float getMcMuscleControl() {
            return mcMuscleControl;
        }

        public void setMcMuscleControl(float mcMuscleControl) {
            this.mcMuscleControl = mcMuscleControl;
        }

        public float getWcWeightControl() {
            return wcWeightControl;
        }

        public void setWcWeightControl(float wcWeightControl) {
            this.wcWeightControl = wcWeightControl;
        }

        public float getFcFatControl() {
            return fcFatControl;
        }

        public void setFcFatControl(float fcFatControl) {
            this.fcFatControl = fcFatControl;
        }

        public float getBmrBasalMetabolicRate() {
            return bmrBasalMetabolicRate;
        }

        public void setBmrBasalMetabolicRate(float bmrBasalMetabolicRate) {
            this.bmrBasalMetabolicRate = bmrBasalMetabolicRate;
        }

        public float getMaBodyAge() {
            return maBodyAge;
        }

        public void setMaBodyAge(float maBodyAge) {
            this.maBodyAge = maBodyAge;
        }

        public float getSbcIndividualScore() {
            return sbcIndividualScore;
        }

        public void setSbcIndividualScore(float sbcIndividualScore) {
            this.sbcIndividualScore = sbcIndividualScore;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }
    }
}
