package brandapp.isport.com.basicres;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import com.isport.brandapp.basicres.R;

import brandapp.isport.com.basicres.commonview.TitleBarView;

public abstract class BaseTitleActivity extends BaseActivity {


    protected TitleBarView titleBarView;
    protected View frameBodyLine;


    @Override
    public View setBodyView() {
        View frameView = LayoutInflater.from(this).inflate(R.layout.common_activity_frame_layout, null);

        LinearLayout frame_body_content = (LinearLayout) frameView
                .findViewById(R.id.frame_body_content);

        View view = inflate(getLayoutId());

        if (view != null) {
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    frame_body_content.getLayoutParams().height);

            frame_body_content.removeAllViews();
            try {
                frame_body_content.addView(view, params);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }

        return frameView;
    }

    @Override
    protected void initBaseData() {
        super.initBaseData();
        initToolBar();
    }

    protected void initToolBar() {
        // toolbar = (Toolbar) findViewById(R.id.title_layout_bar);
        titleBarView = (TitleBarView) findViewById(R.id.title_bar);
        frameBodyLine = (View) findViewById(R.id.frame_body_line);
        if (null != setToolBarTitle()) {
            if (titleBarView != null) {
                titleBarView.setTitle(setToolBarTitle());
            } else {
                // toolbar.setTitle(setToolBarTitle());
            }
        }

        //setSupportActionBar(toolbar);
        // getSupportActionBar().setHomeButtonEnabled(false); //设置返回键可用
        // getSupportActionBar().setDisplayHomeAsUpEnabled(false);

        // toolbar.setTitleTextColor(Color.parseColor("#ffffff")); //设置标题颜色
    }

    /**
     * 在setSupportActionBar之后有用
     */
    protected String setToolBarTitle() {
        return null;
    }

    public TitleBarView getTitleBarView() {
        return titleBarView;
    }


}