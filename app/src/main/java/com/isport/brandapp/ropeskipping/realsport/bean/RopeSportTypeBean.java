package com.isport.brandapp.ropeskipping.realsport.bean;

public class RopeSportTypeBean {

    String topTitle;
    String topTargetTips;
    boolean isClick;
    boolean isStart;
    String bottomTitle;
    String bottomValue;
    String topValue;
    String topUnit;
    String bottomUnit;
    int currentRopeType;
    int ducation;
    int countdownDucation;
    int currentCount;


    public RopeSportTypeBean() {

    }


    public int getCurrentCount() {
        return currentCount;
    }

    public void setCurrentCount(int currentCount) {
        this.currentCount = currentCount;
    }

    public boolean isStart() {
        return isStart;
    }

    public void setStart(boolean start) {
        isStart = start;
    }

    public RopeSportTypeBean(String topTitle, String topTargetTips, boolean isClick, String bottomTitle, String bottomValue, String topValue, int currentRopeType, String topUnit, String bottomUnit) {

        this.topTitle = topTitle;
        this.topTargetTips = topTargetTips;
        this.isClick = isClick;
        this.bottomTitle = bottomTitle;
        this.bottomValue = bottomValue;
        this.topValue = topValue;
        this.currentRopeType = currentRopeType;
        this.topUnit = topUnit;
        this.bottomUnit = bottomUnit;


    }

    public String getTopUnit() {
        return topUnit;
    }

    public void setTopUnit(String topUnitl) {
        this.topUnit = topUnitl;
    }

    public String getBottomUnit() {
        return bottomUnit;
    }

    public void setBottomUnit(String bottomUnitl) {
        this.bottomUnit = bottomUnitl;
    }

    public String getTopTitle() {
        return topTitle;
    }

    public void setTopTitle(String topTitle) {
        this.topTitle = topTitle;
    }

    public String getTopTargetTips() {
        return topTargetTips;
    }

    public void setTopTargetTips(String topTargetTips) {
        this.topTargetTips = topTargetTips;
    }

    public boolean isClick() {
        return isClick;
    }


    public void setClick(boolean click) {
        isClick = click;
    }

    public String getBottomTitle() {
        return bottomTitle;
    }

    public void setBottomTitle(String bottomTitle) {
        this.bottomTitle = bottomTitle;
    }

    public String getBottomValue() {
        return bottomValue;
    }

    public void setBottomValue(String bottomValue) {
        this.bottomValue = bottomValue;
    }

    public String getTopValue() {
        return topValue;
    }

    public void setTopValue(String topValue) {
        this.topValue = topValue;
    }

    public int getCurrentRopeType() {
        return currentRopeType;
    }

    public void setCurrentRopeType(int currentRopeType) {
        this.currentRopeType = currentRopeType;
    }

    public int getCountdownDucation() {
        return countdownDucation;
    }

    public void setCountdownDucation(int countdownDucation) {
        this.countdownDucation = countdownDucation;
    }

    public int getDucation() {
        return ducation;
    }

    public void setDucation(int ducation) {
        this.ducation = ducation;
    }
}
