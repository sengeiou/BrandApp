package com.isport.blelibrary.db.action.watch;

import android.text.TextUtils;

import com.isport.blelibrary.db.action.BleAction;
import com.isport.blelibrary.db.table.watch.Watch_SmartBand_SportDataModel;
import com.isport.blelibrary.gen.Watch_SmartBand_SportDataModelDao;
import com.isport.blelibrary.utils.Logger;
import com.isport.blelibrary.utils.TimeUtils;

import org.greenrobot.greendao.query.QueryBuilder;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * @Author
 * @Date 2019/1/15
 * @Fuction
 */

public class Watch_SmartBand_SportDataModelAction {


    /**
     * 查询是否存储过了type的设备
     */
    public static boolean hasStore(String deviceId, String dateStr) {
        if (TextUtils.isEmpty(deviceId)||TextUtils.isEmpty(dateStr)){
            return false;
        }
        QueryBuilder<Watch_SmartBand_SportDataModel> queryBuilder = BleAction.getDaoSession().queryBuilder
                (Watch_SmartBand_SportDataModel.class);
        queryBuilder.where(Watch_SmartBand_SportDataModelDao.Properties.DeviceId.eq(deviceId),
                           Watch_SmartBand_SportDataModelDao.Properties.DateStr.eq(dateStr));
        if (queryBuilder.list().size() > 0) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 根据deviceId、TimeTamp查询
     */
    public static Watch_SmartBand_SportDataModel findWatchSmartBandSportDataModelByDeviceIdAndDateStr(String deviceId,
                                                                                                      String dateStr) {

        if (TextUtils.isEmpty(deviceId)||TextUtils.isEmpty(dateStr)){
            return null;
        }
        QueryBuilder<Watch_SmartBand_SportDataModel> queryBuilder = BleAction.getDaoSession().queryBuilder
                (Watch_SmartBand_SportDataModel.class);
        queryBuilder.where(Watch_SmartBand_SportDataModelDao.Properties.DeviceId.eq(deviceId),
                           Watch_SmartBand_SportDataModelDao.Properties.DateStr.eq(dateStr));
        List<Watch_SmartBand_SportDataModel> list = queryBuilder.list();
        if (list.size() > 0) {
            return list.get(0);
        } else {
            return null;
        }
    }

    /**
     * 查询deviceId的设备抬腕亮屏,默认值为false，关闭
     */
    public static Watch_SmartBand_SportDataModel findLtTodayWatchSmartBandSportDataModelByDeviceIdAndDateStr(String deviceId, String
            dateStr) {
        if (TextUtils.isEmpty(deviceId)||TextUtils.isEmpty(dateStr)){
            return null;
        }
        QueryBuilder<Watch_SmartBand_SportDataModel> queryBuilder = BleAction.getDaoSession().queryBuilder
                (Watch_SmartBand_SportDataModel.class);
        queryBuilder.where(Watch_SmartBand_SportDataModelDao.Properties.DeviceId.eq(deviceId),
                           Watch_SmartBand_SportDataModelDao.Properties.DateStr.lt(dateStr)).orderDesc
                (Watch_SmartBand_SportDataModelDao.Properties.Timestamp);
        List<Watch_SmartBand_SportDataModel> list = queryBuilder.list();
        if (list.size() > 0) {
            return list.get(0);
        } else {
            return null;
        }
    }

    /**
     * 查询指定deviceId timeTamp之前的20条数据
     */
    public static List<Watch_SmartBand_SportDataModel>
    findLtTodayTenDataWatchSmartBandSportDataModelByDeviceIdAndDateStr(String deviceId, long
            timestamp ) {
        if (TextUtils.isEmpty(deviceId)){
            return null;
        }
        QueryBuilder<Watch_SmartBand_SportDataModel> queryBuilder = BleAction.getDaoSession().queryBuilder
                (Watch_SmartBand_SportDataModel.class);
        queryBuilder.where(Watch_SmartBand_SportDataModelDao.Properties.DeviceId.eq(deviceId),
                           Watch_SmartBand_SportDataModelDao.Properties.DateStr.le(TimeUtils.getTimeByyyyyMMdd(timestamp))).orderDesc
                (Watch_SmartBand_SportDataModelDao.Properties.DateStr).limit(20);
        List<Watch_SmartBand_SportDataModel> list = queryBuilder.list();
        if (list.size() > 0) {
            return list;
        } else {
            return null;
        }
    }

//    limit，限制返回条数  10 保存最后条的时间戳，通过时间戳来查询<

    /**
     * 查询两周的数据，本周和上周的数据
     * 可复用，查当周的数据
     */
    public static ArrayList<Integer> findWatchSmartBandSportDataModelsByDeviceId(Calendar calendar,
                                                                                 String deviceId) {
        //查询
        QueryBuilder<Watch_SmartBand_SportDataModel> queryBuilder = BleAction.getDaoSession().queryBuilder
                (Watch_SmartBand_SportDataModel.class);
        int firstDayOfWeek = calendar.getFirstDayOfWeek();//第一天的位置
        int currentDayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);//当前位置
        Logger.myLog("firstDayOfWeek == " + firstDayOfWeek + "currentDayOfWeek == " + currentDayOfWeek);
        //移动到周一
        calendar.add(Calendar.DAY_OF_MONTH, 2 - currentDayOfWeek);//由于一周是从周日开始,所以会前移动
        Logger.myLog("calendar Day == " + TimeUtils.getTimeByyyyyMMdd(calendar));
        String timeByyyyyMMdd = TimeUtils.getTimeByyyyyMMdd(calendar);//周一的日期
        queryBuilder.where(Watch_SmartBand_SportDataModelDao.Properties.DeviceId.eq(deviceId),
                           Watch_SmartBand_SportDataModelDao.Properties.DateStr.eq(timeByyyyyMMdd));
        ArrayList<Integer> stepList = new ArrayList<>();
        List<Watch_SmartBand_SportDataModel> list = queryBuilder.list();
        if (list.size() > 0) {
            stepList.add(list.get(0).getTotalSteps());
            Logger.myLog("calendar Day step== " + list.get(0).getTotalSteps());
        } else {
            stepList.add(0);
        }
        for (int i = 0; i < 6; i++) {
            queryBuilder = BleAction.getDaoSession().queryBuilder
                    (Watch_SmartBand_SportDataModel.class);
            calendar.add(Calendar.DAY_OF_MONTH, 1);//每次移动一天一直移动到周日
            Logger.myLog("calendar Day == " + TimeUtils.getTimeByyyyyMMdd(calendar));
            timeByyyyyMMdd = TimeUtils.getTimeByyyyyMMdd(calendar);//周一的日期
            queryBuilder.where(Watch_SmartBand_SportDataModelDao.Properties.DeviceId.eq(deviceId),
                               Watch_SmartBand_SportDataModelDao.Properties.DateStr.eq(timeByyyyyMMdd));
            list = queryBuilder.list();
            if (list.size() > 0) {
                stepList.add(list.get(0).getTotalSteps());
                Logger.myLog("calendar Day step== " + list.get(0).getTotalSteps());
            } else {
                stepList.add(0);
            }
        }
//        String whichWeekMonday = TimeUtils.getWhichWeekMonday(TimeUtils.getTimeByyyyyMMdd(calendar), "yyyy-MM-dd",
//                                                              "yyyy-MM-dd");
//        String whichWeekSunday = TimeUtils.getWhichWeekSunday(TimeUtils.getTimeByyyyyMMdd(calendar), "yyyy-MM-dd",
//                                                              "yyyy-MM-dd");
//
//        queryBuilder.where(Watch_SmartBand_SportDataModelDao.Properties.DeviceId.eq(deviceId),
//                           Watch_SmartBand_SportDataModelDao.Properties.DateStr.le(whichWeekSunday),
//                           Watch_SmartBand_SportDataModelDao.Properties.DateStr.ge(whichWeekMonday));
        return stepList;
    }
}
