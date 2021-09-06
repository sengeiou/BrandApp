package com.isport.brandapp.ropeskipping

import brandapp.isport.com.basicres.commonutil.LoadImageUtil
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.isport.brandapp.R
import phone.gym.jkcq.com.socialmodule.util.TimeUtil

/**
 *  Created by BeyondWorlds
 *  on 2020/6/30
 */
class RopeSkippingAdapter(data: MutableList<RopeSkippingBean>) :
        BaseQuickAdapter<RopeSkippingBean, BaseViewHolder>(R.layout.item_rope_skipping_course, data) {

    override fun convert(holder: BaseViewHolder, item: RopeSkippingBean) {
       // holder.setBackgroundResource(R.id.layout, item.bgRes)
        holder.setImageResource(R.id.iv_pic, item.valueRes)
        holder.setText(R.id.tv_value, item.name)
    }
}