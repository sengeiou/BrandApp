package phone.gym.jkcq.com.socialmodule.fragment.present;

import java.util.List;

import brandapp.isport.com.basicres.BaseApp;
import brandapp.isport.com.basicres.commonnet.interceptor.BaseObserver;
import brandapp.isport.com.basicres.commonnet.interceptor.ExceptionHandle;
import brandapp.isport.com.basicres.commonutil.Logger;
import brandapp.isport.com.basicres.mvp.BasePresenter;
import phone.gym.jkcq.com.socialmodule.bean.response.DynamBean;
import phone.gym.jkcq.com.socialmodule.fragment.view.DynamView;
import phone.gym.jkcq.com.socialmodule.personal.repository.DynamicRepository;

public class DynamPresent extends BasePresenter<DynamView> {

    private DynamView dynamView;

    public DynamPresent(DynamView dynamView) {
        this.dynamView = dynamView;
    }

    public void getFirstDynamList(String userId, int dataNumber, int dynamicInfoType) {
        DynamicRepository.getCommunityDynamic(userId, dataNumber, dynamicInfoType).as(dynamView.bindAutoDispose()).subscribe(new BaseObserver<List<DynamBean>>(BaseApp.getApp()) {
            @Override
            protected void hideDialog() {

            }

            @Override
            protected void showDialog() {

            }

            @Override
            public void onError(ExceptionHandle.ResponeThrowable e) {

                if (dynamView != null) {
                    dynamView.failFirstDynamList();
                }

            }

            @Override
            public void onNext(List<DynamBean> dynamBeanListData) {

                Logger.e("getFirstDynamList:", "onNext:" + dynamBeanListData);
                if (dynamView != null) {
                    if (dynamBeanListData != null && dynamBeanListData.size()>0) {
                        Logger.e("getFirstDynamList:", "succcessDynamList:" + dynamBeanListData.size());
                        dynamView.succcessDynamList(dynamBeanListData);
                    } else {
                        Logger.e("getFirstDynamList:", "firstNoContentDynamList:");
                        dynamView.firstNoContentDynamList();
                    }
                }
            }
        });
    }

    public void getNextDynamList(String userId, String dynamicInfoId, int direction, int dataNumbers, int dynamicInfoType) {
        DynamicRepository.getNextCommunityDynamic(userId, dynamicInfoId, direction, dataNumbers, dynamicInfoType).as(dynamView.bindAutoDispose()).subscribe(new BaseObserver<List<DynamBean>>(BaseApp.getApp()) {
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
            public void onNext(List<DynamBean> dynamBeanListData) {

                if (dynamView != null && dynamBeanListData != null) {
                    dynamView.succcessNextDynamList(dynamBeanListData);
                }
            }
        });
    }

    public void getUpDynamList(String userId, String dynamicInfoId, int direction, int dataNumbers, int dynamicInfoType) {
        DynamicRepository.getNextCommunityDynamic(userId, dynamicInfoId, direction, dataNumbers, dynamicInfoType).as(dynamView.bindAutoDispose()).subscribe(new BaseObserver<List<DynamBean>>(BaseApp.getApp()) {
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
            public void onNext(List<DynamBean> dynamBeanListData) {

                if (dynamView != null && dynamBeanListData != null) {
                    dynamView.succcessUpDynamList(dynamBeanListData);
                }
            }
        });
    }


}
