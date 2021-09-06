package com.isport.brandapp.sport.location.bean;

/**
 * 功能:位置 对象
 */
public class SBLocation {
    private long mTime = 0;
    private double mLatitude = 0.0;
    private double mLongitude = 0.0;
    private double mAltitude = 0.0;
    private float mAccuracy = 0.0f;
    private float mSpeed = 0.0f;
    private String county;
    private int GpsAccuracyStatus;
    private Object location;

    public SBLocation() {
    }


    public SBLocation(Object location, long mTime, double mLatitude, double mLongitude, double mAltitude, float mAccuracy, float mSpeed) {
        this.location = location;
        this.mTime = mTime;
        this.mLatitude = mLatitude;
        this.mLongitude = mLongitude;
        this.mAccuracy = mAccuracy;
        this.mSpeed = mSpeed;
    }

    public int getGpsAccuracyStatus() {
        return GpsAccuracyStatus;
    }

    public void setGpsAccuracyStatus(int gpsAccuracyStatus) {
        GpsAccuracyStatus = gpsAccuracyStatus;
    }

    public SBLocation(Object location, long mTime, double mLatitude, double mLongitude, double mAltitude, float mAccuracy, float mSpeed, int GpsAccuracyStatus, String county) {
        this.location = location;
        this.mTime = mTime;
        this.mLatitude = mLatitude;
        this.mLongitude = mLongitude;
        this.mAccuracy = mAccuracy;
        this.mSpeed = mSpeed;
        this.GpsAccuracyStatus = GpsAccuracyStatus;
        this.county = county;
    }

    public SBLocation(long mTime, double mLatitude, double mLongitude, float mAccuracy, float mSpeed) {
        this.mTime = mTime;
        this.mLatitude = mLatitude;
        this.mLongitude = mLongitude;
        this.mAccuracy = mAccuracy;
        this.mSpeed = mSpeed;
    }

    public SBLocation(double mLatitude, double mLongitude) {
        this.mLatitude = mLatitude;
        this.mLongitude = mLongitude;
    }

    public String getCounty() {
        return county;
    }

    public void setCounty(String county) {
        this.county = county;
    }

    public double getAltitude() {
        return mAltitude;
    }

    public void setAltitude(double mAltitude) {
        this.mAltitude = mAltitude;
    }

    public <T> T getLocation() {
        return (T) location;
    }

    public long getTime() {
        return mTime;
    }

    public void setTime(long mTime) {
        this.mTime = mTime;
    }

    public double getLatitude() {
        return mLatitude;
    }

    public void setLatitude(double mLatitude) {
        this.mLatitude = mLatitude;
    }

    public double getLongitude() {
        return mLongitude;
    }

    public void setLongitude(double mLongitude) {
        this.mLongitude = mLongitude;
    }

    public float getAccuracy() {
        return mAccuracy;
    }

    public void setAccuracy(float mAccuracy) {
        this.mAccuracy = mAccuracy;
    }

    public float getSpeed() {
        return mSpeed;
    }

    public void setSpeed(float mSpeed) {
        this.mSpeed = mSpeed;
    }

    @Override
    public String toString() {
        return "SBLocation{" +
                "mTime=" + mTime +
                ", mLatitude=" + mLatitude +
                ", mLongitude=" + mLongitude +
                ", mAltitude=" + mAltitude +
                ", mAccuracy=" + mAccuracy +
                ", mSpeed=" + mSpeed +
                ", location=" + location +
                '}';
    }
}
