package com.eex.mvp.mine.moneyaddress

import com.eex.common.base.Data
import com.eex.domin.entity.moneyaddress.AddressList
import com.eex.domin.entity.moneyaddress.CoinBean
import io.reactivex.Observable
import retrofit2.http.*

/**
 * @Description:
 * @Author: xq
 * @CreateDate: 2019/12/20 11:20
 */
interface AddressApi {

    /**
     * 获取钱包地址接口
     */
//    requestParam.put("pageSize", size.toString())
//    requestParam.put("pageNo", p.toString())
    @FormUrlEncoded
    @POST("user/coinaddress/list-coin-address-page")
    fun addressList(@Header("authorization") apikey: String,
                    @Field("pageSize") pageSize: String = "10",
                    @Field("pageNo") pageNo: String = "1"): Observable<Data<AddressList>>

    /**
     * 删除币账户地址接口
     *
     * @param hashMap
     * @return
     */
//    hashMap.put("id", memactivity_uncompleted_list.get(position).getId())
    @FormUrlEncoded
    @POST("user/coinaddress/del-coin-address")
    fun deleteAddress(@Header("authorization") apikey: String,
                      @Field("id") id: String): Observable<Data<Any>>


    /**
     * 获取所有能充值的币种
     */
//    hashMap.put("type", "0")
    @FormUrlEncoded
    @POST("user/depositcoin/get-coin-list")
    fun getCions(@Header("authorization") apikey: String, @Field("type") type: String): Observable<Data<List<CoinBean>>>

    /**
     * 新增币账户地址接口
     */
//    hashMap.put("coinCode", currencyType)
//    hashMap.put("walletAddress", BiAddress)
    @FormUrlEncoded
    @POST("user/coinaddress/add-coin-account")
    fun addCoinAddress(@Header("authorization") apikey: String,
                       @Field("coinCode") coinCode: String,
                       @Field("walletAddress") walletAddress: String,
                       @Field("remark") remark: String): Observable<Data<Any>>
}