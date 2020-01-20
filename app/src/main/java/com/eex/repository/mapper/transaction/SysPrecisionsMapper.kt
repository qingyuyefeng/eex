package com.eex.repository.mapper.transaction

import com.eex.common.base.Data
import com.eex.domin.entity.transaction.spot.Spot
import com.eex.home.bean.MainList
import io.reactivex.functions.Function
import java.math.BigDecimal
import java.util.ArrayList

class SysPrecisionsMapper(val pair:String):Function<Data<ArrayList<MainList>>, Spot> {
  override fun apply(dto: Data<ArrayList<MainList>>): Spot = Spot(
      priceScale = dto.data.firstOrNull { it.dealPair == pair }?.priceReservation?.intValueExact()?:0,
      numScale = dto.data.firstOrNull { it.dealPair == pair }?.quantityRetention?.intValueExact()?:0,
      quantityMin = dto.data.firstOrNull { it.dealPair == pair }?.minNum?: BigDecimal(0),
      quantityMax = dto.data.firstOrNull { it.dealPair == pair }?.maxNum?: BigDecimal(0)
  )
}