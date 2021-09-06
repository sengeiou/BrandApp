package com.isport.brandapp.login.presenter;

import com.isport.blelibrary.utils.Logger;
import com.isport.brandapp.banner.recycleView.utils.ToastUtil;
import brandapp.isport.com.basicres.entry.bean.UpdatePhotoBean;
import com.isport.brandapp.login.model.IUploadPhoto;
import com.isport.brandapp.login.view.ActivityImageShowView;
import com.isport.brandapp.net.RetrofitClient;

import java.io.File;

import brandapp.isport.com.basicres.BaseApp;
import brandapp.isport.com.basicres.commonnet.interceptor.BaseObserver;
import brandapp.isport.com.basicres.commonnet.interceptor.ExceptionHandle;
import brandapp.isport.com.basicres.commonutil.TokenUtil;
import brandapp.isport.com.basicres.mvp.BasePresenter;
import brandapp.isport.com.basicres.service.observe.NetProgressObservable;

public class ActivityImageShowPresenter extends BasePresenter<ActivityImageShowView> implements IUploadPhoto {


    private ActivityImageShowView showView;

    public ActivityImageShowPresenter(ActivityImageShowView showView) {
        this.showView = showView;
    }


    @Override
    public void postPhoto(File file) {


    }

    @Override
    public void postPhotos(File... files) {
        RetrofitClient.getInstance().updateFile(files[0], TokenUtil.getInstance().getPeopleIdStr(BaseApp.getApp()), "0").subscribe(new BaseObserver<UpdatePhotoBean>(context, true) {
            @Override
            protected void hideDialog() {

            }

            @Override
            protected void showDialog() {

            }

            @Override
            public void onError(ExceptionHandle.ResponeThrowable e) {
                Logger.myLog("ResponeThrowable == " + e.toString());
                ToastUtil.showTextToast(BaseApp.getApp(), e.message);

            }

            @Override
            public void onNext(UpdatePhotoBean updatePhotoBean) {
                NetProgressObservable.getInstance().hide();
                if (isViewAttached()) {
                    mActView.get().postPhotosSuccess(updatePhotoBean);
                }
            }
        });


    }


}
