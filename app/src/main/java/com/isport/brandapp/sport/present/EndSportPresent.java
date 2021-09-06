package com.isport.brandapp.sport.present;

import com.isport.brandapp.device.UpdateSuccessBean;
import com.isport.brandapp.sport.bean.SportSumData;
import com.isport.brandapp.sport.modle.SportDataModle;
import com.isport.brandapp.sport.response.SportRepository;
import com.isport.brandapp.sport.view.EndSportView;

import brandapp.isport.com.basicres.commonnet.interceptor.BaseObserver;
import brandapp.isport.com.basicres.commonnet.interceptor.ExceptionHandle;
import brandapp.isport.com.basicres.mvp.BasePresenter;
import brandapp.isport.com.basicres.service.observe.NetProgressObservable;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.functions.Consumer;

public class EndSportPresent extends BasePresenter<EndSportView> implements IEndSportPresent {
    EndSportView endSportView;

    public EndSportPresent(EndSportView endSportView) {
        this.endSportView = endSportView;
    }

    @Override
    public void addSportSummerData(SportSumData sumData) {
        SportRepository.addSportSummarrequst(sumData).as(endSportView.bindAutoDispose()).subscribe(new BaseObserver<UpdateSuccessBean>(context) {
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
            public void onNext(UpdateSuccessBean updateSuccessBean) {
                NetProgressObservable.getInstance().hide();

            }
        });
    }

    @Override
    public void getDbSummerData(long id) {


        SportDataModle modle = new SportDataModle();
        Observable.create(new ObservableOnSubscribe<SportSumData>() {
            @Override
            public void subscribe(ObservableEmitter<SportSumData> emitter) throws Exception {
                modle.getSummerData(id);
            }
        }).as(endSportView.bindAutoDispose()).subscribe(new Consumer<SportSumData>() {
            @Override
            public void accept(SportSumData sportSumData) throws Exception {
                if (endSportView != null) {
                    endSportView.successSummarData(sportSumData);
                }
            }
        });

    }

    @Override
    public void getSportSummarDataByPrimaryKey(String primaryKey) {
        SportRepository.getSportByPrimaryKey(primaryKey).as(endSportView.bindAutoDispose()).subscribe(new BaseObserver<SportSumData>(context) {
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
            public void onNext(SportSumData updateSuccessBean) {
                NetProgressObservable.getInstance().hide();

                if (endSportView != null) {
                    endSportView.successSummarData(updateSuccessBean);
                }


            }
        });
    }
}
