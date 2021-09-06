package phone.gym.jkcq.com.socialmodule.fragment.present;

import brandapp.isport.com.basicres.BaseApp;
import brandapp.isport.com.basicres.commonnet.interceptor.BaseObserver;
import brandapp.isport.com.basicres.commonnet.interceptor.ExceptionHandle;
import brandapp.isport.com.basicres.commonutil.ToastUtils;
import brandapp.isport.com.basicres.mvp.BasePresenter;
import phone.gym.jkcq.com.socialmodule.fragment.view.ReportView;
import phone.gym.jkcq.com.socialmodule.personal.repository.DynamicRepository;

public class ReportPresent extends BasePresenter<ReportView> {
    ReportView reportView;

    public ReportPresent(ReportView view) {
        this.reportView = view;
    }


    public void reportDynamic(String dynamicId, int reportType) {
        DynamicRepository.reportDynamic(dynamicId, reportType).as(reportView.bindAutoDispose()).subscribe(new BaseObserver<Integer>(BaseApp.getApp()) {
            @Override
            protected void hideDialog() {

            }

            @Override
            protected void showDialog() {

            }

            @Override
            public void onError(ExceptionHandle.ResponeThrowable e) {

                if (reportView != null) {
                    if (e.code == 2000) {
                        ToastUtils.showToast(BaseApp.getApp(), e.message);
                    }

                }
            }

            @Override
            public void onNext(Integer state) {

                if (reportView != null && state != null && state == 1) {
                    reportView.successReport();
                }
            }
        });
    }


}
