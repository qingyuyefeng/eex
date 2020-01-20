package com.eex.mvp.mine.financialcenter

import com.eex.common.base.Data
import com.eex.domin.entity.financialcenter.CoinCycleType
import com.eex.domin.entity.financialcenter.MoneyCoin
import com.eex.home.bean.BiUserData
import io.reactivex.Observable
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.Header
import retrofit2.http.POST

/**
 * @Description:
 * @Author: xq
 * @CreateDate: 2019/12/11 11:51
 */
interface FinancialCenterApi {

    /**
     * 锁仓查询理财币种
     */
    @POST("lockingMoney/config/getLockMoneyCoin")
    fun getMoneyCoins(@Header("authorization") apikey: String): Observable<Data<List<MoneyCoin>>>

    /**
     * 查询理财周期配置
     */
    @FormUrlEncoded
    @POST("lockingMoney/config/getLockingConfigByCoinCode")
    fun getCycleTypes(@Header("authorization") apikey: String, @Field("coinCode") coinCode: String): Observable<Data<CoinCycleType>>

    /**
     * 查询理财配置
     */
    @FormUrlEncoded
    @POST("lockingMoney/record/saveLockingMoney")
    fun getSettings(@Header("authorization") apikey: String,
                    @Field("accountPassWord") accountPassWord: String,
                    @Field("coinCode") coinCode: String,
                    @Field("coinMoney") coinMoney: String,
                    @Field("financialcycleConfigId") financialcycleConfigId: String,
                    @Field("lockAssetsConfigId") lockAssetsConfigId: String,
                    @Field("type") type: String): Observable<Data<BiUserData>>

}