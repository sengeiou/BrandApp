package com.isport.brandapp.device.publicpage;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.isport.brandapp.R;

import brandapp.isport.com.basicres.BaseActivity;
import brandapp.isport.com.basicres.BaseTitleActivity;
import brandapp.isport.com.basicres.commonutil.AppUtil;

public class ActivityDeviceUnbindGuide extends BaseActivity {
    private TextView tvKnown;
    private ImageView iv_pic;

    @Override
    protected int getLayoutId() {
        return R.layout.app_activity_unbind_device_guide_layout;
    }

    @Override
    protected void initView(View view) {
        tvKnown = view.findViewById(R.id.tv_known);
        iv_pic = view.findViewById(R.id.iv_pic);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initEvent() {
        tvKnown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        if (AppUtil.isZh(this)) {
            iv_pic.setImageResource(R.drawable.bg_unbind_device_guide);
        } else {
            iv_pic.setImageResource(R.drawable.bg_unbind_device_guide_en);
        }

    }

    @Override
    protected void initHeader() {

    }
}
