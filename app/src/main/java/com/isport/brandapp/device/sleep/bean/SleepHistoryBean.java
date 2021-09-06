package com.isport.brandapp.device.sleep.bean;

import android.os.Parcel;

import java.util.ArrayList;
import java.util.List;

import brandapp.isport.com.basicres.commonbean.BaseBean;


public class SleepHistoryBean extends BaseBean {

    public List<SleepHistoryList> list;

    public void setList(List<SleepHistoryList> list) {
        this.list = list;
    }

    public List<SleepHistoryList> getList() {
        return this.list;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeList(this.list);
    }

    public SleepHistoryBean() {
    }

    protected SleepHistoryBean(Parcel in) {
        super(in);
        this.list = new ArrayList<SleepHistoryList>();
        in.readList(this.list, SleepHistoryList.class.getClassLoader());
    }

    public static final Creator<SleepHistoryBean> CREATOR = new Creator<SleepHistoryBean>() {
        @Override
        public SleepHistoryBean createFromParcel(Parcel source) {
            return new SleepHistoryBean(source);
        }

        @Override
        public SleepHistoryBean[] newArray(int size) {
            return new SleepHistoryBean[size];
        }
    };
}
