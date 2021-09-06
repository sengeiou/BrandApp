package com.isport.brandapp.device.bracelet.playW311.PlayW311Presenter;

import com.isport.brandapp.R;
import com.isport.brandapp.device.bracelet.playW311.bean.PlayBean;
import com.isport.brandapp.device.bracelet.playW311.view.PlayerView;
import com.isport.brandapp.repository.PlayBandRepository;
import com.isport.brandapp.util.UserAcacheUtil;

import java.util.List;

import brandapp.isport.com.basicres.BaseApp;
import brandapp.isport.com.basicres.commonnet.interceptor.BaseObserver;
import brandapp.isport.com.basicres.commonnet.interceptor.ExceptionHandle;
import brandapp.isport.com.basicres.commonutil.UIUtils;
import brandapp.isport.com.basicres.mvp.BasePresenter;

public class PlayerPresenter extends BasePresenter<PlayerView> {
    private PlayerView view;

    public PlayerPresenter(PlayerView view) {
        this.view = view;
    }


    public void getPlayBanImage(int devcieType) {
        PlayBandRepository.requstGetPlayBandImage(devcieType).subscribe(new BaseObserver<List<PlayBean>>(BaseApp.getApp(), false) {


            @Override
            public void onNext(List<PlayBean> infos) {
                UserAcacheUtil.savePlayBandInfo(devcieType, infos);
                com.isport.blelibrary.utils.Logger.myLog("PlayerPresenter devcieType:" + devcieType + ",getPlayBanImage:" + infos);
                if (view != null) {
                    view.successPlayerSuccess();
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

                if (view != null) {
                    view.onRespondError(UIUtils.getString(R.string.common_please_check_that_your_network_is_connected));
                }
            }
        });
    }


}
