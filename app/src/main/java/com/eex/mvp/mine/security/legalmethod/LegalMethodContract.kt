package com.eex.mvp.mine.security.legalmethod

import com.eex.domin.entity.legalmethod.LegalMethod
import com.eex.mvp.BasePresenter
import com.eex.mvp.BaseView

/**
 * @Description:
 * @Author: xq
 * @CreateDate: 2019/12/25 20:01
 */
class LegalMethodContract {
    interface View: BaseView {
        fun getMethodAccountSuccess(legalMethod: LegalMethod)
    }
    interface Presenter:BasePresenter<View>
}