package com.jkcq.homebike.history.adpter

import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.RelativeLayout
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.isport.blelibrary.utils.Logger
import com.isport.brandapp.R
import com.isport.brandapp.ropeskipping.history.bean.BarInfo
import phone.gym.jkcq.com.commonres.commonutil.DisplayUtils


/**
 *  Created by BeyondWorlds
 *  on 2020/6/30
 */
class BarAdapter(data: MutableList<BarInfo>, count: Int) :
        BaseQuickAdapter<BarInfo, BaseViewHolder>(R.layout.item_history_sport_bar_detail, data) {
    var listcount = 0

    init {
        listcount = count

    }


    override fun convert(holder: BaseViewHolder, item: BarInfo) {
        holder.setText(R.id.tv_date, item.mdDate);
        val view = holder.getView<View>(R.id.view_current_bg)
        val view_width = holder.getView<View>(R.id.view_width)
        if (TextUtils.isEmpty(item.mdDate)) {
            holder.setVisible(R.id.view_bg, false)
            holder.setVisible(R.id.view_current_bg, false)
        } else {
            val lineartopParams: RelativeLayout.LayoutParams =
                    view_width.getLayoutParams() as RelativeLayout.LayoutParams
            var toalWith = DisplayUtils.getScreenWidth(context) - DisplayUtils.dip2px(context, 60f);
            lineartopParams.width = (toalWith / listcount).toInt();
            view_width.setLayoutParams(lineartopParams)


            val linearParams: RelativeLayout.LayoutParams =
                    view.getLayoutParams() as RelativeLayout.LayoutParams
            val size = DisplayUtils.dip2px(context, 102f)
            linearParams.height = (size * (item.currentValue * 1.0f / item.maxVlaue)).toInt()


            Logger.myLog("item.currentValue="+item.currentValue+",item.maxVlaue="+item.maxVlaue+",size="+size+",linearParams.height="+linearParams.height)

            if (linearParams.height >= size) {
                linearParams.height = size;
            }else if(item.currentValue>0&&linearParams.height<DisplayUtils.dip2px(context, 8f)){
                linearParams.height = DisplayUtils.dip2px(context, 8f);
            }
            view.setLayoutParams(linearParams)
            holder.setVisible(R.id.view_bg, true)
            holder.setVisible(R.id.view_current_bg, true)
        }
        holder.setText(R.id.tv_select_value, "" + item.currentValue)
        if (item.isSelect) {
            holder.setVisible(R.id.tv_select_value, true)
            holder.setBackgroundResource(R.id.view_current_bg, R.drawable.shape_current_value_bar_bg)
        } else {
            holder.setBackgroundResource(R.id.view_current_bg, R.drawable.shape_current_value_bar_nor_bg)
            holder.setVisible(R.id.tv_select_value, false)
        }
        /*  holder.setText(R.id.tv_time,""+item.createTime)
          when (item.followStatus) {
              1 -> holder.setText(R.id.tv_follow, R.string.friend_each_follow)
              0, 2 -> holder.setText(R.id.tv_follow, R.string.friend_to_follow)
              3 -> holder.setText(R.id.tv_follow, R.string.friend_already_follow)
          }*/
    }
}