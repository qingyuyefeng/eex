package com.eex.mvp.refresh

import com.eex.Config
import com.eex.domin.entity.LoadMoreState
import com.eex.domin.entity.LoadMoreState.STATUS_DEFAULT
import com.eex.domin.entity.LoadMoreState.STATUS_FAIL
import com.eex.domin.entity.LoadMoreState.STATUS_LOADING
import com.eex.domin.entity.RefreshState
import com.eex.mvp.BasePresenterImpl
import com.eex.mvp.refresh.RefreshContract.View
import com.eex.subcribers.CompletionObserver

/**
 * MVPPlugin
 * 邮箱 784787081@qq.com
 */

abstract class RefreshBasePresenter<T, ITEM, V : View<ITEM>> : BasePresenterImpl<V, RefreshState<T, ITEM>>() {

  init {
    observeSubState({ it.items }, {
      if (it.isEmpty()) {
        mView?.showEmpty()
      } else {
        mView?.refreshList(it)
        mView?.hideEmpty()
      }
    })
    observeSubState({ it.loadMoreState }, {
      mView?.refreshLoadMoreView(it)
      when (it) {
        STATUS_FAIL -> mView?.enableLoadMore()
        STATUS_LOADING -> mView?.enableLoadMore()
        else -> mView?.disableLoadMore()
      }
    })
  }

  private fun setLoadMoreState(loadMoreState: LoadMoreState) {
    voObservable.value?.apply {
      voObservable.value = copy(
          loadMoreState = loadMoreState
      )
    }
  }

  private fun setItems(
    page: Int,
    items: MutableList<ITEM>
  ) {
    voObservable.value?.apply {
      voObservable.value = copy(
          page = page,
          items = items
      )
    }
  }

  fun resetLoadMore() {
    setLoadMoreState(STATUS_LOADING)
  }

  abstract fun list(page: Int)

  protected fun listObserver(page: Int) =
    object : CompletionObserver<RefreshState<T, ITEM>>() {
      override fun onError(t: Throwable) {
        super.onError(t)
        voObservable.value?.apply {
          if (page > 1) {
            setLoadMoreState(STATUS_FAIL)
          }
        }
      }

      override fun onDone() {
        mView?.hideRefreshing()
      }

      override fun onNext(refreshState: RefreshState<T, ITEM>) {
        voObservable.value?.apply {
          val results = items + refreshState.items
          setLoadMoreState(
              if (refreshState.items.size >= Config.PAGE_SIZE)
                STATUS_LOADING
              else
                STATUS_DEFAULT
          )
          if (refreshState.items.size >= Config.PAGE_SIZE) {
            setItems(page, results as MutableList<ITEM>)
          } else {
            setItems(voObservable.value?.page ?: 1, results as MutableList<ITEM>)
          }
        }
      }
    }

  fun loadInitial() {
    mView?.showRefreshing()
    setItems(1, mutableListOf())
    list(1)
  }

  fun loadMore() {
    voObservable.value?.apply {
      list(page + 1)
    }
  }

  override fun handleViewState(vo: RefreshState<T, ITEM>) {

  }

}
