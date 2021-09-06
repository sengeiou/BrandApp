package com.jkcq.train.callback

import android.util.Log
import brandapp.isport.com.basicres.BaseApp
import brandapp.isport.com.basicres.commonnet.interceptor.BaseObserver
import brandapp.isport.com.basicres.commonnet.interceptor.ExceptionHandle.ResponeThrowable
import brandapp.isport.com.basicres.commonutil.TokenUtil
import com.jkcq.train.http.TrainRetrofitHelper
import com.jkcq.train.http.bean.TrainCourseBean
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.util.*

class TrainFinishPresenter {

    private lateinit var mView: TrainFinishView

    constructor() {}
    constructor(view: TrainFinishView) {
        mView = view
    }


    /**
     *先请求完成训练接口，再请求课程信息
     * type 1 中途退出   2完成
     */
    fun getTrainCourseInfo(courseId: String, type: String) {
        val userId = TokenUtil.getInstance().getPeopleIdInt(BaseApp.getApp())
        val map = HashMap<String, String>()
        map["onlineCourseId"] = courseId
        map["userId"] = userId
        map["type"] = type
        TrainRetrofitHelper.getService().postcompleteNotFeedback(map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { response ->
                    Log.e("testt", "response=" + response.code)
                    if (response.code == 2000) {
                        getRealTrainCourseInfo(userId, courseId)
                    }
                }
    }

    /**
     * 获取课程信息
     */
    fun getRealTrainCourseInfo(userId: String, onCourseId: String) {
        TrainRetrofitHelper.getService().getCourseInfo(userId, onCourseId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { response ->
                    Log.e("testt", "responsereal=" + response.code)
                    response.data?.let { mView.onGetCourseInfoSuccess(it) }
                }
    }


    /**
     * 完成课程反馈
     */
    fun postCompleteCourse(courseId: String, type: String, courseInfo: TrainCourseBean) {
        val userId = TokenUtil.getInstance().getPeopleIdInt(BaseApp.getApp())
        val map = HashMap<String, String>()
        map["onlineCoursesId"] = courseId
        map["userId"] = userId
        map["type"] = type
        map["size"] = "" + courseInfo.size
        map["page"] = "" + courseInfo.page
//        map["uid"]=courseInfo.uid
        TrainRetrofitHelper.getService().postcompleteFeedback(map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { response ->
                    mView.onFeedbackSuccess()
                }
    }


    /**
     * 退出课程反馈
     */
    fun quitCourse(courseId: String, type: String, courseInfo: TrainCourseBean) {
        val userId = TokenUtil.getInstance().getPeopleIdInt(BaseApp.getApp())
        val map = HashMap<String, String>()

        map["onlineCoursesId"] = courseId
        map["userId"] = userId
        map["type"] = type
        map["size"] = "" + courseInfo.size
        map["page"] = "" + courseInfo.page
//        map["uid"]=courseInfo.uid
        TrainRetrofitHelper.getService().postQuitFeedback(map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { response ->
                    if (response.code == 2000) {
//                        mView.onFeedbackSuccess()
                    }
                }
    }
}