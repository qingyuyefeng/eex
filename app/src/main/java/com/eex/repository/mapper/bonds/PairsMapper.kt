package com.eex.repository.mapper.bonds

import com.eex.common.base.Data
import com.eex.domin.entity.bonds.Bonds
import com.eex.mvp.market.bean.DealPairList
import io.reactivex.functions.Function
import java.util.ArrayList

object PairsMapper : Function<Data<ArrayList<DealPairList>>, Bonds> {
  override fun apply(dto: Data<ArrayList<DealPairList>>): Bonds = Bonds(
      pairs = retrievePairs(dto)
  )
}

fun retrievePairs(dto: Data<ArrayList<DealPairList>>): List<String> {
  val results = mutableListOf<String>()
  dto.data.forEach {
    it.dealCoinDTOList.forEach { dealCoin ->
      results.add(dealCoin.dealPair)
    }
  }
  return results
}