package com.isport.brandapp.wu.mvp;

import com.isport.brandapp.wu.bean.OnceHrInfo;
import com.isport.brandapp.wu.bean.OxyInfo;

import java.util.List;

import brandapp.isport.com.basicres.mvp.BaseView;

public interface OnceHrHistoryView extends BaseView {
    void getOnceHrHistoryDataSuccess(List<OnceHrInfo> info);
}
