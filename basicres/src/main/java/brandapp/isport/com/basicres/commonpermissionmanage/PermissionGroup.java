package brandapp.isport.com.basicres.commonpermissionmanage;

import android.Manifest;
import android.os.Build;

/**
 * Created by xueming_wu on 2017/12/5 0005.
 * 以权限组的形式申请权限，必须每个都成功才会返回true
 */

public class PermissionGroup {
    /**
     * 日历的读写权限
     */
//    public static final String[] CALENDAR;
    /**
     * 拍照
     */
    public static final String[] CAMERA;


    /**
     * 接听电话权限
     */

    public static final String[] Call_SUPPORT_PERSSION;
    public static final String[] Call_SUPPORT_PERSSION_28;

    /**
     * 拍照和存储权限
     */
    public static final String[] CAMERA_STORAGE;
    /**
     * 联系人
     */
    public static final String[] CONTACTS;
    /**
     * 位置权限
     */
    public static final String[] LOCATION;
    /**
     * 录音权限
     */
    public static final String[] MICROPHONE;
    /**
     * 电话权限
     */
    public static final String[] PHONE;
    /**
     * 传感器权限
     */
//    public static final String[] SENSORS;

    /**
     * 短信权限
     */
//    public static final String[] SMS;

    static {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
//            CALENDAR = new String[]{};
            CAMERA = new String[]{};
            CONTACTS = new String[]{};
            LOCATION = new String[]{};
            MICROPHONE = new String[]{};
            PHONE = new String[]{};
            SHORTCUT = new String[]{};
//            SENSORS = new String[]{};
//            SMS = new String[]{};
            STORAGE = new String[]{};
            CAMERA_STORAGE = new String[]{};
            Call_SUPPORT_PERSSION = new String[]{};
            Call_SUPPORT_PERSSION_28 = new String[]{};
        } else {
//            CALENDAR = new String[]{
//                    Manifest.permission.READ_CALENDAR,
//                    Manifest.permission.WRITE_CALENDAR};

            CAMERA = new String[]{
                    Manifest.permission.CAMERA};

            CAMERA_STORAGE = new String[]{
                    Manifest.permission.CAMERA,
                    Manifest.permission.ACCESS_COARSE_LOCATION,
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
            };

            //READ_PHONE_STATE
            //READ_CALL_LOG
            //CALL_PHONE
            //READ_CONTACTS
            Call_SUPPORT_PERSSION = new String[]{
                    Manifest.permission.READ_PHONE_STATE,
                    Manifest.permission.READ_CALL_LOG,
                    Manifest.permission.CALL_PHONE,
                    Manifest.permission.READ_CONTACTS,
            };
            Call_SUPPORT_PERSSION_28 = new String[]{
                    Manifest.permission.READ_PHONE_STATE,
                    Manifest.permission.READ_CALL_LOG,
                    Manifest.permission.CALL_PHONE,
                    Manifest.permission.READ_CONTACTS,
                    Manifest.permission.ANSWER_PHONE_CALLS
            };

            CONTACTS = new String[]{
                    Manifest.permission.READ_CONTACTS,
                    Manifest.permission.WRITE_CONTACTS};

            LOCATION = new String[]{
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION};

            MICROPHONE = new String[]{
                    Manifest.permission.RECORD_AUDIO};

            PHONE = new String[]{
                    Manifest.permission.READ_PHONE_STATE,
                    Manifest.permission.CALL_PHONE,
//                    Manifest.permission.READ_CALL_LOG,
//                    Manifest.permission.WRITE_CALL_LOG,
//                    Manifest.permission.USE_SIP,
//                    Manifest.permission.PROCESS_OUTGOING_CALLS
            };

//            SENSORS = new String[]{
//                    Manifest.permission.BODY_SENSORS};

//            SMS = new String[]{
//                    Manifest.permission.SEND_SMS,
//                    Manifest.permission.RECEIVE_SMS,
//                    Manifest.permission.READ_SMS,
//                    Manifest.permission.RECEIVE_WAP_PUSH,
//                    Manifest.permission.RECEIVE_MMS};

            STORAGE = new String[]{
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE};
            SHORTCUT = new String[]{
                    Manifest.permission.INSTALL_SHORTCUT,
                    Manifest.permission.UNINSTALL_SHORTCUT};
        }
    }

    /**
     * 存储权限
     */
    public static final String[] STORAGE;
    /**
     * 快捷方式权限
     */
    public static final String[] SHORTCUT;
}
