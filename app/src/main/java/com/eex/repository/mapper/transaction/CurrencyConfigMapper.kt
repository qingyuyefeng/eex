package com.eex.repository.mapper.transaction

import com.eex.common.base.Data
import com.eex.domin.entity.RefreshState
import com.eex.domin.entity.transaction.currency.Currency
import com.eex.domin.entity.transaction.currency.CurrencyItem
import com.eex.home.bean.CoinfigList
import io.reactivex.functions.Function
import java.util.ArrayList

object CurrencyConfigMapper:Function<Data<ArrayList<CoinfigList>>, RefreshState<Currency, CurrencyItem>> {
  override fun apply(dto: Data<ArrayList<CoinfigList>>): RefreshState<Currency, CurrencyItem> =
    RefreshState(
        pageState = Currency(
            availableCurrencies = dto?.data?.map {
              it.tradeCoin
            }?: mutableListOf()
        )
    )
}