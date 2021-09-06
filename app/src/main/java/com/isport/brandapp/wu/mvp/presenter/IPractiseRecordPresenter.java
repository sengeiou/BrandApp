package com.isport.brandapp.wu.mvp.presenter;

import com.isport.brandapp.wu.bean.PractiseRecordInfo;

import java.util.List;

public interface IPractiseRecordPresenter {

   void getPractiseRecordData(int deviceTyp,int type,int page);
   void getTotalPractiseTimes(int deviceTyp,int type);
    void getPractiseRecordDataSuccess(List<PractiseRecordInfo> infos);
}
