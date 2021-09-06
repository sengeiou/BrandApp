package brandapp.isport.com.basicres.action;

import android.text.TextUtils;

import org.greenrobot.greendao.query.QueryBuilder;

import brandapp.isport.com.basicres.entry.WatchRealTimeData;
import brandapp.isport.com.basicres.gen.WatchRealTimeDataDao;

/**
 * @Author
 * @Date 2018/11/5
 * @Fuction
 */

public class WatchRealTimeDataAction {

    public static WatchRealTimeData getWatchRealTimeData(String lastDate, String name) {
        if (TextUtils.isEmpty(lastDate) || TextUtils.isEmpty(name)) {
            return null;
        }
        QueryBuilder<WatchRealTimeData> queryBuilder = BaseAction.getDaoSession().queryBuilder(WatchRealTimeData.class);
        queryBuilder.where(WatchRealTimeDataDao.Properties.Date.like(lastDate), WatchRealTimeDataDao.Properties.Mac.eq(name));
        if (queryBuilder.list().size() > 0) {
            return queryBuilder.list().get(0);
        } else {
            return null;
        }
    }
}
