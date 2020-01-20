package com.eex.mvp.usercenter.register

import com.eex.domin.entity.register.Register
import io.reactivex.Observable

/**
 * @Description:
 * @Author: xq
 * @CreateDate: 2019/11/29 14:25
 */
interface RegisterRepo {

    fun getImageCode(): Observable<Register>

    fun sendPhoneCode(phone: String, imageCode: String, imageKey: String): Observable<Register>

    fun sendEmailCode(email: String): Observable<Register>

    fun register(username: String, password: String, code: String, invateCode: String): Observable<Register>
}