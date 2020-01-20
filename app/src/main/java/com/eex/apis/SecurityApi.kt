package com.eex.apis

import com.landscape.mocknetapi.annotations.MOCK
import com.eex.common.base.Data
import com.eex.home.bean.SMdata
import com.eex.mvp.mine.security.NickBean
import io.reactivex.Observable
import retrofit2.http.Header
import retrofit2.http.POST

interface SecurityApi {

    /**
     * user/get-auth-stauts
     */
    @POST("user/get-auth-stauts")
    @MOCK("security/authStatus")
    fun authStauts(
            @Header(
                    "authorization"
            ) apikey: String
    ): Observable<Data<SMdata>>

    /**
     * 法币收款方式
     */
    @POST("ctc/merchdealaccount/get-list")
    fun getTradingType(@Header("authorization") apikey: String): Observable<Data<List<Any>>>

    /**
     * 法币交易昵称
     */
    @POST("ctc/ctcaccount/ctcUserInfo")
    fun getTradingNick(@Header("authorization") apikey: String): Observable<Data<NickBean>>

}