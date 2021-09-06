package phone.gym.jkcq.com.socialmodule.activity;

import android.content.Intent;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
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

public class FriendSearchActivity extends BaseMVPActivity<FriendView, FriendPresenter> implements View.OnClickListener, FriendView {

    private LinearLayout ll_no_user;
    private EditText et_search;
    private TextView tv_cancel;
    private ImageView iv_clear;
    private RecyclerView recycler_friend_search;
    private SmartRefreshLayout smart_refresh;

    ArrayList<FriendInfo> mFriendInfos = new ArrayList<>();
    FollowAdapter mAdapter;
    private String mCurrentEditContent;
    private int mCurrentPage = 0;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_friend_search;
    }

    @Override
    protected void initView(View view) {
        ll_no_user = findViewById(R.id.ll_no_user);
        tv_cancel = findViewById(R.id.tv_cancel);
        iv_clear = findViewById(R.id.iv_clear);
        et_search = findViewById(R.id.et_search);
        et_search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String content = s.toString();
//                doAutoSearch(content);
                if (content.length() > 0) {
                    mCurrentEditContent = content;
                    iv_clear.setVisibility(View.VISIBLE);
                } else {
                    iv_clear.setVisibility(View.GONE);
                }

            }
        });
        et_search.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                Log.e("et_search", "actionID=" + actionId);
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    doSearch(mCurrentEditContent);
                }
                return false;
            }
        });

        smart_refresh = findViewById(R.id.smart_refresh);
        recycler_friend_search = findViewById(R.id.recycler_friend_search);
        smart_refresh.setEnableRefresh(false);
        smart_refresh.setEnableFooterFollowWhenNoMoreData(true);
//        smart_refresh.setEnableLoadMore(true);
        smart_refresh.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                mActPresenter.searchFriend(mCurrentEditContent, mCurrentPage + 1, 10);

            }
        });
        initRecyclerView();
    }


    private FriendInfo mCurrentFriendInfoInfo;
    private int mCurrentPosition = 0;

    private void initRecyclerView() {

        recycler_friend_search.setLayoutManager(new LinearLayoutManager(this));
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
                Intent intent = new Intent(FriendSearchActivity.this, PersonalHomepageActivity.class);
                intent.putExtra(FriendConstant.USER_ID, info.getUserId());
                startActivity(intent);
//                finish();
            }
        });
        recycler_friend_search.setAdapter(mAdapter);
    }

    @Override
    protected void initData() {

    }

    private void testHttp() {
//        mActPresenter.getFriend("108", 1, 0, 10);
//        mActPresenter.searchFriend("180", "hh");
        mActPresenter.addFollow("108");
        mActPresenter.unFollow("103");

    }

    @Override
    protected void initEvent() {
        tv_cancel.setOnClickListener(this);
        iv_clear.setOnClickListener(this);
    }

    @Override
    protected void initHeader() {

    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.iv_clear) {
            et_search.setText("");
        } else if (id == R.id.tv_cancel) {
//            testHttp();
            finish();
        }
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
    public void findFriendSuccess(ListData<FriendInfo> friendInfos) {
    }

    @Override
    public void searchFriendSuccess(ListData<FriendInfo> friendInfos) {
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

    /**
     * 边输入边搜索
     *
     * @param content
     */
    private void doAutoSearch(String content) {
        if (content.length() > 0) {
            if (content.length() > 20) {
                content = content.substring(0, 20);
            }
            iv_clear.setVisibility(View.VISIBLE);
            mCurrentEditContent = content;
            smart_refresh.setEnableLoadMore(true);
            mActPresenter.searchFriend(mCurrentEditContent, 0, 10);
        } else {
            iv_clear.setVisibility(View.INVISIBLE);
            smart_refresh.setVisibility(View.GONE);
            ll_no_user.setVisibility(View.VISIBLE);
        }
    }

    /**
     * 按搜索键搜索
     *
     * @param content
     */
    private void doSearch(String content) {
        if(content.length()>0){
            smart_refresh.setEnableLoadMore(true);
            mActPresenter.searchFriend(content, 0, 10);
        }
    }
}
