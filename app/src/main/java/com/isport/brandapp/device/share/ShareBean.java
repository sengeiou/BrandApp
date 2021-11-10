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

    //跳绳平均心率
    private String ropeAvgHeart;

    //挑战排行
    private String challengeRank;

    //挑战关卡描述
    private String challengeDesc;

    public String getChallengeDesc() {
        return challengeDesc;
    }

    public void setChallengeDesc(String challengeDesc) {
        this.challengeDesc = challengeDesc;
    }

    public String getChallengeRank() {
        return challengeRank;
    }

    public void setChallengeRank(String challengeRank) {
        this.challengeRank = challengeRank;
    }


    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getCenterValue() {
        return centerValue;
    }

    public void setCenterValue(String centerValue) {
        this.centerValue = centerValue;
    }

    public String getOne() {
        return one;
    }

    public void setOne(String one) {
        this.one = one;
    }

    public String getTwo() {
        return two;
    }

    public void setTwo(String two) {
        this.two = two;
    }

    public String getThree() {
        return three;
    }

    public void setThree(String three) {
        this.three = three;
    }

    public boolean isWeek() {
        return isWeek;
    }

    public void setWeek(boolean week) {
        isWeek = week;
    }

    public String getRopeAvgHeart() {
        return ropeAvgHeart;
    }

    public void setRopeAvgHeart(String ropeAvgHeart) {
        this.ropeAvgHeart = ropeAvgHeart;
    }

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
        dest.writeString(this.ropeAvgHeart);
        dest.writeString(this.challengeDesc);
        dest.writeString(this.challengeRank);
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
        this.ropeAvgHeart = in.readString();
        this.challengeDesc = in.readString();
        this.challengeRank = in.readString();
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
