package com.eex.mvp.mine.security.nrewpassword

import com.eex.common.base.Data
import com.eex.mvp.BasePresenter
import com.eex.mvp.BaseView

/**
 * @Description:
 * @Author: xq
 * @CreateDate: 2019/12/13 11:48
 */
class NewPwdContract {
    interface View :BaseView{
        fun setNewPwdSuccess(data:Data<Any>)
    }
    interface Presenter:BasePresenter<View>
}