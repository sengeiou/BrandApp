package com.isport.blelibrary.db.action.watch_w516;

import android.text.TextUtils;

import androidx.annotation.NonNull;

import com.isport.blelibrary.db.action.BleAction;
import com.isport.blelibrary.db.table.watch_w516.Watch_W560_AlarmModel;
import com.isport.blelibrary.gen.Watch_W560_AlarmModelDao;
import com.isport.blelibrary.utils.Logger;
import com.isport.blelibrary.utils.ThreadPoolUtils;

import org.greenrobot.greendao.query.QueryBuilder;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author
 * @Date 2019/2/24
 * @Fuction
 */

public class Watch_W560_AlarmModelAction {

    /**
     * 查询deviceId的设备闹钟
     */
    public static ArrayList<Watch_W560_AlarmModel> findWatch_W560_AlarmModelByDeviceId(String deviceId, String userId) {
        if (TextUtils.isEmpty(deviceId)||TextUtils.isEmpty(userId)) {
            return null;
        }
        QueryBuilder<Watch_W560_AlarmModel> queryBuilder = BleAction.getDaoSession().queryBuilder(Watch_W560_AlarmModel.class);
        queryBuilder.where(Watch_W560_AlarmModelDao.Properties.DeviceId.eq(deviceId), Watch_W560_AlarmModelDao.Properties.UserId.eq(userId));
        if (queryBuilder.list().size() > 0) {
            ArrayList<Watch_W560_AlarmModel> watch_w560_alarmModels = (ArrayList<Watch_W560_AlarmModel>) queryBuilder.list();
            return watch_w560_alarmModels;
        } else {
            return null;
        }
    }

    public static Watch_W560_AlarmModel findWatch_W560_AlarmModelByDeviceId(@NonNull String deviceId, @NonNull String userId, int index) {

        if (TextUtils.isEmpty(deviceId) || TextUtils.isEmpty(userId)) {
            return null;
        }
        QueryBuilder<Watch_W560_AlarmModel> queryBuilder = BleAction.getDaoSession().queryBuilder(Watch_W560_AlarmModel.class);
        queryBuilder.where(Watch_W560_AlarmModelDao.Properties.DeviceId.eq(deviceId), Watch_W560_AlarmModelDao.Properties.UserId.eq(userId), Watch_W560_AlarmModelDao.Properties.Index.eq(index));
        List<Watch_W560_AlarmModel> list = queryBuilder.list();
        try {
            if (list != null && list.size() > 0) {
                Watch_W560_AlarmModel watch_W560_AlarmModel = list.get(0);
                return watch_W560_AlarmModel;
            } else {
                return null;
            }
        } catch (Exception e) {
            return null;
        }

    }

    public static void saveOrUpdateBraceletW560AlarmModele(final Watch_W560_AlarmModel model) {

        //先删除
        ThreadPoolUtils.getInstance().addTask(new Runnable() {
            @Override
            public void run() {
                Logger.myLog("saveOrUpdateBraceletW560AlarmModele:" + model);

                Watch_W560_AlarmModelDao watch_w560_alarmModelDao = BleAction.getWatch_W560_AlarmModelDao();
                watch_w560_alarmModelDao.insertOrReplace(model);
            }
        });

    }

    public static void deletWatchAlarmItem(Watch_W560_AlarmModel model) {
        Watch_W560_AlarmModelDao deviceTypeTableDao = BleAction.getWatch_W560_AlarmModelDao();
        deviceTypeTableDao.delete(model);
    }

    public static void deletWatchAlarmItems(String deviceId, String userId) {
        if (TextUtils.isEmpty(deviceId)||TextUtils.isEmpty(userId)) {
            return;
        }
        QueryBuilder<Watch_W560_AlarmModel> queryBuilder = BleAction.getDaoSession().queryBuilder(Watch_W560_AlarmModel.class);
        queryBuilder.where(Watch_W560_AlarmModelDao.Properties.DeviceId.eq(deviceId), Watch_W560_AlarmModelDao.Properties.UserId.eq(userId));
        List<Watch_W560_AlarmModel> list = queryBuilder.list();

        if (list.size() > 0) {
            Watch_W560_AlarmModelDao deviceTypeTableDao = BleAction.getWatch_W560_AlarmModelDao();

            for (int i = 0; i < list.size(); i++) {
                deviceTypeTableDao.delete(list.get(i));
            }
        }
    }
}
