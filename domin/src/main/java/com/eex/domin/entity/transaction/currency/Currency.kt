package com.eex.domin.entity.transaction.currency

import com.eex.domin.entity.attorney.DealType
import com.eex.domin.entity.attorney.DealType.BUY
import com.eex.domin.entity.security.Security
import com.eex.domin.entity.transaction.currency.Currency.BindPayType.IDLE
import com.eex.domin.entity.transaction.currency.Currency.SortType.ASC

data class Currency(
  val pair: String = "",
  val tradeId: String = "",
  val tradeUnit: String = "",
  val dealType: DealType = BUY,
  val security: Security = Security(),
  val securityErr: Boolean = false,
  val hasBindPayType: BindPayType = IDLE,
  val storeIdentityState: StoreIdentityState = StoreIdentityState.IDLE,
  val goBuyPage: Boolean = false,
  val goPublishPage: Boolean = false,
  val availableCurrencies: List<String> = mutableListOf(),
  val sortType: SortType = ASC
) {
  enum class BindPayType {
    IDLE,
    BIND,
    UNBIND;
  }

  enum class StoreIdentityState {
    IDLE,
    IS_STORE,
    NOT_STORE;
  }

  enum class SortType {
    ASC,
    DESC;
  }
}