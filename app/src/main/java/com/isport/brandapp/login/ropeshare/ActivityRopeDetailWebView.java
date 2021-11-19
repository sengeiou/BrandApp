package com.isport.brandapp.login.ropeshare;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.os.Build;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebSettings.LayoutAlgorithm;
import android.webkit.WebSettings.ZoomDensity;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemChildClickListener;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.google.gson.Gson;
import com.isport.blelibrary.utils.Logger;
import com.isport.brandapp.App;
import com.isport.brandapp.R;
import com.isport.brandapp.banner.recycleView.utils.ToastUtil;
import com.isport.brandapp.device.share.PackageUtil;
import com.isport.brandapp.login.IJsCallback;
import com.isport.brandapp.login.LockableNestedScrollView;
import com.isport.brandapp.login.WebViewHelper;
import com.isport.brandapp.login.model.ResultWebData;
import com.isport.brandapp.ropeskipping.realsport.RealRopeSkippingActivity;
import com.isport.brandapp.sport.bean.SportSumData;
import com.isport.brandapp.util.ShareHelper;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMWeb;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.core.content.FileProvider;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import brandapp.isport.com.basicres.BaseTitleActivity;
import brandapp.isport.com.basicres.commonpermissionmanage.PermissionGroup;
import brandapp.isport.com.basicres.commonpermissionmanage.PermissionManageUtil;
import brandapp.isport.com.basicres.commonutil.FileUtil;
import brandapp.isport.com.basicres.commonutil.ThreadPoolUtils;
import brandapp.isport.com.basicres.commonutil.ToastUtils;
import brandapp.isport.com.basicres.commonutil.UIUtils;
import brandapp.isport.com.basicres.commonview.TitleBarView;
import brandapp.isport.com.basicres.service.observe.NetProgressObservable;
import phone.gym.jkcq.com.commonres.common.JkConfiguration;

//跳绳详情分享
public class ActivityRopeDetailWebView extends BaseTitleActivity implements UMShareListener, View.OnClickListener, IJsCallback {
    /**
     * 标题
     */
    private TextView title;
    /**
     * 协议
     */
    private WebView tk_webview_dark, tk_webview_def, tk_webview_light;
    private ProgressBar load_pro;
    private RelativeLayout circle_mainhtml_null;

    private String urldark, urlLigh;
    private String strTitle;
    private SportSumData sumData;
    private TextView tvShareCancle;
    private LinearLayout layout_share_pic;
    private LockableNestedScrollView scrollViewDark, scrollviewLight;

    RelativeLayout layoutShare;
    private LinearLayout layout_bottom;
    private LinearLayout layout_bottom_us;
    private View viewZh;
    private ImageView ivQQ, ivWechat, ivWebo, ivFriend, ivFacebook, ivShareMore;
    ;
    private View rl_scale_report_head;
    private RecyclerView recyclerview_message;
    ArrayList<ShareBean> list = new ArrayList<>();
    MessageAdapter mMessageAdapter;


    @Override
    protected int getLayoutId() {
        return R.layout.app_activity_rope_web;
    }


    @Override
    protected void initView(View view) {
        tk_webview_dark = view.findViewById(R.id.tk_webview);
        load_pro = view.findViewById(R.id.load_pro);

        ivQQ = view.findViewById(R.id.iv_qq);
        ivWebo = view.findViewById(R.id.iv_weibo);
        ivWechat = view.findViewById(R.id.iv_wechat);
        ivFriend = view.findViewById(R.id.iv_friend);
        tvShareCancle = view.findViewById(R.id.tv_sharing_cancle);
        ivShareMore = view.findViewById(R.id.iv_more);
        viewZh = view.findViewById(R.id.view_zh);
        ivFacebook = view.findViewById(R.id.iv_facebook);
        layoutShare = view.findViewById(R.id.ll_history_share);
        layout_bottom = view.findViewById(R.id.layout_bottom);
        scrollViewDark = view.findViewById(R.id.layout_pic_dark);
        scrollviewLight = view.findViewById(R.id.layout_pic_light);
        scrollviewLight = view.findViewById(R.id.layout_pic_light);
        layout_share_pic = view.findViewById(R.id.layout_share_pic);
        tk_webview_def = view.findViewById(R.id.tk_webview_def);
        tk_webview_light = view.findViewById(R.id.tk_webview_light);
        rl_scale_report_head = view.findViewById(R.id.rl_scale_report_head);
        recyclerview_message = view.findViewById(R.id.recyclerview_message);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, RecyclerView.HORIZONTAL, true);
        recyclerview_message.setLayoutManager(layoutManager);
        mMessageAdapter = new MessageAdapter(list);
        recyclerview_message.setAdapter(mMessageAdapter);
        mMessageAdapter.setOnItemChildClickListener(new OnItemChildClickListener() {
            @Override
            public void onItemChildClick(@NonNull BaseQuickAdapter adapter, @NonNull View view, int position) {

                Logger.myLog("onItemChildClick");

                for (int i = 0; i < list.size(); i++) {
                    list.get(i).isSelect = false;
                }
                list.get(position).isSelect = true;
                mMessageAdapter.notifyDataSetChanged();
            }
        });
        mMessageAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull BaseQuickAdapter<?, ?> adapter, @NonNull View view, int position) {
                Logger.myLog("setOnItemClickListener");
                for (int i = 0; i < list.size(); i++) {
                    list.get(i).isSelect = false;
                }
                list.get(position).isSelect = true;
                mMessageAdapter.notifyDataSetChanged();
            }
        });
        recyclerview_message.setVisibility(View.GONE);


    }

    String shareUrl = "";

    @Override
    protected void initData() {

        // url = getIntent().getStringExtra("url");
        urldark = getIntent().getStringExtra("urldark");
        urlLigh = getIntent().getStringExtra("urlLigh");
        Log.e("initData url:", "urldark =" + urldark + "urlLigh=" + urlLigh);
        strTitle = getIntent().getStringExtra("title");
        shareUrl = getIntent().getStringExtra("share_url");
        sumData = (SportSumData) getIntent().getSerializableExtra("sumData");

        initSettings(tk_webview_dark);
        initSettings(tk_webview_def);
        initSettings(tk_webview_light);
        titleBarView.setLeftIconEnable(true);
        titleBarView.setTitle(strTitle);
        titleBarView.setRightText("");
        tk_webview_dark.loadUrl(urldark);
        tk_webview_def.loadUrl(urlLigh);
        tk_webview_light.loadUrl(urlLigh);
        tk_webview_dark.setVerticalScrollBarEnabled(false);
        //tk_webview_dark.setLayerType(View.LAYER_TYPE_HARDWARE, null);//开启硬件加速
        tk_webview_def.setVerticalScrollBarEnabled(false);
        //tk_webview_def.setLayerType(View.LAYER_TYPE_HARDWARE, null);//开启硬件加速
        tk_webview_light.setVerticalScrollBarEnabled(false);
        //tk_webview_light.setLayerType(View.LAYER_TYPE_HARDWARE, null);//开启硬件加速
        hideRec();
        titleBarView.setRightIcon(R.drawable.icon_white_share);
        titleBarView.setRightIconVisible(false);

//        tk_webview.loadUrl(AllocationApi.BaseUrl + "/agreement/agreement.html");
     /*   if (NetUtils.hasNetwork(BaseApp.getApp())) {

//        http://192.168.10.203:8100/isport/concumer_basic/agreement/agreement.html
        } else {
            tk_webview.loadUrl("file:////android_asset/userAgreement.html");
        }*/
    }


    @Override
    protected void initEvent() {
        WebViewHelper.enableJs(tk_webview_dark, this);

        ivQQ.setOnClickListener(this);
        ivWebo.setOnClickListener(this);
        ivWechat.setOnClickListener(this);
        ivFriend.setOnClickListener(this);
        tvShareCancle.setOnClickListener(this);
        ivShareMore.setOnClickListener(this);
        ivFacebook.setOnClickListener(this);

        titleBarView.setOnTitleBarClickListener(new TitleBarView.OnTitleBarClickListener() {
            @Override
            public void onLeftClicked(View view) {
                finish();
            }

            @Override
            public void onRightClicked(View view) {

                //显示分享的的操作
                if (picDarkFile != null && picLightFile != null) {
                    showRec();
                } else {
                    checkCameraPersiomm();
                }
                layoutShare.setVisibility(View.VISIBLE);
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
        });
    }

    @Override
    protected void initHeader() {

    }

    @Override
    protected void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();
        tk_webview_dark.removeAllViews();
        destroyWebview();
    }


    /**
     * 解决Receiver not registered: android.widget.ZoomButtonsController
     */
    private void destroyWebview() {
        if (tk_webview_dark != null) {
            tk_webview_dark.getSettings().setBuiltInZoomControls(true);
            tk_webview_dark.setVisibility(View.GONE); // 把destroy()延后


//            long timeout = ViewConfiguration.getZoomControlsTimeout();
//            new Timer().schedule(new TimerTask() {
//                @Override
//                public void run() {
//                	tk_webview.destroy();
//                }
//            }, timeout);
            tk_webview_dark.post(new Runnable() {
                @Override
                public void run() {
                    tk_webview_dark.destroy();
                }
            });
        }
    }

    //
    @SuppressLint({"SetJavaScriptEnabled", "JavascriptInterface", "NewApi"})
    private void initSettings(WebView mWebView) {
        WebSettings webSettings = mWebView.getSettings();
        // 开启java script的支持
        webSettings.setJavaScriptEnabled(true);
        // mWebView.addJavascriptInterface(new mHandler(), "mHandler");
        // 启用localStorage 和 essionStorage
        webSettings.setDomStorageEnabled(true);

        // 开启应用程序缓存
        webSettings.setAppCacheEnabled(false);
        webSettings.setSupportZoom(true);
        webSettings.setBuiltInZoomControls(true);
        webSettings.setLayoutAlgorithm(LayoutAlgorithm.SINGLE_COLUMN);
        String appCacheDir = this.getApplicationContext().getDir("cache", Context.MODE_PRIVATE).getPath();
        webSettings.setAppCachePath(appCacheDir);
        webSettings.setCacheMode(WebSettings.LOAD_DEFAULT);
//		webSettings.setAppCacheMaxSize(1024 * 1024 * 10);// 设置缓冲大小，我设的是10M
        webSettings.setAllowFileAccess(true);
        webSettings.setDomStorageEnabled(true);
        mWebView.setWebViewClient(mWebViewClient);
        DisplayMetrics dm = getResources().getDisplayMetrics();
        int scale = dm.densityDpi;
        WebSettings set = mWebView.getSettings();
        if (scale == 240) { //
            set.setDefaultZoom(ZoomDensity.FAR);
        } else if (scale == 160) {
            set.setDefaultZoom(ZoomDensity.MEDIUM);
        } else {
            set.setDefaultZoom(ZoomDensity.CLOSE);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            mWebView.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        }
        mWebView.setWebChromeClient(wvcc);


        //设置webview支持js
        // set.setJavaScriptEnabled(true);
        //设置本地调用对象及其接口
     /*   mWebView.addJavascriptInterface(new JsInteraction(), "js_to_oc");
        mWebView.addJavascriptInterface(new JsInteraction(), "change_Top_Right_Write");
        mWebView.addJavascriptInterface(new JsInteraction(), "change_Top_Left_Write");
        mWebView.addJavascriptInterface(new JsInteraction(), "change_Top_Green");
        mWebView.addJavascriptInterface(new JsInteraction(), "change_Content_Green");
        mWebView.loadUrl("http://192.168.10.15/healthManager/#/health");*/
        //webView.loadUrl("file:///android_asset/test.html");


        //  mWebView.removeJavascriptInterface("searchBoxJavaBridge_");

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

    @Override
    public void postMessage(String data) {
        Gson gson = new Gson();
        // Logger.e("data", data);
        ResultWebData webData = gson.fromJson(data, ResultWebData.class);
        //Logger.e("data", "" + webData);
        if (webData != null) {
            Intent intent = new Intent(this, RealRopeSkippingActivity.class);
            intent.putExtra("bean", webData.getItem());
            intent.putExtra("ropeSportType", JkConfiguration.RopeSportType.Challenge);
            startActivity(intent);
        }

    }

    @Override
    public void moreList(String data) {

    }


    public class JsInteraction {
        @JavascriptInterface
        public void toastMessage(String message) {   //提供给js调用的方法
            Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
        }
    }


    private WebViewClient mWebViewClient = new WebViewClient() {

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            if (view.getProgress() == 100) {
                //  callJsFunc()
            }

        }

        @Override
        public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
            //circle_mainhtml_null.setVisibility(View.VISIBLE);
            tk_webview_dark.setVisibility(View.GONE);
        }
    };
    WebChromeClient wvcc = new WebChromeClient() {
        @Override
        public void onReceivedTitle(WebView view, String titlet) {
            super.onReceivedTitle(view, titlet);

        }

        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            Logger.myLog("onProgressChanged------" + view + newProgress);
            if (newProgress == 100) {
                if (view == tk_webview_light) {
                    mHandlerDeviceSetting.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            titleBarView.setRightIconVisible(true);
                        }
                    }, 1500);

                }
                load_pro.setVisibility(View.GONE);
            } else {
                if (load_pro.getVisibility() == View.GONE)
                    load_pro.setVisibility(View.VISIBLE);
                load_pro.setProgress(newProgress);
            }

        }

    };

    private void initview() {

//		title.setText(getString(R.string.dayhr_tk));
    }

    private File picDarkFile = null;
    private File picLightFile = null;
    private Bitmap showpicDarkFile = null;
    private Bitmap showpicLightFile = null;

    private void checkCameraPersiomm() {
        PermissionManageUtil permissionManage = new PermissionManageUtil(this);
        permissionManage.requestPermissionsGroup(new RxPermissions(this),
                PermissionGroup.CAMERA_STORAGE, new PermissionManageUtil
                        .OnGetPermissionListener() {
                    @Override
                    public void onGetPermissionYes() {
                        NetProgressObservable.getInstance().show();
                        ThreadPoolUtils.getInstance().addTask(new Runnable() {
                            @Override
                            public void run() {
                                // ThreadPoolUtils.getInstance().addTask(new ShootTask(scrollView,
                                // ActivityScaleReport.this, ActivityScaleReport.this));
                                picDarkFile = getFullScreenBitmap(scrollViewDark, true);


                               /* try {
                                    showpicDarkFile = new Compressor(ActivityRopeDetailWebView.this)
                                            .setMaxWidth(220)
                                            .setQuality(75)
                                            .setCompressFormat(Bitmap.CompressFormat.WEBP)
                                            .setDestinationDirectoryPath(FileUtil.getSDPath())
                                            .compressToFile(picDarkFile);
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }*/

                                mHandlerDeviceSetting.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        picLightFile = getFullScreenBitmap(scrollviewLight, false);
                                        showpicDarkFile=getAnyViewShot(scrollviewLight);
                                       /* try {
                                            showpicLightFile = new Compressor(ActivityRopeDetailWebView.this)
                                                    .setMaxWidth(220)
                                                    .setQuality(75)
                                                    .setCompressFormat(Bitmap.CompressFormat.WEBP)
                                                    .setDestinationDirectoryPath(FileUtil.getSDPath())
                                                    .compressToFile(picLightFile);
                                        } catch (IOException e) {
                                            e.printStackTrace();
                                        }*/
                                        showRec();
                                        NetProgressObservable.getInstance().hide();
                                        mMessageAdapter.notifyDataSetChanged();
                                    }
                                }, 4000);

                                //picFile = getFullWebViewSnapshot(tk_webview);
                               /* runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        //ivShare.setVisibility(View.VISIBLE);
                                        // ivBack.setVisibility(View.VISIBLE);


                                        // llHistoryShare.setVisibility(View.VISIBLE);
                                        //ivTestPic.setImageBitmap(BitmapFactory.decodeFile(picFile.getAbsolutePath()));
                                    }
                                });*/
                                // initLuBanRxJava(getFullScreenBitmap(scrollView));
                            }
                        });


                    }

                    @Override
                    public void onGetPermissionNo() {


                    }
                });

    }

    public void hideRec() {

        layout_share_pic.setVisibility(View.GONE);
    }

    public void showRec() {
        list.clear();
        titleBarView.setRightIconVisible(false);
        list.add(new ShareBean(false, picLightFile));
        list.add(new ShareBean(true, picDarkFile));
        recyclerview_message.scrollToPosition(list.size() - 1);
        recyclerview_message.setVisibility(View.VISIBLE);
        layout_share_pic.setVisibility(View.VISIBLE);
        mMessageAdapter.notifyDataSetChanged();

    }

    boolean isDark = true;

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_sharing_cancle:
                hideRec();
                titleBarView.setRightIconVisible(true);
                layoutShare.setVisibility(View.GONE);
                break;
            case R.id.iv_back:
                finish();
                break;
            case R.id.detail:
                Intent intent = new Intent(context, ActivityRopeDetailWebView.class);
                intent.putExtra("title", UIUtils.getString(R.string.sport_dtail));
                intent.putExtra("url", shareUrl);
                startActivity(intent);
                break;
            case R.id.iv_qq:

                if (PackageUtil.isWxInstall(ActivityRopeDetailWebView.this, PackageUtil.qqPakage)) {
                    sharePlat(SHARE_MEDIA.QQ);
                } else {
                    ToastUtil.showTextToast(ActivityRopeDetailWebView.this, UIUtils.getString(R.string.please_install_software));
                    return;
                }

                break;
            case R.id.iv_friend:
                if (PackageUtil.isWxInstall(ActivityRopeDetailWebView.this, PackageUtil.weichatPakage)) {
                    sharePlat(SHARE_MEDIA.WEIXIN_CIRCLE);
                } else {

                    ToastUtil.showTextToast(ActivityRopeDetailWebView.this, UIUtils.getString(R.string.please_install_software));
                }

                break;
            case R.id.iv_wechat:
                if (PackageUtil.isWxInstall(ActivityRopeDetailWebView.this, PackageUtil.weichatPakage)) {
                    sharePlat(SHARE_MEDIA.WEIXIN);
                } else {

                    ToastUtil.showTextToast(ActivityRopeDetailWebView.this, UIUtils.getString(R.string.please_install_software));
                }

                break;
            case R.id.iv_weibo:
                if (PackageUtil.isWxInstall(ActivityRopeDetailWebView.this, PackageUtil.weiboPakage)) {
                    sharePlat(SHARE_MEDIA.SINA);
                } else {
                    ToastUtil.showTextToast(ActivityRopeDetailWebView.this, UIUtils.getString(R.string.please_install_software));
                    return;
                }

                break;
            case R.id.iv_facebook:
                //判断facebook是否安装，没有按钮
                if (PackageUtil.isWxInstall(ActivityRopeDetailWebView.this, PackageUtil.facebookPakage)) {
                    sharePlat(SHARE_MEDIA.FACEBOOK);
                } else {
                    ToastUtils.showToast(ActivityRopeDetailWebView.this, UIUtils.getString(R.string.please_install_software));
                    return;
                }
                break;
            case R.id.iv_more:
                if (list.size() > 0) {
                    isDark = list.get(0).isSelect;
                }
                if (isDark) {
                    shareFile(picLightFile);
                } else {
                    shareFile(picDarkFile);
                }
                //
                break;

        }
    }


    @Override
    protected void onResume() {
        super.onResume();
        WebViewHelper.jsCallback(tk_webview_dark, "oc_to_js()");
        layoutShare.setVisibility(View.GONE);
        hideRec();
        if (picDarkFile != null) {
            titleBarView.setRightIconVisible(true);
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
            case "facebook":
                share_media = SHARE_MEDIA.FACEBOOK;
                break;
        }
        UMWeb umWeb = new UMWeb(url);
        umWeb.setTitle(UIUtils.getString(R.string.share_title));
        umWeb.setDescription(String.format(UIUtils.getString(R.string.share_content), sumData.getDistance(), sumData.getCalories()));
        umWeb.setThumb(new UMImage(this, R.drawable.ic_t_launcher));
        new ShareAction(ActivityRopeDetailWebView.this).setPlatform(share_media)
                .withMedia(umWeb)
                .setCallback(ActivityRopeDetailWebView.this)
                .share();
    }

    public void shareFile(File file) {
        layoutShare.setVisibility(View.GONE);

        hideRec();
        if (null != file && file.exists()) {
            Intent share = new Intent(Intent.ACTION_SEND);
            Uri uri = null;
            // 判断版本大于等于7.0
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                // "项目包名.fileprovider"即是在清单文件中配置的authorities
                uri = FileProvider.getUriForFile(ActivityRopeDetailWebView.this, getPackageName() + ".fileprovider",
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

    public void shareFile(String url) {


        Intent share = new Intent(Intent.ACTION_SEND);
        Uri uri = null;
        //Platformutil.isInstallApp(context, PlatformUtil.PACKAGE_WECHAT);
        // 判断版本大于等于7.0
        share.putExtra(Intent.EXTRA_STREAM, uri);
        share.putExtra(Intent.EXTRA_TEXT, url);
        share.setType("text/plain");//此处可发送多种文件
        share.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        share.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        // share.setComponent(new ComponentName("com.tencent.mobileqq", "com.tencent.mobileqq.activity.JumpActivity"));
        context.startActivity(Intent.createChooser(share, "Share Image"));
    }

    public static final String PACKAGE_WECHAT = "com.tencent.mm";
    public static final String PACKAGE_MOBILE_QQ = "com.tencent.mobileqq";
    public static final String PACKAGE_QZONE = "com.qzone";
    public static final String PACKAGE_SINA = "com.sina.weibo";

    // 判断是否安装指定app
    public static boolean isInstallApp(Context context, String app_package) {
        final PackageManager packageManager = context.getPackageManager();
        List<PackageInfo> pInfo = packageManager.getInstalledPackages(0);
        if (pInfo != null) {
            for (int i = 0; i < pInfo.size(); i++) {
                String pn = pInfo.get(i).packageName;
                if (app_package.equals(pn)) {
                    return true;
                }
            }
        }
        return false;
    }


    /**
     * 截图view的任意图片
     */
    public static Bitmap getAnyViewShot(View view) {
        view.setDrawingCacheEnabled(true);
        view.buildDrawingCache();
        Bitmap bitmap = Bitmap.createBitmap(view.getDrawingCache());
//        bitmap.setConfig(Bitmap.Config.ARGB_4444);
//        Bitmap bitmap = Bitmap.createBitmap(view.getDrawingCache(), left, top, right, bottom);
        view.setDrawingCacheEnabled(false);
        view.destroyDrawingCache();
        return bitmap;
    }


    /**
     * 获取长截图
     *
     * @return
     */
    /**
     * 获取长截图
     *
     * @return
     */
    /**
     * 获取长截图
     *
     * @return
     */
    public File getFullScreenBitmap(NestedScrollView scrollView, boolean isBalk) {
        int h = 0;
        Bitmap bitmap = null;
        // 获取scrollview实际高度

        for (int i = 0; i < scrollView.getChildCount(); i++) {
            h += scrollView.getChildAt(i).getHeight();//获取scrollView的屏幕高度
            if (isBalk) {
                scrollView.getChildAt(i).setBackgroundColor(
                        Color.parseColor("#000000"));
            } else {
                scrollView.getChildAt(i).setBackgroundColor(
                        Color.parseColor("#ffffff"));
            }
        }
        Logger.myLog("scrollView.getHeight()=" + scrollView.getHeight() + "------h=" + h);
        //如果传的参数不是NestedScrollView，则不需要循环遍历高度
//        h += scrollView.getHeight();//获取scrollView的屏幕高度
//        scrollView.setBackgroundColor(
//                    Color.parseColor("#ffffff"));
        // 创建对应大小的bitmap
        bitmap = Bitmap.createBitmap(scrollView.getWidth(), h,
                Bitmap.Config.ARGB_8888);
        final Canvas canvas = new Canvas(bitmap);//把创建的bitmap放到画布中去
        scrollView.draw(canvas);//绘制canvas 画布
        //  return bitmap;
        //回收
        //head.recycle();
        // 测试输出
        return FileUtil.writeImage(bitmap, FileUtil.getImageFile(FileUtil.getPhotoFileName()), 100);
    }

    public void sharePlat(SHARE_MEDIA share_media) {
        if (list.size() > 0) {
            isDark = list.get(0).isSelect;
        }
        if (isDark) {
            UMImage image = ShareHelper.getUMWeb(this, picLightFile);
            new ShareAction(this).setPlatform(share_media)
                    .withMedia(image)
                    .setCallback(this)
                    .share();
        } else {
            UMImage image = ShareHelper.getUMWeb(this, picDarkFile);
            new ShareAction(this).setPlatform(share_media)
                    .withMedia(image)
                    .setCallback(this)
                    .share();
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        /** attention to this below ,must add this**/
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
    }

}
