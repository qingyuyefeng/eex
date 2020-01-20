package com.eex.dialogs

import android.content.Context
import android.os.Bundle
import com.eex.R
import kotlinx.android.synthetic.main.dialog_c2cbuysell.btn_dimss
import kotlinx.android.synthetic.main.dialog_c2cbuysell.btn_sell
import kotlinx.android.synthetic.main.dialog_c2cbuysell.tltle

class StoplossAlertDialog(
  cxt: Context,
  val title: String,
  val callbk: () -> Unit = {}
) : CenterDialog(cxt) {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.dialog_c2cbuysell)
    tltle.text = title
    btn_dimss.setOnClickListener { dismiss() }
    btn_sell.setOnClickListener {
      dismiss()
      callbk()
    }
  }
}