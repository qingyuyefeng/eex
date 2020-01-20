package com.eex.mvp.mine.security.legalmethod

import com.eex.apis.RetrofitService
import com.eex.constant.Constants
import com.eex.domin.entity.legalmethod.LegalMethod
import com.eex.extensions.filterResult
import com.tencent.mmkv.MMKV
import io.reactivex.Observable
import javax.inject.Inject

/**
 * @Description:
 * @Author: xq
 * @CreateDate: 2019/12/25 20:28
 */
class LegalMethodRepoImpl @Inject constructor(
        private val service: RetrofitService,
        private val mmkv: MMKV
) :LegalMethodRepo{
    override fun merchdealaccount(): Observable<LegalMethod> {
        return service.createApi(LegalMethodApi::class.java)
                .merchdealaccount(mmkv.decodeString(Constants.TOKEN_ID,""))
                .filterResult()
                .map(LegalMethodMapper1)
    }
}