package com.isport.blelibrary.interfaces;

import java.io.IOException;

public interface BluetoothListener {

//	void discovery(Object obj); // 扫描到设备
//
//	void not_discovery(); // 未扫描到4
//
//	void write_message(byte[] data); // 发送数据包
//
//	void write_desdone();			//写入descriptor完成
//
//	void read_message(byte[] data, int length) throws IOException; // 接收数据包

    void not_connected(int iWhy); // 未连上设备

    void connecting();

    void takePhoto();

    void connected() throws IOException; // 连上设备

    void disconnected(); // 断开设备连接

    void not_discoverServices();

    void servicesDiscovered();

    void enableUnLockSuccess();

    void unLockData(float weight);

    void lockData(float weight, float r);

    void onGetBattery(int battery);

    void successAlam(int index);

    void successSleepData();

    void onGetDeviceVersion(String version);

    void realTimeData(int stepNum, float stepKm, int cal);

    void sportData(int stepNum, float stepKm, int cal, String dateString, int sportTime, int maxHR, int minHR);

    void onSetTarget(int target);

    void onSetScreenTime(int time);

    void onSetHandScreen(boolean enable);


    //W516

    void onInDemoModeSuccess();//进入校准模式成功

    void onSettingUserInfoSuccess();//设置用户信息成功

    void onGetUserInfoSuccess();//获取用户信息成功

    void onSyncTimeSuccess();//设置实时时间成功

    void onRealtimeStepData(int step);//实时步数返回

    void onRealtimeStepData(int heartRate, int step, int cal, int dis);//实时步数返回

    void onRealtimeHeartRate(int heartRate);//实时心率返回

    void onSyncError();//同步失败


    void onSetGeneral(boolean unit, boolean language, boolean timeFormat, boolean brightScreen, boolean heartRateSwitch);//设置通用设置

    void onSetAlarm(int repeatCount, String timeString, String messageString);//设置闹钟

    void onSetSleepAndNoDisturb(boolean automaticSleep, boolean sleepRemind, boolean openNoDisturb,
                                String sleepStartTime, String sleepEndTime, String noDisturbStartTime, String noDisturbEndTime);//睡眠设置

    void onSetSedentary(int time, String startTime, String endTime);//久坐提醒

    void onSyncSuccess();//同步成功

    void onSyncSuccessPractiseData(int index);

    void onStartSyncPractiseData(int index);

    void onStartSyncWheatherData();

    void onSyncRopeData();

    void onTempData(int temp);

    void onOxyData(int oxyvalue);

    void onBloodData(int sp, int dp);

    void onGetSettingSuccess(int type, int index);

    void onGetSettingSuccess();

    void clearSyncCmd();

    void onsetGeneral(byte[] bytes);

    void onSuccessTargetStep(int step);

    void onSuccessTargetDistance(int distance);

    void onSuccessTargetCalorie(int calorie);

    void onSuccessWatchFace(int watchIndx);

    void onSuccessTempSub(int value);

    void onSuccessOneHrData(int value);

    void onW560AlarmSettingSuccess();
}
