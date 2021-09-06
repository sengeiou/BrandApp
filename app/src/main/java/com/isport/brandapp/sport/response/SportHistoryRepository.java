package com.isport.brandapp.sport.response;


import com.isport.brandapp.App;
import com.isport.brandapp.Home.bean.SportLastDataBeanList;
import com.isport.brandapp.device.UpdateSuccessBean;
import com.isport.brandapp.net.RetrofitClient;
import com.isport.brandapp.sport.bean.SportSumData;
import com.isport.brandapp.util.InitCommonParms;

import brandapp.isport.com.basicres.BaseApp;
import phone.gym.jkcq.com.commonres.common.JkConfiguration;
import brandapp.isport.com.basicres.commonbean.BaseDbPar;
import brandapp.isport.com.basicres.commonbean.BaseUrl;
import brandapp.isport.com.basicres.commonutil.TokenUtil;
import brandapp.isport.com.basicres.mvp.NetworkBoundResource;
import io.reactivex.Observable;


public class SportHistoryRepository {


    public Observable<SportLastDataBeanList> getLastData() {
        return new NetworkBoundResource<SportLastDataBeanList>() {

            @Override
            public Observable<SportLastDataBeanList> getFromDb() {
                return null;
            }

            @Override
            public Observable<SportLastDataBeanList> getNoCacheData() {
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
            public Observable<SportLastDataBeanList> getRemoteSource() {
                InitCommonParms<SportSumData, BaseUrl, BaseDbPar> parInitCommonParms = new InitCommonParms<>();
                BaseUrl baseUrl = new BaseUrl();
                baseUrl.url1 = JkConfiguration.Url.SPORT;
                baseUrl.url2 = JkConfiguration.Url.INPONESPORT;
                baseUrl.url3 = JkConfiguration.Url.SELECTLASTALL;
                baseUrl.userid = TokenUtil.getInstance().getPeopleIdStr(BaseApp.getApp());
                return (Observable<SportLastDataBeanList>) RetrofitClient.getInstance().post(parInitCommonParms
                        .setPostBody
                                (!(App.appType() == App.httpType)).setBaseUrl(baseUrl).setType(JkConfiguration.RequstType.GET_SPORT_LAST_ALL).getPostBody());
            }

            @Override
            public void saveRemoteSource(SportLastDataBeanList remoteSource) {

            }
        }.getAsObservable();
    }

    public static Observable<UpdateSuccessBean> requst(SportSumData
                                                               sportSumData) {
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

                InitCommonParms<SportSumData, BaseUrl, BaseDbPar> parInitCommonParms = new InitCommonParms<>();
                BaseUrl baseUrl = new BaseUrl();
                baseUrl.url1 = JkConfiguration.Url.SPORT;
                baseUrl.url2 = JkConfiguration.Url.INPONESPORT;
                baseUrl.url3 = JkConfiguration.Url.INSERTSELECTIVE;
                return (Observable<UpdateSuccessBean>) RetrofitClient.getInstance().post(parInitCommonParms
                        .setPostBody
                                (!(App.appType() == App.httpType)).setParms(sportSumData).setBaseUrl(baseUrl).setType(JkConfiguration.RequstType.ADD_SPORT_SUMMER).getPostBody());
//                }
//                return null;
            }

            @Override
            public void saveRemoteSource(UpdateSuccessBean remoteSource) {

            }
        }.getAsObservable();
    }


}
