package com.eex.mvp.transaction

import android.os.Bundle
import com.eex.constant.Constants
import com.eex.constant.Keys
import com.eex.domin.entity.RefreshState
import com.eex.domin.entity.attorney.DealType
import com.eex.domin.entity.attorney.DealType.BUY
import com.eex.domin.entity.attorney.DealType.SALE
import com.eex.domin.entity.security.Security
import com.eex.domin.entity.transaction.currency.Currency
import com.eex.domin.entity.transaction.currency.Currency.BindPayType
import com.eex.domin.entity.transaction.currency.Currency.BindPayType.BIND
import com.eex.domin.entity.transaction.currency.Currency.BindPayType.UNBIND
import com.eex.domin.entity.transaction.currency.Currency.SortType.ASC
import com.eex.domin.entity.transaction.currency.Currency.SortType.DESC
import com.eex.domin.entity.transaction.currency.Currency.StoreIdentityState
import com.eex.domin.entity.transaction.currency.Currency.StoreIdentityState.IDLE
import com.eex.domin.entity.transaction.currency.Currency.StoreIdentityState.IS_STORE
import com.eex.domin.entity.transaction.currency.Currency.StoreIdentityState.NOT_STORE
import com.eex.domin.entity.transaction.currency.CurrencyItem
import com.eex.extensions.asyncAssign
import com.eex.mvp.refresh.RefreshBasePresenter
import com.eex.mvp.transaction.CurrencyTradeContract.View
import com.eex.repository.impl.TransactionRepoImpl
import com.eex.subcribers.CompletionObserver
import com.tencent.mmkv.MMKV
import io.reactivex.Observable
import io.reactivex.ObservableSource
import io.reactivex.ObservableTransformer
import javax.inject.Inject

class CurrencyTradePresenter @Inject constructor(
  val mmkv: MMKV,
  val repo: TransactionRepoImpl
) : RefreshBasePresenter<Currency, CurrencyItem, View<CurrencyItem>>() {
  val isLogin: Boolean
    get() = !mmkv.decodeString(Constants.TOKEN_ID).isNullOrEmpty()

  val hasPhoneCertify: Boolean
    get() = mmkv.decodeString(Constants.PHONE_CERTIFY).orEmpty().isNotEmpty()

  var dealType: DealType
    get() = voObservable.value?.pageState?.dealType ?: BUY
    set(value) {
      voObservable.value?.run {
        voObservable.value = copy(
            pageState = voObservable.value?.pageState?.copy(
                dealType = value
            )
        )
        loadInitial()
      }
    }

  val level: Int
    get() = voObservable.value?.pageState?.security?.level ?: -1

  var pair: String
    get() = voObservable?.value?.pageState?.pair ?: ""
    set(value) {
      voObservable.value?.run {
        voObservable.value = copy(
            pageState = voObservable.value?.pageState?.copy(
                pair = value,
                tradeUnit = value.split("_").getOrElse(1) { "" }
            )
        )
      }
    }

  val currencies: List<String>
    get() = voObservable?.value?.pageState?.availableCurrencies ?: mutableListOf()

  var tradeId: String
    get() = voObservable.value?.pageState?.tradeId ?: ""
    set(value) {
      voObservable.value?.run {
        voObservable.value = copy(
            pageState = voObservable.value?.pageState?.copy(
                tradeId = value
            )
        )
      }
    }

  init {
    observeSubState({ it.pageState?.pair ?: "" }, {
      mView?.setPairName(it)
      loadInitial()
    })
    observeSubState({ it.pageState?.availableCurrencies ?: mutableListOf() }, {
      it.firstOrNull()
          ?.run {
            pair = this
          }
    })
    observeSubState({ it.pageState?.securityErr ?: false }, {
      if (it) {
        when (level) {
          1 -> {
            mView?.showRealNameDialog()
          }
          2, 3 -> {
            mView?.showBindPhoneDialog()
          }
        }
        resetSecurityState()
      }
    })
    observeSubState({ it.pageState?.goBuyPage ?: false }, {
      if (it) {
        if (dealType == BUY) {
          mView?.toPurchaseCurrency(tradeId)
        } else if (dealType == SALE) {
          mView?.toSellCurrency(tradeId)
        }
      }
      resetBindPayType()
    })
    observeSubState({ it.pageState?.hasBindPayType ?: BindPayType.IDLE }, {
      when (it) {
        UNBIND -> {
          mView?.showBindPayTypeDialog()
        }
      }
      resetBindPayType()
    })
    observeSubState({ it.pageState?.storeIdentityState ?: StoreIdentityState.IDLE }, {
      when (it) {
        NOT_STORE -> {
          mView?.showToast("您未申请商家不能发布广告")
        }
      }
      resetStoreIdentity()
    })
    observeSubState({ it.pageState?.goPublishPage ?: false }, {
      if (it) {
        mView?.toPublish()
      }
      resetStoreIdentity()
    })
    observeSubState({ it.pageState?.sortType ?: ASC }, {
      when (it) {
        ASC -> mView?.lightSort(false)
        DESC -> mView?.lightSort(true)
      }
    })
  }

  override fun list(page: Int) {
    voObservable.value?.apply {
      repo.legalCurrency(pageState?.tradeUnit ?: "", page, pageState?.dealType ?: BUY)
          .doOnSubscribe(doOnSubscriber)
          .asyncAssign()
          .subscribe(listObserver(page))
    }
  }

  override fun initPageState(params: Bundle?) {
    val pair = params?.getString(Keys.PARAM_PAIR) ?: ""
    voObservable.value = RefreshState(
        pageState = Currency(
//            pair = pair,
            security = Security(level = -1)
//            tradeUnit = pair.split("_").getOrElse(1) { "" }
        )
    )
  }

  override fun onNewParams(params: Bundle?) {
//    val pair = params?.getString(Keys.PARAM_PAIR) ?: ""
//    voObservable.value = voObservable.value?.copy(
//        pageState = voObservable.value?.pageState?.copy(
//            pair = pair,
//            tradeUnit = pair.split("_").getOrElse(1) { "" }
//        )
//    )
  }

  private fun resetBindPayType() {
    voObservable.value?.run {
      voObservable.value = copy(
          pageState = voObservable.value?.pageState?.copy(
              hasBindPayType = BindPayType.IDLE,
              goBuyPage = false
          )
      )
    }
  }

  private fun resetSecurityState() {
    voObservable.value?.run {
      voObservable.value = copy(
          pageState = voObservable.value?.pageState?.copy(
              security = Security(),
              securityErr = false
          )
      )
    }
  }

  private fun resetStoreIdentity() {
    voObservable.value?.run {
      voObservable.value = copy(
          pageState = voObservable.value?.pageState?.copy(
              storeIdentityState = StoreIdentityState.IDLE,
              goPublishPage = false
          )
      )
    }
  }

  fun loadAvailableCurrencies() {
    voObservable.value?.run {
      mView?.showProgress()
      repo.legalCurrencies()
          .doOnSubscribe(doOnSubscriber)
          .asyncAssign()
          .map { it.pageState ?: Currency() }
          .subscribe(object : CompletionObserver<Currency>() {
            override fun onDone() {
              mView?.dissmissProgress()
            }

            override fun onNext(t: Currency) {
              voObservable.value?.apply {
                voObservable.value = copy(
                    pageState = voObservable.value?.pageState?.copy(
                        availableCurrencies = t.availableCurrencies
                    ) ?: Currency()
                )
              }
            }
          })
    }
  }

  fun sort() {
    voObservable.value?.apply {
      if (pageState?.sortType == ASC) {
        items.sortedWith(Comparator { a, b ->
          if (a.creditRating == 2) {
            return@Comparator 1
          }
          if (b.creditRating == 1) {
            return@Comparator -1
          }
          return@Comparator if (a.creditRating > b.creditRating) 1 else -1
        })
      } else {
        items.sortedWith(Comparator { a, b ->
          if (a.creditRating == 2) {
            return@Comparator -1
          }
          if (b.creditRating == 1) {
            return@Comparator 1
          }
          return@Comparator if (a.creditRating <= b.creditRating) 1 else -1
        })
      }
      val newItems = mutableListOf<CurrencyItem>()
      newItems.addAll(items)
      voObservable.value = copy(
          items = newItems,
          pageState = voObservable.value?.pageState?.copy(
              sortType = if (pageState?.sortType == ASC) DESC else ASC
          )
      )
    }
  }

  fun publish() {
    voObservable.value?.apply {
      mView?.showProgress()
      repo.isStore()
          .filter {
            voObservable.postValue(
                voObservable.value?.copy(
                    pageState = voObservable.value?.pageState?.copy(
                        storeIdentityState = it.pageState?.storeIdentityState ?: IDLE
                    )
                )
            )
            return@filter it.pageState?.storeIdentityState == IS_STORE
          }
          .flatMap {
            repo.checkAuthStatus()
          }
          .compose(certifyProcess)
          .doOnSubscribe(doOnSubscriber)
          .asyncAssign()
          .subscribe(object : CompletionObserver<Currency>() {
            override fun onDone() {
              mView?.dissmissProgress()
            }

            override fun onNext(t: Currency) {
              voObservable.value?.apply {
                voObservable.value = copy(
                    pageState = voObservable.value?.pageState?.copy(
                        goPublishPage = t.hasBindPayType == BIND
                    ) ?: Currency()
                )
              }
            }
          })
    }
  }

  fun trade(id: String) {
    tradeId = id
    voObservable.value?.apply {
      mView?.showProgress()
      repo.checkAuthStatus()
          .compose(certifyProcess)
          .doOnSubscribe(doOnSubscriber)
          .asyncAssign()
          .subscribe(object : CompletionObserver<Currency>() {
            override fun onDone() {
              mView?.dissmissProgress()
            }

            override fun onNext(t: Currency) {
              voObservable.value?.apply {
                voObservable.value = copy(
                    pageState = voObservable.value?.pageState?.copy(
                        goBuyPage = t.hasBindPayType == BIND
                    ) ?: Currency()
                )
              }
            }
          })
    }
  }

  val certifyProcess =
    object : ObservableTransformer<RefreshState<Currency, CurrencyItem>, Currency> {
      override fun apply(upstream: Observable<RefreshState<Currency, CurrencyItem>>): ObservableSource<Currency> {
        return upstream.map {
          it.pageState ?: Currency()
        }
            .filter {
              val success = hasPhoneCertify &&
                  (it.security.level == 2
                      || it.security.level == 3)
              voObservable.postValue(
                  voObservable.value?.copy(
                      pageState = voObservable.value?.pageState?.copy(
                          security = it.security,
                          securityErr = !success
                      )
                  )
              )
              return@filter success
            }
            .flatMap {
              repo.checkPayTypeBindState()
            }
            .map { it.pageState ?: Currency() }
            .map {
              voObservable.postValue(
                  voObservable.value?.copy(
                      pageState = voObservable.value?.pageState?.copy(
                          hasBindPayType = it.hasBindPayType
                      )
                  )
              )
              it
            }
      }
    }
}