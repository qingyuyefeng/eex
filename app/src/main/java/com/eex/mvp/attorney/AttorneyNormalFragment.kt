package com.eex.mvp.attorney

class AttorneyNormalFragment : AttorneyBaseFragment<AttorneyNormalPresenter>() {
  override fun onStart() {
    super.onStart()
    presenter.setType("1,2")
    onRefresh()
  }
}