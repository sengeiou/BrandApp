package phone.gym.jkcq.com.socialmodule.personal;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.blankj.utilcode.util.DeviceUtils;
import com.blankj.utilcode.util.ScreenUtils;
import com.tbruyelle.rxpermissions2.RxPermissions;

import org.greenrobot.eventbus.EventBus;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import androidx.core.content.FileProvider;
import brandapp.isport.com.basicres.BaseApp;
import brandapp.isport.com.basicres.commonpermissionmanage.PermissionGroup;
import brandapp.isport.com.basicres.commonpermissionmanage.PermissionManageUtil;
import brandapp.isport.com.basicres.commonutil.AppUtil;
import brandapp.isport.com.basicres.commonutil.FileUtil;
import brandapp.isport.com.basicres.commonutil.LoadImageUtil;
import brandapp.isport.com.basicres.commonutil.ToastUtils;
import brandapp.isport.com.basicres.commonutil.TokenUtil;
import brandapp.isport.com.basicres.commonutil.UIUtils;
import brandapp.isport.com.basicres.commonview.TitleBarView;
import brandapp.isport.com.basicres.entry.bean.PhotoEventBean;
import brandapp.isport.com.basicres.entry.bean.UpdatePhotoBean;
import brandapp.isport.com.basicres.mvp.BaseMVPTitleActivity;
import brandapp.isport.com.basicres.service.observe.NetProgressObservable;
import id.zelory.compressor.Compressor;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import phone.gym.jkcq.com.commonres.commonutil.BitmapUtils;
import phone.gym.jkcq.com.commonres.commonutil.PhotoChoosePopUtil;
import phone.gym.jkcq.com.socialmodule.R;
import phone.gym.jkcq.com.socialmodule.personal.presenter.UpgradeImagePresenter;
import phone.gym.jkcq.com.socialmodule.personal.view.UpgradeImageView;

public class FriendActivityImageShow extends BaseMVPTitleActivity<UpgradeImageView, UpgradeImagePresenter>
        implements View.OnClickListener, UpgradeImageView {


    private ImageView ivPic;
    private TextView btnSeclectPic;
    //    private ArrayList<String> picList;
    private String picList;

    private PhotoChoosePopUtil photoChoosePopUtil;//弹出选择从哪里读取图片的pop

    private File cameraSavePath;//拍照照片路径
    private Uri uri;

    private static final int RC_CAMERA_PERM = 123;

    @Override
    protected int getLayoutId() {
        return R.layout.friend_app_activity_image_show;
    }


    @Override
    protected void initView(View view) {
        btnSeclectPic = findViewById(R.id.btn_uplode);
        ivPic = findViewById(R.id.iv_pic);
    }

    @Override
    protected void initData() {
        titleBarView.setLeftIconEnable(true);
        titleBarView.setTitle("");
        titleBarView.setRightText("");
        Intent intent = getIntent();
        picList = intent.getStringExtra("pic_list");
        if (picList != null) {
            LoadImageUtil.getInstance().loadCirc(context, picList, ivPic);
        }
    }

    @Override
    protected void initEvent() {
        btnSeclectPic.setOnClickListener(this);

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

    private File actualImage;
    String photoPath;


    @Override
    public void onClick(View v) {

        if (v.getId() == R.id.btn_uplode) {
            showPhotoChoosePop();
        }

    }


    /**
     * 打开相机和存储权限
     */

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
                        if (!checkNet())
                            return;
                        goCamera();
                    }

                    @Override
                    public void onGetPermissionNo() {
                        // ToastUtil.showTextToast(FriendActivityImageShow.this, UIUtils.getString(R.string.location_permissions));
                    }
                });

    }

    /**
     * 打开存储权限
     */

    private void checkStoragePersiomm() {
        PermissionManageUtil permissionManage = new PermissionManageUtil(this);
        RxPermissions mRxPermission = new RxPermissions(this);
        if (!mRxPermission.isGranted(Manifest.permission.READ_EXTERNAL_STORAGE)) {
            permissionManage.requestPermissions(new RxPermissions(this), Manifest.permission.READ_EXTERNAL_STORAGE,
                    UIUtils.getString(R.string.permission_storage), new
                            PermissionManageUtil.OnGetPermissionListener() {

                                @Override
                                public void onGetPermissionYes() {
                                    if (!checkNet())
                                        return;
                                    gallery();
                                }

                                @Override
                                public void onGetPermissionNo() {
                                    ToastUtils.showToastLong(FriendActivityImageShow.this,
                                            UIUtils.getString(R.string.location_permissions));
                                }
                            });
        } else {
            if (!checkNet())
                return;
            gallery();
        }
//        permissionManage.requestPermissionsGroup(new RxPermissions(this),
//                                                 PermissionGroup.STORAGE, new PermissionManageUtil
// .OnGetPermissionListener() {
//                    @Override
//                    public void onGetPermissionYes() {
//                        goPhotoAlbum();
//                    }
//
//                    @Override
//                    public void onGetPermissionNo() {
//                        ToastUtil.showTextToast(ActivityImageShow.this, UIUtils.getString(R.string
// .location_permissions));
//                    }
//                });


     /*   new RxPermissions(this).request(Manifest.permission.CAMERA).subscribe(new Consumer<Boolean>() {
            @Override
            public void accept(Boolean aBoolean) throws Exception {
                if(aBoolean){

                }
            }
        });*/


    }


    /**
     * 从相册获取
     */
    public void gallery() {
//        NetProgressObservable.getInstance().show(false);
        Intent intent = new Intent(Intent.ACTION_PICK);    // 激活系统图库，选择一张图片
        intent.setType("image/*");
        startActivityForResult(intent, PHOTO_REQUEST_GALLERY); // 开启一个带有返回值的Activity，请求码为PHOTO_REQUEST_GALLERY
    }


    //激活相机操作
    private void goCamera() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (FileUtil.isSDExists()) {
            String date = String.valueOf(System.currentTimeMillis());
            cameraSavePath = new File(FileUtil.getImageFile().getAbsolutePath(), date + ".jpeg");
            if (!cameraSavePath.exists()) {
                FileUtil.createFile(cameraSavePath.getAbsolutePath());
            }
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                uri = FileProvider.getUriForFile(FriendActivityImageShow.this, getPackageName() + ".fileprovider",
                        cameraSavePath);
                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            } else {
                uri = Uri.fromFile(cameraSavePath);
            }
            intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
            FriendActivityImageShow.this.startActivityForResult(intent, PHOTO_REQUEST_CAMERA);
        }
    }


    /**
     * 使用鲁班RxJava模式压缩,上传给服务器
     *
     * @param path
     */
    private void initLuBanRxJava(String path) {
        File file = null;
        try {
            file = new Compressor(this)
                    .setMaxWidth(480)
                    .setMaxHeight(480)
                    .setQuality(75)
                    .setCompressFormat(Bitmap.CompressFormat.JPEG)
                    .setDestinationDirectoryPath(FileUtil.getSDPath())
                    .compressToFile(new File(path));
            new Compressor(this)
                    .compressToFileAsFlowable(file)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Consumer<File>() {
                        @Override
                        public void accept(File file) {
                            mActPresenter.saveHeadImage(file, TokenUtil.getInstance().getPeopleIdStr(BaseApp.getApp()));
                        }
                    }, new Consumer<Throwable>() {
                        @Override
                        public void accept(Throwable throwable) {
                            throwable.printStackTrace();
                            // showError(throwable.getMessage());
                        }
                    });


        } catch (Exception e) {
            e.printStackTrace();
        }


    }


    @Override
    protected UpgradeImagePresenter createPresenter() {
        return new UpgradeImagePresenter(this);
    }


    @Override
    public void successSaveImageUrl(String url) {
        // EventBus.getDefault().post(new PhotoEventBean(bean));
    }

    @Override
    public void successSaveHeadUrl(UpdatePhotoBean updatePhotoBean) {
        EventBus.getDefault().post(new PhotoEventBean(updatePhotoBean));
        finish();
    }

    @Override
    public void successOption() {

    }


    private final int PHOTO_REQUEST_CAMERA = 1;
    // 从相册中选择
    private final int PHOTO_REQUEST_GALLERY = 2;

    private final int PHOTO_CUT = 3;
    private File tempFile;
    private File cutFile;
    private String mImgPath;

    private void showPhotoChoosePop() {
        if (null == photoChoosePopUtil) {
            photoChoosePopUtil = new PhotoChoosePopUtil(context);
        }
        photoChoosePopUtil.show(getWindow().getDecorView());
        photoChoosePopUtil.setOnPhotoChooseListener(new PhotoChoosePopUtil.OnPhotoChooseListener() {
            @Override
            public void onChooseCamera() {
                checkCamera();
            }

            @Override
            public void onChoosePhotograph() {
                checkFileWritePermissions();
            }
        });
    }

    private void checkCamera() {
        new RxPermissions(this)
                .request(Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean aBoolean) throws Exception {
                        if (aBoolean) {
                            camera();
                        } else {
                            com.blankj.utilcode.util.ToastUtils.showLong("no permission");
                        }
                    }
                });
    }






    /*
     * 从相机获取
     */
    public void camera() {
//        NetProgressObservable.getInstance().show(false);
        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
        if (FileUtil.isSDExists()) {
            String date = String.valueOf(System.currentTimeMillis());
            tempFile = new File(FileUtil.getImageFile().getAbsolutePath(), date + ".jpeg");
            if (!tempFile.exists()) {
                FileUtil.createFile(tempFile.getAbsolutePath());
            }

            Uri uri;
            if (Build.VERSION.SDK_INT >= 24) {
                uri = FileProvider.getUriForFile(context.getApplicationContext(), getPackageName() + ".fileprovider", tempFile);
            } else {
                uri = Uri.fromFile(tempFile);
            }
            Log.e("NewShareActivity", "uri=" + uri.getPath());
            Log.e("NewShareActivity", "tempFile=" + tempFile.getName());
            String path = BitmapUtils.getRealFilePath(context, uri);
            Log.e("NewShareActivity", "tempFileRealFilePath" + path);
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
            startActivityForResult(intent, PHOTO_REQUEST_CAMERA);
        }
    }

    private void checkFileWritePermissions() {
        new RxPermissions(this)
                .request(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean aBoolean) throws Exception {
                        if (aBoolean) {
                            gallery();
                        } else {
                            ToastUtils.showToast(FriendActivityImageShow.this, "open permission");
                        }
                    }
                });
    }


    /**
     * 调用系统裁剪
     *
     * @param uri
     */
    private void startPhotoZoom(Uri uri) {
        try {
            int size = (int) (ScreenUtils.getScreenWidth() * 1.0);
            Intent intent = new Intent("com.android.camera.action.CROP");
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                //添加这一句表示对目标应用临时授权该Uri所代表的文件
                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);

            }
            //裁剪后输出路径
            if (FileUtil.isSDExists()) {


                String date = String.valueOf(System.currentTimeMillis());
                //            mImgPath=getExternalFilesDir(null).toString()+ "/" + date + ".jpg";
                //            mImgPath= getExternalFilesDir(Environment.DIRECTORY_PICTURES).toString()+ "/" + date + ".jpg";
                mImgPath = FileUtil.getImageFile().getAbsolutePath() + "/" + date + ".jpg";
                cutFile = new File(mImgPath);
                if (!cutFile.exists()) {
                    FileUtil.createFile(cutFile.getAbsolutePath());
                }
                //所有版本这里都这样调用
                intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(cutFile));


//               intent.putExtra(MediaStore.EXTRA_OUTPUT,  getUriForFile(NewShareActivity.this,cutFile));
//                intent.putExtra(MediaStore.EXTRA_OUTPUT,  uri);
                //输入图片路径
                intent.setDataAndType(uri, "image/*");
                intent.putExtra("crop", "true");
                if (Build.MANUFACTURER.equals("HUAWEI")) {
                    intent.putExtra("aspectX", 9998);
                    intent.putExtra("aspectY", 9999);
                } else {
                    intent.putExtra("aspectX", 1);
                    intent.putExtra("aspectY", 1);
                }

            /*    intent.putExtra("outputX", size);
                intent.putExtra("outputY", size);*/
                intent.putExtra("outputX", 480);
                intent.putExtra("outputY", 480);
                intent.putExtra("scale", true);
                intent.putExtra("scaleUpIfNeeded", true);
                intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
                intent.putExtra("return-data", false);

//                uritempFile = Uri.parse("file://" + "/" + Environment.getExternalStorageDirectory().getPath() + "/brandapp/" + "small.jpg");
//                intent.putExtra(MediaStore.EXTRA_OUTPUT, uritempFile);
//                intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
                startActivityForResult(intent, PHOTO_CUT);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Uri uritempFile;

    /**
     * 获取uri
     *
     * @param context
     * @param file
     * @return
     */
    public Uri getUriForFile(Context context, File file) {
        Uri fileUri = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            fileUri = FileProvider.getUriForFile(context, context.getApplicationContext().getPackageName() + ".fileprovider", file);
        } else {
            fileUri = Uri.fromFile(file);
        }
        return fileUri;
    }

    private void setCustomBg(String path) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {

                //  LoadImageUtil.getInstance().displayImagePath(EditUserInfo.this, filePath, ivBg);
                //  initLuBanRxJava(filePath);

                File file = new File(path);

                Log.e("setCustomBg:", file + ""+"-------path");
                if (file != null) {
                    Log.e("setCustomBg:", file.length() + "");
                }

                if (file != null && file.length() > 0) {
                    mActPresenter.saveHeadImage(file, TokenUtil.getInstance().getPeopleIdStr(BaseApp.getApp()));
                }
              /*  if (cutFile != null) {
                    mActPresenter.saveHeadImage(cutFile, TokenUtil.getInstance().getPeopleIdStr(BaseApp.getApp()));
                } else {
                    mActPresenter.saveHeadImage(new File(path), TokenUtil.getInstance().getPeopleIdStr(BaseApp.getApp()));
                }*/

                // BitmapDrawable bd = new BitmapDrawable(ImageUtils.getBitmap(filePath));
                // BitmapDrawable bd = new BitmapDrawable();
                /*layout_bgiamge.setBackground(bd);*/
               /* iv_sure1.setVisibility(View.GONE);
                iv_sure2.setVisibility(View.GONE);
                iv_sure3.setVisibility(View.GONE);
                fl_share_content.setBackground(bd);
                //setAlpha 0-255
                fl_share_content.getBackground().mutate().setAlpha(153);*/
            }
        });


    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        Log.e("onActivityResult", "requestCode=" + requestCode + "resultCode=" + resultCode);
        switch (requestCode) {
            case PHOTO_REQUEST_GALLERY:

                // 从相册返回的数据
                if (data != null && resultCode == RESULT_OK) {
                    // 得到图片的全路径
                    Uri uri = data.getData();
                    String path = BitmapUtils.getRealFilePath(context, uri);
                    if ("Redmi4A".equals(AppUtil.getModel())) {
                        initLuBanRxJava(path);
                    } else {
                        startPhotoZoom(uri);
                    }

                } else {
                    NetProgressObservable.getInstance().hide();
                }
                break;
            case PHOTO_REQUEST_CAMERA:
                if (resultCode == RESULT_OK) {
                    Uri uri = getUriForFile(this, tempFile);
                    String path = BitmapUtils.getRealFilePath(context, uri);
                    if ("Redmi4A".equals(DeviceUtils.getModel())) {
                        Log.e("setCustomBg",tempFile+"");
                       initLuBanRxJava(path);
                    } else {
                        startPhotoZoom(uri);
                    }
                } else {
                    NetProgressObservable.getInstance().hide();
                }
                break;
            case PHOTO_CUT:
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        setCustomBg(mImgPath);
                    }
                }).start();
                break;
            default:
        }
    }




    public String saveImage(String name, Bitmap bmp) {
        File appDir = new File(Environment.getExternalStorageDirectory().getPath());
        if (!appDir.exists()) {
            appDir.mkdir();
        }
        String fileName = name + ".jpg";
        File file = new File(appDir, fileName);
        try {
            FileOutputStream fos = new FileOutputStream(file);
            bmp.compress(Bitmap.CompressFormat.PNG, 100, fos);
            fos.flush();
            fos.close();
            return file.getAbsolutePath();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}
