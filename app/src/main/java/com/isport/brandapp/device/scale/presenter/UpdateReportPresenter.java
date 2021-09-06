package com.isport.brandapp.device.scale.presenter;

import com.isport.blelibrary.db.action.BleAction;
import com.isport.blelibrary.db.table.scale.Scale_FourElectrode_DataModel;
import com.isport.blelibrary.utils.Logger;
import com.isport.brandapp.App;
import com.isport.brandapp.R;
import com.isport.brandapp.device.UpdateSuccessBean;
import com.isport.brandapp.device.scale.view.UpdateReportView;
import com.isport.brandapp.parm.http.EditeUserParm;
import com.isport.brandapp.repository.CustomRepository;
import com.isport.brandapp.repository.ScaleRepository;
import com.isport.brandapp.util.InitParms;

import brandapp.isport.com.basicres.BaseApp;
import phone.gym.jkcq.com.commonres.common.AllocationApi;
import phone.gym.jkcq.com.commonres.common.JkConfiguration;
import brandapp.isport.com.basicres.commonbean.BaseDbPar;
import brandapp.isport.com.basicres.commonbean.BaseUrl;
import brandapp.isport.com.basicres.commonnet.interceptor.BaseObserver;
import brandapp.isport.com.basicres.commonnet.interceptor.ExceptionHandle;
import brandapp.isport.com.basicres.commonnet.net.PostBody;
import brandapp.isport.com.basicres.commonutil.StringUtil;
import brandapp.isport.com.basicres.commonutil.TokenUtil;
import brandapp.isport.com.basicres.commonutil.UIUtils;
import brandapp.isport.com.basicres.mvp.BasePresenter;
import brandapp.isport.com.basicres.service.observe.NetProgressObservable;
import io.reactivex.functions.Consumer;

/**
 * @Author
 * @Date 2018/10/23
 * @Fuction
 */

public class UpdateReportPresenter extends BasePresenter<UpdateReportView> {

    private UpdateReportView view;

    public UpdateReportPresenter(UpdateReportView view) {
        this.view = view;
    }

    public void onRespondError(String message) {
        if (isViewAttached()) {
            mActView.get().onRespondError(message);
        }
    }


    public void updateReport(Scale_FourElectrode_DataModel scale_fourElectrode_dataModel) {
        // TODO: 2019/1/12 暂SDK存储完毕后直接返回，刷新主页数据
        if (App.appType() == App.httpType) {
            Logger.myLog("去上传");
            //mUpdateReportModelImp.updateReport(mScaleCalculateBean);
            ScaleRepository.requst(scale_fourElectrode_dataModel).as(view.bindAutoDispose()).subscribe(new BaseObserver<UpdateSuccessBean>(context) {
                @Override
                protected void hideDialog() {

                }

                @Override
                protected void showDialog() {

                }

                @Override
                public void onError(ExceptionHandle.ResponeThrowable e) {
                    if (isViewAttached()) {
                        Logger.myLog("updateReport==" + e.message);
                        mActView.get().onRespondError(e.message);
                    }
                }

                @Override
                public void onNext(UpdateSuccessBean updateSuccessBean) {
                    NetProgressObservable.getInstance().hide();
                    //上传成功更新reportId
                    scale_fourElectrode_dataModel.setReportId( updateSuccessBean.getPublicId());
                    BleAction.getScale_FourElectrode_DataModelDao().update(scale_fourElectrode_dataModel);
                    if (isViewAttached()) {
                        mActView.get().updateSuccess(updateSuccessBean, scale_fourElectrode_dataModel);
                    }
                }
            });
        } else {
            if (isViewAttached()) {
                mActView.get().updateSuccess(null, scale_fourElectrode_dataModel);
            }
        }
    }

    public void saveUserBaseicInfo(String sex, String name, String height, String weight, String defaultDay) {
/**
 * String url = AllocationApi.postEditCustomerBasicInfo();
 * HashMap<String, Object> parmes = new HashMap<>();
 * parmes.put("userId", TokenUtil.getInstance().getPeopleIdStr(BaseApp.getApp()));
 * parmes.put("interfaceId", String.valueOf(0));
 * parmes.put("nickName", name);
 * parmes.put("gender", sex);
 * parmes.put("height", StringUtil.getNumberStr(height));
 * parmes.put("weight", StringUtil.getNumberStr(weight));
 * parmes.put("birthday", defaultDay);
 */


        String url = AllocationApi.postEditCustomerBasicInfo();
        CustomRepository<String, EditeUserParm, BaseUrl, BaseDbPar> customRepository = new CustomRepository();
        PostBody<EditeUserParm, BaseUrl, BaseDbPar> editeUserParmBaseUrlBaseDbParPostBody = InitParms.setUserPar(TokenUtil.getInstance().getPeopleIdStr(BaseApp.getApp()), "0",
                name, sex, StringUtil.getNumberStr(height), StringUtil
                        .getNumberStr(weight), defaultDay, !(App.appType() == App.httpType), JkConfiguration.RequstType.EDITBASICINFO);
        customRepository.requst(editeUserParmBaseUrlBaseDbParPostBody, true)
                .as(view.bindAutoDispose())
                .subscribe(new Consumer<String>() {

                    @Override
                    public void accept(String message) throws Exception {
                        /**
                         * 保存Id号
                         */
                        if (isViewAttached()) {
                            mActView.get().saveUserBaseInfoSuccess();
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        if (isViewAttached()) {
                            mActView.get().onRespondError(throwable.getMessage().contains("Unable to resolve host") ? UIUtils.getString(R.string.common_please_check_that_your_network_is_connected) : throwable.getMessage().contains(":") ? throwable.getMessage().split(":")[1] : throwable.getMessage());
                        }
                    }
                });


        //model.saveUserBaseicInfo(sex, name, height, weight, defaultDay);
    }
}
