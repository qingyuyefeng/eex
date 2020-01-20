package com.eex.mvp.mine.tradingrecord.coinmoney

import com.eex.domin.entity.dealrecord.DealRecord
import com.eex.mvp.BasePresenter
import com.eex.mvp.BaseView

/**
 * @Description:
 * @Author: xq
 * @CreateDate: 2019/12/31 16:37
 */
class CoinMoneyContract {
    interface View:BaseView{
        fun getDataSuccess(deal: DealRecord)
        fun cancelSuccess(deal: DealRecord)
        fun getLossDataSuccess(deal: DealRecord)
    }
    interface Presenter:BasePresenter<View>
}