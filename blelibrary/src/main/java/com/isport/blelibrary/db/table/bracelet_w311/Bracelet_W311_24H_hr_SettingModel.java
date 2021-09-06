package com.isport.blelibrary.db.table.bracelet_w311;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;

/**
 * @Author
 * @Date 2019/2/24
 * @Fuction
 */
@Entity
public class Bracelet_W311_24H_hr_SettingModel {


//    手表设置的数据库
//    手表设置的数据库
//    userId	用户ID	string
//    deviceId	设备ID	string
//    isUnit	公制/英制	bool
//    isLanguage	英文/中文	bool
//    isTimeFormat	12小时/24小时制	bool
//    isBrightScreen	翻腕亮屏： 开/关	bool
//    isHeartRateSwitch	24小时心率： 开/关	bool


    @Id
    private Long id;
    private String userId;
    private String deviceId;
    private boolean heartRateSwitch;
    public boolean getHeartRateSwitch() {
        return this.heartRateSwitch;
    }
    public void setHeartRateSwitch(boolean heartRateSwitch) {
        this.heartRateSwitch = heartRateSwitch;
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
    @Generated(hash = 266105727)
    public Bracelet_W311_24H_hr_SettingModel(Long id, String userId,
            String deviceId, boolean heartRateSwitch) {
        this.id = id;
        this.userId = userId;
        this.deviceId = deviceId;
        this.heartRateSwitch = heartRateSwitch;
    }
    @Generated(hash = 272184120)
    public Bracelet_W311_24H_hr_SettingModel() {
    }

}
