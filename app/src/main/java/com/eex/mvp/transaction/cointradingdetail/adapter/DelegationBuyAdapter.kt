package com.eex.mvp.transaction.cointradingdetail.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import com.eex.R
import com.eex.common.util.CommonUtil
import com.eex.mvp.transaction.cointradingdetail.BuyOrSell
import com.link184.kidadapter.base.BaseAdapter
import com.link184.kidadapter.base.BaseViewHolder
import java.math.BigDecimal
import java.util.*

/**
 * @Description:
 * @Author: xq
 * @CreateDate: 2020/1/16 22:26
 */
class DelegationBuyAdapter(
        val context: Context,
        val list: MutableList<BuyOrSell>,
        val sortList: MutableList<BuyOrSell> = ArrayList(),
        private var number: Int = 3
) : BaseAdapter<BuyOrSell, DelegationBuyAdapter.Holder>(list) {

    fun setNumber(num: Int) {
        number = num
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): Holder {
        return Holder(LayoutInflater.from(context).inflate(R.layout.re_item_dele_buy, p0, false))
    }

    inner class Holder(view: View) : BaseViewHolder<BuyOrSell>(view) {
        val process = view.findViewById<ProgressBar>(R.id.pb_buy)
        val buyPrice = view.findViewById<TextView>(R.id.tv_buy_price)
        val buyNum = view.findViewById<TextView>(R.id.tv_buy_num)
        override fun bindView(item: BuyOrSell) {
            buyPrice.text = CommonUtil.cutNumber(item.delAmount, number).toString()
            buyNum.text = CommonUtil.cutNumber(item.residueNum, number).toString()

            if (sortList.isNotEmpty()) {
                val max = sortList[0].residueNum
                val cur = if (max == 0.0) {
                    0
                } else {
                    BigDecimal((item.residueNum / max * 100).toString()).setScale(10, BigDecimal.ROUND_HALF_UP).toInt()
                }
                process.progress = /*(Math.random() * 10).toInt()*/ cur
            }
        }
    }
}