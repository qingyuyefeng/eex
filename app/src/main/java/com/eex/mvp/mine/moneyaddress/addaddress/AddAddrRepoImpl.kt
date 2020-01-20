package com.eex.mvp.mine.moneyaddress.addaddress

import com.eex.apis.RetrofitService
import com.eex.constant.Constants
import com.eex.domin.entity.moneyaddress.Address
import com.eex.extensions.filterResult
import com.eex.mvp.mine.moneyaddress.AddAddrMapper
import com.eex.mvp.mine.moneyaddress.AddAddrMapper1
import com.eex.mvp.mine.moneyaddress.AddressApi
import com.tencent.mmkv.MMKV
import io.reactivex.Observable
import javax.inject.Inject

/**
 * @Description:
 * @Author: xq
 * @CreateDate: 2019/12/20 11:46
 */
class AddAddrRepoImpl @Inject constructor(
        private val service: RetrofitService,
        private val mmkv: MMKV
) :AddAddrRepo{

    override fun getCoinType(): Observable<Address> {
        return service.createApi(AddressApi::class.java)
                .getCions(mmkv.decodeString(Constants.TOKEN_ID,""),"0")
                .filterResult()
                .map(AddAddrMapper)
    }
    override fun addAddr(coinCode: String,walletAddress: String,remark: String): Observable<Address> {
        return service.createApi(AddressApi::class.java)
                .addCoinAddress(mmkv.decodeString(Constants.TOKEN_ID,""),coinCode, walletAddress,remark)
                .filterResult()
                .map(AddAddrMapper1)
    }
}