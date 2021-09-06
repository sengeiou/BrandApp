package com.isport.brandapp.repository;

import com.isport.brandapp.App;
import com.isport.brandapp.device.bracelet.playW311.bean.PlayBean;
import com.isport.brandapp.net.RetrofitClient;
import com.isport.brandapp.util.InitCommonParms;

import java.util.List;

import phone.gym.jkcq.com.commonres.common.JkConfiguration;
import brandapp.isport.com.basicres.commonbean.BaseDbPar;
import brandapp.isport.com.basicres.commonbean.BaseUrl;
import brandapp.isport.com.basicres.mvp.NetworkBoundResource;
import io.reactivex.Observable;

public class PlayBandRepository {

    public static Observable<List<PlayBean>> requstGetPlayBandImage(int deviceType) {
        return new NetworkBoundResource<List<PlayBean>>() {
            @Override
            public Observable<List<PlayBean>> getFromDb() {
                return null;
            }

            @Override
            public Observable<List<PlayBean>> getNoCacheData() {
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
            public Observable<List<PlayBean>> getRemoteSource() {
                InitCommonParms<String, BaseUrl, BaseDbPar> parInitCommonParms = new InitCommonParms<>();
                BaseUrl baseUrl = new BaseUrl();
                baseUrl.extend1 = String.valueOf(deviceType);
                return (Observable<List<PlayBean>>) RetrofitClient.getInstance().post(parInitCommonParms
                        .setPostBody(!(App.appType() == App.httpType)).setBaseUrl(baseUrl).setType(JkConfiguration.RequstType.DEVICE_PLAY_URL).getPostBody(), true);

            }

            @Override
            public void saveRemoteSource(List<PlayBean> remoteSource) {

            }
        }.getAsObservable();
    }

}
