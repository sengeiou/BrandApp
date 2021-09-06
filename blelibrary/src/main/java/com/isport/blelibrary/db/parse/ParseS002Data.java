package com.isport.blelibrary.db.parse;

import com.google.gson.Gson;
import com.isport.blelibrary.db.action.s002.S002_DetailDataModelAction;
import com.isport.blelibrary.db.table.s002.S002_Detail_Data;
import com.isport.blelibrary.observe.RopeSyncDataObservable;
import com.isport.blelibrary.utils.Logger;
import com.isport.blelibrary.utils.ThreadPoolUtils;
import com.isport.blelibrary.utils.Utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class ParseS002Data {


    static ArrayList<S002_Detail_Data> ropeList = new ArrayList<>();
    static ArrayList<Stumble> stumbles = new ArrayList<>();
    static ArrayList<Integer> hrlist = new ArrayList<>();
    static ArrayList<Integer> frequencyArray = new ArrayList<>();

    public static void parsumdata(byte[] sums, String deviceId, String userId) {
        // Logger.myLog("ropeDetail=");
        ropeList.clear();
        if (sums.length < 32) {
            return;
        }
        /*final StringBuilder stringBuilder = new StringBuilder(sums.length);
        for (byte byteChar : sums) {
            stringBuilder.append(String.format("%02X ", byteChar));
        }
        Logger.myLog("ropeDetail=" + stringBuilder.toString());*/

        int len = sums.length % 32;
        for (int i = 0, j = 0; i < sums.length - len; i += 32, j++) {
            byte[] data = new byte[32];
            System.arraycopy(sums, j * 32, data, 0, 32);
            signSume(data, deviceId, userId);
        }
    }

    public static void signSume(byte[] data, String deviceId, String userId) {

       /* final StringBuilder stringBuilder = new StringBuilder(data.length);
        for (byte byteChar : data) {
            stringBuilder.append(String.format("%02X ", byteChar));
        }
        Logger.myLog("ropeDetail=" + stringBuilder.toString());
        Log.e("ropeDetail", "number=" + (Utils.byte2Int(data[2]) << 16) + (Utils.byte2Int(data[1]) << 8) + Utils
                .byte2Int(data[0]));*/
        S002_Detail_Data ropeDetail = new S002_Detail_Data();
        ropeDetail.setDeviceId(deviceId);
        ropeDetail.setUserId(userId);
        ropeDetail.setStumbleNum((Utils.byte2Int(data[2]) << 16) + (Utils.byte2Int(data[1]) << 8) + Utils
                .byte2Int(data[0]));
        ropeDetail.setSkippingDuration((Utils.byte2Int(data[5]) << 16) + (Utils.byte2Int(data[4]) << 8) + Utils
                .byte2Int(data[3]));
        Logger.myLog("ropeDetail=skippingDuration=" + (Utils.byte2Int(data[5]) << 16) + (Utils.byte2Int(data[4]) << 8) + Utils
                .byte2Int(data[3]));
        ropeDetail.setExerciseType(Utils.byte2Int(data[6]));
        Logger.myLog("ropeDetail=exerciseType=" + Utils.byte2Int(data[6]));
        ropeDetail.setSkippingNum((Utils.byte2Int(data[9]) << 16) + (Utils.byte2Int(data[8]) << 8) + Utils
                .byte2Int(data[7]));
        Logger.myLog("ropeDetail=skippingNum=" + (Utils.byte2Int(data[9]) << 16) + (Utils.byte2Int(data[8]) << 8) + Utils
                .byte2Int(data[7]));
        ropeDetail.setAverageHeartRate(Utils.byte2Int(data[10]));
        Logger.myLog("ropeDetail=averageHeartRate=" + (Utils.byte2Int(data[9]) << 16) + (Utils.byte2Int(data[8]) << 8) + Utils
                .byte2Int(data[7]));
        // ropeDetail.startTime = (Utils.byte2Int(data[11]) + 2000) + "-" + String.format("%02d", Utils.byte2Int(data[12])) + "-" + String.format("%02d", Utils.byte2Int(data[13]));
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, Utils.byte2Int(data[11]) + 2000);
        calendar.set(Calendar.MONTH, (Utils.byte2Int(data[12]) - 1));
        calendar.set(Calendar.DAY_OF_MONTH, Utils.byte2Int(data[13]));
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);


        ropeDetail.setEndTime("");
       /* Logger.myLog("ropeDetail=year=" + ((Utils.byte2Int(data[11]) + 2000)));
        Logger.myLog("ropeDetail=Month=" + ((Utils.byte2Int(data[12]) - 1)));
        Logger.myLog("ropeDetail=Day=" + Utils.byte2Int(data[13]));
*/
        long sec = (Utils.byte2Int(data[16]) << 16) + (Utils.byte2Int(data[15]) << 8) + Utils
                .byte2Int(data[14]);
        // Logger.myLog("ropeDetail=sec=" + sec);
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        long currentTime = calendar.getTimeInMillis() + sec * 1000;
        long endTime = currentTime + ropeDetail.getSkippingDuration() * 1000;

        ropeDetail.setStartTime(dateFormat.format(new Date(currentTime)));
        ropeDetail.setEndTime(dateFormat.format(new Date(endTime)));
        // Logger.myLog("ropeDetail=setStartTime=" + ropeDetail.getStartTime() + "setEndTime=" + ropeDetail.getEndTime());
        ropeDetail.setTimestamp(currentTime);
        int cal = (Utils.byte2Int(data[20]) << 24) + (Utils.byte2Int(data[19]) << 16) + (Utils.byte2Int(data[18]) << 8) + Utils
                .byte2Int(data[17]);
        ropeDetail.setTotalCalories(cal);
        ropeDetail.setAverageFrequency((Utils.byte2Int(data[22]) << 8) + Utils
                .byte2Int(data[21]));
        ropeDetail.setMaxFrequency((Utils.byte2Int(data[24]) << 8) + Utils
                .byte2Int(data[23]));
        ropeDetail.setSingleMaxSkippingNum((Utils.byte2Int(data[27]) << 16) + (Utils.byte2Int(data[26]) << 8) + Utils
                .byte2Int(data[25]));
        ropeDetail.setChallengeType(Utils.byte2Int(data[28]));

        if (ropeDetail.getExerciseType() == 255) {
            return;
        }
        ropeList.add(ropeDetail);


    }


    public static void parseStumbleNumData(byte[] data) {
        stumbles.clear();
        if (ropeList.size() == 0) {
            return;
        }
        /*final StringBuilder stringBuilder = new StringBuilder(data.length);
        for (byte byteChar : data) {
            stringBuilder.append(String.format("%02X ", byteChar));
        }
        Logger.myLog("StumbleNumData=" + stringBuilder.toString());*/
        for (int i = 0; i < data.length - 6; i += 6) {
            Stumble stumble = new Stumble();
           /* int count = ;
            int time = ;*/
            stumble.setSkippingNum((Utils.byte2Int(data[i + 2]) << 16) + (Utils.byte2Int(data[i + 1]) << 8) + Utils
                    .byte2Int(data[i]));
            stumble.setSkippingDuration((Utils.byte2Int(data[i + 5]) << 16) + (Utils.byte2Int(data[i + 4]) << 8) + Utils
                    .byte2Int(data[i + 3]));
            //stumble.setSkippingNum(data[i + 2] << 16 + data[i + 1] << 8 + data[i]);
            // stumble.setSkippingDuration(data[i + 5] << 16 + data[i + 4] << 8 + data[i + 3]);
            stumbles.add(stumble);
            Logger.myLog("parseStumbleNumData" + stumble);
        }
        S002_Detail_Data ropeDetail;
        Gson gson = new Gson();
        for (int i = 0; i < ropeList.size(); i++) {
            ropeDetail = ropeList.get(i);
            ArrayList<Stumble> stumblesSon = new ArrayList<>();
            int count = ropeDetail.getStumbleNum();
            int index = 0;
            while (count > 0) {
                if (stumbles.size() == 0) {
                    break;
                }
                stumbles.get(0).setId(index);
                stumblesSon.add(stumbles.get(0));
                stumbles.remove(0);
                count--;
                index++;
            }
            ropeDetail.setStumbleArray(gson.toJson(stumblesSon));
            Logger.myLog("parseStumbleNumData son" + gson.toJson(stumblesSon));
        }

    }


    public static void parseHrDetaiData(byte[] data) {
        hrlist.clear();
        frequencyArray.clear();
        if (ropeList.size() == 0) {
            return;
        }
        Gson gson = new Gson();

        /*final StringBuilder stringBuilder = new StringBuilder(data.length);
        for (byte byteChar : data) {
            stringBuilder.append(String.format("%02X ", byteChar));
        }
        Logger.myLog("parseHrDetaiData=" + stringBuilder.toString());*/

        for (int i = 0; i < data.length - 3; i += 3) {
            hrlist.add(Utils.byte2Int(data[i + 2]));
            int frequen = (Utils.byte2Int(data[i + 1]) << 8) + Utils.byte2Int(data[i]);
            frequencyArray.add(frequen);
        }
        Logger.myLog("parseHrDetaiData hrlist=" + hrlist);
        Logger.myLog("parseHrDetaiData frequencyArray=" + frequencyArray);

        S002_Detail_Data ropeDetail;
        for (int i = 0; i < ropeList.size(); i++) {
            ropeDetail = ropeList.get(i);
            ArrayList<Integer> hrlistSon = new ArrayList<>();
            ArrayList<Integer> frequencyArraySon = new ArrayList<>();
            int count = ropeDetail.getSkippingDuration() / 2;
            while (count > 0) {
                if (hrlist.size() == 0) {
                    break;
                }
                hrlistSon.add(hrlist.get(0));
                hrlist.remove(0);
                frequencyArraySon.add(frequencyArray.get(0));
                frequencyArray.remove(0);
                count--;
            }
            ropeDetail.setHeartRateDetailArray(gson.toJson(hrlistSon));
            ropeDetail.setFrequencyArray(gson.toJson(frequencyArraySon));
            ropeDetail.setUpgradeState(0);
            Logger.myLog("parseHrDetaiData" + ropeDetail);
        }

        saveData();
    }


    public static void saveData() {
        ThreadPoolUtils.getInstance().addTask(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < ropeList.size(); i++) {
                    ropeList.get(i).setStumbleNum(ropeList.get(i).getStumbleNum() - 1);
                    S002_DetailDataModelAction.saveOrUpdateRopeData(ropeList.get(i));
                }
                RopeSyncDataObservable.getInstance().successRealData(true);
            }
        });
    }


}
