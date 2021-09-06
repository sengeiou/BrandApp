package com.isport.blelibrary.db.action;

import com.isport.blelibrary.db.parse.ParseData;
import com.isport.blelibrary.db.table.DeviceTypeTable;
import com.isport.blelibrary.gen.DeviceTypeTableDao;
import com.isport.blelibrary.utils.ThreadPoolUtils;

import org.greenrobot.greendao.query.QueryBuilder;

import java.util.List;

/**
 * @Author
 * @Date 2019/1/7
 * @Fuction
 */

public class DeviceTypeTableAction {

    /**
     * 查询是否存储过了type的设备
     */
    public static boolean hasStoreDeviceType(int deviceType, String userId) {
        QueryBuilder<DeviceTypeTable> queryBuilder = BleAction.getDaoSession().queryBuilder(DeviceTypeTable.class);
        queryBuilder.where(DeviceTypeTableDao.Properties.DeviceType.eq(deviceType), DeviceTypeTableDao.Properties
                .UserId.eq(userId));
        if (queryBuilder.list().size() > 0) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 查询是否存储过了type的设备
     */
    public static DeviceTypeTable findDeviceType(int deviceType, String userId) {
        QueryBuilder<DeviceTypeTable> queryBuilder = BleAction.getDaoSession().queryBuilder(DeviceTypeTable.class);
        queryBuilder.where(DeviceTypeTableDao.Properties.DeviceType.eq(deviceType), DeviceTypeTableDao.Properties
                .UserId.eq(userId));
        List<DeviceTypeTable> list = queryBuilder.list();
        if (null == list || list.size() == 0) {
            return null;
        } else {
            return list.get(0);
        }
    }

    /**
     * 解绑设备
     */
    public static void deleteDeviceByType(int deviceType, String userId) {
        DeviceTypeTableDao deviceTypeTableDao = BleAction.getDeviceTypeTableDao();
        DeviceTypeTable deviceTypeTable = findDeviceType(deviceType, userId);
        if (deviceTypeTable != null) {
            deviceTypeTableDao.delete(deviceTypeTable);
        }
    }

    /**
     * 解绑设备
     */
    public static void deleteAllDevices() {
        ThreadPoolUtils.getInstance().addTask(new Runnable() {
            @Override
            public void run() {
                DeviceTypeTableDao deviceTypeTableDao = BleAction.getDeviceTypeTableDao();
                deviceTypeTableDao.deleteAll();
            }
        });

    }

    /**
     * 查询所有设备列表
     *
     * @return
     */
    public static List<DeviceTypeTable> getAll(String userId) {
        QueryBuilder<DeviceTypeTable> queryBuilder = BleAction.getDaoSession().queryBuilder(DeviceTypeTable.class);
        queryBuilder.where(DeviceTypeTableDao.Properties.UserId.eq(userId));
        if (queryBuilder.list().size() > 0) {
            return queryBuilder.list();
        } else {
            return null;
        }
    }

    /**
     * 更新或删除
     */
    public static void updateOrDelete(int deviceType, String mac, String deviceId, String userId, String deviceName) {
        ParseData.saveDeviceType(deviceType, mac, deviceId, userId, deviceName);
        //如果为空，说明不存在的说,那么直接插入
    }

}
