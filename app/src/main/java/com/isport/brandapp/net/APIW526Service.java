package com.isport.brandapp.net;

import com.isport.brandapp.wu.bean.BPInfo;
import com.isport.brandapp.wu.bean.OnceHrInfo;
import com.isport.brandapp.wu.bean.OxyInfo;
import com.isport.brandapp.wu.bean.PractiseRecordInfo;
import com.isport.brandapp.wu.bean.PractiseTimesInfo;
import com.isport.brandapp.wu.bean.TempInfo;

import java.util.List;

import brandapp.isport.com.basicres.commonbean.BaseResponse;
import io.reactivex.Observable;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface APIW526Service {

    //上传锻炼数据
    @POST("wristband526/training/insertSelective")
    Observable<BaseResponse<Integer>> upgradeExercise(@Body RequestBody body);

    @POST("wristband526/training/selectAllMotionTime")
    Observable<BaseResponse<Integer>> getTodayExerciseAllTime(@Query("userId") String useId,
                                                              @Query("time") String time,
                                                              @Query("deviceId") String deviceId);

    @POST("wristband526/training/selectTotalByUserId")
    Observable<BaseResponse<PractiseTimesInfo>> getTotalPractiseTimes(@Query("deviceId") String deviceId,
                                                                      @Query("userId") String useId,
                                                                      @Query("type") String type
    );

    @POST("wristband526/training/selectDatePage")
    Observable<BaseResponse<List<PractiseRecordInfo>>> getSportRecord(
            @Query("deviceId") String deviceId,
            @Query("userId") String userId,
            @Query("type") String type,
            @Query("offSet") String offSet);

    //https://api.mini-banana.com/isport/concumer-wristband/wristband526/training/selectByPrimaryKey?wristbandTrainingId=23
    //查询手环训练数据
    @POST("wristband526/training/selectAllMotionTime")
    Observable<BaseResponse<Integer>> getselectByPrimaryKey(@Query("wristbandTrainingId") String wristbandTrainingId);


    //http://192.168.10.203:8767/isport/concumer-wristband/wristband/WristbandTemperature/insertSelective
    //新增体温数据
    @POST("wristband/WristbandTemperature/insertSelective")
    Observable<BaseResponse<Integer>> insertTmepData(@Body RequestBody body);

    //http://192.168.10.203:8767/isport/concumer-wristband/wristband/WristbandTemperature/selectByPrimaryKey
    //根据ID查询详情数据

    //查询体温数据的条数
    //http://192.168.10.203:8767/isport/concumer-wristband/wristband/WristbandTemperature/selectDateByUserId?deviceId=W557DA9B0F6AF999&num=1&userId=137
    @POST("wristband/WristbandTemperature/selectDateByUserId")
    Observable<BaseResponse<List<TempInfo>>> getNumTempData(@Query("userId") String userId,
                                                            @Query("deviceId") String deviceId,
                                                            @Query("num") String num);

    //分页查询体温数据 偏移量0开始
    //http://192.168.10.203:8767/isport/concumer-wristband/wristband/WristbandTemperature/selectDataByDateList?deviceId=1&offSet=1&userId=1
    //http://192.168.10.203:8767/isport/concumer-wristband/wristband/WristbandTemperature/selectDataByDateList?deviceId=W557DA9B0F6AF999&offSet=0&userId=137

    @POST("wristband/WristbandTemperature/selectDataByDateList")
    Observable<BaseResponse<List<TempInfo>>> getPageTempData(@Query("userId") String userId,
                                                             @Query("deviceId") String deviceId,
                                                             @Query("offSet") String offset);


    //POST /wristband812/bloodOxygen/insertSelective
    @POST("wristband/bloodOxygen/insertSelective")
    Observable<BaseResponse<Integer>> upgradeOxygenData(@Body RequestBody body);

    @POST("wristband/bloodPressure/insertSelective")
    Observable<BaseResponse<Integer>> upgradeBloodPressureData(@Body RequestBody body);


    @POST("wristband/bloodPressure/selectDateByUserId")
    Observable<BaseResponse<List<BPInfo>>> getNumBloodPressureData(@Query("userId") String userId,
                                                                   @Query("deviceId") String deviceId,
                                                                   @Query("num") String num);

    @POST("wristband/bloodPressure/selectDataByDateList")
    Observable<BaseResponse<List<BPInfo>>> getPageNumBloodPressureData(@Query("userId") String userId,
                                                                       @Query("deviceId") String deviceId,
                                                                       @Query("offSet") String offSet);
    //拉取指定指定天数的血氧数据

    @POST("wristband/bloodOxygen/selectDateByUserId")
    Observable<BaseResponse<List<OxyInfo>>> getNumbloodOxygenData(@Query("userId") String userId,
                                                                  @Query("deviceId") String deviceId,
                                                                  @Query("num") String num);
    //分页查询血氧数据
    //POST /wristband/bloodPressure/selectDataByDateList


    @POST("wristband/bloodOxygen/selectDataByDateList")
    Observable<BaseResponse<List<OxyInfo>>> getPageNumbloodOxygenData(@Query("userId") String userId,
                                                                      @Query("deviceId") String deviceId,
                                                                      @Query("offSet") String offSet);

    //isport/concumer-wristband/wristband/singleHeartRate/insertSelective

    @POST("wristband/singleHeartRate/selectDateByUserId")
    Observable<BaseResponse<List<OnceHrInfo>>> getNumOnceHrData(@Query("userId") String userId,
                                                                @Query("deviceId") String deviceId,
                                                                @Query("num") String num);
    @POST("wristband/singleHeartRate/insertSelective")
    Observable<BaseResponse<Integer>> upgradeOnceHRDataData(@Body RequestBody body);

    @POST("wristband/singleHeartRate/selectDataByDateList")
    Observable<BaseResponse<List<OnceHrInfo>>> getPageNumOnceHrData(@Query("userId") String userId,
                                                                    @Query("deviceId") String deviceId,
                                                                    @Query("offSet") String offSet);

}