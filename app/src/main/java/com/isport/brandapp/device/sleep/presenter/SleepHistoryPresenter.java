package com.isport.brandapp.device.sleep.presenter;

import com.isport.brandapp.device.history.util.HistoryParmUtil;
import com.isport.brandapp.device.sleep.bean.SleepHistoryBean;
import com.isport.brandapp.device.sleep.bean.SleepHistoryList;
import com.isport.brandapp.device.sleep.view.SleepHistoryView;
import com.isport.brandapp.parm.db.DeviceHistoryParms;
import com.isport.brandapp.parm.http.HistoryParm;
import com.isport.brandapp.repository.MainResposition;

import java.util.ArrayList;

import phone.gym.jkcq.com.commonres.common.JkConfiguration;
import brandapp.isport.com.basicres.commonbean.BaseResponse;
import brandapp.isport.com.basicres.commonbean.BaseUrl;
import brandapp.isport.com.basicres.commonnet.interceptor.BaseObserver;
import brandapp.isport.com.basicres.commonnet.interceptor.ExceptionHandle;
import brandapp.isport.com.basicres.mvp.BasePresenter;
import brandapp.isport.com.basicres.service.observe.NetProgressObservable;

/**
 * @Author
 * @Date 2018/10/29
 * @Fuction
 */

public class SleepHistoryPresenter extends BasePresenter<SleepHistoryView> {


    private SleepHistoryView view;

    public SleepHistoryPresenter(SleepHistoryView view) {
        this.view = view;
    }


    public void getSleepHistoryModel(int requestCode,long timeTamp,String deviceId,String currentMonth,String deviceType, String mac, String time, String pageNum, String size) {

        MainResposition<BaseResponse<SleepHistoryBean>, HistoryParm, BaseUrl, DeviceHistoryParms> mainResposition = new MainResposition<>();
        // TODO: 2019/1/25 获取历史
        mainResposition.requst(HistoryParmUtil.setHistory(1,JkConfiguration.Url.SLEEPBELT, JkConfiguration.Url.HISTORY_DATA, requestCode, timeTamp,deviceId, currentMonth,deviceType, mac, time, pageNum, size, JkConfiguration.RequstType.SLEEP_HISTORY))
                .as(view.bindAutoDispose())
                .subscribe(new BaseObserver<BaseResponse<SleepHistoryBean>>(context) {
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
                    public void onNext(BaseResponse<SleepHistoryBean> deviceHomeData) {
                        NetProgressObservable.getInstance().hide();
                        if (deviceHomeData.getData() != null && deviceHomeData.getData().list != null) {
                            mActView.get().successLoadMore((ArrayList<SleepHistoryList>) deviceHomeData.getData().list, deviceHomeData.isIslastdata());
                        }
                    }
                });


    }


    public void getFirstHistoryModel(int requestCode,long timeTamp,String deviceId,String currentMonth,String deviceType, String mac, String time, String pageNum, String size) {

        MainResposition<BaseResponse<SleepHistoryBean>, HistoryParm, BaseUrl, DeviceHistoryParms> mainResposition = new MainResposition<>();
        // TODO: 2019/1/25 获取历史
        mainResposition.requst(HistoryParmUtil.setHistory(1,JkConfiguration.Url.SLEEPBELT, JkConfiguration.Url.HISTORY_DATA, requestCode, timeTamp,deviceId,currentMonth, deviceType, mac, time, pageNum, size, JkConfiguration.RequstType.SLEEP_HISTORY))
                .as(view.bindAutoDispose())
                .subscribe(new BaseObserver<BaseResponse<SleepHistoryBean>>(context) {
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
                    public void onNext(BaseResponse<SleepHistoryBean> deviceHomeData) {
                        NetProgressObservable.getInstance().hide();
                        if (isViewAttached()) {
                            if (deviceHomeData.getData() != null && deviceHomeData.getData().list != null) {
                                mActView.get().successRefresh((ArrayList<SleepHistoryList>) deviceHomeData.getData().list, deviceHomeData.isIslastdata());
                            }
                        }
                    }
                });

    }
}
