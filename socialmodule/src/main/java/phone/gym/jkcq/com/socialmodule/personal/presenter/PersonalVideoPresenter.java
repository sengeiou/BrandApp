package phone.gym.jkcq.com.socialmodule.personal.presenter;

import org.greenrobot.eventbus.EventBus;

import brandapp.isport.com.basicres.BaseApp;
import brandapp.isport.com.basicres.commonnet.interceptor.BaseObserver;
import brandapp.isport.com.basicres.commonnet.interceptor.ExceptionHandle;
import brandapp.isport.com.basicres.commonutil.MessageEvent;
import brandapp.isport.com.basicres.mvp.BasePresenter;
import phone.gym.jkcq.com.socialmodule.bean.ListData;
import phone.gym.jkcq.com.socialmodule.bean.response.DynamBean;
import phone.gym.jkcq.com.socialmodule.personal.repository.DynamicRepository;
import phone.gym.jkcq.com.socialmodule.personal.view.PersonalVideoView;

public class PersonalVideoPresenter extends BasePresenter<PersonalVideoView> {


    PersonalVideoView upgradeImageView;

    public PersonalVideoPresenter() {

    }

    public PersonalVideoPresenter(PersonalVideoView view) {
        this.upgradeImageView = view;
    }


    public void getPersonalVideoList(String userId, int size, int page, int videoType) {
        DynamicRepository.getHomePageDynamic(userId, size, page, videoType).subscribe(new BaseObserver<ListData<DynamBean>>(BaseApp.getApp()) {
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
            public void onNext(ListData<DynamBean> dynamBeanListData) {

                if(upgradeImageView!=null){
                    upgradeImageView.successPersonalVideo(dynamBeanListData);
                }
            }
        });
    }

    /**
     *
     */
    public void deleteDynamic(String dynamicId) {
        DynamicRepository.deleteDynamic(dynamicId).subscribe(new BaseObserver<Boolean>(BaseApp.getApp()) {
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
            public void onNext(Boolean status) {

                if (upgradeImageView != null) {
                    EventBus.getDefault().post(new MessageEvent((Object) dynamicId,MessageEvent.del_dynamicId));
                    upgradeImageView.successDeleteAction(status);
                }
            }
        });
    }

}
