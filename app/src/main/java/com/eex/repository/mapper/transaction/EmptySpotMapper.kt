package com.eex.repository.mapper.transaction

import com.eex.common.base.Data
import com.eex.domin.entity.transaction.spot.Spot
import io.reactivex.functions.Function

object EmptySpotMapper : Function<Data<*>, Spot> {
  override fun apply(t: Data<*>) = Spot()
}