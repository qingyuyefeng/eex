package com.eex.apis

import com.eex.Config
import com.eex.common.base.Data
import com.eex.home.bean.Page
import com.eex.market.bean.Delegation
import com.eex.market.bean.Stoploss
import io.reactivex.Observable
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.Header
import retrofit2.http.POST

interface AttorneyApi {

  /**
   * 我的委托-当前委托
   */
  @FormUrlEncoded
  @POST("TradingHall/delegation/getDelegationList")
  fun attorneyList(
    @Header("authorization") apikey: String,
    @Field("pageNo") pageNo: Int,
    @Field("delStatus") type: String,
    @Field("dealType") dealType: String = "",
    @Field("pageSize") pageSize: Int = Config.PAGE_SIZE
  ): Observable<Data<Page<Delegation>>>

  /**
   * 当前委托记录止盈止损
   */
  @FormUrlEncoded
  @POST("TradingHall/stoploss/getStoplossList")
  fun stoplossList(
    @Header("authorization") apikey: String,
    @Field("pageNo") pageNo: Int,
    @Field("dealType") dealType: String = "",
    @Field("pageSize") pageSize: Int = Config.PAGE_SIZE,
    @Field("delStatus") delStatus:String = "0"
  ): Observable<Data<Page<Stoploss>>>

  /**
   * 取消委托
   */
  @FormUrlEncoded
  @POST("TradingHall/delegation/cancelDelegation")
  fun cancelDelegation(
    @Header("authorization") apikey: String,
      @Field("order") id:String = "",
      @Field("coinCode") tradeUnit:String = "",
      @Field("fixPriceCoinCode") fixedUnit:String = ""
  ): Observable<Data<*>>
}