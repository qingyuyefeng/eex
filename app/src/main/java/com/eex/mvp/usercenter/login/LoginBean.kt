package com.eex.mvp.usercenter.login

/**
 * @Description:
 * @Author: xq
 * @CreateDate: 2019/11/27 11:46
 */

data class LoginBean(
    val accountPassWord: Any?,
    val createTime: Any?,
    val ctcDealState: Any?,
    val email: Any?,
    val examState: Any?,
    val googleKey: Any?,
    val googleState: Any?,
    val hasEmail: Int,
    val hasPhone: Int,
    val id: String?,
    val invateCode: String?,
    val invateUser: Any?,
    val inviteUser: Any?,
    val lastLoginIp: Any?,
    val logDTO: LogDTO,
    val merchant: Int,
    val openId: Any?,
    val password: String?,
    val phone: String?,
    val realName: Int,
    val salt: String?,
    val status: Int,
    val tokenId: Any?,
    val userCode: String?,
    val username: String?
)

data class LogDTO(
    val createTime: Long,
    val id: String?,
    val loginIp: String?,
    val operation: String?,
    val userId: String?,
    val userName: String?
)