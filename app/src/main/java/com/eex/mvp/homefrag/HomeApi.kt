package com.eex.mvp.homefrag

import com.eex.common.base.Data
import com.eex.domin.entity.home.Banner
import com.eex.domin.entity.home.HomeBean2
import com.eex.domin.entity.home.ListBean
import com.eex.domin.entity.home.Notice
import com.eex.home.bean.Page
import io.reactivex.Observable
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.Header
import retrofit2.http.POST

/**
 * @Description:
 * @Author: xq
 * @CreateDate: 2019/12/23 16:36
 */
interface HomeApi {

    /**
     * 首页广告
     */
    @FormUrlEncoded
    @POST("user/index/banner")
    fun getBanner(@Header("user-agent") apikey: String, @Field("websiteType") websiteType: String): Observable<Data<Page<Banner>>>

    /**
     * 首页list集合
     */
    @POST("TradingHall/dealPair/getDealPairList")
    fun getDealPairList(): Observable<Data<List<ListBean>>>

    /**
     * 首页根据key查询list
     */
    @FormUrlEncoded
    @POST("TradingHall/macket/getIndexMaketList")
    fun getIndexMaketList(@Field("delkeys") delkeys: String): Observable<Data<List<HomeBean2>>>

    /**
     * 最新消息(公告)
     */
    //正式环境ID
//    hashMap.put("categoryId", "bb3a00b13a64463eafcc35e4bba73bca")
//    hashMap.put("pageSize", "20")
//    hashMap.put("pageNo", "1")
//    hashMap.put("website", "cn")
    @FormUrlEncoded
    @POST("user/index/list-page")
    fun getNotices(@Field("categoryId") categoryId: String = "0d337e9b812d4e3399ca8c3f22f22c7c",
                   @Field("pageSize") pageSize: String = "20",
                   @Field("pageNo") pageNo: String = "1",
                   @Field("websiteType") websiteType: String,
                   @Field("website") website: String = "cn"): Observable<Data<Notice>>
}