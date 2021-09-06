package com.isport.blelibrary.utils;

import android.content.Context;
import android.view.KeyEvent;

/**
 * @author Created by Marcos Cheng on 2016/9/2.
 */
public class HandlerCommand {


    public static boolean handleEndPhoneController(Context context, byte[] data) {
        //DE+08+02+FE+01+ED DE 08 02 FE 01 ED
        if (data == null || data.length < 6)
            return false;
        if ((data[0] & 0xff) == 0xde && (data[1] & 0xff) == 0x08 && (data[2] & 0xff) == 0x02 &&
                (data[3] & 0xff) == 0xFE && (data[4] & 0xff) == 0x01 && (data[5] & 0xff) == 0xed) {
            CmdUtil.sendEndCall(context, new KeyEvent(0, KeyEvent.KEYCODE_HEADSETHOOK));
            return true;
        }
        return false;
    }

    /**
     * @param context
     * @param data
     *
     * 音乐播放(仅安卓手机)：
     * 安卓手机接收命令（CMD_MUSIC_PLAYING）：
     *     01 13 xx[01,08]
     * 01 播放
     * 02 暂停
     * 03 上一首 (需要推送1F命令)
     * 04 下一首 (需要推送1F命令)
     * 05 增加音量
     * 06 减少音量
     * 07 开启音乐协议 (需要推送1F命令)
     * 08 关闭音乐协议
     * 安卓手机发送命令（CMD_GET_MUSIC_MESSAGE）：
     *     01 1F xx[01,03]
     * 01 获取歌唱家+音乐名 例：(谭咏麟 - 再见亦是泪)
     * 02 获取音乐总时间 例：250.989秒  (250.989)
     * 03 获取音乐播放时间 例：62.903秒 (1,1.0,62.903)
     * 注意： 编码和苹果系统统一，使用UTF-8编码，
     *        歌唱家 - 音乐名之间必须使用" - "分隔
     *        音乐数据包最大长度为50bytes(单包发送)
     *
     */
    public static boolean handleMusicController(Context context, byte[] data) {
        if (data == null || data.length < 6)
            return false;
        if ((data[0] & 0xff) == 0x01 && (data[1] & 0xff) == 0x13 && (data[2] & 0xff) == 0x01) { //stringBuilder.toString().trim().startsWith("DE 06 08 FE 01 ED")) {
            if (Constants.MUSIC_DEFAULT) {
                CmdUtil.sendMusicKey(context, new KeyEvent(0, KeyEvent.KEYCODE_MEDIA_PLAY));
                CmdUtil.sendMusicKey(context, new KeyEvent(1, KeyEvent.KEYCODE_MEDIA_PLAY));
            } else {
            }
            return true;
        }
        if ((data[0] & 0xff) == 0x01 && (data[1] & 0xff) == 0x13 && (data[2] & 0xff) == 0x02) {//stringBuilder.toString().trim().startsWith("DE 06 08 FE 00 ED")) {
            if (Constants.MUSIC_DEFAULT) {
                CmdUtil.sendMusicKey(context, new KeyEvent(0, KeyEvent.KEYCODE_MEDIA_STOP));
                CmdUtil.sendMusicKey(context, new KeyEvent(1, KeyEvent.KEYCODE_MEDIA_STOP));
            } else {
            }
            return true;
        }
        if ((data[0] & 0xff) == 0x01 && (data[1] & 0xff) == 0x13 && (data[2] & 0xff) == 0x03){//stringBuilder.toString().trim().startsWith("DE 06 08 FE 02 ED")) {
            if (Constants.MUSIC_DEFAULT) {
                CmdUtil.sendMusicKey(context, new KeyEvent(0, KeyEvent.KEYCODE_MEDIA_PREVIOUS));
                CmdUtil.sendMusicKey(context, new KeyEvent(1, KeyEvent.KEYCODE_MEDIA_PREVIOUS));
            } else {
            }
            return true;
        }
        if ((data[0] & 0xff) == 0xde && (data[1] & 0xff) == 0x06 && (data[2] & 0xff) == 0x08 &&
                (data[3] & 0xff) == 0xfe && (data[4] & 0xff) == 0x03 && (data[5] & 0xff) == 0xed) {//stringBuilder.toString().trim().startsWith("DE 06 08 FE 03 ED")) {
            if (Constants.MUSIC_DEFAULT) {
                CmdUtil.sendMusicKey(context, new KeyEvent(0, KeyEvent.KEYCODE_MEDIA_NEXT));
                CmdUtil.sendMusicKey(context, new KeyEvent(1, KeyEvent.KEYCODE_MEDIA_NEXT));
            } else {
            }
            return true;
        }
        if ((data[0] & 0xff) == 0xde && (data[1] & 0xff) == 0x06 && (data[2] & 0xff) == 0x08 &&
                (data[3] & 0xff) == 0xfe && (data[4] & 0xff) == 0xf0 && (data[5] & 0xff) == 0xed) {//stringBuilder.toString().trim().startsWith("DE 06 08 FE F0 ED")) {
            if (Constants.MUSIC_DEFAULT) {
                CmdUtil.sendMusicKey(context, new KeyEvent(0, KeyEvent.KEYCODE_MEDIA_PAUSE));
                CmdUtil.sendMusicKey(context, new KeyEvent(1, KeyEvent.KEYCODE_MEDIA_PAUSE));
            } else {
            }
            return true;
        }
        return false;
    }


    public static boolean handleTakePhoto(byte[] data) {
        if (data == null || data.length < 6)
            return false;
        if ((data[0] & 0xff) == 0xde && (data[1] & 0xff) == 0x06 && (data[2] & 0xff) == 0x07 &&
                (data[3] & 0xff) == 0xfe && (data[4] & 0xff) == 0x01 && (data[5] & 0xff) == 0xed) {//if(!stringBuilder.toString().startsWith("DE 06 07 FE 01 ED")){
            return true;
        }
        return false;
    }
}
