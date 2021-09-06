package com.isport.blelibrary.managers.order;

import com.isport.blelibrary.utils.CommonDateUtil;
import com.isport.blelibrary.utils.Logger;
import com.isport.blelibrary.utils.Utils;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Calendar;

public class W526Cmd {

    private static final String TAG = "W526Cmd";

    /************************************W526新指令********************************************************************/
    //W526新指令

    /*   */

    /**
     * 新加指令
     *//*
    public byte[] setWeather(int weatherType, int currentLowTemp, int currentHighTemp, int currentTemp) {

        //case CMD_SET_WEATHER:
        // // 发送天气预报数据
        // // 04-12-02-20-25-30
        // 04 后面有 4 个有效数据
        // 12 0x12 天气发送命令
        // 02 [0, 3] = [晴天 阴天 多云 雨天]
        // 20-25-30 当天最低温度、平均温度、最高温度
        // 温度单位摄氏度 1.0 度
        byte[] cmds = new byte[]{0x04, 0x12, (byte) weatherType, (byte) currentLowTemp, (byte) currentTemp, (byte) currentHighTemp};
        return cmds;
    }
*/

    //新的天气设置指令
    //04 有效数据个数 -12 -02-20
    //当天（6bytes），空气指数（2）+实时温度+最高温度+最低温度+天气
    public byte[] setWeather(int index, int qvalue, int currenttemp, int currentHighTemp, int currentLowTemp, int weather) {
        byte[] cmds = new byte[]{0x04, 0x12, (byte) index, (byte) qvalue, (byte) (qvalue >> 8), (byte) currenttemp, (byte) currentHighTemp, (byte) currentLowTemp, (byte) weather};
        return cmds;
    }

    /**
     * 获取背光亮度&背光时间
     *
     * @return
     */
    public byte[] getbackLightAndScreenleve() {
        byte[] cmds = new byte[]{0x00, 0x0B};
        return cmds;
    }

    //1.34 背光亮度&背光时间设置 CMD_SET_BACKLIGHT
    // 02 36 03 0A
    // 03 为背光亮度等级 设置范围 [01, 05]
    // 0A 为背光时间，单位秒 设置范围 [03, 0A]

    public byte[] setbacklight(int leve, int time) {
        byte[] cmds = new byte[]{0x02, 0x36, (byte) leve, (byte) time};
        return cmds;
    }
    //每包数据 18byte，
    //每包第一个字节 BYTE0 是包序号，00 开始，每包加 1，BIT7 位 1 是结束包
    //第一包内容定义如下
    //BYTE1: 信息类型：
    //0 - 来电
    //1 - 短信
    //其他可以根据客户和产品需要另行定义
    //BYTE2 to BYTE17 手机号，或者名字 字符串
    //第 2 包到第 n 包为信息内容
    //如果只有第一包数据，第一包包序号为 0x80

    /**
     * @param
     * @return android ancs模拟通知
     * CMD_SEND_NOTIFICATION  =0X10
     * 01 10 XX XX...
     * 01为序号包(01-09)
     * 10为命令
     * XX 总 18 个
     * 根据序号分类
     * 01 信息类型(2byte)+消息内容长度(2byte)+后面补0
     * 消息类型：1 来电，2...自定义
     * 02 电话号码(15byte)+后面补零
     * 04-09 短信内容(最大91byte)+后面补0x00
     * 03通知时间包(17byte)+后面补0x00
     * 时间包格式：20200108T105632
     * 注意:最后发送时间包
     */

    public ArrayList<byte[]> sendMessage(int messageType, String title, String content) {
        ArrayList<byte[]> list = new ArrayList<>();


      /*  if (true) {
            list.add(new byte[]{0x01, 0x10, 0x01, 0x00, (byte) 0x0B, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00});
            list.add(new byte[]{0x02, 0x10, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00});
            list.add(new byte[]{0x04, 0x10, 0x54, 0x65, 0x73, 0x74, 0x20, 0x6E, 0x6F, 0x74, 0x69, 0x66, 0x79, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00});
            list.add(new byte[]{0x03, 0x10, (byte) 0xE5, 0x07, 0x04, 0x14, 0x54, 0x16, 0x02, 0x19, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00});

            return list;
        }*/

        byte[] titleByte = null;
        byte[] srccontentByte = null;
        byte[] descontentByte = null;
        byte[] contentlen = null;
        byte[] timeByte = null;

        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        String strMonth = CommonDateUtil.formatTwoStr(calendar.get(Calendar.MONTH) + 1);
        String strDay = CommonDateUtil.formatTwoStr(calendar.get(Calendar.DAY_OF_MONTH));
        String strHour = CommonDateUtil.formatTwoStr(calendar.get(Calendar.HOUR_OF_DAY));
        String strMin = CommonDateUtil.formatTwoStr(calendar.get(Calendar.MINUTE));
        String strSecond = CommonDateUtil.formatTwoStr(calendar.get(Calendar.SECOND));
        //String time = "20200110T1036";
        String time = year + strMonth + strDay + "T" + strHour + strMin + strSecond;
        int conntentLen = 0;
        // content = "abcd";
        Logger.myLog(TAG,"title:" + title + ",content:" + content);
        if (title != null) {
            try {
                titleByte = title.getBytes("UTF-8");
                srccontentByte = content.getBytes("UTF-8");

                final StringBuilder stringBuilder = new StringBuilder();
                for (byte byteChar : srccontentByte) {
                    stringBuilder.append(String.format("%02X ", byteChar));
                }

                conntentLen = srccontentByte.length;
                Logger.myLog("18664328616:" + title + ",srccontentByte:" + stringBuilder.toString() + "conntentLen:" + conntentLen);
                if (conntentLen > 91) {
                    conntentLen = 91;
                }
                descontentByte = new byte[conntentLen + 1];
                for (int i = 0; i < conntentLen; i++) {
                    descontentByte[i] = srccontentByte[i];
                }
                descontentByte[conntentLen] = 0;
                stringBuilder.delete(0, stringBuilder.length());
                /*for (byte byteChar : descontentByte) {
                    stringBuilder.append(String.format("%02X ", byteChar));
                }*/
                //  Logger.myLog("18664328616:" + title + ",descontentByte:" + stringBuilder.toString());
                //  Logger.myLog("18664328616--------conntentLen" + conntentLen);
                String contentByteLen = String.valueOf(conntentLen);
                contentlen = contentByteLen.getBytes("UTF-8");
                timeByte = time.getBytes("UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        list.add(sendNotiCmd(contentlen, 1, messageType));
        list.add(sendNotiCmd(titleByte, 2));
        int num1 = (conntentLen + 1) / 18;
        int num2 = (conntentLen + 1) % 18;
        int sumNum = 0;
        if (num2 != 0) {
            sumNum = num1 + 1;
        } else {
            sumNum = num1;
        }
        /**
         * src：源数组

         srcPos：源数组要复制的起始位置

         dest：目标数组

         destPos：目标数组复制的起始位置

         length：复制的长度
         */

        try {
            for (int i = 0; i < sumNum; i++) {
                byte[] notiContent = new byte[18];
                if (i * 18 + 18 < descontentByte.length) {
                    System.arraycopy(descontentByte, i * 18, notiContent, 0, 18);
                } else {
                    System.arraycopy(descontentByte, i * 18, notiContent, 0, (descontentByte.length - i * 18));
                }
                list.add(sendNotiCmd(notiContent, i + 4));
            }

        } catch (Exception e) {
           e.printStackTrace();

        } finally {
            list.add(sendNotiCmd(timeByte, 3));
            return list;
        }

        //时间包格式：20200108T105632


    }

    public static byte[] sendNotiCmd(byte[] notiContent, int packageIndex, int notitype) {
        byte[] btCmd = new byte[20];
        for (int i = 0; i < 20; i++) {
            btCmd[i] = 0x00;
        }
        btCmd[0] = (byte) packageIndex;
        btCmd[1] = (byte) 0x10;
        btCmd[2] = (byte) notitype;
        btCmd[3] = (byte) 0x00;
        if (notiContent != null && notiContent.length <= 16) {
            System.arraycopy(notiContent, 0, btCmd, 4, notiContent.length);
        }
        return btCmd;
    }

    public static byte[] sendNotiCmd(byte[] notiContent, int packageIndex) {
        byte[] btCmd = new byte[20];
        try {
            btCmd[0] = (byte) packageIndex;
            btCmd[1] = (byte) 0x10;
            if (notiContent != null && notiContent.length <= 18) {
                System.arraycopy(notiContent, 0, btCmd, 2, notiContent.length);
            } else {
                System.arraycopy(notiContent, 0, btCmd, 2, 18);
            }
            int length = (notiContent == null ? 0 : notiContent.length);
            if (length < 18 && length >= 0) {
                for (int i = length; i < 18; i++) {
                    btCmd[2 + i] = (byte) 0x00;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            return btCmd;
        }

    }

    //提取锻炼数据 命令&数据格式
//    命令： 0x01-0x42-0x00[00-49]  (最大50组数据)
    public byte[] getExerciseData(int number) {
        byte[] cmds = new byte[]{0x01, 0x42, (byte) number};
        return cmds;
    }


    public byte[] getExerciseData(boolean isState, int starthour, int startMin, int endHour, int endMin) {
        byte[] cmds = new byte[]{0x01, 0x42, 0x00,};
        return cmds;
    }

    //找手环
    public byte[] findW526() {
        byte[] cmds = new byte[]{0x00, 0x19};
        return cmds;
    }


    //设置心率{0x01,0x1A,isOpen}
    public byte[] setW526Hr(boolean isOpen) {
        byte[] cmds = new byte[]{0x01, 0x1A, (byte) (isOpen ? 0x01 : 0x00)};
        return cmds;
    }

    //查心率{0x00,0x09}
    public byte[] getW526Hr() {
        byte[] cmds = new byte[]{0x00, 0x09};
        return cmds;
    }

    public byte[] syncTime() {
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
        return cmds;
    }

    //发送拍照指令给设备
    public byte[] sendPhoto() {
        byte[] cmds = new byte[]{0x01, 0x0d};
        return cmds;
    }

    //获取抬腕{0x00,0x08}
    public byte[] getW526Screen() {
        byte[] cmds = new byte[]{0x00, 0x08};
        return cmds;
    }
    //设置抬腕{
    //        0x05,
    //        0x37,
    //        isOpen,
    //        [startArr.firstObject intValue],
    //        [startArr.lastObject intValue],
    //        [endArray.firstObject intValue],
    //        [endArray.lastObject intValue]
    //    }

    public byte[] setW526Screen(boolean isOpen, int startTimeHour, int startTimeMin, int endTimeHour, int endTimeMin) {
        byte[] cmds = new byte[]{0x05, 0x37, (byte) (isOpen ? 0x01 : 0x00), (byte) startTimeHour, (byte) startTimeMin, (byte) endTimeHour, (byte) endTimeMin};
        return cmds;
    }

    //设置表盘
    public byte[] setWatchFace(int mode) {
        byte[] cmd = new byte[]{0x01, 0x1B, (byte) mode};
        return cmd;
    }

    //获取表盘
    public byte[] getWatchFace() {
        byte[] cmd = new byte[]{0x01, 0x0A};
        return cmd;
    }

    //设置勿扰模式
    public static byte[] sendnoDisturb(boolean enable, int startHour, int startMin, int endHour, int endMin) {
        byte[] cmds = new byte[]{0x05, 0x34, (byte) (enable ? 1 : 0), (byte) startHour, (byte) startMin, (byte) endHour, (byte) endMin};
        return cmds;
    }

    //获取勿扰模式
    public static byte[] getNoDisturb() {
        byte[] cmds = new byte[]{0x00, 0x06};
        return cmds;
    }

    //设置目标指令
    public static byte[] setTargtStep(int target) {
        byte[] cmds = new byte[]{0x03, 0x38, (byte) target, (byte) (target >> 8), (byte) (target >> 16)};
        return cmds;
    }

    //设置W560目标步数指令
    public static byte[] setW560TargetStep(int step) {
        byte[] cmds = new byte[]{0x04, 0x38, (byte) step, (byte) (step >> 8), (byte) (step >> 16), 0x00};
        return cmds;
    }

    //设置目标距离指令
    public static byte[] setW560TargetDistance(int distance) {
        byte[] cmds = new byte[]{0x04, 0x38, (byte) distance, (byte) (distance >> 8), (byte) (distance >> 16), 0x01};
        return cmds;
    }

    //设置目标卡路里指令
    public static byte[] setW560TargetCalorie(int calorie) {
        byte[] cmds = new byte[]{0x03, 0x38, (byte) calorie, (byte) (calorie >> 8), (byte) (calorie >> 16), 0x02};
        return cmds;
    }

    //读取目标指令
    public static byte[] getTargetStep() {
        byte[] cmds = new byte[]{0x00, 0x0C};
        return cmds;
    }

    //开始测量血压
    public byte[] oxyenMeasure(boolean isStart) {
        byte[] cmds;

        if (isStart) {
            cmds = new byte[]{0x01, 0x3D, (byte) 01};
        } else {
            cmds = new byte[]{0x01, 0x3D, (byte) 02};
        }

        return cmds;

    }

    //结束测量血氧
    public byte[] bloodMeasure(boolean isStart) {
        byte[] cmds;
        if (isStart) {
            cmds = new byte[]{0x01, 0x3B, (byte) 01};
        } else {
            cmds = new byte[]{0x01, 0x3B, (byte) 02};
        }
        return cmds;
    }


    //测量单次心率
    public byte[] onceHrMeasure(boolean isStart) {
        byte[] cmds;
        if (isStart) {
            cmds = new byte[]{0x01, 0x50, (byte) 01};//开始测量
        } else {
            cmds = new byte[]{0x01, 0x50, (byte) 02};//结束测量
        }
        return cmds;
    }

    /************************************W526新指令********************************************************************/
}
