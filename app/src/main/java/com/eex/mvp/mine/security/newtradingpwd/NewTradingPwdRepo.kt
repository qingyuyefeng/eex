package com.eex.mvp.mine.security.newtradingpwd

import com.eex.domin.entity.trading.Trading
import io.reactivex.Observable

/**
 * @Description:
 * @Author: xq
 * @CreateDate: 2019/12/24 15:24
 */
interface NewTradingPwdRepo {
    fun getPhoneCode(phone:String):Observable<Trading>

    fun updateAccount( newAccountPwd: String, code: String):Observable<Trading>
}