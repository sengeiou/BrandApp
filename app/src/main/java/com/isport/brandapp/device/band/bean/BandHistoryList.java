package com.isport.brandapp.device.band.bean;

import java.util.List;

import brandapp.isport.com.basicres.commonbean.BaseBean;


/**
 * @Author
 * @Date 2018/10/29
 * @Fuction
 */

public class BandHistoryList extends BaseBean {


    private List<BandDayBean> list ;

    public List<BandDayBean> getList() {
        return list;
    }

    public void setList(List<BandDayBean> list) {
        this.list = list;
    }
}
