package brandapp.isport.com.basicres.action;

import android.text.TextUtils;

import org.greenrobot.greendao.query.QueryBuilder;

import brandapp.isport.com.basicres.commonutil.Logger;
import brandapp.isport.com.basicres.commonutil.ThreadPoolUtils;
import brandapp.isport.com.basicres.entry.WatchTargetBean;
import brandapp.isport.com.basicres.gen.WatchTargetBeanDao;

/**
 * @创建者 bear
 * @创建时间 2019/3/1 18:05
 * @描述
 */
public class WatchTargetBeanAction {
    public static WatchTargetBean getWatchTargetBean(String userId, String deviceId) {
        try {
            if (TextUtils.isEmpty(deviceId)) {
                return null;
            }
            QueryBuilder<WatchTargetBean> queryBuilder = BaseAction.getDaoSession().queryBuilder(WatchTargetBean.class);
            queryBuilder.where(WatchTargetBeanDao.Properties.UserId.eq(userId), WatchTargetBeanDao.Properties.DeviceId.eq(deviceId));
            if (queryBuilder.list().size() > 0) {
                com.isport.blelibrary.utils.Logger.myLog("getWatchTargetBean" + queryBuilder.list().get(0).toString());
                return queryBuilder.list().get(0);
            } else {
                return null;
            }
        } catch (Exception e) {
            return null;
        }

    }
}
