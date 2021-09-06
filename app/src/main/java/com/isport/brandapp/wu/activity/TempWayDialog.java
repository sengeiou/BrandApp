package com.isport.brandapp.wu.activity;

import android.content.Context;
import android.content.res.Resources;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

import com.isport.brandapp.R;

import brandapp.isport.com.basicres.commonalertdialog.BaseDialog;


/**
 * 自定义加载进度条，有旋转进度条+TextView
 */
public class TempWayDialog extends BaseDialog {

    Button btn_known;


    public TempWayDialog(Context context) {
        super(context, R.style.theme_customer_progress_dialog);
        this.setContentView(R.layout.activity_temp_measure_way);
        getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
        this.setCanceledOnTouchOutside(false);
        btn_known = findViewById(R.id.btn_known);
        btn_known.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cancelDialog();
            }
        });
    }


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


}
