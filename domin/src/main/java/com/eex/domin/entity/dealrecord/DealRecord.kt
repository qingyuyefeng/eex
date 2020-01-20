package com.eex.domin.entity.dealrecord

import java.math.BigDecimal

/**
 * @Description:
 * @Author: xq
 * @CreateDate: 2019/12/31 19:20
 */
data class DealRecord(
        val level: Int = -1,
        val msg: String = "",
        val cancelPos: Int = -1,
        val currentList: List<CurrentRecordBean> = ArrayList(),
        val stopLossList: List<StopLossBean> = ArrayList()
)

data class CurrentRecordBean(
        var index:Int = 0,
        var canClick:Boolean = true,
        var aveAmount: BigDecimal? = BigDecimal(0),
        var coinCode: String = "",
        var createTime: String = "",
        var dealType: Int = 0,
        var delAmount: BigDecimal = BigDecimal(0),
        var delNum: BigDecimal = BigDecimal(0),
        var delStatus: Int = 0,
        var delWay: Int = 0,
        var delsource: Int = 0,
        var fixPriceCoinCode: String = "",
        var id: String = "",
        var orderNo: String = "",
        var ratio: BigDecimal = BigDecimal(0),
        var residueNum: BigDecimal = BigDecimal(0),
        var serviceCharge: BigDecimal = BigDecimal(0),
        var tradeAmount: BigDecimal? = BigDecimal(0),
        var transactionNum: BigDecimal? = BigDecimal(0),
        var userId: String = "",
        var userName: String = ""

)

class StopLossBean {
    var coinCode: String = ""
    var createTime: String = ""
    var dealType: Int = 0
    var delAmount: BigDecimal = BigDecimal(0.0)
    var delNum: BigDecimal = BigDecimal(0.0)
    var fixPriceCoinCode: String = ""
    var orderNo: String = ""
    var ratio: BigDecimal = BigDecimal(0.0)
    var remark: String = ""
    var serviceCharge: BigDecimal = BigDecimal(0.0)
    var triggerPrice: BigDecimal = BigDecimal(0.0)
    var triggerState: Int = 0
    var triggerType: Int = 0
    var userName: String = ""
}