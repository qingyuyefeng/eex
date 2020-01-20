package com.eex.mvp.mine.tradingrecord.futuresorder

import com.eex.domin.entity.dealrecord.FuturesOrder
import io.reactivex.Observable

/**
 * @Description:
 * @Author: xq
 * @CreateDate: 2020/1/3 10:58
 */
interface FuturesOrderRepo {
    fun getData(type: Int, delKey: String): Observable<FuturesOrder>
    fun cancelFuture(id: String, coinCode: String, pricingCoin: String, delWay: String): Observable<FuturesOrder>

    fun getNowData(delkeys: String): Observable<FuturesOrder>

    fun closeOut(delAmount: String, indexEntrustId: String): Observable<FuturesOrder>

    fun overNight( id: String,coinCode: String, pricingCoin: String): Observable<FuturesOrder>

    fun stopFull( id: String,targetProfit: String,targetProfitPrice: String,stopLoss: String, stopLossPrice: String): Observable<FuturesOrder>
}