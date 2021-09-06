package com.isport.brandapp.sport;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.isport.blelibrary.utils.TimeUtils;
import com.isport.brandapp.App;
import com.isport.brandapp.R;
import com.isport.brandapp.banner.recycleView.utils.ToastUtil;
import com.isport.brandapp.device.share.PackageUtil;
import com.isport.brandapp.device.sleep.TimeUtil;
import com.isport.brandapp.login.ActivityLogin;
import com.isport.brandapp.login.ActivityWebView;
import com.isport.brandapp.sport.bean.SportSumData;
import com.isport.brandapp.sport.present.EndSportPresent;
import com.isport.brandapp.sport.view.EndSportView;
import com.isport.brandapp.util.AppSP;
import com.isport.brandapp.util.UserAcacheUtil;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMWeb;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import bike.gymproject.viewlibray.ShareItemView;
import brandapp.isport.com.basicres.ActivityManager;
import phone.gym.jkcq.com.commonres.common.JkConfiguration;

import com.isport.blelibrary.utils.CommonDateUtil;

import brandapp.isport.com.basicres.commonutil.MessageEvent;
import brandapp.isport.com.basicres.commonutil.ToastUtils;
import brandapp.isport.com.basicres.commonutil.TokenUtil;
import brandapp.isport.com.basicres.commonutil.UIUtils;
import brandapp.isport.com.basicres.mvp.BaseMVPActivity;
import brandapp.isport.com.basicres.service.observe.BleProgressObservable;
import brandapp.isport.com.basicres.service.observe.NetProgressObservable;
import phone.gym.jkcq.com.commonres.commonutil.DisplayUtils;

public class EndSportActivity extends BaseMVPActivity<EndSportView, EndSportPresent> implements EndSportView, View.OnClickListener, UMShareListener {
    Integer sportType;
    SportSumData sumData;

    TextView tvDis, tvDate;
    TextView tvShareCancle;
    ShareItemView itemViewSpeed, itemViewTime, itemViewCal;
    ImageView ivBottom, ivSportType, ivBack;
    TextView tvDetail, tvShare, tvTitle;
    String publishid;
    View view_head;

    RelativeLayout layoutShare;
    private ImageView ivQQ, ivWechat, ivWebo, ivFriend;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_sport_end_layout;
    }

    @Override
    protected void initView(View view) {

        view_head = view.findViewById(R.id.view_head);

        LinearLayout.LayoutParams ivSsettingLP = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, DisplayUtils.dip2px(context, 40));
        if (App.isPerforatedPanel()) {
            view_head.setLayoutParams(ivSsettingLP);
        }

        tvDis = view.findViewById(R.id.tv_dis);
        tvDate = view.findViewById(R.id.tv_date);
        tvTitle = view.findViewById(R.id.tv_sport_type);
        ivBack = view.findViewById(R.id.iv_back);
        tvShareCancle = view.findViewById(R.id.tv_sharing_cancle);


        itemViewSpeed = view.findViewById(R.id.item_speed);
        itemViewTime = view.findViewById(R.id.item_time);
        itemViewCal = view.findViewById(R.id.item_cal);

        ivSportType = view.findViewById(R.id.iv_sport_type);
        ivBottom = view.findViewById(R.id.iv_bottom);
        tvDetail = view.findViewById(R.id.detail);
        tvShare = view.findViewById(R.id.share);


        ivQQ = view.findViewById(R.id.iv_qq);
        ivWebo = view.findViewById(R.id.iv_weibo);
        ivWechat = view.findViewById(R.id.iv_wechat);
        ivFriend = view.findViewById(R.id.iv_friend);

        layoutShare = view.findViewById(R.id.ll_history_share);

        // titleBarView.setRightIcon(R.drawable.icon_white_share);


    }

    @Override
    protected void initData() {
        sportType = getIntent().getIntExtra("sporttype", 1);
        sumData = (SportSumData) getIntent().getSerializableExtra("SportSumData");
        publishid = getIntent().getStringExtra("publishid");
        EventBus.getDefault().post(MessageEvent.SPORT_UPDATE_SUCESS);
        if (sumData != null) {
            itemViewSpeed.setValueText(sumData.getAvgPace());
            itemViewCal.setValueText(sumData.getCalories());
            itemViewTime.setValueText(TimeUtil.getTimerFormatedStrings(sumData.getTimeLong() * 1000, 0));
            tvDate.setText(TimeUtils.getTimeByMMDDHHMMSS
                    (sumData.getStartTimestamp()));
            tvDis.setText(sumData.getDistance());
        }

        if (sportType == JkConfiguration.SportType.sportIndoor) {
            ivSportType.setVisibility(View.VISIBLE);
            ivBottom.setVisibility(View.GONE);

        } else {
            ivSportType.setVisibility(View.INVISIBLE);
            ivBottom.setVisibility(View.VISIBLE);

        }

        switch (sportType) {
            case JkConfiguration.SportType.sportIndoor:
                tvTitle.setText(UIUtils.getString(R.string.treadmill));
                break;
            case JkConfiguration.SportType.sportOutRuning:
                tvTitle.setText(UIUtils.getString(R.string.outdoor_running));
                break;
            case JkConfiguration.SportType.sportWalk:
                tvTitle.setText(UIUtils.getString(R.string.walking));
                break;
            case JkConfiguration.SportType.sportBike:
                tvTitle.setText(UIUtils.getString(R.string.tdoor_cycling));
                break;

        }
        mActPresenter.getSportSummarDataByPrimaryKey(publishid + "");


    }

    @Override
    protected void initEvent() {

        ivQQ.setOnClickListener(this);
        ivWebo.setOnClickListener(this);
        ivWechat.setOnClickListener(this);
        ivFriend.setOnClickListener(this);

        ivBack.setOnClickListener(this);

        tvShare.setOnClickListener(this);
        tvDetail.setOnClickListener(this);

        tvShareCancle.setOnClickListener(this);

    }

    @Override
    protected void initHeader() {
      //  StatusBarCompat.setStatusBarColor(this, getResources().getColor(com.isport.brandapp.basicres.R.color.common_view_color));
    }

    @Override
    protected EndSportPresent createPresenter() {
        return new EndSportPresent(this);
    }

    @Override
    public void successSummarData(SportSumData sumData) {

        EventBus.getDefault().post(new MessageEvent(MessageEvent.SPORT_UPDATE_SUCESS));

        this.sumData = sumData;
        if (sumData != null) {
            if (sportType == JkConfiguration.SportType.sportBike) {
                itemViewSpeed.setValueText(TextUtils.isEmpty(sumData.getAvgSpeed()) ? "0" : sumData.getAvgSpeed());
                itemViewSpeed.setBottomText(R.string.unit_speed);
            } else {
                itemViewSpeed.setValueText(TextUtils.isEmpty(sumData.getAvgPace()) ? "00'00\"'" : sumData.getAvgPace());
                itemViewSpeed.setBottomText(R.string.pace);
            }

            itemViewTime.setValueText(TimeUtil.getTimerFormatedStrings(sumData.getTimeLong() * 1000, 0));
            tvDate.setText(TimeUtils.getTimeByYYMMDDHHMM
                    (sumData.getEndTimestamp()));
            tvDis.setText(CommonDateUtil.formatTwoPoint(Float.parseFloat(sumData.getDistance())));
            itemViewCal.setValueText(CommonDateUtil.formatInterger(Float.parseFloat(sumData.getCalories())));
        }

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_sharing_cancle:
                layoutShare.setVisibility(View.GONE);
                break;
            case R.id.iv_back:
                finish();
                break;
            case R.id.share:
                layoutShare.setVisibility(View.VISIBLE);
                break;

            case R.id.detail:
                Intent intent = new Intent(context, ActivityWebView.class);
                intent.putExtra("title", UIUtils.getString(R.string.sport_dtail));
                intent.putExtra("url", sumData.getDataUrl());
                intent.putExtra("sumData", sumData);
                startActivity(intent);
                break;
            case R.id.iv_qq:

                if (PackageUtil.isWxInstall(EndSportActivity.this, PackageUtil.qqPakage)) {
                    share("qq", sumData.getShareUrl());
                } else {
                    ToastUtil.showTextToast(EndSportActivity.this, UIUtils.getString(R.string.please_install_software));
                    return;
                }

                break;
            case R.id.iv_friend:
                if (PackageUtil.isWxInstall(EndSportActivity.this, PackageUtil.weichatPakage)) {
                    share("friend", sumData.getShareUrl());
                } else {

                    ToastUtil.showTextToast(EndSportActivity.this, UIUtils.getString(R.string.please_install_software));
                }

                break;
            case R.id.iv_wechat:
                if (PackageUtil.isWxInstall(EndSportActivity.this, PackageUtil.weichatPakage)) {
                    share("wechat", sumData.getShareUrl());
                } else {

                    ToastUtil.showTextToast(EndSportActivity.this, UIUtils.getString(R.string.please_install_software));
                }

                break;
            case R.id.iv_weibo:
                if (PackageUtil.isWxInstall(EndSportActivity.this, PackageUtil.weiboPakage)) {
                    share("weibo", sumData.getShareUrl());
                } else {
                    ToastUtil.showTextToast(EndSportActivity.this, UIUtils.getString(R.string.please_install_software));
                    return;
                }

                break;
        }
    }

    private void share(String type, String url) {
        layoutShare.setVisibility(View.GONE);
        SHARE_MEDIA share_media = SHARE_MEDIA.QQ;
        switch (type) {
            case "qq":
                share_media = SHARE_MEDIA.QQ;
                break;
            case "wechat":
                share_media = SHARE_MEDIA.WEIXIN;

                break;
            case "friend":
                share_media = SHARE_MEDIA.WEIXIN_CIRCLE;

                break;
            case "weibo":
                share_media = SHARE_MEDIA.SINA;

                break;
        }
        UMWeb umWeb = new UMWeb(url);
        umWeb.setTitle(UIUtils.getString(R.string.share_title));
        umWeb.setDescription(String.format(UIUtils.getString(R.string.share_content), sumData.getDistance(), sumData.getCalories()));
        umWeb.setThumb(new UMImage(this, R.drawable.ic_t_launcher));
        //UMImage image = ShareHelper.getUMWeb(EndSportActivity.this, file);*/
        new ShareAction(EndSportActivity.this).setPlatform(share_media)
                .withMedia(umWeb)
                .setCallback(EndSportActivity.this)
                .share();
    }

    @Override
    public void onStart(SHARE_MEDIA share_media) {

    }

    @Override
    public void onResult(SHARE_MEDIA share_media) {

    }

    @Override
    public void onError(SHARE_MEDIA share_media, Throwable throwable) {

    }

    @Override
    public void onCancel(SHARE_MEDIA share_media) {

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
