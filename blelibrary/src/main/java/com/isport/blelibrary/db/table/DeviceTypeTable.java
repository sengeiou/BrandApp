package com.isport.blelibrary.db.table;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;

/**
 * @Author
 * @Date 2019/1/7
 * @Fuction 维护好的设备类型列表, deviceName是协定好的指令类型的名字，不做改变;同类型设备只用查询是否存在，不存在则存储;
 * 当出现第二代产品时，更新type和name
 */

@Entity
public class DeviceTypeTable {

    @Id
    private Long id;
    private String deviceName;
    private int deviceType;
    private String mac;
    private String userId;
    private String deviceId;
    private long timeTamp;//绑定的时间戳，用于区分先后顺序
    public long getTimeTamp() {
        return this.timeTamp;
    }
    public void setTimeTamp(long timeTamp) {
        this.timeTamp = timeTamp;
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
    public String getMac() {
        return this.mac;
    }
    public void setMac(String mac) {
        this.mac = mac;
    }
    public int getDeviceType() {
        return this.deviceType;
    }
    public void setDeviceType(int deviceType) {
        this.deviceType = deviceType;
    }
    public String getDeviceName() {
        return this.deviceName;
    }
    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    @Generated(hash = 306919853)
    public DeviceTypeTable(Long id, String deviceName, int deviceType, String mac,
            String userId, String deviceId, long timeTamp) {
        this.id = id;
        this.deviceName = deviceName;
        this.deviceType = deviceType;
        this.mac = mac;
        this.userId = userId;
        this.deviceId = deviceId;
        this.timeTamp = timeTamp;
    }
    @Generated(hash = 1013301740)
    public DeviceTypeTable() {
    }


}
