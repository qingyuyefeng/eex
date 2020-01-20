package com.eex.mvp.mine.security.tradenick

import com.eex.domin.entity.trading.Trading
import io.reactivex.Observable

/**
 * @Description:
 * @Author: xq
 * @CreateDate: 2019/12/24 16:21
 */
interface TradeNickRepo {
    fun setNick(nick:String): Observable<Trading>
}