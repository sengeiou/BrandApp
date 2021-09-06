package com.isport.brandapp.device.bracelet.braceletModel;

import com.isport.blelibrary.db.action.W81Device.DeviceMeasuredDActionation;
import com.isport.blelibrary.db.action.W81Device.W81DeviceEexerciseAction;
import com.isport.blelibrary.db.table.w526.Device_TempTable;
import com.isport.blelibrary.db.table.w811w814.BloodPressureMode;
import com.isport.blelibrary.db.table.w811w814.OneceHrMode;
import com.isport.blelibrary.db.table.w811w814.OxygenMode;
import com.isport.blelibrary.db.table.w811w814.W81DeviceExerciseData;

import java.util.List;

public class DeviceMeasureDataImp implements IDeviceMeasureData {

    W81DeviceEexerciseAction w81DeviceEexerciseAction;
    DeviceMeasuredDActionation deviceMeasuredDActionation;

    public DeviceMeasureDataImp() {
        w81DeviceEexerciseAction = new W81DeviceEexerciseAction();
        deviceMeasuredDActionation = new DeviceMeasuredDActionation();
    }

    @Override
    public boolean saveOxygenData(String deviceId, String userId, int bloodOxygen, long timestamp, String reportid) {
        long id = DeviceMeasuredDActionation.saveOrUpdateOxygenData(deviceId, userId, bloodOxygen, timestamp, reportid);
        if (id > 0) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean saveBloodPressureData(final String deviceId, final String userId, final int sbp, final int dbp, final long timestamp, final String reportid) {
        long id = deviceMeasuredDActionation.saveOrUpdateBloodPressureData(deviceId, userId, sbp, dbp, timestamp, reportid);
        if (id > 0) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public List<OxygenMode> uploadingOxyenData(String deviceId, String userid, String noUpdateDefValue) {
        return deviceMeasuredDActionation.findNoUpgradeOxygenData(deviceId, userid, noUpdateDefValue);
    }

    @Override
    public List<BloodPressureMode> uploadingBloodPressureData(String deviceId, String userid, String noUpdateDefValue) {
        return deviceMeasuredDActionation.findNoUpgradeBloodPressureData(deviceId, userid, noUpdateDefValue);
    }

    @Override
    public List<W81DeviceExerciseData> uploadingExerciseData(String deviceId, String userid, String noUpdateDefValue) {

        return w81DeviceEexerciseAction.getNoUpgradeDevicesaveDeviceExerciseData(deviceId, userid, noUpdateDefValue);
    }

    @Override
    public List<OneceHrMode> uploadingOnceHrData(String deviceId, String userid, String noUpdateDefValue) {
        return deviceMeasuredDActionation.findNoUpgradeOneceHrModeData(deviceId, userid, noUpdateDefValue);
    }

    @Override
    public List<Device_TempTable> uploadingTempData(String deviceId, String userid, String noUpdateDefValue) {
        return deviceMeasuredDActionation.findNoUpgradeTempValueData(deviceId, userid, noUpdateDefValue);
    }
}
