package com.isport.brandapp.device.bracelet.braceletModel;

import com.isport.blelibrary.db.table.w526.Device_TempTable;
import com.isport.blelibrary.db.table.w811w814.BloodPressureMode;
import com.isport.blelibrary.db.table.w811w814.OneceHrMode;
import com.isport.blelibrary.db.table.w811w814.OxygenMode;
import com.isport.blelibrary.db.table.w811w814.W81DeviceExerciseData;

import java.util.List;

public interface IDeviceMeasureData {




    public boolean saveOxygenData(String devcieId, String userId, int bloodOxygen, long timestamp, String reportid);

    public boolean saveBloodPressureData(final String deviceId, final String userId, final int sbp, final int dbp, final long timestamp, final String reportid);

    public List<OxygenMode> uploadingOxyenData(String deviceId, String userid, String noUpdateDefValue);


    public List<BloodPressureMode> uploadingBloodPressureData(String deviceId, String userid, String noUpdateDefValue);


    public List<W81DeviceExerciseData> uploadingExerciseData(String deviceId, String userid, String noUpdateDefValue);
    public List<OneceHrMode> uploadingOnceHrData(String deviceId, String userid, String noUpdateDefValue);

    public List<Device_TempTable> uploadingTempData(String deviceId, String userid, String noUpdateDefValue);

}
