package com.isport.brandapp.ropeskipping.realsport;

import brandapp.isport.com.basicres.mvp.BaseView;

/**
 * Created by BeyondWorlds
 * on 2020/6/30
 */
public interface RealRopeSkippingView extends BaseView {
    void successChallegUpdate(String challengeId);
    
    void getAllRopeChallengeRank(String rank);

}
