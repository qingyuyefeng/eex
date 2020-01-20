package com.eex.mvp.homefrag.loader

import android.content.Context
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.eex.WPConfig
import com.youth.banner.loader.ImageLoader

/**
 * @Description:
 * @Author: xq
 * @CreateDate: 2019/12/30 14:13
 */
class GlideImageLoader:ImageLoader() {
    /**
     * 网络加载图片
     * 使用了Glide图片加载框架
     */

        override fun displayImage(context: Context, path: Any, imageView: ImageView) {

            Glide.with(context).load(WPConfig.PicBaseUrl + path).into(imageView)
        }

}