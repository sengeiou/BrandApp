package com.isport.brandapp.bind.bean;

import android.os.Parcel;

import brandapp.isport.com.basicres.commonbean.BaseBean;


/**
 * @Author
 * @Date 2018/10/17
 * @Fuction
 */

public class BindBean extends BaseBean {
    private int deviceId;
    @Override
    public String toString() {
        return "BindBean{" +
                "deviceId=" + deviceId +
                '}';
    }

    public int getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(int deviceId) {
        this.deviceId = deviceId;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeInt(this.deviceId);
    }

    public BindBean() {
    }

    protected BindBean(Parcel in) {
        super(in);
        this.deviceId = in.readInt();
    }

    public static final Creator<BindBean> CREATOR = new Creator<BindBean>() {
        public BindBean createFromParcel(Parcel source) {
            return new BindBean(source);
        }

        public BindBean[] newArray(int size) {
            return new BindBean[size];
        }
    };
}
