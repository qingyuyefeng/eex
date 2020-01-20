package com.eex.repository.mapper.transaction

import com.eex.domin.entity.transaction.spot.Spot
import com.eex.domin.entity.transaction.spot.TransactionRecord
import com.eex.extensions.toFullDate
import com.eex.market.bean.PurchaseDta
import io.reactivex.functions.Function
import java.math.BigDecimal

object MarketQuotesMapper : Function<PurchaseDta, Spot> {
  override fun apply(dto: PurchaseDta): Spot {
    val spot = Spot(
        marketBuyInfos = dto.buy?.map {
          TransactionRecord(
              price = it.delAmount,
              num = it.residueNum
          )
        }?.toMutableList() ?: mutableListOf(),
        marketSellInfos = dto.sell?.map {
          TransactionRecord(
              price = it.delAmount,
              num = it.residueNum
          )
        }?.toMutableList() ?: mutableListOf(),
        latestDealAmount = dto.listOrder?.firstOrNull()?.dealAmount ?: BigDecimal(0),
        latestIndexCnyAmount = dto.usdtCny ?: BigDecimal(0),
        recentRecords = dto.listOrder?.map {
          TransactionRecord(
              time = it.dealDate.toFullDate(),
              num = it.dealNum,
              price = it.dealAmount
          )
        }?.toMutableList() ?: mutableListOf()
    )
    spot.recentRecords.forEachIndexed { index, item ->
      item.beyondNext = isBeyondNext(spot.recentRecords, index)
    }
    return spot
  }
}