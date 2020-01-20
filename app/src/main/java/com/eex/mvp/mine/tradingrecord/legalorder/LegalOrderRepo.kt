package com.eex.mvp.mine.tradingrecord.legalorder

import com.eex.domin.entity.dealrecord.LegalOrder
import io.reactivex.Observable

/**
 * @Description:
 * @Author: xq
 * @CreateDate: 2020/1/5 11:57
 */
interface LegalOrderRepo {
    fun getLegalOrder():Observable<LegalOrder>
}