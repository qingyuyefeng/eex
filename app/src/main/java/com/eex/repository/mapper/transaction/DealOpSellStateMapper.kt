package com.eex.repository.mapper.transaction

import com.eex.common.base.Data
import com.eex.domin.entity.transaction.spot.Spot
import com.eex.home.bean.MainList
import io.reactivex.functions.Function
import java.util.ArrayList

class DealOpSellStateMapper(private val pairName:String):Function<Data<ArrayList<MainList>>,Spot> {
  override fun apply(dto: Data<ArrayList<MainList>>) = Spot(
      isBuyOrSellValid = dto.data.none { it.dealPair== pairName && it.sellState == 1}
  )
}