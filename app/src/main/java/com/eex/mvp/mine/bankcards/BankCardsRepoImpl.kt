package com.eex.mvp.mine.bankcards

import com.eex.apis.RetrofitService
import com.eex.constant.Constants
import com.eex.domin.entity.bankcards.BankCards
import com.eex.extensions.filterResult
import com.tencent.mmkv.MMKV
import io.reactivex.Observable
import javax.inject.Inject

/**
 * @Description:
 * @Author: xq
 * @CreateDate: 2019/12/6 15:04
 */
class BankCardsRepoImpl @Inject constructor(
        private val service: RetrofitService,
        private val mmkv: MMKV
) :BankCardsRepo{
    override fun getBankCards(): Observable<BankCards> {
        return service.createApi(BankCardsApi::class.java)
                .getBankCards(mmkv.decodeString(Constants.TOKEN_ID,""))
                .filterResult()
                .map(BankCardsMapper)
    }

    override fun deleteBankcard(cardId: String): Observable<BankCards> {
        return service.createApi(BankCardsApi::class.java)
                .deleteBankCard(mmkv.decodeString(Constants.TOKEN_ID,""),cardId)
                .filterResult()
                .map(BankCardsMapper1)
    }
}