package com.isport.blelibrary.db.action.s002;

import android.text.TextUtils;

import androidx.annotation.Nullable;

import com.isport.blelibrary.db.action.BleAction;
import com.isport.blelibrary.db.table.s002.Summary;
import com.isport.blelibrary.gen.SummaryDao;
import com.isport.blelibrary.utils.Logger;

import org.greenrobot.greendao.query.QueryBuilder;

import java.util.List;

public class S002_SummaryAction {


    public static void deletAll() {
        SummaryDao oxygenModeDao = BleAction.getSummaryDao();
        oxygenModeDao.deleteAll();
    }


    public void saveSummary(@Nullable String userId, @Nullable String strDate, Summary summary, String summaryType) {
        if (TextUtils.isEmpty(userId) || TextUtils.isEmpty(strDate) || TextUtils.isEmpty(summaryType)) {
            return;
        }
        if (summaryType.equals("ALL")) {
            return;
        }
        List<Summary> list1 = findSummaryList(userId, strDate, summaryType);

        if (list1 != null && list1.size() > 0) {
            deletList(list1);
        }

        SummaryDao oxygenModeDao = BleAction.getSummaryDao();
        summary.setUserId(userId);
        summary.setSummaryType(summaryType);
        summary.setDay(strDate);
        long id = oxygenModeDao.insertOrReplace(summary);
        Logger.myLog("saveSummary saveid=" + id);

        list1 = findSummaryList(userId, strDate, summaryType);

    }

    public void deletList(List<Summary> list) {
        SummaryDao oxygenModeDao = BleAction.getSummaryDao();

        for (int i = 0; i < list.size(); i++) {
            oxygenModeDao.delete(list.get(i));
        }

    }

    public Summary findSummary(@Nullable String userId, @Nullable String strDate, @Nullable String summaryType) {
        if (TextUtils.isEmpty(userId) || TextUtils.isEmpty(strDate) || TextUtils.isEmpty(summaryType)) {
            return null;
        }
        QueryBuilder<Summary> queryBuilder = BleAction.getDaoSession().queryBuilder(Summary.class);
        queryBuilder.where(SummaryDao.Properties.UserId.eq(userId), SummaryDao.Properties.Day.eq(strDate), SummaryDao.Properties.SummaryType.eq(summaryType)).orderDesc(SummaryDao.Properties.Day).distinct();
        List<Summary> list = queryBuilder.list();
        if (list != null && list.size() > 0) {
            return list.get(0);
        } else {
            return null;
        }
    }

    public List<Summary> findSummaryList(@Nullable String userId, @Nullable String strDate, @Nullable String summaryType) {
        if (TextUtils.isEmpty(userId) || TextUtils.isEmpty(strDate) || TextUtils.isEmpty(summaryType)) {
            return null;
        }
        QueryBuilder<Summary> queryBuilder = BleAction.getDaoSession().queryBuilder(Summary.class);
        queryBuilder.where(SummaryDao.Properties.UserId.eq(userId), SummaryDao.Properties.Day.eq(strDate), SummaryDao.Properties.SummaryType.eq(summaryType)).orderDesc(SummaryDao.Properties.Day).distinct();
        return queryBuilder.list();
    }
}
