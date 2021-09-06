package com.isport.brandapp.ropeskipping;

public class RopeSkippingBean {
    int valueRes;
    String name;
    int bgRes;
    int ropeSportType;

    public RopeSkippingBean() {

    }

    public RopeSkippingBean(int valueRes, String value, int bgRes,int ropeSportType) {
        this.valueRes = valueRes;
        this.name = value;
        this.bgRes = bgRes;
        this.ropeSportType=ropeSportType;
    }


    public int getRopeSportType() {
        return ropeSportType;
    }

    public void setRopeSportType(int ropeSportType) {
        this.ropeSportType = ropeSportType;
    }

    public int getValueRes() {
        return valueRes;
    }

    public void setValueRes(int valueRes) {
        this.valueRes = valueRes;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getBgRes() {
        return bgRes;
    }

    public void setBgRes(int bgRes) {
        this.bgRes = bgRes;
    }
}
