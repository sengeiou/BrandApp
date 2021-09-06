package com.isport.blelibrary.entry;

public class RopeRealDataBean {
    /**
     * FD04 命令解析(实时数据 1次/秒)
     * 0  跳绳类型1byts+
     * 1  跳绳总个数3bytes+
     * 4  心率1bytes+
     * 5  耗时秒3bytes+
     * 8  卡路里4bytes
     * 12 倒计时时、分 2bytes(倒计时跳绳)
     * 12 倒计数       2bytes(倒计数跳绳)
     */
    int ropeType;
    long ropeSumCount;
    int realHr;
    long time;
    long cal;
    int countdownMin;
    int countdownSec;
    int countdown;
    int countSec;


    public int getCountSec() {
        return countSec;
    }

    public void setCountSec(int countSec) {
        this.countSec = countSec;
    }

    public int getRopeType() {
        return ropeType;
    }

    public void setRopeType(int ropeType) {
        this.ropeType = ropeType;
    }

    public long getRopeSumCount() {
        return ropeSumCount;
    }

    public void setRopeSumCount(long ropeSumCount) {
        this.ropeSumCount = ropeSumCount;
    }

    public int getRealHr() {
        return realHr;
    }

    public void setRealHr(int realHr) {
        this.realHr = realHr;
    }

    public long  getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public long getCal() {
        return cal;
    }

    public void setCal(long cal) {
        this.cal = cal;
    }

    public int getCountdownMin() {
        return countdownMin;
    }

    public void setCountdownMin(int countdownMin) {
        this.countdownMin = countdownMin;
    }

    public int getCountdownSec() {
        return countdownSec;
    }

    public void setCountdownSec(int countdownSec) {
        this.countdownSec = countdownSec;
    }

    public int getCountdown() {
        return countdown;
    }

    public void setCountdown(int countdown) {
        this.countdown = countdown;
    }


    @Override
    public String toString() {
        return "RopeRealDataBean{" +
                "ropeType=" + ropeType +
                ", ropeSumCount=" + ropeSumCount +
                ", realHr=" + realHr +
                ", time=" + time +
                ", cal=" + cal +
                ", countdownMin=" + countdownMin +
                ", countdownSec=" + countdownSec +
                ", countdown=" + countdown +
                '}';
    }
}
