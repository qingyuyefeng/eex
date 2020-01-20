package com.eex.repository.impl

import com.eex.extensions.filterResult
import com.eex.apis.AttorneyApi
import com.eex.apis.RetrofitService
import com.eex.constant.Constants
import com.eex.domin.entity.RefreshState
import com.eex.domin.entity.attorney.Attorney
import com.eex.domin.entity.attorney.AttorneyItem
import com.eex.domin.entity.attorney.DealType
import com.eex.repository.AttorneyRepo
import com.eex.repository.mapper.attorney.AttorneyMapper
import com.eex.repository.mapper.attorney.CancelAttorneyMapper
import com.eex.repository.mapper.attorney.StoplossMapper
import com.tencent.mmkv.MMKV
import io.reactivex.Observable
import javax.inject.Inject

class AttorneyRepoImpl @Inject constructor(
  val service: RetrofitService,
  val mmkv: MMKV
) : AttorneyRepo {
  override fun attorneyList(
    page: Int,
    type: String,
    dealType: DealType
  ) = service.createApi(AttorneyApi::class.java)
      .attorneyList(mmkv.decodeString(Constants.TOKEN_ID,""), page, type,dealType.value)
      .filterResult()
      .map(AttorneyMapper)

  override fun stoplossList(
    page: Int,
    dealType: DealType
  ) = service.createApi(AttorneyApi::class.java)
      .stoplossList(mmkv.decodeString(Constants.TOKEN_ID,""), page,dealType.value)
      .filterResult()
      .map(StoplossMapper)

  override fun cancel(
    id: String,
    tradeUnit: String,
    fixedUnit: String
  ): Observable<RefreshState<Attorney, AttorneyItem>> = service.createApi(AttorneyApi::class.java)
      .cancelDelegation(mmkv.decodeString(Constants.TOKEN_ID,""), id,tradeUnit,fixedUnit)
      .filterResult()
      .map(CancelAttorneyMapper)
}