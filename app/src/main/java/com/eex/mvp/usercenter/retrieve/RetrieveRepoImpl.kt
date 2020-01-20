package com.eex.mvp.usercenter.retrieve

import com.eex.apis.RetrofitService
import com.eex.domin.entity.retrieve.Retrieve
import com.eex.extensions.filterResult
import io.reactivex.Observable
import javax.inject.Inject

/**
 * @Description:
 * @Author: xq
 * @CreateDate: 2019/12/2 18:17
 */
class RetrieveRepoImpl @Inject constructor(
        private val service: RetrofitService
) : RetrieveRepo{

    override fun getEmailCode(email: String): Observable<Retrieve> {
        return service.createApi(RetrieveApi::class.java)
                .sendEmailCode(email)
                .filterResult()
                .map(RetrieveMapper)
    }

    override fun getPhoneCode(phone: String): Observable<Retrieve> {
        return service.createApi(RetrieveApi::class.java)
                .sendPhoneCode(phone)
                .filterResult()
                .map(RetrieveMapper)
    }

    override fun checkCode(phoneoremail: String, code: String, checkType: String): Observable<Retrieve> {
        return service.createApi(RetrieveApi::class.java)
                .checkCode(phoneoremail, code, checkType)
                .filterResult()
                .map(RetrieveMapper)
    }
}