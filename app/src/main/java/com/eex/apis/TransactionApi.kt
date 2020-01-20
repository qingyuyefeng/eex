package com.eex.apis

import com.google.gson.JsonObject
import com.eex.Config
import com.eex.common.base.Data
import com.eex.home.bean.Advertisingvo
import com.eex.home.bean.CoinfigList
import com.eex.home.bean.CtcUserInfo
import com.eex.home.bean.MainData
import com.eex.home.bean.MainList
import com.eex.home.bean.Merchdealaccount
import com.eex.home.bean.NewSdatalist
import com.eex.home.bean.Page
import com.eex.market.bean.CoinCashFlowBean
import com.eex.market.bean.HistoryBean
import com.eex.market.bean.JsonRoot
import com.eex.market.bean.Money
import com.eex.market.bean.PurchaseDatalIst
import com.eex.market.bean.PurchaseDta
import com.eex.market.weight.BuyMoney
import com.eex.market.weight.CashFlow
import com.eex.market.weight.Root
import com.eex.notice.bean.IndustryBean
import com.eex.notice.bean.NoticeBean
import io.reactivex.Observable
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Query
import java.util.ArrayList

interface TransactionApi {

  @FormUrlEncoded
  @POST("TradingHall/dealPair/getDealPairList")
  fun dealPairs(
    @Field("pricingCoin") pricingCoin: String
  ): Observable<Data<ArrayList<MainList>>>

  @POST("TradingHall/dealPair/getDealPairList")
  fun sysPrecisions(): Observable<Data<ArrayList<MainList>>>

  @FormUrlEncoded
  @POST("TradingHall/macket/getTickList")
  fun recentTransactions(
    @Field("dealPair") dealPair: String,
    @Field("size") size: Int = Config.PAGE_SIZE
  ): Observable<Data<ArrayList<PurchaseDatalIst>>>

  @FormUrlEncoded
  @POST("TradingHall/macket/getTickMaket")
  fun markets(@Field("delkey") dealKey: String): Observable<Root<PurchaseDta>>

  @FormUrlEncoded
  @POST("TradingHall/delegation/creatDelegation")
  fun createDelegation(
    @Header(
        "authorization"
    ) apikey: String,
    @Field("delWay") dealWay: String,
    @Field("delNum") dealNum: String,
    @Field("delAmount") dealAmount: String,
    @Field("coinCode") tradeUnit: String = "",
    @Field("fixPriceCoinCode") fixedUnit: String = "",
    @Field("dealType") dealType: String = ""
  ): Observable<Data<Any>>

  @FormUrlEncoded
  @POST("TradingHall/stoploss/creatStoploss")
  fun createStoploss(
    @Header(
        "authorization"
    ) apikey: String,
    @Field("triggerPrice") triggerPrice: String,
    @Field("triggerType") triggerType: String,
    @Field("delNum") dealNum: String,
    @Field("delAmount") dealAmount: String,
    @Field("coinCode") tradeUnit: String = "",
    @Field("fixPriceCoinCode") fixedUnit: String = "",
    @Field("dealType") dealType: String = ""
  ): Observable<Data<Any>>

  @FormUrlEncoded
  @POST("TradingHall/macket/getIndexMaketList")
  fun getIndexMarkets(@Field("delkeys") dealKeys: String): Observable<Data<ArrayList<MainData>>>

  @FormUrlEncoded
  @POST("user/user-deal-account")
  fun getSurplus(
    @Header(
        "authorization"
    ) apikey: String,
    @Field("coinCodes") coinCodes: String
  ): Observable<Data<ArrayList<BuyMoney<Money>>>>

  /**
   * 饼状图json
   */
  @GET("static/data/coin.json") fun pieCoinConfig(): Observable<JsonRoot<JsonObject>>

  /**
   * 饼状图
   */
  @GET("${Config.PIE_CHART_URL}index/coinCashFlow") fun pieData(
    @Query(
        "coincode"
    ) coincode: String
  ): Observable<CashFlow<CoinCashFlowBean<HistoryBean>>>

  /**
   * 消息类型
   */
  @FormUrlEncoded
  @POST("user/index/list-type")
  fun newsType(
    @Header(
        "authorization"
    ) apikey: String,
    @Field("websiteType") source: String = "2",
    @Field("website") language: String = "cn"
  ): Observable<Data<ArrayList<NoticeBean>>>

  /**
   * 最新消息
   */
  @FormUrlEncoded
  @POST("user/index/list-page")
  fun news(
    @Field("categoryId") newsTypeId: String,
    @Field("pageNo") pageNo: Int = 0,
    @Field("websiteType") source: String = "2",
    @Field("website") language: String = "cn",
    @Field("pageSize") pageSize: Int = 1000
  ): Observable<Data<NewSdatalist<IndustryBean>>>

  /**
   * c2c获取法币交易列表
   */
  @POST("ctc/coinTradeConfig/getCoinTradeConfigList")
  fun legalCurrencyConfig(): Observable<Data<ArrayList<CoinfigList>>>

  @FormUrlEncoded
  @POST("ctc/advertisingSelect/getAdvertisingPage")
  fun legalCurrencies(
    @Field("tradeCoin") tradeUnit: String,
    @Field("tradeType") tradeType: String,
    @Field("pageNo") pageNo: Int,
    @Field("pageSize") pageSize: Int = Config.PAGE_SIZE,
    @Field("websiteType") websiteType: Int = 2
  ): Observable<Data<Page<Advertisingvo>>>

  @POST("ctc/merchdealaccount/get-list") fun checkPayTypeBindState(
    @Header(
        "authorization"
    ) apikey: String
  ): Observable<Data<ArrayList<Merchdealaccount>>>

  @POST("ctc/ctcaccount/ctcUserInfo") fun isStore(
    @Header(
        "authorization"
    ) apikey: String
  ): Observable<Data<CtcUserInfo>>
}