package com.isport.brandapp.device.scale.presenter;

import com.isport.blelibrary.db.table.scale.Scale_FourElectrode_DataModel;
import com.isport.blelibrary.utils.Logger;
import com.isport.brandapp.App;
import com.isport.brandapp.AppConfiguration;
import com.isport.brandapp.device.scale.bean.ScaleReportBean;
import com.isport.brandapp.device.scale.view.ScaleReportView;
import com.isport.brandapp.parm.db.ScaleReportParms;
import com.isport.brandapp.repository.MainResposition;
import com.isport.brandapp.util.InitCommonParms;
import com.isport.brandapp.util.RequestCode;

import brandapp.isport.com.basicres.BaseApp;
import brandapp.isport.com.basicres.commonbean.BaseUrl;
import brandapp.isport.com.basicres.commonnet.interceptor.BaseObserver;
import brandapp.isport.com.basicres.commonnet.interceptor.ExceptionHandle;
import brandapp.isport.com.basicres.commonutil.TokenUtil;
import brandapp.isport.com.basicres.entry.bean.BaseParms;
import brandapp.isport.com.basicres.mvp.BasePresenter;
import brandapp.isport.com.basicres.service.observe.NetProgressObservable;
import phone.gym.jkcq.com.commonres.common.JkConfiguration;

/**
 * @Author
 * @Date 2018/10/24
 * @Fuction
 */
public class ScaleReportPresenter extends BasePresenter<ScaleReportView> {

    private ScaleReportView view;

    public ScaleReportPresenter(ScaleReportView view) {
        this.view = view;
    }

    public void getScaleReport(Scale_FourElectrode_DataModel mScale_fourElectrode_dataModel, boolean isDBData, long
            reportId) {
        //mScaleReportModelImp.getScaleReport(id);
        MainResposition<ScaleReportBean, BaseParms, BaseUrl, ScaleReportParms> mainResposition = new
                MainResposition<>();
        InitCommonParms<BaseParms, BaseUrl, ScaleReportParms> initCommonParms = new InitCommonParms<>();
        BaseUrl url = new BaseUrl();
        url.url1 = JkConfiguration.Url.FAT_STEELYARD;
        url.url2 = JkConfiguration.Url.REPORT_DATA;
        url.userid = TokenUtil.getInstance().getPeopleIdInt(BaseApp.getApp());
        ScaleReportParms scaleReportParms = new ScaleReportParms();
        scaleReportParms.deviceId = AppConfiguration.deviceMainBeanList.get(JkConfiguration.DeviceType.BODYFAT).deviceID;
        scaleReportParms.requestCode = RequestCode.Request_getScalerReportData;
        scaleReportParms.mScale_fourElectrode_dataModel = mScale_fourElectrode_dataModel;
        scaleReportParms.timeTamp = reportId;
        Logger.myLog("requestCode == " + RequestCode.Request_getScalerReportData);
        mainResposition.requst(initCommonParms.setPostBody(isDBData ? isDBData : !(App.appType() == App.httpType)).setBaseDbParms
                (scaleReportParms).setBaseUrl
                (url).setType(JkConfiguration.RequstType.FATSTEELYARD_REPORT).getPostBody())
                .as(view.bindAutoDispose())
                .subscribe(new BaseObserver<ScaleReportBean>(context) {
                    @Override
                    protected void hideDialog() {

                    }

                    @Override
                    protected void showDialog() {

                    }

                    @Override
                    public void onError(ExceptionHandle.ResponeThrowable e) {
                        if (isViewAttached())
                            Logger.myLog("getScaleReport == " + e.message);
                        mActView.get().onRespondError(e.message);
                    }

                    @Override
                    public void onNext(ScaleReportBean scaleReportBean) {
                        NetProgressObservable.getInstance().hide();
                        if (isViewAttached()) {
                            mActView.get().getScaleReportSuccess(scaleReportBean);
                        }
                    }
                });

    }

}
