package com.isport.blelibrary.deviceEntry.impl;

import android.bluetooth.BluetoothDevice;
import android.content.Context;

import com.isport.blelibrary.deviceEntry.interfaces.IBraceletW311;
import com.isport.blelibrary.deviceEntry.interfaces.IBraceletW520;
import com.isport.blelibrary.deviceEntry.interfaces.IDeviceType;
import com.isport.blelibrary.entry.AlarmEntry;
import com.isport.blelibrary.entry.AutoSleep;
import com.isport.blelibrary.entry.DisplaySet;
import com.isport.blelibrary.entry.NotificationMsg;
import com.isport.blelibrary.entry.SedentaryRemind;
import com.isport.blelibrary.entry.WristbandData;
import com.isport.blelibrary.entry.WristbandForecast;
import com.isport.blelibrary.managers.BaseManager;
import com.isport.blelibrary.managers.BraceletW520BleManager;
import com.isport.blelibrary.utils.Logger;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * @创建者 bear
 * @创建时间 2019/4/18 11:52
 * @描述
 */
public class W520Device extends BaseDevice implements IDeviceType, IBraceletW311, IBraceletW520 {

    public W520Device(String name, String mac) {
        super();
        this.deviceName = name;
        this.address = mac;
        setType();
    }

    public W520Device(String mac) {
        super();
        this.address = mac;
        setType();
    }

    @Override
    public void disconnect(boolean reConnect) {
        BraceletW520BleManager.getInstance().disconnect(reConnect);
    }


    @Override
    public void connect(boolean isConnectByUser) {
        Logger.myLog("w311Device connect");
        BraceletW520BleManager.getInstance().connectNRF(this, isConnectByUser);

    }

    @Override
    public BaseManager getManager(Context context) {
        return BraceletW520BleManager.getInstance(context);
    }

    @Override
    public void exit() {
        BraceletW520BleManager.getInstance().exit();
    }

    @Override
    public void getBattery() {

    }

    @Override
    public void setType() {
        this.deviceType = TYPE_BRAND_W520;
    }

    //反射来调用BluetoothDevice.removeBond取消设备的配对
    private void unpairDevice(BluetoothDevice device) {
        try {
            Logger.myLog("反射解绑");
            Method m = device.getClass()
                    .getMethod("removeBond", (Class[]) null);
            m.invoke(device, (Object[]) null);
        } catch (Exception e) {
            Logger.myLog(e.getMessage());
        }
    }

    @Override
    public void getRealHrSwitch() {
       // BraceletW520BleManager.getInstance().bracelet_get_utoHeartRateAndTime();
    }

    @Override
    public void set_userinfo() {
        BraceletW520BleManager.getInstance().send_userInfo();

    }

    @Override
    public void set_wear(boolean isLeft) {
        BraceletW520BleManager.getInstance().bracelet_wear(isLeft);
    }


    @Override
    public void set_alar() {

    }

    @Override
    public void set_disPlay(DisplaySet displaySet) {
        BraceletW520BleManager.getInstance().bracelet_set_display(displaySet);
    }

    @Override
    public void get_display() {
        BraceletW520BleManager.getInstance().bracelet_get_display();
    }

    @Override
    public void set_not_disturb(boolean open, int startHour, int startMin, int endHour, int endMin) {

        BraceletW520BleManager.getInstance().set_distrub(open, startHour, startMin, endHour, endMin);

    }

    @Override
    public void get_not_disturb() {
    }

    @Override
    public void get_sedentary_reminder() {
        BraceletW520BleManager.getInstance().getSendentaryRemind();
    }

    @Override
    public void set_sendentary_reminder(List<SedentaryRemind> list) {
        BraceletW520BleManager.getInstance().setSendSedentaryRemind(list);
    }

    @Override
    public void set_hr() {

    }

    @Override
    public void find_bracelet() {
        BraceletW520BleManager.getInstance().find_bracelet();
    }

    @Override
    public void lost_to_remind(boolean isOpen) {

        BraceletW520BleManager.getInstance().isOpenAntiLost(isOpen);
    }

    @Override
    public void set_lift_wrist_to_view_info() {

    }

    @Override
    public void play_bracelet() {

    }

    @Override
    public void set_is_open_raise_hand(boolean isOpen) {
        BraceletW520BleManager.getInstance().setIsOpenRaiseHand(isOpen);
    }

    @Override
    public void set_raise_hand(int type, int startHour, int startMin, int endHour, int endMin) {
        BraceletW520BleManager.getInstance().setRaiseHand(type, startHour, startMin, endHour, endMin);
    }

    @Override
    public void set_defult() {

    }

    @Override
    public void set_hr_setting(boolean isOpen) {
        BraceletW520BleManager.getInstance().set_AutomaticHeartRateAndTime(isOpen);
    }

    @Override
    public void set_hr_setting(boolean isOpen, int time) {
        BraceletW520BleManager.getInstance().set_AutomaticHeartRateAndTime(isOpen, time);
    }

    @Override
    public void sync_data() {
        BraceletW520BleManager.getInstance().set_syncData();
    }

    @Override
    public void w311_send_phone(String comming_phone, String name) {
        BraceletW520BleManager.getInstance().set_phone(comming_phone, name);
    }

    @Override
    public void w311_send_msge(NotificationMsg msg) {
        BraceletW520BleManager.getInstance().sendNotiCmd(msg);
    }

    @Override
    public void setAlarmList(ArrayList<AlarmEntry> list) {
        BraceletW520BleManager.getInstance().set_alarm_list(list);
    }

    @Override
    public void getAlarmList() {
        BraceletW520BleManager.getInstance().bracelet_get_alarmlist();
    }

    @Override
    public void syncTodayData() {
        BraceletW520BleManager.getInstance().syncTodayData();
    }

    @Override
    public void queryWatchFace() {

    }


    @Override
    public void w520SetDial(int enbale) {
        BraceletW520BleManager.getInstance().set_Watch_Face_modle(enbale);
    }

    @Override
    public void setWeather(WristbandData wristbandData, List<WristbandForecast> list) {
        BraceletW520BleManager.getInstance().setWeather(wristbandData, list);
    }

    @Override
    public void setSleepData(AutoSleep sleepData) {

    }

    @Override
    public void getSleepData() {

    }

    @Override
    public void setRaise307J(int state) {
        BraceletW520BleManager.getInstance().setRaiseHand(state);
    }
}
