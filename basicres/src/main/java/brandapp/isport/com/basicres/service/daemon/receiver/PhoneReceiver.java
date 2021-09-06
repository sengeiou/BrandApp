package brandapp.isport.com.basicres.service.daemon.receiver;

import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;


import brandapp.isport.com.basicres.BaseApp;
import brandapp.isport.com.basicres.commonutil.Logger;

import static android.content.Context.TELEPHONY_SERVICE;

/**
 * Created by huashao on 2017/9/20.
 */

public class PhoneReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        System.out.println("action" + intent.getAction());
        if (intent.getAction().equals(Intent.ACTION_NEW_OUTGOING_CALL)) {
//            String phoneNumber = intent
//                    .getStringExtra(Intent.EXTRA_PHONE_NUMBER);
        } else {
            TelephonyManager tm = (TelephonyManager) context.getSystemService(TELEPHONY_SERVICE);
            tm.listen(listener, PhoneStateListener.LISTEN_CALL_STATE);
        }
    }

    PhoneStateListener listener = new PhoneStateListener() {
        @Override
        public void onCallStateChanged(int state, String incomingNumber) {
//注意，方法必须写在super方法后面，否则incomingNumber无法获取到值。
            super.onCallStateChanged(state, incomingNumber);
            try {
                switch (state) {
                    case TelephonyManager.CALL_STATE_RINGING:   //来电
                        String name = null;
                        Logger.e("来电话了", incomingNumber);
                        Uri uri = Uri.parse("content://com.android.contacts/data/phones/filter/" + incomingNumber);
                        ContentResolver resolver = BaseApp.getApp().getContentResolver();
                        Cursor cursor = resolver.query(uri, new String[]{"display_name"}, null, null, null);
                        if (cursor.moveToFirst()) {
                            name = cursor.getString(0);
                            Logger.e("来电话了", name);
                        }
                        cursor.close();
                        String phoneNum = incomingNumber;
                        String phoneName = name;
                        //BleDeviceService.getInstance().setPhoneNumtToDevices(phoneNum,phoneName);
                        break;
                    case TelephonyManager.CALL_STATE_OFFHOOK:   //接通电话
                        break;
                    case TelephonyManager.CALL_STATE_IDLE:  //挂掉电话
                        break;
                }
            } catch (IllegalStateException e) {
                e.printStackTrace();
            }
        }
    };
}
