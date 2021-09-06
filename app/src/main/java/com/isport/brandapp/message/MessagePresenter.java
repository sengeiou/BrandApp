package com.isport.brandapp.message;

import com.isport.brandapp.net.APIService;
import com.isport.brandapp.net.RetrofitClient;

import org.greenrobot.eventbus.EventBus;

import java.util.HashMap;
import java.util.Map;

import brandapp.isport.com.basicres.BaseApp;
import brandapp.isport.com.basicres.commonnet.interceptor.BaseObserver;
import brandapp.isport.com.basicres.commonnet.interceptor.ExceptionHandle;
import brandapp.isport.com.basicres.commonnet.net.RxScheduler;
import brandapp.isport.com.basicres.commonutil.MessageEvent;
import brandapp.isport.com.basicres.commonutil.TokenUtil;
import brandapp.isport.com.basicres.mvp.BasePresenter;
import brandapp.isport.com.basicres.mvp.NetworkBoundResource;
import phone.gym.jkcq.com.socialmodule.bean.FollowInfo;
import phone.gym.jkcq.com.socialmodule.bean.ListData;
import phone.gym.jkcq.com.socialmodule.bean.UpdateFollowStateBean;
import phone.gym.jkcq.com.socialmodule.personal.repository.UserRepository;

public class MessagePresenter extends BasePresenter<MessageView> {

    private MessageView mMessageView;

    public MessagePresenter(MessageView messageView) {
        this.mMessageView = messageView;
    }

    /**
     * 取消关注
     */
    public void unFollow(String toUserId) {
        UserRepository.unFollow(toUserId).as(mMessageView.bindAutoDispose()).subscribe(new BaseObserver<FollowInfo>(BaseApp.getApp()) {
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
//                ToastUtils.showToast(UIUtils.getContext(), UIUtils.getString(R.string.friend_option_success));
                if (info != null) {
                    UpdateFollowStateBean updateFollowStateBean = new UpdateFollowStateBean();
                    updateFollowStateBean.setUserId(toUserId);
                    updateFollowStateBean.setFollowStatus(info.getType());
                    EventBus.getDefault().post(new MessageEvent(updateFollowStateBean, MessageEvent.update_dynamic_follow_state));
                    if (mMessageView != null) {
                        mMessageView.unFollowSuccess(info.getType());
                    }
                }

            }
        });
    }

    /**
     * 关注
     */
    public void addFollow(String toUserId) {
        UserRepository.addFollow(toUserId).as(mMessageView.bindAutoDispose()).subscribe(new BaseObserver<FollowInfo>(BaseApp.getApp()) {
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

//                ToastUtils.showToast(UIUtils.getContext(), UIUtils.getString(R.string.friend_option_success));
                if (info != null) {
                    UpdateFollowStateBean updateFollowStateBean = new UpdateFollowStateBean();
                    updateFollowStateBean.setUserId(toUserId);
                    updateFollowStateBean.setFollowStatus(info.getType());
                    EventBus.getDefault().post(new MessageEvent(updateFollowStateBean, MessageEvent.update_dynamic_follow_state));
                    //刷新下首页的数据

                    if (mMessageView != null) {
                        mMessageView.addFollowSuccess(info.getType());
                    }
                }

            }
        });
    }


    public void getMessageInfo(int page, int size, int videoType) {

        new NetworkBoundResource<ListData<MessageInfo>>() {
            @Override
            public io.reactivex.Observable<ListData<MessageInfo>> getFromDb() {
                return null;
            }

            @Override
            public io.reactivex.Observable<ListData<MessageInfo>> getNoCacheData() {
                return null;
            }

            @Override
            public boolean shouldFetchRemoteSource() {
                return false;
            }

            @Override
            public boolean shouldStandAlone() {
                return false;
            }

            @Override
            public io.reactivex.Observable<ListData<MessageInfo>> getRemoteSource() {
                Map<String, String> map = new HashMap();
                map.put("page", "" + page);
                map.put("size", "" + size);
                map.put("videoType", "" + videoType);
                map.put("userId", TokenUtil.getInstance().getPeopleIdStr(BaseApp.getApp()));
                return RetrofitClient.getRetrofit().create(APIService.class).getMessageInfo(map).compose
                        (RxScheduler.Obs_io_main()).compose(RetrofitClient.transformer);
            }

            @Override
            public void saveRemoteSource(ListData<MessageInfo> bean) {

            }
        }.getAsObservable().subscribe(new BaseObserver<ListData<MessageInfo>>(BaseApp.getApp()) {
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
            public void onNext(ListData<MessageInfo> messageList) {
                if (mMessageView != null) {
                    mMessageView.getMessageInfoSuccess(messageList);
                }

            }
        });
    }

    public void DelMessageInfo(String meesageId) {

        new NetworkBoundResource<Boolean>() {
            @Override
            public io.reactivex.Observable<Boolean> getFromDb() {
                return null;
            }

            @Override
            public io.reactivex.Observable<Boolean> getNoCacheData() {
                return null;
            }

            @Override
            public boolean shouldFetchRemoteSource() {
                return false;
            }

            @Override
            public boolean shouldStandAlone() {
                return false;
            }

            @Override
            public io.reactivex.Observable<Boolean> getRemoteSource() {
                return RetrofitClient.getRetrofit().create(APIService.class).delSocialNew(meesageId).compose
                        (RxScheduler.Obs_io_main()).compose(RetrofitClient.transformer);
            }

            @Override
            public void saveRemoteSource(Boolean bean) {

            }
        }.getAsObservable().subscribe(new BaseObserver<Boolean>(BaseApp.getApp()) {
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
            public void onNext(Boolean messageList) {
                if (mMessageView != null) {
                    mMessageView.successDel(meesageId);
                }

            }
        });
    }


}
