package com.isport.brandapp.Home;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.view.View;
import android.widget.TextView;

import com.isport.brandapp.App;
import com.isport.brandapp.R;
import com.isport.brandapp.login.ActivityLogin;
import com.isport.brandapp.login.ActivityUserAgreement;
import com.isport.brandapp.login.ActivityprivacyAgreement;
import com.isport.brandapp.util.AppSP;
import com.isport.brandapp.util.UserAcacheUtil;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import brandapp.isport.com.basicres.ActivityManager;
import brandapp.isport.com.basicres.BaseTitleWhiteActivity;
import brandapp.isport.com.basicres.commonutil.MessageEvent;
import brandapp.isport.com.basicres.commonutil.ToastUtils;
import brandapp.isport.com.basicres.commonutil.TokenUtil;
import brandapp.isport.com.basicres.commonutil.UIUtils;
import brandapp.isport.com.basicres.commonview.TitleBarView;
import brandapp.isport.com.basicres.service.observe.BleProgressObservable;
import brandapp.isport.com.basicres.service.observe.NetProgressObservable;
import phone.gym.jkcq.com.commonres.common.AllocationApi;

public class AboutActivity extends BaseTitleWhiteActivity implements View.OnClickListener {
    TextView tvVersion;
    TextView tvProtol, tv_privacy, tvProcess;

    @Override
    protected int getLayoutId() {
        return R.layout.app_activity_about;
    }


    @Override
    protected void initView(View view) {

        tvVersion = view.findViewById(R.id.tv_version);
        tvProtol = view.findViewById(R.id.tv_user_protol);
        tv_privacy = view.findViewById(R.id.tv_privacy);
        tvProcess = view.findViewById(R.id.tv_adreess);
    }

    @Override
    protected void initData() {
        titleBarView.setLeftIconEnable(true);
        titleBarView.setTitle(R.string.about_us);
        titleBarView.setRightText("");
        String version = getVersion(context);
        tvVersion.setText(String.format(getResources().getString(R.string.app_version), version));
    }

    @Override
    protected void initEvent() {

        tvProtol.setOnClickListener(this);
        tvProcess.setOnClickListener(this);
        tv_privacy.setOnClickListener(this);
        titleBarView.setOnTitleBarClickListener(new TitleBarView.OnTitleBarClickListener() {
            @Override
            public void onLeftClicked(View view) {
                finish();
            }

            @Override
            public void onRightClicked(View view) {

            }
        });
    }

    @Override
    protected void initHeader() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_privacy:
                Intent intentprivacy = new Intent(context, ActivityprivacyAgreement.class);
                intentprivacy.putExtra("title", UIUtils.getString(R.string.privacy_agreement));
                intentprivacy.putExtra("url", AllocationApi.BaseUrl + "/isport/concumer-basic/agreement/privacyagreement.html");
                startActivity(intentprivacy);
                break;
            case R.id.tv_user_protol:
                Intent intent = new Intent(context, ActivityUserAgreement.class);
                intent.putExtra("title", UIUtils.getString(R.string.app_protol));
                intent.putExtra("url", AllocationApi.BaseUrl + "/isport/concumer-basic/agreement/agreement.html");
                startActivity(intent);
                break;
            case R.id.tv_adreess:
//                Intent intent2 = new Intent(context, ActivityWatchStep.class);
//                startActivity(intent2);
                break;
        }
    }

    public static String getVersion(Context context) {
        PackageManager packageManager = context.getPackageManager();
        PackageInfo packInfo;
        String version = "";
        try {
            packInfo = packageManager.getPackageInfo(context.getPackageName(),
                    0);
            version = packInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return version;
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
}
