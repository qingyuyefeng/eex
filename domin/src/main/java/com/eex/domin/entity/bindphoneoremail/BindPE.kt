package com.eex.domin.entity.bindphoneoremail

/**
 * @Description:
 * @Author: xq
 * @CreateDate: 2019/12/12 16:00
 */
data class BindPE (
        val level:Int = 0  //1、获取验证码  2、绑定手机  3、解绑手机  4、绑定邮箱  5、解绑邮箱
)