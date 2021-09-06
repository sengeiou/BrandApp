
package com.isport.blelibrary.observe;

import com.isport.blelibrary.entry.RopeTargetDataBean;
import com.isport.blelibrary.utils.Utils;

import java.util.Observable;

/**
 * ClassName:NetProgressBar <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Date: 2017年4月19日 上午11:31:46 <br/>
 *
 * @author Administrator
 */
public class GetRopeTargDataObservable extends Observable {

    private static GetRopeTargDataObservable obser;


    @Override
    public synchronized void setChanged() {
        super.setChanged();
    }

    private GetRopeTargDataObservable() {
        super();
    }

    public static GetRopeTargDataObservable getInstance() {
        if (obser == null) {
            synchronized (GetRopeTargDataObservable.class) {
                if (obser == null) {
                    obser = new GetRopeTargDataObservable();
                }
            }
        }
        return obser;
    }

    public void successTargetData(byte[] data) {
        //00 80 01 00 47 84 0E 00 2D 00 00 00
        RopeTargetDataBean ropeRealDataBean = new RopeTargetDataBean();
        ropeRealDataBean.setRopeType(data[3]);
        int countdown = (Utils.byte2Int(data[7]) << 8) + Utils.byte2Int(data[6]);
        ropeRealDataBean.setTargetCount(countdown);
        ropeRealDataBean.setTargetMin(data[4]);
        ropeRealDataBean.setTargetSec(data[5]);
        GetRopeTargDataObservable.getInstance().setChanged();
        GetRopeTargDataObservable.getInstance().notifyObservers(ropeRealDataBean);
    }


}