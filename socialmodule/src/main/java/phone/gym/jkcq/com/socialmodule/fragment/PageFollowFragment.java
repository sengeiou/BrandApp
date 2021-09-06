package phone.gym.jkcq.com.socialmodule.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

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

import brandapp.isport.com.basicres.BaseApp;
import brandapp.isport.com.basicres.commonutil.Logger;
import brandapp.isport.com.basicres.commonutil.MessageEvent;
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
public class PageFollowFragment extends BaseMVPFragment<DynamView, DynamPresent> implements DynamView {


    private static final String POSITION = "position";
    private static final String DYNAMIC_TYPE = "dynamic";


    boolean isStart = false;


    LinearLayout layout_fail;
    TextView btn_try_again;
    ImageView iv_empty;
    TextView tv_no_network;
    TextView tv_cheack_network;

    ViewPager2 viewPager_ver;
    int dynamicInfoType;

    SmartRefreshLayout home_refresh;


    ArrayList<DynamBean> list = new ArrayList<>();

    ViewPagerFragmentStateAdapter adapter;


    public static PageFollowFragment newInstance(String colors, int position, int dynamicInfoType) {

        Bundle args = new Bundle();
        //args.putSerializable(URL, ((ArrayList<String>) colors));
        args.putInt(POSITION, position);
        args.putInt(DYNAMIC_TYPE, dynamicInfoType);
        PageFollowFragment fragment = new PageFollowFragment();
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
        return R.layout.fragment_ver_page;
    }

    @Override
    protected void initView(View view) {

        viewPager_ver = view.findViewById(R.id.viewPager_ver);
        home_refresh = view.findViewById(R.id.home_refresh);
        layout_fail = view.findViewById(R.id.layout_fail);
        btn_try_again = view.findViewById(R.id.btn_try_again);
        iv_empty = view.findViewById(R.id.iv_empty);
        tv_no_network = view.findViewById(R.id.tv_no_network);
        tv_cheack_network = view.findViewById(R.id.tv_cheack_network);

    }

    boolean isShowFragmentCommunity;

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void Event(MessageEvent messageEvent) {
        switch (messageEvent.getMsg()) {
            case MessageEvent.follow_video_exception:
                isShowFragmentCommunity = true;
                isStart = true;
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
                isShowFragmentCommunity = true;
                break;
            case MessageEvent.show_fragment_other:
                isShowFragmentCommunity = false;
                break;
            case MessageEvent.video_follow:
                if (isStart) {
                    mFragPresenter.getFirstDynamList(TokenUtil.getInstance().getPeopleIdInt(BaseApp.getApp()), 2, dynamicInfoType);
                }
                break;
            case MessageEvent.update_dynamic_follow_state:
                //传过来 imageUrl
                //传过来视频的本地地址

                UpdateFollowStateBean bean = (UpdateFollowStateBean) messageEvent.getObj();
                Log.e("Event", "update_dynamic_follow_state--------" + bean.getUserId());

                ArrayList<Integer> indexList = new ArrayList<>();

                for (int i = 0; i < list.size(); i++) {
                    DynamBean dynamBean = list.get(i);
                    if (dynamBean.getUserId().equals(bean.getUserId()) && (bean.getFollowStatus() == 0 || bean.getFollowStatus() == 2)) {
                        indexList.add(i);
                    }
                }
                if (indexList.size() > 0) {
                    for (int i = indexList.size() - 1; i >= 0; i--) {
                        list.remove(i);
                    }
                    adapter.notifyDataSetChanged();
                }

                // mFragPresenter.getFirstDynamList(TokenUtil.getInstance().getPeopleIdInt(BaseApp.getApp()), 2, dynamicInfoType);
                break;
            case MessageEvent.del_dynamicId:
                String del = (String) messageEvent.getObj();

                break;
            case MessageEvent.update_dynamic_like_state:
                ResultLikeBean likebean = (ResultLikeBean) messageEvent.getObj();
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
        EventBus.getDefault().unregister(this);
    }

    @Override
    protected void initData() {
        if (!EventBus.getDefault().isRegistered(this))
            EventBus.getDefault().register(this);

        viewPager_ver.setOffscreenPageLimit(1);
        viewPager_ver.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
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
                    JkConfiguration.CurrentFollowUserId = "";
                    JkConfiguration.CurrentFollowDynamaId = "";
                    mFragPresenter.getFirstDynamList(TokenUtil.getInstance().getPeopleIdInt(BaseApp.getApp()), 2, dynamicInfoType);
                    return;
                }
                DynamBean dynamBean = list.get(position);
                //Log.e("onPageSelected", "onPageSelected:" + position + "list.size():"+(list.size())+" -------" + dynamBean);
                Log.e("onPageSelected", "onPageSelected:" + position + "list.size():" + (list.size()) + " -------");
                JkConfiguration.CurrentFollowUserId = dynamBean.getUserId();
                JkConfiguration.CurrentFollowDynamaId = dynamBean.getDynamicId();
                // Log.e("onPageSelected", "onPageSelected:" + position + "JkConfiguration.CurrentAllUserId" + JkConfiguration.CurrentAllUserId + "JkConfiguration.CurrentFollowDynamaId:" + JkConfiguration.CurrentFollowDynamaId);
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

                Log.e("setOnMultiPurpose", "onHeaderMoving");
            }

            @Override
            public void onHeaderReleased(RefreshHeader header, int headerHeight, int maxDragHeight) {
                Log.e("setOnMultiPurpose", "onHeaderReleased");
            }

            @Override
            public void onHeaderStartAnimator(RefreshHeader header, int headerHeight, int maxDragHeight) {
                Log.e("setOnMultiPurpose", "onHeaderStartAnimator");
            }

            @Override
            public void onHeaderFinish(RefreshHeader header, boolean success) {
                Log.e("setOnMultiPurpose", "onHeaderFinish" + success);
            }

            @Override
            public void onFooterMoving(RefreshFooter footer, boolean isDragging, float percent, int offset, int footerHeight, int maxDragHeight) {
                Log.e("setOnMultiPurpose", "onFooterMoving" + isDragging);
            }

            @Override
            public void onFooterReleased(RefreshFooter footer, int footerHeight, int maxDragHeight) {
                Log.e("setOnMultiPurpose", "onFooterReleased");
            }

            @Override
            public void onFooterStartAnimator(RefreshFooter footer, int footerHeight, int maxDragHeight) {
                Log.e("setOnMultiPurpose", "onFooterStartAnimator");
            }

            @Override
            public void onFooterFinish(RefreshFooter footer, boolean success) {
                Log.e("setOnMultiPurpose", "onFooterFinish");
            }

            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                Log.e("setOnMultiPurpose", "onLoadMore");
            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                Log.e("setOnMultiPurpose", "onRefresh");
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
    public void
    succcessDynamList(List<DynamBean> successList) {


        if (successList != null && successList.size() > 0) {
            layout_fail.setVisibility(View.GONE);
            home_refresh.setVisibility(View.VISIBLE);
        } else {
            home_refresh.setVisibility(View.INVISIBLE);
            EventBus.getDefault().post(new MessageEvent(MessageEvent.show_radioButton));
            home_refresh.finishRefresh();
            iv_empty.setImageResource(R.drawable.bg_dynamic_empty);
            tv_cheack_network.setText(UIUtils.getString(R.string.friend_no_content));
            tv_no_network.setVisibility(View.GONE);
            btn_try_again.setVisibility(View.INVISIBLE);
            return;
        }


        if (successList != null && successList.size() > 0) {
            home_refresh.finishRefresh();
            this.list.clear();
            this.list.addAll(successList);
            adapter = new ViewPagerFragmentStateAdapter(getChildFragmentManager(), this.getLifecycle(), this.list);
            viewPager_ver.setAdapter(adapter);

        }

    }

    @Override
    public void failFirstDynamList() {
        list.clear();

        if (adapter != null) {
            adapter.notifyDataSetChanged();
        }
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
        //没有数据了
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

            return VideoFollowFragment.newInstance(list.get(position), position);
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
        Log.e("PageFollowFragment", "onResume");
        isShowFragmentCommunity = true;
        isStart = true;
        if (list != null && list.size() == 0) {
            mFragPresenter.getFirstDynamList(TokenUtil.getInstance().getPeopleIdInt(BaseApp.getApp()), 2, dynamicInfoType);
        }
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        Logger.e("pageFollowFragement onHiddenChanged" + hidden);
        if (!hidden) {
            Log.e("PageFollowFragment", "onResume");
            isShowFragmentCommunity = true;
            isStart = true;
            if (list != null && list.size() == 0) {
                mFragPresenter.getFirstDynamList(TokenUtil.getInstance().getPeopleIdInt(BaseApp.getApp()), 2, dynamicInfoType);
            }
        }
    }
}
