package com.eex.mvp.mine.security.phoneoremail

import com.eex.apis.RetrofitService
import com.eex.constant.Constants
import com.eex.domin.entity.bindphoneoremail.BindPE
import com.eex.extensions.filterResult
import com.tencent.mmkv.MMKV
import io.reactivex.Observable
import javax.inject.Inject

/**
 * @Description:
 * @Author: xq
 * @CreateDate: 2019/12/12 16:53
 */
class BindsRepoImpl @Inject constructor(
        private val service: RetrofitService,
        private val mmkv: MMKV
) : BindsRepo {

    override fun getPhoneCode(phone: String, sendType: String): Observable<BindPE> {
        return service.createApi(BindsApi::class.java)
                .getCode(mmkv.decodeString(Constants.TOKEN_ID, ""), phone, sendType)
                .filterResult()
                .map(BindsMapper)
    }

    override fun getEmailCode(email: String, sendType: String): Observable<BindPE> {
        return service.createApi(BindsApi::class.java)
                .gainCode(mmkv.decodeString(Constants.TOKEN_ID, ""), email, sendType)
                .filterResult()
                .map(BindsMapper)
    }

    override fun bindPhone(mobilePhone: String, code: String): Observable<BindPE> {
        return service.createApi(BindsApi::class.java)
                .bindPhone(mmkv.decodeString(Constants.TOKEN_ID, ""),
                        mmkv.decodeString(Constants.USERNAME, ""),
                        mobilePhone, code)
                .filterResult()
                .map(BindsMapper)
    }

    override fun unbindPhone(mobilePhone: String, code: String): Observable<BindPE> {
        return service.createApi(BindsApi::class.java)
                .unbindPhone(mmkv.decodeString(Constants.TOKEN_ID, ""),
                        mmkv.decodeString(Constants.USERNAME, ""),
                        mobilePhone, code)
                .filterResult()
                .map(BindsMapper)
    }

    override fun bindEmail(email: String, code: String): Observable<BindPE> {
        return service.createApi(BindsApi::class.java)
                .bindEmail(mmkv.decodeString(Constants.TOKEN_ID, ""),
                        email, code)
                .filterResult()
                .map(BindsMapper)
    }

    override fun unbindEmail(email: String, code: String): Observable<BindPE> {
        return service.createApi(BindsApi::class.java)
                .unbindEmail(mmkv.decodeString(Constants.TOKEN_ID, ""),
                        email, code)
                .filterResult()
                .map(BindsMapper)
    }
}