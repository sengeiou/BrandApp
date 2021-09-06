package com.isport.blelibrary.db.action.watch;

import android.text.TextUtils;

import com.isport.blelibrary.db.action.BleAction;
import com.isport.blelibrary.db.table.watch.Watch_SmartBand_HandScreenModel;
import com.isport.blelibrary.gen.Watch_SmartBand_HandScreenModelDao;

import org.greenrobot.greendao.query.QueryBuilder;

/**
 * @Author
 * @Date 2019/1/8
 * @Fuction
 */

public class Watch_SmartBand_HandScreenModelAction {


    /**
     * 查询是否存储过了deviceId的设备
     */
    public static boolean hasStoreDeviceType(String deviceId) {
        if (TextUtils.isEmpty(deviceId)){
            return false;
        }
        QueryBuilder<Watch_SmartBand_HandScreenModel> queryBuilder = BleAction.getDaoSession().queryBuilder
                (Watch_SmartBand_HandScreenModel.class);
        queryBuilder.where(Watch_SmartBand_HandScreenModelDao.Properties.DeviceId.eq(deviceId));
        if (queryBuilder.list().size() > 0) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 查询deviceId的设备抬腕亮屏,默认值为false，关闭
     */
    public static boolean findHandScreenByDeviceId(String deviceId) {
        if (TextUtils.isEmpty(deviceId)){
            return false;
        }
        QueryBuilder<Watch_SmartBand_HandScreenModel> queryBuilder = BleAction.getDaoSession().queryBuilder
                (Watch_SmartBand_HandScreenModel.class);
        queryBuilder.where(Watch_SmartBand_HandScreenModelDao.Properties.DeviceId.eq(deviceId));
        if (queryBuilder.list().size() > 0) {
            Watch_SmartBand_HandScreenModel watchSmartBandHandScreenModel = queryBuilder.list().get(0);
            return watchSmartBandHandScreenModel.getIsOpen();
        } else {
            return false;
        }
    }

    /**
     * 查询deviceId的设备抬腕亮屏,默认值为false，关闭
     */
    public static Watch_SmartBand_HandScreenModel findHandScreenModelByDeviceId(String deviceId) {
        if (TextUtils.isEmpty(deviceId)){
            return null;
        }
        QueryBuilder<Watch_SmartBand_HandScreenModel> queryBuilder = BleAction.getDaoSession().queryBuilder
                (Watch_SmartBand_HandScreenModel.class);
        queryBuilder.where(Watch_SmartBand_HandScreenModelDao.Properties.DeviceId.eq(deviceId));
        if (queryBuilder.list().size() > 0) {
            Watch_SmartBand_HandScreenModel watchSmartBandHandScreenModel = queryBuilder.list().get(0);
            return watchSmartBandHandScreenModel;
        } else {
            return null;
        }
    }
}
