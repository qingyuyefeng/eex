package com.eex.mvp.mine.tradingrecord.futuresorder

import com.eex.apis.RetrofitService
import com.eex.constant.Constants
import com.eex.domin.entity.dealrecord.FuturesOrder
import com.eex.extensions.filterResult
import com.tencent.mmkv.MMKV
import io.reactivex.Observable
import javax.inject.Inject

/**
 * @Description:
 * @Author: xq
 * @CreateDate: 2020/1/3 15:03
 */
class FuturesOrderRepoImpl @Inject constructor(
        private val service: RetrofitService,
        private val mmkv: MMKV
) : FuturesOrderRepo {
    override fun getData(type: Int, delKey: String): Observable<FuturesOrder> {
        return service.createApi(FuturesOrderApi::class.java)
                .bonds(mmkv.decodeString(Constants.TOKEN_ID, ""), type = type, delKey = delKey)
                .filterResult()
                .map {
                    FuturesOrder(futuresList = it.data.resultList ?: ArrayList())
                }
    }

    override fun cancelFuture(id: String, coinCode: String, pricingCoin: String, delWay: String): Observable<FuturesOrder> {
        return service.createApi(FuturesOrderApi::class.java)
                .cancelFuture(mmkv.decodeString(Constants.TOKEN_ID, ""),
                        id, coinCode, pricingCoin, delWay)
                .filterResult()
                .map {
                    FuturesOrder(msg = it.msg)
                }
    }

    override fun getNowData(delkeys:String): Observable<FuturesOrder> {
        return service.createApi(FuturesOrderApi::class.java)
                .getNowData(delkeys)
                .filterResult()
                .map {
                    FuturesOrder(nowList = it.data?:ArrayList())
                }
    }

    override fun closeOut(delAmount: String, indexEntrustId: String): Observable<FuturesOrder> {
        return service.createApi(FuturesOrderApi::class.java)
                .closeOut(mmkv.decodeString(Constants.TOKEN_ID, ""),
                        delAmount, indexEntrustId)
                .filterResult()
                .map {
                    FuturesOrder(msg = it.msg)
                }
    }

    override fun overNight(id: String, coinCode: String, pricingCoin: String): Observable<FuturesOrder> {
        return service.createApi(FuturesOrderApi::class.java)
                .overNight(mmkv.decodeString(Constants.TOKEN_ID, ""),
                        id, coinCode, pricingCoin)
                .filterResult()
                .map {
                    FuturesOrder(msg = it.msg)
                }
    }

    override fun stopFull(id: String, targetProfit: String, targetProfitPrice: String, stopLoss: String, stopLossPrice: String): Observable<FuturesOrder> {
        return service.createApi(FuturesOrderApi::class.java)
                .stopFull(mmkv.decodeString(Constants.TOKEN_ID, ""),
                        id, targetProfit, targetProfitPrice, stopLoss, stopLossPrice)
                .filterResult()
                .map {
                    FuturesOrder(msg = it.msg)
                }
    }
}