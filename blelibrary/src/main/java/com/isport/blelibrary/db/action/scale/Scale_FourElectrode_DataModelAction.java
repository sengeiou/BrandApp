package com.isport.blelibrary.db.action.scale;

import com.isport.blelibrary.db.action.BleAction;
import com.isport.blelibrary.db.table.scale.Scale_FourElectrode_DataModel;
import com.isport.blelibrary.gen.Scale_FourElectrode_DataModelDao;

import org.greenrobot.greendao.query.QueryBuilder;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author
 * @Date 2019/1/12
 * @Fuction
 */

public class Scale_FourElectrode_DataModelAction {

    /**
     * 查询deviceId的设备抬腕亮屏,默认值为false，关闭
     */
    public static List<Scale_FourElectrode_DataModel> findScaleFourElectrodeDataModelByDeviceId(String userId,int number) {

        //需要查询7条数据

        QueryBuilder<Scale_FourElectrode_DataModel> queryBuilder = BleAction.getDaoSession().queryBuilder
                (Scale_FourElectrode_DataModel.class);
        queryBuilder.where(Scale_FourElectrode_DataModelDao.Properties.UserId.eq(userId)).orderDesc
                (Scale_FourElectrode_DataModelDao.Properties.Timestamp).distinct().limit(number).offset(0);
        List<Scale_FourElectrode_DataModel> scale_fourElectrode_dataModels = new ArrayList<>();
        List<Scale_FourElectrode_DataModel> list = queryBuilder.list();

        //  return list;
        if (list.size() > 0) {
            int size = 7;
            if (list.size() < 7) {
                size = list.size();
            }
            for (int i = 0; i < size; i++) {
                scale_fourElectrode_dataModels.add(list.get(size - 1 - i));
              //  Logger.myLog("scale_fourElectrode_dataModels" + list.get(size - 1 - i));
            }
            return scale_fourElectrode_dataModels;
        } else {
            return null;
        }
    }

    /**
     * 根据deviceId、TimeTamp查询
     */
    public static Scale_FourElectrode_DataModel findScaleFourElectrodeDataModelByDeviceIdAndTimeTamp(long timeTamp,
                                                                                                     String userId) {
        QueryBuilder<Scale_FourElectrode_DataModel> queryBuilder = BleAction.getDaoSession().queryBuilder
                (Scale_FourElectrode_DataModel.class);
        queryBuilder.where(Scale_FourElectrode_DataModelDao.Properties.Timestamp.eq(timeTamp),
                Scale_FourElectrode_DataModelDao.Properties.UserId.eq(userId));
        List<Scale_FourElectrode_DataModel> list = queryBuilder.list();
        if (list.size() > 0) {
            return list.get(0);
        } else {
            return null;
        }
    }

    /**
     * 查询所有设备列表
     *
     * @return
     */
    public static List<Scale_FourElectrode_DataModel> getAll() {
        Scale_FourElectrode_DataModelDao scale_fourElectrode_dataModelDao = BleAction
                .getScale_FourElectrode_DataModelDao();
        List<Scale_FourElectrode_DataModel> messages = scale_fourElectrode_dataModelDao.loadAll();
        if (messages.size() > 0) {
            return messages;
        } else {
            return null;
        }
    }

    /**
     * 查询所有设备列表
     *
     * @return
     */
    public static List<Scale_FourElectrode_DataModel> getAllUnUpdate(String
                                                                             userId) {
        QueryBuilder<Scale_FourElectrode_DataModel> queryBuilder = BleAction.getDaoSession().queryBuilder
                (Scale_FourElectrode_DataModel.class);
        queryBuilder.where(Scale_FourElectrode_DataModelDao.Properties.ReportId.eq(0),
                Scale_FourElectrode_DataModelDao.Properties.UserId.eq(userId)).orderDesc
                (Scale_FourElectrode_DataModelDao.Properties.Timestamp).distinct();
        List<Scale_FourElectrode_DataModel> list = queryBuilder.list();
        if (list.size() > 0) {
            return list;
        } else {
            return null;
        }
    }

    /**
     * 查询指定日期体重记录
     *
     * @return
     */
    public static Scale_FourElectrode_DataModel getWeightByDateStr(String userId, String dateStr) {
        QueryBuilder<Scale_FourElectrode_DataModel> queryBuilder = BleAction.getDaoSession().queryBuilder
                (Scale_FourElectrode_DataModel.class);
        queryBuilder.where(
                Scale_FourElectrode_DataModelDao.Properties.UserId.eq(userId), Scale_FourElectrode_DataModelDao
                        .Properties.DateStr.like("%" + dateStr + "%")).orderDesc
                (Scale_FourElectrode_DataModelDao.Properties.Timestamp).distinct();
        List<Scale_FourElectrode_DataModel> list = queryBuilder.list();
        if (list.size() > 0) {
            return list.get(0);
        } else {
            return null;
        }
    }

    /**
     * 删除指定的时间的查询指定日期体重记录
     *
     * @return
     */
    public static void deletWeightByDateStr(Scale_FourElectrode_DataModel weightByDateStr) {
        BleAction.getDaoSession().delete(weightByDateStr);

    }

    /**
     * 根据月来查询数据，模糊查询
     *
     * @return
     */
    public static List<Scale_FourElectrode_DataModel> getAllMonthData(String monthStrFirst, String monthStrEnd, String
            userId) {
        QueryBuilder<Scale_FourElectrode_DataModel> queryBuilder = BleAction.getDaoSession().queryBuilder
                (Scale_FourElectrode_DataModel.class);
        queryBuilder.where(Scale_FourElectrode_DataModelDao.Properties.DateStr.le(monthStrEnd),
                Scale_FourElectrode_DataModelDao.Properties.DateStr.ge(monthStrFirst),
                Scale_FourElectrode_DataModelDao.Properties.UserId.eq(userId)).orderDesc
                (Scale_FourElectrode_DataModelDao.Properties.Timestamp).distinct();
        List<Scale_FourElectrode_DataModel> list = queryBuilder.list();
        if (list.size() > 0) {
            return list;
        } else {
            return null;
        }
    }

    /**
     * 根据月来查询数据，模糊查询,之前还有没有数据
     * 小于等于这个月是否还有数据的情况
     *
     * @return
     */
    public static List<Scale_FourElectrode_DataModel> getLastMonthData(String monthStr, String userId) {
        QueryBuilder<Scale_FourElectrode_DataModel> queryBuilder = BleAction.getDaoSession().queryBuilder
                (Scale_FourElectrode_DataModel.class);
        queryBuilder.where(Scale_FourElectrode_DataModelDao.Properties.DateStr.lt(monthStr),
                Scale_FourElectrode_DataModelDao.Properties.UserId.eq(userId)).orderDesc
                (Scale_FourElectrode_DataModelDao.Properties.Timestamp).distinct();
        List<Scale_FourElectrode_DataModel> list = queryBuilder.list();
        if (list.size() > 0) {
            return list;
        } else {
            return null;
        }
    }

    /**
     * 根据月来查询数据，模糊查询,之前还有没有数据
     * 小于等于这个月是否还有数据的情况
     *
     * @return
     */
    public static List<Scale_FourElectrode_DataModel> getAllLastMonthData(String monthStr, String
            userId) {
        QueryBuilder<Scale_FourElectrode_DataModel> queryBuilder = BleAction.getDaoSession().queryBuilder
                (Scale_FourElectrode_DataModel.class);
        queryBuilder.where(Scale_FourElectrode_DataModelDao.Properties.DateStr.le(monthStr),
                Scale_FourElectrode_DataModelDao.Properties.UserId.eq(userId)).orderDesc
                (Scale_FourElectrode_DataModelDao.Properties.Timestamp).distinct();
        List<Scale_FourElectrode_DataModel> list = queryBuilder.list();
        if (list.size() > 0) {
            return list;
        } else {
            return null;
        }
    }
}
