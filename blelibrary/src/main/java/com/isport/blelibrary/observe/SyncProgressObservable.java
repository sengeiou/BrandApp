
package com.isport.blelibrary.observe;

import android.os.Bundle;
import android.os.Message;

import com.isport.blelibrary.utils.SyncCacheUtils;

import java.util.Observable;

/**
 * ClassName:NetProgressBar <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Date: 2017年4月19日 上午11:31:46 <br/>
 *
 * @author Administrator
 */
public class SyncProgressObservable extends Observable {

    private static SyncProgressObservable obser;

    public static final int SHOW_PROGRESS_BAR = 0;
    public static final int DISMISS_PORGRESS_BAR = 1;

    @Override
    public synchronized void setChanged() {
        super.setChanged();
    }

    private SyncProgressObservable() {
        super();
    }

    public static SyncProgressObservable getInstance() {
        if (obser == null) {
            synchronized (SyncProgressObservable.class) {
                if (obser == null) {
                    obser = new SyncProgressObservable();
                }
            }
        }
        return obser;
    }


    /**
     * @param isCancelable 是否允许返回键关闭Dialog true允许
     */


    public void sync(int time, boolean isCancelable) {
        //解绑操作才需要弹这个对话框
        if (SyncCacheUtils.getUnbindState()) {
            Message message = Message.obtain();
            Bundle bundle = new Bundle();
            message.what = SyncProgressObservable.SHOW_PROGRESS_BAR;
            message.obj = time;
            message.arg1 = isCancelable ? 0 : 1;
            SyncProgressObservable.getInstance().setChanged();
            SyncProgressObservable.getInstance().notifyObservers(message);
        }
    }


    public void hide() {
        Message message = Message.obtain();
        message.what = SyncProgressObservable.DISMISS_PORGRESS_BAR;
        SyncProgressObservable.getInstance().setChanged();
        SyncProgressObservable.getInstance().notifyObservers(message);
    }

}