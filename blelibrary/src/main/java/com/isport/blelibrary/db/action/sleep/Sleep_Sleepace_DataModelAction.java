package com.isport.blelibrary.db.action.sleep;

import android.database.Cursor;

import com.isport.blelibrary.db.action.BleAction;
import com.isport.blelibrary.db.table.sleep.Sleep_Sleepace_DataModel;
import com.isport.blelibrary.gen.Sleep_Sleepace_DataModelDao;

import org.greenrobot.greendao.query.QueryBuilder;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author
 * @Date 2019/1/16
 * @Fuction
 */

public class Sleep_Sleepace_DataModelAction {
    /**
     * 查询deviceId的设备抬腕亮屏,默认值为false，关闭
     */
    public static Sleep_Sleepace_DataModel findSleep_Sleepace_DataModelByDeviceId(String userId) {
        QueryBuilder<Sleep_Sleepace_DataModel> queryBuilder = BleAction.getDaoSession().queryBuilder
                (Sleep_Sleepace_DataModel.class);
        queryBuilder.where(Sleep_Sleepace_DataModelDao.Properties.UserId.eq(userId)).orderDesc
                (Sleep_Sleepace_DataModelDao.Properties.DateStr).distinct();
        List<Sleep_Sleepace_DataModel> list = queryBuilder.list();
        if (list.size() > 0) {
            return list.get(0);
        } else {
            return null;
        }
    }

    /**
     * 查询deviceId的设备抬腕亮屏,默认值为false，关闭
     */
    public static Sleep_Sleepace_DataModel findSleep_Sleepace_DataModelByDeviceIdAndDateStr(String userId, String
            dateStr) {
        QueryBuilder<Sleep_Sleepace_DataModel> queryBuilder = BleAction.getDaoSession().queryBuilder
                (Sleep_Sleepace_DataModel.class);
        queryBuilder.where(Sleep_Sleepace_DataModelDao.Properties.UserId.eq(userId), Sleep_Sleepace_DataModelDao
                .Properties.DateStr.like("%" + dateStr + "%")).orderDesc
                (Sleep_Sleepace_DataModelDao.Properties.Timestamp);
//        .distinct()
        List<Sleep_Sleepace_DataModel> list = queryBuilder.list();
        if (list.size() > 0) {
            return list.get(0);
        } else {
            return null;
        }
    }

    /**
     * 查询deviceId的设备抬腕亮屏,默认值为false，关闭
     */
    public static Sleep_Sleepace_DataModel findSleep_Sleepace_DataModelByDeviceIdAndTimeTamp(String userId, long
            timeTamp) {
        QueryBuilder<Sleep_Sleepace_DataModel> queryBuilder = BleAction.getDaoSession().queryBuilder
                (Sleep_Sleepace_DataModel.class);
        queryBuilder.where(Sleep_Sleepace_DataModelDao.Properties.UserId.eq(userId), Sleep_Sleepace_DataModelDao
                .Properties.Timestamp.eq(timeTamp)).orderDesc
                (Sleep_Sleepace_DataModelDao.Properties.Timestamp);
//        .distinct()
        List<Sleep_Sleepace_DataModel> list = queryBuilder.list();
        if (list.size() > 0) {
            return list.get(0);
        } else {
            return null;
        }
    }

    /**
     * 查询绑定时间之后的数据,之前的数据舍弃
     */
    public static  List<Sleep_Sleepace_DataModel> findSleep_Sleepace_DataModelByDeviceIdAndTimeTamp1(String userId, long
            timeTamp) {
        QueryBuilder<Sleep_Sleepace_DataModel> queryBuilder = BleAction.getDaoSession().queryBuilder
                (Sleep_Sleepace_DataModel.class);
        queryBuilder.where(Sleep_Sleepace_DataModelDao.Properties.UserId.eq(userId),Sleep_Sleepace_DataModelDao.Properties.ReportId.eq(0), Sleep_Sleepace_DataModelDao
                .Properties.Timestamp.ge(timeTamp)).orderDesc
                (Sleep_Sleepace_DataModelDao.Properties.Timestamp);
//        .distinct()
        List<Sleep_Sleepace_DataModel> list = queryBuilder.list();
        if (list.size() > 0) {
            return list;
        } else {
            return null;
        }
    }

    /**
     * 查询deviceId的设备抬腕亮屏,默认值为false，关闭
     */
    public static List<Sleep_Sleepace_DataModel> findSleep_Sleepace_DataModelsByDeviceId(String userId) {
        QueryBuilder<Sleep_Sleepace_DataModel> queryBuilder = BleAction.getDaoSession().queryBuilder
                (Sleep_Sleepace_DataModel.class);
        queryBuilder.where(Sleep_Sleepace_DataModelDao.Properties.UserId.eq(userId)).orderAsc
                (Sleep_Sleepace_DataModelDao.Properties.DateStr).distinct();
        List<Sleep_Sleepace_DataModel> sleep_Sleepace_DataModels = new ArrayList<>();
        List<Sleep_Sleepace_DataModel> list = queryBuilder.list();
        if (list.size() > 0) {
            if (list.size() == 1) {
                sleep_Sleepace_DataModels.add(list.get(0));
            } else {
                sleep_Sleepace_DataModels.add(list.get(list.size() - 1));
                sleep_Sleepace_DataModels.add(list.get(list.size() - 2));
            }
            return sleep_Sleepace_DataModels;
        } else {
            return null;
        }
    }

    public static List<Sleep_Sleepace_DataModel> getAll() {
        Sleep_Sleepace_DataModelDao sleep_sleepace_dataModelDao = BleAction.getSleep_Sleepace_DataModelDao();
        List<Sleep_Sleepace_DataModel> messages = sleep_sleepace_dataModelDao.loadAll();
        if (messages.size() > 0) {
            return messages;
        } else {
            return null;
        }
    }

    /**
     * 所有月
     *
     * @return
     */
    public static List<String> getListDateStr() {
        String queryString = "SELECT DISTINCT "
                + Sleep_Sleepace_DataModelDao.Properties.DateStr
                .columnName
                + " FROM "
                + Sleep_Sleepace_DataModelDao.TABLENAME;

        ArrayList<String> result = new ArrayList<String>();
        Cursor c = BleAction.getDaoSession().getDatabase().rawQuery(queryString, null);
        try {
            if (c.moveToFirst()) {
                do {
                    result.add(c.getString(0));
                }
                while (c.moveToNext());
            }
        } finally {
            c.close();
        }
        return result;
    }


    /**
     * 指定月查询
     *
     * @param dateStr
     * @return
     */
    public static List<String> listCurrentDateStr(String dateStr) {
        //查询某一字段中不重复的字段内容
        String SQL_DISTINCT_ENAME = "SELECT DISTINCT " + Sleep_Sleepace_DataModelDao.Properties
                .DateStr.columnName + " FROM " + Sleep_Sleepace_DataModelDao.TABLENAME + " WHERE "
                + Sleep_Sleepace_DataModelDao.Properties.DateStr.columnName + " LIKE " + "'%" + dateStr + "%' " + " " +
                "ORDER BY " + Sleep_Sleepace_DataModelDao.Properties.DateStr.columnName + " ASC";
        ArrayList<String> result = new ArrayList<String>();
        Cursor c = BleAction.getDaoSession().getDatabase().rawQuery(SQL_DISTINCT_ENAME, null);
        try {
            if (c.moveToFirst()) {
                do {
                    result.add(c.getString(0));
                }
                while (c.moveToNext());
            }
        } finally {
            c.close();
        }
        return result;
    }






}
