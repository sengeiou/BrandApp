package com.isport.brandapp.bind.Adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.isport.brandapp.R
import com.isport.brandapp.bean.DeviceBean

/**
 *  Created by BeyondWorlds
 *  on 2020/6/30
 */
class DeviceListAdapter(data: MutableList<DeviceBean>) :
        BaseQuickAdapter<DeviceBean, BaseViewHolder>(R.layout.item_all_device_list, data) {


    override fun convert(holder: BaseViewHolder, item: DeviceBean) {
        holder.setText(R.id.tv_device_type, item.scanName)
        holder.setImageResource(R.id.iv_device_type, +item.resId)
        holder.setBackgroundResource(R.id.layout_bg, +item.resBg)
    }
}