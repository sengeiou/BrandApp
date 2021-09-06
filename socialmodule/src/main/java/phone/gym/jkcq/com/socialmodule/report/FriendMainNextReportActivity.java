package phone.gym.jkcq.com.socialmodule.report;

import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.blankj.utilcode.util.KeyboardUtils;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import brandapp.isport.com.basicres.BaseApp;
import brandapp.isport.com.basicres.commonalertdialog.AlertDialogStateCallBack;
import brandapp.isport.com.basicres.commonalertdialog.PublicAlertDialog;
import brandapp.isport.com.basicres.commonutil.LoadImageUtil;
import brandapp.isport.com.basicres.commonutil.MessageEvent;
import brandapp.isport.com.basicres.commonutil.TokenUtil;
import brandapp.isport.com.basicres.commonutil.UIUtils;
import brandapp.isport.com.basicres.commonview.TitleBarView;
import brandapp.isport.com.basicres.mvp.BaseMVPTitleActivity;
import phone.gym.jkcq.com.commonres.commonutil.PhotoChoosePopUtil;
import phone.gym.jkcq.com.socialmodule.FriendConstant;
import phone.gym.jkcq.com.socialmodule.R;
import phone.gym.jkcq.com.socialmodule.activity.PersonalHomepageActivity;
import phone.gym.jkcq.com.socialmodule.bean.ListDataCommend;
import phone.gym.jkcq.com.socialmodule.bean.result.ResultCommentBean;
import phone.gym.jkcq.com.socialmodule.bean.result.ResultLikeBean;
import phone.gym.jkcq.com.socialmodule.report.bean.ReportBean;
import phone.gym.jkcq.com.socialmodule.report.bean.UpdatePeportBean;
import phone.gym.jkcq.com.socialmodule.report.present.CommentPresent;
import phone.gym.jkcq.com.socialmodule.report.view.CommentView;
import phone.gym.jkcq.com.socialmodule.report.view.SoftKeyBoardListener;
import phone.gym.jkcq.com.socialmodule.util.TimeUtil;

public class FriendMainNextReportActivity extends BaseMVPTitleActivity<CommentView, CommentPresent> implements CommentView {
    TextView tv_edit;
    LinearLayout layout_edit;
    EditText et_content;
    TextView tv_send;
    View view_hide;
    MainNextReportAdapter mainReportAdapter;
    SmartRefreshLayout smartRefreshLayout;
    RecyclerView recyclerview;
    TextView tv_empty;
    int mCurrentPosition;
    ReportBean mCurrentReportBean, fromReportBean;

    boolean isMainDel;


    //谁回复谁
    String replayUserId;
    String replayCommend;
    String replayUserName;


    String dynamicId;
    String fromId;
    String meUserId;
    String commondId;

    TextView tv_nickname, tv_like_number, tv_content, tv_send_time, tv_del, tv_acti;

    ImageView iv_like, iv_head_photo;


    ArrayList<ReportBean> list;


    @Override
    protected int getLayoutId() {
        return R.layout.friend_activity_next_report;
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        dynamicId = getIntent().getStringExtra(FriendConstant.DYNAMIC_ID);
        commondId = getIntent().getStringExtra(FriendConstant.COMMEND_ID);
        fromId = getIntent().getStringExtra(FriendConstant.USER_ID);
        fromReportBean = getIntent().getParcelableExtra(FriendConstant.REPORT);
    }

    private void getIntentValue() {
        dynamicId = getIntent().getStringExtra(FriendConstant.DYNAMIC_ID);
        fromId = getIntent().getStringExtra(FriendConstant.USER_ID);
        commondId = getIntent().getStringExtra(FriendConstant.COMMEND_ID);
        meUserId = TokenUtil.getInstance().getPeopleIdInt(BaseApp.getApp());
        fromReportBean = getIntent().getParcelableExtra(FriendConstant.REPORT);
        Log.e("getIntentValue", TextUtils.isEmpty(dynamicId) ? "is null" : dynamicId);
        Log.e("getIntentValue", TextUtils.isEmpty(fromId) ? "is null" : fromId);
        Log.e("getIntentValue", fromReportBean + "");
    }


    @Override
    protected void initView(View view) {

        tv_nickname = view.findViewById(R.id.tv_nickname);
        view_hide = view.findViewById(R.id.view_hide);
        tv_like_number = view.findViewById(R.id.tv_like_number);
        tv_content = view.findViewById(R.id.tv_content);
        tv_send_time = view.findViewById(R.id.tv_send_time);
        iv_head_photo = view.findViewById(R.id.iv_head_photo);
        iv_like = view.findViewById(R.id.iv_like);
        tv_del = view.findViewById(R.id.tv_del);
        tv_acti = view.findViewById(R.id.tv_acti);


        tv_edit = view.findViewById(R.id.tv_edit);
        tv_send = view.findViewById(R.id.tv_send);
        layout_edit = view.findViewById(R.id.layout_edit);
        et_content = view.findViewById(R.id.et_content);
        smartRefreshLayout = view.findViewById(R.id.smart_refresh_like);
        recyclerview = view.findViewById(R.id.recyclerview);
        tv_empty = view.findViewById(R.id.tv_empty);
        //改变默认的单行模式
        et_content.setSingleLine(false);

        //水平滚动设置为False

        et_content.setHorizontallyScrolling(false);


    }

    @Override
    protected void initData() {
        getIntentValue();
        //titleBarView.setLeftIcon(R.drawable.friend_icon_back_black);
        if (fromReportBean != null) {
            titleBarView.setTitle(String.format(UIUtils.getString(R.string.comment_reply_number), fromReportBean.getReplyNums() + ""));
            tv_nickname.setText(fromReportBean.getFromNickName());

            tv_content.setText(fromReportBean.getContent());
            tv_send_time.setText(TimeUtil.getDynmicTime(fromReportBean.getCreateTime(), ""));
            LoadImageUtil.getInstance().loadCirc(this, fromReportBean.getFromHeadUrlTiny(), iv_head_photo);
            setLikeView();
            if (fromReportBean.getFromUserId().equals(TokenUtil.getInstance().getPeopleIdInt(BaseApp.getApp()))) {
                tv_del.setVisibility(View.VISIBLE);
            } else {
                tv_del.setVisibility(View.GONE);
            }
        }
        tv_acti.setVisibility(View.VISIBLE);
        tv_acti.setText(UIUtils.getString(R.string.comment_host));

        list = new ArrayList<>();
        recyclerview.setLayoutManager(new LinearLayoutManager(this));
        mainReportAdapter = new MainNextReportAdapter(list);
        recyclerview.setAdapter(mainReportAdapter);

        onRefresh();
    }


    public void setLikeView() {
        tv_like_number.setText(fromReportBean.getPraiseNums() + "");
        if (fromReportBean.isWhetherPraise()) {
            iv_like.setImageResource(R.drawable.icon_report_like_press);
        } else {
            iv_like.setImageResource(R.drawable.icon_report_like_nor);
        }
    }

    @Override
    protected void initEvent() {


        setSoftKeyBoardListener();

        tv_content.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                showPhotoChoosePop(fromReportBean.getContent());
                return true;
            }
        });
        iv_head_photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startPersonal(fromReportBean.getFromUserId());
            }
        });
        tv_nickname.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startPersonal(fromReportBean.getFromUserId());
            }
        });
        view_hide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                KeyboardUtils.hideSoftInput(et_content);
            }
        });


        et_content.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if (TextUtils.isEmpty(s)) {
                    tv_send.setEnabled(false);
                } else {
                    tv_send.setEnabled(true);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        tv_del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PublicAlertDialog.getInstance().showDialog("", getString(R.string.ensure_delete), context, getResources().getString(R.string.common_dialog_cancel), getResources().getString(R.string.common_dialog_ok), new AlertDialogStateCallBack() {
                    @Override
                    public void determine() {
                        isMainDel = true;
                        mActPresenter.delCommend(commondId);
                    }

                    @Override
                    public void cancel() {

                    }
                }, false);

            }
        });
        smartRefreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                if (list != null && list.size() > 0) {
                    mActPresenter.getCommend(commondId, dynamicId, list.get(list.size() - 1).getDynamicCommentId(), meUserId, 1, 10);
                }
            }
        });
        iv_like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mActPresenter.likeCommend(fromReportBean.getDynamicCommentId(), meUserId, fromReportBean.getFromUserId(), true);
            }
        });
        mainReportAdapter.addOnItemClickListener(new MainNextReportAdapter.OnItemClickLisenter() {
            @Override
            public void onClick(View view, ReportBean info, int position) {
                replayUserId = info.getFromUserId();
                replayUserName = info.getFromNickName();
                replayCommend = info.getDynamicCommentId();
                KeyboardUtils.showSoftInput(et_content);

            }
        });

        mainReportAdapter.addOnChildLongClikeListener(new MainNextReportAdapter.OnChildLongClickLisenter() {
            @Override
            public void onLongClick(View view, ReportBean info, int position) {
                if (view.getId() == R.id.tv_content) {
                    showPhotoChoosePop(info.getContent());
                }
            }
        });
        mainReportAdapter.addOnChildClickListener(new MainNextReportAdapter.OnChildClickLisenter() {
            @Override
            public void onClick(View view, ReportBean info, int position) {


                if (view.getId() == R.id.tv_nickname) {
                    startPersonal(info.getFromUserId());
                } else if (view.getId() == R.id.iv_head_photo) {
                    startPersonal(info.getFromUserId());
                } else if (view.getId() == R.id.tv_replay_nikename) {
                    startPersonal(info.getToUserId());
                } else if (view.getId() == R.id.iv_like) {
                    mCurrentPosition = position;
                    mCurrentReportBean = list.get(position);
                    mActPresenter.likeCommend(mCurrentReportBean.getDynamicCommentId(), meUserId, mCurrentReportBean.getFromUserId(), false);
                } else if (view.getId() == R.id.tv_content) {
                    replayUserId = info.getFromUserId();
                    replayUserName = info.getFromNickName();
                    replayCommend = info.getDynamicCommentId();
                    et_content.setText("");
                    KeyboardUtils.showSoftInput(et_content);

                } else if (view.getId() == R.id.tv_del) {
                    PublicAlertDialog.getInstance().showDialog("", getString(R.string.ensure_delete), context, getResources().getString(R.string.common_dialog_cancel), getResources().getString(R.string.common_dialog_ok), new AlertDialogStateCallBack() {
                        @Override
                        public void determine() {
                            if (position >= 0 && position < list.size()) {
                                isMainDel = false;
                                mCurrentPosition = position;
                                mActPresenter.delCommend(list.get(position).getDynamicCommentId());
                            }
                        }

                        @Override
                        public void cancel() {

                        }
                    }, false);

                }
            }
        });
        titleBarView.setOnTitleBarClickListener(new TitleBarView.OnTitleBarClickListener() {
            @Override
            public void onLeftClicked(View view) {
                finish();
            }

            @Override
            public void onRightClicked(View view) {

            }
        });
        tv_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                replayUserId = "";
                replayUserName = "";
                replayCommend = "";
                KeyboardUtils.showSoftInput(et_content);
            }
        });
        tv_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String strContent = et_content.getText().toString().trim();
                if (TextUtils.isEmpty(strContent)) {
                    return;
                }
                if (TextUtils.isEmpty(replayUserId)) {
                    mActPresenter.addCommend(dynamicId, commondId, meUserId, fromId, strContent);
                } else {
                    mActPresenter.addCommend(dynamicId, replayCommend, meUserId, replayUserId, strContent);
                }
                KeyboardUtils.hideSoftInput(et_content);
            }
        });
    }

    @Override
    protected void initHeader() {

    }

    private void onRefresh() {
        mainReportAdapter.setEmptyView(getEmptempView());
        mActPresenter.getCommend(commondId, dynamicId, "", meUserId, 1, 10);
    }

    public View getEmptempView() {

        View notDataView = getLayoutInflater().inflate(R.layout.tempty_view, recyclerview, false);
        TextView tvEmpty = notDataView.findViewById(R.id.tv_empty_value);
        tvEmpty.setText(UIUtils.getString(R.string.no_replay));
        return notDataView;
    }


    @Override
    protected CommentPresent createPresenter() {
        return new CommentPresent(this);
    }

    @Override
    public void successAddCommend() {
        et_content.setText("");
        mActPresenter.getCommend(commondId, dynamicId, "", meUserId, 1, 10);

    }

    @Override
    public void failAddCommend() {

    }

    @Override
    public void successDelCommend(int commendnumb, int allnumber) {
        if (isMainDel) {
            updateItem(commendnumb, allnumber, true);
            finish();
        } else {
            list.remove(mCurrentPosition);
            mainReportAdapter.notifyDataSetChanged();
            titleBarView.setTitle(String.format(UIUtils.getString(R.string.comment_reply_number), commendnumb + ""));
            updateItem(commendnumb, allnumber, false);
        }
    }


    public void updateItem(int commendnumb, int allnumber, boolean isDel) {
        UpdatePeportBean updatePhotoBean = new UpdatePeportBean();
        updatePhotoBean.setCommendId(fromReportBean.getDynamicCommentId());
        updatePhotoBean.setSonSum(commendnumb);
        updatePhotoBean.setAllSum(allnumber);
        updatePhotoBean.setDel(isDel);
        MessageEvent messageEvent = new MessageEvent(updatePhotoBean, MessageEvent.main_report_update);
        EventBus.getDefault().post(messageEvent);
    }

    @Override
    public void failDelCommend() {

    }

    @Override
    public void successGetCommend(ResultCommentBean<ListDataCommend> data, String positionDynamicCommentId) {

        smartRefreshLayout.finishLoadMore();

        if (data != null) {
            updateItem(data.getReplyCommentNums(), data.getDynamicCommentNums(), false);
            titleBarView.setTitle(String.format(UIUtils.getString(R.string.comment_reply_number), data.getReplyCommentNums() + ""));
            if (data.getPageData() != null) {
                ListDataCommend data1 = data.getPageData();
                if (data1 != null) {
                    List<ReportBean> data1List = data1.getList();
                    Log.e("successGetCommend", data1List.toString());
                    if (data1List != null && data1List.size() > 0) {
                        if (data1List.size() >= 10) {
                            smartRefreshLayout.setEnableLoadMore(true);
                        } else {
                            smartRefreshLayout.setEnableLoadMore(false);
                        }
                        if (TextUtils.isEmpty(positionDynamicCommentId)) {
                            list.clear();
                        }
                        list.addAll(data1List);
                        mainReportAdapter.setList(list);
                        // mainReportAdapter.notifyDataSetChanged();
                        // recyclerview.notify();
                    } else {
                        smartRefreshLayout.setEnableLoadMore(false);
                    }
                }
            }
        }
        if (list.size() > 0) {

        } else {
            mainReportAdapter.notifyDataSetChanged();
            mainReportAdapter.setEmptyView(getEmptempView());
        }

    }

    @Override
    public void failGetCommend() {

    }

    @Override
    public void successLike(ResultLikeBean resultLikeBean, boolean isTop, String commondId) {
        if (isTop) {
            resultLikeBean.setDynamicInfoId(fromReportBean.getDynamicCommentId());
            EventBus.getDefault().post(new MessageEvent(resultLikeBean, MessageEvent.main_report_like_update));
            fromReportBean.setPraiseNums(resultLikeBean.getPraiseNums());
            fromReportBean.setWhetherPraise(resultLikeBean.isWhetherPraise());
            setLikeView();
        } else {
            mCurrentReportBean.setPraiseNums(resultLikeBean.getPraiseNums());
            mCurrentReportBean.setWhetherPraise(resultLikeBean.isWhetherPraise());
            mainReportAdapter.notifyItemChanged(mCurrentPosition);
        }

    }

    /**
     * 添加软键盘监听
     */
    SoftKeyBoardListener softKeyBoardListener;

    private void setSoftKeyBoardListener() {
        softKeyBoardListener = new SoftKeyBoardListener(this);
        //软键盘状态监听
        softKeyBoardListener.setListener(new SoftKeyBoardListener.OnSoftKeyBoardChangeListener() {
            @Override
            public void keyBoardShow(int height) {
                //软键盘已经显示，做逻辑
                if (TextUtils.isEmpty(replayUserName)) {
                    et_content.setHint(UIUtils.getString(R.string.friend_dynamic_hite));
                } else {
                    et_content.setHint(UIUtils.getString(R.string.comment_reply) + replayUserName);
                }
                layout_edit.setVisibility(View.VISIBLE);
                tv_edit.setVisibility(View.GONE);
            }

            @Override
            public void keyBoardHide(int height) {
                //软键盘已经隐藏,做逻辑
                layout_edit.setVisibility(View.GONE);
                tv_edit.setVisibility(View.VISIBLE);

            }
        });
    }

    private void startPersonal(String currentUserId) {
        Intent intent = new Intent(this, PersonalHomepageActivity.class);
        String userId = TokenUtil.getInstance().getPeopleIdInt(BaseApp.getApp());

        if (TextUtils.isEmpty(currentUserId)) {
            currentUserId = "";
        }

        Log.e("startPersonal", "userId:" + userId + "currentUserId:" + currentUserId);

        if (!userId.equals(currentUserId)) {
            intent.putExtra(FriendConstant.USER_ID, currentUserId);
        }
        startActivity(intent);
    }

    private PhotoChoosePopUtil photoChoosePopUtil;//弹出选择从哪里读取图片的pop

    private void showPhotoChoosePop(String content) {


        if (null == photoChoosePopUtil) {
            photoChoosePopUtil = new PhotoChoosePopUtil(context, UIUtils.getString(R.string.copy_text), true);
        }

        photoChoosePopUtil.show(this.getWindow().getDecorView());
        photoChoosePopUtil.setOnPhotoChooseListener(new PhotoChoosePopUtil.OnPhotoChooseListener() {
            @Override
            public void onChooseCamera() {
                //操作1
                // checkCamera();
                //保存到本地
                //cm.getText();//获取粘贴信息
                ClipboardManager cmb = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
                cmb.setText(content); //将内容放入粘贴管理器,在别的地方长按选择"粘贴"即可
            }

            @Override
            public void onChoosePhotograph() {
                ClipboardManager cmb = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
                cmb.setText(content); //将内容放入粘贴管理器,在别的地方长按选择"粘贴"即可
                // checkFileWritePermissions();
                //操作2

            }
        });


    }


}
