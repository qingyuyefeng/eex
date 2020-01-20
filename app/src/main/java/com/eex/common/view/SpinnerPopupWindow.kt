package com.eex.common.view

import android.content.Context
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupWindow
import android.widget.TextView
import com.link184.kidadapter.base.BaseAdapter
import com.link184.kidadapter.base.BaseViewHolder
import com.eex.R
import com.eex.domin.entity.financialcenter.MoneyCoin
import com.eex.domin.entity.moneyaddress.CoinBean

/**
 * @Description:
 * @Author: xq
 * @CreateDate: 2019/12/10 10:01
 */
class SpinnerPopupWindow<T>(val context: Context,
                            list: MutableList<T>,
                            wid: Int = -1,
                            val bgColor: Int = -1,
                            val tvColor: Int = -1,
                            attributeSet: AttributeSet? = null) : PopupWindow(context, attributeSet) {

    init {
        val view = View.inflate(context, R.layout.spinner_pop_layout, null)
        val reView = view.findViewById<RecyclerView>(R.id.rv_spin_pop)
        val adapter = SpinnerAdapter(list)
        reView.adapter = adapter
        reView.layoutManager = LinearLayoutManager(context)
        reView.addItemDecoration(DividerItemDecoration(context,1))
        contentView = view
        width = if(wid == -1) ViewGroup.LayoutParams.MATCH_PARENT else wid
        height = ViewGroup.LayoutParams.WRAP_CONTENT
        animationStyle = R.style.anim_popup_dir
        isOutsideTouchable = true
        isFocusable = true
    }

    interface ItemClick {
        fun itemClick(position: Int)
    }

    private var itemC: ItemClick? = null

    fun setItemClick(click: ItemClick) {
        itemC = click
    }

    inner class SpinnerAdapter(
            list: MutableList<T>
    ) : BaseAdapter<T, SpinnerAdapter.SpinnerHolder>(list) {
        override fun onCreateViewHolder(p0: ViewGroup, p1: Int): SpinnerHolder {
            return SpinnerHolder(LayoutInflater.from(context).inflate(R.layout.item_spin_pop, p0, false))
        }

        inner class SpinnerHolder(view: View) : BaseViewHolder<T>(view) {
            val tv1 = view.findViewById<TextView>(R.id.tv_spin_item1)
            val tv2 = view.findViewById<TextView>(R.id.tv_spin_item2)
            override fun bindView(item: T) {
                if (bgColor != -1) {
                    itemView.setBackgroundColor(bgColor)
                }
                if (tvColor != -1) {
                    tv1.setTextColor(tvColor)
                    tv2.setTextColor(tvColor)
                }
                when (item) {
                    is MoneyCoin -> {
                        tv1.text = item.coinCode
                    }
                    is CoinBean -> {
                        tv1.text = item.coinCode
                    }
                    is String -> {
                        tv1.text = item
                    }
                    //这里继续扩展。。。
                }

                itemView.setOnClickListener {
                    itemC?.itemClick(layoutPosition)
                }
            }
        }
    }


}