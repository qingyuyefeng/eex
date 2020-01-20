package com.eex.domin.entity.bonds

import com.eex.domin.entity.bonds.BondDelegationType.ALL
import com.eex.domin.entity.bonds.BondsType.DELEGATION

data class Bonds(
  val delegationDealType: BondDelegationType = ALL,
  val tradeType: BondDealType = BondDealType.ALL,
  val ageType: TradeAgeType = TradeAgeType.ALL,
  val pair: String = "",
  val pairs: List<String> = listOf(),
  val bondsType: BondsType = DELEGATION
)