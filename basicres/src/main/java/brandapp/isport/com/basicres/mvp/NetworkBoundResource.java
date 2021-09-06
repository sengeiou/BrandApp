package brandapp.isport.com.basicres.mvp;

import android.annotation.SuppressLint;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * @param <T>
 */
public abstract class NetworkBoundResource<T> {

    //需要的参数传进来


    public abstract Observable<T> getFromDb();

    public abstract Observable<T> getNoCacheData();

    public abstract boolean shouldFetchRemoteSource();

    public abstract boolean shouldStandAlone();

    public abstract Observable<T> getRemoteSource();

    public abstract void saveRemoteSource(T remoteSource);


    public Observable<T> getAsNetObservable() {
        return Observable.create(new ObservableOnSubscribe<T>() {
            @Override
            public void subscribe(ObservableEmitter<T> e) throws Exception {


            }
        });
    }

    @SuppressLint("CheckResult")
    public Observable<T> getAsObservable() {
        /**
         * 创建了一个observale
         */


     /*  return getFromDb().filter(new Predicate<T>() {
            @Override
            public boolean test(T t) throws Exception {
                return t != null;
            }
        }).switchIfEmpty(getRemoteSource());*/

        if (shouldStandAlone()) {
            return getFromDb().onErrorResumeNext(new Function<Throwable, ObservableSource<? extends T>>() {
                @Override
                public ObservableSource<? extends T> apply(Throwable throwable) throws Exception {
                    return Observable.create(new ObservableOnSubscribe<T>() {
                        @Override
                        public void subscribe(ObservableEmitter<T> e) throws Exception {
                            //没有缓存的时候返回这个，其他的异常也
                            e.onError(new Throwable("1"));
                        }
                    });
                }
            });


        } else {
            if (shouldFetchRemoteSource()) {
                return Observable.concat(
                        getFromDb().onErrorResumeNext(new Function<Throwable, ObservableSource<? extends T>>() {
                            @Override
                            public ObservableSource<? extends T> apply(Throwable throwable) throws Exception {
                                //这个发送回让observable发送oncomplete指令
                                return Observable.empty();
                            }
                        }),
                        getRemoteSource().onErrorResumeNext(new Function<Throwable, ObservableSource<? extends T>>() {
                            @Override
                            public ObservableSource<? extends T> apply(Throwable throwable) throws Exception {
                                return Observable.empty();
                            }
                        }).doOnNext(new Consumer<T>() {
                            @Override
                            public void accept(T t) throws Exception {
                                if (shouldFetchRemoteSource()) {
                                    saveRemoteSource(t);
                                }
                            }
                        })
                ).subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread());
            } else {
                return getRemoteSource().doOnNext(new Consumer<T>() {
                    @Override
                    public void accept(T t) throws Exception {
                        saveRemoteSource(t);
                    }
                });
            }


        /*return Observable.create(new ObservableOnSubscribe<T>() {
            @Override
            public void subscribe(ObservableEmitter<T> e) throws Exception {


             *//*   if (localData != null) {
                    e.onNext(localData);
                }
                if (shouldFetchRemoteSource()) {
                    T remoteData = getRemoteSource();
                    if (remoteData != null) {
                        saveRemoteSource(remoteData);
                        e.onNext(remoteData);
                    }
                }*//*
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());*/
        }
    }

}
