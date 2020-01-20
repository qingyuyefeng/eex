package com.eex.mvp.mine.security.newtradingpwd

import com.eex.common.base.Data
import com.eex.domin.entity.trading.Trading
import io.reactivex.functions.Function

/**
 * @Description:
 * @Author: xq
 * @CreateDate: 2019/12/24 15:32
 */
object NewTradingPwdMapper:Function<Data<Any>,Trading> {
    override fun apply(t: Data<Any>): Trading {
        return Trading(
                msg = t.msg
        )
    }
}