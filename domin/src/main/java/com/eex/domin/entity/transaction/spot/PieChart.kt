package com.eex.domin.entity.transaction.spot

import java.math.BigDecimal

data class PieChart(
  val bigInflowPercent: BigDecimal = BigDecimal(0),
  val middleInflowPercent: BigDecimal = BigDecimal(0),
  val smallInflowPercent: BigDecimal = BigDecimal(0),
  val bigOutflowPercent: BigDecimal = BigDecimal(0),
  val middleOutflowPercent: BigDecimal = BigDecimal(0),
  val smallOutflowPercent: BigDecimal = BigDecimal(0),
  val totalInflowPercent: BigDecimal = BigDecimal(0),
  val totalOutflowPercent: BigDecimal = BigDecimal(0)
)