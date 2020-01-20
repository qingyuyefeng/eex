package com.eex.mvp.mine.tradingrecord.coinmoney.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.eex.R
import com.eex.domin.entity.dealrecord.StopLossBean
import com.link184.kidadapter.base.BaseAdapter
import com.link184.kidadapter.base.BaseViewHolder
import java.math.BigDecimal

/**
 * @Description:
 * @Author: xq
 * @CreateDate: 2019/12/31 17:44
 */
class StopLossAdapter(
        val context: Context,
        val list: MutableList<StopLossBean>,
        val type: Int = 1
) : BaseAdapter<StopLossBean, StopLossAdapter.CurrentHolder>(list) {

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): CurrentHolder {
        return CurrentHolder(LayoutInflater.from(context).inflate(R.layout.re_item_stoploss_current, p0, false))
    }

    inner class CurrentHolder(view: View) : BaseViewHolder<StopLossBean>(view) {
        val tv_unit_pair = view.findViewById<TextView>(R.id.tv_unit_pair)
        val tv_deal_type = view.findViewById<TextView>(R.id.tv_deal_type)
        val tv_buy_price = view.findViewById<TextView>(R.id.tv_buy_price)
        val tv_delkey = view.findViewById<TextView>(R.id.tv_delkey)
        val tv_quantity = view.findViewById<TextView>(R.id.tv_quantity)
        val tv_trig_status = view.findViewById<TextView>(R.id.tv_trigger_status)
        val tv_time = view.findViewById<TextView>(R.id.tv_time)
        val tv_cancel = view.findViewById<TextView>(R.id.tv_cancel)
        @SuppressLint("SetTextI18n")
        override fun bindView(item: StopLossBean) {
            tv_unit_pair.text = item.coinCode
            when (item.dealType) {
                2 -> {
                    tv_deal_type.text = context.getString(R.string.sell_out)
                    tv_deal_type.setBackgroundResource(R.drawable.corner_solid_small_red)
                }
                else -> {
                    tv_deal_type.text = context.getString(R.string.buy_in)
                    tv_deal_type.setBackgroundResource(R.drawable.corner_solid_small_green)
                }
            }
            tv_buy_price.text = item.delAmount.setScale(3,BigDecimal.ROUND_HALF_UP).toString()
            tv_delkey.text = item.fixPriceCoinCode
            tv_quantity.text = "数量：${item.delNum.setScale(3,BigDecimal.ROUND_HALF_UP)}"
            tv_time.text = item.createTime

            tv_cancel.visibility = if (type == 0) View.VISIBLE else View.GONE

            tv_trig_status.text = when (item.triggerState) {
                0 -> context.getString(R.string.un_trigger)
                1 -> context.getString(R.string.trigger_success)
                2 -> context.getString(R.string.trigger_fail)
                3 -> {
                    tv_cancel.visibility = View.GONE
                    context.getString(R.string.has_cancel)
                }
                else -> ""
            }


            tv_cancel.setOnClickListener {
                revocation?.revocate(item, layoutPosition)
            }
        }
    }

    private var revocation: Revocation? = null

    fun setRevocation(revocation: Revocation) {
        this.revocation = revocation
    }

    interface Revocation {
        fun revocate(bean: StopLossBean, pos: Int)
    }
}