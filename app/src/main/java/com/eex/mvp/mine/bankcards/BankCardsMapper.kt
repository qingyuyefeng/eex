package com.eex.mvp.mine.bankcards

import com.eex.common.base.Data
import com.eex.domin.entity.bankcards.BankCardBean
import com.eex.domin.entity.bankcards.BankCards
import io.reactivex.functions.Function

/**
 * @Description:
 * @Author: xq
 * @CreateDate: 2019/12/6 15:07
 */
object BankCardsMapper : Function<Data<List<BankCardBean>>, BankCards> {
    override fun apply(t: Data<List<BankCardBean>>): BankCards {
        return BankCards(cardsList = t.data)
    }
}

object BankCardsMapper1 : Function<Data<Any>, BankCards> {
    override fun apply(t: Data<Any>): BankCards {
        return BankCards(
                msg = t.msg
        )
    }
}