package com.isport.blelibrary.db.table.w811w814;


import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class FaceWatchMode {
    //    手表设置的数据库
//    faceWatchMode  当前设置的表盘
    @Id
    private Long id;
    private String userId;
    private String deviceId;
    //TIME_SYSTEM_12 0  TIME_SYSTEM_24 1
    private int faceWatchMode;
    public int getFaceWatchMode() {
        return this.faceWatchMode;
    }
    public void setFaceWatchMode(int faceWatchMode) {
        this.faceWatchMode = faceWatchMode;
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
    @Generated(hash = 597045786)
    public FaceWatchMode(Long id, String userId, String deviceId, int faceWatchMode) {
        this.id = id;
        this.userId = userId;
        this.deviceId = deviceId;
        this.faceWatchMode = faceWatchMode;
    }
    @Generated(hash = 288954018)
    public FaceWatchMode() {
    }

    @Override
    public String toString() {
        return "FaceWatchMode{" +
                "id=" + id +
                ", userId='" + userId + '\'' +
                ", deviceId='" + deviceId + '\'' +
                ", faceWatchMode=" + faceWatchMode +
                '}';
    }
}
