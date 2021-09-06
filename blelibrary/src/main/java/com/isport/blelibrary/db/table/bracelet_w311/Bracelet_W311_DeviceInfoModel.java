package com.isport.blelibrary.db.table.bracelet_w311;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * @author Created by Marcos Cheng on 2016/9/14.
 * Device config, all the information are read from ble device, can't be set
 */
@Entity
public class Bracelet_W311_DeviceInfoModel {
    @Id
    private Long id;
    private String  userId;
    private String deviceId;
    private String deviceModel;
    private byte hardwareVersion;
    private int firmwareHighVersion;
    private int firmwareLowVersion;//
    private int powerLevel;

    ///info 1
    private int statePhoto;
    private int stateLock;
    private int stateVibrate;
    private int stateFindPhone;
    private int stateHigh;
    private int stateMusic;
    private int stateBleInterface;
    private int stateProtected;

    ///info2
    private int stateMenu;//有无二级返回界面 0 无  1有
    private int state5Vibrate;//心率5/10分钟是否震动提醒  0 不提醒 1 提醒
    private int stateCallMsg;//来电信息有无震动提醒  0 不提醒  1 提醒
    private int stateConnectVibrate;///连接时震动

    private int statePinCode;//设备重启后有无 6位PIN码
    private int calIconHeart;//卡路里Icon是心 还是火
    private int calCaculateMethod;//卡路里计算方法 bit6 bit7

    ///info 3
    private int stateSleepInterfaceAndFunc;//是否有睡眠功能

    ///info 4
    private int bleRealTimeBroad;///蓝牙实时广播  bit1 bit0
    private int stateleftRight;///左右手
    private int stateAntiLost;///防丢提醒

    private int stateCallRemind;///来电提醒
    private int stateMessageContent;///信息提醒开关和内容 bit6 bit5 00 10 11
    private int stateMessageIcon;//信息提醒界面 有无icon  bit7

    ///info5
    private int stateSyncTime;//是否已设置时间 0  1
    private int stateShowHook;//连接时是否显示对勾
    public int getStateShowHook() {
        return this.stateShowHook;
    }
    public void setStateShowHook(int stateShowHook) {
        this.stateShowHook = stateShowHook;
    }
    public int getStateSyncTime() {
        return this.stateSyncTime;
    }
    public void setStateSyncTime(int stateSyncTime) {
        this.stateSyncTime = stateSyncTime;
    }
    public int getStateMessageIcon() {
        return this.stateMessageIcon;
    }
    public void setStateMessageIcon(int stateMessageIcon) {
        this.stateMessageIcon = stateMessageIcon;
    }
    public int getStateMessageContent() {
        return this.stateMessageContent;
    }
    public void setStateMessageContent(int stateMessageContent) {
        this.stateMessageContent = stateMessageContent;
    }
    public int getStateCallRemind() {
        return this.stateCallRemind;
    }
    public void setStateCallRemind(int stateCallRemind) {
        this.stateCallRemind = stateCallRemind;
    }
    public int getStateAntiLost() {
        return this.stateAntiLost;
    }
    public void setStateAntiLost(int stateAntiLost) {
        this.stateAntiLost = stateAntiLost;
    }
    public int getStateleftRight() {
        return this.stateleftRight;
    }
    public void setStateleftRight(int stateleftRight) {
        this.stateleftRight = stateleftRight;
    }
    public int getBleRealTimeBroad() {
        return this.bleRealTimeBroad;
    }
    public void setBleRealTimeBroad(int bleRealTimeBroad) {
        this.bleRealTimeBroad = bleRealTimeBroad;
    }
    public int getStateSleepInterfaceAndFunc() {
        return this.stateSleepInterfaceAndFunc;
    }
    public void setStateSleepInterfaceAndFunc(int stateSleepInterfaceAndFunc) {
        this.stateSleepInterfaceAndFunc = stateSleepInterfaceAndFunc;
    }
    public int getCalCaculateMethod() {
        return this.calCaculateMethod;
    }
    public void setCalCaculateMethod(int calCaculateMethod) {
        this.calCaculateMethod = calCaculateMethod;
    }
    public int getCalIconHeart() {
        return this.calIconHeart;
    }
    public void setCalIconHeart(int calIconHeart) {
        this.calIconHeart = calIconHeart;
    }
    public int getStatePinCode() {
        return this.statePinCode;
    }
    public void setStatePinCode(int statePinCode) {
        this.statePinCode = statePinCode;
    }
    public int getStateConnectVibrate() {
        return this.stateConnectVibrate;
    }
    public void setStateConnectVibrate(int stateConnectVibrate) {
        this.stateConnectVibrate = stateConnectVibrate;
    }
    public int getStateCallMsg() {
        return this.stateCallMsg;
    }
    public void setStateCallMsg(int stateCallMsg) {
        this.stateCallMsg = stateCallMsg;
    }
    public int getState5Vibrate() {
        return this.state5Vibrate;
    }
    public void setState5Vibrate(int state5Vibrate) {
        this.state5Vibrate = state5Vibrate;
    }
    public int getStateMenu() {
        return this.stateMenu;
    }
    public void setStateMenu(int stateMenu) {
        this.stateMenu = stateMenu;
    }
    public int getStateProtected() {
        return this.stateProtected;
    }
    public void setStateProtected(int stateProtected) {
        this.stateProtected = stateProtected;
    }
    public int getStateBleInterface() {
        return this.stateBleInterface;
    }
    public void setStateBleInterface(int stateBleInterface) {
        this.stateBleInterface = stateBleInterface;
    }
    public int getStateMusic() {
        return this.stateMusic;
    }
    public void setStateMusic(int stateMusic) {
        this.stateMusic = stateMusic;
    }
    public int getStateHigh() {
        return this.stateHigh;
    }
    public void setStateHigh(int stateHigh) {
        this.stateHigh = stateHigh;
    }
    public int getStateFindPhone() {
        return this.stateFindPhone;
    }
    public void setStateFindPhone(int stateFindPhone) {
        this.stateFindPhone = stateFindPhone;
    }
    public int getStateVibrate() {
        return this.stateVibrate;
    }
    public void setStateVibrate(int stateVibrate) {
        this.stateVibrate = stateVibrate;
    }
    public int getStateLock() {
        return this.stateLock;
    }
    public void setStateLock(int stateLock) {
        this.stateLock = stateLock;
    }
    public int getStatePhoto() {
        return this.statePhoto;
    }
    public void setStatePhoto(int statePhoto) {
        this.statePhoto = statePhoto;
    }
    public int getPowerLevel() {
        return this.powerLevel;
    }
    public void setPowerLevel(int powerLevel) {
        this.powerLevel = powerLevel;
    }
    public int getFirmwareLowVersion() {
        return this.firmwareLowVersion;
    }
    public void setFirmwareLowVersion(int firmwareLowVersion) {
        this.firmwareLowVersion = firmwareLowVersion;
    }
    public int getFirmwareHighVersion() {
        return this.firmwareHighVersion;
    }
    public void setFirmwareHighVersion(int firmwareHighVersion) {
        this.firmwareHighVersion = firmwareHighVersion;
    }
    public byte getHardwareVersion() {
        return this.hardwareVersion;
    }
    public void setHardwareVersion(byte hardwareVersion) {
        this.hardwareVersion = hardwareVersion;
    }
    public String getDeviceModel() {
        return this.deviceModel;
    }
    public void setDeviceModel(String deviceModel) {
        this.deviceModel = deviceModel;
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
    @Generated(hash = 715155499)
    public Bracelet_W311_DeviceInfoModel(Long id, String userId, String deviceId,
            String deviceModel, byte hardwareVersion, int firmwareHighVersion,
            int firmwareLowVersion, int powerLevel, int statePhoto, int stateLock,
            int stateVibrate, int stateFindPhone, int stateHigh, int stateMusic,
            int stateBleInterface, int stateProtected, int stateMenu,
            int state5Vibrate, int stateCallMsg, int stateConnectVibrate,
            int statePinCode, int calIconHeart, int calCaculateMethod,
            int stateSleepInterfaceAndFunc, int bleRealTimeBroad,
            int stateleftRight, int stateAntiLost, int stateCallRemind,
            int stateMessageContent, int stateMessageIcon, int stateSyncTime,
            int stateShowHook) {
        this.id = id;
        this.userId = userId;
        this.deviceId = deviceId;
        this.deviceModel = deviceModel;
        this.hardwareVersion = hardwareVersion;
        this.firmwareHighVersion = firmwareHighVersion;
        this.firmwareLowVersion = firmwareLowVersion;
        this.powerLevel = powerLevel;
        this.statePhoto = statePhoto;
        this.stateLock = stateLock;
        this.stateVibrate = stateVibrate;
        this.stateFindPhone = stateFindPhone;
        this.stateHigh = stateHigh;
        this.stateMusic = stateMusic;
        this.stateBleInterface = stateBleInterface;
        this.stateProtected = stateProtected;
        this.stateMenu = stateMenu;
        this.state5Vibrate = state5Vibrate;
        this.stateCallMsg = stateCallMsg;
        this.stateConnectVibrate = stateConnectVibrate;
        this.statePinCode = statePinCode;
        this.calIconHeart = calIconHeart;
        this.calCaculateMethod = calCaculateMethod;
        this.stateSleepInterfaceAndFunc = stateSleepInterfaceAndFunc;
        this.bleRealTimeBroad = bleRealTimeBroad;
        this.stateleftRight = stateleftRight;
        this.stateAntiLost = stateAntiLost;
        this.stateCallRemind = stateCallRemind;
        this.stateMessageContent = stateMessageContent;
        this.stateMessageIcon = stateMessageIcon;
        this.stateSyncTime = stateSyncTime;
        this.stateShowHook = stateShowHook;
    }
    @Generated(hash = 896531482)
    public Bracelet_W311_DeviceInfoModel() {
    }

}
