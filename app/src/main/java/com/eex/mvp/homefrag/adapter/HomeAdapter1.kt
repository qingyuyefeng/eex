package com.eex.mvp.homefrag.adapter

import android.content.Context
import android.support.v4.content.ContextCompat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.link184.kidadapter.base.BaseAdapter
import com.link184.kidadapter.base.BaseViewHolder
import com.eex.R
import com.eex.domin.entity.home.HomeBean2
import java.math.BigDecimal

/**
 * @Description:
 * @Author: xq
 * @CreateDate: 2019/12/23 15:38
 */
class HomeAdapter1(
        private val context: Context,
        list: MutableList<HomeBean2>
) : BaseAdapter<HomeBean2, HomeAdapter1.Holder1>(list) {
    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): Holder1 {
        return Holder1(LayoutInflater.from(context).inflate(R.layout.re_home_item1, p0, false))
    }

    inner class Holder1(view: View) : BaseViewHolder<HomeBean2>(view) {
        val tv_name = view.findViewById<TextView>(R.id.tv_name)
        val tv_count = view.findViewById<TextView>(R.id.tv_count)
        val tv_rate = view.findViewById<TextView>(R.id.tv_rate)
        val tv_result = view.findViewById<TextView>(R.id.tv_result)


        override fun bindView(item: HomeBean2) {
            tv_name.text = item.delKey!!.replace("_", "/")


            val newPrice = item.newPrice ?: 0.0
            val lastPrice = item.lastPrice ?: 0.0


            tv_count.text = item.newPrice.toString()
            tv_count.setTextColor(ContextCompat.getColor(context,if(newPrice >= lastPrice) R.color.color_00a546 else R.color.color_cc3333))

            tv_rate.text = if (lastPrice != 0.0)
                "${(((newPrice - lastPrice) / lastPrice * 10000).toInt()) / 100.0}%"
            else "0%"
            tv_rate.setTextColor(ContextCompat.getColor(context,if(newPrice >= lastPrice) R.color.color_00a546 else R.color.color_cc3333))
            tv_result.text = "≈" + item.usdtCny!!.setScale(4, BigDecimal.ROUND_DOWN) + "CNY"

            itemView.setOnClickListener {
                itemClick?.click(layoutPosition)
            }


        }

    }

    private var itemClick: ItemClick? = null
    fun setItemClick(itemClick: ItemClick) {
        this.itemClick = itemClick
    }

    interface ItemClick {
        fun click(pos: Int)
    }
}