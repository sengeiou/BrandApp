package com.isport.brandapp.bean;

import android.os.Parcel;
import android.os.Parcelable;

public  class HobbyBean  implements Parcelable {
    /**
     * interest : 羽毛球,乒乓球
     * sportPurposes : 塑形
     * sportTimes : 上午
     */

    private String interest;
    private String sportPurposes;
    private String sportTimes;

    public String getInterest() {
        return interest;
    }

    public void setInterest(String interest) {
        this.interest = interest;
    }

    public String getSportPurposes() {
        return sportPurposes;
    }

    public void setSportPurposes(String sportPurposes) {
        this.sportPurposes = sportPurposes;
    }

    public String getSportTimes() {
        return sportTimes;
    }

    public void setSportTimes(String sportTimes) {
        this.sportTimes = sportTimes;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.interest);
        dest.writeString(this.sportPurposes);
        dest.writeString(this.sportTimes);
    }

    public HobbyBean() {
    }

    protected HobbyBean(Parcel in) {
        this.interest = in.readString();
        this.sportPurposes = in.readString();
        this.sportTimes = in.readString();
    }

    public static final Creator<HobbyBean> CREATOR = new Creator<HobbyBean>() {
        @Override
        public HobbyBean createFromParcel(Parcel source) {
            return new HobbyBean(source);
        }

        @Override
        public HobbyBean[] newArray(int size) {
            return new HobbyBean[size];
        }
    };
}