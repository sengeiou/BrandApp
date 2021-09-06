package com.isport.brandapp.net;


import com.isport.brandapp.Home.bean.ResultHomeSportData;
import com.isport.brandapp.Home.bean.SportLastDataBeanList;
import com.isport.brandapp.Home.bean.http.WatchHistoryData;
import com.isport.brandapp.device.UpdateSuccessBean;
import com.isport.brandapp.device.watch.bean.WatchHistoryNList;
import com.isport.brandapp.sport.bean.IphoneSportListVo;
import com.isport.brandapp.sport.bean.IphoneSportWeekVo;
import com.isport.brandapp.sport.bean.ResultHistorySportSummarizingData;
import com.isport.brandapp.sport.bean.ResultSportHistroyList;
import com.isport.brandapp.sport.bean.SportSumData;

import brandapp.isport.com.basicres.commonbean.BaseResponse;
import io.reactivex.Observable;
import okhttp3.RequestBody;
import retrofit2.http.Body;
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
public interface APISportService {


    @POST("{url1}/{url2}/{url3}")
    Observable<BaseResponse<SportLastDataBeanList>> getLastAll(
            @Path("url1") String url1,
            @Path("url2") String url2,
            @Path("url3") String url3,
            @Query("userId") String userId);

    @POST("{url1}/{url2}/{url3}")
    Observable<BaseResponse<String>> getHomeDataSport(
            @Path("url1") String url1,
            @Path("url2") String url2,
            @Path("url3") String url3,
            @Query("userId") String userId, @Query("time") String time);

    @POST("{url1}/{url2}/{url3}")
    Observable<BaseResponse<SportSumData>> getSporSummarDataByPrimaryKey(
            @Path("url1") String url1,
            @Path("url2") String url2,
            @Path("url3") String url3,
            @Query("iphoneSportId") String userId);

    @POST("{url1}/{url2}/{url3}")
    Observable<BaseResponse<ResultHistorySportSummarizingData>> getHistorySummarizingData(
            @Path("url1") String url1,
            @Path("url2") String url2,
            @Path("url3") String url3,
            @Query("userId") String userId);

    //POST /wristband/iphoneSport/selectDateByUserId
    //查询历史的分页数据
    @POST("{url1}/{url2}/{url3}")
    Observable<BaseResponse<ResultSportHistroyList>> getHistoryData(
            @Path("url1") String url1,
            @Path("url2") String url2,
            @Path("url3") String url3,
            @Query("userId") String userId,
            @Query("offSet") String offset);


    @POST("{url1}/{url2}/{url3}")
    Observable<BaseResponse<WatchHistoryNList>> getWatchHistoryByTimeTampData(
            @Path("url1") String wristband,
            @Path("url2") String wristbandSportDetail,
            @Path("url3") String selectByTimestamp,
            @Query("userId") int userId,
            @Query("time") long time,
            @Query("dataType") int dataType);

    @POST("{url1}/{url2}/{url3}")
    Observable<BaseResponse<WatchHistoryNList>> getWatch14DayHistoryByTimeTampData(
            @Path("url1") String wristband,
            @Path("url2") String wristbandSportDetail,
            @Path("url3") String selectByTimestamp,
            @Query("userId") int userId,
            @Query("dateNum") int dateNum,
            @Query("dataType") int dataType,
            @Query("deviceId") String deviceId);

    @POST("{url1}/{url2}/{url3}")
    Observable<BaseResponse<WatchHistoryData>> getWatchPageDayHistoryByTimeTampData(
            @Path("url1") String wristband,
            @Path("url2") String wristbandSportDetail,
            @Path("url3") String selectByTimestamp,
            @Query("userId") int userId,
            @Query("date") String date,
            @Query("deviceId") String deviceId,
            @Query("dataType") int dataType,
            @Query("pageNum") int pageNum,
            @Query("pageSize") int pageSize);

    //查询详情数据

    @POST("{url1}/{url2}/{url3}")
    Observable<BaseResponse<UpdateSuccessBean>> insertSportSummer(
            @Path("url1") String url1,
            @Path("url2") String url2,
            @Path("url3") String url3,
            @Body RequestBody maps);

    @POST("{url1}/{url2}/{url3}")
    Observable<BaseResponse<String>> insertSportDetail(
            @Path("url1") String url1,
            @Path("url2") String url2,
            @Path("url3") String url3,
            @Body RequestBody maps);

    @POST("{url1}/{url2}/{url3}")
    Observable<BaseResponse<UpdateSuccessBean>> insertSportHistoryDetail(
            @Path("url1") String url1,
            @Path("url2") String url2,
            @Path("url3") String url3,
            @Body RequestBody maps);

    //POST /wristband/iphoneSport/selectDateByUserId
    //查询月和周的概要数据
    @POST("{url1}/{url2}/{url3}")
    Observable<BaseResponse<IphoneSportListVo>> getSportHistorySum(
            @Path("url1") String url1,
            @Path("url2") String url2,
            @Path("url3") String url3,
            @Query("userId") String userId,
            @Query("time") String time);


    //POST /wristband/iphoneSport/selectDateByUserId
    //查询历史的不分页
    @POST("{url1}/{url2}/{url3}")
    Observable<BaseResponse<ResultSportHistroyList>> getHistoryDataWeekAndMonth(
            @Path("url1") String url1,
            @Path("url2") String url2,
            @Path("url3") String url3,
            @Query("userId") String userId,
            @Query("time") String time);
}
