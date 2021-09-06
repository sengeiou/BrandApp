package phone.gym.jkcq.com.socialmodule.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.NetworkUtils;
import com.danikula.videocache.HttpProxyCacheServer;
import com.gyf.immersionbar.ImmersionBar;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshFooter;
import com.scwang.smartrefresh.layout.api.RefreshHeader;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.constant.RefreshState;
import com.scwang.smartrefresh.layout.listener.OnMultiPurposeListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;
import brandapp.isport.com.basicres.BaseApp;
import brandapp.isport.com.basicres.commonutil.Logger;
import brandapp.isport.com.basicres.commonutil.MessageEvent;
import brandapp.isport.com.basicres.commonutil.ThreadPoolUtils;
import brandapp.isport.com.basicres.commonutil.ToastUtils;
import brandapp.isport.com.basicres.commonutil.TokenUtil;
import brandapp.isport.com.basicres.commonutil.UIUtils;
import brandapp.isport.com.basicres.mvp.BaseMVPFragment;
import phone.gym.jkcq.com.commonres.common.JkConfiguration;
import phone.gym.jkcq.com.socialmodule.R;
import phone.gym.jkcq.com.socialmodule.bean.UpdateFollowStateBean;
import phone.gym.jkcq.com.socialmodule.bean.response.DynamBean;
import phone.gym.jkcq.com.socialmodule.bean.result.ResultLikeBean;
import phone.gym.jkcq.com.socialmodule.fragment.present.DynamPresent;
import phone.gym.jkcq.com.socialmodule.fragment.view.DynamView;
import phone.gym.jkcq.com.socialmodule.report.bean.UpdateDynicBean;

/**
 * @author wzc
 * @date 2019/3/30
 */
public class PageAllFragment extends BaseMVPFragment<DynamView, DynamPresent> implements DynamView {
    private HttpProxyCacheServer proxyCacheServer;

    private static final String POSITION = "position";
    private static final String DYNAMIC_TYPE = "dynamic";
    private boolean isPause;


    LinearLayout layout_fail;
    TextView btn_try_again;
    ImageView iv_empty;
    TextView tv_no_network;
    TextView tv_cheack_network;


    boolean isStart;

    ViewPager2 viewPager_all_page;
    int dynamicInfoType;

    SmartRefreshLayout home_refresh;


    ArrayList<DynamBean> list = new ArrayList<>();

    ViewPagerFragmentStateAdapter adapter;


    public static PageAllFragment newInstance(String colors, int position, int dynamicInfoType) {

        Bundle args = new Bundle();
        //args.putSerializable(URL, ((ArrayList<String>) colors));
        args.putInt(POSITION, position);
        args.putInt(DYNAMIC_TYPE, dynamicInfoType);
        PageAllFragment fragment = new PageAllFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            dynamicInfoType = getArguments().getInt(DYNAMIC_TYPE, JkConfiguration.DynamicInfoType.ALL);
            // mCurrentPosition = getArguments().getInt(POSITION);
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_all_page;
    }

    @Override
    protected void initView(View view) {
        viewPager_all_page = view.findViewById(R.id.viewPager_all_page);
        home_refresh = view.findViewById(R.id.home_refresh);
        layout_fail = view.findViewById(R.id.layout_fail);
        btn_try_again = view.findViewById(R.id.btn_try_again);
        btn_try_again = view.findViewById(R.id.btn_try_again);
        iv_empty = view.findViewById(R.id.iv_empty);
        tv_no_network = view.findViewById(R.id.tv_no_network);
        tv_cheack_network = view.findViewById(R.id.tv_cheack_network);


    }

    boolean isShowFragmentCommunity;


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void Event(MessageEvent messageEvent) {
        switch (messageEvent.getMsg()) {
            case MessageEvent.all_video_exception:
                isStart = true;
                isShowFragmentCommunity = true;
                Log.e("PageAllFragment", "all_video_exception");
                if (list != null && list.size() == 0) {
                    mFragPresenter.getFirstDynamList(TokenUtil.getInstance().getPeopleIdInt(BaseApp.getApp()), 2, dynamicInfoType);
                }
                break;
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
            case MessageEvent.show_fragment_community:
                Log.e("succcessDynamList", "show_fragment_community");
                isShowFragmentCommunity = true;
                break;
            case MessageEvent.show_fragment_other:
                Log.e("succcessDynamList", "show_fragment_other");
                isShowFragmentCommunity = false;
                Log.e("succcessDynamList", "show_fragment_other");
                break;
            case MessageEvent.video_all:
                if (isStart) {
                    mFragPresenter.getFirstDynamList(TokenUtil.getInstance().getPeopleIdInt(BaseApp.getApp()), 2, dynamicInfoType);
                }
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
                }
                adapter.notifyDataSetChanged();

                break;
            case MessageEvent.del_dynamicId:
                String del = (String) messageEvent.getObj();
                Log.e("Event", "PageAllFragmnet del_dynamicId--------" + del);
                mFragPresenter.getFirstDynamList(TokenUtil.getInstance().getPeopleIdInt(BaseApp.getApp()), 2, dynamicInfoType);
                break;
            case MessageEvent.update_dynamic_like_state:
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

    @Override
    public void onDestroy() {
        super.onDestroy();
        handler.removeCallbacksAndMessages(null);
        EventBus.getDefault().unregister(this);
    }

    @Override
    protected void initData() {
       /* ProxyCacheManager proxyCacheManager;
        String name = ConfigfileNameGenerator.generate(url);
        proxyCacheServer= ProxyCacheManager.instance().newProxy(BaseApp.getApp());
        proxyCacheServe.*/
        ThreadPoolUtils.getInstance().addTask(new Runnable() {
            @Override
            public void run() {

                if (NetworkUtils.isAvailable() && !NetworkUtils.isWifiConnected()) {
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            ToastUtils.showToast(BaseApp.getApp(), UIUtils.getString(R.string.friend_current_play_4g));
                        }
                    });
                }
            }
        });

        if (!EventBus.getDefault().isRegistered(this))
            EventBus.getDefault().register(this);
        if (!TokenUtil.getInstance().getAppFirstUseCommunity(getActivity())) {
            //需要弹出指引
            EventBus.getDefault().post(new MessageEvent(MessageEvent.hide_iv_add));
            CommuniteGuideDialog communiteGuideDialog = new CommuniteGuideDialog(getActivity());
            communiteGuideDialog.showDialog();
        }


        viewPager_all_page.setOffscreenPageLimit(1);
        viewPager_all_page.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                //这里需要大于多少的时候再滑动
                Log.e("PageFragment", "positionOffset" + positionOffset + "positionOffsetPixels" + positionOffsetPixels);
                super.onPageScrolled(position, positionOffset, positionOffsetPixels);
            }

            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                //记录当前选择的数据
                if (list.size() < position) {
                    position = list.size();
                }
                if (list.size() == 0) {
                    JkConfiguration.CurrentAllUserId = "";
                    JkConfiguration.CurrentAllDynamaId = "";
                    mFragPresenter.getFirstDynamList(TokenUtil.getInstance().getPeopleIdInt(BaseApp.getApp()), 2, dynamicInfoType);
                    return;
                }
                DynamBean dynamBean = list.get(position);
                JkConfiguration.CurrentAllUserId = dynamBean.getUserId();
                JkConfiguration.CurrentAllDynamaId = dynamBean.getDynamicId();
                if (position == list.size() - 1) {
                    //需要去请求后面的两条数据
                    mFragPresenter.getNextDynamList(TokenUtil.getInstance().getPeopleIdInt(BaseApp.getApp()), list.get(position).getDynamicId(), 8, 5, dynamicInfoType);

                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                super.onPageScrollStateChanged(state);
            }
        });


    }

    @Override
    protected void initEvent() {
        btn_try_again.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mFragPresenter.getFirstDynamList(TokenUtil.getInstance().getPeopleIdInt(BaseApp.getApp()), 2, dynamicInfoType);
            }
        });
        home_refresh.setOnMultiPurposeListener(new OnMultiPurposeListener() {
            @Override
            public void onHeaderMoving(RefreshHeader header, boolean isDragging, float percent, int offset, int headerHeight, int maxDragHeight) {

            }

            @Override
            public void onHeaderReleased(RefreshHeader header, int headerHeight, int maxDragHeight) {

            }

            @Override
            public void onHeaderStartAnimator(RefreshHeader header, int headerHeight, int maxDragHeight) {

            }

            @Override
            public void onHeaderFinish(RefreshHeader header, boolean success) {

            }

            @Override
            public void onFooterMoving(RefreshFooter footer, boolean isDragging, float percent, int offset, int footerHeight, int maxDragHeight) {

            }

            @Override
            public void onFooterReleased(RefreshFooter footer, int footerHeight, int maxDragHeight) {

            }

            @Override
            public void onFooterStartAnimator(RefreshFooter footer, int footerHeight, int maxDragHeight) {

            }

            @Override
            public void onFooterFinish(RefreshFooter footer, boolean success) {

            }

            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {

            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {

            }

            @Override
            public void onStateChanged(@NonNull RefreshLayout refreshLayout, @NonNull RefreshState oldState, @NonNull RefreshState newState) {
                Log.e("setOnMultiPurpose", "onStateChanged oldState:" + oldState + ",newState:" + newState);

                if (oldState == RefreshState.None || newState == RefreshState.PullDownToRefresh) {
                    EventBus.getDefault().post(new MessageEvent(MessageEvent.hide_radioButton));
                }
                if (oldState == RefreshState.PullDownCanceled || newState == RefreshState.None) {
                    EventBus.getDefault().post(new MessageEvent(MessageEvent.show_radioButton));
                }
                if (oldState == RefreshState.RefreshFinish || newState == RefreshState.None) {
                    EventBus.getDefault().post(new MessageEvent(MessageEvent.show_radioButton));
                }
            }
        });
        home_refresh.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                //把标题栏隐藏
                //完成就把标题栏显示
                mFragPresenter.getFirstDynamList(TokenUtil.getInstance().getPeopleIdInt(BaseApp.getApp()), 2, dynamicInfoType);
            }
        });
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        Logger.e("PageAllFragment onHiddenChanged" + hidden);
        if (!hidden) {
            isStart = true;
            isShowFragmentCommunity = true;
            //GSYVideoManager.onResume();
            //EventBus.getDefault().post(MessageEvent.start_all_player);
            Log.e("PageAllFragment", "onResume");
            if (list != null && list.size() == 0) {
                mFragPresenter.getFirstDynamList(TokenUtil.getInstance().getPeopleIdInt(BaseApp.getApp()), 2, dynamicInfoType);
            }
        }
    }

    Handler handler = new Handler();

    @Override
    public void succcessDynamList(List<DynamBean> successList) {


        if (successList != null && successList.size() > 0) {
            layout_fail.setVisibility(View.GONE);
            home_refresh.setVisibility(View.VISIBLE);
        } else {
            home_refresh.setVisibility(View.VISIBLE);
            iv_empty.setImageResource(R.drawable.bg_dynamic_empty);
            tv_cheack_network.setText(UIUtils.getString(R.string.friend_no_content));
            tv_no_network.setVisibility(View.GONE);
            btn_try_again.setVisibility(View.INVISIBLE);
            return;
        }

        if (successList != null && successList.size() > 0) {
            home_refresh.finishRefresh();

            Log.e("succcessDynamList", "isShowFragmentCommunity" + isShowFragmentCommunity);
            if (!isShowFragmentCommunity) {
                return;
            }

            this.list.clear();
            this.list.addAll(successList);
            adapter = new ViewPagerFragmentStateAdapter(getChildFragmentManager(), this.getLifecycle(), this.list);
            viewPager_all_page.setAdapter(adapter);
            /*adapter.notifyDataSetChanged();
            viewPager_all_page.setCurrentItem(0,false);*/
        } else {
            this.list.clear();
            if (adapter != null) {
                adapter.notifyDataSetChanged();
            }
        }

    }

    @Override
    public void failFirstDynamList() {
        layout_fail.setVisibility(View.VISIBLE);
        home_refresh.setVisibility(View.GONE);
        btn_try_again.setVisibility(View.VISIBLE);
        iv_empty.setImageResource(R.drawable.bg_dynamic_not_newwork);
        tv_cheack_network.setText(UIUtils.getString(R.string.friend_cheack_netWork));
        tv_no_network.setText(UIUtils.getString(R.string.friend_no_netWork));
        tv_no_network.setVisibility(View.VISIBLE);

    }

    @Override
    public void firstNoContentDynamList() {

        list.clear();
        if (adapter != null) {
            adapter.notifyDataSetChanged();
        }
        home_refresh.setVisibility(View.INVISIBLE);
        EventBus.getDefault().post(new MessageEvent(MessageEvent.show_radioButton));
        home_refresh.finishRefresh();
        layout_fail.setVisibility(View.VISIBLE);
        iv_empty.setImageResource(R.drawable.bg_dynamic_empty);
        tv_cheack_network.setText(UIUtils.getString(R.string.friend_no_content));
        tv_no_network.setVisibility(View.GONE);
        btn_try_again.setVisibility(View.INVISIBLE);
    }

    @Override
    public void succcessUpDynamList(List<DynamBean> successList) {
        //需要往前面加
    }

    @Override
    public void succcessNextDynamList(List<DynamBean> successList) {


        layout_fail.setVisibility(View.GONE);
        home_refresh.setVisibility(View.VISIBLE);
        //需要往后面加
        if (successList != null && successList.size() > 0) {
            list.addAll(successList);
            adapter.notifyDataSetChanged();

        }
    }

    @Override
    protected DynamPresent createPersenter() {
        return new DynamPresent(this);
    }

    @Override
    public void initImmersionBar() {
        ImmersionBar.with(this)
                .init();
    }

    class ViewPagerFragmentStateAdapter extends FragmentStateAdapter {

        ArrayList<DynamBean> viewList;

        public ViewPagerFragmentStateAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle, ArrayList<DynamBean> list) {
            super(fragmentManager, lifecycle);
            this.viewList = list;
        }

        @NonNull
        @Override
        public Fragment createFragment(int position) {

            Log.e("pageFragment:", "createFragment" + position);

            return VideoAllFragment.newInstance(list.get(position), position);
        }

        @Override
        public int getItemCount() {
            // Log.e("onPageSelected", "PageFragment onPageSelected: getItemCount" + "list.size():" + (list.size()) + " -------" + "viewList.size()" + viewList.size());
            return viewList.size();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        isStart = false;
        isShowFragmentCommunity = false;
        Log.e("PageAllFragment", "onPause");
    }

    @Override
    public void onResume() {
        super.onResume();
        isStart = true;
        isShowFragmentCommunity = true;
        //GSYVideoManager.onResume();
        //EventBus.getDefault().post(MessageEvent.start_all_player);
        Log.e("PageAllFragment", "onResume");
        if (list != null && list.size() == 0) {
            mFragPresenter.getFirstDynamList(TokenUtil.getInstance().getPeopleIdInt(BaseApp.getApp()), 2, dynamicInfoType);
        }
    }


}
