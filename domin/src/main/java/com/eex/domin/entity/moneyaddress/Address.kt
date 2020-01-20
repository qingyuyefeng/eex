package com.eex.domin.entity.moneyaddress

import java.io.Serializable
import java.math.BigDecimal

/**
 * @Description:
 * @Author: xq
 * @CreateDate: 2019/12/20 10:13
 */
data class Address(
        val level: Int = -1,
        val msg: String = "",
        val deleteId: Int = -1,
        val addrList: List<AddressBean> = ArrayList(),
        val coinList: List<CoinBean> = ArrayList()

)

data class AddressList(
        val pageNo: String? = "",
        val pageSize: String? = "",
        val resultList: List<AddressBean>? = ArrayList(),
        val totalCount: Int = 0
)

data class AddressBean(
        val coinCode: String = "",
        val createTime: Long = 0,
        val createUser: String? = "",
        val deleteMark: Int = 0,
        val id: String = "",
        val remark: String? = "",
        val updateTime: String? = "",
        val updateUser: String? = "",
        val userId: String = "",
        val version: Int = 0,
        val walletAddress: String = ""
):Serializable

data class CoinBean(
        var id: String = "",
        var coinId: String = "",
        var coinCode: String = "",
        var imgUrl: String = "",
        var fixedRate: BigDecimal = BigDecimal(0),
        var currencyRate: BigDecimal = BigDecimal(0),
        var coinMax: BigDecimal = BigDecimal(0),
        var coinMin: BigDecimal = BigDecimal(0),
        var minNum: BigDecimal = BigDecimal(0),
        var maxNum: BigDecimal = BigDecimal(0)
)