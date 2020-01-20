package com.eex.domin.entity.transaction.currency

import com.eex.domin.entity.attorney.DealType
import com.eex.domin.entity.attorney.DealType.BUY
import java.math.BigDecimal

data class CurrencyItem(
  var dealType: DealType = BUY,
  val id:String = "",
  val name: String = "",
  val certified: Boolean = false,
  val creditRating:Int = 0,
  val tradeUnit: String = "",
  val tradeNum: BigDecimal = BigDecimal(0),
  val tradePrice: BigDecimal = BigDecimal(0),
  val boost: BigDecimal = BigDecimal(0),
  val least: BigDecimal = BigDecimal(0),
  val most: BigDecimal = BigDecimal(0),
  val dealCount: Int = 0,
  val dealCompleteCount: Int = 0,
  val dealCompleteRate: String = "0",
  val payTypes: List<PayType> = listOf()
)