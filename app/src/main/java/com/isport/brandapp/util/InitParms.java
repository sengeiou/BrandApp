package com.isport.brandapp.util;

import brandapp.isport.com.basicres.entry.bean.BaseParms;
import com.isport.brandapp.parm.http.EditeUserParm;
import com.isport.brandapp.parm.http.LoginPar;
import com.isport.brandapp.parm.http.ThridPar;

import phone.gym.jkcq.com.commonres.common.JkConfiguration;
import brandapp.isport.com.basicres.commonbean.BaseDbPar;
import brandapp.isport.com.basicres.commonbean.BaseUrl;
import brandapp.isport.com.basicres.commonnet.net.PostBody;

public class InitParms {

    public static PostBody<BaseParms, BaseUrl, BaseDbPar> setUserPar(String userid, String interfaceId, boolean isStandAlone, int requestType) {

        PostBody<BaseParms, BaseUrl, BaseDbPar> parmsPostBody = new PostBody<>();
        parmsPostBody.data = new BaseParms();
        parmsPostBody.data.setInterfaceId(interfaceId);
        parmsPostBody.data.setUserId(userid);
        parmsPostBody.isStandAlone = isStandAlone;
        parmsPostBody.type = requestType;

        BaseUrl baseUrl = new BaseUrl();
        baseUrl.url1 = JkConfiguration.Url.BASIC;
        baseUrl.url2 = JkConfiguration.Url.CUSTOMER;
        baseUrl.url3 = JkConfiguration.Url.GETBASICINFO;
        parmsPostBody.requseturl = baseUrl;

        return parmsPostBody;
    }


    public static BaseParms setUserParMs(String userid, String interfaceId) {

        BaseParms parms = new BaseParms();
        parms.setInterfaceId(interfaceId);
        parms.setUserId(userid);
        return parms;

    }

    public static PostBody<BaseParms, BaseUrl, BaseDbPar> setUrl(String userid, String interfaceId) {

        PostBody<BaseParms, BaseUrl, BaseDbPar> parmsPostBody = new PostBody<>();
        parmsPostBody.data = new BaseParms();
        parmsPostBody.data.setInterfaceId(interfaceId);
        parmsPostBody.data.setUserId(userid);
        return parmsPostBody;


    }


    public static PostBody<BaseParms, BaseUrl, BaseDbPar> setUserPar(String userid, String interfaceId, String url1, String url2) {

        PostBody<BaseParms, BaseUrl, BaseDbPar> parmsPostBody = new PostBody<>();
        parmsPostBody.data = new BaseParms();
        parmsPostBody.data.setInterfaceId(interfaceId);
        parmsPostBody.data.setUserId(userid);
        parmsPostBody.requseturl = new BaseUrl();
        parmsPostBody.requseturl.url1 = url1;
        parmsPostBody.requseturl.url2 = url2;
        return parmsPostBody;


    }

    /**
     * **
     * String url = AllocationApi.postEditCustomerBasicInfo();
     * HashMap<String, Object> parmes = new HashMap<>();
     * parmes.put("userId", TokenUtil.getInstance().getPeopleIdStr(BaseApp.getApp()));
     * parmes.put("interfaceId", String.valueOf(0));
     * parmes.put("nickName", name);
     * parmes.put("gender", sex);
     * parmes.put("height", StringUtil.getNumberStr(height));
     * parmes.put("weight", StringUtil.getNumberStr(weight));
     * parmes.put("birthday", defaultDay);
     */

    public static PostBody<EditeUserParm, BaseUrl, BaseDbPar> setUserPar(String userid, String interfaceId, String nickName, String gender, String height, String weight, String birthday, boolean isStandAlone, int requestType) {
        PostBody<EditeUserParm, BaseUrl, BaseDbPar> postInfoBean = new PostBody();
        postInfoBean.data = new EditeUserParm();
        postInfoBean.data.nickName = nickName;
        postInfoBean.data.setInterfaceId(interfaceId);
        postInfoBean.data.setUserId(userid);
        postInfoBean.data.gender = gender;
        postInfoBean.data.height = height;
        postInfoBean.data.weight = weight;
        postInfoBean.data.birthday = birthday;
        postInfoBean.isStandAlone = isStandAlone;
        postInfoBean.type = requestType;

        BaseUrl baseUrl = new BaseUrl();
        baseUrl.url1 = JkConfiguration.Url.BASIC;
        baseUrl.url2 = JkConfiguration.Url.CUSTOMER;
        baseUrl.url3 = JkConfiguration.Url.EDITBASICINFO;
        postInfoBean.requseturl = baseUrl;

        return postInfoBean;
    }

    public static PostBody<LoginPar, BaseUrl, BaseDbPar> setLoginParm(String phoneNum, String code, String type, String userId, int requestType) {

        PostBody<LoginPar, BaseUrl, BaseDbPar> postInfoBean = new PostBody();
        postInfoBean.data = new LoginPar();
        postInfoBean.data.setInterfaceId("0");
        postInfoBean.data.setUserId(userId);
        postInfoBean.data.verify = code;
        if (Integer.parseInt(type) == 1) {
            postInfoBean.data.mobile = phoneNum;
        } else {
            postInfoBean.data.email = phoneNum;
        }
        postInfoBean.data.type = type;
        postInfoBean.type = requestType;

        BaseUrl baseUrl = new BaseUrl();
        baseUrl.url1 = JkConfiguration.Url.BASIC;
        baseUrl.url2 = JkConfiguration.Url.CUSTOMER;
        baseUrl.url3 = JkConfiguration.Url.LOGIN_BY_MOBILE;
        postInfoBean.requseturl = baseUrl;

        return postInfoBean;


    }

    public static PostBody<ThridPar, BaseUrl, BaseDbPar> setLoginParm(String userid, String interfaceId, String id) {

        PostBody<ThridPar, BaseUrl, BaseDbPar> parmsPostBody = new PostBody<>();
        parmsPostBody.data = new ThridPar();
        parmsPostBody.data.setInterfaceId(interfaceId);
        parmsPostBody.data.setUserId(userid);
        return parmsPostBody;


    }

}
