package com.eex.mvp.usercenter.register

import com.eex.apis.RetrofitService
import com.eex.domin.entity.register.Register
import com.eex.extensions.filterResult
import io.reactivex.Observable
import javax.inject.Inject

/**
 * @Description:
 * @Author: xq
 * @CreateDate: 2019/11/29 14:18
 */
class RegisterRepoImpl @Inject constructor(
        private val service: RetrofitService
) : RegisterRepo {


    override fun sendPhoneCode(phone: String, imageCode: String, imageKey: String): Observable<Register> {
        return service.createApi(RegisterApi::class.java)
                .sendPhoneCode(phone, imageCode, imageKey)
                .filterResult()
                .map(RegisterMapper)
    }

    override fun sendEmailCode(email: String): Observable<Register> {
        return service.createApi(RegisterApi::class.java)
                .sendEmailCode(email)
                .filterResult()
                .map(RegisterMapper)
    }

    override fun getImageCode(): Observable<Register> {
        return service.createApi(RegisterApi::class.java)
                .getImageCode()
                .filterResult()
                .map(RegisterMapper1)
    }

    override fun register(username: String, password: String, code: String, invateCode: String): Observable<Register> {
        return service.createApi(RegisterApi::class.java)
                .register(username, password, code, invateCode)
                .filterResult()
                .map(RegisterMapper2)
    }
}