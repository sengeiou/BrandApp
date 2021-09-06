package com.isport.blelibrary.managers;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import com.google.gson.Gson;
import com.isport.blelibrary.db.action.sleep.Sleep_Sleepace_DataModelAction;
import com.isport.blelibrary.db.parse.ParseData;
import com.isport.blelibrary.db.table.DeviceInformationTable;
import com.isport.blelibrary.db.table.sleep.Sleep_Sleepace_DataModel;
import com.isport.blelibrary.db.table.sleep.Sleep_Sleepace_SleepNoticeModel;
import com.isport.blelibrary.deviceEntry.impl.SleepDevice;
import com.isport.blelibrary.result.impl.sleep.SleepBatteryResult;
import com.isport.blelibrary.result.impl.sleep.SleepCollectionStatusResult;
import com.isport.blelibrary.result.impl.sleep.SleepEnvironmentalDataResult;
import com.isport.blelibrary.result.impl.sleep.SleepHistoryDataResult;
import com.isport.blelibrary.result.impl.sleep.SleepHistoryDataResultList;
import com.isport.blelibrary.result.impl.sleep.SleepOriginalDataResult;
import com.isport.blelibrary.result.impl.sleep.SleepRealTimeDataResult;
import com.isport.blelibrary.result.impl.sleep.SleepSetAutoCollectionResult;
import com.isport.blelibrary.result.impl.sleep.SleepStartCollectionResult;
import com.isport.blelibrary.result.impl.sleep.SleepVersionResult;
import com.isport.blelibrary.utils.Constants;
import com.isport.blelibrary.utils.HistoryDataComparator;
import com.isport.blelibrary.utils.Logger;
import com.isport.blelibrary.utils.TimeUtils;
import com.isport.blelibrary.utils.Utils;
import com.sleepace.sdk.core.heartbreath.domain.Analysis;
import com.sleepace.sdk.core.heartbreath.domain.BatteryBean;
import com.sleepace.sdk.core.heartbreath.domain.Detail;
import com.sleepace.sdk.core.heartbreath.domain.EnvironmentData;
import com.sleepace.sdk.core.heartbreath.domain.HistoryData;
import com.sleepace.sdk.core.heartbreath.domain.LoginBean;
import com.sleepace.sdk.core.heartbreath.domain.OriginalData;
import com.sleepace.sdk.core.heartbreath.domain.RealTimeData;
import com.sleepace.sdk.core.heartbreath.domain.Summary;
import com.sleepace.sdk.interfs.IConnectionStateCallback;
import com.sleepace.sdk.interfs.IDeviceManager;
import com.sleepace.sdk.interfs.IMonitorManager;
import com.sleepace.sdk.interfs.IResultCallback;
import com.sleepace.sdk.manager.CONNECTION_STATE;
import com.sleepace.sdk.manager.CallbackData;
import com.sleepace.sdk.reston.RestOnHelper;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.isport.blelibrary.utils.Constants.COMMON_DURATION;

/**
 * @Author
 * @Date 2018/10/11
 * @Fuction
 */

public class SleepBleManager extends BaseManager {

    private RestOnHelper mRestOnHelper;

    public SleepBleManager() {
    }

    public static SleepBleManager instance;

    public static SleepBleManager getInstance(Context context) {
        if (instance == null) {
            synchronized (ScaleBleManager.class) {
                if (instance == null) {
                    instance = new SleepBleManager();
                    instance.init(context);
                }
            }
        }
        return instance;
    }
    public static SleepBleManager getInstance() {
        if (instance == null) {
            synchronized (ScaleBleManager.class) {
                if (instance == null) {
                    instance = new SleepBleManager();
                }
            }
        }
        return instance;
    }

    public void init(Context mContext) {
        super.init(mContext);
        initHandler();
        mRestOnHelper = RestOnHelper.getInstance(mContext);
        mRestOnHelper.addConnectionStateCallback(mIConnectionStateCallback);
    }

    private void initHandler() {
        final boolean isListenerNull = mBleReciveListeners == null;
        mHandler = new Handler(mContext.getMainLooper()) {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                switch (msg.what) {
                    case 0:
                        // TODO: 2019/1/7 device ID
//                        SleepDeviceInfo.putString(mContext, SleepDeviceInfo.SLEEP_DEVICEID, mCurrentDevice
//                                .getDeviceName());
                        if (mDeviceInformationTable == null) {
                            mDeviceInformationTable = new DeviceInformationTable();
                        }
                        mDeviceInformationTable.setMac(mCurrentDevice.getAddress());
                        mDeviceInformationTable.setDeviceId(mCurrentDevice.getDeviceName());
                        ParseData.saveOrUpdateDeviceInfo(mDeviceInformationTable, -1);
//                        ParseData.saveDeviceType(IDeviceType.TYPE_SLEEP);
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
                        Bundle data1 = msg.getData();
                        SleepBatteryResult sleepBattery = (SleepBatteryResult) data1.getSerializable
                                ("SleepBattery");
                        // TODO: 2019/1/7 电量
                        mDeviceInformationTable.setBattery(sleepBattery == null ? 0 : sleepBattery.getQuantity());
                        ParseData.saveOrUpdateDeviceInfo(mDeviceInformationTable, 0);
//                        SleepDeviceInfo.putInt(mContext, SleepDeviceInfo.SLEEP_BATTERY, sleepBattery.getQuantity());
                        if (!isListenerNull)
                            for (int i = 0; i < mBleReciveListeners.size(); i++) {
                                mBleReciveListeners.get(i).receiveData(sleepBattery);
                            }
                        break;
                    case 3:
                        Bundle data2 = msg.getData();
                        SleepVersionResult sleepVersionResult = (SleepVersionResult) data2.getSerializable
                                ("DeviceVersion");
                        // TODO: 2019/1/7 版本号
//                        SleepDeviceInfo.putString(mContext, SleepDeviceInfo.SLEEP_VERSION, sleepVersionResult
//                                .getVersion());
                        mDeviceInformationTable.setVersion(sleepVersionResult == null ? "" : sleepVersionResult
                                .getVersion());
                        ParseData.saveOrUpdateDeviceInfo(mDeviceInformationTable, 1);
                        if (!isListenerNull)
                            for (int i = 0; i < mBleReciveListeners.size(); i++) {
                                mBleReciveListeners.get(i).receiveData(sleepVersionResult);
                            }
                        break;
                    case 4:
                        Bundle data3 = msg.getData();
                        if (!isListenerNull)
                            for (int i = 0; i < mBleReciveListeners.size(); i++) {
                                mBleReciveListeners.get(i).receiveData((SleepCollectionStatusResult)
                                        data3.getSerializable
                                                ("CollectionStatus"));
                            }
                        break;
                    case 5:
                        Bundle data4 = msg.getData();
                        if (!isListenerNull)
                            for (int i = 0; i < mBleReciveListeners.size(); i++) {
                                mBleReciveListeners.get(i).receiveData((SleepRealTimeDataResult) data4.getSerializable
                                        ("RealTimeData"));
                            }
                        break;
                    case 6:
                        Bundle data5 = msg.getData();
                        if (!isListenerNull)
                            for (int i = 0; i < mBleReciveListeners.size(); i++) {
                                mBleReciveListeners.get(i).receiveData((SleepOriginalDataResult) data5.getSerializable
                                        ("OriginalData"));
                            }
                        break;
                    case 7:
                        Bundle data6 = msg.getData();
                        if (!isListenerNull)
                            for (int i = 0; i < mBleReciveListeners.size(); i++) {
                                mBleReciveListeners.get(i).receiveData((SleepEnvironmentalDataResult)
                                        data6.getSerializable
                                                ("EnvironmentalData"));
                            }
                        break;
                    case 8:
                        Bundle data7 = msg.getData();
                        if (!isListenerNull)
                            for (int i = 0; i < mBleReciveListeners.size(); i++) {
                                mBleReciveListeners.get(i).receiveData((SleepStartCollectionResult)
                                        data7.getSerializable
                                                ("startCollection"));
                            }
                        break;
                    case 9:
                        ArrayList<SleepHistoryDataResult> sleepHistoryDataResults =
                                (ArrayList<SleepHistoryDataResult>) msg.obj;
                        if (!isListenerNull)
                            for (int i = 0; i < mBleReciveListeners.size(); i++) {
                                mBleReciveListeners.get(i).receiveData(new SleepHistoryDataResultList
                                        (sleepHistoryDataResults));
                            }
                        break;
                    case 0x0a:
                        Bundle data9 = msg.getData();
                        if (!isListenerNull)
                            for (int i = 0; i < mBleReciveListeners.size(); i++) {
                                mBleReciveListeners.get(i).receiveData((SleepSetAutoCollectionResult)
                                        data9.getSerializable
                                                ("setAutoCollection"));
                            }
                        break;
                    default:
                        break;
                }
            }
        };
    }

    /**
     * 连接状态的回调
     *
     * @param iDeviceManager
     * @param connection_state
     */
    private IConnectionStateCallback mIConnectionStateCallback = new IConnectionStateCallback() {
        @Override
        public void onStateChanged(IDeviceManager iDeviceManager, CONNECTION_STATE connection_state) {
            if (connection_state == CONNECTION_STATE.CONNECTED) {
                Logger.myLog(" Sleepace 连接成功 ");
                mHandler.sendEmptyMessage(0x00);
            } else if (connection_state == CONNECTION_STATE.DISCONNECT) {
                Logger.myLog(" Sleepace 连接断开 ");
                mHandler.sendEmptyMessage(0x01);
            }
        }
    };

    public void login(SleepDevice device, String deviceCode, int useerID) {
        setCurrentDevice(device);
        mRestOnHelper.login(device.deviceName, device.address, deviceCode, 100, Constants.CONNECT_DURATION,
                mIDataCallback);
    }

    //****************************************************睡眠带部分******************************************************//

    /**
     * 获取版本号和deviceId的回调,连接成功后会回调
     */
    private static IResultCallback<LoginBean> mIDataCallback = new IResultCallback<LoginBean>() {

        @Override
        public void onResultCallback(CallbackData<LoginBean> callbackData) {
            LoginBean loginBean = callbackData.getResult();
            //成功是status 0 失败是 1
            if (checkStatus(callbackData)) {
                Logger.myLog(" CallbackData " + callbackData.toString());
            } else {
                Logger.myLog(" 连接失败了 ");
            }
            //type为接口类型
//            CallbackData CallbackData{ status=0, type=1000, result=LoginBean [hardwareVersion=Z50,
// deviceId=envurpfrld693]}
        }
    };

    public void disconnect() {
//        clearCurrentDevice();
        mRestOnHelper.disconnect();
    }

    public void getBattery() {
        mRestOnHelper.getBattery(COMMON_DURATION, new IResultCallback<BatteryBean>() {
            @Override
            public void onResultCallback(CallbackData<BatteryBean> callbackData) {
                if (checkStatus(callbackData)) {
                    BatteryBean result = callbackData.getResult();
                    Logger.myLog(result.toString());
                    Message message = new Message();
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("SleepBattery", new SleepBatteryResult(result.getChargingState(),
                            result.getQuantity()));
                    message.setData(bundle);
                    message.what = 0x02;
                    mHandler.sendMessage(message);
                }
            }
        });
    }

    public void getDeviceVersion() {
        mRestOnHelper.getDeviceVersion(COMMON_DURATION, new IResultCallback<String>() {
            @Override
            public void onResultCallback(CallbackData<String> callbackData) {
                if (checkStatus(callbackData)) {
                    String result = callbackData.getResult();
                    Logger.myLog("getDeviceVersion " + result.toString());
                    Message message = new Message();
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("DeviceVersion", new SleepVersionResult(result));
                    message.setData(bundle);
                    message.what = 0x03;
                    mHandler.sendMessage(message);
                }
            }
        });
    }

    public void setAutoCollection(final boolean enable, final int hour, final int minute, final int repeat) {
        mRestOnHelper.setAutoCollection(enable, hour, minute, repeat, COMMON_DURATION, new IResultCallback<Void>() {
            @Override
            public void onResultCallback(CallbackData<Void> callbackData) {
                Message message = new Message();
                Bundle bundle = new Bundle();
                if (checkStatus(callbackData)) {
                    Logger.myLog("设置自动监测成功 deviceId== " + mCurrentDevice.deviceName + " mUserId == " + mUserId);
                    Sleep_Sleepace_SleepNoticeModel sleep_sleepace_sleepNoticeModel = new
                            Sleep_Sleepace_SleepNoticeModel();
                    sleep_sleepace_sleepNoticeModel.setDeviceId(mCurrentDevice.deviceName);
                    sleep_sleepace_sleepNoticeModel.setIsOpen(enable);
                    sleep_sleepace_sleepNoticeModel.setRepeat(repeat);
                    sleep_sleepace_sleepNoticeModel.setSleepNoticeTime(hour + ":" + (minute < 10 ? ("0" + minute) :
                            minute));
                    sleep_sleepace_sleepNoticeModel.setUserId(mUserId);
                    ParseData.saveOrUpdateSleepaceAutoCollection(sleep_sleepace_sleepNoticeModel);
                    bundle.putSerializable("setAutoCollection", new SleepSetAutoCollectionResult(true));
                } else {
                    bundle.putSerializable("setAutoCollection", new SleepSetAutoCollectionResult(false));
                }
                message.setData(bundle);
                message.what = 0x0a;
                mHandler.sendMessage(message);
            }
        });
    }

    public void startCollection() {
        mRestOnHelper.startCollection(COMMON_DURATION, new IResultCallback<Void>() {
            @Override
            public void onResultCallback(CallbackData<Void> callbackData) {
                boolean reslut;
                if (checkStatus(callbackData)) {
                    Logger.myLog("开始采集成功");
                    reslut = true;
                } else {
                    reslut = false;
                }
                Message message = new Message();
                Bundle bundle = new Bundle();
                bundle.putSerializable("startCollection", new SleepStartCollectionResult(reslut));
                message.setData(bundle);
                message.what = 0x08;
                mHandler.sendMessage(message);
            }
        });
    }

    public boolean stopCollection() {
        final boolean[] result = {false};
        mRestOnHelper.stopCollection(COMMON_DURATION, new IResultCallback<Void>() {
            @Override
            public void onResultCallback(CallbackData<Void> callbackData) {
                if (checkStatus(callbackData)) {
                    Logger.myLog("关闭采集成功");
                    result[0] = true;
                } else {
                    result[0] = false;
                }
            }
        });
        return result[0];
    }

    public void getCollectionStatus() {
        mRestOnHelper.getCollectionStatus(COMMON_DURATION, new IResultCallback<Byte>() {
            @Override
            public void onResultCallback(CallbackData<Byte> callbackData) {
                if (checkStatus(callbackData)) {
                    Byte result = callbackData.getResult();
                    Logger.myLog("getCollectionStatus " + Utils.byteToInt(result) + " " + (Utils.byteToInt(result) ==
                            0 ? "空闲状态" : "采集中"));
                    Message message = new Message();
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("CollectionStatus", new SleepCollectionStatusResult(result));
                    message.setData(bundle);
                    message.what = 0x04;
                    mHandler.sendMessage(message);
                }
            }
        });
    }

    public boolean startRealTimeData() {
        final boolean[] reault = {false};
        mRestOnHelper.startRealTimeData(COMMON_DURATION, new IResultCallback<RealTimeData>() {
            @Override
            public void onResultCallback(CallbackData<RealTimeData> callbackData) {
                if (checkStatus(callbackData)) {
                    if (callbackData.getCallbackType() == IMonitorManager.METHOD_REALTIME_DATA_OPEN) {
                        //打开实时成功
                        Logger.myLog("开启实时监测成功 ");
                        reault[0] = false;
                    }/*else if(cd.getType() == IMonitorManager.TYPE_SLEEP_STATUS){//睡眠状态，只有状态发生变化时，才会推送
                                RealTimeData realTimeData = (RealTimeData) cd.getResult();
								if(realTimeData.getSleepFlag() == 1){//入睡
									tvSleepStatus.setText(R.string.sleep_);
									printLog(getString(R.string.get_sleep, getString(R.string.sleep_)));
								}else if(realTimeData.getWakeFlag() == 1){//清醒
									tvSleepStatus.setText(R.string.wake_);
									printLog(getString(R.string.get_sleep, getString(R.string.wake_)));
								}else{
									tvSleepStatus.setText(null);
									printLog(getString(R.string.get_sleep, getString(R.string.sleep_) +":" +
									realTimeData.getSleepFlag()+","+getString(R.string.wake_)+":"+realTimeData
									.getWakeFlag()));
								}
							}*/ else if (callbackData.getCallbackType() == IMonitorManager.METHOD_REALTIME_DATA) {
                        //实时数据
                        RealTimeData result = callbackData.getResult();
                        //Logger.myLog("返回实时数据 " + result.toString());
                        Message message = new Message();
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("RealTimeData", new SleepRealTimeDataResult(result.getHeartRate(),
                                result.getBreathRate(),
                                result.getStatus(), result
                                .getStatusValue(),
                                result.getSleepFlag(),
                                result.getWakeFlag()));
                        message.setData(bundle);
                        message.what = 0x05;
                        mHandler.sendMessage(message);
//                        RealTimeData realTimeData = callbackData.getResult();
//                        int sleepStatus = realTimeData.getStatus();
//                        int statusRes = getSleepStatus(sleepStatus);
//                        if (statusRes > 0) {
//                            tvSleepStatus.setText(statusRes);
//                        } else {
//                            tvSleepStatus.setText(null);
//                        }
//                        if (sleepStatus == SleepStatusType.SLEEP_LEAVE) {//离床
//                            tvHeartRate.setText("--");
//                            tvBreathRate.setText("--");
//                        } else {
//                            tvHeartRate.setText(realTimeData.getHeartRate() + getString(R.string.unit_heart));
//                            tvBreathRate.setText(realTimeData.getBreathRate() + getString(R.string.unit_respiration));
//                        }
                    } else {
                        reault[0] = false;
                    }
                }
            }
        });
        return reault[0];
    }

    public boolean stopRealTimeData() {
        final boolean[] reault = {false};
        mRestOnHelper.stopRealTimeData(COMMON_DURATION, new IResultCallback<Void>() {
            @Override
            public void onResultCallback(CallbackData<Void> callbackData) {
                if (checkStatus(callbackData)) {
                    //demo中未判断
//                    if (callbackData.getCallbackType() == IMonitorManager.METHOD_REALTIME_DATA_CLOSE) {
//                    }
                    Logger.myLog("结束实时监测成功");
                    reault[0] = true;
                } else {
                    reault[0] = false;
                }
            }
        });
        return reault[0];
    }

    public boolean startOriginalData() {
        final boolean[] reault = {false};
        mRestOnHelper.startOriginalData(COMMON_DURATION, new IResultCallback<OriginalData>() {
            @Override
            public void onResultCallback(CallbackData<OriginalData> callbackData) {
                if (checkStatus(callbackData)) {
                    if (callbackData.getCallbackType() == IMonitorManager.METHOD_RAW_DATA_OPEN) {//接口执行结果回调
                        Logger.myLog("开始查看监测信号强度成功");
                        reault[0] = true;
                    } else if (callbackData.getCallbackType() == IMonitorManager.METHOD_RAW_DATA) {//原始数据回调
                        if (checkStatus(callbackData)) {
                            OriginalData data = callbackData.getResult();
                            int len = data.getHeartRate() == null ? 0 : data.getHeartRate().length;
                            for (int i = 0; i < len; i++) {
                                if (i % 2 == 0) {
                                    continue;
                                }
//						LogUtil.log(TAG+" rawDataCB i:" + i +",x:" + x+",heartRate:" +data.getHeartRate()[i]+",
// breathRate:" + data.getBreathRate()[i]);
//                                heartView.add(new PointF(x, data.getHeartRate()[i]));

//                                breathView.add(new PointF(x, data.getBreathRate()[i]));
//                                x+=space;
                            }
                            OriginalData result = callbackData.getResult();
                            float[] heartRate = result.getHeartRate();
                            float[] breathRate = result.getBreathRate();
                            int[] rawData = result.getRawData();
                            Logger.myLog("返回的监测信号强度 getBreathRate " + breathRate.toString() + " getHeartRate " +
                                    heartRate.toString() + " getRawData " + rawData.toString());
                            Message message = new Message();
                            Bundle bundle = new Bundle();
                            bundle.putSerializable("OriginalData", new SleepOriginalDataResult(result));
                            message.setData(bundle);
                            message.what = 0x06;
                            mHandler.sendMessage(message);
                        }
                    } else {
                        reault[0] = false;
                    }
                }
            }
        });
        return reault[0];
    }

    public boolean stopOriginalData() {
        final boolean[] reault = {false};
        mRestOnHelper.stopOriginalData(COMMON_DURATION, new IResultCallback<Void>() {
            @Override
            public void onResultCallback(CallbackData<Void> callbackData) {
                if (checkStatus(callbackData)) {
                    Logger.myLog("停止查看监测信号强度成功");
                    reault[0] = true;
                } else {
                    reault[0] = false;
                }
            }
        });
        return reault[0];
    }

    public void historyDownload(int startTime, int endTime, int sex) {
//        Calendar cal = Calendar.getInstance();
//        endTime = (int) (cal.getTimeInMillis() / 1000);
        mRestOnHelper.historyDownload(startTime, endTime, sex, new IResultCallback<List<HistoryData>>() {
            @Override
            public void onResultCallback(CallbackData<List<HistoryData>> callbackData) {
                if (checkStatus(callbackData)) {
                    List<HistoryData> list = callbackData.getResult();
                    Logger.myLog(" historyDownload size:" + list.size());
                    if (list.size() > 0) {
                        ArrayList<SleepHistoryDataResult> sleepHistoryDataResults = new ArrayList<>();
                        Collections.sort(list, new HistoryDataComparator());
                        dealSleepHistoryData(list, sleepHistoryDataResults);
                        Message message = new Message();
                        message.obj = sleepHistoryDataResults;
                        message.what = 0x09;
                        mHandler.sendMessage(message);
                    } else {
                        Message message = new Message();
                        message.obj = null;
                        message.what = 0x09;
                        mHandler.sendMessage(message);
                    }
                }
            }
        });
    }

    private void dealSleepHistoryData(List<HistoryData> list, ArrayList<SleepHistoryDataResult>
            sleepHistoryDataResults) {
        for (int i = 0; i < list.size(); i++) {
            HistoryData historyData = list.get(i);
            int sleepDuration = 0;
            if (historyData.getAnalysis().getReportFlag() == 1) {//长报告
                sleepDuration = historyData.getAnalysis().getDuration();
            } else {//短报告
                sleepDuration = historyData.getSummary().getRecordCount();
            }
            //小于10分钟的是脏数据
            if (sleepDuration >= 10) {
                Analysis analy = historyData.getAnalysis();
                com.isport.blelibrary.result.entry.Analysis analysis = new com.isport.blelibrary
                        .result.entry.Analysis
                        (analy.getAvgBreathRate(), analy.getAvgHeartRate(), analy
                                .getFallAlseepAllTime(),
                                analy.getWakeAndLeaveBedBeforeAllTime(), analy.getLeaveBedTimes(), analy
                                .getTrunOverTimes(),
                                analy.getBodyMovementTimes(), analy.getHeartBeatPauseTimes(), analy
                                .getBreathPauseTimes(),
                                analy.getDeepSleepPerc(), analy.getInSleepPerc(), analy.getLightSleepPerc(),
                                analy
                                        .getWakeSleepPerc(),
                                sleepDuration, analy.getWakeTimes(), analy.getLightSleepAllTime(), analy
                                .getInSleepAllTime(),
                                analy.getDeepSleepAllTime(), analy.getWakeTimes(), analy
                                .getBreathPauseAllTime(),
                                analy.getHeartBeatPauseAllTime(),
                                analy.getLeaveBedTimes(), analy.getMaxHeartBeatRate(), analy
                                .getMaxBreathRate(),
                                analy.getMinHeartBeatRate(), analy.getMinBreathRate(), analy
                                .getHeartBeatRateFastAllTime(),
                                analy.getHeartBeatRateSlowAllTime(),
                                analy.getBreathRateFastAllTime(), analy.getBreathRateSlowAllTime(), analy
                                .getSleepScore(),
                                analy.getSleepCurveArray(), analy.getSleepCurveStatusArray(),
                                analy.getAlgorithmVer(),
                                analy.getFallsleepTimeStamp(), analy.getWakeupTimeStamp(), analy
                                .getReportFlag(),
                                analy.getBreathRateStatusAry(),
                                analy.getHeartRateStatusAry(), analy.getLeftBedStatusAry(), analy
                                .getTurnOverStatusAry(),
                                analy.getMd_body_move_decrease_scale(), analy.getMd_leave_bed_decrease_scale(),
                                analy.getMd_wake_cnt_decrease_scale(), analy.getMd_start_time_decrease_scale(),
                                analy.getMd_fall_asleep_time_decrease_scale(), analy
                                .getMd_perc_deep_decrease_scale(),
                                analy.getMd_sleep_time_decrease_scale(), analy
                                .getMd_sleep_time_increase_scale(),
                                analy.getMd_breath_stop_decrease_scale(), analy
                                .getMd_heart_stop_decrease_scale(),
                                analy.getMd_heart_low_decrease_scale(), analy
                                .getMd_heart_high_decrease_scale(),
                                analy.getMd_breath_low_decrease_scale(),
                                analy.getMd_breath_high_decrease_scale(), analy
                                .getMd_perc_effective_sleep_decrease_scale());
                Detail detail1 = historyData.getDetail();
                com.isport.blelibrary.result.entry.Detail detail = new com.isport.blelibrary.result
                        .entry.Detail
                        (detail1.getBreathRate(), detail1.getHeartRate(), detail1.getStatus(),
                                detail1.getStatusValue(), detail1.getHumidity(), detail1.getTemp());
                Summary summary = historyData.getSummary();
                com.isport.blelibrary.result.entry.Summary summary1 = new com.isport.blelibrary
                        .result.entry.Summary
                        (summary.getRecordCount(), summary.getStartTime(), summary.getStopMode());
                sleepHistoryDataResults.add(new SleepHistoryDataResult(analysis, detail,
                        summary1));
                Gson gson = new Gson();
                Sleep_Sleepace_DataModel sleep_sleepace_dataModel = new Sleep_Sleepace_DataModel();
                sleep_sleepace_dataModel.setDeviceId(mCurrentDevice.deviceName);
                sleep_sleepace_dataModel.setUserId(mUserId);
                //将时间转换成了年月日，不带十分秒
                sleep_sleepace_dataModel.setDateStr(TimeUtils.unixTimeToBeijingTime
                        (summary1.startTime));
                sleep_sleepace_dataModel.setReportId("0");
                sleep_sleepace_dataModel.setTimestamp(summary1.startTime);
                sleep_sleepace_dataModel.setTrunOverStatusAry(gson.toJson(analysis.turnOverStatusAry));
                sleep_sleepace_dataModel.setBreathRateAry(gson.toJson(detail.breathRate));
                sleep_sleepace_dataModel.setHeartRateAry(gson.toJson(detail.heartRate));
                sleep_sleepace_dataModel.setAverageBreathRate(analysis.averageBreathRate);
                sleep_sleepace_dataModel.setAverageHeartBeatRate(analysis.averageHeartBeatRate);
                sleep_sleepace_dataModel.setLeaveBedTimes(analysis.leaveBedTimes);
                sleep_sleepace_dataModel.setTurnOverTimes(analysis.trunOverTimes);
                sleep_sleepace_dataModel.setBodyMovementTimes(analysis.bodyMovementTimes);
                sleep_sleepace_dataModel.setHeartBeatPauseTimes(analysis.heartBeatPauseTimes);
                sleep_sleepace_dataModel.setBreathPauseTimes(analysis.breathPauseTimes);
                sleep_sleepace_dataModel.setHeartBeatPauseAllTime(analysis.heartBeatPauseAllTime);
                sleep_sleepace_dataModel.setBreathPauseAllTime(analysis.breathPauseAllTime);
                sleep_sleepace_dataModel.setLeaveBedAllTime(analysis.leaveBedAllTime);
                sleep_sleepace_dataModel.setMaxHeartBeatRate(analysis.maxHeartBeatRate);
                sleep_sleepace_dataModel.setMinHeartBeatRate(analysis.minHeartBeatRate);
                sleep_sleepace_dataModel.setMaxBreathRate(analysis.maxBreathRate);
                sleep_sleepace_dataModel.setMinBreathRate(analysis.minBreathRate);
                sleep_sleepace_dataModel.setHeartBeatRateFastAllTime(analysis.heartBeatRateFastAllTime);
                sleep_sleepace_dataModel.setHeartBeatRateSlowAllTime(analysis.heartBeatRateSlowAllTime);
                sleep_sleepace_dataModel.setBreathRateFastAllTime(analysis.breathRateFastAllTime);
                sleep_sleepace_dataModel.setBreathRateSlowAllTime(analysis.breathRateSlowAllTime);
                sleep_sleepace_dataModel.setFallAlseepAllTime(analysis.fallAlseepAllTime);
                sleep_sleepace_dataModel.setSleepDuration(analysis.duration);
                sleep_sleepace_dataModel.setDeepSleepAllTime(analysis.deepSleepAllTime);
                sleep_sleepace_dataModel.setDeepSleepPercent(analysis.deepSleepPerc);
                Sleep_Sleepace_DataModel sleep_sleepace_dataModelByDeviceIdAndTimeTamp =
                        Sleep_Sleepace_DataModelAction
                                .findSleep_Sleepace_DataModelByDeviceIdAndTimeTamp(mUserId,
                                        summary1.startTime);
                if (sleep_sleepace_dataModelByDeviceIdAndTimeTamp == null) {
                    ParseData.saveSleep_Sleepace_DataModel(sleep_sleepace_dataModel);
                }
            }
        }
    }


    public void getEnvironmentalData() {
        mRestOnHelper.getEnvironmentalData(COMMON_DURATION, new IResultCallback<EnvironmentData>() {
            @Override
            public void onResultCallback(CallbackData<EnvironmentData> callbackData) {
                if (checkStatus(callbackData)) {
                    EnvironmentData result = callbackData.getResult();
                    Logger.myLog(result.toString());
                    Message message = new Message();
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("EnvironmentalData", new SleepEnvironmentalDataResult(result.getTemperature
                            (), result.getHumidity()));
                    message.setData(bundle);
                    message.what = 0x07;
                    mHandler.sendMessage(message);
                }
            }
        });
    }

    public void upgradeDevice1() {
        mRestOnHelper.upgradeDevice(1, 1, new File(""), new IResultCallback<Integer>() {
            @Override
            public void onResultCallback(CallbackData<Integer> callbackData) {
                if (checkStatus(callbackData)) {

                }
            }
        });
    }

    public void upgradeDevice2() {
        try {
            mRestOnHelper.upgradeDevice(1, 1, new FileInputStream(""), new IResultCallback<Integer>() {
                @Override
                public void onResultCallback(CallbackData<Integer> callbackData) {
                    if (checkStatus(callbackData)) {

                    }
                }
            });
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void exit() {
        disconnect();
    }
}
