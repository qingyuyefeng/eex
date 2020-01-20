package com.eex.domin.entity

import com.eex.domin.entity.LoadMoreState.STATUS_DEFAULT
import com.eex.domin.entity.LoadMoreState.STATUS_LOADING

data class RefreshState<T, ITEM>(
  val loadMoreState: LoadMoreState = STATUS_LOADING,
  val page: Int = 1,
  val items: MutableList<ITEM> = mutableListOf(),
  val pageState: T? = null
)