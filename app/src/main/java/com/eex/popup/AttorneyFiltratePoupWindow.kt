package com.eex.popup

import android.content.Context
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.widget.PopupWindow
import android.widget.RadioGroup
import android.widget.RelativeLayout
import android.widget.TextView
import com.eex.R
import com.eex.domin.entity.attorney.AttorneyType
import com.eex.domin.entity.attorney.AttorneyType.NORMAL
import com.eex.domin.entity.attorney.AttorneyType.STOP
import com.eex.domin.entity.attorney.DealType
import com.eex.domin.entity.attorney.DealType.BOTH
import com.eex.domin.entity.attorney.DealType.BUY
import com.eex.domin.entity.attorney.DealType.SALE

class AttorneyFiltratePoupWindow(
  context: Context,
  private var attorny: AttorneyType = NORMAL,
  private var saleType: DealType = BOTH,
  val callbk: (AttorneyType, DealType) -> Unit
) : PopupWindow() {

  init {
    val container = LayoutInflater.from(context)
        .inflate(R.layout.window_attorney_filtrate, null)
    this.contentView = container
    val radio_attorney = container.findViewById<RadioGroup>(R.id.radio_attorney)
    radio_attorney.setOnCheckedChangeListener { group, i ->
      when (i) {
        R.id.attorney_normal -> {
          attorny = NORMAL
        }
        R.id.attorney_limit -> {
          attorny = STOP
        }
      }
    }
    val radio_sale_type = container.findViewById<RadioGroup>(R.id.radio_sale_type)
    radio_sale_type.setOnCheckedChangeListener { group, i ->
      when (i) {
        R.id.sale_type_both -> {
          saleType = BOTH
        }
        R.id.sale_buy -> {
          saleType = BUY
        }
        R.id.sale_sold -> {
          saleType = SALE
        }
      }
    }
    container.findViewById<TextView>(R.id.btn_reset)
        .setOnClickListener {
          radio_attorney.check(R.id.attorney_normal)
          radio_sale_type.check(R.id.sale_type_both)
        }
    container.findViewById<TextView>(R.id.btn_ok)
        .setOnClickListener {
          callbk(attorny, saleType)
          dismiss()
        }
    when (attorny) {
      NORMAL -> radio_attorney.check(R.id.attorney_normal)
      STOP -> radio_attorney.check(R.id.attorney_limit)
    }
    when (saleType) {
      BOTH -> radio_sale_type.check(R.id.sale_type_both)
      BUY -> radio_sale_type.check(R.id.sale_buy)
      SALE -> radio_sale_type.check(R.id.sale_sold)
    }
    setStyle()
  }

  private fun setStyle() {
    this.width = RelativeLayout.LayoutParams.FILL_PARENT
    this.height = RelativeLayout.LayoutParams.WRAP_CONTENT
    this.isFocusable = true
    this.isOutsideTouchable = true
    val dw = ColorDrawable(-0x50000000)
    this.setBackgroundDrawable(dw)
  }
}
