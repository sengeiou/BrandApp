package com.isport.brandapp.Home.bean.http;

import com.isport.brandapp.device.watch.bean.WatchHistoryNBean;

import java.util.List;

/**
 * @创建者 bear
 * @创建时间 2019/3/13 15:15
 * @描述
 */
public class WatchHistoryData {

    private int pageNum;

    private int pageSize;

    private int total;

    private int pages;

    private List<WatchHistoryNBean> list ;

    private boolean isFirstPage;

    private boolean isLastPage;

    public void setPageNum(int pageNum){
        this.pageNum = pageNum;
    }
    public int getPageNum(){
        return this.pageNum;
    }
    public void setPageSize(int pageSize){
        this.pageSize = pageSize;
    }
    public int getPageSize(){
        return this.pageSize;
    }
    public void setTotal(int total){
        this.total = total;
    }
    public int getTotal(){
        return this.total;
    }
    public void setPages(int pages){
        this.pages = pages;
    }
    public int getPages(){
        return this.pages;
    }
    public void setList(List<WatchHistoryNBean> list){
        this.list = list;
    }
    public List<WatchHistoryNBean> getList(){
        return this.list;
    }
    public void setIsFirstPage(boolean isFirstPage){
        this.isFirstPage = isFirstPage;
    }
    public boolean getIsFirstPage(){
        return this.isFirstPage;
    }
    public void setIsLastPage(boolean isLastPage){
        this.isLastPage = isLastPage;
    }
    public boolean getIsLastPage(){
        return this.isLastPage;
    }

    @Override
    public String toString() {
        return "WatchHistoryData{" +
                "pageNum=" + pageNum +
                ", pageSize=" + pageSize +
                ", total=" + total +
                ", pages=" + pages +
                ", list=" + list +
                ", isFirstPage=" + isFirstPage +
                ", isLastPage=" + isLastPage +
                '}';
    }
}
