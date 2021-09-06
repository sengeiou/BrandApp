package phone.gym.jkcq.com.socialmodule.personal;

import android.util.Log;
import android.view.View;

import com.gyf.immersionbar.ImmersionBar;
import com.shuyu.gsyvideoplayer.GSYVideoManager;
import com.shuyu.gsyvideoplayer.model.VideoOptionModel;
import com.shuyu.gsyvideoplayer.utils.GSYVideoType;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import androidx.viewpager2.widget.ViewPager2;
import brandapp.isport.com.basicres.commonutil.MessageEvent;
import brandapp.isport.com.basicres.mvp.BaseMVPActivity;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import phone.gym.jkcq.com.socialmodule.R;
import phone.gym.jkcq.com.socialmodule.bean.ListData;
import phone.gym.jkcq.com.socialmodule.bean.UpdateFollowStateBean;
import phone.gym.jkcq.com.socialmodule.bean.response.DynamBean;
import phone.gym.jkcq.com.socialmodule.bean.result.ResultLikeBean;
import phone.gym.jkcq.com.socialmodule.personal.adpter.PersonVideoAdapter;
import phone.gym.jkcq.com.socialmodule.personal.presenter.PersonalVideoPresenter;
import phone.gym.jkcq.com.socialmodule.personal.view.PersonalVideoView;
import phone.gym.jkcq.com.socialmodule.report.bean.UpdateDynicBean;
import tv.danmaku.ijk.media.player.IjkMediaPlayer;

public class FriendPersonalVideoActivity extends BaseMVPActivity<PersonalVideoView, PersonalVideoPresenter> implements PersonalVideoView {
    ViewPager2 viewPager2;
    ArrayList<DynamBean> list = new ArrayList<>();
    PersonVideoAdapter adapter;
    int currentPostion;
    String fromUserId;
    boolean isLast;
    int currentPage;
    int videoType;
    int size;


    // public TreeMap<String, DynamBean> personalListMap = new TreeMap<>();

    @Override
    protected int getLayoutId() {
        return R.layout.friend_pesonal_video_ver_page;
    }

    @Override
    protected void initView(View view) {
        viewPager2 = view.findViewById(R.id.viewPager_ver);
    }

    @Override
    protected void initImmersionBar() {
        super.initImmersionBar();
    }

    @Override
    protected void initData() {
        GSYVideoManager.releaseAllVideos();
        GSYVideoType.setShowType(GSYVideoType.SCREEN_TYPE_FULL);
        VideoOptionModel videoOptionModel =
                new VideoOptionModel(IjkMediaPlayer.OPT_CATEGORY_PLAYER, "framedrop", 50);
        VideoOptionModel videoOptionModelDec =
                new VideoOptionModel(IjkMediaPlayer.OPT_CATEGORY_PLAYER, "mediacodec", 0);
        VideoOptionModel videoOptionModelbox =
                new VideoOptionModel(IjkMediaPlayer.OPT_CATEGORY_PLAYER, "videotoolbox", 1);
        //  关闭播放器缓冲，这个必须关闭，否则会出现播放一段时间后，一直卡主，控制台打印 FFP_MSG_BUFFERING_START
        VideoOptionModel videoOptionModelBuffer = new VideoOptionModel(IjkMediaPlayer.OPT_CATEGORY_PLAYER, "packet-buffering", 0);
        List<VideoOptionModel> list = new ArrayList<>();
        list.add(videoOptionModel);
        list.add(videoOptionModelDec);
        list.add(videoOptionModelbox);
        GSYVideoManager.instance().setOptionModelList(list);
        getIntentValue();
        Log.e("initData:", "list" + list + "position:" + currentPostion);
        /*if (list != null) {
            adapter = new PersonVideoAdapter(this, list, fromUserId);
            viewPager2.setAdapter(adapter);
            viewPager2.setCurrentItem(currentPostion, false);
            viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                    super.onPageScrolled(position, positionOffset, positionOffsetPixels);
                }

                @Override
                public void onPageSelected(int position) {
                    super.onPageSelected(position);
                    if (position == list.size() - 1) {

                        Log.e("onPageSelected", "position:" + position + ",currentPage:" + currentPage + ",fromUserId:" + fromUserId + ",videoType:" + videoType);

                        if (!isLast) {
                            mActPresenter.getPersonalVideoList(fromUserId, 10, currentPage + 1, videoType);
                        }
                    }
                }

                @Override
                public void onPageScrollStateChanged(int state) {
                    super.onPageScrollStateChanged(state);
                }
            });
        }*/


    }


    private void getIntentValue() {
        currentPostion = getIntent().getIntExtra("positon", 0);
        isLast = getIntent().getBooleanExtra("isLast", false);
        currentPage = getIntent().getIntExtra("currentPage", 0);
        videoType = getIntent().getIntExtra("videoType", 0);
        size = getIntent().getIntExtra("size", 10);
        list = getIntent().getParcelableArrayListExtra("list");
        fromUserId = getIntent().getStringExtra("fromUserId");

    }

    @Override
    protected void initEvent() {
        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels);
            }

            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                currentPostion = position;
                if (position == list.size() - 1) {
                    Log.e("onPageSelected", "position:" + position + ",currentPage:" + currentPage + ",fromUserId:" + fromUserId + ",videoType:" + videoType);
                    if (!isLast) {
                        mActPresenter.getPersonalVideoList(fromUserId, size, currentPage + 1, videoType);
                    }
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                super.onPageScrollStateChanged(state);
            }
        });
    }

    @Override
    protected void initHeader() {
        // StatusBarCompat.setStatusBarColor(getWindow(), getResources().getColor(R.color.transparent), true);
        ImmersionBar.with(this).init();

    }


    @Override
    protected PersonalVideoPresenter createPresenter() {
        return new PersonalVideoPresenter(this);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        GSYVideoManager.releaseAllVideos();
    }


    private void updateViewPage() {
        Log.e("updateViewPage", "currentPostion:" + currentPostion + "list.size():" + list.size());
        if (list != null) {
            adapter = new PersonVideoAdapter(this, list, fromUserId);
            viewPager2.setAdapter(adapter);
            if (list.size() - 1 < currentPostion) {
                currentPostion = list.size() - 1;
            }
            GSYVideoManager.releaseAllVideos();
            viewPager2.setCurrentItem(currentPostion, false);

        }
    }


    @Override
    protected void onPause() {
        super.onPause();
        endTime();
    }

    @Override
    protected void onResume() {
        super.onResume();
        startTimer();
        mActPresenter.getPersonalVideoList(fromUserId, size, 1, videoType);
        //请求数据

    }

    @Override
    public void successPersonalVideo(ListData<DynamBean> dynamBeanListData) {
        GSYVideoManager.releaseAllVideos();
        if (dynamBeanListData != null) {
            if (dynamBeanListData.getPageNum() == 1) {
                if (list == null) {
                    list = new ArrayList<>();
                } else {
                    list.clear();
                    list.addAll(dynamBeanListData.getList());
                }
                this.isLast = dynamBeanListData.getTotal() - list.size() > 0 ? false : true;
                this.currentPage = dynamBeanListData.getPageNum();
                updateViewPage();
            } else {
                if (list == null) {
                    list = new ArrayList<>();
                }
                list.addAll(dynamBeanListData.getList());
                this.isLast = dynamBeanListData.getTotal() - list.size() > 0 ? false : true;
                this.currentPage = dynamBeanListData.getPageNum();
                if (adapter != null) {
                    adapter.notifyDataSetChanged();
                }


            }
        }
    }

    @Override
    public void successDeleteAction(Boolean status) {

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void Event(MessageEvent messageEvent) {
        switch (messageEvent.getMsg()) {
            case MessageEvent.main_dynicid_update:
                UpdateDynicBean updateDynicBean = (UpdateDynicBean) messageEvent.getObj();
                for (int i = 0; i < list.size(); i++) {
                    DynamBean dynamBean = list.get(i);
                    if (dynamBean.getDynamicId().equals(updateDynicBean.getDynamicId())) {
                        dynamBean.setCommentNums(updateDynicBean.getAllSum());
                    }
                }
                adapter.notifyDataSetChanged();
                break;
            case MessageEvent.update_dynamic_follow_state:
                //传过来 imageUrl
                //传过来视频的本地地址
                UpdateFollowStateBean updateFollowStateBean = (UpdateFollowStateBean) messageEvent.getObj();
                for (int i = 0; i < list.size(); i++) {
                    DynamBean dynamBean = list.get(i);
                    if (dynamBean.getUserId().equals(updateFollowStateBean.getUserId())) {
                        dynamBean.setFollowStatus(updateFollowStateBean.getFollowStatus());
                    }
                    // personalListMap.put(dynamBean.getDynamicId(), dynamBean);
                }
                adapter.notifyDataSetChanged();

                break;

            case MessageEvent.update_dynamic_like_state:

                //需要带动态id

                ResultLikeBean likebean = (ResultLikeBean) messageEvent.getObj();
                Log.e("Event", "PageAllFragmnet update_dynamic_like_state--------" + likebean.getUserId());
                for (int i = 0; i < list.size(); i++) {
                    DynamBean dynamBean = list.get(i);
                    if (dynamBean.getUserId().equals(likebean.getUserId()) && dynamBean.getDynamicId().equals(likebean.getDynamicInfoId())) {
                        dynamBean.setWhetherPraise(likebean.isWhetherPraise());
                        dynamBean.setPraiseNums(likebean.getPraiseNums());
                    }
                }
                adapter.notifyDataSetChanged();
                break;
        }
    }


    Disposable disposableTimer;

    public void endTime() {
        Log.e("video_pause", "endTime" + disposableTimer + "----");

        if (disposableTimer != null && !disposableTimer.isDisposed()) {
            Log.e("video_pause", "endTime  VideoAllFragment" + disposableTimer + "----" + disposableTimer.isDisposed());
            disposableTimer.dispose();
        }
    }

    public void startTimer() {
        try {
            if (disposableTimer != null && !disposableTimer.isDisposed()) {
                disposableTimer.dispose();
            }
            disposableTimer = Observable.interval(200, TimeUnit.MILLISECONDS).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<Long>() {
                @Override
                public void accept(Long aLong) throws Exception {
                    EventBus.getDefault().post(new MessageEvent(MessageEvent.update_progress));
                }
            });
        } catch (Exception e) {

        }


    }


}
