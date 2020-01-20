package com.eex.repository.mapper.bonds

import com.eex.common.base.Data
import com.eex.domin.entity.RefreshState
import com.eex.domin.entity.attorney.AttorneyStatus
import com.eex.domin.entity.bonds.BondDealType
import com.eex.domin.entity.bonds.BondDealType.CALL_OPTION
import com.eex.domin.entity.bonds.BondDelegationType
import com.eex.domin.entity.bonds.BondItem
import com.eex.domin.entity.bonds.Bonds
import com.eex.extensions.toFullDate
import com.eex.home.bean.PageList
import com.eex.mvp.trading.IndexEntrustDTO
import io.reactivex.functions.Function
import java.math.BigDecimal

object BondsMapper : Function<Data<PageList<IndexEntrustDTO>>, RefreshState<Bonds, BondItem>> {
  override fun apply(dto: Data<PageList<IndexEntrustDTO>>): RefreshState<Bonds, BondItem> =
    RefreshState(
        items = dto.data?.resultList?.map {
          BondItem(
              id = it?.id ?: "",
              pair = it?.delkey ?: "",
              bondDealType = BondDealType.from(it?.dealType ?: 0),
              delegationType = BondDelegationType.from(it?.delWay ?: 0),
              attorneyStatus = AttorneyStatus.from(it?.delStatus ?: 1),
              buyPrice = BigDecimal(it?.aveAmount ?: 0.0).setScale(2, BigDecimal.ROUND_DOWN),
              tradeTime = it?.createTime?.toFullDate() ?: "",
              quantity = BigDecimal(it?.delNum ?: 0.0).setScale(2, BigDecimal.ROUND_DOWN),
              balance = it?.let { calculateBalance(it) } ?: BigDecimal(0),
              balanceRate = it?.let {
                calculateRate(
                    calculateBalance(it), BigDecimal(it.earnestMoney)
                )
              }
                  ?: BigDecimal(0)
          )
        }?.toMutableList() ?: mutableListOf()
    )
}

fun calculateBalance(it: IndexEntrustDTO): BigDecimal {
  val dealType = BondDealType.from(it.dealType ?: 0)
  val diff: BigDecimal = if (dealType == CALL_OPTION) {
    BigDecimal(it.opPrice ?: 0.0) - BigDecimal(it.aveAmount ?: 0.0)
  } else {
    BigDecimal(it.aveAmount ?: 0.0) - BigDecimal(it.opPrice ?: 0.0)
  }
  return diff.setScale(2, BigDecimal.ROUND_DOWN)
      .multiply(BigDecimal(it.delNum ?: 0.0))
      .setScale(2, BigDecimal.ROUND_DOWN)
}

fun calculateRate(
  balance: BigDecimal,
  ensureAmount: BigDecimal
): BigDecimal =
  ((balance / ensureAmount).setScale(2, BigDecimal.ROUND_DOWN) * BigDecimal(100)).setScale(
      2, BigDecimal.ROUND_DOWN
  )