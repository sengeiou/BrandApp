package com.isport.brandapp.device.sleep.bean;

import java.util.List;

/**
 * @Author
 * @Date 2018/10/29
 * @Fuction
 */

public class SleepHistoryList {

    private String month;

    private List<SleepDayBean> datalist ;

    public void setMonth(String month){
        this.month = month;
    }
    public String getMonth(){
        return this.month;
    }
    public void setDatalist(List<SleepDayBean> datalist){
        this.datalist = datalist;
    }
    public List<SleepDayBean> getDatalist(){
        return this.datalist;
    }
}
