package com.isport.brandapp.wu.mvp.presenter;

import com.isport.brandapp.wu.bean.BPInfo;

import java.util.List;

public interface IBpHistoryPresenter {

   void getBpHistoryData(int page);
   void getBpNumData();
    void getBpHistoryDataSuccess(List<BPInfo> info);
}
