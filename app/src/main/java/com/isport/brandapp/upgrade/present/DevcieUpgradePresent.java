package com.isport.brandapp.upgrade.present;

import com.isport.brandapp.parm.db.ProgressShowParms;
import brandapp.isport.com.basicres.entry.bean.BaseParms;
import com.isport.brandapp.repository.MainResposition;
import com.isport.brandapp.upgrade.bean.DeviceUpgradeBean;
import com.isport.brandapp.upgrade.view.DeviceUpgradeView;
import com.isport.brandapp.util.InitCommonParms;

import brandapp.isport.com.basicres.BaseApp;
import phone.gym.jkcq.com.commonres.common.JkConfiguration;
import brandapp.isport.com.basicres.commonbean.BaseUrl;
import brandapp.isport.com.basicres.commonnet.interceptor.BaseObserver;
import brandapp.isport.com.basicres.commonnet.interceptor.ExceptionHandle;
import brandapp.isport.com.basicres.commonutil.TokenUtil;
import brandapp.isport.com.basicres.mvp.BasePresenter;

public class DevcieUpgradePresent extends BasePresenter<DeviceUpgradeView> {
    DeviceUpgradeView deviceUpgradeView;

    public DevcieUpgradePresent(DeviceUpgradeView deviceUpgradeView) {
        this.deviceUpgradeView = deviceUpgradeView;
    }

    public void getDeviceUpgradeInfo(int type) {
        MainResposition<DeviceUpgradeBean, BaseParms, BaseUrl, ProgressShowParms> mainResposition = new MainResposition<>();
        InitCommonParms<BaseParms, BaseUrl, ProgressShowParms> commonParms = new InitCommonParms<>();
        BaseParms parms = new BaseParms();
        parms.setUserId( TokenUtil.getInstance().getPeopleIdStr(BaseApp.getApp()));
        parms.setInterfaceId("0");
        BaseUrl baseUrl = new BaseUrl();
        baseUrl.url1 = JkConfiguration.Url.BASIC;
        baseUrl.url2 = JkConfiguration.Url.DEVICEVERSION;
        baseUrl.extend1 = type + "";
        ProgressShowParms deviceIdParms = new ProgressShowParms();
        mainResposition.requst(commonParms.setPostBody(false).setParms(parms)
                .setBaseUrl(baseUrl)
                .setBaseDbParms(deviceIdParms).setType(JkConfiguration.RequstType
                        .DEVCIE_UPGRAGE_INFO)
                .getPostBody())
                .as(deviceUpgradeView.bindAutoDispose()).subscribe(new BaseObserver<DeviceUpgradeBean>(BaseApp.getApp(), false) {

            @Override
            public void onNext(DeviceUpgradeBean successDeviceUpgradeInfo) {

                if (deviceUpgradeView != null) {
                    deviceUpgradeView.successDeviceUpgradeInfo(successDeviceUpgradeInfo);
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
                if (deviceUpgradeView != null) {
                    deviceUpgradeView.onRespondError(e.message);
                }
            }
        });

    }

}
