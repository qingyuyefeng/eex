package com.eex.mvp.mine.security.googleverify.bindgoogle2

import com.eex.domin.entity.google.Google
import com.eex.mvp.BasePresenter
import com.eex.mvp.BaseView

/**
 * @Description:
 * @Author: xq
 * @CreateDate: 2019/12/16 17:18
 */
class GoogleContract2 {
    interface View:BaseView{
        fun checkCodeSuccess(google: Google)
    }
    interface Presenter:BasePresenter<View>
}