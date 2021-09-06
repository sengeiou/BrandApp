package com.isport.blelibrary.managers;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import com.isport.blelibrary.bluetooth.callbacks.ScaleGattCallBack;
import com.isport.blelibrary.db.action.scale.Scale_FourElectrode_DataModelAction;
import com.isport.blelibrary.db.parse.ParseData;
import com.isport.blelibrary.db.table.DeviceInformationTable;
import com.isport.blelibrary.db.table.scale.Scale_FourElectrode_DataModel;
import com.isport.blelibrary.deviceEntry.impl.BaseDevice;
import com.isport.blelibrary.deviceEntry.impl.ScaleDevice;
import com.isport.blelibrary.interfaces.BluetoothListener;
import com.isport.blelibrary.result.impl.scale.ScaleBatteryResult;
import com.isport.blelibrary.result.impl.scale.ScaleCalculateResult;
import com.isport.blelibrary.result.impl.scale.ScaleLockDataResult;
import com.isport.blelibrary.result.impl.scale.ScaleUnLockDataResult;
import com.isport.blelibrary.result.impl.scale.ScaleVersionResult;
import com.isport.blelibrary.utils.FormatUtils;
import com.isport.blelibrary.utils.Logger;
import com.isport.blelibrary.utils.TimeUtils;
import com.isport.blelibrary.utils.Utils;

import java.io.IOException;

/**
 * @Author
 * @Date 2018/9/30
 * @Fuction Ble管理类
 */

public class ScaleBleManager extends BaseManager {

    private BluetoothListener bluetoothListener;
    private ScaleGattCallBack mGattCallBack;

    public ScaleBleManager() {
    }

    public static ScaleBleManager instance;

    public static ScaleBleManager getInstance() {
        if (instance == null) {
            synchronized (ScaleBleManager.class) {
                if (instance == null) {
                    instance = new ScaleBleManager();
                }
            }
        }
        return instance;
    }

    public static ScaleBleManager getInstance(Context context) {
        if (instance == null) {
            synchronized (ScaleBleManager.class) {
                if (instance == null) {
                    instance = new ScaleBleManager();
                    instance.init(context);
                }
            }
        }
        return instance;
    }


    /**
     * 初始化方法
     *
     * @param context
     */
    public void init(Context context) {
        super.init(context);
        // TODO: 2018/9/30 bind Service
        initHandler();
        initNotiHandler();
        setBTListener(btListener);
    }


    private void initHandler() {
        final boolean isListenerNull = mBleReciveListeners == null;
        mHandler = new Handler(Looper.getMainLooper()) {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                switch (msg.what) {
                    case 0x00:
                        // TODO: 2019/1/7 device ID
//                        ScaleDeviceInfo.putString(mContext, ScaleDeviceInfo.SCALE_DEVICEID, mCurrentDevice
//                                .getAddress());
                        if (mDeviceInformationTable == null) {
                            mDeviceInformationTable = new DeviceInformationTable();
                        }
                        mDeviceInformationTable.setMac(mCurrentDevice.getAddress());
                        mDeviceInformationTable.setDeviceId(mCurrentDevice.getAddress());
                        ParseData.saveOrUpdateDeviceInfo(mDeviceInformationTable, -1);
                        //由于体脂称是先展示连接展示数据给我用户看，后点击绑定按钮，故绑定的逻辑抽出，睡眠带和手表在连接成功时算绑定成功
//                        ParseData.saveDeviceType(IDeviceType.TYPE_SCALE);

                        if (!isListenerNull)
                            for (int i = 0; i < mBleReciveListeners.size(); i++) {
                                mBleReciveListeners.get(i).onConnResult(true, true, mCurrentDevice);
                            }
                        break;
                    case 1:
                        if (!isListenerNull)
                            for (int i = 0; i < mBleReciveListeners.size(); i++) {
                                mBleReciveListeners.get(i).onConnResult(false, true, mCurrentDevice);
                            }
                        break;
                    case 2:
                        Bundle data = msg.getData();
                        if (!isListenerNull)
                            for (int i = 0; i < mBleReciveListeners.size(); i++) {
                                mBleReciveListeners.get(i).receiveData(new ScaleUnLockDataResult(data.getFloat
                                        ("unLockData")));
                            }
                        break;
                    case 3:
                        Bundle data1 = msg.getData();
                        if (!isListenerNull)
                            for (int i = 0; i < mBleReciveListeners.size(); i++) {
                                Logger.myLog("lockData");
                                mBleReciveListeners.get(i).receiveData((ScaleLockDataResult) data1.getSerializable
                                        ("lockData"));
                                ScaleCalculateResult calculate = (ScaleCalculateResult) data1.getSerializable
                                        ("calculate");
                                if (calculate != null)
                                    mBleReciveListeners.get(i).receiveData(calculate);
                            }
                        break;
                    case 4:
                        Bundle data2 = msg.getData();
                        ScaleBatteryResult scaleBattery = (ScaleBatteryResult) data2.getSerializable
                                ("ScaleBattery");
                        mDeviceInformationTable.setBattery(scaleBattery == null ? 0 : scaleBattery.getQuantity());
                        ParseData.saveOrUpdateDeviceInfo(mDeviceInformationTable, 0);
                        // TODO: 2019/1/7 电量
//                        ScaleDeviceInfo.putInt(mContext, ScaleDeviceInfo.SCALE_BATTERY, scaleBattery == null ? 0 :
//                                scaleBattery.getQuantity());
                        if (!isListenerNull)
                            for (int i = 0; i < mBleReciveListeners.size(); i++) {
                                mBleReciveListeners.get(i).receiveData(scaleBattery);
                            }
                        break;
                    case 5:
                        Bundle data3 = msg.getData();
                        ScaleVersionResult scaleVersionResult = (ScaleVersionResult) data3.getSerializable
                                ("DeviceVersion");
                        Logger.myLog(" DeviceVersion == " + scaleVersionResult == null ? "" : scaleVersionResult
                                .getVersion());
                        mDeviceInformationTable.setVersion(scaleVersionResult == null ? "" : scaleVersionResult
                                .getVersion());
                        ParseData.saveOrUpdateDeviceInfo(mDeviceInformationTable, 1);
                        // TODO: 2019/1/7 版本号
//                        ScaleDeviceInfo.putString(mContext, ScaleDeviceInfo.SCALE_VERSION, scaleVersionResult == null
//                                ? "" : scaleVersionResult
//                                .getVersion());
                        if (!isListenerNull)
                            for (int i = 0; i < mBleReciveListeners.size(); i++) {
                                mBleReciveListeners.get(i).receiveData(scaleVersionResult);
                            }
                        break;
                    case 0x06:
                        getDeviceVersion();
                        break;
                    case 0x07:
                        sendUnit();
                        break;
                    default:
                        break;
                }
            }
        };
    }

    /**
     * 使能Handler
     */
    boolean enable;

    private void initNotiHandler() {
        if (notiHandler == null) {

            notiHandler = new Handler(Looper.getMainLooper()) {
                @Override
                public void handleMessage(Message msg) {
                    super.handleMessage(msg);

                    switch (msg.what) {
                        case 1:
                            if (mGattCallBack != null) {
                                enable = mGattCallBack.enableNotification();
                                Logger.myLog("非锁定数据" + enable);
                            }
                            break;
                        case 2:
                            if (mGattCallBack != null) {
                                enable = mGattCallBack.enableIndications();
                                Logger.myLog("锁定数据" + enable);
                            }
                            break;
                    }
                }
            };
        }
    }

    public void setCurrentDevice(BaseDevice baseDevice) {
        mCurrentDevice = baseDevice;
    }

    /**
     * 注册监听
     *
     * @param listener
     */
    public void setBTListener(BluetoothListener listener) {
        bluetoothListener = listener;
    }

    /**
     * Bluetooth4.0连接NRF设备
     */
    public void connectNRF(ScaleDevice baseDevice, boolean isConnectByUser) {
        if (mGattCallBack == null) {
            mGattCallBack = new ScaleGattCallBack(bluetoothListener, nrfService, baseDevice);
        }
        setCurrentDevice(baseDevice);
        connectNRF(mGattCallBack, baseDevice, isConnectByUser, 0);
    }

    /**
     * 使能
     *
     * @param b
     */
    public void enableNotification(boolean b) {
        if (b) {
            notiHandler.sendEmptyMessage(0x01);
        } else {
            notiHandler.sendEmptyMessage(0x02);
        }
    }

    /**
     * 断连
     */
    public void disconnect(boolean reconnect) {
        if (mGattCallBack != null)
            mGattCallBack.disconnect(reconnect);
    }

//    public void disconnect(boolean reConnect) {
//        if (mGattCallBack != null)
//            mGattCallBack.disconnect(reConnect);
//    }

    //**********************************************连接监听Scale、Watch********************************************//

    /**
     * 回调后和睡眠带一样，统一以本地广播的形式发出
     */
    private BluetoothListener btListener = new BluetoothListener() {
        @Override
        public void not_connected(int iWhy) {
            Logger.myLog("未连接成功");
        }

        @Override
        public void connecting() {

        }

        @Override
        public void takePhoto() {

        }

        @Override
        public void connected() throws IOException {
            Logger.myLog("连接成功");
            //使能成功后设置单位为kg
            sendUnit();
            mHandler.sendEmptyMessage(0x00);
            mHandler.sendEmptyMessageDelayed(0x06, 800);
            // mHandler.sendEmptyMessageDelayed(0x07, 100);
        }

        @Override
        public void disconnected() {
//            Logger.myLog("连接断开");
            mHandler.sendEmptyMessage(0x01);
        }

        @Override
        public void not_discoverServices() {
            Logger.myLog("获取服务失败");
            // TODO: 2018/10/9 服务未找到的处理，找服务次数调整为两次
        }

        @Override
        public void servicesDiscovered() {
            Logger.myLog("连接成功去使能-非锁定");
            // TODO: 2018/10/9 使能失败的处理

            enableNotification(true);
        }

        @Override
        public void enableUnLockSuccess() {
            Logger.myLog("连接成功去使能-锁定");
            enableNotification(false);
        }

        @Override
        public void unLockData(float weight) {
//            Logger.myLog("unLockData weight " + weight);
            Message message = new Message();
            Bundle bundle = new Bundle();
            bundle.putFloat("unLockData", FormatUtils.formatOnePointReturnFloat(weight));
            message.setData(bundle);
            message.what = 0x02;
            mHandler.sendMessage(message);
        }

        @Override
        public void lockData(float weight, float r) {
//            Logger.myLog("lockData weight " + weight + " r " + r);
            Message message = new Message();
            Bundle bundle = new Bundle();
            bundle.putSerializable("lockData", new ScaleLockDataResult(FormatUtils.formatOnePointReturnFloat(r),
                    FormatUtils.formatOnePointReturnFloat(weight)));
            Logger.myLog("calculate 00 ");
            Scale_FourElectrode_DataModel weightByDateStr = Scale_FourElectrode_DataModelAction
                    .getWeightByDateStr(mUserId, TimeUtils.getTodayYYYYMMDD());
            if (weightByDateStr == null) {
                //在测量页面，并且正负当天差值大于0.1KG的数据才会存储
                bundle.putSerializable("calculate", calculate(FormatUtils.formatOnePointReturnFloat(weight),
                        FormatUtils.formatOnePointReturnFloat(r), Utils
                                .replaceDeviceMacUpperCase(mCurrentDevice
                                        .getAddress
                                                ())));
            } else {
                //TODO 需要把上次的数据删除。把心的数据填充
                if (Math.abs(weightByDateStr.getWeight() - weight) <= 0.1) {
                    Scale_FourElectrode_DataModelAction
                            .deletWeightByDateStr(weightByDateStr);

                }
                bundle.putSerializable("calculate", calculate(FormatUtils.formatOnePointReturnFloat(weight),
                        FormatUtils.formatOnePointReturnFloat(r), Utils
                                .replaceDeviceMacUpperCase(mCurrentDevice
                                        .getAddress
                                                ())));
            }

            Logger.myLog("calculate 11 ");
            message.setData(bundle);
            message.what = 0x03;
            mHandler.sendMessage(message);
        }

        @Override
        public void onGetBattery(int battery) {
            Logger.myLog("Scale onGetBattery == " + battery);
            Message message = new Message();
            Bundle bundle = new Bundle();
            bundle.putSerializable("ScaleBattery", new ScaleBatteryResult(battery));
            message.setData(bundle);
            message.what = 0x04;
            mHandler.sendMessage(message);
        }

        @Override
        public void successAlam(int index) {

        }

        @Override
        public void successSleepData() {

        }

        @Override
        public void onGetDeviceVersion(String version) {
            Logger.myLog("getDeviceVersion " + version);
            Message message = new Message();
            Bundle bundle = new Bundle();
            bundle.putSerializable("DeviceVersion", new ScaleVersionResult(version));
            message.setData(bundle);
            message.what = 0x05;
            mHandler.sendMessage(message);
        }

        @Override
        public void realTimeData(int stepNum, float stepKm, int cal) {

        }

        @Override
        public void sportData(int stepNum, float stepKm, int cal, String dateString, int sportTime, int maxHR, int
                minHR) {

        }

        @Override
        public void onSetTarget(int target) {

        }

        @Override
        public void onSetScreenTime(int time) {

        }

        @Override
        public void onSetHandScreen(boolean enable) {

        }

        @Override
        public void onInDemoModeSuccess() {

        }

        @Override
        public void onSettingUserInfoSuccess() {

        }

        @Override
        public void onGetUserInfoSuccess() {

        }

        @Override
        public void onSyncTimeSuccess() {

        }

        @Override
        public void onRealtimeStepData(int step) {

        }

        public void onRealtimeStepData(int heartRate, int step, int cal, int dis) {

        }

        @Override
        public void onRealtimeHeartRate(int heartRate) {

        }

        @Override
        public void onSyncError() {

        }

        @Override
        public void onSetGeneral(boolean unit, boolean language, boolean timeFormat, boolean brightScreen, boolean
                heartRateSwitch) {

        }

        @Override
        public void onSetAlarm(int repeatCount, String timeString, String messageString) {

        }

        @Override
        public void onSetSleepAndNoDisturb(boolean automaticSleep, boolean sleepRemind, boolean openNoDisturb, String
                sleepStartTime, String sleepEndTime, String noDisturbStartTime, String noDisturbEndTime) {

        }

        @Override
        public void onSetSedentary(int time, String startTime, String endTime) {

        }

        @Override
        public void onSyncSuccess() {

        }

        @Override
        public void onSyncSuccessPractiseData(int index) {

        }

        @Override
        public void onStartSyncPractiseData(int index) {

        }

        @Override
        public void onStartSyncWheatherData() {

        }

        @Override
        public void onSyncRopeData() {

        }

        @Override
        public void onTempData(int temp) {

        }

        @Override
        public void onOxyData(int oxyvalue) {

        }

        @Override
        public void onBloodData(int sp, int dp) {

        }

        @Override
        public void onGetSettingSuccess(int type, int index) {

        }

        @Override
        public void onGetSettingSuccess() {

        }

        @Override
        public void clearSyncCmd() {

        }

        @Override
        public void onsetGeneral(byte[] bytes) {

        }

        @Override
        public void onSuccessTargetStep(int step) {

        }

        @Override
        public void onSuccessTargetDistance(int distance) {

        }

        @Override
        public void onSuccessTargetCalorie(int calorie) {

        }

        @Override
        public void onSuccessWatchFace(int watchIndx) {

        }

        @Override
        public void onSuccessTempSub(int value) {

        }

        @Override
        public void onSuccessOneHrData(int value) {

        }

        @Override
        public void onW560AlarmSettingSuccess() {

        }

    };

    @Override
    public void exit() {
        super.exit();
        if (mGattCallBack != null) {
            mGattCallBack.exit();
        }
    }

    public void getBattery() {
        if (mGattCallBack != null) {
            mGattCallBack.internalReadBatteryLevel();
        }
    }

    public void getDeviceVersion() {
        if (mGattCallBack != null) {
            boolean isTrue = mGattCallBack.internalReadFirmareVersion();
            Logger.myLog("getDeviceVersion:" + isTrue);
        }
    }

    public void close() {
        if (mGattCallBack != null) {
            mGattCallBack.gattClose();
        }
    }

    public void sendUnit() {
        if (mGattCallBack != null) {
            mGattCallBack.writeRXCharacteristicItem(send_unit());
        }
    }

}
