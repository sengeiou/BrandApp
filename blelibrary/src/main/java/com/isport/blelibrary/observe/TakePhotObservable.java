
package com.isport.blelibrary.observe;

import com.isport.blelibrary.entry.DisplaySet;

import java.util.Observable;

/**
 * ClassName:NetProgressBar <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Date: 2017年4月19日 上午11:31:46 <br/>
 *
 * @author Administrator
 */
public class TakePhotObservable extends Observable {

    private static TakePhotObservable obser;

    public static final int SHOW_PROGRESS_BAR = 0;
    public static final int DISMISS_PORGRESS_BAR = 1;

    @Override
    public synchronized void setChanged() {
        super.setChanged();
    }

    private TakePhotObservable() {
        super();
    }

    public static TakePhotObservable getInstance() {
        if (obser == null) {
            synchronized (TakePhotObservable.class) {
                if (obser == null) {
                    obser = new TakePhotObservable();
                }
            }
        }
        return obser;
    }

    public void takePhoto(boolean isOpen) {
        TakePhotObservable.getInstance().setChanged();
        TakePhotObservable.getInstance().notifyObservers(isOpen);
    }

}