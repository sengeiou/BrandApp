package com.isport.blelibrary.db.table.watch;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;

/**
 * @Author
 * @Date 2019/1/8
 * @Fuction
 */

@Entity
public class Watch_SmartBand_HandScreenModel {

    @Id
    private Long id;
    private String deviceId;
    private String userId;
    private boolean isOpen;
    public boolean getIsOpen() {
        return this.isOpen;
    }
    public void setIsOpen(boolean isOpen) {
        this.isOpen = isOpen;
    }
    public String getUserId() {
        return this.userId;
    }
    public void setUserId(String userId) {
        this.userId = userId;
    }
    public String getDeviceId() {
        return this.deviceId;
    }
    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    @Generated(hash = 529571844)
    public Watch_SmartBand_HandScreenModel(Long id, String deviceId, String userId,
            boolean isOpen) {
        this.id = id;
        this.deviceId = deviceId;
        this.userId = userId;
        this.isOpen = isOpen;
    }
    @Generated(hash = 1529848976)
    public Watch_SmartBand_HandScreenModel() {
    }

}
