package com.eex.mvp.mine.tradingrecord.coinmoney

import com.eex.domin.entity.dealrecord.DealRecord
import io.reactivex.Observable

/**
 * @Description:
 * @Author: xq
 * @CreateDate: 2019/12/31 19:26
 */
interface CoinMoneyRepo {
    fun getCurrentData(pageNo: Int, pageSize: Int, delStatus: String): Observable<DealRecord>

    fun cancelDelegation(order: String, coinCode: String, fixPriceCoinCode: String): Observable<DealRecord>

    fun getStopLossData(pageNo: Int, pageSize: Int): Observable<DealRecord>

    fun cancelStoploss(orderNo: String): Observable<DealRecord>
}