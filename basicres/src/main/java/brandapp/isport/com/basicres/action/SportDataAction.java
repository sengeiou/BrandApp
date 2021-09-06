package brandapp.isport.com.basicres.action;

import org.greenrobot.greendao.query.QueryBuilder;

import java.util.ArrayList;
import java.util.List;

import brandapp.isport.com.basicres.commonutil.Logger;
import brandapp.isport.com.basicres.entry.SportBean;
import brandapp.isport.com.basicres.entry.UserInformationBean;
import brandapp.isport.com.basicres.gen.SportBeanDao;
import brandapp.isport.com.basicres.gen.UserInformationBeanDao;

public class SportDataAction {

    /**
     * 查询指定userId的用户信息
     *
     * @param userId
     * @return
     */
    public static SportBean findUserInfoByUserIdAndPublicId(int userId, int publicId) {
        try {
            QueryBuilder<SportBean> queryBuilder = BaseAction.getDaoSession().queryBuilder(SportBean.class);

            queryBuilder.where(SportBeanDao.Properties.UserId.eq(userId), SportBeanDao.Properties.PublicId.eq(publicId));
            if (queryBuilder.list().size() > 0) {
                return queryBuilder.list().get(0);
            } else {
                return null;
            }
        } catch (Exception e) {
            return null;
        }
    }

    public static SportBean getLastData(long id) {
        try {
            return BaseAction.getSportBeanDao().load(id);
        } catch (Exception e) {
            return null;
        }
    }

    public static SportBean getFisrt() {
        try {

            ArrayList<SportBean> list = (ArrayList<SportBean>) BaseAction.getSportBeanDao().loadAll();
            if (list != null && list.size() > 0) {
                list.get(0);

                com.isport.blelibrary.utils.Logger.myLog(list.get(0).toString());
            } else {
                return null;
            }
        } catch (Exception e) {
            return null;
        }

        return null;
    }

    /**
     * 保存用户的数据
     *
     * @param sportBean
     */

    public static long saveSportData(SportBean sportBean) {
        List<SportBean> lists = BaseAction.getSportBeanDao().loadAll();
        try {
            return BaseAction.getSportBeanDao().insertOrReplace(sportBean);

        } catch (Exception e) {

            return 0;
        }
    }

    /**
     * 删除用户运动的数据
     *
     * @param key
     * @return
     */
    public static boolean deletSportBean(long key) {
        try {
            BaseAction.getSportBeanDao().deleteByKey(key);
            return true;

        } catch (Exception e) {
            return true;
        }

    }
}
