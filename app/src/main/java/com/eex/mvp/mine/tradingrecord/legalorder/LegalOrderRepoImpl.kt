package com.eex.mvp.mine.tradingrecord.legalorder

import com.eex.apis.RetrofitService
import com.eex.constant.Constants
import com.eex.domin.entity.dealrecord.LegalOrder
import com.eex.extensions.filterResult
import com.tencent.mmkv.MMKV
import io.reactivex.Observable
import javax.inject.Inject

/**
 * @Description:
 * @Author: xq
 * @CreateDate: 2020/1/5 11:59
 */
class LegalOrderRepoImpl @Inject constructor(
        private val service: RetrofitService,
        private val mmkv: MMKV
) : LegalOrderRepo {
    override fun getLegalOrder(): Observable<LegalOrder> {
        return service.createApi(LegalOrderApi::class.java)
                .getOrderDetailList(mmkv.decodeString(Constants.TOKEN_ID,""))
                .filterResult()
                .map {
                    LegalOrder(legalList = it.data?.resultList?:ArrayList())
                }
    }
}