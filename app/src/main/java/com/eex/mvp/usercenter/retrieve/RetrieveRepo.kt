package com.eex.mvp.usercenter.retrieve

import com.eex.domin.entity.retrieve.Retrieve
import io.reactivex.Observable

/**
 * @Description:
 * @Author: xq
 * @CreateDate: 2019/12/2 18:13
 */
interface RetrieveRepo {

    fun getEmailCode(email: String): Observable<Retrieve>

    fun getPhoneCode(phone: String): Observable<Retrieve>

    fun checkCode(phoneoremail: String, code: String, checkType: String): Observable<Retrieve>

}