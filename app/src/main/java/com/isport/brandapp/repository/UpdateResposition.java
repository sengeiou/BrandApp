package com.isport.brandapp.repository;


import com.isport.blelibrary.ISportAgent;
import com.isport.blelibrary.utils.Logger;
import com.isport.brandapp.AppConfiguration;
import brandapp.isport.com.basicres.commonbean.UserInfoBean;
import com.isport.brandapp.bind.bean.BindInsertOrUpdateBean;
import com.isport.brandapp.net.RetrofitClient;
import com.isport.brandapp.parm.db.BaseDbParms;
import com.isport.brandapp.util.RequestCode;

import org.greenrobot.eventbus.EventBus;

import brandapp.isport.com.basicres.BaseApp;
import brandapp.isport.com.basicres.commonnet.net.PostBody;
import brandapp.isport.com.basicres.commonutil.MessageEvent;
import brandapp.isport.com.basicres.commonutil.TokenUtil;
import brandapp.isport.com.basicres.mvp.NetworkBoundResource;
import brandapp.isport.com.basicres.service.observe.NetProgressObservable;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;


public class UpdateResposition<T, T1, T2, T3> {


    public Observable<T> update(PostBody<T1, T2, T3> postBody) {
        return new NetworkBoundResource<T>() {
            @Override
            public Observable<T> getFromDb() {
                //100ms 的时间操作数据库
                return Observable
                        .create(new ObservableOnSubscribe<T>() {
                            @Override
                            public void subscribe(ObservableEmitter<T> emitter) throws Exception {
                                BaseDbParms dbParm = (BaseDbParms) postBody.dbParm;
                                switch (dbParm.requestCode) {
                                    case RequestCode.Request_unBindDevice:
                                        Logger.myLog("数据库操作解绑 == " +  ((BindInsertOrUpdateBean)postBody.data).toString());
// .getDeviceTypeId());
                                                     ISportAgent.getInstance().deleteDeviceType(((BindInsertOrUpdateBean) postBody
                                                .data).getDeviceTypeId(),TokenUtil.getInstance().getPeopleIdStr(BaseApp.getApp()));
                                        EventBus.getDefault().post(new MessageEvent(MessageEvent
                                                                                            .UNBIND_DEVICE_SUCCESS_EVENT, ((BindInsertOrUpdateBean) postBody.data).getDeviceTypeId()));
                                        NetProgressObservable.getInstance().hide();
                                        emitter.onNext((T) (Integer)0);
                                        break;
                                    case RequestCode.Request_setClockTime:
                                        //是单机版直接返回成功
//                                AutoSleepTimeParms dbParm1 = (AutoSleepTimeParms) postBody.dbParm;
//                                String clockTime = dbParm1.clockTime;
//                                String[] split = clockTime.split(":");
//                                ISportAgent.getInstance().requestBle(BleRequest.Sleep_Sleepace_setAutoCollection,
// true,Integer.parseInt(split[0]),Integer.parseInt(split[1],127));
                                        NetProgressObservable.getInstance().hide();
                                        emitter.onNext((T) "setClockTimeSuccess");
                                        break;
                                    default:
                                        break;
                                }
                            }
                        });
//                .delay(100, TimeUnit.MILLISECONDS);
            }

            @Override
            public Observable<T> getNoCacheData() {
                return null;
            }

            @Override
            public boolean shouldFetchRemoteSource() {
                return false;
            }

            @Override
            public boolean shouldStandAlone() {
                return postBody.isStandAlone;
            }

            @Override
            public Observable<T> getRemoteSource() {
                return (Observable<T>) RetrofitClient.getInstance().post(postBody);
            }

            @Override
            public void saveRemoteSource(T remoteSource) {
                if (remoteSource instanceof String) {

                } else if (remoteSource instanceof UserInfoBean) {
                    AppConfiguration.saveUserInfo((UserInfoBean) remoteSource);
                } else {

                }
            }
        }.getAsObservable();
    }


}
