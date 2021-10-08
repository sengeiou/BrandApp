package phone.gym.jkcq.com.socialmodule.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import androidx.appcompat.app.ActionBar;
import brandapp.isport.com.basicres.BaseActivity;
import brandapp.isport.com.basicres.BaseApp;
import brandapp.isport.com.basicres.commonnet.interceptor.BaseObserver;
import brandapp.isport.com.basicres.commonnet.interceptor.ExceptionHandle;
import brandapp.isport.com.basicres.commonnet.net.RxScheduler;
import brandapp.isport.com.basicres.commonutil.Logger;
import brandapp.isport.com.basicres.mvp.NetworkBoundResource;
import io.reactivex.Observable;
import me.devilsen.czxing.code.BarcodeFormat;
import me.devilsen.czxing.compat.ActivityCompat;
import me.devilsen.czxing.compat.ContextCompat;
import me.devilsen.czxing.util.BarCodeUtil;
import me.devilsen.czxing.util.ScreenUtil;
import me.devilsen.czxing.util.SoundPoolUtil;
import me.devilsen.czxing.view.ScanBoxView;
import me.devilsen.czxing.view.ScanListener;
import me.devilsen.czxing.view.ScanView;
import phone.gym.jkcq.com.socialmodule.FriendConstant;
import phone.gym.jkcq.com.socialmodule.R;
import phone.gym.jkcq.com.socialmodule.net.APIService;
import phone.gym.jkcq.com.socialmodule.net.RetrofitClient;

/**
 * 二维码扫描页面
 */
public class FriendScanActivity extends BaseActivity implements View.OnClickListener,
        ScanListener, ScanListener.AnalysisBrightnessListener {

    private static final int PERMISSIONS_REQUEST_CAMERA = 1;
    private static final int PERMISSIONS_REQUEST_STORAGE = 2;
    private static final int CODE_SELECT_IMAGE = 100;

    private ScanView mScanView;
    //播放提示音
    private SoundPoolUtil mSoundPoolUtil;

    private ImageView iv_back;
    private TextView tv_title;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_friend_scan;
    }

    @Override
    protected void initView(View view) {

            ActionBar supportActionBar = getSupportActionBar();
            if (supportActionBar != null) {
                supportActionBar.hide();
            }
            ScreenUtil.setFullScreen(this);

            iv_back = findViewById(R.id.iv_back);
            tv_title = findViewById(R.id.tv_title);
            tv_title.setText(getString(R.string.friend_scan));
            mScanView = findViewById(R.id.surface_customize_view_scan);

            // 设置扫描模式
//        mScanView.setScanMode(ScanView.SCAN_MODE_MIX);
            // 设置扫描格式 BarcodeFormat
//        mScanView.setBarcodeFormat();

            ScanBoxView scanBox = mScanView.getScanBox();
            // 设置扫码框上下偏移量，可以为负数
//       scanBox.setBoxTopOffset(-BarCodeUtil.dp2px(this, 100));
            // 设置边框大小
            scanBox.setBorderSize(BarCodeUtil.dp2px(this, 200), BarCodeUtil.dp2px(this, 200));
            // 设置扫码框四周的颜色
            scanBox.setMaskColor(Color.parseColor("#9C272626"));
            // 设定四个角的颜色
//        scanBox.setCornerColor();
            // 设定扫描框的边框颜色
//        scanBox.setBorderColor();
            // 设置边框长度(扫码框大小)
//        scanBox.setBorderSize();
            // 设定扫描线的颜色
//        scanBox.setScanLineColor();
            // 设置扫码线移动方向为水平（从左往右）
//      scanBox.setHorizontalScanLine();
            // 设置手电筒打开时的图标
            scanBox.setFlashLightOnDrawable(R.drawable.ic_highlight_blue_open_24dp);
            // 设置手电筒关闭时的图标
            scanBox.setFlashLightOffDrawable(R.drawable.ic_highlight_white_close_24dp);
            // 设置闪光灯打开时的提示文字
            scanBox.setFlashLightOnText(getString(R.string.friend_open_light));
            // 设置闪光灯关闭时的提示文字
            scanBox.setFlashLightOffText(getString(R.string.friend_close_light));
            // 不使用手电筒图标及提示
//        scanBox.invisibleFlashLightIcon();
            // 设置扫码框下方的提示文字
            scanBox.setScanNoticeText(getString(R.string.friend_input_qrcode_to_frame));

            iv_back.setOnClickListener(this);
            // 获取扫码回调
            mScanView.setScanListener(this);
            // 获取亮度测量结果
            mScanView.setAnalysisBrightnessListener(this);

//        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) iv_back.getLayoutParams();
//        layoutParams.topMargin = ScreenUtil.getStatusBarHeight(this);

            mSoundPoolUtil = new SoundPoolUtil();
            mSoundPoolUtil.loadDefault(this);

            requestCameraPermission();
      //  StatusBarCompat.setStatusBarColor(this, getResources().getColor(com.isport.brandapp.basicres.R.color.transparent));

    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initEvent() {

    }

    @Override
    protected void initHeader() {

    }

    @Override
    protected void onResume() {
        super.onResume();
        mScanView.openCamera(); // 打开后置摄像头开始预览，但是并未开始识别
        mScanView.startScan();  // 显示扫描框，并开始识别
        Log.e("onScanSuccess", "onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        mScanView.stopScan();
        mScanView.closeCamera(); // 关闭摄像头预览，并且隐藏扫描框
    }

    @Override
    protected void onDestroy() {
        mScanView.onDestroy(); // 销毁二维码扫描控件
        mSoundPoolUtil.release();
        super.onDestroy();
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.iv_back) {
            finish();
        }
    }

    @Override
    public void onScanSuccess(String result, BarcodeFormat format) {
        mSoundPoolUtil.play();
        Logger.e("scanResult=" + result);
        int size = result.length();
        if (result.contains(FriendConstant.QR_HEAD)) {
            getUserIdByQrString(result.substring(8));
        } else if (result.startsWith("http")) {
            Intent intent = new Intent();
            intent.setAction("android.intent.action.VIEW");
            Uri content_url = Uri.parse(result);
            intent.setData(content_url);
            startActivity(intent);
            finish();
        } else {
            Toast.makeText(FriendScanActivity.this, "QRCode=" + result, Toast.LENGTH_LONG).show();

        }


    }

    /**
     * 可以通过此回调来控制自定义的手电筒显隐藏
     *
     * @param isDark 是否处于黑暗的环境
     */
    @Override
    public void onAnalysisBrightness(boolean isDark) {
        if (isDark) {
            Log.d("analysisBrightness", "您处于黑暗的环境，建议打开手电筒");
        } else {
            Log.d("analysisBrightness", "正常环境，如果您打开了手电筒，可以关闭");
        }
    }

    @Override
    public void onOpenCameraError() {
        Log.e("onOpenCameraError", "onOpenCameraError");
    }


    /**
     * 获取摄像头权限（实际测试中，使用第三方获取权限工具，可能造成摄像头打开失败）
     */
    private void requestCameraPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.CAMERA},
                    PERMISSIONS_REQUEST_CAMERA);
        }
    }

    private void requestStoragePermission() {
        int permission = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE);
        if (permission != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                    PERMISSIONS_REQUEST_STORAGE);
        } else {
            Intent albumIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(albumIntent, CODE_SELECT_IMAGE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == PERMISSIONS_REQUEST_CAMERA) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                mScanView.openCamera();
                mScanView.startScan();
            }
            return;
        } else if (requestCode == PERMISSIONS_REQUEST_STORAGE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Intent albumIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(albumIntent, CODE_SELECT_IMAGE);
            }
            return;
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }


    private void getUserIdByQrString(String qrString) {

        new NetworkBoundResource<String>() {
            @Override
            public Observable<String> getFromDb() {
                return null;
            }

            @Override
            public Observable<String> getNoCacheData() {
                return null;
            }

            @Override
            public boolean shouldFetchRemoteSource() {
                return false;
            }

            @Override
            public boolean shouldStandAlone() {
                return false;
            }

            @Override
            public Observable<String> getRemoteSource() {
                return RetrofitClient.getRetrofit().create(APIService.class).getUserIdByQrString(qrString).compose
                        (RxScheduler.Obs_io_main()).compose(RetrofitClient.transformer);

            }

            @Override
            public void saveRemoteSource(String bean) {

            }
        }.getAsObservable().subscribe(new BaseObserver<String>(BaseApp.getApp()) {
            @Override
            protected void hideDialog() {

            }

            @Override
            protected void showDialog() {

            }

            @Override
            public void onError(ExceptionHandle.ResponeThrowable e) {

            }

            @Override
            public void onNext(String info) {

                if (info != null) {
                    Intent intent = new Intent(FriendScanActivity.this, PersonalHomepageActivity.class);
                    intent.putExtra(FriendConstant.USER_ID, info);
                    startActivity(intent);
                    finish();
                }

            }
        });

    }
}

