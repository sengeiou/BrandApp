package com.isport.blelibrary.db.table.watch_w516;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * @Author
 * @Date 2019/2/24
 * @Fuction
 */
@Entity
public class Watch_W516_NotifyModel {

//    手表来电/信息提醒
//    userId	用户ID	string
//    deviceId	设备ID	string
//    callSwitch	来电提醒：开/关	bool
//    msgSwitch	信息提醒：开/关	bool

    @Id
    private Long id;
    private String userId;
    private String deviceId;
    private boolean callSwitch;
    private boolean msgSwitch;
    public boolean getMsgSwitch() {
        return this.msgSwitch;
    }
    public void setMsgSwitch(boolean msgSwitch) {
        this.msgSwitch = msgSwitch;
    }
    public boolean getCallSwitch() {
        return this.callSwitch;
    }
    public void setCallSwitch(boolean callSwitch) {
        this.callSwitch = callSwitch;
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
    @Generated(hash = 1726182976)
    public Watch_W516_NotifyModel(Long id, String userId, String deviceId,
            boolean callSwitch, boolean msgSwitch) {
        this.id = id;
        this.userId = userId;
        this.deviceId = deviceId;
        this.callSwitch = callSwitch;
        this.msgSwitch = msgSwitch;
    }
    @Generated(hash = 143776826)
    public Watch_W516_NotifyModel() {
    }

}
