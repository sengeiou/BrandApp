package com.isport.blelibrary.managers.order;

import android.os.Build;
import android.text.TextUtils;
import android.util.Log;

import com.crrepa.ble.conn.type.CRPDeviceLanguageType;
import com.isport.blelibrary.managers.BaseManager;
import com.isport.blelibrary.utils.AppLanguageUtil;
import com.isport.blelibrary.utils.Constants;
import com.isport.blelibrary.utils.Logger;
import com.isport.blelibrary.utils.Utils;

import java.nio.charset.StandardCharsets;
import java.util.Calendar;

import androidx.annotation.RequiresApi;

/**
 * @Author
 * @Date 2018/11/1
 * @Fuction
 */

public class ISportOrder {
    private static long mExitTime = 0;

    //********************************************scale**************************************************//

    /**
     * 设置日期:设置年、月、日
     */
    protected static byte[] send_unit() {
        byte[] resultData = new byte[2];
        resultData[0] = 0x01;
        resultData[1] = 0x00;
        Logger.myLog("bytes2HexString == " + Utils.bytes2HexString(resultData));
        return resultData;
    }


    //双指针手表

    //***************************************************双指针手表***********************************************//

    /**
     * 获取设备状态，包括版本号，存储状态
     * 命令: 00-01
     * 应答: 若设备有 2 小时锻炼数据，7 天 24 小时数据，心率未开启，硬件状态正常，软件版本为，00.00.02
     * 设备应答为0F-FF-01-20-1C-07-00-00-32-30-30-2E-30-30-2E-30-32
     */
    protected static byte[] send_read_status() {
        byte[] cmds = new byte[]{0x00, 0x01};
        return cmds;
    }

    /**
     * 通用设置
     * 命令：若设置内容为全部设置，公制，英文，24 小时，抬手亮屏，关闭 24 小时心率，无界面设置
     * 09-32-00-14-00-00-00-00-00-00-00
     * 应答：02-FF-32-00
     * 通用设置
     * Note: 关于界面，时间界面永远在第一个页面，且不可关闭，顺序从 0 开始，排在时间界面后面，界面会根据具体产品要求调整，界面最多 7 个， 若不足 7 个，多余的设置项为 0
     * 实例:
     * 命令:若设置内容为全部设置，公制，英文，24 小时，抬手亮屏，关闭 24 小时心率，无界面设置 09-32-00-14-00-00-00-00-00-00-00
     * 应答:02-FF-32-00
     *
     * @param options     0 - 一次性设置所有项目 1 - 设置公制/英制 2 - 设置英文/中文 3 - 设置 12/24 小时显示模式 4 -设置抬手亮屏 5 -设置24小时心率 6 -设置界面
     * @param openSwitch  BIT0: (0: 公制 1:英制) BIT1 : (0:英文 1:中文) BIT2 : (0:12小时 1:24小时) BIT3: (0:开启抬手亮屏 1: 关闭抬手亮屏)
     *                    BIT4: (0:开启24小时心率 1:关闭24小时心率)
     * @param isOpenView1 界面 1 设置:BIT0 – BIT2 顺序 BIT7 0:关闭 1:开启
     * @param isOpenView2 界面 2 设置:BIT0 – BIT2 顺序 BIT7 0:关闭 1:开启
     * @param isOpenView3 界面 3 设置:BIT0 – BIT2 顺序 BIT7 0:关闭 1:开启
     * @param isOpenView4 界面 4 设置:BIT0 – BIT2 顺序 BIT7 0:关闭 1:开启
     * @param isOpenView5 界面 5 设置:BIT0 – BIT2 顺序 BIT7 0:关闭 1:开启
     * @param isOpenView6 界面 6 设置:BIT0 – BIT2 顺序 BIT7 0:关闭 1:开启
     * @param isOpenView7 界面 7 设置:BIT0 – BIT2 顺序 BIT7 0:关闭 1:开启
     *                    <p>
     *                    默认 公制  英文  24小时
     */
    protected static byte[] send_set_general(boolean open24HeartRate, boolean isCall, boolean isMessage) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("000");//其中包括ANCS Android这边默认发0关闭    1为开始
        if (open24HeartRate) {
            stringBuilder.append("0");
        } else {
            stringBuilder.append("1");
        }
        stringBuilder.append("0");
        //是否为24小时制  24 为1 12小时为0
        stringBuilder.append("1");
        stringBuilder.append("0");
        //为中文还是英文   英文为0，中文为1

        stringBuilder.append("0");

        //去获取
        StringBuilder stringBuilder4 = new StringBuilder();
        stringBuilder4.append("000000");
        if (isMessage) {
            stringBuilder4.append("1");
        } else {
            stringBuilder4.append("0");
        }
        if (isCall) {
            stringBuilder4.append("1");
        } else {
            stringBuilder4.append("0");
        }


        int value = Integer.valueOf(stringBuilder.toString(), 2);
        int value4 = Integer.valueOf(stringBuilder4.toString(), 2);
        Logger.myLog(stringBuilder4.toString() + "value4:" + value4);
        byte[] data = new byte[]{0x0A, 0x32, 0x00, (byte) value, (byte) value4, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00};
        return data;
    }

    protected static byte[] send_set_general(boolean open24HeartRate, boolean isCall, boolean isMessage, boolean is24Hour) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("00");//其中包括ANCS Android这边默认发0关闭    1为开始

        Logger.myLog("获取通用设置 BaseManager.isTmepUnitl" + BaseManager.isTmepUnitl);

        if (TextUtils.isEmpty(BaseManager.isTmepUnitl)) {
            stringBuilder.append("0");
        } else {
            stringBuilder.append(BaseManager.isTmepUnitl);
        }
        if (open24HeartRate) {
            stringBuilder.append("0");
        } else {
            stringBuilder.append("1");
        }
        stringBuilder.append("0");
        //是否为24小时制  24 为1 12小时为0
        if (is24Hour) {
            stringBuilder.append("1");
        } else {
            stringBuilder.append("0");
        }

        //为中文还是英文   英文为0，中文为0
        if (AppLanguageUtil.sendTypeLanguage() == CRPDeviceLanguageType.LANGUAGE_CHINESE) {
            stringBuilder.append("1");
        } else {
            stringBuilder.append("0");
        }

        stringBuilder.append("0");

        //去获取
        StringBuilder stringBuilder4 = new StringBuilder();
        stringBuilder4.append("000000");
        if (isMessage) {
            stringBuilder4.append("1");
        } else {
            stringBuilder4.append("0");
        }
        if (isCall) {
            stringBuilder4.append("1");
        } else {
            stringBuilder4.append("0");
        }


        int value = Integer.valueOf(stringBuilder.toString(), 2);
        int value4 = Integer.valueOf(stringBuilder4.toString(), 2);
        Logger.myLog(stringBuilder4.toString() + "value4:" + value4);
        byte[] data = new byte[]{0x0A, 0x32, 0x00, (byte) value, (byte) value4, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00};
        return data;
    }


    /**
     * 获取通用设置
     * 实例:
     * 命令:00-04
     * 应答:若设置内容为全部设置，公制，英文，24 小时，抬手亮屏，关闭 24 小时心率，无界面设置
     * 09-FF-04-14-00-00-00-00-00-00-00
     */

    protected static byte[] send_get_general() {
        byte[] data = new byte[]{0x00, 0x04};
        return data;
    }

    /**
     * 获取用户信息
     * 命令：若设置内容为: brith day: 1981 年 12 月 12 日，female，体重 100kg，身高 170， 最大心率 190， 最低
     * 心率 60
     * 0A-33-BD-07-0C-0C-00-E8-03-AA-BE-3C
     * 应答：02-FF-33-00
     * <p>
     * 0A 33 C7 07 01 01 01 4C 1D AF 00 00
     * 应答：02-FF-33-00
     * <p>
     * 静息为90  最大 男 220-age   女 226-age
     */
    protected static byte[] send_set_user(int year, int month, int day, int sex, int weight, int height, int
            maxHeartRate, int
                                                  minHeartRate) {
        char[] yearChar = String.format("%04x", year).toCharArray();
        char[] weightChar = String.format("%04x", weight * 10).toCharArray();

        byte[] data = new byte[]{0x0A, 0x33, Utils.uniteBytes(yearChar[2], yearChar[3]), Utils.uniteBytes
                (yearChar[0],
                        yearChar[1]), (byte) month, (byte) day, (byte) sex, Utils.uniteBytes(weightChar[2],
                weightChar[3]), Utils
                .uniteBytes(weightChar[0],
                weightChar[1]), (byte) height, (byte) maxHeartRate,
                (byte) 0x5a};
        return data;
    }

    /**
     * 获取用户信息
     * 命令：00-05
     * 应答：
     * 若设置内容为: brith day: 1981 年 12 月 12 日，female，体重 100kg，身高 170， 最大心率 190， 最低心率 60
     * 0B-FF-05-BD-07-0C-0C-00-E8-03-AA-BE-3C
     */
    protected static byte[] send_get_user() {
        byte[] data = new byte[]{0x00, 0x05};
        return data;
    }

    /**
     * 2 同步时间戳
     * 实例:
     * 命令：若设置内容为: 2018 年 12 月 13 日 14 点 15 分 16 秒
     * 07-30-E2-07-0C-0D-0E-0F-10
     * 应答：02-FF-30-00
     */
    protected static byte[] send_syncTime() {
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

    /**
     * 获取实时时间  获取时间戳
     * 实例:
     * 命令:00-02
     * 应答:若时间为:2018 年 12 月 13 日 14 点 15 分 16 秒
     * 08-FF-02-E2-07-0C-0D-0E-0F-10
     */
    protected static byte[] send_get_calender() {
        byte[] cmds = new byte[]{0x00, 0x02};
        return cmds;
    }


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    protected static byte[] send_set_w526_alarm(int day, int hour, int min, int index) {
        // TODO: 2019/2/20 发送怎么玩
        //哪一天重复打开
//        "10000011"
//        int value = Integer.valueOf(text, 2);
//        "0 保留 0 Saturday  0 Friday  0 Thursday  0 Wednesday  0 Tuesday  0 Monday  0 Sunday"
        //"00000001"
        //Sunday
        byte[] booleanArrayG = Utils.getBooleanArray((byte) day);
        StringBuilder stringBuilderWeek = new StringBuilder();
        if (Utils.byte2Int(booleanArrayG[0]) == 1) {
            stringBuilderWeek.append("1");
        } else {
            stringBuilderWeek.append("0");
        }
        if (Utils.byte2Int(booleanArrayG[1]) == 1) {
            stringBuilderWeek.append("1");
        } else {
            stringBuilderWeek.append("0");
        }
        if (Utils.byte2Int(booleanArrayG[2]) == 1) {
            stringBuilderWeek.append("1");
        } else {
            stringBuilderWeek.append("0");
        }
        if (Utils.byte2Int(booleanArrayG[3]) == 1) {
            stringBuilderWeek.append("1");
        } else {
            stringBuilderWeek.append("0");
        }
        if (Utils.byte2Int(booleanArrayG[4]) == 1) {
            stringBuilderWeek.append("1");
        } else {
            stringBuilderWeek.append("0");
        }
        if (Utils.byte2Int(booleanArrayG[5]) == 1) {
            stringBuilderWeek.append("1");
        } else {
            stringBuilderWeek.append("0");
        }
        if (Utils.byte2Int(booleanArrayG[6]) == 1) {
            stringBuilderWeek.append("1");
        } else {
            stringBuilderWeek.append("0");
        }
        if (Utils.byte2Int(booleanArrayG[7]) == 1) {
            stringBuilderWeek.append("1");
        } else {
            stringBuilderWeek.append("0");
        }
        int valueRepeat = Integer.valueOf(stringBuilderWeek.toString(), 2);

//        "123"   信息
//        12-35-00-03-0A-0A-31-32-33-00-00-00-00-00-00-00-00-00-00-00
        byte[] msg = new byte[14];
        String strInput = "123";
//        StandardCharsets
        byte[] myMsg = strInput.getBytes(StandardCharsets.UTF_8);

        if (myMsg.length <= 14) {//当长度小于45时补0
            Logger.myLog("**总长度小于等于14，不足的地方补0**");
            System.arraycopy(myMsg, 0, msg, 0, myMsg.length);
            msg = Utils.addOO(msg, myMsg.length, 13);
        } else {//长度大于45切割最前面的45
            Logger.myLog("**长度大于14，截取14字节**");
            System.arraycopy(myMsg, 0, msg, 0, 13);
        }
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < msg.length; i++) {
            builder.append(String.format("%02X ", msg[i]));
        }
        //目前只能设置一组
        // TODO: 2019/2/20 msg要区分出有消息内容的部分
        Logger.myLog(Utils.bytes2HexString(msg) + "  =  " + new String(msg));
        byte[] cmds = new byte[20];
        cmds[0] = 0x12;
        cmds[1] = 0x35;
        cmds[2] = (byte) index;
        cmds[3] = (byte) valueRepeat;
        cmds[4] = (byte) hour;
        cmds[5] = (byte) min;
        cmds[6] = msg[0];
        cmds[7] = msg[1];
        cmds[8] = msg[2];
        cmds[9] = msg[3];
        cmds[10] = msg[4];
        cmds[11] = msg[5];
        cmds[12] = msg[6];
        cmds[13] = msg[7];
        cmds[14] = msg[8];
        cmds[15] = msg[9];
        cmds[16] = msg[10];
        cmds[17] = msg[11];
        cmds[18] = msg[12];
        cmds[19] = msg[13];
//        {0x12, 0x35, 0x00, (byte) value,(byte)23,(byte)23,};
        return cmds;
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    protected static byte[] send_set_w526_alarm(boolean enabel, int day, int hour, int min, int index) {
        // TODO: 2019/2/20 发送怎么玩
        //哪一天重复打开
//        "10000011"
//        int value = Integer.valueOf(text, 2);
//        "0 保留 0 Saturday  0 Friday  0 Thursday  0 Wednesday  0 Tuesday  0 Monday  0 Sunday"
        //"00000001"
        //Sunday
        byte[] booleanArrayG = Utils.getBooleanArray((byte) day);
        StringBuilder stringBuilderWeek = new StringBuilder();
        if (Utils.byte2Int(booleanArrayG[0]) == 1) {
            stringBuilderWeek.append("1");
        } else {
            stringBuilderWeek.append("0");
        }
        if (Utils.byte2Int(booleanArrayG[1]) == 1) {
            stringBuilderWeek.append("1");
        } else {
            stringBuilderWeek.append("0");
        }
        if (Utils.byte2Int(booleanArrayG[2]) == 1) {
            stringBuilderWeek.append("1");
        } else {
            stringBuilderWeek.append("0");
        }
        if (Utils.byte2Int(booleanArrayG[3]) == 1) {
            stringBuilderWeek.append("1");
        } else {
            stringBuilderWeek.append("0");
        }
        if (Utils.byte2Int(booleanArrayG[4]) == 1) {
            stringBuilderWeek.append("1");
        } else {
            stringBuilderWeek.append("0");
        }
        if (Utils.byte2Int(booleanArrayG[5]) == 1) {
            stringBuilderWeek.append("1");
        } else {
            stringBuilderWeek.append("0");
        }
        if (Utils.byte2Int(booleanArrayG[6]) == 1) {
            stringBuilderWeek.append("1");
        } else {
            stringBuilderWeek.append("0");
        }
        if (Utils.byte2Int(booleanArrayG[7]) == 1) {
            stringBuilderWeek.append("1");
        } else {
            stringBuilderWeek.append("0");
        }
        int valueRepeat = Integer.valueOf(stringBuilderWeek.toString(), 2);

//        "123"   信息
//        12-35-00-03-0A-0A-31-32-33-00-00-00-00-00-00-00-00-00-00-00
        /*byte[] msg = new byte[14];
        String strInput = "123";
//        StandardCharsets
        byte[] myMsg = strInput.getBytes(StandardCharsets.UTF_8);

        if (myMsg.length <= 14) {//当长度小于45时补0
            Logger.myLog("**总长度小于等于14，不足的地方补0**");
            System.arraycopy(myMsg, 0, msg, 0, myMsg.length);
            msg = Utils.addOO(msg, myMsg.length, 13);
        } else {//长度大于45切割最前面的45
            Logger.myLog("**长度大于14，截取14字节**");
            System.arraycopy(myMsg, 0, msg, 0, 13);
        }
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < msg.length; i++) {
            builder.append(String.format("%02X ", msg[i]));
        }
        //目前只能设置一组
        // TODO: 2019/2/20 msg要区分出有消息内容的部分
        Logger.myLog(Utils.bytes2HexString(msg) + "  =  " + new String(msg));*/
        byte[] cmds = new byte[20];
        cmds[0] = 0x12;
        cmds[1] = 0x35;
        cmds[2] = (byte) index;
        cmds[3] = (byte) (enabel ? 1 : 0);
        cmds[4] = (byte) valueRepeat;
        cmds[5] = (byte) hour;
        cmds[6] = (byte) min;
        for (int i = 7; i <= 19; i++) {
            cmds[i] = 0;
        }
//        {0x12, 0x35, 0x00, (byte) value,(byte)23,(byte)23,};
        return cmds;
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    protected static byte[] send_set_w560_alarm(boolean enabel, int day, int hour, int min, int index, String name) {
        // TODO: 2019/2/20 发送怎么玩
        //哪一天重复打开
//        "10000011"
//        int value = Integer.valueOf(text, 2);
//        "0 保留 0 Saturday  0 Friday  0 Thursday  0 Wednesday  0 Tuesday  0 Monday  0 Sunday"
        //"00000001"
        //Sunday
        byte[] booleanArrayG = Utils.getBooleanArray((byte) day);
        StringBuilder stringBuilderWeek = new StringBuilder();
        if (Utils.byte2Int(booleanArrayG[0]) == 1) {
            stringBuilderWeek.append("1");
        } else {
            stringBuilderWeek.append("0");
        }
        if (Utils.byte2Int(booleanArrayG[1]) == 1) {
            stringBuilderWeek.append("1");
        } else {
            stringBuilderWeek.append("0");
        }
        if (Utils.byte2Int(booleanArrayG[2]) == 1) {
            stringBuilderWeek.append("1");
        } else {
            stringBuilderWeek.append("0");
        }
        if (Utils.byte2Int(booleanArrayG[3]) == 1) {
            stringBuilderWeek.append("1");
        } else {
            stringBuilderWeek.append("0");
        }
        if (Utils.byte2Int(booleanArrayG[4]) == 1) {
            stringBuilderWeek.append("1");
        } else {
            stringBuilderWeek.append("0");
        }
        if (Utils.byte2Int(booleanArrayG[5]) == 1) {
            stringBuilderWeek.append("1");
        } else {
            stringBuilderWeek.append("0");
        }
        if (Utils.byte2Int(booleanArrayG[6]) == 1) {
            stringBuilderWeek.append("1");
        } else {
            stringBuilderWeek.append("0");
        }
        if (Utils.byte2Int(booleanArrayG[7]) == 1) {
            stringBuilderWeek.append("1");
        } else {
            stringBuilderWeek.append("0");
        }
        int valueRepeat = Integer.valueOf(stringBuilderWeek.toString(), 2);

        index += 0x20;
        byte[] cmds = new byte[20];
        cmds[0] = 0x12;
        cmds[1] = 0x35;
        cmds[2] = (byte) index;
        cmds[3] = (byte) (enabel ? (hour | 128) : hour);
        cmds[4] = (byte) min;
        cmds[5] = (byte) valueRepeat;

        byte[] names = new byte[14];
        names = name.getBytes();

        for (int i = 0; i <= 13; i++) {
            if (i < names.length) {
                cmds[i+6] = names[i];
            } else {
                cmds[i+6] = 0x00;
            }
        }

        return cmds;
    }

    /**
     * 新增W560闹钟
     * @param day
     * @param hour
     * @param min
     * @param name
     * @return
     */
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    protected static byte[] send_add_w560_alarm(int day, int hour, int min, String name) {

        byte[] booleanArrayG = Utils.getBooleanArray((byte) day);
        StringBuilder stringBuilderWeek = new StringBuilder();
        if (Utils.byte2Int(booleanArrayG[0]) == 1) {
            stringBuilderWeek.append("1");
        } else {
            stringBuilderWeek.append("0");
        }
        if (Utils.byte2Int(booleanArrayG[1]) == 1) {
            stringBuilderWeek.append("1");
        } else {
            stringBuilderWeek.append("0");
        }
        if (Utils.byte2Int(booleanArrayG[2]) == 1) {
            stringBuilderWeek.append("1");
        } else {
            stringBuilderWeek.append("0");
        }
        if (Utils.byte2Int(booleanArrayG[3]) == 1) {
            stringBuilderWeek.append("1");
        } else {
            stringBuilderWeek.append("0");
        }
        if (Utils.byte2Int(booleanArrayG[4]) == 1) {
            stringBuilderWeek.append("1");
        } else {
            stringBuilderWeek.append("0");
        }
        if (Utils.byte2Int(booleanArrayG[5]) == 1) {
            stringBuilderWeek.append("1");
        } else {
            stringBuilderWeek.append("0");
        }
        if (Utils.byte2Int(booleanArrayG[6]) == 1) {
            stringBuilderWeek.append("1");
        } else {
            stringBuilderWeek.append("0");
        }
        if (Utils.byte2Int(booleanArrayG[7]) == 1) {
            stringBuilderWeek.append("1");
        } else {
            stringBuilderWeek.append("0");
        }
        int valueRepeat = Integer.valueOf(stringBuilderWeek.toString(), 2);

        byte[] cmds = new byte[20];
        cmds[0] = 0x12;
        cmds[1] = 0x35;
        cmds[2] = 0x10;
        cmds[3] = (byte) (hour | 128);
        cmds[4] = (byte) min;
        cmds[5] = (byte) valueRepeat;

        byte[] names = new byte[14];
        if (name == null) {
            name = "";
        }
        names = name.getBytes();

        for (int i = 0; i <= 13; i++) {
            if (i < names.length) {
                cmds[i+6] = names[i];
            } else {
                cmds[i+6] = 0x00;
            }
        }

        return cmds;
    }

    /**
     * 删除W560闹钟
     * @param index
     * @return
     */
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    protected static byte[] send_delete_w560_alarm(int index) {

        index += 0x30;
        byte[] cmds = new byte[3];
        cmds[0] = 0x01;
        cmds[1] = 0x35;
        cmds[2] = (byte) index;

        return cmds;
    }

    /**
     * 设置闹钟
     * 实例:
     * 命令:若设置闹钟 0，10 点 10 分，周 1，周 2 开，闹钟信息为”123”
     * 12-35-00-03-0A-0A-31-32-33-00-00-00-00-00-00-00-00-00-00-00
     * 应答:02-FF-35-00
     */
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    protected static byte[] send_set_alarm(int day, int hour, int min, int index) {
        // TODO: 2019/2/20 发送怎么玩
        //哪一天重复打开
//        "10000011"
//        int value = Integer.valueOf(text, 2);
//        "0 保留 0 Saturday  0 Friday  0 Thursday  0 Wednesday  0 Tuesday  0 Monday  0 Sunday"
        //"00000001"
        //Sunday
        int value = Integer.valueOf("00000001", 2);
        byte[] booleanArrayG = Utils.getBooleanArray((byte) day);
        StringBuilder stringBuilderWeek = new StringBuilder();
        stringBuilderWeek.append("0");

        if (Utils.byte2Int(booleanArrayG[1]) == 1) {
            stringBuilderWeek.append("1");
        } else {
            stringBuilderWeek.append("0");
        }
        if (Utils.byte2Int(booleanArrayG[2]) == 1) {
            stringBuilderWeek.append("1");
        } else {
            stringBuilderWeek.append("0");
        }
        if (Utils.byte2Int(booleanArrayG[3]) == 1) {
            stringBuilderWeek.append("1");
        } else {
            stringBuilderWeek.append("0");
        }
        if (Utils.byte2Int(booleanArrayG[4]) == 1) {
            stringBuilderWeek.append("1");
        } else {
            stringBuilderWeek.append("0");
        }
        if (Utils.byte2Int(booleanArrayG[5]) == 1) {
            stringBuilderWeek.append("1");
        } else {
            stringBuilderWeek.append("0");
        }
        if (Utils.byte2Int(booleanArrayG[6]) == 1) {
            stringBuilderWeek.append("1");
        } else {
            stringBuilderWeek.append("0");
        }
        if (Utils.byte2Int(booleanArrayG[7]) == 1) {
            stringBuilderWeek.append("1");
        } else {
            stringBuilderWeek.append("0");
        }
        int valueRepeat = Integer.valueOf(stringBuilderWeek.toString(), 2);

//        "123"   信息
//        12-35-00-03-0A-0A-31-32-33-00-00-00-00-00-00-00-00-00-00-00
        byte[] msg = new byte[14];
        String strInput = "123";
//        StandardCharsets
        byte[] myMsg = strInput.getBytes(StandardCharsets.UTF_8);

        if (myMsg.length <= 14) {//当长度小于45时补0
            Logger.myLog("**总长度小于等于14，不足的地方补0**");
            System.arraycopy(myMsg, 0, msg, 0, myMsg.length);
            msg = Utils.addOO(msg, myMsg.length, 13);
        } else {//长度大于45切割最前面的45
            Logger.myLog("**长度大于14，截取14字节**");
            System.arraycopy(myMsg, 0, msg, 0, 13);
        }
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < msg.length; i++) {
            builder.append(String.format("%02X ", msg[i]));
        }
        //目前只能设置一组
        // TODO: 2019/2/20 msg要区分出有消息内容的部分
        Logger.myLog(Utils.bytes2HexString(msg) + "  =  " + new String(msg));
        byte[] cmds = new byte[20];
        cmds[0] = 0x12;
        cmds[1] = 0x35;
        cmds[2] = (byte) index;
        cmds[3] = (byte) valueRepeat;
        cmds[4] = (byte) hour;
        cmds[5] = (byte) min;
        cmds[6] = msg[0];
        cmds[7] = msg[1];
        cmds[8] = msg[2];
        cmds[9] = msg[3];
        cmds[10] = msg[4];
        cmds[11] = msg[5];
        cmds[12] = msg[6];
        cmds[13] = msg[7];
        cmds[14] = msg[8];
        cmds[15] = msg[9];
        cmds[16] = msg[10];
        cmds[17] = msg[11];
        cmds[18] = msg[12];
        cmds[19] = msg[13];
//        {0x12, 0x35, 0x00, (byte) value,(byte)23,(byte)23,};
        return cmds;
    }

    /**
     * 获取闹钟设置
     * 实例:
     * 命令: 01-07-00 读取闹钟 0，10 点 10 分，周 1，周 2 开，闹钟信息为”123”
     * 应答:12-FF-07-03-0A-0A-31-32-33-00-00-00-00-00-00-00-00-00-00-00
     *
     * @param index   闹钟下标(第几个闹钟)
     * @param success <#success description#>
     * @param fail    <#fail description#>
     */
    protected static byte[] send_get_alarm(int index) {
        byte[] cmds = new byte[]{0x01, 0x07, (byte) index};
        return cmds;
    }

    /**
     * 获取W560闹钟设置
     * 实例: 01-35-40
     */
    protected static byte[] send_get_w560_alarm() {
        byte[] cmds = new byte[]{0x01, 0x35, 0x40};
        return cmds;
    }

    /**
     * 睡眠检测设置
     * 实例:
     * 命令:若设置为窗口睡眠，有提醒，勿扰时间开启，窗口开始 22:00，窗口结束 7:00, 勿扰时间开始 21:00 勿扰时间结束 8:00
     * 09-34-07-16-00-07-00-15-00-08-00 应答:02-FF-34-00
     */
    protected static byte[] send_set_sleep_setting(boolean isAutoSleep, boolean hasNotif, boolean disturb, int
            testStartTimeH, int testStartTimeM, int
                                                           testEndTimeH, int testEndTimeM,
                                                   int
                                                           disturbStartTimeH, int disturbStartTimeM, int
                                                           disturbEndTimeH, int disturbEndTimeM) {
        // TODO: 2019/2/20 发送怎么玩
//        09-34
        byte[] cmds = new byte[11];
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("00000");
        if (disturb) {
            stringBuilder.append("1");
        } else {
            stringBuilder.append("0");
        }
        if (hasNotif) {
            stringBuilder.append("1");
        } else {
            stringBuilder.append("0");
        }
        if (isAutoSleep) {
            stringBuilder.append("0");
        } else {
            stringBuilder.append("1");
        }
        int value = Integer.valueOf(stringBuilder.toString(), 2);
        cmds[0] = 0x09;
        cmds[1] = 0x34;
        cmds[2] = (byte) value;
        cmds[3] = (byte) testStartTimeH;
        cmds[4] = (byte) testStartTimeM;
        cmds[5] = (byte) testEndTimeH;
        cmds[6] = (byte) testEndTimeM;
        cmds[7] = (byte) disturbStartTimeH;
        cmds[8] = (byte) disturbStartTimeM;
        cmds[9] = (byte) disturbEndTimeH;
        cmds[10] = (byte) disturbEndTimeM;
        return cmds;
    }

    /**
     * 获取睡眠检测设置
     * 实例:
     * 命令:00-06
     * 应答:若设置为窗口睡眠，有提醒，勿扰时间开启，窗口开始 22:00，窗口结束 7:00, 勿扰时间开始 21:00 勿扰时间结束 8:00,
     * 0A-FF-06-07-16-00-07-00-15-00-08-00
     *
     * @param success <#success description#>
     * @param fail    <#fail description#>
     */
    protected static byte[] send_get_sleep_setting() {
        byte[] cmds = new byte[]{0x00, 0x06};
        return cmds;
    }

    /**
     * 设置久坐提醒
     * 实例:
     * 命令:若设置久坐时间为 200 分钟 05-31-C8-
     * 应答:02-FF-31-00
     * 0-255
     *
     * @param timeInterval 久坐提醒时间分钟，小于 5 关闭， 大于等于 5 开启
     * @param success      <#success description#>
     * @param fail         <#fail description#>
     */
    protected static byte[] send_set_sedentary_time(int time, int startHour, int startMin, int endHour, int endMin) {
        // TODO: 2019/2/20 发送怎么玩
        byte[] cmds = new byte[]{0x05, 0x31, (byte) time, (byte) startHour, (byte) startMin, (byte) endHour, (byte) endMin};
        return cmds;
    }

    /**
     * 获取久坐提醒设置
     * 命令：00-03
     * 应答：若设置久坐时间为 200 分钟
     * 02-FF-03-C8
     *
     * @param success <#success description#>
     * @param fail    <#fail description#>
     */
    protected static byte[] send_get_sedentary_time() {
        byte[] cmds = new byte[]{0x00, 0x03};
        return cmds;
    }

    /**
     * 电话
     */
    protected static byte[] send_handleCall() {
        Log.e("handleD", "handleCall");
        byte[] data = new byte[]{(byte) 0x02, 0x10, (byte) 0x80, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,
                0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00};
        return data;
    }

    /**
     * 短信
     */
    protected static byte[] send_handleSms() {
        if ((System.currentTimeMillis() - mExitTime) > 1000) {
            mExitTime = System.currentTimeMillis();
            Log.e("handleD", "handleSms");
            byte[] data = new byte[]{(byte) 0x02, 0x10, (byte) 0x80, 0x01, 0x00, 0x00, 0x00, 0x00, 0x00,
                    0x00, 0x00,
                    0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00};
            return data;
        }
        return null;
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
    protected static byte[] send_handleNotification(String packagename) {
        if (Constants.strPkNames.contains(packagename)) {
            Log.e("handleD", "handleNotification");
            byte[] data = new byte[]{(byte) 0x02, 0x10, (byte) 0x80, 0x01, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,
                    0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00};
            return data;
        }
        return null;
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
    protected static byte[] send_switchMode(int mode) {
        byte[] cmds = new byte[]{0x01, (byte) 0xE2, (byte) mode};
        return cmds;
    }

    protected static byte[] send_switchAdjust(boolean enable) {
        byte[] cmds;
        if (enable) {
            cmds = new byte[]{0x01, 0x11, 0x01};
        } else {
            cmds = new byte[]{0x01, 0x11, (byte) 0xFF};
        }
//            if (enable) {
//                sendWhat = Order_send_adjust_mode;
//            } else {
//                sendWhat = Order_send_exit_adjust_mode;
//            }
        return cmds;
    }

    /**
     * 调时针 分针
     */
    protected static byte[] adjustHourAndMin(int degreesHour, int degreesMin) {
        byte[] cmds = new byte[]{0x03, 0x11, 0x00, (byte) degreesHour, (byte) degreesMin};
        return cmds;
    }

    /**
     * 提取任意一天 24 小时数据
     * 实例:
     * 命令， 提取当天数据，01-40-00
     * 应答: 02-FF-40-00
     * 命令， 提取昨天数据，01-40-01，
     * 应答: 有数据 02-FF-40-00，无数据:02-FF-40-04
     * 命令， 提取第 7 天数据，01-40-07，
     * 应答: 有数据 02-FF-40-00，无数据:02-FF-40-04
     *
     * @param count   范围0-6 (0- Today  大于 0，提取 n 天前数据)
     * @param success <#success description#>
     * @param fail    <#fail description#>
     */
    protected static byte[] send_get_daily_record(int day) {
        byte[] cmds = new byte[]{0x01, (byte) 0x40, (byte) day};
        return cmds;
    }

    /**
     * 清除所有 24 小时数据
     * Note: 清除后当天的数据也变成 0 了。
     * 历史数据没有数据
     * 实例:
     * 命令， 00-41
     * 应答: 02-FF-41-00
     *
     * @param success <#success description#>
     * @param fail    <#fail description#>
     */
    protected static byte[] send_clear_daily_record() {
        byte[] cmds = new byte[]{0x00, (byte) 0x41};
        return cmds;
    }

    protected static byte[] send_getTestData() {
        byte[] cmds = new byte[]{0x01, (byte) 0xE5};
        return cmds;
    }

    /**
     * 提取锻炼数据
     * 每包数据 20byte，通过存储数据上传通道上传
     * 每包第一个字节 BYTE0 是包序号，00 开始，每包加 1，BIT7 位 1 是结束包，如果包序号时 0x7F，下一包包序号为 0x1，后面的包序号依次累加
     * 第一包内容定义如下:
     * BYTE1 to BYTE4: 32 位序列号
     * BYTE5-8: 保留字节
     * BYTE9 to BYTE12:设备开机后的总累计秒数 (从设备上电开始，每秒加一), 大端模式
     * BYTE13 to BYTE16， F3-F3-F3-F3
     * 从第一包第 17 个字节开始，分段记录锻炼数据，一个完整的锻炼数据段包括，
     * BYTE1 to BYT3: 开始位 F1-00-00
     * BYTE4 to BYTE7: 锻炼开始时的总累计秒数，大端模式
     * BYTE8: 固定为 0
     * BYTE9 to BYTEn 每秒记录数据，每组数据 2 个字节，BYTE1 是心率，BYTE2 是步数(低半位是步数， 取值范围 0 to 10，高半位是 activity level ，取值范围 0 to 15)
     * 记录数据中出现 0xFF 表示一个数据段的结束，后面马上开始第二个数据段
     * <p>
     * 实例:命令， 提取锻炼数据，完成后删除， 01-42-01
     * 应答:有数据 02-FF-42-00， 无数据 02-FF-42-04
     *
     * @param isClear <#isClear description#>
     * @param success <#success description#>
     * @param fail    <#fail description#>
     */
    protected static byte[] send_get_exercise_data() {
        byte[] cmds = new byte[]{0x01, (byte) 0x42, 0x01};
        return cmds;
    }

    /**
     * 清除所有锻炼数据
     * 实例：
     * 命令， 00-43
     * 应答：02-FF-43-00
     *
     * @param success <#success description#>
     * @param fail    <#fail description#>
     */
    protected static byte[] send_clear_exercise_data() {
        byte[] cmds = new byte[]{0x00, (byte) 0x43};
        return cmds;
    }

    /**
     * reset 恢复出厂
     * 命令 04-50-A1-FE-74-69
     * 应答：若设备未配对： 02-FF-50-01
     * 若设备已经配对：02-FF-50-00
     */
    protected static byte[] send_reset() {
        byte[] data = new byte[]{0x04, 0x60, (byte) 0xA1, (byte) 0xFE, 0x74, 0x69};
        return data;
    }


    /**
     * 调时针 分针
     */
    protected static byte[] send_adjustHourAndMin(int degreesHour, int degreesMin) {
        byte[] cmds = new byte[]{0x03, 0x11, 0x00, (byte) degreesHour, (byte) degreesMin};
        return cmds;
    }

    /**
     * SET_SN_FACTORY
     * 生产阶段设置序列号
     * <p>
     * 实例: 命令： 若序列号设置为 10000，生产日期为 2018 年 12 月 13 日 14 时 15 分 16 秒
     * 0B-80-10-27-00-00-E2-07-0C-0D-0E-0F-10
     * 应答：02-FF-80-00
     */
    protected static byte[] send_set_sn_factory() {
        byte[] cmds = new byte[]{0x0B, (byte) 0x80, 0x10, 0x27, 0x00, 0x00, (byte) 0xE2, 0x07, 0x0C, 0x0D, 0x0E,
                0x0F, 0x10};
        return cmds;
    }

    /**
     * SET_SN_NORMALMODE
     * 产品生产完成后，更改序列号
     * Message Body
     * Index Size Type Range Additional Information
     * 2 4 Uint32 GENERAL PASSWORD：0X6974FEA1
     * 6 4 Uint32 Uint32 32 位序列号
     * Respond Data：RESPONSE_NO_ERROR
     * NOTE: 命令发送成功后，设备会断开蓝牙，并更改新的蓝牙名字（序列号后 4 位）
     * 实例: 命令： 若序列号设置为 10021，
     * 08-81-A1-FE-74-69-25-27-00-00
     * 应答：02-FF-81-00
     */
    protected static byte[] send_set_sn_normalmode(int SN) {


        byte[] cmds = new byte[]{0x08, (byte) 0x81, (byte) 0xA1, (byte) 0xFE, 0x74, 0x69, (byte) SN, (byte) (SN >> 8), (byte) (SN >> 16),
                (byte) (SN >> 24)};
//        char[] target = String.format("%04x", SN).toCharArray();
//        byte b = uniteBytes(target[0], target[1]);//27
//        byte b1 = uniteBytes(target[2], target[3]);//25
//        byte[] cmds = new byte[]{0x08, (byte) 0x81, (byte) 0xA1, (byte) 0xFE, 0x74, 0x69, b1, b, 0x00,
//                0x00};
       /* char[] target = String.format("%04x", SN).toCharArray();
        byte b = uniteBytes(target[0], target[1]);//27
        byte b1 = uniteBytes(target[2], target[3]);//25
        byte b2 = uniteBytes(target[4], target[5]);//25
//        989681:10000001    2725:10021
        byte[] cmds = new byte[]{0x08, (byte) 0x81, (byte) 0xA1, (byte) 0xFE, 0x74, 0x69, b2, b1, b
                , 0x00};*/
        return cmds;
    }

    /**
     * GET_SN_DATA
     * 读取序列号和生产日期
     *
     * @return 实例:
     * 命令： 00-82
     * 应答：若序列号为 10000，生产日期为 2018 年 12 月 13 日 14 时 15 分 16 秒
     * 0C-FF-82-10-27-00-00-E2-07-0C-0D-0E-0F-10
     */
    protected static byte[] send_get_sn_data() {
        byte[] cmds = new byte[]{0x00, (byte) 0x82};
        return cmds;
    }

    /**
     * SET_BELTNAME
     * 设置序列号
     * <p>
     * 实例:
     * 命令： 若设置名字位 “W516ABCDEFGHIJKLMNOPQRS”不用附加 4 位序列号
     * 第一包： 12-83-00-02-57-35-31-36-41-42-43-44-45-46-47-48-49-4A-4B-4C
     * 第二包： 08-83-81-4D-4E-4F-50-51-52-53
     * 若设置名字位 “W516ABCDEFGH”附加 4 位序列号
     * 0E-83-80-01-57-35-31-36-41-42-43-44-45-46-47-48
     * Page 11 of 14
     * 应答：02-FF-83-00
     *
     * @return
     */
    protected static byte[] send_set_beltname() {
        // TODO: 2019/2/21 待完成
        byte[] cmds = new byte[]{0x03, (byte) 0xE1, 0x09, (byte) 0xE8, 0x03};
        return cmds;
    }

    /**
     * TEST_RESET
     * 设备复位
     *
     * @return 实例:
     * 命令： 00-E0
     * 应答：02-FF-E0-00
     */
    protected static byte[] send_test_reset() {
        // byte[] cmds = new byte[]{0x00, (byte) 0xE0};
        byte[] cmds = new byte[]{0x01, (byte) 0x60, 0x02, (byte) 0xA1, (byte) 0xFE, 0x74, 0x69};
        return cmds;
    }

    /**
     * TEST_MOTORLED
     * 测试马达和 LED
     *
     * @return 实例:
     * 命令： 亮红灯，同时马达转动 1000 次，03-E1-09-E8-03
     * 停止测试：01-E1-00
     */
    protected static byte[] send_test_motorled() {
        byte[] cmds = new byte[]{0x03, (byte) 0xE1, 0x09, (byte) 0xE8, 0x03};
        return cmds;
    }


    /**
     * 停止测试马达和LED
     *
     * @return
     */
    protected static byte[] send_stop_test_motorled() {
        byte[] cmds = new byte[]{0x01, (byte) 0xE1, 0x00};
        return cmds;
    }

    /**
     * TEST_HANDLE
     * 测试指针
     *
     * @return
     */
    protected static byte[] send_test_handle() {
        byte[] cmds = new byte[]{0x01, (byte) 0xE1, 0x00};
        return cmds;
    }

    /**
     * TEST_DISPLAY
     * 测试 OLED 显示
     *
     * @return 实例:
     * 命令：全屏显示 01-E3-FF
     */
    protected static byte[] send_test_display() {
        byte[] cmds = new byte[]{0x01, (byte) 0xE3, (byte) 0xFF};
        return cmds;
    }

    /**
     * TEST_OHR
     * 测试 OHR
     *
     * @return 实例:
     * 命令：心率开启 LED 电流设置为最大 02-E4-01-32
     * 心率开启 LED 电流设置为最小 02-E4-01-08      ---
     */
    protected static byte[] send_test_ohr() {
        byte[] cmds = new byte[]{0x02, (byte) 0xE4, (byte) 0x01, 0x08};
        return cmds;
    }

    /**
     * DEVICE_TO_PHONE
     * 设备发送命令到 APP
     *
     * @return 实例:
     * 命令（设备发出来）：找电话，01-60-00
     * 应答：02-FF-60-00
     */
    protected static byte[] send_device_to_phone() {
        byte[] cmds = new byte[]{0x01, (byte) 0x60, (byte) 0x00};
        return cmds;
    }

    //******************************************watch 思诺嘉手表**********************************************//

    /**
     * 设置日期:设置年、月、日
     */
    protected static byte[] send_date() {
        byte[] resultData = new byte[5];
        Calendar calendar = Calendar.getInstance();
        char[] year = String.format("%04x", calendar.get(Calendar.YEAR)).toCharArray();
        char[] month = String.format("%02x", calendar.get(Calendar.MONTH) + 1).toCharArray();
        char[] day = String.format("%02x", calendar.get(Calendar.DAY_OF_MONTH)).toCharArray();
        resultData[0] = 0x08;
        resultData[1] = uniteBytes(year[0], year[1]);
        resultData[2] = uniteBytes(year[2], year[3]);
        resultData[3] = uniteBytes(month[0], month[1]);
        resultData[4] = uniteBytes(day[0], day[1]);
        Logger.myLog("bytes2HexString == " + Utils.bytes2HexString(resultData));
        return resultData;
    }

    /**
     * 设置时间
     */
    protected static byte[] send_time() {
        byte[] resultData = new byte[4];
        Calendar calendar = Calendar.getInstance();
        char[] hour = String.format("%02x", calendar.get(Calendar.HOUR_OF_DAY)).toCharArray();
        char[] min = String.format("%02x", calendar.get(Calendar.MINUTE)).toCharArray();
        char[] sec = String.format("%02x", calendar.get(Calendar.SECOND)).toCharArray();
        resultData[0] = 0x09;
        resultData[1] = uniteBytes(hour[0], hour[1]);
        resultData[2] = uniteBytes(min[0], min[1]);
        resultData[3] = uniteBytes(sec[0], sec[1]);
        return resultData;
    }

    /**
     * 设置身高
     */
    protected static byte[] send_height(int height) {
        byte[] resultData = new byte[2];
        char[] high = String.format("%02x", height).toCharArray();
        resultData[0] = 0x04;
        resultData[1] = uniteBytes(high[0], high[1]);
        return resultData;
    }

    /**
     * 设置体重
     */
    protected static byte[] send_weight(int weight) {
        byte[] resultData = new byte[2];
        char[] weigh = String.format("%02x", weight).toCharArray();
        resultData[0] = 0x05;
        resultData[1] = uniteBytes(weigh[0], weigh[1]);
        return resultData;
    }

    /**
     * 设置年龄
     */
    protected static byte[] send_age(int age) {
        byte[] resultData = new byte[2];
        char[] ag = String.format("%02x", age).toCharArray();
        resultData[0] = 0x2c;
        resultData[1] = uniteBytes(ag[0], ag[1]);
        return resultData;
    }

    /**
     * 设置性别
     */
    protected static byte[] send_sex(int sex) {
        byte[] resultData = new byte[2];
        resultData[0] = 0x2d;
        resultData[1] = (byte) sex;
        return resultData;
    }

    /**
     * 设置屏保时间
     * 默认值为5s
     */
    protected static byte[] send_screenTime(int screenTime) {
        byte[] resultData = new byte[2];
        char[] screenTim = String.format("%02x", screenTime).toCharArray();
        resultData[0] = 0x0b;
        resultData[1] = uniteBytes(screenTim[0], screenTim[1]);
        return resultData;
    }

    /**
     * 翻腕亮屏开关
     * 1 开  0 关
     */
    protected static byte[] send_handScreen(boolean enable) {
        byte[] resultData = new byte[2];
        resultData[0] = 0x3a;
        resultData[1] = (byte) (enable ? 1 : 0);
        return resultData;
    }

    /**
     * 设置目标步数
     */
    protected static byte[] send_targetStep(int targetStep) {
        byte[] resultData = new byte[3];
        char[] target = String.format("%04x", targetStep).toCharArray();
        resultData[0] = 0x26;
        resultData[1] = uniteBytes(target[0], target[1]);
        resultData[2] = uniteBytes(target[2], target[3]);
        return resultData;
    }

    /**
     * 获取睡眠数据
     */
    protected static byte[] send_get_sleepData() {
        byte[] resultData = new byte[1];
        char[] cha = String.format("%02x", 25).toCharArray();
        resultData[0] = uniteBytes(cha[0], cha[1]);
        return resultData;
    }

    /**
     * 获取运动数据
     */
    protected static byte[] send_get_sportData() {
        byte[] resultData = new byte[1];
        resultData[0] = 0x15;
        return resultData;
    }

    /**
     * 获取版本号
     */
    protected static byte[] send_get_version() {
        byte[] resultData = new byte[1];
        char[] cha = String.format("%02x", 23).toCharArray();
        resultData[0] = uniteBytes(cha[0], cha[1]);
        return resultData;
    }

    /**
     * 社交提醒
     * todo 1 QQ 2 微信 3 Facebook 4 Twitter
     */
    protected static byte[] send_qq() {
        byte[] resultData = new byte[2];
        char[] cha = String.format("%02x", 23).toCharArray();
        resultData[0] = 0x31;
        resultData[0] = uniteBytes(cha[0], cha[1]);
        return resultData;
    }

    /**
     * 电话提醒
     */
    protected static byte[] send_phone() {
        byte[] resultData = new byte[2];
        resultData[0] = 0x06;
        resultData[1] = (byte) 0xaa;
        return resultData;
    }

    /**
     * 短信提醒
     */
    protected static byte[] send_sms() {
        byte[] resultData = new byte[2];
        resultData[0] = 0x07;
        resultData[1] = (byte) 0xaa;
        return resultData;
    }

    /**
     * 闹钟设置
     */
    protected static byte[] send_set_clockTime(int hour, int min) {
        byte[] resultData = new byte[3];
        char[] hourC = String.format("%02x", hour).toCharArray();
        char[] minC = String.format("%02x", min).toCharArray();
        resultData[0] = 0x0c;
        resultData[1] = uniteBytes(hourC[0], hourC[1]);
        resultData[2] = uniteBytes(minC[0], minC[1]);
        return resultData;
    }

    /**
     * 久坐提醒设置
     */
    protected static byte[] send_set_sedentary_reminder(int startHour, int startMin, int endHour, int endMin) {
        byte[] resultData = new byte[5];
        char[] startHourC = String.format("%02x", startHour).toCharArray();
        char[] startMinC = String.format("%02x", startMin).toCharArray();
        char[] endHourC = String.format("%02x", endHour).toCharArray();
        char[] endMinC = String.format("%02x", endMin).toCharArray();
        resultData[0] = 0x1e;
        resultData[1] = uniteBytes(startHourC[0], startHourC[1]);
        resultData[2] = uniteBytes(startMinC[0], startMinC[1]);
        resultData[3] = uniteBytes(endHourC[0], endHourC[1]);
        resultData[4] = uniteBytes(endMinC[0], endMinC[1]);
        return resultData;
    }

    /**
     * 清除历史数据
     */
    protected static byte[] send_set_clear_historydata() {
        byte[] resultData = new byte[2];
        resultData[0] = (byte) 0xd8;
        resultData[1] = (byte) 0x5a;
        return resultData;
    }

    /**
     * 重启手环
     */
    protected static byte[] send_set_restart_watch() {
        byte[] resultData = new byte[2];
        resultData[0] = (byte) 0xa5;
        resultData[1] = (byte) 0x5a;
        return resultData;
    }

    /**
     * 关机
     */
    protected static byte[] send_set_close_watch() {
        byte[] resultData = new byte[2];
        resultData[0] = (byte) 0x5b;
        resultData[1] = (byte) 0x5a;
        return resultData;
    }

    /**
     * 开关实时心率
     */
    protected static byte[] send_set_realtime_heartrate_enable(boolean enable) {
        byte[] resultData = new byte[2];
        resultData[0] = (byte) 0x32;
        resultData[1] = enable ? 0x0b : (byte) 0x16;
        return resultData;
    }

    /**
     * 开全天心率
     */
    protected static byte[] send_enable_heartrate(boolean enable) {
        byte[] resultData = new byte[2];
        char[] cha = String.format("%02x", enable ? 10 : 255).toCharArray();
        resultData[0] = 0x35;
        resultData[1] = uniteBytes(cha[0], cha[1]);
        return resultData;
    }

    /**
     * 获取十分钟活动数据
     */
    protected static byte[] send_get_10min_sportdata(int startHour, int startMin, int endHour, int endMin) {
        byte[] resultData = new byte[5];
        char[] startHourC = String.format("%02x", startHour).toCharArray();
        char[] startMinC = String.format("%02x", startMin).toCharArray();
        char[] endHourC = String.format("%02x", endHour).toCharArray();
        char[] endMinC = String.format("%02x", endMin).toCharArray();
        resultData[0] = 0x27;
        resultData[1] = uniteBytes(startHourC[0], startHourC[1]);
        resultData[2] = uniteBytes(startMinC[0], startMinC[1]);
        resultData[3] = uniteBytes(endHourC[0], endHourC[1]);
        resultData[4] = uniteBytes(endMinC[0], endMinC[1]);
        return resultData;
    }


    // 两个数字的分别的字符形式传入，转出byte值,src0是高位，src1是低位
    public static byte uniteBytes(char src0, char src1) {
        byte b0 = Byte.decode("0x" + src0).byteValue();
        b0 = (byte) (b0 << 4);
        byte b1 = Byte.decode("0x" + src1).byteValue();
        byte ret = (byte) (b0 | b1);
        return ret;
    }

    public static String byte2HexStr(byte[] b) {
        String hs = "";
        String stmp = "";
        for (int n = 0; n < b.length; n++) {
            stmp = (Integer.toHexString(b[n] & 0XFF));
            if (stmp.length() == 1)
                hs = hs + "0" + stmp;
            else
                hs = hs + stmp;
            // if (n<b.length-1) hs=hs+":";
        }
        return hs.toUpperCase();
    }


}
