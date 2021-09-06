package com.isport.blelibrary.db.action.watch_w516;

import android.text.TextUtils;

import androidx.annotation.NonNull;

import com.isport.blelibrary.db.action.BleAction;
import com.isport.blelibrary.db.table.watch_w516.Watch_W516_SedentaryModel;
import com.isport.blelibrary.gen.Watch_W516_SedentaryModelDao;
import com.isport.blelibrary.utils.Logger;

import org.greenrobot.greendao.query.QueryBuilder;

import java.util.List;

/**
 * @Author
 * @Date 2019/2/24
 * @Fuction
 */

public class Watch_W516_SedentaryModelAction {


    /**
     * 查询deviceId的设备久坐提醒
     */
    public static Watch_W516_SedentaryModel findWatch_W516_Watch_W516_SedentaryModelyDeviceId(@NonNull String deviceId, @NonNull String userId) {
        if (TextUtils.isEmpty(deviceId) || TextUtils.isEmpty(userId)) {
            return null;
        }
        Logger.myLog("findWatch_W516_Watch_W516_SedentaryModelyDeviceId:" + deviceId + "userId:" + userId);
        QueryBuilder<Watch_W516_SedentaryModel> queryBuilder = BleAction.getDaoSession().queryBuilder
                (Watch_W516_SedentaryModel.class);
        queryBuilder.where(Watch_W516_SedentaryModelDao.Properties.DeviceId.eq(deviceId), Watch_W516_SedentaryModelDao.Properties.UserId.eq(userId));


        List<Watch_W516_SedentaryModel> list = queryBuilder.list();
        try {
            if (list != null && list.size() > 0) {
                Watch_W516_SedentaryModel watch_W516_SedentaryModel = list.get(0);
                Logger.myLog(watch_W516_SedentaryModel.toString());
                return watch_W516_SedentaryModel;
            } else {
                return null;
            }
        } catch (Exception e) {
            return null;
        }

    }
}
