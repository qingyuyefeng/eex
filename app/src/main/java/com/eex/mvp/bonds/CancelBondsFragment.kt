package com.eex.mvp.bonds

import android.os.Bundle
import com.eex.constant.Keys
import com.eex.domin.entity.bonds.BondsType.CANCEL
import com.eex.domin.entity.bonds.BondsType.HOLD

class CancelBondsFragment() : BondsFragment<CancelBondsPresenter>() {

  init {
    val param = Bundle()
    param.putInt(Keys.PARAM_BOND_TYPE, CANCEL.value)
    arguments = param
  }
}