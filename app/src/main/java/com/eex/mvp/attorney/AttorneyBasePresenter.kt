package com.eex.mvp.attorney

import android.os.Bundle
import com.eex.domin.entity.RefreshState
import com.eex.domin.entity.attorney.Attorney
import com.eex.domin.entity.attorney.AttorneyItem
import com.eex.domin.entity.attorney.AttorneyType
import com.eex.domin.entity.attorney.AttorneyType.NORMAL
import com.eex.domin.entity.attorney.DealType
import com.eex.domin.entity.attorney.DealType.BOTH
import com.eex.extensions.asyncAssign
import com.eex.mvp.refresh.RefreshBasePresenter
import com.eex.repository.impl.AttorneyRepoImpl
import com.eex.subcribers.CompletionObserver

/**
 * MVPPlugin
 * 邮箱 784787081@qq.com
 */

open class AttorneyBasePresenter constructor(
  val repo: AttorneyRepoImpl
) : RefreshBasePresenter<Attorney, AttorneyItem, AttorneyContract.View<AttorneyItem>>() {

  override fun initPageState(params: Bundle?) {
    if (voObservable.value == null) {
      voObservable.value = RefreshState(
          pageState = Attorney()
      )
    }
  }

  fun setType(type: String) {
    voObservable.value?.apply {
      voObservable.value = voObservable.value?.copy(
          pageState = pageState?.copy(
              type = type
          ) ?: Attorney()
      )
    }
  }

  fun setDelegateType(type: AttorneyType) {
    voObservable.value?.apply {
      voObservable.value = copy(
          pageState = pageState?.copy(
              delegateType = type
          ) ?: Attorney()
      )
    }
  }

  fun getDelegateType() = voObservable?.value?.pageState?.delegateType ?: NORMAL

  fun setDealType(type: DealType) {
    voObservable.value?.apply {
      voObservable.value = copy(
          pageState = pageState?.copy(
              dealType = type
          ) ?: Attorney()
      )
    }
  }

  fun getDealType() = voObservable?.value?.pageState?.dealType ?: BOTH

  fun cancel(
    id: String,
    tradeUnit: String,
    fixedUnit: String
  ) {
    mView?.showProgress()
    repo.cancel(id, tradeUnit, fixedUnit)
        .doOnSubscribe(doOnSubscriber)
        .asyncAssign()
        .subscribe(object : CompletionObserver<RefreshState<Attorney, AttorneyItem>>() {
          override fun onDone() {
            mView?.dissmissProgress()
          }

          override fun onNext(t: RefreshState<Attorney, AttorneyItem>) {
            voObservable.value?.apply {
              voObservable.value = copy(
                  pageState = pageState?.copy(
                      cancelState = true
                  )
              )
            }
          }
        })
  }

  override fun list(page: Int) {
    voObservable.value?.apply {
      if (pageState?.delegateType == NORMAL) {
        normalList(page)
      } else {
        stopLossList(page)
      }
    }
  }

  private fun normalList(page: Int) {
    voObservable.value?.apply {
      repo.attorneyList(page, pageState?.type ?: "", pageState?.dealType ?: BOTH)
          .doOnSubscribe(doOnSubscriber)
          .asyncAssign()
          .subscribe(listObserver(page))
    }
  }

  private fun stopLossList(page: Int) {
    voObservable.value?.apply {
      repo.stoplossList(page, pageState?.dealType ?: BOTH)
          .doOnSubscribe(doOnSubscriber)
          .asyncAssign()
          .subscribe(listObserver(page))
    }
  }
}
