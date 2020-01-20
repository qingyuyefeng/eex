package com.eex.domin.entity.retrieve

import java.io.Serializable

/**
 * @Description:
 * @Author: xq
 * @CreateDate: 2019/12/2 18:14
 */
data class Retrieve(
        val level: Int = 1,
        val msg: String = "",
        val type: Int = 0,   //  1邮箱验证 2手机验证
        val username: String = "",
        val code: String = ""
):Serializable