package com.eex.repository

import com.eex.domin.entity.RefreshState
import com.eex.domin.entity.bonds.BondDealType
import com.eex.domin.entity.bonds.BondDelegationType
import com.eex.domin.entity.bonds.BondItem
import com.eex.domin.entity.bonds.Bonds
import com.eex.domin.entity.bonds.BondsType
import com.eex.domin.entity.bonds.TradeAgeType
import io.reactivex.Observable

interface BondsRepo {
  fun pairs(): Observable<Bonds>

  fun getBonds(
    page: Int,
    type: BondsType,
    delKey: String,
    dealType: BondDealType,
    delWay: BondDelegationType,
    bondsNight: TradeAgeType
  ): Observable<RefreshState<Bonds, BondItem>>

  fun cancel(
    id: String,
    coinCode: String,
    pricingCoin: String,
    delWay: BondDelegationType
  ): Observable<RefreshState<Bonds, BondItem>>
}