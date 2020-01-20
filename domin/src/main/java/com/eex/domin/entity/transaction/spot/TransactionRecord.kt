package com.eex.domin.entity.transaction.spot

import java.math.BigDecimal

data class TransactionRecord(
  val time: String = "",
  val price: BigDecimal = BigDecimal(0),
  val num: BigDecimal = BigDecimal(0),
  var beyondNext: Boolean = false
)