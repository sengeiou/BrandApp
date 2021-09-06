package com.isport.brandapp.net;


import com.isport.brandapp.device.UpdateSuccessBean;
import com.isport.brandapp.device.sleep.bean.UpdateSleepReportBean;
import com.isport.brandapp.device.watch.bean.WatchHistoryNList;
import com.isport.brandapp.wu.bean.BPInfo;
import com.isport.brandapp.wu.bean.OnceHrInfo;
import com.isport.brandapp.wu.bean.OxyInfo;
import com.isport.brandapp.wu.bean.PractiseRecordInfo;
import com.isport.brandapp.wu.bean.PractiseTimesInfo;

import java.util.List;

import brandapp.isport.com.basicres.commonbean.BaseResponse;
import io.reactivex.Observable;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * @author azheng
 * @date 2018/4/24.
 * GitHub：https://github.com/RookieExaminer
 * Email：wei.azheng@foxmail.com
 * Description：
 */
public interface APIW81Service {



    //POST /wristband812/bloodOxygen/insertSelective
    @POST("wristband812/singleHeartRate/insertSelective")
    Observable<BaseResponse<Integer>> upgradeOnceHRDataData(@Body RequestBody body);
    //POST /wristband812/bloodOxygen/insertSelective
    @POST("wristband812/bloodOxygen/insertSelective")
    Observable<BaseResponse<Integer>> upgradeOxygenData(@Body RequestBody body);

    @POST("wristband812/bloodPressure/insertSelective")
    Observable<BaseResponse<Integer>> upgradeBloodPressureData(@Body RequestBody body);

    //W81系列上传数据
    //POST /wristband812/wristbandSportDetail/insertSelective
    @POST("wristband812/wristbandSportDetail/insertSelective")
    Observable<BaseResponse<UpdateSuccessBean>> upgradeW81DeviceDetailData(@Body RequestBody body);


    //拉取指定指定天数的血压数据
    //https://api.mini-banana.com/isport/concumer-client/wristband812/bloodPressure/selectDateByUserId?userId=100&deviceId=15252&num=1

    @POST("wristband812/bloodPressure/selectDateByUserId")
    Observable<BaseResponse<List<BPInfo>>> getNumBloodPressureData(@Query("userId") String userId,
                                                                   @Query("deviceId") String deviceId,
                                                                   @Query("num") String num);

    @POST("wristband812/singleHeartRate/selectDateByUserId")
    Observable<BaseResponse<List<OnceHrInfo>>> getNumOnceHrData(@Query("userId") String userId,
                                                                @Query("deviceId") String deviceId,
                                                                @Query("num") String num);

    //分页查询血压数据
    //POST /wristband812/bloodPressure/selectDataByDateList

    // https://api.mini-banana.com/isport/concumer-client/wristband812/bloodPressure/selectDataByDateList?userId=100&deviceId=56&offSet=41

    @POST("wristband812/bloodPressure/selectDataByDateList")
    Observable<BaseResponse<List<BPInfo>>> getPageNumBloodPressureData(@Query("userId") String userId,
                                                                       @Query("deviceId") String deviceId,
                                                                       @Query("offSet") String offSet);

    @POST("wristband812/singleHeartRate/selectDataByDateList")
    Observable<BaseResponse<List<OnceHrInfo>>> getPageNumOnceHrData(@Query("userId") String userId,
                                                                    @Query("deviceId") String deviceId,
                                                                    @Query("offSet") String offSet);
    //分页查询血氧数据
    //POST /wristband812/bloodPressure/selectDataByDateList

    // https://api.mini-banana.com/isport/concumer-client/wristband812/bloodPressure/selectDataByDateList?userId=100&deviceId=56&offSet=41

    @POST("wristband812/bloodOxygen/selectDataByDateList")
    Observable<BaseResponse<List<OxyInfo>>> getPageNumbloodOxygenData(@Query("userId") String userId,
                                                                      @Query("deviceId") String deviceId,
                                                                      @Query("offSet") String offSet);


    //拉取指定指定天数的血氧数据
    //https://api.mini-banana.com/isport/concumer-client/wristband812/bloodPressure/selectDateByUserId?userId=100&deviceId=15252&num=1

    @POST("wristband812/bloodOxygen/selectDateByUserId")
    Observable<BaseResponse<List<OxyInfo>>> getNumbloodOxygenData(@Query("userId") String userId,
                                                                  @Query("deviceId") String deviceId,
                                                                  @Query("num") String num);


    @POST("wristband812/bloodOxygen/selectByPrimaryKey")
    Observable<BaseResponse<UpdateSleepReportBean>> getBloodOxygenDataByPrimaryKey(@Body RequestBody body);


    //https://api.mini-banana.com/isport/concumer-client/wristband812/wristbandSportDetail/selectByNewly?userId=100&dateNum=20&dataType=1&deviceId=2
    //(0步数，1心率，2睡眠)
    // 获取指定条数的历史数据 查询从当天开始第七天的数据
    @POST("wristband812/wristbandSportDetail/selectByNewly")
    Observable<BaseResponse<WatchHistoryNList>> getW81NumDetailData(@Query("userId") String userId,
                                                                    @Query("dateNum") String num,
                                                                    @Query("dataType") String dataType,
                                                                    @Query("deviceId") String deviceId);

    //    //https://api.mini-banana.com/isport/concumer-client/wristband812/wristbandSportDetail/selectByPageSize?userId=100&date=1&deviceId=1&dataType=1&pageNum=1&pageSize=1
    //查询最近有数据的两条数据
    @POST("wristband812/wristbandSportDetail/selectByPageSize")
    Observable<BaseResponse<WatchHistoryNList>> getW81selectByPageSize(@Query("userId") String userId,
                                                                       @Query("pageSize") String pageSize,
                                                                       @Query("pageNum") String pageNum,
                                                                       @Query("dataType") String dataType,
                                                                       @Query("deviceId") String deviceId,
                                                                       @Query("date") String date);

    //Request URL
    //time为月份第一天零点时间戳(0步数，1心率，2睡眠)
    //https://api.mini-banana.com/isport/concumer-client/wristband812/wristbandSportDetail/selectByTimestamp?userId=f&time=233&dataType=23&deviceId=33
    @POST("wristband812/wristbandSportDetail/selectByTimestamp")
    Observable<BaseResponse<WatchHistoryNList>> getW81MonthDetailData(@Query("userId") String userId,
                                                                      @Query("time") String time,
                                                                      @Query("dataType") String dataType,
                                                                      @Query("deviceId") String deviceId);


    /**
     * 查询锻炼记录数据
     *
     * @return
     */
    @POST("wristband812/training/selectDatePage")
    Observable<BaseResponse<List<PractiseRecordInfo>>> getSportRecord(
            @Query("deviceId") String deviceId,
            @Query("userId") String userId,
            @Query("type") String type,
            @Query("offSet") String offSet);

    //上传锻炼数据
    //https://api.mini-banana.com/isport/concumer-client/wristband812/training/insertSelective
    @POST("wristband812/training/insertSelective")
    Observable<BaseResponse<Integer>> upgradeExercise(@Body RequestBody body);


    //wristband812/training/selectAllMotionTime?userId=100&time=100&deviceId=100
    @POST("wristband812/training/selectAllMotionTime")
    Observable<BaseResponse<Integer>> getTodayExerciseAllTime(@Query("userId") String useId,
                                                              @Query("time") String time,
                                                              @Query("deviceId") String deviceId);

    @POST("wristband812/training/selectTotalByUserId")
    Observable<BaseResponse<PractiseTimesInfo>> getTotalPractiseTimes(@Query("deviceId") String deviceId,
                                                                      @Query("userId") String useId,
                                                                      @Query("type") String type
    );


}
