package com.isport.blelibrary.db.action.bracelet_w311;

import android.text.TextUtils;

import com.isport.blelibrary.db.action.BleAction;
import com.isport.blelibrary.db.table.bracelet_w311.Bracelet_w311_hrModel;
import com.isport.blelibrary.gen.Bracelet_w311_hrModelDao;

import org.greenrobot.greendao.query.QueryBuilder;

/**
 * @创建者 bear
 * @创建时间 2019/3/5 15:54
 * @描述
 */
public class Bracelet_W311_HrAction {

    /**
     * 查询deviceId的显示项
     */
    public static Bracelet_w311_hrModel findBracelet_W311_HrSetting(String deviceId) {
        if(TextUtils.isEmpty(deviceId) ){
            return null;
        }
        QueryBuilder<Bracelet_w311_hrModel> queryBuilder = BleAction.getDaoSession().queryBuilder(Bracelet_w311_hrModel.class);
        queryBuilder.where(Bracelet_w311_hrModelDao.Properties.DeviceId.eq(deviceId));
        if (queryBuilder.list().size() > 0) {
            Bracelet_w311_hrModel bracelet_w311_displayModel = queryBuilder.list().get(0);
            return bracelet_w311_displayModel;
        } else {
            return null;
        }
    }


    public static long saveOrUpdateBraceletAutoHr(final Bracelet_w311_hrModel model) {
        long id;
        Bracelet_w311_hrModelDao bracelet_w311_displayModelDao = BleAction.getsBracelet_w311_hrModelDao();
        Bracelet_w311_hrModel bracelet_w311_hrModel = findBracelet_W311_HrSetting(model.getDeviceId());
        if (bracelet_w311_hrModel != null) {
            bracelet_w311_hrModel.setUserId(model.getUserId());
            bracelet_w311_hrModel.setIsOpen(model.getIsOpen());
            bracelet_w311_hrModel.setTimes(model.getTimes());
            id = bracelet_w311_displayModelDao.insertOrReplace(bracelet_w311_hrModel);

        } else {
            model.setId(0l);
            id = bracelet_w311_displayModelDao.insertOrReplace(model);
        }
        return id;
    }


}
