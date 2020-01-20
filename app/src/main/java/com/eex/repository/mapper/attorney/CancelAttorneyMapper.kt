package com.eex.repository.mapper.attorney

import com.eex.common.base.Data
import com.eex.domin.entity.RefreshState
import com.eex.domin.entity.attorney.Attorney
import com.eex.domin.entity.attorney.AttorneyItem
import io.reactivex.functions.Function

object CancelAttorneyMapper : Function<Data<*>, RefreshState<Attorney, AttorneyItem>> {
  override fun apply(dto: Data<*>): RefreshState<Attorney, AttorneyItem> = RefreshState(
      pageState = Attorney(
          cancelState = true
      )
  )
}