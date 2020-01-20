package com.eex.mvp.usercenter.setnewpassword

import com.eex.domin.entity.retrieve.Retrieve
import com.eex.mvp.BasePresenter
import com.eex.mvp.BaseView

/**
 * @Description:
 * @Author: xq
 * @CreateDate: 2019/12/3 16:02
 */
class SetNewContract {
    interface View:BaseView{
        fun setNewSuccess(retrieve: Retrieve)
    }
    interface Presenter:BasePresenter<View>
}