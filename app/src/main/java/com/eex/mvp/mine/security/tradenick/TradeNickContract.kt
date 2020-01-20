package com.eex.mvp.mine.security.tradenick

import com.eex.domin.entity.trading.Trading
import com.eex.mvp.BasePresenter
import com.eex.mvp.BaseView

/**
 * @Description:
 * @Author: xq
 * @CreateDate: 2019/12/24 16:17
 */
class TradeNickContract {
    interface View:BaseView{
        fun setNickSuccess(trading: Trading)
    }
    interface Presenter:BasePresenter<View>
}