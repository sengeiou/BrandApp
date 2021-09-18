package com.isport.blelibrary.bluetooth.callbacks;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCallback;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattDescriptor;
import android.bluetooth.BluetoothGattService;
import android.bluetooth.BluetoothProfile;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.SystemClock;
import android.text.TextUtils;
import android.util.Log;

import com.isport.blelibrary.deviceEntry.impl.BaseDevice;
import com.isport.blelibrary.deviceEntry.interfaces.IDeviceType;
import com.isport.blelibrary.interfaces.BluetoothListener;
import com.isport.blelibrary.interfaces.BluetoothSettingSuccessListener;
import com.isport.blelibrary.observe.BatteryChangeObservable;
import com.isport.blelibrary.utils.Constants;
import com.isport.blelibrary.utils.Logger;

import java.lang.reflect.Method;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import androidx.annotation.MainThread;

/**
 * @Author
 * @Date 2018/11/1
 * @Fuction
 */

public class BaseGattCallback extends BluetoothGattCallback {

    private static final String TAG = "BaseGattCallback";
    
    //共有对象变量
    protected BluetoothGattService mGattService;
    protected BluetoothGattCharacteristic mSendChar;
    protected BluetoothGattCharacteristic mReceiveDataChar;
    protected BluetoothGattCharacteristic mRealTimeDataChar;
    public BluetoothGattCharacteristic mHeartDataChar = null;
    protected BluetoothGattCharacteristic mResponceChar;
    protected BluetoothGattCharacteristic mHeartRateChar;
    protected boolean hasSyncTime;
    protected ExecutorService executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors()
            * 2 + 1);

    public static StringBuilder logBuilder;
    public BaseDevice mBaseDevice;
    //****************************************基础功能*******************************************//
    private boolean tempConnected = false;//是否连接成功
    public boolean isConnected = false;//区别于是否连接成功，是获取完服务后的真正的连接成功
    public int connectState;
    public static int CONNECTED = 0;//连接上
    public static int CONNECTTING = 1;//正在连接
    public static int DISCONNECTED = 2;//断连
    Handler mDelayFindServiceHandler = new Handler(Looper.getMainLooper());
    Handler mMainHandler = new Handler(Looper.getMainLooper());
    public BluetoothListener bluetoothListener;//manager中的监听
    public BluetoothSettingSuccessListener settingSuccessListener;//manager中的监听
    private static int DISCOVERSERVICEDELAY = 5000;//找服务的超时监听
    public BluetoothGatt mNRFBluetoothGatt;
    protected boolean mSendOk;
    private String mBluetoothDeviceAddress;
    public Context mContext;
    public boolean isFisrt;//用以判断是否是连接的第一次，手表获取电量
    private static int ReConnectTimes = 0;//重连次数
    private String currentAddress;
    private int mCurrentDeviceType;//当前连接的设备Type
    public boolean mIsConnectByUser;
    public final int reConTimes = 10000;
    int delay = 300;

    Handler mReconnectHandler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0x01:
                    Logger.myLog("重连 W516  Constants.CAN_RECONNECT" + Constants.CAN_RECONNECT);
                    if (Constants.CAN_RECONNECT) {
                        connectState = DISCONNECTED;
                        connect(currentAddress, mCurrentDeviceType, false, 300);
                    }
                    break;
            }
        }
    };

    boolean isRunOnServicesDiscovered = false;

    @Override
    public void onServicesDiscovered(BluetoothGatt gatt, int status) {
        Logger.myLog("onServicesDiscovered: " + status);
        isRunOnServicesDiscovered = true;
        if (status == 0) {
            if (null != mDelayFindServiceHandler)
                mDelayFindServiceHandler.removeCallbacks(checkoutServiceRunable);
        } else {

        }
    }

    @Override
    public void onDescriptorWrite(BluetoothGatt gatt, BluetoothGattDescriptor descriptor,
                                  int status) {
        Logger.myLog("onDescriptorWrite: " + status);
    }

    @Override
    public void onCharacteristicChanged(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic) {
//        Logger.myLog("onCharacteristic changed");
    }

    //************************************************基础功能**************************************************//

    /**
     * Retrieves a list of supported GATT services on the connected device. This should be
     * invoked only after {@code BluetoothGatt#discoverServices()} completes successfully.
     *
     * @return A {@code List} of supported services.
     */
    public List<BluetoothGattService> getSupportedGattServices() {
        if (mNRFBluetoothGatt == null) return null;
        return mNRFBluetoothGatt.getServices();
    }

    /**
     * 读取电量
     *
     * @return
     */
    public boolean internalReadBatteryLevel() {
        final BluetoothGatt gatt = mNRFBluetoothGatt;
        if (gatt == null)
            return false;

        final BluetoothGattService batteryService = gatt.getService(Constants.BATTERY_SERVICE);
        if (batteryService == null)
            return false;
        final BluetoothGattCharacteristic batteryLevelCharacteristic = batteryService.getCharacteristic
                (Constants.BATTERY_LEVEL_CHARACTERISTIC);
        if (batteryLevelCharacteristic == null)
            return false;

        // Check characteristic property
        final int properties = batteryLevelCharacteristic.getProperties();
        if ((properties & BluetoothGattCharacteristic.PROPERTY_READ) == 0)
            return false;

        return internalReadCharacteristic(batteryLevelCharacteristic);
    }

    private boolean internalReadCharacteristic(final BluetoothGattCharacteristic characteristic) {

        Logger.myLog("internalReadCharacteristic1");
        final BluetoothGatt gatt = mNRFBluetoothGatt;
        if (gatt == null || characteristic == null)
            return false;

        // Check characteristic property
        final int properties = characteristic.getProperties();
        if ((properties & BluetoothGattCharacteristic.PROPERTY_READ) == 0)
            return false;

        return gatt.readCharacteristic(characteristic);
    }

    /**
     * 读取版本
     *
     * @return
     */
    public boolean internalReadFirmareVersion() {

        final BluetoothGatt gatt = mNRFBluetoothGatt;
        if (gatt == null)
            return false;

        final BluetoothGattService batteryService = gatt.getService(Constants.DEVICEINFORMATION_SERVICE);
        if (batteryService == null)
            return false;
        final BluetoothGattCharacteristic batteryLevelCharacteristic = batteryService.getCharacteristic
                (Constants.FIRMWAREREVISION_CHARACTERISTIC);
        if (batteryLevelCharacteristic == null)
            return false;

        // Check characteristic property

        return internalReadCharacteristic(batteryLevelCharacteristic);
    }

    /**
     * Set notification
     *
     * @param characteristic
     * @return
     */


/*    protected boolean internalEnableNotifications(final BluetoothGattCharacteristic characteristic) {

        final BluetoothGatt gatt = mNRFBluetoothGatt;
        if (gatt == null || characteristic == null)
            return false;

        // Check characteristic property
        final int properties = characteristic.getProperties();
        if ((properties & BluetoothGattCharacteristic.PROPERTY_NOTIFY) == 0)
            return false;







        final BluetoothGattDescriptor descriptor = characteristic.getDescriptor(Constants.UUID_DESCRIPTOR);
        if (descriptor != null) {
            descriptor.setValue(BluetoothGattDescriptor.ENABLE_NOTIFICATION_VALUE);
            boolean sendOk = gatt.writeDescriptor(descriptor);
            Logger.myLog("internalEnableNotifications:" + sendOk);
            sendOk = gatt.setCharacteristicNotification(characteristic, true);
            Logger.myLog("internalEnableNotifications:" + sendOk);
            return sendOk;
        }
        return false;
    }*/
    @MainThread
    protected boolean internalEnableNotifications(final BluetoothGattCharacteristic characteristic) {
        final BluetoothGatt gatt = mNRFBluetoothGatt;
        if (gatt == null || characteristic == null)
            return false;

        final BluetoothGattDescriptor descriptor = characteristic.getDescriptor(Constants.UUID_DESCRIPTOR);
        if (descriptor != null) {
            Logger.myLog("mNRFBluetoothGatt.getDevice().getAddress()=" + mNRFBluetoothGatt.getDevice().getAddress() + "------internalEnableNotifications gatt.setCharacteristicNotification(" + characteristic.getUuid() + ", true)");
            gatt.setCharacteristicNotification(characteristic, true);

            descriptor.setValue(BluetoothGattDescriptor.ENABLE_NOTIFICATION_VALUE);
            Logger.myLog("internalEnableNotifications Enabling notifications for " + characteristic.getUuid());
            Logger.myLog("internalEnableNotifications gatt.writeDescriptor(" + Constants.UUID_DESCRIPTOR + ", value=0x01-00)");
            return internalWriteDescriptorWorkaround(descriptor);
        }
        return false;
    }

    @MainThread
    private boolean internalWriteDescriptorWorkaround(final BluetoothGattDescriptor descriptor) {
        final BluetoothGatt gatt = mNRFBluetoothGatt;
        if (gatt == null || descriptor == null)
            return false;

        final BluetoothGattCharacteristic parentCharacteristic = descriptor.getCharacteristic();
        final int originalWriteType = parentCharacteristic.getWriteType();
        parentCharacteristic.setWriteType(BluetoothGattCharacteristic.WRITE_TYPE_DEFAULT);
        final boolean result = gatt.writeDescriptor(descriptor);
        parentCharacteristic.setWriteType(originalWriteType);
        Logger.myLog("internalEnableNotifications gatt.setWriteType(" + result);
        return result;
    }

    /**
     * Set indicate
     *
     * @param characteristic
     * @return
     */
    protected boolean internalEnableIndications(final BluetoothGattCharacteristic characteristic) {
        final BluetoothGatt gatt = mNRFBluetoothGatt;
        if (gatt == null || characteristic == null)
            return false;
        // Check characteristic property
        final int properties = characteristic.getProperties();
        if ((properties & BluetoothGattCharacteristic.PROPERTY_INDICATE) == 0)
            return false;

        boolean isEnableNotification = gatt.setCharacteristicNotification(characteristic, true);


        final BluetoothGattDescriptor descriptor = characteristic.getDescriptor(Constants.UUID_DESCRIPTOR);
        if (descriptor != null) {
            descriptor.setValue(BluetoothGattDescriptor.ENABLE_INDICATION_VALUE);
            if (gatt != null) {
                return gatt.writeDescriptor(descriptor);
            } else {
                return false;
            }
        }
        return false;
    }


    @MainThread
    protected boolean internalDisableNotifications(final BluetoothGattCharacteristic characteristic) {
        final BluetoothGatt gatt = mNRFBluetoothGatt;
        //if (gatt == null || characteristic == null || !mConnected)
        if (gatt == null || characteristic == null)
            return false;

        final BluetoothGattDescriptor descriptor = characteristic.getDescriptor(Constants.UUID_DESCRIPTOR);
        if (descriptor != null) {
            gatt.setCharacteristicNotification(characteristic, false);

            descriptor.setValue(BluetoothGattDescriptor.DISABLE_NOTIFICATION_VALUE);
            Logger.myLog("internalEnableNotifications Disabling notifications and indications for " + characteristic.getUuid());
            Logger.myLog("internalEnableNotifications gatt.writeDescriptor(" + Constants.UUID_DESCRIPTOR + ", value=0x00-00)");
            return internalWriteDescriptorWorkaround(descriptor);
        }
        return false;
    }


    /**
     * 5秒后检查service是否获取到
     */
    Runnable checkoutServiceRunable = new Runnable() {
        @Override
        public void run() {
            Logger.myLog("DiscoverServices Again!");
            disDiscovery++;
            if (disDiscovery > 2) {
                if (!isRunOnServicesDiscovered) {
                    bluetoothListener.not_discoverServices();
                    // mNRFBluetoothGatt.disconnect();
                }
            } else {
                if (!isRunOnServicesDiscovered) {
                    doServiceDiscovery();
                }
            }
        }
    };

    @Override
    public void onCharacteristicWrite(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic, int status) {
        Logger.myLog("onCharacteristicWrite, status: " + status);
        if (status == BluetoothGatt.GATT_SUCCESS) {
//            Logger.myLog("GATT_WRITE_SUCCESS!");
            mSendOk = true;
        } else if (status == BluetoothGatt.GATT_WRITE_NOT_PERMITTED) {
            Logger.myLog("GATT_WRITE_NOT_PERMITTED");
        } else {
            Logger.myLog("Write failed , Status is " + status);
        }
    }

    @Override
    public void onCharacteristicRead(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic, int status) {
        if (status == BluetoothGatt.GATT_SUCCESS) {
            byte[] values = characteristic.getValue();
            if (characteristic.getService().getUuid().equals(Constants.BATTERY_SERVICE) && characteristic.getUuid()
                    .equals(Constants.BATTERY_LEVEL_CHARACTERISTIC)) {
                Logger.myLog("onCharacteristicRead***onGetBattery***" + (values[0] & 0xff) + "bluetoothListener" + bluetoothListener);
                if (bluetoothListener != null) {
                    bluetoothListener.onGetBattery(values[0] & 0xff);
                }
                if (mBaseDevice != null) {
                    BatteryChangeObservable.getInstance().getBattery(values[0] & 0xff, mBaseDevice.deviceType, mBaseDevice.deviceName, true);
                }
            } else if (characteristic.getService().getUuid().equals(Constants.DEVICEINFORMATION_SERVICE) &&
                    characteristic.getUuid()
                            .equals(Constants.FIRMWAREREVISION_CHARACTERISTIC)) {
                Logger.myLog("onCharacteristicRead***FirmwareVersion111 ***" + new String(values));
                String s = new String(values);

                // internalReadBatteryLevel();
            }
            Logger.myLog("onCharacteristic read GATT success");
        } else {
            Logger.myLog("onCharacteristic read GATT failed");
        }
    }

    /**
     * 找服务
     */
    int disDiscovery = 0;

    private void toDoServiceDiscovery() {
        Logger.myLog("mNRFBluetoothGatt!=null 去找服务");
        isConnected = false;
        connectState = CONNECTTING;
        isRunOnServicesDiscovered = false;
        disDiscovery = 0;

        if (mNRFBluetoothGatt != null) {
            //  final boolean bonded = mNRFBluetoothGatt.getDevice().getBondState() == BluetoothDevice.BOND_BONDED;

            // Logger.myLog("baseGattCallback: bonded" + bonded);

            int tempdelay = 300;
            //体脂秤值需要300ms
            if (delay == 0) {
                delay = 300;
            } else {
                delay = 1600;
            }
            mMainHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    doServiceDiscovery();
                }
            }, tempdelay);
        }

        /*if (null != mDelayFindServiceHandler) {
            mDelayFindServiceHandler.removeCallbacks(checkoutServiceRunable);
            mDelayFindServiceHandler.postDelayed(checkoutServiceRunable, DISCOVERSERVICEDELAY);
        }*/
    }

    /**
     * 处理断连.指定的
     */
    private void peripheralDisconnected() {
        tempConnected = false;
        Constants.tempConnected = tempConnected;
        isConnected = false;
        isFisrt = false;
        connectState = DISCONNECTED;
        ReConnectTimes = 0;
        Logger.myLog("Disconnected from GATT server.");
        disconnect();
        close();
        if (bluetoothListener != null)
            bluetoothListener.disconnected();
    }

    /**
     * 清除蓝牙缓存
     */
    protected boolean refresh(BluetoothGatt gatt) {


        try {
            Method localMethod = gatt.getClass().getMethod("refresh");
            if (localMethod != null) {
                boolean res = (Boolean) localMethod.invoke(gatt);
                Log.e("BaseGattCallBack", "refreshServices res:" + res);
                return res;
            }
        } catch (Exception e) {
            Log.e("BaseGattCallBack", "BaseGattCallBack" + " refresh An exception occured while refreshing device:" + e.getMessage());
            return false;
        }
        return false;
    }


    //解绑  就不能再去重新连接


    public void disconnect(boolean reconnect) {
        Constants.CAN_RECONNECT = reconnect;
        if (!reconnect) {
            mCurrentDeviceType = IDeviceType.TYPE_NULL;
            mReconnectHandler.removeCallbacksAndMessages(null);
        }
        isConnected = false;
        connectState = DISCONNECTED;
        if (null != mDelayFindServiceHandler)
            mDelayFindServiceHandler.removeCallbacks(checkoutServiceRunable);
        Logger.myLog("mNRFBluetoothGatt" + mNRFBluetoothGatt);
        if (mNRFBluetoothGatt == null) {
            Logger.myLog("BluetoothAdapter not initialized" + bluetoothListener);
            if (bluetoothListener != null) {
                bluetoothListener.disconnected();
            }
            return;
        }
        Logger.myLog("去断开连接3333333333333333");
        Logger.myLog("开门成功主动调用断开操作");
        if (mNRFBluetoothGatt != null) {
            Logger.myLog("去断开连接44444444444444444");
            Logger.myLog("gatt != null开门成功主动调用断开操作，线程 == " + Thread.currentThread());
            //mNRFBluetoothGatt.disconnect();
            gattClose();
            if (bluetoothListener != null) {
                bluetoothListener.disconnected();
            }
        }

        System.gc();
    }

    /**
     * After using a given BLE device, the app must call this method to ensure resources are
     * released properly.
     */

    /**
     * 断开当前设备
     */
    public void disconnect() {

        mSendChar = null;
        mReceiveDataChar = null;
        mRealTimeDataChar = null;
        mHeartDataChar = null;
        mResponceChar = null;
        mHeartRateChar = null;
        mBLEHandler.post(new Runnable() {
            @Override
            public void run() {
                try {
                    if (mNRFBluetoothGatt != null) {
                        refresh(mNRFBluetoothGatt);
                        mNRFBluetoothGatt.disconnect();
                    }
//            if (mBluetoothAdapter != null) {
//                mBluetoothAdapter.cancelDiscovery();
//            }

                } catch (Exception e) {
                    e.printStackTrace();
                }
                System.gc();
            }
        });

    }

    public void close() {
        mBLEHandler.post(new Runnable() {
            @Override
            public void run() {
                try {
                    if (mNRFBluetoothGatt != null) {
                        mNRFBluetoothGatt.close();
                        mNRFBluetoothGatt = null;
                    }
                } catch (Exception ignored) {
                }
            }
        });
    }

    public void gattClose() {
        connectState = DISCONNECTED;
        if (mNRFBluetoothGatt == null) {
            return;
        }
        Logger.myLog("mNRFBluetoothGatt close");
        if (mNRFBluetoothGatt != null) {
            refresh(mNRFBluetoothGatt);
            mNRFBluetoothGatt.close();
            mNRFBluetoothGatt = null;
        }
    }

    public void gattCloseFaileNotity() {
        connectState = DISCONNECTED;
        if (mNRFBluetoothGatt == null) {
            return;
        }
        Logger.myLog("mNRFBluetoothGatt close");
        if (mNRFBluetoothGatt != null) {
            refresh(mNRFBluetoothGatt);
            mNRFBluetoothGatt.close();
            mNRFBluetoothGatt = null;
        }
        //这里需要修改下重新连接的次数
        reConnectW516();
    }


    public BaseGattCallback(BluetoothListener bluetoothListener, Context context, BaseDevice baseDevice) {
        this.bluetoothListener = bluetoothListener;
        this.mContext = context;
        this.mBaseDevice = baseDevice;
        register();

    }

    public void setBaseDevice(BaseDevice baseDevice) {
        this.mBaseDevice = baseDevice;
    }

    private void register() {
        try {
            IntentFilter filter = new IntentFilter();
            filter.addAction(BluetoothAdapter.ACTION_STATE_CHANGED);
            mContext.registerReceiver(broadcastReceiver, filter);
        } catch (Exception e) {
            Logger.myLog(e.toString());
        }

    }

    private final BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if(action == null)
                return;
            if (action.equals(Intent.ACTION_TIME_CHANGED) || action.equals(Intent.ACTION_TIMEZONE_CHANGED)) {

            } else if (action.equals(BluetoothAdapter.ACTION_STATE_CHANGED)) {
                int state = intent.getIntExtra(BluetoothAdapter.EXTRA_STATE, BluetoothAdapter.STATE_OFF);
                if (state == BluetoothAdapter.STATE_ON) {
                    if (isCanRe(mCurrentDeviceType) && Constants.CAN_RECONNECT) {
                        if (bluetoothListener != null) {
                            bluetoothListener.connecting();
                        }
                        Logger.myLog("BluetoothAdapter.STATE_ON");
                        mReconnectHandler.removeMessages(0x01);
                        mReconnectHandler.sendEmptyMessageDelayed(0x01, 10000);
                    }
                } else if (state == BluetoothAdapter.STATE_OFF) {
                    //关闭蓝牙时，移除所有重连,断连回调里面会查询蓝牙是否打开，不打开不做重连操作
                    Logger.myLog("BluetoothAdapter.STATE_OFF");
                    mReconnectHandler.removeCallbacksAndMessages(null);
                    //清楚
                    //gattClose();
                    //同步数据也失败

                }
            } else if (action.equals(BluetoothDevice.ACTION_BOND_STATE_CHANGED)) {
                int bondState = intent.getIntExtra(BluetoothDevice.EXTRA_BOND_STATE, BluetoothDevice.BOND_NONE);
                BluetoothDevice dv = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                String key = intent.getStringExtra(BluetoothDevice.EXTRA_PAIRING_KEY);
                if (bondState == BluetoothDevice.BOND_NONE) {//取消、出错、超时配对后不尝试自动重连

                }
            }
        }
    };

    private void doServiceDiscovery() {


        if (mNRFBluetoothGatt != null) {
            Logger.myLog("DiscoverServices begin");

           /* if (mNRFBluetoothGatt != null) {
                mNRFBluetoothGatt.discoverServices();
            }*/
            //  while (!isRunOnServicesDiscovered) {
            mDelayFindServiceHandler.removeCallbacks(checkoutServiceRunable);
            mDelayFindServiceHandler.postDelayed(checkoutServiceRunable, DISCOVERSERVICEDELAY);
            // Thread.sleep(5000);
            if (mNRFBluetoothGatt != null && !mNRFBluetoothGatt.discoverServices()) {
                Logger.myLog("discoverServices return false");
                if (mNRFBluetoothGatt.getDevice().getBondState() != BluetoothDevice.BOND_BONDING) {
                    Logger.myLog("BluetoothDevice.BOND_BONDING");
                }
            } else {
                Logger.myLog("DiscoverServices return true");
            }
            // }
        } else
            Logger.myLog("disCoverServiceRunable，Gatt is null!");


    }


    @Override
    public void onConnectionStateChange(BluetoothGatt gatt, final int status, int newState) {
        Logger.myLog("onConnectionStateChange: " + status + " to " + newState);


        if (status == 0 && newState == 0) {
            peripheralDisconnected();
            return;
        }
        if (status != BluetoothProfile.STATE_DISCONNECTED && status != BluetoothProfile.STATE_CONNECTED && status != BluetoothProfile.STATE_CONNECTING && status != BluetoothProfile.STATE_DISCONNECTING) {
            // peripheralDisconnected();
            // return;
        }
        if (newState == BluetoothProfile.STATE_CONNECTED) {


            Logger.myLog("Gatt Connected");
            tempConnected = true;
            Constants.tempConnected = tempConnected;
            isFisrt = true;
            // TODO: 2018/10/8 连接成功的广播可以放在找到服务后
            if (null != mNRFBluetoothGatt && tempConnected) {
                toDoServiceDiscovery();
            } else {//部分机器在callback的时候gatt出现Null的状况,此处极度偶发，暂不处理
                Logger.myLog("On connect but gatt still null,waiting...");
                SystemClock.sleep(50);
                if (null != mNRFBluetoothGatt && tempConnected) {
                    toDoServiceDiscovery();
                } else {
                    Logger.myLog("mNRFBluetoothGatt == null");
                }
            }
        } else if (newState == BluetoothProfile.STATE_DISCONNECTED) {

            if (!isDFUUnBindDevcie(status)) {
                if ((status == 133 || status == 22 || status == 34 || status == 8) && Constants.CAN_RECONNECT) {
                    //gattClose();
                    if (isBleEnable() && isCanRe(mCurrentDeviceType) && Constants.CAN_RECONNECT) {
                        toReconnect();
                    }
//                peripheralDisconnected();
                } else {
                    reConnectW516();
                }
            }

        } else {
            //todo 在异常情况下也应处理好状态
            if (!isDFUUnBindDevcie(status)) {
                Logger.myLog("error onConnectionStateChange code" + newState);
                //异常断连给一次重连机会
//            toReconnect();
                //异常情况也要重连，如果是W516情况
                if (isBleEnable() && isCanRe(mCurrentDeviceType) && Constants.CAN_RECONNECT) {
                    mReconnectHandler.removeMessages(0x01);
                    mReconnectHandler.sendEmptyMessageDelayed(0x01, reConTimes);
                    peripheralDisconnected();
                } else {
//                peripheralDisconnected();
                }
            }
        }
    }

    private boolean isDFUUnBindDevcie(int status) {
        if (Constants.isDFU) {
            unbind(currentAddress);
            peripheralDisconnected();
            return true;
        } else {
            return false;

        }
    }

    public void unbind(String mac) {


        Logger.myLog("BaseAgent unbind" + mac);
        BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (bluetoothAdapter != null) {
            BluetoothDevice bleDevice = bluetoothAdapter.getRemoteDevice(mac);
            if (bleDevice != null) {
                unpairDevice(bleDevice);
            }
        }
    }

    //反射来调用BluetoothDevice.removeBond取消设备的配对
    private void unpairDevice(BluetoothDevice device) {
        try {
            Method m = device.getClass()
                    .getMethod("removeBond", (Class[]) null);
            m.invoke(device, (Object[]) null);
        } catch (Exception e) {
            Log.e("BaseGattCallBack", e.getMessage());
        }
    }

    private void toReconnect() {
        if (ReConnectTimes < 1) {
            ReConnectTimes++;
            connectState = DISCONNECTED;
            disconnect();
            close();
            connect(currentAddress, mCurrentDeviceType, false, 3000);
        } else {
            reConnectW516();
        }
    }

    /**
     * W516的当前设备，需要做重连操作
     */
    private void reConnectW516() {
        //如果是关闭蓝牙的状态,则不做重连操作
        Logger.myLog("reConnectW516 isBleEnable()" + isBleEnable() + ",isCanRe(mCurrentDeviceType):" + isCanRe(mCurrentDeviceType) + ",mCurrentDeviceType:" + mCurrentDeviceType + ",Constants.CAN_RECONNECT:" + Constants.CAN_RECONNECT);
        if (isBleEnable() && isCanRe(mCurrentDeviceType) && Constants.CAN_RECONNECT) {
            mReconnectHandler.removeMessages(0x01);
            mReconnectHandler.sendEmptyMessageDelayed(0x01, reConTimes);
            peripheralDisconnected();
        } else {
            peripheralDisconnected();
        }
    }

    /**
     * Connects to the GATT server hosted on the Bluetooth LE device.
     *
     * @param
     * @return Return true if the connection is initiated successfully. The connection result
     * is reported asynchronously through the
     * {@code BluetoothGattCallback#onConnectionStateChange(android.bluetooth.BluetoothGatt, int, int)}
     * callback.
     */


    private boolean isCanRe(int deviceType) {
        if (deviceType == IDeviceType.TYPE_WATCH
                || deviceType == IDeviceType.TYPE_WATCH_W526
                || deviceType == IDeviceType.TYPE_BRAND_W311
                || deviceType == IDeviceType.TYPE_BRAND_W520
                || deviceType == IDeviceType.TYPE_BRAND_W307J
                || deviceType == IDeviceType.TYPE_WATCH_W557
                || deviceType == IDeviceType.TYPE_WATCH_W812B
                || deviceType == IDeviceType.TYPE_WATCH_W560
                || deviceType == IDeviceType.TYPE_WATCH_W560B
                || deviceType == IDeviceType.TYPE_ROPE_S002) {
            return true;
        } else {
            return false;
        }
    }

    private boolean isNoReConDevice(int type) {
        if (type != IDeviceType.TYPE_WATCH
                && type != IDeviceType.TYPE_BRAND_W311
                && type != IDeviceType.TYPE_BRAND_W520
                && type != IDeviceType.TYPE_WATCH_W526
                && type != IDeviceType.TYPE_WATCH_W557
                && type != IDeviceType.TYPE_WATCH_W812B
                && type != IDeviceType.TYPE_WATCH_W560B
                && type != IDeviceType.TYPE_BRAND_W307J
                && type != IDeviceType.TYPE_ROPE_S002) {
            return true;
        } else {
            return false;
        }
    }

    public synchronized boolean connect(final String address, int deviceType, boolean isConnectByUser, int deday) {


        this.delay = deday;
        this.mIsConnectByUser = isConnectByUser;
        //1.手表正在连接过程中,直接返回，这是出现多次连接手表的情况
        if (connectState == CONNECTTING && isCanRe(deviceType)) {
            Logger.myLog(TAG,"设备正在连接中,去连接手表");
            return false;
        }
        //1.手环正在连接过程中,直接返回，这是出现多次连接手表的情况
       /* if (connectState == CONNECTTING && deviceType == IDeviceType.TYPE_BRAND_W311) {
            Logger.myLog("设备正在连接中,去连接手环");
            return false;
        }*/

        //2.当正在连接手表时,连接的是体脂秤或者其他设备，说明在主动连接其他设备,那么就直接放过
        if (connectState == CONNECTTING && isNoReConDevice(mCurrentDeviceType)) {
            Logger.myLog(TAG,"去连接的设备不是手表,库里正在执行连接操作");
        }
        mCurrentDeviceType = deviceType;
        if (isNoReConDevice(mCurrentDeviceType)) {
            //如果不是连接的手表，那么说明此时不应该做手表的重连
            Constants.CAN_RECONNECT = false;
        }
        //不论连接的什么设备，此时已经是在连接了，去掉连接监听
        mReconnectHandler.removeCallbacksAndMessages(null);

        Logger.myLog("NRF Connect begin" + Constants.CAN_RECONNECT);
        if (mBluetoothDeviceAddress != null)
            Logger.myLog("add " + mBluetoothDeviceAddress + " in:" + address);
        // TODO: 2018/10/8 绑定状态是否要去掉
        Logger.myLog("重新创建连接，获取远端设备");
        final BluetoothDevice device = getDeviceWithAdress(address);
        if (device == null) {
            Logger.myLog("Device not found.  Unable to connect.");
            // TODO: 2019/4/10 当找不到当前设备时,重新连接
            if (isBleEnable() && isCanRe(deviceType) && Constants.CAN_RECONNECT) {
                mReconnectHandler.removeMessages(0);
                mReconnectHandler.sendEmptyMessageDelayed(0x01, reConTimes);
                peripheralDisconnected();
            } else {
                //如果没有找到设备，那么要回调连接断开到app,这样可以在app里面主动做连接
                peripheralDisconnected();
            }
            return false;
        }

        BluetoothAdapter bleAdapter = BluetoothAdapter.getDefaultAdapter();

        if (bleAdapter == null || !bleAdapter.isEnabled()) {
            //蓝牙未开启时，不做任何操作,只是断连
            peripheralDisconnected();
            return false;
        }


        Logger.myLog(TAG,"Create a new connection begin.");
        tempConnected = false;
        Constants.tempConnected = tempConnected;
        isConnected = false;
        currentAddress = address;
        connectState = CONNECTTING;
        // TODO: 2018/11/1 在这儿分配callback
        if (getGatt(device, mContext, this)) {
            Logger.myLog(TAG,"mNRFBluetoothGatt!=null,说明调用成功，设置初始连接状态等信息");
            mBluetoothDeviceAddress = address;
            return true;
        } else
            return false;
    }

    private boolean isBleEnable() {
        BluetoothAdapter bleAdapter = BluetoothAdapter.getDefaultAdapter();

        if (bleAdapter == null || !bleAdapter.isEnabled()) {
            //蓝牙未开启时，不做任何操作,只是断连
            return false;
        } else {
            return true;
        }
    }

    /**
     * get bluetooth device with address
     *
     * @param address
     * @return
     */
    public BluetoothDevice getDeviceWithAdress(String address) {
        BluetoothDevice device = null;
        if (TextUtils.isEmpty(address)) {
            return null;
        }
        try {
            device = BluetoothAdapter.getDefaultAdapter().getRemoteDevice(address);

        } catch (Exception e) {
            e.printStackTrace();
            device = null;

        } finally {
            return device;
        }
        //return address == null ? null : BluetoothAdapter.getDefaultAdapter().getRemoteDevice(address);
    }

    private Handler mBLEHandler = new Handler(Looper.getMainLooper());

    /**
     * 注意,反射得到的方法!!不支持!!BR协议
     *
     * @param device
     * @param mService
     * @param mGattCallback
     * @return
     */

    private void connectGattCompat(BluetoothDevice device, Context mService, BluetoothGattCallback mGattCallback) {
        //主要解决 status=133问题
        // aiAutoConnect = !aiAutoConnect;


        //Logger.myLog("mNRFBluetoothGatt" + "device=" + device + mNRFBluetoothGatt + "mNR" + mNRFBluetoothGatt.getDevice().getAddress() + "-----" + mNRFBluetoothGatt.getDevice().getName());

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            final int preferredPhy = 1;
            mNRFBluetoothGatt = device.connectGatt(mService, false, mGattCallback,
                    BluetoothDevice.TRANSPORT_LE, preferredPhy/*, handler*/);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            mNRFBluetoothGatt = device.connectGatt(mService, false, mGattCallback,
                    BluetoothDevice.TRANSPORT_LE);
        } else {
            mNRFBluetoothGatt = device.connectGatt(mService, false, mGattCallback);
        }
       /* try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                // connectRequest will never be null here.
                final int preferredPhy = 1;
           *//* log(Log.DEBUG, "gatt = device.connectGatt(autoConnect = false, TRANSPORT_LE, "
                    + phyMaskToString(preferredPhy) + ")");*//*
                // A variant of connectGatt with Handled can't be used here.
                // Check https://github.com/NordicSemiconductor/Android-BLE-Library/issues/54
                Log.e("connect", "gatt = device.connectGatt(autoConnect = false, TRANSPORT_LE,preferredPhy" + preferredPhy);
                // mNRFBluetoothGatt = device.connectGatt(mService, false, mGattCallback);
                return device.connectGatt(mService, false, mGattCallback,
                        BluetoothDevice.TRANSPORT_LE, preferredPhy*//*, mHandler*//*);
            } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                return device.connectGatt(mContext, false, mGattCallback, BluetoothDevice.TRANSPORT_LE);
            } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                //参考 https://stackoverflow.com/questions/28018722/android-could-not-connect-to-bluetooth-device-on-lollipop
                Method connectGattMethod = null;
                try {
                    connectGattMethod = device.getClass().getMethod("connectGatt", Context.class, boolean.class, BluetoothGattCallback.class, int.class);
                } catch (NoSuchMethodException e) {
                    e.printStackTrace();
                }
                try {
                    return (BluetoothGatt) connectGattMethod.invoke(device, mContext, false, mGattCallback, BluetoothDevice.TRANSPORT_LE);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (IllegalArgumentException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
        } catch (Exception ignored) {
        }
        return device.connectGatt(mContext, false, mGattCallback);*/
    }

    private final Object LOCK = new Object();

    public boolean getGatt(BluetoothDevice device, Context mService, BluetoothGattCallback mGattCallback) {

        if (bluetoothListener != null) {
            bluetoothListener.connecting();
        }
        mBLEHandler.removeCallbacks(null);
        synchronized (LOCK) {
            try {
                if (mNRFBluetoothGatt != null) {
                    mNRFBluetoothGatt.disconnect();
                }
                //   mNRFBluetoothGatt = null;
                try {
                    Thread.sleep(200); // Is 200 ms enough?
                } catch (final InterruptedException e) {
                    // Ignore
                }

            } catch (Exception ignored) {
            }

        }
        connectGattCompat(device, mService, mGattCallback);
        if (null != mNRFBluetoothGatt) {
            Logger.myLog(TAG,"mNRFBluetoothGatt!=null,说明调用成功");
            return true;
        } else {
            Logger.myLog("Bluetooth gatt fetch error");
            return false;
        }


    }


    public static void saveLog(StringBuilder builder) {
        if (builder != null) {
            if (logBuilder == null) {
                logBuilder = new StringBuilder();
            }
            logBuilder.append(builder.toString()).append("\r\n");
        }
    }

    public void exit() {
        mReconnectHandler.removeCallbacksAndMessages(null);
        //退出App不再执行重连,当再次进入app时才会重连
        disconnect(false);
    }
}
