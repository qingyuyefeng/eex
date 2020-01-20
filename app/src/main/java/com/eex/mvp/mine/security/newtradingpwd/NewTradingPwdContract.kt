package com.eex.mvp.mine.security.newtradingpwd

import com.eex.domin.entity.trading.Trading
import com.eex.mvp.BasePresenter
import com.eex.mvp.BaseView

/**
 * @Description:
 * @Author: xq
 * @CreateDate: 2019/12/24 14:18
 */
class NewTradingPwdContract {
    interface View:BaseView{
        fun getphoneCodeSuccess(trading: Trading)
        fun setPwdSuccess(trading: Trading)
    }
    interface Presenter:BasePresenter<View>
}