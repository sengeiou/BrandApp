package com.isport.brandapp.device.share

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver
import android.widget.RelativeLayout
import androidx.core.content.ContextCompat
import com.isport.brandapp.R

/**
 * 针对RelativeLayout，显示或隐藏一个遮罩层
 * shouldShow：是否显示遮罩层
 * maskColor：遮罩层颜色
 */
fun RelativeLayout.showMaskLayer(shouldShow: Boolean = true, maskColor: String = "#80F6BCC4") {
    val maskViewTag = "MASK_VIEW_TAG"
    var maskView = findViewWithTag<View>(maskViewTag)
    if (shouldShow) {
        if (maskView == null) {
            maskView = View(context)
            maskView.tag = maskViewTag
            //设置遮罩层颜色
            maskView.setBackgroundColor(Color.parseColor(maskColor))
            //屏蔽点击事件
            maskView.setOnClickListener { }
            addView(this, maskView)
        }
    } else {
        maskView?.let { removeView(it) }
    }
}

fun RelativeLayout.showGradientMaskLayer(shouldShow: Boolean = true) {
    val maskViewTag = "MASK_VIEW_TAG"
    var maskView = findViewWithTag<View>(maskViewTag)
    if (shouldShow) {
        if (maskView == null) {
            maskView = View(context)
            maskView.tag = maskViewTag
            //设置遮罩层颜色
            setGradientBackground(context, maskView)
            //屏蔽点击事件
            maskView.setOnClickListener { }
            addView(this, maskView)
        }
    } else {
        maskView?.let { removeView(it) }
    }
}

private fun setGradientBackground(context: Context, view: View) {
    /* val colors = intArrayOf(
             ContextCompat.getColor(context, R.color.gradientStartColor),
             ContextCompat.getColor(context, R.color.gradientCenterColor),
             ContextCompat.getColor(context, R.color.gradientEndColor)
     )*/
    // val g = GradientDrawable(GradientDrawable.Orientation.TOP_BOTTOM, colors)
    // view.background = g
}

/**
 * 动态添加遮罩层view。
 * 通过调用viewTreeObserver的addOnPreDrawListener方法，可以在视图绘制前进行添加。
 * 因为此时视图已经经过了onMeasure，知道了自己的宽高。
 */
private fun addView(viewGroup: ViewGroup, maskView: View?) {
    viewGroup.viewTreeObserver.addOnPreDrawListener(object : ViewTreeObserver.OnPreDrawListener {
        override fun onPreDraw(): Boolean {
            viewGroup.viewTreeObserver.removeOnPreDrawListener(this)
            viewGroup.addView(maskView, viewGroup.width, viewGroup.height)
            return true
        }
    })
}