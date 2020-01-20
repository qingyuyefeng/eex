package com.eex.mvp.market.marketfrag

import android.os.Bundle
import com.eex.domin.entity.market.Market
import com.eex.mvp.BasePresenterImpl
import javax.inject.Inject

/**
 * @Description:
 * @Author: xq
 * @CreateDate: 2019/12/16 15:21
 */
class MarketPresenter @Inject constructor(

) :BasePresenterImpl<MarketContract.View,Market>(), MarketContract.Presenter{
    override fun handleViewState(vo: Market) {
    }

    override fun initPageState(params: Bundle?) {
    }

}