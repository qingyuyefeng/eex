package com.eex.common.view

import android.content.Context
import android.graphics.drawable.ColorDrawable
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.eex.R
import com.eex.mvp.mine.tradingrecord.coinmoney.CoinChoose
import com.link184.kidadapter.base.BaseAdapter
import com.link184.kidadapter.base.BaseViewHolder

class FilterPoupWindow(
        val context: Context,
        val index: Int,
        val list: MutableList<CoinChoose>,
        private var attorny: Int = 1, //1普通委托 2止盈止损
        private var saleType: Int = 0, //0全部 1买入 2卖出
        private var dealStatus: Int = 0,  //1未成交，2部分成交，3已完成，4部分成交已撤销 5已撤销
        private var coinType: String = "all",
        val callbk: (Int, Int, Int, String) -> Unit
) : PopupWindow() {

    init {
        val container = LayoutInflater.from(context)
                .inflate(R.layout.window_orders_filter, null)
        this.contentView = container
        val radio_attorney = container.findViewById<RadioGroup>(R.id.radio_attorney)

        val radio_sale_type = container.findViewById<RadioGroup>(R.id.radio_sale_type)

        val radio_deal_status = container.findViewById<RadioGroup>(R.id.radio_deal_status)

        val ll_buy_or_sell = container.findViewById<LinearLayout>(R.id.ll_buy_or_sell)

        val ll_deal_status = container.findViewById<LinearLayout>(R.id.ll_deal_status)

        val ll_delegation_type = container.findViewById<LinearLayout>(R.id.ll_delegation_type)

        val ll_choose_coin_type = container.findViewById<LinearLayout>(R.id.ll_choose_coin_type)

        ll_delegation_type.visibility = if (index == 2) View.GONE else View.VISIBLE
        ll_deal_status.visibility = if (index != 0) View.GONE else View.VISIBLE
        ll_buy_or_sell.visibility = if (index == 0) View.GONE else View.VISIBLE
        ll_choose_coin_type.visibility = if (index != 1) View.VISIBLE else View.GONE

        radio_attorney.setOnCheckedChangeListener { group, i ->
            when (i) {
                R.id.attorney_normal -> {
                    attorny = 1
                    if (index == 0) {
                        ll_deal_status.visibility = View.VISIBLE
                        radio_deal_status.check(R.id.deal_type_all)
                    }
                }
                R.id.attorney_limit -> {
                    attorny = 2
                    if (index == 0) {
                        ll_deal_status.visibility = View.GONE
                        radio_deal_status.check(R.id.deal_type_all)
                    }
                }
            }
        }

        radio_deal_status.setOnCheckedChangeListener { group, checkedId ->
            when (checkedId) {
                R.id.deal_type_all -> {
                    dealStatus = 0
                }
                R.id.not_deal -> {
                    dealStatus = 1
                }
                R.id.some_deal -> {
                    dealStatus = 2
                }
            }
        }

        radio_sale_type.setOnCheckedChangeListener { group, i ->
            when (i) {
                R.id.sale_type_both -> {
                    saleType = 0
                }
                R.id.sale_buy -> {
                    saleType = 1
                }
                R.id.sale_sold -> {
                    saleType = 2
                }
            }
        }

        val rv_coin_type = container.findViewById<RecyclerView>(R.id.rv_coin_type)
        list.add(0, CoinChoose(context.getString(R.string.all_coin_type), true))
        val coinAdapter = CoinTypeAdapter(list)
        rv_coin_type.adapter = coinAdapter
        rv_coin_type.layoutManager = GridLayoutManager(context, 3)

        container.findViewById<TextView>(R.id.btn_reset)
                .setOnClickListener {
                    radio_attorney.check(R.id.attorney_normal)
                    radio_sale_type.check(R.id.sale_type_both)
                    coinAdapter.setCheck(0)
                }
        container.findViewById<TextView>(R.id.btn_ok)
                .setOnClickListener {
                    callbk(attorny, saleType, dealStatus, coinType)
                    dismiss()
                }
        when (attorny) {
            1 -> radio_attorney.check(R.id.attorney_normal)
            2 -> radio_attorney.check(R.id.attorney_limit)
        }
        when (saleType) {
            1 -> radio_sale_type.check(R.id.sale_type_both)
            2 -> radio_sale_type.check(R.id.sale_buy)
            3 -> radio_sale_type.check(R.id.sale_sold)
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

    inner class CoinTypeAdapter(private val list0: MutableList<CoinChoose>) : BaseAdapter<CoinChoose, CoinTypeAdapter.CoinTypeHolder>(list0) {

        fun setCheck(pos: Int) {
            list0.forEach {
                it.isChecked = false
            }
            list0[pos].isChecked = true
            notifyDataSetChanged()
            coinType = list0[pos].name
        }

        override fun onCreateViewHolder(p0: ViewGroup, p1: Int): CoinTypeHolder {
            return CoinTypeHolder(LayoutInflater.from(context).inflate(R.layout.item_coin_type_choose, p0, false))
        }

        inner class CoinTypeHolder(view: View) : BaseViewHolder<CoinChoose>(view) {
            val cb_coinType = view.findViewById<CheckBox>(R.id.cb_coin_type)
            override fun bindView(item: CoinChoose) {
                cb_coinType.text = item.name
                cb_coinType.isChecked = item.isChecked

                cb_coinType.setOnClickListener {
                    setCheck(layoutPosition)
                }
            }
        }
    }
}
