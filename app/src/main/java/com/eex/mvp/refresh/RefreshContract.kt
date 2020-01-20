package com.eex.mvp.refresh

import com.eex.domin.entity.LoadMoreState
import com.eex.mvp.BasePresenter
import com.eex.mvp.BaseView

/**
 * MVPPlugin
 * 邮箱 784787081@qq.com
 */

class RefreshContract {
  interface View<ITEM> : BaseView {
    fun onRefresh()
    fun refreshList(items: List<ITEM>)
    fun showEmpty()
    fun hideEmpty()
    fun enableLoadMore()
    fun disableLoadMore()
    fun refreshLoadMoreView(state:LoadMoreState)
  }

  interface Presenter<V:View<ITEM>,ITEM> : BasePresenter<V>
}
