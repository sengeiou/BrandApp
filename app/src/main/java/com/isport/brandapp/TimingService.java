package com.isport.brandapp;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;

import com.isport.brandapp.util.NotificationHelper;

import java.util.Calendar;

import androidx.annotation.Nullable;
import brandapp.isport.com.basicres.commonutil.Logger;
import brandapp.isport.com.basicres.commonutil.TokenUtil;

public class TimingService extends Service {

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        Logger.d("DaemonService---->onStartCommand，启动前台service");

        // filter.addAction(AlarmManagerUtil.ACTION_TIMER_SERVICE);//添加定时

        // 如果Service被终止
        // 当资源允许情况下，重启service
        return START_STICKY;
    }


    /**
     * 系统的一个广播，每分钟会发送一个广播
     */
    BroadcastReceiver timeReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(Intent.ACTION_TIME_TICK)) {
               /* updateTime();
                EventBus.getDefault().post(new UpdateTime());*/


                long time = TokenUtil.getInstance().getSleepTime(context);
                if (time == 0) {
                    return;
                }
                Calendar calendar = Calendar.getInstance();

                Calendar calendar1 = Calendar.getInstance();
                calendar1.setTimeInMillis(time);

                if (calendar.get(Calendar.HOUR_OF_DAY) == calendar1.get(Calendar.HOUR_OF_DAY) && (calendar.get(Calendar.MINUTE) == calendar1.get(Calendar.MINUTE))) {
                    NotificationHelper.show(context);
                }
            }
        }
    };

    public static final int NOTICE_ID = 100;

    @Override
    public void onCreate() {
        super.onCreate();
        IntentFilter filter = new IntentFilter();
        filter.addAction(Intent.ACTION_TIME_TICK);
        // registerReceiver(receiver, filter);

        registerReceiver(timeReceiver, filter);
        /*Logger.d("DaemonService---->onCreate被调用，启动前台service");
        //如果API大于18，需要弹出一个可见通知
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
            Notification.Builder builder = new Notification.Builder(this);
            builder.setSmallIcon(R.drawable.ic_t_launcher);
            builder.setContentTitle("KeepAppAlive");
            builder.setContentText("DaemonService is runing...");
            startForeground(NOTICE_ID, builder.build());
            // 如果觉得常驻通知栏体验不好
            // 可以通过启动CancelNoticeService，将通知移除，oom_adj值不变
            Intent intent = new Intent(this, CancelNoticeService.class);
            startService(intent);
        } else {
            startForeground(NOTICE_ID, new Notification());
        }*/
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        if (timeReceiver != null) {
            unregisterReceiver(timeReceiver);
        }
        // 如果Service被杀死，干掉通知
        /*if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
            NotificationManager mManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            mManager.cancel(NOTICE_ID);
        }
        Logger.d("DaemonService---->onDestroy，前台service被杀死");
        // 重启自己
        Intent intent = new Intent(getApplicationContext(), TimingService.class);
        startService(intent);*/
    }

}
