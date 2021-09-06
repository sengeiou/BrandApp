package com.isport.brandapp.repository;


import com.isport.blelibrary.entry.WristbandData;
import com.isport.blelibrary.entry.WristbandForecast;
import com.isport.blelibrary.entry.WristbandWeather;
import com.isport.blelibrary.utils.Constants;
import com.isport.brandapp.App;
import com.isport.brandapp.net.RetrofitClient;
import com.isport.brandapp.util.DeviceTypeUtil;
import com.isport.brandapp.util.InitCommonParms;

import java.util.List;

import brandapp.isport.com.basicres.BaseApp;
import phone.gym.jkcq.com.commonres.common.JkConfiguration;
import brandapp.isport.com.basicres.commonbean.BaseDbPar;
import brandapp.isport.com.basicres.commonbean.BaseUrl;
import brandapp.isport.com.basicres.commonutil.AppUtil;
import brandapp.isport.com.basicres.mvp.NetworkBoundResource;
import io.reactivex.Observable;


public class WheatherRepository {

    public static Observable<WristbandData> requstTodayWeather(double lan, double lon, String city, int deviceType) {
        return new NetworkBoundResource<WristbandData>() {
            @Override
            public Observable<WristbandData> getFromDb() {
                return null;
            }

            @Override
            public Observable<WristbandData> getNoCacheData() {
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
            public Observable<WristbandData> getRemoteSource() {

                InitCommonParms<String, BaseUrl, BaseDbPar> parInitCommonParms = new InitCommonParms<>();
                BaseUrl baseUrl = new BaseUrl();
                baseUrl.lan = String.valueOf(lan);
                baseUrl.lon = String.valueOf(lon);
                if (AppUtil.isZh(BaseApp.getApp())) {
                    baseUrl.language = String.valueOf(0);
                } else {
                    baseUrl.language = String.valueOf(1);
                }
                baseUrl.city = city;
                baseUrl.dataType = String.valueOf(deviceType);
                return (Observable<WristbandData>) RetrofitClient.getInstance().post(parInitCommonParms
                        .setPostBody(!(App.appType() == App.httpType)).setBaseUrl(baseUrl).setType(JkConfiguration.RequstType.GET_TODAY_WEATHER).getPostBody(), true);
            }

            @Override
            public void saveRemoteSource(WristbandData remoteSource) {

                //缓存当前的数据

            }
        }.getAsObservable();
    }

    public static Observable<List<WristbandForecast>> requstWristbandForecast(double lan, double lon, String city, int deviceType) {
        return new NetworkBoundResource<List<WristbandForecast>>() {
            @Override
            public Observable<List<WristbandForecast>> getFromDb() {
                return null;
            }

            @Override
            public Observable<List<WristbandForecast>> getNoCacheData() {
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
            public Observable<List<WristbandForecast>> getRemoteSource() {

                //language:0中文，1英文,dayNum:天数
                InitCommonParms<String, BaseUrl, BaseDbPar> parInitCommonParms = new InitCommonParms<>();
                BaseUrl baseUrl = new BaseUrl();
                baseUrl.lan = String.valueOf(lan);
                baseUrl.lon = String.valueOf(lon);
                if (AppUtil.isZh(BaseApp.getApp())) {
                    baseUrl.language = String.valueOf(0);
                } else {
                    baseUrl.language = String.valueOf(1);
                }
                baseUrl.city = city;

                if (DeviceTypeUtil.isContainW81(deviceType)) {
                    baseUrl.dataNum = String.valueOf(7);
                } else {
                    baseUrl.dataNum = String.valueOf(3);
                }
                baseUrl.dataType = String.valueOf(deviceType);
                return (Observable<List<WristbandForecast>>) RetrofitClient.getInstance().post(parInitCommonParms
                        .setPostBody(!(App.appType() == App.httpType)).setBaseUrl(baseUrl).setType(JkConfiguration.RequstType.GET_15DATE_WEATHER).getPostBody(), true);

            }

            @Override
            public void saveRemoteSource(List<WristbandForecast> remoteSource) {

            }
        }.getAsObservable();
    }

    public static Observable<WristbandWeather> requstWeather(double lan, double lon, String city, int deviceType) {
        return new NetworkBoundResource<WristbandWeather>() {
            @Override
            public Observable<WristbandWeather> getFromDb() {
                return null;
            }

            @Override
            public Observable<WristbandWeather> getNoCacheData() {
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
            public Observable<WristbandWeather> getRemoteSource() {

                //language:0中文，1英文,dayNum:天数
                InitCommonParms<String, BaseUrl, BaseDbPar> parInitCommonParms = new InitCommonParms<>();
                BaseUrl baseUrl = new BaseUrl();
                baseUrl.lan = String.valueOf(lan);
                baseUrl.lon = String.valueOf(lon);
                ////中国 China
                if (Constants.currentCountry.equals("中国") || Constants.currentCountry.equals("China")) {
                    baseUrl.language = String.valueOf(0);
                } else {
                    baseUrl.language = String.valueOf(1);
                }
                baseUrl.city = city;

                if (deviceType == JkConfiguration.DeviceType.Brand_W520) {
                    baseUrl.dataNum = String.valueOf(3);
                } else {
                    baseUrl.dataNum = String.valueOf(7);
                }
                baseUrl.dataType = String.valueOf(deviceType);
                return (Observable<WristbandWeather>) RetrofitClient.getInstance().post(parInitCommonParms
                        .setPostBody(!(App.appType() == App.httpType)).setBaseUrl(baseUrl).setType(JkConfiguration.RequstType.GET_WEATHER).getPostBody(), true);

            }

            @Override
            public void saveRemoteSource(WristbandWeather remoteSource) {

            }
        }.getAsObservable();
    }


}
