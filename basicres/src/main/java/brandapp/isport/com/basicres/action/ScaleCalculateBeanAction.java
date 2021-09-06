package brandapp.isport.com.basicres.action;


import java.util.List;

import brandapp.isport.com.basicres.entry.ScaleCalculateBean;
import brandapp.isport.com.basicres.gen.ScaleCalculateBeanDao;

/**
 * @Author Administrator
 * @Date 2017/12/21
 * @Fuction
 */

public class ScaleCalculateBeanAction {

    public static List<ScaleCalculateBean> getAll(){
        ScaleCalculateBeanDao scaleCalculateBeanDao = BaseAction.getScaleCalculateBeanDao();
        List<ScaleCalculateBean> messages = scaleCalculateBeanDao.loadAll();
        if (messages.size() > 0) {
            return messages;
        } else {
            return null;
        }
    }

//    public static boolean getSleepData(ScaleCalculateBeanDao scaleCalculateBeanDao, String lastDate){
//        QueryBuilder<ScaleCalculateBean> queryBuilder = BleAction.getDaoSession().queryBuilder(ScaleCalculateBean.class);
//        queryBuilder.where(ScaleCalculateBeanDao.Properties.Date.like(lastDate));
//        if (queryBuilder.list().size() > 0) {
//            return true;
//        } else {
//            return false;
//        }
//    }
//
//    public static List<ScaleCalculateBean> getSleepData( String lastDate){
//        QueryBuilder<ScaleCalculateBean> queryBuilder = BleAction.getDaoSession().queryBuilder(ScaleCalculateBean.class);
//        queryBuilder.where(ScaleCalculateBeanDao.Properties.Date.like(lastDate));
//        if (queryBuilder.list().size() > 0) {
//            return queryBuilder.list();
//        } else {
//            return null;
//        }
//    }
}
