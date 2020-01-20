package com.eex.domin.entity.attorney

import com.eex.domin.entity.attorney.AttorneyType.NORMAL
import com.eex.domin.entity.attorney.DealType.BOTH

data class Attorney(
  val delegateType: AttorneyType = NORMAL,
  val type: String = "",
  val dealType: DealType = BOTH,
  val cancelState: Boolean = false
)