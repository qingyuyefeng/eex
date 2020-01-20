package com.eex.repository.mapper.attorney

import com.eex.common.base.Data
import com.eex.domin.entity.RefreshState
import com.eex.domin.entity.attorney.Attorney
import com.eex.domin.entity.attorney.AttorneyItem
import com.eex.domin.entity.attorney.AttorneyStatus
import com.eex.domin.entity.attorney.DealType
import com.eex.home.bean.Page
import com.eex.market.bean.Delegation
import io.reactivex.functions.Function
import java.math.BigDecimal

object AttorneyMapper : Function<Data<Page<Delegation>>, RefreshState<Attorney, AttorneyItem>> {
  override fun apply(dto: Data<Page<Delegation>>): RefreshState<Attorney, AttorneyItem> =
    RefreshState(
        items = dto.data?.resultList?.map {
          AttorneyItem(
              id = it?.orderNo ?: "",
              time = it?.createTime ?: "",
              tradeUnit = it?.coinCode ?: "",
              fixedUnit = it?.fixPriceCoinCode ?: "",
              surplusQuantity = (it?.residueNum ?: BigDecimal("0")).toDouble(),
              totalQuantity = (it?.delNum ?: BigDecimal("0")).toDouble(),
              dealQuantity = (it?.delNum ?: BigDecimal("0")).toDouble() - (it?.residueNum ?: BigDecimal("0")).toDouble(),
              dealWay = it?.delWay?:0,
              price = (it?.delAmount ?: BigDecimal("0")).toDouble(),
              status = AttorneyStatus.from(it?.delStatus ?: 0),
              dealType = DealType.from(it?.dealType?.toString() ?: "1")
          )
        }?.toMutableList() ?: mutableListOf()
    )
}