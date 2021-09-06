package phone.gym.jkcq.com.socialmodule.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;

import com.gyf.immersionbar.ImmersionBar;
import com.shuyu.gsyvideoplayer.video.StandardGSYVideoPlayer;

import androidx.annotation.Nullable;
import brandapp.isport.com.basicres.BaseFragment;
import phone.gym.jkcq.com.commonres.common.JkConfiguration;
import phone.gym.jkcq.com.socialmodule.R;

public class VideoFragment extends BaseFragment {

    StandardGSYVideoPlayer gsyVideoManager;
    View view_pause;

    private String url = "";
    private long mCurrentPosition = 0L;
    Handler handler = new Handler();

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_video;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            url = (String) getArguments().getSerializable(FROMURL);
            // mCurrentPosition = getArguments().getInt(POSITION);
        }
    }

    @Override
    protected void initView(View view) {
        gsyVideoManager = view.findViewById(R.id.videoPlayer);
        view_pause = view.findViewById(R.id.view_pause);

       /* backButton.visibility = GONE
        titleTextView.visibility = GONE
        fullscreenButton.visibility = GONE
        isNeedShowWifiTip = false
        isLooping = true
        dismissControlTime = 0*/
        gsyVideoManager.getTitleTextView().setVisibility(View.GONE);
        gsyVideoManager.getBackButton().setVisibility(View.GONE);
       // gsyVideoManager.getFullscreenButton().setVisibility(View.GONE);
        gsyVideoManager.setLooping(true);
        gsyVideoManager.setNeedShowWifiTip(false);
        gsyVideoManager.setDismissControlTime(0);

    }

    @Override
    public void onResume() {
        super.onResume();
        if (mCurrentPosition > 0) {
            //处在社区界面才播放
            if(JkConfiguration.sCurrentFragmentTAG.equals(JkConfiguration.FRAGMENT_COMMUNITY)){
                gsyVideoManager.onVideoResume(false);
            }
        } else {
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    gsyVideoManager.startPlayLogic();
                }
            }, 200);
        }
    }



    @Override
    public void onPause() {
        super.onPause();
        gsyVideoManager.onVideoPause();
        mCurrentPosition = gsyVideoManager.getGSYVideoManager().getPlayer().getBufferedPercentage();
        //mCurrentPosition = videoPlayer?.getGSYVideoManager()?.currentPosition ?: 0
    }


    @Override
    protected void initData() {
        view_pause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (gsyVideoManager.getGSYVideoManager().isPlaying()) {
                    gsyVideoManager.onVideoPause();
                } else {
                    gsyVideoManager.onVideoResume(false);
                }
            }
        });
        mCurrentPosition = 0;
        //gsyVideoManager.startPlayLogic();
        gsyVideoManager.setUpLazy(url, false, null, null, "");

    }

    /**
     * 选择普通模式
     */
   /* public void initVideo() {
        //外部辅助的旋转，帮助全屏
        orientationUtils = new OrientationUtils(this, gsyVideoManager, getOrientationOption());
        //初始化不打开外部的旋转
        orientationUtils.setEnable(false);
        if (getGSYVideoPlayer().getFullscreenButton() != null) {
            getGSYVideoPlayer().getFullscreenButton().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showFull();
                    clickForFullScreen();
                }
            });
        }
    }*/


    /**
     * 选择builder模式
     */
    public void initVideoBuilderMode() {
    }


    @Override
    protected void initEvent() {

    }

    private static final String POSITION = "position";
    private static final String FROMURL = "url";

    public static VideoFragment newInstance(String url, int position) {

        Bundle args = new Bundle();
        args.putSerializable(FROMURL, url);
        args.putInt(POSITION, position);
        VideoFragment fragment = new VideoFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void initImmersionBar() {
        ImmersionBar.with(this)
                .init();
    }
}
