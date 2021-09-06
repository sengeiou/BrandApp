package com.isport.blelibrary.managers;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.Vibrator;

import androidx.core.app.ActivityCompat;

import android.text.TextUtils;
import android.util.Log;

import com.isport.blelibrary.bluetooth.callbacks.BraceletW311GattCallBack;
import com.isport.blelibrary.db.parse.ParseData;
import com.isport.blelibrary.db.table.DeviceInformationTable;
import com.isport.blelibrary.deviceEntry.impl.BaseDevice;
import com.isport.blelibrary.deviceEntry.impl.W307JDevice;
import com.isport.blelibrary.deviceEntry.impl.W311Device;
import com.isport.blelibrary.entry.AlarmEntry;
import com.isport.blelibrary.entry.DisplaySet;
import com.isport.blelibrary.entry.NotificationMsg;
import com.isport.blelibrary.entry.SedentaryRemind;
import com.isport.blelibrary.entry.WristMode;
import com.isport.blelibrary.interfaces.BluetoothListener;
import com.isport.blelibrary.interfaces.W311BluetoothListener;
import com.isport.blelibrary.result.impl.w311.BraceletW311RealTimeResult;
import com.isport.blelibrary.result.impl.w311.BraceletW311SyncComplete;
import com.isport.blelibrary.result.impl.w311.BraceletW311SyncStart;
import com.isport.blelibrary.result.impl.watch.DeviceAlarmListResult;
import com.isport.blelibrary.result.impl.watch.WatchHrHeartResult;
import com.isport.blelibrary.utils.BleSPUtils;
import com.isport.blelibrary.utils.Constants;
import com.isport.blelibrary.utils.DateUtil;
import com.isport.blelibrary.utils.Logger;
import com.isport.blelibrary.utils.SyncCacheUtils;
import com.isport.blelibrary.utils.TimeUtils;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Vector;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @创建者 bear
 * @创建时间 2019/4/18 11:36
 * @描述
 */
public class BraceletW311BleManager extends BaseManager {
    public static ConcurrentHashMap<String, Integer> cacheRetrySyncDataInfo = new ConcurrentHashMap<>();
    public static ConcurrentHashMap<String, Integer> cacheRetrySyncHrDataInfo = new ConcurrentHashMap<>();
    private BraceletW311GattCallBack mGattCallBack;
    private W311BluetoothListener bluetoothListener;


    public BraceletW311BleManager() {
    }

    public BraceletW311BleManager(Context context) {
        init(context);
    }

    public static BraceletW311BleManager instance;

    public static BraceletW311BleManager getInstance(Context context) {
        if (instance == null) {
            synchronized (BraceletW311BleManager.class) {
                if (instance == null) {
                    instance = new BraceletW311BleManager(context);
                    instance.init(context);
                }
            }
        }
        return instance;
    }


    public static BraceletW311BleManager getInstance() {
        if (instance == null) {
            synchronized (BraceletW311BleManager.class) {
                if (instance == null) {
                    instance = new BraceletW311BleManager();
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
        initSyncHandler();
        initTimeout();
        setBTListener(btListener);
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
                        case HandlerContans.mSyncHandlerStepTimeOut:
                            Logger.myLog("mSyncHandlerHrTimeOut");
                            //数据无无响应
                            if (!isListenerNull) {
                                for (int i = 0; i < mBleReciveListeners.size(); i++) {
                                    mBleReciveListeners.get(i).receiveData(new BraceletW311SyncComplete(BraceletW311SyncComplete.TIMEOUT));
                                }
                            }

                            break;

                    }
                }
            };
        }
    }

    public void clearAllHandler() {
        if (mHandler != null) {
            mHandler.sendEmptyMessage(0);
        }
        if (notiHandler != null) {
            notiHandler.sendEmptyMessage(0);
        }
        if (sendHandler != null) {
            sendHandler.sendEmptyMessage(0);
        }
        if (syncHandler != null) {
            syncHandler.sendEmptyMessage(0);
        }
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
           /*if (syncHandler.hasMessages(HandlerContans.mSyncHandlerHrTimeOut) || syncHandler.hasMessages(HandlerContans.mSyncHandlerStepTimeOut)) {
                syncHandler.removeMessages(HandlerContans.mSyncHandlerHrTimeOut);
                syncHandler.removeMessages(HandlerContans.mSyncHandlerStepTimeOut);
                // syncHandler.sendEmptyMessageDelayed(HandlerContans.mSyncHandlerHrTimeOut, 0);\
                timeOutHandler.sendEmptyMessageDelayed(HandlerContans.mSyncHandlerStepTimeOut, 0);
            }
            clearAll();*/

        }
    }

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


    /**
     * 注册监听
     *
     * @param listener
     */
    public void setBTListener(BluetoothListener listener) {
        bluetoothListener = (W311BluetoothListener) listener;
    }

    /**
     * Bluetooth4.0连接NRF设备
     */
    public void connectNRF(W311Device baseDevice, boolean isConnectByUser) {
        if (btListener == null) {
            setBTListener(btListener);
        }
        Logger.myLog("mBaseManager" + baseDevice);
        if (mGattCallBack == null) {
            //这样会有bug就是mGattCallBacke的值没有改变
            mGattCallBack = new BraceletW311GattCallBack(bluetoothListener, nrfService, baseDevice);
        } else {
            mGattCallBack.setBaseDevice(baseDevice);
        }
        connectNRF(mGattCallBack, baseDevice, isConnectByUser, 300);
    }

    public void connectNRF(W307JDevice baseDevice, boolean isConnectByUser) {
        if (btListener == null) {
            setBTListener(btListener);
        }
        Logger.myLog("mBaseManager" + baseDevice);
        if (mGattCallBack == null) {
            //这样会有bug就是mGattCallBacke的值没有改变
            mGattCallBack = new BraceletW311GattCallBack(bluetoothListener, nrfService, baseDevice);
        } else {
            mGattCallBack.setBaseDevice(baseDevice);
        }
        connectNRF(mGattCallBack, baseDevice, isConnectByUser, 300);
    }

    public void getSetting() {
        Logger.myLog("SyncCacheUtils.getSetting(mContext):" + SyncCacheUtils.getSetting(mContext));
        if (!SyncCacheUtils.getSetting(mContext)) {
            SyncCacheUtils.saveSetting(mContext);
            //显示提醒列表
            sendHandler.sendEmptyMessageDelayed(HandlerContans.mSenderGetDisplay, 200);
            //闹钟列表
            sendHandler.sendEmptyMessageDelayed(HandlerContans.mSenderGetAlarmList, 600);
            //久坐提醒列表
            sendHandler.sendEmptyMessageDelayed(HandlerContans.mSenderGetSedentary, 1000);

            sendHandler.sendEmptyMessageDelayed(HandlerContans.mSenderGetDisturb, 1500);

            sendHandler.sendEmptyMessageDelayed(HandlerContans.mSenderGetRaiseHand, 2000);

            sendHandler.sendEmptyMessageDelayed(HandlerContans.mSenderGetAutoHeart, 2500);
        }
    }

    private W311BluetoothListener btListener = new W311BluetoothListener(){
        @Override
        public void onSysnSportDate(int startYear, int startMonth, int startDay) {
            int[] tpi = new int[]{startYear, startMonth, startDay};
            Message msgTp = Message.obtain();
            msgTp.obj = tpi;
            msgTp.what = HandlerContans.mSyncHandlerSyncDate;
            if (syncHandler.hasMessages(HandlerContans.mSyncHandlerStepTimeOut)) {
                syncHandler.removeMessages(HandlerContans.mSyncHandlerStepTimeOut);
            }
            syncHandler.sendEmptyMessageDelayed(HandlerContans.mSyncHandlerStepTimeOut, SYNC_TIMEOUT);
            syncHandler.sendMessageDelayed(msgTp, 500);
        }

        @Override
        public void onSysnHrDate(int startYear, int startMonth, int startDay) {
            int[] tpi = new int[]{startYear, startMonth, startDay};
            Message msgTp = Message.obtain();
            msgTp.obj = tpi;
            msgTp.what = HandlerContans.mSyncHandlerSyncHrDate;
            if (syncHandler.hasMessages(HandlerContans.mSyncHandlerHrTimeOut)) {
                syncHandler.removeMessages(HandlerContans.mSyncHandlerHrTimeOut);
            }
            syncHandler.sendEmptyMessageDelayed(HandlerContans.mSyncHandlerHrTimeOut, SYNC_TIMEOUT);
            syncHandler.sendMessageDelayed(msgTp, times);
        }

        @Override
        public void onSyncHrDataComptelety() {
            Message msgTp = Message.obtain();
            msgTp.what = HandlerContans.mSyncHandlerHrComptely;
            syncHandler.sendMessageDelayed(msgTp, times);
        }

        @Override
        public void onOpenReal() {
          /*  Message msgTp = Message.obtain();
            msgTp.what = HandlerContans.mSyncHandlerSyncOpenReal;
            syncHandler.sendMessageDelayed(msgTp, 1000);*/
        }

        @Override
        public void onStartSync() {
            //开始同步数据
            Logger.myLog("开始同步数据 getSyncDataTime:" + SyncCacheUtils.getSyncDataTime(mContext) + ",getSetting:" + SyncCacheUtils.getSetting(mContext));
            if (!SyncCacheUtils.getSyncDataTime(mContext)) {
                SyncCacheUtils.saveStartSync(mContext);
                syncHandler.sendEmptyMessageDelayed(HandlerContans.mSyncHandlerStartSync, 500);
                if (syncHandler.hasMessages(HandlerContans.mSyncHandlerStepTimeOut)) {
                    syncHandler.removeMessages(HandlerContans.mSyncHandlerStepTimeOut);
                }
                syncHandler.sendEmptyMessageDelayed(HandlerContans.mSyncHandlerStepTimeOut, SYNC_TIMEOUT);
                mHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (mBleReciveListeners != null) {
                            for (int i = 0; i < mBleReciveListeners.size(); i++) {
                                mBleReciveListeners.get(i).receiveData(new BraceletW311SyncStart());
                            }
                        }
                    }
                }, 0);

                //开始同步

            } else {
                getSetting();
            }

        }

        @Override
        public void onW311RealTimeData(int sumStep, float sumDis, int sumCal, String mac) {
            Message message = new Message();
            Object[] objects = new Object[4];
            objects[0] = sumStep;
            objects[1] = sumDis;
            objects[2] = sumCal;
            objects[3] = mac;
            message.what = HandlerContans.mHandlerRealData;
            message.obj = objects;
            mHandler.sendMessageDelayed(message, times);
        }


        @Override
        public void onSuccessSendPhone() {
            Message message = new Message();
            Object objects = name;
            //objects = "wangwu";
            message.what = HandlerContans.mSenderPhoneName;
            message.obj = objects;
            sendHandler.sendMessageDelayed(message, times);
        }

        @Override
        public void onSuccessSendMesg(int mesgType, int dataIndex) {
            Message message = new Message();
            int[] tpi = new int[]{mesgType, dataIndex};
            Message msgTp = Message.obtain();
            msgTp.obj = tpi;
            message.what = HandlerContans.mSenderMessage;
            message.obj = tpi;
            sendHandler.sendMessageDelayed(message, times);
        }

        @Override
        public void onSendUserInfo() {
            // 如果是第一次连接就需要去发送震动的指令，否则就不需要发送震动的指令
            Logger.myLog("getFirstBindW311State:" + SyncCacheUtils.getFirstBindW311State(mContext));
            if (SyncCacheUtils.getFirstBindW311State(mContext)) {
                SyncCacheUtils.clearFirstBindW311(mContext);
                sendHandler.sendEmptyMessageDelayed(HandlerContans.mSenderSendVibrateConnected, times);
                sendHandler.sendEmptyMessageDelayed(HandlerContans.mSenderUserInfo, 800);
                syncHandler.sendEmptyMessageDelayed(HandlerContans.mSyncHandlerSyncOpenReal, 1500);
            } else {
                sendHandler.sendEmptyMessageDelayed(HandlerContans.mSenderUserInfo, times);
                syncHandler.sendEmptyMessageDelayed(HandlerContans.mSyncHandlerSyncOpenReal, 800);
            }
            //send_userInfo();
        }

        @Override
        public void findPhoen() {
            findMobilePhone();
        }

        @Override
        public void onGetWatchFaceMode(int mode) {

        }

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
        public void takePhoto() {

        }

        @Override
        public void connected() throws IOException {
            Logger.myLog("连接成功,获取版本号");
            notiHandler.removeMessages(0x02);
            clearAll();
            mHandler.sendEmptyMessage(HandlerContans.mHandlerConnetSuccessState);
            //连接成功读取版本号 3000
            mHandler.sendEmptyMessageDelayed(HandlerContans.mHandlerVersion, 3000);
        }

        @Override
        public void disconnected() {
            Logger.myLog("连接断开");
            if (syncHandler.hasMessages(HandlerContans.mSyncHandlerHrTimeOut) || syncHandler.hasMessages(HandlerContans.mSyncHandlerStepTimeOut)) {
                syncHandler.removeMessages(HandlerContans.mSyncHandlerHrTimeOut);
                syncHandler.removeMessages(HandlerContans.mSyncHandlerStepTimeOut);
                // syncHandler.sendEmptyMessageDelayed(HandlerContans.mSyncHandlerHrTimeOut, 0);\
                timeOutHandler.sendEmptyMessageDelayed(HandlerContans.mSyncHandlerStepTimeOut, 0);
            }
            clearAll();

            mHandler.sendEmptyMessage(HandlerContans.mHandlerConnetFailState);

        }

        @Override
        public void not_discoverServices() {
            Logger.myLog("获取服务失败");
            close();
            // TODO: 2018/10/9 服务未找到的处理，找服务次数调整为两次
        }

        @Override
        public void servicesDiscovered() {
            Logger.myLog("连接成功去使能-非锁定");
            // TODO: 2018/10/9 使能失败的处理
            enableNotification();



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

        }

        @Override
        public void successAlam(int index) {
            mHandler.sendEmptyMessageDelayed(HandlerContans.mDevcieAlarList, 1000);
        }

        @Override
        public void successSleepData() {

        }

        @Override
        public void onGetDeviceVersion(String version) {
            // Logger.myLog("getDeviceVersionxxxxxxxxx" + version);
            // Message message = new Message();
            // Bundle bundle = new Bundle();
            // bundle.putSerializable("DeviceVersion", new BrandVersionResult(version));
            // message.setData(bundle);
            //message.what = HandlerContans.mHandlerbattery;
            //mHandler.sendMessage(message);
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

        @Override
        public void onRealtimeStepData(int heartRate, int step, int cal, int dis) {

        }

        @Override
        public void onRealtimeHeartRate(int heartRate) {
            //实时心率数据
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

        @Override
        public void onSyncCompte() {
            syncHandler.sendEmptyMessage(HandlerContans.mSyncHandlerComptely);
        }

        @Override
        public void onDeviceSuccess(int type) {


            if (!Constants.isDFU) {
                //这里加一个延时
                if (type == 0) {
                    sendHandler.sendEmptyMessageDelayed(HandlerContans.mSenderSetBaseTime, times);
                } else {
                    sendHandler.sendEmptyMessageDelayed(HandlerContans.mSenderSetDate, times);
                }
            }

            Logger.myLog("onDeviceSuccess  HandlerContans.mHandlerbattery");
            mHandler.sendEmptyMessage(HandlerContans.mHandlerbattery);


        }

        @Override
        public void onSyncError() {
        }

        @Override
        public void onSetScreenTime(int time) {

        }
    };

    /**
     * 使能
     */
    public void enableNotification() {
        notiHandler.sendEmptyMessageDelayed(0x01, 500);
    }

    private void initHandler() {
        final boolean isListenerNull = mBleReciveListeners == null;
        mHandler = new Handler(Looper.getMainLooper()) {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                switch (msg.what) {
                    case HandlerContans.mHandlerConnetting:
                        if (!isListenerNull && mCurrentDevice!=null)
                            for (int i = 0; i < mBleReciveListeners.size(); i++) {
                                mBleReciveListeners.get(i).onConnecting(mCurrentDevice);
                            }
                        break;
                    case HandlerContans.mDevcieAlarList:
                        //HandlerContans.mDevcieAlarList

                        //Logger.myLog("HandlerContans.mDevcieAlarList");
                        if (!isListenerNull) {
                            for (int i = 0; i < mBleReciveListeners.size(); i++) {
                                mBleReciveListeners.get(i).receiveData(new DeviceAlarmListResult(mCurrentDevice.deviceName));
                                //Logger.myLog("HandlerContans.mDevcieAlarList2");
                            }
                        }
                        break;
                    case HandlerContans.mHandlerConnetFailState:
                        if (!isListenerNull) {
                            Logger.myLog("startAni1 mBleReciveListeners:" + mBleReciveListeners.size() + "false");
                            for (int i = 0; i < mBleReciveListeners.size(); i++) {

                                mBleReciveListeners.get(i).onConnResult(false, mGattCallBack.mIsConnectByUser, mCurrentDevice);
                            }
                        }
                        break;
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
                        if (!isListenerNull) {
                            Logger.myLog("startAni1 mBleReciveListeners:" + mBleReciveListeners.size() + "true");
                            for (int i = 0; i < mBleReciveListeners.size(); i++) {
                                mBleReciveListeners.get(i).onConnResult(true, mGattCallBack.mIsConnectByUser, mCurrentDevice);
                            }
                        }
                        break;
                    case HandlerContans.mHandlerVersion:
                        getDeviceVersion();
                        break;
                    case HandlerContans.mHandlerbattery://电量
                       /* //set_calender();
                        Bundle data3 = msg.getData();
                        BraceletW311VersionResult brandVersionResult = (BraceletW311VersionResult) data3.getSerializable
                                ("DeviceVersion");
                        mDeviceInformationTable.setVersion(brandVersionResult == null ? "" : brandVersionResult
                                .getVersion());
                        Logger.myLog(" DeviceVersion == " + (brandVersionResult == null ? "" : brandVersionResult
                                .getVersion()) + " mDeviceInformationTable == " + mDeviceInformationTable.toString());
                        //1版本号，2是电量
                        ParseData.saveOrUpdateDeviceInfo(mDeviceInformationTable, 1);*/
                        // TODO: 2019/1/7 版本号
//                        WatchDeviceInfo.putString(mContext, WatchDeviceInfo.WATCH_VERSION, watchVersionResult ==null
//                                ? "" : watchVersionResult
//                                .getVersion());
                        // TODO: 2019/3/4 判断是否是第一次绑定,第一次绑定设置
                        //设置所有的设置项
                        if (!isListenerNull) {
                            Logger.myLog("onDeviceSuccess  HandlerContans.mHandlerbattery" + mCurrentDevice);
                            if (mCurrentDevice == null) {
                                return;
                            }
                            for (int i = 0; i < mBleReciveListeners.size(); i++) {
                                Logger.myLog("onDeviceSuccess  HandlerContans.mHandlerbattery2" + mCurrentDevice);
                                mBleReciveListeners.get(i).onBattreyOrVersion(mCurrentDevice);
                            }
                        }
                        break;
                    case HandlerContans.mHandlerRealData:
                        Object[] obj = (Object[]) msg.obj;
                        if (!isListenerNull) {
                            for (int i = 0; i < mBleReciveListeners.size(); i++) {
                                mBleReciveListeners.get(i).receiveData(new BraceletW311RealTimeResult((Integer) obj[0], (Float) obj[1], (Integer) obj[2], (String) obj[3]));
                            }
                        }
                        break;
                    case 0x14:
                        if (!isListenerNull) {
                            Object[] objHr = (Object[]) msg.obj;
                            for (int i = 0; i < mBleReciveListeners.size(); i++) {
                                mBleReciveListeners.get(i).receiveData(new WatchHrHeartResult((Integer) objHr[0], mCurrentDevice.deviceName));
                            }
                        }
                        break;
                }
            }
        };
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
                                notiHandler.sendEmptyMessageDelayed(0x02, 30000);
                                mGattCallBack.enableNotification();
                            }
                            break;
                        case 0x02:
                            disconnect(true);
                            break;
                        case 0x03:
                            break;
                        case 0x04:
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
                        case HandlerContans.mSenderSetBaseTime:
                            set_calender(1);
                            break;
                        case HandlerContans.mSenderSetDate:
                            set_calender(2);
                            break;
                        case HandlerContans.mSenderPhoneName:
                            String inf = (String) msg.obj;
                            set_phone_name(inf);
                            break;
                        case HandlerContans.mSenderMessage:
                            int[] infs = (int[]) msg.obj;
                            handleNotiResponse(infs[0], infs[1]);
                            break;
                        case HandlerContans.mSenderUserInfo:
                            send_userInfo();
                            break;
                        case HandlerContans.mSenderSendVibrateConnected:
                            send_first_sendVibrateConnected();
                            break;
                        case HandlerContans.mSenderGetSedentary:
                            bracelet_get_sedintary();
                            break;
                        case HandlerContans.mSenderGetAlarmList:
                            bracelet_get_alarmlist();
                            break;
                        case HandlerContans.mSenderGetDisplay:
                            bracelet_get_display();
                            break;
                        case HandlerContans.mSenderGetAutoHeart:
                            bracelet_get_utoHeartRateAndTime();
                            break;
                        case HandlerContans.mSenderGetDisturb:
                            bracelet_get_disturb();
                            break;
                        case HandlerContans.mSenderGetRaiseHand:
                            bracelet_get_raiseHand();
                            break;

                    }
                }
            };
        }
    }

    private MediaPlayer mMediaPlayer;
    private Vibrator vibrator1;
    /**
     * 寻找手机
     */
    private Handler findPhoneHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (mMediaPlayer != null && mMediaPlayer.isPlaying()) {
                mMediaPlayer.stop();
                mMediaPlayer.reset();
            }
            if (vibrator1 != null && vibrator1.hasVibrator()) {
                vibrator1.cancel();
            }
            /*Intent intent = new Intent(DialogFindPhone.ACTION_STOP_FINDPHONE);
            sendBroadcast(intent);*/
        }
    };

    public void findMobilePhone() {

        if (mMediaPlayer == null) {
            mMediaPlayer = new MediaPlayer();
        }
        if (vibrator1 == null) {
            vibrator1 = (Vibrator) mContext.getSystemService(Context.VIBRATOR_SERVICE);
        }
        AudioManager mAudioManager = (AudioManager) mContext.getSystemService(Context.AUDIO_SERVICE);  //音量控制,初始化定义
        int maxVolume = mAudioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);//最大音量
        int currentVolume = mAudioManager.getStreamVolume(AudioManager.STREAM_MUSIC);//当前音量
        if (currentVolume == 0) {
            mAudioManager.setStreamVolume(AudioManager.STREAM_MUSIC, maxVolume, 0); //tempVolume:音量绝值
        }
        if (mMediaPlayer.isPlaying()) {
            // stopFindMolibePhone();
        } else {
            //  initFindPhoneDialog();
            try {
                mMediaPlayer.setDataSource(mContext, getSystemDefultRingtoneUri());
                mMediaPlayer.setLooping(true);
                mMediaPlayer.prepare();
            } catch (IllegalStateException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (NullPointerException e) {
                e.printStackTrace();
            }
            findPhoneHandler.sendEmptyMessageDelayed(0x01, 10000);
            mMediaPlayer.start();
            if (ActivityCompat.checkSelfPermission(mContext, Manifest.permission.VIBRATE) != PackageManager
                    .PERMISSION_GRANTED) {
                return;
            } else {

                vibrator1.vibrate(new long[]{700, 300, 700, 300, 700, 300, 700, 300, 700, 300, 700, 300, 700, 300, 700,
                        300, 700, 300, 700, 300}, -1);
            }

        }
    }

    private Uri getSystemDefultRingtoneUri() {
        Uri tp = RingtoneManager.getActualDefaultRingtoneUri(mContext,
                RingtoneManager.TYPE_RINGTONE);
        if (tp == null) {
            tp = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE);
        }
        if (tp == null) {
            tp = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
        }
        return tp;
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
                    final boolean isListenerNull = mBleReciveListeners == null;
                    switch (msg.what) {
                        case HandlerContans.mSyncHandlerSyncDate:

                            int[] inf = (int[]) msg.obj;
                            syncDate(inf[0], inf[1], inf[2]);
                            break;
                        case HandlerContans.mSyncHandlerSyncHrDate:
                            int[] infHr = (int[]) msg.obj;
                            syncHrData(infHr[0], infHr[1], infHr[2]);
                            break;
                        case HandlerContans.mSyncHandlerHrComptely:
                            if (syncHandler.hasMessages(HandlerContans.mSyncHandlerHrTimeOut)) {
                                syncHandler.removeMessages(HandlerContans.mSyncHandlerHrTimeOut);
                            }
                            if (syncHandler.hasMessages(HandlerContans.mSyncHandlerStepTimeOut)) {
                                syncHandler.removeMessages(HandlerContans.mSyncHandlerStepTimeOut);
                            }
                            Logger.myLog("mSyncHandlerHrComptely");
                            //数据同步完成
                            SyncCacheUtils.saveSyncDataTime(mContext);
                            getSetting();
                            if (!isListenerNull) {
                                for (int i = 0; i < mBleReciveListeners.size(); i++) {
                                    mBleReciveListeners.get(i).receiveData(new BraceletW311SyncComplete(BraceletW311SyncComplete.SUCCESS));
                                }
                            }
                            break;
                        case HandlerContans.mSyncHandlerStartSync:
                            sendStartSync();
                            break;
                        case HandlerContans.mSyncHandlerSyncOpenReal:
                            sendOpenReal();
                            break;
                        case HandlerContans.mSyncHandlerComptely:
                            //同步完成
                            Logger.myLog("同步运动数据完成");
                            if (syncHandler.hasMessages(HandlerContans.mSyncHandlerHrTimeOut)) {
                                syncHandler.removeMessages(HandlerContans.mSyncHandlerHrTimeOut);
                            }
                            if (syncHandler.hasMessages(HandlerContans.mSyncHandlerStepTimeOut)) {
                                syncHandler.removeMessages(HandlerContans.mSyncHandlerStepTimeOut);
                            }
                            set_SyncHrData();
                            break;
                        case HandlerContans.mSyncHandlerStepTimeOut:
                            //set_syncData();
                            Logger.myLog("mSyncHandlerStepTimeOut");
                            SyncCacheUtils.clearSysData(mContext);
                            getSetting();
                            //数据无无响应
                            if (mBleReciveListeners != null) {
                                for (int i = 0; i < mBleReciveListeners.size(); i++) {
                                    Logger.myLog("mSyncHandlerStepTimeOut2");
                                    mBleReciveListeners.get(i).receiveData(new BraceletW311SyncComplete(BraceletW311SyncComplete.TIMEOUT));
                                }
                            }
                            break;
                        case HandlerContans.mSyncHandlerHrTimeOut:
                            // set_SyncHrData();
                            Logger.myLog("mSyncHandlerHrTimeOut");
                            //数据无无响应
                            if (!isListenerNull) {
                                for (int i = 0; i < mBleReciveListeners.size(); i++) {
                                    mBleReciveListeners.get(i).receiveData(new BraceletW311SyncComplete(BraceletW311SyncComplete.TIMEOUT));
                                }
                            }

                            break;

                    }
                }
            };
        }
    }

    public void set_calender(int type) {
        if (mGattCallBack != null) {
            mGattCallBack.writeTXCharacteristicItem(sendBaseTime(mContext, type));
        }
    }

    public void syncTodayData() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) + 1;
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        syncDate(year, month, day);

    }

    public void syncTodaySportData() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) + 1;
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        syncDate(year, month, day);
    }


    public void syncTodayHrData() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) + 1;
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        syncHrData(year, month, day);
    }


    //这里需要有个同步超时逻辑如果在30s内同步失败就需要把对话框给隐藏了。

    public void syncDate(int year, int month, int day) {
        Logger.myLog("同步运动数据year:" + year + "month:" + month + "day:" + day + ",mGattCallBack:" + mGattCallBack);
        if (syncHandler.hasMessages(HandlerContans.mSyncHandlerStepTimeOut)) {
            syncHandler.removeMessages(HandlerContans.mSyncHandlerStepTimeOut);
        }
        syncHandler.sendEmptyMessageDelayed(HandlerContans.mSyncHandlerStepTimeOut, SYNC_TIMEOUT);
        if (mGattCallBack != null) {
            String key = year + "_" + month + "_" + day;
            if (cacheRetrySyncDataInfo.containsKey(key)) {
                int retryTimes = cacheRetrySyncDataInfo.get(key);
                Logger.myLog("syncDate cacheRetrySyncDataInfo.containsKey retryTimes:" + retryTimes);
                retryTimes++;
                cacheRetrySyncDataInfo.put(key, retryTimes);
            } else {
                Logger.myLog("cacheRetrySyncDataInfo 第一次执行:");
                cacheRetrySyncDataInfo.clear();
                cacheRetrySyncDataInfo.put(key, 1);
            }
            mGattCallBack.cleardata();
            mGattCallBack.setStartYearMonthDay(year, month, day);
            mGattCallBack.writeTXCharacteristicItem(sendSyncDay(year, month, day));
        }
    }

    public void syncHrData(int year, int month, int day) {
        if (syncHandler.hasMessages(HandlerContans.mSyncHandlerHrTimeOut)) {
            syncHandler.removeMessages(HandlerContans.mSyncHandlerHrTimeOut);
        }
        syncHandler.sendEmptyMessageDelayed(HandlerContans.mSyncHandlerHrTimeOut, SYNC_TIMEOUT);
        Logger.myLog("同步心率数据的year:" + year + "month:" + month + "day:" + day + ",mGattCallBack:" + mGattCallBack);
        if (mGattCallBack != null) {
            String key = year + "_" + month + "_" + day;
            if (cacheRetrySyncHrDataInfo.containsKey(key)) {
                int retryTimes = cacheRetrySyncHrDataInfo.get(key);
                Logger.myLog("syncHrData cacheRetrySyncHrDataInfo.containsKey retryTimes:" + retryTimes);
                retryTimes++;
                cacheRetrySyncHrDataInfo.clear();
                cacheRetrySyncHrDataInfo.put(key, retryTimes);
            } else {
                Logger.myLog("cacheRetrySyncHrDataInfo 第一次执行:");
                cacheRetrySyncHrDataInfo.clear();
                cacheRetrySyncHrDataInfo.put(key, 1);
            }
            mGattCallBack.cleardata();
            mGattCallBack.setStartYearMonthDay(year, month, day);
            mGattCallBack.writeTXCharacteristicItem(queryHeartRateHistoryByDate(year, month, day));
        }

    }

    public void sendOpenReal() {
        if (mGattCallBack != null) {
            mGattCallBack.cleardata();
            mGattCallBack.writeTXCharacteristicItem(openReal());
        }
    }

    public void sendStartSync() {
        if (mGattCallBack != null) {
            mGattCallBack.cleardata();
            mGattCallBack.writeTXCharacteristicItem(sendCmdSync());
        }
    }

    public void get_calender() {
        if (mGattCallBack != null) {
            mGattCallBack.writeTXCharacteristicItem(send_get_calender());
        }
    }

    private void setSetting() {
        sendHandler.sendEmptyMessageDelayed(0x01, 100);
        sendHandler.sendEmptyMessageDelayed(0x02, 300);
        sendHandler.sendEmptyMessageDelayed(0x03, 500);
        sendHandler.sendEmptyMessageDelayed(0x06, 700);

//        sendHandler.sendEmptyMessageDelayed(0x04, 700);//设备暂时是自动睡眠
        sendHandler.sendEmptyMessageDelayed(0x05, 1200);
    }

    public void send_userInfo() {
        if (mGattCallBack != null) {
            mGattCallBack.writeTXCharacteristicItem(sendUserInfo(mContext));
        }
    }

    public void bracelet_wear(boolean isLeft) {
        if (mGattCallBack != null) {
            WristMode wristMode = new WristMode(isLeft);
            mGattCallBack.writeTXCharacteristicItem(setWristMode(wristMode));
        }
    }

    public void bracelet_setSedintaryRemind(List<SedentaryRemind> list) {
        if (mGattCallBack != null) {
            mGattCallBack.writeTXCharacteristicItem(setSedintaryRemind(list));
        }
    }

    public void bracelet_set_display(DisplaySet displaySet) {
        if (mGattCallBack != null) {
            mGattCallBack.writeTXCharacteristicItem(send_setDisplayInterface(displaySet));
        }
    }

    public void bracelet_get_display() {
        if (mGattCallBack != null) {
            mGattCallBack.writeTXCharacteristicItem(send_get_display());
        }
    }

    public void bracelet_get_alarmlist() {
        if (mGattCallBack != null) {
            mGattCallBack.writeTXCharacteristicItem(send_get_AlarmList());
        }
    }


    public void bracelet_get_disturb() {
        if (mGattCallBack != null) {
            mGattCallBack.writeTXCharacteristicItem(get_disturb_cmd());
        }
    }

    public void bracelet_get_raiseHand() {
        if (mGattCallBack != null) {
            mGattCallBack.writeTXCharacteristicItem(get_raiseHand_cmd());
        }
    }

    public void bracelet_get_utoHeartRateAndTime() {

        if (mGattCallBack != null) {
            mGattCallBack.writeTXCharacteristicItem(get_autoHeartRateAndTime_cmd());
        }
    }


    public void bracelet_get_sedintary() {
        if (mGattCallBack != null) {
            mGattCallBack.writeTXCharacteristicItem(send_get_SedintaryRemind());
        }
    }

    public void find_bracelet() {
        if (mGattCallBack != null) {
            mGattCallBack.writeTXCharacteristicItem(findDevice());
        }
    }

    public void isOpenAntiLost(boolean isOpen) {
        if (mGattCallBack != null) {
            if (isOpen) {
                mGattCallBack.writeTXCharacteristicItem(openAntiLost());
            } else {
                mGattCallBack.writeTXCharacteristicItem(closeAntiLost());
            }
        }
    }

    public void getSendentaryRemind() {
        if (mGattCallBack != null) {
            mGattCallBack.writeTXCharacteristicItem(send_get_SedintaryRemind());
        }
    }

    public void setSendSedentaryRemind(List<SedentaryRemind> list) {
        if (mGattCallBack != null) {
            mGattCallBack.writeTXCharacteristicItem(setSedintaryRemind(list));
        }
    }

    public void setIsOpenRaiseHand(boolean isOpen) {
        if (mGattCallBack != null) {
            mGattCallBack.writeTXCharacteristicItem(raiseHand(isOpen));
        }
    }

    public void setRaiseHand(int type, int startHour, int startMin, int endHour, int endMin) {
        if (mGattCallBack != null) {
            mGattCallBack.writeTXCharacteristicItem(raiseHand(type, startHour, startMin, endHour, endMin));
        }
    }

    public void set_AutomaticHeartRateAndTime(boolean isopen, int times) {
        if (mGattCallBack != null) {
            mGattCallBack.writeTXCharacteristicItem(setAutomaticHeartRateAndTime(isopen, times));
        }
    }

    static String name = "";


    public void set_phone(String comming_phone, String names) {
        if (mGattCallBack != null) {
            if (TextUtils.isEmpty(names)) {
                name = comming_phone;
            }
            name = names;
            mGattCallBack.writeTXCharacteristicItem(sendPhoneNum(comming_phone, 0));
        }
    }

    public void set_distrub(boolean open, int startHour, int startMin, int endHour, int endMin) {
        if (mGattCallBack != null) {


            mGattCallBack.writeTXCharacteristicItem(setDisturb(open, startHour, startMin, endHour, endMin));
        }
    }

    public void set_phone_name(String name) {
        if (mGattCallBack != null) {
            mGattCallBack.writeTXCharacteristicItem(sendPhoneNum(name, 1));
        }
    }


    public void set_SyncHrData() {
        String todayYYYYMMDD = TimeUtils.getBefor15dayYYMMDD();

        String lastSyncTime = BleSPUtils.getString(mContext, BleSPUtils.Bracelet_LAST_HR_SYNCTIME, todayYYYYMMDD);
        //"yyyy-MM-dd"
        Logger.myLog("同步心率数据todayYYYYMMDD" + todayYYYYMMDD + "lastSyncTime" + lastSyncTime);
        String[] strings = lastSyncTime.split("-");
        int[] tpi = new int[]{Integer.parseInt(strings[0]), Integer.parseInt(strings[1]), Integer.parseInt(strings[2])};
        Message msgTp = Message.obtain();
        msgTp.obj = tpi;
        msgTp.what = HandlerContans.mSyncHandlerSyncHrDate;
        syncHandler.sendMessageDelayed(msgTp, 150);
    }

    public void set_syncData() {
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (mBleReciveListeners != null) {
                    for (int i = 0; i < mBleReciveListeners.size(); i++) {
                        mBleReciveListeners.get(i).receiveData(new BraceletW311SyncStart());
                    }
                }
            }
        }, 0);


        //
        syncHandler.sendEmptyMessageDelayed(HandlerContans.mSyncHandlerStartSync, 500);
        if (syncHandler.hasMessages(HandlerContans.mSyncHandlerStepTimeOut)) {
            syncHandler.removeMessages(HandlerContans.mSyncHandlerStepTimeOut);
        }
        syncHandler.sendEmptyMessageDelayed(HandlerContans.mSyncHandlerStepTimeOut, SYNC_TIMEOUT);

      /*  String todayYYYYMMDD = TimeUtils.getBefor15dayYYMMDD();
        String lastSyncTime = BleSPUtils.getString(mContext, BleSPUtils.Bracelet_LAST_SYNCTIME, todayYYYYMMDD);
        //"yyyy-MM-dd"
        String[] strings = lastSyncTime.split("-");
        int year = Integer.parseInt(strings[0]);
        int month = Integer.parseInt(strings[1]);
        int day = Integer.parseInt(strings[2]);
        int[] tpi = new int[]{year, month, day};
        Message msgTp = Message.obtain();
        msgTp.obj = tpi;
        msgTp.what = HandlerContans.mSyncHandlerSyncDate;
        syncHandler.sendMessageDelayed(msgTp, 500);


        Calendar calendar = Calendar.getInstance();

        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month - 1);
        calendar.set(Calendar.DAY_OF_MONTH, day);

        int dif = DateUtil.getDifDay(calendar);


        Logger.myLog("同步的年月日" + todayYYYYMMDD + "lastSyncTime:" + lastSyncTime + "dif:" + dif);

        SyncProgressObservable.getInstance().sync(DeviceTimesUtil.getTime(dif, 0), false);*/
    }

    public void set_AutomaticHeartRateAndTime(boolean isopen) {
        if (mGattCallBack != null) {
            mGattCallBack.writeTXCharacteristicItem(setHeartTimingTest(isopen));
        }
    }

    public void set_alarm_list(List<AlarmEntry> list) {
        if (mGattCallBack != null) {
            mGattCallBack.writeTXCharacteristicItem(w311setAlarm(list));
        }
    }

    public void send_first_sendVibrateConnected() {
        if (mGattCallBack != null) {
            mGattCallBack.writeTXCharacteristicItem(sendVibrateConnected());
        }
    }


//发送消息开始方法

    /**
     * send notification
     *
     * @param msg the notification will sync with ble device
     */
    public void sendNotiCmd(NotificationMsg msg) {
        //如果存储消息的list为null创建list
        if (msgVector == null) {
            msgVector = new Vector<>();
        }
        if (IS_DEBUG)
            Log.e(TAG, "***NotiManager.msgVector长度***" + msgVector.size());
        //当消息列表的长度大于等于1，那么移除最后一条，以达到刷新最新的目的
        if (msgVector.size() >= 1) {
            if (IS_DEBUG)
                Log.e(TAG, "**消息列表长度>=15**移除最后一条");
            msgVector.remove(msgVector.size() - 1);
        }
        if (IS_DEBUG)
            Log.e(TAG, "**添加新消息到消息列表中");
        //添加最新的消息到消息list中
        msgVector.add(msg);
        //当消息间隔大于5秒时，判定为此时的消息为最新的一条
        if (System.currentTimeMillis() - startNoti > 5000) {
            if (IS_DEBUG)
                Log.e(TAG, "***两条发送间隔5s,不然就不发送***");
            currentNotiIndex = 0;
        }
        sendNotiCmd();
    }


    int currentNotiIndex;

    /**
     * send notification or sms to ble device
     *
     * @param notiContent
     * @param packageIndex
     * @param notitype
     */
   /* public void sendNotiCmd(byte[] notiContent, int packageIndex, int notitype) {

        byte[] btCmd = new byte[20];
        btCmd[0] = (byte) 0xbe;
        btCmd[1] = (byte) 0x06;
        btCmd[2] = (byte) notitype;
        btCmd[3] = (byte) 0xfe;
        btCmd[4] = (byte) packageIndex;
        if (notiContent != null && notiContent.length <= 15) {
            System.arraycopy(notiContent, 0, btCmd, 5, notiContent.length);
        }
        int length = (notiContent == null ? 0 : notiContent.length);
        if (length < 15 && length >= 0) {
            for (int i = length; i < 15; i++) {
                btCmd[5 + i] = (byte) 0xff;
            }
        }
        startNoti = System.currentTimeMillis();
        //发送命令
        //sendCommand(SEND_DATA_CHAR, mGattService, mBluetoothGatt, btCmd);



    }*/

    long startNoti;
    public static Vector<NotificationMsg> msgVector = new Vector<>();

    /**
     * send notification
     */
    static String TAG = "braceletW311BleManager";
    static boolean IS_DEBUG = true;

    public void sendNotiCmd() {
        if (currentNotiIndex == 0) {
            if (msgVector != null && msgVector.size() > 0) {
                startNoti = System.currentTimeMillis();
                Log.e(TAG, "***开始发送的时间戳***");
                //这儿的意思是永远保持消息列表只有一条消息，取第一条就行了
                NotificationMsg msg = msgVector.get(0);
                byte[] tp = msg.getMsgContent();
                //先发title部分
                //todo reflex客户需要title 和 content衔接在一起
                byte[] ppp = new byte[15];
                if (tp.length < 15) {
                    System.arraycopy(tp, 0, ppp, 0, tp.length);
                } else {
                    System.arraycopy(tp, 0, ppp, 0, 15);
                }
                //Convert back to String
//                String s = new String(bytes);
                if (IS_DEBUG)
                    Log.e(TAG, "**发送title部分" + new String(ppp));
                StringBuilder builder = new StringBuilder();
                for (int i = 0; i < ppp.length; i++) {
                    builder.append(String.format("%02X ", ppp[i]));
                }

                if (IS_DEBUG) {
                    for (int i = 0; i < ppp.length; i++) {
                        builder.append(String.format("%02X", ppp[i]) + " ");
                    }
                }
                if (IS_DEBUG)
                    Log.e(TAG, "**发送title部分**" + builder.toString());
                if (mGattCallBack != null)
                    mGattCallBack.writeTXCharacteristicItem(sendNotiCmd(ppp, 1, msg.getMsgType()));
                currentNotiIndex = 1;
            }
        }
    }

    ///handle notification DE 06 type index ED
    public void handleNotiResponse(int mesgType, int dataIndex) {
        // NotificationEntry entry = NotificationEntry.getInstance(context);
        int type = mesgType;
        //判断信息类别，如果不再范围内，那么index为0
        if (!(type >= 0x12 && type <= 0x2B)) {
            currentNotiIndex = 0;
            return;
        }
        int index = dataIndex;
        //index为1，发完title部分了
        //是否需要发送详情 现在是否
        boolean isDetail = true;
        if (index == 1 && !isDetail) {///not send more info
            byte[] pppp = new byte[]{(byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff, (byte)
                    0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff,
                    (byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff};
            //第二包发ff
            if (mGattCallBack != null) {
                mGattCallBack.writeTXCharacteristicItem(sendNotiCmd(pppp, index + 1, type));
            }

            return;
        } else if (!isDetail) {
            currentNotiIndex = 0;
            removeNotificationMsg();

            sendNotiCmd();
            return;
        }
        if (msgVector != null && msgVector.size() > 0) {
            if (IS_DEBUG)
                Log.e(TAG, "***消息不是空的***");
            NotificationMsg msg = msgVector.get(0);
            byte[] tp = msg.getMsgContent();
            if (index == 1 && isDetail) {
                byte[] ppp = new byte[15];
                System.arraycopy(tp, 15, ppp, 0, 15);

                if (mGattCallBack != null)
                    mGattCallBack.writeTXCharacteristicItem(sendNotiCmd(ppp, index + 1, type));
                if (IS_DEBUG)
                    Log.e(TAG, "**0000发第一包后，发第二包**index== " + (index + 1) + "***内容***" + new String(ppp));
                StringBuilder builder = new StringBuilder();
                for (int i = 0; i < ppp.length; i++) {
                    builder.append(String.format("%02X ", ppp[i]));
                }
                if (IS_DEBUG) {
                    for (int i = 0; i < ppp.length; i++) {
                        builder.append(String.format("%02X", ppp[i]) + " ");
                    }
                    Logger.myLog(DateUtil.dataToString(new Date(), "MM/dd HH:mm:ss") + "**发送index== " +
                            (index + 1) + "***内容***" + new String(ppp) + " hex str" + builder
                            .toString() + "\r\n");
                }
                if (IS_DEBUG)
                    Log.e(TAG, "**0000发第一包后，发第二包**index== " + (index + 1) + "***内容***" + builder.toString());
            } else if (index > 1) {
                //收到第四个包 或者 发送包的最后一个字节是0xff  即发送完成
                if ((index + 1) * 15 > 60 || (tp[index * 15 - 1] == (byte) 0xFF)) {
                    if (IS_DEBUG)
                        Log.e(TAG, "**22222发第四包后，进入下一个消息发送**");
                    currentNotiIndex = 0;
                    removeNotificationMsg();
                    sendNotiCmd();
                } else {
                    byte[] nn = new byte[15];
                    System.arraycopy(tp, index * 15, nn, 0, nn.length);
                    if (mGattCallBack != null) {
                        mGattCallBack.writeTXCharacteristicItem(sendNotiCmd(nn, index + 1, type));
                    }
                    if (IS_DEBUG)
                        Log.e(TAG, "111111**发第" + index + "一包后，发第" + (index + 1) + "包" + "***内容***" + new String
                                (nn));
                    StringBuilder builder = new StringBuilder();
                    for (int i = 0; i < nn.length; i++) {
                        builder.append(String.format("%02X ", nn[i]));
                    }
                    if (IS_DEBUG) {
                        for (int i = 0; i < nn.length; i++) {
                            builder.append(String.format("%02X", nn[i]) + " ");
                        }
                        Logger.myLog(DateUtil.dataToString(new Date(), "MM/dd HH:mm:ss") + "**发送index== " +
                                (index + 1) + "***内容***" + new String(nn) + " hex str" +
                                builder.toString() + "\r\n");
                    }
                    if (IS_DEBUG)
                        Log.e(TAG, "111111**发第" + index + "一包后，发第" + (index + 1) + "包" + "***内容***" + builder
                                .toString());
                }
            }
        } else {
            if (IS_DEBUG)
                Log.e(TAG, "***消息列表是空的***");
            currentNotiIndex = 0;
            removeNotificationMsg();
            sendNotiCmd();
        }
    }

    private void removeNotificationMsg() {
        if (msgVector != null && msgVector.size() > 0) {
            msgVector.remove(0);
        }
    }


}
