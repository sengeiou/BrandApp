  
package com.isport.brandapp.sport.service;

import brandapp.isport.com.basicres.mvp.BaseView;

/** 
 * ClassName:OutdoorRunningServiceView <br/> 
 * Function: TODO ADD FUNCTION. <br/> 
 * Date:     2017年3月25日 下午6:05:55 <br/> 
 * @author   Administrator 
 * @version       
 */
public interface OutdoorRunningServiceView extends BaseView {

    void onPkRunFinishFailed(String message);

    void onPkRunSuccess(String result);

}
  