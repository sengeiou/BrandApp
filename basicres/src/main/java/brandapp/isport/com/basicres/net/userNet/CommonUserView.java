package brandapp.isport.com.basicres.net.userNet;

import brandapp.isport.com.basicres.commonbean.CommonFriendRelation;
import brandapp.isport.com.basicres.commonbean.UserInfoBean;
import brandapp.isport.com.basicres.mvp.BaseView;

public interface CommonUserView extends BaseView {

    public void onSuccessUserInfo(UserInfoBean userInfoBean);
    public void onSuccessUserFriendRelation(CommonFriendRelation userInfoBean);




}
