package com.isport.brandapp.device.scale.bean;

/**
 * 报告list
 */
public class ScaleBean {
    @Override
    public String toString() {
        return "ScaleBean{" +
                "fatSteelyardDetailId=" + fatSteelyardDetailId +
                ", creatTime='" + creatTime + '\'' +
                ", imgUrl='" + imgUrl + '\'' +
                ", title='" + title + '\'' +
                ", value='" + value + '\'' +
                ", standard='" + standard + '\'' +
                ", color='" + color + '\'' +
                '}';
    }

    public int fatSteelyardDetailId;//详情id

    public long creatTime;//服务器数据库创建时间

    public String imgUrl;//头像部分

    public int getImgInt() {
        return imgInt;
    }

    public void setImgInt(int imgInt) {
        this.imgInt = imgInt;
    }

    public String title;//名字

    public String value;//值

    public String standard;//标准

    public String color;//标准颜色值

    public int imgInt;//标准颜色值

    public void setFatSteelyardDetailId(int fatSteelyardDetailId) {
        this.fatSteelyardDetailId = fatSteelyardDetailId;
    }

    public int getFatSteelyardDetailId() {
        return this.fatSteelyardDetailId;
    }

    public void setCreatTime(long creatTime) {
        this.creatTime = creatTime;
    }

    public long getCreatTime() {
        return this.creatTime;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getImgUrl() {
        return this.imgUrl;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return this.title;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }

    public void setStandard(String standard) {
        this.standard = standard;
    }

    public String getStandard() {
        return this.standard;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getColor() {
        return this.color;
    }

}
