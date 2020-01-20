package com.eex.mvp.transaction

import android.os.Bundle
import com.eex.constant.Keys
import com.eex.domin.entity.transaction.spot.Spot
import com.eex.extensions.asyncAssign
import com.eex.mvp.BasePresenterImpl
import com.eex.mvp.transaction.SpotTradeContract.FragmentWrapperView
import com.eex.repository.impl.TransactionRepoImpl
import com.eex.subcribers.CompletionObserver
import javax.inject.Inject

class TransactionFragmentPresenter @Inject constructor(val repo: TransactionRepoImpl) :
    BasePresenterImpl<FragmentWrapperView, Spot>(),
    SpotTradeContract.FragmentWrapperPresenter {
  var pair: String
    get() = voObservable.value?.pair ?: ""
    set(value) {
      voObservable.value?.run {
        voObservable.value = copy(
            pair = value
        )
      }
    }

  val futerspair: String
    get() = voObservable.value?.futerspair ?: ""

  val index: Int
    get() = voObservable.value?.index ?: 0

  init {
    observeSubState({ it.pairs }, {
      if (pair.isEmpty()) {
        if (it.firstOrNull() == null) {
          mView?.renderFail()
        } else {
          mView?.renderViewPager(it.first())
          pair = it.first()
          mView?.updateFuturePair(futerspair)
        }
      } else {
        mView?.renderViewPager(pair)
      }
    })
    observeSubState({ it.pair }, {
      mView?.updatePair(it)
    })
    observeSubState({ it.futerspair }, {
      mView?.updateFuturePair(it)
    })
    observeSubState(false, { it.index }, {
      mView?.select(it)
    })
  }

  override fun initPageState(params: Bundle?) {
    val pair = params?.getString(Keys.PARAM_PAIR) ?: ""
    voObservable.value = Spot(
        pair = pair
    )
  }

  override fun onNewParams(params: Bundle?) {
    val pair = params?.getString(Keys.PARAM_PAIR) ?: voObservable.value?.pair ?: ""
    val futerspair =
      params?.getString(Keys.PARAM_PAIR_DATA) ?: voObservable.value?.futerspair ?: "BTC_USDT"
    val index = params?.getInt(Keys.TRANS_SELECT, 0) ?: 0
    voObservable.value = voObservable.value?.copy(
        pair = pair,
        futerspair = futerspair,
        index = index
    )
  }

  fun pairs() {
    voObservable.value?.run {
      mView?.showProgress()
      repo.pairs()
          .doOnSubscribe(doOnSubscriber)
          .asyncAssign()
          .subscribe(object : CompletionObserver<Spot>() {
            override fun onDone() {
              mView?.dissmissProgress()
            }

            override fun onNext(spot: Spot) {
              voObservable.value = voObservable.value?.copy(
                  pairs = spot.pairs
              )
            }
          })
    }
  }

  override fun handleViewState(vo: Spot) {
    // ignore
  }
}