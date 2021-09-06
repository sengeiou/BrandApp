package com.isport.brandapp.wu.mvp.presenter;

import com.isport.brandapp.wu.bean.OnceHrInfo;
import com.isport.brandapp.wu.bean.OxyInfo;

import java.util.List;

public interface IOnceHrHistoryPresenter {

   void getOnceHrHistoryData(int page);
   void getOnceHrNumData();
    void getOnceHrHistoryDataSuccess(List<OnceHrInfo> info);
}
