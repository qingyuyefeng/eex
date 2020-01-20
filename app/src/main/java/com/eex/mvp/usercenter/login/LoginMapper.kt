package com.eex.mvp.usercenter.login

import com.eex.common.base.Data
import com.eex.domin.entity.login.Login
import io.reactivex.functions.Function

/**
 * @Description:
 * @Author: xq
 * @CreateDate: 2019/11/25 15:10
 */
object LoginMapper : Function<Data<LoginBean>, Login> {
    override fun apply(t: Data<LoginBean>): Login {
        return Login(
                msg = t.msg,
                userName = t.data.username?:"",
                phone = t.data.phone ?: "",
                googleState = t.data.googleState?.toString() ?: "",
                googleKey = t.data.googleKey?.toString() ?: "",
                tokenId = t.data.tokenId?.toString() ?: "",
                id = t.data.id?:"",
                email = t.data.email?.toString() ?: "",
                accountPassWord = t.data.accountPassWord?.toString() ?: "",
                createTime = t.data.createTime?.toString() ?: "",
                ctcDealState = t.data.ctcDealState?.toString() ?: "",
                examState = t.data.examState?.toString() ?: "",
                hasEmail = t.data.hasEmail,
                hasPhone = t.data.hasPhone,
                invateCode = t.data.invateCode?:"",
                invateUser = t.data.invateUser?.toString() ?: "",
                inviteUser = t.data.inviteUser?.toString() ?: "",
                lastLoginIp = t.data.lastLoginIp?.toString() ?: "",
                merchant = t.data.merchant,
                openId = t.data.openId?.toString() ?: "",
                passWord = t.data.password?:"",
                realName = t.data.realName,
                salt = t.data.salt?:"",
                status = t.data.status,
                userCode = t.data.userCode?:"",
                loginIp = t.data.logDTO.loginIp?:"",
                operation = t.data.logDTO.operation?:"",
                userId = t.data.logDTO.userId?:""
        )
    }
}

object LoginMapper1 : Function<Data<*>, Login> {
    override fun apply(t: Data<*>): Login {
        return Login(
                msg = t.msg
        )
    }
}