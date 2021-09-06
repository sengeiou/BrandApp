package com.isport.blelibrary.db.action.watch_w516;

import android.text.TextUtils;

import androidx.annotation.Nullable;

import com.isport.blelibrary.db.action.BleAction;
import com.isport.blelibrary.db.table.watch_w516.Watch_W516_SettingModel;
import com.isport.blelibrary.gen.Watch_W516_SettingModelDao;

import org.greenrobot.greendao.query.QueryBuilder;

import java.util.List;

/**
 * @Author
 * @Date 2019/2/24
 * @Fuction
 */

public class Watch_W516_SettingModelAction {

    /**
     * 查询deviceId的设备通用设置
     */
    public static Watch_W516_SettingModel findW516SettingModelByDeviceId(@Nullable String deviceId, @Nullable String userId) {
        if (TextUtils.isEmpty(deviceId) || TextUtils.isEmpty(userId)) {
            return null;
        }
        QueryBuilder<Watch_W516_SettingModel> queryBuilder = BleAction.getDaoSession().queryBuilder(Watch_W516_SettingModel.class);
        queryBuilder.where(Watch_W516_SettingModelDao.Properties.DeviceId.eq(deviceId), Watch_W516_SettingModelDao.Properties.UserId.eq(userId));

        List<Watch_W516_SettingModel> list = queryBuilder.list();
        try {
            if (list != null && list.size() > 0) {
                Watch_W516_SettingModel watch_W516_SettingModel = list.get(0);
                return watch_W516_SettingModel;
            } else {
                return null;
            }
        } catch (Exception e) {
            return null;
        }


    }
}
