package com.isport.blelibrary.db.action.watch;

import android.text.TextUtils;

import com.isport.blelibrary.db.action.BleAction;
import com.isport.blelibrary.db.table.watch.Watch_SmartBand_StepTargetModel;
import com.isport.blelibrary.gen.Watch_SmartBand_StepTargetModelDao;

import org.greenrobot.greendao.query.QueryBuilder;

/**
 * @Author
 * @Date 2019/1/8
 * @Fuction
 */

public class Watch_SmartBand_StepTargetModelAction {

    /**
     * 查询是否存储过了deviceId的设备
     */
    public static boolean hasStoreDeviceType(String deviceId) {
        if (TextUtils.isEmpty(deviceId)) {
            return false;
        }
        QueryBuilder<Watch_SmartBand_StepTargetModel> queryBuilder = BleAction.getDaoSession().queryBuilder(Watch_SmartBand_StepTargetModel.class);
        queryBuilder.where(Watch_SmartBand_StepTargetModelDao.Properties.DeviceId.eq(deviceId));
        if (queryBuilder.list().size() > 0) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 查询deviceId的设备目标信息,默认值为6000步
     */
    public static int findTargetByDeviceId(String deviceId) {
        if (TextUtils.isEmpty(deviceId)) {
            return 6000;
        }
        QueryBuilder<Watch_SmartBand_StepTargetModel> queryBuilder = BleAction.getDaoSession().queryBuilder(Watch_SmartBand_StepTargetModel.class);
        queryBuilder.where(Watch_SmartBand_StepTargetModelDao.Properties.DeviceId.eq(deviceId));
        if (queryBuilder.list().size() > 0) {
            Watch_SmartBand_StepTargetModel watch_smartBand_stepTargetModel = queryBuilder.list().get(0);
            return watch_smartBand_stepTargetModel.getTarget();
        } else {
            return 6000;
        }
    }

    /**
     * 查询deviceId的设备目标信息,默认值为6000步
     */
    public static Watch_SmartBand_StepTargetModel findTargetModelByDeviceId(String deviceId) {
        if (TextUtils.isEmpty(deviceId)) {
            return null;
        }
        QueryBuilder<Watch_SmartBand_StepTargetModel> queryBuilder = BleAction.getDaoSession().queryBuilder(Watch_SmartBand_StepTargetModel.class);
        queryBuilder.where(Watch_SmartBand_StepTargetModelDao.Properties.DeviceId.eq(deviceId));
        if (queryBuilder.list().size() > 0) {
            Watch_SmartBand_StepTargetModel watch_smartBand_stepTargetModel = queryBuilder.list().get(0);
            return watch_smartBand_stepTargetModel;
        } else {
            return null;
        }
    }
}
