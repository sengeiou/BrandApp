package com.isport.blelibrary.managers;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import com.isport.blelibrary.ISportAgent;
import com.isport.blelibrary.bluetooth.callbacks.Watch516GattCallBack;
import com.isport.blelibrary.db.action.BleAction;
import com.isport.blelibrary.db.action.watch_w516.Watch_W516_AlarmModelAction;
import com.isport.blelibrary.db.action.watch_w516.Watch_W516_NotifyModelAction;
import com.isport.blelibrary.db.action.watch_w516.Watch_W516_SedentaryModelAction;
import com.isport.blelibrary.db.action.watch_w516.Watch_W516_SettingModelAction;
import com.isport.blelibrary.db.action.watch_w516.Watch_W516_SleepAndNoDisturbModelAction;
import com.isport.blelibrary.db.parse.ParseData;
import com.isport.blelibrary.db.table.DeviceInformationTable;
import com.isport.blelibrary.db.table.watch.Watch_SmartBand_SportDataModel;
import com.isport.blelibrary.db.table.watch_w516.Watch_W516_AlarmModel;
import com.isport.blelibrary.db.table.watch_w516.Watch_W516_NotifyModel;
import com.isport.blelibrary.db.table.watch_w516.Watch_W516_SedentaryModel;
import com.isport.blelibrary.db.table.watch_w516.Watch_W516_SettingModel;
import com.isport.blelibrary.db.table.watch_w516.Watch_W516_SleepAndNoDisturbModel;
import com.isport.blelibrary.deviceEntry.impl.BaseDevice;
import com.isport.blelibrary.deviceEntry.impl.Watch516Device;
import com.isport.blelibrary.interfaces.BluetoothListener;
import com.isport.blelibrary.observe.SyncProgressObservable;
import com.isport.blelibrary.result.impl.w311.BraceletW311SyncStart;
import com.isport.blelibrary.result.impl.watch.WatchBatteryResult;
import com.isport.blelibrary.result.impl.watch.WatchHrHeartResult;
import com.isport.blelibrary.result.impl.watch.WatchRealTimeResult;
import com.isport.blelibrary.result.impl.watch.WatchVersionResult;
import com.isport.blelibrary.result.impl.watch_w516.WatchW516AlarmResult;
import com.isport.blelibrary.result.impl.watch_w516.WatchW516InAdjustModeReslut;
import com.isport.blelibrary.result.impl.watch_w516.WatchW516SettingResult;
import com.isport.blelibrary.result.impl.watch_w516.WatchW516SleepSettingResult;
import com.isport.blelibrary.result.impl.watch_w516.WatchW516SyncResult;
import com.isport.blelibrary.utils.BleRequest;
import com.isport.blelibrary.utils.BleSPUtils;
import com.isport.blelibrary.utils.Constants;
import com.isport.blelibrary.utils.DeviceTimesUtil;
import com.isport.blelibrary.utils.Logger;
import com.isport.blelibrary.utils.SyncCacheUtils;
import com.isport.blelibrary.utils.TimeUtils;

import java.io.IOException;

/**
 * @Author
 * @Date 2019/2/19
 * @Fuction
 */

public class Watch516BleManager extends BaseManager {

    private Watch516GattCallBack mGattCallBack;
    private BluetoothListener bluetoothListener;
    private int mCurrentDay;//当前同步的位置
    private int mFailedTimes;//同步失败的次数

    public Watch516BleManager() {
    }

    public static Watch516BleManager instance;

    public static Watch516BleManager getInstance(Context context) {
        if (instance == null) {
            synchronized (Watch516BleManager.class) {
                if (instance == null) {
                    instance = new Watch516BleManager();
                    instance.init(context);
                }
            }
        }
        return instance;
    }

    public static Watch516BleManager getInstance() {
        if (instance == null) {
            synchronized (Watch516BleManager.class) {
                if (instance == null) {
                    instance = new Watch516BleManager();
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
        initSendHandler();
        initTimeout();
        initSyncHandler();
        setBTListener(btListener);
    }

    /**
     * Bluetooth4.0连接NRF设备
     */
    public void connectNRF(Watch516Device baseDevice, boolean isConnectByUser) {
        if (btListener == null) {
            setBTListener(btListener);
        }
        if (mGattCallBack == null) {
            mGattCallBack = new Watch516GattCallBack(bluetoothListener, nrfService, baseDevice);
        } else {
            mGattCallBack.setBaseDevice(baseDevice);
        }
        connectNRF(mGattCallBack, baseDevice, isConnectByUser, 300);
    }

    @Override
    public void exit() {
        super.exit();
        if (mGattCallBack != null) {
            mGattCallBack.exit();
        }
    }

    public void setCurrentDevice(BaseDevice baseDevice) {
        mCurrentDevice = baseDevice;
    }

    /**
     * 断连
     */
    public void disconnect(boolean reconnect) {
        if (mGattCallBack != null) {
            Logger.myLog("去断开连接222222222222222");
            notiHandler.removeMessages(0x02);
            mGattCallBack.disconnect(reconnect);
            /*if (syncHandler.hasMessages(0x00)) {
                syncHandler.removeMessages(0x00);
                //同步失败
                mHandler.sendEmptyMessage(0x13);
                // syncHandler.sendEmptyMessageDelayed(0x00, 0);
            }
            mHandler.sendEmptyMessage(HandlerContans.mHandlerConnetFailState);*/
        }
    }

//    public void disconnect(boolean reConnect) {
//        if (mGattCallBack != null) {
//            Logger.myLog("去断开连接222222222222222");
//            mGattCallBack.disconnect(reConnect);
//        }
//    }

    public void getBattery() {
        if (mGattCallBack != null) {
            mGattCallBack.internalReadBatteryLevel();
        }
    }

    public void close() {
        if (mGattCallBack != null) {
            mGattCallBack.gattClose();
        }
    }

    public void getDeviceVersion() {
        if (mGattCallBack != null) {
            mGattCallBack.internalReadFirmareVersion();
        }
    }

    private void initHandler() {



        final boolean isListenerNull = mBleReciveListeners == null;
        mHandler = new Handler(Looper.getMainLooper()) {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                switch (msg.what) {
                    case HandlerContans.mHandlerConnetSuccessState:
                        // TODO: 2019/1/7 device ID
//                        WatchDeviceInfo.putString(mContext, WatchDeviceInfo.WATCH_DEVICEID, mCurrentDevice
//                                .getAddress());

                        if (mDeviceInformationTable == null) {
                            mDeviceInformationTable = new DeviceInformationTable();
                        }
                        mDeviceInformationTable.setMac(mCurrentDevice.getAddress());
                        mDeviceInformationTable.setDeviceId(mCurrentDevice.getDeviceName());
                        ParseData.saveOrUpdateDeviceInfo(mDeviceInformationTable, -1);

//                        ParseData.saveDeviceType(IDeviceType.TYPE_WATCH);
                        if (!isListenerNull)
                            for (int i = 0; i < mBleReciveListeners.size(); i++) {
                                mBleReciveListeners.get(i).onConnResult(true, mGattCallBack.mIsConnectByUser, mCurrentDevice);
                            }
                        break;
                    case HandlerContans.mHandlerConnetting:
                        if (!isListenerNull && mCurrentDevice!=null)
                            for (int i = 0; i < mBleReciveListeners.size(); i++) {
                                mBleReciveListeners.get(i).onConnecting(mCurrentDevice);
                            }
                        break;
                    case HandlerContans.mHandlerConnetFailState:
                        if (!isListenerNull)
                            for (int i = 0; i < mBleReciveListeners.size(); i++) {
                                mBleReciveListeners.get(i).onConnResult(false, mGattCallBack.mIsConnectByUser, mCurrentDevice);
                            }
                        break;
                    case 2:
                        Bundle data2 = msg.getData();
                        WatchBatteryResult watchBatteryResult = (WatchBatteryResult) data2.getSerializable
                                ("onGetBattery");
                        // TODO: 2019/1/7 电量
//                        WatchDeviceInfo.putInt(mContext, WatchDeviceInfo.WATCH_BATTERY, watchBatteryResult == null
// ? 0 :
//                                watchBatteryResult.getQuantity());
                        mDeviceInformationTable.setBattery(watchBatteryResult == null ? 0 : watchBatteryResult
                                .getQuantity());
                        ParseData.saveOrUpdateDeviceInfo(mDeviceInformationTable, 0);
                        getDeviceVersion();//获取版本号
                        if (!isListenerNull)
                            for (int i = 0; i < mBleReciveListeners.size(); i++) {
                                mBleReciveListeners.get(i).receiveData(watchBatteryResult);
                            }
                        break;
                    case HandlerContans.mHandlerbattery:
                        Bundle data3 = msg.getData();
                        WatchVersionResult watchVersionResult = (WatchVersionResult) data3.getSerializable
                                ("DeviceVersion");
                        mDeviceInformationTable.setVersion(watchVersionResult == null ? "" : watchVersionResult
                                .getVersion());
                        Logger.myLog(" DeviceVersion == " + (watchVersionResult == null ? "" : watchVersionResult
                                .getVersion()) + " mDeviceInformationTable == " + mDeviceInformationTable.toString());
                        ParseData.saveOrUpdateDeviceInfo(mDeviceInformationTable, 1);
                        // TODO: 2019/1/7 版本号
//                        WatchDeviceInfo.putString(mContext, WatchDeviceInfo.WATCH_VERSION, watchVersionResult ==null
//                                ? "" : watchVersionResult
//                                .getVersion());
                        // TODO: 2019/3/4 判断是否是第一次绑定,第一次绑定设置
                        //设置所有的设置项
                        if (!isListenerNull)
                            for (int i = 0; i < mBleReciveListeners.size(); i++) {
                                mBleReciveListeners.get(i).receiveData(watchVersionResult);

                                Logger.myLog("onBattreyOrVersion" + mCurrentDevice);
                                if (mCurrentDevice != null) {
                                    mBleReciveListeners.get(i).onBattreyOrVersion(mCurrentDevice);
                                }

                            }
                        break;
                    case 0x04:
                        Object[] obj = (Object[]) msg.obj;
                        if (!isListenerNull && mCurrentDevice!=null)
                            for (int i = 0; i < mBleReciveListeners.size(); i++) {
                                mBleReciveListeners.get(i).receiveData(new WatchRealTimeResult((int) obj[1], 0, 0, (int) obj[0], mCurrentDevice.deviceName));
                                mBleReciveListeners.get(i).receiveData(new WatchHrHeartResult((Integer) obj[0], mCurrentDevice.deviceName));
                            }
                        break;
//                    case 5:
//                        getSportDataTimeOut();
//                        Object[] objects = (Object[]) msg.obj;
//                        mSportDataList.add(new WatchSportData((int) objects[0], (float) objects[1], (int) objects[2],
//                                                              (String) objects[3], (int) objects[4], (int)
//                                                                      objects[5], (int) objects[6], mCurrentDevice
//                                                                      .deviceName));
//                        Watch_SmartBand_SportDataModel watch_smartBand_sportDataModel = new
//                                Watch_SmartBand_SportDataModel();
//                        watch_smartBand_sportDataModel.setTotalSteps((int) objects[0]);
//                        watch_smartBand_sportDataModel.setTotalDistance((float) objects[1]);
//                        watch_smartBand_sportDataModel.setTotalCalories((int) objects[2]);
//                        watch_smartBand_sportDataModel.setDateStr((String) objects[3]);
//                        watch_smartBand_sportDataModel.setDeviceId(mCurrentDevice.deviceName);
//                        watch_smartBand_sportDataModel.setUserId(mUserId);
//                        watch_smartBand_sportDataModel.setTimestamp(System.currentTimeMillis());
//                        ParseData.saveWatchSmartBandSportDataModel(watch_smartBand_sportDataModel);
//                        mWatch_smartBand_sportDataModels.add(watch_smartBand_sportDataModel);
//                        break;
//                    case 6:
//                        // TODO: 2019/1/15 存储运动数据
////                        ParseData.saveWatchSmartBandSportDataModels(mWatch_smartBand_sportDataModels);
//                        mHandler.sendEmptyMessageDelayed(0x0a,200);
//                        break;
                    case 7:
                        if (!isListenerNull)
                            for (int i = 0; i < mBleReciveListeners.size(); i++) {
                                mBleReciveListeners.get(i).receiveData(new WatchW516SleepSettingResult());
                            }
                        break;
                    case 8:
                        if (!isListenerNull)
                            for (int i = 0; i < mBleReciveListeners.size(); i++) {
                                mBleReciveListeners.get(i).receiveData(new WatchW516SettingResult());
                            }
                        break;
                    case 9:
                        if (!isListenerNull)
                            for (int i = 0; i < mBleReciveListeners.size(); i++) {
                                mBleReciveListeners.get(i).receiveData(new WatchW516AlarmResult());
                            }
                        break;
                    case 0x10:
                        getBattery();
                        break;
                    case 0x11:
                        if (!isListenerNull)
                            for (int i = 0; i < mBleReciveListeners.size(); i++) {
                                mBleReciveListeners.get(i).receiveData(new WatchW516InAdjustModeReslut());
                            }
                        break;
                    case 0x12://同步成功
                        if (!isListenerNull)
                            for (int i = 0; i < mBleReciveListeners.size(); i++) {
                                SyncCacheUtils.saveSyncDataTime(mContext);
                                SyncCacheUtils.saveStartSync(mContext);
                                getSetting();
                                mBleReciveListeners.get(i).receiveData(new WatchW516SyncResult(WatchW516SyncResult.SUCCESS));
                            }
                        break;
                    case 0x13://超时回调
                        if (!isListenerNull) {
                            Logger.myLog("连接断开 超时回调");
                            SyncProgressObservable.getInstance().hide();
                            for (int i = 0; i < mBleReciveListeners.size(); i++) {
                                mBleReciveListeners.get(i).receiveData(new WatchW516SyncResult(WatchW516SyncResult.FAILED));
                            }
                        }
                        break;
                    case 0x14:
                        if (!isListenerNull&&mCurrentDevice!=null) {
                            Object[] objHr = (Object[]) msg.obj;
                            for (int i = 0; i < mBleReciveListeners.size(); i++) {
                                mBleReciveListeners.get(i).receiveData(new WatchHrHeartResult((Integer) objHr[0], mCurrentDevice.deviceName));
                            }
                        }
                        break;
                    case 0x15:
                        if (!isListenerNull)
                            for (int i = 0; i < mBleReciveListeners.size(); i++) {
                                mBleReciveListeners.get(i).receiveData(new WatchW516SyncResult(WatchW516SyncResult.SYNCING));
                            }
                        break;

//                    case 0x0a:
//                        if (!isListenerNull)
//                            for (int i = 0; i < mBleReciveListeners.size(); i++) {
//                                mBleReciveListeners.get(i).receiveData(new WatchSportDataListResult(mSportDataList));
//                            }
//                        break;
//                    default:
//                        break;
                }
            }
        };
    }

    private void setSetting() {
        // sendHandler.sendEmptyMessageDelayed(0x01, 100);
        //sendHandler.sendEmptyMessageDelayed(0x02, 300);
        // sendHandler.sendEmptyMessageDelayed(0x03, 500);
        // sendHandler.sendEmptyMessageDelayed(0x06, 700);

//        sendHandler.sendEmptyMessageDelayed(0x04, 700);//设备暂时是自动睡眠
        //getSetting();
        //同步就是在我连接超过了半小时
        if (!SyncCacheUtils.getSyncDataTime(mContext)) {
            //开始同步

            sendHandler.sendEmptyMessageDelayed(0x05, 100);
        } else {
            getSetting();
        }
    }


    //只有在第一次第一次启用和

    private void getSetting() {
        if (!SyncCacheUtils.getSetting(mContext)) {
            SyncCacheUtils.saveSetting(mContext);
            sendHandler.sendEmptyMessageDelayed(HandlerContans.mSenderGetGeneral, 100);
            sendHandler.sendEmptyMessageDelayed(HandlerContans.mSenderGetSedentary, 500);
            sendHandler.sendEmptyMessageDelayed(HandlerContans.mSenderGetAlarmList, 1000);
        }
    }

    /**
     * 使能Handler
     */
    private void initNotiHandler() {
        if (notiHandler == null) {
            notiHandler = new Handler(Looper.getMainLooper()) {
                @Override
                public void handleMessage(Message msg) {
                    super.handleMessage(msg);

                    switch (msg.what) {
                        case 1:
                            if (mGattCallBack != null) {
                                //通知设置超时 如果是在规定的时间内通道没有设置成功就让他断开走重连逻辑
                                notiHandler.sendEmptyMessageDelayed(0x02, 20000);
                                mGattCallBack.enableNotification();
                            }
                            break;
                        case 0x02:
                            Logger.myLog("notiHandler 0x02: disconnect");
                            disconnect(true);
                            break;
//                        case 2:
//                            sendTime();
//                            break;
//                        case 3:
//                            Logger.myLog("sendHeight == " + msg.arg1);
//                            sendHeight(msg.arg1);
//                            break;
//                        case 4:
//                            Logger.myLog("sendWeight == " + msg.arg1);
//                            sendWeight(msg.arg1);
//                            break;
//                        case 5:
//                            Logger.myLog("sendAge == " + msg.arg1);
//                            sendAge(msg.arg1);
//                            break;
//                        case 6:
//                            Logger.myLog("sendSex == " + msg.arg1);
//                            sendSex(msg.arg1);
//                            break;
//                        case 7:
//                            Logger.myLog("sendTargetStep == " + msg.arg1);
//                            sendTargetStep(msg.arg1);
//                            break;
//                        case 8:
//                            Logger.myLog("sendScreenTime == " + msg.arg1);
//                            sendScreenTime(msg.arg1);
//                            break;
//                        case 9:
//                            Logger.myLog("sendHandScreen == " + (boolean) msg.obj);
//                            sendHandScreen((boolean) msg.obj);
//                            break;
                    }
                }
            };
        }
    }

    /**
     * 同步Handler
     */
    private void initSyncHandler() {
        if (syncHandler == null) {
            syncHandler = new Handler(Looper.getMainLooper()) {
                @Override
                public void handleMessage(Message msg) {
                    super.handleMessage(msg);

                    switch (msg.what) {
                        case 0x00:
                            syncHandler.removeCallbacksAndMessages(null);
                            // TODO: 2019/3/4 超时的回调，接着请求
                            mHandler.sendEmptyMessage(0x13);
                            Logger.myLog("同步超时，第" + mCurrentDay);
                            /*if (mFailedTimes < 3) {
                                mFailedTimes++;
                                get_daily_record(mCurrentDay, false);
                            } else {
                                //超时回调
                                mHandler.sendEmptyMessage(0x13);
                            }*/
                            break;
                        case 0x01:
                            syncHandler.removeMessages(0x00);
                            // TODO: 2019/3/4 失败的回调，接着请求
                            Logger.myLog("失败的回调,接着同步" + mCurrentDay);
                            if (mFailedTimes < 3) {
                                mFailedTimes++;
                                get_daily_record(mCurrentDay, false);
                            } else {
                                //失败回调
                                mHandler.sendEmptyMessage(0x13);
                            }
                            break;
                        case 2:
                            syncHandler.removeMessages(0x00);
                            // TODO: 2019/3/4 成功的回调，接着请求
                            if (mCurrentDay == 0) {
                                //同步成功
                                SyncProgressObservable.getInstance().hide();
                                Logger.myLog("同步成功");
                                mHandler.sendEmptyMessage(0x12);
                            } else {
                                //接着同步
                                Logger.myLog("成功的回调,接着同步" + mCurrentDay);
                                mCurrentDay--;
                                get_daily_record(mCurrentDay, true);
                            }
                            break;
                    }
                }
            };
        }
    }

    /**
     * 超时handler
     */
    public void initTimeout() {
        if (timeOutHandler == null) {
            timeOutHandler = new Handler(Looper.getMainLooper()) {
                @Override
                public void handleMessage(Message msg) {
                    final boolean isListenerNull = mBleReciveListeners == null;
                    super.handleMessage(msg);
                    switch (msg.what) {
                        case 0x13:
                            Logger.myLog("连接断开 超时回调");
                            SyncProgressObservable.getInstance().hide();
                            for (int i = 0; i < mBleReciveListeners.size(); i++) {
                                mBleReciveListeners.get(i).receiveData(new WatchW516SyncResult(WatchW516SyncResult.FAILED));
                            }
                            break;
                        case HandlerContans.mNotiyFail:
                            if (mGattCallBack != null) {
                                mGattCallBack.gattClose();
                            }
                            break;

                    }
                }
            };
        }
    }

    /**
     * 发送指令Handler
     */
    private void initSendHandler() {
        if (sendHandler == null) {
            sendHandler = new Handler(Looper.getMainLooper()) {
                @Override
                public void handleMessage(Message msg) {
                    super.handleMessage(msg);

                    switch (msg.what) {
                        case 1:
                            /**
                             * 久坐提醒
                             */
                            Watch_W516_SedentaryModel watch_w516_watch_w516_sedentaryModelyDeviceId = Watch_W516_SedentaryModelAction.findWatch_W516_Watch_W516_SedentaryModelyDeviceId(mCurrentDevice.deviceName, BaseManager.mUserId);
                            if (watch_w516_watch_w516_sedentaryModelyDeviceId == null) {
                                //默認設置為关闭，1分钟，需要设置给设备
                                Watch_W516_SedentaryModel watch_w516_sedentaryModel = new Watch_W516_SedentaryModel();
                                watch_w516_sedentaryModel.setDeviceId(mCurrentDevice.deviceName);
                                watch_w516_sedentaryModel.setUserId(mUserId);
                                watch_w516_sedentaryModel.setLongSitTimeLong(Constants.W516defPerid);
                                watch_w516_sedentaryModel.setLongSitStartTime(Constants.defStartTime);
                                watch_w516_sedentaryModel.setLongSitEndTime(Constants.defEndTime);
                                BleAction.getWatch_W516_SedentaryModelDao().insertOrReplace(watch_w516_sedentaryModel);
                                ISportAgent.getInstance().requestBle(BleRequest.Watch_W516_SET_SEDENTARY_TIME, Constants.W516defPerid, Constants.defStarHour, Constants.defStartMin, Constants.defEndHour, Constants.defEndMin);
                            } else {
                                String longSitStartTime = watch_w516_watch_w516_sedentaryModelyDeviceId.getLongSitStartTime();
                                String longSitEndTime = watch_w516_watch_w516_sedentaryModelyDeviceId.getLongSitEndTime();
                                String[] start;
                                if (longSitStartTime != null) {
                                    start = longSitStartTime.split(":");
                                } else {
                                    start = new String[]{Constants.defStrStarHour, Constants.defStrStartMin};
                                }
                                String[] end;
                                if (longSitEndTime != null) {
                                    end = longSitEndTime.split(":");
                                } else {
                                    end = new String[]{Constants.defStrEndHour, Constants.defStrEndMin};
                                }
                                ISportAgent.getInstance().requestBle(BleRequest.Watch_W516_SET_SEDENTARY_TIME, watch_w516_watch_w516_sedentaryModelyDeviceId.getLongSitTimeLong(), Integer.parseInt(start[0]), Integer.parseInt(start[1]), Integer.parseInt(end[0]), Integer.parseInt(end[0]));
                            }
                            break;
                        case 2:
                            /**
                             * 24H心率开关
                             */
                            break;
                        case 3:
                            /**
                             * 闹钟
                             */
                            Watch_W516_AlarmModel watch_w516_alarmModelByDeviceId = Watch_W516_AlarmModelAction.findWatch_W516_AlarmModelByDeviceId(mCurrentDevice.deviceName, BaseManager.mUserId);
                            if (watch_w516_alarmModelByDeviceId == null) {
                                //没有闹钟默认是0，信息"起床"，时间为8:00
                                Watch_W516_AlarmModel watch_w516_alarmModel = new Watch_W516_AlarmModel();
                                watch_w516_alarmModel.setDeviceId(mCurrentDevice.deviceName);
                                watch_w516_alarmModel.setUserId(mUserId);
                                watch_w516_alarmModel.setMessageString("123");
                                watch_w516_alarmModel.setRepeatCount(0);
                                watch_w516_alarmModel.setTimeString(Constants.defStartTime);
                                BleAction.getWatch_W516_AlarmModelDao().insertOrReplace(watch_w516_alarmModel);
                                ISportAgent.getInstance().requestBle(BleRequest.Watch_W516_SET_ALARM,true, 0, 8, 0, 0);
                            } else {
                                String[] split = watch_w516_alarmModelByDeviceId.getTimeString().split(":");
                                ISportAgent.getInstance().requestBle(BleRequest.Watch_W516_SET_ALARM, true,watch_w516_alarmModelByDeviceId.getRepeatCount(), Integer.parseInt(split[0]), Integer.parseInt(split[1]), 0);
                            }
                            break;
                        case 4:
                            /**
                             * 睡眠/勿扰设置
                             */
                            Watch_W516_SleepAndNoDisturbModel watch_w516_sleepAndNoDisturbModelyDeviceId = Watch_W516_SleepAndNoDisturbModelAction.findWatch_W516_SleepAndNoDisturbModelyDeviceId(mUserId, mCurrentDevice.deviceName);
                            if (watch_w516_sleepAndNoDisturbModelyDeviceId == null && mCurrentDevice!=null) {
                                //如果是没有设置过，全部设置为关闭
                                Watch_W516_SleepAndNoDisturbModel watch_w516_sleepAndNoDisturbModel = new Watch_W516_SleepAndNoDisturbModel();
                                watch_w516_sleepAndNoDisturbModel.setDeviceId(mCurrentDevice.deviceName);
                                watch_w516_sleepAndNoDisturbModel.setUserId(mUserId);
                                watch_w516_sleepAndNoDisturbModel.setAutomaticSleep(true);
                                watch_w516_sleepAndNoDisturbModel.setSleepRemind(false);
                                // TODO: 2019/3/2 要查看是12小时还是24小时制
                                watch_w516_sleepAndNoDisturbModel.setSleepStartTime(Constants.defEndTime);
                                watch_w516_sleepAndNoDisturbModel.setSleepEndTime(Constants.defStartTime);
                                watch_w516_sleepAndNoDisturbModel.setOpenNoDisturb(false);
                                // TODO: 2019/3/2 要查看是12小时还是24小时制
                                watch_w516_sleepAndNoDisturbModel.setNoDisturbStartTime(Constants.defEndTime);
                                watch_w516_sleepAndNoDisturbModel.setNoDisturbEndTime(Constants.defStartTime);
                                BleAction.getWatch_W516_SleepAndNoDisturbModelDao().insertOrReplace(watch_w516_sleepAndNoDisturbModel);
                                ISportAgent.getInstance().requestBle(BleRequest.Watch_W516_SET_SLEEP_SETTING, true, false, false,
                                        20, 0, 8, 0, 20, 0, 8, 0);
                            } else {
                                String sleepStartTime = watch_w516_sleepAndNoDisturbModelyDeviceId.getSleepStartTime();
                                String[] splitSleepStartTime;
                                if (sleepStartTime != null) {
                                    splitSleepStartTime = sleepStartTime.split(":");
                                } else {
                                    splitSleepStartTime = new String[]{"20", "0"};
                                }

                                String sleepEndTime = watch_w516_sleepAndNoDisturbModelyDeviceId.getSleepEndTime();
                                String[] splitSleepEndTime;
                                if (sleepEndTime != null) {
                                    splitSleepEndTime = sleepEndTime.split(":");
                                } else {
                                    splitSleepEndTime = new String[]{"8", "0"};
                                }
                                String noDisturbStartTime = watch_w516_sleepAndNoDisturbModelyDeviceId.getNoDisturbStartTime();
                                String[] splitNoDisturbStartTime;
                                if (noDisturbStartTime != null) {
                                    splitNoDisturbStartTime = noDisturbStartTime.split(":");
                                } else {
                                    splitNoDisturbStartTime = new String[]{"20", "0"};
                                }
                                String noDisturbEndTime = watch_w516_sleepAndNoDisturbModelyDeviceId.getNoDisturbEndTime();
                                String[] splitNoDisturbEndTime;
                                if (noDisturbEndTime != null) {
                                    splitNoDisturbEndTime = noDisturbEndTime.split(":");
                                } else {
                                    splitNoDisturbEndTime = new String[]{"8", "0"};
                                }
                                ISportAgent.getInstance().requestBle(BleRequest.Watch_W516_SET_SLEEP_SETTING, !watch_w516_sleepAndNoDisturbModelyDeviceId.getAutomaticSleep(), watch_w516_sleepAndNoDisturbModelyDeviceId.getSleepRemind(), watch_w516_sleepAndNoDisturbModelyDeviceId.getOpenNoDisturb(),
                                        Integer.parseInt(splitSleepStartTime[0]), Integer.parseInt(splitSleepStartTime[1]), Integer.parseInt(splitSleepEndTime[0]), Integer.parseInt(splitSleepEndTime[1]), Integer.parseInt(splitNoDisturbStartTime[0]), Integer.parseInt(splitNoDisturbStartTime[1]), Integer.parseInt(splitNoDisturbEndTime[0]), Integer.parseInt(splitNoDisturbEndTime[1]));
                            }
                            break;
                        case 0x05:
                            //开始同步发送一个同步的信息到上层
                            int[] inf = (int[]) msg.obj;
                            final boolean isListenerNull = mBleReciveListeners == null;
                            if (!isListenerNull) {
                                for (int i = 0; i < mBleReciveListeners.size(); i++) {
                                    mBleReciveListeners.get(i).receiveData(new BraceletW311SyncStart());
                                }
                            }
                            // TODO: 2019/3/4 同步数据的逻辑,同步除今天以外的前推的7天,当天也同步，
                            // 同步成功和失败的回调,每个日期同步都要有同步超时，可重试的次数为3次
                            //需要获取当前同步到的天数
                            String todayYYYYMMDD = TimeUtils.getTodayYYYYMMDD();
                            String string = BleSPUtils.getString(mContext, BleSPUtils.WATCH_LAST_SYNCTIME, todayYYYYMMDD);
                            //比较上次同步的日期是否距离今天大于7天,如果，大于7天，从7(最开始)开始请求;如果不是则从间隔的天数开始请求;
                            //每次请求完毕,存储同步完成的dateStr,作为下次请求的开始
                            //
                            mHandler.sendEmptyMessage(0x15);
                            if (TimeUtils.isToday(string)) {
                                get_daily_record(0, true);
                                SyncProgressObservable.getInstance().sync(DeviceTimesUtil.getTime(1, 1), false);
                            } else {
                                int gapCount = TimeUtils.getGapCount(string, todayYYYYMMDD, "yyyy-MM-dd");

                                Logger.myLog("BleSPUtils.WATCH_LAST_SYNCTIME" + BleSPUtils.getString(mContext, BleSPUtils.WATCH_LAST_SYNCTIME, "2018-0201") + ",gapCount:" + gapCount);


                                if (gapCount > 7) {
                                    SyncProgressObservable.getInstance().sync(DeviceTimesUtil.getTime(8, 1), false);
                                    get_daily_record(7, true);
                                } else {
                                    SyncProgressObservable.getInstance().sync(DeviceTimesUtil.getTime(gapCount + 1, 1), false);
                                    get_daily_record(gapCount, true);
                                }
                            }
                            break;
                        case 0x06:
                            //个人信息
                            // TODO: 2019/3/4 同步数据的逻辑,同步除今天以外的前推的7天,当天也同步，
                            // 同步成功和失败的回调,每个日期同步都要有同步超时，可重试的次数为3次
                            set_user(mYear, mMonth, mDay, mSex, mWeight, (int) mHeight, 0, 0);
                            break;
                        case HandlerContans.mSenderGetGeneral:
                            getGeneral();
                            break;
                        case HandlerContans.mSenderGetSedentary:
                            getSedentaryTime();
                            break;
                        case HandlerContans.mSenderGetAlarmList:
                            getAlarm(0);
                            break;
                    }
                }
            };
        }
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
     * clear all
     */
    public void clearAll() {
        if (syncHandler != null) {
            syncHandler.removeCallbacksAndMessages(null);
        }
        if (notiHandler != null) {
            notiHandler.removeCallbacksAndMessages(null);
        }
        if (mHandler != null) {
            mHandler.removeCallbacksAndMessages(null);
        }
        if (sendHandler != null) {
            sendHandler.removeCallbacksAndMessages(null);
        }
    }

    /**
     * 使能
     */
    public void enableNotification() {
        notiHandler.sendEmptyMessageDelayed(0x01, 500);
    }

    private BluetoothListener btListener = new BluetoothListener() {
        @Override
        public void not_connected(int iWhy) {
            Logger.myLog("未连接成功");
        }

        @Override
        public void connecting() {
            //正在连接中
            mHandler.sendEmptyMessage(HandlerContans.mHandlerConnetting);
        }

        @Override
        public void connected() throws IOException {
            Logger.myLog("连接成功,获取版本号");
            notiHandler.removeMessages(0x02);
            clearAll();
            timeOutHandler.removeMessages(HandlerContans.mNotiyFail);
            mHandler.sendEmptyMessage(HandlerContans.mHandlerConnetSuccessState);
            //连接成功读取版本号 3000
            mHandler.sendEmptyMessageDelayed(0x10, 3000);
        }

        @Override
        public void disconnected() {
            Logger.myLog("watch516 连接断开");
            SyncCacheUtils.setUnBindState(false);
            if (syncHandler.hasMessages(0x00)) {
                syncHandler.removeMessages(0x00);
                //同步失败
                Logger.myLog("连接断开 同步中");
                timeOutHandler.sendEmptyMessage(0x13);
            }
            clearAll();
            timeOutHandler.removeMessages(HandlerContans.mNotiyFail);
            mHandler.sendEmptyMessage(HandlerContans.mHandlerConnetFailState);


        }

        @Override
        public void not_discoverServices() {
            Logger.myLog("获取服务失败");
            timeOutHandler.removeMessages(HandlerContans.mNotiyFail);
            timeOutHandler.sendEmptyMessageDelayed(HandlerContans.mNotiyFail, 500);
            // TODO: 2018/10/9 服务未找到的处理，找服务次数调整为两次
        }

        @Override
        public void servicesDiscovered() {
            Logger.myLog("连接成功去使能-非锁定");
            // TODO: 2018/10/9 使能失败的处理
            enableNotification();
            timeOutHandler.removeMessages(HandlerContans.mNotiyFail);
            timeOutHandler.sendEmptyMessageDelayed(HandlerContans.mNotiyFail, 10000);
        }

        @Override
        public void enableUnLockSuccess() {

        }

        @Override
        public void unLockData(float weight) {
        }

        @Override
        public void lockData(float weight, float r) {
        }

        @Override
        public void onGetBattery(int battery) {
            Logger.myLog("onGetBattery == " + battery);
            Message message = new Message();
            Bundle bundle = new Bundle();
            bundle.putSerializable("onGetBattery", new WatchBatteryResult(battery));
            message.setData(bundle);
            message.what = 0x02;
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
            if(mCurrentDevice!=null) {
                Logger.myLog("getDeviceVersionxxxxxxxxx" + version);
                Message message = new Message();
                Bundle bundle = new Bundle();
                bundle.putSerializable("DeviceVersion", new WatchVersionResult(version, mCurrentDevice.address));
                message.setData(bundle);
                message.what = HandlerContans.mHandlerbattery;
                mHandler.sendMessage(message);
            }

        }

        @Override
        public void realTimeData(int stepNum, float stepKm, int cal) {
            if(mCurrentDevice==null){
                return;
            }
            Logger.myLog("realTimeData stepNum == " + stepNum + " stepKm == " + stepKm + " cal == " + cal);
            Message message = new Message();
            Object[] objects = new Object[3];
            objects[0] = stepNum;
            objects[1] = stepKm;
            objects[2] = cal;

            Watch_SmartBand_SportDataModel watch_smartBand_sportDataModelRealTime = new
                    Watch_SmartBand_SportDataModel();
            watch_smartBand_sportDataModelRealTime.setTotalSteps((int) objects[0]);
            watch_smartBand_sportDataModelRealTime.setTotalDistance((float) objects[1]);
            watch_smartBand_sportDataModelRealTime.setTotalCalories((int) objects[2]);
            watch_smartBand_sportDataModelRealTime.setDateStr(TimeUtils.getTodayYYYYMMDD());
            watch_smartBand_sportDataModelRealTime.setDeviceId(mCurrentDevice.deviceName);
            watch_smartBand_sportDataModelRealTime.setUserId(mUserId);
            watch_smartBand_sportDataModelRealTime.setTimestamp(System.currentTimeMillis());
            ParseData.saveWatchSmartBandSportDataModel(watch_smartBand_sportDataModelRealTime);
            message.what = 0x04;
            message.obj = objects;
            mHandler.sendMessageDelayed(message, 100);
        }

        @Override
        public void sportData(int stepNum, float stepKm, int cal, String dateString, int sportTime, int maxHR, int
                minHR) {
            Message message = new Message();
            Object[] objects = new Object[7];
            objects[0] = stepNum;
            objects[1] = stepKm;
            objects[2] = cal;
            objects[3] = dateString;
            objects[4] = sportTime;
            objects[5] = maxHR;
            objects[6] = minHR;
            message.what = 0x05;
            message.obj = objects;
            mHandler.sendMessage(message);

        }

        @Override
        public void onSetTarget(int target) {
        }

        @Override
        public void onSetHandScreen(boolean enable) {
        }

        @Override
        public void onInDemoModeSuccess() {
            mHandler.sendEmptyMessage(0x11);
        }

        @Override
        public void onSettingUserInfoSuccess() {

        }

        @Override
        public void onGetUserInfoSuccess() {
            setSetting();
        }

        @Override
        public void onSyncTimeSuccess() {

        }

        @Override
        public void onRealtimeStepData(int step) {
            Logger.myLog("onRealtimeStepData == " + step);
        }

        @Override
        public void onRealtimeStepData(int heartRate, int step, int cal, int dis) {
            Message message = new Message();
            Object[] objects = new Object[2];
            objects[0] = heartRate;
            objects[1] = step;
            // TODO: 2019/3/1 实时数据存储
//            Watch_SmartBand_SportDataModel watch_smartBand_sportDataModelRealTime = new
//                    Watch_SmartBand_SportDataModel();
//            watch_smartBand_sportDataModelRealTime.setTotalSteps((int) objects[0]);
//            watch_smartBand_sportDataModelRealTime.setTotalDistance((float) objects[1]);
//            watch_smartBand_sportDataModelRealTime.setTotalCalories((int) objects[2]);
//            watch_smartBand_sportDataModelRealTime.setDateStr(TimeUtils.getTodayYYYYMMDD());
//            watch_smartBand_sportDataModelRealTime.setDeviceId(mCurrentDevice.deviceName);
//            watch_smartBand_sportDataModelRealTime.setUserId(mUserId);
//            watch_smartBand_sportDataModelRealTime.setTimestamp(System.currentTimeMillis());
//            ParseData.saveWatchSmartBandSportDataModel(watch_smartBand_sportDataModelRealTime);
            message.what = 0x04;
            message.obj = objects;
            mHandler.sendMessageDelayed(message, 100);
        }

        @Override
        public void onRealtimeHeartRate(int heartRate) {
            Message message = new Message();
            Object[] objects = new Object[1];
            objects[0] = heartRate;
            message.what = 0x14;
            message.obj = objects;
            mHandler.sendMessageDelayed(message, 100);
        }


        @Override
        public void onSetGeneral(boolean unit, boolean language, boolean timeFormat, boolean brightScreen, boolean
                heartRateSwitch) {
            if(mCurrentDevice == null){
                return;
            }
            Watch_W516_SettingModel watch_w516_settingModel = new Watch_W516_SettingModel();
            watch_w516_settingModel.setDeviceId(mCurrentDevice.deviceName);
            watch_w516_settingModel.setUserId(mUserId);
            watch_w516_settingModel.setUnit(unit);
            watch_w516_settingModel.setLanguage(language);
            watch_w516_settingModel.setTimeFormat(timeFormat);
            watch_w516_settingModel.setBrightScreen(brightScreen);
            watch_w516_settingModel.setHeartRateSwitch(heartRateSwitch);
            ParseData.saveOrUpdateWatchW516Setting(watch_w516_settingModel);
            Message message = new Message();
            message.what = 0x08;
            mHandler.sendMessageDelayed(message, 100);
        }

        @Override
        public void onSetAlarm(int repeatCount, String timeString, String messageString) {
            if(mCurrentDevice == null){
                return;
            }
            Watch_W516_AlarmModel watch_w516_alarmModel = new Watch_W516_AlarmModel();
            watch_w516_alarmModel.setDeviceId(mCurrentDevice.deviceName);
            watch_w516_alarmModel.setUserId(mUserId);
            watch_w516_alarmModel.setMessageString("");
            watch_w516_alarmModel.setRepeatCount(repeatCount);
            watch_w516_alarmModel.setTimeString(timeString);
            ParseData.saveOrUpdateWatchW516Alarm(watch_w516_alarmModel);
            Logger.myLog("repeat =222= " + repeatCount);
            Message message = new Message();
            message.what = 0x09;
            mHandler.sendMessageDelayed(message, 100);
        }

        @Override
        public void onSetSleepAndNoDisturb(boolean automaticSleep, boolean sleepRemind, boolean openNoDisturb, String
                sleepStartTime, String sleepEndTime, String noDisturbStartTime, String noDisturbEndTime) {
            if(mCurrentDevice == null){
                return;
            }
            Watch_W516_SleepAndNoDisturbModel watch_w516_sleepAndNoDisturbModel = new Watch_W516_SleepAndNoDisturbModel();
            watch_w516_sleepAndNoDisturbModel.setDeviceId(mCurrentDevice.deviceName);
            watch_w516_sleepAndNoDisturbModel.setUserId(mUserId);
            watch_w516_sleepAndNoDisturbModel.setAutomaticSleep(automaticSleep);
            watch_w516_sleepAndNoDisturbModel.setSleepRemind(sleepRemind);
            watch_w516_sleepAndNoDisturbModel.setOpenNoDisturb(openNoDisturb);
            watch_w516_sleepAndNoDisturbModel.setSleepStartTime(sleepStartTime);
            watch_w516_sleepAndNoDisturbModel.setSleepEndTime(sleepEndTime);
            watch_w516_sleepAndNoDisturbModel.setNoDisturbStartTime(noDisturbStartTime);
            watch_w516_sleepAndNoDisturbModel.setNoDisturbEndTime(noDisturbEndTime);
            ParseData.saveOrUpdateWatchW516SleepAndNoDisturb(watch_w516_sleepAndNoDisturbModel);
            Logger.myLog("watch_w516_sleepAndNoDisturbModel== " + watch_w516_sleepAndNoDisturbModel.toString());
            Message message = new Message();
            message.what = 0x07;
            mHandler.sendMessageDelayed(message, 100);
        }

        @Override
        public void onSetSedentary(int time, String startTime, String endTime) {
            if(mCurrentDevice == null){
                return;
            }
            Watch_W516_SedentaryModel watch_w516_sedentaryModel = new Watch_W516_SedentaryModel();
            watch_w516_sedentaryModel.setDeviceId(mCurrentDevice.deviceName);
            watch_w516_sedentaryModel.setUserId(mUserId);
            watch_w516_sedentaryModel.setLongSitTimeLong(time);
            watch_w516_sedentaryModel.setLongSitStartTime(startTime);
            watch_w516_sedentaryModel.setLongSitEndTime(endTime);
            if (time == 1) {
                watch_w516_sedentaryModel.setIsEnable(false);
            } else {
                watch_w516_sedentaryModel.setIsEnable(true);
            }
            ParseData.saveOrUpdateWatchW516Sedentary(watch_w516_sedentaryModel);
            Message message = new Message();
            message.what = 0x07;
            mHandler.sendMessageDelayed(message, 100);
        }

        @Override
        public void onSyncSuccess() {
            syncHandler.sendEmptyMessage(0x02);
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
        public void takePhoto() {

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


        @Override
        public void onSyncError() {
            syncHandler.sendEmptyMessage(0x01);
        }


        @Override
        public void onSetScreenTime(int time) {

        }
    };

    //****************************************指令部分*************************************************//

    public void read_status() {
        if (mGattCallBack != null) {
            mGattCallBack.writeTXCharacteristicItem(send_read_status());
        }
    }

    public void set_general(boolean open24HeartRate, boolean isCall, boolean isMessage) {
        if (mGattCallBack != null) {
            mGattCallBack.writeTXCharacteristicItem(send_set_general(open24HeartRate, isCall, isMessage));
        }
    }

    public void set_general(boolean open24HeartRate, boolean isHeart) {
        if(mCurrentDevice == null){
            return;
        }
        boolean isCall = false, isMessage = false;
        Watch_W516_SettingModel w516SettingModelByDeviceId = Watch_W516_SettingModelAction.findW516SettingModelByDeviceId(mCurrentDevice.getDeviceName(), BaseManager.mUserId);

        if (w516SettingModelByDeviceId == null) {
            w516SettingModelByDeviceId = new Watch_W516_SettingModel();
        }

        if (isHeart) {
            w516SettingModelByDeviceId.setHeartRateSwitch(open24HeartRate);
            //更改24小时开启的开关
            ParseData.saveOrUpdateWatchW516Setting(w516SettingModelByDeviceId);
            if (w516SettingModelByDeviceId != null) {
                open24HeartRate = w516SettingModelByDeviceId.getHeartRateSwitch();
            }
        }
        Watch_W516_NotifyModel watch_w516_notifyModelByDeviceId = Watch_W516_NotifyModelAction.findWatch_W516_NotifyModelByDeviceId(mCurrentDevice.getDeviceName(), BaseManager.mUserId);
        if (watch_w516_notifyModelByDeviceId != null) {
            isCall = watch_w516_notifyModelByDeviceId.getCallSwitch();
            isMessage = watch_w516_notifyModelByDeviceId.getMsgSwitch();
        }
        if (mGattCallBack != null) {
            mGattCallBack.writeTXCharacteristicItem(send_set_general(open24HeartRate, isCall, isMessage));
        }
    }

    public void get_general() {
        if (mGattCallBack != null) {
            mGattCallBack.writeTXCharacteristicItem(send_get_general());
        }
    }

    public void set_user(int year, int month, int day, int sex, int weight, int height, int maxHeartRate, int
            minHeartRate) {
        if (mGattCallBack != null) {
            mGattCallBack.writeTXCharacteristicItem(send_set_user(year, month, day, sex, weight, height, mSex == 0 ? (220 - mAge) : (226 - mAge),
                    90));
        }
    }

    public void get_user() {
        if (mGattCallBack != null) {
            mGattCallBack.writeTXCharacteristicItem(send_get_user());
        }
    }

    public void set_calender() {
        if (mGattCallBack != null) {
            mGattCallBack.writeTXCharacteristicItem(send_syncTime());
        }
    }

    public void get_calender() {
        if (mGattCallBack != null) {
            mGattCallBack.writeTXCharacteristicItem(send_get_calender());
        }
    }

    public void set_alarm(boolean enabel,int day, int hour, int min, int index) {
        if (mGattCallBack != null) {
            mGattCallBack.writeTXCharacteristicItem(send_set_alarm(day, hour, min, index));
        }
    }

    public void switch_mode(boolean inMode) {
        if (mGattCallBack != null) {
//            mGattCallBack.writeTXCharacteristicItem(send_switchAdjust(inMode));
            mGattCallBack.switchAdjust(inMode);
        }
    }

    public void adjust(int hour, int min) {
        if (mGattCallBack != null) {
            mGattCallBack.adjustHourAndMin(hour, min);
        }
    }

    public void get_alarm(int index) {
        if (mGattCallBack != null) {
            mGattCallBack.writeTXCharacteristicItem(send_get_alarm(index));
        }
    }

    public void set_sleep_setting(boolean isAutoSleep, boolean hasNotif, boolean disturb, int
            testStartTimeH, int testStartTimeM, int
                                          testEndTimeH, int testEndTimeM,
                                  int
                                          disturbStartTimeH, int disturbStartTimeM, int
                                          disturbEndTimeH, int disturbEndTimeM) {
        if (mGattCallBack != null) {
            mGattCallBack.writeTXCharacteristicItem(send_set_sleep_setting(isAutoSleep, hasNotif, disturb,
                    testStartTimeH, testStartTimeM,
                    testEndTimeH, testEndTimeM,

                    disturbStartTimeH, disturbStartTimeM,
                    disturbEndTimeH, disturbEndTimeM));
        }
    }

    public void get_sleep_setting() {
        if (mGattCallBack != null) {
            mGattCallBack.writeTXCharacteristicItem(send_get_sleep_setting());
        }
    }

    public void set_sedentary_time(int time, int startHour, int startMin, int endHour, int endMin) {
        if (mGattCallBack != null) {
            mGattCallBack.writeTXCharacteristicItem(send_set_sedentary_time(time, startHour, startMin, endHour, endMin));
        }
    }

    public void get_sedentary_time() {
        if (mGattCallBack != null) {
            mGattCallBack.writeTXCharacteristicItem(send_get_sedentary_time());
        }
    }

    public void send_notification(int type) {
        // TODO: 2019/2/20 短信、通知、电话来决定发哪一个
        if (mGattCallBack != null) {
            mGattCallBack.writeTXCharacteristicItem(type == 0 ? send_handleCall() : send_handleSms());
        }
    }

    public void send_notification_N(String pakStr) {
        // TODO: 2019/2/20 短信、通知、电话来决定发哪一个
        if (mGattCallBack != null) {
            mGattCallBack.writeTXCharacteristicItem(send_handleNotification(pakStr));
        }
    }

    public void set_handle(boolean enable) {
        if (mGattCallBack != null) {
            mGattCallBack.writeTXCharacteristicItem(send_switchAdjust(enable));
        }
    }


    public void syncUnBinde() {
        sendHandler.sendEmptyMessageDelayed(0x05, 100);
    }

    public void get_daily_record(int day, boolean tryAgain) {

        //这里去弹出需要去取多少天的


        if (mGattCallBack != null) {
            mCurrentDay = day;
            if (tryAgain)
                mFailedTimes = 0;
            if (syncHandler.hasMessages(0x00)) {
                syncHandler.removeMessages(0x00);
            }
            //开始同步超时
            syncHandler.sendEmptyMessageDelayed(0x00, SYNC_TIMEOUT);
            mGattCallBack.writeTXCharacteristicItem(send_get_daily_record(day));
        }
    }

    public void clear_daily_record() {
        if (mGattCallBack != null) {
            mGattCallBack.writeTXCharacteristicItem(send_clear_daily_record());
        }
    }

    public void getTestData() {
        if (mGattCallBack != null) {
            mGattCallBack.writeTXCharacteristicItem(send_getTestData());
        }
    }

    public void get_exercise_data() {
        if (mGattCallBack != null) {
            mGattCallBack.writeTXCharacteristicItem(send_get_exercise_data());
        }
    }

    public void clear_exercise_data() {
        if (mGattCallBack != null) {
            mGattCallBack.writeTXCharacteristicItem(send_clear_exercise_data());
        }
    }

    public void set_default() {
        if (mGattCallBack != null) {
            mGattCallBack.writeTXCharacteristicItem(send_reset());
        }
    }

    public void set_sn_factory() {
        if (mGattCallBack != null) {
            mGattCallBack.writeTXCharacteristicItem(send_set_sn_factory());
        }
    }

    public void set_sn_normalmode(int SN) {
        if (mGattCallBack != null) {
            mGattCallBack.writeTXCharacteristicItem(send_set_sn_normalmode(SN));
        }
    }

    public void get_sn_data() {
        if (mGattCallBack != null) {
            mGattCallBack.writeTXCharacteristicItem(send_get_sn_data());
        }
    }

    public void set_beltname() {
        if (mGattCallBack != null) {
            mGattCallBack.writeTXCharacteristicItem(send_set_beltname());
        }
    }

    public void test_reset() {
        if (mGattCallBack != null) {
            mGattCallBack.writeTXCharacteristicItem(send_test_reset());
        }
    }

    public void test_motorled() {
        if (mGattCallBack != null) {
            mGattCallBack.writeTXCharacteristicItem(send_test_motorled());
        }
    }

    public void stop_test_motorled() {
        if (mGattCallBack != null) {
            mGattCallBack.writeTXCharacteristicItem(send_stop_test_motorled());
        }
    }

    public void test_handle() {
        if (mGattCallBack != null) {
            mGattCallBack.writeTXCharacteristicItem(send_test_handle());
        }
    }

    public void test_display() {
        if (mGattCallBack != null) {
            mGattCallBack.writeTXCharacteristicItem(send_test_display());
        }
    }

    public void test_ohr() {
        if (mGattCallBack != null) {
            mGattCallBack.writeTXCharacteristicItem(send_test_ohr());
        }
    }

    public void device_to_phone() {
        if (mGattCallBack != null) {
            mGattCallBack.writeTXCharacteristicItem(send_device_to_phone());
        }
    }


    public void getGeneral() {
        if (mGattCallBack != null) {
            mGattCallBack.writeTXCharacteristicItem(send_get_general());
        }
    }

    public void getAlarm(int index) {
        if (mGattCallBack != null) {
            mGattCallBack.writeTXCharacteristicItem(send_get_alarm(index));
        }
    }

    public void getSedentaryTime() {
        if (mGattCallBack != null) {
            mGattCallBack.writeTXCharacteristicItem(send_get_sedentary_time());
        }
    }


}
