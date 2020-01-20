package com.eex.mvp.mine.userinfo

import com.eex.domin.entity.security.Security
import com.eex.mvp.BasePresenter
import com.eex.mvp.BaseView

/**
 * @Description:
 * @Author: xq
 * @CreateDate: 2020/1/2 15:29
 */
class UserInfoContract {
    interface View:BaseView{
        fun getInfoSuccess(security: Security)
    }
    interface Presenter:BasePresenter<View>
}