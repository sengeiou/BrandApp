package com.isport.brandapp.ropeskipping.realsport.dialog

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.drawable.ColorDrawable
import android.view.*
import android.widget.PopupWindow
import android.widget.TextView
import bike.gymproject.viewlibray.pickerview.ArrayPickerView
import bike.gymproject.viewlibray.pickerview.DatePickerView
import brandapp.isport.com.basicres.commonutil.UIUtils
import com.isport.brandapp.R

/**
 *  Created by BeyondWorlds
 *  on 2020/7/31
 */
class SelectPopupWindow(var mSelectPopupListener: OnSelectPopupListener?) : ArrayPickerView.ItemSelectedValue {

    companion object {
        const val ROPENUMBER = "ropenumber"
        const val ROPE_TIME_RE = "ropeTimeRe"
        const val ROPE_COUNT_RE = "ropeCountRe"
        const val ROPE_HR_VALUE = "ropeCountValue"
        const val BIRTHDAY = "birthday"
    }

    private var mMenuView: View? = null
    private var localData = ArrayList<String>()
    private var localDataNoUnit = ArrayList<String>()
    private val genderDatas = arrayOf<String>("男", "女")
    private val heightData = ArrayList<String>() // 身高

    private val heightDataNoUnit = ArrayList<String>() // 身高不带单位

//    private val weightData = ArrayList<String>() // 体重

    private val weightDataNoUnit = ArrayList<String>() // 体重不带单位

    private var popupWindow: PopupWindow? = null
    private var localChooseStr: String? = null
    private var selectType: String? = null
    private var showIndex = 0


    /**
     * @param context
     * @param view
     * @param type
     * @param lastData 上次的数据
     */
    @SuppressLint("ClickableViewAccessibility")
    fun popWindowSelect(
            context: Context,
            view: View?,
            type: String?,
            lastData: String,
            isCyclic: Boolean
    ) {
//        AppOpsManager appOpsManager = (AppOpsManager) getSystemService(Context.APP_OPS_SERVICE);
//        int checkOp = appOpsManager.checkOp(AppOpsManager.OPSTR_CAMERA, android.os.Process.myUid(), getPackageName());
        selectType = type
        localData.clear()
        when (type) {
//100～9999

            ROPENUMBER -> {
                var j = 100
                while (j < 9999) {
                    localData.add((j).toString())
                    localDataNoUnit.add("${j} " + UIUtils.getString(R.string.rope_unit))
//                    heightDataNoUnit[j] = (j + 140).toString()
//                    heightData[j] = "${j + 140} cm"
                    /* if (localData[j] == lastData) {
                         showIndex = j
                     }*/
                    if (j >= 1000) {
                        j += 500
                    } else {
                        j += 100
                    }
                }
                localData.add("9999")
                localDataNoUnit.add("9999 " + UIUtils.getString(R.string.rope_unit))
//                localData = heightData
//                localDataNoUnit = heightDataNoUnit
            }
            ROPE_HR_VALUE -> {

            }
            ROPE_TIME_RE -> {
                var j = 20
                while (j <= 60) {
                    localData.add((j).toString())
                    localDataNoUnit.add("${j} " + UIUtils.getString(R.string.rope_unit))
                    j = j + 10
                }
            }
            ROPE_COUNT_RE -> {
                var j = 50
                while (j <= 200) {
                    localData.add((j).toString())
                    localDataNoUnit.add("${j} " + UIUtils.getString(R.string.rope_unit))
                    j = j + 50
                }
            }
        }
//        val inflater = context
//            .getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        mMenuView = LayoutInflater.from(context).inflate(R.layout.app_pop_bottom_setting, null)
        val tv_determine =
                mMenuView!!.findViewById<View>(R.id.tv_determine) as TextView
        val tv_cancel =
                mMenuView!!.findViewById<View>(R.id.tv_cancel) as TextView
        val datePicker: ArrayPickerView =
                mMenuView!!.findViewById<View>(R.id.datePicker) as ArrayPickerView
        datePicker.setData(localData)
        datePicker.setCyclic(isCyclic)
        datePicker.setItemOnclick(this)
        datePicker.setSelectItem(showIndex)

//        localChooseStr = localData[showIndex]
        localChooseStr = localData[showIndex];
        popupWindow = PopupWindow(context)
        popupWindow!!.width = ViewGroup.LayoutParams.MATCH_PARENT
        popupWindow!!.height = ViewGroup.LayoutParams.WRAP_CONTENT
        popupWindow!!.contentView = mMenuView
        popupWindow!!.setBackgroundDrawable(ColorDrawable(0x00000000))
        popupWindow!!.isOutsideTouchable = false
        popupWindow!!.isFocusable = true
        popupWindow!!.animationStyle = R.style.popwin_anim_style
        popupWindow!!.showAtLocation(
                view, Gravity.BOTTOM
                or Gravity.CENTER_HORIZONTAL, 0, 0
        )

        if(mMenuView == null)
            return

        mMenuView!!.setOnTouchListener { v, event ->

            val linView = mMenuView!!.findViewById<View>(R.id.pop_layout) ?: return@setOnTouchListener false
            val height =
                    linView.top
            val y = event.y.toInt()
            if (event.action == MotionEvent.ACTION_UP) {
                if (y < height) {
                    popupWindow!!.dismiss()
                }
            }
            true
        }
        tv_determine.setOnClickListener {

            mSelectPopupListener?.onSelect(selectType!!, localChooseStr!!)
            popupWindow!!.dismiss()
        }
        tv_cancel.setOnClickListener { popupWindow!!.dismiss() }
    }

    @SuppressLint("ClickableViewAccessibility")
    fun popWindowSelect(
            context: Context,
            view: View?,
            type: String?,
            lastData: String,
            mindata: Int,
            maxData: Int,
            interve: Int,
            isCyclic: Boolean
    ) {
//        AppOpsManager appOpsManager = (AppOpsManager) getSystemService(Context.APP_OPS_SERVICE);
//        int checkOp = appOpsManager.checkOp(AppOpsManager.OPSTR_CAMERA, android.os.Process.myUid(), getPackageName());
        selectType = type
        localData.clear()
        when (type) {
//100～9999

            ROPE_HR_VALUE -> {
                var j = mindata
                while (j <= maxData) {
                    localData.add((j).toString())
                    localDataNoUnit.add("${j} " + UIUtils.getString(R.string.rope_unit))
                    j += interve
                }
            }

        }
//        val inflater = context
//            .getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        mMenuView = LayoutInflater.from(context).inflate(R.layout.app_pop_bottom_setting, null)
        val tv_determine =
                mMenuView!!.findViewById<View>(R.id.tv_determine) as TextView
        val tv_cancel =
                mMenuView!!.findViewById<View>(R.id.tv_cancel) as TextView
        val datePicker: ArrayPickerView =
                mMenuView!!.findViewById<View>(R.id.datePicker) as ArrayPickerView
        datePicker.setData(localData)
        datePicker.setCyclic(isCyclic)
        datePicker.setItemOnclick(this)
        datePicker.setSelectItem(showIndex)

//        localChooseStr = localData[showIndex]
        localChooseStr = localData[showIndex];
        popupWindow = PopupWindow(context)
        popupWindow!!.width = ViewGroup.LayoutParams.MATCH_PARENT
        popupWindow!!.height = ViewGroup.LayoutParams.WRAP_CONTENT
        popupWindow!!.contentView = mMenuView
        popupWindow!!.setBackgroundDrawable(ColorDrawable(0x00000000))
        popupWindow!!.isOutsideTouchable = false
        popupWindow!!.isFocusable = true
        popupWindow!!.animationStyle = R.style.popwin_anim_style
        popupWindow!!.showAtLocation(
                view, Gravity.BOTTOM
                or Gravity.CENTER_HORIZONTAL, 0, 0
        )

        if(mMenuView == null)
            return

        mMenuView!!.setOnTouchListener { v, event ->

            val linView = mMenuView!!.findViewById<View>(R.id.pop_layout) ?: return@setOnTouchListener false

            val height =
                    linView.top
            val y = event.y.toInt()
            if (event.action == MotionEvent.ACTION_UP) {
                if (y < height) {
                    popupWindow!!.dismiss()
                }
            }
            true
        }
        tv_determine.setOnClickListener {

            mSelectPopupListener?.onSelect(selectType!!, localChooseStr!!)
            popupWindow!!.dismiss()
        }
        tv_cancel.setOnClickListener { popupWindow!!.dismiss() }
    }

    override fun onItemSelectedValue(str: String?) {
        localChooseStr = str
    }

    //温度设置选择器
    private var mTmeapView: View? = null
    private var popupWindowTemp: PopupWindow? = null

    // 时间选择器

    // 时间选择器
    @SuppressLint("ClickableViewAccessibility")
    fun setPopupWindowTemp(
            context: Context,
            view: View?,
            value: Int
    ) {
        val inflater = context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        mTmeapView = inflater.inflate(R.layout.app_pop_bottom_temp, null)
        val tv_determine =
                mTmeapView!!.findViewById<View>(R.id.tv_determine) as TextView
        val tv_cancel =
                mTmeapView!!.findViewById<View>(R.id.tv_cancel) as TextView
        val datePicker2: ArrayPickerView =
                mTmeapView!!.findViewById<View>(R.id.datePicker2) as ArrayPickerView
        val datePicker3: ArrayPickerView =
                mTmeapView!!.findViewById<View>(R.id.datePicker3) as ArrayPickerView
        popupWindowTemp = PopupWindow(context)
        popupWindowTemp!!.width = ViewGroup.LayoutParams.MATCH_PARENT
        popupWindowTemp!!.height = ViewGroup.LayoutParams.WRAP_CONTENT
        //popupWindowBirth.setHeight(DisplayUtils.dip2px(context, 150));
        popupWindowTemp!!.contentView = mTmeapView
        popupWindowTemp!!.setBackgroundDrawable(ColorDrawable(0x00000000))
        popupWindowTemp!!.isOutsideTouchable = false
        popupWindowTemp!!.isFocusable = true
        popupWindowTemp!!.animationStyle = R.style.popwin_anim_style
        popupWindowTemp!!.showAtLocation(
                view, Gravity.BOTTOM
                or Gravity.CENTER_HORIZONTAL, 0, 0
        )
        val localData1 = ArrayList<String>()
        var showIndex1 = 0
        var showIndex2 = 0
        var showIndex3 = 0
        val currentValue = Math.abs(value)
        val yu = currentValue % 10
        val ge = currentValue / 10
        val localData2 = ArrayList<String>()
        val localData3 = ArrayList<String>()
        for (i in 0..99) {
            if (i <= 59) {
                if (ge == i) {
                    showIndex2 = i
                }
                localData3.add(i.toString() + "")

            }
            localData2.add(i.toString() + "")
        }
        datePicker2.setData(localData2)
        datePicker2.setCyclic(false)
        datePicker2.setItemOnclick(this)
        datePicker2.setSelectItem(showIndex2)
        datePicker3.setData(localData3)
        datePicker3.setCyclic(false)
        datePicker3.setItemOnclick(this)
        datePicker3.setSelectItem(showIndex3)


        mTmeapView!!.setOnTouchListener { v, event ->

            val linView = mTmeapView!!.findViewById<View>(R.id.pop_layout) ?: return@setOnTouchListener false


            val height = linView.top
            val y = event.y.toInt()
            if (event.action == MotionEvent.ACTION_UP) {
                if (y < height) {
                    popupWindowTemp!!.dismiss()
                }
            }
            true
        }
        var hourValue = 0
        tv_determine.setOnClickListener {
            hourValue = datePicker2.getItem().toInt() * 60 + (datePicker3.getItem().toInt())
            mSelectPopupListener?.let {
                val value: String = hourValue.toString()
                it.onSelect(BIRTHDAY, value)
            }
            popupWindowTemp!!.dismiss()
        }
        tv_cancel.setOnClickListener { popupWindowTemp!!.dismiss() }
    }

    private var mMenuViewBirth: View? = null
    private var popupWindowBirth: PopupWindow? = null
    // 时间选择器

    // 时间选择器
    @SuppressLint("ClickableViewAccessibility")
    fun setPopupWindow(
            context: Context,
            view: View?,
            type: String,
            defaultDay: String?
    ) {
        val inflater = context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        mMenuViewBirth = inflater.inflate(R.layout.app_pop_date, null)
        val tv_determine =
                mMenuViewBirth!!.findViewById<View>(R.id.tv_determine) as TextView
        val tv_cancel =
                mMenuViewBirth!!.findViewById<View>(R.id.tv_cancel) as TextView
        val datePicker: DatePickerView =
                mMenuViewBirth!!.findViewById<View>(R.id.datePicker) as DatePickerView
        if (type == "3") {
            datePicker.setType(3)
        }
        datePicker.setDefaultItemAdapter(defaultDay)
        datePicker.setCyclic(false)
        popupWindowBirth = PopupWindow(context)
        popupWindowBirth!!.width = ViewGroup.LayoutParams.MATCH_PARENT
        popupWindowBirth!!.height = ViewGroup.LayoutParams.WRAP_CONTENT
        //popupWindowBirth.setHeight(DisplayUtils.dip2px(context, 150));
        popupWindowBirth!!.contentView = mMenuViewBirth
        popupWindowBirth!!.setBackgroundDrawable(ColorDrawable(0x00000000))
        popupWindowBirth!!.isOutsideTouchable = false
        popupWindowBirth!!.isFocusable = true
        popupWindowBirth!!.animationStyle = R.style.popwin_anim_style
        popupWindowBirth!!.showAtLocation(
                view, Gravity.BOTTOM
                or Gravity.CENTER_HORIZONTAL, 0, 0
        )
        mMenuViewBirth!!.setOnTouchListener { v, event ->

            val linView = mMenuViewBirth!!.findViewById<View>(R.id.pop_layout)
                    ?: return@setOnTouchListener false

            val height =  linView.top
            val y = event.y.toInt()
            if (event.action == MotionEvent.ACTION_UP) {
                if (y < height) {
                    popupWindowBirth!!.dismiss()
                }
            }
            true
        }
        tv_determine.setOnClickListener { //                calculationAgeAndConstellation(datePicker.getTime());
            //                localUserChooseBirthday = datePicker.getTime();
            mSelectPopupListener?.onSelect(type, datePicker.getTime())
            popupWindowBirth!!.dismiss()
        }
        tv_cancel.setOnClickListener { popupWindowBirth!!.dismiss() }
    }

    interface OnSelectPopupListener {
        fun onSelect(type: String, data: String)
    }
}