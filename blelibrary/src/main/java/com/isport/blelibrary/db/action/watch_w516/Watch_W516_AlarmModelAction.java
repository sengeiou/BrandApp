package com.isport.blelibrary.db.action.watch_w516;

import android.text.TextUtils;

import androidx.annotation.NonNull;

import com.isport.blelibrary.db.action.BleAction;
import com.isport.blelibrary.db.table.watch_w516.Watch_W516_AlarmModel;
import com.isport.blelibrary.gen.Watch_W516_AlarmModelDao;

import org.greenrobot.greendao.query.QueryBuilder;

import java.util.List;

/**
 * @Author
 * @Date 2019/2/24
 * @Fuction
 */

public class Watch_W516_AlarmModelAction {

    /**
     * 查询deviceId的设备闹钟
     */
    public static Watch_W516_AlarmModel findWatch_W516_AlarmModelByDeviceId(@NonNull String deviceId, @NonNull String userId) {

        if (TextUtils.isEmpty(deviceId) || TextUtils.isEmpty(userId)) {
            return null;
        }
        QueryBuilder<Watch_W516_AlarmModel> queryBuilder = BleAction.getDaoSession().queryBuilder(Watch_W516_AlarmModel.class);
        queryBuilder.where(Watch_W516_AlarmModelDao.Properties.DeviceId.eq(deviceId), Watch_W516_AlarmModelDao.Properties.UserId.eq(userId));
        List<Watch_W516_AlarmModel> list = queryBuilder.list();
        try {
            if (list != null && list.size() > 0) {
                Watch_W516_AlarmModel watch_W516_AlarmModel = list.get(0);
                return watch_W516_AlarmModel;
            } else {
                return null;
            }
        } catch (Exception e) {
            return null;
        }

    }
}
