package com.isport.brandapp.repository;

import android.text.TextUtils;

import com.isport.blelibrary.db.parse.RopeDetail;
import com.isport.brandapp.App;
import com.isport.brandapp.Home.bean.AdviceBean;
import com.isport.brandapp.net.RetrofitClient;
import com.isport.brandapp.util.InitCommonParms;

import java.util.List;

import brandapp.isport.com.basicres.BaseApp;
import brandapp.isport.com.basicres.commonbean.BaseDbPar;
import brandapp.isport.com.basicres.commonbean.BaseUrl;
import brandapp.isport.com.basicres.commonutil.TokenUtil;
import brandapp.isport.com.basicres.mvp.NetworkBoundResource;
import io.reactivex.Observable;
import phone.gym.jkcq.com.commonres.common.JkConfiguration;

public class AdviceListRepository {
    public static Observable<List<AdviceBean>> requestAdviceList() {
        return new NetworkBoundResource<List<AdviceBean>>() {
            @Override
            public Observable<List<AdviceBean>> getFromDb() {
                return null;
            }

            @Override
            public Observable<List<AdviceBean>> getNoCacheData() {
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
            public Observable<List<AdviceBean>> getRemoteSource() {


                InitCommonParms<List<RopeDetail>, BaseUrl, BaseDbPar> parInitCommonParms = new InitCommonParms<>();
                BaseUrl baseUrl = new BaseUrl();
                baseUrl.userid = TokenUtil.getInstance().getPeopleIdInt(BaseApp.getApp());
                if (TextUtils.isEmpty(baseUrl.userid)) {
                    baseUrl.userid = "0";
                }
                return (Observable<List<AdviceBean>>) RetrofitClient.getInstance().post(parInitCommonParms
                        .setPostBody(!(App.appType() == App.httpType)).setBaseUrl(baseUrl).setType(JkConfiguration.RequstType.adviceList).getPostBody());
            }

            @Override
            public void saveRemoteSource(List<AdviceBean> remoteSource) {



            }
        }.getAsObservable();
    }

}
