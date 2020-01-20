package com.eex.extensions

import android.arch.convert.RxJavaConvert
import android.arch.lifecycle.LiveData

fun <T> LiveData<T>.toObservable() = RxJavaConvert.toObservable(this)