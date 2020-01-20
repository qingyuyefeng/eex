package com.eex.repository.mapper.transaction

import com.eex.common.base.Data
import com.eex.domin.entity.transaction.spot.IndustryNew
import com.eex.domin.entity.transaction.spot.Spot
import com.eex.home.bean.NewSdatalist
import com.eex.notice.bean.IndustryBean
import io.reactivex.functions.Function

object IndustryNewMapper : Function<Data<NewSdatalist<IndustryBean>>, Spot> {
  override fun apply(dto: Data<NewSdatalist<IndustryBean>>) = Spot(
      news = dto.data?.listData?.map {
        IndustryNew(
            id = it.id?:"",
            time = it.createTime?:"",
            title = it.title?:"",
            content = it.content?:"",
            source = it.categoryName?:""
        )
      } ?: emptyList()
  )
}