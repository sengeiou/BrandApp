package com.isport.brandapp.wu.mvp.presenter;

import com.isport.brandapp.wu.bean.OxyInfo;

import java.util.List;

public interface IOxyHistoryPresenter {

   void getOxyHistoryData(int page);
   void getOxyNumData();
    void getOxyHistoryDataSuccess(List<OxyInfo> info);
}
