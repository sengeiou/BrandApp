package com.isport.brandapp.device.bracelet.bean;

public class StateBean {

    public boolean isHrState;
    public boolean isCall;
    public boolean isMessage;
    public String tempUnitl;

    @Override
    public String toString() {
        return "StateBean{" +
                "isHrState=" + isHrState +
                ", isCall=" + isCall +
                ", isMessage=" + isMessage +
                ", isTempUnitl=" + tempUnitl +
                '}';
    }
}
