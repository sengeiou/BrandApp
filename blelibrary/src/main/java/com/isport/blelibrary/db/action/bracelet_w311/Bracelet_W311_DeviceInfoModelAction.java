package com.isport.blelibrary.db.action.bracelet_w311;

import android.text.TextUtils;

import com.isport.blelibrary.db.action.BleAction;
import com.isport.blelibrary.db.table.bracelet_w311.Bracelet_W311_DeviceInfoModel;
import com.isport.blelibrary.entry.DeviceInfo;
import com.isport.blelibrary.gen.Bracelet_W311_DeviceInfoModelDao;
import com.isport.blelibrary.utils.Logger;

import org.greenrobot.greendao.query.QueryBuilder;

public class Bracelet_W311_DeviceInfoModelAction {


    public static Bracelet_W311_DeviceInfoModel findBraceletW311DeviceInfo(String userId, String deviceId) {
        if(TextUtils.isEmpty(deviceId) || TextUtils.isEmpty(userId)){
            return null;
        }
        QueryBuilder<Bracelet_W311_DeviceInfoModel> queryBuilder = BleAction.getDaoSession().queryBuilder(Bracelet_W311_DeviceInfoModel.class);
        queryBuilder.where(Bracelet_W311_DeviceInfoModelDao.Properties.DeviceId.eq(deviceId));
        if (queryBuilder.list().size() > 0) {
            Bracelet_W311_DeviceInfoModel bracelet_w311_displayModel = queryBuilder.list().get(0);
            return bracelet_w311_displayModel;
        } else {
            return null;
        }
    }

    public static boolean saveOrUpdateW311DeviceInfo(String userId, DeviceInfo model, String deviceName) {


        long id;
        Bracelet_W311_DeviceInfoModelDao bracelet_w311_deviceInfoModelDao = BleAction.getsBracelet_w311_deviceInfoModelDao();
        Bracelet_W311_DeviceInfoModel deviceModel = findBraceletW311DeviceInfo(userId, deviceName);
        if (deviceModel == null) {
            deviceModel = new Bracelet_W311_DeviceInfoModel();
            deviceModel.setUserId(userId);
            deviceModel.setDeviceId(deviceName);
        }
        deviceModel.setDeviceModel(model.getDeviceModel());
        deviceModel.setHardwareVersion(model.getHardwareVersion());
        deviceModel.setFirmwareHighVersion(model.getFirmwareHighVersion());
        deviceModel.setFirmwareLowVersion(model.getFirmwareLowVersion());
        deviceModel.setPowerLevel(model.getPowerLevel());
        deviceModel.setStatePhoto(model.getStatePhoto());
        deviceModel.setStateVibrate(model.getStateVibrate());
        deviceModel.setStateLock(model.getStateLock());
        deviceModel.setStateFindPhone(model.getStateFindPhone());
        deviceModel.setStateHigh(model.getStateHigh());
        deviceModel.setStateMusic(model.getStateMusic());
        deviceModel.setStateBleInterface(model.getStateBleInterface());
        deviceModel.setStateProtected(model.getStateProtected());

        deviceModel.setStateMenu(model.getStateMenu());
        deviceModel.setState5Vibrate(model.getState5Vibrate());
        deviceModel.setStateCallMsg(model.getStateCallMsg());
        deviceModel.setStateConnectVibrate(model.getStateConnectVibrate());

        deviceModel.setStatePinCode(model.getStatePinCode());
        deviceModel.setCalIconHeart(model.getCalIconHeart());
        deviceModel.setCalCaculateMethod(model.getCalCaculateMethod());
        deviceModel.setBleRealTimeBroad(model.getBleRealTimeBroad());
        deviceModel.setStateleftRight(model.getStateleftRight());
        deviceModel.setStateAntiLost(model.getStateAntiLost());
        deviceModel.setStateCallRemind(model.getStateCallRemind());
        deviceModel.setStateMessageContent(model.getStateMessageContent());
        deviceModel.setStateMessageIcon(model.getStateMessageIcon());
        deviceModel.setStateSleepInterfaceAndFunc(model.getStateSleepInterfaceAndFunc());
        deviceModel.setStateSyncTime(model.getStateSyncTime());
        deviceModel.setStateConnectVibrate(model.getStateConnectVibrate());
        Logger.myLog(deviceModel.toString());
        id = bracelet_w311_deviceInfoModelDao.insertOrReplace(deviceModel);
        //return id;
        return true;




    }

}
