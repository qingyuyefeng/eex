package com.eex.domin.entity.login

/**
 * @Description: 登录返回实例
 * @Author: xq
 * @CreateDate: 2019/11/25 11:02
 */
data class Login(
        val level:Int = -1, //1获取验证码之前的login 2获取验证码 3获取验证码之后的login
        val msg:String = "",
        var loginType:String = "",
        val userName: String = "",
        val inputNumber: String = "",
        val passWord: String = "",
        val language: String = "",
        val phone: String = "",
        val googleState: String = "",
        val googleKey:String = "",
        val tokenId:String = "",
        val email:String = "",
        val accountPassWord: String = "",
        val createTime: String = "",
        val ctcDealState: String = "",
        val examState: String = "",
        val hasEmail: Int = 0,
        val hasPhone: Int = 0,
        val id: String = "",
        val invateCode: String = "",
        val invateUser: String = "",
        val inviteUser: String = "",
        val lastLoginIp: String = "",
        val merchant: Int = 0,
        val openId: String = "",
        val realName: Int = 0,
        val salt: String = "",
        val status: Int = 0,
        val userCode: String = "",
        val loginIp: String = "",
        val operation: String = "",
        val userId: String = ""
)