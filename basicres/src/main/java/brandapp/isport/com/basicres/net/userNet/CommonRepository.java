package brandapp.isport.com.basicres.net.userNet;

import android.text.TextUtils;

import brandapp.isport.com.basicres.BaseApp;
import brandapp.isport.com.basicres.commonbean.CommonFriendRelation;
import brandapp.isport.com.basicres.commonbean.UserInfoBean;
import brandapp.isport.com.basicres.commonnet.net.PostBody;
import brandapp.isport.com.basicres.commonutil.TokenUtil;
import brandapp.isport.com.basicres.entry.bean.BaseParms;
import brandapp.isport.com.basicres.entry.bean.OssBean;
import brandapp.isport.com.basicres.mvp.NetworkBoundResource;
import io.reactivex.Observable;
import phone.gym.jkcq.com.commonres.common.JkConfiguration;

public class CommonRepository {


    public static Observable<OssBean> requestOssToken() {
        return new NetworkBoundResource<OssBean>() {
            @Override
            public Observable<OssBean> getFromDb() {
                return null;
            }

            @Override
            public Observable<OssBean> getNoCacheData() {
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
            public Observable<OssBean> getRemoteSource() {

                return (Observable<OssBean>) UserRetrofitClient.getInstance().gettOss();


            }

            @Override
            public void saveRemoteSource(OssBean bean) {

            }
        }.getAsObservable();

    }

    public static Observable<CommonFriendRelation> requsetFriendRelation(String userId) {
        return new NetworkBoundResource<CommonFriendRelation>() {
            @Override
            public Observable<CommonFriendRelation> getFromDb() {
                return null;
            }

            @Override
            public Observable<CommonFriendRelation> getNoCacheData() {
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
            public Observable<CommonFriendRelation> getRemoteSource() {
                PostBody postBody = new PostBody();
                BaseParms parms = new BaseParms();
                parms.setInterfaceId("0");
                parms.setUserId(userId);
                postBody.data = parms;
                postBody.type = JkConfiguration.RequstType.GET_USER_FRIEND_RELATION;
                return (Observable<CommonFriendRelation>) UserRetrofitClient.getInstance().post(postBody);


            }

            @Override
            public void saveRemoteSource(CommonFriendRelation bean) {

                //这里需要保存用户信息

            }
        }.getAsObservable();
    }

    public static Observable<UserInfoBean> requsetUserInfo(String userId) {
        return new NetworkBoundResource<UserInfoBean>() {
            @Override
            public Observable<UserInfoBean> getFromDb() {
                return null;
            }

            @Override
            public Observable<UserInfoBean> getNoCacheData() {
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
            public Observable<UserInfoBean> getRemoteSource() {
                PostBody postBody = new PostBody();
                BaseParms parms = new BaseParms();
                parms.setInterfaceId("0");
                parms.setUserId(userId);
                postBody.data = parms;
                postBody.type = JkConfiguration.RequstType.GET_USERINFO;
                return (Observable<UserInfoBean>) UserRetrofitClient.getInstance().post(postBody);


            }

            @Override
            public void saveRemoteSource(UserInfoBean bean) {

                //这里需要保存用户信息

                if(bean!=null) {
                    String id=TokenUtil.getInstance().getPeopleIdStr(BaseApp.getApp());
                    if(TextUtils.isEmpty(id)){
                        TokenUtil.getInstance().updatePeopleId(BaseApp.getApp(), bean.getUserId());
                    }
                    CommonUserAcacheUtil.saveUsrInfo(bean.getUserId(), bean);
                }

            }
        }.getAsObservable();
    }



}
