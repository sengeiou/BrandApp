
package brandapp.isport.com.basicres.service.observe;

import java.util.Observable;

/**
 * ClassName:NetProgressBar <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Date: 2017年4月19日 上午11:31:46 <br/>
 *
 * @author Administrator
 */
public class BatteryLowObservable extends Observable {

    private static BatteryLowObservable obser;

    public static final int SHOW_SCALE_TIPS = 10;
    public static final int DISMISS_SCALE_TIPS = 11;

    @Override
    public synchronized void setChanged() {
        super.setChanged();
    }

    private BatteryLowObservable() {
        super();
    }

    public static BatteryLowObservable getInstance() {
        if (obser == null) {
            synchronized (BatteryLowObservable.class) {
                if (obser == null) {
                    obser = new BatteryLowObservable();
                }
            }
        }
        return obser;
    }

    public void show() {
        show(null);
    }

    /**
     * @param isCancelable 是否允许返回键关闭Dialog true允许
     */
    public void show(boolean isCancelable) {
        show(null, isCancelable);
    }

    public void show(String msg) {
        show(msg, true);
    }

    public void show(String msg, boolean isCancelable) {
        BatteryLowObservable.getInstance().setChanged();
        BatteryLowObservable.getInstance().notifyObservers(true);
    }


}