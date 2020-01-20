package com.eex.domin.entity.dealrecord

import java.io.Serializable
import java.math.BigDecimal

/**
 * @Description:
 * @Author: xq
 * @CreateDate: 2020/1/3 15:17
 */
data class FuturesOrder(
        val level: Int = -1,
        val msg: String = "",
        val canclePos: Int = -1,
        val futuresList: List<FuturesOrderBean> = ArrayList(),
        val nowList:List<NowData> = ArrayList()
)

data class FuturesOrderBean(  //data class IndexEntrustDTO
        var index: Int = 0,
        var nowPrice: Double? = 0.0,
        var lastPrice: Double? =0.0,
        var cny: BigDecimal? = BigDecimal(0),
        var aveAmount: Double = 0.0,
        var bondsNight: Int = 0,
        var coin: Int = 0,
        var coinCode: String = "",
        var createTime: Long = 0,
        var dealType: Int = 0,
        var delAmount: Double = 0.0,
        var delNum: Double = 0.0,
        var delStatus: Int = 0,
        var delWay: Int = 0,
        var delkey: String = "",
        var delsource: Int = 0,
        var earnestMoney: Double = 0.0,
        var id: String = "",
        var lever: Int = 0,
        var nightMoney: Double = 0.0,
        var opAvgPrice: Double = 0.0,
        var opCondition: Int = 0,
        var opPrice: Double = 0.0,
        var opResidueNum: Double = 0.0,
        var opStatus: Int = 0,
        var priceReservation: Int = 0,
        var pricingCoin: String = "",
        var profitLoss: Double = 0.0,
        var quantityRetention: Int = 0,
        var ratio: Double = 0.0,
        var residueNum: Double = 0.0,
        var serviceCharge: Double = 0.0,
        var stopLoss: Double = 0.0,
        var stopLossPrice: Double = 0.0,
        var targetProfit: Double = 0.0,
        var targetProfitPrice: Double = 0.0,
        var updateTime: Long = 0,
        var userId: String = "",
        var userName: String = "",
        var opTime: Long = 0,
        var opType: Int = 0,
        var actualServiceCharge: Double = 0.0,
        var realName: Any? = null
):Serializable

data class NowData(
        var delKey: String? = null,
        var newPrice: BigDecimal? = null,
        var dealNum: BigDecimal? = null,
        var higePrice: BigDecimal? = null,
        var fooPrice: BigDecimal? = null,
        var lastPrice: BigDecimal? = null,
        var cny: BigDecimal? = null,
        var usdt: BigDecimal? = null,
        var usdtCny: BigDecimal? = null
)