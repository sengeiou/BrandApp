package phone.gym.jkcq.com.socialmodule.fragment.present;

import org.greenrobot.eventbus.EventBus;

import brandapp.isport.com.basicres.BaseApp;
import brandapp.isport.com.basicres.commonnet.interceptor.BaseObserver;
import brandapp.isport.com.basicres.commonnet.interceptor.ExceptionHandle;
import brandapp.isport.com.basicres.commonutil.MessageEvent;
import brandapp.isport.com.basicres.mvp.BasePresenter;
import phone.gym.jkcq.com.socialmodule.bean.result.ResultLikeBean;
import phone.gym.jkcq.com.socialmodule.fragment.view.LikeView;
import phone.gym.jkcq.com.socialmodule.personal.repository.DynamicRepository;

public class LikePresent extends BasePresenter<LikeView> {
    LikeView likeView;

    public LikePresent(LikeView view) {
        this.likeView = view;
    }


    public void likeToOhter(String dynamicId, String userId, String toUserId) {
        DynamicRepository.dynamicPraise(dynamicId, userId, toUserId).as(likeView.bindAutoDispose()).subscribe(new BaseObserver<ResultLikeBean>(BaseApp.getApp()) {
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
            public void onNext(ResultLikeBean resultLikeBean) {

                if (likeView != null && resultLikeBean != null) {
                    //这里发广播，点赞还是取消了
                    resultLikeBean.setUserId(toUserId);
                    resultLikeBean.setDynamicInfoId(dynamicId);
                    EventBus.getDefault().post(new MessageEvent(resultLikeBean, MessageEvent.update_dynamic_like_state));
                   likeView.successLikeToOther(resultLikeBean.getPraiseNums(), resultLikeBean.isWhetherPraise());
                }
            }
        });
    }


}
