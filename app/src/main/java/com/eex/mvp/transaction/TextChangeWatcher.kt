package com.eex.mvp.transaction

import android.text.TextWatcher

abstract class TextChangeWatcher : TextWatcher {
  var tempInput: String = ""

  override fun onTextChanged(
    s: CharSequence?,
    start: Int,
    before: Int,
    count: Int
  ) {

  }

  override fun beforeTextChanged(
    p0: CharSequence?,
    p1: Int,
    p2: Int,
    p3: Int
  ) {
  }
}