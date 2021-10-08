package com.isport.brandapp.message

import android.content.Intent
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import brandapp.isport.com.basicres.BaseApp
import brandapp.isport.com.basicres.commonalertdialog.AlertDialogStateCallBack
import brandapp.isport.com.basicres.commonalertdialog.PublicAlertDialog
import brandapp.isport.com.basicres.commonutil.TokenUtil
import brandapp.isport.com.basicres.commonutil.UIUtils
import brandapp.isport.com.basicres.commonview.TitleBarView
import brandapp.isport.com.basicres.mvp.BaseMVPTitleActivity
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.listener.OnItemChildClickListener
import com.chad.library.adapter.base.listener.OnItemClickListener
import com.chad.library.adapter.base.listener.OnItemLongClickListener
import com.isport.brandapp.R
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener
import com.scwang.smartrefresh.layout.listener.OnRefreshListener
import kotlinx.android.synthetic.main.activity_message.*
import phone.gym.jkcq.com.socialmodule.FriendConstant
import phone.gym.jkcq.com.socialmodule.activity.PersonalHomepageActivity
import phone.gym.jkcq.com.socialmodule.bean.ListData

//我的消息页面
internal class MessageActivity() : BaseMVPTitleActivity<MessageView, MessagePresenter>(), MessageView {

    var mDataList = mutableListOf<MessageInfo>()
    lateinit var mMessageAdapter: MessageAdapter
    var mCurrentMessage: MessageInfo? = null
    var mCurrentPosition = 0
    var currentPageNumber = 1


    override fun getLayoutId(): Int = R.layout.activity_message

    override fun initEvent() {
    }

    override fun initView(view: View?) {
        recyclerview_message.layoutManager = LinearLayoutManager(this)
        mMessageAdapter = MessageAdapter(mDataList)
        recyclerview_message.adapter = mMessageAdapter

        //        smart_refresh.setEnableLoadMore(true);
        smart_refresh_message.setOnRefreshListener(OnRefreshListener {
            currentPageNumber = 1;
            mActPresenter.getMessageInfo(currentPageNumber, 20, 2);
        })
        smart_refresh_message.setOnLoadMoreListener(OnLoadMoreListener {
            mActPresenter.getMessageInfo(currentPageNumber, 20, 2);
        })

        mMessageAdapter.setOnItemClickListener(object : OnItemClickListener {
            override fun onItemClick(adapter: BaseQuickAdapter<*, *>, view: View, position: Int) {
                startPersonal(mDataList.get(position).fromUserId);
            }
        })

        mMessageAdapter.setOnItemLongClickListener(object : OnItemLongClickListener {
            override fun onItemLongClick(adapter: BaseQuickAdapter<*, *>, view: View, position: Int): Boolean {
                mCurrentMessage = mDataList.get(position)
                delDialog(mCurrentMessage?.socialNewsId)
                return true
            }
        })
        mMessageAdapter.setOnItemChildClickListener(object : OnItemChildClickListener {
            override fun onItemChildClick(adapter: BaseQuickAdapter<*, *>, view: View, position: Int) {
                mCurrentMessage = mDataList.get(position)
                mCurrentPosition = position
                when (view.id) {
                    R.id.tv_follow -> {
                        when (mCurrentMessage?.followStatus) {
                            1, 3 -> PublicAlertDialog.getInstance().showDialog("", context.resources.getString(R.string.ensure_unfollow), context, resources.getString(R.string.common_dialog_cancel), resources.getString(R.string.common_dialog_ok), object : AlertDialogStateCallBack {
                                override fun determine() {
                                    mActPresenter.unFollow("" + mCurrentMessage?.fromUserId)
                                }

                                override fun cancel() {}
                            }, false)
                            0, 2 -> mActPresenter.addFollow("" + mCurrentMessage?.fromUserId)
                        }
                    }
                    R.id.iv_head_photo -> {
                        startPersonal(mDataList.get(position).fromUserId);
                    }
                    R.id.tv_nickname -> {
                        startPersonal(mDataList.get(position).fromUserId);
                    }
                }
            }
        })
    }


    private fun startPersonal(currentUserId: String) {
        var currentUserId = currentUserId
        val intent = Intent(this, PersonalHomepageActivity::class.java)
        val userId = TokenUtil.getInstance().getPeopleIdInt(BaseApp.getApp())
        if (TextUtils.isEmpty(currentUserId)) {
            currentUserId = ""
        }
        Log.e("startPersonal", "userId:" + userId + "currentUserId:" + currentUserId)
        if (userId != currentUserId) {
            intent.putExtra(FriendConstant.USER_ID, currentUserId)
        }
        startActivity(intent)
    }

    override fun initHeader() {
        titleBarView.setTitle(getString(R.string.friend_message))
        titleBarView.setOnTitleBarClickListener(object : TitleBarView.OnTitleBarClickListener() {
            override fun onRightClicked(view: View?) {
            }

            override fun onLeftClicked(view: View?) {
                finish()
            }
        })
    }

    override fun initData() {
        currentPageNumber=1
        mActPresenter.getMessageInfo(currentPageNumber, 20, 2);
    }


    override fun createPresenter(): MessagePresenter {
        return MessagePresenter(this);
    }

    override fun getMessageInfoSuccess(info: ListData<MessageInfo>?) {
        smart_refresh_message.finishRefresh();
        smart_refresh_message.finishLoadMore();


        if (info!!.isIsFirstPage) {
            mDataList.clear();
            if (info?.list != null && info.list.size > 0) {
                mDataList.addAll(info?.list);
                mMessageAdapter.setList(mDataList)
            } else {
                mMessageAdapter.notifyDataSetChanged()
                mMessageAdapter.setEmptyView(getEmptempView()!!)
            }
        } else {
            mDataList.addAll(info?.list)
            mMessageAdapter.notifyDataSetChanged()
        }
        currentPageNumber = info!!.pageNum + 1;
        if (info!!.total == mDataList.size) {
            smart_refresh_message.setEnableLoadMore(false);
        } else {

            smart_refresh_message.setEnableLoadMore(true);
        }

        // mMessageAdapter.setList(info?.list)
    }


    fun delDialog(socialNewsId: String?) {
        PublicAlertDialog.getInstance().showDialog("", getString(phone.gym.jkcq.com.socialmodule.R.string.ensure_delete), context, resources.getString(phone.gym.jkcq.com.socialmodule.R.string.common_dialog_cancel), resources.getString(phone.gym.jkcq.com.socialmodule.R.string.common_dialog_ok), object : AlertDialogStateCallBack {
            override fun determine() {
                mActPresenter.DelMessageInfo(socialNewsId)
            }

            override fun cancel() {}
        }, false)
    }

    override fun successDel(messageId: String?) {

        var removeIndx = -1
        for (i in 0..mDataList.size - 1) {
            if (mDataList.get(i).socialNewsId.equals(messageId)) {
                removeIndx = i;
            }
        }
        if (removeIndx != -1 && removeIndx < mDataList.size) {
            mDataList.removeAt(removeIndx)
        }
        mMessageAdapter.notifyDataSetChanged()
    }

    override fun addFollowSuccess(type: Int) {
//        mCurrentMessage?.followStatus = type
        checkSameUser(mCurrentMessage?.fromUserId!!, type)
        mMessageAdapter.notifyDataSetChanged()
    }

    override fun unFollowSuccess(type: Int) {
//        mCurrentMessage?.followStatus = type
        checkSameUser(mCurrentMessage?.fromUserId!!, type)
        mMessageAdapter.notifyDataSetChanged()
    }

    fun checkSameUser(id: String, type: Int) {
        for (data in mDataList) {
            if (data.fromUserId == id) {
                data.followStatus = type
            }
        }
    }

    fun getEmptempView(): View? {
        val notDataView = layoutInflater.inflate(phone.gym.jkcq.com.socialmodule.R.layout.tempty_view, recyclerview_message, false)
        val tvEmpty = notDataView.findViewById<TextView>(phone.gym.jkcq.com.socialmodule.R.id.tv_empty_value)
        tvEmpty.text = UIUtils.getString(R.string.no_message)
        notDataView.setOnClickListener { }
        return notDataView
    }


}
