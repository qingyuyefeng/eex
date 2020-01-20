package com.eex.repository.adapter

import com.squareup.moshi.FromJson
import com.squareup.moshi.ToJson
import java.math.BigDecimal

class BigDecimalAdapter {
  @ToJson
  internal fun toJson(status: BigDecimal): String {
    return status.toPlainString()
  }

  @FromJson
  internal fun fromJson(value: String): BigDecimal = BigDecimal(value)
}