package com.isport.blelibrary.db.action.s002;

import android.text.TextUtils;

import androidx.annotation.Nullable;

import com.isport.blelibrary.db.action.BleAction;
import com.isport.blelibrary.db.table.s002.DailySummaries;
import com.isport.blelibrary.gen.DailySummariesDao;

import org.greenrobot.greendao.query.QueryBuilder;

import java.util.List;

public class S002_DailySummaryAction {


    public static void deletAll() {
        DailySummariesDao oxygenModeDao = BleAction.getDailySummariesDao();
        oxygenModeDao.deleteAll();
    }


    public void saveDailySummary(@Nullable String userId, List<DailySummaries> list) {

        for (int i = 0; i < list.size(); i++) {
            List<DailySummaries> list1 = findDailySummary(userId, list.get(i).getDay());
            deletList(list1);
        }
        DailySummariesDao oxygenModeDao = BleAction.getDailySummariesDao();
        for (int i = 0; i < list.size(); i++) {
            list.get(i).setUserId(userId);
            long id = oxygenModeDao.insertOrReplace(list.get(i));
        }


    }

    public void deletList(List<DailySummaries> list) {
        DailySummariesDao oxygenModeDao = BleAction.getDailySummariesDao();

        for (int i = 0; i < list.size(); i++) {
            oxygenModeDao.delete(list.get(0));
        }

    }

    public void deletBean(DailySummaries bean) {
        DailySummariesDao oxygenModeDao = BleAction.getDailySummariesDao();


        oxygenModeDao.delete(bean);


    }

    public List<DailySummaries> findDailySummary(@Nullable String userId, @Nullable String strDate) {
        if (TextUtils.isEmpty(userId) || TextUtils.isEmpty(userId)) {
            return null;
        }
        QueryBuilder<DailySummaries> queryBuilder = BleAction.getDaoSession().queryBuilder(DailySummaries.class);
        queryBuilder.where(DailySummariesDao.Properties.UserId.eq(userId), DailySummariesDao.Properties.Day.eq(strDate));
        return queryBuilder.list();
    }
    public List<DailySummaries> findDailySummary(@Nullable String userId, @Nullable String strDate,@Nullable String endDate) {
        if (TextUtils.isEmpty(userId) || TextUtils.isEmpty(userId)) {
            return null;
        }
        QueryBuilder<DailySummaries> queryBuilder = BleAction.getDaoSession().queryBuilder(DailySummaries.class);
        queryBuilder.where(DailySummariesDao.Properties.UserId.eq(userId), DailySummariesDao.Properties.Day.eq(strDate));
        return queryBuilder.list();
    }
    public DailySummaries findDailyBean(@Nullable String userId, @Nullable String strDate) {
        if (TextUtils.isEmpty(userId) || TextUtils.isEmpty(userId)) {
            return null;
        }
        QueryBuilder<DailySummaries> queryBuilder = BleAction.getDaoSession().queryBuilder(DailySummaries.class);
        queryBuilder.where(DailySummariesDao.Properties.UserId.eq(userId), DailySummariesDao.Properties.Day.eq(strDate));
        List<DailySummaries> list=queryBuilder.list();
        if(list!=null && list.size()>0){
            return list.get(0);
        }else{
            return null;
        }
    }
}
