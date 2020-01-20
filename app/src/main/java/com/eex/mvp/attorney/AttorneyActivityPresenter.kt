package com.eex.mvp.attorney

import android.os.Bundle
import com.eex.domin.entity.attorney.Attorney
import com.eex.mvp.BasePresenterImpl
import com.eex.mvp.attorney.AttorneyContract.ActivityView
import javax.inject.Inject

class AttorneyActivityPresenter @Inject constructor() : BasePresenterImpl<ActivityView, Attorney>(),
    AttorneyContract.ActivityPresenter {
  override fun initPageState(params: Bundle?) {

  }

  override fun handleViewState(vo: Attorney) {
    // ignore
  }
}