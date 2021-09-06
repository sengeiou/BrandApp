package com.isport.brandapp.device.watch.adapter

import brandapp.isport.com.basicres.commonutil.LoadImageUtil
import brandapp.isport.com.basicres.commonutil.UIUtils
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.isport.brandapp.R
import com.isport.brandapp.device.watch.bean.HrHitoryDetail
import phone.gym.jkcq.com.socialmodule.util.TimeUtil

/**
 *  Created by BeyondWorlds
 *  on 2020/6/30
 */
class HrListAdapter(data: MutableList<HrHitoryDetail>) :
        BaseQuickAdapter<HrHitoryDetail, BaseViewHolder>(R.layout.item_hr_history, data) {

    override fun convert(holder: BaseViewHolder, item: HrHitoryDetail) {
        holder.setText(R.id.tv_times, item.startTime + "~" + item.endTime)
        holder.setText(R.id.tv_max_hr, "" + item.maxHr)
        holder.setText(R.id.tv_min_hr, "" + item.minHr)
        if (item.isSelect) {
            holder.setBackgroundResource(R.id.layout_view, R.drawable.shape_btn_green_20_bg)
            holder.setTextColor(R.id.tv_follow, UIUtils.getColor(R.color.white));
            holder.setTextColor(R.id.tv_times, UIUtils.getColor(R.color.white));
            holder.setTextColor(R.id.tv_max_hr, UIUtils.getColor(R.color.white));
            holder.setTextColor(R.id.tv_min_hr, UIUtils.getColor(R.color.white));
            holder.setTextColor(R.id.tv_max_hr_title, UIUtils.getColor(R.color.white));
            holder.setTextColor(R.id.tv_min_hr_title, UIUtils.getColor(R.color.white));
        } else {
            holder.setBackgroundResource(R.id.layout_view, R.drawable.shape_btn_white_20_bg)
            holder.setTextColor(R.id.tv_follow, UIUtils.getColor(R.color.common_white));
            holder.setTextColor(R.id.tv_times, UIUtils.getColor(R.color.common_white));
            holder.setTextColor(R.id.tv_max_hr, UIUtils.getColor(R.color.common_white));
            holder.setTextColor(R.id.tv_min_hr, UIUtils.getColor(R.color.common_white));
            holder.setTextColor(R.id.tv_max_hr_title, UIUtils.getColor(R.color.common_white));
            holder.setTextColor(R.id.tv_min_hr_title, UIUtils.getColor(R.color.common_white));
        }
        /* holder.setText(R.id.tv_nickname,item.fromNickName)
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