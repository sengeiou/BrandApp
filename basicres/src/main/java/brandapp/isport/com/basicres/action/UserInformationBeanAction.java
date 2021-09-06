package brandapp.isport.com.basicres.action;


import org.greenrobot.greendao.query.QueryBuilder;

import brandapp.isport.com.basicres.entry.UserInformationBean;
import brandapp.isport.com.basicres.gen.UserInformationBeanDao;

/**
 * @Author
 * @Date 2019/1/8
 * @Fuction
 */

public class UserInformationBeanAction {

    /**
     * 设备是否已经存储过了
     * @param userId
     * @return
     */
    public static boolean hasStoreUserInfo(String userId) {
        QueryBuilder<UserInformationBean> queryBuilder = BaseAction.getDaoSession().queryBuilder(UserInformationBean.class);
        queryBuilder.where(UserInformationBeanDao.Properties.UserId.eq(userId));
        if (queryBuilder.list().size() > 0) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 查询指定userId的用户信息
     * @param userId
     * @return
     */
    public static UserInformationBean findUserInfoByUserId(String userId) {
        QueryBuilder<UserInformationBean> queryBuilder = BaseAction.getDaoSession().queryBuilder(UserInformationBean.class);
        queryBuilder.where(UserInformationBeanDao.Properties.UserId.eq(userId));
        if (queryBuilder.list().size() > 0) {
            return queryBuilder.list().get(0);
        } else {
            return null;
        }
    }
}
