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
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.blankj.utilcode.util.KeyboardUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemLongClickListener;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import brandapp.isport.com.basicres.BaseApp;
import brandapp.isport.com.basicres.commonalertdialog.AlertDialogStateCallBack;
import brandapp.isport.com.basicres.commonalertdialog.PublicAlertDialog;
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
import phone.gym.jkcq.com.socialmodule.report.bean.UpdateDynicBean;
import phone.gym.jkcq.com.socialmodule.report.bean.UpdatePeportBean;
import phone.gym.jkcq.com.socialmodule.report.present.CommentPresent;
import phone.gym.jkcq.com.socialmodule.report.view.CommentView;
import phone.gym.jkcq.com.socialmodule.report.view.SoftKeyBoardListener;

public class FriendMainReportActivity extends BaseMVPTitleActivity<CommentView, CommentPresent> implements CommentView {
    TextView tv_edit;
    LinearLayout layout_edit;
    EditText et_content;
    TextView tv_send;
    MainReportAdapter mainReportAdapter;
    SmartRefreshLayout smartRefreshLayout;
    RecyclerView recyclerview;
    TextView tv_empty;
    View view_hide;
    int mCurrentPosition;
    ReportBean mCurrentReportBean;


    String dynamicId;
    String fromId;
    String meUserId;
    int replyCount;

    ArrayList<ReportBean> list;


    @Override
    protected int getLayoutId() {
        return R.layout.friend_activity_main_report;
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        dynamicId = getIntent().getStringExtra(FriendConstant.DYNAMIC_ID);
        fromId = getIntent().getStringExtra(FriendConstant.USER_ID);
    }

    private void getIntentValue() {
        dynamicId = getIntent().getStringExtra(FriendConstant.DYNAMIC_ID);
        fromId = getIntent().getStringExtra(FriendConstant.USER_ID);
        replyCount = getIntent().getIntExtra(FriendConstant.REPLYCOUNT, 0);
        meUserId = TokenUtil.getInstance().getPeopleIdInt(BaseApp.getApp());
        Log.e("getIntentValue", TextUtils.isEmpty(dynamicId) ? "is null" : dynamicId);
        Log.e("getIntentValue", TextUtils.isEmpty(fromId) ? "is null" : fromId);
    }

    int currentIndex = -1;

    @Override
    public void Event(MessageEvent messageEvent) {
        switch (messageEvent.getMsg()) {
            case MessageEvent.main_report_like_update:
                currentIndex = -1;
                ResultLikeBean resultLikeBean = (ResultLikeBean) messageEvent.getObj();
                for (int i = 0; i < list.size(); i++) {
                    if (list.get(i).getDynamicCommentId().equals(resultLikeBean.getDynamicInfoId())) {
                        currentIndex = i;
                    }
                }
                if (currentIndex != -1) {
                    ReportBean updateReport = list.get(currentIndex);
                    updateReport.setPraiseNums(resultLikeBean.getPraiseNums());
                    updateReport.setWhetherPraise(resultLikeBean.isWhetherPraise());
                    mainReportAdapter.notifyItemChanged(currentIndex);

                }

                break;
            case MessageEvent.main_report_update:
                currentIndex = -1;
                UpdatePeportBean updatePhotoBean = (UpdatePeportBean) messageEvent.getObj();
                replyCount = updatePhotoBean.getAllSum();
                updateItem();
                titleBarView.setTitle(String.format(UIUtils.getString(R.string.comment_number), replyCount + ""));
                //titleBarView.setTitle(String.format(UIUtils.getString(R.string.comment_number), replyCount + ""));
                for (int i = 0; i < list.size(); i++) {
                    if (list.get(i).getDynamicCommentId().equals(updatePhotoBean.getCommendId())) {
                        currentIndex = i;
                    }
                }
                if (currentIndex != -1) {
                    if (updatePhotoBean.isDel()) {
                        list.remove(currentIndex);
                        mainReportAdapter.notifyDataSetChanged();
                    } else {
                        ReportBean updateReport = list.get(currentIndex);
                        updateReport.setReplyNums(updatePhotoBean.getSonSum());
                        mainReportAdapter.notifyItemChanged(currentIndex);
                    }
                }

                break;

        }
    }

    @Override
    protected void initView(View view) {

        tv_edit = view.findViewById(R.id.tv_edit);
        tv_send = view.findViewById(R.id.tv_send);
        layout_edit = view.findViewById(R.id.layout_edit);
        et_content = view.findViewById(R.id.et_content);
        smartRefreshLayout = view.findViewById(R.id.smart_refresh_like);
        recyclerview = view.findViewById(R.id.recyclerview);
        tv_empty = view.findViewById(R.id.tv_empty);
        view_hide = view.findViewById(R.id.view_hide);
        //改变默认的单行模式
        et_content.setSingleLine(false);

        //水平滚动设置为False

        et_content.setHorizontallyScrolling(false);
    }

    @Override
    protected void initData() {
        EventBus.getDefault().register(this);
        getIntentValue();
        titleBarView.setTitle(String.format(UIUtils.getString(R.string.comment_number), replyCount + ""));
        // titleBarView.setLeftIcon(R.drawable.friend_icon_back_black);
        list = new ArrayList<>();
        recyclerview.setLayoutManager(new LinearLayoutManager(this));
        mainReportAdapter = new MainReportAdapter(list);
        recyclerview.setAdapter(mainReportAdapter);
        onRefresh();
    }

    private void onRefresh() {
        mainReportAdapter.setEmptyView(getEmptempView());
        mActPresenter.getCommend("", dynamicId, "", meUserId, 1, 10);
    }

    public View getEmptempView() {

        View notDataView = getLayoutInflater().inflate(R.layout.tempty_view, recyclerview, false);
        return notDataView;
    }


    @Override
    protected void initEvent() {
        setSoftKeyBoardListener();
        view_hide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                KeyboardUtils.hideSoftInput(et_content);
            }
        });
        mainReportAdapter.addOnChildLongClikeListener(new MainReportAdapter.OnChildLongClickLisenter() {
            @Override
            public void onLongClick(View view, ReportBean info, int position) {
                if (view.getId() == R.id.tv_content) {
                    showPhotoChoosePop(info.getContent());
                }
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

        smartRefreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                if (list != null && list.size() > 0) {
                    mActPresenter.getCommend("", dynamicId, list.get(list.size() - 1).getDynamicCommentId(), meUserId, 1, 10);
                }
            }
        });
        mainReportAdapter.addOnItemClickListener(new MainReportAdapter.OnItemClickLisenter() {
            @Override
            public void onClick(View view, ReportBean info, int position) {


                Intent intent = new Intent(FriendMainReportActivity.this, FriendMainNextReportActivity.class);

                intent.putExtra(FriendConstant.DYNAMIC_ID, dynamicId);
                intent.putExtra(FriendConstant.USER_ID, info.getFromUserId());
                intent.putExtra(FriendConstant.COMMEND_ID, info.getDynamicCommentId());
                intent.putExtra(FriendConstant.REPORT, info);

                startActivity(intent);
            }
        });

        mainReportAdapter.addOnChildClickListener(new MainReportAdapter.OnChildClickLisenter() {
            @Override
            public void onClick(View view, ReportBean info, int position) {
                if (view.getId() == R.id.tv_nickname) {
                    startPersonal(info.getFromUserId());
                } else if (view.getId() == R.id.iv_like) {
                    mCurrentPosition = position;
                    mCurrentReportBean = list.get(position);
                    mActPresenter.likeCommend(mCurrentReportBean.getDynamicCommentId(), meUserId, mCurrentReportBean.getFromUserId(), false);
                } else if (view.getId() == R.id.tv_content) {
                    Intent intent = new Intent(FriendMainReportActivity.this, FriendMainNextReportActivity.class);
                    intent.putExtra(FriendConstant.DYNAMIC_ID, dynamicId);
                    intent.putExtra(FriendConstant.USER_ID, info.getFromUserId());
                    intent.putExtra(FriendConstant.COMMEND_ID, info.getDynamicCommentId());
                    intent.putExtra(FriendConstant.REPORT, info);
                    startActivity(intent);

                } else if (view.getId() == R.id.iv_head_photo) {
                    startPersonal(info.getFromUserId());
                } else if (view.getId() == R.id.tv_del) {
                    PublicAlertDialog.getInstance().showDialog("", getString(R.string.ensure_delete), context, getResources().getString(R.string.common_dialog_cancel), getResources().getString(R.string.common_dialog_ok), new AlertDialogStateCallBack() {
                        @Override
                        public void determine() {
                            if (position >= 0 && position < list.size()) {
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
                // ToastUtils.showToast(BaseApp.getApp(), "点击了tv_edit");
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
                mActPresenter.addCommend(dynamicId, "", meUserId, fromId, strContent);
                KeyboardUtils.hideSoftInput(et_content);
            }
        });
    }

    @Override
    protected void initHeader() {

    }

    @Override
    protected CommentPresent createPresenter() {
        return new CommentPresent(this);
    }

    @Override
    public void successAddCommend() {
        et_content.setText("");
        mActPresenter.getCommend("", dynamicId, "", meUserId, 1, 10);

    }

    @Override
    public void failAddCommend() {
        tv_edit.setVisibility(View.VISIBLE);
    }

    @Override
    public void successDelCommend(int commendnumb, int sumAll) {
        list.remove(mCurrentPosition);
        mainReportAdapter.notifyDataSetChanged();
        replyCount = sumAll;
        updateItem();
        titleBarView.setTitle(String.format(UIUtils.getString(R.string.comment_number), commendnumb + ""));
    }

    @Override
    public void failDelCommend() {

    }

    @Override
    public void successGetCommend(ResultCommentBean<ListDataCommend> data, String positionDynamicCommentId) {

        smartRefreshLayout.finishLoadMore();
        mainReportAdapter.setOnItemLongClickListener(new OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(@NonNull BaseQuickAdapter adapter, @NonNull View view, int position) {
                return false;
            }
        });

        if (data != null) {
            replyCount = data.getCommentTotal();
            titleBarView.setTitle(String.format(UIUtils.getString(R.string.comment_number), replyCount + ""));
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

        updateItem();
        if (list.size() > 0) {

        } else {
            mainReportAdapter.setEmptyView(getEmptempView());
        }

    }

    @Override
    public void failGetCommend() {

    }

    @Override
    public void successLike(ResultLikeBean resultLikeBean, boolean isTop, String commendId) {
        mCurrentReportBean.setPraiseNums(resultLikeBean.getPraiseNums());
        mCurrentReportBean.setWhetherPraise(resultLikeBean.isWhetherPraise());
        mainReportAdapter.notifyItemChanged(mCurrentPosition);

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


    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }


    public void updateItem() {
        UpdateDynicBean updatePhotoBean = new UpdateDynicBean();
        updatePhotoBean.setDynamicId(dynamicId);
        updatePhotoBean.setAllSum(replyCount);
        // Logger.e("updateItem:" + replyCount);
        MessageEvent messageEvent = new MessageEvent(updatePhotoBean, MessageEvent.main_dynicid_update);
        EventBus.getDefault().post(messageEvent);
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
                ClipboardManager cmb = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
                cmb.setText(content); //将内容放入粘贴管理器,在别的地方长按选择"粘贴"即可
                //cm.getText();//获取粘贴信息


            }

            @Override
            public void onChoosePhotograph() {
                // checkFileWritePermissions();
                //操作2

            }
        });


    }
}
