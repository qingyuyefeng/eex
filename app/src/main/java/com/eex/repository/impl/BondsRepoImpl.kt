package com.eex.repository.impl

import com.eex.apis.BondsApi
import com.eex.apis.RetrofitService
import com.eex.constant.Constants
import com.eex.domin.entity.RefreshState
import com.eex.domin.entity.bonds.BondDealType
import com.eex.domin.entity.bonds.BondDealType.ALL
import com.eex.domin.entity.bonds.BondDelegationType
import com.eex.domin.entity.bonds.BondItem
import com.eex.domin.entity.bonds.Bonds
import com.eex.domin.entity.bonds.BondsType
import com.eex.domin.entity.bonds.TradeAgeType
import com.eex.extensions.filterResult
import com.eex.repository.BondsRepo
import com.eex.repository.mapper.bonds.BondsMapper
import com.eex.repository.mapper.bonds.CancelBondMapper
import com.eex.repository.mapper.bonds.PairsMapper
import com.tencent.mmkv.MMKV
import io.reactivex.Observable
import javax.inject.Inject

class BondsRepoImpl @Inject constructor(
  val service: RetrofitService,
  val mmkv: MMKV
) : BondsRepo {
  private val PARAM_DEALTYPE = "dealType"
  private val PARAM_DELWAY = "delWay"
  private val PARAM_BONDSNIGHT = "bondsNight"

  override fun getBonds(
    page: Int,
    type: BondsType,
    delKey: String,
    dealType: BondDealType,
    delWay: BondDelegationType,
    bondsNight: TradeAgeType
  ): Observable<RefreshState<Bonds, BondItem>> {
    val extraParams = hashMapOf<String, String>()
    if (dealType != ALL) {
      extraParams[PARAM_DEALTYPE] = dealType.value.toString()
    }
    if (delWay != BondDelegationType.ALL) {
      extraParams[PARAM_DELWAY] = delWay.value.toString()
    }
    if (bondsNight != TradeAgeType.ALL) {
      extraParams[PARAM_BONDSNIGHT] = bondsNight.value.toString()
    }
    return service.createApi(BondsApi::class.java)
        .bonds(
            mmkv.decodeString(Constants.TOKEN_ID,""), page,
            type.value, delKey, extraParams
        )
        .filterResult()
        .map(BondsMapper)
  }

  override fun pairs(): Observable<Bonds> =
    service.createApi(BondsApi::class.java)
        .pairs().filterResult().map(PairsMapper)

  override fun cancel(
    id: String,
    coinCode: String,
    pricingCoin: String,
    delWay: BondDelegationType
  ): Observable<RefreshState<Bonds, BondItem>> =
    service.createApi(BondsApi::class.java)
        .cancel(id, coinCode, pricingCoin, delWay.value).filterResult().map(CancelBondMapper)
}