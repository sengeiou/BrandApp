package com.isport.blelibrary.bluetooth.scanner;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.le.BluetoothLeScanner;
import android.bluetooth.le.ScanCallback;
import android.bluetooth.le.ScanSettings;
import android.content.Context;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import com.isport.blelibrary.utils.Logger;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @author Created by  Marcos Cheng on 2017/8/23.
 * <p>
 * Manage BLE Scan
 */

public class ScanManager implements BleLeScanCallback.BleScanCallback.OnScanCallback, BleLeScanCallback
        .OnLeScanCallback {

    /**
     * use the {@link BluetoothAdapter#startLeScan(BluetoothAdapter.LeScanCallback)} to scan，
     * see {@link #startJELIYScan()}
     */
    public static final int SCAN_TYPE_JELLY = 0;
    /**
     * use the {@link BluetoothAdapter#getBluetoothLeScanner()},
     * {@link BluetoothLeScanner#startScan(List, ScanSettings, ScanCallback)}to scan device
     * when use this, it need your app run on Android LOLLIPOP or up, of course you can use a compatible method
     * {@link #SCAN_TYPE_COMPATIBLE}
     * see {@link #startLOLLIPOPScan()}
     */
    public static final int SCAN_TYPE_LOLLIPOP = 1;

    /**
     * use a compatible method to scan, both SCAN_TYPE_JELIY and SCAN_TYPE_TYPE_LOLLIPOP
     * see {@link #startLeScan(boolean)}
     */
    public static final int SCAN_TYPE_COMPATIBLE = 2;

    /**
     * Fails to start scan as Ble scan with the  same settings is already started by the app
     */
    public static final int SCAN_FAILED_ALREADY_STARTED = 1;
    /**
     * Fails to start scan sa app cannot be regstered
     */
    public static final int SCAN_FAILED_APPLICATION_REGISTRATION_FAILED = 2;
    /**
     * Fails to start scan due an internal error
     */
    public static final int SCAN_FAILED_INTERNAL_ERROR = 3;
    /**
     * Fails to start power optimized scan as this feature is not supported
     */
    public static final int SCAN_FAILED_FEATURE_UNSUPPORTED = 4;


    public static ScanManager sInstance;
    private Context mContext;
    private BleLeScanCallback bleLeScanCallback;
    private BleLeScanCallback.BleScanCallback bleScanCallback;
    private static ScanHandler scanHandler;
    private long scanTime = 30000;
    private boolean isScaning = false;
    private OnScanManagerListener mScanListener;
    private ScanSettings scanSettings;
    private int scanType = SCAN_TYPE_JELLY;
    private boolean mHasTimeOut;

    HashMap<Integer, Long> scanIntervalMap = new HashMap<>();//搜索次数和搜索时时间戳,用于做7.0以及以上30s内5次后无返回的情况
    private int scanTimes;//搜索次数

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0x01:
                    startScanBle();
                    break;
            }
        }
    };

    private ScanManager(Context context) {
        BluetoothAdapter bleAdapter = BluetoothAdapter.getDefaultAdapter();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            ScanSettings.Builder settingsBuilder = new ScanSettings.Builder();
            settingsBuilder.setScanMode(ScanSettings.SCAN_MODE_LOW_LATENCY);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                settingsBuilder.setCallbackType(ScanSettings.CALLBACK_TYPE_ALL_MATCHES);
                //修复android M不能扫描到设备的bug
                try {
                    if (bleAdapter.isOffloadedScanBatchingSupported()) {
                        settingsBuilder.setReportDelay(1000);
                    }

                } catch (Exception e) {

                }


            }

            scanSettings = settingsBuilder.build();
        }
        scanTimes = 0;
        mContext = context;
        scanHandler = new ScanHandler(this, Looper.myLooper());
    }

    /**
     * get instance of ScanManager
     *
     * @param context
     * @return
     */
    public static ScanManager getInstance(Context context) {
        if (sInstance == null) {
            synchronized (ScanManager.class) {
                sInstance = new ScanManager(context.getApplicationContext());
            }
        }
        return sInstance;
    }

    /**
     * how long delay to cancel scan
     *
     * @param scanTime duration of scan
     */
    public void setScanTime(long scanTime) {
        this.scanTime = scanTime;
    }

    public void setScanListener(OnScanManagerListener listener) {
        this.mScanListener = listener;
    }

    public void setScanSettings(ScanSettings scanSettings) {
        this.scanSettings = scanSettings;
    }

    /**
     * start to scan ble device, you can use different way to scan by set scan type {@link #setScanType(int)}.
     * {@link #SCAN_TYPE_JELLY} is default.
     * As I known, use SCAN_TYPE_JELLY to scan, it's easy to connect , how ever, when you use the type that
     * {@link #SCAN_TYPE_COMPATIBLE} or {@link #SCAN_TYPE_LOLLIPOP},
     * it may be encounter error which error code is 133 before first connection success.
     * it may be that the parameters of default scansetting is not correctly, so you can call
     * {@link #setScanSettings(ScanSettings)} to set scansettings by yourself.
     *
     * @param hasTimeOut
     * @return start le scan success of fail
     */
    public boolean startLeScan(boolean hasTimeOut) {
        BluetoothAdapter bleAdapter = BluetoothAdapter.getDefaultAdapter();
        if (bleAdapter == null || !bleAdapter.isEnabled()) {
            Logger.myLog("开始扫描555");
            return false;
        }
        if (isScaning) {
            Logger.myLog("开始扫描6666");
            isScaning = false;
            cancelLeScan(false);
        }
        this.mHasTimeOut = hasTimeOut;
        startScan(hasTimeOut);
        return true;
    }

    private void startScan(boolean hasTimeOut) {
        scanTimes++;
        scanIntervalMap.put(scanTimes, System.currentTimeMillis());
        long longInterval;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            Logger.myLog("7.0以上扫描");
            if (scanTimes < 5) {
                Logger.myLog(" scanTimes <5  == " + scanTimes);
                mHandler.sendEmptyMessageDelayed(0x01, 2000);
            } else {
                longInterval = scanIntervalMap.get(scanTimes) - scanIntervalMap.get(scanTimes - 4);
                Logger.myLog(" scanTimes >=5  == " + scanTimes);
                if (longInterval < 30000) {
                    Logger.myLog("longInterval == " + longInterval + " longInterval -- " + (30000 - longInterval));
                    mHandler.sendEmptyMessageDelayed(0x01, 30000 - longInterval);
                } else {
                    mHandler.sendEmptyMessageDelayed(0x01, 2000);
                }
            }
        } else {
            Logger.myLog("7.0以下扫描");
            mHandler.sendEmptyMessageDelayed(0x01, 300);
        }
    }

    private void startScanBle() {

        Logger.myLog("startScanBle" + "scanType:" + scanType);
        // scanType = SCAN_TYPE_COMPATIBLE;
        if (scanType == SCAN_TYPE_JELLY) {
            startJELIYScan();
        } else if (scanType == SCAN_TYPE_LOLLIPOP) {
            startLOLLIPOPScan();
        } else if (scanType == SCAN_TYPE_COMPATIBLE) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                startLOLLIPOPScan();
            } else {
                startJELIYScan();
            }
        }
        isScaning = true;
        if (mHasTimeOut)
            scanHandler.sendEmptyMessageDelayed(0x01, scanTime);
    }

    /**
     * to scan device on android 4.3 and up
     */
    private void startJELIYScan() {
        if (bleLeScanCallback == null) {
            bleLeScanCallback = new BleLeScanCallback();
            bleLeScanCallback.setScanCallback(this);
        }
        Logger.myLog("开始扫描777 startJELIYScan");
        BluetoothAdapter bleAdapter = BluetoothAdapter.getDefaultAdapter();
        bleAdapter.startLeScan(bleLeScanCallback);
    }

    /**
     * to scan device on Android LOLLIPOP and up
     */
    private void startLOLLIPOPScan() {
        Logger.myLog("开始扫描777 startLOLLIPOPScan");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            if (bleScanCallback == null) {
                bleScanCallback = new BleLeScanCallback.BleScanCallback();
                bleScanCallback.setOnScanCallback(this);
            }
            BluetoothAdapter bleAdapter = BluetoothAdapter.getDefaultAdapter();
            BluetoothLeScanner scanner = bleAdapter.getBluetoothLeScanner();
            scanner.startScan(null, scanSettings, bleScanCallback);
        }
    }

    /**
     * see {@link #SCAN_TYPE_COMPATIBLE}, {@link #SCAN_TYPE_JELLY} , {@link #SCAN_TYPE_LOLLIPOP}
     *
     * @param scanType the scan type
     */
    public void setScanType(int scanType) {
        this.scanType = scanType;
    }


    /**
     * @return return the state of scan
     */
    public boolean isScaning() {
        return this.isScaning;
    }

    public void exit() {
        cancelLeScan(false);
        scanTimes = 0;
        scanIntervalMap.clear();
    }

    /**
     * cancel le scan
     *
     * @return
     */
    public boolean cancelLeScan(boolean isFinished) {
        if (scanHandler.hasMessages(0x01)) {
            scanHandler.removeMessages(0x01);
        }
        BluetoothAdapter bleAdapter = BluetoothAdapter.getDefaultAdapter();
        Logger.myLog("cancelLeScan" + "isScaning:" + isScaning + "isFinished:" + isFinished + "bleAdapter == null || !bleAdapter.isEnabled():" + (bleAdapter == null || !bleAdapter.isEnabled()));
        if (bleAdapter == null || !bleAdapter.isEnabled()) {
            if (isScaning) {
                if (isFinished) {
                    if (mScanListener != null) {
                        mScanListener.onScanFinished();
                    }
                }
            }
            isScaning = false;
            return false;
        } else {
            if (isScaning) {
                if (isFinished) {
                    if (mScanListener != null) {
                        mScanListener.onScanFinished();
                    }
                }
            }
            isScaning = false;

            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
                //4.4之前
                bleAdapter.stopLeScan(bleLeScanCallback);
            } else {
                //4.4及之后
                bleAdapter.stopLeScan(bleLeScanCallback);
                //bleAdapter.getBluetoothLeScanner().stopScan(bleScanCallback);
            }


            return true;
        }
       /* if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            BluetoothLeScanner scanner = bleAdapter.getBluetoothLeScanner();
            scanner.stopScan(bleScanCallback);
        } else {*/
        //}
    }

    /**
     * called on version that below{@link Build.VERSION_CODES#LOLLIPOP}
     *
     * @param result scan result
     */
    @Override
    public void onScanResult(BleLeScanCallback.ScanResult result) {
        if (mScanListener != null) {
            ArrayList<BleLeScanCallback.ScanResult> list = new ArrayList<>();
            list.add(result);
            synchronized (mScanListener) {
                mScanListener.onBatchScanResults(list);
            }
        }
    }

    @Override
    public void onBatchScanResults(ArrayList<BleLeScanCallback.ScanResult> results) {
        if (mScanListener != null) {
            synchronized (mScanListener) {
                mScanListener.onBatchScanResults(results);
            }
        }
    }

    @Override
    public void onScanFailed(int errorCode) {
        if (mScanListener != null) {
            synchronized (mScanListener) {
                mScanListener.onScanFailed(errorCode);
            }
        }
    }

    class ScanHandler extends Handler {
        private WeakReference<ScanManager> scanManager;

        public ScanHandler(ScanManager manager, Looper looper) {
            super(looper);
            scanManager = new WeakReference<ScanManager>(manager);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (scanManager.get() != null) {
                switch (msg.what) {
                    case 0x01:
                        scanManager.get().cancelLeScan(true);
                        break;
                }
            }
        }
    }

    /**
     * if you want to get the result of scan, you need implement the interface
     */
    public interface OnScanManagerListener {
        /**
         * it will be call if there are devices be scaned
         *
         * @param results
         */
        void onBatchScanResults(ArrayList<BleLeScanCallback.ScanResult> results);

        /**
         * if the scan was canceled it would be call
         */
        void onScanFinished();

        /**
         * see {@link #SCAN_FAILED_ALREADY_STARTED},{@link #SCAN_FAILED_APPLICATION_REGISTRATION_FAILED},
         * {@link #SCAN_FAILED_FEATURE_UNSUPPORTED},{@link #SCAN_FAILED_INTERNAL_ERROR}
         *
         * @param errorCode
         */
        void onScanFailed(int errorCode);
    }

}
