package com.eex.mvp.usercenter.login

import com.eex.apis.RetrofitService
import com.eex.domin.entity.login.Login
import com.eex.extensions.filterResult
import com.tencent.mmkv.MMKV
import io.reactivex.Observable
import javax.inject.Inject

/**
 * @Description:
 * @Author: xq
 * @CreateDate: 2019/11/25 14:48
 */
class LoginRepoImpl @Inject constructor(
        private val service: RetrofitService,
        val mmkv: MMKV
) : LoginRepo {

    override fun loginOne(username: String, password: String): Observable<Login> {
        return service.createApi(LoginApi::class.java)
                .loginFirst(username, password)
                .filterResult()
                .map(LoginMapper)
    }

    override fun loginTwoPE(checkType: String, phoneoremail: String, code: String, username: String, password: String): Observable<Login> {
        return service.createApi(LoginApi::class.java)
                .loginSecondPE(checkType, phoneoremail, code, username, password)
                .filterResult()
                .map(LoginMapper)
    }
    override fun loginTwoGO(checkType: String, googleKey: String, code: String, username: String, password: String): Observable<Login> {
        return service.createApi(LoginApi::class.java)
                .loginSecondGO(checkType, googleKey, code, username, password)
                .filterResult()
                .map(LoginMapper)
    }

    override fun getPhoneCode(phone: String, userName: String): Observable<Login> {
        return service.createApi(LoginApi::class.java)
                .sendPhoneCode(phone, userName)
                .filterResult()
                .map(LoginMapper1)
    }

    override fun getEmailCode(email: String, userName: String): Observable<Login> {
        return service.createApi(LoginApi::class.java)
                .sendEmailCode(email,userName)
                .filterResult()
                .map(LoginMapper1)
    }
}