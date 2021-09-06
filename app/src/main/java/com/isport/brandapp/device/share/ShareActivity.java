package com.isport.brandapp.device.share;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.os.Build;
import androidx.core.content.FileProvider;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.share.model.SharePhoto;
import com.facebook.share.model.SharePhotoContent;
import com.facebook.share.widget.ShareDialog;
import com.isport.brandapp.App;
import com.isport.brandapp.R;
import brandapp.isport.com.basicres.commonbean.UserInfoBean;
import com.isport.brandapp.device.PremissionUtil;
import com.isport.brandapp.device.PremissionUtil.OnResult;
import com.isport.brandapp.login.ActivityLogin;
import com.isport.brandapp.util.AppSP;
import com.isport.brandapp.util.ShareHelper;
import com.isport.brandapp.util.UserAcacheUtil;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.File;

import bike.gymproject.viewlibray.ShareItemView;
import brandapp.isport.com.basicres.ActivityManager;
import brandapp.isport.com.basicres.BaseActivity;
import brandapp.isport.com.basicres.net.userNet.CommonUserAcacheUtil;
import phone.gym.jkcq.com.commonres.common.JkConfiguration;
import brandapp.isport.com.basicres.commonutil.AppUtil;
import brandapp.isport.com.basicres.commonutil.LoadImageUtil;
import brandapp.isport.com.basicres.commonutil.MessageEvent;
import brandapp.isport.com.basicres.commonutil.ToastUtils;
import brandapp.isport.com.basicres.commonutil.TokenUtil;
import brandapp.isport.com.basicres.commonutil.UIUtils;
import brandapp.isport.com.basicres.commonview.RoundImageView;
import brandapp.isport.com.basicres.service.observe.BleProgressObservable;
import brandapp.isport.com.basicres.service.observe.NetProgressObservable;

public class ShareActivity extends BaseActivity implements View.OnClickListener, OnResult, UMShareListener {
    private LinearLayout layoutContent;
    private RelativeLayout layoutAll;
    private ShareItemView itemCentre;
    private ShareItemView itemOne;
    private ShareItemView itemTwo;
    private ShareItemView itemThree;
    private RoundImageView ivHead;
    private TextView tvName;
    private ImageView ivQQ, ivWechat, ivWebo, ivFriend, ivFacebook, ivShareMore;
    private UserInfoBean infoBean;
    private View viewZh;

    private PremissionUtil util;

    private ShareBean shareBean;

    private File file;
    private LinearLayout layout_bottom;
    private ImageView ivBack;
    private LinearLayout layoutStep;
    private TextView tvStepType, tvStepTime;

    @Override
    protected int getLayoutId() {
        return R.layout.app_activity_device_share;
    }

    @Override
    protected void initView(View view) {

        layoutAll = view.findViewById(R.id.layout_all);
        ivBack = view.findViewById(R.id.iv_back);
        layout_bottom = view.findViewById(R.id.layout_bottom);
        viewZh = view.findViewById(R.id.view_zh);
        layoutContent = view.findViewById(R.id.layout_share_content);
        layoutStep = view.findViewById(R.id.layout_step_share);
        tvStepType = view.findViewById(R.id.step_type);
        tvStepTime = view.findViewById(R.id.step_time);
        itemCentre = view.findViewById(R.id.item_centre);
        itemOne = view.findViewById(R.id.item_one);
        itemTwo = view.findViewById(R.id.item_two);
        itemThree = view.findViewById(R.id.item_three);


        ivHead = view.findViewById(R.id.iv_head);
        tvName = view.findViewById(R.id.tv_name);

        ivQQ = view.findViewById(R.id.iv_qq);
        ivWebo = view.findViewById(R.id.iv_weibo);
        ivWechat = view.findViewById(R.id.iv_wechat);
        ivFriend = view.findViewById(R.id.iv_friend);
        ivShareMore = view.findViewById(R.id.iv_more);

        ivFacebook = view.findViewById(R.id.iv_facebook);
        //ivTwitter = view.findViewById(R.id.iv_twitter);
        if (App.getApp().isUSA()) {
            viewZh.setVisibility(View.GONE);
            ivFacebook.setVisibility(View.VISIBLE);
            ivWebo.setVisibility(View.GONE);
            ivFriend.setVisibility(View.GONE);
            // layout_bottom.setVisibility(View.INVISIBLE);
            // layout_bottom_us.setVisibility(View.VISIBLE);
        } else {
            viewZh.setVisibility(View.VISIBLE);
            ivFacebook.setVisibility(View.GONE);
            ivWebo.setVisibility(View.VISIBLE);
            ivFriend.setVisibility(View.VISIBLE);
        }
    }

    @Override
    protected void initData() {
        getIntentValue();

        util = new PremissionUtil();


        infoBean = CommonUserAcacheUtil.getUserInfo(TokenUtil.getInstance().getPeopleIdStr(this));
        if (infoBean != null) {
            if (App.isHttp()) {
                LoadImageUtil.getInstance().loadCirc(context, infoBean.getHeadUrl(), ivHead);
            } else {
                if (!TextUtils.isEmpty(infoBean.getHeadUrl())) {
                    ivHead.setImageBitmap(BitmapFactory.decodeFile(infoBean.getHeadUrl()));
                }
            }
            tvName.setText(infoBean.getNickName());
        }

        if (currentShareDeviceType == JkConfiguration.DeviceType.WATCH_W516 || currentShareDeviceType == JkConfiguration.DeviceType.BRAND_W311) {


            itemCentre.setVisibility(View.GONE);
            layoutStep.setVisibility(View.VISIBLE);
            if (shareBean == null) {
                return;
            }
            tvStepType.setText(shareBean.centerValue);
            tvStepTime.setText(shareBean.time);

           /* itemCentre.setBottomText(shareBean.time);

            itemCentre.setValueText(shareBean.centerValue);*/
            /*itemCentre.setTitleText(R.string.last_step_recor);
            itemCentre.setUnitText(R.string.unit_steps);
*/

            /**
             *  <string name="app_share_goal_rate">目标比例</string>
             <string name="app_share_goal_dis">里程</string>
             <string name="app_share_goal_dis_unit">米</string>
             <string name="app_share_goal_cal">消耗</string>
             <string name="app_share_goal_cal_unit">千卡</string>
             */

            if (shareBean.isWeek) {
                itemOne.setBottomText(UIUtils.getString(R.string.avg_step));
                itemTwo.setBottomText(UIUtils.getString(R.string.avg_dis));
                itemThree.setBottomText(UIUtils.getString(R.string.avg_cal));
            } else {
                itemOne.setBottomText(R.string.today_step);
                itemTwo.setBottomText(R.string.today_dis);
                itemThree.setBottomText(R.string.today_cal);
            }
         /*   itemOne.setBottomText(R.string.today_step);
            itemTwo.setBottomText(R.string.today_dis);
            itemThree.setBottomText(R.string.today_cal);*/

            itemOne.setUnitText(UIUtils.getString(R.string.step_unit));
            itemTwo.setUnitText(R.string.app_share_goal_dis_unit);
            itemThree.setUnitText(R.string.app_share_goal_cal_unit);


            itemOne.setTitleIcon();
            itemTwo.setTitleIcon();
            itemThree.setTitleIcon();


        } else if (currentShareDeviceType == JkConfiguration.DeviceType.SLEEP) {
            itemCentre.setVisibility(View.VISIBLE);
            layoutStep.setVisibility(View.GONE);
            itemCentre.setTitleText(R.string.total_sleep_duration);

            /**
             * <string name="app_share_sleep">睡眠</string>
             <string name="app_share_sleep_deep">深睡</string>
             <string name="app_share_go_sleep_duration">入睡时长</string>
             */

            itemOne.setBottomText(R.string.deep_sleep);
            itemTwo.setBottomText(R.string.light_sleep);
            itemThree.setBottomText(R.string.awake_sleep);

            itemOne.setTitleIcon(R.drawable.icon_share_sleep_deep);
            itemTwo.setTitleIcon(R.drawable.icon_share_sleep);
            itemThree.setTitleIcon(R.drawable.icon_share_sleep_duration);
        }
        if (currentShareDeviceType == JkConfiguration.DeviceType.HEART_RATE) {
            itemCentre.setVisibility(View.VISIBLE);
            layoutStep.setVisibility(View.GONE);
            itemCentre.setTitleText(R.string.watch_heart_rate_current);
            itemCentre.setUnitText(R.string.unit_time_per_minute);


            /**
             *  <string name="app_share_goal_rate">目标比例</string>
             <string name="app_share_goal_dis">里程</string>
             <string name="app_share_goal_dis_unit">米</string>
             <string name="app_share_goal_cal">消耗</string>
             <string name="app_share_goal_cal_unit">千卡</string>
             */

            itemOne.setBottomText(R.string.watch_heart_rate_maxium);
            itemTwo.setBottomText(R.string.watch_heart_rate_minium);
            itemThree.setBottomText(R.string.watch_heart_rate_average);

            itemOne.setUnitText(R.string.unit_time_per_minute);
            itemTwo.setUnitText(R.string.unit_time_per_minute);
            itemThree.setUnitText(R.string.unit_time_per_minute);


            itemOne.setTitleIcon(R.drawable.watch_heart_rate_max);
            itemTwo.setTitleIcon(R.drawable.watch_heart_rate_min);
            itemThree.setTitleIcon(R.drawable.watch_heart_rate_avg);


        } else {

        }

        if (shareBean == null) {
            return;
        }
        if (currentShareDeviceType != JkConfiguration.DeviceType.WATCH_W516) {
            itemCentre.setBottomText(shareBean.time);

            itemCentre.setValueText(shareBean.centerValue);
        }

        itemOne.setValueText(shareBean.one);
        itemTwo.setValueText(shareBean.two);
        itemThree.setValueText(shareBean.three);

        util.setOnResultLister(this);

    }

    int currentShareDeviceType;

    private void getIntentValue() {
        currentShareDeviceType = getIntent().getIntExtra(JkConfiguration.FROM_TYPE, JkConfiguration.DeviceType.WATCH_W516);
        shareBean = getIntent().getParcelableExtra(JkConfiguration.FROM_BEAN);
    }

    @Override
    protected void initEvent() {
        ivQQ.setOnClickListener(this);
        ivWebo.setOnClickListener(this);
        ivWechat.setOnClickListener(this);
        ivFriend.setOnClickListener(this);
        ivShareMore.setOnClickListener(this);

        ivFacebook.setOnClickListener(this);
        //ivTwitter.setOnClickListener(this);

        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


    }

    @Override
    protected void initHeader() {
       // StatusBarCompat.setStatusBarColor(getWindow(), getResources().getColor(R.color.transparent), true);
    }

    public void hideView() {
        ivBack.setVisibility(View.INVISIBLE);
        if (AppUtil.isZh(this)) {
            layout_bottom.setVisibility(View.INVISIBLE);
        }
    }

    public void showView() {
        ivBack.setVisibility(View.VISIBLE);
        if (AppUtil.isZh(this)) {
            layout_bottom.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_qq:

                if (PackageUtil.isWxInstall(ShareActivity.this, PackageUtil.qqPakage)) {
                    hideView();
                    util.checkCameraPersiomm(this, this, layoutAll, "qq");
                } else {
                    ToastUtils.showToast(ShareActivity.this, UIUtils.getString(R.string.please_install_software));
                    return;
                }

                break;
            case R.id.iv_friend:
                if (PackageUtil.isWxInstall(ShareActivity.this, PackageUtil.weichatPakage)) {
                    hideView();
                    util.checkCameraPersiomm(this, this, layoutAll, "friend");
                } else {

                    ToastUtils.showToast(ShareActivity.this, UIUtils.getString(R.string.please_install_software));
                }

                break;
            case R.id.iv_wechat:
                if (PackageUtil.isWxInstall(ShareActivity.this, PackageUtil.weichatPakage)) {
                    hideView();
                    util.checkCameraPersiomm(this, this, layoutAll, "wechat");
                } else {

                    ToastUtils.showToast(ShareActivity.this, UIUtils.getString(R.string.please_install_software));
                }

                break;
            case R.id.iv_weibo:
                if (PackageUtil.isWxInstall(ShareActivity.this, PackageUtil.weiboPakage)) {
                    hideView();
                    util.checkCameraPersiomm(this, this, layoutAll, "weibo");
                } else {
                    ToastUtils.showToast(ShareActivity.this, UIUtils.getString(R.string.please_install_software));
                    return;
                }

                break;
            case R.id.iv_facebook:
                //判断facebook是否安装，没有按钮
                if (PackageUtil.isWxInstall(ShareActivity.this, PackageUtil.facebookPakage)) {
                    hideView();
                    util.checkCameraPersiomm(this, this, layoutAll, "facebook");
                } else {
                    ToastUtils.showToast(ShareActivity.this, UIUtils.getString(R.string.please_install_software));
                    return;
                }
                break;

            case R.id.iv_more:
                hideView();
                util.checkCameraPersiomm(this, this, layoutAll, "more");
                break;

        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        showView();
    }

    @Override
    public void OnResultYes(File file, String type) {
        this.file = file;

        if (type.equals("more")) {
            shareFile(file);
        }
        if (type.equals("facebook") || type.equals("twitter")) {
            shareFaceBook();
        } else {
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
            UMImage image = ShareHelper.getUMWeb(ShareActivity.this, file);
            new ShareAction(ShareActivity.this).setPlatform(share_media)
                    .withMedia(image)
                    .setCallback(ShareActivity.this)
                    .share();
        }
    }

    /**
     * 分享到facebook
     */
    private CallbackManager callBackManager;

    public void shareFaceBook() {
        Bitmap image = BitmapFactory.decodeFile(file.getPath());
        callBackManager = CallbackManager.Factory.create();
        ShareDialog shareDialog = new ShareDialog(this);
        shareDialog.registerCallback(callBackManager, facebookCallback);
        SharePhoto photo = new SharePhoto.Builder()
                .setBitmap(image)
                .build();
        SharePhotoContent content = new SharePhotoContent.Builder()
                .addPhoto(photo)
                .build();
        shareDialog.show(content);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (callBackManager != null)
            callBackManager.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void OnresultNo(File file) {
        this.file = file;
    }

    @Override
    public void onStart(SHARE_MEDIA share_media) {

    }

    @Override
    public void onResult(SHARE_MEDIA share_media) {
        //ToastUtil.showTextToast(ShareActivity.this, share_media + " 分享成功啦");
        //  finish();
    }

    @Override
    public void onError(SHARE_MEDIA share_media, Throwable throwable) {
        //ToastUtil.showTextToast(ShareActivity.this, share_media + " 分享失败啦");
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

    /**
     * facebook分享状态回调
     */
    private FacebookCallback facebookCallback = new FacebookCallback() {

        @Override
        public void onSuccess(Object o) {
//            SysoutUtil.out("onSuccess" + o.toString());
//            Message msg = Message.obtain();
//            msg.what = SHARE_COMPLETE;
//            mHandler.sendMessage(msg);
        }

        @Override
        public void onCancel() {
//            SysoutUtil.out("onCancel");
//            Message msg = Message.obtain();
//            msg.what = SHARE_CANCEL;
//            mHandler.sendMessage(msg);
        }

        @Override
        public void onError(FacebookException error) {
//            SysoutUtil.out("onError");
//            ToastUtils.showToast("share error--" + error.getMessage());
//            Message msg = Message.obtain();
//            msg.what = SHARE_ERROR;
//            mHandler.sendMessage(msg);
        }
    };


    public void shareFile(File file) {
        if (null != file && file.exists()) {
            Intent share = new Intent(Intent.ACTION_SEND);
            Uri uri = null;
            // 判断版本大于等于7.0
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                // "项目包名.fileprovider"即是在清单文件中配置的authorities
                uri = FileProvider.getUriForFile(ShareActivity.this, getPackageName() + ".fileprovider",
                        file);
                share.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            } else {
                uri = Uri.fromFile(file);
            }
            share.putExtra(Intent.EXTRA_STREAM, uri);
            share.setType(getMimeType(file.getAbsolutePath()));//此处可发送多种文件
            share.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            share.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            context.startActivity(Intent.createChooser(share, "Share Image"));
        }
    }

    // 根据文件后缀名获得对应的MIME类型。
    private static String getMimeType(String filePath) {
        MediaMetadataRetriever mmr = new MediaMetadataRetriever();
        String mime = "image/*";
       /* if (filePath != null) {
            try {
                mmr.setDataSource(filePath);
                mime = mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_MIMETYPE);
            } catch (IllegalStateException e) {
                return mime;
            } catch (IllegalArgumentException e) {
                return mime;
            } catch (RuntimeException e) {
                return mime;
            }
        }*/
        return mime;
    }


}
