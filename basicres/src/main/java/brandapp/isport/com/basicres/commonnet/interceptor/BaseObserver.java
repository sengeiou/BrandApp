package brandapp.isport.com.basicres.commonnet.interceptor;

import android.content.Context;

import brandapp.isport.com.basicres.service.observe.NetProgressObservable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;


public abstract class BaseObserver<T> implements Observer<T> {
    private Context context;
    private boolean isShow;

    public BaseObserver(Context context) {
        this.context = context;
        isShow = false;
    }

    public BaseObserver(Context context, boolean isShow) {
        this.context = context;
        this.isShow = isShow;
    }

    public BaseObserver() {

    }

    @Override
    public void onError(Throwable e) {
        // Log.e("lvr", e.getMessage());
        // todo error somthing
        NetProgressObservable.getInstance().hide();
        if (e instanceof ExceptionHandle.ResponeThrowable) {
            onError((ExceptionHandle.ResponeThrowable) e);
        } else {
            onError(new ExceptionHandle.ResponeThrowable(e, ExceptionHandle.ERROR.UNKNOWN));
        }
    }

    @Override
    public void onSubscribe(Disposable d) {
        //Toast.makeText(context, "建立连接", Toast.LENGTH_SHORT).show();

        //可以弹出Dialog 提示正在加载
        if (isShow) {
            NetProgressObservable.getInstance().show();
            showDialog();
        }

    }

    protected abstract void hideDialog();

    protected abstract void showDialog();

    @Override
    public void onComplete() {

        //Toast.makeText(context, "请求完毕", Toast.LENGTH_SHORT).show();
        //可以取消Dialog 加载完毕
        NetProgressObservable.getInstance().hide();
        hideDialog();
    }


    public abstract void onError(ExceptionHandle.ResponeThrowable e);

}
