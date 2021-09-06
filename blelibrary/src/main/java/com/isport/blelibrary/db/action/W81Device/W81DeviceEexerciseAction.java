package com.isport.blelibrary.db.action.W81Device;

import android.text.TextUtils;

import com.google.gson.Gson;
import com.isport.blelibrary.db.CommonInterFace.WatchData;
import com.isport.blelibrary.db.action.BleAction;
import com.isport.blelibrary.db.parse.ParseData;
import com.isport.blelibrary.db.table.w811w814.W81DeviceExerciseData;
import com.isport.blelibrary.db.table.w811w814.W81DeviceExerciseHrData;
import com.isport.blelibrary.gen.W81DeviceExerciseDataDao;
import com.isport.blelibrary.gen.W81DeviceExerciseHrDataDao;
import com.isport.blelibrary.utils.Logger;
import com.isport.blelibrary.utils.ThreadPoolUtils;

import org.greenrobot.greendao.query.QueryBuilder;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;

public class W81DeviceEexerciseAction {

    private static final String TAG = "W81DeviceEexerciseActio";

    W81DeviceExerciseDataDao w81DeviceExerciseDataDao;
    W81DeviceExerciseHrDataDao w81DeviceExerciseHrDataDao;

    public W81DeviceEexerciseAction() {
        w81DeviceExerciseDataDao = BleAction.getsW81DeviceExerciseDataDao();
        w81DeviceExerciseHrDataDao = BleAction.getsW81DeviceExerciseHrDataDao();
    }


    /**
     * private String userId;
     * private String deviceId;
     * private String avgHr;
     * private String hrArray;//心率数据
     * private int timeInterval;
     * private long startMeasureTime;
     */
    public synchronized void saveExerciseHrData(String userId, String deviceId, List<Integer> listHr, int timeInterval, Long startMeasureTime) {

        List<Integer> tempList = new ArrayList<>();
        tempList.addAll(listHr);

        W81DeviceExerciseHrData w81DeviceExerciseHrData = findMeasureTimeEexerciseHrData(userId, deviceId, startMeasureTime);
        int avg = ParseData.calAvgHr(tempList);
        if (w81DeviceExerciseHrData == null) {
            w81DeviceExerciseHrData = new W81DeviceExerciseHrData();
            w81DeviceExerciseHrData.setStartMeasureTime(startMeasureTime);
            w81DeviceExerciseHrData.setDeviceId(deviceId);
            w81DeviceExerciseHrData.setUserId(userId);
        }
        w81DeviceExerciseHrData.setAvgHr(avg);
        w81DeviceExerciseHrData.setTimeInterval(timeInterval);

        Gson gson = new Gson();
        w81DeviceExerciseHrData.setHrArray(gson.toJson(tempList));
        saveEexerciseHrData(w81DeviceExerciseHrData);

    }


    public void updateEexerciseWristbandId(@NonNull String deviceId, @NonNull String userId, @NonNull String wridId) {
        if (TextUtils.isEmpty(deviceId) || TextUtils.isEmpty(userId) || TextUtils.isEmpty(wridId)) {
            return;
        }
        QueryBuilder<W81DeviceExerciseData> queryBuilder = BleAction.getDaoSession().queryBuilder(W81DeviceExerciseData.class);
        queryBuilder.where(W81DeviceExerciseDataDao.Properties.DeviceId.eq(deviceId), W81DeviceExerciseDataDao.Properties.UserId.eq(userId), W81DeviceExerciseDataDao.Properties.WristbandSportDetailId.eq(wridId));
        List<W81DeviceExerciseData> list = queryBuilder.list();
        W81DeviceExerciseData w81DeviceExerciseData;
        if (list != null && list.size() > 0) {
            for (int i = 0; i < list.size(); i++) {
                W81DeviceExerciseDataDao oxygenModeDao = BleAction.getsW81DeviceExerciseDataDao();
                w81DeviceExerciseData = list.get(i);
                w81DeviceExerciseData.setWristbandSportDetailId("1");
                oxygenModeDao.update(w81DeviceExerciseData);
            }
        }
    }

    public void saveEexerciseHrData(W81DeviceExerciseHrData hrData) {
        if (w81DeviceExerciseHrDataDao != null) {
            w81DeviceExerciseHrDataDao.insertOrReplace(hrData);
        }
        Logger.myLog("saveEexerciseHrData" + hrData);
    }


    public W81DeviceExerciseHrData findMeasureTimeEexerciseHrData(@NonNull String userId, @NonNull String deviceId, long starMeasureTime) {
        QueryBuilder<W81DeviceExerciseHrData> queryBuilder = BleAction.getDaoSession().queryBuilder(W81DeviceExerciseHrData.class);
        queryBuilder.where(W81DeviceExerciseHrDataDao.Properties.DeviceId.eq(deviceId), W81DeviceExerciseHrDataDao.Properties.UserId.eq(userId), W81DeviceExerciseHrDataDao.Properties.StartMeasureTime.eq(starMeasureTime)).offset(0).limit(1);
        List<W81DeviceExerciseHrData> list = queryBuilder.list();
        if (list != null && list.size() > 0) {
            W81DeviceExerciseHrData w81DeviceExerciseHrData = list.get(0);
            return w81DeviceExerciseHrData;
        } else {
            return null;
        }
    }


    public W81DeviceExerciseData findAndExerciseData(@NonNull String userId, @NonNull String deviceId, long starTime, long endTime) {


        if (TextUtils.isEmpty(userId) || TextUtils.isEmpty(deviceId)) {
            return null;
        }

        Logger.myLog("锻炼序号：findAndExerciseData:" + userId + ",baseDevice.deviceName:" + deviceId + ",startTime" + starTime + ",endTime:" + endTime);


        QueryBuilder<W81DeviceExerciseData> queryBuilder = BleAction.getDaoSession().queryBuilder(W81DeviceExerciseData.class);
        queryBuilder.where(W81DeviceExerciseDataDao.Properties.DeviceId.eq(deviceId), W81DeviceExerciseDataDao.Properties.UserId.eq(userId), W81DeviceExerciseDataDao.Properties.StartTimestamp.eq(starTime), W81DeviceExerciseDataDao.Properties.EndTimestamp.eq(endTime));

        List<W81DeviceExerciseData> list = queryBuilder.list();

        if (list != null && list.size() > 0) {
            return list.get(0);
            //  saveDeviceEexerciseHrData(userId, deviceId, starTime, endTime, w81DeviceExerciseHrData.getHrArray(), w81DeviceExerciseHrData.getAvgHr(), w81DeviceExerciseHrData.getTimeInterval());
        } else {
            return null;
        }
    }


    //greenDao除了eq()操作之外还有很多其他方法大大方便了我们日常查询操作比如：
    //- eq()：==
    //- noteq()：!=
    //- gt()： >
    //- lt()：<
    //- ge：>=
    //- le:<=
    //- like()：包含
    //- between：俩者之间
    //- in：在某个值内
    //- notIn：不在某个值内

    public W81DeviceExerciseHrData findAndSaveExerciseHrData(@NonNull String userId, @NonNull String deviceId, long starTime, long endTime) {


        if (TextUtils.isEmpty(userId) || TextUtils.isEmpty(deviceId)) {
            return null;
        }

        QueryBuilder<W81DeviceExerciseHrData> queryBuilder = BleAction.getDaoSession().queryBuilder(W81DeviceExerciseHrData.class);
        queryBuilder.where(W81DeviceExerciseHrDataDao.Properties.DeviceId.eq(deviceId), W81DeviceExerciseHrDataDao.Properties.UserId.eq(userId), W81DeviceExerciseHrDataDao.Properties.StartMeasureTime.ge(starTime), W81DeviceExerciseHrDataDao.Properties.StartMeasureTime.le(endTime)).offset(0).limit(1);
        List<W81DeviceExerciseHrData> list = queryBuilder.list();
        if (list != null && list.size() > 0) {
            return list.get(0);
            //  saveDeviceEexerciseHrData(userId, deviceId, starTime, endTime, w81DeviceExerciseHrData.getHrArray(), w81DeviceExerciseHrData.getAvgHr(), w81DeviceExerciseHrData.getTimeInterval());
        } else {
            return null;
        }
    }


    public List<W81DeviceExerciseData> getNoUpgradeDevicesaveDeviceExerciseData(@NonNull String deviceId, @NonNull String userId, @NonNull String WristbandSportDetailId) {

        if (TextUtils.isEmpty(deviceId) || TextUtils.isEmpty(userId) || TextUtils.isEmpty(WristbandSportDetailId)) {
            return null;
        }

        //w526添加测试数据
        //saveDefExerciseData(userId,deviceId,"0","2020-02-21",System.currentTimeMillis(),System.currentTimeMillis()+60*1000,60,2,200,78,23,System.currentTimeMillis(),89,"[78,89,66,90,55]",1,1);
        //saveDefExerciseData(userId,deviceId,"0","2020-02-21",System.currentTimeMillis(),System.currentTimeMillis()+60*1000,60,3,200,78,23,System.currentTimeMillis(),89,"[78,89,66,90,55]",1,1);
        //saveDefExerciseData(userId,deviceId,"0","2020-02-21",System.currentTimeMillis(),System.currentTimeMillis()+60*1000,60,4,200,78,23,System.currentTimeMillis(),89,"[78,89,66,90,55]",1,1);
        //saveDefExerciseData(userId,deviceId,"0","2020-02-21",System.currentTimeMillis(),System.currentTimeMillis()+60*1000,60,5,200,78,23,System.currentTimeMillis(),89,"[78,89,66,90,55]",1,1);
        //saveDefExerciseData(userId,deviceId,"0","2020-02-21",System.currentTimeMillis(),System.currentTimeMillis()+60*1000,60,6,200,78,23,System.currentTimeMillis(),89,"[78,89,66,90,55]",1,1);
        //saveDefExerciseData(userId,deviceId,"0","2020-02-21",System.currentTimeMillis(),System.currentTimeMillis()+60*1000,60,7,200,78,23,System.currentTimeMillis(),89,"[78,89,66,90,55]",1,1);
        // saveDefExerciseData(userId,deviceId,"0","2020-02-21",System.currentTimeMillis(),System.currentTimeMillis()+60*1000,60,8,200,78,23,System.currentTimeMillis(),89,"[78,89,66,90,55]",1,1);

        QueryBuilder<W81DeviceExerciseData> queryBuilder = BleAction.getDaoSession().queryBuilder(W81DeviceExerciseData.class);
        queryBuilder.where(W81DeviceExerciseDataDao.Properties.DeviceId.eq(deviceId), W81DeviceExerciseDataDao.Properties.UserId.eq(userId), W81DeviceExerciseDataDao.Properties.WristbandSportDetailId.eq(WristbandSportDetailId), W81DeviceExerciseDataDao.Properties.EndTimestamp.notEq(0)).distinct();

        return queryBuilder.list();
        /*if (queryBuilder.list().size() > 0) {
            W81DeviceExerciseData w81DeviceDetailData = queryBuilder.list().get(0);
            Logger.myLog("getW81DeviceDetialData" + w81DeviceDetailData);
        } else {
            return null;
        }
*/
    }


    public synchronized void saveW526DeviceExerciseData(String userId, String deviceId, String wristbandSportDetailId, String dateStr,
                                                        Long startTimestamp, Long endTimestamp, int vaildTimeLength,
                                                        int exerciseType, int totalSteps, int totalDistance, int totalCalories, int avg, String strHr) {

        W81DeviceExerciseData w81DeviceExerciseData = getDevicEexercise(userId, deviceId, startTimestamp);
        if (w81DeviceExerciseData == null) {
            w81DeviceExerciseData = new W81DeviceExerciseData();
            w81DeviceExerciseData.setUserId(userId);
            w81DeviceExerciseData.setDeviceId(deviceId);
        }
        w81DeviceExerciseData.setDateStr(dateStr);
        w81DeviceExerciseData.setWristbandSportDetailId(wristbandSportDetailId);
        w81DeviceExerciseData.setStartTimestamp(startTimestamp);
        w81DeviceExerciseData.setEndTimestamp(endTimestamp);
        w81DeviceExerciseData.setStartMeasureTime(startTimestamp);
        w81DeviceExerciseData.setVaildTimeLength(String.valueOf(vaildTimeLength));
        w81DeviceExerciseData.setExerciseType(String.valueOf(exerciseType));
        w81DeviceExerciseData.setTotalDistance(String.valueOf(totalDistance));
        w81DeviceExerciseData.setTotalSteps(String.valueOf(totalSteps));
        w81DeviceExerciseData.setTotalCalories(String.valueOf(totalCalories));
        if (avg >= 30) {
            w81DeviceExerciseData.setHasHR(WatchData.HAS_HR);
        } else {
            w81DeviceExerciseData.setHasHR(WatchData.NO_HR);
        }
        w81DeviceExerciseData.setTimeInterval(1);
        w81DeviceExerciseData.setHrArray(strHr);
        w81DeviceExerciseData.setAvgHr(avg + "");
        saveDefExerciseData(w81DeviceExerciseData);

    }


    public synchronized void saveDeviceExerciseData(String userId, String deviceId, String wristbandSportDetailId, String dateStr,
                                                    Long startTimestamp, Long endTimestamp, int vaildTimeLength,
                                                    int exerciseType, int totalSteps, int totalDistance, int totalCalories) {


        ThreadPoolUtils.getInstance().addTask(new Runnable() {
            @Override
            public void run() {
                W81DeviceExerciseData w81DeviceExerciseData = getDevicEexercise(userId, deviceId, startTimestamp);
                if (w81DeviceExerciseData == null) {
                    w81DeviceExerciseData = new W81DeviceExerciseData();
                    w81DeviceExerciseData.setUserId(userId);
                    w81DeviceExerciseData.setDeviceId(deviceId);
                }
                w81DeviceExerciseData.setDateStr(dateStr);
                w81DeviceExerciseData.setWristbandSportDetailId(wristbandSportDetailId);
                w81DeviceExerciseData.setStartTimestamp(startTimestamp);
                w81DeviceExerciseData.setEndTimestamp(endTimestamp);
                w81DeviceExerciseData.setStartMeasureTime(startTimestamp);
                w81DeviceExerciseData.setVaildTimeLength(String.valueOf(vaildTimeLength));
                if (exerciseType < 1 ) {
                    w81DeviceExerciseData.setExerciseType("1");
                } else {
                    w81DeviceExerciseData.setExerciseType(String.valueOf(exerciseType));
                }
                w81DeviceExerciseData.setTotalDistance(String.valueOf(totalDistance));
                w81DeviceExerciseData.setTotalSteps(String.valueOf(totalSteps));
                w81DeviceExerciseData.setTotalDistance(String.valueOf(totalDistance));
                w81DeviceExerciseData.setTotalCalories(String.valueOf(totalCalories));
                W81DeviceExerciseHrData hrData = findAndSaveExerciseHrData(userId, deviceId, startTimestamp, endTimestamp);
                if (hrData != null) {
                    w81DeviceExerciseData.setAvgHr(String.valueOf(hrData.getAvgHr()));
                    if (hrData.getAvgHr() >= 30) {
                        w81DeviceExerciseData.setHasHR(WatchData.HAS_HR);
                    } else {
                        w81DeviceExerciseData.setHasHR(WatchData.NO_HR);
                    }
                    w81DeviceExerciseData.setTimeInterval(hrData.getTimeInterval());
                    w81DeviceExerciseData.setHrArray(hrData.getHrArray());
                } else {
                    if (TextUtils.isEmpty(w81DeviceExerciseData.getHrArray())) {
                        w81DeviceExerciseData.setAvgHr("0");
                        w81DeviceExerciseData.setHasHR(WatchData.NO_HR);
                    }
                }
                saveDefExerciseData(w81DeviceExerciseData);
            }
        });


    }


    public void saveDeviceEexerciseHrData(String userId, String deviceId, long
            starTime, long endtime, String strHr, int avgHr, int timeInterval) {
        W81DeviceExerciseData w81DeviceExerciseData = getDevicEexercise(userId, deviceId, starTime, endtime);
        if (w81DeviceExerciseData != null) {
            w81DeviceExerciseData.setAvgHr(String.valueOf(avgHr));
            if (avgHr >= 30) {
                w81DeviceExerciseData.setHasHR(WatchData.HAS_HR);
            } else {
                w81DeviceExerciseData.setHasHR(WatchData.NO_HR);
            }
            w81DeviceExerciseData.setTimeInterval(timeInterval);
            w81DeviceExerciseData.setHrArray(strHr);
            saveDefExerciseData(w81DeviceExerciseData);
        } else {
            // saveDefExerciseData(userId, deviceId, wristbandSportDetailId, dateStr, startMeasureTime, 0l, 0, 0, 0, 0, 0, startMeasureTime, avgHr, gson.toJson(hrList), timeInterval, hasHr);
        }
    }

    public void saveDeviceEexerciseHrData(String userId, String deviceId, String
            wristbandSportDetailId, String dateStr,
                                          Long startMeasureTime, List<Integer> hrList, int timeInterval) {
        if (TextUtils.isEmpty(userId) || TextUtils.isEmpty(deviceId) || TextUtils.isEmpty(wristbandSportDetailId) || TextUtils.isEmpty(dateStr)) {
            return;
        }
        W81DeviceExerciseData w81DeviceExerciseData = getDevicEexercise(userId, deviceId, startMeasureTime);

        int hasHr = WatchData.HAS_HR;
        int avgHr = 0;
        Gson gson = new Gson();
        int avg = ParseData.calAvgHr(hrList);
        if (avg != 0) {
            hasHr = WatchData.HAS_HR;
        } else {
            hasHr = WatchData.NO_HR;
        }
        if (w81DeviceExerciseData != null) {
            w81DeviceExerciseData.setWristbandSportDetailId(wristbandSportDetailId);
            w81DeviceExerciseData.setDateStr(dateStr);
            w81DeviceExerciseData.setStartTimestamp(startMeasureTime);
            w81DeviceExerciseData.setStartMeasureTime(startMeasureTime);

            w81DeviceExerciseData.setAvgHr(String.valueOf(avgHr));
            w81DeviceExerciseData.setHasHR(hasHr);
            w81DeviceExerciseData.setTimeInterval(timeInterval);
            w81DeviceExerciseData.setHrArray(gson.toJson(hrList));
            saveDefExerciseData(w81DeviceExerciseData);
        } else {
            saveDefExerciseData(userId, deviceId, wristbandSportDetailId, dateStr, startMeasureTime, 0l, 0, 0, 0, 0, 0, startMeasureTime, avgHr, gson.toJson(hrList), timeInterval, hasHr);
        }
    }


    public void saveDefExerciseData(W81DeviceExerciseData exerciseData) {
        if (w81DeviceExerciseDataDao != null) {
            w81DeviceExerciseDataDao.insertOrReplace(exerciseData);
        }

        Logger.myLog(TAG,"--保存锻炼数据=saveDefExerciseData:" + new Gson().toJson(exerciseData));
    }

    /**
     * @param userId
     * @param deviceId
     * @param wristbandSportDetailId
     * @param dateStr
     * @param startTimestamp
     * @param endTimestamp
     * @param vaildTimeLength
     * @param exerciseType
     * @param totalSteps
     * @param totalDistance
     * @param totalCalories
     * @param startMeasureTime
     * @param avgHr
     * @param hrArray
     * @param timeInterval
     * @param hasHr
     */
    public void saveDefExerciseData(String userId, String deviceId, String
            wristbandSportDetailId, String dateStr, Long startTimestamp, Long endTimestamp,
                                    int vaildTimeLength, int exerciseType, int totalSteps, int totalDistance,
                                    int totalCalories, Long startMeasureTime, int avgHr, String hrArray, int timeInterval,
                                    int hasHr) {
        W81DeviceExerciseData w81DeviceExerciseData = new W81DeviceExerciseData();
        w81DeviceExerciseData.setUserId(userId);
        w81DeviceExerciseData.setDeviceId(deviceId);
        w81DeviceExerciseData.setWristbandSportDetailId(wristbandSportDetailId);
        w81DeviceExerciseData.setDateStr(dateStr);
        w81DeviceExerciseData.setStartTimestamp(startTimestamp);
        w81DeviceExerciseData.setEndTimestamp(endTimestamp);
        w81DeviceExerciseData.setVaildTimeLength(String.valueOf(vaildTimeLength));
        w81DeviceExerciseData.setExerciseType(String.valueOf(exerciseType));
        w81DeviceExerciseData.setTotalSteps(String.valueOf(totalSteps));
        w81DeviceExerciseData.setTotalCalories(String.valueOf(totalCalories));
        w81DeviceExerciseData.setTotalDistance(String.valueOf(totalDistance));
        w81DeviceExerciseData.setStartMeasureTime(startMeasureTime);
        w81DeviceExerciseData.setHrArray(hrArray);
        w81DeviceExerciseData.setAvgHr(String.valueOf(avgHr));
        w81DeviceExerciseData.setTimeInterval(timeInterval);
        w81DeviceExerciseData.setHasHR(hasHr);
        saveDefExerciseData(w81DeviceExerciseData);


    }

    public W81DeviceExerciseData getDevicEexercise(String userId, String deviceId, Long
            startTimestamp) {

        if (TextUtils.isEmpty(userId) || TextUtils.isEmpty(deviceId)) {
            return null;
        }

        QueryBuilder<W81DeviceExerciseData> queryBuilder = BleAction.getDaoSession().queryBuilder(W81DeviceExerciseData.class);
        queryBuilder.where(W81DeviceExerciseDataDao.Properties.DeviceId.eq(deviceId), W81DeviceExerciseDataDao.Properties.UserId.eq(userId), W81DeviceExerciseDataDao.Properties.StartTimestamp.eq(startTimestamp));
        if (queryBuilder.list().size() > 0) {
            W81DeviceExerciseData w81DeviceDetailData = queryBuilder.list().get(0);
            Logger.myLog("getW81DeviceDetialData" + w81DeviceDetailData);
            return w81DeviceDetailData;
        } else {
            Logger.myLog("getW81DeviceDetialData null deviceId:" + deviceId + "UserId" + userId + "startTimestamp:" + startTimestamp);
            return null;
        }
    }

    public W81DeviceExerciseData getDevicEexercise(String userId, String deviceId, Long
            startTimestamp, Long endTimesstamp) {
        if (TextUtils.isEmpty(userId) || TextUtils.isEmpty(deviceId)) {
            return null;
        }
        QueryBuilder<W81DeviceExerciseData> queryBuilder = BleAction.getDaoSession().queryBuilder(W81DeviceExerciseData.class);
        queryBuilder.where(W81DeviceExerciseDataDao.Properties.DeviceId.eq(deviceId), W81DeviceExerciseDataDao.Properties.UserId.eq(userId), W81DeviceExerciseDataDao.Properties.StartTimestamp.eq(startTimestamp), W81DeviceExerciseDataDao.Properties.EndTimestamp.eq(endTimesstamp));
        if (queryBuilder.list().size() > 0) {
            W81DeviceExerciseData w81DeviceDetailData = queryBuilder.list().get(0);
            Logger.myLog("getW81DeviceDetialData" + w81DeviceDetailData);
            return w81DeviceDetailData;
        } else {
            return null;
        }
    }
}
