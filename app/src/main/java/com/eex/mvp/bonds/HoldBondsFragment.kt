package com.eex.mvp.bonds

import android.os.Bundle
import com.eex.constant.Keys
import com.eex.domin.entity.bonds.BondsType.HOLD

class HoldBondsFragment() : BondsFragment<HoldBondsPresenter>() {

  init {
    val param = Bundle()
    param.putInt(Keys.PARAM_BOND_TYPE, HOLD.value)
    arguments = param
  }
}