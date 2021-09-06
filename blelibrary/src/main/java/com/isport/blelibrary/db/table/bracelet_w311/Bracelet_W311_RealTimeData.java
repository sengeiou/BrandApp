package com.isport.blelibrary.db.table.bracelet_w311;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;

/**
 * @Author
 * @Date 2018/11/5
 * @Fuction
 */
@Entity
public class Bracelet_W311_RealTimeData {
    @Id
    private Long id;//解决数据重叠bug
    private String userId;
    private String deviceId;
    private int stepNum;
    private float stepKm;
    private int cal;
    private String date;
    private String mac;
    public String getMac() {
        return this.mac;
    }
    public void setMac(String mac) {
        this.mac = mac;
    }
    public String getDate() {
        return this.date;
    }
    public void setDate(String date) {
        this.date = date;
    }
    public int getCal() {
        return this.cal;
    }
    public void setCal(int cal) {
        this.cal = cal;
    }
    public float getStepKm() {
        return this.stepKm;
    }
    public void setStepKm(float stepKm) {
        this.stepKm = stepKm;
    }
    public int getStepNum() {
        return this.stepNum;
    }
    public void setStepNum(int stepNum) {
        this.stepNum = stepNum;
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
    @Generated(hash = 801446022)
    public Bracelet_W311_RealTimeData(Long id, String userId, String deviceId,
            int stepNum, float stepKm, int cal, String date, String mac) {
        this.id = id;
        this.userId = userId;
        this.deviceId = deviceId;
        this.stepNum = stepNum;
        this.stepKm = stepKm;
        this.cal = cal;
        this.date = date;
        this.mac = mac;
    }
    @Generated(hash = 647364999)
    public Bracelet_W311_RealTimeData() {
    }



}
