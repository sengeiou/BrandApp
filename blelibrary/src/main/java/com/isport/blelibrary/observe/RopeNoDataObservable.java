
package com.isport.blelibrary.observe;

import com.isport.blelibrary.utils.Logger;

import java.util.Observable;

/**
 * ClassName:NetProgressBar <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Date: 2017年4月19日 上午11:31:46 <br/>
 *
 * @author Administrator
 */
public class RopeNoDataObservable extends Observable {

    private static RopeNoDataObservable obser;


    @Override
    public synchronized void setChanged() {
        super.setChanged();
    }

    private RopeNoDataObservable() {
        super();
    }

    public static RopeNoDataObservable getInstance() {
        if (obser == null) {
            synchronized (RopeNoDataObservable.class) {
                if (obser == null) {
                    obser = new RopeNoDataObservable();
                }
            }
        }
        return obser;
    }

    public void noDataUpdate() {
        Logger.myLog("noDataUpdate");
        RopeNoDataObservable.getInstance().setChanged();
        RopeNoDataObservable.getInstance().notifyObservers(true);
    }


}