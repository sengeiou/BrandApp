package com.isport.brandapp.login.presenter;

import com.isport.brandapp.login.view.ActivityBindMobilePhoneView;
import com.isport.brandapp.parm.http.LoginPar;
import com.isport.brandapp.repository.CustomRepository;
import com.isport.brandapp.util.InitParms;

import phone.gym.jkcq.com.commonres.common.AllocationApi;
import phone.gym.jkcq.com.commonres.common.JkConfiguration;
import brandapp.isport.com.basicres.commonbean.BaseDbPar;
import brandapp.isport.com.basicres.commonbean.BaseUrl;
import brandapp.isport.com.basicres.commonbean.UserInfoBean;
import brandapp.isport.com.basicres.commonnet.interceptor.BaseObserver;
import brandapp.isport.com.basicres.commonnet.interceptor.ExceptionHandle;
import brandapp.isport.com.basicres.commonnet.net.PostBody;
import brandapp.isport.com.basicres.commonutil.InfoUtil;
import brandapp.isport.com.basicres.mvp.BasePresenter;
import brandapp.isport.com.basicres.service.observe.NetProgressObservable;
import io.reactivex.functions.Consumer;

/**
 * Created by huashao on 2017/10/27.
 */

public class ActivityBindMobilePhonePresenter extends BasePresenter<ActivityBindMobilePhoneView> {


    ActivityBindMobilePhoneView view;

    public ActivityBindMobilePhonePresenter(ActivityBindMobilePhoneView view) {
        this.view = view;

    }


    /**
     * 获取验证码
     *
     * @param mobile
     */
    public void getVerCode(String mobile) {


        VerifyImpl<String> verify = new VerifyImpl<>();
        verify.getVerify(mobile, "1").as(view.bindAutoDispose()).subscribe(new BaseObserver<String>(context) {
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

    /**
     * 绑定手机
     *
     * @param phoneNum
     * @param code
     */
    public void bindPhone(String phoneNum, String code, String userId) {

        String url = AllocationApi.postLoginMobileInfo();
        PostBody<LoginPar, BaseUrl, BaseDbPar> loginParBaseUrlBaseDbParPostBody = InitParms.setLoginParm(phoneNum, code, "1", userId, JkConfiguration.RequstType.LOGIN_BY_MOBILE);
        CustomRepository<UserInfoBean, LoginPar, BaseUrl, BaseDbPar> customRepository = new CustomRepository();
        customRepository.requst(loginParBaseUrlBaseDbParPostBody, false)
                .as(view.bindAutoDispose())
                .subscribe(new Consumer<UserInfoBean>() {

                    @Override
                    public void accept(UserInfoBean userInfoBean) throws Exception {
                        if (isViewAttached()) {
                            /**
                             * 保存Id号
                             */
                            mActView.get().bindPhoneSuccess(userInfoBean);

                            // getUserInfoModel.getUserInfo();
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        if (isViewAttached()) {
                            NetProgressObservable.getInstance().hide();
                            mActView.get().onRespondError(throwable.getMessage().contains(":") ? throwable.getMessage().split(":")[1] : throwable.getMessage());
                        }

                    }
                });

    }


    /**
     * 检测号码是否存在
     */
    public boolean checkPhoneNum(String phoneNum) {

        return InfoUtil.isPhoneNumberValid(phoneNum);
    }


}
