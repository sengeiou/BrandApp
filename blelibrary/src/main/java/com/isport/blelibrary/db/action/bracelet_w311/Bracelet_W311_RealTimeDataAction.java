package com.isport.blelibrary.db.action.bracelet_w311;

import android.text.TextUtils;

import com.isport.blelibrary.db.action.BleAction;
import com.isport.blelibrary.db.table.bracelet_w311.Bracelet_W311_RealTimeData;
import com.isport.blelibrary.db.table.bracelet_w311.Bracelet_w311_hrModel;
import com.isport.blelibrary.gen.Bracelet_W311_RealTimeDataDao;
import com.isport.blelibrary.gen.Bracelet_w311_hrModelDao;
import com.isport.blelibrary.utils.Logger;

import org.greenrobot.greendao.query.QueryBuilder;

/**
 * @创建者 bear
 * @创建时间 2019/3/5 15:54
 * @描述
 */
public class Bracelet_W311_RealTimeDataAction {

    /**
     * 查询deviceId的显示项
     */
    public static Bracelet_W311_RealTimeData find_real_time_data(String userId, String deviceId, String date) {
        if (TextUtils.isEmpty(deviceId) || TextUtils.isEmpty(date)) {
            return null;
        }
        QueryBuilder<Bracelet_W311_RealTimeData> queryBuilder = BleAction.getDaoSession().queryBuilder(Bracelet_W311_RealTimeData.class);
        queryBuilder.where(Bracelet_W311_RealTimeDataDao.Properties.DeviceId.eq(deviceId), Bracelet_W311_RealTimeDataDao.Properties.UserId.eq(userId), Bracelet_W311_RealTimeDataDao.Properties.Date.eq(date));
        if (queryBuilder.list().size() > 0) {
            Bracelet_W311_RealTimeData bracelet_w311_displayModel = queryBuilder.list().get(0);
            return bracelet_w311_displayModel;
        } else {
            return null;
        }
    }


    public static long saveOrUpdateW311RealTimeData(String userId, final Bracelet_W311_RealTimeData model, boolean isclear) {
        long id;
        Bracelet_W311_RealTimeDataDao bracelet_w311_displayModelDao = BleAction.getBracelet_w311_realTimeDataDao();
        Bracelet_W311_RealTimeData bracelet_w311_realTimeData = find_real_time_data(userId, model.getDeviceId(), model.getDate());
        if (bracelet_w311_realTimeData != null) {
           // Logger.myLog("model.getStepNum():" + model.getStepNum() + "bracelet_w311_realTimeData.getStepNum():" + bracelet_w311_realTimeData.getStepNum());
            if (!isclear) {
                if (model.getStepNum() <= bracelet_w311_realTimeData.getStepNum()) {
                    return -1;
                }
            }
            bracelet_w311_realTimeData.setUserId(model.getUserId());
            bracelet_w311_realTimeData.setCal(model.getCal());
            bracelet_w311_realTimeData.setDate(model.getDate());
            bracelet_w311_realTimeData.setMac(model.getMac());
            bracelet_w311_realTimeData.setStepKm(model.getStepKm());
            bracelet_w311_realTimeData.setStepNum(model.getStepNum());
            id = bracelet_w311_displayModelDao.insertOrReplace(bracelet_w311_realTimeData);

        } else {
            // model.setId(0l);
            id = bracelet_w311_displayModelDao.insertOrReplace(model);
        }
        return id;
    }


}
