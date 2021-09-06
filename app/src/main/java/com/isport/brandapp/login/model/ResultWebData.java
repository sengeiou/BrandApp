package com.isport.brandapp.login.model;

import java.io.Serializable;

public class ResultWebData implements Serializable {

    String type;

    Challeng item;


    /**
     * url:"192.168.10.166:8083/storeDetails",
     * _id:'110',
     * name:'zxy'
     */
    private String url;
    private String orderNo;
    private String totalPrice;
    //url
    //price
    private String productName;

    //map
    private String currentLng;
    private String currentLat;
    private String clubLng;
    private String clubLat;
    private String groupDetail;
    private String destination;
    //俱乐部名字
    private String clubName;

    //在线课程
    private String videoUrl;
    private String courseId;

    public String getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }

    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    public String getClubName() {
        return clubName;
    }

    public void setClubName(String clubName) {
        this.clubName = clubName;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getGroupDetail() {
        return groupDetail;
    }

    public void setGroupDetail(String groupDetail) {
        this.groupDetail = groupDetail;
    }


    public String getClubLng() {
        return clubLng;
    }

    public void setClubLng(String clubLng) {
        this.clubLng = clubLng;
    }

    public String getClubLat() {
        return clubLat;
    }

    public void setClubLat(String clubLat) {
        this.clubLat = clubLat;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getCurrentLng() {
        return currentLng;
    }

    public void setCurrentLng(String currentLng) {
        this.currentLng = currentLng;
    }

    public String getCurrentLat() {
        return currentLat;
    }

    public void setCurrentLat(String currentLat) {
        this.currentLat = currentLat;
    }


    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(String totalPrice) {
        this.totalPrice = totalPrice;
    }


    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Challeng getItem() {
        return item;
    }

    public void setItem(Challeng item) {
        this.item = item;
    }

    @Override
    public String toString() {
        return "ResultWebData{" +
                "type='" + type + '\'' +
                ", item=" + item +
                ", url='" + url + '\'' +
                ", orderNo='" + orderNo + '\'' +
                ", totalPrice='" + totalPrice + '\'' +
                ", productName='" + productName + '\'' +
                ", currentLng='" + currentLng + '\'' +
                ", currentLat='" + currentLat + '\'' +
                ", clubLng='" + clubLng + '\'' +
                ", clubLat='" + clubLat + '\'' +
                ", groupDetail='" + groupDetail + '\'' +
                ", destination='" + destination + '\'' +
                ", clubName='" + clubName + '\'' +
                ", videoUrl='" + videoUrl + '\'' +
                ", courseId='" + courseId + '\'' +
                '}';
    }
}
