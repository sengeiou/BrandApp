package com.jkcq.train.callback;

import com.jkcq.train.http.bean.TrainCourseBean;

public interface TrainFinishView {
    void onGetCourseInfoSuccess(TrainCourseBean courseInfo);
    void onFeedbackSuccess();
}
