package com.isport.brandapp.home.bean.http;

import brandapp.isport.com.basicres.commonbean.BaseBean;

/**
 * @Author
 * @Date 2018/10/24
 * @Fuction 主页数据列表
 */

public class DeviceHomeData extends BaseBean {

    private SleepBel sleepBelt;
    private Fatsteelyard fatsteelyard;
    private Wristbandstep wristbandstep;

    public Wristbandstep getWristbandstep() {
        return wristbandstep;
    }

    public void setWristbandstep(Wristbandstep wristbandstep) {
        this.wristbandstep = wristbandstep;
    }

    public void setSleepBel(SleepBel sleepBel) {
        this.sleepBelt = sleepBel;
    }

    public SleepBel getSleepBel() {
        return this.sleepBelt;
    }

    public void setFatsteelyard(Fatsteelyard fatsteelyard) {
        this.fatsteelyard = fatsteelyard;
    }

    public Fatsteelyard getFatsteelyard() {
        return this.fatsteelyard;
    }
}
