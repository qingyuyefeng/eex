package com.eex.mvp.transaction

import com.eex.mvp.refresh.RefreshContract

class CurrencyTradeContract {

  interface View<ITEM> : RefreshContract.View<ITEM> {
    fun setPairName(pair: String)
    fun showBindPhoneDialog()
    fun showRealNameDialog()
    fun showBindPayTypeDialog()
    fun toPurchaseCurrency(id: String)
    fun toSellCurrency(id: String)
    fun toPublish()
    fun lightSort(light:Boolean)
  }
}