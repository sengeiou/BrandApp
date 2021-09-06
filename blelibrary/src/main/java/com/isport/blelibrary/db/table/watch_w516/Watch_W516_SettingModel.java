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
public class Watch_W516_SettingModel {


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
    private boolean unit;
    private boolean language;
    private boolean timeFormat;
    private boolean brightScreen;
    private boolean heartRateSwitch;
    public boolean getHeartRateSwitch() {
        return this.heartRateSwitch;
    }
    public void setHeartRateSwitch(boolean heartRateSwitch) {
        this.heartRateSwitch = heartRateSwitch;
    }
    public boolean getBrightScreen() {
        return this.brightScreen;
    }
    public void setBrightScreen(boolean brightScreen) {
        this.brightScreen = brightScreen;
    }
    public boolean getTimeFormat() {
        return this.timeFormat;
    }
    public void setTimeFormat(boolean timeFormat) {
        this.timeFormat = timeFormat;
    }
    public boolean getLanguage() {
        return this.language;
    }
    public void setLanguage(boolean language) {
        this.language = language;
    }
    public boolean getUnit() {
        return this.unit;
    }
    public void setUnit(boolean unit) {
        this.unit = unit;
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
    @Generated(hash = 761192281)
    public Watch_W516_SettingModel(Long id, String userId, String deviceId,
            boolean unit, boolean language, boolean timeFormat,
            boolean brightScreen, boolean heartRateSwitch) {
        this.id = id;
        this.userId = userId;
        this.deviceId = deviceId;
        this.unit = unit;
        this.language = language;
        this.timeFormat = timeFormat;
        this.brightScreen = brightScreen;
        this.heartRateSwitch = heartRateSwitch;
    }
    @Generated(hash = 1925682363)
    public Watch_W516_SettingModel() {
    }

}
