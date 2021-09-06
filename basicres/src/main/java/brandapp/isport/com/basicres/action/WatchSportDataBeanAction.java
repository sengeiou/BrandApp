package brandapp.isport.com.basicres.action;

import org.greenrobot.greendao.query.QueryBuilder;

import java.util.List;

import brandapp.isport.com.basicres.entry.WatchSportDataBean;
import brandapp.isport.com.basicres.gen.WatchSportDataBeanDao;

/**
 * @Author
 * @Date 2018/11/6
 * @Fuction
 */

public class WatchSportDataBeanAction {

    public static List<WatchSportDataBean> getWatchSportData(String lastDate, String name) {
        QueryBuilder<WatchSportDataBean> queryBuilder = BaseAction.getDaoSession().queryBuilder(WatchSportDataBean.class);
        queryBuilder.where(WatchSportDataBeanDao.Properties.DateStr.gt(lastDate), WatchSportDataBeanDao.Properties.Mac.eq(name));
        if (queryBuilder.list().size() > 0) {
            return queryBuilder.list();
        } else {
            return null;
        }
    }
}
