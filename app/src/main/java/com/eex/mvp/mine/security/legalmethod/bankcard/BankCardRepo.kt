package com.eex.mvp.mine.security.legalmethod.bankcard

import com.eex.domin.entity.legalmethod.LegalMethod
import io.reactivex.Observable

/**
 * @Description:
 * @Author: xq
 * @CreateDate: 2019/12/25 17:44
 */
interface BankCardRepo {

    fun getPhoneCode(): Observable<LegalMethod>

    fun getEmailCode(): Observable<LegalMethod>

    fun setBankCard(userName: String, id: String, code: String, accountNo: String, bankName: String, childBankName: String, bankAddress: String): Observable<LegalMethod>

}