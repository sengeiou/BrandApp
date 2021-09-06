package com.isport.blelibrary.db.action.watch;

import android.text.TextUtils;

import com.isport.blelibrary.db.action.BleAction;
import com.isport.blelibrary.db.table.watch.Watch_SmartBand_ScreenTimeModel;
import com.isport.blelibrary.gen.Watch_SmartBand_ScreenTimeModelDao;

import org.greenrobot.greendao.query.QueryBuilder;

/**
 * @Author
 * @Date 2019/1/8
 * @Fuction
 */

public class Watch_SmartBand_ScreenTimeModelAction {


    /**
     * 查询是否存储过了deviceId的设备
     */
    public static boolean hasStoreDeviceType(String deviceId) {
        if (TextUtils.isEmpty(deviceId)){
            return false;
        }
        QueryBuilder<Watch_SmartBand_ScreenTimeModel> queryBuilder = BleAction.getDaoSession().queryBuilder
                (Watch_SmartBand_ScreenTimeModel.class);
        queryBuilder.where(Watch_SmartBand_ScreenTimeModelDao.Properties.DeviceId.eq(deviceId));
        if (queryBuilder.list().size() > 0) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 查询deviceId的设备亮屏时间,默认值为5分钟
     */
    public static int findScreenTimeByDeviceId(String deviceId) {
        if (TextUtils.isEmpty(deviceId)){
            return 5;
        }
        QueryBuilder<Watch_SmartBand_ScreenTimeModel> queryBuilder = BleAction.getDaoSession().queryBuilder(Watch_SmartBand_ScreenTimeModel.class);
        queryBuilder.where(Watch_SmartBand_ScreenTimeModelDao.Properties.DeviceId.eq(deviceId));
        if (queryBuilder.list().size() > 0) {
            Watch_SmartBand_ScreenTimeModel watch_smartBand_screenTimeModel = queryBuilder.list().get(0);
            return watch_smartBand_screenTimeModel.getTime();
        } else {
            return 5;
        }
    }

    /**
     * 查询deviceId的设备亮屏时间,默认值为5分钟
     */
    public static Watch_SmartBand_ScreenTimeModel findScreenTimeModelByDeviceId(String deviceId) {
        if (TextUtils.isEmpty(deviceId)){
            return null;
        }
        QueryBuilder<Watch_SmartBand_ScreenTimeModel> queryBuilder = BleAction.getDaoSession().queryBuilder(Watch_SmartBand_ScreenTimeModel.class);
        queryBuilder.where(Watch_SmartBand_ScreenTimeModelDao.Properties.DeviceId.eq(deviceId));
        if (queryBuilder.list().size() > 0) {
            Watch_SmartBand_ScreenTimeModel watch_smartBand_screenTimeModel = queryBuilder.list().get(0);
            return watch_smartBand_screenTimeModel;
        } else {
            return null;
        }
    }
}
