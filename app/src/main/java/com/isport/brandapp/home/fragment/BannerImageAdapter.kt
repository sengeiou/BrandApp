package com.isport.brandapp.home.fragment

import android.os.Build
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import brandapp.isport.com.basicres.BaseApp
import brandapp.isport.com.basicres.commonutil.AppUtil
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.isport.blelibrary.utils.Logger
import com.isport.brandapp.home.bean.AdviceBean
import com.isport.brandapp.R
import com.youth.banner.adapter.BannerAdapter
import com.youth.banner.util.BannerUtils

class BannerImageAdapter(imageUrls: List<AdviceBean>) :
        BannerAdapter<AdviceBean, BannerImageAdapter.ImageHolder>(imageUrls) {


    override fun onCreateHolder(parent: ViewGroup?, viewType: Int): ImageHolder {
        val imageView = ImageView(parent!!.context)
        val params = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
        )
        imageView.layoutParams = params
        imageView.scaleType = ImageView.ScaleType.CENTER_CROP
        //通过裁剪实现圆角
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            BannerUtils.setBannerRound(imageView, 0f)
        }
        return ImageHolder(imageView)
    }

    override fun onBindView(
            holder: ImageHolder?,
            data: AdviceBean?,
            position: Int,
            size: Int
    ) {
        val options = RequestOptions()
        options.fitCenter().placeholder(R.drawable.default_advice_picture)
                .error(R.drawable.default_advice_picture).dontAnimate()

        Logger.myLog("getAdviceList2 data!!.imageUrl=" + data!!.imageUrl)

        if (AppUtil.isZh(BaseApp.getApp())) {
            Glide.with(holder!!.itemView)
                    .load(data!!.imageUrl).apply(options)
                    .into(holder.imageView)
        } else {
            Glide.with(holder!!.itemView)
                    .load(data!!.imageUrlEn).apply(options)
                    .into(holder.imageView)
        }

    }


    class ImageHolder(view: View) : RecyclerView.ViewHolder(view) {
        var imageView: ImageView

        init {
            imageView = view as ImageView
        }
    }

}
