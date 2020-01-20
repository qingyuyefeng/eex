package com.eex.repository.mapper.transaction

import com.eex.common.base.Data
import com.eex.domin.entity.RefreshState
import com.eex.domin.entity.attorney.DealType
import com.eex.domin.entity.transaction.currency.Currency
import com.eex.domin.entity.transaction.currency.CurrencyItem
import com.eex.domin.entity.transaction.currency.PayType
import com.eex.home.bean.Advertisingvo
import com.eex.home.bean.Page
import io.reactivex.functions.Function
import java.math.BigDecimal
import java.text.DecimalFormat

class CurrenciesMapper(val dealType: DealType) : Function<Data<Page<Advertisingvo>>, RefreshState<Currency, CurrencyItem>> {
  override fun apply(dto: Data<Page<Advertisingvo>>): RefreshState<Currency, CurrencyItem> =
    RefreshState(
        items = dto.data?.resultList?.map {
          CurrencyItem(
              dealType = dealType,
              id = it.id?:"",
              name = it.merchName ?: "",
              certified = it.merchantStatus == 2,
              creditRating = it.merchantStatus,
              tradeUnit = it.tradeCoin ?: "",
              tradeNum = it.tradeNum ?: BigDecimal(0),
              tradePrice = it.tradePrice ?: BigDecimal(0),
              boost = it.premium ?: BigDecimal(0),
              least = it.minTradeNum ?: BigDecimal(0),
              most = it.maxTradeNum ?: BigDecimal(0),
              dealCount = it.tradeCount ?: 0,
              dealCompleteCount = it.tradeOKCount ?: 0,
              dealCompleteRate = calculateRate(it.tradeCount ?: 0, it.tradeOKCount ?: 0),
              payTypes = it.accountType.map { type ->
                PayType.from(type)
              }
          )
        }?.toMutableList() ?: mutableListOf()
    )
}

fun calculateRate(
  dealCount: Int,
  successCount: Int
): String {
  if (dealCount == 0) {
    return "0"
  }
  val df = DecimalFormat("0.00")
  return df.format((successCount.toFloat() / dealCount * 100).toDouble())
}