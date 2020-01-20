package com.eex.mvp.mine.tradingrecord.coinmoney

import com.eex.apis.RetrofitService
import com.eex.constant.Constants
import com.eex.domin.entity.dealrecord.DealRecord
import com.eex.extensions.filterResult
import com.tencent.mmkv.MMKV
import io.reactivex.Observable
import javax.inject.Inject

/**
 * @Description:
 * @Author: xq
 * @CreateDate: 2019/12/31 19:29
 */
class CoinMoneyRepoImpl @Inject constructor(
        private val service: RetrofitService,
        private val mmkv: MMKV
) : CoinMoneyRepo {
    override fun getCurrentData(pageNo: Int, pageSize: Int, delStatus: String): Observable<DealRecord> {
        return service.createApi(CoinMoneyApi::class.java)
                .getCurrentData(apikey = mmkv.decodeString(Constants.TOKEN_ID, ""),
                        pageNo = pageNo,
                        pageSize = pageSize,
                        delStatus = delStatus)
                .filterResult()
                .map(CoinMoneyMapper)
    }

    override fun cancelDelegation(order: String, coinCode: String, fixPriceCoinCode: String): Observable<DealRecord> {
        return service.createApi(CoinMoneyApi::class.java)
                .cancelDelegation(mmkv.decodeString(Constants.TOKEN_ID, ""),
                        order, coinCode, fixPriceCoinCode)
                .filterResult()
                .map(CoinMoneyMapper1)
    }

    override fun getStopLossData(pageNo: Int, pageSize: Int): Observable<DealRecord> {
        return service.createApi(CoinMoneyApi::class.java)
                .getStopLoss(mmkv.decodeString(Constants.TOKEN_ID, ""),
                        pageNo, pageSize)
                .filterResult()
                .map(CoinMoneyMapper2)
    }

    override fun cancelStoploss(orderNo: String): Observable<DealRecord> {
        return service.createApi(CoinMoneyApi::class.java)
                .cancelStoploss(mmkv.decodeString(Constants.TOKEN_ID, ""), orderNo)
                .filterResult()
                .map(CoinMoneyMapper1)
    }
}