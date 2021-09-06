package com.isport.brandapp.login;

import android.Manifest;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.isport.blelibrary.managers.Constants;
import com.isport.blelibrary.utils.Logger;
import com.isport.brandapp.App;
import com.isport.brandapp.R;
import com.isport.brandapp.banner.recycleView.utils.ToastUtil;
import com.isport.brandapp.login.presenter.ActivityImageShowPresenter;
import com.isport.brandapp.login.view.ActivityImageShowView;
import com.tbruyelle.rxpermissions2.RxPermissions;

import org.greenrobot.eventbus.EventBus;

import java.io.File;

import androidx.annotation.Nullable;
import androidx.core.content.FileProvider;
import brandapp.isport.com.basicres.BaseApp;
import brandapp.isport.com.basicres.action.BaseAction;
import brandapp.isport.com.basicres.action.UserInformationBeanAction;
import brandapp.isport.com.basicres.commonbean.UserInfoBean;
import brandapp.isport.com.basicres.commonpermissionmanage.PermissionGroup;
import brandapp.isport.com.basicres.commonpermissionmanage.PermissionManageUtil;
import brandapp.isport.com.basicres.commonutil.FileUtil;
import brandapp.isport.com.basicres.commonutil.LoadImageUtil;
import brandapp.isport.com.basicres.commonutil.ToastUtils;
import brandapp.isport.com.basicres.commonutil.TokenUtil;
import brandapp.isport.com.basicres.commonutil.UIUtils;
import brandapp.isport.com.basicres.commonutil.getPhotoFromPhotoAlbum;
import brandapp.isport.com.basicres.commonview.TitleBarView;
import brandapp.isport.com.basicres.entry.UserInformationBean;
import brandapp.isport.com.basicres.entry.bean.PhotoEventBean;
import brandapp.isport.com.basicres.entry.bean.UpdatePhotoBean;
import brandapp.isport.com.basicres.gen.UserInformationBeanDao;
import brandapp.isport.com.basicres.mvp.BaseMVPTitleActivity;
import brandapp.isport.com.basicres.net.userNet.CommonUserAcacheUtil;
import brandapp.isport.com.basicres.service.observe.NetProgressObservable;
import id.zelory.compressor.Compressor;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import phone.gym.jkcq.com.commonres.commonutil.DisplayUtils;
import phone.gym.jkcq.com.commonres.commonutil.PhotoChoosePopUtil;

public class ActivityImageShow extends BaseMVPTitleActivity<ActivityImageShowView, ActivityImageShowPresenter>
        implements View.OnClickListener, ActivityImageShowView {
    private final int PHOTO_REQUEST_GALLERY = 2;    // 从相册中选择
    private final int PHOTO_REQUEST_CAMERA = 1;     //相机拍照
    private final int PHOTO_REQUEST_CUT = 3;//裁剪

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
        return R.layout.app_activity_image_show;
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
        Logger.myLog("CustomRepository == picList " + picList);
        if (picList != null) {
            if (App.appType() == App.httpType) {
                LoadImageUtil.getInstance().loadCirc(context, picList, ivPic);
            } else {
                if (!TextUtils.isEmpty(picList)) {
                    ivPic.setImageBitmap(BitmapFactory.decodeFile(picList));
                }
                //LoadImageUtil.getInstance().displayImagePath(context, headUrl, ivHead);
            }
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
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case PHOTO_REQUEST_GALLERY:
                // 从相册返回的数据
                if (data != null && resultCode == RESULT_OK) {
                    // 得到图片的全路径
                    photoPath = getPhotoFromPhotoAlbum.getRealPathFromUri(this, data.getData());
                    Logger.myLog("PHOTO_REQUEST_GALLERY" + photoPath);
                    LoadImageUtil.getInstance().displayImagePath(context, photoPath, ivPic);
                    if ("Redmi4A".equals(getModel())) {
                        // LogTest.test("path="+path);
                        initLuBanRxJava(photoPath);
                    } else {
                        initLuBanRxJava(photoPath);
                        //startPhotoZoom(uri);
                    }
                    //initLuBanRxJava(photoPath);
                } else {
                    NetProgressObservable.getInstance().hide();
                }
                break;
            case PHOTO_REQUEST_CUT:

                initLuBanRxJava(mImgPath);

                break;
            case PHOTO_REQUEST_CAMERA:
                if (resultCode == RESULT_OK) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                        photoPath = String.valueOf(cameraSavePath);
                    } else {
                        photoPath = uri.getEncodedPath();
                    }
                    if (App.appType() == App.httpType) {
                        LoadImageUtil.getInstance().displayImagePath(context, photoPath, ivPic);
                    } else {
                        if (TextUtils.isEmpty(photoPath)) {
                            LoadImageUtil.getInstance().displayImagePath(context, photoPath, ivPic);
                        } else {
                            ivPic.setImageBitmap(BitmapFactory.decodeFile(photoPath));
                        }
                    }

                    if ("Redmi4A".equals(getModel())) {
                        // LogTest.test("path="+path);
                        initLuBanRxJava(photoPath);
                    } else {
                        initLuBanRxJava(photoPath);
                        // startPhotoZoom(uri);
                    }

                    Log.d("拍照返回图片路径:", photoPath);
                    //上传图片
                    // mActPresenter.compressPhoto(new File(path));
                } else {
                    NetProgressObservable.getInstance().hide();
                }
                break;
        }
    }

    public String getModel() {
        String model = Build.MODEL;
        if (model != null) {
            model = model.trim().replaceAll("\\s*", "");
        } else {
            model = "";
        }
        return model;
    }

    /**
     * 调用系统裁剪
     *
     * @param uri
     */
    String mImgPath;
    File cutFile;

    private void startPhotoZoom(Uri uri) {
        int size = (int) (DisplayUtils.getScreenWidth(BaseApp.getApp()) * 0.8);
        Intent intent = new Intent("com.android.camera.action.CROP");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            //添加这一句表示对目标应用临时授权该Uri所代表的文件
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);

        }

        //裁剪后输出路径
        if (FileUtil.isSDExists()) {
            String date = String.valueOf(System.currentTimeMillis());
            mImgPath = FileUtil.getImageFile().getAbsolutePath() + "/" + date + ".jpg";
            cutFile = new File(mImgPath);
            if (!cutFile.exists()) {
                FileUtil.createFile(cutFile.getAbsolutePath());
            }
            intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(cutFile));
            //输入图片路径
            intent.setDataAndType(uri, "image/*");
            intent.putExtra("crop", "true");
            //  LogTest.test("model=" + Build.MODEL);
            if (Build.MODEL.contains("VIE-AL10")) {
                intent.putExtra("aspectX", 9998);
                intent.putExtra("aspectY", 9999);
            } else {
                intent.putExtra("aspectX", 1);
                intent.putExtra("aspectY", 1);
            }

            intent.putExtra("outputX", size);
            intent.putExtra("outputY", size);
            intent.putExtra("scale", true);
            intent.putExtra("scaleUpIfNeeded", true);
            intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
            intent.putExtra("return-data", false);
            startActivityForResult(intent, PHOTO_REQUEST_CUT);
        }

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_uplode:
                showPhotoChoosePop();
                break;
        }
    }

    /**
     * 照片选择
     */
    private void showPhotoChoosePop() {
        if (null == photoChoosePopUtil) {
            photoChoosePopUtil = new PhotoChoosePopUtil(context);
        }
        photoChoosePopUtil.show(ivPic);
        photoChoosePopUtil.setOnPhotoChooseListener(new PhotoChoosePopUtil.OnPhotoChooseListener() {
            @Override
            public void onChooseCamera() {
                checkCameraPersiomm();
            }

            @Override
            public void onChoosePhotograph() {
                checkStoragePersiomm();
            }
        });
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
                        if (App.appType() == App.httpType) {
                            if (!checkNet())
                                return;
                        }
                        goCamera();
                    }

                    @Override
                    public void onGetPermissionNo() {
                        ToastUtil.showTextToast(ActivityImageShow.this, UIUtils.getString(R.string.location_permissions));
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
                                    if (App.appType() == App.httpType) {
                                        if (!checkNet())
                                            return;
                                    }
                                    goPhotoAlbum();
                                }

                                @Override
                                public void onGetPermissionNo() {
                                    ToastUtils.showToastLong(ActivityImageShow.this,
                                            UIUtils.getString(R.string.location_permissions));
                                }
                            });
        } else {
            if (App.appType() == App.httpType) {
                if (!checkNet())
                    return;
            }
            goPhotoAlbum();
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


    //激活相册操作
    private void goPhotoAlbum() {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, PHOTO_REQUEST_GALLERY);
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
                uri = FileProvider.getUriForFile(ActivityImageShow.this, getPackageName() + ".fileprovider",
                        cameraSavePath);
                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            } else {
                uri = Uri.fromFile(cameraSavePath);
            }
            intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
            ActivityImageShow.this.startActivityForResult(intent, PHOTO_REQUEST_CAMERA);
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
           /* file = new Compressor(this)
                    .setMaxWidth(640)
                    .setMaxHeight(480)
                    .setQuality(75)
                    .setCompressFormat(Bitmap.CompressFormat.WEBP)
                    .setDestinationDirectoryPath(FileUtil.getSDPath())
                    .compressToFile(new File(path));*/
            new Compressor(this)
                    .compressToFileAsFlowable(new File(path))
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Consumer<File>() {
                        @Override
                        public void accept(File file) {
                            //这里去上传图片
                            // LoadImageUtil.getInstance().displayImagePath(context, photoPath, ivPic);


                            ivPic.setImageBitmap(BitmapFactory.decodeFile(file.getAbsolutePath()));

                            if (App.appType() == App.httpType) {
                                final File[] compressFiles = new File[1];
                                compressFiles[0] = file;
                                mActPresenter.postPhotos(compressFiles);
                            } else {
                                UserInformationBean userInfoByUserId = UserInformationBeanAction.findUserInfoByUserId(Constants.defUserId);
                                UserInformationBeanDao userInformationBeanDao = BaseAction.getUserInformationBeanDao();
                                if (userInfoByUserId != null) {
                                    userInfoByUserId.setHeadImage_s(file.getAbsolutePath());
                                    userInfoByUserId.setHeadImage(file.getAbsolutePath());
                                    userInformationBeanDao.update(userInfoByUserId);
                                }

                                //TODO 保存路径到
                                UserInfoBean bean = CommonUserAcacheUtil.getUserInfo(TokenUtil.getInstance().getPeopleIdStr
                                        (BaseApp.getApp()));
                                bean.setHeadUrl(file.getAbsolutePath());
                                bean.setHeadUrlTiny(file.getAbsolutePath());
                                UpdatePhotoBean updatePhotoBean = new UpdatePhotoBean();
                                TokenUtil.getInstance().updatePepoleHeadUrl(BaseApp.getApp(), file.getAbsolutePath());
                                updatePhotoBean.headUrl = file.getAbsolutePath();
                                updatePhotoBean.headUrlTiny = file.getAbsolutePath();
                                CommonUserAcacheUtil.saveUsrInfo(TokenUtil.getInstance().getPeopleIdStr(BaseApp.getApp()), bean);

                                EventBus.getDefault().post(new PhotoEventBean(updatePhotoBean));
                                finish();
                            }

                            //compressedImage = file;
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
    protected ActivityImageShowPresenter createPresenter() {
        return new ActivityImageShowPresenter(this);
    }

    @Override
    public void postPhotosSuccess(UpdatePhotoBean bean) {


        Logger.myLog("postPhotosSuccess == " + bean.toString());
        EventBus.getDefault().post(new PhotoEventBean(bean));
        //发个通知给fragment页面
        //关掉当前页面
        finish();


        //  ToastUtil.showTextToast("保存成功");
    }


}
