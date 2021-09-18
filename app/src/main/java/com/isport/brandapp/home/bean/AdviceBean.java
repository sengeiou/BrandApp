package com.isport.brandapp.home.bean;

public class AdviceBean {
    /**
     * "uid": "13154706",
     * "name": "r53",
     * "sort": null,
     * "jumpUrl": "12312&userId=317",
     * "imageUrl": "https://isportcloud.oss-cn-shenzhen.aliyuncs.com/manager/1602470138437.png",
     * "imageUrlEn": "https://isportcloud.oss-cn-shenzhen.aliyuncs.com/manager/1602468821939.png",
     * "content": "demoData"
     */
    String uid;
    String name;
    String sort;
    String jumpUrl;
    String imageUrl;
    String imageUrlEn;
    String content;

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public String getJumpUrl() {
        return jumpUrl;
    }

    public void setJumpUrl(String jumpUrl) {
        this.jumpUrl = jumpUrl;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getImageUrlEn() {
        return imageUrlEn;
    }

    public void setImageUrlEn(String imageUrlEn) {
        this.imageUrlEn = imageUrlEn;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "AdviceBean{" +
                "uid='" + uid + '\'' +
                ", name='" + name + '\'' +
                ", sort='" + sort + '\'' +
                ", jumpUrl='" + jumpUrl + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                ", imageUrlEn='" + imageUrlEn + '\'' +
                ", content='" + content + '\'' +
                '}';
    }
}

