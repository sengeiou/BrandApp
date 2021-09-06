package phone.gym.jkcq.com.socialmodule.personal.repository;


import java.io.File;
import java.util.List;
import java.util.Map;

import brandapp.isport.com.basicres.commonbean.BaseUrl;
import brandapp.isport.com.basicres.commonnet.net.PostBody;
import brandapp.isport.com.basicres.commonnet.net.RxScheduler;
import brandapp.isport.com.basicres.commonutil.StringUtil;
import brandapp.isport.com.basicres.entry.bean.UpdatePhotoBean;
import brandapp.isport.com.basicres.mvp.NetworkBoundResource;
import brandapp.isport.com.basicres.net.userNet.UserRetrofitClient;
import io.reactivex.Observable;
import phone.gym.jkcq.com.commonres.common.JkConfiguration;
import phone.gym.jkcq.com.socialmodule.bean.FollowInfo;
import phone.gym.jkcq.com.socialmodule.bean.FriendInfo;
import phone.gym.jkcq.com.socialmodule.bean.ListData;
import phone.gym.jkcq.com.socialmodule.bean.PraiseInfo;
import phone.gym.jkcq.com.socialmodule.bean.RankInfo;
import phone.gym.jkcq.com.socialmodule.net.APIService;
import phone.gym.jkcq.com.socialmodule.net.RetrofitClient;
import phone.gym.jkcq.com.socialmodule.personal.bean.UpdateUserBean;


public class UserRepository {


    public static Observable<Integer> requestEditUserBg(String userId, String url) {
        return new NetworkBoundResource<Integer>() {
            @Override
            public Observable<Integer> getFromDb() {
                return null;
            }

            @Override
            public Observable<Integer> getNoCacheData() {
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
            public Observable<Integer> getRemoteSource() {
                PostBody postBody = new PostBody();
                BaseUrl baseUrl = new BaseUrl();
                baseUrl.userid = userId;
                baseUrl.extend1 = url;
                postBody.requseturl = baseUrl;
                return (Observable<Integer>) RetrofitClient.getInstance().postEdit(postBody);


            }

            @Override
            public void saveRemoteSource(Integer bean) {

            }
        }.getAsObservable();

    }

    public static Observable<Integer> requestEditUserBean(String userId, String sex, String name, String height, String weight, String defaultDay, String myProfile) {
        return new NetworkBoundResource<Integer>() {
            @Override
            public Observable<Integer> getFromDb() {
                return null;
            }

            @Override
            public Observable<Integer> getNoCacheData() {
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
            public Observable<Integer> getRemoteSource() {
                PostBody postBody = new PostBody();
                BaseUrl baseUrl = new BaseUrl();
                UpdateUserBean userInfoBean = new UpdateUserBean();
                userInfoBean.setGender(sex);
                userInfoBean.setHeight(StringUtil.getNumberStr(height));
                userInfoBean.setInterfaceId(0);
                userInfoBean.setUserId(userId);
                userInfoBean.setMyProfile(myProfile);
                userInfoBean.setNickName(name);
                userInfoBean.setWeight(StringUtil.getNumberStr(weight));
                userInfoBean.setBirthday(defaultDay);
                postBody.data = userInfoBean;
                postBody.type = JkConfiguration.RequstType.EDITBASICINFO;
                return (Observable<Integer>) UserRetrofitClient.getInstance().post(postBody);


            }

            @Override
            public void saveRemoteSource(Integer bean) {

            }
        }.getAsObservable();

    }

    public static Observable<UpdatePhotoBean> requestSaveHeadImage(File file, String userid) {
        return new NetworkBoundResource<UpdatePhotoBean>() {
            @Override
            public Observable<UpdatePhotoBean> getFromDb() {
                return null;
            }

            @Override
            public Observable<UpdatePhotoBean> getNoCacheData() {
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
            public Observable<UpdatePhotoBean> getRemoteSource() {
                return (Observable<UpdatePhotoBean>) RetrofitClient.getInstance().updateFile(file, userid);
            }

            @Override
            public void saveRemoteSource(UpdatePhotoBean bean) {

            }
        }.getAsObservable();
    }

    /**
     * 取消关注
     *
     * @return
     */
    public static Observable<FollowInfo> unFollow(String toUserId) {
        return new NetworkBoundResource<FollowInfo>() {
            @Override
            public Observable<FollowInfo> getFromDb() {
                return null;
            }

            @Override
            public Observable<FollowInfo> getNoCacheData() {
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
            public Observable<FollowInfo> getRemoteSource() {
                return RetrofitClient.getRetrofit().create(APIService.class).unFollow(toUserId).compose
                        (RxScheduler.Obs_io_main()).compose(RetrofitClient.transformer);

            }

            @Override
            public void saveRemoteSource(FollowInfo bean) {

            }
        }.getAsObservable();


    }

    /**
     * 添加关注
     *
     * @return
     */
    public static Observable<FollowInfo> addFollow(String toUserId) {
        return new NetworkBoundResource<FollowInfo>() {
            @Override
            public Observable<FollowInfo> getFromDb() {
                return null;
            }

            @Override
            public Observable<FollowInfo> getNoCacheData() {
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
            public Observable<FollowInfo> getRemoteSource() {
                return RetrofitClient.getRetrofit().create(APIService.class).addFollow(toUserId).compose
                        (RxScheduler.Obs_io_main()).compose(RetrofitClient.transformer);

            }

            @Override
            public void saveRemoteSource(FollowInfo bean) {

            }
        }.getAsObservable();


    }

    /**
     * 查询好友
     *
     * @param userId
     * @param type
     * @param page
     * @param size
     * @return
     */
    public static Observable<ListData<FriendInfo>> getFriend(String userId, int type, int page, int size) {
        return new NetworkBoundResource<ListData<FriendInfo>>() {
            @Override
            public Observable<ListData<FriendInfo>> getFromDb() {
                return null;
            }

            @Override
            public Observable<ListData<FriendInfo>> getNoCacheData() {
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
            public Observable<ListData<FriendInfo>> getRemoteSource() {
                return RetrofitClient.getRetrofit().create(APIService.class).getFriendList(userId, type, page, size).compose
                        (RxScheduler.Obs_io_main()).compose(RetrofitClient.transformer);

            }

            @Override
            public void saveRemoteSource(ListData<FriendInfo> bean) {

            }
        }.getAsObservable();


    }

    /**
     * 搜索好友
     *
     * @param map
     * @return
     */
    public static Observable<ListData<FriendInfo>> searchFriend(Map map, int page, int size) {
        return new NetworkBoundResource<ListData<FriendInfo>>() {
            @Override
            public Observable<ListData<FriendInfo>> getFromDb() {
                return null;
            }

            @Override
            public Observable<ListData<FriendInfo>> getNoCacheData() {
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
            public Observable<ListData<FriendInfo>> getRemoteSource() {
                return RetrofitClient.getRetrofit().create(APIService.class).searchFriend(map, page, size).compose
                        (RxScheduler.Obs_io_main()).compose(RetrofitClient.transformer);

            }

            @Override
            public void saveRemoteSource(ListData<FriendInfo> bean) {

            }
        }.getAsObservable();

    }


    /**
     * 获取排行信息
     *
     * @param map
     * @return
     */
    public static Observable<List<RankInfo>> getRankInfo(Map map) {
        return new NetworkBoundResource<List<RankInfo>>() {
            @Override
            public Observable<List<RankInfo>> getFromDb() {
                return null;
            }

            @Override
            public Observable<List<RankInfo>> getNoCacheData() {
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
            public Observable<List<RankInfo>> getRemoteSource() {
                return RetrofitClient.getRetrofit().create(APIService.class).getRankInfo(map).compose
                        (RxScheduler.Obs_io_main()).compose(RetrofitClient.transformer);

            }

            @Override
            public void saveRemoteSource(List<RankInfo> bean) {

            }
        }.getAsObservable();

    }

    /**
     * 获取跳绳排行信息
     *
     * @param day
     * @param type
     * @param userId
     * @return
     */
    public static Observable<List<RankInfo>> getRopeRankInfo(String day, String type, String userId) {
        return new NetworkBoundResource<List<RankInfo>>() {
            @Override
            public Observable<List<RankInfo>> getFromDb() {
                return null;
            }

            @Override
            public Observable<List<RankInfo>> getNoCacheData() {
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
            public Observable<List<RankInfo>> getRemoteSource() {
                return RetrofitClient.getRetrofit().create(APIService.class).getRopeRankInfo(day, type, userId).compose
                        (RxScheduler.Obs_io_main()).compose(RetrofitClient.transformer);

            }

            @Override
            public void saveRemoteSource(List<RankInfo> bean) {

            }
        }.getAsObservable();

    }

    /**
     * 排行点赞
     *
     * @param map
     * @return
     */
    public static Observable<PraiseInfo> praiseRank(Map map) {
        return new NetworkBoundResource<PraiseInfo>() {
            @Override
            public Observable<PraiseInfo> getFromDb() {
                return null;
            }

            @Override
            public Observable<PraiseInfo> getNoCacheData() {
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
            public Observable<PraiseInfo> getRemoteSource() {
                return RetrofitClient.getRetrofit().create(APIService.class).rankPraise(map).compose
                        (RxScheduler.Obs_io_main()).compose(RetrofitClient.transformer);

            }

            @Override
            public void saveRemoteSource(PraiseInfo bean) {

            }
        }.getAsObservable();

    }
}
