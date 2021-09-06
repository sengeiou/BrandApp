package phone.gym.jkcq.com.socialmodule.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.gyf.immersionbar.ImmersionBar;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import brandapp.isport.com.basicres.commonutil.MessageEvent;
import phone.gym.jkcq.com.commonres.common.JkConfiguration;
import phone.gym.jkcq.com.commonres.commonutil.DisplayUtils;
import phone.gym.jkcq.com.socialmodule.R;
import phone.gym.jkcq.com.socialmodule.adapter.LikeAdapter;
import phone.gym.jkcq.com.socialmodule.adapter.ProductionAdapter;
import phone.gym.jkcq.com.socialmodule.adapter.SpaceItemDecoration;
import phone.gym.jkcq.com.socialmodule.bean.ListData;
import phone.gym.jkcq.com.socialmodule.bean.MessageUpdatePesonalDynamic;
import phone.gym.jkcq.com.socialmodule.bean.response.DynamBean;
import phone.gym.jkcq.com.socialmodule.personal.FriendPersonalVideoActivity;
import phone.gym.jkcq.com.socialmodule.personal.presenter.PersonalVideoPresenter;
import phone.gym.jkcq.com.socialmodule.personal.view.PersonalVideoView;

public class LikeFragment extends AbsActionFragment implements PersonalVideoView {

    PersonalVideoPresenter presenter;

    private SmartRefreshLayout smart_refresh_like;
    private View mView;
    private RecyclerView recyclerview;
    private BaseQuickAdapter adapter;
    ArrayList<DynamBean> mList = new ArrayList<>();
    private int mCurrentPosition;

    private LinearLayout ll_nodata;
    private String userId;
    int videoType, mSize = 12, page = 1;
    boolean isLast;


    private static String FROM_USERID = "userId";
    private static String FROM_VEDIOTYPE = "vediotype";


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_production;
    }

    @Override
    protected void initView(View view) {
        this.mView = view;
        initView();
    }


    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    protected void initData() {
        presenter = new PersonalVideoPresenter(this);
        presenter.getPersonalVideoList(userId, mSize, page, videoType);
    }

    @Override
    protected void initEvent() {

    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void Event(MessageEvent messageEvent) {
        switch (messageEvent.getMsg()) {
            case MessageEvent.update_dynamic_personal_vedio:
                //传过来 imageUrl
                //传过来视频的本地地址
                MessageUpdatePesonalDynamic messageUpdatePesonalDynamic = (MessageUpdatePesonalDynamic) messageEvent.getObj();
                if (videoType == messageUpdatePesonalDynamic.getVideoType() && userId.equals(messageUpdatePesonalDynamic.getUserId())) {
                    isLast = messageUpdatePesonalDynamic.isLast();
                    page = messageUpdatePesonalDynamic.getCurrentPage();
                    mList.addAll(messageUpdatePesonalDynamic.getList());
                    adapter.notifyDataSetChanged();
                }

                break;
        }
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            userId = getArguments().getString(FROM_USERID);
            videoType = getArguments().getInt(FROM_VEDIOTYPE);
            // mCurrentPosition = getArguments().getInt(POSITION);
        }
        if (!EventBus.getDefault().isRegistered(this))
            EventBus.getDefault().register(this);
    }

    public static LikeFragment newInstance(String fromuserId, int fromvideoType) {

        Bundle args = new Bundle();
        args.putString(FROM_USERID, fromuserId);
        args.putInt(FROM_VEDIOTYPE, fromvideoType);
        LikeFragment fragment = new LikeFragment();
        fragment.setArguments(args);
        return fragment;
    }

    private void initView() {
        ll_nodata = mView.findViewById(R.id.ll_nodata);
        smart_refresh_like = mView.findViewById(R.id.smart_refresh_like);
        smart_refresh_like.setEnableFooterFollowWhenNoMoreData(true);
        smart_refresh_like.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                loadMoreData();
            }
        });
        recyclerview = mView.findViewById(R.id.recyclerview);
        GridLayoutManager layoutManager = new GridLayoutManager(this.getActivity(), 3);
        layoutManager.setOrientation(RecyclerView.VERTICAL);


        //设置布局管理器
        recyclerview.setLayoutManager(layoutManager);
        recyclerview.addItemDecoration(new SpaceItemDecoration(DisplayUtils.dip2px(getActivity(), 3)));
        if (videoType == JkConfiguration.DynamicInfoType.LIKE) {
            adapter = new LikeAdapter(mList);
        } else {
            adapter = new ProductionAdapter(mList);
//            adapter = new CustomAdapter(getActivity(),list);
        }
        recyclerview.setAdapter(adapter);
        adapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull BaseQuickAdapter<?, ?> adapter, @NonNull View view, int position) {
                Intent intent = new Intent(getActivity(), FriendPersonalVideoActivity.class);
                intent.putExtra("positon", position);
                intent.putExtra("fromUserId", userId);
                intent.putExtra("list", mList);
                intent.putExtra("size", mList.size());
                intent.putExtra("isLast", isLast);
                intent.putExtra("currentPage", page);
                intent.putExtra("videoType", videoType);
                startActivity(intent);
            }
        });


    }


    @Override
    public void onRespondError(String message) {
        smart_refresh_like.finishLoadMore();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }


    @Override
    public void successPersonalVideo(ListData<DynamBean> listData) {
        List itemList = listData.getList();
        if (listData.getTotal() > 0) {
            ll_nodata.setVisibility(View.GONE);
            smart_refresh_like.setVisibility(View.VISIBLE);
            isLast = listData.isIsLastPage();
            if (isLast) {
                smart_refresh_like.finishLoadMoreWithNoMoreData();
            } else {
                smart_refresh_like.finishLoadMore();
            }
            if (listData.getPageNum() <= 1) {
                mList.clear();
            }
            mList.addAll(itemList);
            page = listData.getPageNum();
            adapter.notifyDataSetChanged();
        } else {
            mList.clear();
//            adapter.notifyDataSetChanged();
            ll_nodata.setVisibility(View.VISIBLE);
            smart_refresh_like.setVisibility(View.GONE);
//            smart_refresh_like.finishLoadMoreWithNoMoreData();
        }
//        Log.e("LikeFragment", " size=" + itemList.size() + " isLast=" + isLast +
//                "  pageNum" + listData.getPageNum() + " pages=" + listData.getPages() + " pagesize=" + listData.getPageSize()
//                + " total=" + listData.getTotal());
    }

    @Override
    public void successDeleteAction(Boolean status) {

    }

    @Override
    public void loadMoreData() {
        presenter.getPersonalVideoList(userId, mSize, page + 1, videoType);
    }

    @Override
    public void resetData() {
        Log.e("LikeFragment", "resetData");
        smart_refresh_like.resetNoMoreData();
        mList.clear();
        page = 1;
        presenter.getPersonalVideoList(userId, mSize, page, videoType);
    }

    @Override
    public void initImmersionBar() {
        ImmersionBar.with(this)
                .init();
    }
}
