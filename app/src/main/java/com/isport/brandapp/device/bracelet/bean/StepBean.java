package com.isport.brandapp.device.bracelet.bean;

public class StepBean {
    long step;
    long time;
    String strDate;

    public StepBean(){

    }

    public StepBean(long step,long time,String strDate){
        this.step=step;
        this.time=time;
        this.strDate=strDate;
    }
    public long getStep() {
        return step;
    }

    public void setStep(long step) {
        this.step = step;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public String getStrDate() {
        return strDate;
    }

    public void setStrDate(String strDate) {
        this.strDate = strDate;
    }
}
