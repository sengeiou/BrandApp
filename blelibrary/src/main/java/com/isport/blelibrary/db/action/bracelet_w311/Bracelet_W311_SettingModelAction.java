package com.isport.blelibrary.db.action.bracelet_w311;

import android.text.TextUtils;

import com.isport.blelibrary.db.action.BleAction;
import com.isport.blelibrary.db.table.bracelet_w311.Bracelet_W311_24H_hr_SettingModel;
import com.isport.blelibrary.db.table.bracelet_w311.Bracelet_W311_DisplayModel;
import com.isport.blelibrary.db.table.bracelet_w311.Bracelet_W311_ThridMessageModel;
import com.isport.blelibrary.db.table.bracelet_w311.Bracelet_W311_WearModel;
import com.isport.blelibrary.db.table.w811w814.DeviceTimeFormat;
import com.isport.blelibrary.db.table.w811w814.FaceWatchMode;
import com.isport.blelibrary.gen.Bracelet_W311_24H_hr_SettingModelDao;
import com.isport.blelibrary.gen.Bracelet_W311_DisplayModelDao;
import com.isport.blelibrary.gen.Bracelet_W311_ThridMessageModelDao;
import com.isport.blelibrary.gen.Bracelet_W311_WearModelDao;
import com.isport.blelibrary.gen.DeviceTimeFormatDao;
import com.isport.blelibrary.gen.FaceWatchModeDao;
import com.isport.blelibrary.utils.Logger;

import org.greenrobot.greendao.query.QueryBuilder;

public class Bracelet_W311_SettingModelAction {

    /**
     * 查询deviceId的显示项
     */
    public static Bracelet_W311_DisplayModel findWatch_W311_DisplayModelByDeviceId(String deviceId) {
        if(TextUtils.isEmpty(deviceId)){
            return null;
        }
        QueryBuilder<Bracelet_W311_DisplayModel> queryBuilder = BleAction.getDaoSession().queryBuilder(Bracelet_W311_DisplayModel.class);
        queryBuilder.where(Bracelet_W311_DisplayModelDao.Properties.DeviceId.eq(deviceId));
        if (queryBuilder.list().size() > 0) {
            Bracelet_W311_DisplayModel bracelet_w311_displayModel = queryBuilder.list().get(0);
            return bracelet_w311_displayModel;
        } else {
            return null;
        }
    }


    public static long saveOrUpdateBraceletDisplay(final Bracelet_W311_DisplayModel model) {
        long id;
        Bracelet_W311_DisplayModelDao bracelet_w311_displayModelDao = BleAction.getsBracelet_w311_displayModelDao();
        Bracelet_W311_DisplayModel bracelet_w311_displayModel = findWatch_W311_DisplayModelByDeviceId(model.getDeviceId());
        if (bracelet_w311_displayModel != null) {
            bracelet_w311_displayModel.setUserId(model.getUserId());
            bracelet_w311_displayModel.setIsShowAlarm(model.getIsShowAlarm());
            bracelet_w311_displayModel.setIsShowDis(model.getIsShowDis());
            bracelet_w311_displayModel.setIsShowCal(model.getIsShowCal());
            bracelet_w311_displayModel.setIsShowComplete(model.getIsShowComplete());
            bracelet_w311_displayModel.setIsShowPresent(model.getIsShowPresent());
            bracelet_w311_displayModel.setIsShowSportTime(model.getIsShowSportTime());
            id = bracelet_w311_displayModelDao.insertOrReplace(bracelet_w311_displayModel);

        } else {
            //model.setId(0l);
            id = bracelet_w311_displayModelDao.insertOrReplace(model);
        }
        return id;
    }


    /*
     * 查询deviceId需要提示的第三方的
     */
    public static Bracelet_W311_ThridMessageModel findBracelet_W311_ThridMessage(String deviceId, String userId) {
        if(TextUtils.isEmpty(deviceId) || TextUtils.isEmpty(userId)){
            return null;
        }
        QueryBuilder<Bracelet_W311_ThridMessageModel> queryBuilder = BleAction.getDaoSession().queryBuilder(Bracelet_W311_ThridMessageModel.class);
        queryBuilder.where(Bracelet_W311_ThridMessageModelDao.Properties.DeviceId.eq(deviceId), Bracelet_W311_ThridMessageModelDao.Properties.UserId.eq(userId));
        if (queryBuilder.list().size() > 0) {
            Bracelet_W311_ThridMessageModel bracelet_w311_displayModel = queryBuilder.list().get(0);
            return bracelet_w311_displayModel;
        } else {
            return null;
        }
    }


    public static long saveOrUpdateBraceletThridMessage(final Bracelet_W311_ThridMessageModel model) {
        long id;
        Bracelet_W311_ThridMessageModelDao bracelet_w311_thridMessageModelDao = BleAction.getsBracelet_w311_thridMessageModelDao();
        Bracelet_W311_ThridMessageModel bracelet_w311_thridMessage = findBracelet_W311_ThridMessage(model.getDeviceId(), model.getUserId());
        if (bracelet_w311_thridMessage != null) {
            bracelet_w311_thridMessage.setUserId(model.getUserId());
            bracelet_w311_thridMessage.setIsWechat(model.getIsWechat());
            bracelet_w311_thridMessage.setIsQQ(model.getIsQQ());
            bracelet_w311_thridMessage.setIsFaceBook(model.getIsFaceBook());
            bracelet_w311_thridMessage.setIsSkype(model.getIsSkype());
            bracelet_w311_thridMessage.setIsTwitter(model.getIsTwitter());
            bracelet_w311_thridMessage.setIsWhatApp(model.getIsWhatApp());
            id = bracelet_w311_thridMessageModelDao.insertOrReplace(bracelet_w311_thridMessage);
        } else {
            //model.setId(0l);
            id = bracelet_w311_thridMessageModelDao.insertOrReplace(model);
        }
        return id;
    }

    //查询佩戴方式
    public static Bracelet_W311_WearModel findBracelet_W311_WearModel(String deviceId, String userid) {

        if(TextUtils.isEmpty(deviceId) || TextUtils.isEmpty(userid)){
            return null;
        }
        QueryBuilder<Bracelet_W311_WearModel> queryBuilder = BleAction.getDaoSession().queryBuilder(Bracelet_W311_WearModel.class);
        queryBuilder.where(Bracelet_W311_WearModelDao.Properties.DeviceId.eq(deviceId), Bracelet_W311_WearModelDao.Properties.UserId.eq(userid));
        if (queryBuilder.list().size() > 0) {
            Bracelet_W311_WearModel bracelet_w311_wearModel = queryBuilder.list().get(0);
            return bracelet_w311_wearModel;
        } else {
            return null;
        }
    }

    //查询是否开启24小时监测心率
    public static Bracelet_W311_24H_hr_SettingModel findBracelet_w311_24h_hrModel(String deviceId, String userId) {
        if(TextUtils.isEmpty(deviceId) || TextUtils.isEmpty(userId)){
            return null;
        }
        Bracelet_W311_24H_hr_SettingModel bracelet_w311_wearModel;
        if (TextUtils.isEmpty(deviceId)) {
            bracelet_w311_wearModel = new Bracelet_W311_24H_hr_SettingModel();
            bracelet_w311_wearModel.setHeartRateSwitch(false);
            return bracelet_w311_wearModel;
        }
        QueryBuilder<Bracelet_W311_24H_hr_SettingModel> queryBuilder = BleAction.getDaoSession().queryBuilder(Bracelet_W311_24H_hr_SettingModel.class);
        queryBuilder.where(Bracelet_W311_24H_hr_SettingModelDao.Properties.DeviceId.eq(deviceId), Bracelet_W311_24H_hr_SettingModelDao.Properties.UserId.eq(userId));

        if (queryBuilder.list().size() > 0) {
            bracelet_w311_wearModel = queryBuilder.list().get(0);
            return bracelet_w311_wearModel;
        } else {
            bracelet_w311_wearModel = new Bracelet_W311_24H_hr_SettingModel();
            bracelet_w311_wearModel.setHeartRateSwitch(false);
            return bracelet_w311_wearModel;
        }
    }


    public static long saveOrUpdateBraceletWearModel(final Bracelet_W311_WearModel model) {
        long id;
        Bracelet_W311_WearModelDao bracelet_w311_wearModelDao = BleAction.getBracelet_w311_wearModelDao();
        Bracelet_W311_WearModel bracelet_w311_wearModel = findBracelet_W311_WearModel(model.getDeviceId(), model.getUserId());
        if (bracelet_w311_wearModel != null) {
            bracelet_w311_wearModel.setUserId(model.getUserId());
            bracelet_w311_wearModel.setDeviceId(model.getDeviceId());
            bracelet_w311_wearModel.setIsLeft(model.getIsLeft());
            id = bracelet_w311_wearModelDao.insertOrReplace(bracelet_w311_wearModel);
        } else {
            //model.setId(0l);
            id = bracelet_w311_wearModelDao.insertOrReplace(model);
        }
        return id;
    }

    public static long saveOrUpateBracelet24HHrSetting(final Bracelet_W311_24H_hr_SettingModel model) {
        long id;
        Logger.myLog("saveOrUpateBracelet24HHrSetting:" + model.toString());
        Bracelet_W311_24H_hr_SettingModelDao bracelet_w311_24H_hr_settingModelDao = BleAction.getsBracelet_w311_24H_hr_settingModelDao();
        Bracelet_W311_24H_hr_SettingModel bracelet_w311_24H_hr_settingModel = findBracelet_w311_24h_hrModel(model.getDeviceId(), model.getUserId());
        if (bracelet_w311_24H_hr_settingModel != null) {
            bracelet_w311_24H_hr_settingModel.setUserId(model.getUserId());
            bracelet_w311_24H_hr_settingModel.setDeviceId(model.getDeviceId());
            bracelet_w311_24H_hr_settingModel.setHeartRateSwitch(model.getHeartRateSwitch());
            id = bracelet_w311_24H_hr_settingModelDao.insertOrReplace(bracelet_w311_24H_hr_settingModel);
        } else {
            //model.setId(0l);
            id = bracelet_w311_24H_hr_settingModelDao.insertOrReplace(model);
        }
        return id;
    }

    public static long saveOrUpdateWatchFaces(String devcieId, String userId, int faceMode) {
        long id;
        FaceWatchModeDao faceWatchModeDao = BleAction.getsFaceWatchModeDao();
        FaceWatchMode faceWatchMode = findWatchFaces(userId, devcieId);
        if (faceWatchMode != null) {
            faceWatchMode.setFaceWatchMode(faceMode);
            id = faceWatchModeDao.insertOrReplace(faceWatchMode);
        } else {
            //model.setId(0l);
            FaceWatchMode model = new FaceWatchMode();
            model.setFaceWatchMode(faceMode);
            model.setUserId(userId);
            model.setDeviceId(devcieId);
            id = faceWatchModeDao.insertOrReplace(model);
        }
        Logger.myLog("getWatchFace:saveOrUpdateWatchFaces" + faceWatchMode + "id:" + id);
        return id;
    }

    //表盘设置
    public static FaceWatchMode findWatchFaces(String userId, String deviceId) {
        Logger.myLog("getWatchFace:findWatchFaces" + deviceId);
        QueryBuilder<FaceWatchMode> queryBuilder = BleAction.getDaoSession().queryBuilder(FaceWatchMode.class);
        queryBuilder.where(FaceWatchModeDao.Properties.DeviceId.eq(deviceId), FaceWatchModeDao.Properties.UserId.eq(userId));
        if (queryBuilder.list().size() > 0) {
            FaceWatchMode bracelet_w311_wearModel = queryBuilder.list().get(0);
            return bracelet_w311_wearModel;
        } else {
            return null;
        }
    }

    public static long saveOrUpdateDeviceTimeFormate(String devcieId, String userId, int formart) {
        if (TextUtils.isEmpty(devcieId) || TextUtils.isEmpty(userId)) {
            return 0;
        }
        long id = 0;
        DeviceTimeFormatDao faceWatchModeDao = BleAction.getsDeviceTimeFormatDao();
        DeviceTimeFormat deviceTimeFormat = getDeviceFormatBean(devcieId, userId);
        Logger.myLog("onTimeSystem: isaveOrUpdateDeviceTimeFormate" + deviceTimeFormat);
        if (deviceTimeFormat != null) {
            deviceTimeFormat.setTimeformate(formart);
            id = faceWatchModeDao.insertOrReplace(deviceTimeFormat);
        } else {
            //model.setId(0l);
            deviceTimeFormat = new DeviceTimeFormat();
            deviceTimeFormat.setDeviceId(devcieId);
            deviceTimeFormat.setUserId(userId);
            deviceTimeFormat.setTimeformate(formart);

            id = faceWatchModeDao.insertOrReplace(deviceTimeFormat);
        }
        Logger.myLog("getWatchFace:saveOrUpdateWatchFaces" + deviceTimeFormat + "id:" + id);
        return id;
    }

    public static DeviceTimeFormat getDeviceFormatBean(String devcieId, String userId) {
        if (TextUtils.isEmpty(devcieId) || TextUtils.isEmpty(userId)) {
            return null;
        }
        QueryBuilder<DeviceTimeFormat> queryBuilder = BleAction.getDaoSession().queryBuilder(DeviceTimeFormat.class);
        queryBuilder.where(DeviceTimeFormatDao.Properties.DeviceId.eq(devcieId), DeviceTimeFormatDao.Properties.UserId.eq(userId));
        if (queryBuilder.list().size() > 0) {
            DeviceTimeFormat deviceTimeFormat = queryBuilder.list().get(0);
            return deviceTimeFormat;
        } else {
            return null;
        }
    }

}
