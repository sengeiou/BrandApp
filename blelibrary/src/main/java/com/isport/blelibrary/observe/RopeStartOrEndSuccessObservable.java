
package com.isport.blelibrary.observe;

import com.isport.blelibrary.entry.RopeRealDataBean;

import java.util.Observable;

/**
 * ClassName:NetProgressBar <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Date: 2017年4月19日 上午11:31:46 <br/>
 *
 * @author Administrator
 */
public class RopeStartOrEndSuccessObservable extends Observable {

    private static RopeStartOrEndSuccessObservable obser;


    @Override
    public synchronized void setChanged() {
        super.setChanged();
    }

    private RopeStartOrEndSuccessObservable() {
        super();
    }

    public static RopeStartOrEndSuccessObservable getInstance() {
        if (obser == null) {
            synchronized (RopeStartOrEndSuccessObservable.class) {
                if (obser == null) {
                    obser = new RopeStartOrEndSuccessObservable();
                }
            }
        }
        return obser;
    }


    public void getRopeType(int type, int min,int sec,int countdown) {
        RopeRealDataBean ropeRealDataBean = new RopeRealDataBean();
        ropeRealDataBean.setRopeType(type);
        ropeRealDataBean.setCountdown(countdown);
        ropeRealDataBean.setCountdownMin(min);
        ropeRealDataBean.setCountdownSec(sec);
        RopeStartOrEndSuccessObservable.getInstance().setChanged();
        RopeStartOrEndSuccessObservable.getInstance().notifyObservers(ropeRealDataBean);
    }

    public void successStartOrEnd(Boolean isSuccess) {

        RopeStartOrEndSuccessObservable.getInstance().setChanged();
        RopeStartOrEndSuccessObservable.getInstance().notifyObservers(isSuccess);
    }


}