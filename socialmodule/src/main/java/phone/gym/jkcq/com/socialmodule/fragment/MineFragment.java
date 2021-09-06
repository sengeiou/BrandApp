package phone.gym.jkcq.com.socialmodule.fragment;

import android.os.Bundle;
import android.view.View;

import com.gyf.immersionbar.ImmersionBar;

import brandapp.isport.com.basicres.BaseFragment;
import phone.gym.jkcq.com.socialmodule.R;

public class MineFragment extends BaseFragment {
    private static final String URL = "url";
    private static final String POSITION = "position";

    public static MineFragment newInstance(String colors, int position) {

        Bundle args = new Bundle();
        //args.putSerializable(URL, ((ArrayList<String>) colors));
        args.putInt(POSITION, position);
        MineFragment fragment = new MineFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.friend_activity_personalpagehome;
    }

    @Override
    protected void initView(View view) {

    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initEvent() {

    }

    @Override
    public void initImmersionBar() {
        ImmersionBar.with(this)
                .init();
    }
}
