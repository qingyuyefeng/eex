package com.eex.mvp.mine.moneyaddress

import com.eex.domin.entity.moneyaddress.Address
import io.reactivex.Observable

/**
 * @Description:
 * @Author: xq
 * @CreateDate: 2019/12/20 10:45
 */
interface AddressRepo {
    fun getAddress():Observable<Address>

    fun deleteAddr(addrId:String): Observable<Address>
}