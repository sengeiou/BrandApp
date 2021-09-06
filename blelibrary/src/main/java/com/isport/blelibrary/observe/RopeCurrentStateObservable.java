
package com.isport.blelibrary.observe;

import java.util.Observable;

/**
 * ClassName:NetProgressBar <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Date: 2017年4月19日 上午11:31:46 <br/>
 *
 * @author Administrator
 */
public class RopeCurrentStateObservable extends Observable {

    private static RopeCurrentStateObservable obser;


    @Override
    public synchronized void setChanged() {
        super.setChanged();
    }

    private RopeCurrentStateObservable() {
        super();
    }

    public static RopeCurrentStateObservable getInstance() {
        if (obser == null) {
            synchronized (RopeCurrentStateObservable.class) {
                if (obser == null) {
                    obser = new RopeCurrentStateObservable();
                }
            }
        }
        return obser;
    }

    public void successStartOrEnd(boolean isSuccess) {

        RopeCurrentStateObservable.getInstance().setChanged();
        RopeCurrentStateObservable.getInstance().notifyObservers(isSuccess);
    }


}