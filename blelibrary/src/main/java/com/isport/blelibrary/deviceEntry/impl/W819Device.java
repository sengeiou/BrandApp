package com.isport.blelibrary.deviceEntry.impl;

import android.content.Context;

import com.isport.blelibrary.deviceEntry.interfaces.IBraceletW311;
import com.isport.blelibrary.deviceEntry.interfaces.IBraceletW520;
import com.isport.blelibrary.deviceEntry.interfaces.IDeviceType;
import com.isport.blelibrary.deviceEntry.interfaces.ITarget;
import com.isport.blelibrary.entry.AlarmEntry;
import com.isport.blelibrary.entry.AutoSleep;
import com.isport.blelibrary.entry.DisplaySet;
import com.isport.blelibrary.entry.NotificationMsg;
import com.isport.blelibrary.entry.SedentaryRemind;
import com.isport.blelibrary.entry.WristbandData;
import com.isport.blelibrary.entry.WristbandForecast;
import com.isport.blelibrary.managers.BaseManager;
import com.isport.blelibrary.managers.BraceletW811W814Manager;
import com.isport.blelibrary.utils.Logger;

import java.util.ArrayList;
import java.util.List;

/**
 * @创建者 bear
 * @创建时间 2019/4/18 11:52
 * @描述
 */
public class W819Device extends BaseDevice implements IDeviceType, IBraceletW311, ITarget, IBraceletW520 {

    public W819Device(String name, String mac) {
        super();
        this.deviceName = name;
        this.address = mac;
        setType();
    }

    public W819Device(String mac) {
        super();
        this.address = mac;
        setType();
    }

    @Override
    public void disconnect(boolean reConnect) {
        BraceletW811W814Manager.getInstance().disconnect(reConnect);
    }


    @Override
    public void connect(boolean isConnectByUser) {
        Logger.myLog("connect------------- W819");
        BraceletW811W814Manager.getInstance().connect(this);
        //BraceletW311BleManager.getInstance().connectNRF(this, isConnectByUser);

    }

    @Override
    public BaseManager getManager(Context context) {
        return BraceletW811W814Manager.getInstance(context);
    }

    @Override
    public void exit() {
        BraceletW811W814Manager.getInstance().exit();
    }

    @Override
    public void getBattery() {

    }

    @Override
    public void setType() {
        this.deviceType = TYPE_BRAND_W819;
    }

    @Override
    public void syncTodayData() {
        BraceletW811W814Manager.getInstance().syncTodayData();
    }

    @Override
    public void queryWatchFace() {
        BraceletW811W814Manager.getInstance().queryWatchFace();
    }


    @Override
    public void getRealHrSwitch() {

    }

    @Override
    public void set_userinfo() {
        BraceletW811W814Manager.getInstance().sendUserInfoW81();
    }

    @Override
    public void set_wear(boolean isLeft) {
    }


    @Override
    public void set_alar() {

    }

    @Override
    public void set_disPlay(DisplaySet displaySet) {
    }

    @Override
    public void get_display() {
    }

    @Override
    public void set_not_disturb(boolean open, int startHour, int startMin, int endHour, int endMin) {
        BraceletW811W814Manager.getInstance().senddistrub(open, startHour, startMin, endHour, endMin);

    }

    @Override
    public void get_not_disturb() {
    }

    @Override
    public void get_sedentary_reminder() {
    }

    @Override
    public void set_sendentary_reminder(List<SedentaryRemind> list) {
        BraceletW811W814Manager.getInstance().sendSedentaryReminderAndTime(list);
    }

    @Override
    public void set_hr() {

    }

    @Override
    public void find_bracelet() {
        BraceletW811W814Manager.getInstance().find_Device();
    }

    @Override
    public void lost_to_remind(boolean isOpen) {

    }

    @Override
    public void set_lift_wrist_to_view_info() {

    }

    @Override
    public void play_bracelet() {

    }

    @Override
    public void set_is_open_raise_hand(boolean isOpen) {
        if (isOpen) {
            BraceletW811W814Manager.getInstance().sendQuickViewAndTime(2, 0, 0, 0, 0);
        } else {
            BraceletW811W814Manager.getInstance().sendQuickView(isOpen);
        }

    }

    @Override
    public void set_raise_hand(int type, int startHour, int startMin, int endHour, int endMin) {
        BraceletW811W814Manager.getInstance().sendQuickViewAndTime(type, endHour, endMin, startHour, startMin);
    }

    @Override
    public void set_defult() {

    }

    @Override
    public void set_hr_setting(boolean isOpen) {
    }

    @Override
    public void set_hr_setting(boolean isOpen, int time) {
    }

    @Override
    public void sync_data() {
        BraceletW811W814Manager.getInstance().syncDeviceData();
    }

    @Override
    public void w311_send_phone(String comming_phone, String name) {
    }

    @Override
    public void w311_send_msge(NotificationMsg msg) {
    }

    @Override
    public void setAlarmList(ArrayList<AlarmEntry> list) {
        BraceletW811W814Manager.getInstance().sendAlarmList(list);
    }

    @Override
    public void getAlarmList() {

    }

    @Override
    public void setDeviceGoalStep(int targetStep) {
        BraceletW811W814Manager.getInstance().sendDevcieGoalStep(targetStep);
    }

    @Override
    public void setTimeFormat(int timeFormat) {
        BraceletW811W814Manager.getInstance().sendTimeSystem(timeFormat);
    }

    @Override
    public void sendMessage(String message, int messageType) {
        BraceletW811W814Manager.getInstance().sendMessage(message, messageType);
    }

    @Override
    public void w520SetDial(int enbale) {
        BraceletW811W814Manager.getInstance().setWatchFace(enbale);
    }

    @Override
    public void setWeather(WristbandData wristbandData, List<WristbandForecast> list) {

    }

    @Override
    public void setSleepData(AutoSleep sleepData) {

    }

    @Override
    public void getSleepData() {

    }

    @Override
    public void setRaise307J(int state) {

    }

    @Override
    public void showSwitchCameraView() {
        BraceletW811W814Manager.getInstance().sendSwitchCameraView();
    }

    @Override
    public void sendOtherMessageSwitch(boolean isSwitch) {
        BraceletW811W814Manager.getInstance().sendOhterMessageSwitch(isSwitch);
    }

    @Override
    public void measureBloodPressure(boolean isState) {
        BraceletW811W814Manager.getInstance().sendMeasureBloodPressureState(isState);
    }

    @Override
    public void measureOxygenBlood(boolean isState) {
        BraceletW811W814Manager.getInstance().sendMeasureOxygenState(isState);
    }

    @Override
    public void measureOnceHrData(boolean isState) {
        BraceletW811W814Manager.getInstance().sendMeasureOnceHrdataState(isState);
    }

    @Override
    public void w81QeryAlarmList() {
        BraceletW811W814Manager.getInstance().getAlarList();
    }

    @Override
    public void setTodayWeather(WristbandData weather, String city) {
        BraceletW811W814Manager.getInstance().sendWeatherToday(weather, city);
    }

    @Override
    public void set15DayWeather(List<WristbandForecast> weather) {
        BraceletW811W814Manager.getInstance().sendFutureWeather(weather);
    }
    @Override
    public void getVersion() {
        BraceletW811W814Manager.getInstance().findVersion();
    }
}
