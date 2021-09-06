package com.isport.brandapp.device.history.presenter;

import com.isport.brandapp.device.band.bean.BandDayBean;
import com.isport.brandapp.device.band.bean.BandHistoryList;
import com.isport.brandapp.device.history.util.HistoryParmUtil;
import com.isport.brandapp.device.history.view.HistoryView;
import com.isport.brandapp.parm.db.DeviceHistoryParms;
import com.isport.brandapp.parm.http.HistoryParm;
import com.isport.brandapp.repository.MainResposition;

import java.util.ArrayList;

import phone.gym.jkcq.com.commonres.common.JkConfiguration;
import brandapp.isport.com.basicres.commonbean.BaseUrl;
import brandapp.isport.com.basicres.commonnet.interceptor.BaseObserver;
import brandapp.isport.com.basicres.commonnet.interceptor.ExceptionHandle;
import brandapp.isport.com.basicres.mvp.BasePresenter;
import brandapp.isport.com.basicres.service.observe.NetProgressObservable;

/**
 * @Author flyly
 * @Date 2018/11/1
 * @Fuction
 */
public class HistoryPresenter extends BasePresenter<HistoryView> {

    private HistoryView view;

    public HistoryPresenter(HistoryView view) {
        this.view = view;
    }


    public void getSportHistroyModel(int requestCode, long timeTamp, String deviceId, String currentMonth, String
            deviceType, String mac, String time, String pageNum, String size) {
        MainResposition<BandHistoryList, HistoryParm, BaseUrl, DeviceHistoryParms> mainResposition = new
                MainResposition<>();
        // TODO: 2019/1/25 获取历史
        mainResposition.requst(HistoryParmUtil.setHistory(1, JkConfiguration.Url.WRISTBAND_STEP, JkConfiguration.Url
                                                                  .HISTORY_DATA, requestCode, timeTamp, deviceId,
                                                          currentMonth, deviceType, mac, time, pageNum, size,
                                                          JkConfiguration.RequstType.WRISTBAND_HISTORY))
                .as(view.bindAutoDispose())
                .subscribe(new BaseObserver<BandHistoryList>(context) {
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
                    public void onNext(BandHistoryList deviceHomeData) {
                        NetProgressObservable.getInstance().hide();
                        if (deviceHomeData.getList() != null) {
                            mActView.get().successSportLoadMore((ArrayList<BandDayBean>) deviceHomeData.getList());
                        }
                    }
                });

    }

    public void getFirstHistoryModel(int requestCode, long timeTamp, String deviceId, String currentMonth, String
            deviceType, String mac, String time, String pageNum, String size) {
        MainResposition<BandHistoryList, HistoryParm, BaseUrl, DeviceHistoryParms> mainResposition = new
                MainResposition<>();
        // TODO: 2019/1/25 获取历史
        mainResposition.requst(HistoryParmUtil.setHistory(1, JkConfiguration.Url.WRISTBAND_STEP, JkConfiguration.Url
                                                                  .HISTORY_DATA, requestCode, timeTamp, deviceId,
                                                          currentMonth, deviceType, mac, time, pageNum, size,
                                                          JkConfiguration.RequstType.WRISTBAND_HISTORY))
                .as(view.bindAutoDispose())
                .subscribe(new BaseObserver<BandHistoryList>(context) {
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
                    public void onNext(BandHistoryList deviceHomeData) {
                        NetProgressObservable.getInstance().hide();
                        if (deviceHomeData.getList() != null) {
                            mActView.get().successSportRefresh((ArrayList<BandDayBean>) deviceHomeData.getList());
                        }
                    }
                });

    }


}
