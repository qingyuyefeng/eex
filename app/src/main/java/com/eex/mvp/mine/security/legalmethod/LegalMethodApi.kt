package com.eex.mvp.mine.security.legalmethod

import com.eex.common.base.Data
import com.eex.domin.entity.legalmethod.MerchdAccount
import io.reactivex.Observable
import retrofit2.http.Header
import retrofit2.http.POST

/**
 * @Description:
 * @Author: xq
 * @CreateDate: 2019/12/25 19:53
 */
interface LegalMethodApi {
    /**
     * c2c查询用户是否绑定支付方式
     */
    @POST("ctc/merchdealaccount/get-list")
    fun merchdealaccount(@Header("authorization") apikey: String): Observable<Data<List<MerchdAccount>>>
}