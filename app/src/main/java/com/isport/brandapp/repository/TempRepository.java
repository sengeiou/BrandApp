package com.isport.brandapp.repository;


import com.isport.blelibrary.db.table.w526.Device_TempTable;
import com.isport.blelibrary.utils.Logger;
import com.isport.brandapp.App;
import com.isport.brandapp.device.bracelet.braceletModel.DeviceMeasureDataImp;
import com.isport.brandapp.device.bracelet.braceletModel.IDeviceMeasureData;
import com.isport.brandapp.net.RetrofitClient;
import com.isport.brandapp.util.InitCommonParms;
import com.isport.brandapp.wu.bean.BPInfo;
import com.isport.brandapp.wu.bean.TempInfo;

import java.util.ArrayList;
import java.util.List;

import phone.gym.jkcq.com.commonres.common.JkConfiguration;
import brandapp.isport.com.basicres.commonbean.BaseDbPar;
import brandapp.isport.com.basicres.commonbean.BaseUrl;
import brandapp.isport.com.basicres.mvp.NetworkBoundResource;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;


public class TempRepository {

    public static Observable<Integer> requstUpgradeTempData(String deviceId, String userId) {
        return new NetworkBoundResource<Integer>() {
            @Override
            public Observable<Integer> getFromDb() {
                return null;
            }

            @Override
            public Observable<Integer> getNoCacheData() {
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
            public Observable<Integer> getRemoteSource() {
                List<TempInfo> upgradeList = new ArrayList<>();
                try {
                    IDeviceMeasureData deviceMeasureData = new DeviceMeasureDataImp();
                    List<Device_TempTable> list = deviceMeasureData.uploadingTempData(deviceId, userId, "0");
                    Logger.myLog("requstUpgradeTempData success:no upgrade1" + list + "userId：" + userId + " deviceId：" + deviceId);
                    if (list == null) {
                        list = new ArrayList<>();
                    }
                    Device_TempTable oxygenMode;
                    TempInfo info;
                    for (int i = 0; i < list.size(); i++) {
                        oxygenMode = list.get(i);
                        info = new TempInfo();
                        info.setCentigrade(oxygenMode.getCentigrade());
                        info.setFahrenheit(oxygenMode.getFahrenheit());
                        info.setDeviceId(oxygenMode.getDeviceId());
                        info.setTimestamp(oxygenMode.getTimestamp());
                        info.setUserId(oxygenMode.getUserId());
                        upgradeList.add(info);
                    }


                } catch (Exception e) {
                    e.printStackTrace();

                } finally {
                    if (upgradeList.size() == 0) {
                        return Observable.create(new ObservableOnSubscribe<Integer>() {
                            @Override
                            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
                                emitter.onNext(-1);
                                emitter.onComplete();
                            }
                        });
                    } else {
                        InitCommonParms<List<TempInfo>, BaseUrl, BaseDbPar> parInitCommonParms = new InitCommonParms<>();
                        BaseUrl baseUrl = new BaseUrl();
                        return (Observable<Integer>) RetrofitClient.getInstance().postTemp(parInitCommonParms
                                .setPostBody(!(App.appType() == App.httpType)).setParms(upgradeList).setBaseUrl(baseUrl).setType(JkConfiguration.RequstType.TEMP_DATA_INSERT).getPostBody());
                    }
                }


            }

            @Override
            public void saveRemoteSource(Integer remoteSource) {

            }
        }.getAsObservable();
    }


    public static Observable<List<TempInfo>> requstNumTempData(String deviceId, String userId, int num) {
        return new NetworkBoundResource<List<TempInfo>>() {
            @Override
            public Observable<List<TempInfo>> getFromDb() {
                return null;
            }

            @Override
            public Observable<List<TempInfo>> getNoCacheData() {
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
            public Observable<List<TempInfo>> getRemoteSource() {
                InitCommonParms<List<TempInfo>, BaseUrl, BaseDbPar> parInitCommonParms = new InitCommonParms<>();
                BaseUrl baseUrl = new BaseUrl();
                baseUrl.userid = userId;
                baseUrl.extend1 = String.valueOf(num);
                baseUrl.deviceid = deviceId;
                return (Observable<List<TempInfo>>) RetrofitClient.getInstance().postTemp(parInitCommonParms
                        .setPostBody(!(App.appType() == App.httpType)).setBaseUrl(baseUrl).setType(JkConfiguration.RequstType.TEMP_DATA_NUMB).getPostBody());

            }

            @Override
            public void saveRemoteSource(List<TempInfo> remoteSource) {

            }
        }.getAsObservable();
    }

    public static Observable<List<TempInfo>> requstPageTempData(String deviceId, String userId, int offset) {
        return new NetworkBoundResource<List<TempInfo>>() {
            @Override
            public Observable<List<TempInfo>> getFromDb() {
                return null;
            }

            @Override
            public Observable<List<TempInfo>> getNoCacheData() {
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
            public Observable<List<TempInfo>> getRemoteSource() {
                InitCommonParms<List<BPInfo>, BaseUrl, BaseDbPar> parInitCommonParms = new InitCommonParms<>();
                BaseUrl baseUrl = new BaseUrl();
                baseUrl.userid = userId;
                baseUrl.extend1 = String.valueOf(offset);
                baseUrl.deviceid = deviceId;
                return (Observable<List<TempInfo>>) RetrofitClient.getInstance().postTemp(parInitCommonParms
                        .setPostBody(!(App.appType() == App.httpType)).setBaseUrl(baseUrl).setType(JkConfiguration.RequstType.TEMP_DATA_PAGE).getPostBody());

            }

            @Override
            public void saveRemoteSource(List<TempInfo> remoteSource) {

            }
        }.getAsObservable();
    }
}
