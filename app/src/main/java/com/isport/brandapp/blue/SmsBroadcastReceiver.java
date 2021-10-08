package com.isport.brandapp.blue;

/**
 * @author Created by Marcos Cheng on 2016/6/2.
 * If there are sms and {@link com.isport.isportlibrary.entry.NotificationEntry#isAllowSMS} is true, it will be
 * pushed to ble device
 */

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.text.TextUtils;
import android.util.Log;

import com.crrepa.ble.conn.type.CRPBleMessageType;
import com.isport.blelibrary.ISportAgent;
import com.isport.blelibrary.db.action.watch_w516.Watch_W516_NotifyModelAction;
import com.isport.blelibrary.db.table.watch_w516.Watch_W516_NotifyModel;
import com.isport.blelibrary.deviceEntry.impl.BaseDevice;
import com.isport.blelibrary.deviceEntry.interfaces.IDeviceType;
import com.isport.blelibrary.entry.NotificationMsg;
import com.isport.blelibrary.utils.BleRequest;
import com.isport.blelibrary.utils.Utils;
import com.isport.brandapp.AppConfiguration;
import com.isport.brandapp.util.DeviceTypeUtil;

import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import androidx.core.app.ActivityCompat;
import brandapp.isport.com.basicres.BaseApp;
import brandapp.isport.com.basicres.commonutil.TokenUtil;

public class SmsBroadcastReceiver extends BroadcastReceiver {
    private static final String TAG = SmsBroadcastReceiver.class.getSimpleName();
    static String regEx = "[\u4e00-\u9fa5]";
    static Pattern pat = Pattern.compile(regEx);

    public static boolean isContainsChinese(String str) {
        Matcher matcher = pat.matcher(str);
        boolean flg = false;
        if (matcher.find()) {
            flg = true;
        }
        return flg;
    }

    public static String Bytes2String(byte[] bData) {
        int nLen = 0;
        for (int i = 0; i < bData.length; i++) {
            if (bData[i] != 0) nLen += 1;
            else break;
        }
        byte[] bStringBytes = new byte[nLen];
        for (int j = 0; j < nLen; j++) {
            bStringBytes[j] = bData[j];
        }
        String sbyte = null;
        try {
            //  sbyte = new String(bStringBytes, "GBK");// "mnw
            sbyte = new String(bStringBytes, "UTF-8");// "mnw

        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return sbyte;// "mnw
    }

    private long mExitTime = 0;

    public void onReceive(Context context, Intent intent) {
        try {
            Log.e("SmsBroadcastReceiver", "onReceive");
            if (ActivityCompat.checkSelfPermission(context, Manifest.permission.RECEIVE_SMS) != PackageManager
                    .PERMISSION_GRANTED) {
                Log.e("SmsBroadcastReceiver", "onReceive2");
                return;
            }
//            MainService mainService = MainService.getInstance(context);
//            if (mainService == null || mainService.getConnectionState() != BaseController.STATE_CONNECTED)
//                return;
//            BaseController baseController = mainService.getCurrentController();
            SmsMessage msg = null;
            Bundle bundle = intent.getExtras();
//            NotificationEntry entry = NotificationEntry.getInstance(context);
//            if (bundle != null && entry.isAllowSMS() && entry.isOpenNoti()) {
            if (bundle != null) {
                Object[] pdusObj = (Object[]) bundle.get("pdus");
                if (pdusObj != null) {
                    for (Object p : pdusObj) {
                        msg = SmsMessage.createFromPdu((byte[]) p);
                        String msgTxt = msg.getMessageBody();//get message body
                        Date date = new Date(msg.getTimestampMillis());//message time
                        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        String senderNumber = msg.getOriginatingAddress();


                        String senderName = ContentUtils.contactNameByNumber(context, senderNumber);
//                    NotificationEntry notificationEntry = NotificationEntry.getInstance(context);
//
//                    boolean isopen_sms = notificationEntry.isAllowSMS();
//                    Log.e("TAG", "phone numeber " + senderNumber + "sms content ===" + msgTxt + "  isopen_sms = " +
// isopen_sms);
//                    if (isopen_sms && msgTxt != null) {
                        if (msgTxt != null) {
                            byte[] strMsg = msgTxt.getBytes("UTF-8");
                            byte[] strNum = senderNumber.getBytes("UTF-8");
                            Log.e("TAG", "currentTimeMillis == " + System.currentTimeMillis() + ",mExitTime:" + mExitTime + "mExitTime == " + mExitTime + "msgTxt：" + msgTxt + ":strNum:" + strNum + "msgTxt:" + msgTxt + "senderName:" + senderName);
                            if ((System.currentTimeMillis() - mExitTime) > 1000) {
                                mExitTime = System.currentTimeMillis();
                                if (AppConfiguration.isConnected) {
                                    BaseDevice device = ISportAgent.getInstance().getCurrnetDevice();
                                    if (device == null) {
                                        return;
                                    }
                                    String deviceName = device.deviceName;
                                    int deviceType = device.deviceType;
                                    // TODO: 2018/11/8 同步解绑的逻辑
                                    Watch_W516_NotifyModel watch_w516_notifyModelByDeviceId = Watch_W516_NotifyModelAction.findWatch_W516_NotifyModelByDeviceId(deviceName, TokenUtil.getInstance().getPeopleIdInt(BaseApp.getApp()));

                                    if (watch_w516_notifyModelByDeviceId == null) {
                                        return;
                                    }
                                    boolean isMessageSwitch = watch_w516_notifyModelByDeviceId.getMsgSwitch();

                                    if (isMessageSwitch == false) {
                                        return;
                                    }
                                    if (DeviceTypeUtil.isContainW55X(deviceType)) {
                                        if (!TextUtils.isEmpty(senderName)) {
                                            senderNumber = senderName;
                                        }

                                        if((device.getDeviceType() == IDeviceType.TYPE_WATCH_W560 || device.getDeviceType() == IDeviceType.TYPE_WATCH_W560B) &&( Build.BOARD.toLowerCase().equals("huawei") || Build.BOARD.toLowerCase().equals("honor"))){
                                            ISportAgent.getInstance().requestBle(BleRequest.w526_send_message, senderNumber, msgTxt, 1);
                                            return;
                                        }

                                        //ISportAgent.getInstance().requestBle(BleRequest.w526_send_message, senderNumber, msgTxt, CRPBleMessageType.MESSAGE_SMS + 1);
                                    } else if (DeviceTypeUtil.isContainWatch(deviceType)) {
                                        ISportAgent.getInstance().requestBle(BleRequest.Watch_W516_SEND_NOTIFICATION, 1);
                                    } else if (DeviceTypeUtil.isContainWrishBrand(deviceType)) {
                                        handlerW311Msg(true, msgTxt, senderNumber, senderName, 18);
                                    } else if (DeviceTypeUtil.isContaintW81(deviceType)) {
                                        if (DeviceTypeUtil.isContaintW81(deviceType)) {
                                            if (!TextUtils.isEmpty(senderName)) {
                                                senderNumber = senderName;
                                            }
                                            ISportAgent.getInstance().requestBle(BleRequest.w81_send_message, senderNumber + ":" + msgTxt, CRPBleMessageType.MESSAGE_SMS);
                                        }
                                    }
                                }
                            }
                        }
                    }
                    return;
                }
                return;
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }


    private void handlerW311Msg(boolean isKitKat, String text, String title, String tickerText, int type) throws
            UnsupportedEncodingException {
//                ***text ***Vghh ***title ***‪+86 177 2047 9861‬: ​***tickerText ***null ***type ***27
//                ***text ***gh ***title ***Test(‎3条信息):madbug ***tickerText ***null ***type ***27
//                ***text ***madbug @Test :gh ***title ***WhatsApp ***tickerText ***null ***type ***27
        byte[] btTicket = null;
        if (tickerText != null) {
            btTicket = tickerText.getBytes("UTF-8");
        }
        byte[] content = new byte[60];//所有包括title和content最大60的长度

        //todo 内容和title都未去掉空格
        if (isKitKat) {
         /*   if (CmdController.getInstance(mContext).getBaseDevice() != null) {
                int deviceType = CmdController.getInstance(mContext).getBaseDevice().getDeviceType();
                String deviceName = CmdController.getInstance(mContext).getBaseDevice().getName();
                String tpName = (deviceName == null ? "" : deviceName.contains("_") ? deviceName.split("_")[0] : deviceName.contains("-") ?
                        deviceName.split("-")[0] : deviceName.split(" ")[0]).toLowerCase();
                float version = CmdController.getInstance(mContext).getVersion();*/
//            if (BaseDevice.TYPE_W307S==deviceType){
//                //todo  费费客户307s要求title和content衔接在一起
//            }
            Log.e(TAG, "**是REFLEX发送**" + " title == " + title + " text == " + text);
            String needSendStr;
            needSendStr = title + " " + text;
            StringBuilder needSendStrbuilder = new StringBuilder();
            for (int i = 0; i < needSendStr.length(); i++) {
                String bb = needSendStr.substring(i, i + 1);
// 生成一个Pattern,同时编译一个正则表达式,其中的u4E00("一"的unicode编码)-\u9FA5("龥"的unicode编码)
                if (Utils.isGB2312(bb)) {
                    Log.e(TAG, "**是中文用空格代替 == " + bb);
                    needSendStrbuilder.append(" ");
                } else {
                    Log.e(TAG, "**不是中文直接添加== " + bb);
                    needSendStrbuilder.append(bb);
                }
            }
//                char[] chars = needSendStr.toCharArray();
//                for (int i = 0; i < chars.length; i++) {
//                    if (isGB2312(String.valueOf(chars[i]))){
//                        //是中文，直接用" "空格代替
//                        Log.e(TAG, "**是中文用空格代替 == "+String.valueOf(chars[i]));
//                        needSendStrbuilder.append(" ");
//                    }else{
//                        //不是中文，直接拼接
//                        Log.e(TAG, "**不是中文直接添加== "+String.valueOf(chars[i]));
//                        needSendStrbuilder.append(String.valueOf(chars[i]));
//                    }
//                }
            needSendStr = needSendStrbuilder.toString();
            Log.e(TAG, "**要发送的Str== " + needSendStr);
            byte[] byTextTp = needSendStr.getBytes("UTF-8");
            if (byTextTp.length <= 60) {//当长度小于45时补0
                Log.e(TAG, "**title和content的总长度小于等于60，不足的地方补0**");
                System.arraycopy(byTextTp, 0, content, 0, byTextTp.length);
                content = Utils.addFF(content, byTextTp.length, 59);
            } else {//长度大于45切割最前面的45
                Log.e(TAG, "**title和content的长度大于60，截取60字节**");
                System.arraycopy(byTextTp, 0, content, 0, 59);
            }
            StringBuilder builder = new StringBuilder();
            for (int i = 0; i < content.length; i++) {
                builder.append(String.format("%02X ", content[i]));
            }
            Log.e(TAG, "**要去发送**" + builder.toString());
        }
//        byte[] ddd = new byte[15];
//        System.arraycopy(content, 0, ddd, 0, 15);
        // CmdController.getInstance(mContext).sendNotiCmd(new NotificationMsg(type, content));

        BaseDevice device = ISportAgent.getInstance().getCurrnetDevice();
        if (device == null) {
            return;
        }

        if (DeviceTypeUtil.isContainWrishBrand(device.deviceType)) {
            //判断是否已经打开消息开关
            NotificationMsg msg = new NotificationMsg(type, content);
            ISportAgent.getInstance().requestBle(BleRequest.bracelet_send_msg, msg);
        }

    }


}