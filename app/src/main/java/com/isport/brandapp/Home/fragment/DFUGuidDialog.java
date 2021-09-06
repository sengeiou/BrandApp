package com.isport.brandapp.Home.fragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.isport.brandapp.R;

import java.io.IOException;

import phone.gym.jkcq.com.commonres.common.JkConfiguration;
import brandapp.isport.com.basicres.commonutil.UIUtils;
import pl.droidsonroids.gif.GifDrawable;
import pl.droidsonroids.gif.GifImageView;


/**
 * 自定义加载进度条，有旋转进度条+TextView
 */
public class DFUGuidDialog {

    private AlertDialog dialog;
    LinearLayout layout;
    private Activity mActivity;
    //private Context mContext;

    LinearLayout rlOnscale;
    TextView tvTitle, tvContent, tvKnown;
    // ImageView ivPic;
    GifImageView ivPic;

    public DFUGuidDialog(Activity activity, int deviceType) {
        this.mActivity = activity;
        // this.mContext = activity;
        //StatusBarCompat.setStatusBarColor(activity.getWindow(), mContext.getResources().getColor(com.isport.brandapp.R.color.transparent), true);

        dialog = new AlertDialog.Builder(mActivity, R.style.alert_dialog).create();
        dialog.setCanceledOnTouchOutside(false);
        dialog.setCancelable(false);
        dialog.show();
        dialog.getWindow().setContentView(R.layout.activity_dfu_guide);
        tvTitle = dialog.getWindow().findViewById(R.id.tv_title);
        tvContent = dialog.getWindow().findViewById(R.id.tv_conttent);
        ivPic = dialog.getWindow().findViewById(R.id.iv_pic);
        tvKnown = dialog.getWindow().findViewById(R.id.btn_known);

        if (deviceType == JkConfiguration.DeviceType.Watch_W813) {
            tvContent.setText(UIUtils.getString(R.string.w813dfu_guide));
            try {
                GifDrawable gifFromResource = new GifDrawable(activity.getResources(), R.drawable.w813ota);
                ivPic.setImageDrawable(gifFromResource);
            } catch (IOException e) {
                e.printStackTrace();
            }
            //   LoadImageUtil.getInstance().loadGif(mActivity, R.drawable.w813ota, ivPic);
        } else {
            tvContent.setText(UIUtils.getString(R.string.w814_dfu_guide));
            //LoadImageUtil.getInstance().loadGif(mActivity, R.drawable.w814ota, ivPic);
            try {
                GifDrawable gifFromResource = new GifDrawable(activity.getResources(), R.drawable.w814ota);
                ivPic.setImageDrawable(gifFromResource);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        tvKnown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                cancelDialog();

            }
        });
    }

    public void isShow() {
        if (!isLiving(mActivity)) {
            return;
        }


        try {
            if (dialog != null && !dialog.isShowing()) {
                dialog.show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * 判断activity是否存活
     */
    private static boolean isLiving(Activity activity) {

        if (activity == null) {
            return false;
        }

        return true;
    }


    /**
     * 显示是否可按返回键取消对话框
     *
     * @param isback 是否允许返回键响应
     */
    public void showDialog(boolean isback) {
        dialog.show();
    }


    /**
     * 关闭对话
     */
    public void cancelDialog() {
        try {
            if (dialog != null && dialog.isShowing()) {
                dialog.dismiss();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


    }
}
