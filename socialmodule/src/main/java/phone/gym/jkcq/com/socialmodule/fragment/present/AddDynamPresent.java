package phone.gym.jkcq.com.socialmodule.fragment.present;

import android.text.TextUtils;

import brandapp.isport.com.basicres.BaseApp;
import brandapp.isport.com.basicres.commonnet.interceptor.BaseObserver;
import brandapp.isport.com.basicres.commonnet.interceptor.ExceptionHandle;
import brandapp.isport.com.basicres.mvp.BasePresenter;
import phone.gym.jkcq.com.socialmodule.fragment.view.AddDynamView;
import phone.gym.jkcq.com.socialmodule.personal.repository.DynamicRepository;

public class AddDynamPresent extends BasePresenter<AddDynamView> {

    private AddDynamView dynamView;

    public AddDynamPresent(AddDynamView dynamView) {
        this.dynamView = dynamView;
    }

    public void sendDynacim(String userId, String content, String coverUrl, String videoUrl) {


        DynamicRepository.sendNewDynamic(userId, content, coverUrl, videoUrl).as(dynamView.bindAutoDispose()).subscribe(new BaseObserver<String>(BaseApp.getApp()) {
            @Override
            public void onNext(String s) {
                if (dynamView != null) {
                    if (TextUtils.isEmpty(s)) {
                        dynamView.failSendDynamic();
                    } else {
                        if (s.equals("true")) {
                            dynamView.successSendDynamic();
                        } else {
                            dynamView.failSendDynamic();
                        }
                    }
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
                dynamView.failSendDynamic();
            }

        });
    }

}
