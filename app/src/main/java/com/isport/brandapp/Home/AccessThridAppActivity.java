package com.isport.brandapp.Home;

import android.content.Intent;
import android.view.View;

import com.isport.brandapp.App;
import com.isport.brandapp.R;
import com.isport.brandapp.Third_party_access.GoogleFitActivity;
import com.isport.brandapp.login.ActivityLogin;
import com.isport.brandapp.util.AppSP;
import com.isport.brandapp.util.UserAcacheUtil;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import bike.gymproject.viewlibray.ItemView;
import brandapp.isport.com.basicres.ActivityManager;
import brandapp.isport.com.basicres.BaseTitleActivity;
import brandapp.isport.com.basicres.commonutil.MessageEvent;
import brandapp.isport.com.basicres.commonutil.ToastUtils;
import brandapp.isport.com.basicres.commonutil.TokenUtil;
import brandapp.isport.com.basicres.commonutil.UIUtils;
import brandapp.isport.com.basicres.commonview.TitleBarView;
import brandapp.isport.com.basicres.service.observe.BleProgressObservable;
import brandapp.isport.com.basicres.service.observe.NetProgressObservable;

public class AccessThridAppActivity extends BaseTitleActivity implements View.OnClickListener {
    ItemView itemViewGoogleFit;

    @Override
    protected int getLayoutId() {
        return R.layout.app_activity_access_third;
    }

    @Override
    protected void initView(View view) {

        itemViewGoogleFit = view.findViewById(R.id.itemview_google_fit);

        if (App.isZh(this)) {
            itemViewGoogleFit.setVisibility(View.VISIBLE);
        } else {
            itemViewGoogleFit.setVisibility(View.VISIBLE);
        }
    }

    @Override
    protected void initData() {
        //c4a049fd79a25a83af46d9c135314c6d  c4a049fd79a25a83af46d9c135314c6d
        //2510540
        //FeedbackAPI.init(app, "23015524", "c4a049fd79a25a83af46d9c135314c6d");
        //25140601  94d0c175298ecae86b7a66783592b8eb

        //FeedbackAPI.init(app);
        titleBarView.setLeftIconEnable(true);
        titleBarView.setTitle(R.string.set);
        titleBarView.setRightText("");

    }

    @Override
    protected void initEvent() {
        itemViewGoogleFit.setOnClickListener(this);

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
            case R.id.itemview_google_fit:
                Intent intent = new Intent(this, GoogleFitActivity.class);
                startActivity(intent);
                break;
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
}
