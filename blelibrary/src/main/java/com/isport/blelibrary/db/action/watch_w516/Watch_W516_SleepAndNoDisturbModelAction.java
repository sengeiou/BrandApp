package com.isport.blelibrary.db.action.watch_w516;

import android.text.TextUtils;

import com.isport.blelibrary.db.action.BleAction;
import com.isport.blelibrary.db.table.watch_w516.Watch_W516_SleepAndNoDisturbModel;
import com.isport.blelibrary.gen.Watch_W516_SleepAndNoDisturbModelDao;

import org.greenrobot.greendao.query.QueryBuilder;

import java.util.List;

/**
 * @Author
 * @Date 2019/2/24
 * @Fuction
 */

public class Watch_W516_SleepAndNoDisturbModelAction {

    /**
     * 查询deviceId的设备睡眠设置
     */
    public static Watch_W516_SleepAndNoDisturbModel findWatch_W516_SleepAndNoDisturbModelyDeviceId(String userId, String deviceId) {

        try {
            if (TextUtils.isEmpty(deviceId) || TextUtils.isEmpty(userId)) {
                return null;
            }

            QueryBuilder<Watch_W516_SleepAndNoDisturbModel> queryBuilder = BleAction.getDaoSession().queryBuilder
                    (Watch_W516_SleepAndNoDisturbModel.class);

            queryBuilder.where(Watch_W516_SleepAndNoDisturbModelDao.Properties.DeviceId.eq(deviceId), Watch_W516_SleepAndNoDisturbModelDao.Properties.UserId.eq(userId));


            List<Watch_W516_SleepAndNoDisturbModel> list = queryBuilder.list();

            try {
                if (list != null && list.size() > 0) {
                    Watch_W516_SleepAndNoDisturbModel watch_w516_sleepAndNoDisturbModel = list.get(0);
                    return watch_w516_sleepAndNoDisturbModel;
                } else {
                    return null;
                }
            } catch (Exception e) {
                return null;
            }

        } catch (Exception e) {
            return null;
        }


    }
}
