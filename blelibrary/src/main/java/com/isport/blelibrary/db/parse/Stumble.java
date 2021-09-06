package com.isport.blelibrary.db.parse;

public class Stumble {
    int id;
    int skippingNum;
    int skippingDuration;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSkippingNum() {
        return skippingNum;
    }

    public void setSkippingNum(int skippingNum) {
        this.skippingNum = skippingNum;
    }

    public int getSkippingDuration() {
        return skippingDuration;
    }

    public void setSkippingDuration(int skippingDuration) {
        this.skippingDuration = skippingDuration;
    }

    @Override
    public String toString() {
        return "Stumble{" +
                "id=" + id +
                ", skippingNum=" + skippingNum +
                ", skippingDuration=" + skippingDuration +
                '}';
    }
}
