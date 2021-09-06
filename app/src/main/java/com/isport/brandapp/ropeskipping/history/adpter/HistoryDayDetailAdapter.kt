package com.jkcq.homebike.history.adpter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.isport.blelibrary.db.table.s002.DailyBrief
import com.isport.blelibrary.utils.DateUtil
import com.isport.brandapp.R
import phone.gym.jkcq.com.commonres.common.JkConfiguration


/**
 *  Created by BeyondWorlds
 *  on 2020/6/30
 */
class HistoryDayDetailAdapter(data: MutableList<DailyBrief>) :
        BaseQuickAdapter<DailyBrief, BaseViewHolder>(R.layout.item_rope_day_history_detail, data) {
    init {

    }

    override fun convert(holder: BaseViewHolder, item: DailyBrief) {


        holder.setText(R.id.tv_start_time, item.hhandMin)
        holder.setText(R.id.tv_sum_count, item.skippingNum)
        holder.setText(R.id.tv_duration, DateUtil.getRopeFormatTimehhmmss(item.skippingDuration))
        holder.setText(R.id.tv_cal, item.totalCalories)
        holder.setText(R.id.tv_avg_cout, item.averageFrequency)

        when (item.exerciseType) {
            JkConfiguration.RopeSportType.Free -> {
                holder.setImageResource(R.id.iv_rope_type, R.drawable.icon_rope_history_free)
            }
            JkConfiguration.RopeSportType.Count -> {
                holder.setImageResource(R.id.iv_rope_type, R.drawable.icon_rope_history_count)
            }
            JkConfiguration.RopeSportType.Time -> {
                holder.setImageResource(R.id.iv_rope_type, R.drawable.icon_rope_history_time)
            }


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