package com.eex.mvp.mine.financialcenter

import com.eex.apis.RetrofitService
import com.eex.constant.Constants
import com.eex.domin.entity.financialcenter.FinancialCenter
import com.eex.extensions.filterResult
import com.tencent.mmkv.MMKV
import io.reactivex.Observable
import javax.inject.Inject

/**
 * @Description:
 * @Author: xq
 * @CreateDate: 2019/12/11 14:08
 */
class FinancialRepoImpl @Inject constructor(
        private val service: RetrofitService,
        private val mmkv: MMKV
) : FinancialCenterRepo {
    override fun getMoneyCoins(): Observable<FinancialCenter> {
        return service.createApi(FinancialCenterApi::class.java)
                .getMoneyCoins(mmkv.decodeString(Constants.TOKEN_ID, ""))
                .filterResult()
                .map(FinancialCenterMapper)
    }

    override fun getCycleTypes(coinCode: String): Observable<FinancialCenter> {
        return service.createApi(FinancialCenterApi::class.java)
                .getCycleTypes(mmkv.decodeString(Constants.TOKEN_ID, ""), coinCode)
                .filterResult()
                .map(FinancialCenterMapper1)
    }

    override fun getSettings(tradingPwd:String,coinCode: String, coinMoney: String, financialcycleConfigId: String, lockAssetsConfigId: String, type: String): Observable<FinancialCenter> {
        return service.createApi(FinancialCenterApi::class.java)
                .getSettings(mmkv.decodeString(Constants.TOKEN_ID, ""), tradingPwd,
                        coinCode, coinMoney, financialcycleConfigId, lockAssetsConfigId, type)
                .filterResult()
                .map(FinancialCenterMapper2)
    }
}
