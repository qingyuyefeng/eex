package com.eex.mvp.trading.tradingfrag


import android.os.Bundle
import com.eex.domin.entity.trading.Trading
import com.eex.mvp.BasePresenterImpl
import javax.inject.Inject

/**
 * @Description:
 * @Author: xq
 * @CreateDate: 2019/12/16 15:21
 */
class TradingPresenter @Inject constructor(

) : BasePresenterImpl<TradingContract.View, Trading>(), TradingContract.Presenter {
    override fun handleViewState(vo: Trading) {
    }

    override fun initPageState(params: Bundle?) {
    }

}