package com.isport.brandapp.wu.activity;

import android.view.View;
import android.widget.Button;

import com.isport.brandapp.R;

import brandapp.isport.com.basicres.BaseTitleActivity;

public class TempMeasureWayActivity extends BaseTitleActivity {

    Button btn_known;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_temp_measure_way;
    }

    @Override
    protected void initView(View view) {
        btn_known = view.findViewById(R.id.btn_known);
    }

    @Override
    protected void initData() {
        titleBarView.setTitle(getResources().getString(R.string.temp_measure_way));
        titleBarView.setLeftIconEnable(false);

    }

    @Override
    protected void initEvent() {
        btn_known.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    protected void initHeader() {

    }

}
