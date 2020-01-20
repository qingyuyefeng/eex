package com.eex.domin.entity.mine

import java.math.BigDecimal

/**
 * @Description:
 * @Author: xq
 * @CreateDate: 2019/12/5 9:54
 */
data class Mine(
        val level:Int = -1,
        val userName: String = "",
        val merchantStatus: Int = 0,
        val bean:MineBean = MineBean()
)

data class MineBean(
        val frozenMoneyCny: BigDecimal? = null,
        val totalMoneyBtc: BigDecimal? = null,
        val totalMoneyCny: BigDecimal? = null,
        val totalMoneyUsdt: BigDecimal? = null,
        val useMoneyCny: BigDecimal? = null,
        val userCoinAccount: List<UserCoinAccount>? = ArrayList(),
        val userId: String? = ""
)

data class UserCoinAccount(
        var coinCode: String? = "",
        var coinName: String? = "",
        var imgUrl: String? = "",
        var coinMoney: BigDecimal? = null,
        var totalMoeny: BigDecimal? = null,
        var frozenMoney: BigDecimal? = null
)