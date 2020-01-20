package com.eex.popup

import android.content.Context
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.widget.PopupWindow
import android.widget.RadioGroup
import android.widget.RelativeLayout
import android.widget.TextView
import com.eex.R
import com.eex.dialogs.CurrencySelectDialog
import com.eex.domin.entity.bonds.BondDealType
import com.eex.domin.entity.bonds.BondDelegationType
import com.eex.domin.entity.bonds.BondDelegationType.ALL
import com.eex.domin.entity.bonds.BondDelegationType.MARKET_PRICE
import com.eex.domin.entity.bonds.BondDelegationType.TRIGGER
import com.eex.domin.entity.bonds.TradeAgeType

class BondsFiltratePoupWindow(
  context: Context,
  private var delegationDealType: BondDelegationType = ALL,
  private var bondDealType: BondDealType = BondDealType.ALL,
  private var ageType: TradeAgeType = TradeAgeType.ALL,
  private val currencys: List<String>,
  val callbk: (BondDelegationType, BondDealType, TradeAgeType, String) -> Unit
) : PopupWindow() {

  private var currency: String = ""

  init {
    val container = LayoutInflater.from(context)
        .inflate(R.layout.window_bonds_filtrate, null)
    this.contentView = container
    val radio_attorney = container.findViewById<RadioGroup>(R.id.radio_attorney)
    radio_attorney.setOnCheckedChangeListener { group, i ->
      when (i) {
        R.id.delegation_all -> {
          delegationDealType = ALL
        }
        R.id.delegation_market -> {
          delegationDealType = MARKET_PRICE
        }
        R.id.delegation_trigger -> {
          delegationDealType = TRIGGER
        }
      }
    }
    val radio_deal_type = container.findViewById<RadioGroup>(R.id.radio_deal_type)
    radio_deal_type.setOnCheckedChangeListener { group, i ->
      when (i) {
        R.id.deal_type_both -> {
          bondDealType = BondDealType.ALL
        }
        R.id.deal_call -> {
          bondDealType = BondDealType.CALL_OPTION
        }
        R.id.deal_put -> {
          bondDealType = BondDealType.PUT_OPTION
        }
      }
    }
    val radio_over_night = container.findViewById<RadioGroup>(R.id.radio_over_night)
    radio_over_night.setOnCheckedChangeListener { group, i ->
      when (i) {
        R.id.over_night_both -> {
          ageType = TradeAgeType.ALL
        }
        R.id.next_day -> {
          ageType = TradeAgeType.NEXT_DAY
        }
        R.id.daily -> {
          ageType = TradeAgeType.DAILY
        }
      }
    }
    when (delegationDealType) {
      ALL -> radio_attorney.check(R.id.delegation_all)
      MARKET_PRICE -> radio_attorney.check(R.id.delegation_market)
      TRIGGER -> radio_attorney.check(R.id.delegation_trigger)
    }
    when (bondDealType) {
      BondDealType.ALL -> radio_deal_type.check(R.id.deal_type_both)
      BondDealType.CALL_OPTION -> radio_deal_type.check(R.id.deal_call)
      BondDealType.PUT_OPTION -> radio_deal_type.check(R.id.deal_put)
    }
    when (ageType) {
      TradeAgeType.ALL->radio_over_night.check(R.id.over_night_both)
      TradeAgeType.NEXT_DAY->radio_over_night.check(R.id.next_day)
      TradeAgeType.DAILY->radio_over_night.check(R.id.daily)
    }
    val tv_currency_all = container.findViewById<TextView>(R.id.tv_currency_all)
    tv_currency_all.setOnClickListener {
      CurrencySelectDialog(context, currencys) {
        currency = it
        tv_currency_all.text = it
        tv_currency_all.setBackgroundResource(R.drawable.radio_filtrate_selected_bg)
      }.show()
    }
    container.findViewById<TextView>(R.id.btn_reset)
        .setOnClickListener {
          radio_attorney.check(R.id.delegation_all)
          radio_deal_type.check(R.id.deal_type_both)
          radio_over_night.check(R.id.over_night_both)
          tv_currency_all.text = "全部"
          tv_currency_all.setBackgroundResource(R.drawable.radio_filtrate_unselected_bg)
        }
    container.findViewById<TextView>(R.id.btn_ok)
        .setOnClickListener {
          callbk(delegationDealType, bondDealType, ageType, currency)
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
