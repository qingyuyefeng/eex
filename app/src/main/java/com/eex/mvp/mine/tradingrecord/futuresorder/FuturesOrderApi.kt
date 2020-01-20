package com.eex.mvp.mine.tradingrecord.futuresorder

import com.eex.common.base.Data
import com.eex.domin.entity.dealrecord.FuturesOrderBean
import com.eex.domin.entity.dealrecord.NowData
import com.eex.home.bean.PageList
import com.eex.mvp.trading.IndexMarketList
import io.reactivex.Observable
import retrofit2.http.*
import java.util.ArrayList
import java.util.HashMap

/**
 * @Description:
 * @Author: xq
 * @CreateDate: 2020/1/3 15:27
 */
interface FuturesOrderApi {
    @FormUrlEncoded
    @POST("future/indexEntrust/bonds")
    fun bonds(
            @Header("authorization") apikey: String,
            @Field("type") type: Int,
            @Field("delKey") delKey: String = "",
//            @FieldMap extra: HashMap<String, String>,
            @Field("pageNo") pageNo: Int = 1,
            @Field("pageSize") pageSize: Int = 100
    ): Observable<Data<PageList<FuturesOrderBean>>>

    /**
     * 指数行情
     */
    @FormUrlEncoded
    @POST("future/indexKline/getIndexMarketList")
    fun getNowData(@Field("delkeys") delkeys: String): Observable<Data<List<NowData>>>

    /**
     * 撤销委托
     */
//    hashMap.put("order", list.get(position).getOrderNo() + "")
//    hashMap.put("coinCode", list.get(position).getCoinCode() + "")
//    hashMap.put("fixPriceCoinCode", list.get(position).getFixPriceCoinCode() + "")
    @FormUrlEncoded
    @POST("future/indexEntrust/cancelIndexEntrust")
    fun cancelFuture(@Header("authorization") apikey: String,
                     @Field("id") id: String,
                     @Field("coinCode") coinCode: String,
                     @Field("pricingCoin") pricingCoin: String,
                     @Field("delWay") delWay: String): Observable<Data<Any>>

    /**
     * 平仓
     */
    @FormUrlEncoded
    @POST("future/indexEntrust/updateIndexEntrustOP")
    fun closeOut(@Header("authorization") apikey: String,
                 @Field("delAmount") delAmount: String,
                 @Field("indexEntrustId") indexEntrustId: String,
                 @Field("opType") opType: String = "1"): Observable<Data<Any>>

    /**
     * 修改是否持仓过夜
     */
    @FormUrlEncoded
    @POST("future/indexEntrust/updateBondsNight")
    fun overNight(@Header("authorization") apikey: String,
                  @Field("id") id: String,
                  @Field("coinCode") coinCode: String,
                  @Field("pricingCoin") pricingCoin: String): Observable<Data<Any>>

    /**
     * 止盈止损
     */
//    hashMap["indexEntrustId"] = dto.id
//    hashMap["targetProfit"] = profit.toString()
//    hashMap["targetProfitPrice"] = profitPrice.toString()
//    hashMap["stopLoss"] = loss.toString()
//    hashMap["stopLossPrice"] = lossPrice .toString()
    @FormUrlEncoded
    @POST("future/indexEntrust/updateSurplusDamage")
    fun stopFull(@Header("authorization") apikey: String,
                 @Field("indexEntrustId") id: String,
                 @Field("targetProfit") targetProfit: String,
                 @Field("targetProfitPrice") targetProfitPrice: String,
                 @Field("stopLoss") stopLoss: String,
                 @Field("stopLossPrice") stopLossPrice: String): Observable<Data<Any>>
}