package com.eex.mvp.mine.tradingrecord.coinmoney

import com.eex.common.base.Data
import com.eex.domin.entity.dealrecord.CurrentRecordBean
import com.eex.domin.entity.dealrecord.StopLossBean
import com.eex.home.bean.Page
import io.reactivex.Observable
import retrofit2.http.*
import java.util.HashMap

/**
 * @Description:
 * @Author: xq
 * @CreateDate: 2019/12/31 17:56
 */
interface CoinMoneyApi {
    /**
     * 我的委托-当前委托
     */
//    hashMap.put("pageSize", size.toString())
//    hashMap.put("pageNo", p.toString())
//    hashMap.put("excludeCode", "GLS")
//    hashMap.put("delStatus", "2,3,4")
    @FormUrlEncoded
    @POST("TradingHall/delegation/getDelegationList")
    fun getCurrentData(
            @Header("authorization") apikey: String,
            @Field("pageNo") pageNo: Int,
            @Field("delStatus") delStatus: String,
            @Field("pageSize") pageSize: Int,
            @Field("excludeCode") excludeCode: String? = null
    ): Observable<Data<Page<CurrentRecordBean>>>

    /**
     * 当前委托记录止盈止损
     */
//    hashMap.put("pageSize", size.toString())
//    hashMap.put("pageNo", p.toString())
//    hashMap.put("delStatus", "0")
    @FormUrlEncoded
    @POST("TradingHall/stoploss/getStoplossList")
    fun getStopLoss(
            @Header("authorization") apikey: String,
            @Field("pageNo") pageNo: Int,
            @Field("pageSize") pageSize: Int,
            @Field("delStatus") delStatus: String = "0"
    ): Observable<Data<Page<StopLossBean>>>

    /**
     * 取消委托
     */
//    hashMap.put("order", list.get(position).getOrderNo() + "")
//    hashMap.put("coinCode", list.get(position).getCoinCode() + "")
//    hashMap.put("fixPriceCoinCode", list.get(position).getFixPriceCoinCode() + "")
    @FormUrlEncoded
    @POST("TradingHall/delegation/cancelDelegation")
    fun cancelDelegation(@Header("authorization") apikey: String,
                         @Field("order") order: String,
                         @Field("coinCode") coinCode: String,
                         @Field("fixPriceCoinCode") fixPriceCoinCode: String): Observable<Data<Any>>

    /**
     * 取消止盈止损的委托
     */
//    hashMap.put("order", list.get(position).getOrderNo() + "")
//    hashMap.put("coinCode", list.get(position).getCoinCode() + "")
//    hashMap.put("fixPriceCoinCode", list.get(position).getFixPriceCoinCode() + "")
    @FormUrlEncoded
    @POST("TradingHall/stoploss/cancelStoploss")
    fun cancelStoploss(@Header("authorization") apikey: String,
                       @Field("orderNo") orderNo: String): Observable<Data<Any>>
}