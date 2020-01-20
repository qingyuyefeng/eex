package com.eex.dialogs

import android.content.Context
import android.os.Bundle
import android.support.v4.content.res.ResourcesCompat
import android.support.v7.widget.LinearLayoutManager
import com.eex.R
import com.link184.kidadapter.setUp
import com.link184.kidadapter.simple.SingleKidAdapter
import kotlinx.android.synthetic.main.dialog_currency_select.currency_list
import kotlinx.android.synthetic.main.dialog_currency_select.tv_cancel
import kotlinx.android.synthetic.main.dialog_currency_select.tv_ok
import kotlinx.android.synthetic.main.item_trade_type.view.item_type
import kotlinx.android.synthetic.main.item_trade_type.view.op_container

class CurrencySelectDialog(
  val cxt: Context,
  val currencys: List<String>,
  val onItemClick: (String) -> Unit
) : BottomDialog(cxt) {
  private var selectIdx = 0
  lateinit var adapter: SingleKidAdapter<String>

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.dialog_currency_select)
    tv_ok.setOnClickListener {
      onItemClick(currencys[selectIdx])
      dismiss()
    }
    tv_cancel.setOnClickListener {
      dismiss()
    }
    adapter = currency_list.setUp<String> {
      withLayoutManager {
        LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
      }
      withLayoutResId(R.layout.item_trade_type)
      withItems(currencys.toMutableList())
      bindIndexed { currency, index ->
        item_type.text = currency
        if (index == selectIdx) {
          item_type.setTextColor(ResourcesCompat.getColor(resources, R.color.color_0080ff, null))
          op_container.setBackgroundColor(
              ResourcesCompat.getColor(resources, R.color.color_f2f4f9, null)
          )
        } else {
          item_type.setTextColor(ResourcesCompat.getColor(resources, R.color.color_4d6599, null))
          op_container.setBackgroundColor(ResourcesCompat.getColor(resources, R.color.white, null))
        }
        setOnClickListener {
          selectIdx = index
          lightOk()
          adapter.notifyDataSetChanged()
        }
      }
    }
  }

  private fun lightOk() {
    tv_ok.setTextColor(ResourcesCompat.getColor(cxt.resources, R.color.color_4d6599, null))
  }

}