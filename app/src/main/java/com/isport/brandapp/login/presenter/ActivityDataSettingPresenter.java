package com.isport.brandapp.login.presenter;

import android.util.Log;

import com.isport.blelibrary.ISportAgent;
import com.isport.blelibrary.utils.BleRequest;
import com.isport.brandapp.App;
import com.isport.brandapp.AppConfiguration;
import com.isport.brandapp.R;
import com.isport.brandapp.login.view.ActivitySettingUserInfoView;
import brandapp.isport.com.basicres.entry.bean.BaseParms;
import com.isport.brandapp.parm.http.EditeUserParm;
import com.isport.brandapp.repository.CustomRepository;
import com.isport.brandapp.util.InitParms;

import brandapp.isport.com.basicres.BaseApp;
import phone.gym.jkcq.com.commonres.common.AllocationApi;
import phone.gym.jkcq.com.commonres.common.JkConfiguration;
import brandapp.isport.com.basicres.commonbean.BaseDbPar;
import brandapp.isport.com.basicres.commonbean.BaseUrl;
import brandapp.isport.com.basicres.commonbean.UserInfoBean;
import brandapp.isport.com.basicres.commonnet.net.PostBody;
import brandapp.isport.com.basicres.commonutil.StringUtil;
import brandapp.isport.com.basicres.commonutil.TokenUtil;
import brandapp.isport.com.basicres.commonutil.UIUtils;
import brandapp.isport.com.basicres.mvp.BasePresenter;
import io.reactivex.functions.Consumer;

/**
 * Created by huashao on 2017/10/30.
 */

public class ActivityDataSettingPresenter extends BasePresenter<ActivitySettingUserInfoView> implements
        IActivityDataSettingPresenter {
    ActivitySettingUserInfoView view;

    public ActivityDataSettingPresenter(ActivitySettingUserInfoView view) {
        this.view = view;
    }


    @Override
    public void getCustomerBasicInfo() {

        String url = AllocationApi.postCustomerRelationInfo();
        CustomRepository<UserInfoBean, BaseParms, BaseUrl, BaseDbPar> customRepository = new CustomRepository();
        PostBody<BaseParms, BaseUrl, BaseDbPar> baseParmsBaseUrlBaseDbParPostBody = InitParms.setUserPar(TokenUtil.getInstance().getPeopleIdStr(BaseApp.getApp()), "0", !(App.appType() == App.httpType), JkConfiguration.RequstType.GET_USERINFO);
        customRepository.requst(baseParmsBaseUrlBaseDbParPostBody, false)
                .as(view.bindAutoDispose())
                .subscribe(new Consumer<UserInfoBean>() {

                    @Override
                    public void accept(UserInfoBean userInfoBean) throws Exception {
                        Log.e("CustomRepository", userInfoBean.toString());
                        /**
                         * 保存Id号
                         */
                        if (isViewAttached()) {
                            mActView.get().getUserInfo(userInfoBean);
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


    }

    @Override
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
                            //如果连接是W311需要去设置用户的身高和体重
                            if (AppConfiguration.isConnected) {
                                if (ISportAgent.getInstance().getCurrnetDevice().deviceType == JkConfiguration.DeviceType.BRAND_W311) {
                                    ISportAgent.getInstance().requsetW311Ble(BleRequest.BRACELET_W311_SET_USERINFO);
                                }
                            }
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        if (isViewAttached()) {
                           // mActView.get().onRespondError(throwable.getMessage().contains("Unable to resolve host") ? UIUtils.getString(R.string.common_please_check_that_your_network_is_connected) : throwable.getMessage().contains(":") ? throwable.getMessage().split(":")[1] : throwable.getMessage());
                        }
                    }
                });


        //model.saveUserBaseicInfo(sex, name, height, weight, defaultDay);
    }



   /* @Override
    public void saveUserBaseInfoSuccess() {

        if (isViewAttached()) {
            mActView.get().saveUserBaseInfoSuccess();
        }
    }
*/

}
