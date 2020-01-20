package com.eex.mvp.mine.tradingrecord.legalorder

import com.eex.common.base.Data
import com.eex.domin.entity.dealrecord.LegalOrderBean
import com.eex.home.bean.Page
import io.reactivex.Observable
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.Header
import retrofit2.http.POST

/**
 * @Description:
 * @Author: xq
 * @CreateDate: 2020/1/5 11:54
 */
interface LegalOrderApi {
    /**
     * c2c查询用户订单列表
     */
//    hashMap.put("pageNo", pageNo + "")
//    hashMap.put("pageSize", pageSize + "")
    @FormUrlEncoded
    @POST("ctc/shop/buy/getOrderDetailList")
    fun getOrderDetailList(@Header("authorization") apikey: String,
                           @Field("pageNo") pageNo: String = "1",
                           @Field("pageSize") pageSize: String = "50"): Observable<Data<Page<LegalOrderBean>>>
}