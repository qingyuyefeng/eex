package com.eex.repository.mapper.transaction

import com.eex.common.base.Data
import com.eex.domin.entity.RefreshState
import com.eex.domin.entity.transaction.currency.Currency
import com.eex.domin.entity.transaction.currency.Currency.BindPayType.BIND
import com.eex.domin.entity.transaction.currency.Currency.BindPayType.UNBIND
import com.eex.domin.entity.transaction.currency.CurrencyItem
import com.eex.home.bean.Merchdealaccount
import io.reactivex.functions.Function
import java.util.ArrayList

object PayTypeBindStateMapper : Function<Data<ArrayList<Merchdealaccount>>, RefreshState<Currency, CurrencyItem>> {
  override fun apply(dto: Data<ArrayList<Merchdealaccount>>): RefreshState<Currency, CurrencyItem> =
    RefreshState(
        pageState = Currency(
            hasBindPayType = if (dto.data.isNullOrEmpty()) UNBIND else BIND
        )
    )
}