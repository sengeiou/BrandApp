package com.isport.brandapp.sport;

import android.annotation.SuppressLint;
import android.app.KeyguardManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Binder;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.os.PowerManager;
import android.util.Log;

import com.example.websocket.WsManager;
import com.isport.blelibrary.utils.ThreadSinglePoolUtils;
import com.isport.brandapp.device.sleep.TimeUtil;
import com.isport.brandapp.sport.bean.ArrayThree;
import com.isport.brandapp.sport.bean.IndoorRunDatas;
import com.isport.brandapp.sport.bean.runargs.ArgsForInRunService;
import com.isport.brandapp.sport.location.LocationServiceHelper;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import brandapp.isport.com.basicres.commonutil.Logger;
import brandapp.isport.com.basicres.service.observe.IndoorRunObservable;
import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;


public class OutSportService extends Service implements LocationServiceHelper.OnLocationListener {
    private JWebSocketClientBinder mBinder = new JWebSocketClientBinder();
    private final static int GRAY_SERVICE_ID = 1001;
    private LocationServiceHelper mLSHelper;
    public static int pauseCount = 0;
    public static final IndoorRunDatas theMomentRunData = new IndoorRunDatas();

    @Override
    public void onLocationChanged(String city, double latitude, double longitude) {

    }

    //灰色保活
    public static class GrayInnerService extends Service {

        @Override
        public int onStartCommand(Intent intent, int flags, int startId) {

            Log.e("onStartCommand", "onStartCommand-----");
            startForeground(GRAY_SERVICE_ID, new Notification());
            stopForeground(true);
            stopSelf();
            return super.onStartCommand(intent, flags, startId);
        }

        @Override
        public IBinder onBind(Intent intent) {
            return null;
        }
    }


    PowerManager.WakeLock wakeLock;//锁屏唤醒

    //获取电源锁，保持该服务在屏幕熄灭时仍然获取CPU时，保持运行
    @SuppressLint("InvalidWakeLockTag")
    private void acquireWakeLock() {
        if (null == wakeLock) {
            PowerManager pm = (PowerManager) this.getSystemService(Context.POWER_SERVICE);
            wakeLock = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK | PowerManager.ON_AFTER_RELEASE, "PostLocationService");
            if (null != wakeLock) {
                wakeLock.acquire();
            }
        }
    }

    //用于Activity和service通讯
    public class JWebSocketClientBinder extends Binder {
        public OutSportService getService() {
            return OutSportService.this;
        }
    }

    private NotificationManager notiManager;

    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.e("GaoDeLocationImpl", "onCreate");

    }

    private void startLocation() {
        //重复定位 大于1小时的,重新定位
        if (mLSHelper != null) {
            mLSHelper.startLocation();
        }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        //初始化websocket
        // initSocketClient();
        Log.e("GaoDeLocationImpl", "onStartCommand");
        mLSHelper = new LocationServiceHelper(this);
        mLSHelper.setOnLocationListener(this);
        startLocation();
        mHandler.postDelayed(heartBeatRunnable, HEART_BEAT_RATE);//开启心跳检测

        argsForInRunService.clearData();
        argsForInRunService.phoneStartTime = System.currentTimeMillis();

        theMomentRunData.clearData();
        inRunIsPause = false;
        isFirst = true;
        isFirstPace = true;

        //所有的数据都恢复默认值

        //当前的运动类型
        pauseCount = 0;
        arrayThreePhoneVelocityTool = new ArrayThree();
        arrayThreePhoneVelocityTool.cleanAtRunFinish();
        startTimer();
        Notification.Builder builder = new Notification.Builder(this.getApplicationContext()); //获取一个Notification构造器
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
//修改安卓8.1以上系统报错
            NotificationChannel notificationChannel = new NotificationChannel("notification_id", "bonlala", NotificationManager.IMPORTANCE_MIN);
            notificationChannel.enableLights(false);//如果使用中的设备支持通知灯，则说明此通知通道是否应显示灯
            notificationChannel.setShowBadge(false);//是否显示角标
            notificationChannel.setLockscreenVisibility(Notification.VISIBILITY_SECRET);
            notiManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            notiManager.createNotificationChannel(notificationChannel);
            builder.setChannelId("notification_id");
        }
        builder.setAutoCancel(true);//用户点击就自动消失
        Notification notification = builder.build(); // 获取构建好的Notification
        notification.defaults = Notification.DEFAULT_SOUND; //设置为默认的声音
        // notiManager.notify(110 /* ID of notification */, notification);  //这就是那个
        // 参数一：唯一的通知标识；参数二：通知消息。
        startForeground(110, notification);// 开始前台服务

       /* //设置service为前台服务，提高优先级
        if (Build.VERSION.SDK_INT < 18) {
            //Android4.3以下 ，隐藏Notification上的图标
            startForeground(GRAY_SERVICE_ID, new Notification());
        } else if (Build.VERSION.SDK_INT > 18 && Build.VERSION.SDK_INT < 25) {
            //Android4.3 - Android7.0，隐藏Notification上的图标
            Intent innerIntent = new Intent(this, GrayInnerService.class);
            startService(innerIntent);
            startForeground(GRAY_SERVICE_ID, new Notification());
        } else {
            //Android7.0以上app启动后通知栏会出现一条"正在运行"的通知
            startForeground(GRAY_SERVICE_ID, new Notification());
        }
*/
        acquireWakeLock();
        return START_STICKY;
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            stopForeground(true);// 停止前台服务--参数：表示是否移除之前的通知
        }
        if (inRunIsPause && disposableTimer != null && !disposableTimer.isDisposed()) {
            Logger.e("disposableTimer end");
            disposableTimer.dispose();
        }
    }

    public OutSportService() {
    }


    /**
     * 初始化websocket连接
     */
    private void initSocketClient() {

    }

    /**
     * 连接websocket
     */

    /**
     * 发送消息
     *
     * @param msg
     */

    /**
     * 断开连接
     */


//    -----------------------------------消息通知--------------------------------------------------------

    /**
     * 检查锁屏状态，如果锁屏先点亮屏幕
     *
     * @param content
     */
    private void checkLockAndShowNotification(String content) {
        //管理锁屏的一个服务
        KeyguardManager km = (KeyguardManager) getSystemService(Context.KEYGUARD_SERVICE);
        if (km.inKeyguardRestrictedInputMode()) {//锁屏
            //获取电源管理器对象
            PowerManager pm = (PowerManager) this.getSystemService(Context.POWER_SERVICE);
            if (!pm.isScreenOn()) {
                @SuppressLint("InvalidWakeLockTag") PowerManager.WakeLock wl = pm.newWakeLock(PowerManager.ACQUIRE_CAUSES_WAKEUP |
                        PowerManager.SCREEN_BRIGHT_WAKE_LOCK, "bright");
                wl.acquire();  //点亮屏幕
                wl.release();  //任务结束后释放
            }
            //sendNotification(content);
        } else {
            // sendNotification(content);
        }
    }

    /**
     * 发送通知
     *
     * @param content
     */
    private void sendNotification(String content) {
        /*Intent intent = new Intent();
        intent.setClass(this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        NotificationManager notifyManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        Notification notification = new NotificationCompat.Builder(this)
                .setAutoCancel(true)
                // 设置该通知优先级
                .setPriority(Notification.PRIORITY_MAX)
                .setSmallIcon(R.drawable.icon)
                .setContentTitle("服务器")
                .setContentText(content)
                .setVisibility(VISIBILITY_PUBLIC)
                .setWhen(System.currentTimeMillis())
                // 向通知添加声音、闪灯和振动效果
                .setDefaults(Notification.DEFAULT_VIBRATE | Notification.DEFAULT_ALL | Notification.DEFAULT_SOUND)
                .setContentIntent(pendingIntent)
                .build();
        notifyManager.notify(1, notification);//id要保证唯一*/
    }


    public void stopConnService() {
        mHandler.removeCallbacks(heartBeatRunnable);
        stopSelf();
    }

    //    -------------------------------------websocket心跳检测------------------------------------------------
    private static final long HEART_BEAT_RATE = 10 * 1000;//每隔10秒进行一次对长连接的心跳检测
    private Handler mHandler = new Handler();
    private Runnable heartBeatRunnable = new Runnable() {
        @Override
        public void run() {
            Log.e("heartBeatRunnable", "------");
            if (WsManager.logBuilder == null) {
                WsManager.logBuilder = new StringBuilder();
            }
            WsManager.logBuilder.append(new StringBuilder(dataToString(new Date(), "HH:mm:ss")) + "--heartBeatRunnable");
            // checkLockAndShowNotification("2");
            mHandler.postDelayed(this, HEART_BEAT_RATE);
        }
    };

    public static boolean isFirst = true;
    static Disposable disposableTimer, paceTimer;
    public static ArrayThree arrayThreePhoneVelocityTool = null;

    public static boolean isFirstPace;
    public static ArgsForInRunService argsForInRunService = new ArgsForInRunService();

    static double sencondDis = 0;
    public static boolean inRunIsPause = false;

    public static void startTimer() {

        //20秒的总距离

        if (disposableTimer != null && !disposableTimer.isDisposed()) {
            // disposableTimer.dispose();
            return;
        }
        disposableTimer = Observable.interval(1, TimeUnit.SECONDS).subscribe(new Consumer<Long>() {

            @Override
            public void accept(Long aLong) throws Exception {
                //需要单线程去处理这些逻辑
                ThreadSinglePoolUtils.getInstance().addTask(new Runnable() {
                    @Override
                    public void run() {
                        if (inRunIsPause) {
                            arrayThreePhoneVelocityTool.cleanAtRunFinish();
                            argsForInRunService.phonePauseTimeTotal++;
                        } else {
                            long sportTimer = System.currentTimeMillis() - argsForInRunService.phonePauseTimeTotal * 1000 - argsForInRunService.phoneStartTime;
                            theMomentRunData.setTime(TimeUtil.getTimerFormatedStrings(sportTimer, 0))
                                    .setTotalStep(argsForInRunService.phoneStepValue).setTimer(sportTimer / 1000);//保存的是秒数
                            IndoorRunObservable.getInstance().setChanged();
                            IndoorRunObservable.getInstance().notifyObservers(theMomentRunData);
                        }


                    }
                });


            }
        });
    }


    public static String dataToString(Date date, String format) {
        try {
            SimpleDateFormat format1 = new SimpleDateFormat(format);
            return format1.format(date);
        } catch (Exception e) {
            return System.currentTimeMillis() + "";
        }

    }

}

