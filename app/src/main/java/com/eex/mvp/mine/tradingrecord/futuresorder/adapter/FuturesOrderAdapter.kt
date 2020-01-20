package com.eex.mvp.mine.tradingrecord.futuresorder.adapter

import android.content.Context
import android.support.v4.content.ContextCompat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import com.eex.R
import com.eex.common.util.CommonUtil
import com.eex.domin.entity.dealrecord.FuturesOrderBean
import com.eex.domin.entity.dealrecord.NowData
import com.eex.navigation.Navigator
import com.link184.kidadapter.base.BaseAdapter
import com.link184.kidadapter.base.BaseViewHolder
import java.math.BigDecimal
import java.text.NumberFormat
import java.text.SimpleDateFormat

/**
 * @Description:
 * @Author: xq
 * @CreateDate: 2020/1/3 16:21
 */
class FuturesOrderAdapter(
        val context: Context,
        val list: MutableList<FuturesOrderBean>
) : BaseAdapter<FuturesOrderBean, FuturesOrderAdapter.FuturesHolder>(list) {

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): FuturesHolder {
        return FuturesHolder(LayoutInflater.from(context).inflate(R.layout.re_item_futures_order, p0, false))
    }

    inner class FuturesHolder(view: View) : BaseViewHolder<FuturesOrderBean>(view) {
        val tv_unit_pair = view.findViewById<TextView>(R.id.tv_unit_pair)
        val tv_deal_type = view.findViewById<TextView>(R.id.tv_deal_type)
        val tv_change = view.findViewById<TextView>(R.id.tv_change)
        val tv_change_rate = view.findViewById<TextView>(R.id.tv_change_rate)
        val tv_buy_price = view.findViewById<TextView>(R.id.tv_buy_price)
        val tv_quantity = view.findViewById<TextView>(R.id.tv_quantity)
        val tv_time = view.findViewById<TextView>(R.id.tv_time)
        val tv_cancel = view.findViewById<TextView>(R.id.tv_cancel)
//        val tv_has_close_out = view.findViewById<TextView>(R.id.tv_has_close_out)
//        val tv_has_cancel = view.findViewById<TextView>(R.id.tv_has_cancel)
        val ll_tvs = view.findViewById<LinearLayout>(R.id.ll_tvs)
        val tv_over_night = view.findViewById<TextView>(R.id.tv_over_night)
        val tv_full_stop = view.findViewById<TextView>(R.id.tv_full_stop)
        val tv_close_out = view.findViewById<TextView>(R.id.tv_close_out)
        override fun bindView(item: FuturesOrderBean) {
            val nowPrice = item.nowPrice?:0.0
            tv_unit_pair.text = if (item.delkey.contains("_")) item.delkey.replace("_", "/") else item.delkey
            if (item.dealType == 1) {
                tv_deal_type.setBackgroundResource(R.drawable.buy_up_tv_bg)
                tv_deal_type.text = context.getString(R.string.buy_up)

                val change = (nowPrice - item.delAmount) * item.delNum
                tv_change.text = "${BigDecimal(change).setScale(item.priceReservation, BigDecimal.ROUND_FLOOR)}"
                tv_change.setTextColor(if (change >= 0) ContextCompat.getColor(context, R.color.color_00a546) else ContextCompat.getColor(context, R.color.color_a50000))
                tv_change_rate.text =
                        if (item.earnestMoney != 0.0)
                            "${((change / item.earnestMoney * 10000).toInt()) / 100.0}%"
                        else "0%"
                tv_change_rate.setTextColor(if (change >= 0) ContextCompat.getColor(context, R.color.color_00a546) else ContextCompat.getColor(context, R.color.color_a50000))
            } else if (item.dealType == 2) {
                tv_deal_type.setBackgroundResource(R.drawable.buy_down_tv_bg)
                tv_deal_type.text = context.getString(R.string.buy_down)

                val change = (item.delAmount - nowPrice) * item.delNum
                tv_change.text = "${BigDecimal(change).setScale(item.priceReservation, BigDecimal.ROUND_FLOOR)}"
                tv_change.setTextColor(if (change >= 0) ContextCompat.getColor(context, R.color.color_00a546) else ContextCompat.getColor(context, R.color.color_a50000))
                tv_change_rate.text =
                        if (item.earnestMoney != 0.0)
                            "${((change / item.earnestMoney * 10000).toInt()) / 100.0}%"
                        else "0%"
                tv_change_rate.setTextColor(if (change >= 0) ContextCompat.getColor(context, R.color.color_00a546) else ContextCompat.getColor(context, R.color.color_a50000))
            }

            tv_buy_price.text = "买入价：${NumberFormat.getInstance().format(item.delAmount)}"
            tv_quantity.text = "数量：${CommonUtil.cutNumber(item.delNum,item.quantityRetention)}"
            tv_time.text = SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(item.createTime)
            when (item.index) {
                0 -> {  //当前委托
                    tv_cancel.visibility = View.VISIBLE
//                    tv_has_close_out.visibility = View.GONE
//                    tv_has_cancel.visibility = View.GONE
                    ll_tvs.visibility = View.GONE
                }
                1 -> {  //所有持仓
                    tv_cancel.visibility = View.GONE
//                    tv_has_close_out.visibility = View.GONE
//                    tv_has_cancel.visibility = View.GONE
                    ll_tvs.visibility = View.VISIBLE
                }
                2 -> {  //持仓结算
                    tv_cancel.visibility = View.GONE
//                    tv_has_close_out.visibility = View.VISIBLE
//                    tv_has_cancel.visibility = View.GONE
                    ll_tvs.visibility = View.GONE
                    tv_change.setTextColor(if (item.profitLoss >= 0) ContextCompat.getColor(context, R.color.color_00a546) else ContextCompat.getColor(context, R.color.color_a50000))
                    tv_change.text = NumberFormat.getInstance().format(item.profitLoss)
                    tv_change_rate.setTextColor(if (item.profitLoss >= 0) ContextCompat.getColor(context, R.color.color_00a546) else ContextCompat.getColor(context, R.color.color_a50000))
                    tv_change_rate.text = if (item.earnestMoney == 0.0)
                        "0%"
                    else
                        "${BigDecimal(item.profitLoss / item.earnestMoney * 100).setScale(2, BigDecimal.ROUND_HALF_UP)}%"
                }
                3 -> {  //持仓撤单
                    tv_cancel.visibility = View.GONE
//                    tv_has_close_out.visibility = View.GONE
//                    tv_has_cancel.visibility = View.VISIBLE
                    ll_tvs.visibility = View.GONE
                }
            }
            tv_change.visibility = if(item.index == 3) View.INVISIBLE else View.VISIBLE
            tv_change_rate.visibility = if(item.index == 3) View.INVISIBLE else View.VISIBLE

            tv_cancel.setOnClickListener {
                //撤单
                click?.cancel(layoutPosition)
            }
            tv_over_night.setOnClickListener {
                //过夜
                click?.overNight(layoutPosition)
            }
            tv_full_stop.setOnClickListener {
                //止盈止损
                click?.fullStop(layoutPosition)
            }
            tv_close_out.setOnClickListener {
                //平仓
                click?.closeOut(layoutPosition)
            }
            itemView.setOnClickListener {
                Navigator.toFuturesOrderDetailActivity(context,item)
            }
        }
    }

    fun setNewData(nowData: NowData) {
        list.forEach { item ->
            if (item.delkey == nowData.delKey) {
                nowData.newPrice?.let {
                    item.nowPrice = it.toDouble()
                }
                nowData.lastPrice?.let {
                    item.lastPrice = it.toDouble()
                }
                nowData.cny?.let {
                    item.cny = it
                }
            }
        }
    }

    private var click: TextViewClick? = null
    fun setClick(click: TextViewClick) {
        this.click = click
    }

    interface TextViewClick {
        fun cancel(pos:Int)
        fun overNight(pos:Int)
        fun fullStop(pos:Int)
        fun closeOut(pos:Int)
    }
}