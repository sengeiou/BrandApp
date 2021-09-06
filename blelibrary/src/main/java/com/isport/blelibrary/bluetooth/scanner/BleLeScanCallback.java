package com.isport.blelibrary.bluetooth.scanner;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.le.ScanCallback;
import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import android.util.Log;

import com.isport.blelibrary.utils.Logger;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;

/**
 * @author Created by Marcos Cheng on 2017/8/23.
 *
 */

public class BleLeScanCallback implements BluetoothAdapter.LeScanCallback {

    private String TAG = "BleLeScanCallback";
    private OnLeScanCallback mScanCallback;

    public BleLeScanCallback() {

    }

    @Override
    public void onLeScan(BluetoothDevice bluetoothDevice, int rssi, byte[] scanRecord) {
//        Log.e(TAG, "onLeScan");
        ScanResult result = new ScanResult(bluetoothDevice, ScanRecord.parseFromBytes(scanRecord), rssi, Calendar.getInstance().getTimeInMillis());
        if(mScanCallback != null) {
            mScanCallback.onScanResult(result);
        }
    }

    public void setScanCallback(OnLeScanCallback mScanCallback) {
        this.mScanCallback = mScanCallback;
    }

    interface OnLeScanCallback {
        void onScanResult(ScanResult result);
    }

    /**
     * @author Created by Marcos Cheng on 2017/8/23.
     */
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public static class BleScanCallback extends ScanCallback {

        private String TAG = "BleScanCallback";

        private OnScanCallback onScanCallback;

        public BleScanCallback() {

        }

        public void setOnScanCallback(OnScanCallback onScanCallback) {
            this.onScanCallback = onScanCallback;
        }

        @Override
        public void onScanResult(int callbackType, android.bluetooth.le.ScanResult result) {
            //Log.e(TAG, "onScanResult  rssi = "+result.getRssi()+" ");
            super.onScanResult(callbackType, result);
            List<android.bluetooth.le.ScanResult> list = new ArrayList<>();
            if(onScanCallback != null) {
                list.add(result);
                onBatchScanResults(list);
            }
        }

        @Override
        public void onBatchScanResults(List<android.bluetooth.le.ScanResult> results) {
            super.onBatchScanResults(results);
            if(results != null && results.size()>0) {
                ArrayList<ScanResult> list = new ArrayList<>();
                for (int i = 0; i < results.size(); i++) {
                    android.bluetooth.le.ScanResult scanResult = results.get(i);
                    Log.e(TAG, "onScanResult  rssi = "+scanResult.getRssi());
                    String dname = scanResult.getScanRecord().getDeviceName() ;
                    if(dname != null)
                        dname = dname.trim();
                    list.add(new ScanResult(scanResult.getDevice(), ScanRecord.parseFromBytes(scanResult.getScanRecord().getBytes()),
                                            scanResult.getRssi(), scanResult.getTimestampNanos()));
                }
                Logger.myLog("扫描返回000");
                if(onScanCallback != null) {
                    onScanCallback.onBatchScanResults(list);
                }
            }

        }

        @Override
        public void onScanFailed(int errorCode) {
            super.onScanFailed(errorCode);
            if(onScanCallback != null) {
                onScanCallback.onScanFailed(errorCode);
            }
        }

        public interface OnScanCallback {


            void onBatchScanResults(ArrayList<ScanResult> results);
            void onScanFailed(int errorCode);
        }


    }

    /**
     * @author Created by Marcos Cheng on 2017/8/23.
     *
     */

    public static class ScanResult implements Parcelable {

        // Remote bluetooth device.
        private BluetoothDevice mDevice;

        // Scan record, including advertising data and scan response data.
        @Nullable
        private ScanRecord mScanRecord;

        // Received signal strength.
        private int mRssi;

        // Device timestamp when the result was last seen.
        private long mTimestampNanos;

        public void setRssi(int rssi) {
            this.mRssi = rssi;
        }

        /**
         * Constructor of scan result.
         *
         * @param device Remote bluetooth device that is found.
         * @param scanRecord Scan record including both advertising data and scan response data.
         * @param rssi Received signal strength.
         * @param timestampNanos Device timestamp when the scan result was observed.
         */
        public ScanResult(BluetoothDevice device, ScanRecord scanRecord, int rssi,
                          long timestampNanos) {
            mDevice = device;
            mScanRecord = scanRecord;
            mRssi = rssi;
            mTimestampNanos = timestampNanos;
        }

        private ScanResult(Parcel in) {
            readFromParcel(in);
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            if (mDevice != null) {
                dest.writeInt(1);
                mDevice.writeToParcel(dest, flags);
            } else {
                dest.writeInt(0);
            }
            if (mScanRecord != null) {
                dest.writeInt(1);
                dest.writeByteArray(mScanRecord.getBytes());
            } else {
                dest.writeInt(0);
            }
            dest.writeInt(mRssi);
            dest.writeLong(mTimestampNanos);
        }

        private void readFromParcel(Parcel in) {
            if (in.readInt() == 1) {
                mDevice = BluetoothDevice.CREATOR.createFromParcel(in);
            }
            if (in.readInt() == 1) {
                mScanRecord = ScanRecord.parseFromBytes(in.createByteArray());
            }
            mRssi = in.readInt();
            mTimestampNanos = in.readLong();
        }

        @Override
        public int describeContents() {
            return 0;
        }

        /**
         * Returns the remote bluetooth device identified by the bluetooth device address.
         */
        public BluetoothDevice getDevice() {
            return mDevice;
        }

        /**
         * Returns the scan record, which is a combination of advertisement and scan response.
         */
        @Nullable
        public ScanRecord getScanRecord() {
            return mScanRecord;
        }

        /**
         * Returns the received signal strength in dBm. The valid range is [-127, 127].
         */
        public int getRssi() {
            return mRssi;
        }

        /**
         * Returns timestamp since boot when the scan record was observed.
         */
        public long getTimestampNanos() {
            return mTimestampNanos;
        }

        @Override
        public int hashCode() {
            return Objects.hash(mDevice, mRssi, mScanRecord, mTimestampNanos);
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null || getClass() != obj.getClass()) {
                return false;
            }
            ScanResult other = (ScanResult) obj;
            return Objects.equals(mDevice, other.mDevice) && (mRssi == other.mRssi) &&
                    Objects.equals(mScanRecord, other.mScanRecord)
                    && (mTimestampNanos == other.mTimestampNanos);
        }

        @Override
        public String toString() {
            return "ScanResult{" + "mDevice=" + mDevice + ", mScanRecord="
                    + Objects.toString(mScanRecord) + ", mRssi=" + mRssi + ", mTimestampNanos="
                    + mTimestampNanos + '}';
        }

        public static final Creator<ScanResult> CREATOR = new Creator<ScanResult>() {
            @Override
            public ScanResult createFromParcel(Parcel source) {
                return new ScanResult(source);
            }

            @Override
            public ScanResult[] newArray(int size) {
                return new ScanResult[size];
            }
        };
    }
}
