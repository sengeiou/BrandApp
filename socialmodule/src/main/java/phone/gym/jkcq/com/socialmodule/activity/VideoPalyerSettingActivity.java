package phone.gym.jkcq.com.socialmodule.activity;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import brandapp.isport.com.basicres.BaseTitleActivity;
import brandapp.isport.com.basicres.commonutil.UIUtils;
import phone.gym.jkcq.com.socialmodule.R;

public class VideoPalyerSettingActivity extends BaseTitleActivity {
    LinearLayout layout_play_wifi, layout_play_4g_wifi, layout_play_close;
    ImageView iv_play_wifi, iv_play_4g_wifi, iv_play_close;

    @Override
    protected int getLayoutId() {
        return R.layout.friend_activity_palyer_setting;
    }

    @Override
    protected void initView(View view) {
        layout_play_wifi = view.findViewById(R.id.layout_play_wifi);
        layout_play_4g_wifi = view.findViewById(R.id.layout_play_4g_wifi);
        layout_play_close = view.findViewById(R.id.layout_play_close);
        iv_play_wifi = view.findViewById(R.id.iv_play_wifi);
        iv_play_4g_wifi = view.findViewById(R.id.iv_play_4g_wifi);
        iv_play_close = view.findViewById(R.id.iv_play_close);
        titleBarView.setTitle(UIUtils.getString(R.string.video_player));
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
