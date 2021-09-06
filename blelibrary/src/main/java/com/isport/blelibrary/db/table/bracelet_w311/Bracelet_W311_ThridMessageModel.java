package com.isport.blelibrary.db.table.bracelet_w311;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class Bracelet_W311_ThridMessageModel {

    @Id
    private Long id;
    private String userId;
    private String deviceId;
    private boolean isWechat;
    private boolean isQQ;
    private boolean isWhatApp;
    private boolean isFaceBook;
    private boolean isTwitter;
    private boolean isSkype;
    private boolean isInstagram;
    private boolean isMessage;
    private boolean isLinkedin;
    private boolean isOthers;
    private boolean iskakaotalk;
    private boolean isLine;
    public boolean getIsLine() {
        return this.isLine;
    }
    public void setIsLine(boolean isLine) {
        this.isLine = isLine;
    }
    public boolean getIskakaotalk() {
        return this.iskakaotalk;
    }
    public void setIskakaotalk(boolean iskakaotalk) {
        this.iskakaotalk = iskakaotalk;
    }
    public boolean getIsOthers() {
        return this.isOthers;
    }
    public void setIsOthers(boolean isOthers) {
        this.isOthers = isOthers;
    }
    public boolean getIsLinkedin() {
        return this.isLinkedin;
    }
    public void setIsLinkedin(boolean isLinkedin) {
        this.isLinkedin = isLinkedin;
    }
    public boolean getIsMessage() {
        return this.isMessage;
    }
    public void setIsMessage(boolean isMessage) {
        this.isMessage = isMessage;
    }
    public boolean getIsInstagram() {
        return this.isInstagram;
    }
    public void setIsInstagram(boolean isInstagram) {
        this.isInstagram = isInstagram;
    }
    public boolean getIsSkype() {
        return this.isSkype;
    }
    public void setIsSkype(boolean isSkype) {
        this.isSkype = isSkype;
    }
    public boolean getIsTwitter() {
        return this.isTwitter;
    }
    public void setIsTwitter(boolean isTwitter) {
        this.isTwitter = isTwitter;
    }
    public boolean getIsFaceBook() {
        return this.isFaceBook;
    }
    public void setIsFaceBook(boolean isFaceBook) {
        this.isFaceBook = isFaceBook;
    }
    public boolean getIsWhatApp() {
        return this.isWhatApp;
    }
    public void setIsWhatApp(boolean isWhatApp) {
        this.isWhatApp = isWhatApp;
    }
    public boolean getIsQQ() {
        return this.isQQ;
    }
    public void setIsQQ(boolean isQQ) {
        this.isQQ = isQQ;
    }
    public boolean getIsWechat() {
        return this.isWechat;
    }
    public void setIsWechat(boolean isWechat) {
        this.isWechat = isWechat;
    }
    public String getDeviceId() {
        return this.deviceId;
    }
    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }
    public String getUserId() {
        return this.userId;
    }
    public void setUserId(String userId) {
        this.userId = userId;
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    @Generated(hash = 1422652410)
    public Bracelet_W311_ThridMessageModel(Long id, String userId, String deviceId,
            boolean isWechat, boolean isQQ, boolean isWhatApp, boolean isFaceBook,
            boolean isTwitter, boolean isSkype, boolean isInstagram,
            boolean isMessage, boolean isLinkedin, boolean isOthers,
            boolean iskakaotalk, boolean isLine) {
        this.id = id;
        this.userId = userId;
        this.deviceId = deviceId;
        this.isWechat = isWechat;
        this.isQQ = isQQ;
        this.isWhatApp = isWhatApp;
        this.isFaceBook = isFaceBook;
        this.isTwitter = isTwitter;
        this.isSkype = isSkype;
        this.isInstagram = isInstagram;
        this.isMessage = isMessage;
        this.isLinkedin = isLinkedin;
        this.isOthers = isOthers;
        this.iskakaotalk = iskakaotalk;
        this.isLine = isLine;
    }
    @Generated(hash = 555119601)
    public Bracelet_W311_ThridMessageModel() {
    }




}
