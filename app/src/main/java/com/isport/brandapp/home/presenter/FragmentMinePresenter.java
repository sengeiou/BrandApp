package com.isport.brandapp.home.presenter;


import com.isport.brandapp.App;
import com.isport.brandapp.home.view.FragmentMineView;

import brandapp.isport.com.basicres.commonbean.UserInfoBean;
import brandapp.isport.com.basicres.entry.bean.BaseParms;
import com.isport.brandapp.repository.CustomRepository;
import com.isport.brandapp.util.InitParms;

import brandapp.isport.com.basicres.BaseApp;
import phone.gym.jkcq.com.commonres.common.AllocationApi;
import phone.gym.jkcq.com.commonres.common.JkConfiguration;
import brandapp.isport.com.basicres.commonbean.BaseDbPar;
import brandapp.isport.com.basicres.commonbean.BaseUrl;
import brandapp.isport.com.basicres.commonnet.interceptor.BaseObserver;
import brandapp.isport.com.basicres.commonnet.interceptor.ExceptionHandle;
import brandapp.isport.com.basicres.commonnet.net.PostBody;
import brandapp.isport.com.basicres.mvp.BasePresenter;

/**
 * Created by huashao on 2017/10/27.
 */

public class FragmentMinePresenter extends BasePresenter<FragmentMineView> implements IFragmentMinePresenter {
    private FragmentMineView view;

    public FragmentMinePresenter(FragmentMineView view) {
        this.view = view;
    }


    @Override
    public void getUserInfo(String userId) {
        String url = AllocationApi.postCustomerRelationInfo();
        CustomRepository<UserInfoBean, BaseParms, BaseUrl, BaseDbPar> customRepository = new CustomRepository();
        PostBody<BaseParms, BaseUrl, BaseDbPar> baseParmsBaseUrlBaseDbParPostBody = InitParms.setUserPar(userId, "0", !(App.appType() == App.httpType), JkConfiguration.RequstType.GET_USERINFO);
        customRepository.requst(baseParmsBaseUrlBaseDbParPostBody, false)
                .subscribe(new BaseObserver<UserInfoBean>(BaseApp.getApp()) {

                    @Override
                    public void onNext(UserInfoBean userInfoBean) {
                        if (isViewAttached()) {
                            mActView.get().successGetUserInfo(userInfoBean);
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

                    }
                });
    }
}
