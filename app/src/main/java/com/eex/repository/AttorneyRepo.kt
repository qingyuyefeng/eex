package com.eex.repository

import com.eex.domin.entity.RefreshState
import com.eex.domin.entity.attorney.Attorney
import com.eex.domin.entity.attorney.AttorneyItem
import com.eex.domin.entity.attorney.DealType
import io.reactivex.Observable

interface AttorneyRepo {

  fun attorneyList(
    page: Int,
    type: String,
    dealType: DealType
  ): Observable<RefreshState<Attorney, AttorneyItem>>

  fun stoplossList(
    page: Int,
    dealType: DealType
  ): Observable<RefreshState<Attorney, AttorneyItem>>

  fun cancel(
    id: String,
    tradeUnit: String,
    fixedUnit: String
  ):Observable<RefreshState<Attorney, AttorneyItem>>
}