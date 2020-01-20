package com.eex.mvp.mine.bankcards

import com.eex.domin.entity.bankcards.BankCards
import io.reactivex.Observable

/**
 * @Description:
 * @Author: xq
 * @CreateDate: 2019/12/6 14:49
 */
interface BankCardsRepo {
    fun getBankCards():Observable<BankCards>

    fun deleteBankcard(cardId:String):Observable<BankCards>
}