package phone.gym.jkcq.com.socialmodule.net;


import java.util.List;
import java.util.Map;

import brandapp.isport.com.basicres.commonbean.BaseResponse;
import io.reactivex.Observable;
import okhttp3.RequestBody;
import phone.gym.jkcq.com.socialmodule.bean.FollowInfo;
import phone.gym.jkcq.com.socialmodule.bean.FriendInfo;
import phone.gym.jkcq.com.socialmodule.bean.ListData;
import phone.gym.jkcq.com.socialmodule.bean.ListDataCommend;
import phone.gym.jkcq.com.socialmodule.bean.PraiseInfo;
import phone.gym.jkcq.com.socialmodule.bean.RankInfo;
import phone.gym.jkcq.com.socialmodule.bean.response.CommendDelBean;
import phone.gym.jkcq.com.socialmodule.bean.response.DynamBean;
import phone.gym.jkcq.com.socialmodule.bean.result.ResultCommentBean;
import phone.gym.jkcq.com.socialmodule.bean.result.ResultLikeBean;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * @author azheng
 * @date 2018/4/24.
 * GitHub：https://github.com/RookieExaminer
 * Email：wei.azheng@foxmail.com
 * Description：
 */
public interface APIService {


    /**
     * 上传背景图片
     * http://192.168.10.203:8767/isport/concumer-basic/basic/customer/editBackground
     */

    @POST("isport/concumer-basic/basic/customer/editBackground")
    Observable<BaseResponse<Integer>> editBackGround(@Query("url") String url, @Query("userId") String userId);
//    @POST("/basic/customer/editBackground")
//    Observable<BaseResponse<Integer>> editBackGround(@Query("url") String url, @Query("userId") String userId);

    /**
     * 添加关注
     *
     * @param toUserId
     * @return
     */
    @POST("isport/concumer-basic/friend/addFollowWithInterest")
    Observable<BaseResponse<FollowInfo>> addFollow(@Query("toUserId") String toUserId);

    /**
     * 取消关注
     *
     * @param toUserId
     * @return
     */
    @POST("isport/concumer-basic/friend/cancelFollowWithInterest")
    Observable<BaseResponse<FollowInfo>> unFollow(@Query("toUserId") String toUserId);

    /**
     * 搜索好友
     *
     * @param map
     * @return
     */
    @POST("isport/concumer-basic/friend/findList")
    Observable<BaseResponse<ListData<FriendInfo>>> searchFriend(@Body Map map, @Query("page") int page, @Query("size") int size);

    /**
     * 查询好友、粉丝、关注列表
     *
     * @param userId
     * @param type
     * @param page
     * @param size
     * @return
     */
    @GET("isport/concumer-basic/friend/findList/{userId}/{type}")
    Observable<BaseResponse<ListData<FriendInfo>>> getFriendList(@Path("userId") String userId, @Path("type") int type, @Query("page") int page, @Query("size") int size);

    /**
     * 获取用户id
     *
     * @param qrString
     * @return
     */
    @POST("isport/concumer-basic/basic/customer/{qrString}")
    Observable<BaseResponse<String>> getUserIdByQrString(@Path("qrString") String qrString);

    //社区动态搜索
    //http://192.168.10.203:8767/isport/concumer-basic/dynamicIinfo/community
    @POST("isport/concumer-basic/dynamicIinfo/community")
    Observable<BaseResponse<List<DynamBean>>> getCommunityDynamic(@Body RequestBody body);

    ///dynamicIinfo/homePage
    //app:个人主页动态信息分页查询,page(1、2…),默认返回12条数据;videoType:2喜欢,3作品
    //http://192.168.10.203:8767/isport/concumer-basic/dynamicIinfo/homePage
    @POST("isport/concumer-basic/dynamicIinfo/homePage")
    Observable<BaseResponse<ListData<DynamBean>>> getHomePageDynamic(@Body RequestBody body);


    //http://192.168.10.203:8767/isport/concumer-basic/dynamicPraise
    //dynamicPraise
    //app:新增或删除点赞
    @POST("isport/concumer-basic/dynamicPraise")
    Observable<BaseResponse<ResultLikeBean>> optionDynamicPraise(@Body RequestBody body);


    //http://192.168.10.203:8767/isport/concumer-basic/dynamicIinfo/accuse/121/1
    //举报接口
    @POST("isport/concumer-basic/dynamicIinfo/accuse/{dynamicId}/{reportType}")
    Observable<BaseResponse<Integer>> optionReportDynamic(@Path("dynamicId") String dynamicId, @Path("reportType") int reportType);

    //删除自己的动态
    //http://192.168.10.203:8767/isport/concumer-basic/dynamicIinfo/11
    @POST("isport/concumer-basic/dynamicIinfo/{dynamicId}")
    Observable<BaseResponse<Boolean>> deletDynamic(@Path("dynamicId") String dynamicId);

    //新增动态
    //http://192.168.10.203:8767/isport/concumer-basic/dynamicIinfo
    @POST("isport/concumer-basic/dynamicIinfo")
    Observable<BaseResponse<String>> addNewDynamic(@Body RequestBody body);

    //启用停用排行榜
    @POST("isport/concumer-basic/friend/enableRanking")
    Observable<BaseResponse<Boolean>> enableRank(@Body Map map);

    //启用停用跳绳排行榜
    @POST("isport/concumer-basic/ropeRankings")
    Observable<BaseResponse<Boolean>> ropeEnableRank(@Body Map map);

    //新增或取消排行点赞
    @POST("isport/concumer-basic/friend/rankingPraiseNums")
    Observable<BaseResponse<PraiseInfo>> rankPraise(@Body Map map);

    //新增或取消排行点赞
    @POST("isport/concumer-basic/friend/friendRanking")
    Observable<BaseResponse<List<RankInfo>>> getRankInfo(@Body Map map);

    //新增或取消排行点赞https://test.api.mini-banana.com/isport/concumer-basic/ropeRankings?day=2020-09-22&type=1&userId=317
    @GET("isport/concumer-basic/ropeRankings")
    Observable<BaseResponse<List<RankInfo>>> getRopeRankInfo(@Query("day") String day, @Query("type") String type, @Query("userId") String userId);


    //评论相关的接口


    //评论 点赞和取消点赞
    //https://test.api.mini-banana.com/isport/concumer-basic/commentPraise

    @POST("isport/concumer-basic/commentPraise")
    Observable<BaseResponse<ResultLikeBean>> liketoReport(@Body Map map);

    //新增评论
    //https://test.api.mini-banana.com/isport/concumer-basic/dynamicComment
    @POST("isport/concumer-basic/dynamicComment")
    Observable<BaseResponse<String>> addDynamicComment(@Body Map map);

    //删除评论
    //https://test.api.mini-banana.com/isport/concumer-basic/dynamicComment/111
    @POST("isport/concumer-basic/dynamicComment/{dynamicCommentId}")
    Observable<BaseResponse<CommendDelBean>> delDynamicComment(@Path("dynamicCommentId") String dynamicCommentId);

    //获取主评论
    //https://test.api.mini-banana.com/isport/concumer-basic/dynamicComment/commentPage?dynamicCommentId=11&dynamicId=11&meUserId=11&page=1&size=1
    @POST("isport/concumer-basic/dynamicComment/commentPage")
    Observable<BaseResponse<ResultCommentBean<ListDataCommend>>> getDynamicCommentCommentPage(
            @Body Map map);

    //获取子评论
    //https://test.api.mini-banana.com/isport/concumer-basic/dynamicComment/replyPage?dynamicCommentId=1&dynamicId=1&meUserId=1&page=1&size=1
    @POST("isport/concumer-basic/dynamicComment/replyPage")
    Observable<BaseResponse<ResultCommentBean<ListDataCommend>>> getDynamicCommentReplyPage(@Body Map map);
}
