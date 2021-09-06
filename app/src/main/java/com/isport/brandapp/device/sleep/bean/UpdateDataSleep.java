package com.isport.brandapp.device.sleep.bean;

public class UpdateDataSleep {

    private UpdateSleepReportBean bean;
    private SleepUpdateBean sleepUpdateBean;

    public UpdateDataSleep(SleepUpdateBean sleepUpdateBean){
        this.bean=bean;
        this.sleepUpdateBean=sleepUpdateBean;
    }

    public UpdateSleepReportBean getBean() {
        return bean;
    }

    public void setBean(UpdateSleepReportBean bean) {
        this.bean = bean;
    }

    public SleepUpdateBean getSleepUpdateBean() {
        return sleepUpdateBean;
    }

    public void setSleepUpdateBean(SleepUpdateBean sleepUpdateBean) {
        this.sleepUpdateBean = sleepUpdateBean;
    }
}
