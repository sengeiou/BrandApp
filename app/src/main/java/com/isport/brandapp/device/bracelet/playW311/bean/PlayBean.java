package com.isport.brandapp.device.bracelet.playW311.bean;

public class PlayBean {
    /**
     * id : 24
     * deviceType : 812
     * fileName : w812-1.gif
     * title : 滑动屏幕切换功能
     * titleEn : Slide screen switching function
     * content : 上下滑动屏幕切换步数、心率、睡眠等功能
     * contentEn : Swipe the screen up or down to switch steps, heart rate, sleep, etc.
     * url : https://manager.fitalent.com.cn/static/2019/11/2/11-21-35-301781.gif
     * seq : 10
     * createTime : 2019-12-01T19:21:34.000+0000
     * extend1 : null
     * extend2 : null
     * extend3 : null
     */

    private String id;
    private int deviceType;
    private String fileName;
    private String title1;
    private String titleEn1;
    private String title1Content1;
    private String titleEn1Content1;
    private String url1;
    private String createTime;
    private String title1Content2;
    private String titleEn1Content2;
    private String urlEn1;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(int deviceType) {
        this.deviceType = deviceType;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getTitle1() {
        return title1;
    }

    public void setTitle1(String title1) {
        this.title1 = title1;
    }

    public String getTitleEn1() {
        return titleEn1;
    }

    public void setTitleEn1(String titleEn1) {
        this.titleEn1 = titleEn1;
    }

    public String getTitle1Content1() {
        return title1Content1;
    }

    public void setTitle1Content1(String title1Content1) {
        this.title1Content1 = title1Content1;
    }

    public String getTitleEn1Content1() {
        return titleEn1Content1;
    }

    public void setTitleEn1Content1(String titleEn1Content1) {
        this.titleEn1Content1 = titleEn1Content1;
    }

    public String getUrl1() {
        return url1;
    }

    public void setUrl1(String url1) {
        this.url1 = url1;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getTitle1Content2() {
        return title1Content2;
    }

    public void setTitle1Content2(String title1Content2) {
        this.title1Content2 = title1Content2;
    }

    public String getTitleEn1Content2() {
        return titleEn1Content2;
    }

    public void setTitleEn1Content2(String titleEn1Content2) {
        this.titleEn1Content2 = titleEn1Content2;
    }

    public String getUrlEn1() {
        return urlEn1;
    }

    public void setUrlEn1(String urlEn1) {
        this.urlEn1 = urlEn1;
    }

    @Override
    public String toString() {
        return "PlayBean{" +
                "id='" + id + '\'' +
                ", deviceType=" + deviceType +
                ", fileName='" + fileName + '\'' +
                ", title1='" + title1 + '\'' +
                ", titleEn1='" + titleEn1 + '\'' +
                ", title1Content1='" + title1Content1 + '\'' +
                ", titleEn1Content1='" + titleEn1Content1 + '\'' +
                ", url1='" + url1 + '\'' +
                ", createTime='" + createTime + '\'' +
                ", title1Content2='" + title1Content2 + '\'' +
                ", titleEn1Content2='" + titleEn1Content2 + '\'' +
                ", urlEn1='" + urlEn1 + '\'' +
                '}';
    }
}
