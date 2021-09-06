package com.isport.blelibrary.db.action.bracelet_w311;

import android.text.TextUtils;

import com.isport.blelibrary.db.action.BleAction;
import com.isport.blelibrary.db.table.bracelet_w311.Bracelet_W311_LiftWristToViewInfoModel;
import com.isport.blelibrary.gen.Bracelet_W311_LiftWristToViewInfoModelDao;

import org.greenrobot.greendao.query.QueryBuilder;

public class Bracelet_W311_liftwristModelAction {

    /**
     * 查询deviceId的显示项
     */
    public static Bracelet_W311_LiftWristToViewInfoModel findBracelet_W311_liftwrist(String deviceId) {
        if(TextUtils.isEmpty(deviceId)){
            return null;
        }
        QueryBuilder<Bracelet_W311_LiftWristToViewInfoModel> queryBuilder = BleAction.getDaoSession().queryBuilder(Bracelet_W311_LiftWristToViewInfoModel.class);
        queryBuilder.where(Bracelet_W311_LiftWristToViewInfoModelDao.Properties.DeviceId.eq(deviceId));
        if (queryBuilder.list().size() > 0) {
            Bracelet_W311_LiftWristToViewInfoModel bracelet_w311_displayModel = queryBuilder.list().get(0);
            return bracelet_w311_displayModel;
        } else {
            return null;
        }
    }


    public synchronized static long saveOrUpdateBraceletLift(final Bracelet_W311_LiftWristToViewInfoModel model) {
        long id;
        Bracelet_W311_LiftWristToViewInfoModelDao bracelet_w311_displayModelDao = BleAction.getsBracelet_w311_liftWristToViewInfoModelDao();
        Bracelet_W311_LiftWristToViewInfoModel bracelet_w311_liftWristToViewInfoModel = findBracelet_W311_liftwrist(model.getDeviceId());
        if (bracelet_w311_liftWristToViewInfoModel != null) {
            bracelet_w311_liftWristToViewInfoModel.setUserId(model.getUserId());
            bracelet_w311_liftWristToViewInfoModel.setSwichType(model.getSwichType());
            bracelet_w311_liftWristToViewInfoModel.setStartHour(model.getStartHour());
            bracelet_w311_liftWristToViewInfoModel.setEndHour(model.getEndHour());
            bracelet_w311_liftWristToViewInfoModel.setStartMin(model.getStartMin());
            bracelet_w311_liftWristToViewInfoModel.setEndMin(model.getEndMin());
            bracelet_w311_liftWristToViewInfoModel.setIsNextDay(model.getIsNextDay());
            id = bracelet_w311_displayModelDao.insertOrReplace(bracelet_w311_liftWristToViewInfoModel);

        } else {
            model.setId(0l);
            id = bracelet_w311_displayModelDao.insertOrReplace(model);
        }
        return id;
    }


}
