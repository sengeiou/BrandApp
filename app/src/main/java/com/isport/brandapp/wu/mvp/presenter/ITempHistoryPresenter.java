package com.isport.brandapp.wu.mvp.presenter;

import com.isport.brandapp.wu.bean.BPInfo;
import com.isport.brandapp.wu.bean.TempInfo;

import java.util.List;

public interface ITempHistoryPresenter {

    void getTempHistoryData(int page);

    void getTempNumData();

    void getTempUnitl(String deviceId, String userId);

    void getTempNumberHistory(String deviceId, String userId,int number);

    void getLastTempData(String deviceId, String userId);


}
