package com.isport.blelibrary.db.table;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;

/**
 * @Author
 * @Date 2019/1/7
 * @Fuction 设备信息数据, 在在连接成功是存储设备信息，当有电量和版本信息返回时则存储，没有则null
 */
@Entity
public class DeviceInformationTable {

    @Id
    private Long id;//解决数据重叠bug
    private String deviceId;//每个设备独有的一个属性，唯一
    private String uuid;//ios 设备uuid
    private String mac;//android 设备mac
    private int battery;//电量
    private String version;//版本号

    public String getVersion() {
        return this.version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public int getBattery() {
        return this.battery;
    }

    public void setBattery(int battery) {
        this.battery = battery;
    }

    public String getMac() {
        return this.mac;
    }

    public void setMac(String mac) {
        this.mac = mac;
    }

    public String getUuid() {
        return this.uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
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

    @Generated(hash = 193385303)
    public DeviceInformationTable(Long id, String deviceId, String uuid,
                                  String mac, int battery, String version) {
        this.id = id;
        this.deviceId = deviceId;
        this.uuid = uuid;
        this.mac = mac;
        this.battery = battery;
        this.version = version;
    }

    public DeviceInformationTable() {
    }

    @Override
    public String toString() {
        return "DeviceInformationTable{" +
                "id=" + id +
                ", deviceId='" + deviceId + '\'' +
                ", uuid='" + uuid + '\'' +
                ", mac='" + mac + '\'' +
                ", battery=" + battery +
                ", version='" + version + '\'' +
                '}';
    }
}
