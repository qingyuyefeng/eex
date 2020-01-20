package com.eex.mvp

import android.arch.lifecycle.LifecycleOwner
import androidx.annotation.StringRes

/**
 * MVPPlugin
 * 邮箱 784787081@qq.com
 */

interface BaseView : LifecycleOwner {
  fun showProgress()
  fun dissmissProgress()
  fun showRefreshing()
  fun hideRefreshing()
  fun showLoadMore()
  fun hideLoadMore()
  fun showToast(value: String)
  fun showToast(@StringRes resId: Int)

  enum class STATE {
    CONTENT,
    NO_DATA,
    ERROR,
    LOADING,
    REFRESHING,
    LOAD_MORE,
    TOAST
  }
}
