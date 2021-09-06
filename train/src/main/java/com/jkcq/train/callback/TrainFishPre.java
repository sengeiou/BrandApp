package com.jkcq.train.callback;

import com.jkcq.train.http.TrainRetrofitHelper;
import com.jkcq.train.http.bean.TrainCourseBean;

import java.util.HashMap;

import brandapp.isport.com.basicres.BaseApp;
import brandapp.isport.com.basicres.commonbean.BaseResponse;
import brandapp.isport.com.basicres.commonnet.interceptor.BaseObserver;
import brandapp.isport.com.basicres.commonnet.interceptor.ExceptionHandle;
import brandapp.isport.com.basicres.commonutil.TokenUtil;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class TrainFishPre {
    TrainFinishView view;

    public TrainFishPre(TrainFinishView view) {
        this.view = view;
    }

    public TrainFishPre() {

    }

    public void getTrainCourseInfo(final String courseId, String type) {
        final String userId = TokenUtil.getInstance().getPeopleIdInt(BaseApp.getApp());
        HashMap map = new HashMap<String, String>();
        map.put("onlineCourseId", courseId);
        map.put("userId", userId);
        map.put("type", type);
        TrainRetrofitHelper.getService().postcompleteNotFeedback(map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<BaseResponse>() {
                               @Override
                               public void onNext(BaseResponse baseResponse) {
                                   if (baseResponse.getCode() == 2000) {
                                       getRealTrainCourseInfo(userId, courseId);
                                   }
                               }

                               @Override
                               protected void hideDialog() {

                               }

                               @Override
                               protected void showDialog() {

                               }

                               @Override
                               public void onError(ExceptionHandle.ResponeThrowable e) {

                               }


                           }
                );
    }


    /**
     * 获取课程信息
     */
    void getRealTrainCourseInfo(String userId, String onCourseId) {
        TrainRetrofitHelper.getService().getCourseInfo(userId, onCourseId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<BaseResponse>() {
                    @Override
                    public void onNext(BaseResponse baseResponse) {
                        view.onGetCourseInfoSuccess((TrainCourseBean) baseResponse.getData());
                    }

                    @Override
                    protected void hideDialog() {

                    }

                    @Override
                    protected void showDialog() {

                    }

                    @Override
                    public void onError(ExceptionHandle.ResponeThrowable e) {

                    }
                });
    }

    /**
     * 完成课程反馈
     */
    public void postCompleteCourse(String courseId, String type, TrainCourseBean courseInfo) {
        String userId = TokenUtil.getInstance().getPeopleIdInt(BaseApp.getApp());
        HashMap map = new HashMap<String, Object>();
        map.put("onlineCoursesId", courseId);
        map.put("userId", userId);
        map.put("type", type);
        map.put("size", courseInfo.getSize());
        map.put("page", +courseInfo.getPage());
//        map["uid"]=courseInfo.uid
        TrainRetrofitHelper.getService().postcompleteFeedback(map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver() {
                    @Override
                    protected void hideDialog() {

                    }

                    @Override
                    protected void showDialog() {

                    }

                    @Override
                    public void onError(ExceptionHandle.ResponeThrowable e) {

                    }

                    @Override
                    public void onNext(Object o) {
                        view.onFeedbackSuccess();
                    }
                });


    }

    /**
     * 退出课程反馈
     */
    public void quitCourse(String courseId, String type, TrainCourseBean courseInfo) {
        String userId = TokenUtil.getInstance().getPeopleIdInt(BaseApp.getApp());
        HashMap map = new HashMap<String, Object>();
        map.put("onlineCoursesId", courseId);
        map.put("userId", userId);
        map.put("type", type);
        map.put("size", courseInfo.getSize());
        map.put("page", courseInfo.getPage());

//        map["uid"]=courseInfo.uid
        TrainRetrofitHelper.getService().postQuitFeedback(map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver() {
                    @Override
                    protected void hideDialog() {

                    }

                    @Override
                    protected void showDialog() {

                    }

                    @Override
                    public void onError(ExceptionHandle.ResponeThrowable e) {

                    }

                    @Override
                    public void onNext(Object o) {
                        //  mView.onFeedbackSuccess()
                    }
                });
    }

}
