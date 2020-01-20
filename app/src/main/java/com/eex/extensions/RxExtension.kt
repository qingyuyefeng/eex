package com.eex.extensions

import com.eex.common.base.Data
import com.eex.exceptions.InvalidAuthException
import com.eex.exceptions.ServerException
import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

fun <T> Observable<T>.asyncAssign(): Observable<T> =
  this.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())

fun <T> Flowable<T>.asyncAssign(): Flowable<T> =
  this.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())

fun <T : Data<*>> Observable<T>.filterResult(): Observable<T> =
  this.filter {
      when {
          it.code == 10001 || it.code == 10002 -> throw InvalidAuthException(it.msg)
          it.isStauts -> return@filter true
          else -> throw ServerException(it.msg)
      }
  }

fun <T, SUB> Observable<T>.subcribeSubState(
  distinct: Boolean = true,
  map: (T) -> SUB
): Observable<SUB> {
  val observable = this.filter {
    map(it) != null
  }
      .map {
        map(it)
      }
  return if (distinct) observable
      .distinctUntilChanged() else observable
}