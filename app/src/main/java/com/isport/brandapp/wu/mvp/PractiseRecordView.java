package com.isport.brandapp.wu.mvp;

import com.isport.brandapp.wu.bean.PractiseRecordInfo;
import com.isport.brandapp.wu.bean.PractiseTimesInfo;

import java.util.List;

import brandapp.isport.com.basicres.mvp.BaseView;

public interface PractiseRecordView extends BaseView {
    void getPractiseRecordSuccess(List<PractiseRecordInfo> infos);

    void getTotalPractiseTimesSuccess(PractiseTimesInfo data);
}
