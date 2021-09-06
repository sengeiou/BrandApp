package com.isport.blelibrary.db.action;

import androidx.annotation.Nullable;
import android.text.TextUtils;

import com.isport.blelibrary.db.table.DeviceTempUnitlTable;
import com.isport.blelibrary.gen.DeviceTempUnitlTableDao;
import com.isport.blelibrary.utils.Logger;

import org.greenrobot.greendao.query.QueryBuilder;

import java.util.List;

/**
 * @Author
 * @Date 2019/1/7
 * @Fuction
 */

public class DeviceTempUnitlTableAction {

    DeviceTempUnitlTableDao deviceTempUnitlTableDao;

    public DeviceTempUnitlTable findTempUnitl(@Nullable String deviceId, @Nullable String userid) {

        if (TextUtils.isEmpty(deviceId) || TextUtils.isEmpty(userid)) {
            return null;
        }
        QueryBuilder<DeviceTempUnitlTable> queryBuilder = BleAction.getDaoSession().queryBuilder(DeviceTempUnitlTable.class);
        queryBuilder.where(DeviceTempUnitlTableDao.Properties.DeviceName.eq(deviceId), DeviceTempUnitlTableDao.Properties.UserId.eq(userid));


        List<DeviceTempUnitlTable> list = queryBuilder.list();
        if (list.size() > 0) {
            DeviceTempUnitlTable deviceTempUnitlTable = list.get(0);
            return deviceTempUnitlTable;
        } else {
            return null;
        }
    }


    public void saveTempUnitlModel(@Nullable String deviceId, @Nullable String userId, String unitl) {
        if (TextUtils.isEmpty(deviceId) || TextUtils.isEmpty(userId)) {
            return;
        }
        if (TextUtils.isEmpty(unitl)) {
            unitl = "0";
        }
        DeviceTempUnitlTable deviceTempUnitlTable = findTempUnitl(deviceId, userId);

        if (deviceTempUnitlTable != null) {
            deviceTempUnitlTable.setTempUnitl(unitl);
        } else {
            deviceTempUnitlTable = new DeviceTempUnitlTable();
            deviceTempUnitlTable.setDeviceName(deviceId);
            deviceTempUnitlTable.setTempUnitl(unitl);
            deviceTempUnitlTable.setUserId(userId);
        }
        saveTempUnitlModel(deviceTempUnitlTable);
    }


    public void saveTempUnitlModel(DeviceTempUnitlTable model) {
        Logger.myLog("saveTempUnitlModel:" + model);
        deviceTempUnitlTableDao = BleAction.getDeviceTempUnitlTableDao();
        if (deviceTempUnitlTableDao != null) {
            deviceTempUnitlTableDao.insertOrReplace(model);
        }


    }

}
