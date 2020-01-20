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

class FuturesFilterPoupWindow(
        val context: Context,
        val index: Int,
        val list: MutableList<CoinChoose>,
        private var attorny: Int = 0, //0全部 1市价交易 2触发交易
        private var dealType: Int = 0, //0全部 1买涨 2买跌
        private var ifOver: Int = -1, // -1全部 0不过夜 1过夜
        private var coinType: String = "all",
        val callbk: (Int, Int, Int, String) -> Unit
) : PopupWindow() {

    init {
        val container = LayoutInflater.from(context)
                .inflate(R.layout.window_futures_filter, null)
        this.contentView = container

        val rg_deleType = container.findViewById<RadioGroup>(R.id.radio_attorney)

        val rg_dealType = container.findViewById<RadioGroup>(R.id.radio_deal_status)

        val rg_ifOver = container.findViewById<RadioGroup>(R.id.rg_if_over_night)


        rg_deleType.setOnCheckedChangeListener { group, i ->
            attorny = group.indexOfChild(group.findViewById(i))
        }

        rg_dealType.setOnCheckedChangeListener { group, i ->
            dealType = group.indexOfChild(group.findViewById(i))
        }

        rg_ifOver.setOnCheckedChangeListener { group, i ->
            when (group.indexOfChild(group.findViewById(i))) {
                0 -> ifOver = -1
                1 -> ifOver = 1
                2 -> ifOver = 0
            }
        }

        val rv_coin_type = container.findViewById<RecyclerView>(R.id.rv_coin_type)
        list.add(0, CoinChoose(context.getString(R.string.all_coin_type), true))
        val coinAdapter = CoinTypeAdapter(list)
        rv_coin_type.adapter = coinAdapter
        rv_coin_type.layoutManager = GridLayoutManager(context, 3)

        container.findViewById<TextView>(R.id.btn_reset)
                .setOnClickListener {
                    rg_deleType.check(R.id.rb_dele_all)
                    rg_dealType.check(R.id.rb_deal_type_all)
                    rg_ifOver.check(R.id.rb_night_all)
                    coinAdapter.setCheck(0)
                }
        container.findViewById<TextView>(R.id.btn_ok)
                .setOnClickListener {
                    callbk(attorny, dealType, ifOver, coinType)
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
