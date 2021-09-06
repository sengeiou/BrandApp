package com.isport.blelibrary.bluetooth.callbacks;

import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattDescriptor;
import android.bluetooth.BluetoothGattService;
import android.content.Context;
import android.os.SystemClock;
import android.text.TextUtils;
import android.util.Log;

import com.isport.blelibrary.db.action.bracelet_w311.Bracelet_W311_DeviceInfoModelAction;
import com.isport.blelibrary.db.action.bracelet_w311.Bracelet_W311_SettingModelAction;
import com.isport.blelibrary.db.parse.Parse311Data;
import com.isport.blelibrary.db.parse.ParseData;
import com.isport.blelibrary.db.table.bracelet_w311.Bracelet_W311_WearModel;
import com.isport.blelibrary.deviceEntry.impl.BaseDevice;
import com.isport.blelibrary.entry.DeviceInfo;
import com.isport.blelibrary.interfaces.BluetoothListener;
import com.isport.blelibrary.interfaces.W311BluetoothListener;
import com.isport.blelibrary.managers.BaseManager;
import com.isport.blelibrary.observe.SyncProgressObservable;
import com.isport.blelibrary.utils.BleSPUtils;
import com.isport.blelibrary.utils.Constants;
import com.isport.blelibrary.utils.DateUtil;
import com.isport.blelibrary.utils.DeviceTimesUtil;
import com.isport.blelibrary.utils.Logger;
import com.isport.blelibrary.utils.ThreadPoolUtils;
import com.isport.blelibrary.utils.TimeUtils;
import com.isport.blelibrary.utils.Utils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * @创建者 bear
 * @创建时间 2019/4/18 9:39
 * @描述
 */
public class BraceletW311GattCallBack extends BaseGattCallback {


    private String TAG = BraceletW311GattCallBack.class.getCanonicalName();

    private List<byte[]> mCache;
    List<byte[]> result = new ArrayList<>();
    private List<byte[]> mHeartRateCache;

    private int startYear, startMonth, startDay, dayCount;
    private int heartRateStartYear, heartRateStartMonth, heartRateStartDay, heartRateDayCount;


    public void setStartYearMonthDay(int startYear, int startMonth, int startDay) {
        this.startYear = startYear;
        this.startMonth = startMonth;
        this.startDay = startDay;
        this.dayCount = 0;

    }

    public BraceletW311GattCallBack(BluetoothListener bluetoothListener, Context context, BaseDevice baseDevice) {
        super(bluetoothListener, context, baseDevice);
    }

    @Override
    public void onServicesDiscovered(BluetoothGatt gatt, int status) {
        super.onServicesDiscovered(gatt, status);
        if (status == BluetoothGatt.GATT_SUCCESS) {
            List<BluetoothGattService> list = gatt.getServices();
            if (list != null) {
                for (int i = 0; i < list.size(); i++) {
                    BluetoothGattService service = list.get(i);
                    Log.e("LLLLL service uuid", service.getUuid().toString());
                    if (service.getUuid().equals(Constants.UUID_MAIN_SERVICE)) {
                        List<BluetoothGattCharacteristic> characteristicList = service.getCharacteristics();
                        for (int j = 0; j < characteristicList.size(); j++) {
                            BluetoothGattCharacteristic characteristic = characteristicList.get(j);
                            Log.e("LLLLL char uuid", characteristic.getUuid().toString());
                            if (characteristic.getUuid().equals(Constants.UUID_SEND_DATA_CHAR)) {
                                mSendChar = characteristic;
                                Log.e("LLLLL", "*** mSendChar getCharacteristic***" + mSendChar.toString());
                                mGattService = service;
                            } else if (characteristic.getUuid().equals(Constants.UUID_RECEIVE_DATA_CHAR)) {
                                mReceiveDataChar = characteristic;
                                Log.e("LLLLL", "***mResponceChar getCharacteristic***" + mReceiveDataChar.toString());
                            } else if (characteristic.getUuid().equals(Constants.UUID_REALTIME_RECEIVE_DATA_CHAR)) {
                                mRealTimeDataChar = characteristic;
                                Log.e("LLLLL", "***mRealTimeDataChar getCharacteristic***" + mRealTimeDataChar
                                        .toString());
                            }
                        }
                    } else if (service.getUuid().equals(Constants.HEART_RATE_SERVICE)) {
                        List<BluetoothGattCharacteristic> characteristicList = service.getCharacteristics();
                        for (int j = 0; j < characteristicList.size(); j++) {
                            BluetoothGattCharacteristic characteristic = characteristicList.get(j);
                            Log.e("LLLLL .HEART_RATE", characteristic.getUuid().toString());
                            if (characteristic.getUuid().equals(Constants.HEART_RATE_CHARACTERISTIC)) {
                                mHeartDataChar = characteristic;
                            }
                        }
                    }
                }
                if (mSendChar != null && mReceiveDataChar != null && mRealTimeDataChar !=
                        null) {
                    Log.e("LLLLL", "获取服务成功,开始使能");
//                    enableNotifications(mResponceChar);
                    if (bluetoothListener != null)
                        bluetoothListener.servicesDiscovered();
                } else {
                    //  bluetoothListener.refresh();
//                    disconnect();
                    if (bluetoothListener != null)
                        bluetoothListener.not_discoverServices();
                }
            }
        } else {
            Logger.myLog("onServicesDiscovered received: " + status);
//            refreshGatt();
//            disconnect();////找不到就断开
        }
    }

    @Override
    public void onDescriptorWrite(BluetoothGatt gatt, BluetoothGattDescriptor descriptor, int status) {
        super.onDescriptorWrite(gatt, descriptor, status);
        Logger.myLog("onDescriptorWrite" + gatt.getDevice().getName() + "---" + gatt.getDevice().getAddress());
        BluetoothGattCharacteristic characteristic = descriptor.getCharacteristic();
        if (status == BluetoothGatt.GATT_SUCCESS) {
            Logger.myLog("***使能成功***" + characteristic.getUuid().toString());
            if (characteristic.getUuid().equals(Constants.UUID_RECEIVE_DATA_CHAR)) {
                Logger.myLog("***使能mReceiveDataChar***");
                //internalEnableNotifications(mSendChar);
                internalEnableIndications(mSendChar);

            } else if (characteristic.getUuid().equals(Constants.UUID_SEND_DATA_CHAR)) {
                Logger.myLog("***使能mRealTimeDataChar***");
                internalEnableNotifications(mRealTimeDataChar);
                mMainHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        realConn();
                    }
                }, 3000);


            } else if (characteristic.getUuid().equals(Constants.UUID_REALTIME_RECEIVE_DATA_CHAR)) {
                Logger.myLog("***mHeartDataChar***");
                internalEnableNotifications(mHeartDataChar);

            } else if (characteristic.getUuid().equals(Constants.HEART_RATE_CHARACTERISTIC)) {
                ///打开通道可以通讯了才算连接成功
                //realConn();
            }
        } else {
            Logger.myLog("***使能失败***" + status);
        }
    }


    private void realConn() {
        isConnected = true;
        connectState = CONNECTED;
        Logger.myLog("真正的连接成功!");
        mReconnectHandler.removeMessages(0x01);
        try {
            if (bluetoothListener != null) {
                bluetoothListener.connected();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onCharacteristicRead(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic, int status) {
        super.onCharacteristicRead(gatt, characteristic, status);
        byte[] values = characteristic.getValue();
        if (characteristic.getService().getUuid().equals(Constants.BATTERY_SERVICE) && characteristic.getUuid()
                .equals(Constants.BATTERY_LEVEL_CHARACTERISTIC)) {
            Logger.myLog("***onBatteryChanged***" + (values[0] & 0xff));
            if (bluetoothListener != null) {
                bluetoothListener.onGetBattery(values[0] & 0xff);
            }
        } else if (characteristic.getService().getUuid().equals(Constants.DEVICEINFORMATION_SERVICE) &&
                characteristic.getUuid()
                        .equals(Constants.FIRMWAREREVISION_CHARACTERISTIC)) {
            byte[] version = new byte[5];
            version[0] = values[0];
            version[1] = values[1];
            version[2] = values[2];
            version[3] = values[3];
            version[4] = values[4];
            //获取用户信息

            // BE+06+09+FB 手机请求设备的版本信息
            writeTXCharacteristicItem(new byte[]{(byte) 0xBE, 0x06, (byte) 0x09, (byte) 0xFB});
            Logger.myLog("***FirmwareVersion111***" + new String(values) + " FirmwareVersion***" + Utils
                    .bytes2HexString(values));
            if (bluetoothListener != null) {
                bluetoothListener.onGetDeviceVersion(new String(values));
            }
        }
    }

    /**
     * This method will check if Heart rate value is in 8 bits or 16 bits
     */
    private boolean isHeartRateInUINT16(final byte value) {
        return ((value & 0x01) != 0);
    }

    @Override
    public void onCharacteristicChanged(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic) {
        super.onCharacteristicChanged(gatt, characteristic);


        handleReceive(gatt, characteristic);
    }

    private void handleReceive(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic) {
        final String mac = gatt.getDevice().getAddress();
        byte[] values = characteristic.getValue();

//        命令 04-00-A1-FE-74-69
//        应答：若设备未配对： 02-FF-00-00
//        若设备已经配对：02-FF-00-01
        if (Constants.IS_DEBUG) {
            final StringBuilder stringBuilder = new StringBuilder(values.length);
            for (byte byteChar : values) {
                stringBuilder.append(String.format("%02X ", byteChar));
            }
//                    saveLog(new StringBuilder(DateUtil.dataToString(new Date(), "HH:mm:ss")).append(" ff01 REC ")
// .append
//                            (stringBuilder.toString()).append(" UUID ").append(characteristic.getUuid().toString()));
//            Logger.myLog(" ReceiverCmd " + stringBuilder.toString() + " UUID " + characteristic.getUuid()
//                    .toString());
        }
        //心率实时数据通道
        if (characteristic.getService().getUuid().equals(Constants.HEART_RATE_SERVICE)) {
            if (characteristic.getUuid().equals(Constants.HEART_RATE_CHARACTERISTIC)) {

                if (true) {
                    final StringBuilder stringBuilder = new StringBuilder(values.length);
                    for (byte byteChar : values) {
                        stringBuilder.append(String.format("%02X ", byteChar));
                    }
                    int heartRate;
                    if (isHeartRateInUINT16(characteristic.getValue()[0])) {
                        heartRate = characteristic.getIntValue(BluetoothGattCharacteristic.FORMAT_UINT16, 1);
                    } else {
                        heartRate = characteristic.getIntValue(BluetoothGattCharacteristic.FORMAT_UINT8, 1);
                    }


                    if (logBuilder == null)
                        logBuilder = new StringBuilder();
                    logBuilder.append(DateUtil.dataToString(new Date(), "MM/dd HH:mm:ss") + " onCharacteristicChanged " +
                            "heartRate = " + heartRate +
                            "\r\n");
                    //Log.e(TAG, "onCharacteristicChanged heartRate = " + heartRate);
                    if (heartRate <= 30) {
                        return;
                    }
                    //实时心率数据上报给UI层
                    if (bluetoothListener != null) {
                        bluetoothListener.onRealtimeHeartRate(heartRate);
                    }
                    Log.e("REALTIME_RECEIVE_DATA_", "第四通道  ReceiverCmd " + stringBuilder.toString() + "heart:" + heartRate);
                    /*if (logBuilder == null)
                        logBuilder = new StringBuilder();
                    logBuilder.append(DateUtil.dataToString(new Date(), "MM/dd HH:mm:ss") + " REALTIME_RECEIVE_DATA_ " +
                            "= " +
                            stringBuilder.toString() + "\r\n");*/
                }

                //实时数据通道
//                if (bluetoothListener != null) {
//                    bluetoothListener.onRealtimeHeartRate(Utils.byte2Int(values[1]));
//                }
            }
        }
        if (characteristic.getService().getUuid().equals(Constants.UUID_MAIN_SERVICE)) {
            if (characteristic.getUuid().equals(Constants.UUID_SEND_DATA_CHAR)) {

                if (Constants.IS_DEBUG) {
                    final StringBuilder stringBuilder = new StringBuilder(values.length);
                    for (byte byteChar : values) {
                        stringBuilder.append(String.format("%02X ", byteChar));
                    }
                    saveLog(new StringBuilder(DateUtil.dataToString(new Date(), "HH:mm:ss")).append(" SEND ReceiverCmd ")
                            .append
                                    (stringBuilder.toString()).append(" UUID ").append(characteristic.getUuid().toString()));
                    Logger.myLog(" 应答通道 ReceiverCmd " + stringBuilder.toString() + " UUID " + characteristic.getUuid()
                            .toString());
                }
                //应答通道
                if (values != null && values.length >= 2) {

                    if (Utils.byte2Int(values[0]) == 0xDE && Utils.byte2Int(values[1]) == 0x01 && Utils.byte2Int
                            (values[2]) == 0x01 && Utils.byte2Int
                            (values[3]) == 0xED) {// syncTime返回  DE+01+01+ED
                        Logger.myLog("发送当前时间成功");
                        //bluetoothListener.onDeviceSuccess(2);

                    } else if (Utils.byte2Int(values[0]) == 0xDE && Utils.byte2Int(values[1]) == 0x01 && Utils.byte2Int
                            (values[2]) == 0x02 && Utils.byte2Int
                            (values[3]) == 0xED) {// syncTime返回  DE+01+02+ED
                        Logger.myLog("发送本地时间成功");


                        //如果不是第一次就不要发送震动

                        //如果是第一次绑定就需要发送这个命令，否则就去发送其他的指令


                        if ((W311BluetoothListener) bluetoothListener != null) {
                            ((W311BluetoothListener) bluetoothListener).onSendUserInfo();
                        }
                       /* if ((W311BluetoothListener) bluetoothListener != null) {
                            ((W311BluetoothListener) bluetoothListener).onOpenReal();
                        }*/

                    } else if (Utils.byte2Int(values[0]) == 0xDE && Utils.byte2Int(values[1]) == 0x02 && Utils.byte2Int
                            (values[2]) == 0x01 && Utils.byte2Int
                            (values[3]) == 0x06) {
                        Logger.myLog("" +
                                "没有运动数据");
                        String todayYYYYMMDD = TimeUtils.getBefor15dayYYMMDD();
                        Calendar currentCa = Calendar.getInstance();
                        String lastSyncTime = BleSPUtils.getString(mContext, BleSPUtils.Bracelet_LAST_SYNCTIME, todayYYYYMMDD);
                        String[] strings = lastSyncTime.split("-");

                        //将取出的最后更新的时间赋值，如果这一天没有数据就把日期+1，如果是当天没有数据，就直接完成

                        Calendar calendar = Calendar.getInstance();
                        calendar.set(Integer.parseInt(strings[0]), Integer.parseInt(strings[1]) - 1, Integer.parseInt(strings[2]));
                        calendar.add(Calendar.DAY_OF_MONTH, 1);
                        if (calendar.getTimeInMillis() > System.currentTimeMillis()) {

                        } else {
                            //没有数据就把最后的更新日期+1
                            String currentRequst = TimeUtils.getTimeByyyyyMMdd(calendar.getTimeInMillis());
                            BleSPUtils.putString(mContext, BleSPUtils.Bracelet_LAST_SYNCTIME, currentRequst);
                            lastSyncTime = BleSPUtils.getString(mContext, BleSPUtils.Bracelet_LAST_SYNCTIME, todayYYYYMMDD);
                            strings = lastSyncTime.split("-");
                        }

                        int[] tpi = new int[]{Integer.parseInt(strings[0]), Integer.parseInt(strings[1]), Integer.parseInt(strings[2])};
                        Logger.myLog("同步的年月日 todayYYYYMMDD:" + todayYYYYMMDD + "DateUtil.dataToString(currentCa.getTime(), \"yyyy-MM-dd\")" + DateUtil.dataToString(currentCa.getTime(), "yyyy-MM-dd") + "lastSyncTime" + lastSyncTime + "tpi" + tpi[0] + ":" + tpi[1] + ":" + tpi[2]);
                        // tpi = new int[]{Integer.parseInt(strings[0]), Integer.parseInt(strings[1]), Integer.parseInt(strings[2])};
                        String current = TimeUtils.getTodayYYYYMMDD();
                        if (lastSyncTime.equals(current)) {
                            if ((W311BluetoothListener) bluetoothListener != null) {
//                                //需要+1
                                Logger.myLog("运动数据startYear:" + tpi[0] + "startMonth:" + tpi[1] + "startDay:" + tpi[2]);
                                ((W311BluetoothListener) bluetoothListener).onSyncCompte();
                            }
                        } else {
                            if ((W311BluetoothListener) bluetoothListener != null) {
                                Logger.myLog("startYear:" + tpi[0] + "startMonth:" + tpi[1] + "startDay:" + tpi[2]);
                                ((W311BluetoothListener) bluetoothListener).onSysnSportDate(tpi[0], tpi[1], tpi[2]);
                            }
                        }

                    } else if (Utils.byte2Int(values[0]) == 0xDE && Utils.byte2Int(values[1]) == 0x02 && Utils.byte2Int
                            (values[2]) == 0x10 && Utils.byte2Int
                            (values[3]) == 0x06) {
                        Logger.myLog("没有心率历史数据");
                        String todayYYYYMMDD = TimeUtils.getBefor15dayYYMMDD();

                        Calendar currentCa = Calendar.getInstance();
                        currentCa.set(Calendar.YEAR, startYear);
                        currentCa.set(Calendar.MONTH, startMonth - 1);
                        currentCa.set(Calendar.DAY_OF_MONTH, startDay);
                        currentCa.add(Calendar.DAY_OF_MONTH, 1);
                        BleSPUtils.putString(mContext, BleSPUtils.Bracelet_LAST_HR_SYNCTIME, DateUtil.dataToString(currentCa.getTime(), "yyyy-MM-dd"));

                        String lastSyncTime = BleSPUtils.getString(mContext, BleSPUtils.Bracelet_LAST_HR_SYNCTIME, todayYYYYMMDD);
                        String[] strings = lastSyncTime.split("-");

                        Calendar calendar = Calendar.getInstance();
                        calendar.set(Integer.parseInt(strings[0]), Integer.parseInt(strings[1]) - 1, Integer.parseInt(strings[2]));

                        String currentRequst = TimeUtils.getTimeByyyyyMMdd(calendar.getTimeInMillis());
                        strings = currentRequst.split("-");
                        int[] tpi = new int[]{Integer.parseInt(strings[0]), Integer.parseInt(strings[1]), Integer.parseInt(strings[2])};
                        String current = TimeUtils.getTodayYYYYMMDD();
                        if (lastSyncTime.equals(current)) {
                            //结束同步时间
                            if ((W311BluetoothListener) bluetoothListener != null) {
                                Logger.myLog("心率历史数据同步时间startYear:" + tpi[0] + "startMonth:" + tpi[1] + "startDay:" + tpi[2]);
                                ((W311BluetoothListener) bluetoothListener).onSyncHrDataComptelety();
                            }
                        } else {
                            if ((W311BluetoothListener) bluetoothListener != null) {
                                Logger.myLog("心率历史数据同步时间startYear:" + tpi[0] + "startMonth:" + tpi[1] + "startDay:" + tpi[2]);
                                ((W311BluetoothListener) bluetoothListener).onSysnHrDate(tpi[0], tpi[1], tpi[2]);
                            }
                        }

                    } else if (Utils.byte2Int(values[0]) == 0xDE && Utils.byte2Int(values[1]) == 0x06 && Utils.byte2Int
                            (values[2]) == 0x31 && Utils.byte2Int
                            (values[3]) == 0xED) {
                        Logger.myLog("手机首次连接设备成功后，通知设备震动提醒并显示勾");
                        //发送用户数据
                        //用户数据发送成功打开实时通道


                    } else if (Utils.byte2Int(values[0]) == 0xde && Utils.byte2Int(values[1]) == 0x02 && Utils.byte2Int
                            (values[2]) == 0x03 &&
                            Utils.byte2Int(values[3]) == 0xed) {//"DE 02 03 ED".equals(stringBuilder.toString().trim())) {
                        //hasOpenRealTime = true;
                        Logger.myLog("实时数据通道已打开");
                        if ((W311BluetoothListener) bluetoothListener != null) {
                            ((W311BluetoothListener) bluetoothListener).onStartSync();
                        }
                    } else if (Utils.byte2Int(values[0]) == 0xde && Utils.byte2Int(values[1]) == 0x02 && Utils.byte2Int
                            (values[2]) == 0x05 && Utils.byte2Int(values[3]) == 0xfb) {//stringBuilder.toString().trim().startsWith("DE 02 05 FB"))
                        // {// get
                        // history date(start - end)，如果开始时间是2015-01-01

                        Logger.myLog("获取历史数据开始结束日期");

                      /*  if (syncHandler.hasMessages(0x01))
                            syncHandler.removeMessages(0x01);*/
                        byte[] starts = new byte[4];
                        System.arraycopy(values, 4, starts, 0, 4);
                        byte[] ends = new byte[4];
                        System.arraycopy(values, 8, ends, 0, 4);
                        mCache = null;
                        if (starts[0] == 0 && starts[1] == 0) {// no history data
                            Logger.myLog("no history data");
                            // TODO: 2018/7/5 没有历史的情况，直接请求当天的数据，历史不包含当天数据
//                            syncState = STATE_SYNC_COMPLETED;
//                            mCache = null;
//                            if (callback != null) {
//                                callback.syncState(syncState);
//                            }
                            Calendar cal = Calendar.getInstance();
                            startYear = cal.get(Calendar.YEAR);
                            startMonth = cal.get(Calendar.MONTH) + 1;
                            startDay = cal.get(Calendar.DAY_OF_MONTH);
                            dayCount = 0;
                            // TODO: 2018/3/17 开始同步时间
                            Logger.myLog("no history data startYear:" + startYear + "startMonth:" + startMonth + "startDay:" + startDay);
                            if ((W311BluetoothListener) bluetoothListener != null) {
                                Logger.myLog("startYear:" + startYear + "startMonth:" + startMonth + "startDay:" + startDay);
                                //这里计算出有几天的数据 没有历史数据就只同步当前的数据
                                SyncProgressObservable.getInstance().sync(DeviceTimesUtil.getTime(1, 0), false);
                                ((W311BluetoothListener) bluetoothListener).onSysnSportDate(startYear, startMonth, startDay);
                            }
                        } else {
                            startYear = starts[0] * 256 + (starts[1] & 0x00ff);
                            startMonth = starts[2];//1-12
                            startDay = starts[3];
                            Logger.myLog("获取到的startYear:" + startYear + "startMonth:" + startMonth + "startDay:" + startDay);
                          /*  if (IS_DEBUG)
                                Log.e(TAG, "start time = " + startYear + "-" + startMonth + "-" + startDay);*/
                            Calendar sc = Calendar.getInstance();
                            long stime = sc.getTimeInMillis() / 1000;//当前时间
                            sc.set(startYear, startMonth - 1, startDay);
                            long sttime = sc.getTimeInMillis() / 1000;
                            if (stime - sttime > 3600 * 24 * 11) {
                                sc = Calendar.getInstance();
                                sc.add(Calendar.DAY_OF_MONTH, -10);
                                startYear = sc.get(Calendar.YEAR);
                                startMonth = sc.get(Calendar.MONTH) + 1;
                                startDay = sc.get(Calendar.DAY_OF_MONTH);
                            }
                            if (stime - sttime <= 0) {
                                sc = Calendar.getInstance();
                                startYear = sc.get(Calendar.YEAR);
                                startMonth = sc.get(Calendar.MONTH) + 1;
                                startDay = sc.get(Calendar.DAY_OF_MONTH);
                            }

                            if (startYear == 2015 && startMonth == 1 && startDay == 1) {
                                Calendar cal = Calendar.getInstance();
                                cal.add(Calendar.DAY_OF_MONTH, -10);
                                startYear = cal.get(Calendar.YEAR);
                                startMonth = cal.get(Calendar.MONTH) + 1;
                                startDay = cal.get(Calendar.DAY_OF_MONTH);
                            }
                            int endYear = ends[0] * 256 + (ends[1] & 0x00ff);
                            int endMonth = ends[2];
                            int endDay = ends[3];

                            Calendar calendar = Calendar.getInstance();
                            calendar.set(Calendar.HOUR_OF_DAY, 0);
                            calendar.set(Calendar.MINUTE, 0);
                            calendar.set(Calendar.SECOND, 0);
                            calendar.set(Calendar.MILLISECOND, 0);
                            int year = calendar.get(Calendar.YEAR);
                            int month = calendar.get(Calendar.MONTH) + 1;//0 - 11
                            int day = calendar.get(Calendar.DAY_OF_MONTH);
                            String todayYYYYMMDD = TimeUtils.getBefor15dayYYMMDD();
                            Calendar lastCalendar = Calendar.getInstance();
                            lastCalendar.set(Calendar.HOUR_OF_DAY, 0);
                            lastCalendar.set(Calendar.MINUTE, 0);
                            lastCalendar.set(Calendar.SECOND, 0);
                            lastCalendar.set(Calendar.MILLISECOND, 0);
                            long startTime = 0;
                            String lastSyncTime = BleSPUtils.getString(mContext, BleSPUtils.Bracelet_LAST_SYNCTIME, todayYYYYMMDD);
                            if (!TextUtils.isEmpty(lastSyncTime)) {
                                String[] strs = lastSyncTime.split("-");
                                int ty = Integer.valueOf(strs[0]);
                                int tm = Integer.valueOf(strs[1]);
                                int td = Integer.valueOf(strs[2]);
                                lastCalendar.set(Calendar.YEAR, ty);
                                lastCalendar.set(Calendar.MONTH, tm - 1);
                                lastCalendar.set(Calendar.DAY_OF_MONTH, td);
                                if (lastCalendar.after(calendar)) {
                                    startYear = year;
                                    startMonth = month;
                                    startDay = day;
                                } else {
                                    startYear = ty;
                                    startMonth = tm;
                                    startDay = td;
                                }
                                Logger.myLog("开始同步时间 year:" + year + ",month:" + month + ",day:" + day + ",ty:" + ty + ",tm:" + tm + ",td:" + td + ",lastSyncTime:" + lastSyncTime);

                            }
                            calendar.set(Calendar.YEAR, startYear);
                            calendar.set(Calendar.MONTH, startMonth - 1);
                            calendar.set(Calendar.DAY_OF_MONTH, startDay);


                            int diffDays = DateUtil.getDifDay(calendar);


                            dayCount = 0;
                            if ((W311BluetoothListener) bluetoothListener != null) {
                                SyncProgressObservable.getInstance().sync(DeviceTimesUtil.getTime(diffDays, 0), false);
                                Logger.myLog("开始同步时间startYear:" + "diffDays:" + diffDays + "startYear," + startYear + "startMonth:" + startMonth + "startDay:" + startDay);
                                ((W311BluetoothListener) bluetoothListener).onSysnSportDate(startYear, startMonth, startDay);
                            }
                        }
                    } else if (Utils.byte2Int(values[0]) == 0xDE && Utils.byte2Int(values[1]) == 0x01 && Utils.byte2Int
                            (values[2]) == 0x03 && Utils.byte2Int
                            (values[3]) == 0xED) {// 设置用户基本信息返回  DE+01+03+ED
                        Logger.myLog("设置用户基本信息成功");
                    } else if (Utils.byte2Int(values[0]) == 0xDE && Utils.byte2Int(values[1]) == 0x01 && Utils.byte2Int
                            (values[2]) == 0x01 && Utils.byte2Int
                            (values[3]) == 0xFB) {// 设备发送公/英制,12H/24H,经常活动地时区,本地日期,本地时间到手机  DE+01+01+FB
                        Logger.myLog("查询设备公英制等基本信息成功");
                        int metricImp = values[4];
                        int is24Hour = values[5];
                        int activeTimezone = (((values[6] & 0x80) == 0x80) ? -1 * (values[6] & 0x40) / 2 : (values[6] &
                                0x40));
                        int timeZone = (((values[7] & 0x80) == 0x80) ? -1 * (values[7] & 0x40) / 2 : (values[7] & 0x40));
                        int year = Utils.byteArrayToInt(new byte[]{values[8], values[9]});
                        int month = Utils.byteToInt(values[10]);
                        int day = Utils.byteToInt(values[11]);
                        int week = Utils.byteToInt(values[12]);
                        int hour = Utils.byteToInt(values[13]);
                        int minute = Utils.byteToInt(values[14]);
                        int second = Utils.byteToInt(values[15]);
                    } else if (Utils.byte2Int(values[0]) == 0xDE && Utils.byte2Int(values[1]) == 0x01 && Utils.byte2Int
                            (values[2]) == 0x03 && Utils.byte2Int
                            (values[3]) == 0xFB) {// 设备返回体重，生日，步距  DE+01+03+FB
                        Logger.myLog("设备返回体重，生日，步距成功");
                        int year = Utils.byteArrayToInt(new byte[]{values[4], values[5]});
                        int month = Utils.byteToInt(values[6]);
                        int day = Utils.byteToInt(values[7]);
                        int weight = Utils.byteArrayToInt(new byte[]{values[8], values[9]});
                        int targeStep = Utils.byteArrayToInt(new byte[]{values[10], values[11], values[12]});
                        int strideLen = Utils.byteArrayToInt(new byte[]{values[13], values[14]});
                        int targetSleepHour = Utils.byteToInt(values[15]);
                        int targetSleepMin = Utils.byteToInt(values[16]);
                    } else if (Utils.byte2Int(values[0]) == 0xDE && Utils.byte2Int(values[1]) == 0x01 && Utils.byte2Int
                            (values[2]) == 0x04 && Utils.byte2Int
                            (values[3]) == 0xED) {//黑白屏，是否待机显示发送指令返回 DE+01+04+ED
                        Logger.myLog("黑白屏，是否待机显示发送指令成功");
                    } else if (Utils.byte2Int(values[0]) == 0xDE && Utils.byte2Int(values[1]) == 0x01 && Utils.byte2Int
                            (values[2]) == 0x05 && Utils.byte2Int
                            (values[3]) == 0xED) {//是否需要隐私保护指令发送成功 DE+01+05+ED
                        Logger.myLog("是否需要隐私保护指令发送成功");
                    } else if (Utils.byte2Int(values[0]) == 0xA5 && Utils.byte2Int(values[1]) == 0x01 && Utils.byte2Int
                            (values[2]) == 0x00 && Utils.byte2Int
                            (values[3]) == 0xFE) {//请求设备复位信息成功 A5+01+00+FE
                        Logger.myLog("请求设备复位信息成功");
                    } else if (Utils.byte2Int(values[0]) == 0xDE && Utils.byte2Int(values[1]) == 0x01 && Utils.byte2Int
                            (values[2]) == 0x07 && Utils.byte2Int
                            (values[3]) == 0xED) {//睡眠设置成功 DE+01+07+ED
                        Logger.myLog("睡眠设置成功");
                    } else if (Utils.byte2Int(values[0]) == 0xDE && Utils.byte2Int(values[1]) == 0x01 && Utils.byte2Int
                            (values[2]) == 0x08 && Utils.byte2Int
                            (values[3]) == 0xED) {//设置手环Display成功 DE+01+08+ED
                        Logger.myLog("设置手环Display成功");
                    } else if (Utils.byte2Int(values[0]) == 0xDE && Utils.byte2Int(values[1]) == 0x01 && Utils.byte2Int
                            (values[2]) == 0x09 && Utils.byte2Int
                            (values[3]) == 0xED) {//设置闹钟成功 DE+01+09+ED
                        Logger.myLog("设置闹钟成功");
                    } else if (Utils.byte2Int(values[0]) == 0xDE && Utils.byte2Int(values[1]) == 0x01 && Utils.byte2Int
                            (values[2]) == 0x0B && Utils.byte2Int
                            (values[3]) == 0xED) {//设置左右手成功 DE+01+0B+ED
                        Logger.myLog("设置左右手成功");
                    } else if (Utils.byte2Int(values[0]) == 0xDE && Utils.byte2Int(values[1]) == 0x01 && Utils.byte2Int
                            (values[2]) == 0x0C && Utils.byte2Int
                            (values[3]) == 0xED) {//设置久坐提醒成功 DE+01+0C+ED
                        Logger.myLog("设置久坐提醒成功");
                    } else if (Utils.byte2Int(values[0]) == 0xDE && Utils.byte2Int(values[1]) == 0x01 && Utils.byte2Int
                            (values[2]) == 0x15 && Utils.byte2Int
                            (values[3]) == 0xED) {//设置自动心率测试时间成功 DE+01+15+ED
                        Logger.myLog("设置自动心率测试时间成功");
                    } else if (Utils.byte2Int(values[0]) == 0xDE && Utils.byte2Int(values[1]) == 0x01 && Utils.byte2Int
                            (values[2]) == 0x16 && Utils.byte2Int
                            (values[3]) == 0xED) {//设置闹钟提醒内容成功 DE+01+16+ED
                        Logger.myLog("设置闹钟提醒内容成功");
                    } else if (Utils.byte2Int(values[0]) == 0xDE && Utils.byte2Int(values[1]) == 0x01 && Utils.byte2Int
                            (values[2]) == 0x05 && Utils.byte2Int
                            (values[3]) == 0xFB) {//获取手机后6位MAC DE+01+05+FB
                        Logger.myLog("获取手机后6位MAC成功");
                    } else if (Utils.byte2Int(values[0]) == 0xDE && Utils.byte2Int(values[1]) == 0x01 && Utils.byte2Int
                            (values[2]) == 0x07 && Utils.byte2Int
                            (values[3]) == 0xFE) {//获取睡眠和午休时间 DE+01+07+FE
                        Logger.myLog("获取睡眠和午休时间成功");
                    } else if (Utils.byte2Int(values[0]) == 0xDE && Utils.byte2Int(values[1]) == 0x01 && Utils.byte2Int
                            (values[2]) == 0x08 && Utils.byte2Int
                            (values[3]) == 0xFE) {//获取Display成功 DE+01+08+FE
                        Parse311Data.parserDisplay(values, mBaseDevice);
                        Logger.myLog("获取Display成功");
                    } else if (Utils.byte2Int(values[0]) == 0xDE && Utils.byte2Int(values[1]) == 0x01 && Utils.byte2Int
                            (values[2]) == 0x09 && Utils.byte2Int
                            (values[3]) == 0xFE) {//获取闹钟设置成功 DE+01+09+FE
                        Logger.myLog("获取闹钟设置成功");

                        if (bluetoothListener != null) {
                            bluetoothListener.successAlam(0);
                        }
                        Parse311Data.parserAlarmInfo(values, mBaseDevice);
                    } else if (Utils.byte2Int(values[0]) == 0xDE && Utils.byte2Int(values[1]) == 0x01 && Utils.byte2Int
                            (values[2]) == 0x19 && Utils.byte2Int
                            (values[3]) == 0xFB) {
                        Logger.myLog("获取自动心率成功");
                        ParseData.saveAutoHeartRateAndTime(values, mBaseDevice);

                    } else if (Utils.byte2Int(values[0]) == 0xDE && Utils.byte2Int(values[1]) == 0x01 && Utils.byte2Int
                            (values[2]) == 0x18 && Utils.byte2Int
                            (values[3]) == 0xFB) {
                        Logger.myLog("获取抬手亮屏成功");
                        ParseData.saveRaiseHand(values, mBaseDevice);

                    } else if (Utils.byte2Int(values[0]) == 0xDE && Utils.byte2Int(values[1]) == 0x01 && Utils.byte2Int
                            (values[2]) == 0x21 && Utils.byte2Int
                            (values[3]) == 0xFB) {
                        ParseData.saveDisturb(values, mBaseDevice);
                        Logger.myLog("获取勿扰成功");
                    } else if (Utils.byte2Int(values[0]) == 0xDE && Utils.byte2Int(values[1]) == 0x01 && Utils.byte2Int
                            (values[2]) == 0x0C && Utils.byte2Int
                            (values[3]) == 0xFE) {//获取久坐设置成功 DE+01+0C+FE
                        Logger.myLog("获取久坐设置成功");
                        Parse311Data.parserSedentaryInfo(values, mBaseDevice);
                    } else if (Utils.byte2Int(values[0]) == 0xDE && Utils.byte2Int(values[1]) == 0x01 && Utils.byte2Int
                            (values[2]) == 0x15 && Utils.byte2Int
                            (values[3]) == 0xFE) {//获取定时心率测试时间成功 DE+01+15+FE
                        Parse311Data.parserTimingHeartDetect(values, mBaseDevice);
                        Logger.myLog("获取定时心率测试时间成功");
                    } else if (Utils.byte2Int(values[0]) == 0xDE && Utils.byte2Int(values[1]) == 0x01 && Utils.byte2Int
                            (values[2]) == 0x20 && Utils.byte2Int
                            (values[3]) == 0xED) {//设置5分钟心率自动关闭成功 DE+01+20+ED
                        Logger.myLog("设置5分钟心率自动关闭成功");
                    } else if (Utils.byte2Int(values[0]) == 0xDE && Utils.byte2Int(values[1]) == 0x01 && Utils.byte2Int
                            (values[2]) == 0x18 && Utils.byte2Int
                            (values[3]) == 0xED) {//抬手亮屏设置成功 DE+01+18+ED
                        Logger.myLog("抬手亮屏设置成功");
                    } else if (Utils.byte2Int(values[0]) == 0xDE && Utils.byte2Int(values[1]) == 0x01 && Utils.byte2Int
                            (values[2]) == 0x19 && Utils.byte2Int
                            (values[3]) == 0xED) {//自动开启心率设置成功 DE+01+19+ED
                        Logger.myLog("自动开启心率设置成功/监测时间间隔");
                    }
                    if (values.length == 5 && (Utils.byte2Int(values[0])) == 0xde && (Utils.byte2Int(values[1])) == 0x06 && (Utils.byte2Int(values[4]) &
                            0xff) == 0xed) {
                        //设置消息类型的回调
                        Logger.myLog("消息设置成功" + ":消息类别" + Utils.byte2Int(values[2]) + ":第几包：" + Utils.byte2Int(values[3]));
                        if (bluetoothListener != null) {
                            ((W311BluetoothListener) (bluetoothListener)).onSuccessSendMesg(Utils.byte2Int(values[2]), Utils.byte2Int(values[3]));
                        }
                    } else if (Utils.byte2Int(values[0]) == 0xde && Utils.byte2Int(values[1]) == 0x06 && Utils.byte2Int(values[2]) == 0x02 &&
                            Utils.byte2Int(values[3]) == 0xed) {//"DE 06 02 ED".equals(stringBuilder.toString().trim())) {
                        Logger.myLog("发送来电电话号码成功");
                        if (bluetoothListener != null) {
                            ((W311BluetoothListener) (bluetoothListener)).onSuccessSendPhone();
                        }
                        //再发送联系人名字
                        //commandHandler.sendEmptyMessage(0x13);//300
                    } else if (Utils.byte2Int(values[0]) == 0xDE && Utils.byte2Int(values[1]) == 0x06 && Utils.byte2Int
                            (values[2]) == 0x0F && Utils.byte2Int
                            (values[3]) == 0xED) {//找设备返回 DE+06+0F+ED
                        Logger.myLog("找设备返回");
                    } else if (Utils.byte2Int(values[0]) == 0xDE && Utils.byte2Int(values[1]) == 0x06 && Utils.byte2Int
                            (values[2]) == 0x0E && Utils.byte2Int
                            (values[3]) == 0xED) {//关闭防丢功能成功 DE+06+0E+ED
                        Logger.myLog("关闭防丢功能成功");
                    } else if (Utils.byte2Int(values[0]) == 0xDE && Utils.byte2Int(values[1]) == 0x06 && Utils.byte2Int
                            (values[2]) == 0x0D && Utils.byte2Int
                            (values[3]) == 0xED) {//开启防丢功能成功 DE+06+0D+ED
                        Logger.myLog("开启防丢功能成功");
                    } else if (Utils.byte2Int(values[0]) == 0xDE && Utils.byte2Int(values[1]) == 0x06 && Utils.byte2Int
                            (values[2]) == 0x09 && Utils.byte2Int
                            (values[3]) == 0xFB) {//获取设备的版本信息成功 DE+06+09+FB

                        Logger.myLog("获取设备的版本信息成功");


                        final DeviceInfo deviceInfo = DeviceInfo.getInstance();
                        byte[] model = new byte[6];
                        System.arraycopy(values, 4, model, 0, 6);
                        String str = new String(model);
                        deviceInfo.setDeviceModel(str);
                        deviceInfo.setHardwareVersion(values[10]);
                        deviceInfo.setFirmwareHighVersion(values[11] & 0xff);
                        deviceInfo.setFirmwareLowVersion(values[12] & 0xff);
                        deviceInfo.setPowerLevel(values[17] & 0xff);
                        byte[] btttt = new byte[6];
                        btttt[0] = values[13];
                        btttt[1] = values[14];
                        btttt[2] = values[15];
                        btttt[3] = values[16];
                        btttt[4] = values[18];
                        btttt[5] = values[19];
                        deviceInfo.paraserInfo(btttt);//设置的configure信息
                        int info2 = btttt[1] & 0xff;
                        int connectVibrate = (info2 >> 3) & 1;//心率存储提醒是否开启
                        if (bluetoothListener != null)
                            ThreadPoolUtils.getInstance().addTask(new Runnable() {
                                @Override
                                public void run() {
                                    Bracelet_W311_DeviceInfoModelAction.saveOrUpdateW311DeviceInfo(BaseManager.mUserId, deviceInfo, mBaseDevice.getDeviceName());

                                    //这里需要去保存很多的设置信息，，佩戴方式
                                    Bracelet_W311_WearModel wearModel = new Bracelet_W311_WearModel();
                                    wearModel.setDeviceId(mBaseDevice.getDeviceName());
                                    wearModel.setUserId(BaseManager.mUserId);
                                    wearModel.setIsLeft(deviceInfo.getStateleftRight() == 0 ? true : false);
                                    Bracelet_W311_SettingModelAction.saveOrUpdateBraceletWearModel(wearModel);
                                    //消息提醒的始发打开
                                    ParseData.saveCallMessage(mBaseDevice, deviceInfo.getStateCallRemind() == 0 ? false : true, deviceInfo.getStateCallMsg() == 0 ? false : true);
                                    //防丢提醒


                                    //寻找手机功能

                                }
                            });
                        ((W311BluetoothListener) bluetoothListener).onDeviceSuccess(1);


                    } else if (Utils.byte2Int(values[0]) == 0xBE && Utils.byte2Int(values[1]) == 0x06 && Utils.byte2Int
                            (values[2]) == 0x08 && Utils.byte2Int
                            (values[3]) == 0xFB) {//播放音乐设置成功 BE+06+08+FB
                        Logger.myLog("播放音乐设置成功");
                    } else if (Utils.byte2Int(values[0]) == 0xBE && Utils.byte2Int(values[1]) == 0x06 && Utils.byte2Int
                            (values[2]) == 0x07 && Utils.byte2Int
                            (values[3]) == 0xFB) {//开启拍照成功 BE+06+07+FB
                        Logger.myLog("开启拍照成功");
                    }
//                    else if (Utils.byte2Int(values[0]) == 0xDE && Utils.byte2Int(values[1]) == 0x01 && Utils.byte2Int
//                            (values[2]) == 0x19 && Utils.byte2Int
//                            (values[3]) == 0xED) {//自动开启心率设置成功 DE+06+消息类型+01/02/03/4+ED
//                        Logger.myLog("自动开启心率设置成功/监测时间间隔");
//                    }
                }
            } else if (characteristic.getUuid().equals(Constants.UUID_RECEIVE_DATA_CHAR)) {
                if (true) {
                    StringBuilder stringBuilder = new StringBuilder(values.length);
                    for (byte byteChar : values) {
                        stringBuilder.append(String.format("%02X ", byteChar));
                    }
                    if (logBuilder == null)
                        logBuilder = new StringBuilder();
                    logBuilder.append("第二通道 ReceiverCmd " +
                            stringBuilder.toString()).append
                            ("\r\n");
                    Log.e("WatchW311GattCallBack", "第二通道  ReceiverCmd " + stringBuilder.toString() + " data.length =" + values.length);
                }

                //同步心率结束的指令
                if ((values[0] & 0xff) == 0xde && (values[1] & 0xff) == 0x02 && (values[2] & 0xff) == 0x10 && (values[3]
                        & 0xff) == 0xed) {//结束指令,1分钟一个数据
                    //同步完哪一天的数据结束包,移除超时监听
                /*    if (syncHandler.hasMessages(0x01))
                        syncHandler.removeMessages(0x01);
                    cancelDataTimer();*/

                    Log.e("WatchW311GattCallBack", "心率数据接收结束");

                    /*Calendar calendar = getCalendar();
                    Calendar curCalendar = getCurCalendar();
                    int y = calendar.get(Calendar.YEAR);
                    int m = calendar.get(Calendar.MONTH);
                    int d = calendar.get(Calendar.DAY_OF_MONTH);

                    //cleardata();*/

                    //收到结束指令，解析数据
                    result.clear();
                    result.addAll(mCache);
                    executorService.execute(new Runnable() {
                        @Override
                        public void run() {
                            Parse311Data.processHeartRateHistoryData(true, mContext, mBaseDevice.address, result, bluetoothListener, BaseManager.mUserId, mBaseDevice.deviceName);
                        }
                    });
                   /* if (calendar.getTimeInMillis() <= curCalendar.getTimeInMillis()) {
//                                initDataTimer();
                        // TODO: 2018/3/17 同步的下一个日期
                        int[] tpi = new int[]{calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH) + 1,
                                calendar.get(Calendar.DAY_OF_MONTH)};
                      *//*  Message msgTp = Message.obtain();
                        msgTp.obj = tpi;
                        msgTp.what = 0x16;*//*
                        if (bluetoothListener != null) {
                            ((W311BluetoothListener) bluetoothListener).onSysnHrDate(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH) + 1, calendar.get(Calendar.DAY_OF_MONTH));
                        }
                        // commandHandler.sendMessageDelayed(msgTp, 150);
                        //sendSyncDay(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH) + 1, calendar
                        // .get(Calendar.DAY_OF_MONTH));
                    } else {
                      *//*  if (IS_DEBUG)
                            Log.e(TAG, "***44***同步成功");
                        syncFinishOrError(curCalendar, 1);*//*
                    }*/
                } else if ((values[0] & 0xff) == 0xde && (values[1] & 0xff) == 0x02 && (values[2] & 0xff) == 0x01 && (values[3]
                        & 0xff) == 0xed) {//结束指令,1分钟一个数据
                    Log.e("WatchW311GattCallBack", "运动数据接收结束");
                    result.clear();
                    result.addAll(mCache);
                    //收到结束指令，解析数据
                    executorService.execute(new Runnable() {
                        @Override
                        public void run() {
                            Parse311Data.parseW31124HData(result, bluetoothListener, mBaseDevice, mContext);
                        }
                    });
                   /* if (calendar.getTimeInMillis() <= curCalendar.getTimeInMillis()) {
//                                initDataTimer();
                        // TODO: 2018/3/17 同步的下一个日期
                        int[] tpi = new int[]{calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH) + 1,
                                calendar.get(Calendar.DAY_OF_MONTH)};
                      *//*  Message msgTp = Message.obtain();
                        msgTp.obj = tpi;
                        msgTp.what = 0x16;*//*

                        // commandHandler.sendMessageDelayed(msgTp, 150);
                        //sendSyncDay(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH) + 1, calendar
                        // .get(Calendar.DAY_OF_MONTH));
                    } else {
                      *//*  if (IS_DEBUG)
                            Log.e(TAG, "***44***同步成功");
                        syncFinishOrError(curCalendar, 1);*//*
                    }*/
                } else {

                    if (mCache == null) {
                        mCache = Collections.synchronizedList(new ArrayList<byte[]>());
                    }
                    Log.e("WatchW311GattCallBack", "加入数据" + mCache.size());
                    mCache.add(values);
                }
            } else if (characteristic.getUuid().equals(Constants.UUID_REALTIME_RECEIVE_DATA_CHAR)) {
                Logger.myLog("实时数据");

                if (Utils.byte2Int(values[0]) == 0xDE && Utils.byte2Int(values[1]) == 0x06 && Utils.byte2Int
                        (values[2]) == 0x10 && Utils.byte2Int
                        (values[3]) == 0xED) {//找手机返回 BE+06+10+ED
                    Logger.myLog("找手机返回");
                    if (bluetoothListener != null) {
                        ((W311BluetoothListener) bluetoothListener).findPhoen();
                    }

                    return;
                }
                final StringBuilder stringBuilder = new StringBuilder(values.length);
                for (byte byteChar : values) {
                    stringBuilder.append(String.format("%02X ", byteChar));
                }
                if (logBuilder == null)
                    logBuilder = new StringBuilder();
                logBuilder.append(DateUtil.dataToString(new Date(), "HH:mm:ss") + " FF04 R = " + stringBuilder
                        .toString() + "\r\n");
               /* if (HandlerCommand.handleTakePhoto(data, dsCallBack))
                    return;
                if (HandlerCommand.handleFindPhone(data, dsCallBack)) {//find mobile phone
                    byte[] fff = new byte[]{(byte) 0xbe, 0x06, 0x10, (byte) 0xed};
                    sendCommand(SEND_DATA_CHAR, mGattService, mBluetoothGatt, fff);
                    return;
                }
                if (HandlerCommand.handleMusicController(context, data, dsCallBack))////Music controll
                    return;*/

                if (values != null && values.length >= 4 && ((values[0] & 0xff) == 0xde && (values[1] & 0xff) == 0x02 &&
                        (values[2] & 0xff) == 0x01 && (values[3] & 0xff) == 0xfe)) {//stringBuilder.toString().trim()
                    // .startsWith("DE 02 01 FE")) {////real time data
                    int totalStep = Parse311Data.byteArrayToInt(new byte[]{values[8], values[9], values[10], values[11]});
                    int totalCaloric = Parse311Data.byteArrayToInt(new byte[]{values[12], values[13], values[14], values[15]});
                    float totalDist = Parse311Data.byteArrayToInt(new byte[]{values[16], values[17]}) / 100.0f;
                    int totalSportTime = Parse311Data.byteArrayToInt(new byte[]{values[18], values[19]});
                    Logger.myLog("实时数据" + "totalStep:" + totalStep + "totalCaloric:" + totalCaloric + "totalDist:" + totalDist + "totalSportTime:" + totalSportTime);

                    if (bluetoothListener != null) {
                        ((W311BluetoothListener) (bluetoothListener)).onW311RealTimeData(totalStep, totalDist, totalCaloric, mBaseDevice.address);
                    }

                /* if (dsCallBack != null) {
                        dsCallBack.customeCmdResult(data);
                    }*/
                }
            }
        }


    }

    @Override
    public void onCharacteristicWrite(BluetoothGatt gatt, BluetoothGattCharacteristic
            characteristic, int status) {
        super.onCharacteristicWrite(gatt, characteristic, status);
        if (status == BluetoothGatt.GATT_SUCCESS) {
            Logger.myLog("GATT_WRITE_SUCCESS!");
            if (characteristic != null) {
                byte[] value = characteristic.getValue();
                if (value != null && value.length > 0) {

                }
            }
        } else if (status == BluetoothGatt.GATT_WRITE_NOT_PERMITTED) {
            Logger.myLog("GATT_WRITE_NOT_PERMITTED");
        } else {
            Logger.myLog("Write failed , Status is " + status);
        }
    }

    @Override
    public void onConnectionStateChange(BluetoothGatt gatt, int status, int newState) {
        super.onConnectionStateChange(gatt, status, newState);
        if (status == BluetoothGatt.GATT_SUCCESS) {

        } else {
            hasSyncTime = false;
        }
    }

    public boolean enableNotification() {
        if (mReceiveDataChar != null) {
            internalEnableIndications(mReceiveDataChar);
            return internalEnableNotifications(mReceiveDataChar);
        } else {
            return false;
        }
    }

    /**
     * 清理缓存
     */

    public void cleardata() {
        if (mCache != null) {
            mCache = null;
            result.clear();
        }
    }

    /**
     * 需要指定的特征值
     *
     * @param data
     * @return
     */
    public synchronized boolean writeTXCharacteristicItem(byte[] data) {
        Logger.myLog("writeTXCharacteristicItem isConnected:" + isConnected + ",data:" + Arrays.toString(data) + ",mNRFBluetoothGatt:" + mNRFBluetoothGatt);
        if (isConnected && data != null && data.length > 0 && mNRFBluetoothGatt != null) {
            mSendOk = false;
            final StringBuilder stringBuilder = new StringBuilder();
            for (byte byteChar : data) {
                stringBuilder.append(String.format("%02X ", byteChar));
            }
            // saveLog(new StringBuilder(DateUtil.dataToString(new Date(), "MM/dd HH:mm:ss") + " sendCommand " + stringBuilder.toString()));
            Logger.myLog("writeTXCharacteristicItem " + stringBuilder.toString());
            mSendChar.setValue(data);
            int mRetry = 0;
            boolean status = false;
            while (!status && isConnected && mNRFBluetoothGatt != null && mRetry < 3) {
                status = mNRFBluetoothGatt.writeCharacteristic(mSendChar);
                mRetry++;
                saveLog(new StringBuilder(DateUtil.dataToString(new Date(), "MM/dd HH:mm:ss") + "Write data status:" + status + ",mRetry:" + mRetry + ",isConnected:" + isConnected + ",mSendOk:" + mSendOk + ",mNRFBluetoothGatt:" + mNRFBluetoothGatt));
                if (status) {
                    //   mSendOk = true;
                }
                if (!mSendOk) {
                    SystemClock.sleep(200);
                }
            }
            return mSendOk;
        } else {
            return false;
        }
    }


    //*************************************************数据解析*********************************************//

//    private void parserSedentaryInfo(byte[] data) {
//        if (data != null && data.length == 19) {
//            int beginHour1 = data[5] & 0xff;
//            int beginMin1 = data[6] & 0xff;
//            int endHour1 = data[7] & 0xff;
//            int endMin1 = data[8] & 0xff;
//            SedentaryRemind sedentaryRemind1 = new SedentaryRemind(beginHour1 == 0 && beginMin1 == 0 && endHour1 == 0
//                    && endMin1 == 0,
//                    beginHour1, beginMin1, endHour1, endMin1);
//
//            int beginHour2 = data[9] & 0xff;
//            int beginMin2 = data[10] & 0xff;
//            int endHour2 = data[11] & 0xff;
//            int endMin2 = data[12] & 0xff;
//            SedentaryRemind sedentaryRemind2 = new SedentaryRemind(beginHour2 == 0 && beginMin2 == 0 && endHour2 == 0
//                    && endMin2 == 0,
//                    beginHour1, beginMin1, endHour1, endMin1);
//
//            int beginHour3 = data[13] & 0xff;
//            int beginMin3 = data[14] & 0xff;
//            int endHour3 = data[15] & 0xff;
//            int endMin3 = data[16] & 0xff;
//            SedentaryRemind sedentaryRemind3 = new SedentaryRemind(beginHour3 == 0 && beginMin3 == 0 && endHour3 == 0
//                    && endMin3 == 0,
//                    beginHour1, beginMin1, endHour1, endMin1);
//
//            int noexcise = (data[17] & 0xff) * 60 + (data[18] & 0xff);
//            ArrayList<SedentaryRemind> list = new ArrayList<>();
//            list.add(sedentaryRemind1);
//            list.add(sedentaryRemind2);
//            list.add(sedentaryRemind3);
//            SedentaryRemind.noExerceseTime = noexcise;
//
//            Intent intent = new Intent(Constants.ACTION_QUERY_SEDENTARY);
//            intent.putParcelableArrayListExtra(Constants.EXTRA_QUERY_SEDENTARY, list);
//            LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
//
//        }
//    }


    /**
     * 获取下一个同步计步历史数据的日期
     *
     * @return
     */
    private Calendar getCalendar() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        calendar.set(Calendar.YEAR, startYear);
        calendar.set(Calendar.MONTH, startMonth - 1);
        calendar.set(Calendar.DAY_OF_MONTH, startDay);
        dayCount++;
        calendar.add(Calendar.DAY_OF_MONTH, dayCount);
        return calendar;
    }

    /**
     * 获取下一个同步心率历史数据的日期
     *
     * @return
     */
    private Calendar getHeartRateCalendar() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        calendar.set(Calendar.YEAR, heartRateStartYear);
        calendar.set(Calendar.MONTH, heartRateStartMonth - 1);
        calendar.set(Calendar.DAY_OF_MONTH, heartRateStartDay);
        calendar.add(Calendar.DAY_OF_MONTH, heartRateDayCount);
        Log.e(TAG, "***last calendar***" + calendar.get(Calendar.DAY_OF_MONTH));
        StringBuilder builderTp = new StringBuilder(String.format("%04d", calendar.get(Calendar.YEAR))).append("-")
                .append(String.format("%02d", calendar.get(Calendar.MONTH) + 1)).append("-")
                .append(String.format("%02d", calendar.get(Calendar.DAY_OF_MONTH)));
//        putString(KEY_LAST_SYNC_HEARTRATE_TIME + currentMac, builderTp.toString());
        //if (IS_DEBUG)
        Log.e(TAG, "***KEY_LAST_SYNC_HEARTRATE_TIME 当前同步成功或者失败的时间点***" + builderTp.toString());
        heartRateDayCount++;
        calendar.add(Calendar.DAY_OF_MONTH, 1);
        Log.e(TAG, "***next calendar***" + calendar.get(Calendar.DAY_OF_MONTH));
        return calendar;
    }

    /**
     * 获取当前时间0点
     *
     * @return
     */
    private Calendar getCurCalendar() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar;
    }

    public int byteArrayToInt(byte[] data) {
        int value = 0;
        if (data != null && data.length > 0) {
            int len = data.length - 1;
            for (int i = len; i >= 0; i--) {
                value += ((data[len - i] & 0x00ff) << (8 * i));
            }
        }
        return value;
    }

}
