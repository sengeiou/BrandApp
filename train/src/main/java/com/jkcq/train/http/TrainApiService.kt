package com.jkcq.train.http

import brandapp.isport.com.basicres.commonbean.BaseResponse
import com.jkcq.train.http.bean.TrainCourseBean
import io.reactivex.Observable
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface TrainApiService {

    /**
     * 完成课程，中途退出或者完成课程
     * @param map
     * @return
     */
    @POST("/isport/producer-course/onlineCourses/end")
    fun postcompleteNotFeedback(@Body map: Map<String, String>): Observable<BaseResponse<String>>

    /**
     * 完成训练反馈
     * @param map
     * @return
     */
    @POST("/isport/producer-course/onlineCourses/completeFeedback")
    fun postcompleteFeedback(@Body map: Map<String, String>): Observable<BaseResponse<String>>

    /**
     * 退出反馈
     */
    @POST("/isport/producer-course/onlineCourses/quitFeedback")
    fun postQuitFeedback(@Body map: Map<String, String>): Observable<BaseResponse<String>>


    @GET("/isport/producer-course/onlineCourses/app/{userId}/{onCourseId}")
    fun getCourseInfo(@Path("userId") userId: String, @Path("onCourseId") onCourseId: String): Observable<BaseResponse<TrainCourseBean>>
}