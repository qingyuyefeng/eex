package com.eex.mvp.mine.bankcards.addbankcard

import com.eex.apis.RetrofitService
import com.eex.constant.Constants
import com.eex.domin.entity.bankcards.BankCards
import com.eex.extensions.filterResult
import com.eex.mvp.mine.bankcards.BankCardsApi
import com.eex.mvp.mine.bankcards.BankCardsMapper1
import com.tencent.mmkv.MMKV
import io.reactivex.Observable
import javax.inject.Inject

/**
 * @Description:
 * @Author: xq
 * @CreateDate: 2019/12/7 20:14
 */
class AddBankRepoImpl @Inject constructor(
        private val service: RetrofitService,
        private val mmkv: MMKV
) : AddBankRepo {
    override fun addBankCard(bankName: String,
                             bankAddress: String,
                             bankChildName: String,
                             givename: String,
                             surname: String,
                             bankCardNo: String): Observable<BankCards> {
        return service.createApi(BankCardsApi::class.java)
                .addBankCard(mmkv.decodeString(Constants.TOKEN_ID,""),
                        bankName, bankAddress,
                        bankChildName, givename,
                        surname, bankCardNo)
                .filterResult()
                .map(BankCardsMapper1)
    }
}