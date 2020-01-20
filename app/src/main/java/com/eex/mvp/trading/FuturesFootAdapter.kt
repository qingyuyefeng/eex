package com.eex.mvp.trading

import android.content.Context
import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import com.eex.R
import com.eex.domin.entity.dealrecord.FuturesOrderBean
import com.eex.navigation.Navigator
import java.text.NumberFormat


/**
 * @Description:
 * @Author: xq
 * @CreateDate: 2019/12/28 16:03
 */
class FuturesFootAdapter(
        val context: Context,
        val list: MutableList<FuturesOrderBean>
) : RecyclerView.Adapter<FuturesFootAdapter.FuturesFootHolder>() {

    private var checkStopFull: CheckStopFull? = null

    fun setCheckStopFull(checkStopFull: CheckStopFull) {
        this.checkStopFull = checkStopFull
    }

    interface CheckStopFull {
        fun checkSF(pos: Int)

        fun clostOut(pos: Int)

        fun overNight(pos: Int)

        fun cancel(pos: Int)
    }

    fun setSocketNewData(data: IndexMarketList) {
        for (i in list.indices) {
            if (list[i].delkey == data.delKey) {
                data.newPrice?.let {
                    list[i].nowPrice = it.toDouble()
                    notifyItemChanged(i)
                }
                data.lastPrice?.let {
                    list[i].lastPrice = it.toDouble()
                }
            }
        }
    }

    fun setNewData(data: IndexMarketList) {
        for (i in list.indices) {
            if (list[i].delkey == data.delKey) {
                data.newPrice?.let {
                    list[i].nowPrice = it.toDouble()
                }
                data.lastPrice?.let {
                    list[i].lastPrice = it.toDouble()
                }
                data.cny?.let {
                    list[i].cny = it
                }
            }
        }
    }

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): FuturesFootHolder {
        return FuturesFootHolder(LayoutInflater.from(context).inflate(R.layout.re_itme_futures_foot, p0, false))
    }

    override fun onBindViewHolder(p0: FuturesFootHolder, p1: Int) {
        val item = list[p1]
        p0.tv_coin_name.text = item.coinCode
        val nowPrice = item.nowPrice ?: 0.0
        if (item.dealType == 1) {
            p0.iv_up_or_down.setBackgroundResource(R.drawable.buy_up_tv_bg)
            p0.iv_up_or_down.text = context.getString(R.string.buy_up)
        } else if (item.dealType == 2) {
            p0.iv_up_or_down.setBackgroundResource(R.drawable.buy_down_tv_bg)
            p0.iv_up_or_down.text = context.getString(R.string.buy_down)
        }

        when (item.index) {
            2 -> { //结算的盈亏
                p0.tv_now_price.text = "${NumberFormat.getInstance().format(item.opPrice)}"
                p0.tv_change.text = "${NumberFormat.getInstance().format(item.profitLoss)}"
                p0.tv_change.setTextColor(if (item.profitLoss >= 0) ContextCompat.getColor(context, R.color.color_00a546) else ContextCompat.getColor(context, R.color.color_a50000))
                p0.tv_change_rate.text =
                        if (item.earnestMoney != 0.0)
                            "${((item.profitLoss / item.earnestMoney * 10000).toInt()) / 100.0}%"
                        else "0%"
                p0.tv_change_rate.setTextColor(if (item.profitLoss >= 0) ContextCompat.getColor(context, R.color.color_00a546) else ContextCompat.getColor(context, R.color.color_a50000))
            }
            else -> {
                p0.tv_now_price.text = "${NumberFormat.getInstance().format(item.nowPrice)}"
                if (item.dealType == 1) { //买涨
                    val change = (nowPrice - item.delAmount) * item.delNum
                    p0.tv_change.text = "${NumberFormat.getInstance().format(change)}"
                    p0.tv_change.setTextColor(if (change >= 0) ContextCompat.getColor(context, R.color.color_00a546) else ContextCompat.getColor(context, R.color.color_a50000))
                    p0.tv_change_rate.text =
                            if (item.earnestMoney != 0.0)
                                "${((change / item.earnestMoney * 10000).toInt()) / 100.0}%"
                            else "0%"
                    p0.tv_change_rate.setTextColor(if (change >= 0) ContextCompat.getColor(context, R.color.color_00a546) else ContextCompat.getColor(context, R.color.color_a50000))
                } else if (item.dealType == 2) { //买跌
                    val change = (item.delAmount - nowPrice) * item.delNum
                    p0.tv_change.text = "${NumberFormat.getInstance().format(change)}"
                    p0.tv_change.setTextColor(if (change >= 0) ContextCompat.getColor(context, R.color.color_00a546) else ContextCompat.getColor(context, R.color.color_a50000))
                    p0.tv_change_rate.text =
                            if (item.earnestMoney != 0.0)
                                "${((change / item.earnestMoney * 10000).toInt()) / 100.0}%"
                            else "0%"
                    p0.tv_change_rate.setTextColor(if (change >= 0) ContextCompat.getColor(context, R.color.color_00a546) else ContextCompat.getColor(context, R.color.color_a50000))
                }
            }
        }

        p0.tv_rest_count.text = "数量：${item.delNum}个"
        p0.tv_buy_price.text = "买入价：${NumberFormat.getInstance().format(item.delAmount)}"
        p0.tv_stop_price.text = "止盈价：${NumberFormat.getInstance().format(item.targetProfitPrice)}"

        p0.ll_tvs.visibility = if (item.index == 1) View.VISIBLE else View.GONE
        p0.ll_cancel.visibility = if (item.index == 4) View.VISIBLE else View.GONE
        p0.tv_change.visibility = if(item.index == 3) View.INVISIBLE else View.VISIBLE
        p0.tv_change_rate.visibility = if(item.index == 3) View.INVISIBLE else View.VISIBLE

        p0.tv_button1.setOnClickListener {
            //止盈止损
            checkStopFull?.checkSF(p1)
        }
        p0.tv_button2.setOnClickListener {
            //平仓
            checkStopFull?.clostOut(p1)
        }
        p0.tv_button3.setOnClickListener {
            //过夜
            checkStopFull?.overNight(p1)
        }
        p0.tv_cancel.setOnClickListener {
            //撤销
            checkStopFull?.cancel(p1)
        }

        p0.itemView.setOnClickListener {
            Navigator.toFuturesOrderDetailActivity(context, item)
        }
    }

    override fun getItemCount(): Int = list.size

    inner class FuturesFootHolder(view: View) : RecyclerView.ViewHolder(view) {

        val tv_coin_name = view.findViewById<TextView>(R.id.tv_coin_name)
        val iv_up_or_down = view.findViewById<TextView>(R.id.tv_up_or_down)
        val tv_now_price = view.findViewById<TextView>(R.id.tv_now_price)
        val tv_change = view.findViewById<TextView>(R.id.tv_change)
        val tv_change_rate = view.findViewById<TextView>(R.id.tv_change_rate)
        val tv_rest_count = view.findViewById<TextView>(R.id.tv_rest_count)
        val tv_buy_price = view.findViewById<TextView>(R.id.tv_buy_price)
        val tv_stop_price = view.findViewById<TextView>(R.id.tv_stop_price)
        val ll_tvs = view.findViewById<LinearLayout>(R.id.ll_tvs)
        val tv_button1 = view.findViewById<TextView>(R.id.tv_button1)
        val tv_button2 = view.findViewById<TextView>(R.id.tv_button2)
        val tv_button3 = view.findViewById<TextView>(R.id.tv_button3)
        val ll_cancel = view.findViewById<LinearLayout>(R.id.ll_tv_cancel)
        val tv_cancel = view.findViewById<TextView>(R.id.tv_cancel)
    }
}
