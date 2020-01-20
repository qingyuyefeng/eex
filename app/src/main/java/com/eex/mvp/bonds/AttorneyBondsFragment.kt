package com.eex.mvp.bonds

import android.os.Bundle
import com.eex.constant.Keys
import com.eex.domin.entity.bonds.BondsType.DELEGATION
import com.eex.domin.entity.bonds.BondsType.HOLD

class AttorneyBondsFragment() : BondsFragment<AttorneyBondsPresenter>() {

  init {
    val param = Bundle()
    param.putInt(Keys.PARAM_BOND_TYPE, DELEGATION.value)
    arguments = param
  }
}