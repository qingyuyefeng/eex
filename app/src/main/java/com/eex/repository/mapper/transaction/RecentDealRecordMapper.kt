package com.eex.repository.mapper.transaction

import com.eex.common.base.Data
import com.eex.domin.entity.transaction.spot.Spot
import com.eex.domin.entity.transaction.spot.TransactionRecord
import com.eex.extensions.toFullDate
import com.eex.market.bean.PurchaseDatalIst
import io.reactivex.functions.Function
import java.util.ArrayList

object RecentDealRecordMapper : Function<Data<ArrayList<PurchaseDatalIst>>, Spot> {
  override fun apply(dto: Data<ArrayList<PurchaseDatalIst>>):Spot{
    val spot = Spot(
        recentRecords = dto.data?.mapIndexed { index, it ->
          TransactionRecord(
              time = it.dealTime.toFullDate(),
              num = it.dealNum,
              price = it.dealPrice
          )
        }?.toMutableList() ?: mutableListOf()
    )
    spot.recentRecords.forEachIndexed { index, item ->
      item.beyondNext = isBeyondNext(spot.recentRecords,index)
    }
    return spot
  }
}

fun isBeyondNext(
  data: List<TransactionRecord>,
  index: Int
): Boolean {
  if (index == data.size - 1) {
    return false
  }
  return data[index].price >= data[index + 1].price
}