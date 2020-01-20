package com.eex.domin.entity.register

/**
 * @Description:
 * @Author: xq
 * @CreateDate: 2019/11/29 14:12
 */
data class Register(
        val level:Int = 1,  //1图形验证，2验证码，3注册
        val msg:String = "",
        val imageKey: String = "",
        val imageCode:String = ""
)