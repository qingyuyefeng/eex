package com.eex.repository.mapper.transaction

import com.eex.domin.entity.transaction.spot.PieChart
import com.eex.domin.entity.transaction.spot.Spot
import com.eex.market.bean.CoinCashFlowBean
import com.eex.market.bean.HistoryBean
import com.eex.market.weight.CashFlow
import io.reactivex.functions.Function
import java.math.BigDecimal

object PieChartDataMapper : Function<CashFlow<CoinCashFlowBean<HistoryBean>>, Spot> {
  override fun apply(dto: CashFlow<CoinCashFlowBean<HistoryBean>>): Spot {
    val spot = Spot()
    dto.data?.today?.apply {
      val total = (buy_vol_usd ?: BigDecimal(0)) + (sell_vol_usd ?: BigDecimal(0))
      spot.pieChart = PieChart(
          bigInflowPercent = (buy_vol_usd_set?.b ?: BigDecimal(0)) / total,
          middleInflowPercent = (buy_vol_usd_set?.m ?: BigDecimal(0)) / total,
          smallInflowPercent = (buy_vol_usd_set?.s ?: BigDecimal(0)) / total,
          bigOutflowPercent = (sell_vol_usd_set?.b ?: BigDecimal(0)) / total,
          middleOutflowPercent = (sell_vol_usd_set?.m ?: BigDecimal(0)) / total,
          smallOutflowPercent = (sell_vol_usd_set?.s ?: BigDecimal(0)) / total,
          totalInflowPercent = (buy_vol_usd ?: BigDecimal(0))/total,
          totalOutflowPercent = (sell_vol_usd ?: BigDecimal(0))/total
      )
    }
    return spot
  }
}