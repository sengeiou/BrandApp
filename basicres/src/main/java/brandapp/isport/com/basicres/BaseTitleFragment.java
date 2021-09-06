package brandapp.isport.com.basicres;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.isport.brandapp.basicres.R;

import brandapp.isport.com.basicres.commonview.TitleBarView;

public abstract class BaseTitleFragment extends BaseFragment {

    //protected Toolbar toolbar;

    protected TitleBarView titleBarView;
    protected View frameBodyLine;

    @Override
    public View setBodyView(ViewGroup container) {
        View frameView = mInflater.inflate(R.layout.common_activity_frame_layout, container, false);

        LinearLayout frame_body_content = (LinearLayout) frameView
                .findViewById(R.id.frame_body_content);
        frameBodyLine = frameView.findViewById(R.id.frame_body_line);
        View view = LayoutInflater.from(getActivity()).inflate(getLayoutId(), null);

        if (view != null) {
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    frame_body_content.getLayoutParams().height);

            frame_body_content.removeAllViews();
            frame_body_content.addView(view, params);
        }
        return frameView;
    }

    @Override
    protected void initBaseData() {
        super.initBaseData();
        initToolBar();
    }

    protected void initToolBar() {
        //toolbar = (Toolbar) mView.findViewById(R.id.title_layout_bar);
        titleBarView = (TitleBarView) mView.findViewById(R.id.title_bar);
        if (null != setToolBarTitle()) {
            if (titleBarView != null) {
                titleBarView.setTitle(setToolBarTitle());
            } else {
                // toolbar.setTitle(setToolBarTitle());
            }
        }
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
