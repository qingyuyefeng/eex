package com.eex.mvp.usercenter.login

import com.eex.domin.entity.login.Login
import com.eex.mvp.BasePresenter
import com.eex.mvp.BaseView

/**
 * @Description:
 * @Author: xq
 * @CreateDate: 2019/11/25 14:42
 */
class LoginContract {
    interface View : BaseView {
        fun loginSuccess(login:Login)
        fun getCodeSuccess(login: Login)
    }

    interface Presenter : BasePresenter<View>
}