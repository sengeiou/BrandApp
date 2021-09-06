
package brandapp.isport.com.basicres.service.observe;

import android.os.Bundle;
import android.os.Message;
import android.text.TextUtils;

import com.isport.blelibrary.utils.Logger;

import java.util.Observable;

/**
 * ClassName:NetProgressBar <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Date: 2017年4月19日 上午11:31:46 <br/>
 *
 * @author Administrator
 */
public class NetProgressObservable extends Observable {

    private static NetProgressObservable obser;

    public static final int SHOW_PROGRESS_BAR    = 0;
    public static final int DISMISS_PORGRESS_BAR = 1;

    @Override
    public synchronized void setChanged() {
        super.setChanged();
    }

    private NetProgressObservable() {
        super();
    }

    public static NetProgressObservable getInstance() {
        if (obser == null) {
            synchronized (NetProgressObservable.class) {
                if (obser == null) {
                    obser = new NetProgressObservable();
                }
            }
        }
        return obser;
    }

    public void show() {
        show(null);
    }

    /**
     * @param isCancelable 是否允许返回键关闭Dialog true允许
     */
    public void show(boolean isCancelable) {
        show(null, isCancelable);
    }

    public void show(String msg) {
        show(msg, true);
    }

    public void show(String msg, boolean isCancelable) {
        Message message = Message.obtain();
        Bundle bundle = new Bundle();
        message.what = NetProgressObservable.SHOW_PROGRESS_BAR;
        if (!TextUtils.isEmpty(msg)) {
            message.obj = msg;
        }
        message.arg1 = isCancelable ? 0 : 1;
        NetProgressObservable.getInstance().setChanged();
        NetProgressObservable.getInstance().notifyObservers(message);
    }

    public void hide(String Str) {
        Logger.myLog("hide == " + Str);
        Message message = Message.obtain();
        message.what = NetProgressObservable.DISMISS_PORGRESS_BAR;
        NetProgressObservable.getInstance().setChanged();
        NetProgressObservable.getInstance().notifyObservers(message);
    }

    public void hide() {
        Message message = Message.obtain();
        message.what = NetProgressObservable.DISMISS_PORGRESS_BAR;
        NetProgressObservable.getInstance().setChanged();
        NetProgressObservable.getInstance().notifyObservers(message);
    }

}