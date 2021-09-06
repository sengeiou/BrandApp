package phone.gym.jkcq.com.socialmodule.mvp.view;

import brandapp.isport.com.basicres.mvp.BaseView;
import phone.gym.jkcq.com.socialmodule.bean.FriendInfo;
import phone.gym.jkcq.com.socialmodule.bean.ListData;

public interface FriendView extends BaseView {

    void addFollowSuccess(int type);
    void unFollowSuccess(int type);
    void findFriendSuccess(ListData<FriendInfo> friendInfos);
    void searchFriendSuccess(ListData<FriendInfo> friendInfos);
}
