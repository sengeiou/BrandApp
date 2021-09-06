package brandapp.isport.com.basicres.action;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import brandapp.isport.com.basicres.gen.DaoMaster;
import brandapp.isport.com.basicres.gen.DaoSession;
import brandapp.isport.com.basicres.gen.ScaleCalculateBeanDao;
import brandapp.isport.com.basicres.gen.SportBeanDao;
import brandapp.isport.com.basicres.gen.UserInformationBeanDao;
import brandapp.isport.com.basicres.gen.WatchRealTimeDataDao;
import brandapp.isport.com.basicres.gen.WatchSportDataBeanDao;
import brandapp.isport.com.basicres.gen.WatchTargetBeanDao;


/**
 * @Author
 * @Date 2017/9/10
 * @Fuction
 */

public class BaseAction {

    //lt 小于  le小于等于  gt大于 ge大于等于 eq等于


    private static SQLiteDatabase         sSQLiteDatabase;
    private static DaoMaster              sDaoMaster;
    private static DaoSession             sDaoSession;
    private static ScaleCalculateBeanDao  sScaleCalculateBeanDao;
    private static WatchRealTimeDataDao   sWatchRealTimeDataDao;
    private static WatchSportDataBeanDao  sWatchSportDataBeanDao;
    private static UserInformationBeanDao sUserInformationBeanDao;
    private static SportBeanDao           sSportDataAction;
    private static WatchTargetBeanDao     sWatchTargetBeanDao;


    public static ScaleCalculateBeanDao getScaleCalculateBeanDao() {
        return sScaleCalculateBeanDao;
    }

    public static WatchRealTimeDataDao getWatchRealTimeDataDao() {
        return sWatchRealTimeDataDao;
    }

    public static WatchSportDataBeanDao getWatchSportDataBeanDao() {
        return sWatchSportDataBeanDao;
    }

    public static UserInformationBeanDao getUserInformationBeanDao() {
        return sUserInformationBeanDao;
    }

    public static SportBeanDao getSportBeanDao() {
        return sSportDataAction;
    }

    public static WatchTargetBeanDao getWatchTargetBeanDao() {
        return sWatchTargetBeanDao;
    }


    public static DaoSession getDaoSession() {
        return sDaoSession;
    }

    public static void init(Context context) {
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(context, "isport_brandapp_db", null);
        sSQLiteDatabase = helper.getWritableDatabase();
        sDaoMaster = new DaoMaster(sSQLiteDatabase);
        sDaoSession = sDaoMaster.newSession();
        sScaleCalculateBeanDao = sDaoSession.getScaleCalculateBeanDao();
        sWatchRealTimeDataDao = sDaoSession.getWatchRealTimeDataDao();
        sWatchSportDataBeanDao = sDaoSession.getWatchSportDataBeanDao();
        sUserInformationBeanDao = sDaoSession.getUserInformationBeanDao();
        sSportDataAction = sDaoSession.getSportBeanDao();
        sWatchTargetBeanDao = sDaoSession.getWatchTargetBeanDao();
    }

    public static void dropDatas() {
        /*ThreadPoolUtils.getInstance().addTask(new Runnable() {
            @Override
            public void run() {
                sScaleCalculateBeanDao.deleteAll();
                sWatchRealTimeDataDao .deleteAll();
                sWatchSportDataBeanDao .deleteAll();
                sUserInformationBeanDao .deleteAll();
                sSportDataAction .deleteAll();
                sWatchTargetBeanDao.deleteAll();
            }
        });*/
    }
}
