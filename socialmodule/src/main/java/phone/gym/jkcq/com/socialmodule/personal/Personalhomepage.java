package phone.gym.jkcq.com.socialmodule.personal;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.material.appbar.AppBarLayout;

import androidx.coordinatorlayout.widget.CoordinatorLayout;
import bike.gymproject.viewlibray.FriendItemView;
import brandapp.isport.com.basicres.BaseActivity;
import brandapp.isport.com.basicres.BaseApp;
import brandapp.isport.com.basicres.commonalertdialog.AlertDialogStateCallBack;
import brandapp.isport.com.basicres.commonalertdialog.PublicAlertDialog;
import brandapp.isport.com.basicres.commonbean.CommonFriendRelation;
import brandapp.isport.com.basicres.commonbean.UserInfoBean;
import brandapp.isport.com.basicres.commonutil.LoadImageUtil;
import brandapp.isport.com.basicres.commonutil.Logger;
import brandapp.isport.com.basicres.commonutil.TokenUtil;
import brandapp.isport.com.basicres.commonutil.UIUtils;
import brandapp.isport.com.basicres.commonutil.ViewMultiClickUtil;
import brandapp.isport.com.basicres.commonview.RoundImageView;
import brandapp.isport.com.basicres.net.userNet.CommonUserPresenter;
import brandapp.isport.com.basicres.net.userNet.CommonUserView;
import phone.gym.jkcq.com.commonres.commonutil.DisplayUtils;
import phone.gym.jkcq.com.commonres.commonutil.UserUtils;
import phone.gym.jkcq.com.socialmodule.FriendConstant;
import phone.gym.jkcq.com.socialmodule.R;
import phone.gym.jkcq.com.socialmodule.activity.FansActivity;
import phone.gym.jkcq.com.socialmodule.activity.FollowActivity;
import phone.gym.jkcq.com.socialmodule.activity.FriendActivity;
import phone.gym.jkcq.com.socialmodule.activity.FriendQRCodeActivity;
import phone.gym.jkcq.com.socialmodule.bean.FriendInfo;
import phone.gym.jkcq.com.socialmodule.bean.ListData;
import phone.gym.jkcq.com.socialmodule.mvp.presenter.FriendPresenter;
import phone.gym.jkcq.com.socialmodule.mvp.view.FriendView;

public class Personalhomepage extends BaseActivity implements CommonUserView, FriendView, AppBarLayout.OnOffsetChangedListener {


    private static final int PERCENTAGE_TO_ANIMATE_AVATAR = 30;
    private static final int PERCENTAGE_TO_ANIMATE_Head = 80;
    private boolean mIsAvatarShown = true;
    private boolean mIsAvatarTop = true;

    private int mMaxScrollSize;


    private final String TAG_MINE = "mine";
    private final String TAG_FOLLOW = "follow";
    private final String TAG_NOT_FOLLOW = "notfollow";


    TextView tv_myprofile;
    TextView tv_age;

    TextView tv_name, tv_top_name;

    RoundImageView iv_head, iv_top_head;

    private ImageView iv_back, tv_top_back;
    FriendItemView tvFriend, tvFans, tvFollow;
    private TextView tvEdit, tv_top_edit;
    private ImageView ivBg, iv_sq, iv_top_sq;
    private TextView tv_top_feed_number, tv_feed_number;

    private String fromUserId;

    private RelativeLayout layout_top_head;
    private LinearLayout layout_top_dongtai, layout_top;
    private CoordinatorLayout coordinatorLayout;
    private AppBarLayout appBarLayout;
    RelativeLayout layout_content;
    int layout_height;

    boolean isFirstLoad;

    private CommonUserPresenter presenter;
    private FriendPresenter mFriendPresenter;

    private UserInfoBean mCurrentUserInfo;

    @Override
    protected int getLayoutId() {
        return R.layout.friend_activity_personalpagehome;
    }


    @Override
    protected void initView(View view) {
        Logger.e("view:" + view);
        if (view != null) {
            ivBg = view.findViewById(R.id.iv_bg);
            iv_sq = view.findViewById(R.id.iv_sq);

            iv_back = view.findViewById(R.id.iv_back);
            tv_age = view.findViewById(R.id.tv_age);
            tv_myprofile = view.findViewById(R.id.tv_myprofile);
            iv_head = view.findViewById(R.id.iv_head);
            tv_name = view.findViewById(R.id.tv_name);

            tvFriend = view.findViewById(R.id.tv_friend);
            tvFans = view.findViewById(R.id.tv_fans);
            tvFollow = view.findViewById(R.id.tv_follow);
            tvEdit = view.findViewById(R.id.tv_edit);
            layout_top_head = view.findViewById(R.id.layout_top_head);
            layout_top = view.findViewById(R.id.layout_top);
            layout_top_dongtai = view.findViewById(R.id.layout_top_dongtai);
            coordinatorLayout = view.findViewById(R.id.annonce_main_coordinator);
            appBarLayout = view.findViewById(R.id.app_bar);
            layout_content = view.findViewById(R.id.layout_content);

            tv_top_back = view.findViewById(R.id.tv_top_back);
            iv_top_head = view.findViewById(R.id.iv_top_head);
            iv_top_sq = view.findViewById(R.id.iv_top_sq);
            tv_top_name = view.findViewById(R.id.tv_top_name);
            tv_top_edit = view.findViewById(R.id.tv_top_edit);
            tv_feed_number = view.findViewById(R.id.tv_feed_number);
            tv_top_feed_number = view.findViewById(R.id.tv_top_feed_number);
        }
        // layout_bg.getBackground().mutate().setAlpha(153);
        //layout_bg.getBackground().mutate().setAlpha(10);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        fromUserId = intent.getStringExtra(FriendConstant.USER_ID);
    }

    @Override
    protected void onResume() {
        super.onResume();
//        Log.e("onNewIntent1", "fromUserId=" + fromUserId);
        if (TextUtils.isEmpty(fromUserId)) {
            iv_sq.setVisibility(View.VISIBLE);
        } else {
            iv_sq.setVisibility(View.GONE);
        }
        if (TextUtils.isEmpty(fromUserId)) {
            presenter.getUserinfo(TokenUtil.getInstance().getPeopleIdStr(BaseApp.getApp()));
            presenter.getUserFriendRelation(TokenUtil.getInstance().getPeopleIdStr(BaseApp.getApp()));
        } else {
            presenter.getUserinfo(fromUserId);
            presenter.getUserFriendRelation(fromUserId);
        }
    }


    @Override
    protected void initData() {
        layout_content.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                Logger.e("onGlobalLayout:" + layout_content.getHeight());
                layout_height = layout_content.getHeight();
                isFirstLoad = true;
                layout_content.getViewTreeObserver().removeOnGlobalLayoutListener(this);
            }
        });
        createPresent();
        fromUserId = getIntent().getStringExtra(FriendConstant.USER_ID);
        mFriendPresenter = new FriendPresenter(this);
        topHeadShow();


    }

    Handler handler = new Handler();

    private void createPresent() {
        presenter = new CommonUserPresenter(this);
    }

    @Override
    protected void initEvent() {
        iv_head.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ViewMultiClickUtil.onMultiClick(v)) {
                    return;
                }
                Intent intent = new Intent(Personalhomepage.this, ShowImageActivity.class);
                if (mCurrentUserInfo != null) {
                    intent.putExtra("pic_list", mCurrentUserInfo.getHeadUrl());
                }
                startActivity(intent);
            }
        });
        appBarLayout.addOnOffsetChangedListener(this);
        iv_sq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ViewMultiClickUtil.onMultiClick(v)) {
                    return;
                }
                startActivity(new Intent(Personalhomepage.this, FriendQRCodeActivity.class));
            }
        });
        iv_top_sq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ViewMultiClickUtil.onMultiClick(v)) {
                    return;
                }
                startActivity(new Intent(Personalhomepage.this, FriendQRCodeActivity.class));
            }
        });
        tv_top_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        tv_top_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        tvEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ViewMultiClickUtil.onMultiClick(v)) {
                    return;
                }
                String tag = (String) tvEdit.getTag();
                if (TextUtils.isEmpty(tag)) {
                    tag = TAG_MINE;
                }
                if (tag.equals(TAG_MINE)) {
                    Intent intent = new Intent(Personalhomepage.this, EditUserInfo.class);
                    startActivity(intent);
                } else if (tag.equals(TAG_FOLLOW)) {

                    PublicAlertDialog.getInstance().showDialog("", context.getResources().getString(R.string.ensure_unfollow), context, getResources().getString(R.string.common_dialog_cancel), getResources().getString(R.string.common_dialog_ok), new AlertDialogStateCallBack() {
                        @Override
                        public void determine() {
                            mFriendPresenter.unFollow(fromUserId);
                        }

                        @Override
                        public void cancel() {
                        }
                    }, false);


                } else if (tag.equals(TAG_NOT_FOLLOW)) {
                    mFriendPresenter.addFollow(fromUserId);
                }
            }
        });
        tvFriend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ViewMultiClickUtil.onMultiClick(v)) {
                    return;
                }
                startFollowActivity(FriendActivity.class);
            }
        });
        tvFans.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ViewMultiClickUtil.onMultiClick(v)) {
                    return;
                }
                startFollowActivity(FansActivity.class);
            }
        });
        tvFollow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ViewMultiClickUtil.onMultiClick(v)) {
                    return;
                }
                startFollowActivity(FollowActivity.class);
            }
        });
    }


    private void startFollowActivity(Class<?> cls) {
        Intent intent = new Intent(Personalhomepage.this, cls);
        if (!TextUtils.isEmpty(fromUserId)) {
            intent.putExtra(FriendConstant.USER_ID, fromUserId);
        } else if (mCurrentUserInfo != null && !TextUtils.isEmpty(mCurrentUserInfo.getUserId())) {
            intent.putExtra(FriendConstant.USER_ID, mCurrentUserInfo.getUserId());
        } else {
            return;
        }
        startActivity(intent);
    }

    @Override
    protected void initHeader() {
        //  StatusBarCompat.setStatusBarColor(this, getResources().getColor(R.color.common_text_color));
        //StatusBarCompat.setStatusBarColor(getWindow(), getResources().getColor(R.color.transparent), true);

    }


    @Override
    public void onRespondError(String message) {

    }


    /**
     * 根据资源ID获取Drawable/设置边框
     *
     * @param resId
     * @return
     */
    private Drawable getDrawables(int resId) {
        if ((resId >>> 24) < 2) {
            return null;
        }
        Drawable drawable;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            drawable = this.getDrawable(resId);
        } else {
            drawable = this.getResources().getDrawable(resId);
        }

        if (drawable != null) {
            drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
        }
        return drawable;
    }

    String currentProfile;
    String strtvcurrentProfile;

    @Override
    public void onSuccessUserInfo(UserInfoBean userInfoBean) {

        if (userInfoBean != null) {
            mCurrentUserInfo = userInfoBean;
            currentProfile = userInfoBean.getMyProfile();
            if (TextUtils.isEmpty(currentProfile)) {
                currentProfile = "";
            }
            strtvcurrentProfile = tv_myprofile.getText().toString().trim();
            if (TextUtils.isEmpty(strtvcurrentProfile)) {
                strtvcurrentProfile = "";
            }
            if (TextUtils.isEmpty(userInfoBean.getMyProfile())) {
                if (!TextUtils.isEmpty(strtvcurrentProfile)) {
                    int height = tv_myprofile.getHeight();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {

                            tv_myprofile.setText(currentProfile);
                            ViewGroup.LayoutParams params = layout_content.getLayoutParams();
                            /*if (layout_height == 0) {
                                layout_height = layout_content.getHeight();
                            } else {

                            }*/
                            params.height = layout_height;
                            Logger.e("layout_content.getHeight()", "layout_content.getHeight():" + layout_content.getHeight() + "height:" + height + "layout_content:" + layout_content.getHeight());


                            layout_content.setLayoutParams(params);

                        }
                    }, 500);
                } else {
                    tv_myprofile.setVisibility(View.GONE);
                }
            } else {
                //有一种情况不需要重新去刷布局就是没有修改的時候


                if (!currentProfile.equals(strtvcurrentProfile)) {
                    tv_myprofile.setText(userInfoBean.getMyProfile());
                    tv_myprofile.setVisibility(View.INVISIBLE);
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {

                            int height = tv_myprofile.getHeight();
                            tv_myprofile.setVisibility(View.VISIBLE);
                            ViewGroup.LayoutParams params = layout_content.getLayoutParams();

                            Logger.e("layout_content.getHeight()", "layout_content.getHeight():" + layout_content.getHeight() + "height:" + height + "layout_content:" + layout_content.getHeight());
                            //只有第一次才需要加30
                            params.height = layout_height + height + DisplayUtils.dip2px(BaseApp.getApp(), 30);

                            layout_content.setLayoutParams(params);
                        }
                    }, 500);
                }
            }

            tv_name.setText(userInfoBean.getNickName());
            tv_top_name.setText(userInfoBean.getNickName());
            Drawable drawable = null;
            if (userInfoBean.getGender().equals("Male")) {
                drawable = getDrawables(R.drawable.common_icon_male);
            } else {
                drawable = getDrawables(R.drawable.common_icon_female);
            }
            int age = UserUtils.getAge(userInfoBean.getBirthday());
            drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
            tv_age.setCompoundDrawables(drawable, null, null, null);
            tv_age.setText(age + "");
            LoadImageUtil.getInstance().loadCirc(Personalhomepage.this, userInfoBean.getHeadUrl(), iv_head);
            LoadImageUtil.getInstance().loadCirc(Personalhomepage.this, userInfoBean.getHeadUrl(), iv_top_head);
            LoadImageUtil.getInstance().loadCirc(Personalhomepage.this, userInfoBean.getBackgroundUrl(), ivBg, R.drawable.friend_bg_homepage);

        }

    }

    int followState = 0, fans = 0, friend = 0, follow = 0;

    @Override
    public void onSuccessUserFriendRelation(CommonFriendRelation commonFriendRelation) {
        //自己过来的就并没有传这个ID

        if (commonFriendRelation != null) {
            followState = commonFriendRelation.getFollowStatus();
            fans = commonFriendRelation.getFansNums();
            friend = commonFriendRelation.getFriendNums();
            follow = commonFriendRelation.getFollowNums();
            tv_top_feed_number.setText(commonFriendRelation.getTrendsNums() + "");
            tv_feed_number.setText(commonFriendRelation.getTrendsNums() + "");
        }
        if (TextUtils.isEmpty(fromUserId)) {
            tvEdit.setTag(TAG_MINE);
            tvEdit.setText(UIUtils.getString(R.string.edit_person_page));
        } else {
            //1、好友（互相关注），2、粉丝（别人是自己的粉丝,显示+关注），3、关注（自己是别人的粉丝，显示取消关注）
            switch (followState) {
                case 1:
                    tvEdit.setTag(TAG_FOLLOW);
                    tvEdit.setText(UIUtils.getString(R.string.friend_state_fans));
                    tv_top_edit.setTag(TAG_FOLLOW);
                    tv_top_edit.setText(UIUtils.getString(R.string.friend_state_fans));
                    break;
                case 0:
                case 2:
                    tvEdit.setTag(TAG_NOT_FOLLOW);
                    tvEdit.setText(UIUtils.getString(R.string.friend_state_follow));
                    tv_top_edit.setTag(TAG_NOT_FOLLOW);
                    tv_top_edit.setText(UIUtils.getString(R.string.friend_state_follow));
                    break;
                case 3:
                    tvEdit.setTag(TAG_FOLLOW);
                    tvEdit.setText(UIUtils.getString(R.string.friend_state_friend));
                    tv_top_edit.setTag(TAG_FOLLOW);
                    tv_top_edit.setText(UIUtils.getString(R.string.friend_state_friend));
                    break;
            }
        }
        tvFollow.setValue(follow);
        tvFans.setValue(fans);
        tvFriend.setValue(friend);

    }


    @Override
    public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
        Log.e("onOffsetChanged", "onOffsetChanged:" + appBarLayout.getTotalScrollRange() + "verticalOffset:" + verticalOffset);

        if (mMaxScrollSize == 0)
            mMaxScrollSize = appBarLayout.getTotalScrollRange();

        int percentage = (Math.abs(verticalOffset)) * 100 / mMaxScrollSize;
        if (percentage >= PERCENTAGE_TO_ANIMATE_Head && mIsAvatarTop) {
            layout_top_dongtai.setVisibility(View.VISIBLE);
            mIsAvatarTop = false;
        }
        if (percentage <= PERCENTAGE_TO_ANIMATE_Head && !mIsAvatarTop) {
            layout_top_dongtai.setVisibility(View.GONE);
            mIsAvatarTop = true;
        }


        if (percentage >= PERCENTAGE_TO_ANIMATE_AVATAR && mIsAvatarShown) {
            layout_top.setVisibility(View.VISIBLE);
            mIsAvatarShown = false;
            // layout_top.setVisibility(View.VISIBLE);
            // tv_title.setVisibility(View.VISIBLE);
            //collapsingToolbarLayout.setContentScrimColor(getResources().getColor(R.color.design_default_color_primary));
          /*  mProfileImage.animate()
                    .scaleY(0).scaleX(0)
                    .setDuration(200)
                    .start();*/
        }

        if (percentage <= PERCENTAGE_TO_ANIMATE_AVATAR && !mIsAvatarShown) {
            mIsAvatarShown = true;
            layout_top.setVisibility(View.GONE);
            //   layout_top.setVisibility(View.GONE);
            // tv_title.setVisibility(View.GONE);
            // collapsingToolbarLayout.setContentScrim(null);
          /*  mProfileImage.animate()
                    .scaleY(1).scaleX(1)
                    .start();*/
        }
    }


    public void setEditState(int type) {
        switch (type) {
            case 1:
                tvEdit.setTag(TAG_FOLLOW);
                tvEdit.setText(UIUtils.getString(R.string.friend_state_fans));
                tv_top_edit.setTag(TAG_FOLLOW);
                tv_top_edit.setText(UIUtils.getString(R.string.friend_state_fans));
                break;
            case 0:
            case 2:
                tvEdit.setTag(TAG_NOT_FOLLOW);
                tvEdit.setText(UIUtils.getString(R.string.friend_state_follow));
                tv_top_edit.setTag(TAG_NOT_FOLLOW);
                tv_top_edit.setText(UIUtils.getString(R.string.friend_state_follow));
                break;
            case 3:
                tvEdit.setTag(TAG_FOLLOW);
                tvEdit.setText(UIUtils.getString(R.string.friend_state_friend));
                tv_top_edit.setTag(TAG_FOLLOW);
                tv_top_edit.setText(UIUtils.getString(R.string.friend_state_friend));
                break;
        }
    }

    public void topHeadShow() {
        if (TextUtils.isEmpty(fromUserId)) {
            iv_top_sq.setVisibility(View.VISIBLE);
            tv_top_edit.setVisibility(View.GONE);

        } else {
            iv_top_sq.setVisibility(View.GONE);
            tv_top_edit.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void addFollowSuccess(int type) {
        setEditState(type);
        if (fromUserId != null) {
            presenter.getUserFriendRelation(fromUserId);
        }
    }

    @Override
    public void unFollowSuccess(int type) {
        setEditState(type);
        if (fromUserId != null) {
            presenter.getUserFriendRelation(fromUserId);
        }
    }

    @Override
    public void findFriendSuccess(ListData<FriendInfo> friendInfos) {

    }

    @Override
    public void searchFriendSuccess(ListData<FriendInfo> friendInfos) {

    }
}
