package com.isport.blelibrary.result.impl.sleep;

import com.isport.blelibrary.result.IResult;

import java.io.Serializable;

/**
 * @Author
 * @Date 2018/10/10
 * @Fuction
 */

public class SleepRealTimeDataResult implements IResult,Serializable {
    @Override
    public String getType() {
        return SLEEP_REALTIME;
    }

    private short heartRate;
    private short breathRate;
    private byte status;
    private int statusValue;
    private int sleepFlag;
    private int wakeFlag;

    @Override
    public String toString() {
        return "SleepRealTimeDataResult{" +
                "heartRate=" + heartRate +
                ", breathRate=" + breathRate +
                ", status=" + status +
                ", statusValue=" + statusValue +
                ", sleepFlag=" + sleepFlag +
                ", wakeFlag=" + wakeFlag +
                '}';
    }

    public short getHeartRate() {
        return heartRate;
    }

    public void setHeartRate(short heartRate) {
        this.heartRate = heartRate;
    }

    public short getBreathRate() {
        return breathRate;
    }

    public void setBreathRate(short breathRate) {
        this.breathRate = breathRate;
    }

    public byte getStatus() {
        return status;
    }

    public void setStatus(byte status) {
        this.status = status;
    }

    public int getStatusValue() {
        return statusValue;
    }

    public void setStatusValue(int statusValue) {
        this.statusValue = statusValue;
    }

    public int getSleepFlag() {
        return sleepFlag;
    }

    public void setSleepFlag(int sleepFlag) {
        this.sleepFlag = sleepFlag;
    }

    public int getWakeFlag() {
        return wakeFlag;
    }

    public void setWakeFlag(int wakeFlag) {
        this.wakeFlag = wakeFlag;
    }

    public SleepRealTimeDataResult(short heartRate, short breathRate, byte status, int statusValue, int sleepFlag,
                                   int wakeFlag) {

        this.heartRate = heartRate;
        this.breathRate = breathRate;
        this.status = status;
        this.statusValue = statusValue;
        this.sleepFlag = sleepFlag;
        this.wakeFlag = wakeFlag;
    }
}
