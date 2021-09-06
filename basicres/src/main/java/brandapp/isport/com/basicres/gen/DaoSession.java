package brandapp.isport.com.basicres.gen;

import java.util.Map;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.AbstractDaoSession;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.identityscope.IdentityScopeType;
import org.greenrobot.greendao.internal.DaoConfig;

import brandapp.isport.com.basicres.entry.ScaleCalculateBean;
import brandapp.isport.com.basicres.entry.SportBean;
import brandapp.isport.com.basicres.entry.UserInformationBean;
import brandapp.isport.com.basicres.entry.WatchRealTimeData;
import brandapp.isport.com.basicres.entry.WatchSportDataBean;
import brandapp.isport.com.basicres.entry.WatchTargetBean;

import brandapp.isport.com.basicres.gen.ScaleCalculateBeanDao;
import brandapp.isport.com.basicres.gen.SportBeanDao;
import brandapp.isport.com.basicres.gen.UserInformationBeanDao;
import brandapp.isport.com.basicres.gen.WatchRealTimeDataDao;
import brandapp.isport.com.basicres.gen.WatchSportDataBeanDao;
import brandapp.isport.com.basicres.gen.WatchTargetBeanDao;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.

/**
 * {@inheritDoc}
 * 
 * @see org.greenrobot.greendao.AbstractDaoSession
 */
public class DaoSession extends AbstractDaoSession {

    private final DaoConfig scaleCalculateBeanDaoConfig;
    private final DaoConfig sportBeanDaoConfig;
    private final DaoConfig userInformationBeanDaoConfig;
    private final DaoConfig watchRealTimeDataDaoConfig;
    private final DaoConfig watchSportDataBeanDaoConfig;
    private final DaoConfig watchTargetBeanDaoConfig;

    private final ScaleCalculateBeanDao scaleCalculateBeanDao;
    private final SportBeanDao sportBeanDao;
    private final UserInformationBeanDao userInformationBeanDao;
    private final WatchRealTimeDataDao watchRealTimeDataDao;
    private final WatchSportDataBeanDao watchSportDataBeanDao;
    private final WatchTargetBeanDao watchTargetBeanDao;

    public DaoSession(Database db, IdentityScopeType type, Map<Class<? extends AbstractDao<?, ?>>, DaoConfig>
            daoConfigMap) {
        super(db);

        scaleCalculateBeanDaoConfig = daoConfigMap.get(ScaleCalculateBeanDao.class).clone();
        scaleCalculateBeanDaoConfig.initIdentityScope(type);

        sportBeanDaoConfig = daoConfigMap.get(SportBeanDao.class).clone();
        sportBeanDaoConfig.initIdentityScope(type);

        userInformationBeanDaoConfig = daoConfigMap.get(UserInformationBeanDao.class).clone();
        userInformationBeanDaoConfig.initIdentityScope(type);

        watchRealTimeDataDaoConfig = daoConfigMap.get(WatchRealTimeDataDao.class).clone();
        watchRealTimeDataDaoConfig.initIdentityScope(type);

        watchSportDataBeanDaoConfig = daoConfigMap.get(WatchSportDataBeanDao.class).clone();
        watchSportDataBeanDaoConfig.initIdentityScope(type);

        watchTargetBeanDaoConfig = daoConfigMap.get(WatchTargetBeanDao.class).clone();
        watchTargetBeanDaoConfig.initIdentityScope(type);

        scaleCalculateBeanDao = new ScaleCalculateBeanDao(scaleCalculateBeanDaoConfig, this);
        sportBeanDao = new SportBeanDao(sportBeanDaoConfig, this);
        userInformationBeanDao = new UserInformationBeanDao(userInformationBeanDaoConfig, this);
        watchRealTimeDataDao = new WatchRealTimeDataDao(watchRealTimeDataDaoConfig, this);
        watchSportDataBeanDao = new WatchSportDataBeanDao(watchSportDataBeanDaoConfig, this);
        watchTargetBeanDao = new WatchTargetBeanDao(watchTargetBeanDaoConfig, this);

        registerDao(ScaleCalculateBean.class, scaleCalculateBeanDao);
        registerDao(SportBean.class, sportBeanDao);
        registerDao(UserInformationBean.class, userInformationBeanDao);
        registerDao(WatchRealTimeData.class, watchRealTimeDataDao);
        registerDao(WatchSportDataBean.class, watchSportDataBeanDao);
        registerDao(WatchTargetBean.class, watchTargetBeanDao);
    }
    
    public void clear() {
        scaleCalculateBeanDaoConfig.getIdentityScope().clear();
        sportBeanDaoConfig.getIdentityScope().clear();
        userInformationBeanDaoConfig.getIdentityScope().clear();
        watchRealTimeDataDaoConfig.getIdentityScope().clear();
        watchSportDataBeanDaoConfig.getIdentityScope().clear();
        watchTargetBeanDaoConfig.getIdentityScope().clear();
    }

    public ScaleCalculateBeanDao getScaleCalculateBeanDao() {
        return scaleCalculateBeanDao;
    }

    public SportBeanDao getSportBeanDao() {
        return sportBeanDao;
    }

    public UserInformationBeanDao getUserInformationBeanDao() {
        return userInformationBeanDao;
    }

    public WatchRealTimeDataDao getWatchRealTimeDataDao() {
        return watchRealTimeDataDao;
    }

    public WatchSportDataBeanDao getWatchSportDataBeanDao() {
        return watchSportDataBeanDao;
    }

    public WatchTargetBeanDao getWatchTargetBeanDao() {
        return watchTargetBeanDao;
    }

}
