package com.eex.domin.entity.bonds

import com.eex.domin.entity.attorney.AttorneyStatus
import com.eex.domin.entity.attorney.AttorneyStatus.UN_TRADE
import com.eex.domin.entity.bonds.BondDealType.ALL
import com.eex.domin.entity.bonds.BondDealType.CALL_OPTION
import java.math.BigDecimal

data class BondItem(
  val id:String = "",
  val pair: String = "",
  val bondDealType: BondDealType = ALL,
  val delegationType: BondDelegationType = BondDelegationType.ALL,
  val attorneyStatus: AttorneyStatus = UN_TRADE,
  val buyPrice: BigDecimal = BigDecimal(0),
  val tradeTime: String = "",
  val quantity: BigDecimal = BigDecimal(0),
  val balance: BigDecimal = BigDecimal(0),
  val balanceRate: BigDecimal = BigDecimal(0)
)