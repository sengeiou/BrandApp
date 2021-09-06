package com.isport.brandapp.device.bracelet

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.isport.blelibrary.db.table.bracelet_w311.Bracelet_W311_AlarmModel
import com.isport.brandapp.R
import com.isport.brandapp.device.bracelet.Utils.RepeatUtils

/**
 *  Created by BeyondWorlds
 *  on 2020/6/30
 */
class AlarmListAdapter(data: MutableList<Bracelet_W311_AlarmModel>, type: Int) :
        BaseQuickAdapter<Bracelet_W311_AlarmModel, BaseViewHolder>(R.layout.app_item_alarm, data) {
    var currentType = 0

    init {

        currentType = type
        addChildClickViewIds(R.id.view_car_remind_radio)
    }


    override fun convert(holder: BaseViewHolder, item: Bracelet_W311_AlarmModel) {
        if (item.isOpen) {
            holder.setImageResource(R.id.view_car_remind_radio, R.drawable.icon_open)
            holder.setTextColor(R.id.tv_repeat, context.resources.getColor(R.color.common_white))
            holder.setTextColor(R.id.tv_time, context.resources.getColor(R.color.common_white))
        } else {
            holder.setImageResource(R.id.view_car_remind_radio, R.drawable.icon_close)
            holder.setTextColor(R.id.tv_repeat, context.resources.getColor(R.color.common_text_grey_color))
            holder.setTextColor(R.id.tv_time, context.resources.getColor(R.color.common_text_grey_color))
        }
        holder.setText(R.id.tv_repeat, RepeatUtils.setRepeat(currentType, item.getRepeatCount()))
        holder.setText(R.id.tv_time, item.timeString)

        /*holder.setText(R.id.tv_nickname,item.fromNickName)
        holder.setText(R.id.tv_time,""+item.createTime)
        when (item.followStatus) {
            1 -> holder.setText(R.id.tv_follow, R.string.friend_each_follow)
            0, 2 -> holder.setText(R.id.tv_follow, R.string.friend_to_follow)
            3 -> holder.setText(R.id.tv_follow, R.string.friend_already_follow)
        }
        holder.setText(R.id.tv_time, TimeUtil.getDynmicTime(item.getCreateTime(), ""))
        LoadImageUtil.getInstance().loadCirc(context, item.fromHeadUrlTiny, holder.getView(R.id.iv_head_photo))*/
    }
}