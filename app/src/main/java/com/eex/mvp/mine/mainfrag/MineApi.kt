package com.eex.mvp.mine.mainfrag

import com.eex.common.base.Data
import com.eex.domin.entity.mine.MineBean
import com.eex.home.bean.CtcUserInfo
import io.reactivex.Observable
import retrofit2.http.Header
import retrofit2.http.POST

/**
 * @Description:
 * @Author: xq
 * @CreateDate: 2019/12/5 10:10
 */
interface MineApi {
    /**
     * 个人中心（获取用户信息）
     */
    @POST("user/user-account-info")
    fun getUserInfo(@Header("authorization") apikey: String): Observable<Data<MineBean>>

    /**
     * 判断是不是C2C商家
     */
    @POST("ctc/ctcaccount/ctcUserInfo")
    fun isSeller(@Header("authorization") apikey: String): Observable<Data<CtcUserInfo>>
}