package com.link184.kidadapter.base

interface LoadMoreInteractor {
  fun setLoadMoreListener(callbk: () -> Unit)
  fun setLoadMoreEnable(enable: Boolean)
}