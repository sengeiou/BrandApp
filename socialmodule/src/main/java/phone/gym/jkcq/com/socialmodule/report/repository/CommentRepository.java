package phone.gym.jkcq.com.socialmodule.report.repository;

import android.text.TextUtils;

import java.util.HashMap;
import java.util.Map;

import brandapp.isport.com.basicres.commonnet.net.RxScheduler;
import brandapp.isport.com.basicres.mvp.NetworkBoundResource;
import io.reactivex.Observable;
import phone.gym.jkcq.com.socialmodule.bean.ListDataCommend;
import phone.gym.jkcq.com.socialmodule.bean.response.CommendDelBean;
import phone.gym.jkcq.com.socialmodule.bean.result.ResultCommentBean;
import phone.gym.jkcq.com.socialmodule.bean.result.ResultLikeBean;
import phone.gym.jkcq.com.socialmodule.net.APIService;
import phone.gym.jkcq.com.socialmodule.net.RetrofitClient;

public class CommentRepository {
    /**
     * 评论点赞功能
     *
     * @param
     * @return
     */
    public static Observable<ResultLikeBean> dynamicPraise(String dynamicCommentId, String meUserId, String toUserId) {
        return new NetworkBoundResource<ResultLikeBean>() {
            @Override
            public Observable<ResultLikeBean> getFromDb() {
                return null;
            }

            @Override
            public Observable<ResultLikeBean> getNoCacheData() {
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
            public Observable<ResultLikeBean> getRemoteSource() {

                Map<String, Object> map = new HashMap();
                map.put("dynamicCommentId", dynamicCommentId);
                map.put("meUserId", meUserId);
                map.put("toUserId", toUserId);
                return RetrofitClient.getRetrofit().create(APIService.class).liketoReport(map).compose
                        (RxScheduler.Obs_io_main()).compose(RetrofitClient.transformer);

            }

            @Override
            public void saveRemoteSource(ResultLikeBean bean) {

            }
        }.getAsObservable();

    }

    /**
     * 发表评论功能
     *
     * @param
     * @return
     */
    public static Observable<String> addComment(String dynamicId, String dynamicCommentId, String fromUserId, String toUserId, String content) {
        return new NetworkBoundResource<String>() {
            @Override
            public Observable<String> getFromDb() {
                return null;
            }

            @Override
            public Observable<String> getNoCacheData() {
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
            public Observable<String> getRemoteSource() {
                Map<String, Object> map = new HashMap();

                /**
                 * {
                 *   "content": "测试APP",
                 *   "contentType": 0,
                 *   "dynamicId": "1263776117649383425",
                 *   "fromUserId": 260,
                 *   "toUserId": 260
                 * }
                 */
                map.put("content", content);
                map.put("contentType", 0);
                map.put("dynamicId", dynamicId);
                map.put("fromUserId", fromUserId);
                map.put("toUserId", toUserId);
                if (!TextUtils.isEmpty(dynamicCommentId)) {
                    map.put("dynamicCommentId", dynamicCommentId);
                }
                return RetrofitClient.getRetrofit().create(APIService.class).addDynamicComment(map).compose
                        (RxScheduler.Obs_io_main()).compose(RetrofitClient.transformer);

            }

            @Override
            public void saveRemoteSource(String bean) {

            }
        }.getAsObservable();

    }

    /**
     * 获取评论
     *
     * @param
     * @return
     */
    public static Observable<ResultCommentBean<ListDataCommend>> getCommond(String dynamicCommentId, String dynamicId, String positionDynamicCommentId, String meUserId, int page, int size) {
        return new NetworkBoundResource<ResultCommentBean<ListDataCommend>>() {
            @Override
            public Observable<ResultCommentBean<ListDataCommend>> getFromDb() {
                return null;
            }

            @Override
            public Observable<ResultCommentBean<ListDataCommend>> getNoCacheData() {
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
            public Observable<ResultCommentBean<ListDataCommend>> getRemoteSource() {

                Map<String, Object> map = new HashMap();

                /**
                 * {
                 *   "content": "测试APP",
                 *   "contentType": 0,
                 *   "dynamicId": "1263776117649383425",
                 *   "fromUserId": 260,
                 *   "toUserId": 260
                 * }
                 */
                if(!TextUtils.isEmpty(dynamicCommentId)) {
                    map.put("dynamicCommentId", dynamicCommentId);
                }

                map.put("dynamicId", dynamicId);
                map.put("meUserId", meUserId);
                if(!TextUtils.isEmpty(positionDynamicCommentId)) {
                    map.put("positionDynamicCommentId", positionDynamicCommentId);
                }
                map.put("page", page);
                map.put("size", size);
                if (!TextUtils.isEmpty(dynamicCommentId)) {
                    //子评论
                    return RetrofitClient.getRetrofit().create(APIService.class).getDynamicCommentReplyPage(map).compose
                            (RxScheduler.Obs_io_main()).compose(RetrofitClient.transformer);
                }else{
                    //主评论
                    return RetrofitClient.getRetrofit().create(APIService.class).getDynamicCommentCommentPage(map).compose
                            (RxScheduler.Obs_io_main()).compose(RetrofitClient.transformer);
                }


            }

            @Override
            public void saveRemoteSource(ResultCommentBean<ListDataCommend> bean) {

            }
        }.getAsObservable();

    }

    /**
     * 删除评论
     */
    public static Observable<CommendDelBean> delCommond(String dynamicCommentId) {
        return new NetworkBoundResource<CommendDelBean>() {
            @Override
            public Observable<CommendDelBean> getFromDb() {
                return null;
            }

            @Override
            public Observable<CommendDelBean> getNoCacheData() {
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
            public Observable<CommendDelBean> getRemoteSource() {

                    return RetrofitClient.getRetrofit().create(APIService.class).delDynamicComment(dynamicCommentId).compose
                            (RxScheduler.Obs_io_main()).compose(RetrofitClient.transformer);


            }

            @Override
            public void saveRemoteSource(CommendDelBean bean) {

            }
        }.getAsObservable();

    }



}
