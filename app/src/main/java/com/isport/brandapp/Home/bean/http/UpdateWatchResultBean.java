package com.isport.brandapp.Home.bean.http;

import android.os.Parcel;

import brandapp.isport.com.basicres.commonbean.BaseBean;

/**
 * @Author
 * @Date 2018/11/6
 * @Fuction
 */

public class UpdateWatchResultBean extends BaseBean {
    private int wristbandStepId;

    private long lastAppTime;

    private long lastServerTime;

    public void setWristbandStepId(int wristbandStepId) {
        this.wristbandStepId = wristbandStepId;
    }

    public int getWristbandStepId() {
        return this.wristbandStepId;
    }

    public void setLastAppTime(long lastAppTime) {
        this.lastAppTime = lastAppTime;
    }

    public long getLastAppTime() {
        return this.lastAppTime;
    }

    public void setLastServerTime(long lastServerTime) {
        this.lastServerTime = lastServerTime;
    }

    public long getLastServerTime() {
        return this.lastServerTime;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeInt(this.wristbandStepId);
        dest.writeLong(this.lastAppTime);
        dest.writeLong(this.lastServerTime);
    }

    public UpdateWatchResultBean() {
    }

    protected UpdateWatchResultBean(Parcel in) {
        super(in);
        this.wristbandStepId = in.readInt();
        this.lastAppTime = in.readLong();
        this.lastServerTime = in.readLong();
    }

    public static final Creator<UpdateWatchResultBean> CREATOR = new Creator<UpdateWatchResultBean>() {
        @Override
        public UpdateWatchResultBean createFromParcel(Parcel source) {
            return new UpdateWatchResultBean(source);
        }

        @Override
        public UpdateWatchResultBean[] newArray(int size) {
            return new UpdateWatchResultBean[size];
        }
    };
}
