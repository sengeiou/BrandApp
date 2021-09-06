package phone.gym.jkcq.com.socialmodule.personal.presenter;

import java.io.File;

import brandapp.isport.com.basicres.BaseApp;
import brandapp.isport.com.basicres.commonnet.interceptor.BaseObserver;
import brandapp.isport.com.basicres.commonnet.interceptor.ExceptionHandle;
import brandapp.isport.com.basicres.commonutil.ToastUtils;
import brandapp.isport.com.basicres.commonutil.TokenUtil;
import brandapp.isport.com.basicres.commonutil.UIUtils;
import brandapp.isport.com.basicres.entry.bean.UpdatePhotoBean;
import brandapp.isport.com.basicres.mvp.BasePresenter;
import phone.gym.jkcq.com.socialmodule.R;
import phone.gym.jkcq.com.socialmodule.personal.repository.UserRepository;
import phone.gym.jkcq.com.socialmodule.personal.view.UpgradeImageView;

public class UpgradeImagePresenter extends BasePresenter<UpgradeImageView> {


    UpgradeImageView upgradeImageView;

    public UpgradeImagePresenter() {

    }

    public UpgradeImagePresenter(UpgradeImageView view) {
        this.upgradeImageView = view;
    }


    public void saveHeadImage(File file, String userId) {

        UserRepository.requestSaveHeadImage(file, userId).as(upgradeImageView.bindAutoDispose()).subscribe(new BaseObserver<UpdatePhotoBean>(BaseApp.getApp(), true) {
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
            public void onNext(UpdatePhotoBean updatePhotoBean) {
                if (upgradeImageView != null) {
                    upgradeImageView.successSaveHeadUrl(updatePhotoBean);
                }
            }
        });
    }


    public void saveUserInfo(String userId, String sex, String name, String height, String weight, String defaultDay, String myProfile) {
        UserRepository.requestEditUserBean(userId, sex, name, height, weight, defaultDay, myProfile).as(upgradeImageView.bindAutoDispose()).subscribe(new BaseObserver<Integer>(BaseApp.getApp()) {
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
            public void onNext(Integer integer) {

                if (integer != null && integer == 1) {
                    if (upgradeImageView != null) {
                        upgradeImageView.successOption();
                    }
                    ToastUtils.showToast(BaseApp.getApp(), UIUtils.getString(R.string.friend_eidt_user_succsss));
                } else {
                    ToastUtils.showToast(BaseApp.getApp(), UIUtils.getString(R.string.friend_eidt_user_fail));
                }

            }
        });
    }

    public void saveEditBg(String userId, String url) {
        UserRepository.requestEditUserBg(userId, url).as(upgradeImageView.bindAutoDispose()).subscribe(new BaseObserver<Integer>(BaseApp.getApp()) {
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
            public void onNext(Integer integer) {

                if (integer != null && integer == 1) {
                    if (upgradeImageView != null) {
                        upgradeImageView.successSaveImageUrl(url);
                    }
                }

            }
        });
    }


}
