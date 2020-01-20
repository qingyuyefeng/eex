package com.eex.repository.mapper.attorney

import com.eex.common.base.Data
import com.eex.domin.entity.RefreshState
import com.eex.domin.entity.attorney.Attorney
import com.eex.domin.entity.attorney.AttorneyItem
import com.eex.domin.entity.attorney.DealType
import com.eex.domin.entity.attorney.TradeTriggerStatus
import com.eex.extensions.toFullDate
import com.eex.home.bean.Page
import com.eex.market.bean.Stoploss
import io.reactivex.functions.Function
import java.math.BigDecimal

object StoplossMapper : Function<Data<Page<Stoploss>>, RefreshState<Attorney, AttorneyItem>> {
  override fun apply(dto: Data<Page<Stoploss>>): RefreshState<Attorney, AttorneyItem> =
    RefreshState(
        items = dto.data?.resultList?.map {
          AttorneyItem(
              id = it?.orderNo ?: "",
              time = it?.createTime?.toFullDate() ?: "",
              tradeUnit = it?.coinCode ?: "",
              fixedUnit = it?.fixPriceCoinCode ?: "",
              totalQuantity = (it?.delNum ?: BigDecimal("0")).toDouble(),
              price = (it?.delAmount ?: BigDecimal("0")).toDouble(),
              triggerStatus = TradeTriggerStatus.from(it?.triggerState ?: 0),
              dealType = DealType.from(it?.dealType?.toString() ?: "1")
          )
        }?.toMutableList() ?: mutableListOf()
    )
}