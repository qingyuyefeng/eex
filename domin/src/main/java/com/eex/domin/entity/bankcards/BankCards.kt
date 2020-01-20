package com.eex.domin.entity.bankcards

import java.io.Serializable

/**
 * @Description:
 * @Author: xq
 * @CreateDate: 2019/12/6 14:47
 */
data class BankCards(
        val level:Int = 0,
        val msg:String = "",
        val deleteId:Int = -1,
        val cardsList: List<BankCardBean> = ArrayList()
)
data class BankCardBean (
    val surname: String = "",
    val givename: String = "",
    val bankCardNo: String = "",
    val bankName: String = "",
    val bankAddress: String = "",
    val bankChildName: String = "",
    val id: String = ""
):Serializable