package com.isport.brandapp.dialog;

import android.content.Context;
import android.content.res.Resources;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.isport.brandapp.R;

import brandapp.isport.com.basicres.commonalertdialog.BaseDialog;
import brandapp.isport.com.basicres.commonutil.TokenUtil;


/**
 * 自定义加载进度条，有旋转进度条+TextView
 */
public class CommuniteDeviceSportDetailGuideDialog extends BaseDialog {
    TextView tvOk;
    Context mContext;


    public CommuniteDeviceSportDetailGuideDialog(Context context, String key, int animation) {
        super(context, R.style.theme_customer_progress_dialog);
        this.setContentView(R.layout.dialog_device_left_right_guide);
        getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
        getWindow().setWindowAnimations(animation);
        mContext = context;
        tvOk = findViewById(R.id.tv_known);
        tvOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TokenUtil.getInstance().updateKeyValue(mContext, "true", key);
                cancelDialog();
            }
        });
        this.setCanceledOnTouchOutside(false);
    }


    /**
     * 显示默认消息对话框
     */
    public void showDialog() {
        try {

            this.show();
            this.setCanceledOnTouchOutside(false);
        } catch (Resources.NotFoundException e) {
            e.printStackTrace();
        }
    }


    /**
     * 显示是否可按返回键取消对话框
     *
     * @param isback 是否允许返回键响应
     */
    public void showDialog(boolean isback) {
        this.setCancelable(!isback);
        this.show();
    }

    /**
     * 显示帶字符串并設置是否可按返回鍵取消对话�?
     *
     * @param resId  资源id
     * @param isback true是不相应back�?
     */


    /**
     * 关闭对话
     */
    public void cancelDialog() {
        try {
            this.dismiss();
            //ImmersionBar.destroy(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
