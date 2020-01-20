package com.eex.mvp.mine.tradingrecord.futuresorder

import com.eex.domin.entity.dealrecord.FuturesOrder
import com.eex.mvp.BasePresenter
import com.eex.mvp.BaseView

/**
 * @Description:
 * @Author: xq
 * @CreateDate: 2020/1/3 10:57
 */
class FuturesOrderCintract {
    interface View:BaseView{
        fun getDataSuccess(futures:FuturesOrder)

        fun cancelCurSuccess(futures:FuturesOrder)

        fun getNowDataSuccess(futures:FuturesOrder)

        fun closeOutSuccess(futures:FuturesOrder)

        fun overNightSuccess(futures:FuturesOrder)

        fun stopFullSuccess(futures:FuturesOrder)
    }
    interface Presenter:BasePresenter<View>
}