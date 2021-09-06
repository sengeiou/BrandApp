package com.isport.brandapp.sport.location.utils;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;

import com.isport.brandapp.Home.MainActivity;
import com.isport.brandapp.R;


/**
 * 功能:app数据发布通知工具
 * 做了两个兼容:
 * 1.这就厉害了,兼容各种黑底/白底状态栏 跟随系统自动适应文字/图标相应地变色
 * 2.兼容了Android 8.0
 */

public class AppDataNotifyUtil {
    //    private static int mContentTitleColor = -123;
//    private static int mContentTextColor = -123;
//    private static String mContentTitle = "FindContentTitle";
//    private static String mContentText = "FindContentText";
//    private static String title = "";
//    private static String content = "";
    private static final int MAIN_NOTIFY_ID = 0x230000;
    public static final String MAIN_CHANNEL_ID = "CHANNEL_" + MAIN_NOTIFY_ID;


//    private static void init(Context context) {
//        Bitmap icon = BitmapFactory.decodeResource(context.getResources(), R.mipmap.ic_launcher);
//        {
//            NotificationCompat.Builder mCompatBuilder = new NotificationCompat.Builder(context);
//            mCompatBuilder.setContentTitle(mContentTitle);
//            mCompatBuilder.setContentText(mContentText);
//            mCompatBuilder.setLargeIcon(icon);
//            Notification notification = mCompatBuilder.build();
//            ViewGroup viewGroup = null;
//            if (notification.contentView != null) {
//                viewGroup = (ViewGroup) notification.contentView.apply(context, new LinearLayout(context));
//            } else {
//                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
//                    Notification.Builder mDefBuilder = new Notification.Builder(context);
//                    mDefBuilder.setContentTitle(mContentTitle);
//                    mDefBuilder.setContentText(mContentText);
//                    mDefBuilder.setLargeIcon(icon);
//                    viewGroup = (ViewGroup) mDefBuilder.createContentView().apply(context, new LinearLayout(context));
//                }
//            }
//
//            find(viewGroup);
//
//        }
//    }

//    private stat_


    public static void updateNotificationTitle(Context context, String title, String content) {
        sendNotification(context, MAIN_CHANNEL_ID, MAIN_NOTIFY_ID, title, content);
    }

//    public static void updateNotificationPushContent(Context context, String content) {
//        sendNotification(context, MAIN_CHANNEL_ID, MAIN_NOTIFY_ID, R.mipmap.ic_launcher, R.mipmap.icon_notify_step, null, R.mipmap.icon_notify_push, content);
//    }
//
//    public static void updateNotificationAlarmClockContent(Context context, String content) {
//        sendNotification(context, MAIN_CHANNEL_ID, MAIN_NOTIFY_ID, R.mipmap.ic_launcher, R.mipmap.icon_notify_step, null, R.mipmap.icon_notify_alarm_clock, content);
//    }
//
//    public static void updateNotificationScheduleContent(Context context, String content) {
//        sendNotification(context, MAIN_CHANNEL_ID, MAIN_NOTIFY_ID, R.mipmap.ic_launcher, R.mipmap.icon_notify_step, null, R.mipmap.icon_notify_schedule, content);
//    }

    private static void sendNotification(Context context, @NonNull String channelId, int notifyId, String title, String content) {

//        if (contentIconId == -1) {
//            contentIconId = AppDataNotifyUtil.contentIconId;
//        }
//        if (IF.isEmpty(title)) {
//            title = AppDataNotifyUtil.title;
//        }
//        if (IF.isEmpty(content)) {
//            content = AppDataNotifyUtil.content;
//        }
//        AppDataNotifyUtil.title = title;
//        AppDataNotifyUtil.content = content;
//
//        if (mContentTitleColor == -123) {
//            mContentTitleColor = 0XFFCCCCCC;
//            init(context);
//        }
        NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel mChannel = new NotificationChannel(channelId, context.getString(R.string.bonlala_loaction_off), NotificationManager.IMPORTANCE_DEFAULT);
            mChannel.enableLights(false);
            mChannel.enableVibration(false);
            mChannel.setSound(null, null);
            if (manager != null) {
                manager.createNotificationChannel(mChannel);
            }
            builder.setChannelId(channelId);
        }

        int iconId = R.drawable.icon_mine_thrid;
        builder.setSmallIcon(iconId);
        builder.setAutoCancel(false);
        builder.setVibrate(new long[]{});
        builder.setSound(null);
        builder.setOngoing(true);
        builder.setContentTitle(title);
        builder.setContentText(content);

        Bitmap icon = BitmapFactory.decodeResource(context.getResources(), iconId);
        builder.setLargeIcon(icon);
//        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.notification_layout);
//        views.setTextColor(R.id.tvTitle, mContentTitleColor);
//        views.setTextColor(R.id.tvContent, mContentTitleColor);
//        views.setTextViewText(R.id.tvTitle, title);
//        views.setTextViewText(R.id.tvContent, content);
//        views.setImageViewBitmap(R.id.ivImage, icon);
//        views.setImageViewResource(R.id.ivTitle, titleIconId);
//        views.setImageViewResource(R.id.ivContent, contentIconId);
//        views.setInt(R.id.ivTitle, "setColorFilter", mContentTitleColor);
//        views.setInt(R.id.ivContent, "setColorFilter", mContentTitleColor);
//        builder.setContent(views);

        PendingIntent contentIntent = PendingIntent.getActivity(context, 0, new Intent(context, MainActivity.class), 0);
        builder.setContentIntent(contentIntent);
        if (manager != null) {
            Notification notification = builder.build();
            notification.flags |= NotificationCompat.FLAG_NO_CLEAR;
            notification.flags |= NotificationCompat.FLAG_FOREGROUND_SERVICE;
            if (context instanceof Service) {
                ((Service) context).startForeground(notifyId, notification);
            } else {
                manager.notify(notifyId, notification);
            }
        }

    }


}
