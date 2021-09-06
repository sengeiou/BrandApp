package brandapp.isport.com.basicres.net.userNet;

import brandapp.isport.com.basicres.BaseApp;
import brandapp.isport.com.basicres.aliyun.AliyunManager;
import brandapp.isport.com.basicres.commonbean.CommonFriendRelation;
import brandapp.isport.com.basicres.commonbean.UserInfoBean;
import brandapp.isport.com.basicres.commonnet.interceptor.BaseObserver;
import brandapp.isport.com.basicres.commonnet.interceptor.ExceptionHandle;
import brandapp.isport.com.basicres.commonutil.DownloadFileUtils;
import brandapp.isport.com.basicres.commonutil.Logger;
import brandapp.isport.com.basicres.commonutil.ThreadPoolUtils;
import brandapp.isport.com.basicres.commonutil.onDownloadFileListener;
import brandapp.isport.com.basicres.entry.bean.OssBean;
import brandapp.isport.com.basicres.mvp.BasePresenter;

public class CommonUserPresenter extends BasePresenter<CommonAliView> {


    CommonAliView upgradeImageView;
    CommonUserView commonUserView;

    public CommonUserPresenter() {

    }

    public CommonUserPresenter(CommonAliView view) {
        this.upgradeImageView = view;
    }

    public CommonUserPresenter(CommonUserView view) {
        this.commonUserView = view;
    }



    public void startDown(String url,String path,String fileName){
        DownloadFileUtils.getInstance().downBin(url, path, fileName, new onDownloadFileListener() {
            @Override
            public void onStart(float length) {
              /*  tvBtnState.setEnabled(false);
                progressValue.setProgress(0);*/
                // isDownding = true;
            }

            @Override
            public void onProgress(float progress) {
              /*  tvBtnState.setEnabled(false);
                progressValue.setProgress((int) (progress * 100));
                tvBtnState.setText(String.format(UIUtils.getString(R.string.file_downlod_present), (int) (progress * 100)));*/
            }

            @Override
            public void onComplete() {
                if (upgradeImageView != null) {
                   upgradeImageView.successUpgradeImageUrl(path);
                }
                /*

                Log.e("successUpgradeImageUrl", "file.lenth" + file.length() + "pathUrl:" + path);
                if (file.length() > 0) {
                    // ImageVideoFileUtils.insertVideo(pathUrl, getActivity());
                    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                        ImageVideoFileUtils.insertVideo(pathUrl, getActivity());
                    } else {
                        ImageVideoFileUtils.saveVideo(getActivity(), file);
                    }

                }*/
               /* tvStateEnabled(true);
                //tvBtnState.setEnabled(true);
                tvBtnState.setText(UIUtils.getString(R.string.device_upgrade));
                tvBtnState.setTag(upgrade);
                showUpgradeDialog(UIUtils.getString(R.string.file_downlod_success_tips));*/

            }

            @Override
            public void onFail() {
                //com.blankj.utilcode.util.ToastUtils.showLong("error");
                brandapp.isport.com.basicres.service.observe.NetProgressObservable.getInstance().hide();
               /* tvBtnState.setEnabled(true);
                progressValue.setProgress(100);
                tvBtnState.setText(UIUtils.getString(R.string.try_again));
                tvBtnState.setTag(download);
                downloadFail();*/
            }
        });

    }

    public void getOssAliToken() {

        CommonRepository.requestOssToken().as(upgradeImageView.bindAutoDispose()).subscribe(new BaseObserver<OssBean>(BaseApp.getApp()) {
            @Override
            protected void hideDialog() {

            }

            @Override
            protected void showDialog() {

            }

            @Override
            public void onError(ExceptionHandle.ResponeThrowable e) {

                //第一步失败
                if (upgradeImageView != null) {
                    upgradeImageView.onFailAliOptin(1);
                }

            }

            @Override
            public void onNext(OssBean ossBean) {
                if (upgradeImageView != null) {
                    upgradeImageView.successGetAliToken(ossBean);
                }
            }
        });
    }


    public void getUserinfo(String userId) {
        CommonRepository.requsetUserInfo(userId).as(commonUserView.bindAutoDispose()).subscribe(new BaseObserver<UserInfoBean>(BaseApp.getApp()) {
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
            public void onNext(UserInfoBean userInfoBean) {
                if (userInfoBean != null) {
                    CommonUserAcacheUtil.saveUsrInfo(userInfoBean.getUserId(), userInfoBean);

                }
                if (commonUserView != null) {
                    commonUserView.onSuccessUserInfo(userInfoBean);

                }

            }
        });
    }

    public void getUserFriendRelation(String userId) {
        CommonRepository.requsetFriendRelation(userId).as(commonUserView.bindAutoDispose()).subscribe(new BaseObserver<CommonFriendRelation>(BaseApp.getApp()) {
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
            public void onNext(CommonFriendRelation commonFriendRelation) {

                if (commonUserView != null) {
                    commonUserView.onSuccessUserFriendRelation(commonFriendRelation);
                }

            }
        });
    }


    public void upgradeImageAli(String buckeName, String keyId, String secretId, String token, String imageName, String path) {
        AliyunManager aliyunManager = new AliyunManager(buckeName, keyId, secretId, token, imageName, new AliyunManager.callback() {
            @Override
            public void upLoadSuccess(int type, final String imgPath) {
                Logger.e("imgPath=" + imgPath + "upgradeImageView" + upgradeImageView);

                if (upgradeImageView != null) {
                    upgradeImageView.successUpgradeImageUrl(imgPath);
                }

            }

            @Override
            public void upLoadFailed(String error) {
                Logger.e("imgPath=" + error);
                com.blankj.utilcode.util.ToastUtils.showLong(error);
                brandapp.isport.com.basicres.service.observe.NetProgressObservable.getInstance().hide();

            }

            @Override
            public void upLoadProgress(long currentSize, long totalSize) {
                if (upgradeImageView != null) {
                    upgradeImageView.upgradeProgress(currentSize, totalSize);
                }
            }
        });
        aliyunManager.upLoadFile(path);
    }
    AliyunManager aliyunManager;
    public void cancelVideo(){
        if(aliyunManager!=null){
            aliyunManager.cancelTask();
        }
    }
    public void upgradeVideoAli(String buckeName, String keyId, String secretId, String token, String imageName, String path) {
            aliyunManager = new AliyunManager(buckeName, keyId, secretId, token, imageName, new AliyunManager.callback() {
            @Override
            public void upLoadSuccess(int type, final String imgPath) {
                Logger.e("imgPath=" + imgPath + "upgradeImageView" + upgradeImageView);

                if (upgradeImageView != null) {
                    upgradeImageView.successUpgradeImageUrl(imgPath);
                }

            }

            @Override
            public void upLoadFailed(String error) {
                //第二步失败
                if (upgradeImageView != null) {
                    upgradeImageView.onFailAliOptin(2);
                }
                Logger.e("imgPath=" + error);
                com.blankj.utilcode.util.ToastUtils.showLong(error);
                brandapp.isport.com.basicres.service.observe.NetProgressObservable.getInstance().hide();

            }

            @Override
            public void upLoadProgress(long currentSize, long totalSize) {
                if (upgradeImageView != null) {
                    upgradeImageView.upgradeProgress(currentSize, totalSize);
                }
            }
        });
        aliyunManager.upLoadVideoFile(path);
    }

    public void downFileAli(String buckeName, String keyId, String secretId, String token, String filePath, String url) {

        ThreadPoolUtils.getInstance().addTask(new Runnable() {
            @Override
            public void run() {
                AliyunManager aliyunManager = new AliyunManager(buckeName, keyId, secretId, token, filePath, new AliyunManager.callback() {
                    @Override
                    public void upLoadSuccess(int type, final String imgPath) {
                        Logger.e("imgPath=" + imgPath + "upgradeImageView" + upgradeImageView);

                        if (upgradeImageView != null) {
                            upgradeImageView.successUpgradeImageUrl(imgPath);
                        }

                    }

                    @Override
                    public void upLoadFailed(String error) {
                        Logger.e("imgPath=" + error);
                        com.blankj.utilcode.util.ToastUtils.showLong(error);
                        brandapp.isport.com.basicres.service.observe.NetProgressObservable.getInstance().hide();

                    }

                    @Override
                    public void upLoadProgress(long currentSize, long totalSize) {
                        if (upgradeImageView != null) {
                            upgradeImageView.upgradeProgress(currentSize, totalSize);
                        }
                    }
                });
                aliyunManager.downFile(filePath, url);
            }
        });


    }
}
