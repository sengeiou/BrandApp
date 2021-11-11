package com.jkcq.platform;

import android.app.Application;
import android.content.Context;

import com.umeng.analytics.MobclickAgent;
import com.umeng.commonsdk.UMConfigure;

public class PlatformManager {

    private Context mContext;
    private boolean isShowLog=true;
    private static final String APPKEY_UMENG="59e99b4c734be4484b0000c5";
    /**
     * 初始化第三方平台
     * @param context
     */
    public PlatformManager(Application context){
        this.mContext=context;
    }

    public  void init(){
        initUmeng();
    }

    /**
     * 友盟初始化
     */
    private void initUmeng(){

       UMConfigure.init( mContext, APPKEY_UMENG,  "jkcq", UMConfigure.DEVICE_TYPE_PHONE, null);
       UMConfigure.setLogEnabled(isShowLog);
        // interval 单位为毫秒，如果想设定为40秒，interval应为 40*1000.
        MobclickAgent.setSessionContinueMillis(30*1000);//黑屏，应用后台运行超过30s启动都算一次启动
        // 选用AUTO页面采集模式
        MobclickAgent.setPageCollectionMode(MobclickAgent.PageMode.AUTO);

    }
}
