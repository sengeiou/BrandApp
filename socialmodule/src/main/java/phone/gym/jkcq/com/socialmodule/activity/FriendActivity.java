package phone.gym.jkcq.com.socialmodule.activity;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;

import java.util.ArrayList;

import brandapp.isport.com.basicres.commonalertdialog.AlertDialogStateCallBack;
import brandapp.isport.com.basicres.commonalertdialog.PublicAlertDialog;
import brandapp.isport.com.basicres.mvp.BaseMVPActivity;
import phone.gym.jkcq.com.socialmodule.FriendConstant;
import phone.gym.jkcq.com.socialmodule.R;
import phone.gym.jkcq.com.socialmodule.adapter.FollowAdapter;
import phone.gym.jkcq.com.socialmodule.bean.FriendInfo;
import phone.gym.jkcq.com.socialmodule.bean.ListData;
import phone.gym.jkcq.com.socialmodule.mvp.presenter.FriendPresenter;
import phone.gym.jkcq.com.socialmodule.mvp.view.FriendView;

public class FriendActivity extends BaseMVPActivity<FriendView, FriendPresenter> implements FriendView {
    private LinearLayout ll_no_user;
    private TextView tv_title;
    private ImageView iv_back;
    private RecyclerView recycler_follow;
    private SmartRefreshLayout smart_refresh;

    ArrayList<FriendInfo> mFriendInfos = new ArrayList<>();
    FollowAdapter mAdapter;
    private int mCurrentPage = 0;
    private LinearLayout ll_search;

    private String mUserId = "";

    @Override
    protected int getLayoutId() {
        return R.layout.activity_follow;
    }

    @Override
    protected void initView(View view) {
        tv_title = findViewById(R.id.tv_title);
        tv_title.setText(getString(R.string.friend));
        iv_back = findViewById(R.id.iv_back);
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        ll_search=findViewById(R.id.ll_search);
        ll_search.setVisibility(View.VISIBLE);
        ll_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(FriendActivity.this,FriendSearchActivity.class));
            }
        });
        ll_no_user = findViewById(R.id.ll_no_user);
        smart_refresh = findViewById(R.id.smart_refresh);
        recycler_follow = findViewById(R.id.recycler_follow);
        smart_refresh.setEnableRefresh(false);
        smart_refresh.setEnableFooterFollowWhenNoMoreData(true);
//        smart_refresh.setEnableLoadMore(true);
        smart_refresh.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                mActPresenter.getFriend(mUserId, FriendConstant.FRIEND, mCurrentPage + 1, 10);
            }
        });
        initRecyclerView();
    }


    private FriendInfo mCurrentFriendInfoInfo;
    private int mCurrentPosition = 0;

    private void initRecyclerView() {

        recycler_follow.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new FollowAdapter(mFriendInfos);
        mAdapter.addOnChildClickListener(new FollowAdapter.OnChildClickLisenter() {
            @Override
            public void onClick(View view, FriendInfo info, int position) {
                mCurrentFriendInfoInfo = info;
                mCurrentPosition = position;
                switch (info.getType()) {
                    case 1:
                    case 3:
                        PublicAlertDialog.getInstance().showDialog("", context.getResources().getString(R.string.ensure_unfollow), context, getResources().getString(R.string.common_dialog_cancel), getResources().getString(R.string.common_dialog_ok), new AlertDialogStateCallBack() {
                            @Override
                            public void determine() {
                                mActPresenter.unFollow(info.getUserId());
                            }

                            @Override
                            public void cancel() {
                            }
                        }, false);
                        break;
                    case 0:
                    case 2:
                        mActPresenter.addFollow(info.getUserId());
                        break;

                }
            }
        });
        mAdapter.addOnItemClickListener(new FollowAdapter.OnItemClickLisenter() {
            @Override
            public void onClick(View view, FriendInfo info, int position) {
                Intent intent = new Intent(FriendActivity.this, PersonalHomepageActivity.class);
                intent.putExtra(FriendConstant.USER_ID, info.getUserId());
                startActivity(intent);
//                finish();
            }
        });
        recycler_follow.setAdapter(mAdapter);
    }

    @Override
    protected void initData() {
        mUserId = getIntent().getStringExtra(FriendConstant.USER_ID);
        if (TextUtils.isEmpty(mUserId)) {
            finish();
            return;
        }
        mActPresenter.getFriend(mUserId, FriendConstant.FRIEND, 0, 10);
    }

    private void testHttp() {
//        mActPresenter.getFriend("108", 1, 0, 10);
//        mActPresenter.searchFriend("180", "hh");
        mActPresenter.addFollow("108");
        mActPresenter.unFollow("103");

    }

    @Override
    protected void initEvent() {
    }

    @Override
    protected void initHeader() {

    }


    @Override
    protected FriendPresenter createPresenter() {
        return new FriendPresenter(this);
    }

    @Override
    public void addFollowSuccess(int type) {
        mCurrentFriendInfoInfo.setType(type);
        mAdapter.notifyItemChanged(mCurrentPosition);
    }

    @Override
    public void unFollowSuccess(int type) {
        mCurrentFriendInfoInfo.setType(type);
        mAdapter.notifyItemChanged(mCurrentPosition);
    }

    @Override
    public void searchFriendSuccess(ListData<FriendInfo> friendInfos) {
    }

    @Override
    public void findFriendSuccess(ListData<FriendInfo> friendInfos) {
        if (smart_refresh != null) {
            mCurrentPage = friendInfos.getPageNum();
            if (friendInfos != null && friendInfos.getList().size() > 0) {
                ll_no_user.setVisibility(View.GONE);
                smart_refresh.setVisibility(View.VISIBLE);
                if (mCurrentPage > 1) {
//                    mFriendInfos.addAll(friendInfos.getList());
                    mAdapter.addData(friendInfos.getList());
                } else {
//                    mFriendInfos=(ArrayList<FriendInfo>) friendInfos.getList();
                    mAdapter.setNewData(friendInfos.getList());
                }
                if (friendInfos.isIsLastPage()) {
                    smart_refresh.finishLoadMoreWithNoMoreData();
                } else {
                    smart_refresh.finishLoadMore();
                }
            } else {
                smart_refresh.setVisibility(View.GONE);
                ll_no_user.setVisibility(View.VISIBLE);
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mFriendInfos.clear();
    }
}

