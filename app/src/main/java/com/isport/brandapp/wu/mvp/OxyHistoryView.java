package com.isport.brandapp.wu.mvp;

import com.isport.brandapp.wu.bean.OxyInfo;

import java.util.List;

import brandapp.isport.com.basicres.mvp.BaseView;

public interface OxyHistoryView extends BaseView {
    void getOxyHistoryDataSuccess(List<OxyInfo> info);
}
