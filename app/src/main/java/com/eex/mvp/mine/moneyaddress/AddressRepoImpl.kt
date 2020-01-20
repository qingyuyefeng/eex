package com.eex.mvp.mine.moneyaddress

import com.eex.apis.RetrofitService
import com.eex.constant.Constants
import com.eex.domin.entity.moneyaddress.Address
import com.eex.extensions.filterResult
import com.tencent.mmkv.MMKV
import io.reactivex.Observable
import javax.inject.Inject

/**
 * @Description:
 * @Author: xq
 * @CreateDate: 2019/12/20 11:17
 */
class AddressRepoImpl @Inject constructor(
        private val service: RetrofitService,
        private val mmkv: MMKV
) :AddressRepo{
    override fun getAddress(): Observable<Address> {
        return service.createApi(AddressApi::class.java)
                .addressList(mmkv.decodeString(Constants.TOKEN_ID,""))
                .filterResult()
                .map(AddressMapper)
    }
    override fun deleteAddr(addrId: String): Observable<Address> {
        return service.createApi(AddressApi::class.java)
                .deleteAddress(mmkv.decodeString(Constants.TOKEN_ID,""),addrId)
                .filterResult()
                .map(AddAddrMapper1)
    }
}