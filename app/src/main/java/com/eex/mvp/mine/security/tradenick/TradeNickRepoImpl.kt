package com.eex.mvp.mine.security.tradenick

import com.eex.apis.RetrofitService
import com.eex.constant.Constants
import com.eex.domin.entity.trading.Trading
import com.eex.extensions.filterResult
import com.eex.mvp.mine.security.newtradingpwd.NewTradingPwdMapper
import com.tencent.mmkv.MMKV
import io.reactivex.Observable
import javax.inject.Inject

/**
 * @Description:
 * @Author: xq
 * @CreateDate: 2019/12/24 16:22
 */
class TradeNickRepoImpl @Inject constructor(
        private val service: RetrofitService,
        private val mmkv: MMKV
) :TradeNickRepo{
    override fun setNick(nick: String): Observable<Trading> {
        return service.createApi(TradeNickApi::class.java)
                .setNickName(mmkv.decodeString(Constants.TOKEN_ID,""),nick)
                .filterResult()
                .map(NewTradingPwdMapper)
    }
}