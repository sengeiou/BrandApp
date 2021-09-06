package phone.gym.jkcq.com.socialmodule.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.blankj.utilcode.util.ToastUtils;
import com.gyf.immersionbar.ImmersionBar;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import brandapp.isport.com.basicres.commonalertdialog.AlertDialogStateCallBack;
import brandapp.isport.com.basicres.commonalertdialog.PublicAlertDialog;
import brandapp.isport.com.basicres.commonutil.MessageEvent;
import phone.gym.jkcq.com.commonres.commonutil.DisplayUtils;
import phone.gym.jkcq.com.socialmodule.R;
import phone.gym.jkcq.com.socialmodule.activity.PersonalHomepageActivity;
import phone.gym.jkcq.com.socialmodule.adapter.CustomAdapter;
import phone.gym.jkcq.com.socialmodule.adapter.SpaceItemDecoration;
import phone.gym.jkcq.com.socialmodule.bean.ListData;
import phone.gym.jkcq.com.socialmodule.bean.response.DynamBean;
import phone.gym.jkcq.com.socialmodule.personal.FriendPersonalVideoActivity;
import phone.gym.jkcq.com.socialmodule.personal.presenter.PersonalVideoPresenter;
import phone.gym.jkcq.com.socialmodule.personal.view.PersonalVideoView;

public class ProductionFragment extends AbsActionFragment implements PersonalVideoView {

    PersonalVideoPresenter presenter;

    private LinearLayout ll_nodata;
    private SmartRefreshLayout smart_refresh_like;
    private View mView;
    private RecyclerView recyclerview;
    private CustomAdapter mAdapter;
    ArrayList<DynamBean> mList = new ArrayList<>();
    private int mCurrentPosition;

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
    protected void initData() {
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
               /* MessageUpdatePesonalDynamic messageUpdatePesonalDynamic = (MessageUpdatePesonalDynamic) messageEvent.getObj();
                if (videoType == messageUpdatePesonalDynamic.getVideoType() && userId.equals(messageUpdatePesonalDynamic.getUserId())) {
                    isLast = messageUpdatePesonalDynamic.isLast();
                    page = messageUpdatePesonalDynamic.getCurrentPage();
                    mList.addAll(messageUpdatePesonalDynamic.getList());
                    mAdapter.notifyDataSetChanged();
                }
*/
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

    public static ProductionFragment newInstance(String fromuserId, int fromvideoType) {

        Bundle args = new Bundle();
        args.putString(FROM_USERID, fromuserId);
        args.putInt(FROM_VEDIOTYPE, fromvideoType);
        ProductionFragment fragment = new ProductionFragment();
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

        presenter = new PersonalVideoPresenter(this);

//        presenter.getPersonalVideoList(userId, size, page, videoType);
        presenter.getPersonalVideoList(userId, mSize, page, videoType);

        //设置布局管理器
        recyclerview.setLayoutManager(layoutManager);
        recyclerview.addItemDecoration(new SpaceItemDecoration(DisplayUtils.dip2px(getActivity(), 8)));
        mAdapter = new CustomAdapter(getActivity(), mList);
        recyclerview.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(new CustomAdapter.OnItemClickListener() {

            @Override
            public void onChildClick(View view, int position) {
                if (view.getId() == R.id.iv_delete) {
                    PublicAlertDialog.getInstance().showDialog("", getString(R.string.ensure_delete), context, getResources().getString(R.string.common_dialog_cancel), getResources().getString(R.string.common_dialog_ok), new AlertDialogStateCallBack() {
                        @Override
                        public void determine() {
                            mCurrentPosition = position;
                            if (mList.size() > position && position >= 0) {
                                presenter.deleteDynamic(mList.get(position).getDynamicId());
                            } else {
                                mCurrentPosition = 0;
                            }
                        }

                        @Override
                        public void cancel() {

                        }
                    }, false);
                }
            }

            @Override
            public void onItemClick(View view, int position) {
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
        smart_refresh_like.finishLoadMoreWithNoMoreData();

    }


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
            mAdapter.notifyDataSetChanged();
        } else {
            mList.clear();
//            adapter.notifyDataSetChanged();
            ll_nodata.setVisibility(View.VISIBLE);
            smart_refresh_like.setVisibility(View.GONE);
//            smart_refresh_like.finishLoadMoreWithNoMoreData();
        }
    }

    @Override
    public void successDeleteAction(Boolean status) {
        Log.e("action", "successDeleteAction   size=" + mList.size());
        if (status) {
            if (mList.size() <= 1) {
                mList.clear();
                ll_nodata.setVisibility(View.VISIBLE);
                smart_refresh_like.setVisibility(View.GONE);
            } else {
                mList.remove(mCurrentPosition);
//            mAdapter.notifyItemRemoved(mCurrentPosition);
                mAdapter.notifyDataSetChanged();
            }
            PersonalHomepageActivity activity = ((PersonalHomepageActivity) (getActivity()));
            if (activity != null) {
                activity.refreshData(userId);
            }
        } else {
            ToastUtils.showShort("删除失败");
        }
    }

    @Override
    public void initImmersionBar() {
        ImmersionBar.with(this)
                .init();
    }


    @Override
    public void loadMoreData() {
        presenter.getPersonalVideoList(userId, mSize, page + 1, videoType);
    }

    @Override
    public void resetData() {
        smart_refresh_like.resetNoMoreData();
        mList.clear();
        page = 1;
        presenter.getPersonalVideoList(userId, mSize, page, videoType);
    }
}
