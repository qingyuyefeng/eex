package com.eex.mvp.trading.tradingfrag

import com.eex.R
import com.eex.domin.entity.trading.Trading
import com.eex.mvp.MVPBaseFragment

/**
 * @Description:
 * @Author: xq
 * @CreateDate: 2019/12/16 15:10
 */
class TradingFragment : MVPBaseFragment<Trading, TradingContract.View, TradingPresenter>(), TradingContract.View {
    override val layoutId: Int
        get() = R.layout.fragment_notice

}