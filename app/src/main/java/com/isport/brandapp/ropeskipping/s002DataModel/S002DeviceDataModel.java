package com.isport.brandapp.ropeskipping.s002DataModel;

import com.isport.blelibrary.db.parse.RopeDetail;

import java.util.List;

public interface S002DeviceDataModel {


    /**
     * 获取所有未上传的数据
     */
    List<RopeDetail> getAllNoUpgradeS002DeviceDetailData(String deviceId, String userId, String defWriId);


    void updateWriId(String deviceId, String userId, String strDate, String updateWriId);

    void udpateAll(String deviceId, String userId);


}
