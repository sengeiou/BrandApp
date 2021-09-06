package com.isport.brandapp.Home;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.view.View.OnClickListener;

import com.isport.blelibrary.utils.Constants;
import com.isport.brandapp.AppConfiguration;
import com.isport.brandapp.Home.fragment.FragmentNewData;
import com.isport.brandapp.R;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import brandapp.isport.com.basicres.BaseActivity;
import brandapp.isport.com.basicres.commonutil.Logger;
import brandapp.isport.com.basicres.commonutil.MessageEvent;


public class DeviceMainActivity extends BaseActivity implements OnClickListener {


    FragmentNewData fragmentData;
    FragmentManager mFragmentManager;


    private List<Fragment> fragmentList = new ArrayList<>();

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
//        try {
//            super.onRestoreInstanceState(savedInstanceState);
//        } catch (Exception e) {
//        }
        // savedInstanceState = null;
    }

    @Override
    protected void onResume() {
        super.onResume();
        AppConfiguration.isScaleScan = false;
        AppConfiguration.isScaleRealTime = false;
        AppConfiguration.isScaleConnectting = false;
        Constants.isDFU = false;//不是升级模式
    }

    private static final String DATA_FRAGMENT_KEY = "fragmentData";

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {

        if (fragmentData != null) {
            getSupportFragmentManager().putFragment(outState, DATA_FRAGMENT_KEY, fragmentData);
        }
        super.onSaveInstanceState(outState, outPersistentState);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        com.isport.blelibrary.utils.Logger.myLog("onNewIntent onCreate" + savedInstanceState);
        if (mFragmentManager == null) {
            mFragmentManager = this.getSupportFragmentManager();
        }
        if (savedInstanceState != null) {
            fragmentData = (FragmentNewData) mFragmentManager.findFragmentByTag
                    (DATA_FRAGMENT_KEY);

            addToList(fragmentData);
        } else {
            initFragment();

            //  isSave = false;
        }
    }

    private void addToList(Fragment fragment) {
        if (fragment != null) {
            fragmentList.add(fragment);
        }

        Logger.e("fragmentList数量" + fragmentList.size());
    }


    /*添加fragment*/
    private void addFragment(Fragment fragment) {

        /*判断该fragment是否已经被添加过  如果没有被添加  则添加*/
        if (!fragment.isAdded()) {
            mFragmentManager.beginTransaction().add(R.id.frament, fragment).commitAllowingStateLoss();
            /*添加到 fragmentList*/
            fragmentList.add(fragment);
        }
    }

    /*显示fragment*/
    private void showFragment(Fragment fragment) {


        Logger.e("fragmentList數量：" + fragmentList.size());
        for (Fragment frag : fragmentList) {
            if (frag != fragment) {
                /*先隐藏其他fragment*/
                Logger.e("隱藏" + fragment);
                mFragmentManager.beginTransaction().hide(frag).commitAllowingStateLoss();
            }
        }
        mFragmentManager.beginTransaction().show(fragment).commitAllowingStateLoss();

    }

    @Override
    protected void onNewIntent(Intent intent) {
        com.isport.blelibrary.utils.Logger.myLog("onNewIntent");
        super.onNewIntent(intent);
    }

    @Override
    public void onRestoreInstanceState(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onRestoreInstanceState(savedInstanceState, persistentState);
    }


    @Override
    protected int getLayoutId() {
        return R.layout.app_activity_device_main;
    }

    @Override
    protected void initView(View view) {

    }

    @Override
    protected void initData() {


    }


    @Override
    protected void initEvent() {
       /* IntentFilter filter = new IntentFilter();
        filter.addAction(Intent.ACTION_TIME_TICK);*/
        if (!EventBus.getDefault().isRegistered(this))
            EventBus.getDefault().register(this);
    }

    @Override
    protected void initHeader() {
    }


    @Override
    public void onClick(View v) {


    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void Event(MessageEvent messageEvent) {
    }


    private void initFragment() {
        /* 默认显示home  fragment*/

        fragmentData = new FragmentNewData();
        addFragment(fragmentData);
        showFragment(fragmentData);


    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        // stopService(serviceIntent);
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void update(Observable o, Object arg) {
        super.update(o, arg);
    }


}
