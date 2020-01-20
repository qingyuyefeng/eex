package com.link184.kidadapter.base

class LoadMoreHelper(var enable: Boolean = false) {
  var callbk: (() -> Unit)? = null

  fun setLoadMoreEnable(enable: Boolean) {
    this.enable = enable
  }

  fun setLoadMoreListener(callbk: () -> Unit) {
    this.callbk = callbk
  }

  fun loadMore() {
    if (enable && callbk != null) {
      callbk!!()
    }
  }
}