package com.isport.brandapp.device.scale.view;

import com.isport.blelibrary.db.table.scale.Scale_FourElectrode_DataModel;
import com.isport.brandapp.device.UpdateSuccessBean;

import brandapp.isport.com.basicres.mvp.BaseView;

/**
 * @Author
 * @Date 2018/10/23
 * @Fuction
 */

public interface UpdateReportView extends BaseView{
    void updateSuccess(UpdateSuccessBean bean,Scale_FourElectrode_DataModel scale_fourElectrode_dataModel);
    void saveUserBaseInfoSuccess();
}
