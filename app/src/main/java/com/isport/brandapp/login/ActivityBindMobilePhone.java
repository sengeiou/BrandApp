package com.isport.brandapp.login;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.isport.brandapp.App;
import com.isport.brandapp.R;
import com.isport.brandapp.login.presenter.ActivityBindMobilePhonePresenter;
import com.isport.brandapp.login.view.ActivityBindMobilePhoneView;
import com.isport.brandapp.util.AppSP;
import com.isport.brandapp.util.UserAcacheUtil;
import com.isport.brandapp.view.TimerView;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import brandapp.isport.com.basicres.ActivityManager;
import brandapp.isport.com.basicres.commonbean.UserInfoBean;
import brandapp.isport.com.basicres.commonutil.MessageEvent;
import brandapp.isport.com.basicres.commonutil.ToastUtils;
import brandapp.isport.com.basicres.commonutil.TokenUtil;
import brandapp.isport.com.basicres.commonutil.UIUtils;
import brandapp.isport.com.basicres.commonview.TitleBarView;
import brandapp.isport.com.basicres.mvp.BaseMVPWhiteTitleActivity;
import brandapp.isport.com.basicres.service.observe.BleProgressObservable;
import brandapp.isport.com.basicres.service.observe.NetProgressObservable;
import phone.gym.jkcq.com.commonres.common.AllocationApi;
import phone.gym.jkcq.com.commonres.common.JkConfiguration;


/**
 *第三方登录绑定手机号
 */
public class ActivityBindMobilePhone extends BaseMVPWhiteTitleActivity<ActivityBindMobilePhoneView, ActivityBindMobilePhonePresenter> implements ActivityBindMobilePhoneView, View.OnClickListener {

    private EditText etPhone;
    private EditText etCode;
    private TimerView timer;
    private TextView btnBindMobilePhone;
    private String bindPhoneNum;
    private String phoneNum;

 /*   private CheckBox checkBox;
    private TextView tvProtol;*/

    @Override
    protected ActivityBindMobilePhonePresenter createPresenter() {
        return new ActivityBindMobilePhonePresenter(this);
    }


    private boolean isFromSetting;
    private String from;

    private void getIntentData() {
        isFromSetting = getIntent().getBooleanExtra(JkConfiguration.GymUserInfo.FROMSETTING, false);
        from = getIntent().getStringExtra(JkConfiguration.GymUserInfo.FROM);
        /*if (isFromSetting) {
            titleBarView.setRightTextViewStateIsShow(false);
            titleBarView.setLeftIconEnable(true);
        }*/
        if (!TextUtils.isEmpty(from)) {

        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.app_actvity_bind_mobilephone;
    }


    @Override
    protected void initView(View view) {
        etPhone = (EditText) findViewById(R.id.et_phone);
        etCode = (EditText) findViewById(R.id.et_code);
        timer = (TimerView) findViewById(R.id.timer);
        btnBindMobilePhone = (TextView) findViewById(R.id.btn_bind_mobile_phone);
       /* tvProtol = findViewById(R.id.tv_pro);
        checkBox = findViewById(R.id.check_open);*/
    }

    /**
     * 获取验证码成功
     */
    @Override
    public void getVerCodeSuccess(String message) {
        showToast(message);
        timer.startTimer();
    }


    /**
     * 获取验证
     */
    @Override
    public void getVerCodeNotPass(String message) {
        showToast(message);
        /**
         * 此手机已绑定其他用户,需要停止倒计时
         */
        timer.stopTimer();

        // timer.startTimer();
    }

    /**
     * 绑定手机成功
     */
    @Override
    public void bindPhoneSuccess(UserInfoBean bean) {//TODO  绑定手机成功,下一步
        showToast(bean.getMessage());
        Intent intentActivityDataSetting = new Intent(context, ActivitySettingUserInfo.class);
        startActivity(intentActivityDataSetting);
        ActivityManager.getInstance().finishAllActivity();
        TokenUtil.getInstance().updateIsRegidit(app, "true");
        //finish();
    }

    @Override
    public void onRespondError(String message) {
        showToast(message);
    }

    @Override
    protected void initData() {
        titleBarView.setLeftIconEnable(true);
        timer.setOnClickListener(this);
        titleBarView.setTitle(R.string.binding_tel);
        titleBarView.setRightText("");
        /**
         * 隐藏跳过此步骤
         */
        getIntentData();
    }

    @Override
    protected void initEvent() {
        btnBindMobilePhone.setOnClickListener(this);
        //tvProtol.setOnClickListener(this);

        titleBarView.setOnTitleBarClickListener(new TitleBarView.OnTitleBarClickListener() {
            @Override
            public void onLeftClicked(View view) {
                finish();

            }

            @Override
            public void onRightClicked(View view) {
              /*  Intent intentActivityDataSetting = new Intent(context, ActivityDataSetting.class);
                startActivity(intentActivityDataSetting);*/
//                finish();
            }
        });
    }

    @Override
    protected void initHeader() {
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.timer:
//                if (!checkNet()&& App.appType()==App.httpType){
//                    return;
//                }
                if (mActPresenter.checkPhoneNum(etPhone.getText().toString().trim())) {//TODO 发送验证码请求
                    mActPresenter.getVerCode(etPhone.getText().toString().trim());
                    phoneNum = etPhone.getText().toString().trim();
                } else {
                    showToast(R.string.enter_correct_tel_notice);
                }

                break;
            case R.id.btn_bind_mobile_phone://TODO 绑定手机的网络请求
//                if (!checkNet()&&App.appType()==App.httpType){
//                    return;
//                }
                if (mActPresenter.checkPhoneNum(etPhone.getText().toString().trim())) {
                    if (etCode.getText().length() != 4) {
                        showToast(R.string.enter_correct_vertify_notice);
                        return;
                    }
                    /*if (!checkBox.isChecked()) {
                        showToast(R.string.agree_user_agreement_notice);
                        return;
                    }*/
                    phoneNum = etPhone.getText().toString().trim();
                    mActPresenter.bindPhone(etPhone.getText().toString().trim(), etCode.getText().toString().trim(), TokenUtil.getInstance().getPeopleIdStr(getApplicationContext()));
                    bindPhoneNum = etPhone.getText().toString().trim();
                } else {
                    showToast(R.string.enter_correct_tel_notice);
                }

                break;
            case R.id.tv_pro: {
                Intent intent = new Intent(context, ActivityUserAgreement.class);
                intent.putExtra("title", UIUtils.getString(R.string.app_protol));
                intent.putExtra("url", AllocationApi.BaseUrl + "/agreement/agreement.html");
                startActivity(intent);
                //ToastUtils.showToast(context, "点击了用户协议");
                break;
            }

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
