
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
public class RopeSyncDataObservable extends Observable {

    private static RopeSyncDataObservable obser;


    @Override
    public synchronized void setChanged() {
        super.setChanged();
    }

    private RopeSyncDataObservable() {
        super();
    }

    public static RopeSyncDataObservable getInstance() {
        if (obser == null) {
            synchronized (RopeSyncDataObservable.class) {
                if (obser == null) {
                    obser = new RopeSyncDataObservable();
                }
            }
        }
        return obser;
    }

    public void successRealData(Boolean isSuccess) {

        RopeSyncDataObservable.getInstance().setChanged();
        RopeSyncDataObservable.getInstance().notifyObservers(isSuccess);
    }

    public void sucessGetDisplaySet(DisplaySet displaySet) {
        RopeSyncDataObservable.getInstance().setChanged();
        RopeSyncDataObservable.getInstance().notifyObservers(displaySet);
    }

}