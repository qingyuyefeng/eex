package com.eex.domin.entity.financialcenter

/**
 * @Description:
 * @Author: xq
 * @CreateDate: 2019/12/11 13:07
 */
data class FinancialCenter(
        val level: Int = 0,
        val msg :String = "",
        val coinList: List<MoneyCoin> = ArrayList(),
        val type: Int = 1, //1本息续约 2本金续约 3到期结束
        val coinCycleType: CoinCycleType = CoinCycleType()
)

data class MoneyCoin(
        var coinCode: String = "",
        var id: String = "",
        var imgUrl: String = ""
)

data class CoinCycleType(
        val coinCode: String = "",
        val coinCodeIwm: CoinCodeIwm = CoinCodeIwm(),
        val coinCodeProfit: String = "",
        val coinCodeProfitIwm: Any? = null,
        val cycleConfigVOList: List<CycleConfigVO> = ArrayList(),
        val feeDeductionType: Int = 0,
        val id: String = "",
        val levers: Int = 0,
        val lockFixedFee: Int = 0,
        val lockRateFee: Int = 0,
        val maxNum: Double = 0.0,
        val minLockNum: Double = 0.0,
        val minNum: Double = 0.0,
        val returnFeeType: Int = 0
)

data class CoinCodeIwm(
        val dealDate: Long = 0L,
        val dealNum: Double = 0.0,
        val delKey: String = "",
        val fooPrice: Double = 0.0,
        val higePrice: Double = 0.0,
        val lastPrice: Double = 0.0,
        val newPrice: Double = 0.0,
        val usdtCny: Double = 0.0
)

data class CycleConfigVO(
        val activeEndTime: String = "",
        val activeRate: Double = 0.0,
        val activeStartTime: String = "",
        val financialCycle: Int = 0,
        val financialCycleMonth: Any,
        val financialCycleYear: Int = 0,
        val fixedDailyRate: Double = 0.0,
        val id: String = "",
        val lockAlreadyCount: Double = 0.0,
        val lockAssetsConfigId: String = "",
        val lockCount: Double = 0.0
)