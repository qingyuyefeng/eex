package com.eex.mvp.mine.security.newtradingpwd

import com.eex.apis.RetrofitService
import com.eex.constant.Constants
import com.eex.domin.entity.trading.Trading
import com.eex.extensions.filterResult
import com.tencent.mmkv.MMKV
import io.reactivex.Observable
import javax.inject.Inject

/**
 * @Description:
 * @Author: xq
 * @CreateDate: 2019/12/24 15:26
 */
class NewTradingPwdRepoImpl @Inject constructor(
    private val service: RetrofitService,
    private val mmkv: MMKV
) :NewTradingPwdRepo{
    override fun getPhoneCode(phone: String): Observable<Trading> {
        return service.createApi(NewTradingPwdApi::class.java)
                .phoneCode(apikey = mmkv.decodeString(Constants.TOKEN_ID,""),
                        phone = phone,
                        userName = mmkv.decodeString(Constants.USERNAME,""))
                .filterResult()
                .map(NewTradingPwdMapper)
    }


    override fun updateAccount( newAccountPwd: String,code: String): Observable<Trading> {
        return service.createApi(NewTradingPwdApi::class.java)
                .updateAccount(mmkv.decodeString(Constants.TOKEN_ID,""),newAccountPwd, code)
                .filterResult()
                .map(NewTradingPwdMapper)
    }

}