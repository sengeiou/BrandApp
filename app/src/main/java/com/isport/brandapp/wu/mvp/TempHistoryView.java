package com.isport.brandapp.wu.mvp;

import com.isport.brandapp.wu.bean.BPInfo;
import com.isport.brandapp.wu.bean.TempInfo;

import java.util.List;

import brandapp.isport.com.basicres.mvp.BaseView;

public interface TempHistoryView extends BaseView {
    void getTempHistorySuccess(List<TempInfo> info);
    void getLastTempSuccess(TempInfo info);
    void getTempUtil(String unitl);

}
