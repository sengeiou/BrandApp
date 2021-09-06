package com.isport.brandapp.login.presenter;


import android.util.Log;

import com.isport.blelibrary.utils.Logger;
import com.isport.brandapp.banner.recycleView.utils.ToastUtil;
import com.isport.brandapp.login.view.LoginBaseView;
import com.isport.brandapp.parm.http.LoginPar;
import com.isport.brandapp.parm.http.ThridPar;
import com.isport.brandapp.repository.CustomRepository;
import com.isport.brandapp.util.InitParms;

import brandapp.isport.com.basicres.BaseApp;
import brandapp.isport.com.basicres.commonbean.UserInfoBean;
import phone.gym.jkcq.com.commonres.common.AllocationApi;
import phone.gym.jkcq.com.commonres.common.JkConfiguration;
import brandapp.isport.com.basicres.commonbean.BaseDbPar;
import brandapp.isport.com.basicres.commonbean.BaseUrl;
import brandapp.isport.com.basicres.commonnet.interceptor.BaseObserver;
import brandapp.isport.com.basicres.commonnet.interceptor.ExceptionHandle;
import brandapp.isport.com.basicres.commonnet.net.PostBody;
import brandapp.isport.com.basicres.commonutil.InfoUtil;
import brandapp.isport.com.basicres.mvp.BasePresenter;
import brandapp.isport.com.basicres.service.observe.NetProgressObservable;

/**
 * Created by Administrator on 2017/10/16.
 */
public class LoginPresenter extends BasePresenter<LoginBaseView> implements ILoginPresent {


    LoginBaseView view;

    public LoginPresenter(LoginBaseView view) {
        this.view = view;
    }


    /**
     * 登录
     */
    @Override
    public void login(String phoneNum, String code, String type) {

        String url = AllocationApi.postLoginMobileInfo();
        PostBody<LoginPar, BaseUrl, BaseDbPar> loginParBaseUrlBaseDbParPostBody = InitParms.setLoginParm(phoneNum, code, type, "0", JkConfiguration.RequstType.LOGIN_BY_MOBILE);
        CustomRepository<UserInfoBean, LoginPar, BaseUrl, BaseDbPar> customRepository = new CustomRepository();

        customRepository.requst(loginParBaseUrlBaseDbParPostBody, false)
                .as(view.bindAutoDispose())
                .subscribe(new BaseObserver<UserInfoBean>(BaseApp.getApp(), true) {
                    @Override
                    public void onNext(UserInfoBean userInfoBean) {
                        if (isViewAttached()) {
                            /**
                             * 保存Id号
                             */
                            mActView.get().loginSuccess(userInfoBean);

                        }
                    }

                    @Override
                    protected void hideDialog() {

                    }

                    @Override
                    protected void showDialog() {

                    }

                    @Override
                    public void onError(ExceptionHandle.ResponeThrowable e) {
                        doShowNetError();
                        if (isViewAttached()) {
                            //  mActView.get().onRespondError(e.getMessage().contains("Unable to resolve host") ? UIUtils.getString(R.string.common_please_check_that_your_network_is_connected) : e.getMessage().contains(":") ? e.getMessage().split(":")[1] : e.getMessage());
                        }
                        Log.d("TestRepository", e.getMessage());
                    }
                });

    }


    /**
     * 第三方登录
     *
     * @param platformType
     * @param authId
     * @param nickName
     * @param headUrl
     */
    @Override
    public void thirdPartyLogin(int platformType, String authId, String nickName, String headUrl) {


        Logger.myLog("thirdPartyLogin");
        PostBody<ThridPar, BaseUrl, BaseDbPar> parPostBody = new PostBody<>();
        parPostBody.data = new ThridPar();
        parPostBody.data.authId = authId;
        parPostBody.data.platformType = platformType + "";
        parPostBody.data.nickName = nickName;
        parPostBody.data.url = headUrl;
        parPostBody.type = JkConfiguration.RequstType.LOGIN_BY_THIRD;

        BaseUrl baseUrl = new BaseUrl();
        baseUrl.url1 = JkConfiguration.Url.BASIC;
        baseUrl.url2 = JkConfiguration.Url.CUSTOMER;
        baseUrl.url3 = JkConfiguration.Url.AUTHORIZED_LANDING;
        parPostBody.requseturl = baseUrl;

        CustomRepository<UserInfoBean, ThridPar, BaseUrl, BaseDbPar> customRepository = new CustomRepository<>();
        customRepository.requst(parPostBody, false).as(view.bindAutoDispose())
                .subscribe(new BaseObserver<UserInfoBean>(context, true) {
                    @Override
                    protected void hideDialog() {

                    }

                    @Override
                    protected void showDialog() {

                    }

                    @Override
                    public void onError(ExceptionHandle.ResponeThrowable e) {
                        if (isViewAttached()) {
                            ToastUtil.showTextToast(BaseApp.getApp(), e.message);
                        }
                    }

                    @Override
                    public void onNext(UserInfoBean userInfoBean) {
                        NetProgressObservable.getInstance().hide();

                        if (isViewAttached()) {
                            mActView.get().thirdPartyLoginSuccess(userInfoBean, platformType);
                        }
                    }
                });


    }

   /* @Override
    public void loginSuccess(UserInfoBean loginBean) {
        if (isViewAttached()) {
            *//**
     * 保存Id号
     *//*

            mActView.get().loginSuccess(loginBean);
            *//**
     * 获取用户信息
     *//*
            getUserInfoModel.getUserInfo();
        }
    }

    @Override
    public void getVerCodeSuccess(BaseBean baseBean) {
        if (isViewAttached()) {
            mActView.get().getVerCodeSuccess(baseBean);
        }
    }

    @Override
    public void getVerCodeNotPass(BaseBean baseBean) {
        if (isViewAttached()) {
            mActView.get().getVerCodeNotPass(baseBean);
        }
    }

    @Override
    public void thirdPartyLoginSuccess(UserInfoBean thirdLoginBean) {
        if (isViewAttached()) {
            mActView.get().thirdPartyLoginSuccess(thirdLoginBean);
        }
    }

    @Override
    public void loginBackCode(int code) {

    }


    @Override
    public void onRespondError(String message) {
        if (isViewAttached()) {
            mActView.get().onRespondError(message);
        }
    }
*/

    /**
     * 检测号码是否存在
     */
    public boolean checkPhoneNum(String phoneNum) {
        return InfoUtil.isPhoneNumberValid(phoneNum);
    }

    public boolean checkEmail(String email) {
        return InfoUtil.isEmail(email);
    }


    @Override
    public void getVerify(String phone, String type) {


        VerifyImpl<String> verify = new VerifyImpl<>();
        verify.getVerify(phone, type).as(view.bindAutoDispose()).subscribe(new BaseObserver<String>(context, true) {
            @Override
            protected void hideDialog() {

            }

            @Override
            protected void showDialog() {

            }

            @Override
            public void onError(ExceptionHandle.ResponeThrowable e) {
                if (isViewAttached()) {
                    NetProgressObservable.getInstance().hide();
                    mActView.get().onRespondError(e.message);
                }
            }

            @Override
            public void onNext(String message) {
                NetProgressObservable.getInstance().hide();

                if (isViewAttached()) {
                    mActView.get().getVerCodeSuccess(message);
                }
            }
        });


    }

    @Override
    public void getEmailVerify(String email, String type, String language) {
        VerifyImpl<String> verify = new VerifyImpl<>();
        verify.getEmailVerify(email, type, language).as(view.bindAutoDispose()).subscribe(new BaseObserver<String>(context, true) {
            @Override
            protected void hideDialog() {

            }

            @Override
            protected void showDialog() {

            }

            @Override
            public void onError(ExceptionHandle.ResponeThrowable e) {
                if (isViewAttached()) {
                    NetProgressObservable.getInstance().hide();
                    mActView.get().onRespondError(e.message);
                }
            }

            @Override
            public void onNext(String message) {
                NetProgressObservable.getInstance().hide();

                if (isViewAttached()) {
                    mActView.get().getVerCodeSuccess(message);
                }
            }
        });

    }

    @Override
    public void doRefresh() {

    }

    @Override
    public void doShowNetError() {

    }
}