package com.isport.brandapp.home.presenter;

import android.content.Context;

import com.isport.blelibrary.utils.Logger;
import com.isport.brandapp.App;
import com.isport.brandapp.device.history.util.HistoryParmUtil;
import com.isport.brandapp.device.watch.bean.WatchHistoryNBean;
import com.isport.brandapp.device.watch.bean.WatchHistoryNList;
import com.isport.brandapp.parm.http.WatchHistoryParms;
import com.isport.brandapp.repository.CustomRepository;
import com.isport.brandapp.util.DeviceDataUtil;
import com.isport.brandapp.util.DeviceTypeUtil;
import com.isport.brandapp.util.W311DeviceDataUtil;

import java.util.Calendar;
import java.util.List;

import brandapp.isport.com.basicres.commonbean.BaseDbPar;
import brandapp.isport.com.basicres.commonbean.BaseUrl;
import brandapp.isport.com.basicres.commonnet.interceptor.BaseObserver;
import brandapp.isport.com.basicres.commonnet.interceptor.ExceptionHandle;
import brandapp.isport.com.basicres.commonnet.net.PostBody;
import brandapp.isport.com.basicres.service.observe.NetProgressObservable;

public class DeviceHistotyDataPresenter {


    public synchronized static void getMonthData(Calendar instance, int requstType, int currentType, Context context) {

        //需要区分是w516还是w311
        Logger.myLog("HistotyDataPresenter getMonthData " + "requstType:" + requstType + "currentType:" + currentType);

        if (!(App.appType() == App.httpType)) {
            return;
        }

        /**
         * 获取步数，当月的数据，同步到本地
         */
        CustomRepository<WatchHistoryNList, WatchHistoryParms, BaseUrl, BaseDbPar> watchHistory = new
                CustomRepository<>();
        PostBody<WatchHistoryParms, BaseUrl, BaseDbPar> watchHistoryDataList = null;
        if (DeviceTypeUtil.isContainWrishBrand(currentType)) {
            watchHistoryDataList = HistoryParmUtil
                    .getBraceletHistoryByTimeTampData(instance.getTimeInMillis(), requstType);
        } else {
            watchHistoryDataList = HistoryParmUtil
                    .getWatchHistoryByTimeTampData(instance.getTimeInMillis(), requstType);
        }
        if (watchHistoryDataList == null) {
            return;
        }
        watchHistory.requst(watchHistoryDataList).subscribe(new BaseObserver<WatchHistoryNList>(context) {
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
            public void onNext(WatchHistoryNList watchHistoryNList) {
                NetProgressObservable.getInstance().hide();
                //Logger.myLog("HistotyDataPresenter getMonthData 获取主页WatchHistoryNList成功 == " + watchHistoryNList.toString());
                if (watchHistoryNList.getList() == null || (watchHistoryNList.getList() != null && watchHistoryNList
                        .getList().size() == 0)) {
                    //无数据的情况
                } else {
                    //有数据的情况
                    List<WatchHistoryNBean> list = watchHistoryNList.getList();
                    for (int i = 0; i < list.size(); i++) {
                        WatchHistoryNBean watchHistoryNBean = list.get(i);
                        if (DeviceTypeUtil.isContainWatch(currentType)) {
                            DeviceDataUtil.getWatch_W516_24HDataModel(watchHistoryNBean, requstType);
                        } else if (DeviceTypeUtil.isContainWrishBrand(currentType)) {
                            //  Logger.myLog("HistotyDataPresenter onNext " + "requstType:" + requstType + "currentType:" + currentType+"watchHistoryNBean:"+watchHistoryNBean.getHeartRateDetailArray());
                            W311DeviceDataUtil.getBracelet_W311_24HDataModel(watchHistoryNBean, requstType);
                        }
                    }
                }
            }
        });
        NetProgressObservable.getInstance().hide();

    }


}
