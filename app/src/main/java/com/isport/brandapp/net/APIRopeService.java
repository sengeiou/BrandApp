package com.isport.brandapp.net;


import com.isport.blelibrary.db.table.s002.DailyBrief;
import com.isport.blelibrary.db.table.s002.DailySummaries;
import com.isport.blelibrary.db.table.s002.Summary;
import com.isport.brandapp.home.bean.RopeDetailBean;

import java.util.List;

import brandapp.isport.com.basicres.commonbean.BaseResponse;
import io.reactivex.Observable;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * @author azheng
 * @date 2020/4/24.
 * GitHub：https://github.com/RookieExaminer
 * Email：wei.azheng@foxmail.com
 * Description：
 */
public interface APIRopeService {


    @POST("ropeSport/details")
    Observable<BaseResponse<String>> updateRopeSport(@Body RequestBody body);

    //https://test.api.mini-banana.com/isport/producer-course/onlineCourses/trainingHomepageUrl?userId=317
    @GET("isport/producer-course/onlineCourses/trainingHomepageUrl")
    Observable<BaseResponse<String>> getCourseUrl(@Query("userId") String userId);

    //https://test.api.mini-banana.com/isport/producer-rope/ropeSport/statistics/sportDaysInMonth?month=2020-09&userId=317

    @GET("ropeSport/statistics/sportDaysInMonth")
    Observable<BaseResponse<List<String>>> getSportDayInMonth(@Query("userId") String userId, @Query("month") String month);

    //https://test.api.mini-banana.com/isport/producer-rope/ropeSport/statistics/dailyBrief?day=2020-09-18&userId=317

    /**
     * @param day    日期（yyyy-MM-dd）
     * @param userId
     * @return
     */
    @GET("ropeSport/statistics/dailyBrief")
    Observable<BaseResponse<List<DailyBrief>>> getDailyBrief(@Query("userId") String userId, @Query("day") String day);

    /**
     * /**
     * https://test.api.mini-banana.com/isport/producer-rope/ropeSport/statistics/dailySummaries?day=2020-09-18&summaryType=WEEK&userId=317
     * /ropeSport/statistics/dailySummaries
     *
     * @param day
     * @param userId
     * @param summaryType WEEK MONTH
     * @return
     */
    @GET("ropeSport/statistics/dailySummaries")
    Observable<BaseResponse<List<DailySummaries>>> getDailySummaries(@Query("userId") String userId, @Query("day") String day, @Query("summaryType") String summaryType);

    //https://test.api.mini-banana.com/isport/producer-rope/ropeSport/statistics/summary?day=2020-09-18&summaryType=DAY&userId=317

    /**
     * @param day
     * @param userId
     * @param summaryType DAY WEEK MONTH ALL
     * @return
     */
    @GET("ropeSport/statistics/summary")
    Observable<BaseResponse<Summary>> getSummary(@Query("userId") String userId, @Query("day") String day, @Query("summaryType") String summaryType);


    /**
     * https://test.api.mini-banana.com/isport/producer-rope/ropeSport/details/url
     */
    @GET("ropeSport/details/url")
    Observable<BaseResponse<RopeDetailBean>> getDetailUrl();

    /**
     * https://test.api.mini-banana.com/isport/producer-rope/challenges/challengeUrl
     */
    @GET("challenges/challengeUrl")
    Observable<BaseResponse<String>> getChallengeUrl(@Query("userId") String userId);

    @POST("user-challenge-records")
    Observable<BaseResponse<String>> postChallengeRecords(@Body RequestBody body);

}
