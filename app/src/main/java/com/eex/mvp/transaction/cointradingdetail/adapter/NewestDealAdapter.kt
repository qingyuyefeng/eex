package com.eex.mvp.transaction.cointradingdetail.adapter

import android.content.Context
import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.eex.R
import com.eex.market.bean.PurchaseDatalIst
import java.text.NumberFormat
import java.text.SimpleDateFormat

/**
 * @Description:
 * @Author: xq
 * @CreateDate: 2020/1/17 14:06
 */
class NewestDealAdapter(
        val context:Context,
        val list:MutableList<PurchaseDatalIst>
) :RecyclerView.Adapter<NewestDealAdapter.Holder>(){

    override fun getItemCount() = list.size + 1

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): Holder {
        return Holder(LayoutInflater.from(context).inflate(R.layout.re_item_newest_deal,p0,false))
    }
    override fun onBindViewHolder(holder: Holder, position: Int) {
        if(position == 0){
            holder.tv_time.text = "时间"
            holder.tv_price.text = "价格"
            holder.tv_price.setTextColor(ContextCompat.getColor(context,R.color.color_4d6599))
            holder.tv_num.text = "数量"
        }else{
            val item = list[position-1]
            holder.tv_time.text = SimpleDateFormat("HH:mm:ss").format(item.dealTime)
            holder.tv_price.text = NumberFormat.getInstance().format(item.dealPrice)
            holder.tv_price.setTextColor(ContextCompat.getColor(context,R.color.color_a50000))
            holder.tv_num.text = NumberFormat.getInstance().format(item.dealNum)
        }
    }

    inner class Holder(view:View):RecyclerView.ViewHolder(view){
        val tv_time = view.findViewById<TextView>(R.id.tv_deal_time)
        val tv_price = view.findViewById<TextView>(R.id.tv_deal_price)
        val tv_num = view.findViewById<TextView>(R.id.tv_deal_num)
    }
}