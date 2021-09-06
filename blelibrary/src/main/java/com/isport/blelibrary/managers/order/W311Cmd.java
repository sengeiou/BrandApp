package com.isport.blelibrary.managers.order;

import android.content.Context;

import com.isport.blelibrary.entry.AlarmEntry;
import com.isport.blelibrary.entry.AutoSleep;
import com.isport.blelibrary.entry.DisplaySet;
import com.isport.blelibrary.entry.SedentaryRemind;
import com.isport.blelibrary.entry.WristMode;
import com.isport.blelibrary.managers.BaseManager;
import com.isport.blelibrary.utils.DateUtil;
import com.isport.blelibrary.utils.StepArithmeticUtil;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;

/**
 * @Author
 * @Date 2018/11/1
 * @Fuction
 */

public class W311Cmd extends ISportOrder {
    /**
     * set base time, but you needn't call it in general
     */
    public byte[] sendBaseTime(Context context, int type) {
        byte is24 = (byte) (DateUtil.is24Hour(context) ? 0 : 1);
        byte metricImperal = 0;
        int timezone = getActiveTimeZone();
        byte activeTimeZone = (byte) (timezone < 0 ? Math.abs(timezone) * 2 + 0x80 : Math.abs(timezone) * 2);
        timezone = DateUtil.getTimeZone();
        byte currentTimeZone = (byte) (timezone < 0 ? Math.abs(timezone) * 2 + 0x80 : Math.abs(timezone) * 2);
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) + 1;
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int week = calendar.get(Calendar.DAY_OF_WEEK) - 1;
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);
        int seconds = calendar.get(Calendar.SECOND);

      /*  byte[] time = new byte[]{(byte) 0xbe, (byte) 0x01, (byte) type, (byte) 0xfe, metricImperal, is24,
                activeTimeZone, currentTimeZone,
                (byte) (year >> 8), (byte) year, (byte) month, (byte) day, (byte) week, (byte) hour, (byte)
                minute, (byte) seconds};*/

        byte[] time = new byte[]{(byte) 0xbe, (byte) 0x01, (byte) type, (byte) 0xfe,
                (byte) (year >> 8), (byte) year, (byte) month, (byte) day, (byte) week, currentTimeZone, (byte)
                hour, (byte) minute, (byte) seconds, is24};
        return time;

    }

    public int getActiveTimeZone() {
        String timezoneId = null;
        if (timezoneId == null) {
            timezoneId = TimeZone.getDefault().getID();
        }
        TimeZone timezone = TimeZone.getTimeZone(timezoneId);
        int rawOffSet = timezone.getRawOffset();
        int offset = rawOffSet / 3600000;
        return offset;
        /*String timezone = TimeZone.getDefault().getDisplayName(true, TimeZone.SHORT).trim().substring(3).split(":")[0];
        String tmp = timezone.substring(0,1);
        boolean isPlus = true;
        if(tmp.equals("+")){
            timezone = timezone.substring(1,timezone.length());
            isPlus = true;
        }else if (tmp.equals("-")){
            timezone = timezone.substring(1,timezone.length());
            isPlus = false;
        }
        int tz = (isPlus?Integer.valueOf(timezone):((-1)*Integer.valueOf(timezone)));
        return sharedPreferences.getInt(USER_ACTIVE_TIME_ZONE,
                Integer.valueOf(tz));*/
    }

    //手机首次连接设备成功后，通知设备震动提醒并显示勾
    public byte[] sendVibrateConnected() {
        byte[] data = new byte[]{(byte) 0xbe, 0x06, 0x31, (byte) 2, (byte) (true ? 1 : 0),
                (byte)
                        0xed};
        return data;
    }

    //打开实时命令 BE0203ED
    public byte[] openReal() {
        byte[] data = new byte[]{(byte) 0xbe, 0x02, 0x03, (byte) 0xed};
        return data;
    }

    public byte[] sendCmdSync() {
        byte[] data = new byte[]{(byte) 0xbe, 0x02, 0x05, (byte) 0xed};
        return data;
    }

    /**
     * 设置用户信息
     *
     * @param context
     * @return
     */
    public byte[] sendUserInfo(Context context) {

        //这里来去数据的目标步数，如果是

        int year = BaseManager.mYear;
        int month = BaseManager.mMonth;
        int day = BaseManager.mDay;
        int targetSteps = BaseManager.targStep;
        int weight = (int) BaseManager.mWeight * 100;
        //weight = (int) ((metricImperial == 0 ? weight : weight / 0.45359237f));
        //9）性别： 00-－女性； 01-男性
        int strideLength = (int) (60 * 100);


        strideLength = StepArithmeticUtil.getStep(BaseManager.mHeight, BaseManager.mSex) * 100;


        // Logger.myLog("sendUserInfo BaseManager.mHeight:" + BaseManager.mHeight + ",BaseManager.mSex" + BaseManager.mSex + ",strideLength:" + strideLength);

        // Logger.myLog("BaseManager.mHeight:" + BaseManager.mHeight + ",BaseManager.mWeight :" + BaseManager.mWeight + ",BaseManager.targStep" + BaseManager.targStep + ",BaseManager.mAge:" + BaseManager.mAge + ",BaseManager.mSex:" + BaseManager.mSex + ",strideLength:" + strideLength);
        //strideLength = (metricImperial == 0 ? strideLength : (int) (strideLength * 2.54));
        int height = Math.round(BaseManager.mHeight * 100);
        AutoSleep autoSleep = AutoSleep.getInstance(context);
        int sleepHour = autoSleep.getSleepTargetHour();
        int sleepMin = autoSleep.getSleepTargetMin();
        //Logger.myLog("userinfo:weight:" + weight + ",height:" + height);
        return new byte[]{(byte) 0xbe, (byte) 0x01, (byte) 0x03, (byte) 0xfe, (byte) ((height & 0xffff) >> 8)
                , (byte) height, (byte) BaseManager.mAge, (byte) BaseManager.mSex,
                (byte) (weight >> 8), (byte) weight, (byte) (targetSteps >> 16),
                (byte) (targetSteps >> 8), (byte) targetSteps, (byte) (strideLength >> 8), (byte)
                strideLength, (byte) sleepHour, (byte) sleepMin};
    }


    /**
     * 同步的具体指令
     *
     * @param year
     * @param month
     * @param day
     */
    public byte[] sendSyncDay(int year, int month, int day) {
        byte[] data = new byte[10];
        data[0] = (byte) 0xbe;
        data[1] = 0x02;
        data[2] = 0x01;
        data[3] = (byte) 0xfe;
        data[4] = (byte) (year >> 8);
        data[5] = (byte) year;
        data[6] = (byte) month;
        data[7] = (byte) day;
        data[8] = 0;
        data[9] = 0;
        //sendCommand(SEND_DATA_CHAR, mGattService, mBluetoothGatt, data);
        //同步哪一天时，发送超时
        // syncHandler.sendEmptyMessageDelayed(0x01, DEFAULT_SYNC_TIMEOUT);

        return data;
    }

    /**
     * to reset device
     */
    public byte[] reset() {
        //byte[] bs = new byte[]{(byte) 0xff, (byte) 0xfa, (byte) 0xfc, (byte) 0xf7, 0x00, 0x01, 0x02, 0x07,
        // 0x55, 0x33, 0x66, 0x31, 0x18, (byte) 0x89, 0x60, 0x00};
        byte[] bs = new byte[]{(byte) 0xBE, 0x06, 0x30, (byte) 0xED};
        return bs;
    }

    /**
     * set base time, but you needn't call it in general
     */
   /* public void sendBaseTime() {
        if (state == STATE_CONNECTED) {
            UserInfo userInfo = UserInfo.getInstance(context);
            byte is24 = (byte) (DateUtil.is24Hour(context) ? 0 : 1);
            byte metricImperal = (byte) userInfo.getMetricImperial();
            int timezone = userInfo.getActiveTimeZone();
            byte activeTimeZone = (byte) (timezone < 0 ? Math.abs(timezone) * 2 + 0x80 : Math.abs(timezone) * 2);
            timezone = DateUtil.getTimeZone();
            byte currentTimeZone = (byte) (timezone < 0 ? Math.abs(timezone) * 2 + 0x80 : Math.abs(timezone) * 2);
            Calendar calendar = Calendar.getInstance();
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH) + 1;
            int day = calendar.get(Calendar.DAY_OF_MONTH);
            int week = calendar.get(Calendar.DAY_OF_WEEK) - 1;
            int hour = calendar.get(Calendar.HOUR_OF_DAY);
            int minute = calendar.get(Calendar.MINUTE);
            int seconds = calendar.get(Calendar.SECOND);

            byte[] time = new byte[]{(byte) 0xbe, (byte) 0x01, (byte) 0x01, (byte) 0xfe, metricImperal, is24,
                    activeTimeZone, currentTimeZone,
                    (byte) (year >> 8), (byte) year, (byte) month, (byte) day, (byte) week, (byte) hour, (byte)
                    minute, (byte) seconds};
            sendCommand(SEND_DATA_CHAR, mGattService, mBluetoothGatt, time);
        }
    }*/

    /**
     * if one of the alarms  is on,the alarm is on
     *
     * @param list the size no more than 5
     */
    public byte[] w311setAlarm(List<AlarmEntry> list) {

        byte[] data = new byte[20];
        data[0] = (byte) 0xbe;
        data[1] = 0x01;
        data[2] = 0x09;
        data[3] = (byte) 0xfe;

        int states = 0;
        int index = 5;
        for (int i = 0; i < (list.size() > 5 ? 5 : list.size()); i++) {

            AlarmEntry entry = list.get(i);
            data[index] = (byte) entry.getStartHour();
            index++;
            data[index] = (byte) entry.getStartMin();
            index++;
            //byte repeat = ((byte) (entry.getRepeat() > 0 ? (entry.getRepeat() | 0x80) : entry.getRepeat()));
            byte repeat = ((byte) ((entry.getRepeat() & 0xff) > 0 ? ((entry.getRepeat() & 0xff) & 0x7F) : entry
                    .getRepeat()));
            data[index] = repeat;
            index++;
            if (entry.isOn()) {
                states = states + (1 << i);
            }
        }
        data[4] = (byte) states;
        if (list.size() < 5) {
            for (int i = list.size(); i < 5; i++) {
                data[index] = (byte) 0xff;
                index++;
                data[index] = (byte) 0xff;
                index++;
                data[index] = 0x00;
                index++;
            }


        }
        return data;
    }

    //********************************************W311**************************************************//

    /**
     * 校正设备的系统时间
     * sync time to ble device
     *
     * @param context
     * @return
     */
    public static byte[] send_syncTime(Context context) {
        int timezone = DateUtil.getTimeZone();
        byte currentTimeZone = (byte) (timezone < 0 ? Math.abs(timezone) * 2 + 0x80 : Math.abs(timezone) * 2);
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) + 1;
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int week = calendar.get(Calendar.DAY_OF_WEEK) - 1;
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);
        int seconds = calendar.get(Calendar.SECOND);
        byte is24 = (byte) (DateUtil.is24Hour(context) ? 0 : 1);

        byte[] time = new byte[]{(byte) 0xbe, (byte) 0x01, (byte) 0x02, (byte) 0xfe,
                (byte) (year >> 8), (byte) year, (byte) month, (byte) day, (byte) week, currentTimeZone, (byte)
                hour, (byte) minute, (byte) seconds, is24};
        return time;
    }

    /**
     * 设置用户的基本信息
     * send user info to device, for example birthday, height, weight, target step ,stride length and so on.
     * 身高/体重/步距部分*100
     *
     * @return
     */
    public static byte[] send_set_userInfo(int height, int age, int gender, int weight, int targetSteps, int strideLength, int sleepHour, int sleepMin) {
        weight = weight * 100;
        strideLength = strideLength * 100;
        height = height * 100;
        byte[] data = new byte[]{(byte) 0xbe, (byte) 0x01, (byte) 0x03, (byte) 0xfe, (byte) (height >> 8), (byte) height, (byte) age, (byte)
                gender, (byte) (weight >> 8), (byte) weight, (byte) (targetSteps >> 16),
                (byte) (targetSteps >> 8), (byte) targetSteps, (byte) (strideLength >> 8), (byte)
                strideLength, (byte) sleepHour, (byte) sleepMin};
        return data;
    }

    /**
     * 手机请求设备发送公/ /英制,12H/24H ,经常活动地时区 ,本地日期
     *
     * @return
     */
    public static byte[] send_get_generalInfo() {
        byte[] data = new byte[]{(byte) 0xbe, (byte) 0x01, (byte) 0x01, (byte) 0xED};
        return data;
    }

    /**
     * 查询身高体重等等信息
     *
     * @return
     */
    public static byte[] send_get_WeightBrithStrideLength() {
        byte[] data = new byte[]{(byte) 0xbe, (byte) 0x01, (byte) 0x03, (byte) 0xED};
        return data;
    }

    /**
     * black-and-white display
     * 设置黑白屏和是否待机显示
     *
     * @param blackOrWhite 0 白屏 1 黑屏  display 是否待机展示  0 显示  1 不显示
     * @return
     */
    public static byte[] send_get_BlackAndWhiteDisplay(int blackOrWhite, boolean display) {
        byte[] data = new byte[]{(byte) 0xbe, (byte) 0x01, (byte) 0x04, (byte) 0xFE, (byte) blackOrWhite, (byte) (display ? 0x00 : 0x01)};
        return data;
    }

    /**
     * 是否需要数据隐私保护
     *
     * @param open
     * @return
     */
    public static byte[] send_privacy(boolean open, String mac) {
        byte[] bs = new byte[13];
        bs[0] = (byte) 0xbe;
        bs[1] = 0x01;
        bs[2] = 0x05;
        bs[3] = (byte) 0xfe;
        bs[4] = (byte) 0xff;
        bs[5] = 0x00;
        bs[6] = 0x00;
//        BE 01 05 FE FF 00 00 98 E7 F5 A1 D7 4A
//        String maccccc = getBtAddressViaReflection();
//        if (maccccc == null) {
//            maccccc = mac;
//        }
        String[] macs = mac.split(":");
        for (int i = 0; i < macs.length; i++) {
            bs[7 + i] = (byte) Integer.parseInt(macs[i], 16);
        }
        return bs;
    }

//    public static String getBtAddressViaReflection() {
//        BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
//        Object bluetoothManagerService = new Mirror().on(bluetoothAdapter).get().field("mService");
//        if (bluetoothManagerService == null) {
//            return null;
//        }
//        Object address = new Mirror().on(bluetoothManagerService).invoke().method("getAddress").withoutArgs();
//        if (address != null && address instanceof String) {
//            return (String) address;
//        } else {
//            return null;
//        }
//    }

    /**
     * 获取设备复位信息
     *
     * @return
     */
    public static byte[] send_get_resetInfo() {
        byte[] data = new byte[]{(byte) 0xA5, (byte) 0x01, (byte) 0x00, (byte) 0xED};
        return data;
    }

    public static byte[] send_setAutoSleep(AutoSleep autoSleep) {
        byte[] data = null;
        int switchOpen = (autoSleep.isAutoSleep() ? 1 : 0);
        if (switchOpen == 0) {
            data = new byte[]{(byte) 0xbe, (byte) 0x01, (byte) 0x07, (byte) 0xfe, (byte) 0x00, (byte) 0xff,
                    (byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff
                    , (byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff};
        } else {
            boolean isSleep = autoSleep.isSleep();
            boolean isSleepRemind = autoSleep.isSleepRemind();
            boolean isNap = autoSleep.isNap();
            boolean isNapRemind = autoSleep.isNapRemind();
//            if (baseDevice != null && (baseDevice.getDeviceType() != BaseDevice.TYPE_W311N && baseDevice
//                    .getDeviceType() != BaseDevice.TYPE_AT200 &&
//                    baseDevice.getDeviceType() != BaseDevice.TYPE_AS97)) {
//                isNapRemind = false;
//                isNap = false;
//            }
            int sleepStartHour = autoSleep.getSleepStartHour();
            int sleepStartMinute = autoSleep.getSleepStartMin();
            Calendar ccc = Calendar.getInstance();
            ccc.set(Calendar.HOUR_OF_DAY, sleepStartHour);
            ccc.set(Calendar.MINUTE, sleepStartMinute);
            ccc.add(Calendar.MINUTE, -1 * autoSleep.getSleepRemindTime());
            int sleepRemindHour = ccc.get(Calendar.HOUR_OF_DAY);
            int sleepReminMin = ccc.get(Calendar.MINUTE);

            int napStartHour = autoSleep.getNapStartHour();
            int napStartMin = autoSleep.getNapStartMin();
            ccc = Calendar.getInstance();
            ccc.set(Calendar.HOUR_OF_DAY, napStartHour);
            ccc.set(Calendar.MINUTE, napStartMin);
            ccc.add(Calendar.MINUTE, -1 * autoSleep.getNapRemindTime());
            int napRemindHour = ccc.get(Calendar.HOUR_OF_DAY);
            int napRemindMin = ccc.get(Calendar.MINUTE);
                /*17Byte BE＋01＋07+FE+开关控制（1byte）
                +计划睡眠小时(1byte)+计划睡眠分(1byte)
                +睡眠提醒小时(1byte) +睡眠提醒分(1byte)
                +计划起床小时(1byte) +计划起床分(1byte)
                +计划午休小时(1byte) +计划午休分(1byte)
                +结束午休小时(1byte) +结束午休分(1byte)
                +午休提醒小时(1byte) +午休提醒分(1byte)*/
            data = new byte[]{(byte) 0xbe, (byte) 0x01, (byte) 0x07, (byte) 0xfe, (byte) 0x01, (byte) (isSleep ?
                    autoSleep.getSleepStartHour() : 0xfe),
                    (byte) (isSleep ? autoSleep.getSleepStartMin() : 0xfe), (byte) (isSleepRemind ?
                    sleepRemindHour : 0xfe),
                    (byte) (isSleepRemind ? sleepReminMin : 0xfe),
                    (byte) (isSleep ? autoSleep.getSleepEndHour() : 0xfe), (byte) (isSleep ? autoSleep
                    .getSleepEndMin() : 0xfe),
                    (byte) (isNap ? autoSleep.getNapStartHour() : 0xfe),
                    (byte) (isNap ? autoSleep.getNapStartMin() : 0xfe), (byte) (isNap ? autoSleep.getNapEndHour()
                    : 0xfe),
                    (byte) (isNap ? autoSleep.getNapEndMin() : 0xfe), (byte) (isNapRemind ? napRemindHour : 0xfe),
                    (byte) (isNapRemind ? (napRemindMin) : 0xfe)};
        }
        return data;
    }

    /**
     * set which interface show on device 设置显示模式
     *
     * @param displaySet
     */
    public static byte[] send_setDisplayInterface(DisplaySet displaySet) {
        if (displaySet != null) {
            byte[] data = new byte[20];
            data[0] = (byte) 0xbe;
            data[1] = (byte) 0x01;
            data[2] = (byte) 0x08;
            data[3] = (byte) 0xfe;
            int index = 4;
           /* if (displaySet.isShowLogo()) {
                data[index] = 0x00;
                index++;
            }*/
            if (displaySet.isShowCala()) {
                data[index] = 0x03;
                index++;
            }
            if (displaySet.isShowDist()) {
                data[index] = 0x04;
                index++;
            }
            if (displaySet.isShowSportTime()) {
                data[index] = 0x05;
                index++;
            }
            if (displaySet.isShowProgress()) {
                data[index] = 0x06;
                index++;
            }
            if (displaySet.isShowEmotion()) {
                data[index] = 0x07;
                index++;
            }
            if (displaySet.isShowAlarm()) {
                data[index] = 0x08;
                index++;
            }
            if (displaySet.isShowSmsMissedCall()) {
                data[index] = 0x0A;
                index++;
            }
            if (displaySet.isShowIncomingReminder()) {
                data[index] = 0x0B;
                index++;
            }
            if (displaySet.isShowMsgContent()) {
                data[index] = 0x0D;
                index++;
            }
            if (displaySet.isShowCountDown()) {
                data[index] = 0x0f;
                index++;
            }
            for (int i = index; i < 20; i++) {
                data[i] = (byte) 0xff;
            }
            return data;
        }
        return null;
    }

    /**
     * if one of the alarms  is on,the alarm is on
     *
     * @param list the size no more than 5 w311 闹钟设置提醒
     */
    public static byte[] setAlarm(List<AlarmEntry> list) {

        if (list != null && list.size() > 0) {
            byte[] data = new byte[20];
            data[0] = (byte) 0xbe;
            data[1] = 0x01;
            data[2] = 0x09;
            data[3] = (byte) 0xfe;

            int states = 0;
            int index = 5;
            for (int i = 0; i < (list.size() > 5 ? 5 : list.size()); i++) {

                AlarmEntry entry = list.get(i);
                data[index] = (byte) entry.getStartHour();
                index++;
                data[index] = (byte) entry.getStartMin();
                index++;
                //byte repeat = ((byte) (entry.getRepeat() > 0 ? (entry.getRepeat() | 0x80) : entry.getRepeat()));
                byte repeat = ((byte) ((entry.getRepeat() & 0xff) > 0 ? ((entry.getRepeat() & 0xff) & 0x7F) : entry
                        .getRepeat()));
                data[index] = repeat;
                index++;
                if (entry.isOn()) {
                    states = states + (1 << i);
                }
            }
            data[4] = (byte) states;
            if (list.size() < 5) {
                for (int i = list.size(); i < 5; i++) {
                    data[index] = (byte) 0xff;
                    index++;
                    data[index] = (byte) 0xff;
                    index++;
                    data[index] = 0x00;
                    index++;
                }
            }
            return data;
        }
        return null;
    }


    /**
     * 勿扰模式指令
     * 格式：BE-01-21-FE-
     * <p>
     * 开关(00:关 01:开)-开始（小时-分钟）-结
     * 束（小时-分钟）
     * 回复：DE-01-21-ED
     */
    public static byte[] setDisturb(boolean open, int startHour, int startMin, int endHour, int endMin) {
        byte[] cmds = new byte[9];
        if (open) {
            cmds = new byte[]{(byte) 0xbe, 0x01, 0x21, (byte) 0xFE, (byte) 0x01, (byte) startHour, (byte) startMin, (byte) endHour, (byte) endMin};
        } else {
            cmds = new byte[]{(byte) 0xbe, 0x01, 0x21, (byte) 0xFE, (byte) 0x00, (byte) 0, (byte) 0, (byte) 0, (byte) 0};
        }
        return cmds;
    }

    /**
     * set wrist mode,left hand or right hand
     * 左右手设置
     * 0 左手  1 右手
     *
     * @param wristMode
     */
    public static byte[] setWristMode(WristMode wristMode) {
        if (wristMode != null) {
            byte[] data = new byte[]{(byte) 0xbe, 0x01, 0x0b, (byte) 0xfe,
                    (byte) (wristMode.isLeftHand()
                            ? 0 : 1)};
            return data;
        }
        return null;
    }

    /**
     * one of list is on,the sedentary remnid is on
     * 久坐提醒
     *
     * @param list max size is 3
     */
    public static byte[] setSedintaryRemind(List<SedentaryRemind> list) {
        if (list != null && list.size() > 0) {
            byte[] data = new byte[19];
            data[0] = (byte) 0xbe;
            data[1] = 0x01;
            data[2] = 0x0c;
            data[3] = (byte) 0xfe;
            boolean isOn = false;
            List<byte[]> listD = new ArrayList<>();
            int index = -1;
            for (int i = 0; i < (list.size() > 3 ? 3 : list.size()); i++) {
                if (list.get(i).isOn()) {
                    SedentaryRemind remind = list.get(i);
                    isOn = true;
                    index = i;
                    byte[] tp = new byte[4];
                    tp[0] = (byte) remind.getBeginHour();
                    tp[1] = (byte) remind.getBeginMin();
                    tp[2] = (byte) remind.getEndHour();
                    tp[3] = (byte) remind.getEndMin();
                    listD.add(tp);
                } else {
                    byte[] tp = new byte[]{0, 0, 0, 0};
                    listD.add(tp);
                }
            }
            data[4] = (byte) (isOn ? 1 : 0);
            if (!isOn) {
                for (int i = 5; i < 19; i++) {
                    data[i] = 0;
                }
            } else {
                if (index != -1) {
                    System.arraycopy(listD.get(index), 0, data, 5, 4);
                    if (listD.size() > 1) {
                        System.arraycopy(list.get(listD.size() - 1 - index), 0, data, 5 + 4, 4);
                        for (int i = 0; i < listD.size(); i++) {
                            if (i != index && (i != listD.size() - 1 - index)) {
                                System.arraycopy(list.get(i), 0, data, 13, 4);
                            }
                        }
                    }
                }
                data[17] = (byte) (SedentaryRemind.noExerceseTime / 60);
                data[18] = (byte) (SedentaryRemind.noExerceseTime % 60);
            }
            return data;
        }
        return null;
    }

    /**
     * set heart timing test
     * 自动心率测试时间  BE0115FE  BE 01 19 FE 01 05
     *
     * @param heartTimingTest
     */

    public static byte[] setHeartTimingTest(boolean heartTimingTest) {
        byte[] data = new byte[17];
        data[0] = (byte) 0xbe;
        data[1] = 0x01;
        data[2] = 0x15;
        data[3] = (byte) 0xfe;

        if (heartTimingTest) {
            // data[4]=0;
            data[4] = (byte) 0x81;
        } else {
            //data[4]=1;
            data[4] = (byte) 0x80;
        }
        for (int i = 5; i < 17; i++) {
            data[i] = (byte) 0xff;
        }
       /* if (heartTimingTest) {
            data[4] = 0;
            for (int i = 5; i < 17; i++) {
                data[i] = (byte) 0xff;
            }
        } else {
            data[4] = 1;
            data[5] = heartTimingTest.isFirstEnable() ? (byte) heartTimingTest.getFirStartHour() : (byte) 0xff;
            data[6] = heartTimingTest.isFirstEnable() ? (byte) heartTimingTest.getFirstStartMin() : (byte) 0xff;
            data[7] = heartTimingTest.isFirstEnable() ? (byte) heartTimingTest.getFirstEndHour() : (byte) 0xff;
            data[8] = heartTimingTest.isFirstEnable() ? (byte) heartTimingTest.getFirstEndMin() : (byte) 0xff;
            data[9] = heartTimingTest.isSecondEnable() ? (byte) heartTimingTest.getSecStartHour() : (byte) 0xff;
            data[10] = heartTimingTest.isSecondEnable() ? (byte) heartTimingTest.getSecStartMin() : (byte) 0xff;
            data[11] = heartTimingTest.isSecondEnable() ? (byte) heartTimingTest.getSecEndHour() : (byte) 0xff;
            data[12] = heartTimingTest.isSecondEnable() ? (byte) heartTimingTest.getSecEndMin() : (byte) 0xff;
            data[13] = heartTimingTest.isThirdEnable() ? (byte) heartTimingTest.getThirdStartHour() : (byte) 0xff;
            data[14] = heartTimingTest.isThirdEnable() ? (byte) heartTimingTest.getThirdStartMin() : (byte) 0xff;
            data[15] = heartTimingTest.isThirdEnable() ? (byte) heartTimingTest.getThirdEndHour() : (byte) 0xff;
            data[16] = heartTimingTest.isThirdEnable() ? (byte) heartTimingTest.getThirdEndMin() : (byte) 0xff;
        }*/
        return data;
    }

    /**
     * {@link #setAlarm(List)}
     * set alarm description
     * 设置闹钟提醒内容
     *
     * @param description no more than 15 byte
     * @param index       the value is between 0 and 4,the order is same as {@link #setAlarm(List)}
     * @param showDescrip show description content on device
     */
    public static byte[] setAlarmDescription(String description, int index, boolean showDescrip) {
        description = (description == null ? "" : description);
        byte[] data = new byte[20];
        data[0] = (byte) 0xbe;
        data[1] = 0x01;
        data[2] = 0x16;
        data[3] = (byte) 0xfe;
        if (description.trim().equals(""))
            showDescrip = false;
        data[4] = (byte) (showDescrip ? 1 : 0);
        data[5] = (byte) index;
        byte[] tps = null;
        try {
            tps = description.getBytes("UTF-8");
        } catch (UnsupportedEncodingException e) {
            tps = description.getBytes();
            e.printStackTrace();
        }
        int tpsL = tps.length;
        System.arraycopy(tps, 0, data, 6, tpsL > 14 ? 14 : tpsL);
        if (tps.length < 14) {
            for (int i = 19; i > tpsL + 5; i--) {
                data[i] = (byte) 0xff;
            }
        }
        return data;
    }

    /**
     * 获取隐私配对码
     *
     * @return
     */
    public static byte[] send_get_privacy_mac() {
        byte[] data = new byte[]{(byte) 0xBE, (byte) 0x01, (byte) 0x05, (byte) 0xEE};
        return data;
    }

    /**
     * 查询设备的睡眠时间，午休时间
     *
     * @return
     */
    public static byte[] send_get_sleepdata() {
        byte[] data = new byte[]{(byte) 0xBE, (byte) 0x01, (byte) 0x07, (byte) 0xED};
        return data;
    }


    /**
     * 查询定时心率测试时间
     *
     * @return
     */
    public static byte[] send_get_heartRateTestTime() {
        byte[] data = new byte[]{(byte) 0xBE, (byte) 0x01, (byte) 0x15, (byte) 0xED};
        return data;
    }

    /**
     * 开启5分钟心率自动关闭
     *
     * @return
     */
    public static byte[] send_set_5MinutesAutomaticAttentionRate(boolean open) {
        byte[] data = new byte[]{(byte) 0xBE, (byte) 0x01, (byte) 0x20, (byte) 0xFE, (byte) (open ? 1 : 0)};
        return data;
    }

    /**
     * 自动心率开启指令和监测间隔时间设置
     * 格式：BE-01-19-FE-开关-时间  开关(00：关 01：开） 监测间隔：15分钟，30分钟，45分钟，60 分钟
     * 回复：DE-01-19-ED
     * 备注：若自动心率关闭，则无后面的监测间隔，若自动心率 打开，则会还有监测监测的设置项，分别是15分钟，30分钟，45分钟，60分钟，
     * 供用户选择。
     * time 15min  30min  45min  60min
     */
    public static byte[] setAutomaticHeartRateAndTime(boolean enable, int time) {
        byte[] data = new byte[]{(byte) 0xbe, 0x01, 0x19, (byte) 0xFE, (byte) (enable ? 1 : 0), (byte) time};
        return data;
    }

    /**
     * 1.抬手亮屏开关指令
     * 格式：BE-01-18-FE-开关（00：关 01：开）
     * 回复：DE-01-18-ED  W311以及BEAT 固件版本号 91.12     、307 REFLEX 固件版本号90.88 以上
     */
    public static byte[] raiseHand(boolean enable) {
        byte[] data;
        if (enable) {
            data = new byte[]{(byte) 0xbe, 0x01, 0x18, (byte) 0xFE, (byte) 0x80, (byte) 0, (byte) 0, (byte) 0, (byte) 0};
        } else {
            data = new byte[]{(byte) 0xbe, 0x01, 0x18, (byte) 0xFE, (byte) 0x00, (byte) 0, (byte) 0, (byte) 0, (byte) 0};
        }
        return data;

    }

    public static byte[] raiseHand(int type) {
        byte[] data = null;
        /**
         * 关闭是2
         *          全天开启0
         *          睡觉时间段关
         */
        switch (type) {
            case 0:
                data = new byte[]{(byte) 0xbe, 0x01, 0x18, (byte) 0xFE, (byte) 0x01, (byte) 0x01};
                break;
            case 1:
                data = new byte[]{(byte) 0xbe, 0x01, 0x18, (byte) 0xFE, (byte) 0x01, (byte) 0x00};
                break;
            case 2:
                data = new byte[]{(byte) 0xbe, 0x01, 0x18, (byte) 0xFE, (byte) 0x00, (byte) 0x00};
                break;

        }
        return data;
    }


    /**
     * 1.抬手亮屏开关指令，加入时间段
     * 格式：BE-01-18-FE-开关(bit7:总开关，bit0：时间段使能开关，
     * 00 关闭）-时间段（开始
     * （小时-分钟）-结束（小时-分钟））
     * 80 时间段开启，81 全天开启，00 全天关闭
     * 回复：DE-01-18-ED  W311以及BEAT 固件版本号 91.61
     * type 0  时间段   1  全天开启  2  全天关闭
     * startHour endHour  0-23
     * endHour   endMin   0-59
     */
    public static byte[] raiseHand(int type, int startHour, int startMin, int endHour, int endMin) {
        byte[] cmds = new byte[9];
        if (type == 0) {
            cmds = new byte[]{(byte) 0xbe, 0x01, 0x18, (byte) 0xFE, (byte) 0x81, (byte) 0, (byte) 0, (byte) 0, (byte) 0};
        } else if (type == 1) {
            cmds = new byte[]{(byte) 0xbe, 0x01, 0x18, (byte) 0xFE, (byte) 0x81, (byte) startHour, (byte) startMin, (byte) endHour, (byte) endMin};
        } else if (type == 2) {
            cmds = new byte[]{(byte) 0xbe, 0x01, 0x18, (byte) 0xFE, (byte) 0x00, (byte) 0, (byte) 0, (byte) 0, (byte) 0};
        }
        return cmds;
    }


    /**
     * 2心率整点监测开关指令
     * 格式：BE-01-19-FE-开关（00：关 01：开）
     * 回复：DE-01-19-ED
     */
    public static byte[] heartRate(boolean enable) {
        byte[] data = new byte[]{(byte) 0xbe, 0x01, 0x19, (byte) 0xFE, (byte) (enable ? 1 : 0)};
        return data;
    }

    /**
     * 心率开关
     */


    /**
     * send notification or sms to ble device
     * 多包发送
     *
     * @param notiContent  信息内容截取15byte，不足15则填充
     * @param packageIndex 第几包
     * @param notitype     消息类型
     *                     Message=12 QQ 个人版=13 Wechat=14 at=14
     *                     Skype=15 Facebook=16 Twitter=17
     *                     Linkedin=18 Instagram=19 Life inovatyon=1A
     */
    public static byte[] sendNotiCmd(byte[] notiContent, int packageIndex, int notitype) {
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
        return btCmd;
    }

    /**
     * 找手机
     */
    public static byte[] findPhone() {
        byte[] data = new byte[]{(byte) 0xDE, 0x06, 0x10, (byte) 0xED};
        return data;
    }

    /**
     * 找设备
     */
    public static byte[] findDevice() {
        byte[] data = new byte[]{(byte) 0xBE, 0x06, 0x0F, (byte) 0xED};
        return data;
    }

    /**
     * 关闭防丢功能
     */
    public static byte[] closeAntiLost() {
        byte[] data = new byte[]{(byte) 0xBE, 0x06, 0x0E, (byte) 0xED};
        return data;
    }


    /**
     * 开启防丢功能
     */
    public static byte[] openAntiLost() {
        byte[] data = new byte[]{(byte) 0xBE, 0x06, 0x0D, (byte) 0xED};
        return data;
    }

    /**
     * 获取设备的版本信息   writeTXCharacteristicItem(new byte[]{(byte) 0xBE, 0x06, (byte) 0x09, (byte) 0xFB});
     */
    public static byte[] send_getDeviceInfo() {
        byte[] data = new byte[]{(byte) 0xBE, 0x06, 0x09, (byte) 0xFB};
        return data;
    }

    /**
     * 启动手机音乐播放功能
     * 0 停止播放  1 开始播放  2 播放前一首  3 播放后一首 F0暂停播放
     */
    public static byte[] openMusic(byte set) {
        byte[] data = new byte[]{(byte) 0xDE, 0x06, 0x08, (byte) 0xFE, set, (byte) 0xED};
        return data;
    }

    /**
     * 启动拍照功能
     * 1 开始拍照
     */
    public static byte[] getPhoto() {
        byte[] data = new byte[]{(byte) 0xDE, 0x06, 0x07, (byte) 0xFE, 0x01, (byte) 0xED};
        return data;
    }

    /**
     * send phone number or contact name
     *
     * @param phoneOrName
     * @param type        0 phone 1 name
     *                    返回 DE 06 01/02 ED   01 名字  02  手机号
     */
    public static byte[] sendPhoneNum(String phoneOrName, int type) {
        if (type == 0 && phoneOrName == null)
            return null;
        byte[] time = new byte[20];
        int c = 0;
        time[c++] = (byte) 0xbe;
        time[c++] = (byte) 0x06;
        time[c++] = (byte) (type == 0 ? 0x02 : 0x01);
        time[c++] = (byte) 0xfe;
        phoneOrName = (phoneOrName == null ? "" : phoneOrName);
        try {
            byte[] bs = phoneOrName.getBytes("UTF-8");
            int len = (bs == null ? 0 : bs.length);
            len = (len > 15 ? 15 : len);
            time[c++] = (byte) len;

            for (int i = 0; i < len; i++) {
                time[c++] = bs[i];
            }
            if (c < 20) {
                for (int t = c; t < 20; t++) {
                    time[c++] = (byte) 0xff;
                }
            }
            return time;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * Query the heart rate stored data for the specified date
     * BE-02-10-FE-YY(年2byte)-MM(月)-DD(日)-序号  (序号表示从第几个开始，APP默认发00）
     */
    public byte[] queryHeartRateHistoryByDate(int year, int month, int day) {
       /* if (state == STATE_CONNECTED) {
            isSyncHeartRateHistorying = true;
            syncHandler.sendEmptyMessageDelayed(0x03, HEARTRATE_SYNC_TIMEOUT);*/
        byte[] value = new byte[9];
        value[0] = (byte) 0xBE;
        value[1] = (byte) 0x02;
        value[2] = (byte) 0x10;
        value[3] = (byte) 0xFE;
        value[4] = (byte) (year >> 8);
        value[5] = (byte) year;
        value[6] = (byte) month;
        value[7] = (byte) day;
        value[8] = 0x00;
        return value;
        /* sendCommand(SEND_DATA_CHAR, mGattService, mBluetoothGatt, value);*/
    }

    /**
     * 发送联系人
     *
     * @param comming_phone
     */
    public byte[] sendPhoneName(String comming_phone) {
        comming_phone = (comming_phone == null ? "" : comming_phone);
        byte[] time = new byte[20];
        int c = 0;
        time[c++] = (byte) 0xbe;
        time[c++] = (byte) 0x06;
        time[c++] = (byte) 0x01;
        time[c++] = (byte) 0xfe;
        byte[] bs = comming_phone.getBytes();
        Byte phoneLength = (byte) bs.length;

        byte len = phoneLength > 15 ? 15 : phoneLength;
        time[c++] = len;
        //if (!IsChineseOrNot.isChinese(comming_phone)) {
        for (int i = 0; i < len; i++) {
            byte b = bs[i];
            time[c++] = b;
        }
        /*} else {
            time[c++] = 0x00;
		}*/

        if (c < 20) {
            for (int t = c; t < 20; t++) {
                time[c++] = (byte) 0xff;
            }
        }

        return time;
    }


    /**
     * 手机查询设备的工作界面及免打扰功能
     * 数据： 4Bytes BE+01+08+ED
     * 1.20- -1 1 ，设备返回工作界面及免打扰功能
     * 数据： 20Bytes DE ＋ 01 ＋ 08+FE
     * + + 开启模式 1 1 （ 1byte ） + + 开启模式 2 2 （ 1byte ） + + 开启模式 3 3 （ 1byte ）
     * + + 开启模式 4 4 （ 1byte ） + + 开启模式 5 5 （ 1byte ） + + 开启模式 6 6 （ 1byte ）
     * + + 开启模式 7 7 （ 1byte ） + + 开启模式 8 8 （ 1byte ） + + 开启模式 9 9 （ 1byte ）
     * + + 开启模式 10 （ 1byte ） + + 开启模式 11 （ 1byte ） + + 开启模式 12
     * （ 1byte ）
     * + + 开启模式 13 （ 1byte ） + + 开启模式 14 （ 1byte ） + + 开启模式 15
     * （ 1byte ）
     * + + 开启模式 16 （ 1byte
     *
     * @return
     */
    public static byte[] send_get_display() {
        byte[] data = new byte[]{(byte) 0xBE, (byte) 0x01, (byte) 0x08, (byte) 0xED};
        return data;
    }

    /**
     * 手机查询闹铃时间及使能： 4Bytes BE+01+09+ED
     * 1.22- - 1, 设备返回闹铃时间及使能
     * 数据： 20Bytes DE+01+09+FE+ 闹钟开关（ 1Byte ）
     * + + 第 1  闹铃小时 (1Byte)+ 第 1  闹铃分钟 (1Byte)+ 闹铃 1  重复标志
     * (1Byt e)
     * + + 第 2  闹铃小时 (1Byte)+ 第 2  闹铃分钟 (1Byte)+ 闹铃 2  重复标志
     * (1Byte)
     * + + 第 3  闹铃小时 (1Byte)+ 第 3  闹铃分钟 (1Byte)+ 闹铃 3  重复标志
     * (1Byte)
     * + + 第 4  闹铃小时 (1Byte)+ 第 4  闹铃分钟 (1Byte)+ 闹铃 4  重复标志
     * (1Byte)
     * + + 第 5  闹铃小时 (1Byte)+ 第 5  闹铃分钟 (1Byte)+ 闹铃 5  重复标志
     * (1Byte)
     */
    public static byte[] send_get_AlarmList() {
        byte[] data = new byte[]{(byte) 0xBE, (byte) 0x01, (byte) 0x09, (byte) 0xED};
        return data;
    }

    /**
     * 查询久坐提醒
     * <p>
     * /**
     * *  1, 设备返回久坐提醒
     * * 数据： 19Bytes DE ＋ 01 ＋ 0C+FE ＋启用开关 (1byte)
     * * + + 开始时 1(1byte) ＋开始分 1(1byte) ＋结束时 1(1byte) ＋结束分
     * * 1(1byte)
     * * + + 开始时 2(1byte) ＋开始分 2(1byte) ＋结束时 2(1byte) ＋结束分
     * * 2(1byte)
     * * + + 开始时 3(1byte) ＋开始分 3(1byte) ＋结束时 3(1byte) ＋结束分
     * * 3(1byte)
     * * ＋静止时 (1byte) ＋静止分
     * * @return
     *
     * @return
     */
    public static byte[] send_get_SedintaryRemind() {
        byte[] data = new byte[]{(byte) 0xBE, (byte) 0x01, (byte) 0x0C, (byte) 0xED};
        return data;
    }

    /**
     * 手机查询设备定时心率测试时间
     * 数据： 4Bytes BE+ 01+15+ED
     * 1.52- -1 1 ，设备收到后返回
     * 数据 : 17Byte DE ＋ 01 ＋ 15+FE+ 开关控制（ 1byte ）
     * + + 早上开启心率小时 (1byte)+  早上开启心率分 (1byte)
     * + + 早上关闭心率小时 (1byte)+  早上关闭心率分 (1byte)
     * + + 中午开启心率小时 (1byte)+  中午开启心率分 (1byte)
     * + + 中午关闭心率小时 (1byte)+  中午关闭心率分 (1byte)
     * + + 晚上开启心率小时 (1byte)+  晚上开启心率分 (1byte)
     * + + 晚上关闭心率小时 (1byte)+  晚上关闭心率分 (1byte)
     * 备注：
     * 1) 开关控制（ 1byte ） -------- 开启定时心率测试/ / 关闭定时心率测试
     * 1 1- - 1) 00 :  禁止开启定时心率测试
     * 01 :  允许开启定时心率测试
     * 1 1- - 2) 80 :  立即关闭心率测试
     * 81 :  立即开启心率测试
     * 当发送 80/81  时后面的字节可以全部用 FF  代替
     * 2 2 ） 3  组时间 :  都是按照 24H  制。
     * 3 3 ）若每一组对应的定时时间是关闭的，相应的小时和分为 FF ＋
     *
     * @return
     */
    public static byte[] send_get_Hr() {
        byte[] data = new byte[]{(byte) 0xBE, (byte) 0x01, (byte) 0x15, (byte) 0xED};
        return data;
    }

    //获取抬手亮屏的指令
    public static byte[] get_raiseHand_cmd() {
        byte[] data = new byte[]{(byte) 0xBE, (byte) 0x01, (byte) 0x18, (byte) 0xED};
        return data;
    }

    //获取勿扰的指令 ：BE-01-21-ED
    public static byte[] get_disturb_cmd() {
        byte[] data = new byte[]{(byte) 0xBE, (byte) 0x01, (byte) 0x21, (byte) 0xED};
        return data;
    }

    //获取自动心率开启指令 ：BE+01+19+ED
    public static byte[] get_autoHeartRateAndTime_cmd() {
        byte[] data = new byte[]{(byte) 0xBE, (byte) 0x01, (byte) 0x19, (byte) 0xED};
        return data;
    }

    public int currentTemp(int temp) {
        if (temp < 0) {
            temp = Math.abs(temp) + 511;
        }
        return temp;
    }

    public byte[] sendWeatherCmd(boolean havsData, int todayWeather, int todaytempUnit, int todayhightTemp, int todaylowTemp, int todayaqi, int nextWeather, int nexttempUnit, int nexthightTemp, int nextlowTemp, int nextaqi, int afterWeather, int aftertempUnit, int afterhightTemp, int afterlowTemp, int afteryaqi) {
        todayhightTemp = currentTemp(todayhightTemp);
        todaylowTemp = currentTemp(todaylowTemp);
        nexthightTemp = currentTemp(nexthightTemp);
        nextlowTemp = currentTemp(nextlowTemp);
        afterhightTemp = currentTemp(afterhightTemp);
        afterlowTemp = currentTemp(afterlowTemp);


        long currentDay = (todayWeather << 26) | (todaytempUnit << 25) | (todayhightTemp << 15) | (todaylowTemp << 5) | (todayaqi);
        long nextDay = (nextWeather << 26) | (nexttempUnit << 25) | (nexthightTemp << 15) | (nextlowTemp << 5) | (nextaqi);
        long afterDay = (afterWeather << 26) | (aftertempUnit << 25) | (afterhightTemp << 15) | (afterlowTemp << 5) | (afteryaqi);

      /*  //(byte) ((height & 0xffff) >> 8)
        int one = (byte) currentDay >> 24 & 0xff;
        int two = (byte) currentDay >> 16 & 0xff;
        int three = (byte) currentDay >> 8 & 0xff;
        int four = (byte) currentDay & 0xff;*/

        byte[] data = new byte[]{(byte) 0xbe, 0x01, 0x26, (byte) 0xFE, (byte) (havsData ? 1 : 0), (byte) (currentDay >> 24 & 0xff), (byte) (currentDay >> 16 & 0xff), (byte) (currentDay >> 8 & 0xff), (byte) (currentDay & 0xff)
                , (byte) (nextDay >> 24 & 0xff), (byte) (nextDay >> 16 & 0xff), (byte) (nextDay >> 8 & 0xff), (byte) (nextDay & 0xff)
                , (byte) (afterDay >> 24 & 0xff), (byte) (afterDay >> 16 & 0xff), (byte) (afterDay >> 8 & 0xff), (byte) (afterDay & 0xff)};
        //return data;

        return data;

    }
}
