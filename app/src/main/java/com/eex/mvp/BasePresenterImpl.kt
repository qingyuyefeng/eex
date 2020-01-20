package com.eex.mvp

import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.LifecycleObserver
import android.arch.lifecycle.LifecycleOwner
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Observer
import android.arch.lifecycle.OnLifecycleEvent
import android.os.Bundle
import com.eex.extensions.subcribeSubState
import com.eex.extensions.toObservable
import io.reactivex.disposables.Disposable
import io.reactivex.functions.Consumer
import org.jetbrains.annotations.NotNull
import timber.log.Timber

/**
 * MVPPlugin
 * 邮箱 784787081@qq.com
 */

abstract class BasePresenterImpl<V : BaseView, T> : BasePresenter<V>, LifecycleObserver {
  val voObservable: MutableLiveData<T> = MutableLiveData()
  val disposables: MutableList<Disposable> = mutableListOf()

  val doOnSubscriber = Consumer<Disposable> {
    disposables.add(it)
  }

  protected var mView: V? = null
  override fun attachView(view: V) {
    mView = view
    if (view is LifecycleOwner) {
      view.lifecycle.addObserver(this)
      voObservable.observe(view, Observer<T> {
        it?.apply {
//          Timber.v("Page State==>${this.hashCode()}|${this}")
          handleViewState(this)
        }
      })
    }
  }

  fun <SUB> observeSubState(
    mapper: (T) -> SUB,
    executor: (SUB) -> Unit
  ) = observeSubState(true, mapper, executor)

  fun <SUB> observeSubState(
    distinct: Boolean = true,
    mapper: (T) -> SUB,
    executor: (SUB) -> Unit
  ) {
    voObservable.toObservable()
        .subcribeSubState(distinct = distinct, map = mapper)
        .doOnSubscribe(doOnSubscriber)
        .subscribe {
          executor(it)
        }
  }

  open fun handleViewState(vo: T){}

  override fun onNewParams(params: Bundle?) {
    // ignore
  }

  override fun detachView() {
    mView = null
  }

  @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
  fun onDestroy(@NotNull owner: LifecycleOwner) {
    disposables.forEach {
      if (!it.isDisposed) {
        it.dispose()
      }
    }
    disposables.clear()
  }
}
