package com.isport.brandapp.sport;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.gyf.immersionbar.ImmersionBar;
import com.isport.brandapp.App;
import com.isport.brandapp.R;
import com.isport.brandapp.bean.DeviceBean;
import com.isport.brandapp.login.ActivityLogin;
import com.isport.brandapp.sport.fragment.FragmentAllSport;
import com.isport.brandapp.sport.fragment.FragmentList;
import com.isport.brandapp.util.AppSP;
import com.isport.brandapp.util.UserAcacheUtil;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.Calendar;
import java.util.Observable;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import brandapp.isport.com.basicres.ActivityManager;
import brandapp.isport.com.basicres.BaseActivity;
import brandapp.isport.com.basicres.commonutil.MessageEvent;
import brandapp.isport.com.basicres.commonutil.ToastUtils;
import brandapp.isport.com.basicres.commonutil.TokenUtil;
import brandapp.isport.com.basicres.commonutil.UIUtils;
import brandapp.isport.com.basicres.service.observe.BleProgressObservable;
import brandapp.isport.com.basicres.service.observe.NetProgressObservable;
import brandapp.isport.com.basicres.service.observe.TodayObservable;
import phone.gym.jkcq.com.commonres.common.JkConfiguration;

public class SportReportActivity extends BaseActivity implements View.OnClickListener {

    private DeviceBean deviceBean;
    private TextView tvTitle;
    private ImageView ivBack, ivShare, ivCaleder;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_report;
    }

    @Override
    protected void initView(View view) {
        tvTitle = view.findViewById(R.id.tv_sport_type);
        ivBack = view.findViewById(R.id.iv_back);
        ivShare = view.findViewById(R.id.iv_share);
        ivCaleder = view.findViewById(R.id.iv_calender);
        ivShare.setVisibility(View.GONE);
        ivCaleder.setVisibility(View.GONE);
        try {
            FragmentManager manager = getSupportFragmentManager();
            FragmentTransaction transaction = manager.beginTransaction();
            FragmentAllSport fragmentList = new FragmentAllSport();
            Bundle bundle = new Bundle();
            bundle.putInt("type", FragmentList.TYPE_DAY);
            deviceBean = (DeviceBean) getIntent().getSerializableExtra(JkConfiguration.DEVICE);
            bundle.putSerializable(JkConfiguration.DEVICE, deviceBean);
            fragmentList.setArguments(bundle);
            transaction.replace(R.id.content, fragmentList);
            transaction.commitAllowingStateLoss();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    protected void initData() {
        TodayObservable.getInstance().addObserver(this);

        tvTitle.setText(UIUtils.getString(R.string.sport_history_memory));

        // TODO: 2018/11/5 查询历史
    }

    @Override
    protected void initEvent() {
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }

    @Override
    protected void initHeader() {
        ImmersionBar.with(this).statusBarDarkFont(true)
                .init();
       // StatusBarCompat.setStatusBarColor(getWindow(), getResources().getColor(R.color.transparent), true);
    }

    @Override
    public void update(Observable o, Object arg) {
        super.update(o, arg);
        if (o instanceof TodayObservable) {

            try {
                Integer type = (Integer) arg;
                if (type == FragmentList.TYPE_DAY) {
                    FragmentManager manager = getSupportFragmentManager();
                    FragmentTransaction transaction = manager.beginTransaction();
                    FragmentAllSport fragmentList = new FragmentAllSport();
                    Calendar cal = Calendar.getInstance();
                    cal.add(Calendar.DATE, 0);
                    int date = (int) (cal.getTimeInMillis() / 1000);
                /*Fragment fragment = new DayFragment();
                Bundle bundle = new Bundle();
                bundle.putInt("date", date);
                bundle.putSerializable(JkConfiguration.DEVICE, deviceBean);
                fragment.setArguments(bundle);*/
                    transaction.replace(R.id.content, fragmentList);
                    transaction.commitAllowingStateLoss();
                } else {
                    FragmentManager manager = getSupportFragmentManager();
                    FragmentTransaction transaction = manager.beginTransaction();
                    FragmentList fragmentList = new FragmentList();
                    Bundle bundle = new Bundle();

                    bundle.putInt("type", type);
                    bundle.putSerializable(JkConfiguration.DEVICE, deviceBean);

                    fragmentList.setArguments(bundle);
                    transaction.replace(R.id.content, fragmentList);
                    transaction.commitAllowingStateLoss();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }


        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //rgTab.check(R.id.rb_day);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void Event(MessageEvent messageEvent) {
        switch (messageEvent.getMsg()) {
            case MessageEvent.NEED_LOGIN:
                ToastUtils.showToast(context, UIUtils.getString(com.isport.brandapp.basicres.R.string.login_again));
                NetProgressObservable.getInstance().hide();
                BleProgressObservable.getInstance().hide();
                TokenUtil.getInstance().clear(context);
                UserAcacheUtil.clearAll();
                AppSP.putBoolean(context, AppSP.CAN_RECONNECT, false);
                App.initAppState();
                Intent intent = new Intent(context, ActivityLogin.class);
                context.startActivity(intent);
                ActivityManager.getInstance().finishAllActivity(ActivityLogin.class.getSimpleName());
                break;
            default:
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        TodayObservable.getInstance().deleteObserver(this);
    }
}
