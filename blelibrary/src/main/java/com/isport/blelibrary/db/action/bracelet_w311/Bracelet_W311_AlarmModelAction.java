package com.isport.blelibrary.db.action.bracelet_w311;

import android.text.TextUtils;

import com.isport.blelibrary.db.action.BleAction;
import com.isport.blelibrary.db.table.bracelet_w311.Bracelet_W311_AlarmModel;
import com.isport.blelibrary.gen.Bracelet_W311_AlarmModelDao;
import com.isport.blelibrary.utils.Logger;
import com.isport.blelibrary.utils.ThreadPoolUtils;

import org.greenrobot.greendao.query.QueryBuilder;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author
 * @Date 2019/2/24
 * @Fuction
 */

public class Bracelet_W311_AlarmModelAction {

    /**
     * 查询deviceId的设备闹钟
     */
    public static ArrayList<Bracelet_W311_AlarmModel> findBracelet_W311_AlarmModelByDeviceId(String deviceId, String userId) {
        if (TextUtils.isEmpty(deviceId)||TextUtils.isEmpty(userId)) {
            return null;
        }
        QueryBuilder<Bracelet_W311_AlarmModel> queryBuilder = BleAction.getDaoSession().queryBuilder(Bracelet_W311_AlarmModel.class);
        queryBuilder.where(Bracelet_W311_AlarmModelDao.Properties.DeviceId.eq(deviceId), Bracelet_W311_AlarmModelDao.Properties.UserId.eq(userId));
        if (queryBuilder.list().size() > 0) {
            ArrayList<Bracelet_W311_AlarmModel> watch_W516_AlarmModel = (ArrayList<Bracelet_W311_AlarmModel>) queryBuilder.list();
            return watch_W516_AlarmModel;
        } else {
            return null;
        }
    }

    public static Bracelet_W311_AlarmModel findBracelet_W311_AlarmModelByDeviceId(String deviceId, String userId, int index) {
        if(TextUtils.isEmpty(deviceId) || TextUtils.isEmpty(userId)){
            return null;
        }
        QueryBuilder<Bracelet_W311_AlarmModel> queryBuilder = BleAction.getDaoSession().queryBuilder(Bracelet_W311_AlarmModel.class);
        queryBuilder.where(Bracelet_W311_AlarmModelDao.Properties.DeviceId.eq(deviceId), Bracelet_W311_AlarmModelDao.Properties.UserId.eq(userId), Bracelet_W311_AlarmModelDao.Properties.AlarmId.eq(index));
        if (queryBuilder.list().size() > 0) {
            ArrayList<Bracelet_W311_AlarmModel> watch_W516_AlarmModel = (ArrayList<Bracelet_W311_AlarmModel>) queryBuilder.list();
            return watch_W516_AlarmModel.get(0);
        } else {
            return null;
        }
    }


    public static void saveOrUpdateBraceletW311AlarmModele(final List<Bracelet_W311_AlarmModel> models, final String deviceId, final String userId) {

        //先删除
        // deletBracelet(deviceId, userId);
        ThreadPoolUtils.getInstance().addTask(new Runnable() {
            @Override
            public void run() {
                if (TextUtils.isEmpty(deviceId)) {
                    return;
                }
                Bracelet_W311_AlarmModelDao deviceTypeTableDao = BleAction.getBracelet_W311_AlarmModelDao();

                deviceTypeTableDao.deleteAll();

               // ArrayList<Bracelet_W311_AlarmModel> deviceTypeTable = findBracelet_W311_AlarmModelByDeviceId(deviceId, userId);
               /* if (deviceTypeTable != null) {
                    for (int i = 0; i < deviceTypeTable.size(); i++) {
                        deviceTypeTableDao.delete(deviceTypeTable.get(i));
                    }
                }*/
                for (int i = 0; i < models.size(); i++) {
                    Bracelet_W311_AlarmModel model = models.get(i);
                    Bracelet_W311_AlarmModelDao bracelet_w311_displayModelDao = BleAction.getBracelet_W311_AlarmModelDao();
                    bracelet_w311_displayModelDao.insertOrReplace(model);
                }
            }
        });


    }

    public static synchronized void delectAlarmModel() {
        ThreadPoolUtils.getInstance().addTask(new Runnable() {
            @Override
            public void run() {
                Bracelet_W311_AlarmModelDao bracelet_w311_displayModelDao = BleAction.getBracelet_W311_AlarmModelDao();
                bracelet_w311_displayModelDao.deleteAll();
            }
        });


    }


    public static synchronized void saveW526AlarmBean(int index, String userId, String deviceId, int reapeat, String strTime, boolean enable) {
        if(TextUtils.isEmpty(deviceId) || TextUtils.isEmpty(userId)){
            return;
        }

        Bracelet_W311_AlarmModelDao bracelet_w311_displayModelDao = BleAction.getBracelet_W311_AlarmModelDao();
        Bracelet_W311_AlarmModel bean = new Bracelet_W311_AlarmModel();
        bean.setAlarmId(index);
        bean.setDeviceId(deviceId);
        bean.setUserId(userId);
        bean.setIsOpen(enable);
        bean.setMessageString("123");
        bean.setRepeatCount(reapeat);
        bean.setTimeString(strTime);
        bracelet_w311_displayModelDao.insertOrReplace(bean);
    }

    public static synchronized void saveW526AlarmBean(Bracelet_W311_AlarmModel model) {
        Bracelet_W311_AlarmModelDao bracelet_w311_displayModelDao = BleAction.getBracelet_W311_AlarmModelDao();
        Bracelet_W311_AlarmModel bean = findBracelet_W311_AlarmModelByDeviceId(model.getDeviceId(), model.getUserId(), model.getAlarmId());


        if (bean != null) {
            Logger.myLog("saveW526AlarmBean :" + bean.toString());
            bean.setMessageString(model.getMessageString());
            bean.setTimeString(model.getTimeString());
            bean.setRepeatCount(model.getRepeatCount());
            bean.setIsOpen(model.getIsOpen());
            bracelet_w311_displayModelDao.insertOrReplace(bean);
        } else {
            Logger.myLog("saveW526AlarmBean :" + bean);
            bracelet_w311_displayModelDao.insertOrReplace(model);
        }
    }

    public static synchronized void saveW311AlarmModel(final List<Bracelet_W311_AlarmModel> list) {
        Logger.myLog("saveW311AlarmModel:");
        ThreadPoolUtils.getInstance().addTask(new Runnable() {
            @Override
            public void run() {
                ArrayList<Bracelet_W311_AlarmModel> tempList = new ArrayList();
                tempList.addAll(list);
                Bracelet_W311_AlarmModelDao bracelet_w311_displayModelDao = BleAction.getBracelet_W311_AlarmModelDao();
                bracelet_w311_displayModelDao.deleteAll();
                if (tempList != null) {
                    for (int i = 0; i < tempList.size(); i++) {
                        bracelet_w311_displayModelDao.insertOrReplace(tempList.get(i));
                    }
                }


                Logger.myLog("saveW311AlarmModel:" + tempList);

            }
        });
    }

    public static void saveOrUpdateBraceletW311AlarmModele(final Bracelet_W311_AlarmModel models) {

        //先删除
        ThreadPoolUtils.getInstance().addTask(new Runnable() {
            @Override
            public void run() {
                Logger.myLog("saveOrUpdateBraceletW311AlarmModele:" + models);

                Bracelet_W311_AlarmModelDao bracelet_w311_displayModelDao = BleAction.getBracelet_W311_AlarmModelDao();
                bracelet_w311_displayModelDao.insertOrReplace(models);
            }
        });


    }


    /**
     *
     */
    public static void deletBracelet(final String deviceId, final String userId) {
        ThreadPoolUtils.getInstance().addTask(new Runnable() {
            @Override
            public void run() {
                if(TextUtils.isEmpty(deviceId) || TextUtils.isEmpty(userId)){
                    return ;
                }
                Bracelet_W311_AlarmModelDao deviceTypeTableDao = BleAction.getBracelet_W311_AlarmModelDao();
                ArrayList<Bracelet_W311_AlarmModel> deviceTypeTable = findBracelet_W311_AlarmModelByDeviceId(deviceId, userId);
                if (deviceTypeTable != null) {
                    for (int i = 0; i < deviceTypeTable.size(); i++) {
                        deviceTypeTableDao.delete(deviceTypeTable.get(i));
                    }
                }
            }
        });
    }

    public static void deletBraceletalarmItem(Bracelet_W311_AlarmModel model) {
        Bracelet_W311_AlarmModelDao deviceTypeTableDao = BleAction.getBracelet_W311_AlarmModelDao();
        deviceTypeTableDao.delete(model);
    }

}
