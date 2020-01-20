package com.eex.apis

import com.eex.Config
import com.eex.common.base.Data
import com.eex.home.bean.PageList
import com.eex.mvp.market.bean.DealPairList
import com.eex.mvp.trading.IndexEntrustDTO
import io.reactivex.Observable
import retrofit2.http.Field
import retrofit2.http.FieldMap
import retrofit2.http.FormUrlEncoded
import retrofit2.http.Header
import retrofit2.http.POST
import java.util.ArrayList

interface BondsApi {

  @POST("future/indexKline/getDealPairList")
  fun pairs(): Observable<Data<ArrayList<DealPairList>>>

  @FormUrlEncoded
  @POST("future/indexEntrust/bonds")
  fun bonds(
    @Header(
        "authorization"
    ) apikey: String,
    @Field("pageNo") pageNo: Int,
    @Field("type") type: Int,
    @Field("delKey") delKey: String,
    @FieldMap extra: HashMap<String, String>,
    @Field("pageSize") pageSize: Int = Config.PAGE_SIZE
  ): Observable<Data<PageList<IndexEntrustDTO>>>

  @FormUrlEncoded
  @POST("/future/indexEntrust/cancelIndexEntrust")
  fun cancel(
    @Field("id") id: String,
    @Field("coinCode") coinCode: String,
    @Field("pricingCoin") pricingCoin: String,
    @Field("delWay") delWay: Int
  ): Observable<Data<Any>>
}