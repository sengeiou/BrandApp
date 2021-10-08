package com.isport.brandapp.sport;

import com.isport.brandapp.App;
import com.isport.brandapp.device.UpdateSuccessBean;
import com.isport.brandapp.sport.bean.IndoorRunDatas;
import com.isport.brandapp.sport.bean.SportSumData;
import com.isport.brandapp.sport.bean.runargs.ArgsForInRunService;
import com.isport.brandapp.sport.modle.SportDataModle;
import com.isport.brandapp.sport.response.SportRepository;

import brandapp.isport.com.basicres.BaseApp;
import brandapp.isport.com.basicres.commonnet.interceptor.BaseObserver;
import brandapp.isport.com.basicres.commonnet.interceptor.ExceptionHandle;
import brandapp.isport.com.basicres.mvp.BasePresenter;
import brandapp.isport.com.basicres.service.observe.NetProgressObservable;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

public class InDoorSportPresent extends BasePresenter<InDoorSportView> {

    InDoorSportView inDoorSportView;

    /**
     * 用步数计算距离。
     */

    public InDoorSportPresent(InDoorSportView inDoorSportView) {
        this.inDoorSportView = inDoorSportView;
    }

    long id;


    public void savaSportDb(ArgsForInRunService argsForInRunService, IndoorRunDatas indoorRunDatas, int sportType) {
        SportDataModle modle = new SportDataModle();
        Observable.create(new ObservableOnSubscribe<Long>() {
            @Override
            public void subscribe(ObservableEmitter<Long> emitter) throws Exception {
                id = modle.saveSportData(argsForInRunService, indoorRunDatas, sportType);
                emitter.onNext(id);
            }
        }).observeOn(Schedulers.io()).subscribe(new Consumer<Long>() {
            @Override
            public void accept(Long aLong) throws Exception {
                NetProgressObservable.getInstance().hide();

            }
        });
    }

    public void saveSportData(ArgsForInRunService argsForInRunService, IndoorRunDatas indoorRunDatas, int sportType) {

        //先把数据保存到数据库


        SportDataModle modle = new SportDataModle();
        Observable.create(new ObservableOnSubscribe<Long>() {
            @Override
            public void subscribe(ObservableEmitter<Long> emitter) throws Exception {
                id = modle.saveSportData(argsForInRunService, indoorRunDatas, sportType);
                emitter.onNext(id);
            }
        }).flatMap(new Function<Long, ObservableSource<UpdateSuccessBean>>() {
            @Override
            public ObservableSource<UpdateSuccessBean> apply(Long aLong) throws Exception {
                SportDataModle modle = new SportDataModle();
                SportSumData sumData = modle.getSummerData(aLong);
                return SportRepository.addSportSummarrequst(sumData);

            }
        }).as(inDoorSportView.bindAutoDispose()).subscribe(new BaseObserver<UpdateSuccessBean>(BaseApp.getApp()) {
            @Override
            protected void hideDialog() {

            }

            @Override
            protected void showDialog() {

            }

            @Override
            public void onError(ExceptionHandle.ResponeThrowable e) {
                if (inDoorSportView != null) {
                    inDoorSportView.failSaveData();
                }
            }

            @Override
            public void onNext(UpdateSuccessBean sumData) {
                NetProgressObservable.getInstance().hide();
                if(sumData == null)
                    return;
                if (inDoorSportView != null) {
                    //去服务器上传数据，是后台自动去上传的，上传都成功把本地数据的数据删除
                    App.saveSportDtail(id, sumData.getPublicId());

                    inDoorSportView.successSaveData(sumData.getPublicId());
                }
            }
        });

    }

}
