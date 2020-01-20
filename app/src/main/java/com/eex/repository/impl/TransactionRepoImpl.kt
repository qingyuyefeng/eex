package com.eex.repository.impl

import android.content.Context
import com.eex.R
import com.eex.WPConfig
import com.eex.apis.RetrofitService
import com.eex.apis.SecurityApi
import com.eex.apis.TransactionApi
import com.eex.common.websocket.RxWebSocket
import com.eex.constant.Constants
import com.eex.domin.entity.RefreshState
import com.eex.domin.entity.attorney.DealType
import com.eex.domin.entity.attorney.DealType.BUY
import com.eex.domin.entity.transaction.DealWay
import com.eex.domin.entity.transaction.currency.Currency
import com.eex.domin.entity.transaction.currency.CurrencyItem
import com.eex.domin.entity.transaction.spot.Spot
import com.eex.exceptions.InvalidAuthException
import com.eex.exceptions.ServerException
import com.eex.extensions.filterResult
import com.eex.market.bean.PurchaseDta
import com.eex.market.bean.bIDataVO
import com.eex.market.weight.Root
import com.eex.repository.TransationRepo
import com.eex.repository.mapper.transaction.AuthStatusMapper
import com.eex.repository.mapper.transaction.CheckStoreIdentityMapper
import com.eex.repository.mapper.transaction.CurrenciesMapper
import com.eex.repository.mapper.transaction.CurrencyConfigMapper
import com.eex.repository.mapper.transaction.DealOpBuyStateMapper
import com.eex.repository.mapper.transaction.DealOpSellStateMapper
import com.eex.repository.mapper.transaction.EmptySpotMapper
import com.eex.repository.mapper.transaction.IndustryNewMapper
import com.eex.repository.mapper.transaction.MarketQuotesMapper
import com.eex.repository.mapper.transaction.PairsMapper
import com.eex.repository.mapper.transaction.PayTypeBindStateMapper
import com.eex.repository.mapper.transaction.PersistentSurplusMapper
import com.eex.repository.mapper.transaction.PieChartDataMapper
import com.eex.repository.mapper.transaction.RecentDealRecordMapper
import com.eex.repository.mapper.transaction.SysPrecisionsMapper
import com.eex.repository.mapper.transaction.UsableQuantityMapper
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import com.tencent.mmkv.MMKV
import io.reactivex.Observable
import timber.log.Timber
import java.util.concurrent.TimeUnit.MILLISECONDS
import javax.inject.Inject

class TransactionRepoImpl @Inject constructor(
  val context: Context,
  val service: RetrofitService,
  val mmkv: MMKV,
  moshi: Moshi
) : TransationRepo {

  private val spotTradeInfoAdapter: JsonAdapter<Root<PurchaseDta?>?> =
    moshi.adapter(Types.newParameterizedType(Root::class.java, PurchaseDta::class.java))

  private val surplusAdapter: JsonAdapter<bIDataVO?> =
    moshi.adapter(bIDataVO::class.java)

  override fun checkTradeValid(
    pair: String,
    fixCoinUnit: String,
    dealType: DealType
  ): Observable<Spot> = service.createApi(TransactionApi::class.java)
      .dealPairs(fixCoinUnit)
      .filterResult().map(
          if (dealType == BUY) DealOpBuyStateMapper(pair) else DealOpSellStateMapper(pair)
      )

  override fun sysPrecisions(pair: String) = service.createApi(TransactionApi::class.java)
      .sysPrecisions()
      .filterResult().map(SysPrecisionsMapper(pair))

  override fun pairs(): Observable<Spot> = service.createApi(TransactionApi::class.java)
      .sysPrecisions()
      .filterResult().map(PairsMapper)

  override fun recentTransactions(pair: String): Observable<Spot> =
    service.createApi(TransactionApi::class.java)
        .recentTransactions(pair).filterResult().map(RecentDealRecordMapper)

  override fun markets(key: String): Observable<Spot> =
    service.createApi(TransactionApi::class.java)
        .markets(key).filter {
          if (it.isStauts) {
            return@filter true
          } else {
            throw ServerException(it.msg)
          }
        }
        .filter {
          it?.data != null
        }
        .map {
          MarketQuotesMapper.apply(it.data)
        }

  override fun newAttorney(
    dealWay: DealWay,
    dealNum: String,
    amount: String,
    tradeUnit: String,
    fixedUnit: String,
    dealType: DealType
  ) = service.createApi(TransactionApi::class.java)
      .createDelegation(
          mmkv.decodeString(Constants.TOKEN_ID, ""), dealWay.value.toString(), dealNum, amount,
          tradeUnit,
          fixedUnit, dealType.value
      ).filterResult().map(EmptySpotMapper)

  override fun newStoploss(
    triggerPrice: String,
    beyondDealPrice: Boolean,
    attorneyNum: String,
    attorneyPrice: String,
    tradeUnit: String,
    fixedUnit: String,
    dealType: DealType
  ) = service.createApi(TransactionApi::class.java)
      .createStoploss(
          mmkv.decodeString(Constants.TOKEN_ID, ""), triggerPrice,
          if (beyondDealPrice) "1" else "2",
          attorneyNum, attorneyPrice,
          tradeUnit,
          fixedUnit, dealType.value
      ).filterResult().map(EmptySpotMapper)

  override fun getSurplus(
    key: String,
    fixCoin: String
  ): Observable<Spot> =
    service.createApi(TransactionApi::class.java)
        .getSurplus(mmkv.decodeString(Constants.TOKEN_ID, ""), key).filterResult().map(
            UsableQuantityMapper(fixCoin)
        )

  override fun getSurplusByPersistent(
    tradeUnit: String,
    fixCoinUnit: String
  ): Observable<Spot> =
    RxWebSocket.get(WPConfig.WSUrl + "coinsocket/" + mmkv.decodeString(Constants.TOKEN_ID, ""))
        .filter {
          it.string != null
        }
        .map {
          Timber.w("剩余可用长连接==>${it.string}")
          surplusAdapter.fromJson(it.string)
        }
        .filter {
          it.coinCode == tradeUnit || it.coinCode == fixCoinUnit
        }
        .map {
          PersistentSurplusMapper(tradeUnit).apply(it)
        }.throttleWithTimeout(200, MILLISECONDS)

  override fun getSpotTradeInfoByPersistent(pair: String): Observable<Spot> =
    RxWebSocket.get(WPConfig.WSUrl + "websocket/" + pair)
        .filter {
          it.string != null
        }
        .map {
          Timber.w("市场信息长连接==>${it.string}")
          spotTradeInfoAdapter.fromJson(it.string)
        }.filter {
          it?.data != null && it?.datatype == "tick"
        }.map {
          return@map it?.run {
            data?.run {
              MarketQuotesMapper.apply(this)
            }
          } ?: Spot()
        }.throttleWithTimeout(1, MILLISECONDS)

  override fun pieData(coincode: String) =
    service.createApi(TransactionApi::class.java).pieCoinConfig().flatMap { data ->
      service.createApi(TransactionApi::class.java)
          .pieData(
              data.list.firstOrNull {
                it.keySet()
                    .contains(coincode)
              }?.get(coincode)?.toString()?.replace("\"", "") ?: ""
          )
    }.map(PieChartDataMapper)

  override fun industryNews() = service.createApi(TransactionApi::class.java)
      .newsType(mmkv.decodeString(Constants.TOKEN_ID, "")).filterResult()
      .map { origin ->
        origin.data.firstOrNull {
          it.categoryName == "行业快讯"
        }
            ?.id
      }
      .flatMap {
        service.createApi(TransactionApi::class.java)
            .news(it, 0)
      }.filterResult().map(IndustryNewMapper)

  override fun legalCurrencies(): Observable<RefreshState<Currency, CurrencyItem>> =
    service.createApi(TransactionApi::class.java)
        .legalCurrencyConfig()
        .filterResult().map(CurrencyConfigMapper)

  override fun legalCurrency(
    tradeUnit: String,
    page: Int,
    dealType: DealType
  ): Observable<RefreshState<Currency, CurrencyItem>> =
    service.createApi(TransactionApi::class.java)
        .legalCurrencies(tradeUnit, dealType.value, page)
        .filterResult()
        .map(CurrenciesMapper(dealType))

  override fun checkAuthStatus(): Observable<RefreshState<Currency, CurrencyItem>> =
    service.createApi(SecurityApi::class.java)
        .authStauts(mmkv.decodeString(Constants.TOKEN_ID, ""))
        .filter {
          if (it.code == 10002 || it.code == 10001) {
            throw InvalidAuthException(context.resources.getString(R.string.loginno))
          } else {
            return@filter true
          }
        }
        .filterResult()
        .map(AuthStatusMapper)

  override fun checkPayTypeBindState(): Observable<RefreshState<Currency, CurrencyItem>> =
    service.createApi(TransactionApi::class.java)
        .checkPayTypeBindState(mmkv.decodeString(Constants.TOKEN_ID, ""))
        .filterResult().map(PayTypeBindStateMapper)

  override fun isStore(): Observable<RefreshState<Currency, CurrencyItem>> =
    service.createApi(TransactionApi::class.java)
        .isStore(mmkv.decodeString(Constants.TOKEN_ID, ""))
        .filterResult().map(CheckStoreIdentityMapper)
}