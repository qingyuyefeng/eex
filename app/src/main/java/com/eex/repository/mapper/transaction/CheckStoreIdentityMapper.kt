package com.eex.repository.mapper.transaction

import com.eex.common.base.Data
import com.eex.domin.entity.RefreshState
import com.eex.domin.entity.transaction.currency.Currency
import com.eex.domin.entity.transaction.currency.Currency.StoreIdentityState.IS_STORE
import com.eex.domin.entity.transaction.currency.Currency.StoreIdentityState.NOT_STORE
import com.eex.domin.entity.transaction.currency.CurrencyItem
import com.eex.home.bean.CtcUserInfo
import io.reactivex.functions.Function

object CheckStoreIdentityMapper : Function<Data<CtcUserInfo>, RefreshState<Currency, CurrencyItem>> {
  override fun apply(dto: Data<CtcUserInfo>): RefreshState<Currency, CurrencyItem> =
    RefreshState(
        pageState = Currency(
            storeIdentityState = if (dto.data.merchantStatus == 2) IS_STORE else NOT_STORE
        )
    )
}