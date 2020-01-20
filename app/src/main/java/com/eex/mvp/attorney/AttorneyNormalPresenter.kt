package com.eex.mvp.attorney

import android.os.Bundle
import com.eex.domin.entity.attorney.AttorneyItem
import com.eex.domin.entity.attorney.AttorneyType.NORMAL
import com.eex.mvp.attorney.AttorneyContract.View
import com.eex.repository.impl.AttorneyRepoImpl
import javax.inject.Inject

class AttorneyNormalPresenter @Inject constructor(
  repo: AttorneyRepoImpl
) : AttorneyBasePresenter(repo) {
  override fun initPageState(params: Bundle?) {
    super.initPageState(params)
    setDelegateType(NORMAL)
  }
}