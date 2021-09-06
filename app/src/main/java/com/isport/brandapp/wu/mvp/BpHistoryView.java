package com.isport.brandapp.wu.mvp;

import com.isport.brandapp.wu.bean.BPInfo;

import java.util.List;

import brandapp.isport.com.basicres.mvp.BaseView;

public interface BpHistoryView extends BaseView {
    void getBpHistorySuccess(List<BPInfo> info);
}
