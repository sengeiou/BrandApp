package com.isport.brandapp.bind.bean;

import android.os.Parcel;

import brandapp.isport.com.basicres.commonbean.BaseBean;

/**
 * @Author
 * @Date 2018/10/31
 * @Fuction
 */

public class ClockTimeBean extends BaseBean {

    private String clockTime;

    @Override
    public String toString() {
        return "ClockTimeBean{" +
                "clockTime='" + clockTime + '\'' +
                '}';
    }

    public String getClockTime() {
        return clockTime;
    }

    public void setClockTime(String clockTime) {
        this.clockTime = clockTime;
    }

    public ClockTimeBean(String clockTime) {

        this.clockTime = clockTime;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeString(this.clockTime);
    }

    public ClockTimeBean() {
    }

    protected ClockTimeBean(Parcel in) {
        super(in);
        this.clockTime = in.readString();
    }

    public static final Creator<ClockTimeBean> CREATOR = new Creator<ClockTimeBean>() {
        @Override
        public ClockTimeBean createFromParcel(Parcel source) {
            return new ClockTimeBean(source);
        }

        @Override
        public ClockTimeBean[] newArray(int size) {
            return new ClockTimeBean[size];
        }
    };
}
