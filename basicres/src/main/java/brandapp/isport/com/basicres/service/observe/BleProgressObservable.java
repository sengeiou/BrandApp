package brandapp.isport.com.basicres.service.observe;

import android.os.Bundle;
import android.os.Message;
import android.text.TextUtils;

import com.isport.blelibrary.utils.Logger;

import java.util.Observable;

/**
 * @创建者 bear
 * @创建时间 2019/4/2 19:44
 * @描述
 */
public class BleProgressObservable extends Observable {

    private static BleProgressObservable obser;

    public static final int SHOW_BLE_PROGRESS_BAR    = 0;
    public static final int DISMISS_BLE_PORGRESS_BAR = 1;

    @Override
    public synchronized void setChanged() {
        super.setChanged();
    }

    private BleProgressObservable() {
        super();
    }

    public static BleProgressObservable getInstance() {
        if (obser == null) {
            synchronized (BleProgressObservable.class) {
                if (obser == null) {
                    obser = new BleProgressObservable();
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
        message.what = BleProgressObservable.SHOW_BLE_PROGRESS_BAR;
        if (!TextUtils.isEmpty(msg)) {
            message.obj = msg;
        }
        message.arg1 = isCancelable ? 0 : 1;
        BleProgressObservable.getInstance().setChanged();
        BleProgressObservable.getInstance().notifyObservers(message);
    }

    public void hide(String Str) {
        Logger.myLog("hide == " + Str);
        Message message = Message.obtain();
        message.what = BleProgressObservable.DISMISS_BLE_PORGRESS_BAR;
        BleProgressObservable.getInstance().setChanged();
        BleProgressObservable.getInstance().notifyObservers(message);
    }

    public void hide() {
        Message message = Message.obtain();
        message.what = BleProgressObservable.DISMISS_BLE_PORGRESS_BAR;
        BleProgressObservable.getInstance().setChanged();
        BleProgressObservable.getInstance().notifyObservers(message);
    }

}
