package com.eex.domin.entity.dealrecord

import java.math.BigDecimal

/**
 * @Description:
 * @Author: xq
 * @CreateDate: 2020/1/5 11:41
 */
data class LegalOrder(
        val level: Int = -1,
        val legalList: List<LegalOrderBean> = ArrayList()
)

data class LegalOrderBean(
        var userAccount: String = "",
        var id: String = "",
        var adUserId: String = "",
        var coinCode: String = "",
        var orderNo: String = "",
        var sellId: String = "",
        var sellOrderNo: String = "",
        var userId: String = "",
        var createTime: String = "",
        var dealNum: BigDecimal = BigDecimal(0),
        var price: BigDecimal = BigDecimal(0),
        var serviceFee: BigDecimal = BigDecimal(0),
        var state: Int = 0,
        var transactionType: Int = 0,
        var unitPrice: BigDecimal = BigDecimal(0),
        var margin: BigDecimal = BigDecimal(0),
        var userType: Int = 0,
        var payEndTime: Long = 0,
        var confirmTime: Long = 0,
        var currentTime: Long = 0
)