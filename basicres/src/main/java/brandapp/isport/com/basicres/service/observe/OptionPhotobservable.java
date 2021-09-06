package brandapp.isport.com.basicres.service.observe;

import com.isport.blelibrary.observe.TakePhotObservable;

import java.util.Observable;

public class OptionPhotobservable extends Observable {


    private static OptionPhotobservable obser;

    public static final int SHOW_PROGRESS_BAR = 0;
    public static final int DISMISS_PORGRESS_BAR = 1;

    @Override
    public synchronized void setChanged() {
        super.setChanged();
    }

    private OptionPhotobservable() {
        super();
    }

    public static OptionPhotobservable getInstance() {
        if (obser == null) {
            synchronized (TakePhotObservable.class) {
                if (obser == null) {
                    obser = new OptionPhotobservable();
                }
            }
        }
        return obser;
    }

    public void takePhoto(boolean isOpen) {
        OptionPhotobservable.getInstance().setChanged();
        OptionPhotobservable.getInstance().notifyObservers(isOpen);
    }
}
