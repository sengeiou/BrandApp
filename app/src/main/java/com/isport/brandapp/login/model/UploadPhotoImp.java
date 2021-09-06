package com.isport.brandapp.login.model;

import android.content.Context;

import com.isport.brandapp.login.view.ActivityImageShowView;

import java.io.File;

import phone.gym.jkcq.com.commonres.common.AllocationApi;
import brandapp.isport.com.basicres.commonutil.TokenUtil;
import brandapp.isport.com.basicres.mvp.BasePresenterModel;
import brandapp.isport.com.basicres.service.observe.NetProgressObservable;

public class UploadPhotoImp extends BasePresenterModel<ActivityImageShowView> implements IUploadPhoto {
    public UploadPhotoImp(Context context, ActivityImageShowView baseView) {
        super(context, baseView);
    }

    @Override
    public void postPhoto(File file) {

    }

    @Override
    public void postPhotos(File... files) {
        if (null == files || files.length <= 0) {
            NetProgressObservable.getInstance().hide();
            baseView.onRespondError("图片上传错误");
            return;
        }


        NetProgressObservable.getInstance().show(false);

        String url = AllocationApi.postUserPhoto(TokenUtil.getInstance().getPeopleIdStr(context));

      /*  NetUtils.doUpload(url, new HashMap<String, Object>(), NetUtils.getPairFiles(files), UpdatePhotoBean.class, new OnHttpRequestCallBack<UpdatePhotoBean>() {
            @Override
            public void onSuccess(UpdatePhotoBean bean) {
                baseView.postPhotosSuccess(bean);
                NetProgressObservable.getInstance().hide();
            }

            @Override
            public void onGetErrorCode(UpdatePhotoBean bean) {
                NetProgressObservable.getInstance().hide();
                baseView.onRespondError("图片上传错误");
            }

            @Override
            public void onFailure(int errorCode, String msg) {
                NetProgressObservable.getInstance().hide();
                baseView.onRespondError(msg);
            }
        });*/
    }
}
