package com.isport.brandapp.home.presenter;

import com.isport.brandapp.home.bean.SportLastDataBeanList;
import com.isport.brandapp.home.view.FragmentSportView;
import com.isport.brandapp.sport.response.SportRepository;

import brandapp.isport.com.basicres.BaseApp;
import brandapp.isport.com.basicres.commonnet.interceptor.BaseObserver;
import brandapp.isport.com.basicres.commonnet.interceptor.ExceptionHandle;
import brandapp.isport.com.basicres.mvp.BasePresenter;

public class FragmentSportPresenter extends BasePresenter<FragmentSportView> implements IFragmentSportPresenter {
    FragmentSportView view;

    public FragmentSportPresenter(FragmentSportView view) {
        this.view = view;
    }

    @Override
    public void getAllLastData(String userId) {
        SportRepository sportRepository = new SportRepository();
        sportRepository.getLastData().as(view.bindAutoDispose()).subscribe(new BaseObserver<SportLastDataBeanList>(BaseApp.getApp(), false) {
            @Override
            protected void hideDialog() {

            }

            @Override
            protected void showDialog() {

            }

            @Override
            public void onError(ExceptionHandle.ResponeThrowable e) {

            }

            @Override
            public void onNext(SportLastDataBeanList sportLastDataBeanList) {

                if (view != null) {
                    view.success(sportLastDataBeanList);
                }

            }
        });
    }
}
