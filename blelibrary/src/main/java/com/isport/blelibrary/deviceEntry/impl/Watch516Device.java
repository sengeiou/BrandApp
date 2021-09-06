package com.isport.blelibrary.deviceEntry.impl;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Context;

import com.isport.blelibrary.deviceEntry.interfaces.IDeviceType;
import com.isport.blelibrary.deviceEntry.interfaces.IWatch516;
import com.isport.blelibrary.managers.BaseManager;
import com.isport.blelibrary.managers.Watch516BleManager;
import com.isport.blelibrary.utils.Logger;

import java.lang.reflect.Method;

/**
 * @Author
 * @Date 2019/2/19
 * @Fuction
 */

public class Watch516Device extends BaseDevice implements IDeviceType, IWatch516 {

    /**
     * 名字 deviceName
     * mac地址 address
     * 信号值 rssi
     * 哪个类型的设备 deviceType
     */

    public Watch516Device(String name, String mac) {
        super();
        this.deviceName = name;
        this.address = mac;
        setType();
    }

    public Watch516Device(String mac) {
        super();
        this.address = mac;
        setType();
    }

    @Override
    public void disconnect(boolean reconnect) {
        Watch516BleManager.getInstance().disconnect(reconnect);
//        unbind(this.address);
    }

//    @Override
//    public void disconnect(boolean reConnect) {
//        Watch516BleManager.getInstance().disconnect(reConnect);
////        unbind(this.address);
//    }

    public void unbind(String mac) {
        BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (bluetoothAdapter != null) {
            BluetoothDevice bleDevice = bluetoothAdapter.getRemoteDevice(mac);
            if (bleDevice != null) {
                unpairDevice(bleDevice);
            }
        }
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
    public void connect(boolean isConnectByUser) {
        Watch516BleManager.getInstance().connectNRF(this, isConnectByUser);
    }

    @Override
    public BaseManager getManager(Context context) {
        return Watch516BleManager.getInstance(context);
    }

    @Override
    public void getBattery() {
        Watch516BleManager.getInstance().getBattery();
    }

    @Override
    public void exit() {
        Watch516BleManager.getInstance().exit();
    }

    @Override
    public void close() {
        Watch516BleManager.getInstance().close();
    }

    public void getDeviceVersion() {
        Watch516BleManager.getInstance().getDeviceVersion();
    }

    @Override
    public void setType() {
        this.deviceType = TYPE_WATCH;
    }

    @Override
    public void syncTodayData() {
        Watch516BleManager.getInstance().get_daily_record(0, true);
    }

    @Override
    public void queryWatchFace() {
        //Watch516BleManager.getInstance().findWatchFace();
    }

    @Override
    public void read_status() {
        Watch516BleManager.getInstance().read_status();
    }

    @Override
    public void set_general(boolean open24HeartRate, boolean isHeart) {
        Watch516BleManager.getInstance().set_general(open24HeartRate, isHeart);
    }

    @Override
    public void set_general(boolean open24HeartRate, boolean isCall, boolean isMessage) {
        Watch516BleManager.getInstance().set_general(open24HeartRate, isCall,isMessage);
    }

    @Override
    public void get_general() {
        Watch516BleManager.getInstance().get_general();
    }

    @Override
    public void set_user(int year, int month, int day, int sex, int weight, int height, int maxHeartRate, int
            minHeartRate) {
        Watch516BleManager.getInstance().set_user(year, month, day, sex, weight, height, maxHeartRate,
                minHeartRate);
    }

    @Override
    public void get_user() {
        Watch516BleManager.getInstance().get_user();
    }

    @Override
    public void set_calender() {
        Watch516BleManager.getInstance().set_calender();
    }

    @Override
    public void get_calender() {
        Watch516BleManager.getInstance().get_calender();
    }

    @Override
    public void set_alarm(boolean eanble,int day, int hour, int min,int index) {
        Watch516BleManager.getInstance().set_alarm(eanble,day, hour, min,index);
    }

    @Override
    public void switch_mode(boolean inMode) {
        Watch516BleManager.getInstance().switch_mode(inMode);
    }

    @Override
    public void adjust(int hour, int min) {
        Watch516BleManager.getInstance().adjust(hour, min);
    }

    @Override
    public void get_alarm(int index) {
        Watch516BleManager.getInstance().get_alarm(index);
    }

    @Override
    public void set_sleep_setting(boolean isAutoSleep, boolean hasNotif, boolean disturb, int
            testStartTimeH, int testStartTimeM, int
                                          testEndTimeH, int testEndTimeM,
                                  int
                                          disturbStartTimeH, int disturbStartTimeM, int
                                          disturbEndTimeH, int disturbEndTimeM) {
        Watch516BleManager.getInstance().set_sleep_setting(isAutoSleep, hasNotif, disturb,
                testStartTimeH, testStartTimeM,
                testEndTimeH, testEndTimeM,

                disturbStartTimeH, disturbStartTimeM,
                disturbEndTimeH, disturbEndTimeM);
    }

    @Override
    public void get_sleep_setting() {
        Watch516BleManager.getInstance().get_sleep_setting();
    }


    @Override
    public void set_sedentary_time(int time, int startHour, int startMin, int endHour, int endMin) {
        Watch516BleManager.getInstance().set_sedentary_time(time, startHour, startMin, endHour, endMin);
    }

    @Override
    public void get_sedentary_time() {
        Watch516BleManager.getInstance().get_sedentary_time();
    }

    @Override
    public void send_notification(int type) {
        Watch516BleManager.getInstance().send_notification(type);
    }

    @Override
    public void send_notificationN(String type) {
        Watch516BleManager.getInstance().send_notification_N(type);
    }

    @Override
    public void set_handle(boolean enable) {
        Watch516BleManager.getInstance().set_handle(enable);
    }

    @Override
    public void get_daily_record(int day) {
        Watch516BleManager.getInstance().get_daily_record(day, true);
    }

    @Override
    public void clear_daily_record() {
        Watch516BleManager.getInstance().clear_daily_record();
    }

    @Override
    public void getTestData() {
        Watch516BleManager.getInstance().getTestData();
    }

    @Override
    public void get_exercise_data() {
        Watch516BleManager.getInstance().get_exercise_data();
    }

    @Override
    public void clear_exercise_data() {
        Watch516BleManager.getInstance().clear_exercise_data();
    }

    @Override
    public void set_default() {
        Watch516BleManager.getInstance().set_default();
    }

    @Override
    public void set_sn_factory() {
        Watch516BleManager.getInstance().set_sn_factory();
    }

    @Override
    public void set_sn_normalmode(int SN) {
        Watch516BleManager.getInstance().set_sn_normalmode(SN);
    }

    @Override
    public void get_sn_data() {
        Watch516BleManager.getInstance().get_sn_data();
    }

    @Override
    public void set_beltname() {
        Watch516BleManager.getInstance().set_beltname();
    }

    @Override
    public void test_reset() {
        Watch516BleManager.getInstance().test_reset();
    }

    @Override
    public void test_motorled() {
        Watch516BleManager.getInstance().test_motorled();
    }

    @Override
    public void stop_test_motorled() {
        Watch516BleManager.getInstance().stop_test_motorled();
    }

    @Override
    public void test_handle() {
        Watch516BleManager.getInstance().test_handle();
    }

    @Override
    public void test_display() {
        Watch516BleManager.getInstance().test_display();
    }

    @Override
    public void test_ohr() {
        Watch516BleManager.getInstance().test_ohr();
    }

    @Override
    public void device_to_phone() {
        Watch516BleManager.getInstance().device_to_phone();
    }
}
