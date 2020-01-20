package com.eex.views

import android.content.Context
import android.view.View
import com.eex.R
import com.eex.domin.entity.LoadMoreState
import com.eex.domin.entity.LoadMoreState.STATUS_DEFAULT
import com.eex.domin.entity.LoadMoreState.STATUS_END
import com.eex.domin.entity.LoadMoreState.STATUS_FAIL
import com.eex.domin.entity.LoadMoreState.STATUS_LOADING

class LoadMoreHolder(
  val context: Context,
  val retryCallbk: () -> Unit = {}
) {
  val layoutId: Int
    get() = R.layout.quick_view_load_more

  private val loadingViewId: Int
    get() = R.id.load_more_loading_view

  private val loadFailViewId: Int
    get() = R.id.load_more_load_fail_view

  private val loadEndViewId: Int
    get() = R.id.load_more_load_end_view

  fun convert(
    root: View,
    loadMoreState: LoadMoreState
  ) {
    bindClick(root, loadMoreState)
    when (loadMoreState) {
      STATUS_LOADING -> {
        visibleLoading(root, true)
        visibleLoadFail(root, false)
        visibleLoadEnd(root, false)
      }
      STATUS_FAIL -> {
        visibleLoading(root, false)
        visibleLoadFail(root, true)
        visibleLoadEnd(root, false)
      }
      STATUS_END -> {
        visibleLoading(root, false)
        visibleLoadFail(root, false)
        visibleLoadEnd(root, true)
      }
      STATUS_DEFAULT -> {
        visibleLoading(root, false)
        visibleLoadFail(root, false)
        visibleLoadEnd(root, false)
      }
    }
  }

  private fun bindClick(
    root: View,
    loadMoreState: LoadMoreState
  ) {
    root.setOnClickListener {
      when (loadMoreState) {
        STATUS_FAIL -> {
          retryCallbk()
        }
      }
    }
  }

  private fun visibleLoading(
    root: View,
    visible: Boolean
  ) {
    root.findViewById<View>(loadingViewId)
        .visibility = if (visible) View.VISIBLE else View.GONE
  }

  private fun visibleLoadFail(
    root: View,
    visible: Boolean
  ) {
    root.findViewById<View>(loadFailViewId)
        .visibility = if (visible) View.VISIBLE else View.GONE
  }

  private fun visibleLoadEnd(
    root: View,
    visible: Boolean
  ) {
    val loadEndViewId = loadEndViewId
    if (loadEndViewId != 0) {
      root.findViewById<View>(loadEndViewId)
          .visibility = if (visible) View.VISIBLE else View.GONE
    }
  }
}
