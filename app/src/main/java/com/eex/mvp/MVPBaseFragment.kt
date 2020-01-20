package com.eex.mvp

import android.arch.lifecycle.DefaultLifecycleObserver
import android.arch.lifecycle.LifecycleObserver
import android.arch.lifecycle.LifecycleOwner
import android.content.Context
import android.os.Bundle
import android.support.annotation.LayoutRes
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.eex.common.base.LazyLoadFragment
import com.eex.dialogs.LoadingDialog
import com.eex.extensions.toast
import com.tencent.mmkv.MMKV
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.AndroidSupportInjection
import dagger.android.support.HasSupportFragmentInjector
import timber.log.Timber
import javax.inject.Inject

/**
 * MVPPlugin
 * 邮箱 784787081@qq.com
 */

abstract class MVPBaseFragment<VO, V : BaseView, T : BasePresenterImpl<V, VO>> : LazyLoadFragment(),
    HasSupportFragmentInjector, BaseView {
  @Inject
  lateinit var presenter: T
  @Inject
  lateinit var childFragmentInjector: DispatchingAndroidInjector<Fragment>
  @Inject
  lateinit var kv: MMKV

  @get:LayoutRes
  abstract val layoutId: Int
  private var mRootView: View? = null

  var mLoadingDialog: LoadingDialog? = null

  override fun onCreate(savedInstanceState: Bundle?) {
    isInit = true
    presenter.initPageState(arguments)
    presenter.attachView(this as V)
    super.onCreate(savedInstanceState)
    mLoadingDialog = LoadingDialog(activity as Context)
  }

  override fun onActivityCreated(savedInstanceState: Bundle?) {
    super.onActivityCreated(savedInstanceState)
    isCanLoadData()
  }

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {
    mRootView = inflater.inflate(layoutId, container, false)
    return mRootView
  }

  override fun setUserVisibleHint(isVisibleToUser: Boolean) {
    super.setUserVisibleHint(isVisibleToUser)
    if (isVisibleToUser) {
      Log.e("现在在这里：", this.javaClass.canonicalName)
    }
  }

  override fun onAttach(context: Context) {
    AndroidSupportInjection.inject(this)
    super.onAttach(context)
  }

  override fun onDestroyView() {
    super.onDestroyView()
    mRootView = null
  }

  override fun onDestroy() {
    super.onDestroy()
    presenter.detachView()
  }

  override fun supportFragmentInjector(): AndroidInjector<Fragment> {
    return childFragmentInjector
  }

  override fun showToast(msg: String) {
    toast { msg }
  }

  override fun showToast(resId: Int) {
    toast(resId)
  }

  override fun showProgress() {
    if (mLoadingDialog == null) {
      try {
        mLoadingDialog = LoadingDialog(activity)
        mLoadingDialog!!.show()
      } catch (e: Exception) {
      }

    } else {
      mLoadingDialog!!.show()
    }
  }

  override fun dissmissProgress() {
    try {
      if (mLoadingDialog != null) {
        mLoadingDialog!!.dismiss()
      }
    } catch (e: Exception) {
      e.printStackTrace()
    }
  }

  override fun showRefreshing() {

  }

  override fun hideRefreshing() {

  }

  override fun showLoadMore() {

  }

  override fun hideLoadMore() {

  }

  override fun lazyLoad() {
    presenter.onNewParams(arguments)
  }
}
