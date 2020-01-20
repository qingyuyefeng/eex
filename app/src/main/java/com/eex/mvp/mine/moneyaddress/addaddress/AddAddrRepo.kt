package com.eex.mvp.mine.moneyaddress.addaddress

import com.eex.domin.entity.moneyaddress.Address
import io.reactivex.Observable

/**
 * @Description:
 * @Author: xq
 * @CreateDate: 2019/12/20 11:31
 */
interface AddAddrRepo {
    fun getCoinType():Observable<Address>
    fun addAddr(coinCode: String, walletAddress: String,remark: String):Observable<Address>
}