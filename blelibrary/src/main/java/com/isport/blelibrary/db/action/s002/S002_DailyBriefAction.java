package com.isport.blelibrary.db.action.s002;

import android.text.TextUtils;

import androidx.annotation.Nullable;

import com.isport.blelibrary.db.action.BleAction;
import com.isport.blelibrary.db.table.s002.DailyBrief;
import com.isport.blelibrary.gen.DailyBriefDao;

import org.greenrobot.greendao.query.QueryBuilder;

import java.util.List;

public class S002_DailyBriefAction {


    public static void deletAll() {
        DailyBriefDao oxygenModeDao = BleAction.getsDailyBriefDao();
        oxygenModeDao.deleteAll();
    }


    public void saveDailyBrief(@Nullable String userId, @Nullable String strDate, List<DailyBrief> list) {

        List<DailyBrief> list1 = findDailyBrief(userId, strDate);
        if (list1 != null && list1.size() > 0) {
            deletList(list1);
        }
        DailyBriefDao oxygenModeDao = BleAction.getsDailyBriefDao();
        for (int i = 0; i < list.size(); i++) {
            list.get(i).setUserId(userId);
            list.get(i).setStrDate(strDate);
            long id = oxygenModeDao.insertOrReplace(list.get(i));
        }


    }

    public void deletList(List<DailyBrief> list) {
        DailyBriefDao oxygenModeDao = BleAction.getsDailyBriefDao();

        for (int i = 0; i < list.size(); i++) {
            oxygenModeDao.delete(list.get(0));
        }

    }

    public List<DailyBrief> findDailyBrief(@Nullable String userId, @Nullable String strDate) {
        if (TextUtils.isEmpty(userId) || TextUtils.isEmpty(strDate)) {
            return null;
        }
        QueryBuilder<DailyBrief> queryBuilder = BleAction.getDaoSession().queryBuilder(DailyBrief.class);
        queryBuilder.where(DailyBriefDao.Properties.UserId.eq(userId), DailyBriefDao.Properties.StrDate.eq(strDate)).orderDesc(DailyBriefDao.Properties.StrDate).distinct();
        return queryBuilder.list();
    }


}
