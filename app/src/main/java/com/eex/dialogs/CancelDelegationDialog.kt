package com.eex.dialogs

import android.content.Context
import android.os.Bundle
import com.eex.R
import kotlinx.android.synthetic.main.popwind_theorder.button_cancel
import kotlinx.android.synthetic.main.popwind_theorder.button_sure

class CancelDelegationDialog(
  cxt: Context,
  val callbk: () -> Unit
) : CenterDialog(cxt) {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    window?.setWindowAnimations(R.style.anim_popup_dir)
    setContentView(R.layout.popwind_theorder)
    button_sure.setOnClickListener {
      callbk()
      dismiss()
    }
    button_cancel.setOnClickListener {
      dismiss()
    }
  }

}