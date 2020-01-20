package com.eex.repository

import com.eex.domin.entity.RefreshState
import com.eex.domin.entity.attorney.DealType
import com.eex.domin.entity.transaction.DealWay
import com.eex.domin.entity.transaction.currency.Currency
import com.eex.domin.entity.transaction.currency.CurrencyItem
import com.eex.domin.entity.transaction.spot.Spot
import io.reactivex.Observable

interface TransationRepo {
  fun checkTradeValid(
    pair: String,
    fixCoinUnit: String,
    dealType: DealType
  ): Observable<Spot>

  fun sysPrecisions(pair: String): Observable<Spot>

  fun recentTransactions(pair: String): Observable<Spot>

  fun markets(key: String): Observable<Spot>

  fun newAttorney(
    dealWay: DealWay,
    dealNum: String,
    amount: String,
    tradeUnit: String,
    fixedUnit: String,
    dealType: DealType
  ): Observable<Spot>

  fun newStoploss(
    triggerPrice: String,
    beyondDealPrice: Boolean,
    attorneyNum: String,
    attorneyPrice: String,
    tradeUnit: String,
    fixedUnit: String,
    dealType: DealType
  ): Observable<Spot>

  fun getSurplus(
    key: String,
    fixCoin: String
  ): Observable<Spot>

  fun getSurplusByPersistent(
    tradeUnit: String,
    fixCoinUnit: String
  ): Observable<Spot>

  fun getSpotTradeInfoByPersistent(pair: String): Observable<Spot>

  fun pairs(): Observable<Spot>

  fun pieData(coincode: String): Observable<Spot>

  fun industryNews(): Observable<Spot>

  fun legalCurrencies(): Observable<RefreshState<Currency, CurrencyItem>>

  fun legalCurrency(
    tradeUnit: String,
    page: Int,
    dealType: DealType
  ): Observable<RefreshState<Currency, CurrencyItem>>

  fun checkAuthStatus(): Observable<RefreshState<Currency, CurrencyItem>>

  fun checkPayTypeBindState(): Observable<RefreshState<Currency, CurrencyItem>>

  fun isStore(): Observable<RefreshState<Currency, CurrencyItem>>
}