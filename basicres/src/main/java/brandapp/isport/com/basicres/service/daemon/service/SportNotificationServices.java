package brandapp.isport.com.basicres.service.daemon.service;

import android.app.Notification;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.service.notification.NotificationListenerService;
import android.service.notification.StatusBarNotification;
import android.text.TextUtils;
import android.util.Log;
import android.widget.RemoteViews;


import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import phone.gym.jkcq.com.commonres.common.AllocationApi;

/*
 *
 *
 * @author mhj
 * Create at 2018/4/8 19:38
 */
public class SportNotificationServices extends NotificationListenerService {

    private String type = "";

    @Override
    public void onNotificationPosted(StatusBarNotification sbn) {
        super.onNotificationPosted(sbn);
        //当有新通知到来时会回调；
        // 如果该通知的包名不是微信，那么 pass 掉
        if ("com.tencent.mm".equals(sbn.getPackageName())
                || "com.tencent.mobileqq".equals(sbn.getPackageName())
                || "com.skype.raider".equals(sbn.getPackageName())
                || "com.twitter.android".equals(sbn.getPackageName())
                || "com.facebook.katana".equals(sbn.getPackageName())
                || "jp.naver.line.android".equals(sbn.getPackageName())
                || "com.instagram.android".equals(sbn.getPackageName())) {

            if ("com.tencent.mm".equals(sbn.getPackageName())) {
                type = AllocationApi.SendPhoneMessageType.WECHAT;
            }
            if ("com.tencent.mobileqq".equals(sbn.getPackageName())) {
                type = AllocationApi.SendPhoneMessageType.QQ;
            }
            if ("com.skype.raider".equals(sbn.getPackageName())) {
                type = AllocationApi.SendPhoneMessageType.SKYPE;
            }
            if ("com.twitter.android".equals(sbn.getPackageName())) {
                type = AllocationApi.SendPhoneMessageType.TWITTER;
            }
            if ("com.facebook.katana".equals(sbn.getPackageName())) {
                type = AllocationApi.SendPhoneMessageType.FACEBOOK;
            }
            if ("jp.naver.line.android".equals(sbn.getPackageName())) {
                type = AllocationApi.SendPhoneMessageType.LINKEDIN;
            }
            if ("com.instagram.android".equals(sbn.getPackageName())) {
                type = AllocationApi.SendPhoneMessageType.INSTAGRAM;
            }

            Notification notification = sbn.getNotification();
            if (notification == null) {
                return;
            }
//        PendingIntent pendingIntent = null;
            // 当 API > 18 时，使用 extras 获取通知的详细信息
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                Bundle extras = notification.extras;
                if (extras != null) {
                    // 获取通知标题
                    String title = extras.getString(Notification.EXTRA_TITLE, "");
                    // 获取通知内容
                    String content = extras.getString(Notification.EXTRA_TEXT, "");
                  /*  if (JkConfiguration.bluetoothDeviceCon) {
                        BleDeviceService.getInstance().setPhoneMessage(content, title, type);
                    }*/
                }
            } else {
//             当 API = 18 时，利用反射获取内容字段
                List<String> textList = getText(notification);
                if (textList != null && textList.size() > 0) {
                    for (String text : textList) {
                        Log.e("shao", "text:" + text);
//                    if (!TextUtils.isEmpty(text) && text.contains("[微信红包]")) {
//                        pendingIntent = notification.contentIntent;
//                        break;
//                    }
                    }
                }
            }

        }
    }

    @Override
    public void onNotificationRemoved(StatusBarNotification sbn) {
        super.onNotificationRemoved(sbn);
        //当有通知移除时会回调；
    }

    @Override
    public void onListenerConnected() {
        super.onListenerConnected();
        //是可用的并且和通知管理器连接成功时回调。


    }


    public List<String> getText(Notification notification) {
        if (null == notification) {
            return null;
        }
        RemoteViews views = notification.bigContentView;
        if (views == null) {
            views = notification.contentView;
        }
        if (views == null) {
            return null;
        }
        // Use reflection to examine the m_actions member of the given RemoteViews object.
        // It's not pretty, but it works.
        List<String> text = new ArrayList<>();
        try {
            Field field = views.getClass().getDeclaredField("mActions");
            field.setAccessible(true);
            @SuppressWarnings("unchecked")
            ArrayList<Parcelable> actions = (ArrayList<Parcelable>) field.get(views);
            // Find the setText() and setTime() reflection actions
            for (Parcelable p : actions) {
                Parcel parcel = Parcel.obtain();
                p.writeToParcel(parcel, 0);
                parcel.setDataPosition(0);
                // The tag tells which type of action it is (2 is ReflectionAction, from the source)
                int tag = parcel.readInt();
                if (tag != 2) continue;
                // View ID
                parcel.readInt();
                String methodName = parcel.readString();
                if (null == methodName) {
                    continue;
                } else if (methodName.equals("setText")) {
                    // Parameter type (10 = Character Sequence)
                    parcel.readInt();
                    // Store the actual string
                    String t = TextUtils.CHAR_SEQUENCE_CREATOR.createFromParcel(parcel).toString().trim();
                    text.add(t);
                }
                parcel.recycle();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return text;
    }
}
