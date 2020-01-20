package com.eex.mvp.mine.financialcenter

import com.eex.domin.entity.financialcenter.FinancialCenter
import com.eex.mvp.BasePresenter
import com.eex.mvp.BaseView

/**
 * @Description:
 * @Author: xq
 * @CreateDate: 2019/12/11 11:53
 */
class FinancialCenterContract {
    interface View : BaseView {
        fun getCoinsSuccess(financialCenter: FinancialCenter)
        fun getCycleTypesSuccess(financialCenter: FinancialCenter)
        fun getSettingsSuccess(financialCenter: FinancialCenter)
    }

    interface Presenter : BasePresenter<View>
}