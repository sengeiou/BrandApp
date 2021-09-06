package com.isport.brandapp.repository;


import com.isport.brandapp.App;
import com.isport.brandapp.device.UpdateSuccessBean;
import com.isport.brandapp.device.watch.bean.WatchHistoryNList;
import com.isport.brandapp.device.watch.bean.WatchInsertBean;
import com.isport.brandapp.net.RetrofitClient;
import com.isport.brandapp.util.InitCommonParms;

import phone.gym.jkcq.com.commonres.common.JkConfiguration;
import brandapp.isport.com.basicres.commonbean.BaseDbPar;
import brandapp.isport.com.basicres.commonbean.BaseUrl;
import brandapp.isport.com.basicres.mvp.NetworkBoundResource;
import io.reactivex.Observable;


public class W81DeviceDataRepository {


    public W81DeviceDataRepository() {
    }


    public static Observable<UpdateSuccessBean> requstUpgradeW81Data(WatchInsertBean watchInsertBean) {
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
                InitCommonParms<WatchInsertBean, BaseUrl, BaseDbPar> parInitCommonParms = new InitCommonParms<>();
                BaseUrl baseUrl = new BaseUrl();
                return (Observable<UpdateSuccessBean>) RetrofitClient.getInstance().postW81(parInitCommonParms
                        .setPostBody(!(App.appType() == App.httpType)).setParms(watchInsertBean).setBaseUrl(baseUrl).setType(JkConfiguration.RequstType.W81DETAIL_DATA_UPGRADE).getPostBody());

            }

            @Override
            public void saveRemoteSource(UpdateSuccessBean remoteSource) {

            }
        }.getAsObservable();
    }

    public static Observable<WatchHistoryNList> requstGetNumberW81Data(String deviceId, String userId, String dataType, String dataNum) {
        return new NetworkBoundResource<WatchHistoryNList>() {
            @Override
            public Observable<WatchHistoryNList> getFromDb() {
                return null;
            }

            @Override
            public Observable<WatchHistoryNList> getNoCacheData() {
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
            public Observable<WatchHistoryNList> getRemoteSource() {
                InitCommonParms<WatchInsertBean, BaseUrl, BaseDbPar> parInitCommonParms = new InitCommonParms<>();
                BaseUrl baseUrl = new BaseUrl();
                baseUrl.userid = userId;
                baseUrl.deviceid = deviceId;
                baseUrl.dataNum = dataNum;
                baseUrl.dataType = dataType;
                return (Observable<WatchHistoryNList>) RetrofitClient.getInstance().postW81(parInitCommonParms
                        .setPostBody(!(App.appType() == App.httpType)).setBaseUrl(baseUrl).setType(JkConfiguration.RequstType.W81_NUM_DEVICE_DETAIL_DATA).getPostBody());


            }

            @Override
            public void saveRemoteSource(WatchHistoryNList remoteSource) {

            }
        }.getAsObservable();
    }

    public static Observable<WatchHistoryNList> requstMonthW81Data(String deviceId, String userId, String dataType, Long time) {
        return new NetworkBoundResource<WatchHistoryNList>() {
            @Override
            public Observable<WatchHistoryNList> getFromDb() {
                return null;
            }

            @Override
            public Observable<WatchHistoryNList> getNoCacheData() {
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
            public Observable<WatchHistoryNList> getRemoteSource() {
                InitCommonParms<WatchInsertBean, BaseUrl, BaseDbPar> parInitCommonParms = new InitCommonParms<>();
                BaseUrl baseUrl = new BaseUrl();
                baseUrl.deviceid = deviceId;
                baseUrl.userid = userId;
                baseUrl.dataType = dataType;
                baseUrl.extend1 = String.valueOf(time);
                return (Observable<WatchHistoryNList>) RetrofitClient.getInstance().postW81(parInitCommonParms
                        .setPostBody(!(App.appType() == App.httpType)).setBaseUrl(baseUrl).setType(JkConfiguration.RequstType.W81_MONTH_DEVICE_DETAIL_DATA).getPostBody());

            }

            @Override
            public void saveRemoteSource(WatchHistoryNList remoteSource) {

            }
        }.getAsObservable();
    }


}
