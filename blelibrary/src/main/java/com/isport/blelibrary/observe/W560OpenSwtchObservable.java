
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
public class W560OpenSwtchObservable extends Observable {

    private static W560OpenSwtchObservable obser;


    @Override
    public synchronized void setChanged() {
        super.setChanged();
    }

    private W560OpenSwtchObservable() {
        super();
    }

    public static W560OpenSwtchObservable getInstance() {
        if (obser == null) {
            synchronized (W560OpenSwtchObservable.class) {
                if (obser == null) {
                    obser = new W560OpenSwtchObservable();
                }
            }
        }
        return obser;
    }

    public void noDataUpdate() {
        Logger.myLog("noDataUpdate");
        W560OpenSwtchObservable.getInstance().setChanged();
        W560OpenSwtchObservable.getInstance().notifyObservers();
    }


}