package com.eex.mvp.mine.security.googleverify.bindgoogle1

import com.eex.domin.entity.google.Google
import com.eex.mvp.BasePresenter
import com.eex.mvp.BaseView

/**
 * @Description:
 * @Author: xq
 * @CreateDate: 2019/12/16 17:18
 */
class GoogleContract {
    interface View:BaseView{
        fun getKeySuccess(google: Google)
    }
    interface Presenter:BasePresenter<View>
}