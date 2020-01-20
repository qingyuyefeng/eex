package com.eex.repository.mapper.transaction

import com.eex.common.base.Data
import com.eex.domin.entity.RefreshState
import com.eex.domin.entity.security.Security
import com.eex.domin.entity.transaction.currency.Currency
import com.eex.domin.entity.transaction.currency.CurrencyItem
import com.eex.home.bean.SMdata
import io.reactivex.functions.Function

object AuthStatusMapper : Function<Data<SMdata>, RefreshState<Currency, CurrencyItem>> {
  override fun apply(dto: Data<SMdata>): RefreshState<Currency, CurrencyItem> = RefreshState(
      pageState = Currency(
          security = Security(level = dto.data.level)
      )
  )
}