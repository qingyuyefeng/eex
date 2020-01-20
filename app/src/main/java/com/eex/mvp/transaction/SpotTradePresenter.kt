package com.eex.mvp.transaction

import android.content.Context
import android.os.Bundle
import android.util.Log
import com.eex.R
import com.eex.constant.Constants
import com.eex.constant.Keys
import com.eex.domin.entity.attorney.DealType
import com.eex.domin.entity.attorney.DealType.BOTH
import com.eex.domin.entity.attorney.DealType.BUY
import com.eex.domin.entity.attorney.DealType.SALE
import com.eex.domin.entity.transaction.DealWay
import com.eex.domin.entity.transaction.DealWay.LIMIT
import com.eex.domin.entity.transaction.DealWay.MARKET
import com.eex.domin.entity.transaction.DealWay.STOPLOSS
import com.eex.domin.entity.transaction.spot.Spot
import com.eex.domin.entity.transaction.spot.Spot.FOCUS
import com.eex.domin.entity.transaction.spot.Spot.FOCUS.IDLE
import com.eex.domin.entity.transaction.spot.TransactionRecord
import com.eex.extensions.asyncAssign
import com.eex.extensions.limit
import com.eex.extensions.obtainMinCalculateScale
import com.eex.extensions.withDefault
import com.eex.mvp.BasePresenterImpl
import com.eex.mvp.transaction.SpotTradeContract.Presenter
import com.eex.mvp.transaction.SpotTradeContract.View
import com.eex.repository.impl.TransactionRepoImpl
import com.eex.subcribers.CompletionObserver
import com.google.gson.Gson
import com.tencent.mmkv.MMKV
import io.reactivex.disposables.Disposable
import java.math.BigDecimal
import javax.inject.Inject

/**
 * MVPPlugin
 * 邮箱 784787081@qq.com
 */

class SpotTradePresenter @Inject constructor(
  val context: Context,
  val mmkv: MMKV,
  val repo: TransactionRepoImpl
) : BasePresenterImpl<View, Spot>(),
    Presenter {
  private val persistentDisposables: MutableList<Disposable> = mutableListOf()

  private val listNormalLimit = 10
  private val listExtendLimit = 20

  init {
    observeSubState({ it.pair }, {
      mView?.setPairName(it)
      clearInput()
      disposePersistentLink()
      reload()
      resetState()
    })
    observeSubState({ it.isBuyOrSellValid }, {
      if (it) {
        mView?.validTradeButton()
      } else {
        mView?.inValidTradeButton()
      }
    })
    observeSubState({ it.dealType }, {
      if (it == BUY) {
        mView?.setBuyButton()
        mView?.showUsableWhileBuy(
            usableFixCoinQuantity.setScale(5, BigDecimal.ROUND_DOWN).toPlainString()
        )
      } else {
        mView?.setSellButton()
        mView?.showUsableWhileSell(
            usableCoinQuantity.setScale(5, BigDecimal.ROUND_DOWN).toPlainString()
        )
      }
      clearInput()
    })
    observeSubState({ it.tradeType }, {
      if (it == STOPLOSS) {
        mView?.showStoplossInputBox()
      } else {
        mView?.hideStoplossInputBox()
      }
      mView?.updateTradeTypeTitle(it.desc)
      clearInput()
      when (it) {
        MARKET -> {
          mView?.setPriceInputHint(context.getString(R.string.market_price))
          mView?.disablePriceInput()
          mView?.updateMarketPrice(
              latestDealAmount.toPlainString()
          )
        }
        else -> {
          mView?.setPriceInputHint(context.getString(R.string.trust_price))
          mView?.enablePriceInput()
        }
      }
    })
    observeSubState({ it.usableFixCoinQuantity }, {
      if (dealType == BUY) {
        mView?.showUsableWhileBuy(it.setScale(5, BigDecimal.ROUND_DOWN).toPlainString())
      }
    })
    observeSubState({ it.usableCoinQuantity }, {
      if (dealType == SALE) {
        mView?.showUsableWhileSell(it.setScale(5, BigDecimal.ROUND_DOWN).toPlainString())
      }
    })
    observeSubState({ it.triggerValuation }, {
      mView?.setTriggerValuation(it.toPlainString())
    })
    observeSubState({ it.attorneyValuation }, {
      mView?.setAttorneyValuation(it.toPlainString())
    })
    observeSubState({ it.dealTotalAmount }, {
      mView?.setDealTotalAmount(it.setScale(sysPriceScale, BigDecimal.ROUND_DOWN).toPlainString())
    })
    observeSubState({ it.latestDealAmount }, {
      mView?.updateLatestDealAmount(it.toPlainString())
      if (tradeType == MARKET) {
        mView?.updateMarketPrice(it.toPlainString())
      }
    })
    observeSubState({ it.latestIndexCnyAmount }, {
      mView?.updateLatestIndexCnyAmount(it.setScale(2, BigDecimal.ROUND_DOWN).toPlainString())
    })
    observeSubState({ it.windowType }, {
      reloadWindow(it)
      mView?.setCurrentDealTypeFiltrate(it)
    })
    observeSubState({ it.windowScale }, {
      reloadWindow(windowType)
      mView?.setCurrentWindowScaleFiltrate(it)
    })
    observeSubState({ it.marketBuyInfos }, {
      reloadWindow(windowType)
      mView?.drawDepthMap(it, sells)
    })
    observeSubState({ it.marketSellInfos }, {
      reloadWindow(windowType)
      mView?.drawDepthMap(buys, it)
    })
    observeSubState({ it.recentRecords }, {
      mView?.refreshRecentRecords(if (it.size > 50) it.subList(0, 50) else it)
    })
    observeSubState({ it.news }, {
      mView?.refreshNews(it)
    })
    observeSubState({ it.percent }, {
      mView?.switchPercent(it.multiply(BigDecimal(4)).toInt() - 1)
    })
    observeSubState({ it.pieChart }, {
      mView?.drawPieChart(it)
    })
  }

  val isLogin: Boolean
    get() = !mmkv.decodeString(Constants.TOKEN_ID).isNullOrEmpty()

  var pairName: String
    get() = voObservable.value?.pair?.replace("_", "/") ?: ""
    set(value) {
      voObservable.value?.run {
        voObservable.value = copy(
            pair = value,
            tradeUnit = value.split("_").getOrElse(0) { "" },
            fixedUnit = value.split("_").getOrElse(1) { "" }
        )
      }
    }

  var tradeType: DealWay
    get() = voObservable.value?.tradeType ?: LIMIT
    set(value) {
      voObservable.value?.run {
        voObservable.value = copy(
            tradeType = value
        )
      }
    }

  var dealType: DealType
    get() = voObservable.value?.dealType ?: BUY
    set(value) {
      voObservable.value?.run {
        voObservable.value = copy(
            dealType = value
        )
      }
    }

  var windowType: DealType
    get() = voObservable.value?.windowType ?: BOTH
    set(value) {
      voObservable.value?.run {
        voObservable.value = copy(
            windowType = value
        )
      }
    }

  var windowScale: Int
    get() = voObservable.value?.windowScale ?: 5
    set(value) {
      voObservable.value?.run {
        voObservable.value = copy(
            windowScale = value
        )
      }
    }

  val beyondDealPrice: Boolean
    get() = voObservable.value?.latestDealAmount ?: BigDecimal(
        0
    ) <= voObservable.value?.triggerPrice ?: BigDecimal(0)

  var triggerPrice: String
    get() = voObservable.value?.triggerPrice?.setScale(
        voObservable.value?.priceScale ?: 0, BigDecimal.ROUND_DOWN
    )
        ?.stripTrailingZeros()
        ?.toPlainString() ?: ""
    set(value) {
      var s = value
      if (s.startsWith(".")) {
        s = "0$s"
      }
      if (s.endsWith(".")) {
        s += "0"
      }
      if (s.endsWith("-")) {
        s = "0"
      }
      s.withDefault("0")
          .let {
            voObservable.value?.run {
              val rate =
                if (latestDealAmount.toInt() == 0) BigDecimal(
                    0
                ) else (latestIndexCnyAmount / latestDealAmount)
              voObservable.value = copy(
                  triggerPrice = BigDecimal(it),
                  triggerValuation = (BigDecimal(it) * rate).setScale(2, BigDecimal.ROUND_DOWN)
              )
            }
          }
    }

  var attorneyPrice: String
    get() = voObservable.value?.attorneyPrice?.setScale(
        voObservable.value?.priceScale ?: 0, BigDecimal.ROUND_DOWN
    )
        ?.stripTrailingZeros()
        ?.toPlainString() ?: ""
    set(value) {
      var s = value
      if (s.startsWith(".")) {
        s = "0$s"
      }
      if (s.endsWith(".")) {
        s += "0"
      }
      if (s.endsWith("-")) {
        s = "0"
      }
      s.withDefault("0")
          .let {
//              if (value.endsWith(".")) value.substring(0, value.lastIndexOf(".")) else value
            voObservable.value?.run {
              val rate =
                if (latestDealAmount == BigDecimal(0)) BigDecimal(
                    0
                ) else (latestIndexCnyAmount / latestDealAmount)
              voObservable.value = copy(
                  attorneyPrice = BigDecimal(it),
                  attorneyValuation = (BigDecimal(it) * rate).setScale(2, BigDecimal.ROUND_DOWN),
                  dealTotalAmount = BigDecimal(it) * attorneyNum
              )
            }
          }
    }

  var attorneyNum: String
    get() = voObservable.value?.attorneyNum?.setScale(
        voObservable.value?.numScale ?: 0, BigDecimal.ROUND_DOWN
    )
        ?.stripTrailingZeros()
        ?.toPlainString() ?: ""
    set(value) {
      var s = value
      if (s.startsWith(".")) {
        s = "0$s"
      }
      if (s.endsWith(".")) {
        s += "0"
      }
      if (s.endsWith("-")) {
        s = "0"
      }
      s.withDefault("0")
          .let {
            voObservable.value?.run {
              voObservable.value = copy(
                  attorneyNum = BigDecimal(it),
                  dealTotalAmount = BigDecimal(it) * attorneyPrice
              )
            }
          }
    }

  var percent: BigDecimal
    get() = voObservable.value?.percent ?: BigDecimal(0.25)
    set(value) {
      voObservable.value?.run {
        var num = BigDecimal(0)
        if (dealType == BUY) {
          if (attorneyPrice.toDouble() != 0.0) {
            num = usableFixCoinQuantity.divide(
                attorneyPrice, 10,
                BigDecimal.ROUND_HALF_UP
            ) * value
          }
        } else {
          num = usableCoinQuantity * value
        }
        voObservable.value = copy(
            percent = value,
            attorneyNum = num
        )
      }
    }

  var focus: FOCUS
    get() = voObservable.value?.focus ?: IDLE
    set(value) {
      voObservable.value?.run {
        voObservable.value = copy(
            focus = value
        )
      }
    }

  val tradeUnit: String
    get() = voObservable.value?.tradeUnit ?: ""

  val fixedUnit: String
    get() = voObservable.value?.fixedUnit ?: ""

  val buys: List<TransactionRecord>
    get() = voObservable.value?.marketBuyInfos ?: emptyList()

  val sells: List<TransactionRecord>
    get() = voObservable.value?.marketSellInfos ?: emptyList()

  val sysPriceScale: Int
    get() = voObservable.value?.priceScale ?: 0

  val sysNumScale: Int
    get() = voObservable.value?.numScale ?: 0

  val usableFixCoinQuantity: BigDecimal
    get() = voObservable.value?.usableFixCoinQuantity ?: BigDecimal(0)

  val usableCoinQuantity: BigDecimal
    get() = voObservable.value?.usableCoinQuantity ?: BigDecimal(0)

  val latestDealAmount: BigDecimal
    get() = voObservable.value?.latestDealAmount ?: BigDecimal(0)

  val marketSellInfos: List<TransactionRecord>
    get() = voObservable.value?.marketSellInfos ?: mutableListOf()

  val marketBuyInfos: List<TransactionRecord>
    get() = voObservable.value?.marketBuyInfos ?: mutableListOf()

  private fun reloadWindow(it: DealType) {
    when (it) {
      BOTH -> {
        mView?.showBuys(true)
        mView?.showSells(true)
        mView?.refreshBuys(marketBuyInfos.limit(listNormalLimit))
        mView?.refreshSells(marketSellInfos.limit(listNormalLimit))
      }
      BUY -> {
        mView?.showBuys(true)
        mView?.showSells(false)
        mView?.refreshBuys(marketBuyInfos)
      }
      else -> {
        mView?.showBuys(false)
        mView?.showSells(true)
        mView?.refreshSells(marketSellInfos)
      }
    }
  }

  fun addTriggerPrice() {
    voObservable.value?.apply {
      voObservable.value = copy(
          triggerPrice = triggerPrice.add(
              BigDecimal(this@SpotTradePresenter.triggerPrice.obtainMinCalculateScale())
          )
      )
    }
  }

  fun subtractTriggerPrice() {
    voObservable.value?.apply {
      voObservable.value = copy(
          triggerPrice = triggerPrice.subtract(
              BigDecimal(this@SpotTradePresenter.triggerPrice.obtainMinCalculateScale())
          )
      )
    }
  }

  fun addAttorneyPrice() {
    voObservable.value?.apply {
      voObservable.value = copy(
          attorneyPrice = attorneyPrice.add(
              BigDecimal(this@SpotTradePresenter.attorneyPrice.obtainMinCalculateScale())
          )
      )
    }
  }

  fun subtractAttorneyPrice() {
    voObservable.value?.apply {
      voObservable.value = copy(
          attorneyPrice = attorneyPrice.subtract(
              BigDecimal(this@SpotTradePresenter.attorneyPrice.obtainMinCalculateScale())
          )
      )
    }
  }

  fun addAttorneyNum() {
    voObservable.value?.apply {
      voObservable.value = copy(
          attorneyNum = attorneyNum.add(
              BigDecimal(this@SpotTradePresenter.attorneyNum.obtainMinCalculateScale())
          )
      )
    }
  }

  fun subtractAttorneyNum() {
    voObservable.value?.apply {
      voObservable.value = copy(
          attorneyNum = attorneyNum.subtract(
              BigDecimal(this@SpotTradePresenter.attorneyNum.obtainMinCalculateScale())
          )
      )
    }
  }

  fun querySurplus(startPersistent:Boolean = true) {
    voObservable.value?.apply {
      repo.getSurplus(pair, fixedUnit)
          .doOnSubscribe(doOnSubscriber)
          .asyncAssign()
          .subscribe(object : CompletionObserver<Spot>() {
            override fun onSubscribe(d: Disposable) {
              super.onSubscribe(d)
              persistentDisposables.add(d)
            }

            override fun onDone() {

            }

            override fun onNext(spot: Spot) {
              voObservable.value = voObservable.value?.copy(
                  usableFixCoinQuantity = spot.usableFixCoinQuantity,
                  usableCoinQuantity = spot.usableCoinQuantity
              )
              if (startPersistent) {
                getSurplusByPersistent()
              }
            }
          })
    }
  }

  fun getSurplusByPersistent() {
    voObservable.value?.apply {
      repo.getSurplusByPersistent(tradeUnit, fixedUnit)
          .doOnSubscribe(doOnSubscriber)
          .asyncAssign()
          .subscribe(object : CompletionObserver<Spot>() {
            override fun onSubscribe(d: Disposable) {
              super.onSubscribe(d)
              persistentDisposables.add(d)
            }

            override fun onDone() {

            }

            override fun onNext(spot: Spot) {
              voObservable.value = if (spot.updateCoinQuantity) voObservable.value?.copy(
                  usableCoinQuantity = spot.usableCoinQuantity
              ) else voObservable.value?.copy(
                  usableFixCoinQuantity = spot.usableFixCoinQuantity
              )
            }
          })
    }
  }

  fun checkTradeValid(dealType: DealType) {
    voObservable.value?.apply {
      mView?.showProgress()
      repo.checkTradeValid(pair, fixedUnit, dealType)
          .doOnSubscribe(doOnSubscriber)
          .asyncAssign()
          .subscribe(object : CompletionObserver<Spot>() {
            override fun onDone() {
              mView?.dissmissProgress()
            }

            override fun onNext(spot: Spot) {
              voObservable.value = voObservable.value?.copy(
                  isBuyOrSellValid = spot.isBuyOrSellValid
              )
            }
          })
    }
  }

  fun sysPrecisions() {
    voObservable.value?.apply {
      repo.sysPrecisions(pair)
          .doOnSubscribe(doOnSubscriber)
          .asyncAssign()
          .subscribe(object : CompletionObserver<Spot>() {
            override fun onDone() {
            }

            override fun onNext(spot: Spot) {
              voObservable.value = voObservable.value?.copy(
                  priceScale = spot.priceScale,
                  numScale = spot.numScale,
                  quantityMin = spot.quantityMin,
                  quantityMax = spot.quantityMax
              )
            }
          })
    }
  }

  fun queryMarketData() {
    voObservable.value?.apply {
      mView?.showProgress()
      repo.markets(pair)
          .doOnSubscribe(doOnSubscriber)
          .asyncAssign()
          .subscribe(object : CompletionObserver<Spot>() {
            override fun onDone() {
              mView?.dissmissProgress()
            }

            override fun onNext(spot: Spot) {
              updateMarketData(spot)
              queryMarketDataByPersistent()
            }
          })
    }
  }

  fun queryMarketDataByPersistent() {
    voObservable.value?.apply {
      repo.getSpotTradeInfoByPersistent(pair)
          .doOnSubscribe(doOnSubscriber)
          .asyncAssign()
          .subscribe(object : CompletionObserver<Spot>() {
            override fun onSubscribe(d: Disposable) {
              super.onSubscribe(d)
              persistentDisposables.add(d)
            }

            override fun onDone() {

            }

            override fun onNext(spot: Spot) {
              updateMarketData(spot)
            }
          })
    }
  }

  private fun updateMarketData(spot: Spot) {
    spot.recentRecords.addAll(voObservable.value?.recentRecords ?: mutableListOf())
    spot.marketBuyInfos.addAll(voObservable.value?.marketBuyInfos ?: mutableListOf())
    voObservable.value = voObservable.value?.copy(
        latestDealAmount = if (spot.latestDealAmount == BigDecimal(
                0
            )
        ) voObservable.value?.latestDealAmount ?: BigDecimal(
            0
        ) else spot.latestDealAmount,
        latestIndexCnyAmount = if (spot.latestIndexCnyAmount == BigDecimal(
                0
            )
        ) voObservable.value?.latestIndexCnyAmount ?: BigDecimal(
            0
        ) else spot.latestIndexCnyAmount,
        marketBuyInfos = spot.marketBuyInfos.limit(listExtendLimit).toMutableList(),
        marketSellInfos = spot.marketSellInfos.sortedWith(Comparator { a, b ->
          a.price.compareTo(b.price)
        }).limit(listExtendLimit).toMutableList(),
        recentRecords = spot.recentRecords.limit(listNormalLimit).toMutableList()
    )
  }

  fun newAttorney() {
    voObservable.value?.apply {
      mView?.showProgress()
      repo.newAttorney(
          tradeType, attorneyNum.toPlainString(), attorneyPrice.toPlainString(), tradeUnit,
          fixedUnit, dealType
      )
          .doOnSubscribe(doOnSubscriber)
          .asyncAssign()
          .subscribe(object : CompletionObserver<Spot>() {
            override fun onDone() {
              mView?.dissmissProgress()
            }

            override fun onNext(spot: Spot) {
              mView?.showToast("委托成功")
              querySurplus(false)
            }
          })
    }
  }

  fun newStoploss() {

    voObservable.value?.apply {
      mView?.showProgress()
      repo.newStoploss(
          triggerPrice.toPlainString(),
          beyondDealPrice,
          attorneyNum.toPlainString(), attorneyPrice.toPlainString(), tradeUnit,
          fixedUnit, dealType
      )
          .doOnSubscribe(doOnSubscriber)
          .asyncAssign()
          .subscribe(object : CompletionObserver<Spot>() {
            override fun onDone() {
              mView?.dissmissProgress()
            }

            override fun onNext(spot: Spot) {
              mView?.showToast("委托成功")
              querySurplus(false)
            }
          })
    }
  }

  fun getIndustryNews() {
    voObservable.value?.apply {
      repo.industryNews()
          .doOnSubscribe(doOnSubscriber)
          .asyncAssign()
          .subscribe(object : CompletionObserver<Spot>() {
            override fun onDone() {}

            override fun onNext(spot: Spot) {
              voObservable.value = voObservable.value?.copy(
                  news = spot.news.limit(listNormalLimit)
              )
            }
          })
    }
  }

  fun queryPieChartData() {
    voObservable.value?.apply {
      repo.pieData(tradeUnit)
          .doOnSubscribe(doOnSubscriber)
          .asyncAssign()
          .subscribe(object : CompletionObserver<Spot>() {
            override fun onDone() {}

            override fun onNext(spot: Spot) {
              voObservable.value = voObservable.value?.copy(
                  pieChart = spot.pieChart
              )
            }
          })
    }
  }

  override fun handleViewState(vo: Spot) {
  }

  override fun initPageState(params: Bundle?) {
    val pair = params?.getString(Keys.PARAM_PAIR) ?: ""
    voObservable.value = Spot(
        pair = pair,
        tradeUnit = pair.split("_").getOrElse(0) { "" },
        fixedUnit = pair.split("_").getOrElse(1) { "" }
    )
  }

  override fun onNewParams(params: Bundle?) {
    val pair = params?.getString(Keys.PARAM_PAIR) ?: voObservable.value?.pair ?: ""
    voObservable.value = voObservable.value?.copy(
        pair = pair,
        tradeUnit = pair.split("_").getOrElse(0) { "" },
        fixedUnit = pair.split("_").getOrElse(1) { "" }
    )
  }

  fun reload() {
    checkTradeValid(BUY)
    sysPrecisions()
    queryMarketData()
    querySurplus()
    queryPieChartData()
  }

  private fun disposePersistentLink() {
    persistentDisposables.forEach {
      if (!it.isDisposed) {
        it.dispose()
      }
    }
    persistentDisposables.clear()
  }

  private fun clearInput() {
    mView?.clearInput()
    voObservable.value?.run {
      voObservable.value = copy(
          triggerValuation = BigDecimal(0),
          attorneyValuation = BigDecimal(0),
          dealTotalAmount = BigDecimal(0),
          attorneyPrice = BigDecimal(0),
          attorneyNum = BigDecimal(0),
          triggerPrice = BigDecimal(0),
          percent = BigDecimal(0.25)
      )
    }
  }

  private fun resetState() {
    voObservable.value?.run {
      val newSpot = Spot()
      voObservable.value = newSpot.copy(
          index = index,
          pair = pair,
          futerspair = futerspair,
          pairs = pairs,
          windowScale = windowScale,
          tradeUnit = tradeUnit,
          fixedUnit = fixedUnit,
          quantityMax = quantityMax,
          priceScale = priceScale,
          numScale = numScale
      )
    }
  }

  fun beyondPriceLimit(input: String): Boolean {
    if (input.lastIndexOf(".") == -1) {
      return false
    }
    return (input.length - input.lastIndexOf(".")) > (sysPriceScale + 1)
  }

  fun limitByPriceScale(input: String): String {
    return if (beyondPriceLimit(input)) input.substring(
        0, input.lastIndexOf(".") + sysPriceScale + 1
    ) else input
  }

  fun beyondNumLimit(input: String): Boolean {
    if (input.lastIndexOf(".") == -1) {
      return false
    }
    return (input.length - input.lastIndexOf(".")) > (sysNumScale + 1)
  }

  fun limitByNumScale(input: String): String {
    return if (beyondNumLimit(input)) {
      input.substring(0, input.lastIndexOf(".") + sysNumScale + 1)
    } else {
      input
    }
  }
}
