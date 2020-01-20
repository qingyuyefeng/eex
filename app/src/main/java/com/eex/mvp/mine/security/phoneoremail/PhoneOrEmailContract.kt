package com.eex.mvp.mine.security.phoneoremail

import com.eex.domin.entity.bindphoneoremail.BindPE
import com.eex.mvp.BasePresenter
import com.eex.mvp.BaseView

/**
 * @Description:
 * @Author: xq
 * @CreateDate: 2019/12/12 15:55
 */
class PhoneOrEmailContract {
    interface View : BaseView {
        fun getCodeSuccess()
        fun dealSuccess(bindPE: BindPE)
    }

    interface Presenter : BasePresenter<View>
}