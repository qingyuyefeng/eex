package com.eex.mvp.mine.tradingrecord.legalorder.adapter

import android.content.Context
import android.support.v4.content.ContextCompat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.eex.R
import com.eex.domin.entity.dealrecord.LegalOrderBean
import com.link184.kidadapter.base.BaseAdapter
import com.link184.kidadapter.base.BaseViewHolder
import java.math.BigDecimal

/**
 * @Description:
 * @Author: xq
 * @CreateDate: 2020/1/5 14:16
 */
class LegalOrderAdapter(
        val context: Context,
        val list: MutableList<LegalOrderBean>
) : BaseAdapter<LegalOrderBean, LegalOrderAdapter.LegalHolder>(list) {
    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): LegalHolder {
        return LegalHolder(LayoutInflater.from(context).inflate(R.layout.re_item_legal_order, p0, false))
    }

    inner class LegalHolder(view: View) : BaseViewHolder<LegalOrderBean>(view) {
        val tv_name = view.findViewById<TextView>(R.id.tv_name)
        val tv_seller = view.findViewById<TextView>(R.id.tv_seller)
        val tv_buy_or_sell = view.findViewById<TextView>(R.id.tv_buy_or_sell)
        val tv_deal_state = view.findViewById<TextView>(R.id.tv_deal_state)
        val tv_deal_amount = view.findViewById<TextView>(R.id.tv_deal_amount)
        val tv_deal_quantity = view.findViewById<TextView>(R.id.tv_deal_quantity)
        val tv_ave_price = view.findViewById<TextView>(R.id.tv_ave_price)
        val tv_deal_number = view.findViewById<TextView>(R.id.tv_deal_number)
        override fun bindView(item: LegalOrderBean) {
            tv_name.text = item.coinCode
            tv_seller.visibility = if (item.userType == 2) View.VISIBLE else View.GONE
            if(item.transactionType == 1){
                tv_buy_or_sell.text = "买入"
                tv_buy_or_sell.setTextColor(ContextCompat.getColor(context,R.color.color_00a546))
                tv_buy_or_sell.setBackgroundColor(ContextCompat.getColor(context,R.color.color_3300a546))
            }else{
                tv_buy_or_sell.text = "卖出"
                tv_buy_or_sell.setTextColor(ContextCompat.getColor(context,R.color.color_a50000))
                tv_buy_or_sell.setBackgroundColor(ContextCompat.getColor(context,R.color.color_33a50000))
            }
            when(item.state){
                1->{
                    tv_deal_state.text = "待付款"
                    tv_deal_state.setTextColor(ContextCompat.getColor(context,R.color.color_0080ff))
                    tv_deal_state.setBackgroundColor(ContextCompat.getColor(context,R.color.color_330080ff))
                }
                2->{
                    tv_deal_state.text = "待收款"
                    tv_deal_state.setTextColor(ContextCompat.getColor(context,R.color.color_0080ff))
                    tv_deal_state.setBackgroundColor(ContextCompat.getColor(context,R.color.color_330080ff))
                }
                3->{
                    tv_deal_state.text = "已取消"
                    tv_deal_state.setTextColor(ContextCompat.getColor(context,R.color.color_cc3333))
                    tv_deal_state.setBackgroundColor(ContextCompat.getColor(context,R.color.color_33CC3333))
                }
                4->{
                    tv_deal_state.text = "已过期"
                    tv_deal_state.setTextColor(ContextCompat.getColor(context,R.color.color_FFAA00))
                    tv_deal_state.setBackgroundColor(ContextCompat.getColor(context,R.color.color_33ffaa00))
                }
                5->{
                    tv_deal_state.text = "已完成"
                    tv_deal_state.setTextColor(ContextCompat.getColor(context,R.color.color_00a546))
                    tv_deal_state.setBackgroundColor(ContextCompat.getColor(context,R.color.color_3300a546))
                }
            }
            tv_deal_amount.text = item.price.setScale(3,BigDecimal.ROUND_HALF_UP).toString()
            tv_deal_quantity.text = "${item.dealNum.setScale(3,BigDecimal.ROUND_HALF_UP)} ${item.coinCode}"
            tv_ave_price.text = item.unitPrice.setScale(3,BigDecimal.ROUND_HALF_UP).toString()
            tv_deal_number.text = "订单编号：${item.orderNo}"
        }
    }
}