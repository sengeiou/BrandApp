package com.isport.brandapp.device.share;

import android.os.Parcel;
import android.os.Parcelable;

public class ShareBean implements Parcelable {
    public String time;
    public String centerValue;
    public String one;
    public String two;
    public String three;
    public boolean isWeek;


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.time);
        dest.writeString(this.centerValue);
        dest.writeString(this.one);
        dest.writeString(this.two);
        dest.writeString(this.three);
        dest.writeByte(this.isWeek ? (byte) 1 : (byte) 0);
    }

    public ShareBean() {
    }

    protected ShareBean(Parcel in) {
        this.time = in.readString();
        this.centerValue = in.readString();
        this.one = in.readString();
        this.two = in.readString();
        this.three = in.readString();
        this.isWeek = in.readByte() != 0;
    }

    public static final Creator<ShareBean> CREATOR = new Creator<ShareBean>() {
        @Override
        public ShareBean createFromParcel(Parcel source) {
            return new ShareBean(source);
        }

        @Override
        public ShareBean[] newArray(int size) {
            return new ShareBean[size];
        }
    };
}
