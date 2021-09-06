package com.isport.brandapp.device.bracelet;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.Point;
import android.hardware.Camera;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.isport.blelibrary.utils.Logger;
import com.isport.brandapp.App;
import com.isport.brandapp.R;
import com.isport.brandapp.banner.recycleView.utils.ToastUtil;
import com.tbruyelle.rxpermissions2.RxPermissions;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.concurrent.TimeUnit;

import androidx.core.app.ActivityCompat;
import brandapp.isport.com.basicres.BaseActivity;
import brandapp.isport.com.basicres.commonpermissionmanage.PermissionGroup;
import brandapp.isport.com.basicres.commonpermissionmanage.PermissionManageUtil;
import brandapp.isport.com.basicres.commonutil.LoadImageUtil;
import brandapp.isport.com.basicres.commonutil.UIUtils;
import brandapp.isport.com.basicres.commonview.RoundImageView;
import brandapp.isport.com.basicres.service.observe.NetProgressObservable;
import brandapp.isport.com.basicres.service.observe.OptionPhotobservable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;

/**
 * Created by 中庸 on 2016/5/20.
 */
@Route(path = "/main/CamaraActivity1")
public class CamaraActivity extends BaseActivity implements View.OnClickListener {

    private static final String TAG = CamaraActivity.class.getSimpleName();
    //启动摄像机
    private Camera mCamera;

    public boolean isopen_camara = false;
    private SurfaceView surfaceView;
    private SurfaceHolder mholder = null;
    private SurfaceCallback previewCallBack;
    private boolean isTakingPhoto;//是否正在拍照

    RoundImageView ivAlbum;

    ImageView ivSwitch;
    ImageView iv_back;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_camara;
    }

    @Override
    protected void initView(View view) {
        ivSwitch = view.findViewById(R.id.iv_switch);
        ivAlbum = view.findViewById(R.id.iv_album);
        iv_back = view.findViewById(R.id.iv_back);
    }

    @Override
    protected void initData() {
        checkCameraPersiomm();
        // verifyPermission(new String[]{Manifest.permission.CAMERA});
        Log.e("mainService", "6666");
        // 预览控件
        surfaceView = (SurfaceView) this
                .findViewById(R.id.surfaceView);
        // 设置参数
        ivAlbum.setVisibility(View.GONE);
        surfaceView.getHolder().setKeepScreenOn(true);
        surfaceView.getHolder().setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
        surfaceView.setOnClickListener(this);

    }

    private void goPhotoAlbum() {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivity(intent);
    }

    @Override
    protected void initEvent() {

        ivSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchCamara();
            }
        });
        ivAlbum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goPhotoAlbum();
            }
        });
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }


    @Override
    protected void initHeader() {

    }


    private void checkCameraPersiomm() {

        // cameraTask();
      /* new RxPermissions(this)
                .requestEach(Manifest.permission.CAMERA)
                .subscribe(permission -> { // will emit 2 Permission objects
                    if (permission.granted) {
                        // `permission.name` is granted !
                        ToastUtil.showTextToast(ActivityImageShow.this, "允许");
                        goCamera();
                    } else if (permission.shouldShowRequestPermissionRationale) {
                        // Denied permission without ask never again
                        ToastUtil.showTextToast(ActivityImageShow.this, "没有被允许不得入内");
                        //点击了拒绝
                    } else {
                        // Denied permission with ask never again
                        // Need to go to the settings

                        ToastUtil.showTextToast(ActivityImageShow.this, "拒绝");
                    }
                });
*/

        PermissionManageUtil permissionManage = new PermissionManageUtil(this);
        permissionManage.requestPermissionsGroup(new RxPermissions(this),
                PermissionGroup.CAMERA_STORAGE, new PermissionManageUtil
                        .OnGetPermissionListener() {
                    @Override
                    public void onGetPermissionYes() {
                        if (App.appType() == App.httpType) {
                            if (!checkNet())
                                return;
                        }
                        //goCamera();
                    }

                    @Override
                    public void onGetPermissionNo() {
                        ToastUtil.showTextToast(CamaraActivity.this, UIUtils.getString(R.string.location_permissions));
                    }
                });

    }

    public void verifyPermission(String[] permissions) {
        if (permissions != null) {
            List<String> lists = new ArrayList<>();
            for (int i = 0; i < permissions.length; i++) {
                if (ActivityCompat.checkSelfPermission(this, permissions[i]) != PackageManager.PERMISSION_GRANTED) {
                    if (ActivityCompat.shouldShowRequestPermissionRationale(this, permissions[i])) {

                    }
                    lists.add(permissions[i]);
                }
            }
            if (lists.size() > 0) {
                String[] ps = new String[lists.size()];
                for (int i = 0; i < lists.size(); i++) {
                    ps[i] = lists.get(i);
                }
                ActivityCompat.requestPermissions(this, ps, 1);
            }
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    private void takePhoto() {
        if (!isopen_camara) {
            previewCallBack = new SurfaceCallback();
            surfaceView.getHolder().addCallback(previewCallBack);
            isTakeSuccess = true;
        } else {
            autoTakePhoto();
        }
    }


    @Override
    protected void onResume() {
        super.onResume();
        OptionPhotobservable.getInstance().addObserver(this);
        previewCallBack = new SurfaceCallback();
        surfaceView.getHolder().addCallback(previewCallBack);
        if (mCamera == null) {
            if (isopen_camara) {

            }
        }
    }

    boolean isTakeSuccess = true;

    @Override
    public void update(Observable o, Object arg) {
        super.update(o, arg);
        Logger.myLog("update camaraActivity isTakingPhoto" + isTakingPhoto + "isopen_camara");
        if (o instanceof OptionPhotobservable && isTakeSuccess) {
            isTakeSuccess = false;
            //showCountDownPopWindow();
            takePhoto();
        }
    }

    PopupWindow popupWindow;

    private void showCountDownPopWindow() {
        // 一个自定义的布局，作为显示的内容
        View contentView = LayoutInflater.from(CamaraActivity.this).inflate(R.layout.popwin_take_photo_down, null);

        final TextView mTimer = (TextView) contentView.findViewById(R.id.timer);

        popupWindow = new PopupWindow(contentView, LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT, true);

        popupWindow.setTouchable(true);

        popupWindow.setTouchInterceptor(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                // Logger.i("mengdd", "onTouch : ");
                // 这里如果返回true的话，touch事件将被拦截
                // 拦截后 PopupWindow的onTouchEvent不被调用，这样点击外部区域无法dismiss
                return true;
            }
        });

        // 设置好参数之后再show
        // 设置layout在PopupWindow中显示的位置
        popupWindow.showAtLocation(ivSwitch, Gravity.FILL, 0, 0);


        //  final Disposable[] disposable = new Disposable[1];
        io.reactivex.Observable.interval(0, 1, TimeUnit.SECONDS)
                .take(4).observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<Long>() {
            @Override
            public void onSubscribe(Disposable d) {
                //   disposable[0] =d;
            }

            @Override
            public void onNext(Long aLong) {
                NetProgressObservable.getInstance().hide();
                mTimer.setText(3 - aLong + "");
                mTimer.setAnimation(setAnim());
                mTimer.startAnimation(setAnim());
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {
                popupWindow.dismiss();
                //保存用户运动的类型

                takePhoto();

            }
        });
    }


    public AnimationSet setAnim() {
        AlphaAnimation alphaAnimation = new AlphaAnimation(1.0f, 0.0f);
        // 渐变时间
        alphaAnimation.setDuration(1000);
        ScaleAnimation animation = new ScaleAnimation(0.0f, 2.6f, 0.0f, 2.6f,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        animation.setDuration(1000);
        AnimationSet animationSet = new AnimationSet(true);
        animationSet.addAnimation(animation);
        animationSet.addAnimation(alphaAnimation);
        animationSet.setFillAfter(true);
        return animationSet;
        // updateSoundAndView(millisUntilFinished, animationSet);
    }

    @Override
    protected void onPause() {
        super.onPause();
        OptionPhotobservable.getInstance().deleteObserver(this);
        if (mCamera != null) {
            if (surfaceView != null && surfaceView.getHolder() != null && previewCallBack != null) {
                surfaceView.getHolder().removeCallback(previewCallBack);
            }
            mCamera.setPreviewCallback(null);
            mCamera.stopPreview();
            mCamera.lock();
            mCamera.release();
            isTakeSuccess = true;
            mCamera = null;
        }
        isopen_camara = false;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.surfaceView:
                autoFocus();
                break;
        }
    }

    boolean isSurfaceCreate;

    // 预览界面回调
    private final class SurfaceCallback implements SurfaceHolder.Callback {
        // 预览界面被创建
        public void surfaceCreated(SurfaceHolder holder) {
            isSurfaceCreate = true;
            try {
                //1代表打开后置摄像头,0代表打开前置摄像头.
                mCamera = Camera.open(cameraPosition);// 打开摄像头
                setCameraDisplayOrientation(cameraPosition, mCamera);
                setParams(holder, cameraPosition);
            } catch (Exception e) {
                e.printStackTrace();
                if (mCamera != null) {
                    holder.removeCallback(this);
                    mCamera.setPreviewCallback(null);
                    mCamera.stopPreview();
                    mCamera.lock();
                    mCamera.release();
                    mCamera = null;
                }
                finish();
            }
        }

        public void surfaceChanged(SurfaceHolder holder, int format, int width,
                                   int height) {
            System.out.println("surfaceChanged");
            isopen_camara = true;
            Logger.myLog("surfaceChanged: " + width + "  " + height);
            //autoTakePhoto();
        }

        // 预览界面被销毁
        public void surfaceDestroyed(SurfaceHolder holder) {
            System.out.println("surfaceDestroyed");
            isSurfaceCreate = false;
            if (!isopen_camara)
                return;
            if (mCamera != null) {
                holder.removeCallback(this);
                mCamera.setPreviewCallback(null);
                mCamera.stopPreview();
                mCamera.lock();
                mCamera.release();
                mCamera = null;
            }
        }

    }

    public void reset() {
        int cameraCount = 0;
        Camera.CameraInfo cameraInfo = new Camera.CameraInfo();
        cameraCount = Camera.getNumberOfCameras();//得到摄像头的个数

        for (int i = 0; i < cameraCount; i++) {
            Camera.getCameraInfo(i, cameraInfo);//得到每一个摄像头的信息
            if (cameraInfo.facing == cameraPosition) {//代表摄像头的方位，CAMERA_FACING_FRONT前置      CAMERA_FACING_BACK后置
                if (surfaceView != null && surfaceView.getHolder() != null && previewCallBack != null) {
                    surfaceView.getHolder().removeCallback(previewCallBack);
                }
                if (mCamera != null) {
                    mCamera.setPreviewCallback(null);
                    mCamera.stopPreview();//停掉原来摄像头的预览
                    mCamera.lock();
                    mCamera.release();//释放资源
                    mCamera = null;//取消原来摄像头
                }
                mCamera = Camera.open(i);//打开当前选中的摄像头
                setCameraDisplayOrientation(i, mCamera);
                if (null != mholder)
                    setParams(mholder, cameraPosition);

                break;
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    private void setParams(SurfaceHolder mySurfaceView, int postion) {
        int PreviewWidth = 0;
        int PreviewHeight = 0;
        int PictureWidth = 0;
        int PictureHeight = 0;
        WindowManager wm = (WindowManager) getSystemService(Context.WINDOW_SERVICE);//获取窗口的管理器
        // Camera.Parameters parameters = mCamera.getParameters();


        try {
            Camera.Parameters parameters = mCamera.getParameters();
            //mySurfaceView.setFixedSize(parameters.get);
            Point bestPreviewSizeValue1 = findBestPreviewSizeValue(parameters.getSupportedPreviewSizes());

            // mySurfaceView.setFixedSize(bestPreviewSizeValue1.x, bestPreviewSizeValue1.y);
            if (Camera.CameraInfo.CAMERA_FACING_FRONT == cameraPosition) {
                //parameters.setRotation(180);
            }
            parameters.setPreviewSize(bestPreviewSizeValue1.x, bestPreviewSizeValue1.y);
            mCamera.setParameters(parameters);
        } catch (Exception e) {
            Log.d("wangc", "set parameters fail");
        }
        if (context.getResources().getConfiguration().orientation != Configuration.ORIENTATION_LANDSCAPE) {
            // mCamera.setDisplayOrientation(0);
            if (cameraPosition == 1) {
                mCamera.setDisplayOrientation(90);
            } else {
                mCamera.setDisplayOrientation(90);
            }

        } else {//如果是横屏
            mCamera.setDisplayOrientation(0);
        }

            /*// 选择合适的预览尺寸
            List<Camera.Size> sizeList = parameters.getSupportedPreviewSizes();
            List<Camera.Size> pictureSizes = parameters.getSupportedPictureSizes();
            if (sizeList.size() > 0) {
                for (int i = 0; i < sizeList.size(); i++) {
                    Log.e(TAG, "   PreviewHeight  " + sizeList.get(i).height + "  PreviewWidth  " + sizeList.get(i)
                            .width);
                    if (i == 0) {
                        PreviewWidth = sizeList.get(i).width;
                        PreviewHeight = sizeList.get(i).height;
                    }
                }
            }
            if (pictureSizes.size() > 0) {
                for (int i = 0; i < pictureSizes.size(); i++) {
                    Log.e(TAG, "   PictureHeight  " + pictureSizes.get(i).height + "  PictureWidth  " + pictureSizes
                            .get(i)
                            .width);
                    if (i == 0) {
                        PictureWidth = pictureSizes.get(i).width;
                        PictureHeight = pictureSizes.get(i).height;
                    }
                }
            }
            parameters.setPreviewSize(PreviewWidth, PreviewHeight); //获得摄像区域的大小
            //parameters.setPreviewFrameRate(3);//每秒3帧  每秒从摄像头里面获得3个画面
            //parameters.setPreviewFpsRange(3,);
            List<int[]> list = parameters.getSupportedPreviewFpsRange();
            int[] v = null;
            int index = 0;
            int min = 0;
            for (int i = 0; i < list.size(); i++) {
                v = list.get(i);
                if (v[0] > min) {
                    min = v[0];
                    index = i;
                }
            }
            List<String> allFocus = parameters.getSupportedFocusModes();

            if (allFocus.contains(Camera.Parameters.FLASH_MODE_AUTO)) {
                parameters.setFocusMode(Camera.Parameters.FOCUS_MODE_AUTO);
            } else if (allFocus.contains(Camera.Parameters.FOCUS_MODE_CONTINUOUS_VIDEO)) {
                parameters.setFocusMode(Camera.Parameters.FOCUS_MODE_CONTINUOUS_PICTURE);//
                // FOCUS_MODE_CONTINUOUS_PICTURE FOCUS_MODE_AUTO
            }
            parameters.setPreviewFpsRange(list.get(index)[0], list.get(index)[1]);
            parameters.setPictureFormat(PixelFormat.JPEG);//设置照片输出的格式
            parameters.set("jpeg-quality", 100);//设置照片质量
            parameters.setPictureSize(PictureWidth, PictureHeight); //获得摄像区域的大小
            if (cameraPosition == 1) {
                parameters.setRotation(270);
            }
            // parameters.setRotation(180); //Java部分
            mCamera.setParameters(parameters);//把上面的设置 赋给摄像头*/
        // mCamera.setPreviewDisplay(mySurfaceView);//把摄像头获得画面显示在SurfaceView控件里面
        try {
            mCamera.setPreviewDisplay(mySurfaceView);
        } catch (IOException e) {
            e.printStackTrace();
        }


        mholder = mySurfaceView;
        mCamera.setPreviewCallback(new Camera.PreviewCallback() {
            @Override
            public void onPreviewFrame(byte[] data, Camera camera) {

            }
        });
        mCamera.startPreview();//开始预览
        mCamera.cancelAutoFocus();
        autoFocus();

        //   mPreviewRunning = true;

    }


    /**
     * 通过对比得到与宽高比最接近的尺寸（如果有相同尺寸，优先选择）
     *
     * @return 得到与原宽高比例最接近的尺寸
     */
    private static Point findBestPreviewSizeValue(List<Camera.Size> sizeList) {
        int bestX = 0;
        int bestY = 0;
        int size = 0;
        for (Camera.Size nowSize : sizeList) {
            int newX = nowSize.width;
            int newY = nowSize.height;

            Logger.myLog("findBestPreviewSizeValue:newX:" + newX + ",newY:" + newY);
            int newSize = Math.abs(newX * newX) + Math.abs(newY * newY);
            float ratio = (float) (newY * 1.0 / newX);
            if (newSize >= size && ratio != 0.75) {//确保图片是16:9
                if (bestX == bestY) {
                    continue;
                }
                bestX = newX;
                bestY = newY;
                size = newSize;

            } else if (newSize < size) {
                continue;
            }
        }
        if (bestX > 0 && bestY > 0) {
            Logger.myLog("findBestPreviewSizeValue:bestX:" + bestX + ",bestX:" + bestY);
            return new Point(bestX, bestY);

        }
        return null;

    }


    Handler handler = null;

    private void autoTakePhoto() {
        // 拍照前需要对焦 获取清析的图片

        try {
            if (null == mCamera) return;
            mCamera.autoFocus(new Camera.AutoFocusCallback() {
                @Override
                public void onAutoFocus(boolean success, Camera camera) {

                    if (success && isopen_camara) {
                        // 对焦成功
                        //    Toast.makeText(MainActivity.this, "对焦成功 !!",Toast.LENGTH_SHORT).show();
                        if (!isTakingPhoto) {
                            isTakingPhoto = true;
                            handler = new Handler();
                            handler.post(new Runnable() {
                                @Override
                                public void run() {
                                    mCamera.takePicture(null, null, new MyPictureCallback());
                                }
                            });
                        }
                    }
                }
            });
        } catch (Exception e) {

            Logger.myLog("e" + e.toString());

        }
    }

    private int getBitmapDegree(String path) {
        int degree = 0;
        try {
            // 从指定路径下读取图片，并获取其EXIF信息
            ExifInterface exifInterface = new ExifInterface(path);
            // 获取图片的旋转信息
            int orientation = exifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION,
                    ExifInterface.ORIENTATION_NORMAL);
            switch (orientation) {
                case ExifInterface.ORIENTATION_ROTATE_90:
                    degree = 90;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    degree = 180;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_270:
                    degree = 270;
                    break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return degree;
    }

    // 照片回调
    private final class MyPictureCallback implements Camera.PictureCallback {
        // 照片生成后
        @Override
        public void onPictureTaken(byte[] data, Camera camera) {
            try {
                Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
                data = null;
                Matrix matrix = new Matrix();
                Logger.myLog("cameraPosition:" + cameraPosition);
                if (cameraPosition == 1) {
                    matrix.setRotate(rotateDegress - 90);
                } else {
                    matrix.setRotate(rotateDegress + 90);
                }
                File jpgFile = new File(Environment.getExternalStorageDirectory() + "/DCIM/camera");
                if (!jpgFile.exists()) {
                    jpgFile.mkdir();
                }
                File jpgFile1 = new File(jpgFile.getAbsoluteFile(), System.currentTimeMillis() + ".jpg");
                bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
                FileOutputStream fos = new FileOutputStream(jpgFile1);
                bitmap.compress(Bitmap.CompressFormat.JPEG, 80, fos);
                // Toast.makeText(CamaraActivity.this, getString(R.string.save_success), Toast.LENGTH_SHORT).show();
                fos.close();
                bitmap.recycle();
                bitmap = null;
                Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
                Uri uri = Uri.fromFile(jpgFile1);
                intent.setData(uri);
                sendBroadcast(intent);
                ivAlbum.setVisibility(View.VISIBLE);
                isTakeSuccess = true;
                LoadImageUtil.displayImagePath(CamaraActivity.this, jpgFile1.getPath(), ivAlbum);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                // if (Build.VERSION.SDK_INT >= 24) {
                reset();
                //  }
                isTakingPhoto = false;
            }
        }
    }

    private int cameraPosition = 0;//0代表前置摄像头，1代表后置摄像头

    private void switchCamara() {
        if (mCamera == null)
            return;
        //切换前后摄像头
        int cameraCount = 0;
        Camera.CameraInfo cameraInfo = new Camera.CameraInfo();
        cameraCount = Camera.getNumberOfCameras();//得到摄像头的个数

        for (int i = 0; i < cameraCount; i++) {
            Camera.getCameraInfo(i, cameraInfo);//得到每一个摄像头的信息
            if (cameraPosition == 1) {
                //现在是后置，变更为前置
                if (cameraInfo.facing == Camera.CameraInfo.CAMERA_FACING_BACK) {//代表摄像头的方位，CAMERA_FACING_FRONT前置      CAMERA_FACING_BACK后置
                    if (surfaceView != null && surfaceView.getHolder() != null && previewCallBack != null) {
                        surfaceView.getHolder().removeCallback(previewCallBack);
                    }
                    mCamera.setPreviewCallback(null);
                    mCamera.stopPreview();//停掉原来摄像头的预览
                    mCamera.lock();
                    mCamera.release();//释放资源
                    mCamera = null;//取消原来摄像头
                    mCamera = Camera.open(i);//打开当前选中的摄像头
                    mCamera.startPreview();//开始预览*/
                    setCameraDisplayOrientation(i, mCamera);
                    if (null != mholder)
                        setParams(mholder, Camera.CameraInfo.CAMERA_FACING_BACK);
                    cameraPosition = 0;
                    break;
                }
            } else {
                //现在是前置， 变更为后置
                if (cameraInfo.facing == Camera.CameraInfo.CAMERA_FACING_FRONT) {//代表摄像头的方位，CAMERA_FACING_FRONT前置      CAMERA_FACING_BACK后置
                    if (surfaceView != null && surfaceView.getHolder() != null && previewCallBack != null) {
                        surfaceView.getHolder().removeCallback(previewCallBack);
                    }
                    mCamera.setPreviewCallback(null);
                    mCamera.stopPreview();//停掉原来摄像头的预览
                    mCamera.lock();
                    mCamera.release();//释放资源
                    mCamera = null;//取消原来摄像头
                    mCamera = Camera.open(i);//打开当前选中的摄像头
                    mCamera.startPreview();//开始预览*/
                    setCameraDisplayOrientation(i, mCamera);
                    if (null != mholder)
                        setParams(mholder, Camera.CameraInfo.CAMERA_FACING_FRONT);
                    cameraPosition = 1;
                    break;
                }
            }
            autoFocus();
        }
    }

    private void autoFocus() {

        if (!autoFocusHandler.hasMessages(0x01)) {
            autoFocusHandler.sendEmptyMessageDelayed(0x01, 1000);
        }
    }

    private int rotateDegress;


    public static void setCameraDisplayOrientation(Activity activity,
                                                   int cameraId, android.hardware.Camera camera) {
        android.hardware.Camera.CameraInfo info =
                new android.hardware.Camera.CameraInfo();
        android.hardware.Camera.getCameraInfo(cameraId, info);
        int rotation = activity.getWindowManager().getDefaultDisplay()
                .getRotation();
        int degrees = 0;
        switch (rotation) {
            case Surface.ROTATION_0:
                degrees = 0;
                break;
            case Surface.ROTATION_90:
                degrees = 90;
                break;
            case Surface.ROTATION_180:
                degrees = 180;
                break;
            case Surface.ROTATION_270:
                degrees = 270;
                break;
        }

        int result;
        if (info.facing == Camera.CameraInfo.CAMERA_FACING_FRONT) {
            result = (info.orientation + degrees) % 360;
            result = (360 - result) % 360;  // compensate the mirror
        } else {  // back-facing
            result = (info.orientation - degrees + 360) % 360;
        }
        camera.setDisplayOrientation(result);
    }


    public void setCameraDisplayOrientation(int cameraId, Camera camera) {
        Camera.CameraInfo info =
                new Camera.CameraInfo();
        Camera.getCameraInfo(cameraId, info);
        int rotation = getWindowManager().getDefaultDisplay()
                .getRotation();
        int degrees = 0;

        switch (rotation) {
            case Surface.ROTATION_0:
                degrees = 0;
                break;
            case Surface.ROTATION_90:
                degrees = 90;
                break;
            case Surface.ROTATION_180:
                degrees = 180;
                break;
            case Surface.ROTATION_270:
                degrees = 270;
                break;
        }
        Logger.myLog("setCameraDisplayOrientation" + rotation + "degrees:" + degrees);
        int result;
        if (info.facing == Camera.CameraInfo.CAMERA_FACING_FRONT) {
            result = (info.orientation + degrees) % 360;
            result = (360 - result) % 360; // compensate the mirror
        } else { // back-facing
            result = (info.orientation - degrees + 360) % 360;
        }
        camera.setDisplayOrientation(result);

        rotateDegress = degrees;
        Logger.myLog("setCameraDisplayOrientation: rotateDegress" + rotateDegress);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_VOLUME_DOWN) {
            takePhoto();
            return true;
        }
        return super.onKeyDown(keyCode, event);

    }

    private Handler autoFocusHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            try {
                if (mCamera != null && isSurfaceCreate) {
                    Camera.Parameters p = mCamera.getParameters();
                    List<String> focusModes = p.getSupportedFocusModes();

                    if (focusModes != null && focusModes.contains(Camera.Parameters.FOCUS_MODE_AUTO)) {
                        mCamera.autoFocus(null);
                    } else {
                        //Phone does not support autofocus!
                    }
                }
            } catch (RuntimeException e) {
                e.printStackTrace();
            }
        }
    };
}
