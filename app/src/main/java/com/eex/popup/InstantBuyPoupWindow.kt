package com.eex.popup

import android.content.Context
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.View
import android.widget.EditText
import android.widget.PopupWindow
import android.widget.RadioGroup
import android.widget.RelativeLayout
import android.widget.TextView
import com.androidkun.xtablayout.XTabLayout
import com.androidkun.xtablayout.XTabLayout.Tab
import com.eex.R
import com.eex.domin.entity.attorney.DealType
import com.eex.domin.entity.attorney.DealType.BUY
import com.eex.domin.entity.attorney.DealType.SALE
import com.eex.domin.entity.transaction.currency.PayType
import com.eex.domin.entity.transaction.currency.PayType.ALIPAY
import com.eex.domin.entity.transaction.currency.PayType.BANK_CARD
import com.eex.domin.entity.transaction.currency.PayType.WEIXIN
import com.eex.extensions.withDefault
import java.math.BigDecimal

class InstantBuyPoupWindow(
  context: Context,
  val callbk: (DealType, PayType, BigDecimal) -> Unit
) : PopupWindow() {
  var dealType: DealType = BUY
  var payType: PayType = WEIXIN
  var fix_price_unit: TextView
  var price_unit: TextView
  var tv_deal_amount: EditText
  var tv_tips: TextView

  init {
    val container = LayoutInflater.from(context)
        .inflate(R.layout.window_buy_instant, null)
    this.contentView = container
    fix_price_unit = container.findViewById(R.id.fix_price_unit)
    price_unit = container.findViewById(R.id.price_unit)
    tv_deal_amount = container.findViewById(R.id.tv_deal_amount)
    tv_tips = container.findViewById(R.id.tv_tips)
    val deal_type = container.findViewById<XTabLayout>(R.id.deal_type)
    deal_type.addTab(deal_type.newTab().setText("购买"))
    deal_type.addTab(deal_type.newTab().setText("出售"))
    deal_type.setOnTabSelectedListener(object : XTabLayout.OnTabSelectedListener {
      override fun onTabReselected(tab: Tab?) {
      }

      override fun onTabUnselected(tab: Tab?) {
      }

      override fun onTabSelected(tab: Tab?) {
        tab?.apply {
          if (position == 0) {
            dealType = BUY
            fix_price_unit.visibility = View.GONE
            price_unit.visibility = View.VISIBLE
            tv_deal_amount.setHint(R.string.buy_instant_hint)
            tv_tips.setText(R.string.amount_trade)
          } else {
            dealType = SALE
            fix_price_unit.visibility = View.VISIBLE
            price_unit.visibility = View.GONE
            tv_deal_amount.setHint(R.string.sell_instant_hint)
            tv_tips.setText(R.string.number_sale)
          }
        }
      }
    })
    deal_type.getTabAt(0)
        ?.select()

    val radio_pay_type = container.findViewById<RadioGroup>(R.id.radio_pay_type)
    radio_pay_type.setOnCheckedChangeListener { group, i ->
      when (i) {
        R.id.pay_type_wechat -> {
          payType = WEIXIN
        }
        R.id.pay_type_alipay -> {
          payType = ALIPAY
        }
        R.id.pay_type_bank -> {
          payType = BANK_CARD
        }
      }
    }
    container.findViewById<RelativeLayout>(R.id.btn_buy_instant)
        .setOnClickListener {
          callbk(dealType, payType, BigDecimal(tv_deal_amount.text.toString().withDefault("0")))
          dismiss()
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
