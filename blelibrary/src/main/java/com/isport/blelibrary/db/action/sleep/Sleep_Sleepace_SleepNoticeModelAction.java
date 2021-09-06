package com.isport.blelibrary.db.action.sleep;

import com.isport.blelibrary.db.action.BleAction;
import com.isport.blelibrary.db.table.sleep.Sleep_Sleepace_SleepNoticeModel;
import com.isport.blelibrary.gen.Sleep_Sleepace_SleepNoticeModelDao;

import org.greenrobot.greendao.query.QueryBuilder;

/**
 * @Author
 * @Date 2019/1/10
 * @Fuction
 */

public class Sleep_Sleepace_SleepNoticeModelAction {

    /**
     * 查询是否存储过了deviceId的设备
     */
    public static boolean hasStoreDeviceType(String deviceId) {
        QueryBuilder<Sleep_Sleepace_SleepNoticeModel> queryBuilder = BleAction.getDaoSession().queryBuilder
                (Sleep_Sleepace_SleepNoticeModel.class);
        queryBuilder.where(Sleep_Sleepace_SleepNoticeModelDao.Properties.DeviceId.eq(deviceId));
        if (queryBuilder.list().size() > 0) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 查询deviceId的设备抬腕亮屏,默认值为false，关闭
     */
    public static String findNoticeTimeByDeviceId(String deviceId) {
        QueryBuilder<Sleep_Sleepace_SleepNoticeModel> queryBuilder = BleAction.getDaoSession().queryBuilder
                (Sleep_Sleepace_SleepNoticeModel.class);
        queryBuilder.where(Sleep_Sleepace_SleepNoticeModelDao.Properties.DeviceId.eq(deviceId));
        if (queryBuilder.list().size() > 0) {
            Sleep_Sleepace_SleepNoticeModel sleep_sleepace_sleepNoticeModel = queryBuilder.list().get(0);
            return sleep_sleepace_sleepNoticeModel.getSleepNoticeTime();
        } else {
            return "";
        }
    }
}
