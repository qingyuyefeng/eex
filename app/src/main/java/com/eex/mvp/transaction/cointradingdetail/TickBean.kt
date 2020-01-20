package com.eex.mvp.transaction.cointradingdetail

/**
 * @Description:
 * @Author: xq
 * @CreateDate: 2020/1/19 17:11
 */

data class TickBean(
    var delKey: String?,
    var listDelegation: List<Any>?,
    var listOrder: List<Any>?,
    var buy: List<BuyOrSell>?,
    var sell: List<BuyOrSell>?,
    var usdtCny: Double?
)

data class BuyOrSell(
    var delAmount: Double,
    var residueNum: Double
)

