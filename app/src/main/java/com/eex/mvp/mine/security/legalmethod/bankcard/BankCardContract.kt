package com.eex.mvp.mine.security.legalmethod.bankcard

import com.eex.domin.entity.legalmethod.LegalMethod
import com.eex.mvp.BasePresenter
import com.eex.mvp.BaseView

/**
 * @Description:
 * @Author: xq
 * @CreateDate: 2019/12/23 9:39
 */
class BankCardContract {
    interface View:BaseView{
        fun getPhoneCodeSuccess(legalMethod: LegalMethod)
        fun getEmailCodeSuccess(legalMethod: LegalMethod)
        fun setBankCardSuccess(legalMethod: LegalMethod)
    }
    interface Presenter:BasePresenter<View>
}