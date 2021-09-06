package com.isport.brandapp.device.sleep.bean;

import android.os.Parcel;

import brandapp.isport.com.basicres.commonbean.BaseBean;

/**
 * @Author
 * @Date 2018/10/29
 * @Fuction
 */

public class UpdateSleepReportBean extends BaseBean {
    private long sleepBeltById;
    private long lastTime;

    @Override
    public String toString() {
        return "UpdateSleepReportBean{" +
                "sleepBeltById=" + sleepBeltById +
                ", lastTime=" + lastTime +
                '}';
    }

    public UpdateSleepReportBean(long sleepBeltById, long lastTime) {
        this.sleepBeltById = sleepBeltById;
        this.lastTime = lastTime;
    }

    public UpdateSleepReportBean(Parcel in, long sleepBeltById, long lastTime) {
        super(in);
        this.sleepBeltById = sleepBeltById;
        this.lastTime = lastTime;
    }

    public long getSleepBeltById() {
        return sleepBeltById;
    }

    public void setSleepBeltById(long sleepBeltById) {
        this.sleepBeltById = sleepBeltById;
    }

    public long getLastTime() {
        return lastTime;
    }

    public void setLastTime(long lastTime) {
        this.lastTime = lastTime;
    }

    @Override

    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeLong(this.sleepBeltById);
        dest.writeLong(this.lastTime);
    }

    public UpdateSleepReportBean() {
    }

    protected UpdateSleepReportBean(Parcel in) {
        super(in);
        this.sleepBeltById = in.readLong();
        this.lastTime = in.readLong();
    }

    public static final Creator<UpdateSleepReportBean> CREATOR = new Creator<UpdateSleepReportBean>() {
        @Override
        public UpdateSleepReportBean createFromParcel(Parcel source) {
            return new UpdateSleepReportBean(source);
        }

        @Override
        public UpdateSleepReportBean[] newArray(int size) {
            return new UpdateSleepReportBean[size];
        }
    };
}
