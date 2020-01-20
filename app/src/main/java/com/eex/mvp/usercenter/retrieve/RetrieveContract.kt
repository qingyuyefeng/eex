package com.eex.mvp.usercenter.retrieve

import com.eex.domin.entity.retrieve.Retrieve
import com.eex.mvp.BasePresenter
import com.eex.mvp.BaseView

/**
 * @Description:
 * @Author: xq
 * @CreateDate: 2019/12/2 18:22
 */
class RetrieveContract {
    interface View: BaseView {
        fun getCodeSuccess(retrieve: Retrieve)
        fun nextSuccess(retrieve: Retrieve)
    }
    interface Presenter : BasePresenter<View>
}