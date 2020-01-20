package com.eex.mvp.bonds

import android.os.Bundle
import com.eex.constant.Keys
import com.eex.domin.entity.RefreshState
import com.eex.domin.entity.attorney.AttorneyStatus
import com.eex.domin.entity.attorney.AttorneyStatus.UN_TRADE
import com.eex.domin.entity.bonds.BondDealType
import com.eex.domin.entity.bonds.BondDelegationType
import com.eex.domin.entity.bonds.BondItem
import com.eex.domin.entity.bonds.Bonds
import com.eex.domin.entity.bonds.BondsType
import com.eex.domin.entity.bonds.BondsType.DELEGATION
import com.eex.domin.entity.bonds.TradeAgeType
import com.eex.extensions.asyncAssign
import com.eex.extensions.ifNull
import com.eex.mvp.bonds.BondsContract.View
import com.eex.mvp.refresh.RefreshBasePresenter
import com.eex.repository.impl.BondsRepoImpl
import com.eex.subcribers.CompletionObserver

/**
 * MVPPlugin
 * 邮箱 784787081@qq.com
 */

open class BondsBaseFragmentPresenter(
  val repo: BondsRepoImpl
) : RefreshBasePresenter<Bonds, BondItem, View<BondItem>>() {

  init {
//    observeSubState({ it.pageState?.delegationDealType ?: BondDelegationType.ALL },
//        { loadInitial() })
//    observeSubState({ it.pageState?.tradeType ?: BondDealType.ALL }, { loadInitial() })
//    observeSubState({ it.pageState?.ageType ?: TradeAgeType.ALL }, { loadInitial() })
//    observeSubState({ it.pageState?.pair ?: "" }, { loadInitial() })

    observeSubState({ it.pageState ?: Bonds()},
        { loadInitial() })
  }

  override fun list(page: Int) {
    voObservable.value?.apply {
      repo.getBonds(
          page,
          pageState?.bondsType ?: DELEGATION,
          pageState?.pair ?: "",
          pageState?.tradeType ?: BondDealType.ALL,
          pageState?.delegationDealType ?: BondDelegationType.ALL,
          pageState?.ageType ?: TradeAgeType.ALL
      )
          .doOnSubscribe(doOnSubscriber)
          .asyncAssign()
          .subscribe(listObserver(page))
    }
  }

  override fun initPageState(params: Bundle?) {
    voObservable.value = voObservable.value.ifNull {
      RefreshState(
          pageState = Bonds(
              bondsType = BondsType.from(
                  params?.getInt(Keys.PARAM_BOND_TYPE, BondsType.HOLD.value) ?: BondsType.HOLD.value
              )
          )
      )
    }
  }

  override fun onNewParams(params: Bundle?) {
    val delegationType =
      BondDelegationType.from(
          params?.getInt(Keys.PARAM_BOND_DELEGATION_TYPE, BondDelegationType.ALL.value)
              ?: BondDelegationType.ALL.value
      )
    val dealType = BondDealType.from(
        params?.getInt(Keys.PARAM_BOND_DEAL_TYPE, BondDealType.ALL.value) ?: BondDealType.ALL.value
    )
    val ageType = TradeAgeType.from(
        params?.getInt(Keys.PARAM_TRADE_AGE_TYPE, TradeAgeType.ALL.value) ?: TradeAgeType.ALL.value
    )
    val currency = params?.getString(Keys.PARAM_CURRENCY) ?: ""
    voObservable.value = voObservable.value?.copy(
        pageState = voObservable.value?.pageState?.copy(
            delegationDealType = delegationType,
            tradeType = dealType,
            ageType = ageType,
            pair = currency
        )
    )
  }

  fun cancel(
    id: String,
    pair: String,
    delegationType: BondDelegationType,
    attorneyStatus: AttorneyStatus
  ) {
    if (attorneyStatus != UN_TRADE) {
      mView?.showToast("只有未成交才能撤单")
      return
    }
    voObservable.value?.apply {
      mView?.showProgress()
      repo.cancel(id, pair.split("/")[0], pair.split("/")[1], delegationType)
          .doOnSubscribe(doOnSubscriber)
          .asyncAssign()
          .subscribe(object : CompletionObserver<RefreshState<Bonds, BondItem>>() {
            override fun onDone() {
              mView?.dissmissProgress()
            }

            override fun onNext(t: RefreshState<Bonds, BondItem>) {
              loadInitial()
            }
          })
    }
  }

}
