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
public class DeviceTempUnitlTable {

    @Id
    private Long id;
    private String deviceName;
    private String userId;
    private String tempUnitl;//0,1

    public String getTempUnitl() {
        return this.tempUnitl;
    }

    public void setTempUnitl(String tempUnitl) {
        this.tempUnitl = tempUnitl;
    }

    public String getUserId() {
        return this.userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
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

    @Generated(hash = 64882958)
    public DeviceTempUnitlTable(Long id, String deviceName, String userId,
                                String tempUnitl) {
        this.id = id;
        this.deviceName = deviceName;
        this.userId = userId;
        this.tempUnitl = tempUnitl;
    }

    @Generated(hash = 1369820578)
    public DeviceTempUnitlTable() {
    }


    @Override
    public String toString() {
        return "DeviceTempUnitlTable{" +
                "id=" + id +
                ", deviceName='" + deviceName + '\'' +
                ", userId='" + userId + '\'' +
                ", tempUnitl='" + tempUnitl + '\'' +
                '}';
    }
}
