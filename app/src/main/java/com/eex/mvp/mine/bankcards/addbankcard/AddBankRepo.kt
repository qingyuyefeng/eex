package com.eex.mvp.mine.bankcards.addbankcard

import com.eex.domin.entity.bankcards.BankCards
import io.reactivex.Observable

/**
 * @Description:
 * @Author: xq
 * @CreateDate: 2019/12/7 20:15
 */
interface AddBankRepo {
    fun addBankCard(bankName: String,
                    bankAddress: String,
                    bankChildName: String,
                    givename: String,
                    surname: String,
                    bankCardNo: String): Observable<BankCards>
}