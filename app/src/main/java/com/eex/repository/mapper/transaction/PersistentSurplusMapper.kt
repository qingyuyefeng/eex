package com.eex.repository.mapper.transaction

import com.eex.domin.entity.transaction.spot.Spot
import com.eex.market.bean.bIDataVO
import io.reactivex.functions.Function
import java.math.BigDecimal

class PersistentSurplusMapper(val tradeUnit: String) : Function<bIDataVO, Spot> {
  override fun apply(dto: bIDataVO) = if (dto.coinCode == tradeUnit) Spot(
      updateCoinQuantity = true,
      usableCoinQuantity = dto.coinMoney?: BigDecimal(0)
  ) else Spot(
      updateCoinQuantity = false,
      usableFixCoinQuantity = dto.coinMoney
  )
}