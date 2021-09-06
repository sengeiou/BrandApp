package phone.gym.jkcq.com.socialmodule.activity;

import android.view.View;
import android.widget.TextView;

import brandapp.isport.com.basicres.BaseActivity;
import phone.gym.jkcq.com.socialmodule.R;

public class ScanActivity extends BaseActivity {

    private TextView tv_title;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_scan;
    }

    @Override
    protected void initView(View view) {
        tv_title = findViewById(R.id.tv_title);
        tv_title.setText(R.string.friend_scan);
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
}
