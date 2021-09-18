package com.isport.brandapp.device.scale;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.isport.blelibrary.db.table.scale.Scale_FourElectrode_DataModel;
import com.isport.blelibrary.utils.CommonDateUtil;
import com.isport.blelibrary.utils.Logger;
import com.isport.brandapp.App;
import com.isport.brandapp.R;
import com.isport.brandapp.banner.recycleView.utils.ToastUtil;
import com.isport.brandapp.device.scale.adpter.AdapterScaleReport;
import com.isport.brandapp.device.scale.bean.ScaleBean;
import com.isport.brandapp.device.scale.bean.ScaleReportBean;
import com.isport.brandapp.device.scale.presenter.ScaleReportPresenter;
import com.isport.brandapp.device.scale.view.BarView;
import com.isport.brandapp.device.scale.view.ScaleReportView;
import com.isport.brandapp.device.share.PackageUtil;
import com.isport.brandapp.home.bean.http.Fatsteelyard;
import com.isport.brandapp.login.ActivityLogin;
import com.isport.brandapp.util.AppSP;
import com.isport.brandapp.util.ShareHelper;
import com.isport.brandapp.util.UserAcacheUtil;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import androidx.core.content.FileProvider;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.LinearLayoutManager;
import bike.gymproject.viewlibray.pickerview.utils.DateUtils;
import brandapp.isport.com.basicres.ActivityManager;
import brandapp.isport.com.basicres.commonbean.UserInfoBean;
import brandapp.isport.com.basicres.commonpermissionmanage.PermissionGroup;
import brandapp.isport.com.basicres.commonpermissionmanage.PermissionManageUtil;
import brandapp.isport.com.basicres.commonrecyclerview.FullyLinearLayoutManager;
import brandapp.isport.com.basicres.commonrecyclerview.RefreshRecyclerView;
import brandapp.isport.com.basicres.commonutil.FileUtil;
import brandapp.isport.com.basicres.commonutil.LoadImageUtil;
import brandapp.isport.com.basicres.commonutil.MessageEvent;
import brandapp.isport.com.basicres.commonutil.ThreadPoolUtils;
import brandapp.isport.com.basicres.commonutil.ToastUtils;
import brandapp.isport.com.basicres.commonutil.TokenUtil;
import brandapp.isport.com.basicres.commonutil.UIUtils;
import brandapp.isport.com.basicres.commonview.RoundImageView;
import brandapp.isport.com.basicres.mvp.BaseMVPActivity;
import brandapp.isport.com.basicres.net.userNet.CommonUserAcacheUtil;
import brandapp.isport.com.basicres.service.observe.BleProgressObservable;
import brandapp.isport.com.basicres.service.observe.NetProgressObservable;
import id.zelory.compressor.Compressor;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import phone.gym.jkcq.com.commonres.commonutil.DisplayUtils;

public class ActivityScaleReport extends BaseMVPActivity<ScaleReportView, ScaleReportPresenter>
        implements ScaleReportView, View.OnClickListener, UMShareListener {

    TextView tvTitle, tvReportUserName, tvReportTime;
    ImageView ivBack, ivShare;
    RoundImageView ivHead;

    UserInfoBean infoBean;
    BarView barView;
    View head_view;

    RefreshRecyclerView refreshRecyclerView;
    AdapterScaleReport adapter;

    ArrayList<ScaleBean> lists = new ArrayList<>();

    TextView tvHeadStande, tvGrade;

    View headView, viewShareLayout;
    private View viewZh;

    //ImageView ivTestPic;

    TextView tvWeight;
    TextView tvBMIValue;
    private RelativeLayout llHistoryShare;
    private Fatsteelyard mMFatsteelyard;

    private File picFile = null;
    private NestedScrollView scrollView;
    private Scale_FourElectrode_DataModel mScale_fourElectrode_dataModel;
    private View rl_scale_report_head;
    private ImageView ivQQ, ivWechat, ivWebo, ivFriend, ivFacebook, ivShareMore;

    @Override
    protected ScaleReportPresenter createPresenter() {
        return new ScaleReportPresenter(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.app_activity_scale_report;
    }

    @Override
    protected void initView(View view) {
        ivBack = view.findViewById(R.id.iv_setting);
        head_view = view.findViewById(R.id.head_view);
        rl_scale_report_head = view.findViewById(R.id.rl_scale_report_head);
        RelativeLayout.LayoutParams ivSsettingLP = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, DisplayUtils.dip2px(context, 40));
        if (App.isPerforatedPanel()) {
            head_view.setVisibility(View.VISIBLE);
        }

        ivShare = view.findViewById(R.id.iv_edit);
        ivHead = view.findViewById(R.id.iv_head);
        scrollView = view.findViewById(R.id.layout);
        viewShareLayout = view.findViewById(R.id.layout_view);
        tvTitle = view.findViewById(R.id.tv_title_name);
        tvReportUserName = view.findViewById(R.id.tv_report_name);
        tvReportTime = view.findViewById(R.id.tv_report_time);
        refreshRecyclerView = view.findViewById(R.id.recycler_result);
        barView = view.findViewById(R.id.barview);
        tvBMIValue = view.findViewById(R.id.tv_bmi_value);
        tvWeight = view.findViewById(R.id.weight);
        llHistoryShare = view.findViewById(R.id.ll_history_share);
        //ivTestPic = view.findViewById(R.id.test_pic);

        ivShare.setImageResource(R.drawable.icon_white_share);
        ivBack.setImageResource(R.drawable.icon_white_back);
        tvReportTime.setVisibility(View.VISIBLE);
        tvReportUserName.setVisibility(View.VISIBLE);


        ivQQ = view.findViewById(R.id.iv_qq);
        ivWebo = view.findViewById(R.id.iv_weibo);
        ivWechat = view.findViewById(R.id.iv_wechat);
        ivFriend = view.findViewById(R.id.iv_friend);
        ivShareMore = view.findViewById(R.id.iv_more);
        viewZh = view.findViewById(R.id.view_zh);

        ivFacebook = view.findViewById(R.id.iv_facebook);


        headView = LayoutInflater.from(context).inflate(R.layout.item_scale_head, null, false);
        tvGrade = headView.findViewById(R.id.tv_grade);
        tvHeadStande = headView.findViewById(R.id.tv_head_stande);


        adapter = new AdapterScaleReport(ActivityScaleReport.this);
        //TODO 俱乐部名称 recycler_club_content
        FullyLinearLayoutManager mClubFullyLinearLayoutManager = new FullyLinearLayoutManager(context);
        mClubFullyLinearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        refreshRecyclerView.setLayoutManager(mClubFullyLinearLayoutManager);
        //headerBannerView = (CommonBannerView) headerView.findViewById(R.id.main_home_banner);
        refreshRecyclerView.addHeaderView(headView);

        refreshRecyclerView.setAdapter(adapter);

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
        long reportId = 0;
        mScale_fourElectrode_dataModel = (Scale_FourElectrode_DataModel) getIntent().getSerializableExtra
                ("mScale_fourElectrode_dataModel");
        boolean isDBData;
        if (mScale_fourElectrode_dataModel != null) {
            reportId = mScale_fourElectrode_dataModel.getTimestamp();
            isDBData = true;
        } else {
            mMFatsteelyard = (Fatsteelyard) getIntent().getSerializableExtra("mMFatsteelyard");
            if (mMFatsteelyard == null) {
                long fatSteelyardId = getIntent().getLongExtra("fatSteelyardId", 0);
                isDBData = true;
                reportId = fatSteelyardId;
            } else {
                isDBData = false;
                reportId = mMFatsteelyard.getReportId();
            }
        }
        Logger.myLog("reportId == " + reportId);
//        mActPresenter.getScaleReport(reportId,isDBData);
        mActPresenter.getScaleReport(mScale_fourElectrode_dataModel, isDBData, reportId);

        // TODO: 2019/1/14 头像部分要修改
        infoBean = CommonUserAcacheUtil.getUserInfo(TokenUtil.getInstance().getPeopleIdStr(this));
        if (infoBean != null) {
            if (App.appType() == App.httpType) {
                LoadImageUtil.getInstance().loadCirc(context, infoBean.getHeadUrl(), ivHead);
            } else {
                if (!TextUtils.isEmpty(infoBean.getHeadUrl())) {
                    ivHead.setImageBitmap(BitmapFactory.decodeFile(infoBean.getHeadUrl()));
                }
            }
            tvReportUserName.setText(infoBean.getNickName());
        }
        /**
         * 传过来的时候把BMI的值在barview里头进行设置,体重值也可以从测试页面返回
         */
        refreshRecyclerView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


            }
        });

        picFile = null;
    }

    @Override
    protected void initEvent() {
        ivBack.setOnClickListener(this);
        ivShare.setOnClickListener(this);
        viewShareLayout.setOnClickListener(this);

    }

    @Override
    protected void initHeader() {
        //  StatusBarCompat.setStatusBarColor(getWindow(), getResources().getColor(R.color.transparent), false);
    }

    private void updateData() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_facebook:
                //判断facebook是否安装，没有按钮
                if (PackageUtil.isWxInstall(ActivityScaleReport.this, PackageUtil.facebookPakage)) {
                    sharePlat(picFile, SHARE_MEDIA.FACEBOOK);
                } else {
                    ToastUtils.showToast(ActivityScaleReport.this, UIUtils.getString(R.string.please_install_software));
                    return;
                }
                break;
            case R.id.iv_setting:
                finish();
                break;
            case R.id.iv_edit:
                //分享进行截屏，然后显示图标
                checkCameraPersiomm();
                break;
            case R.id.iv_wechat:
                llHistoryShare.setVisibility(View.GONE);
                if (PackageUtil.isWxInstall(this, PackageUtil.weichatPakage)) {
                    sharePlat(picFile, SHARE_MEDIA.WEIXIN);
                } else {
                    ToastUtil.showTextToast(this, "请安装对应软件");
                }
                break;
            case R.id.iv_friend:         //友盟,分享活动到第三方
                llHistoryShare.setVisibility(View.GONE);
                if (PackageUtil.isWxInstall(this, PackageUtil.weichatPakage)) {
                    sharePlat(picFile, SHARE_MEDIA.WEIXIN_CIRCLE);
                } else {
                    ToastUtil.showTextToast(this, "请安装对应软件");
                }
                break;
            case R.id.iv_qq:         //友盟,分享活动到第三方
                llHistoryShare.setVisibility(View.GONE);
                if (PackageUtil.isWxInstall(this, PackageUtil.qqPakage)) {
                    sharePlat(picFile, SHARE_MEDIA.QQ);
                } else {
                    ToastUtil.showTextToast(this, "请安装对应软件");
                }
                break;
            case R.id.iv_weibo:
                llHistoryShare.setVisibility(View.GONE);
                if (PackageUtil.isWxInstall(this, PackageUtil.weiboPakage)) {
                    sharePlat(picFile, SHARE_MEDIA.SINA);
                } else {
                    ToastUtil.showTextToast(this, "请安装对应软件");
                }
                break;
            case R.id.tv_sharing_cancle:
                llHistoryShare.setVisibility(View.GONE);
                break;
            case R.id.iv_more:
                llHistoryShare.setVisibility(View.GONE);
                shareFile(picFile);
                //util.checkCameraPersiomm(this, this, layoutAll, "more");
                break;
        }
    }


    public void shareFile(File file) {
        if (null != file && file.exists()) {
            Intent share = new Intent(Intent.ACTION_SEND);
            Uri uri = null;
            // 判断版本大于等于7.0
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                // "项目包名.fileprovider"即是在清单文件中配置的authorities
                uri = FileProvider.getUriForFile(ActivityScaleReport.this, getPackageName() + ".fileprovider",
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

    /**
     * 打开相机和存储权限
     */

    private void checkCameraPersiomm() {
        PermissionManageUtil permissionManage = new PermissionManageUtil(this);
        permissionManage.requestPermissionsGroup(new RxPermissions(this),
                PermissionGroup.CAMERA_STORAGE, new PermissionManageUtil
                        .OnGetPermissionListener() {
                    @Override
                    public void onGetPermissionYes() {
                        NetProgressObservable.getInstance().show();
                        ivShare.setVisibility(View.INVISIBLE);
                        ivBack.setVisibility(View.INVISIBLE);
                        ThreadPoolUtils.getInstance().addTask(new Runnable() {
                            @Override
                            public void run() {
                                // ThreadPoolUtils.getInstance().addTask(new ShootTask(scrollView,
                                // ActivityScaleReport.this, ActivityScaleReport.this));
                                picFile = getFullScreenBitmap(scrollView);
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        ivShare.setVisibility(View.VISIBLE);
                                        ivBack.setVisibility(View.VISIBLE);
                                        NetProgressObservable.getInstance().hide();
                                        llHistoryShare.setVisibility(View.VISIBLE);
                                        //ivTestPic.setImageBitmap(BitmapFactory.decodeFile(picFile.getAbsolutePath()));
                                    }
                                });
                                // initLuBanRxJava(getFullScreenBitmap(scrollView));
                            }
                        });


                    }

                    @Override
                    public void onGetPermissionNo() {


                    }
                });

    }

    Handler mHandler = new Handler();

    @Override
    public void getScaleReportSuccess(ScaleReportBean scaleReportBean) {
        tvReportTime.setText(DateUtils.getAMTime(scaleReportBean.getCreatTime()));
        lists.clear();
        ScaleBean scaleBean = scaleReportBean.getList().get(0);
        tvGrade.setText(scaleBean.getValue().split("_")[0]);
        // tvHeadStande.setText(scaleBean.getStandard());
        tvWeight.setText(scaleReportBean.getWeight().split("_")[0]);
        GradientDrawable drawable = (GradientDrawable) tvHeadStande.getBackground();
        drawable.setColor(Color.parseColor(scaleBean.color));
        tvHeadStande.setText(scaleBean.standard);
        tvHeadStande.setVisibility(View.VISIBLE);


        tvBMIValue.setText(String.format(getString(R.string.app_scale_bmi), CommonDateUtil.formatOnePoint(Double.valueOf(scaleReportBean.getBmi()))));

        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                barView.setCurrentBMIvalue(Float.valueOf(scaleReportBean.getBmi()) > 50 ? 50 : Float.valueOf
                        (scaleReportBean
                                .getBmi()));
                barView.postInvalidate();
            }
        }, 200);

       /* GradientDrawable drawable = (GradientDrawable) tvHeadStande.getBackground();
        drawable.setColor(Color.parseColor(scaleBean.getColor() == null ? "#666666" : scaleBean.getColor()));*/
        List<ScaleBean> list = scaleReportBean.getList();
        list.remove(0);
        Logger.myLog(list.toString());
        adapter.setData(list);
        adapter.notifyDataSetChanged();
    }


    private void initLuBanRxJava(File file) {
        new Compressor(this)
                .compressToFileAsFlowable(file)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<File>() {
                    @Override
                    public void accept(File file) {
                        picFile = file;
                        //ivTestPic.setImageBitmap(BitmapFactory.decodeFile(file.getAbsolutePath()));


                       /* //这里去上传图片
                        // LoadImageUtil.getInstance().displayImagePath(context, photoPath, ivPic);
                        ivPic.setImageBitmap(BitmapFactory.decodeFile(file.getAbsolutePath()));
                        final File[] compressFiles = new File[1];
                        compressFiles[0] = file;
                        mActPresenter.postPhotos(compressFiles);*/

                        //compressedImage = file;
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) {
                        throwable.printStackTrace();
                        // showError(throwable.getMessage());
                    }
                });

    }


    /**
     * 获取长截图
     *
     * @return
     */
    public File getFullScreenBitmap(NestedScrollView scrollVew) {
        int h = 0;
        Bitmap bitmap;
        for (int i = 0; i < scrollVew.getChildCount(); i++) {
            h += scrollVew.getChildAt(i).getHeight();
        }
        // 创建对应大小的bitmap
        bitmap = Bitmap.createBitmap(scrollVew.getWidth(), h,
                Bitmap.Config.ARGB_8888);
        final Canvas canvas = new Canvas(bitmap);
        canvas.drawColor(UIUtils.getColor(R.color.common_view_color));
        scrollVew.draw(canvas);

        //获取顶部布局的bitmap
        Bitmap head = Bitmap.createBitmap(rl_scale_report_head.getWidth(), rl_scale_report_head.getHeight(),
                Bitmap.Config.ARGB_8888);
        final Canvas canvasHead = new Canvas(head);
        canvasHead.drawColor(Color.WHITE);
        rl_scale_report_head.draw(canvasHead);
        Bitmap newbmp = Bitmap.createBitmap(scrollVew.getWidth(), h + head.getHeight(), Bitmap.Config
                .ARGB_8888);
        Canvas cv = new Canvas(newbmp);
        cv.drawBitmap(head, 0, 0, null);// 在 0，0坐标开始画入headBitmap
        cv.drawBitmap(bitmap, 0, head.getHeight(), null);// 在 0，headHeight坐标开始填充课表的Bitmap
        cv.save();// 保存
        cv.restore();// 存储
        //回收
        head.recycle();
        // 测试输出
        return FileUtil.writeImage(newbmp, FileUtil.getImageFile(FileUtil.getPhotoFileName()), 100);
    }


    @Override
    public void onStart(SHARE_MEDIA share_media) {

    }

    @Override
    public void onResult(SHARE_MEDIA share_media) {
        // ToastUtil.showTextToast(ActivityScaleReport.this, share_media + " 分享成功啦");
        //  finish();
    }

    @Override
    public void onError(SHARE_MEDIA share_media, Throwable throwable) {
        // ToastUtil.showTextToast(ActivityScaleReport.this, share_media + " 分享失败啦");
    }

    @Override
    public void onCancel(SHARE_MEDIA share_media) {

    }


    public void sharePlat(File file, SHARE_MEDIA share_media) {
        UMImage image = ShareHelper.getUMWeb(this, file);
        new ShareAction(this).setPlatform(share_media)
                .withMedia(image)
                .setCallback(this)
                .share();
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
