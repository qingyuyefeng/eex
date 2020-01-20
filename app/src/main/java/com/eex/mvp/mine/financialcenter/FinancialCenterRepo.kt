package com.eex.mvp.mine.financialcenter

import com.eex.domin.entity.financialcenter.FinancialCenter
import io.reactivex.Observable

/**
 * @Description:
 * @Author: xq
 * @CreateDate: 2019/12/11 13:04
 */
interface FinancialCenterRepo {
    fun getMoneyCoins(): Observable<FinancialCenter>
    fun getCycleTypes(coinCode: String): Observable<FinancialCenter>
    fun getSettings(tradingPwd:String,
                    coinCode: String,
                    coinMoney: String,
                    financialcycleConfigId: String,
                    lockAssetsConfigId: String,
                    type: String): Observable<FinancialCenter>
}
