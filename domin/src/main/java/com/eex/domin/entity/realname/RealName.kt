package com.eex.domin.entity.realname

import android.graphics.Bitmap

/**
 * @Description:
 * @Author: xq
 * @CreateDate: 2019/12/17 16:17
 */
data class RealName(
        val level: Int = -1,
        val msg: String = "",
        val picName: String = "",
        val bitmap: Bitmap? = null
)