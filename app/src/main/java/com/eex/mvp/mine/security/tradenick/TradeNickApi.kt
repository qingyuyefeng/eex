package com.eex.mvp.mine.security.tradenick

import com.eex.common.base.Data
import io.reactivex.Observable
import retrofit2.http.*

/**
 * @Description:
 * @Author: xq
 * @CreateDate: 2019/12/24 16:25
 */
interface TradeNickApi {
    /**
     * 修改商家名称
     *
     * @param hashMap
     * @return
     */

    @FormUrlEncoded
    @POST("ctc/ctcaccount/ctcNickName")
    fun setNickName(@Header("authorization") apikey: String,
                    @Field("nickName") nickName: String): Observable<Data<Any>>
}