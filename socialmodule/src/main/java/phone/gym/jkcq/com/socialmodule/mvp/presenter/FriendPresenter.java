package phone.gym.jkcq.com.socialmodule.mvp.presenter;

import org.greenrobot.eventbus.EventBus;

import java.util.HashMap;

import brandapp.isport.com.basicres.BaseApp;
import brandapp.isport.com.basicres.commonnet.interceptor.BaseObserver;
import brandapp.isport.com.basicres.commonnet.interceptor.ExceptionHandle;
import brandapp.isport.com.basicres.commonutil.MessageEvent;
import brandapp.isport.com.basicres.commonutil.ToastUtils;
import brandapp.isport.com.basicres.commonutil.UIUtils;
import brandapp.isport.com.basicres.mvp.BasePresenter;
import phone.gym.jkcq.com.socialmodule.R;
import phone.gym.jkcq.com.socialmodule.bean.FollowInfo;
import phone.gym.jkcq.com.socialmodule.bean.FriendInfo;
import phone.gym.jkcq.com.socialmodule.bean.ListData;
import phone.gym.jkcq.com.socialmodule.bean.UpdateFollowStateBean;
import phone.gym.jkcq.com.socialmodule.mvp.view.FriendView;
import phone.gym.jkcq.com.socialmodule.personal.repository.UserRepository;

public class FriendPresenter extends BasePresenter<FriendView> {

    private FriendView mFriendView ;

    public FriendPresenter(FriendView friendView){
        this.mFriendView=friendView;
    }

    /**
     * 取消关注
     */
    public void unFollow(String toUserId) {
        UserRepository.unFollow(toUserId).as(mFriendView.bindAutoDispose()).subscribe(new BaseObserver<FollowInfo>(BaseApp.getApp()) {
            @Override
            protected void hideDialog() {

            }

            @Override
            protected void showDialog() {

            }

            @Override
            public void onError(ExceptionHandle.ResponeThrowable e) {

            }

            @Override
            public void onNext(FollowInfo info) {
                ToastUtils.showToast(UIUtils.getContext(), UIUtils.getString(R.string.friend_option_success));
                if (info != null ) {
                    UpdateFollowStateBean updateFollowStateBean = new UpdateFollowStateBean();
                    updateFollowStateBean.setUserId(toUserId);
                    updateFollowStateBean.setFollowStatus(info.getType());
                    EventBus.getDefault().post(new MessageEvent(updateFollowStateBean, MessageEvent.update_dynamic_follow_state));
                    if (mFriendView != null) {
                        mFriendView.unFollowSuccess(info.getType());
                    }
                }

            }
        });
    }

    /**
     * 关注
     */
    public void addFollow(String toUserId) {
        UserRepository.addFollow(toUserId).as(mFriendView.bindAutoDispose()).subscribe(new BaseObserver<FollowInfo>(BaseApp.getApp()) {
            @Override
            protected void hideDialog() {

            }

            @Override
            protected void showDialog() {

            }

            @Override
            public void onError(ExceptionHandle.ResponeThrowable e) {

            }

            @Override
            public void onNext(FollowInfo info) {
                //需要刷新数据集合的数据 用户id是这个的数据

                ToastUtils.showToast(UIUtils.getContext(), UIUtils.getString(R.string.friend_option_success));
                if (info != null ) {
                    UpdateFollowStateBean updateFollowStateBean = new UpdateFollowStateBean();
                    updateFollowStateBean.setUserId(toUserId);
                    updateFollowStateBean.setFollowStatus(info.getType());
                    EventBus.getDefault().post(new MessageEvent(updateFollowStateBean, MessageEvent.update_dynamic_follow_state));
                    //刷新下首页的数据

                    if (mFriendView != null) {
                        mFriendView.addFollowSuccess(info.getType());
                    }
                }

            }
        });
    }

    /**
     * 查询粉丝
     * @param userId
     * @param type
     * @param page
     * @param size
     */
    public void getFriend(String userId,int type,int page,int size) {
        UserRepository.getFriend(userId, type,page,size).as(mFriendView.bindAutoDispose()).subscribe(new BaseObserver<ListData<FriendInfo>>(BaseApp.getApp()) {
            @Override
            protected void hideDialog() {

            }

            @Override
            protected void showDialog() {

            }

            @Override
            public void onError(ExceptionHandle.ResponeThrowable e) {

            }

            @Override
            public void onNext(ListData<FriendInfo> info) {

                if (info != null ) {
                    if (mFriendView != null) {
                        mFriendView.findFriendSuccess(info);
                    }
                }

            }
        });
    }

    /**
     * 搜索粉丝
     */
    public void searchFriend(String value,int page,int size) {
        HashMap<String,String> map=new HashMap();
        map.put("queryKey",value);
        UserRepository.searchFriend( map,page,size).as(mFriendView.bindAutoDispose()).subscribe(new BaseObserver<ListData<FriendInfo>>(BaseApp.getApp()) {
            @Override
            protected void hideDialog() {

            }

            @Override
            protected void showDialog() {

            }

            @Override
            public void onError(ExceptionHandle.ResponeThrowable e) {

            }

            @Override
            public void onNext(ListData<FriendInfo> info) {

                if (info != null ) {
                    if (mFriendView != null) {
                        mFriendView.searchFriendSuccess(info);
                    }
                }

            }
        });
    }

    public void followFriend() {

    }

    public void unFollowFriend() {

    }
}
