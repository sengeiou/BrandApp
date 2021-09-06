
package com.isport.blelibrary.observe;

import android.os.Bundle;
import android.os.Message;
import android.text.TextUtils;

import com.isport.blelibrary.entry.DisplaySet;
import com.isport.blelibrary.utils.Logger;

import java.util.Observable;

/**
 * ClassName:NetProgressBar <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Date: 2017年4月19日 上午11:31:46 <br/>
 *
 * @author Administrator
 */
public class BleGetStateObservable extends Observable {

    private static BleGetStateObservable obser;

    public static final int SHOW_PROGRESS_BAR = 0;
    public static final int DISMISS_PORGRESS_BAR = 1;

    @Override
    public synchronized void setChanged() {
        super.setChanged();
    }

    private BleGetStateObservable() {
        super();
    }

    public static BleGetStateObservable getInstance() {
        if (obser == null) {
            synchronized (BleGetStateObservable.class) {
                if (obser == null) {
                    obser = new BleGetStateObservable();
                }
            }
        }
        return obser;
    }

    public void sucessGetDisplaySet(DisplaySet displaySet) {
        BleGetStateObservable.getInstance().setChanged();
        BleGetStateObservable.getInstance().notifyObservers(displaySet);
    }

}