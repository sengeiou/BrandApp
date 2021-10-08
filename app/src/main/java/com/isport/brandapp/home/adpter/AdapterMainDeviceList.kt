package com.isport.brandapp.home.adpter

import android.text.TextUtils
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import brandapp.isport.com.basicres.commonutil.UIUtils
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.isport.brandapp.home.bean.MainDeviceBean
import com.isport.brandapp.R
import com.isport.brandapp.banner.recycleView.utils.ToastUtil
import com.isport.brandapp.util.DeviceTypeUtil
import com.isport.brandapp.view.VerBatteryView

/**
 *  Created by BeyondWorlds
 *  on 2020/6/30
 */
class AdapterMainDeviceList(data: MutableList<MainDeviceBean>) :
        BaseQuickAdapter<MainDeviceBean, BaseViewHolder>(R.layout.item_main_deivce_list, data) {
    init {
        addChildClickViewIds(R.id.iv_setting)
        addChildClickViewIds(R.id.mShadowLayout)
    }

    override fun convert(holder: BaseViewHolder, item: MainDeviceBean) {
        if (item.isConn) {
            holder.setTextColor(R.id.tv_device_conn_state, UIUtils.getColor(R.color.common_view_color))
            holder.setText(R.id.tv_device_conn_state, R.string.connected)
            setIconAla(true, holder.getView(R.id.iv_pic))
            setIconAla(true, holder.getView(R.id.iv_right))
            setIconAla(true, holder.getView(R.id.iv_setting))
            holder.getView<ProgressBar>(R.id.pro_deviceing).visibility = View.INVISIBLE
            //holder.setVisible(R.id.pro_deviceing, false)
        } else {

            if (item.connState == 0) {
                holder.setText(R.id.tv_device_conn_state, R.string.disConnect)
                setIconAla(true, holder.getView(R.id.iv_pic))
                holder.getView<ProgressBar>(R.id.pro_deviceing).visibility = View.INVISIBLE
                // holder.setVisible(R.id.pro_deviceing, false)
//                ToastUtil.init(context)
//                ToastUtil.showTextToast(context.resources.getString(R.string.connect_fail))
            } else {
                setIconAla(false, holder.getView(R.id.iv_pic))
                holder.setText(R.id.tv_device_conn_state, R.string.app_isconnecting)
                holder.getView<ProgressBar>(R.id.pro_deviceing).visibility = View.VISIBLE
                // holder.setVisible(R.id.pro_deviceing, true)
            }
            holder.setTextColor(R.id.tv_device_conn_state, UIUtils.getColor(R.color.common_rope_time_color))

            setIconAla(false, holder.getView(R.id.iv_right))
            setIconAla(false, holder.getView(R.id.iv_setting))
        }
        holder.setText(R.id.tv_device_name, item.devicename)
        if (DeviceTypeUtil.deviceWatch(item.deviceType)) {
            holder.setText(R.id.tv_device_type_name, UIUtils.getString(R.string.watch))
        } else if (DeviceTypeUtil.deviceBrand(item.deviceType)) {
            holder.setText(R.id.tv_device_type_name, UIUtils.getString(R.string.wristband))
        }
        if (DeviceTypeUtil.isContainBody(item.deviceType)) {
            holder.setText(R.id.tv_device_type_name, UIUtils.getString(R.string.body_fat_scale))
            isScale(holder, true)
            if (item.scaleWeight == 0f) {
                holder.setText(R.id.tv_last_weight_value, UIUtils.getString(R.string.no_data))
            } else {
                holder.setText(R.id.tv_last_weight_value, "" + item.scaleWeight)
            }
            if (TextUtils.isEmpty(item.scaleTime)) {
                holder.setText(R.id.tv_last_weight_time, "")
            } else {
                holder.setText(R.id.tv_last_weight_time, "" + item.scaleTime)
            }
        } else {
            if (item.isConn) {
                var ivttery = holder.getView<VerBatteryView>(R.id.iv_battery)
                ivttery.setProgress(item.battery)
                if (item.battery > 0) {
                    holder.setText(R.id.tv_battery, "" + item.battery + "%")
                } else {
                    holder.setText(R.id.tv_battery, "")
                }
                isScale(holder, false)
            } else {
                hideAll(holder)
            }
        }


        if (DeviceTypeUtil.isContainBrand(item.deviceType)) {
            holder.setText(R.id.tv_device_type_name, UIUtils.getString(R.string.wristband))
        }

        if (DeviceTypeUtil.isContainRope(item.deviceType)) {
            holder.setText(R.id.tv_device_type_name, UIUtils.getString(R.string.rope_skipping))
        }

        holder.setImageResource(R.id.iv_pic, item.deviceRes)


        /*  holder.setText(R.id.tv_nickname,item.fromNickName)
          holder.setText(R.id.tv_time,""+item.createTime)
          when (item.followStatus) {
              1 -> holder.setText(R.id.tv_follow, R.string.friend_each_follow)
              0, 2 -> holder.setText(R.id.tv_follow, R.string.friend_to_follow)
              3 -> holder.setText(R.id.tv_follow, R.string.friend_already_follow)
          }
          holder.setText(R.id.tv_time, TimeUtil.getDynmicTime(item.getCreateTime(), ""))
          LoadImageUtil.getInstance().load(context, item.fromHeadUrlTiny, holder.getView(R.id.iv_head_photo))*/
    }

    fun setIconAla(isConnect: Boolean, imageView: ImageView) {
        if (isConnect) {
            imageView.alpha = 1.0f
        } else {
            imageView.alpha = 0.3f
        }
    }


    fun isScale(holder: BaseViewHolder, isScale: Boolean) {
        holder.setGone(R.id.iv_battery, isScale)
        holder.setGone(R.id.tv_battery, isScale)
        holder.setGone(R.id.tv_last_weight_title, !isScale)
        holder.setGone(R.id.tv_last_weight_value, !isScale)
        holder.setGone(R.id.tv_last_weight_unit, !isScale)
        holder.setGone(R.id.tv_last_weight_time, !isScale)
    }

    fun hideAll(holder: BaseViewHolder) {
        holder.setGone(R.id.iv_battery, true)
        holder.setGone(R.id.tv_battery, true)
        holder.setGone(R.id.tv_last_weight_title, true)
        holder.setGone(R.id.tv_last_weight_value, true)
        holder.setGone(R.id.tv_last_weight_unit, true)
        holder.setGone(R.id.tv_last_weight_time, true)
    }
}