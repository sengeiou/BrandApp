package com.jkcq.homebike.history.adpter

import android.content.Intent
import android.graphics.Color
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import brandapp.isport.com.basicres.BaseApp
import brandapp.isport.com.basicres.commonutil.TokenUtil
import brandapp.isport.com.basicres.commonutil.UIUtils
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.isport.blelibrary.db.table.s002.DailyBrief
import com.isport.blelibrary.utils.AppLanguageUtil
import com.isport.brandapp.AppConfiguration
import com.isport.brandapp.R
import com.isport.brandapp.login.ropeshare.ActivityRopeDetailWebView
import com.isport.brandapp.ropeskipping.response.ResponseDailySummaries


/**
 *  Created by BeyondWorlds
 *  on 2020/6/30
 */
class HistoryWeekDetailAdapter(data: MutableList<ResponseDailySummaries>) :
        BaseQuickAdapter<ResponseDailySummaries, BaseViewHolder>(R.layout.item_rope_week_history_detail, data) {
    init {

        addChildClickViewIds(R.id.layout_head)
    }


    private fun updateRecyclerView(recyclerView: RecyclerView) {


    }

    lateinit var historyDayDetailAdapter: HistoryDayDetailAdapter

    private fun setItemRecyclerView(recyclerView: RecyclerView, mExerciseInfos: MutableList<DailyBrief>) {
        recyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        historyDayDetailAdapter = HistoryDayDetailAdapter(mExerciseInfos)
        historyDayDetailAdapter.setOnItemClickListener { v, note, position ->
            var intent = Intent(context, ActivityRopeDetailWebView::class.java)
            intent.putExtra("title", UIUtils.getString(R.string.rope_dtail))
            intent.putExtra("urldark", AppConfiguration.ropedetailDarkurl + "?userId=" + TokenUtil.getInstance().getPeopleIdInt(BaseApp.instance) + "&ropeId=" + mExerciseInfos.get(position).ropeSportDetailId+ "&language=" + AppLanguageUtil.getCurrentLanguageStr())
            intent.putExtra("urlLigh", AppConfiguration.ropedetailLighturl + "?userId=" + TokenUtil.getInstance().getPeopleIdInt(BaseApp.instance) + "&ropeId=" + mExerciseInfos.get(position).ropeSportDetailId+ "&language=" + AppLanguageUtil.getCurrentLanguageStr())
            // intent.putExtra("url", AppConfiguration.ropedetailurl + "?userId=" + TokenUtil.getInstance().getPeopleIdInt(BaseApp.instance) + "&ropeId=" + mExerciseInfos.get(position).ropeSportDetailId)
            context.startActivity(intent)
        }
        recyclerView.adapter = historyDayDetailAdapter
    }

    override fun convert(holder: BaseViewHolder, item: ResponseDailySummaries) {
        var rec = holder.getView<RecyclerView>(R.id.rec_day)

        holder.setText(R.id.tv_count, "" + item.totalSkippingNum)
        holder.setText(R.id.tv_cal, "" + item.totalCalories)
        holder.setText(R.id.tv_date, "" + item.day)

        if (item.isOpen) {
            holder.setVisible(R.id.rec_day, true)
            if (item!!.list != null) {
                item!!.currentShowList.addAll(item!!.list)
                holder.setImageResource(R.id.iv_arrow_right, R.drawable.icon_rope_up)
                holder.setBackgroundResource(R.id.layout_view, R.drawable.shape_btn_white_20_bg)
                setItemRecyclerView(rec, item!!.currentShowList)
            } else {
                holder.setImageResource(R.id.iv_arrow_right, R.drawable.icon_rope_down)
                holder.setBackgroundColor(R.id.layout_view, Color.TRANSPARENT)
            }
        } else {
            if (item!!.currentShowList != null) {
                item!!.currentShowList.clear()
                setItemRecyclerView(rec, item!!.currentShowList)
            }
            holder.setVisible(R.id.rec_day, false)
            holder.setImageResource(R.id.iv_arrow_right, R.drawable.icon_rope_down)
            holder.setBackgroundColor(R.id.layout_view, Color.TRANSPARENT)
        }
        /*holder.setText(R.id.tv_date, item.date);
        val view = holder.getView<View>(R.id.view_current_bg)
        if (TextUtils.isEmpty(item.date)) {
            holder.setVisible(R.id.view_bg, false)
            holder.setVisible(R.id.view_current_bg, false)
        } else {
            val linearParams: RelativeLayout.LayoutParams =
                    view.getLayoutParams() as RelativeLayout.LayoutParams
            val size = DisplayUtils.dip2px(context, 130f)
            linearParams.height = (size * (item.currentValue * 1.0f / item.maxVlaue)).toInt()
            if (linearParams.height >= size) {
                linearParams.height = size;
            }
            view.setLayoutParams(linearParams)
            Log.e(
                    "linearParams.height",
                    " " + linearParams.height + "--currentValue=" + item.currentValue + "---maxVlaue=" + item.maxVlaue + "size=" + size
            )
            holder.setVisible(R.id.view_bg, true)
            holder.setVisible(R.id.view_current_bg, true)
        }

        if (item.isSelect) {
            holder.setVisible(R.id.tv_select, true)
        } else {
            holder.setVisible(R.id.tv_select, false)
        }*/
        /*  holder.setText(R.id.tv_time,""+item.createTime)
          when (item.followStatus) {
              1 -> holder.setText(R.id.tv_follow, R.string.friend_each_follow)
              0, 2 -> holder.setText(R.id.tv_follow, R.string.friend_to_follow)
              3 -> holder.setText(R.id.tv_follow, R.string.friend_already_follow)
          }*/
    }
}