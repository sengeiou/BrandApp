package com.isport.brandapp.Home.fragment;


import com.amap.api.maps.model.LatLng;

public class LatLongData {
    private float  lattitude;//维度
    private float  longitude;//经度
    private LatLng latLng;

    private String speer;//速度

    public LatLongData() {

    }

    public LatLongData(float lattitude, float longitude) {
        this.lattitude = lattitude;
        this.longitude = longitude;
    }

    public LatLng getLatLng() {
        return latLng;
    }

    public void setLatLng(LatLng latLng) {
        this.latLng = latLng;
    }

    public Long getTime() {
        return time;
    }

    public void setTime(Long time) {
        this.time = time;
    }

    private Long time;

    public double getLattitude() {
        return lattitude;
    }

    public void setLattitude(float lattitude) {
        this.lattitude = lattitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(float longitude) {
        this.longitude = longitude;
    }

    public String getSpeer() {
        return speer;
    }

    public void setSpeer(String speer) {
        this.speer = speer;
    }


    @Override
    public String toString() {
        return "[" + longitude + "," + lattitude + "]";
    }
}
