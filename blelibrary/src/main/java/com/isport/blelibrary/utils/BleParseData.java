package com.isport.blelibrary.utils;

import android.content.Intent;

import com.isport.blelibrary.entry.DisplaySet;
import com.isport.blelibrary.observe.BleGetStateObservable;

import java.util.ArrayList;
import java.util.List;

public class BleParseData {

    /**
     * 解析display信息
     *
     * @param data
     */
    public static void parserDisplay(byte[] data) {
        if (data != null && data.length == 20) {
            List<Integer> tpDisp = new ArrayList<>();
            for (int i = 4; i < 20; i++) {
                tpDisp.add(data[i] & 0xff);
            }
            DisplaySet displaySet = new DisplaySet();
            for (int i = 0; i < tpDisp.size(); i++) {
                int val = tpDisp.get(i);
                if (val == 0x00) {
                    displaySet.setShowLogo(true);
                } else if (val == 0x03) {
                    displaySet.setShowCala(true);
                } else if (val == 0x04) {
                    displaySet.setShowDist(true);
                } else if (val == 0x05) {
                    displaySet.setShowSportTime(true);
                } else if (val == 0x06) {
                    displaySet.setShowProgress(true);
                } else if (val == 0x07) {
                    displaySet.setShowEmotion(true);
                } else if (val == 0x08) {
                    displaySet.setShowAlarm(true);
                } else if (val == 0x0A) {
                    displaySet.setShowSmsMissedCall(true);///数量
                } else if (val == 0x0B) {
                    displaySet.setShowIncomingReminder(true);
                } else if (val == 0x0D || val == 0x1D) {
                    displaySet.setShowMsgContentPush(true);
                } else if (val == 0x0F) {///倒计时
                    displaySet.setShowCountDown(true);
                }
            }

            BleGetStateObservable.getInstance().sucessGetDisplaySet(displaySet);
         /*   Intent intent = new Intent(Constants.ACTION_QUERY_DISPLAY);
            intent.putExtra(Constants.EXTRA_QUERY_DISPLAY, displaySet);
            LocalBroadcastManager.getInstance(context).sendBroadcast(intent);*/

        }
    }

}
