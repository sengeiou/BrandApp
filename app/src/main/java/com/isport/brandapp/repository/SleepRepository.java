package com.isport.brandapp.repository;


import com.isport.blelibrary.db.table.sleep.Sleep_Sleepace_DataModel;
import com.isport.brandapp.App;
import com.isport.brandapp.device.UpdateSuccessBean;
import com.isport.brandapp.device.sleep.bean.SleepInsertBean;
import com.isport.brandapp.net.RetrofitClient;
import com.isport.brandapp.util.InitCommonParms;

import phone.gym.jkcq.com.commonres.common.JkConfiguration;
import brandapp.isport.com.basicres.commonbean.BaseDbPar;
import brandapp.isport.com.basicres.commonbean.BaseUrl;
import brandapp.isport.com.basicres.mvp.NetworkBoundResource;
import io.reactivex.Observable;


public class SleepRepository {


    public static Observable<UpdateSuccessBean> requst(Sleep_Sleepace_DataModel
                                                                    sleep_sleepace_dataModelByDeviceId) {
        return new NetworkBoundResource<UpdateSuccessBean>() {
            @Override
            public Observable<UpdateSuccessBean> getFromDb() {
                return null;
            }

            @Override
            public Observable<UpdateSuccessBean> getNoCacheData() {
                return null;
            }

            @Override
            public boolean shouldFetchRemoteSource() {
                return false;
            }

            @Override
            public boolean shouldStandAlone() {
                return false;
            }

            @Override
            public Observable<UpdateSuccessBean> getRemoteSource() {

                InitCommonParms<SleepInsertBean, BaseUrl, BaseDbPar> parInitCommonParms = new InitCommonParms<>();
                BaseUrl baseUrl = new BaseUrl();
                baseUrl.url1 = JkConfiguration.Url.SLEEPBELT;
                baseUrl.url2 = JkConfiguration.Url.SLEEPBELT_TARGET;
                baseUrl.url3 = JkConfiguration.Url.INSERTSELECTIVE;
                // String url = AllocationApi.postSynchronizeSleepData();
                SleepInsertBean sleepInsertBean = new SleepInsertBean();
                sleepInsertBean.setAverageBreathRate(sleep_sleepace_dataModelByDeviceId.getAverageBreathRate());
                sleepInsertBean.setAverageHeartBeatRate(sleep_sleepace_dataModelByDeviceId.getAverageHeartBeatRate());
                sleepInsertBean.setBodyMovementTimes(sleep_sleepace_dataModelByDeviceId.getBodyMovementTimes());
                sleepInsertBean.setBreathPauseAllTime(sleep_sleepace_dataModelByDeviceId.getBreathPauseAllTime());
                sleepInsertBean.setBreathPauseTimes(sleep_sleepace_dataModelByDeviceId.getBreathPauseTimes());
                sleepInsertBean.setBreathRateFastAllTime(sleep_sleepace_dataModelByDeviceId.getBreathRateFastAllTime());
                sleepInsertBean.setBreathRateSlowAllTime(sleep_sleepace_dataModelByDeviceId.getBreathRateSlowAllTime());
                sleepInsertBean.setDateStr(sleep_sleepace_dataModelByDeviceId.getDateStr());
                sleepInsertBean.setUserId(sleep_sleepace_dataModelByDeviceId.getUserId());
                sleepInsertBean.setDeviceId(sleep_sleepace_dataModelByDeviceId.getDeviceId());
                sleepInsertBean.setTimestamp(sleep_sleepace_dataModelByDeviceId.getTimestamp()*1000L);
                sleepInsertBean.setDeepSleepDuration(sleep_sleepace_dataModelByDeviceId.getDeepSleepAllTime());
                sleepInsertBean.setDeepSleepPercent(sleep_sleepace_dataModelByDeviceId.getDeepSleepPercent());
                sleepInsertBean.setFallSleepAllTime(sleep_sleepace_dataModelByDeviceId.getFallAlseepAllTime());
                sleepInsertBean.setHeartBeatPauseAllTime(sleep_sleepace_dataModelByDeviceId.getHeartBeatPauseAllTime());
                sleepInsertBean.setHeartBeatPauseTimes(sleep_sleepace_dataModelByDeviceId.getHeartBeatPauseTimes());
                sleepInsertBean.setHeartBeatRateFastAllTime(sleep_sleepace_dataModelByDeviceId.getHeartBeatRateFastAllTime());
                sleepInsertBean.setHeartBeatRateSlowAllTime(sleep_sleepace_dataModelByDeviceId.getHeartBeatRateSlowAllTime());
                sleepInsertBean.setLeaveBedAllTime(sleep_sleepace_dataModelByDeviceId.getLeaveBedAllTime());
                sleepInsertBean.setLeaveBedTimes(sleep_sleepace_dataModelByDeviceId.getLeaveBedTimes());
                sleepInsertBean.setMaxHeartBeatRate(sleep_sleepace_dataModelByDeviceId.getMaxHeartBeatRate());
                sleepInsertBean.setMinHeartBeatRate(sleep_sleepace_dataModelByDeviceId.getMinHeartBeatRate());
                sleepInsertBean.setMaxBreathRate(sleep_sleepace_dataModelByDeviceId.getMaxBreathRate());
                sleepInsertBean.setMinBreathRate(sleep_sleepace_dataModelByDeviceId.getMinBreathRate());
                sleepInsertBean.setTurnOverTimes(sleep_sleepace_dataModelByDeviceId.getTurnOverTimes());
                sleepInsertBean.setTrunOverStatusAry(sleep_sleepace_dataModelByDeviceId.getTrunOverStatusAry());
                sleepInsertBean.setHeartBeatRateValueArray(sleep_sleepace_dataModelByDeviceId.getHeartRateAry());
                sleepInsertBean.setBreathRateValueArray(sleep_sleepace_dataModelByDeviceId.getBreathRateAry());

                // TODO: 2019/1/18 传到哪儿去的，要适配
//                EventBus.getDefault().post(new UpdateDataSleep(sleepUpdateBean));
                return (Observable<UpdateSuccessBean>) RetrofitClient.getInstance().post(parInitCommonParms
                                                                                                      .setPostBody
                                                                                                              (!(App.appType()==App.httpType)).setParms(sleepInsertBean).setBaseUrl(baseUrl).setType(JkConfiguration.RequstType.SLEEP_UPDATE).getPostBody());
//                }
//                return null;
            }

            @Override
            public void saveRemoteSource(UpdateSuccessBean remoteSource) {

            }
        }.getAsObservable();
    }


}
