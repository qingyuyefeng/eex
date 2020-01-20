package com.eex.mvp.bonds

import android.os.Bundle
import com.eex.domin.entity.bonds.Bonds
import com.eex.extensions.asyncAssign
import com.eex.mvp.BasePresenterImpl
import com.eex.mvp.bonds.BondsContract.ActivityPresenter
import com.eex.mvp.bonds.BondsContract.ActivityView
import com.eex.repository.impl.BondsRepoImpl
import com.eex.subcribers.CompletionObserver
import javax.inject.Inject

/**
 * MVPPlugin
 * 邮箱 784787081@qq.com
 */

class BondsPresenter @Inject constructor(val repo: BondsRepoImpl) : BasePresenterImpl<ActivityView, Bonds>(),
    ActivityPresenter {

  var bonds: Bonds
    get() = voObservable.value ?: Bonds()
    set(value) {
      voObservable.value?.run {
        voObservable.value = copy(
            delegationDealType = value.delegationDealType,
            tradeType = value.tradeType,
            ageType = value.ageType,
            pair = value.pair
        )
      }
    }

  val pairs: List<String>
    get() = voObservable.value?.pairs ?: listOf()

  override fun initPageState(params: Bundle?) {
    voObservable.value = Bonds()
  }

  fun getPairs() {
    mView?.showProgress()
    repo.pairs()
        .doOnSubscribe(doOnSubscriber)
        .asyncAssign()
        .subscribe(object : CompletionObserver<Bonds>() {
          override fun onDone() {
            mView?.dissmissProgress()
          }

          override fun onNext(bonds: Bonds) {
            voObservable.value?.apply {
              voObservable.value = copy(
                  pairs = bonds.pairs
              )
            }
          }

        })
  }

}
