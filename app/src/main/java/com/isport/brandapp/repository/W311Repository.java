package com.isport.brandapp.repository;


import com.isport.blelibrary.db.CommonInterFace.WatchData;
import com.isport.blelibrary.db.table.bracelet_w311.Bracelet_W311_24HDataModel;
import com.isport.brandapp.App;
import com.isport.brandapp.home.bean.http.UpdateWatchBean;
import com.isport.brandapp.home.bean.http.UpdateWatchResultBean;
import com.isport.brandapp.home.bean.http.WatchDayData;
import com.isport.brandapp.home.bean.http.Wristbandstep;
import com.isport.brandapp.device.UpdateSuccessBean;
import com.isport.brandapp.device.watch.bean.WatchInsertBean;
import com.isport.brandapp.net.RetrofitClient;
import com.isport.brandapp.util.AppSP;
import com.isport.brandapp.util.InitCommonParms;

import java.util.ArrayList;
import java.util.List;

import bike.gymproject.viewlibray.pickerview.utils.DateUtils;
import brandapp.isport.com.basicres.BaseApp;
import brandapp.isport.com.basicres.action.WatchSportDataBeanAction;
import phone.gym.jkcq.com.commonres.common.AllocationApi;
import phone.gym.jkcq.com.commonres.common.JkConfiguration;
import brandapp.isport.com.basicres.commonbean.BaseDbPar;
import brandapp.isport.com.basicres.commonbean.BaseUrl;
import brandapp.isport.com.basicres.commonutil.TokenUtil;
import brandapp.isport.com.basicres.entry.WatchSportDataBean;
import brandapp.isport.com.basicres.mvp.NetworkBoundResource;
import io.reactivex.Observable;
import phone.gym.jkcq.com.commonres.commonutil.CommonDateUtil;


public class W311Repository {


    public static Observable<UpdateWatchResultBean> requst(Wristbandstep mWristbandstep) {
        return new NetworkBoundResource<UpdateWatchResultBean>() {
            @Override
            public Observable<UpdateWatchResultBean> getFromDb() {
                return null;
            }

            @Override
            public Observable<UpdateWatchResultBean> getNoCacheData() {
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
            public Observable<UpdateWatchResultBean> getRemoteSource() {
                String name = AppSP.getString(BaseApp.getApp(), AppSP.DEVICE_CURRENTNAME, "");
                List<WatchSportDataBean> watchSportData = null;
                if (!DateUtils.getDateYMDByTime(mWristbandstep.getBuildTime()).equals(DateUtils.getYMD(System.currentTimeMillis()))) {
                    if ("--".equals(mWristbandstep.getStepNum())) {
                        //没有上传过数据，上传所有数据
                        watchSportData = WatchSportDataBeanAction.getWatchSportData
                                ("2018-01-01", name);
                    } else {
                        long buildTime = mWristbandstep.getBuildTime();
                        String dateYMDByTime = DateUtils.getDateYMDByTime(buildTime);
                        //有上传数据，查询时间戳后的数据
                        watchSportData = WatchSportDataBeanAction.getWatchSportData(dateYMDByTime, name);
                    }
                }
                if (watchSportData != null) {
                    List<WatchDayData> watchDayDataList = new ArrayList<>();
                    UpdateWatchBean updateWatchBean = new UpdateWatchBean();
                    for (int i = 0; i < watchSportData.size(); i++) {
                        WatchSportDataBean watchSportDataBean = watchSportData.get(i);
                        WatchDayData watchDayData = new WatchDayData();
                        watchDayData.setCalorie(watchSportDataBean.getCal() + "");
                        watchDayData.setStepKm(watchSportDataBean.getStepKm() + "");
                        watchDayData.setStepNum(watchSportDataBean.getStepNum() + "");
                        watchDayData.setBuildTime(DateUtils.getTimeStamp(watchSportDataBean.getDateStr()) + "");
                        watchDayDataList.add(watchDayData);
                    }
                    updateWatchBean.setData(watchDayDataList);
                    updateWatchBean.setDeviceType(JkConfiguration.DeviceType.WATCH_W516);
                    updateWatchBean.setInterfaceId(0);
                    updateWatchBean.setMac(name);
                    updateWatchBean.setTime("");
                    updateWatchBean.setUserId(Integer.parseInt(TokenUtil.getInstance().getPeopleIdStr(BaseApp.getApp())));

                    String url = AllocationApi.postSynchronizeWatchData();

                    InitCommonParms<UpdateWatchBean, BaseUrl, BaseDbPar> parInitCommonParms = new InitCommonParms<>();
                    BaseUrl baseUrl = new BaseUrl();
                    baseUrl.url1 = JkConfiguration.Url.WRISTBAND_STEP;
                    baseUrl.url2 = JkConfiguration.Url.SYNCHRONIZ_EDATA;

                    return (Observable<UpdateWatchResultBean>) RetrofitClient.getInstance().post(parInitCommonParms.setPostBody(!(App.appType() == App.httpType)).setParms(updateWatchBean).setBaseUrl(baseUrl).setType(JkConfiguration.RequstType.WRISTBAND_UPDATE).getPostBody());
                }
                return null;
            }

            @Override
            public void saveRemoteSource(UpdateWatchResultBean remoteSource) {

            }
        }.getAsObservable();
    }


    public static Observable<UpdateSuccessBean> requst(Bracelet_W311_24HDataModel watch_w516_24HDataModel) {
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

                //上传的距离为千米
                InitCommonParms<WatchInsertBean, BaseUrl, BaseDbPar> parInitCommonParms = new InitCommonParms<>();
                BaseUrl baseUrl = new BaseUrl();
                baseUrl.url1 = JkConfiguration.Url.SPORT;
                baseUrl.url2 = JkConfiguration.Url.WRISTBANDSPORTDETAIL;
                baseUrl.url3 = JkConfiguration.Url.INSERTSELECTIVE;
                // String url = AllocationApi.postSynchronizeSleepData();
                WatchInsertBean watchInsertBean = new WatchInsertBean();
                watchInsertBean.setDateStr(watch_w516_24HDataModel.getDateStr());
                watchInsertBean.setDeviceId(watch_w516_24HDataModel.getDeviceId());
                watchInsertBean.setUserId(TokenUtil.getInstance().getPeopleIdStr(BaseApp.getApp()));
                watchInsertBean.setHeartRateDetailArray(watch_w516_24HDataModel.getHrArray());
                watchInsertBean.setSleepDetailArray(watch_w516_24HDataModel.getSleepArray());
                watchInsertBean.setStepDetailArray(watch_w516_24HDataModel.getStepArray());
                watchInsertBean.setTotalSteps((int) watch_w516_24HDataModel.getTotalSteps());
                watchInsertBean.setTotalCalories(watch_w516_24HDataModel.getTotalCalories() + "");
                watchInsertBean.setTotalSleepTime(watch_w516_24HDataModel.getTotalSleepTime());
                watchInsertBean.setIsHaveHeartRate(watch_w516_24HDataModel.getHasHR() == WatchData.HAS_HR ? "1" : "0");
                watchInsertBean.setTotalDistance(CommonDateUtil.formatTwoPoint(watch_w516_24HDataModel.getTotalDistance()));
                // TODO: 2019/1/18 传到哪儿去的，要适配
//                EventBus.getDefault().post(new UpdateDataSleep(sleepUpdateBean));
                return (Observable<UpdateSuccessBean>) RetrofitClient.getInstance().postBracelet(parInitCommonParms
                        .setPostBody
                                (!(App.appType() == App.httpType)).setParms(watchInsertBean).setBaseUrl(baseUrl).setType(JkConfiguration.RequstType.BRACELET_UPDATE).getPostBody());
//                }
//                return null;
            }

            @Override
            public void saveRemoteSource(UpdateSuccessBean remoteSource) {

            }
        }.getAsObservable();
    }


}
