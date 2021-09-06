package com.isport.brandapp.device.watch.bean;

import java.util.List;


/**
 * @创建者 bear
 * @创建时间 2019/3/12 14:48
 * @描述
 */
public class WatchHistoryNList {

    public void setList(List<WatchHistoryNBean> list) {
        this.list = list;
    }

    private List<WatchHistoryNBean> list;

    public List<WatchHistoryNBean> getList() {
        return this.list;
    }

    @Override
    public String toString() {
        return "WatchHistoryNList{" +
                "list=" + list +
                '}';
    }
}
