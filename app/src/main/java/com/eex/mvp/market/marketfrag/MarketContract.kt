package com.eex.mvp.market.marketfrag

import com.eex.mvp.BasePresenter
import com.eex.mvp.BaseView

/**
 * @Description:
 * @Author: xq
 * @CreateDate: 2019/12/16 15:17
 */
class MarketContract {
    interface View:BaseView{

    }
    interface Presenter:BasePresenter<View>
}