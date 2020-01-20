package com.eex.mvp.mine.tradingrecord.legalorder

import com.eex.domin.entity.dealrecord.LegalOrder
import com.eex.mvp.BasePresenter
import com.eex.mvp.BaseView

/**
 * @Description:
 * @Author: xq
 * @CreateDate: 2020/1/5 11:38
 */
class LegalOrderContract {
    interface View:BaseView{
        fun getDataSuccess(legalOrder: LegalOrder)
    }
    interface Presenter:BasePresenter<View>
}