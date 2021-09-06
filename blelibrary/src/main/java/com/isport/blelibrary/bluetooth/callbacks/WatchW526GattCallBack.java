package com.isport.blelibrary.bluetooth.callbacks;

import android.app.Notification;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattDescriptor;
import android.bluetooth.BluetoothGattService;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.SystemClock;
import android.util.Log;

import com.isport.blelibrary.db.parse.ParseData;
import com.isport.blelibrary.deviceEntry.impl.BaseDevice;
import com.isport.blelibrary.interfaces.BluetoothListener;
import com.isport.blelibrary.managers.BaseManager;
import com.isport.blelibrary.utils.Constants;
import com.isport.blelibrary.utils.DateUtil;
import com.isport.blelibrary.utils.Logger;
import com.isport.blelibrary.utils.Utils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * @Author
 * @Date 2019/2/19
 * @Fuction
 */

public class WatchW526GattCallBack extends BaseGattCallback {

    private static final String TAG = "WatchW526GattCallBack";

    //***********************************************已整理******************************************************//


    BlockingQueue<DataBean> cmds = new LinkedBlockingQueue<>();


    private Handler sendHandler = null;//发送消息handler
    private static final long PAIRTIMEOUT = 20000;//配对超时时间
    protected int sendWhat;

    private static final int Order_send_adjust_mode = 1;//校准模式
    private static final int Order_send_exit_adjust_mode = 2;//退出校准模式
    private static final int Order_send_exit_adjust_mode_adjusting = 3;//正在校准
    private static final int Order_null = 0;//无状态模式
    private static boolean isPractisData = false;
    private static boolean isSyncData = false;

    private long mExitTime = 0;

    private List<byte[]> m24HDATA;
    private List<byte[]> mPractiseHDATA;


    public WatchW526GattCallBack(BluetoothListener bluetoothListener, Context context, BaseDevice baseDevice) {
        super(bluetoothListener, context, baseDevice);
        if (sendHandler == null) {
            sendHandler = new Handler(Looper.getMainLooper()) {
                @Override
                public void handleMessage(Message msg) {
                    super.handleMessage(msg);
                    switch (msg.what) {
                        case 1:
                            if (!hasSyncTime && isConnected) {
                                //连接状态，没有同步时间的情况，直接断连，说明没有绑定
                                Logger.myLog("连接状态，没有同步时间的情况，直接断连，说明没有绑定 !hasSyncTime:" + !hasSyncTime + "isConnected:" + isConnected);
                                disconnect(false);
                            }
                            break;
                        case 2:
                            syncTime();
                            break;
                        case 3:
                            writeTXCharacteristicItem(new byte[]{0x04, 0x00, (byte) 0xA1, (byte) 0xFE, 0x74, 0x69});
                            //sendHandler.sendEmptyMessageDelayed(0x01, PAIRTIMEOUT);//做一个15s的超时
                            break;
                        default:
                            break;
                    }
                }
            };
        }
    }

    @Override
    public void disconnect(boolean reconnect) {
        super.disconnect(reconnect);
        sendHandler.removeCallbacksAndMessages(null);
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
                    if (service.getUuid().equals(Constants.mainW516UUID)) {
                        List<BluetoothGattCharacteristic> characteristicList = service.getCharacteristics();
                        for (int j = 0; j < characteristicList.size(); j++) {
                            BluetoothGattCharacteristic characteristic = characteristicList.get(j);
                            Log.e("LLLLL char uuid", characteristic.getUuid().toString());
                            if (characteristic.getUuid().equals(Constants.sendW516UUID)) {
                                mSendChar = characteristic;
                                Log.e("LLLLL", "*** mSendChar getCharacteristic***" + mSendChar.toString());
                                mGattService = service;
                            } else if (characteristic.getUuid().equals(Constants.responceW516UUID)) {
                                mResponceChar = characteristic;
                                Log.e("LLLLL", "***mResponceChar getCharacteristic***" + mResponceChar.toString());
                            } else if (characteristic.getUuid().equals(Constants.receiveDataW516UUID)) {
                                mReceiveDataChar = characteristic;
                                Log.e("LLLLL", "***mResponceChar getCharacteristic***" + mReceiveDataChar.toString());
                            } else if (characteristic.getUuid().equals(Constants.realTimeDataW516UUID)) {
                                mRealTimeDataChar = characteristic;
                                Log.e("LLLLL", "***mRealTimeDataChar getCharacteristic***" + mRealTimeDataChar
                                        .toString());
                            }
                        }
                    } else if (service.getUuid().equals(Constants.HEART_RATE_SERVICE)) {
                        List<BluetoothGattCharacteristic> characteristicList = service.getCharacteristics();
                        for (int j = 0; j < characteristicList.size(); j++) {
                            BluetoothGattCharacteristic characteristic = characteristicList.get(j);
                            Log.e("LLLLL char uuid", characteristic.getUuid().toString());
                            if (characteristic.getUuid().equals(Constants.HEART_RATE_CHARACTERISTIC)) {
                                mHeartRateChar = characteristic;
                                Log.e("LLLLL", "*** mHeartRateChar getCharacteristic***" + mHeartRateChar.toString());
                            }
                        }
                    }
                }
                if (mSendChar != null && mResponceChar != null && mReceiveDataChar != null && mRealTimeDataChar !=
                        null && mHeartRateChar != null) {
                    Log.e("LLLLL", "获取服务成功,开始使能" + bluetoothListener);
//                    enableNotifications(mResponceChar);
                    if (bluetoothListener != null)
                        bluetoothListener.servicesDiscovered();
                } else {
//                    refreshGatt();
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
        BluetoothGattCharacteristic characteristic = descriptor.getCharacteristic();
        if (status == BluetoothGatt.GATT_SUCCESS) {
            Logger.myLog("***使能成功***" + characteristic.getUuid().toString());
            if (characteristic.getUuid().equals(Constants.responceW516UUID)) {
                Logger.myLog("***使能mReceiveDataChar***");

                mMainHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        internalEnableNotifications(mReceiveDataChar);
                    }
                }, 200);
            } else if (characteristic.getUuid().equals(Constants.receiveDataW516UUID)) {
                Logger.myLog("***使能mRealTimeDataChar***");
                mMainHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        internalEnableNotifications(mRealTimeDataChar);
                    }
                }, 200);

            } else if (characteristic.getUuid().equals(Constants.realTimeDataW516UUID)) {
                internalEnableNotifications(mHeartRateChar);
                ///打开通道可以通讯了才算连接成功
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
        } else {
            Logger.myLog("***使能失败***" + status);
        }
    }

    @Override
    public void onCharacteristicRead(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic, int status) {
        super.onCharacteristicRead(gatt, characteristic, status);
        byte[] values = characteristic.getValue();
        if (characteristic.getService().getUuid().equals(Constants.BATTERY_SERVICE) && characteristic.getUuid()
                .equals(Constants.BATTERY_LEVEL_CHARACTERISTIC)) {
            Logger.myLog("***onBatteryChanged***" + (values[0] & 0xff));
        } else if (characteristic.getService().getUuid().equals(Constants.DEVICEINFORMATION_SERVICE) &&
                characteristic.getUuid()
                        .equals(Constants.FIRMWAREREVISION_CHARACTERISTIC)) {
            byte[] version = new byte[5];
            version[0] = values[0];
            version[1] = values[1];
            version[2] = values[2];
            version[3] = values[3];
            version[4] = values[4];
            Logger.myLog("***FirmwareVersion111***" + new String(values) + " FirmwareVersion***" + Utils
                    .bytes2HexString(values));
            //立即发送会出现不回调的情况 同步时间
           /* if (!Constants.isDFU) {
                sendHandler.sendEmptyMessageDelayed(0X02, 1000);
            }*/
//            命令 04-00-A1-FE-74-69
//            应答：若设备未配对： 02-FF-00-00
//            若设备已经配对：02-FF-00-01

            //writeTXCharacteristicItem(new byte[]{0x04, 0x00, (byte) 0xA1, (byte) 0xFE, 0x74, 0x69});
            // sendHandler.sendEmptyMessageDelayed(0x01, PAIRTIMEOUT);//做一个15s的超时
//            syncTime();
            /*if (bluetoothListener != null) {
                bluetoothListener.onGetDeviceVersion(new String(values));
            }*/
        }
    }

    public void handleReceive(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic) {
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
        if (characteristic.getService().getUuid().equals(Constants.mainW516UUID)) {
            if (characteristic.getUuid().equals(Constants.responceW516UUID)) {

                if (Constants.IS_DEBUG) {
                    final StringBuilder stringBuilder = new StringBuilder(values.length);
                    for (byte byteChar : values) {
                        stringBuilder.append(String.format("%02X ", byteChar));
                    }
                    saveLog(new StringBuilder(DateUtil.dataToString(new Date(), "yyyy/MM/dd HH:mm:ss.SSS")).append(" ReceiverCmd ").append(stringBuilder.toString()));
                    Logger.myLog(" ReceiverCmd " + stringBuilder.toString() + " UUID " + characteristic.getUuid()
                            .toString().substring(0, 8));
                }
                //应答通道
                if (values != null && values.length >= 2) {
                    //TODO w526没有配对的这个逻辑了。
                    if (Utils.byte2Int(values[0]) == 0x02 && Utils.byte2Int(values[1]) == 0xFF && Utils.byte2Int
                            (values[2]) == 0x00 && Utils.byte2Int
                            (values[3]) == 0x01) {//02-FF-00-01
                        //已经确定配对了,直接发送时间给设备
                        /*if (sendHandler.hasMessages(0x01)) {
                            sendHandler.removeMessages(0x01);
                        }
                        //立即发送会出现不回调的情况
                        if (!Constants.isDFU) {
                            sendHandler.sendEmptyMessageDelayed(0X02, 3000);
                        }*/
                    } else if (Utils.byte2Int(values[0]) == 0x02 && Utils.byte2Int(values[1]) == 0xFF && Utils.byte2Int
                            (values[2]) == 0x32 && Utils.byte2Int
                            (values[3]) == 0x00) {//02-FF-32-00
                        //设置通用后，去获取用户信息
                        if (bluetoothListener != null) {
                            bluetoothListener.onGetSettingSuccess();
                        }

                    } else if (Utils.byte2Int(values[0]) == 0x02 && Utils.byte2Int(values[1]) == 0xFF && Utils.byte2Int
                            (values[2]) == 0x11 && Utils.byte2Int
                            (values[3]) == 0x00) {//02-FF-11-00
                        //进入和推出校准模式  校准时/分针返回
                        if (sendWhat == Order_send_adjust_mode) {
                            sendWhat = Order_null;
                            if (bluetoothListener != null) {
                                bluetoothListener.onInDemoModeSuccess();
                            }
                        } else if (sendWhat == Order_send_exit_adjust_mode) {
                            //退出校准模式，再次发送当前时间
                            syncTime();
                        } else if (sendWhat == Order_send_exit_adjust_mode_adjusting) {
                            //退出校准模式，再次发送当前时间
                        } else {
                            sendWhat = Order_null;
                            syncTime();
                        }
                    } else if (Utils.byte2Int(values[0]) == 0x02 && Utils.byte2Int(values[1]) == 0xFF && Utils.byte2Int
                            (values[2]) == 0x30 && Utils.byte2Int
                            (values[3]) == 0x00) {//02-FF-30-00
                        //发送时间返回，去获取用户信息
                        if (sendWhat == Order_send_exit_adjust_mode_adjusting || sendWhat == Order_send_exit_adjust_mode || sendWhat == Order_send_adjust_mode) {
                            //有可能未发送成功,还在校准模式
                            sendWhat = Order_null;
                        } else {
                            if (!hasSyncTime) {
                                hasSyncTime = true;
//                            setGeneral();
                            }
                            if (bluetoothListener != null) {
                                bluetoothListener.onSettingUserInfoSuccess();
                            }
                            queryUserInfo();//查询是没有用的
                           /* if (bluetoothListener != null) {
                                bluetoothListener.onSyncTimeSuccess();
                            }*/
                        }
                    } else if (Utils.byte2Int(values[0]) == 0x02 && Utils.byte2Int(values[1]) == 0xFF && Utils.byte2Int
                            (values[2]) == 0x33 && Utils.byte2Int
                            (values[3]) == 0x00) {//02-FF-33-00
                        //设置用户信息成功
                        if (bluetoothListener != null) {
                            bluetoothListener.onSettingUserInfoSuccess();
                        }
                    } else if (Utils.byte2Int(values[0]) == 0x0B && Utils.byte2Int(values[1]) == 0xFF && Utils.byte2Int
                            (values[2]) == 0x05) {//0B-FF-05
//                          若设置内容为: brith day: 1981 年 12 月 12 日，female，体重 100kg，身高 170， 最大心率 190， 最低心率 60
//                                * 0B-FF-05-BD-07-0C-0C-00-E8-03-AA-BE-3C
                        //用户信息返回

//                        if (apUserSetting == null) {
//                            apUserSetting = new UserSettingW516();
//                        }

                        //查询信息返回,当用户信息为空时，只有最高个最低心率值,设置一次用户信息
                        //默认 生日 1991 01 01 性别 1男性 height 175 weight 75 maxHeartRate 200  minHeartRate 50
                        if (Utils.byte2Int(values[3]) == 0x00 && Utils.byte2Int(values[4]) == 0x00) {
                            int max = Utils.byte2Int(values[11]) < 30 ? 190 : Utils.byte2Int(values[11]);
                            int min = Utils.byte2Int(values[12]) < 30 ? 60 : Utils.byte2Int(values[12]);
                           // setUserInfo(1991, 1, 1, 1, 75, 175, max, min);
//                            apUserSetting.setYear(1991);
//                            apUserSetting.setMonth(1);
//                            apUserSetting.setDay(1);
//                            apUserSetting.setGender(1);
//                            apUserSetting.setWeight(75);
//                            apUserSetting.setHeight(175);
//                            apUserSetting.setMaxHeartRate(max);
//                            apUserSetting.setMinHeartRate(min);
                        } else {
//                            Log.e(TAG, Utils.byteArrayToInt(new byte[]{values[4], values[3]}) + "==year==" + Utils
//                                    .byteArrayToInt(new byte[]{values[3], values[4]}));
//                            Log.e(TAG, Utils.byteArrayToInt(new byte[]{values[9], values[8]}) / 10 + "==weight==" +
//                                    Utils.byteArrayToInt(new byte[]{values[8], values[9]}) / 10);
//                            apUserSetting.setYear(Utils.byteArrayToInt(new byte[]{values[4], values[3]}));
//                            apUserSetting.setWeight(Utils.byteArrayToInt(new byte[]{values[9], values[8]}) / 10);
//                            apUserSetting.setMonth(Utils.byte2Int(values[5]));
//                            apUserSetting.setDay(Utils.byte2Int(values[6]));
//                            apUserSetting.setGender(Utils.byte2Int(values[7]));
//                            apUserSetting.setHeight(Utils.byte2Int(values[10]));
//                            apUserSetting.setMaxHeartRate(Utils.byte2Int(values[11]));
//                            apUserSetting.setMinHeartRate(Utils.byte2Int(values[12]));
                            Logger.myLog(TAG,"年 " + Utils.byteArrayToInt(new byte[]{values[4], values[3]}) + " 月 " +
                                    Utils.byte2Int(values[5]) + " 日 " + Utils.byte2Int(values[6]) + " 体重" +
                                    " " +
                                    +(Utils.byteArrayToInt(new byte[]{values[9], values[8]}) / 10)
                                    + " 身高 " + Utils.byte2Int(values[10]) + " 性别 " + (Utils.byte2Int
                                    (values[7]) == 0 ? "女" : "男") + " 最大心率 " + Utils.byte2Int(values[11]) + " 最小心率 "
                                    + Utils.byte2Int(values[12]));
                        }
                        if (bluetoothListener != null) {
                            bluetoothListener.onGetUserInfoSuccess();
                        }

                    } else if (Utils.byte2Int(values[0]) == 0x0F && Utils.byte2Int(values[1]) == 0xFF && Utils.byte2Int
                            (values[2]) == 0x01) {//  0F-FF-01
                        //设置设备状态
//                        0F-FF-01-20-1C-07-00-00-32-30-30-2E-30-30-2E-30-32
                        byte[] version = new byte[8];
                        version[0] = values[9];
                        version[1] = values[10];
                        version[2] = values[11];
                        version[3] = values[12];
                        version[4] = values[13];
                        version[5] = values[14];
                        version[6] = values[15];
                        version[7] = values[16];
                        byte[] versionV = new byte[1];
                        versionV[0] = values[8];
                        byte b = 12;
                        String binary = Integer.toBinaryString(b);//转换为2进制字符串
                        System.out.println(binary);
                        int i = Integer.parseInt(binary, 2);//将二进制串转为数字
                        byte d = (byte) i;
                        System.out.println(d);
                        byte[] booleanArray = Utils.getBooleanArray(values[7]);
                        byte[] booleanArrayG = Utils.getBooleanArray((byte) 0x04);
                        Logger.myLog(TAG,"锻炼数据时间 == " + Utils.byteArrayToInt(new byte[]{values[4], values[3]}) + " " +
                                "多少天24小时数据 == " + Utils.byte2Int(values[5]) + " 心率状态 == " + Utils
                                .byte2Int(values[6]) + " 固件状态 == " + Utils.byte2Int(values[7]) + " 固件状态二进制 == " +
                                Integer.toBinaryString((values[7] & 0xFF) + 0x100).substring(1) + " 固件版本" +
                                " == " + new String(versionV) + " 软件版本 == " + new String(version) + " " +
                                "二进制各个bit值 == " + Utils.byte2Int(booleanArray[0]) + " - " + Utils
                                .byte2Int(booleanArray[1]) + " - " + Utils.byte2Int(booleanArray[2]) +
                                "二进制各个bit值 == " + Utils.byte2Int(booleanArrayG[0]) + " - " + Utils
                                .byte2Int(booleanArrayG[1]) + " - " + Utils.byte2Int(booleanArrayG[2]) + " - " + Utils
                                .byte2Int(booleanArrayG[3]) + " - " + Utils
                                .byte2Int(booleanArrayG[4]) + " - " + Utils.byte2Int(booleanArrayG[5]) + " - " + Utils
                                .byte2Int(booleanArrayG[6]) + " - " + Utils
                                .byte2Int(booleanArrayG[7]));

                       if (bluetoothListener != null)
                            bluetoothListener.onGetDeviceVersion(new String(version));
                        if (!Constants.isDFU) {
                            sendHandler.sendEmptyMessageDelayed(0X02, 200);
                        }

                    } else if (Utils.byte2Int(values[0]) == 0x0a && Utils.byte2Int(values[1]) == 0xFF && Utils.byte2Int
                            (values[2]) == 0x04) {//09-FF-04

                        byte[] tempBtype = new byte[values.length];
                        for (int i = 0; i < values.length; i++) {
                            tempBtype[i] = values[i];
                        }


                        //获取通用设置
//                        09-FF-04-14-00-00-00-00-00-00-00
                        byte[] booleanArrayG = Utils.getBooleanArray(values[3]);

                        //获取设备的基础信息
                        BaseManager.isTmepUnitl = Utils.byte2Int(booleanArrayG[2]) + "";
                        if (mBaseDevice != null) {
                            ParseData.saveTempUtil(mBaseDevice.deviceName, BaseManager.mUserId, Utils.byte2Int(booleanArrayG[2]) + "");
                        }
                        if (bluetoothListener != null) {
                           // bluetoothListener.onGetSettingSuccess();
                            bluetoothListener.onsetGeneral(tempBtype);
                        }

                        Logger.myLog(TAG,"获取通用设置,二进制各个bit值 == " + " 公英制 == " + (Utils.byte2Int(booleanArrayG[7]) == 0 ? "公制" :
                                "英制")
                                + " 中英文 == " + (Utils.byte2Int(booleanArrayG[6]) == 0 ? "英文" : "中文")
                                + " 小时制 == " + (Utils.byte2Int(booleanArrayG[5]) == 0 ? "12小时" : "24小时")
                                + " 抬腕亮屏 == " + (Utils.byte2Int(booleanArrayG[4]) == 0 ? "开启" : "关闭")
                                + " 24小时心率 == " + (Utils.byte2Int(booleanArrayG[3]) == 0 ? "开启" : "关闭")
                                + "温度单位 ==" + (Utils.byte2Int(booleanArrayG[2]) == 0 ? "摄氏度" : "华氏度")
                                + " BaseManager.isTmepUnitl:" + BaseManager.isTmepUnitl);
                        ParseData.saveGeneral(tempBtype, mBaseDevice);
                    } else if (Utils.byte2Int(values[0]) == 0x01 && Utils.byte2Int(values[1]) == 0x15 && Utils.byte2Int
                            (values[2]) == 0x01) {
                        Logger.myLog("设置拍照功能");
                        if (bluetoothListener != null) {
                            bluetoothListener.takePhoto();
                        }
                    } else if (Utils.byte2Int(values[0]) == 0x02 && Utils.byte2Int(values[1]) == 0xFF && Utils.byte2Int
                            (values[2]) == 0x09) {
                        Logger.myLog("获取24小时心率状态成功");
                        if (bluetoothListener != null) {
                            bluetoothListener.onGetSettingSuccess();
                        }
                        ParseData.save24HrSwitch(mBaseDevice.deviceName, values[3]);
                    } else if (Utils.byte2Int(values[0]) == 0x02 && Utils.byte2Int(values[1]) == 0xFF && Utils.byte2Int
                            (values[2]) == 0x38) {
                        Logger.myLog("设置目标步数成功");
                        if (bluetoothListener != null) {
                            bluetoothListener.onGetSettingSuccess();
                        }
                        //获取目标步数成功
                    } else if (Utils.byte2Int(values[0]) == 0x04 && Utils.byte2Int(values[1]) == 0xFF && Utils.byte2Int
                            (values[2]) == 0x0C) {

                        if (bluetoothListener != null) {
                            bluetoothListener.onGetSettingSuccess();
                            try {
                                int targetStep = Utils.byteArrayToInt(new byte[]{
                                        values[5], values[4], values[3]});
                                Logger.myLog(TAG,"获取目标步数成功 targetStep" + targetStep);
                                bluetoothListener.onSuccessTargetStep(targetStep);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                        //设置目标步数成功
                    } else if (Utils.byte2Int(values[0]) == 0x08 && Utils.byte2Int(values[1]) == 0xFF && Utils.byte2Int
                            (values[2]) == 0x02) {//08 FF 02
                        if (bluetoothListener != null) {
                            bluetoothListener.onGetSettingSuccess();
                        }
                        //获取时间戳，实时时间
//                        命令：00-02
//                        应答：若时间为：2018 年 12 月 13 日 14 点 15 分 16 秒
//                        08-FF-02-E2-07-0C-0D-0E-0F-10
                        Logger.myLog(TAG,"获取的实时时间 " + Utils.byteArrayToInt(new byte[]{values[4], values[3]}) + "年" +
                                Utils.byte2Int(values[5]) + "月" + Utils.byte2Int(values[6]) + "日" +
                                +Utils.byte2Int(values[7]) + "点" +
                                +Utils.byte2Int(values[8]) + "分" +
                                +Utils.byte2Int(values[9]) + "秒");
                    } else if (Utils.byte2Int(values[1]) == 0xFF && Utils.byte2Int(values[2]) == 0x07) {//12-FF-07
                        if (bluetoothListener != null) {
                            bluetoothListener.onGetSettingSuccess();
                        }
                        //获取闹钟设置
//                        实例:
//                        命令： 01-07-00 读取闹钟 0，10 点 10 分，周日，周 1 开，闹钟信息为”123”
//                        Page 7 of 14
//                        应答：12-FF-07-03-0A-0A-31-32-33-00-00-00-00-00-00-00-00-00-00-00
                        byte[] booleanArrayG = Utils.getBooleanArray(values[3]);
                        if (Utils.byte2Int(values[3]) == 2) {
                            //获取闹钟成功
                            bluetoothListener.successAlam(Utils.byte2Int(values[3]));
                           // bluetoothListener.onGetSettingSuccess();
                        }
                        if (Utils.byte2Int(values[3]) == 0) {
                            //无闹钟设置
                            Logger.myLog("闹钟设置关闭设置");
                        } else {
                            byte[] msg = new byte[14];
                            msg[0] = values[6];
                            msg[1] = values[7];
                            msg[2] = values[8];
                            msg[3] = values[9];
                            msg[4] = values[10];
                            msg[5] = values[11];
                            msg[6] = values[12];
                            msg[7] = values[13];
                            msg[8] = values[14];
                            msg[9] = values[15];
                            msg[10] = values[16];
                            msg[11] = values[17];
                            msg[12] = values[18];
                            msg[13] = values[19];
                            Logger.myLog(TAG,"获取闹钟设置,二进制各个bit值 == " + " Sunday == " + (Utils.byte2Int(booleanArrayG[7]) == 0 ?
                                    "关" :
                                    "开")
                                    + " Monday  == " + (Utils.byte2Int(booleanArrayG[6]) == 0 ? "关" : "开")
                                    + " Tuesday  == " + (Utils.byte2Int(booleanArrayG[5]) == 0 ? "关" : "开")
                                    + " Wednesday  == " + (Utils.byte2Int(booleanArrayG[4]) == 0 ? "关" :
                                    "开")
                                    + " Thursday  == " + (Utils.byte2Int(booleanArrayG[3]) == 0 ? "关" :
                                    "开")
                                    + " Friday   == " + (Utils.byte2Int(booleanArrayG[2]) == 0 ? "关" : "开")
                                    + " Saturday   == " + (Utils.byte2Int(booleanArrayG[1]) == 0 ? "关" :
                                    "开")
                                    + " 时 " + Utils.byte2Int(values[4]) + " 分 " + Utils.byte2Int
                                    (values[5]) + " 闹钟信息 " + new String(msg));
                        }
                        byte[] tempByte = ParseData.getTempByte(values);
                        ParseData.saveW526Alarm(tempByte, mBaseDevice);

                    } else if (Utils.byte2Int(values[0]) == 0x06 && Utils.byte2Int(values[1]) == 0xFF && Utils.byte2Int
                            (values[2]) == 0x06) {//0A-FF-06
                        if (bluetoothListener != null) {
                            bluetoothListener.onGetSettingSuccess();
                        }
                        Logger.myLog("获取勿扰模式成功");
                        byte[] tempByte = ParseData.getTempByte(values);
                        ParseData.saveW526Disturb(tempByte, mBaseDevice);

//
                    } else if (Utils.byte2Int(values[1]) == 0xFF && Utils.byte2Int
                            (values[2]) == 0x10) {
                        Logger.myLog("设置消息成功" + "index:" + Utils.byte2Int(values[0]));
                        if (bluetoothListener != null) {
                            bluetoothListener.onGetSettingSuccess();
                        }
                    } else if (Utils.byte2Int(values[0]) == 0x02 && Utils.byte2Int(values[1]) == 0xFF && Utils.byte2Int
                            (values[2]) == 0x34 && Utils.byte2Int
                            (values[3]) == 0x00) {//02-FF-34-00
                        //设置睡眠监测成功
                        Logger.myLog("设置睡眠监测成功");
                    } else if (Utils.byte2Int(values[0]) == 0x02 && Utils.byte2Int(values[1]) == 0xFF && Utils.byte2Int
                            (values[2]) == 0x31 && Utils.byte2Int
                            (values[3]) == 0x00) {//02-FF-31-00
                        //设置久坐提醒成功
                        Logger.myLog("设置久坐提醒成功");
                    } else if (Utils.byte2Int(values[0]) == 0x06 && Utils.byte2Int(values[1]) == 0xFF && Utils.byte2Int
                            (values[2]) == 0x08) {
                        if (bluetoothListener != null) {
                            bluetoothListener.onGetSettingSuccess();
                        }
                        Logger.myLog("获取抬腕亮屏设置设置成功 == 开关" + Utils.byte2Int(values[3]) + "开始时间hour:" + Utils.byte2Int(values[4]) + "开始时间min:" + Utils.byte2Int(values[5]) + "结束时间hour:" + Utils.byte2Int(values[6]) + "结束时间min:" + Utils.byte2Int(values[7]));
                        byte[] tempByte = ParseData.getTempByte(values);
                        ParseData.saveW526RaiseHand(tempByte, mBaseDevice);
                        //获取抬腕亮屏设置设置
                    } else if (Utils.byte2Int(values[0]) == 0x03 && Utils.byte2Int(values[1]) == 0xFF && Utils.byte2Int
                            (values[2]) == 0x0b) {
                        if (bluetoothListener != null) {
                            bluetoothListener.onGetSettingSuccess();
                        }
                        //获取背光设置
                        Logger.myLog("获取背光设置成功 == " + Utils.byte2Int(values[3]) + "背光亮度等级----" + Utils.byte2Int(values[4]) + "为背光时间");
                        byte[] tempByte = ParseData.getTempByte(values);
                        ParseData.saveBackLight(tempByte, mBaseDevice);
                    } else if (Utils.byte2Int(values[0]) == 0x02 && Utils.byte2Int(values[1]) == 0xFF && Utils.byte2Int
                            (values[2]) == 0x1A) {
                        Logger.myLog("设置心率开关成功 == ");
                        if (bluetoothListener != null) {
                            bluetoothListener.onGetSettingSuccess();
                        }
                    } else if (Utils.byte2Int(values[0]) == 0x02 && Utils.byte2Int(values[1]) == 0xFF && Utils.byte2Int
                            (values[2]) == 0x0a) {
                        //获取表盘设置
                        if (bluetoothListener != null) {
                            bluetoothListener.onGetSettingSuccess();
                            bluetoothListener.onSuccessWatchFace(values[3]);
                        }
                        Logger.myLog("获取表盘设置成功 == " + Utils.byte2Int(values[3]) + "分钟");
                        byte[] tempByte = ParseData.getTempByte(values);
                        ParseData.saveWatchFace(tempByte, mBaseDevice);
                    } else if (Utils.byte2Int(values[0]) == 0x06 && Utils.byte2Int(values[1]) == 0xFF && Utils.byte2Int
                            (values[2]) == 0x03) {//06-FF-03-C8
                        //获取久坐提醒
                        if (bluetoothListener != null) {
                            bluetoothListener.onGetSettingSuccess();
                        }
                        Logger.myLog("获取久坐提醒成功 == " + Utils.byte2Int(values[3]) + "分钟");
                        byte[] tempByte = ParseData.getTempByte(values);
                        ParseData.saveSedentaryTime(tempByte, mBaseDevice);
                    } else if (Utils.byte2Int(values[0]) == 0x02 && Utils.byte2Int(values[1]) == 0xFF && Utils.byte2Int
                            (values[2]) == 0x40 && Utils.byte2Int
                            (values[3]) == 0x04) {//02-FF-40-04
                        //没有24小时数据
                        Logger.myLog("没有24小时数据");
                        //没有数据也要直接返回
                        if (bluetoothListener != null) {
                            bluetoothListener.onSyncSuccess();
                        }
                    } else if (Utils.byte2Int(values[0]) == 0x02 && Utils.byte2Int(values[1]) == 0xFF && Utils.byte2Int
                            (values[2]) == 0x40 && Utils.byte2Int
                            (values[3]) == 0x00) {//02-FF-40-00
                        //没有24小时数据
                        isPractisData = false;
                        isSyncData = true;
                        Logger.myLog("有24小时数据,开始收数据");

                        // bluetoothListener.onSyncStart();

                    } else if (Utils.byte2Int(values[0]) == 0x02 && Utils.byte2Int(values[1]) == 0xFF && Utils.byte2Int
                            (values[2]) == 0x41 && Utils.byte2Int
                            (values[3]) == 0x00) {//02-FF-41-00
                        //清除所有24小时数据
                        Logger.myLog("清除所有24小时数据成功");
                    } else if (Utils.byte2Int(values[0]) == 0x02 && Utils.byte2Int(values[1]) == 0xFF && Utils.byte2Int
                            (values[2]) == 0x42 && Utils.byte2Int
                            (values[3]) == 0x00) {//有数据 02-FF-42-00，
                        //有锻炼数据

                        Logger.myLog("有锻炼数据,开始收数据");
                        isPractisData = true;
                        isSyncData = true;
                        if (mPractiseHDATA == null) {
                            mPractiseHDATA = Collections.synchronizedList(new ArrayList<byte[]>());
                        }
                        mPractiseHDATA.clear();

                    } else if (Utils.byte2Int(values[0]) == 0x02 && Utils.byte2Int(values[1]) == 0xFF && Utils.byte2Int
                            (values[2]) == 0x42 && Utils.byte2Int
                            (values[3]) == 0x04) {//无数据 02-FF-42-04
                        //无锻炼数据
                        Logger.myLog("无锻炼数据");
                        //这里需要回调给上层，已经更新完成
                        //-1就是已经完成
                        if (bluetoothListener != null) {
                            bluetoothListener.onSyncSuccessPractiseData(-1);
                        }
                    } else if (Utils.byte2Int(values[0]) == 0x03 && Utils.byte2Int(values[1]) == 0xFF && Utils.byte2Int(values[2]) == 0x12) {
                        if (bluetoothListener != null) {
                            Logger.myLog(TAG,"天气设置成功 ");
                            bluetoothListener.onGetSettingSuccess();
                        }
                    } else if (Utils.byte2Int(values[0]) == 0x01 && Utils.byte2Int(values[1]) == 0x17 && Utils.byte2Int
                            (values[2]) == 01) {
                        Logger.myLog(TAG,"onStartSyncPractiseData");
                        if (bluetoothListener != null) {
                            bluetoothListener.onStartSyncPractiseData(0);
                        }
                    }
                    //01 0X18 01   天气预报无数据通知
                    else if (Utils.byte2Int(values[0]) == 0x01 && Utils.byte2Int(values[1]) == 0x18 && Utils.byte2Int
                            (values[2]) == 01) {
                        if (bluetoothListener != null) {
                            bluetoothListener.onStartSyncWheatherData();
                        }
                    }
                    //01 1c 50
                    else if (Utils.byte2Int(values[0]) == 0x01 && Utils.byte2Int(values[1]) == 0x1c) {
                        if (bluetoothListener != null) {
                            bluetoothListener.onGetBattery(Utils.byte2Int(values[2]));
                        }
                    }
                    //单次温度返回
                    else if (Utils.byte2Int(values[0]) == 0x03 && Utils.byte2Int(values[1]) == 0x3A && Utils.byte2Int
                            (values[2]) == 01) {
                        if (bluetoothListener != null) {
                            int temp = (Utils.byte2Int(values[4]) << 8) + Utils.byte2Int(values[3]);
                            Logger.myLog("单次温度返回" + temp);
                            bluetoothListener.onTempData(temp);
                        }
                    }
                    //单次血压返回
                    else if (Utils.byte2Int(values[0]) == 0x02 && Utils.byte2Int(values[1]) == 0x3c) {
                        if (bluetoothListener != null) {
                            Logger.myLog("单次血压返回" + Utils.byte2Int(values[2]) + "---" + Utils.byte2Int(values[3]));
                            bluetoothListener.onBloodData(Utils.byte2Int(values[2]), Utils.byte2Int(values[3]));
                        }
                    }
                    //单次血氧返回
                    else if (Utils.byte2Int(values[0]) == 0x01 && Utils.byte2Int(values[1]) == 0x3e) {
                        if (bluetoothListener != null) {
                            int temp = Utils.byte2Int(values[2]);
                            Logger.myLog("单次血氧返回" + temp);
                            bluetoothListener.onOxyData(temp);
                        }
                    }
                    else if (Utils.byte2Int(values[0]) == 0x02 && Utils.byte2Int(values[1]) == 0xFF && Utils.byte2Int
                            (values[2]) == 0x43 && Utils.byte2Int
                            (values[3]) == 0x00) {//02-FF-43-00
                        //清除所有锻炼数据成功
                        Logger.myLog("清除所有锻炼数据成功");
                    } else if (Utils.byte2Int(values[0]) == 0x02 && Utils.byte2Int(values[1]) == 0xFF && Utils.byte2Int
                            (values[2]) == 0x80 && Utils.byte2Int
                            (values[3]) == 0x00) {//02-FF-80-00
                        //生产阶段设置序列号
                        Logger.myLog("生产阶段设置序列号成功");
                    } else if (Utils.byte2Int(values[0]) == 0x02 && Utils.byte2Int(values[1]) == 0xFF && Utils.byte2Int
                            (values[2]) == 0x81 && Utils.byte2Int
                            (values[3]) == 0x00) {//02-FF-81-00
                        //生产阶段设置序列号
                        Logger.myLog("产品生产完成后，更改序列号成功");
                    } else if (Utils.byte2Int(values[0]) == 0x02 && Utils.byte2Int(values[1]) == 0xFF && Utils.byte2Int
                            (values[2]) == 0xE1 && Utils.byte2Int
                            (values[3]) == 0x00) {//02 FF E1 00
                        //停止测试马达和LED成功
                        Logger.myLog("停止或开始测试马达和LED成功");
                    } else if (Utils.byte2Int(values[0]) == 0x02 && Utils.byte2Int(values[1]) == 0xFF && Utils.byte2Int
                            (values[2]) == 0xE0 && Utils.byte2Int
                            (values[3]) == 0x00) {//02 FF E0 00
                        //设备复位
                        Logger.myLog("设备复位");
                    } else if (Utils.byte2Int(values[0]) == 0x02 && Utils.byte2Int(values[1]) == 0xFF && Utils.byte2Int
                            (values[2]) == 0xE4 && Utils.byte2Int
                            (values[3]) == 0x00) {//02 FF E4 00
                        //测试 OHR
                        Logger.myLog("测试 OHR");
                    } else if (Utils.byte2Int(values[0]) == 0x0C && Utils.byte2Int(values[1]) == 0xFF && Utils.byte2Int
                            (values[2]) == 0x82) {//0C-FF-82
                        //读取序列号和生产日期
//                        命令： 00-82
//                        应答：若序列号为 10000，生产日期为 2018 年 12 月 13 日 14 时 15 分 16 秒
//                        0C-FF-82-10-27-00-00-E2-07-0C-0D-0E-0F-10
                        long SN = (Utils.byte2Int(values[6]) << 32) + (Utils.byte2Int(values[5]) << 16) + (Utils
                                .byte2Int(values[4]) << 8) + Utils
                                .byte2Int
                                        (values[3]);
                        int year = (Utils.byte2Int(values[8]) << 8) + Utils.byte2Int(values[7]);
                        int non = Utils.byte2Int(values[9]);
                        int day = Utils.byte2Int(values[10]);
                        int hour = Utils.byte2Int(values[11]);
                        int min = Utils.byte2Int(values[12]);
                        int sec = Utils.byte2Int(values[13]);
                        Logger.myLog("读取序列号和生产日期 " + "SN == " + SN + " Time == " + year + "年" + non + "月" + day + "日"
                                + hour + "时" + min + "分" + sec + "秒");
                    }
                }
            } else if (characteristic.getUuid().equals(Constants.realTimeDataW516UUID)) {
                //实时数据通道 命令通道
//                02 5C 00 00 00 00 00 00

               /* final StringBuilder stringBuilder = new StringBuilder(values.length);
                for (byte byteChar : values) {
                    stringBuilder.append(String.format("%02X ", byteChar));
                }
                Logger.myLog(" 实时数据通道 ReceiverCmd " + stringBuilder.toString() + " UUID " + characteristic.getUuid()
                        .toString());*/

                //01 0x17 01  锻炼数据完成回调
                if (Utils.byte2Int(values[0]) == 0x01 && Utils.byte2Int(values[1]) == 0x17 && Utils.byte2Int
                        (values[2]) == 01) {
                    if (bluetoothListener != null) {
                        if (!isSyncData)
                            bluetoothListener.onStartSyncPractiseData(0);
                    }
                    return;
                }
                //01 0X18 01   天气预报无数据通知
                if (Utils.byte2Int(values[0]) == 0x01 && Utils.byte2Int(values[1]) == 0x18 && Utils.byte2Int
                        (values[2]) == 01) {
                    if (bluetoothListener != null) {
                        bluetoothListener.onStartSyncWheatherData();
                    }
                    return;
                }


                try {
                    if (bluetoothListener != null) {
                        bluetoothListener.onRealtimeStepData(Utils.byte2Int(values[1]), Utils.byteArrayToInt(new byte[]{
                                values[6], values[5], values[4]}), Utils.byteArrayToInt(new byte[]{
                                values[9], values[8], values[7]}), Utils.byteArrayToInt(new byte[]{
                                values[12], values[11], values[10]}));
                    }
                } catch (Exception e) {

                }

            } else if (characteristic.getUuid().equals(Constants.receiveDataW516UUID)) {
                final StringBuilder stringBuilder = new StringBuilder(values.length);
                for (byte byteChar : values) {
                    stringBuilder.append(String.format("%02X ", byteChar));
                }
             /*   saveLog(new StringBuilder(DateUtil.dataToString(new Date(), "HH:mm:ss")).append(" ReceiverCmd ")
                        .append
                                (stringBuilder.toString()).append(" UUID ").append(characteristic.getUuid().toString()));*/


                saveLog(new StringBuilder(DateUtil.dataToString(new Date(), "yyyy/MM/dd HH:mm:ss.SSS")).append(" ReceiverCmd ").append(stringBuilder.toString()));


                Logger.myLog(" ReceiverCmd " + stringBuilder.toString() + " UUID " + characteristic.getUuid()
                        .toString());
                if (isPractisData) {
                    if (mPractiseHDATA == null) {
                        mPractiseHDATA = Collections.synchronizedList(new ArrayList<byte[]>());
                    }
                    addPractiseData(values);
                } else {
                    if (m24HDATA == null) {
                        m24HDATA = Collections.synchronizedList(new ArrayList<byte[]>());
                    }
                    add24HDATA(values);
                }
            }
        } else if (characteristic.getService().getUuid().equals(Constants.HEART_RATE_SERVICE)) {
            if (characteristic.getUuid().equals(Constants.HEART_RATE_CHARACTERISTIC)) {
                //实时数据通道
//                if (bluetoothListener != null) {
//                    bluetoothListener.onRealtimeHeartRate(Utils.byte2Int(values[1]));
//                }
            }
        }
    }

    private void addPractiseData(byte[] values) {
        byte[] valuesResult = new byte[20];
        if (mPractiseHDATA.size() >= 2) {
            System.arraycopy(values, 1, valuesResult, 0, 19);
            // ParseData.parsW526PractiseData(mPractiseHDATA, bluetoothListener, mBaseDevice, mContext);
        } else {
            System.arraycopy(values, 0, valuesResult, 0, 19);
        }


        mPractiseHDATA.add(valuesResult);
        Logger.myLog("addPractiseData:" + mPractiseHDATA.size());
        int end = Utils.byte2Int(values[0]);
        if (end > 127) {
            isSyncData = false;
            ParseData.parsW526PractiseData(mPractiseHDATA, bluetoothListener, mBaseDevice, mContext);
        }

    }

    private void add24HDATA(byte[] values) {
        byte[] valuesResult = new byte[19];
        System.arraycopy(values, 1, valuesResult, 0, 19);
//        Logger.myLog("Utils.byte2Int(values[0]) = " + Utils.byte2Int(values[0]));
        if (Utils.byte2Int(values[0]) == 0) {
            m24HDATA.clear();
        }
        m24HDATA.add(valuesResult);
        int end = Utils.byte2Int(values[0]);
        Logger.myLog("end " + end);
        //最后一包的结束序号为大于127认为是结束
        if (end > 127) {
            //最后一包数据，开始解析
//            Logger.myLog("end 去解析>127");
            isSyncData = false;
            ParseData.parseW52624HData(m24HDATA, bluetoothListener, mBaseDevice, mContext);
        } else {
//            Logger.myLog("end 不去解析<127");
        }
    }

    @Override
    public void onCharacteristicWrite(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic, int status) {
        super.onCharacteristicWrite(gatt, characteristic, status);
        if (status == BluetoothGatt.GATT_SUCCESS) {
            Logger.myLog("GATT_WRITE_SUCCESS!");
            if (characteristic != null) {
                byte[] value = characteristic.getValue();
                if (value != null && value.length > 0) {
                    if (Utils.byte2Int(value[0]) == 0x0A && Utils.byte2Int(value[1]) == 0x32) {
//                        {0x09, 0x32, 0x00, 0x04, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00};


                        byte[] booleanArrayG = Utils.getBooleanArray(value[3]);
                        Logger.myLog("二进制各个bit值 == " + " 公英制 == " + (Utils.byte2Int(booleanArrayG[7]) == 0 ? "公制" :
                                "英制")
                                + " 中英文 == " + (Utils.byte2Int(booleanArrayG[6]) == 0 ? "英文" : "中文")
                                + " 小时制 == " + (Utils.byte2Int(booleanArrayG[5]) == 0 ? "12小时" : "24小时")
                                + " 抬腕亮屏 == " + (Utils.byte2Int(booleanArrayG[4]) == 0 ? "开启" : "关闭")
                                + " 24小时心率 == " + (Utils.byte2Int(booleanArrayG[3]) == 0 ? "开启" : "关闭"));
                        if (bluetoothListener != null) {
                            bluetoothListener.onSetGeneral(Utils.byte2Int(booleanArrayG[7]) == 0, Utils.byte2Int
                                    (booleanArrayG[6]) == 0, Utils.byte2Int(booleanArrayG[5]) == 0, Utils.byte2Int
                                    (booleanArrayG[4]) == 0, Utils.byte2Int(booleanArrayG[3]) == 0);
                        }
                    } else if (Utils.byte2Int(value[0]) == 0x12 && Utils.byte2Int(value[1]) == 0x35) {
                        if (bluetoothListener != null) {
                            Logger.myLog("repeat =111= " + Utils.byte2Int(value[3]));
                            bluetoothListener.onGetSettingSuccess();
                            bluetoothListener.onSetAlarm(Utils.byte2Int(value[3]), Utils.byte2Int(value[4]) + ":" + (Utils.byte2Int(value[5]) < 10 ? ("0" + Utils.byte2Int(value[5])) : Utils.byte2Int(value[5])), "");
                        }
                    } else if (Utils.byte2Int(value[0]) == 0x09 && Utils.byte2Int(value[1]) == 0x34) {
                        byte[] booleanArrayG = Utils.getBooleanArray(value[2]);
                        Logger.myLog("二进制各个bit值 == " + " 睡眠 == " + (Utils.byte2Int(booleanArrayG[7]) == 0 ? "自动睡眠" :
                                "窗口睡眠")
                                + " 窗口提醒开关 == " + (Utils.byte2Int(booleanArrayG[6]) == 0 ? "关" : "开")
                                + " 勿扰提醒开关 == " + (Utils.byte2Int(booleanArrayG[5]) == 0 ? "关" : "开"));
                        if (bluetoothListener != null) {
                            bluetoothListener.onSetSleepAndNoDisturb(Utils.byte2Int(booleanArrayG[7]) == 0, Utils.byte2Int(booleanArrayG[6]) != 0, Utils.byte2Int(booleanArrayG[5]) != 0,
                                    Utils.byte2Int(value[3]) + ":" + (Utils.byte2Int(value[4]) < 10 ? ("0" + Utils.byte2Int(value[4])) : Utils.byte2Int(value[4])),
                                    Utils.byte2Int(value[5]) + ":" + (Utils.byte2Int(value[6]) < 10 ? ("0" + Utils.byte2Int(value[6])) : Utils.byte2Int(value[6])),
                                    Utils.byte2Int(value[7]) + ":" + (Utils.byte2Int(value[8]) < 10 ? ("0" + Utils.byte2Int(value[8])) : Utils.byte2Int(value[8])),
                                    Utils.byte2Int(value[9]) + ":" + (Utils.byte2Int(value[10]) < 10 ? ("0" + Utils.byte2Int(value[10])) : Utils.byte2Int(value[10])));
                        }
                    } else if (Utils.byte2Int(value[0]) == 0x05 && Utils.byte2Int(value[1]) == 0x31) {
                        if (bluetoothListener != null) {
                            bluetoothListener.onSetSedentary(Utils.byte2Int(value[2]), (Utils.byte2Int(value[3]) < 10 ? ("0" + Utils.byte2Int(value[3])) : Utils.byte2Int(value[3])) + ":" + (Utils.byte2Int(value[4]) < 10 ? ("0" + Utils.byte2Int(value[4])) : Utils.byte2Int(value[4])), (Utils.byte2Int(value[5]) < 10 ? ("0" + Utils.byte2Int(value[5])) : Utils.byte2Int(value[5])) + ":" + (Utils.byte2Int(value[6]) < 10 ? ("0" + Utils.byte2Int(value[6])) : Utils.byte2Int(value[6])));
                        }
                    }
                }
            }
        } else if (status == BluetoothGatt.GATT_WRITE_NOT_PERMITTED) {
            Logger.myLog("GATT_WRITE_NOT_PERMITTED");
        } else {
            Logger.myLog("Write failed , Status is " + status);
        }
    }

    @Override
    public void onCharacteristicChanged(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic) {
        super.onCharacteristicChanged(gatt, characteristic);
        handleReceive(gatt, characteristic);
    }


    //***************************************************指令部分***********************************************//

    /**
     * 2 同步时间戳
     * 实例:
     * 命令：若设置内容为: 2018 年 12 月 13 日 14 点 15 分 16 秒
     * 07-30-E2-07-0C-0D-0E-0F-10
     * 应答：02-FF-30-00
     */
    public void syncTime() {
        if (isConnected) {
            Calendar instance = Calendar.getInstance();
            char[] year = String.format("%04x", instance.get(Calendar.YEAR)).toCharArray();
            int month = instance.get(Calendar.MONTH);
            int day = instance.get(Calendar.DAY_OF_MONTH);
            int hour = instance.get(Calendar.HOUR_OF_DAY);
            int minute = instance.get(Calendar.MINUTE);
            int second = instance.get(Calendar.SECOND);
            Logger.myLog(String.format("%02X ", Utils.uniteBytes(year[2], year[3])) + "***" + String.format("%02X ",
                    Utils.uniteBytes(year[0], year[1])));
            byte[] cmds = new byte[]{0x07, 0x30, Utils.uniteBytes(year[2], year[3]), Utils.uniteBytes(year[0],
                    year[1]),
                    (byte) (month + 1), (byte) day, (byte) hour, (byte) minute, (byte) second};
            writeTXCharacteristicItem(cmds);
        }
    }

    /**
     * 获取用户信息
     * 命令：00-05
     * 应答：
     * 若设置内容为: brith day: 1981 年 12 月 12 日，female，体重 100kg，身高 170， 最大心率 190， 最低心率 60
     * 0B-FF-05-BD-07-0C-0C-00-E8-03-AA-BE-3C
     */
    private void queryUserInfo() {
        if (isConnected) {
            byte[] data = new byte[]{0x00, 0x05};
            writeTXCharacteristicItem(data);
        }
    }

    /**
     * 获取用户信息
     * 命令：若设置内容为: brith day: 1981 年 12 月 12 日，female，体重 100kg，身高 170， 最大心率 190， 最低
     * 心率 60
     * 0A-33-BD-07-0C-0C-00-E8-03-AA-BE-3C
     * 0A 33 C7 07 01 01 01 4C 1D AF 00 00
     * 应答：02-FF-33-00
     */
    public void setUserInfo(int year, int month, int day, int sex, int weight, int height, int maxHeartRate, int
            minHeartRate) {
        if (isConnected) {
            char[] yearChar = String.format("%04x", year).toCharArray();
            char[] weightChar = String.format("%04x", weight * 10).toCharArray();

            byte[] data = new byte[]{0x0A, 0x33, Utils.uniteBytes(yearChar[2], yearChar[3]), Utils.uniteBytes
                    (yearChar[0],
                            yearChar[1]), (byte) month, (byte) day, (byte) sex, Utils.uniteBytes(weightChar[2],
                    weightChar[3]), Utils
                    .uniteBytes(weightChar[0],
                    weightChar[1]), (byte) height, (byte) maxHeartRate,
                    (byte) minHeartRate};
            writeTXCharacteristicItem(data);
        }
    }

    /**
     * 通用设置
     * 命令：若设置内容为全部设置，公制，英文，24 小时，抬手亮屏，关闭 24 小时心率，无界面设置
     * 09-32-00-14-00-00-00-00-00-00-00
     * 应答：02-FF-32-00
     */
    public void setGeneral() {
        if (isConnected) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("0000");
//            if (open24HeartRate){
//                stringBuilder.append("0");
//            }else{
            //默认关闭24小时心率
            stringBuilder.append("1");
//            }
            stringBuilder.append("0");
            stringBuilder.append("1");
            stringBuilder.append("0");
            stringBuilder.append("0");
            int value = Integer.valueOf(stringBuilder.toString(), 2);
            byte[] data = new byte[]{0x09, 0x32, 0x00, (byte) value, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00};
            writeTXCharacteristicItem(data);
        }
    }

    /**
     * reset 恢复出厂
     * 命令 04-50-A1-FE-74-69
     * 应答：若设备未配对： 02-FF-50-01
     * 若设备已经配对：02-FF-50-00
     */
    public void reset() {
        if (isConnected) {
            byte[] data = new byte[]{0x04, 0x50, (byte) 0xA1, (byte) 0xFE, 0x74, 0x69};
            writeTXCharacteristicItem(data);
        }
    }

    /**
     * 电话
     */
    public void handleCall() {
        if (isConnected) {
            Log.e("handleD", "handleCall");
            byte[] data = new byte[]{(byte) 0x02, 0x10, (byte) 0x80, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,
                    0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00};
            writeTXCharacteristicItem(data);
        }
    }

    /**
     * 短信
     */
    public void handleSms() {
        if (isConnected) {
            if ((System.currentTimeMillis() - mExitTime) > 1000) {
                mExitTime = System.currentTimeMillis();
                Log.e("handleD", "handleSms");
                byte[] data = new byte[]{(byte) 0x02, 0x10, (byte) 0x80, 0x01, 0x00, 0x00, 0x00, 0x00, 0x00,
                        0x00, 0x00,
                        0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00};
                writeTXCharacteristicItem(data);
            }
        }
    }

    /**
     * 发送通知  应用信息，短信等
     *
     * @param ctx
     * @param packagename  whose notifications you want to push to ble device
     * @param notification the notification will be pushed to ble device
     *                     命令：来电通知，电话号码 1234567890，12-10-80-00-31-32-33-34-35-36-37-38-39-30-00-00-00-00-00-00
     *                     应答：02-FF-60-00
     */
    public void handleNotification(Context ctx, String packagename, Notification notification) {
        if (isConnected && Constants.strPkNames.contains(packagename)) {
            Log.e("handleD", "handleNotification");
            byte[] data = new byte[]{(byte) 0x02, 0x10, (byte) 0x80, 0x01, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,
                    0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00};
            writeTXCharacteristicItem(data);
        }
    }

    /**
     * 0x00 控制指针转动
     * 0x01 手表指针回到 0 点位置
     * 0x02 DEMO mode
     * 切换模式
     * FF退出demo模式
     *
     * @param mode
     */
    public void switchMode(int mode) {
        if (isConnected) {
            byte[] cmds = new byte[]{0x01, (byte) 0xE2, (byte) mode};
            writeTXCharacteristicItem(cmds);
        }
    }

    public void switchAdjust(boolean enable) {
        if (isConnected) {
            byte[] cmds;
            if (enable) {
                cmds = new byte[]{0x01, 0x11, 0x01};
            } else {
                cmds = new byte[]{0x01, 0x11, (byte) 0xFF};
            }
            if (enable) {
                sendWhat = Order_send_adjust_mode;
            } else {
                sendWhat = Order_send_exit_adjust_mode;
            }
            writeTXCharacteristicItem(cmds);
        }
    }


    /**
     * 调时针 分针
     */
    public void adjustHourAndMin(int degreesHour, int degreesMin) {
        if (isConnected) {
            byte[] cmds = new byte[]{0x03, 0x11, 0x00, (byte) degreesHour, (byte) degreesMin};
            sendWhat = Order_send_exit_adjust_mode_adjusting;
            writeTXCharacteristicItem(cmds);
        }
    }

    //***************************************************指令部分***********************************************//


    @Override
    public void onConnectionStateChange(BluetoothGatt gatt, int status, int newState) {
        super.onConnectionStateChange(gatt, status, newState);
        if (status == BluetoothGatt.GATT_SUCCESS) {

        } else {
            hasSyncTime = false;
        }
    }

    public boolean enableNotification() {
        if (mResponceChar != null) {
            return internalEnableNotifications(mResponceChar);
        } else {
            return false;
        }
    }

    /**
     * 需要指定的特征值
     *
     * @param data
     * @return
     */
    public boolean writeTXCharacteristicItem(byte[] data) {
        if (isConnected && data != null) {
            if (mNRFBluetoothGatt == null) {
                Logger.myLog("Gatt is null");
                return false;
            }
            final StringBuilder stringBuilder = new StringBuilder();
            for (byte byteChar : data) {
                stringBuilder.append(String.format("%02X ", byteChar));
            }
            saveLog(new StringBuilder(DateUtil.dataToString(new Date(), "yyyy/MM/dd HH:mm:ss.SSS") + " sendCommand " + stringBuilder.toString()));
            Logger.myLog("writeTXCharacteristicItem " + stringBuilder.toString());
            mSendChar.setValue(data);
            // boolean status = mNRFBluetoothGatt.writeCharacteristic(mSendChar);

            boolean status = false;
            int mRetry = 0;

            while (!status && isConnected && mNRFBluetoothGatt != null && mRetry < 3) {
                status = mNRFBluetoothGatt.writeCharacteristic(mSendChar);
                mRetry++;
                saveLog(new StringBuilder(DateUtil.dataToString(new Date(), "yyyy/MM/dd HH:mm:ss.SSS") + "Write:" + status + ",mRetry:" + mRetry + ",isConnected:" + isConnected));
                if (status) {
                    mSendOk = true;
                }
                if (!mSendOk) {
                    SystemClock.sleep(20);
                }
            }

          /*  int mRetry = 0;
            while (mRetry < 5 && isConnected && mNRFBluetoothGatt != null && !mSendOk) {
                boolean status = mNRFBluetoothGatt.writeCharacteristic(mSendChar);
                mRetry++;
                Logger.myLog("Write data status:" + status + ",mRetry:" + mRetry + ",isConnected:" + isConnected + ",mSendOk:" + mSendOk + ",mNRFBluetoothGatt:" + mNRFBluetoothGatt);
                if (status) {
                    //   mSendOk = true;
                }
                if (!mSendOk) {
                    SystemClock.sleep(200);
                }
            }*/
            return status;
           /* boolean status = mNRFBluetoothGatt.writeCharacteristic(mSendChar);
            if (status == false) {
                Logger.myLog("Write data failed!");
                return status;
            }
            return true;*/
        } else {
            return false;
        }
    }


    public int getQueuryLenth() {
        return cmds.size();
    }

    public void clearQueuryData() {
        cmds.clear();
    }

    public void addQueuryData(DataBean data) {
        cmds.offer(data);
    }

    public DataBean pollQueuryData() {

        return cmds.poll();
    }
}
