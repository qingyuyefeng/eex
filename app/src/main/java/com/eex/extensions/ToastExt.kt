package com.eex.extensions

import android.content.Context
import android.support.annotation.StringRes
import android.support.v4.app.Fragment
import android.widget.Toast

fun Context.toast(value: String) = toast { value }

inline fun Context.toast(value: () -> String) =
  Toast.makeText(this, value(), Toast.LENGTH_SHORT).show()

inline fun Fragment.toast(value: () -> String) =
  Toast.makeText(activity, value(), Toast.LENGTH_SHORT).show()

fun Context.toast(@StringRes resId: Int) =
  Toast.makeText(this, resId, Toast.LENGTH_SHORT).show()

inline fun Fragment.toast(@StringRes resId: Int) =
  Toast.makeText(activity, resId, Toast.LENGTH_SHORT).show()

