package com.eex.mvp.mine.bankcards

import com.eex.domin.entity.bankcards.BankCards
import com.eex.mvp.BasePresenter
import com.eex.mvp.BaseView

/**
 * @Description:
 * @Author: xq
 * @CreateDate: 2019/12/6 14:44
 */
class BankCardsContract {
    interface View:BaseView{
        fun getCardsSuccess(bankCards: BankCards)
        fun deleteSuccess(bankCards: BankCards)
    }
    interface Presenter:BasePresenter<View>
}