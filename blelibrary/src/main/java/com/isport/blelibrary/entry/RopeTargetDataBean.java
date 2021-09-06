package com.isport.blelibrary.entry;

public class RopeTargetDataBean {
    /**
     * FD02
     * 06 FF 28 01 01 00 64 00
     * 0  跳绳类型1byts
     * 1  01
     * 2  00
     * 3  64
     */
    int ropeType;
    int targetMin;
    int targetSec;
    int targetCount;


    public int getRopeType() {
        return ropeType;
    }

    public void setRopeType(int ropeType) {
        this.ropeType = ropeType;
    }

    public int getTargetMin() {
        return targetMin;
    }

    public void setTargetMin(int targetMin) {
        this.targetMin = targetMin;
    }

    public int getTargetSec() {
        return targetSec;
    }

    public void setTargetSec(int targetSec) {
        this.targetSec = targetSec;
    }

    public int getTargetCount() {
        return targetCount;
    }

    public void setTargetCount(int targetCount) {
        this.targetCount = targetCount;
    }

    @Override
    public String toString() {
        return "RopeTargetDataBean{" +
                "ropeType=" + ropeType +
                ", targetMin=" + targetMin +
                ", targetSec=" + targetSec +
                ", targetCount=" + targetCount +
                '}';
    }
}
