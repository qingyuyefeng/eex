package com.eex.mvp.mine.security.legalmethod.alipayorwechat

import com.eex.domin.entity.legalmethod.LegalMethod
import com.eex.mvp.BasePresenter
import com.eex.mvp.BaseView

/**
 * @Description:
 * @Author: xq
 * @CreateDate: 2019/12/23 10:11
 */
class AlipayOrWechatContract {
    interface View:BaseView{
        fun getCodeSuccess(legalMethod: LegalMethod)
        fun uploadPicSuccess(legalMethod: LegalMethod)
        fun setMethodSuccess(legalMethod: LegalMethod)
    }
    interface Presenter:BasePresenter<View>
}