package com.eex.repository.impl

import com.eex.extensions.filterResult
import com.eex.apis.RetrofitService
import com.eex.apis.SecurityApi
import com.eex.constant.Constants
import com.eex.domin.entity.security.Security
import com.eex.repository.SecurityRepo
import com.eex.repository.mapper.SecurityMapper
import com.eex.repository.mapper.SecurityMapper1
import com.eex.repository.mapper.SecurityMapper2
import com.tencent.mmkv.MMKV
import io.reactivex.Observable
import javax.inject.Inject

class SecurityRepoImpl @Inject constructor(
        val service: RetrofitService,
        val mmkv: MMKV
) : SecurityRepo {
    override fun checkAuthStatus(): Observable<Security> =
            service.createApi(SecurityApi::class.java)
                    .authStauts(mmkv.decodeString(Constants.TOKEN_ID,""))
                    .filterResult()
                    .map(SecurityMapper)


    override fun getTradingType(): Observable<Security> {
        return service.createApi(SecurityApi::class.java)
                .getTradingType(mmkv.decodeString(Constants.TOKEN_ID,""))
                .filterResult()
                .map(SecurityMapper1)
    }

    override fun getTradingNick(): Observable<Security> {
        return service.createApi(SecurityApi::class.java)
                .getTradingNick(mmkv.decodeString(Constants.TOKEN_ID,""))
                .filterResult()
                .map(SecurityMapper2)
    }



}