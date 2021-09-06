package brandapp.isport.com.basicres.commonnet.interceptor;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;

public class Transformer<T> implements ObservableTransformer {
    @Override
    public ObservableSource apply(Observable upstream) {
        return (Observable<T>) upstream.map(new Handle<T>()).onErrorResumeNext(new HttpResponseFunc<T>());
    }
}
