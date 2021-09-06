package com.isport.brandapp.message

import brandapp.isport.com.basicres.commonutil.LoadImageUtil
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.isport.brandapp.R
import phone.gym.jkcq.com.socialmodule.util.TimeUtil

/**
 *  Created by BeyondWorlds
 *  on 2020/6/30
 */
class MessageAdapter(data: MutableList<MessageInfo>) :
        BaseQuickAdapter<MessageInfo, BaseViewHolder>(R.layout.item_message_follow, data) {
    init {
        addChildClickViewIds(R.id.tv_follow)
        addChildClickViewIds(R.id.iv_head_photo)
        addChildClickViewIds(R.id.tv_nickname)

    }

    override fun convert(holder: BaseViewHolder, item: MessageInfo) {
        holder.setText(R.id.tv_nickname, item.fromNickName)
        holder.setText(R.id.tv_time, "" + item.createTime)
        when (item.followStatus) {
            1 -> {
                holder.setText(R.id.tv_follow, R.string.friend_each_follow)
                holder.setBackgroundResource(R.id.tv_follow, R.drawable.shape_btn_gray_20_bg)
                holder.setTextColor(R.id.tv_follow, context.resources.getColor(R.color.common_white))
            }
            0, 2 -> {
                holder.setText(R.id.tv_follow, R.string.friend_to_follow)
                holder.setBackgroundResource(R.id.tv_follow, R.drawable.shape_btn_green_20_bg)
                holder.setTextColor(R.id.tv_follow, context.resources.getColor(R.color.white))
            }
            3 -> {
                holder.setText(R.id.tv_follow, R.string.friend_already_follow)
                holder.setBackgroundResource(R.id.tv_follow, R.drawable.shape_btn_green_20_bg)
                holder.setTextColor(R.id.tv_follow, context.resources.getColor(R.color.white))
            }
        }
        holder.setText(R.id.tv_time, TimeUtil.getDynmicTime(item.getCreateTime(), ""))
        LoadImageUtil.getInstance().loadCirc(context, item.fromHeadUrlTiny, holder.getView(R.id.iv_head_photo))
    }
}