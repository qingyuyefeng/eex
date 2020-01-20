package com.eex.mvp.usercenter.register

import com.eex.domin.entity.register.Register
import com.eex.mvp.BasePresenter
import com.eex.mvp.BaseView

/**
 * @Description:
 * @Author: xq
 * @CreateDate: 2019/11/29 14:11
 */
class RegisterContract {
    interface View:BaseView{
        fun getImageCodeSuccess(register: Register)
        fun getCodeSuccess(register: Register)
        fun registerSuccess(register: Register)
    }
    interface Presenter :BasePresenter<View>
}