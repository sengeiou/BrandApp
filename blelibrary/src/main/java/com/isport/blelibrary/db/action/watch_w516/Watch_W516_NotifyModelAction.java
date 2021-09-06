package com.isport.blelibrary.db.action.watch_w516;

import android.text.TextUtils;

import androidx.annotation.Nullable;

import com.isport.blelibrary.db.action.BleAction;
import com.isport.blelibrary.db.table.watch_w516.Watch_W516_NotifyModel;
import com.isport.blelibrary.gen.Watch_W516_NotifyModelDao;

import org.greenrobot.greendao.query.QueryBuilder;

import java.util.List;

/**
 * @创建者 bear
 * @创建时间 2019/3/2 11:40
 * @描述
 */
public class Watch_W516_NotifyModelAction {

    public static Watch_W516_NotifyModel findWatch_W516_NotifyModelByDeviceId(@Nullable String deviceId, @Nullable String userId) {
        if (TextUtils.isEmpty(deviceId) || TextUtils.isEmpty(userId)) {
            return null;
        }
        QueryBuilder<Watch_W516_NotifyModel> queryBuilder = BleAction.getDaoSession().queryBuilder(Watch_W516_NotifyModel.class);
        queryBuilder.where(Watch_W516_NotifyModelDao.Properties.DeviceId.eq(deviceId), Watch_W516_NotifyModelDao.Properties.UserId.eq(userId));

        List<Watch_W516_NotifyModel> list = queryBuilder.list();

        try {
            if (list != null && list.size() > 0) {
                Watch_W516_NotifyModel watch_w516_notifyModel = list.get(0);
                return watch_w516_notifyModel;
            } else {
                return null;
            }
        } catch (Exception e) {
            return null;
        }

    }
}
