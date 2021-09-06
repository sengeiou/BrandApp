package com.isport.brandapp.device.sleep.bean;

import java.util.List;

/**
 * @Author
 * @Date 2019/1/26
 * @Fuction
 */

public class SleepHistoryNList {

    private List<SleepHistoryNBean> list ;

    public void setList(List<SleepHistoryNBean> list){
        this.list = list;
    }
    public List<SleepHistoryNBean> getList(){
        return this.list;
    }

    @Override
    public String toString() {
        return "SleepHistoryNList{" +
                "list=" + list +
                '}';
    }
}
