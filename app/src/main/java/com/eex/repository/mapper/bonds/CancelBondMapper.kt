package com.eex.repository.mapper.bonds

import com.eex.common.base.Data
import com.eex.domin.entity.RefreshState
import com.eex.domin.entity.bonds.BondItem
import com.eex.domin.entity.bonds.Bonds
import io.reactivex.functions.Function

object CancelBondMapper : Function<Data<Any>, RefreshState<Bonds, BondItem>> {
  override fun apply(dto: Data<Any>): RefreshState<Bonds, BondItem> =
    RefreshState()
}