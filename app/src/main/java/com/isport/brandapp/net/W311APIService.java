package com.isport.brandapp.net;


import com.isport.brandapp.device.UpdateSuccessBean;
import com.isport.brandapp.device.watch.bean.WatchHistoryNList;

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
public interface W311APIService {


    @POST("{url1}/{url2}/{url3}")
    Observable<BaseResponse<UpdateSuccessBean>> updateBraceletDetailData(@Path("url1") String url1,
                                                                         @Path("url2") String url2,
                                                                         @Path("url3") String url3,
                                                                         @Body RequestBody body);

    @POST("{url1}/{url2}/{url3}")
    Observable<BaseResponse<WatchHistoryNList>> getWatch14DayHistoryByTimeTampData(
            @Path("url1") String wristband,
            @Path("url2") String wristbandSportDetail,
            @Path("url3") String selectByTimestamp,
            @Query("userId") int userId,
            @Query("dateNum") int dateNum,
            @Query("dataType") int dataType,
            @Query("deviceId") String deviceId);


}
