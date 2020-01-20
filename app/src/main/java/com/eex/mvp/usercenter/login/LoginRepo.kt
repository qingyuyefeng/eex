package com.eex.mvp.usercenter.login

import com.eex.domin.entity.login.Login
import io.reactivex.Observable

/**
 * @Description:
 * @Author: xq
 * @CreateDate: 2019/11/25 14:48
 */
interface LoginRepo {
    fun loginOne(username: String, password: String): Observable<Login>

    fun loginTwoPE(checkType: String, phoneoremail: String, code: String, username: String, password: String): Observable<Login>

    fun loginTwoGO(checkType: String, googleKey: String, code: String, username: String, password: String): Observable<Login>

    fun getPhoneCode(phone: String, userName: String): Observable<Login>

    fun getEmailCode(email: String, userName: String): Observable<Login>
}