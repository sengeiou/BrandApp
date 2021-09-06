package com.isport.brandapp.login.presenter;

import com.isport.brandapp.App;
import brandapp.isport.com.basicres.entry.bean.BaseParms;
import com.isport.brandapp.repository.MainResposition;
import com.isport.brandapp.util.InitCommonParms;

import phone.gym.jkcq.com.commonres.common.JkConfiguration;
import brandapp.isport.com.basicres.commonbean.BaseDbPar;
import brandapp.isport.com.basicres.commonbean.BaseUrl;
import io.reactivex.Observable;

public class VerifyImpl<T> {

    public Observable<T> getVerify(String phone, String type) {
        MainResposition<T, BaseParms, BaseUrl, BaseDbPar> mainResposition = new MainResposition<>();
        InitCommonParms<BaseParms, BaseUrl, BaseDbPar> initCommonParms = new InitCommonParms<>();
        BaseUrl baseUrl = new BaseUrl();
        baseUrl.url1 = JkConfiguration.Url.BASIC;
        baseUrl.url2 = JkConfiguration.Url.VERIFY;
        baseUrl.extend1 = phone;
        baseUrl.userid = type;

        return (Observable<T>) mainResposition.requst(initCommonParms.setPostBody(!(App.appType()==App.httpType)).setBaseUrl(baseUrl).setType(JkConfiguration.RequstType.VERIFY).getPostBody());
    }
    public Observable<T> getEmailVerify(String phone, String type,String language) {
        MainResposition<T, BaseParms, BaseUrl, BaseDbPar> mainResposition = new MainResposition<>();
        InitCommonParms<BaseParms, BaseUrl, BaseDbPar> initCommonParms = new InitCommonParms<>();
        BaseUrl baseUrl = new BaseUrl();
        baseUrl.url1 = JkConfiguration.Url.BASIC;
        baseUrl.url2 = JkConfiguration.Url.VERIFY;
        baseUrl.language=language;
        baseUrl.extend1 = phone;
        baseUrl.userid = type;

        return (Observable<T>) mainResposition.requst(initCommonParms.setPostBody(!(App.appType()==App.httpType)).setBaseUrl(baseUrl).setType(JkConfiguration.RequstType.EMAIL_VERIFY).getPostBody());
    }
}
