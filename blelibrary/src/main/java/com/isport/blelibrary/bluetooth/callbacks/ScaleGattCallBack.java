package com.isport.blelibrary.bluetooth.callbacks;

import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattDescriptor;
import android.bluetooth.BluetoothGattService;
import android.content.Context;

import com.isport.blelibrary.deviceEntry.impl.BaseDevice;
import com.isport.blelibrary.interfaces.BluetoothListener;
import com.isport.blelibrary.utils.Constants;
import com.isport.blelibrary.utils.Logger;
import com.isport.blelibrary.utils.Utils;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

/**
 * @Author
 * @Date 2018/11/1
 * @Fuction
 */

public class ScaleGattCallBack extends BaseGattCallback {

    private BluetoothGattCharacteristic mTxCharacteristic;
    private BluetoothGattCharacteristic mRxCharacteristic;
    private BluetoothGattCharacteristic mRxLockCharacteristic;

    public ScaleGattCallBack(BluetoothListener bluetoothListener, Context context, BaseDevice baseDevice) {
        super(bluetoothListener, context, baseDevice);
    }

    @Override
    public void onServicesDiscovered(BluetoothGatt gatt, int status) {
        super.onServicesDiscovered(gatt, status);
        if (status == BluetoothGatt.GATT_SUCCESS) {
            List<BluetoothGattService> services = gatt.getServices();
            String uuid = null;
            for (BluetoothGattService bluetoothGattService : services) {
                uuid = bluetoothGattService.getUuid().toString();
                Logger.myLog("找到服务 == " + uuid);
                if (Constants.MAIN_SERVICE_UUID.toString().equals(uuid)) {
                    for (BluetoothGattCharacteristic bluetoothGattCharacteristic : bluetoothGattService
                            .getCharacteristics()) {
                        String characterUUID = bluetoothGattCharacteristic.getUuid().toString();
                        Logger.myLog("找到特征服务值 == " + characterUUID);
                        if (Constants.WRITE_CHAR_UUID.toString().equals(characterUUID)) {
                            mTxCharacteristic = bluetoothGattCharacteristic;
                        } else if (Constants.NOTIFY_CHAR_UUID.toString().equals(characterUUID)) {
                            mRxCharacteristic = bluetoothGattCharacteristic;
                        }
                    }
                } else if (Constants.LOCK_MAIN_SERVICE_UUID.toString().equals(uuid)) {
                    for (BluetoothGattCharacteristic bluetoothGattCharacteristic : bluetoothGattService
                            .getCharacteristics()) {
                        String characterUUID = bluetoothGattCharacteristic.getUuid().toString();
                        if (Constants.LOCK_INDICATE_CHAR_UUID.toString().equals(characterUUID)) {
                            mRxLockCharacteristic = bluetoothGattCharacteristic;
                        }
                    }
                }
            }
            if (mTxCharacteristic == null || mRxCharacteristic == null) {
                Logger.myLog(mRxCharacteristic.getUuid().toString());
                Logger.myLog("Tx charateristic not found!");
                if (bluetoothListener != null)
                    bluetoothListener.not_discoverServices();
                return;
            }
            //修改单位
            if (bluetoothListener != null)
                bluetoothListener.servicesDiscovered();

        } else {
            Logger.myLog("onServicesDiscovered received: " + status);
        }
    }

    @Override
    public void onDescriptorWrite(BluetoothGatt gatt, BluetoothGattDescriptor descriptor, int status) {
        super.onDescriptorWrite(gatt, descriptor, status);

        if (status == BluetoothGatt.GATT_SUCCESS) {
            final UUID uuid = descriptor.getCharacteristic().getUuid();
            Logger.myLog("onDescriptorWrite" + uuid.toString());
            if (uuid.equals(Constants.NOTIFY_CHAR_UUID)) {
                if (bluetoothListener != null)
                    bluetoothListener.enableUnLockSuccess();
            } else if (uuid.equals(Constants.LOCK_INDICATE_CHAR_UUID)) {////历史数据接收通道, 设置成功后，开启命令控制通道 SEND_DATA_CHAR
                isConnected = true;
                Logger.myLog("真正的连接成功!");
                try {
                    if (bluetoothListener != null)
                        bluetoothListener.connected();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public void onCharacteristicChanged(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic) {
        super.onCharacteristicChanged(gatt, characteristic);
        if (characteristic != null) {

            Logger.myLog("characteristic.getUuid() " + characteristic.getUuid());
            if (characteristic.getUuid().equals(Constants.NOTIFY_CHAR_UUID)) {
                byte[] value = characteristic.getValue();
                int bodyR = Utils.byteArrayToInt1(new byte[]{value[9], value[10]});
                int bodyWeight = Utils.byteArrayToInt1(new byte[]{value[11], value[12]});
                Logger.myLog("UnLock onCharacteristic changed bodyR " + bodyR / (float) 10 + " bodyWeight " +
                        bodyWeight / (float) 10);
                if (bluetoothListener != null)
                    bluetoothListener.unLockData(bodyWeight / (float) 10);
            } else if (characteristic.getUuid().equals(Constants.LOCK_INDICATE_CHAR_UUID)) {
                byte[] value = characteristic.getValue();
                int bodyR = Utils.byteArrayToInt1(new byte[]{value[9], value[10]});
                int bodyWeight = Utils.byteArrayToInt1(new byte[]{value[11], value[12]});
                Logger.myLog("Lock onCharacteristic changed bodyR " + bodyR / (float) 10 + " bodyWeight " +
                        bodyWeight / (float) 10);
                if (bluetoothListener != null)
                    bluetoothListener.lockData(bodyWeight / (float) 10, bodyR / (float) 10);
            }
        }
    }

    /**
     * 需要指定的特征值
     *
     * @param data
     * @return
     */
    public boolean writeRXCharacteristicItem(byte[] data) {
        if (mNRFBluetoothGatt == null) {
            Logger.myLog("Gatt is null");
            return false;
        }
        mTxCharacteristic.setValue(data);
        if (!true)
            mTxCharacteristic.setWriteType(BluetoothGattCharacteristic.WRITE_TYPE_NO_RESPONSE);
        boolean status = mNRFBluetoothGatt.writeCharacteristic(mTxCharacteristic);
        if (status == false) {
            Logger.myLog("Write data failed!");
            return status;
        }
        return true;
    }


    /**
     * 非锁定数据
     *
     * @return
     */
    public boolean enableNotification() {
        if (mRxCharacteristic != null) {
            // internalDisableNotifications(mRxCharacteristic);
            mMainHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    internalEnableNotifications(mRxCharacteristic);
                }
            }, 50);

        } else {
            return false;
        }
        return true;
    }

    @Override
    public void onCharacteristicRead(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic, int status) {
        super.onCharacteristicRead(gatt, characteristic, status);

        if (status == BluetoothGatt.GATT_SUCCESS) {
            byte[] values = characteristic.getValue();
            if (characteristic.getService().getUuid().equals(Constants.DEVICEINFORMATION_SERVICE) &&
                    characteristic.getUuid()
                            .equals(Constants.FIRMWAREREVISION_CHARACTERISTIC)) {
                String s = new String(values);
                //获取用户信息

                // BE+06+09+FB 手机请求设备的版本信息
                Logger.myLog("***FirmwareVersion222***" + new String(values) + " FirmwareVersion***" + Utils
                        .bytes2HexString(values));
                if (bluetoothListener != null) {
                    bluetoothListener.onGetDeviceVersion(new String(values));
                }
            }
        }
    }

    /**
     * 锁定数据
     *
     * @return
     */
    public boolean enableIndications() {
        if (mRxLockCharacteristic != null) {
            return internalEnableIndications(mRxLockCharacteristic);
        } else {
            return false;
        }
    }
}
