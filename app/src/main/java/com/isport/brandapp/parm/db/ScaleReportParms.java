package com.isport.brandapp.parm.db;

import com.isport.blelibrary.db.table.scale.Scale_FourElectrode_DataModel;

/**
 * @Author
 * @Date 2019/1/14
 * @Fuction
 */

public class ScaleReportParms extends BaseDbParms {
    public String deviceId;//设备id
    public Scale_FourElectrode_DataModel mScale_fourElectrode_dataModel;
    public long timeTamp;
}
