package com.isport.brandapp.home.fragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.view.View;
import android.widget.LinearLayout;

import org.greenrobot.eventbus.EventBus;

import brandapp.isport.com.basicres.commonutil.MessageEvent;


/**
 * 自定义加载进度条，有旋转进度条+TextView
 */
public class ScaleDialog {

    private AlertDialog dialog;
    LinearLayout layout;
    private Activity mActivity;
    //private Context mContext;

    LinearLayout rlOnscale;

    public ScaleDialog(Activity activity) {
        this.mActivity = activity;
        // this.mContext = activity;
        //StatusBarCompat.setStatusBarColor(activity.getWindow(), mContext.getResources().getColor(com.isport.brandapp.R.color.transparent), true);

        dialog = new AlertDialog.Builder(mActivity, com.isport.brandapp.R.style.alert_dialog).create();
        dialog.setCanceledOnTouchOutside(false);
        dialog.setCancelable(false);
        dialog.show();
        dialog.getWindow().setContentView(com.isport.brandapp.R.layout.app_scale_layout);
        rlOnscale = dialog.getWindow().findViewById(com.isport.brandapp.R.id.rl_onscale);
        rlOnscale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                EventBus.getDefault().post(new MessageEvent(MessageEvent.reconnect_device));
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
