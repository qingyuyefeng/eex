package com.eex.mvp.refresh

import android.content.Context
import android.os.Bundle
import android.support.annotation.LayoutRes
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import com.link184.kidadapter.setUp
import com.link184.kidadapter.typed.TypedKidAdapter
import com.eex.domin.entity.LoadMoreState
import com.eex.domin.entity.RefreshState
import com.eex.mvp.MVPBaseFragment
import com.eex.views.LoadMoreHolder

abstract class RefreshBaseFragment<T, ITEM, V : RefreshContract.View<ITEM>, P : RefreshBasePresenter<T, ITEM, V>>
  : MVPBaseFragment<RefreshState<T, ITEM>, V, P>(),
    RefreshContract.View<ITEM> {
  abstract fun getRecyclerView(): RecyclerView

  companion object {
    const val TYPE_ITEM = "TYPE_ITEM"
    const val TYPE_LOADMORE = "TYPE_LOADMORE"
  }

  lateinit var adapter: TypedKidAdapter
  lateinit var loadMoreHolder: LoadMoreHolder

  override fun onActivityCreated(savedInstanceState: Bundle?) {
    super.onActivityCreated(savedInstanceState)
    loadMoreHolder = LoadMoreHolder(activity as Context) {
      presenter.resetLoadMore()
    }
    configRecyclerView(getRecyclerView())
  }

  fun configRecyclerView(recyclerView: RecyclerView) {
    adapter = recyclerView.setUp {
      withLayoutManager {
        LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
      }
      withViewType(TYPE_ITEM) {
        withLayoutResId(itemLayoutId())
        bindModelType(obtainItemCls())
        bind<ITEM> { item ->
          genLayoutContainer(this).bindItem(item)
        }
      }

      withViewType(TYPE_LOADMORE) {
        withLayoutResId(loadMoreHolder.layoutId)
        withItem(LoadMoreState.STATUS_DEFAULT)
        bind<LoadMoreState> { item ->
          loadMoreHolder.convert(this, item)
        }
      }
    }
    adapter.setLoadMoreEnable(true)
    adapter.setLoadMoreListener {
      presenter.loadMore()
    }
  }

  @LayoutRes abstract fun itemLayoutId(): Int

  abstract fun obtainItemCls(): Class<*>

  abstract fun genLayoutContainer(containerView: View): ItemLayoutContainer<ITEM>

  override fun showEmpty() {

  }

  override fun hideEmpty() {

  }

  override fun enableLoadMore() {
    adapter.setLoadMoreEnable(true)
  }

  override fun disableLoadMore() {
    adapter.setLoadMoreEnable(false)
  }

  override fun refreshLoadMoreView(state: LoadMoreState) {
    adapter update {
      replaceAllItems(mutableListOf(state), TYPE_LOADMORE)
    }
  }

  override fun onRefresh() {
    presenter.loadInitial()
  }
}