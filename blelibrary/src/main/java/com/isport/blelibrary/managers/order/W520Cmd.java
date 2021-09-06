package com.isport.blelibrary.managers.order;

public class W520Cmd extends W311Cmd {

    public static int type_out = 1;
    public static int type_bike = 2;
    public static int type_indoor = 3;

    /**
     * 手机开启运动模式指令 格式：BE-01-24-ED 回复
     * ：DE-01-24-FB-运动模式-开关(00:关 01:开)
     * 运动模式:01:室外走，02:骑行，03：室内走。
     */
    public byte[] sendOpenSportMode(int type, boolean enable) {
        byte[] data = new byte[]{(byte) 0xbe, 0x01, 0x24, (byte) 0xfb, (byte) type, (byte) (enable ? 1 : 0)};
        return data;
    }


    /**
     * 格式：BE-01-27-FE-首页（00-默认 01-指针表盘） 回复：DE-01-27-00
     */

    public byte[] sendWatchFace(int enable) {
        enable = enable - 1;
        //byte[] data = new byte[]{(byte) 0xbe, 0x01, 0x27, (byte) 0xfb};
        byte[] data = new byte[]{(byte) 0xbe, 0x01, 0x27, (byte) 0xfe, (byte) enable};
        return data;
    }

    public byte[] getWatchFace() {
        byte[] data = new byte[]{(byte) 0xbe, 0x01, 0x27, (byte) 0xed};
        return data;
    }


    /**
     * 读取存储运动数据指令
     * BE-02-16-ED
     */

    public byte[] sendReadMemorySportData() {
        byte[] data = new byte[]{(byte) 0xbe, 0x02, 0x16, (byte) 0xED};
        return data;
    }

    /**
     * 发送天气指令
     * BE-01-26-FE-数据(00 无数据，01 有数据)
     */


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

        //(byte) ((height & 0xffff) >> 8)
        int one = (byte) currentDay >> 24 & 0xff;
        int two = (byte) currentDay >> 16 & 0xff;
        int three = (byte) currentDay >> 8 & 0xff;
        int four = (byte) currentDay & 0xff;

        byte[] data = new byte[]{(byte) 0xbe, 0x01, 0x26, (byte) 0xFE, (byte) (havsData ? 1 : 0), (byte) (currentDay >> 24 & 0xff), (byte) (currentDay >> 16 & 0xff), (byte) (currentDay >> 8 & 0xff), (byte) (currentDay & 0xff)
                , (byte) (nextDay >> 24 & 0xff), (byte) (nextDay >> 16 & 0xff), (byte) (nextDay >> 8 & 0xff), (byte) (nextDay & 0xff)
                , (byte) (afterDay >> 24 & 0xff), (byte) (afterDay >> 16 & 0xff), (byte) (afterDay >> 8 & 0xff), (byte) (afterDay & 0xff)};
        return data;
    }

    //IOS控制信息推送指令
    //BE-01-22-FE-开关1(1Bytes)-开关2(1Bytes)
    //DE-01-22-ED  回复设置成功
    //查询 BE+01+22+ED

    //开关1： bit0：SMS bit1: QQ bit2: WeChat bit3: Skype bit4: facebook bit5: Twitter bit6: linkedin
    //bit7: WhatsApp 开关2： bit0: instagram bit1: Messenger bit2: 来电提醒

    public byte[] sendMessage(boolean sms, boolean qq, boolean wechat, boolean skype, boolean facebook, boolean linkedin, boolean whatsapp, boolean instagram, boolean Messenger, boolean isCall) {

        int smsint = 1, qqInt = 2, wechatint = 4, skypeint = 8, facebookint = 16, linkedinint = 32, whatsappint = 64, instagramint = 1, messengerint = 2, isCallint = 4;
        if (!sms) {
            smsint = 0;
        }
        if (!qq) {
            qqInt = 0;
        }
        if (!wechat) {
            wechatint = 0;
        }
        if (!skype) {
            skypeint = 0;
        }
        if (!facebook) {
            facebookint = 0;
        }
        if (!linkedin) {
            linkedinint = 0;
        }
        if (!whatsapp) {
            whatsappint = 0;
        }
        if (!instagram) {
            instagramint = 0;
        }
        if (!Messenger) {
            messengerint = 0;
        }
        if (!isCall) {
            isCallint = 0;
        }

        int sms1 = smsint | qqInt | wechatint | facebookint | linkedinint | whatsappint | skypeint;
        int sms2 = instagramint | messengerint | isCallint;

        //格式：BE-01-22-FE-开关1(1Bytes)-开关2(1Bytes)
        byte[] data = new byte[]{(byte) 0xbe, 0x01, 0x22, (byte) 0xFE, (byte) sms1, (byte) sms2};
        return data;

    }


}
