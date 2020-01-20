package com.eex.mvp.mine.security.phoneoremail

import com.eex.domin.entity.bindphoneoremail.BindPE
import io.reactivex.Observable

/**
 * @Description:
 * @Author: xq
 * @CreateDate: 2019/12/12 16:52
 */
interface BindsRepo {
    fun getPhoneCode(phone: String, sendType: String): Observable<BindPE>

    fun getEmailCode(email: String, sendType: String): Observable<BindPE>

    fun bindPhone(mobilePhone: String, code: String): Observable<BindPE>

    fun unbindPhone(mobilePhone: String, code: String): Observable<BindPE>

    fun bindEmail(email: String, code: String): Observable<BindPE>

    fun unbindEmail(email: String, code: String): Observable<BindPE>
}