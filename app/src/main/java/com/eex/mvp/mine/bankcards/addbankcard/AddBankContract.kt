package com.eex.mvp.mine.bankcards.addbankcard

import com.eex.domin.entity.bankcards.BankCards
import com.eex.mvp.BasePresenter
import com.eex.mvp.BaseView

/**
 * @Description:
 * @Author: xq
 * @CreateDate: 2019/12/7 13:20
 */
class AddBankContract {
    interface View: BaseView {
        fun addBankSuccess(bankCards: BankCards)
    }
    interface Presenter :BasePresenter<View>
}