package com.eex.mvp.mine.security.googleverify.unbindgoogle

import com.eex.domin.entity.google.Google
import com.eex.mvp.BasePresenter
import com.eex.mvp.BaseView

/**
 * @Description:
 * @Author: xq
 * @CreateDate: 2019/12/17 14:01
 */
class UnbindGoogleContract {
    interface View:BaseView{
        fun unbindSuccess(google: Google)
    }
    interface Presenter:BasePresenter<View>
}