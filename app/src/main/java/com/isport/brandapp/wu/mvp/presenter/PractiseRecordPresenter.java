package com.isport.brandapp.wu.mvp.presenter;

import com.isport.brandapp.AppConfiguration;
import com.isport.brandapp.repository.ExerciseRepository;
import com.isport.brandapp.wu.bean.PractiseRecordInfo;
import com.isport.brandapp.wu.bean.PractiseTimesInfo;
import com.isport.brandapp.wu.mvp.PractiseRecordView;

import java.util.List;

import brandapp.isport.com.basicres.BaseApp;
import brandapp.isport.com.basicres.commonnet.interceptor.BaseObserver;
import brandapp.isport.com.basicres.commonnet.interceptor.ExceptionHandle;
import brandapp.isport.com.basicres.commonutil.TokenUtil;
import brandapp.isport.com.basicres.mvp.BasePresenter;

public class PractiseRecordPresenter extends BasePresenter<PractiseRecordView> implements IPractiseRecordPresenter {

    @Override
    public void getPractiseRecordData(int deviceTyp, int type, int page) {
        ExerciseRepository.requstSportRecordPageData(deviceTyp, AppConfiguration.braceletID, TokenUtil.getInstance().getPeopleIdStr(BaseApp.getApp()), type, page).as(mActView.get().bindAutoDispose()).subscribe(new BaseObserver<List<PractiseRecordInfo>>(BaseApp.getApp(), true) {
            @Override
            public void onNext(List<PractiseRecordInfo> infos) {
                if (isViewAttached()) {
                    mActView.get().getPractiseRecordSuccess(infos);
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

    @Override
    public void getTotalPractiseTimes(int deviceTyp, int type) {
        ExerciseRepository.requstTotalPractiseData(deviceTyp, AppConfiguration.braceletID, TokenUtil.getInstance().getPeopleIdStr(BaseApp.getApp()), type).as(mActView.get().bindAutoDispose()).subscribe(new BaseObserver<PractiseTimesInfo>(BaseApp.getApp(), false) {
            @Override
            public void onNext(PractiseTimesInfo data) {
                if (isViewAttached()) {
                    mActView.get().getTotalPractiseTimesSuccess(data);
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

    @Override
    public void getPractiseRecordDataSuccess(List<PractiseRecordInfo> infos) {
        if (isViewAttached()) {
            mActView.get().getPractiseRecordSuccess(infos);
        }
    }
}
