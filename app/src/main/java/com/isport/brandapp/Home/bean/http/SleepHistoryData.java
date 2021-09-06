package com.isport.brandapp.Home.bean.http;

import java.util.List;

/**
 * @Author
 * @Date 2019/1/19
 * @Fuction
 */

public class SleepHistoryData {

    private int pageNum;
    private int pageSize;
    private int total;
    private int pages;
    private List<SleepList> list;
    private boolean isFirstPage;
    private boolean isLastPage;

    public int getPageNum() {
        return pageNum;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getPages() {
        return pages;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }

    public List<SleepList> getList() {
        return list;
    }

    public void setList(List<SleepList> list) {
        this.list = list;
    }

    public boolean isFirstPage() {
        return isFirstPage;
    }

    public void setFirstPage(boolean firstPage) {
        isFirstPage = firstPage;
    }

    public boolean isLastPage() {
        return isLastPage;
    }

    public void setLastPage(boolean lastPage) {
        isLastPage = lastPage;
    }

    @Override
    public String toString() {
        return "SleepHistoryData{" +
                "pageNum=" + pageNum +
                ", pageSize=" + pageSize +
                ", total=" + total +
                ", pages=" + pages +
                ", list=" + list +
                ", isFirstPage=" + isFirstPage +
                ", isLastPage=" + isLastPage +
                '}';
    }

    public class SleepList {
        @Override
        public String toString() {
            return "SleepList{" +
                    "sleepbeltTargetId=" + sleepbeltTargetId +
                    ", deviceId='" + deviceId + '\'' +
                    ", userId=" + userId +
                    ", pk='" + pk + '\'' +
                    ", dateStr='" + dateStr + '\'' +
                    ", timestamp='" + timestamp + '\'' +
                    ", trunOverStatusAry='" + trunOverStatusAry + '\'' +
                    ", averageHeartBeatRate=" + averageHeartBeatRate +
                    ", averageBreathRate=" + averageBreathRate +
                    ", leaveBedTimes=" + leaveBedTimes +
                    ", turnOverTimes=" + turnOverTimes +
                    ", bodyMovementTimes=" + bodyMovementTimes +
                    ", heartBeatPauseTimes=" + heartBeatPauseTimes +
                    ", breathPauseTimes=" + breathPauseTimes +
                    ", heartBeatPauseAllTime=" + heartBeatPauseAllTime +
                    ", breathPauseAllTime=" + breathPauseAllTime +
                    ", leaveBedAllTime=" + leaveBedAllTime +
                    ", maxHeartBeatRate=" + maxHeartBeatRate +
                    ", minHeartBeatRate=" + minHeartBeatRate +
                    ", maxBreathRate=" + maxBreathRate +
                    ", minBreathRate=" + minBreathRate +
                    ", heartBeatRateFastAllTime=" + heartBeatRateFastAllTime +
                    ", heartBeatRateSlowAllTime=" + heartBeatRateSlowAllTime +
                    ", breathRateFastAllTime=" + breathRateFastAllTime +
                    ", breathRateSlowAllTime=" + breathRateSlowAllTime +
                    ", hardwareVersion='" + hardwareVersion + '\'' +
                    ", startSleepTime='" + startSleepTime + '\'' +
                    ", fallSleepAllTime=" + fallSleepAllTime +
                    ", sleepDuration=" + sleepDuration +
                    ", deepSleepDuration=" + deepSleepDuration +
                    ", deepSleepPercent=" + deepSleepPercent +
                    ", breathRateValueArray='" + breathRateValueArray + '\'' +
                    ", heartBeatRateValueArray='" + heartBeatRateValueArray + '\'' +
                    ", createTime='" + createTime + '\'' +
                    '}';
        }


        private String sleepbeltTargetId;
        private String deviceId;

        private int userId;

        private String pk;

        private String dateStr;

        private String timestamp;

        private String trunOverStatusAry;

        private int averageHeartBeatRate;

        private int averageBreathRate;

        private int leaveBedTimes;

        private int turnOverTimes;

        private int bodyMovementTimes;

        private int heartBeatPauseTimes;

        private int breathPauseTimes;

        private int heartBeatPauseAllTime;

        private int breathPauseAllTime;

        private int leaveBedAllTime;

        private int maxHeartBeatRate;

        private int minHeartBeatRate;

        private int maxBreathRate;

        private int minBreathRate;

        private int heartBeatRateFastAllTime;

        private int heartBeatRateSlowAllTime;

        private int breathRateFastAllTime;

        private int breathRateSlowAllTime;

        private String hardwareVersion;

        private String startSleepTime;

        private int fallSleepAllTime;

        private int sleepDuration;

        private int deepSleepDuration;

        private int deepSleepPercent;

        private String breathRateValueArray;

        private String heartBeatRateValueArray;

        private String createTime;

        public void setSleepbeltTargetId(String  sleepbeltTargetId){
            this.sleepbeltTargetId = sleepbeltTargetId;
        }
        public String getSleepbeltTargetId(){
            return this.sleepbeltTargetId;
        }
        public void setDeviceId(String deviceId){
            this.deviceId = deviceId;
        }
        public String getDeviceId(){
            return this.deviceId;
        }
        public void setUserId(int userId){
            this.userId = userId;
        }
        public int getUserId(){
            return this.userId;
        }
        public void setPk(String pk){
            this.pk = pk;
        }
        public String getPk(){
            return this.pk;
        }
        public void setDateStr(String dateStr){
            this.dateStr = dateStr;
        }
        public String getDateStr(){
            return this.dateStr;
        }
        public void setTimestamp(String timestamp){
            this.timestamp = timestamp;
        }
        public String getTimestamp(){
            return this.timestamp;
        }
        public void setTrunOverStatusAry(String trunOverStatusAry){
            this.trunOverStatusAry = trunOverStatusAry;
        }
        public String getTrunOverStatusAry(){
            return this.trunOverStatusAry;
        }
        public void setAverageHeartBeatRate(int averageHeartBeatRate){
            this.averageHeartBeatRate = averageHeartBeatRate;
        }
        public int getAverageHeartBeatRate(){
            return this.averageHeartBeatRate;
        }
        public void setAverageBreathRate(int averageBreathRate){
            this.averageBreathRate = averageBreathRate;
        }
        public int getAverageBreathRate(){
            return this.averageBreathRate;
        }
        public void setLeaveBedTimes(int leaveBedTimes){
            this.leaveBedTimes = leaveBedTimes;
        }
        public int getLeaveBedTimes(){
            return this.leaveBedTimes;
        }
        public void setTurnOverTimes(int turnOverTimes){
            this.turnOverTimes = turnOverTimes;
        }
        public int getTurnOverTimes(){
            return this.turnOverTimes;
        }
        public void setBodyMovementTimes(int bodyMovementTimes){
            this.bodyMovementTimes = bodyMovementTimes;
        }
        public int getBodyMovementTimes(){
            return this.bodyMovementTimes;
        }
        public void setHeartBeatPauseTimes(int heartBeatPauseTimes){
            this.heartBeatPauseTimes = heartBeatPauseTimes;
        }
        public int getHeartBeatPauseTimes(){
            return this.heartBeatPauseTimes;
        }
        public void setBreathPauseTimes(int breathPauseTimes){
            this.breathPauseTimes = breathPauseTimes;
        }
        public int getBreathPauseTimes(){
            return this.breathPauseTimes;
        }
        public void setHeartBeatPauseAllTime(int heartBeatPauseAllTime){
            this.heartBeatPauseAllTime = heartBeatPauseAllTime;
        }
        public int getHeartBeatPauseAllTime(){
            return this.heartBeatPauseAllTime;
        }
        public void setBreathPauseAllTime(int breathPauseAllTime){
            this.breathPauseAllTime = breathPauseAllTime;
        }
        public int getBreathPauseAllTime(){
            return this.breathPauseAllTime;
        }
        public void setLeaveBedAllTime(int leaveBedAllTime){
            this.leaveBedAllTime = leaveBedAllTime;
        }
        public int getLeaveBedAllTime(){
            return this.leaveBedAllTime;
        }
        public void setMaxHeartBeatRate(int maxHeartBeatRate){
            this.maxHeartBeatRate = maxHeartBeatRate;
        }
        public int getMaxHeartBeatRate(){
            return this.maxHeartBeatRate;
        }
        public void setMinHeartBeatRate(int minHeartBeatRate){
            this.minHeartBeatRate = minHeartBeatRate;
        }
        public int getMinHeartBeatRate(){
            return this.minHeartBeatRate;
        }
        public void setMaxBreathRate(int maxBreathRate){
            this.maxBreathRate = maxBreathRate;
        }
        public int getMaxBreathRate(){
            return this.maxBreathRate;
        }
        public void setMinBreathRate(int minBreathRate){
            this.minBreathRate = minBreathRate;
        }
        public int getMinBreathRate(){
            return this.minBreathRate;
        }
        public void setHeartBeatRateFastAllTime(int heartBeatRateFastAllTime){
            this.heartBeatRateFastAllTime = heartBeatRateFastAllTime;
        }
        public int getHeartBeatRateFastAllTime(){
            return this.heartBeatRateFastAllTime;
        }
        public void setHeartBeatRateSlowAllTime(int heartBeatRateSlowAllTime){
            this.heartBeatRateSlowAllTime = heartBeatRateSlowAllTime;
        }
        public int getHeartBeatRateSlowAllTime(){
            return this.heartBeatRateSlowAllTime;
        }
        public void setBreathRateFastAllTime(int breathRateFastAllTime){
            this.breathRateFastAllTime = breathRateFastAllTime;
        }
        public int getBreathRateFastAllTime(){
            return this.breathRateFastAllTime;
        }
        public void setBreathRateSlowAllTime(int breathRateSlowAllTime){
            this.breathRateSlowAllTime = breathRateSlowAllTime;
        }
        public int getBreathRateSlowAllTime(){
            return this.breathRateSlowAllTime;
        }
        public void setHardwareVersion(String hardwareVersion){
            this.hardwareVersion = hardwareVersion;
        }
        public String getHardwareVersion(){
            return this.hardwareVersion;
        }
        public void setStartSleepTime(String startSleepTime){
            this.startSleepTime = startSleepTime;
        }
        public String getStartSleepTime(){
            return this.startSleepTime;
        }
        public void setFallSleepAllTime(int fallSleepAllTime){
            this.fallSleepAllTime = fallSleepAllTime;
        }
        public int getFallSleepAllTime(){
            return this.fallSleepAllTime;
        }
        public void setSleepDuration(int sleepDuration){
            this.sleepDuration = sleepDuration;
        }
        public int getSleepDuration(){
            return this.sleepDuration;
        }
        public void setDeepSleepDuration(int deepSleepDuration){
            this.deepSleepDuration = deepSleepDuration;
        }
        public int getDeepSleepDuration(){
            return this.deepSleepDuration;
        }
        public void setDeepSleepPercent(int deepSleepPercent){
            this.deepSleepPercent = deepSleepPercent;
        }
        public int getDeepSleepPercent(){
            return this.deepSleepPercent;
        }
        public void setBreathRateValueArray(String breathRateValueArray){
            this.breathRateValueArray = breathRateValueArray;
        }
        public String getBreathRateValueArray(){
            return this.breathRateValueArray;
        }
        public void setHeartBeatRateValueArray(String heartBeatRateValueArray){
            this.heartBeatRateValueArray = heartBeatRateValueArray;
        }
        public String getHeartBeatRateValueArray(){
            return this.heartBeatRateValueArray;
        }
        public void setCreateTime(String createTime){
            this.createTime = createTime;
        }
        public String getCreateTime(){
            return this.createTime;
        }
    }

}
