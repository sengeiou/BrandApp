package com.isport.brandapp.device.scale.view;

import com.isport.brandapp.device.scale.bean.ScaleReportBean;

import brandapp.isport.com.basicres.mvp.BaseView;

/**
 * @Author
 * @Date 2018/10/24
 * @Fuction
 */

public interface ScaleReportView extends BaseView {
    void getScaleReportSuccess(ScaleReportBean scaleReportBean);
}
