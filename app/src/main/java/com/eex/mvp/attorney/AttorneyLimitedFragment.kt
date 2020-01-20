package com.eex.mvp.attorney

import com.eex.domin.entity.attorney.AttorneyType.STOP

class AttorneyLimitedFragment : AttorneyBaseFragment<AttorneyLimitedPresenter>(){
  override fun onStart() {
    super.onStart()
    presenter.setDelegateType(STOP)
    onRefresh()
  }
}