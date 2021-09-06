
package com.isport.blelibrary.observe;

import com.isport.blelibrary.observe.bean.ResultBean;

import java.util.Observable;

/**
 * ClassName:NetProgressBar <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Date: 2017年4月19日 上午11:31:46 <br/>
 *
 * @author Administrator
 */
public class BleSettingObservable extends Observable {

    private static BleSettingObservable obser;

    public static final int SHOW_PROGRESS_BAR = 0;
    public static final int DISMISS_PORGRESS_BAR = 1;

    @Override
    public synchronized void setChanged() {
        super.setChanged();
    }

    private BleSettingObservable() {
        super();
    }

    public static BleSettingObservable getInstance() {
        if (obser == null) {
            synchronized (BleSettingObservable.class) {
                if (obser == null) {
                    obser = new BleSettingObservable();
                }
            }
        }
        return obser;
    }

    public void successSendCmd(ResultBean resultBean) {
        BleSettingObservable.getInstance().setChanged();
        BleSettingObservable.getInstance().notifyObservers(resultBean);
    }

}