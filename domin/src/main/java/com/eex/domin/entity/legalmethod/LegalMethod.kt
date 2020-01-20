package com.eex.domin.entity.legalmethod

import android.graphics.Bitmap

/**
 * @Description:
 * @Author: xq
 * @CreateDate: 2019/12/23 9:56
 */
data class LegalMethod(
        val level: Int = -1,
        val msg: String = "",
        val listAccount: List<MerchdAccount> = ArrayList(),
        val picName: String = "",
        val bitmap: Bitmap? = null
)

data class MerchdAccount(
        var accountNo: String = "",
        var accountType: Int = 0,
        var bankAddress: String = "",
        var bankName: String = "",
        var childBankName: String = "",
        var id: String = "",
        var imageUrl: String = "",
        var userName: String = ""
)