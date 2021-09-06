package com.isport.brandapp.ropeskipping.s002DataModel;

import com.isport.blelibrary.db.action.s002.S002_DetailDataModelAction;
import com.isport.blelibrary.db.parse.RopeDetail;
import com.isport.blelibrary.db.table.s002.S002_Detail_Data;
import com.isport.blelibrary.utils.Logger;

import java.util.ArrayList;
import java.util.List;

public class IS002DeviceDataModel implements S002DeviceDataModel {
    S002_DetailDataModelAction action;

    public IS002DeviceDataModel() {
        action = new S002_DetailDataModelAction();
    }

    @Override
    public List<RopeDetail> getAllNoUpgradeS002DeviceDetailData(String deviceId, String userId, String defWriId) {

        List<S002_Detail_Data> list = action.findNoUpgradeRopeData(deviceId, userId, 0);
        if (list == null) {
            list = new ArrayList<>();
        }
        List<RopeDetail> ropeDetails = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            RopeDetail bean = new RopeDetail();
            S002_Detail_Data data = list.get(i);
            bean.setAverageFrequency(data.getAverageFrequency());
            bean.setAverageHeartRate(data.getAverageHeartRate());
            bean.setUserId(data.getUserId());
            bean.setDeviceId(data.getDeviceId());
            bean.setEndTime(data.getEndTime());
            bean.setStartTime(data.getStartTime());
            bean.setExerciseType(data.getExerciseType());
            bean.setMaxFrequency(data.getMaxFrequency());
            bean.setSingleMaxSkippingNum(data.getSingleMaxSkippingNum());
            bean.setHeartRateDetailArray(data.getHeartRateDetailArray());
            bean.setFrequencyArray(data.getFrequencyArray());
            bean.setSkippingDuration(data.getSkippingDuration());
            bean.setStumbleArray(data.getStumbleArray());
            bean.setStumbleNum(data.getStumbleNum());
            bean.setEndTime(data.getEndTime());
            bean.setTotalCalories(data.getTotalCalories());
            bean.setSkippingNum(data.getSkippingNum());
            ropeDetails.add(bean);

            Logger.myLog("getAllNoUpgradeS002DeviceDetailData:" + bean);

        }
        return ropeDetails;
    }

    @Override
    public void updateWriId(String deviceId, String userId, String strDate, String updateWriId) {


    }

    @Override
    public void udpateAll(String deviceId, String userId) {

        action.updateAll(deviceId, userId);
    }
}
