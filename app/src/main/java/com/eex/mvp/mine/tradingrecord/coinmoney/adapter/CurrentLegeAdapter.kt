package com.eex.mvp.mine.tradingrecord.coinmoney.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.eex.R
import com.eex.domin.entity.dealrecord.CurrentRecordBean
import com.link184.kidadapter.base.BaseAdapter
import com.link184.kidadapter.base.BaseViewHolder
import java.math.BigDecimal
import java.text.NumberFormat

/**
 * @Description:
 * @Author: xq
 * @CreateDate: 2019/12/31 17:44
 */
class CurrentLegeAdapter(
        val context: Context,
        val list: MutableList<CurrentRecordBean>
) : BaseAdapter<CurrentRecordBean, CurrentLegeAdapter.CurrentHolder>(list) {

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): CurrentHolder {
        return CurrentHolder(LayoutInflater.from(context).inflate(R.layout.re_item_coinmoney_current, p0, false))
    }

    inner class CurrentHolder(view: View) : BaseViewHolder<CurrentRecordBean>(view) {
        val tv_unit_pair = view.findViewById<TextView>(R.id.tv_unit_pair)
        val tv_deal_type = view.findViewById<TextView>(R.id.tv_deal_type)
        val tv_buy_price = view.findViewById<TextView>(R.id.tv_buy_price)
        val tv_delkey = view.findViewById<TextView>(R.id.tv_delkey)
        val tv_lege_quantity = view.findViewById<TextView>(R.id.tv_lege_quantity)
        val tv_rest_quantity = view.findViewById<TextView>(R.id.tv_rest_quantity)
        val tv_time = view.findViewById<TextView>(R.id.tv_time)
        val tv_status = view.findViewById<TextView>(R.id.tv_status)
        val tv_cancel = view.findViewById<TextView>(R.id.tv_cancel)
        @SuppressLint("SetTextI18n")
        override fun bindView(item: CurrentRecordBean) {
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
            tv_buy_price.text = item.delAmount.setScale(3, BigDecimal.ROUND_HALF_DOWN).toString()
            tv_delkey.text = item.fixPriceCoinCode
            when (item.index) {
                0 -> { //当前委托
                    tv_lege_quantity.text = "数量：${item.delNum.setScale(3, BigDecimal.ROUND_HALF_UP)}"
                    tv_rest_quantity.text = "剩余：${item.residueNum.setScale(3, BigDecimal.ROUND_HALF_UP)}"
//                /**
//                  * 委托状态(1未成交，2部分成交，3已完成，4部分成交已撤销 5已撤销)
//                  */
//              var delStatus: Int = 0
                    tv_status.text = when (item.delStatus) {
                        2 -> context.getString(R.string.some_deal)
                        3 -> context.getString(R.string.all_deal)
                        4 -> context.getString(R.string.some_deal_cancel)
                        5 -> context.getString(R.string.has_cancel)
                        else -> context.getString(R.string.not_deal)
                    }
                    tv_cancel.visibility = View.VISIBLE
                }
                1, 2 -> { //历史委托,成交记录
                    tv_lege_quantity.text = "成交量：${item.delNum.setScale(3, BigDecimal.ROUND_HALF_UP)}"
                    val ave = item.aveAmount ?: BigDecimal(0)
                    tv_rest_quantity.text = "均价：${ave.setScale(3, BigDecimal.ROUND_HALF_UP)} ${item.fixPriceCoinCode}"
                    tv_status.text = "手续费：${NumberFormat.getInstance().format(item.serviceCharge)}"
                    tv_cancel.visibility = View.GONE
                }
            }
            tv_time.text = item.createTime
            tv_cancel.isEnabled = item.canClick
            tv_cancel.setOnClickListener {
                revocation?.revocate(item, layoutPosition)
            }
        }
    }

    fun setEnable(item: CurrentRecordBean) {
        item.canClick = false
    }

    private var revocation: Revocation? = null

    fun setRevocation(revocation: Revocation) {
        this.revocation = revocation
    }

    interface Revocation {
        fun revocate(bean: CurrentRecordBean, pos: Int)
    }
}