package com.eex.mvp.mine.financialcenter

import com.eex.common.base.Data
import com.eex.domin.entity.financialcenter.CoinCycleType
import com.eex.domin.entity.financialcenter.FinancialCenter
import com.eex.domin.entity.financialcenter.MoneyCoin
import com.eex.home.bean.BiUserData
import io.reactivex.functions.Function

/**
 * @Description:
 * @Author: xq
 * @CreateDate: 2019/12/11 14:14
 */
object FinancialCenterMapper : Function<Data<List<MoneyCoin>>, FinancialCenter> {
    override fun apply(t: Data<List<MoneyCoin>>): FinancialCenter {
        return FinancialCenter(
                coinList = t.data
        )
    }
}

object FinancialCenterMapper1 : Function<Data<CoinCycleType>, FinancialCenter> {
    override fun apply(t: Data<CoinCycleType>): FinancialCenter {
        return FinancialCenter(
                coinCycleType = t.data
        )
    }
}

object FinancialCenterMapper2 : Function<Data<BiUserData>, FinancialCenter> {
    override fun apply(t: Data<BiUserData>): FinancialCenter {
        return FinancialCenter(
                msg = t.msg
        )
    }
}