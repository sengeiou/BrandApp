
package com.isport.blelibrary.observe;

import com.isport.blelibrary.utils.Constants;
import com.isport.blelibrary.utils.Logger;

import java.util.Observable;

/**
 * ClassName:NetProgressBar <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Date: 2017年4月19日 上午11:31:46 <br/>
 *
 * @author Administrator
 */
public class W560HrSwtchObservable extends Observable {

    private static W560HrSwtchObservable obser;


    @Override
    public synchronized void setChanged() {
        super.setChanged();
    }

    private W560HrSwtchObservable() {
        super();
    }

    public static W560HrSwtchObservable getInstance() {
        if (obser == null) {
            synchronized (W560HrSwtchObservable.class) {
                if (obser == null) {
                    obser = new W560HrSwtchObservable();
                }
            }
        }
        return obser;
    }

    public void noDataUpdate(boolean state) {
        Logger.myLog("noDataUpdate");
        Constants.W55XHrState = state;
        W560HrSwtchObservable.getInstance().setChanged();
        W560HrSwtchObservable.getInstance().notifyObservers(state);
    }


}