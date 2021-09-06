  
package brandapp.isport.com.basicres.service.observe;

import java.util.Observable;

/** 
 * ClassName:IndoorRunObservable <br/> 
 * Function: TODO ADD FUNCTION. <br/> 
 * Date:     2017年3月26日 下午8:12:38 <br/> 
 * @author   Administrator 
 * @version       
 */
public class IndoorRunObservable extends Observable {

    private static IndoorRunObservable obser;

    @Override
    public synchronized void setChanged() {
        super.setChanged();
    }

    private IndoorRunObservable() {
        super();
    }

    public static IndoorRunObservable getInstance() {
        if (obser == null) {
            synchronized (IndoorRunObservable.class) {
                if (obser == null) {
                    obser = new IndoorRunObservable();
                }
            }
        }
        return obser;
    }


}
  