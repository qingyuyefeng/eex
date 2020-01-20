package com.eex.repository.mapper.transaction

import com.eex.common.base.Data
import com.eex.domin.entity.transaction.spot.Spot
import com.eex.market.bean.Money
import com.eex.market.weight.BuyMoney
import io.reactivex.functions.Function
import java.math.BigDecimal
import java.util.ArrayList

class UsableQuantityMapper(val fixPriceCoin: String) : Function<Data<ArrayList<BuyMoney<Money>>>, Spot> {
  override fun apply(dto: Data<ArrayList<BuyMoney<Money>>>) = Spot(
      usableCoinQuantity = dto.data?.firstOrNull()?.data?.coinMoney ?: BigDecimal(0),
      usableFixCoinQuantity = if (fixPriceCoin == "CNYE")
        dto.data?.getOrNull(1)?.data?.usableCNY ?: BigDecimal(0)
      else
        dto.data?.getOrNull(1)?.data?.coinMoney ?: BigDecimal(0)
  )
}