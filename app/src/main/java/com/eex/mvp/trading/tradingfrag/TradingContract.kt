package com.eex.mvp.trading.tradingfrag

import com.eex.mvp.BasePresenter
import com.eex.mvp.BaseView

/**
 * @Description:
 * @Author: xq
 * @CreateDate: 2019/12/16 15:17
 */
class TradingContract {
    interface View:BaseView{

    }
    interface Presenter:BasePresenter<View>
}